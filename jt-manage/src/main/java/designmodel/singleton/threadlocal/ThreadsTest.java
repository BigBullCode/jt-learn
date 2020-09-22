package designmodel.singleton.threadlocal;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * 多线程测试线程局部变量单例
 * @author Administrator
 *
 */
public class ThreadsTest {
	
	
	public static void main(String[] args) {
		
		System.out.println(ThreadLocalSingleton.getInstance());
		
		new Thread() {
		    public void run() {
		        System.out.println("匿名内部类创建线程并启动");
		    }

		}.start();
		
		new Thread() {
			public void run() {
				
			}
		}.start();
	}
}
