package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.User;
import com.jt.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	private UserService userService;
	//用户通过localhost:8090/findAll获取用户数据
	@RequestMapping("findAll")
	public String findAll(Model model){
		List<User> list = userService.findAll();
		model.addAttribute("userList", list);
		return "userList";
	}
}
