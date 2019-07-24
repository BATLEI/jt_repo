package com.jt;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMybatisPlus {
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void insert() {
		User user = new User();
		user.setName("mybatisplus");
		user.setAge(19);
		user.setSex("男");
		int rows = userMapper.insert(user);
		System.out.println("影响行数:"+rows);
	}
	
	/**查询用户列表信息
		将对象中不为null的属性当做where条件
		条件:age=18 
		关键字说明:
		= eq,> gt,< lt,ge >=,le <=  
	*/
	@Test
	public void testFind() {
		//1.方式1.使用对象封装
		//User user = new User();
		//user.setAge(18);
		
		//2.直接使用字段赋值
		QueryWrapper<User> queryWrapper 
					= new QueryWrapper<User>();
		//测试等于
		//queryWrapper.eq("age",18);
		//测试大于
		queryWrapper.ge("age",18);
		List<User> userList = 
				userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 3.数据更新 将年龄为18岁的用户改19岁
	 * entity:需要修改数据的值
	 * updateWrapper 条件构造器 充当where条件
	 */
	@Test
	public void testUpdate() {
		User user = new User();
		user.setAge(19);
		UpdateWrapper<User> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.eq("age", 18);
		userMapper.update(user, updateWrapper);
	}
	
	//删除name字段为null的数据
	@Test
	public void deleteUser() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.isNull("name");
		userMapper.delete(queryWrapper);
	}
}
