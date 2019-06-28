package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.controller.UserController;
import com.jt.pojo.User;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCRUD {
	
	@Autowired		//从容器中取值
	private UserController userController;
	
	@Test
	public void insertUser() {
		User user = new User();
		user.setName("苏妲己c").setSex("男").setAge(18);
		userController.insertUser(user);
		System.out.println("入库成功!!!!!");
	}
	
	//测试修改
	@Test
	public void testUpdate() {
		User user = new User();
		user.setAge(18).setName("哈利波特!!!");
		userController.updateUser(user);
	}
}
