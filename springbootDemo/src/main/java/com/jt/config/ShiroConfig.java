package com.jt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jt.pojo.Shiro;

//该类中标识就是xml中数据  <bean id="方法名" class="返回值类型" />
@Configuration		
public class ShiroConfig {
	/**
	 * public 返回值类型  方法名称(参数列表)
	 * <bean id="方法名" class="返回值类型" />
	 * @return
	 */
	@Bean
	public Shiro shiro() {
		
		return new Shiro();
	}
}
