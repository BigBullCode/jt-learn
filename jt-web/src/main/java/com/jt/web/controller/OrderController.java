package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	//跳转页面.结算到订单确认页面的跳转
	@RequestMapping("/create")
	public String toOrderCart(Model model){
		//根据用户id查询购物车信息
		Long userId=UserThreadLocal.get().getId();
		List<Cart> carts=cartService.findCartByUserId(userId);
		model.addAttribute("carts",carts);//这里必须以这种格式来添加数据
		return "order-cart";
	}
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrde(Order order){
/*		try {
			Long userId=UserThreadLocal.get().getId();
			order.setUserId(userId);
			//入库后返回订单号2
			String orderId=orderService.saveOrder(order);
			if(StringUtils.isEmpty(orderId)){
				throw new RuntimeException();
			}
			return SysResult.oK(orderId);//入库后返回订单号
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单新增失败");*/
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			//入库后返回订单号
			String orderId = orderService.saveOrder(order);
			//判断数据是否有效
			if(StringUtils.isEmpty(orderId)){
				throw new RuntimeException();
			}
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"订单新增失败");
	}
	
	@RequestMapping("/success")
	public String findOrderById(String id,Model model){
		Order order=orderService.findOrderById(id);
		
		model.addAttribute("order",order);
		return "success";
	}
	
}
