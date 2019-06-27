package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	/**
	 * 分析:如果能够获取url值,
	 * 		这个值就是页面跳转名称
	 * 思路: 获取url中指定参数
	 * 
	 * restFul:
	 * 		1.要求参数必须使用/分割
	 * 		2.参数位置必须固定
	 * 		3.接收参数时必须使用{}标识参数.
	 * 		    使用特定的注解 @PathVariable
	 * 		    并且名称最好一致
	 */
	//根据用户请求,跳转页面
	@RequestMapping("/page/{moduleName}")
	public String itemAdd(@PathVariable String moduleName) {
		
		return moduleName;
	}
}
