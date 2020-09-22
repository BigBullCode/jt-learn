package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	private static final ObjectMapper objectMapper=new ObjectMapper();
	
	//根据用户id查询购物车信息
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId){
		try {
			List<Cart> cartList =cartService.findCartListByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车信息查询失败");
	}
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON){
		try {
			Cart cart=objectMapper.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车添加失败");
	}
	
	
	
	
	//url:"http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		@RequestMapping("/update/num/{userId}/{itemId}/{num}")//添加userId为了保证购物车的准确性
		@ResponseBody
		public SysResult updateCartNum(@PathVariable Long userId,@PathVariable Long itemId,@PathVariable Integer num){

			try {
				return cartService.updateCartNum(userId,itemId,num);
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.build(201, "商品修改失败");
			}
		}

		/*@Override
		public void setBeanName(String name) {
			System.out.println("我是controller"+name);
			
		}
		*/
		//http://cart.jt.com/cart/delete/{userId}/{itemId}
		@RequestMapping("/delete/{userId}/{itemId}")
		@ResponseBody
		public SysResult deleteCart(@PathVariable Long userId,@PathVariable Long itemId){
			try {
				cartService.deleteCart(userId,itemId);
				return SysResult.oK();
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.build(201, "商品删除失败");
			}
		}
	
	
		
	
	
	
	
	
	
}
