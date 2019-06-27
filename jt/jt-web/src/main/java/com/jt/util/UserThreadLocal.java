package com.jt.util;

import com.jt.pojo.User;
//ThreadLocal是线程安全的
public class UserThreadLocal {
	/**
	 * 获取多个数据,用Map<k,v>集合
	 */
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	public static void set(User user) {
		thread.set(user);
	}
	
	public static User get() {
		return thread.get();
	}
	
	public static void remove() {
		thread.remove();
	}
}
