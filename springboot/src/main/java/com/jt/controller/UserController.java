package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//要求:如果返回值都是JSON/String则使用该注解
@RestController //= @Controller + @ResponseBody
public class UserController {
	
	@RequestMapping("/hello")
	//调用视图解析器跳转页面
	public String hello() {
		
		return "你好SpringBoot";
	}
	
	
	
	
	
	
}
