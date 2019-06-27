package com.jt.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	@Transactional
	@Override
	public void saveUser(User user) {
		// 将密码加密
		//补齐数据入库,email使用电话
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
			.setEmail(user.getPhone())
			.setCreated(new Date())
			.setUpdated(user.getCreated());
		
		userMapper.insert(user);
	}
	
	//校验用户名密码
	//数据正确     1.生成加密秘钥:md5(JT_TICKET_+username+当前毫秒数)
	//        2.将userDB数据转化为userJson
	//        3.将数据保存到redis7天超时
	@Override
	public String findUserByUP(User user) {
		String token = null;
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		if (userDB != null) {
			token = "JT_TICKET_"+userDB.getUsername()+System.currentTimeMillis();
			token = DigestUtils.md5DigestAsHex(token.getBytes());
			//脱敏处理
			userDB.setPassword("不告诉你");
			
			String userJSON = ObjectMapperUtil.toJSON(userDB);
			jedisCluster.setex(token, 7*24*60*60, userJSON);
		}
		return token;
		
	}
	
}
