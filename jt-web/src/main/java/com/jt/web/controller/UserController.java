package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	
	//实现通用页面跳转,登录注册
	@RequestMapping("/{Module}")
	public String toModule(@PathVariable String Module){
		
		return Module;
	}
	
	@RequestMapping("/doRegister")
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
	
	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response){
		try {
			//获取服务端返回的token数据
			String token=userService.finUserByUP(user);
			//判断返回值是否有效
			if(StringUtils.isEmpty(token)){
				throw new RuntimeException();
			}
			//表示获取token是正确的,将token保存在cookie中
			Cookie cookie=new Cookie("JT_TICKET",token);
			cookie.setPath("/");
			cookie.setMaxAge(3600*24*7);
			response.addCookie(cookie);
			
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败");
	}
	
	
	
/*	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if("JT_TICKET".equals(cookie.getName())){
				String ticket=cookie.getValue();
				jedisCluster.del(ticket);
				break;
			}
		}
		//清空Cooke数据
		Cookie cookie=new Cookie("JT_TICKET","");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		//重定向到系统首页
		return "redirect:/index.html";
	}*/
	
	
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies =request.getCookies();
		for(Cookie cookie:cookies){
			if("JT_TICKET".equals(cookie.getName())){
				String ticket =cookie.getValue();
				jedisCluster.del(ticket);
				break;
			}
		}
		//清空cookie数据
		
		/*
		 * 正常的cookie只能在一个应用中共享，即一个cookie只能由创建它的应用获得。
           1.可在同一应用服务器内共享方法：设置cookie.setPath("/"); 
                                 本机tomcat/webapp下面有两个应用：webapp_a和webapp_b， 
			1）原来在webapp_a下面设置的cookie，在webapp_b下面获取不到，path默认是产生cookie的应用的路径。

			2）若在webapp_a下面设置cookie的时候，增加一条cookie.setPath("/");或者cookie.setPath("/webapp_b/");
			就可以在webapp_b下面获取到cas设置的cookie了。

			3）此处的参数，是相对于应用服务器存放应用的文件夹的根目录而言的(比如tomcat下面的webapp)，因此cookie.setPath("/");之后，可以在webapp文件夹下的所有应用共享cookie，而cookie.setPath("/webapp_b/");
			是指cas应用设置的cookie只能在webapp_b应用下的获得，即便是产生这个cookie的webapp_a应用也不可以。

			4）设置cookie.setPath("/webapp_b/jsp")或者cookie.setPath("/webapp_b/jsp/")的时候，只有在webapp_b/jsp下面可以获得cookie，在webapp_b下面但是在jsp文件夹外的都不能获得cookie。

			5）设置cookie.setPath("/webapp_b");，是指在webapp_b下面才可以使用cookie，这样就不可以在产生cookie的应用webapp_a下面获取cookie了

			6）有多条cookie.setPath("XXX");语句的时候，起作用的以最后一条为准。 */
		
		
		Cookie cookie=new Cookie("JT_TICKET","");
		cookie.setPath("/");
		cookie.setMaxAge(0);//设置cookie的时间，保存年份
		response.addCookie(cookie);
		//重定向到系统首页
		return "redirect:/index.html";
	}
	
}
















