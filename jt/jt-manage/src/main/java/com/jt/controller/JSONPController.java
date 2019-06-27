package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	//@RequestMapping("/web/testJSONP")
	public String testJSONP(String callback) {
		User user = new User();
		user.setId(100);
		user.setName("tomcat");
		
		String json = ObjectMapperUtil.toJSON(user);
		return callback+"("+json+")";
	}
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		User user = new User();
		user.setId(100);
		user.setName("tomcat");
		JSONPObject object = new JSONPObject(callback, user);
		return object;
	}
}
