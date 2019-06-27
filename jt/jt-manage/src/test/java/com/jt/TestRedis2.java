package com.jt;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	
	//测试hash/list/事务控制
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.175.129",6379);
		jedis.hset("user1","id","200");
		jedis.hset("user1", "name", "tomcat服务器");
		String result = jedis.hget("user1","name");
		System.out.println(result);
		System.out.println(jedis.hgetAll("user1"));
	}
	
	//编辑list集合
	@Test
	public void list() {
		Jedis jedis = 
				new Jedis("192.168.175.129",6379);
		jedis.lpush("list","1","2","3","4");
		System.out.println(jedis.rpop("list"));
	}
	
	
	//redis事务控制
	@Test
	public void TestTx() {
		Jedis jedis = new Jedis("192.168.175.129",6379);
		Transaction transaction = jedis.multi();	//开启事务
		try {
			transaction.set("ww", "wwww");
			transaction.set("dd",  null );
			transaction.exec();
		} catch (Exception e) {
			transaction.discard();
		}
	}
}
