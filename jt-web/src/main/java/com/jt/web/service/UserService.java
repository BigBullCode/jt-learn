package com.jt.web.service;

import com.jt.web.pojo.User;

public interface UserService {

	void saveUser(User user);

//	String finUserByUP(User user);
	
	String findUserByUP(User user);

	String finUserByUP(User user);

}
