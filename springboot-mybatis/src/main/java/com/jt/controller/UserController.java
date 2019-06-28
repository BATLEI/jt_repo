package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.User;
import com.jt.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//用户通过localhost:8090/findAll获取用户数据
	@RequestMapping("/findAll")
	public List<User> findAll(){
		
		return userService.findAll();
	}
	
	//用户新增
	public int insertUser(User user) {
		
		return userService.insertUser(user);
	}
	
	
	public int updateUser(User user) {
		
		return userService.updateUser(user);
	}
	
	
	
	
	
	
	
	
}
