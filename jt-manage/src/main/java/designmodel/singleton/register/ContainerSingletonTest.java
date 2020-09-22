package designmodel.singleton.register;

public class ContainerSingletonTest {

	
	public static void main(String[] args) {
		Object instance = ContainerSingleton.getInstance("designmodel.singleton.register.Pojo");
		Object instance1 = ContainerSingleton.getInstance("designmodel.singleton.register.Pojo");
		
		System.out.println(instance);
		System.out.println(instance1);
	}
}

class Pojo{
	
}
