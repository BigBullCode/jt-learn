package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private HttpClientService httpClient;
	
	private static final ObjectMapper objectMapper=new ObjectMapper();
	
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		String url="http://cart.jt.com/cart/query/"+userId;
		
		String result=httpClient.doGet(url);
		try {
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			if(sysResult.getStatus()==200){
				List<Cart> cartList = (List<Cart>) sysResult.getData();
				return cartList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveCart(Cart cart){
		String url="http://cart.jt.com/cart/save";
		Map<String,String> params = new HashMap<String, String>();
		String cartJSON = null;
		try {
			cartJSON = objectMapper.writeValueAsString(cart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.put("cartJSON", cartJSON);
		
		httpClient.doPost(url, params);
	}
	
	
	@Override
	public void updateCartNum(Long userId, Long itemId, Integer num) {
		String url="http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		try {
			httpClient.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCart(Long userId,Long itemId) {
		
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		try {
			String jsonData = httpClient.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
