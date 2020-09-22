package com.jt.sso.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	/*@Autowired
	private UserService userService;
	
	//实现用户的检验
	@RequestMapping("/check/{parm}/{type}")//restful的方式要直接调用,有@PathVariable注解
	@ResponseBody
	public MappingJacksonValue checkUser(
			@PathVariable String param,@PathVariable Integer type,String callback){
		//查询后台的数据,返回结果的信息,返回true信息已存在,用布尔
		boolean flag=userService.findCheckUser(param,type);
		MappingJacksonValue jacksonValue=new MappingJacksonValue(SysResult.oK(flag));
		
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}*/
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	//实现用户的检验 http://sso.jt.com/user/check/{param}/{type}
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue checkUser(@PathVariable String param,
			@PathVariable Integer type,String callback){
		//查询后台数据 返回结果信息 true 信息已存在
		boolean flag = userService.findCheckUser(param,type);
		MappingJacksonValue jacksonValue = 
				new MappingJacksonValue(SysResult.oK(flag));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户新增失败");
	}
	
	//实现用户登录
/*	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(User user){
		try {
			//获取服务端返回的token数据
			String token=userService.finUserByUP(user);
			if(token==null){
				throw new RuntimeException();
			}
			return SysResult.oK(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户查询失败");
	}
	*/
	
	//实现用户登录
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUp(User user){
		try{
			//获取服务端返回的token数据
			String token=userService.findUserByUP(user);
			if(token==null){
				throw new RuntimeException();
			}
			return SysResult.oK(token);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SysResult.build(201, "用户查询失败");
	}
	
	
	//通过ticket获取用户信息
	/*@RequestMapping("/query/{ticket}")
	@ResponseBody
	public MappingJacksonValue findUserByTicket(@PathVariable String ticket,String callback){
		
		MappingJacksonValue jacksonValue = null;
		try {
			String userJSON = jedisCluster.get(ticket);
			
			if(!StringUtils.isEmpty(userJSON)){
				*//**
				 * 问题:是否需要将userJSON转化为user对象返回??
				 * 答:不需要 因为页面js解析时已经处理了
				 * var _data = JSON.parse(data.data);
				 *//*
				jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
				jacksonValue.setJsonpFunction(callback);
				return jacksonValue;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		jacksonValue = new MappingJacksonValue(SysResult.build(201,"查询数据失败"));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue; 
	}*/
	//通过ticket获取用户信息
	/**
	 * 根据页面信息提示为jsonp跨域请求
	 */
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public MappingJacksonValue findUserByTicket(@PathVariable String ticket,String callback){
		MappingJacksonValue jacksonValue =null;
		try {
			String userJSON = jedisCluster.get(ticket);
			
			if(!StringUtils.isEmpty(userJSON)){
				//此时不许要将userJSON转化为user对象返回，因为js中是用来JSON.parse(data.data),处理了
				jacksonValue =new MappingJacksonValue(SysResult.oK(userJSON));
				jacksonValue.setJsonpFunction(callback);
				return jacksonValue;
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		jacksonValue=new MappingJacksonValue(SysResult.build(201, "查询数据失败"));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}





















