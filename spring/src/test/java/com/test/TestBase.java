package com.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {
	
	ApplicationContext context;
	@Before
	public void init() {
		context = 
			new ClassPathXmlApplicationContext("spring-configs.xml");
	} 
	@Test
	public void testContext() {
		System.out.println(context);
	}
	
}
