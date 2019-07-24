package com.test;

import java.sql.SQLException;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class TestDataSource extends TestBase{
	
	@Test
	public void testDataSource() throws SQLException {
		//1.获取bean对象:DruidDataSource
		DruidDataSource dataSource = context.getBean("dataSource",DruidDataSource.class);
		//2.获取连接
		DruidPooledConnection conn = dataSource.getConnection();
		System.out.println(conn);
	} 
}
