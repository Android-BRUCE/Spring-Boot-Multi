import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: Spring-Boot-Multi
 * @description: https://blog.csdn.net/dan_xp/article/details/1820852
 * jdk 代理设计模式
 * @author: Brucezheng
 * @create: 2018-06-20 15:11
 **/
public class ProxySellFisher implements InvocationHandler {
    private SellFisher sell;

    public ProxySellFisher(SellFisher sell) {
        this.sell = sell;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("the fish price higher");
        return (Integer) method.invoke(sell, args) + 10;
    }
   /* private SellFisher sell;
    public ProxySellFisher(SellFisher sell) {
        this.sell = sell;
    }
    public int sellFish() {
        System.out.println("the fish price higher");
        return sell.sellFish()+10;
    }*/


}

class ClientTest {
    public static void main(String args[]) {
        SellFisher s = new ConcreteSellFisher();
        InvocationHandler p = new ProxySellFisher(s);
        Object obj = Proxy.newProxyInstance(s.getClass().getClassLoader(), s.getClass().getInterfaces(), p);
        int a = ((SellFisher) obj).sellFish();
        System.out.println(a);
    }
}