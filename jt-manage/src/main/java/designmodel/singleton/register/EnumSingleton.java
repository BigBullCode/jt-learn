package designmodel.singleton.register;


/**
 * 枚举式单例
 * 
 * 无法通过反射创建枚举对象，因此，Enum从底层避免反射破坏单例
 * @author Administrator
 *
 */
public enum EnumSingleton {

	INSTANCE;

	private Object data;


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 全局访问点
	 * @return
	 */
	public static EnumSingleton getInstance() {
		return INSTANCE;
	}

	/**
	 * 防止序列化破坏单例
	 * 1. 在通过序列化读取为对象时，用到了ObjectInputStream.readObject()方法
	 * 2.readObject()方法内部调用了一个readObject0方法
	 * 3.readObject0方法内有一个 checkResolve方法
	 * 4.checkResolve内用一个readOrdinaryObject方法，内有个判断方法为：hasReadResolveMethod()，用于判断单例对象内部有没有resolve方法
	 * 5.如果没有resolve方法，将会调用newInstance()方法，创建新的对象，破坏单例。
	 * 6.如果有resolve方法，首先会去调用invokeReadResolve(obj)方法
	 * 6.invokeReadResolve(obj)方法，通过反射机制，调用单例对象的resolve方法：readResolveMethod.invoke(obj,new Object[] null),得到对象并返回
	 * 7.hasReadResolveMethod()方法在得到invokeReadResolve(obj)方法返回值后，赋值给单例对象。
	 * 8.因此，如果在单例对象内有resolve()方法时，通过反序列化创建实例化对象时，始终是通过resolve方法获取的单例对象
	 * @return
	 */
	private Object readResovle() {return INSTANCE;}

}
