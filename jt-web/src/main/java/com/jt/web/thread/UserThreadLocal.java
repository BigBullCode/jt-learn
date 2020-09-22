package com.jt.web.thread;

import com.jt.web.pojo.User;
/**
 * 线程本地变量,用来实现本线程内的数据共享,需要手动回收
 * @author zhangdongdong
 */
public class UserThreadLocal {
	//如果存多个值,将User换成Map<k,v>即可
	private static ThreadLocal<User> userThread=new ThreadLocal<User>();
	
	public static void set(User user){
		userThread.set(user);
	}
	public static User get(){
		return userThread.get();
	}
	public static void remove(){
		userThread.remove();
	}
	
}
