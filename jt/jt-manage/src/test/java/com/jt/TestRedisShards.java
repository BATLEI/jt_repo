package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {
	
	/**
	 * 操作时需要将多台redis当做1台使用.
	 */
	@Test
	public void testShards() {
		List<JedisShardInfo> shards = 
				new ArrayList<JedisShardInfo>();
		JedisShardInfo info1 = 
				new JedisShardInfo("192.168.175.129",6379);
		JedisShardInfo info2 = 
				new JedisShardInfo("192.168.175.129",6380);
		JedisShardInfo info3 = 
		new JedisShardInfo("192.168.175.129",6381);
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		
		//操作分片 redis对象工具类
		ShardedJedis shardedJedis = 
				new ShardedJedis(shards);
		shardedJedis.set("www","1902班");
		System.out.println(shardedJedis.get("1902"));
	}
}
