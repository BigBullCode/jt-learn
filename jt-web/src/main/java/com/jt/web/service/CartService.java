package com.jt.web.service;

import java.util.List;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	

	void deleteCart(Long userId, Long itemId);


	void updateCartNum(Long userId, Long itemId, Integer num);

}
