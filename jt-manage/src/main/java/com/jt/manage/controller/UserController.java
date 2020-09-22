package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;


@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("queryAll")
	public String queryAll(Model model){//结果封装到model对象中，model本质上创建变量放入request中，转向jsp页面
		List<User> userList = userService.queryAll();
		model.addAttribute("userList",userList);
		
		return "userList";
	}
}
