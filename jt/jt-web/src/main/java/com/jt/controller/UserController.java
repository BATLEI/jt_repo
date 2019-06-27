package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	//	@Autowired
	//	private UserService userService;

	//导入dubbo的接口
	@Reference(timeout=3000,check=false)
	private DubboUserService dubboUserService;
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}

	//使用dubbo的形式实现业务调用
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			dubboUserService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
			return SysResult.fail();
		}
	}

	/**
	 * 实现用户的登录
	 * cookie.setMaxAge(0)  立即销毁
	 * cookie.setMaxAge(-1)   会话结束销毁
	 * cookie.setPath("/")   cookie的权限
	 * 
	 * @param user
	 * @return
	 */

	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {
		try {
			String token = dubboUserService.findUserByUP(user);
			//将token保存到cookie
			if (!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(7*24*3600);
				cookie.setDomain("jt.com");      //要求所有的  xxxx.jt.com  共享cookie
				cookie.setPath("/");
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies.length != 0) {
			String token = null;
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
			//判断token数据是否有值
			if (!StringUtils.isEmpty(token)) {
				jedisCluster.del(token);
				Cookie cookie = new Cookie("JT_TICKET", "");
				cookie.setMaxAge(0);
				cookie.setDomain("jt.com");
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		return "redirect:/";

	}
}
