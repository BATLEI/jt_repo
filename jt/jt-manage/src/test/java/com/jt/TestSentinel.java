package com.jt;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	//测试哨兵get/set操作
	@SuppressWarnings("resource")
	@Test
	public void test1() {
		HashSet<String> sentinels = new HashSet<>();
		sentinels.add("192.168.22.129:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = sentinelPool.getResource();
		jedis.set("cc", "端午节过后没假了");
		System.out.println(jedis.get("cc"));
		jedis.close();
	}
}
