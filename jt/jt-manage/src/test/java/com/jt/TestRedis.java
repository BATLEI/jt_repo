package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.User;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	//Mybatis~~~对象/集合~~~~Redis
	//String类型操作方式   配置文件3处  防火墙
	//IP:端口号
	@Test
	public void testString() {
		Jedis jedis = 
		new Jedis("192.168.175.129",6379);
		jedis.set("1902","1902班");
		jedis.expire("1902", 10);
		System.out.println(jedis.get("1902"));
		
	}
	
	//设定数据的超时方法
	//分布式锁!!!!!
	@Test
	public void testTimeOut() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.175.129",6379);
		jedis.setex("aa", 2, "aa");
		System.out.println(jedis.get("aa"));
		Thread.sleep(3000);
		//当key不存在时操作正常.当key存在时,则操作失败
		Long result = jedis.setnx("aa","bb");
		System.out.println("获取输出数据:"+result+":"+jedis.get("aa"));
	}
	
	/**
	 * 3.利用Redis保存业务数据  数据库
	 *  数据库数据:  对象 Object
	 *  String类型要求只能存储字符串类型
	 *  item ~~~  JSON  ~~~ 字符串
	 */
	@Test
	public void objectToJSON() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(1000L)
				.setItemDesc("测试方法")
				.setCreated(new Date())
				.setUpdated(new Date());
				
		ObjectMapper mapper = new ObjectMapper();
		//转化JSON时必须 get/set方法
		String json = 
				mapper.writeValueAsString(itemDesc);
		System.out.println(json);
		//将json串转化为对象
		ItemDesc desc2 = 
				mapper.readValue(json, ItemDesc.class);
		System.out.println("测试对象:"+desc2);
	}
	
	//实现List集合与JSON转化
	@SuppressWarnings("unused")
	@Test
	public void listTOJSON() throws IOException {
		ItemDesc itemDesc1 = new ItemDesc();
		itemDesc1.setItemId(1000L)
				.setItemDesc("测试方法");
		
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(1000L)
				.setItemDesc("测试方法");
		List<ItemDesc> list = new ArrayList<ItemDesc>();
		list.add(itemDesc1);
		list.add(itemDesc2);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println("集合转化为JSON"+json);
		//将数据保存到redis中
		Jedis jedis = new Jedis("192.168.175.129",6379);
		jedis.set("itemDescList", json);
		
		//从redis中获取数据
		String result = jedis.get("itemDescList");
		List<ItemDesc> descList = mapper.readValue(result,list.getClass());
		System.out.println(descList);
	}
	
	
	
	/**
	 * 1.首先获取对象的getXXXX方法.
	 * 2.将get去掉,之后首字母小写获取属性的名称
	 * 3.之后将属性名称 : 属性的值进行拼接.
	 * 4.形成json串(字符串)
	 * @throws JsonProcessingException
	 */
	@Test
	public void userToJSON() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setId(1000);
		user.setName("json测试");
		user.setAge(18);
		user.setSex("男");
		String json = 
				mapper.writeValueAsString(user);
		System.out.println(json);
	}
	
	/**
	 * 1.获取userJSON串
	 * 2.通过json串获取json中key
	 * 3.根据class类型的反射机制实例化对象
	 * 4.根据key调用setKey方法为对象赋值.
	 * 5.最终生成对象.
	 * 6.可以利用@JsonIgnoreProperties
	 * 			(ignoreUnknown = true)
	 * 	 注解忽略未知属性	
	 */
	@Test
	public void jsonToUser() throws IOException {
		//该方法准备json串
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setId(1000);
		user.setName("json测试");
		user.setAge(18);
		user.setSex("男");
		String json = 
				mapper.writeValueAsString(user);
		//以下方法实现了数据的转化
		User user2 = mapper.readValue(json, User.class);
		System.out.println(user2);
	}
}
