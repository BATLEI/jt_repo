package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void saveUser(User user);

	String findUserByUP(User user);



	
}
