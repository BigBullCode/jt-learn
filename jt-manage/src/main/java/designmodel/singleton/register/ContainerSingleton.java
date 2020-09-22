package designmodel.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring 借鉴枚举单例的ioc单例模式   存在线程安全问题  枚举式单例在序列化时会被破坏
 * 
 * 线程安全问题的解决：参考spring单例模式线程安全方案，这里已AbstractBeanFactory的单例来分析
 * 1.AbstractBeanFactory内有个getBean()方法，内有调用doGetBean(name,null,null,false)方法
 * 2.在doGetBean方法内，调用了getObjectForBeanInstance()方法，内部调用了getObjectFromFactoryBean(方法和getCachedObjectForFactoryBean()方法
 * 3.getCachedObjectForFactoryBean方法中，就直接从容器factoryBeanObjectCache中取值
 * 4.getObjectFromFactoryBean方法中，先判断factory是否为单例，如果不是单例，就new单例
 * 5.如果factory是单例：同步方法体内取单例（线程安全操作）
 * @author Administrator
 *
 */
public class ContainerSingleton {

	
	private ContainerSingleton() {};
	
	private static Map<String, Object> ioc = new ConcurrentHashMap<>();
	
	public static Object getInstance(String className) {
		if (!ioc.containsKey(className)) {
			Object instance = null;
			try {
				instance = Class.forName(className).newInstance();
				ioc.put(className, instance);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			return instance;
		}
		return ioc.get(className);
	}
}
