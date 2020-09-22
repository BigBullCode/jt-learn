package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl extends BaseService implements CartService {
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		List<Cart> cartList = cartMapper.select(cart);
		return cartList;
	}
	
	/**
	 * 如果数据库中有数据,则做数量更新操作
	 * 如果没有,新增购物车
	 */
	@Override
	public void saveCart(Cart cart) {
		Cart cartDB=cartMapper.findCartByUI(cart);
		if(cartDB==null){
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else{
			int num=cart.getNum()+cartDB.getNum();
			cartDB.setNum(num);
			cartMapper.updateByPrimaryKey(cartDB);
		}
	}
	
	@Override
	public void deleteCart(Long userId, Long itemId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		super.deleteByWhere(cart);
		
	}
	@Override
	public SysResult updateCartNum(Long userId, Long itemId, Integer num) {
		try {
			cartMapper.updateCartByUserIdAndItemId(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "商品修改失败");
		}
	}

	
}
