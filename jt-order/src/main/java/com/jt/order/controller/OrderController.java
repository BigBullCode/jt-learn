package com.jt.order.controller;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	private static final ObjectMapper objectMapper=new ObjectMapper();
	
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON){
		try {
			Order order=objectMapper.readValue(orderJSON, Order.class);
			String orderId=orderService.saveOrder(order);
			if(StringUtils.isEmpty(orderId)){
				throw new RuntimeException();
			}
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增订单失败");
	}
	
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId){
		Order order = orderService.findOrderById(orderId);
		return order;
	}
}
