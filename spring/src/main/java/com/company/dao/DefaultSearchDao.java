package com.company.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.company.cache.LruCache;

//spring注入两个对象DruidDataSource   LruCache
public class DefaultSearchDao {
	
	private LruCache lruCache;
	private DruidDataSource druidDataSource;
	
	public Object search(String key) {
		
		
		return key;
	}
	
	public void setDruidDataSource(DruidDataSource druidDataSource) {
		this.druidDataSource = druidDataSource;
	}
	public void setLruCache(LruCache lruCache) {
		this.lruCache = lruCache;
	}
	
	@Override
	public String toString() {
		return "DefaultSearchDao [lruCache=" + lruCache + ", druidDataSource=" + druidDataSource + "]";
	}
	
}
