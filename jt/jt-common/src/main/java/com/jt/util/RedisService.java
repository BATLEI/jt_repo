package com.jt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;
	
	//封装get方法
	public String get(String key) {
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}
	
	public void set(String key,String value) {
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.set(key,value);
		jedis.close();
	}
	
	public void setex(String key,int secondes,String value) {
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.setex(key,secondes,value);
		jedis.close();
	}
}
