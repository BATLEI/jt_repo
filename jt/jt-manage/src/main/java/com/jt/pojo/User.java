package com.jt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Data
//当程序转化对象时如果遇到未知属性则忽略
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private Integer id;
	private String name;
	private Integer age;
	private String sex;
	public Integer getIds() {
		
		return id;
	}
	
	
}
