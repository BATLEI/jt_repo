package com.test;


import org.junit.Test;

import com.company.dao.DefaultSearchDao;

public class TestSearchDao extends TestBase{
	
	@Test
	public void testSearchDao() {
		DefaultSearchDao dao = context.getBean("search",DefaultSearchDao.class);
		System.out.println(dao);
	}
}
