package designmodel.singleton.register;

import java.lang.reflect.Constructor;

public class EnumSingletonTest {

	
	public static void main(String[] args) {
		
		EnumSingleton instance = EnumSingleton.getInstance();
		try {
			instance.setData(new Object());
			Class clazz = EnumSingleton.class;
			Constructor c = clazz.getDeclaredConstructor(String.class, int.class);//由于Enum源码可知，Enum需有参构造
			c.setAccessible(true);
			Object o = c.newInstance();
			//这里会报错：无法通过反射创建枚举对象，因此，Enum从底层避免反射破坏单例
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
