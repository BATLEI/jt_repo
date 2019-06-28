package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

public interface UserMapper 
				extends BaseMapper<User>{
	
	//查询全部的user表中的数据
	List<User> findAll();

	/**
	 * 规定:	Mybatis中要求数据传参必须是单值
	 * 方案:
	 * 	0.本身就是单值               id
	 * 	1.利用对象将数据封装    id age name
	 * 	2.将数据封装为map集合.  @Param("key")int id
	 * 	3.将数据封装为array/list
	 * @param user
	 * @return
	 */
	@Insert("insert into user values(null,#{name},#{age},#{sex})")
	int insertUser(User user);
	
	//实现用户修改 age=18的 名称改为xxx
	@Update("update user set name=#{name} where age=#{age}")
	int updateUser(User user);
	
	
}
