package com.jt.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//导入配置文件
@PropertySource
(value ="classpath:/properties/dog.properties")
public class Dog {
	@Value("${dog.id}")   //set方法
	private Integer id;
	@Value("${dog.name}")
	private String name;
	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + "]";
	}
	
	
	
	
}
