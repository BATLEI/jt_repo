package com.company.cache;
/**
 * 基于此对象存储出数据
 * 1)存储结构：链表+散列表
 * 2)淘汰算法：LRU
 */
import java.util.LinkedHashMap;

public class LruCache {//将此对象交给Spring管理
    private LinkedHashMap<String,Object> cache;
    private int maxCap;
	public LruCache(int maxCap) {
    	this.maxCap=maxCap;
    }
	
	@SuppressWarnings("serial")
    public void init() {
    	 cache=new LinkedHashMap<String, Object>(
    			 maxCap,0.75f,true){
    		@Override
    		protected boolean removeEldestEntry(java.util.Map.Entry<String, Object> eldest) {
    			return size()>maxCap;
    		}
    	 };//默认false(记录存储顺序) 
     }
	
     public synchronized void put(String key,Object value) {
    	 cache.put(key, value);
     }
     public synchronized Object get(String key) {
    	 return cache.get(key);
     }
     
	@Override
	public String toString() {
		return "LruCache [cache=" + cache + "]";
	}
}







