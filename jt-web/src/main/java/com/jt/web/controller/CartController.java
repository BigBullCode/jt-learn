package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@RequestMapping("/show")
	public String findCartByUserId(Model model){
		Long userId=UserThreadLocal.get().getId();                           //暂时写死
		List<Cart> cartList=cartService.findCartByUserId(userId);
		
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	/**
	 * 通过商品id,添加购物车
	 * @param itemId
	 * @param cart
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public String addCart(@PathVariable Long itemId ,Cart cart){
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
		Long userId=UserThreadLocal.get().getId();
		try {
			cartService.updateCartNum(userId,itemId,num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}
			
			
	//cart/delete/1474391950.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		
		Long userId = UserThreadLocal.get().getId();
		//根据购物车ID删除购物车信息
		cartService.deleteCart(userId,itemId);
		//如果删除成功重定向到购物车页面
		return "redirect:/cart/show.html";
	}
	
}
