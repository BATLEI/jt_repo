package com.jt.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		User user = UserThreadLocal.get();
		Long userId = user.getId();
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}

	//实现购物车数量修改
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart,HttpServletRequest request) {
		try {
			User user = UserThreadLocal.get();
			Long userId = user.getId();
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	//实现购物车商品删除
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart,HttpServletRequest request) {
		User user = UserThreadLocal.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	/**
	 * 新增购物车
	 * 页面表单提交 发起post请求  , 携带购物车参数
	 * @param cart
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart,HttpServletRequest request) {
		User user = UserThreadLocal.get();
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.insertCart(cart);
		return "redirect:/cart/show.html";
	}
	
}
