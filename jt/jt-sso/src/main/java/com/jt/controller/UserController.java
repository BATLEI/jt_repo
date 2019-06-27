package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	//由于是跨域的请求,返回值必须处理callback(json)
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		JSONPObject object = null;
		try {
			boolean flag = userService.checkUser(param,type);
			object = new JSONPObject(callback,SysResult.ok(flag));
		} catch (Exception e) {
			e.printStackTrace();
			object = new JSONPObject(callback,SysResult.fail());
		}

		return object;
	} 

	/**
	 * 利用跨域实现数据回显
	 * @return
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		String userJSON = jedisCluster.get(ticket);
		if (StringUtils.isEmpty(userJSON)) {
			return new JSONPObject(callback, SysResult.fail());
		}else {
			return new JSONPObject(callback, SysResult.ok(userJSON));
		}
	}
}
