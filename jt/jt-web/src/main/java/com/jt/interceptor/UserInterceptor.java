package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
/**
 * 在spring4中要求必须重写3个方法
 * spring5中添加了default属性,不需要都重写
 */
@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * true:放行
	 * false:拦截    跳转到登录页面
	 * 业务逻辑:
	 * 1.获取cookie
	 * 2.从cookie中获取token
	 * 3.判断redis缓存中是否有数据
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		//1.获取cookie
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//2.token是否为空
		if (!StringUtils.isEmpty(token)) {
			String userJSON = jedisCluster.get(token);
			if (!StringUtils.isEmpty(userJSON)) {
				
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				//request.setAttribute("JT_USER", user);
				//request.getSession().setAttribute("JT_USER", user);
				UserThreadLocal.set(user);
				return true;
			}
			
		}
		response.sendRedirect("/user/login.html");
		return false;
	}
	
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    		ModelAndView modelAndView) throws Exception {
    	// TODO Auto-generated method stub
    	HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    		throws Exception {
    	//用完之后销毁,以免占用服务器内存
    	//request.getSession().removeAttribute("JT_USER");
    	UserThreadLocal.remove();
    }
    
}
