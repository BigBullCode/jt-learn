package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private HttpClientService httpClient;

	private static final ObjectMapper objectMapper=new ObjectMapper();

	//返回新增订单的orderId
	/*@Override
	public String saveOrder(Order order) {
		String url="http://order.jt.com/order/create";

		try {
			String orderJSON = objectMapper.writeValueAsString(order);
			Map<String,String> params=new HashMap<String,String>();
			params.put("orderJSON", orderJSON);

			String result=httpClient.doPost(url,params);

			SysResult sysResult = objectMapper.readValue(result, SysResult.class);

			if(sysResult.getStatus()==200){
				//获取OrderId
				return (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return null;
	}*/
	//返回新增订单的orderId
		@Override
		public String saveOrder(Order order) {
			String url = "http://order.jt.com/order/create";
			try {
				String orderJSON = objectMapper.writeValueAsString(order);
				Map<String,String> params = new HashMap<String,String>();
				params.put("orderJSON", orderJSON);
				String result = httpClient.doPost(url, params);
				SysResult sysResult = objectMapper.readValue(result,SysResult.class);
				if(sysResult.getStatus() == 200){
					//获取orderId
					return (String) sysResult.getData();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			return null;
		}
	@Override
	public Order findOrderById(String id) {
		Order order=null;
		String url="http://order.jt.com/order/query/"+id;
		String result = httpClient.doGet(url);
		try {
		if(!StringUtils.isEmpty(result)){
				order = objectMapper.readValue(result, Order.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}	
}

