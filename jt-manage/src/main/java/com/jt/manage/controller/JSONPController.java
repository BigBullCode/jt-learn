package com.jt.manage.controller;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.Item;

@Controller
public class JSONPController {
	/*@RequestMapping("/web/testJSONP")
	@ResponseBody
	public String testJSONP(String callback){
		return callback+"("+"{id:100,name:\"tom\"}"+")";
	}*/
	
	
	
	/**
	 * Sping提供的JSONP的解决方案
	 * @param callback
	 * @return
	 * @throws JsonProcessingException 
	 */
	/*@RequestMapping("/web/testJSONP")
	@ResponseBody
	public MappingJacksonValue testJSONP(String callback){
		Item item=new Item();
		item.setId(90L);
		item.setTitle("dfdfjdlfkd");
		MappingJacksonValue jacksonValue=new MappingJacksonValue(item);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}*/
	
	
	@RequestMapping("/web/testJSONP")
	@ResponseBody
	public String testJSONP(String callback) throws JsonProcessingException{
		Item item =new Item();
		item.setId(8L);
		item.setTitle("ssssssssss");
		ObjectMapper objectMapper=new ObjectMapper();
		String json = objectMapper.writeValueAsString(item);
		return callback+"("+json+")";
	}
}





















