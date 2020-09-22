package designmodel.singleton.threadlocal;


/**
 *线程局部变量单例单线程测试
 * @author Administrator
 *
 */
public class Test {
	
	public static void main(String[] args) {
		System.out.println(ThreadLocalSingleton.getInstance());
		System.out.println(ThreadLocalSingleton.getInstance());
		System.out.println(ThreadLocalSingleton.getInstance());
		System.out.println(ThreadLocalSingleton.getInstance());
	}
}
