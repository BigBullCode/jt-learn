package designmodel.singleton.hungry;

/**
 * 有点，执行效率高，性能高，没有任何的锁
 */
public class HungrySinleton {

    private static final HungrySinleton hungrySingleton = new HungrySinleton();

    private HungrySinleton(){};

    public static HungrySinleton getInstance(){
        return hungrySingleton;
    }
}
