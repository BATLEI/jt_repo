package com.jt.service;

import java.util.List;

import com.jt.pojo.User;

public interface UserService {

	List<User> findAll();
	int updateByAge(Integer age);
	int insertUser(User user);
	int deleteByName(User user);
}
