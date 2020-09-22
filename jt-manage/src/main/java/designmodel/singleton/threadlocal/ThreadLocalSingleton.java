package designmodel.singleton.threadlocal;

/**
 * 线程局部变量单例
 * @author Administrator
 *
 */
public class ThreadLocalSingleton {
	
	private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance = 
			new ThreadLocal<ThreadLocalSingleton>() {

				@Override
				protected ThreadLocalSingleton initialValue() {
					return new ThreadLocalSingleton();
				}
	};

	private ThreadLocalSingleton() {
	}
	
	public static ThreadLocalSingleton getInstance() {
		return threadLocalInstance.get();
	}
	
}

