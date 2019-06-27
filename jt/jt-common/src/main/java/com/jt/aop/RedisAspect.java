package com.jt.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

@Component	//交给spring容器管理
@Aspect		//标识切面类
public class RedisAspect {
//	@Autowired(required=false)
//	private ShardedJedis shardedJedis;
//	@Autowired(required=false)
//	private RedisService shardedJedis;
	@Autowired(required=false)
	private JedisCluster shardedJedis;
	
	
	@Around("@annotation(cache_find)")
	public Object around(ProceedingJoinPoint joinPoint,Cache_find cache_find) {
		//获取key
		String key = getKey(joinPoint,cache_find);
		//查缓存
		String result = shardedJedis.get(key);
		Object data = null;
		if (StringUtils.isEmpty(result)) {
			//缓存为空,查数据库
			try {
				data = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(data);
				//将查出的数据存入缓存
				if (cache_find.secondes()==0) {
					shardedJedis.set(key, json);
				}else {
					shardedJedis.setex(key, cache_find.secondes(), json);
				}
				System.out.println("查数据库");
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else {
			//缓存不为空,查缓存
			data = ObjectMapperUtil.toObject(result, getTargetClass(joinPoint));
			System.out.println("查缓存");
		}
		return data;
	}

	private Class getTargetClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		
		return signature.getReturnType();
	}

	private String getKey(ProceedingJoinPoint joinPoint, Cache_find cache_find) {
		KEY_ENUM keyType = cache_find.keyType();
		//判断key的类型,分情况处理
		if (keyType.equals(KEY_ENUM.AUTO)) {
			String str = String.valueOf(joinPoint.getArgs()[0]) ;
			return cache_find.key()+"_"+str;
		}else {
			return cache_find.key();
		}
	}
}
