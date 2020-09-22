package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/web")
public class WebItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	
	
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public MappingJacksonValue findItemCatAll(String callback){
		//查询全部的商品分类信息
		ItemCatResult itemCatResult=itemCatService.findTtemCatCache();//findItemCatAll();//这里可以用redis缓存
		//回显信息,转为json串
		MappingJacksonValue jacksonValue=new MappingJacksonValue(itemCatResult);
		//回调函数
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
