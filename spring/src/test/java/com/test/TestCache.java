package com.test;

import org.junit.Test;

import com.company.cache.LruCache;

public class TestCache extends TestBase{
	@Test
	public void testLruCache() {
		LruCache cache = context.getBean("lruCache",LruCache.class);
		cache.put("A", 100);
		cache.put("B", 200);
		cache.put("C", 300);
		cache.put("D", 400);
		cache.get("B");
		cache.put("E", 500);
		System.out.println(cache);
	}
}
