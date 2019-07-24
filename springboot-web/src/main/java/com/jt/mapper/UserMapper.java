package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

public interface UserMapper extends BaseMapper<User>{
	
	//查询全部的user表中的数据
	List<User> findAll();
	
	int updateByAge(Integer age);
	int insertUser(@Param("name")String name,
			@Param("age")Integer age,
			@Param("sex")String sex);
	int deleteByName(String name);
}
