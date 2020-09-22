package designmodel.singleton.lazy;

import java.lang.reflect.Constructor;

/**
 * 懒汉式单例：
 * 优点：节省了内存，线程安全
 * 缺点：性能下降
 * @author Administrator
 *
 */
public class LazySimpleSingleton {
	private static volatile LazySimpleSingleton instance;
	//隐藏构造方法，将无参构造设置为私有，会出现被反射攻击的问题，因此重写私有无参构造
//	private LazySimpleSingleton() {}
	/**
	 * 该方法为单例构造实例方法，但是会出现线程问题--线程不安全
	 * 两个线程，都去调用getInstance()
	 * 当两个线程同时进入instance==null的判断，便会重复创建两次
	 * 加速，会造成性能下降
	 * @return
	 */
	public static synchronized LazySimpleSingleton getInstance() {
		if (instance == null) {
			instance = new LazySimpleSingleton();
		}
		return instance;
	}
	
	/**
	 * 同步代码块，同样无法解决性能问题
	 * @return
	 */
	public static synchronized LazySimpleSingleton getInstanceLockCode() {
		//只在这里加锁，只是将阻塞移到了这里，还是无法解决性能问题
		synchronized(LazySimpleSingleton.class) { 
			
			if (instance == null) {
				instance = new LazySimpleSingleton();
			}
		}
		return instance;
	}
	
	/**
	 * 同步代码块双重判断，能够有效解决线程安全的性能问题
	 * 
	 * 会产生指令重排序问题，需要在对象初始化加上volatile解决指令重排序问题
	 * 
	 * 缺点：代码繁杂
	 * @return
	 */
	public static synchronized LazySimpleSingleton getInstanceLockCodeDoubleCheck() {
		//检查是否要阻塞
		if (instance == null) {
			synchronized(LazySimpleSingleton.class) { 
				//检查是否要重新创建实例
				if (instance == null) {
					instance = new LazySimpleSingleton();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 代码优化:静态内部类方式优化
	 * 
	 * 优点：利用了java语法特点，性能高，避免了内存浪费
	 * 缺点：能够被反射破坏
	 * 
	 * 加载方式：1.加载classPath的class文件，
	 * LazySimpleSingleton.class : 最先加载此文件，当加载到此方法时，需要用到内部类，才会去加载内部类的class文件，所以可以完美实现懒汉式单例的线程安全性能问题
	 * LazySimpleSingleton$LazyHolder.class :
	 * @return
	 */
	public static synchronized LazySimpleSingleton getInstanceInnerClass() {
		return LazyHolder.INSTANCE;
	}
	/**
	 * 静态内部类
	 * @author Administrator
	 *
	 */
	private static class LazyHolder{
		private static final LazySimpleSingleton INSTANCE = new LazySimpleSingleton();
	}
	
	//以上的写法均能被反射破坏
	
	/**
	 * 反射攻击
	 */
	public void reflectAttack() {
		
		try {
			Class<?> clazz = LazySimpleSingleton.class;
			Constructor<?> c = clazz.getDeclaredConstructor(null);
			c.setAccessible(true);
			Object newInstance = c.newInstance(); //反射获取实例
			Object newInstance2 = c.newInstance(); //打破单例规则
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为解决反射破坏，在私有构造内进行单例判断即可
	 * 
	 * 缺点：在代码中抛出异常，代码需优化，可使用注册式单例
	 */
	private LazySimpleSingleton() {
		if (LazyHolder.INSTANCE != null) {
			throw new RuntimeException("不允许非法访问");
		}
	}
	
	
	
	
}
