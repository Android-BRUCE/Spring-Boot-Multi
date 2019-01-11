/**
 * @program: Spring-Boot-Multi
 * @description: 暴露服务
 * @author: Brucezheng
 * @create: 2018-06-19 15:48
 **/
import com.alibaba.study.rpc.framework.RpcFramework;
import com.alibaba.study.rpc.test.HelloService;
import com.alibaba.study.rpc.test.HelloServiceImpl;

public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
