package com.jt;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest//springboot启动加载对象
public class SpringbootApplicationTests {

	@Autowired
	private User user;
	
	@Test
	public void testUser() {
		user.setId(100);
		user.setName("1902班!!!!");
		System.out.println(user);
	}
	
	
	
	
	
	
	
	/*@Test
	public void contextLoads() {
		//要求:获取user对象从容器中获取
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/xxx/xxx.xml");
		User user = context.getBean("user");
		user.setId(111);
		
	}*/
	
	
	
	
	

}
