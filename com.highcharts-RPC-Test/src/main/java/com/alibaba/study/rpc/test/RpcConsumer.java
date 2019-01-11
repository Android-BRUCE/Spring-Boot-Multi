package com.alibaba.study.rpc.test;
import com.alibaba.study.rpc.framework.RpcFramework;
/**
 * @program: Spring-Boot-Multi
 * @description: 引用服务
 * @author: Brucezheng
 * @create: 2018-06-19 15:50
 **/

/**
 * 这个简单的例子的实现思路是使用阻塞的socket IO流来进行server和client的通信，也就是rpc应用中服务提供方和服务消费方。
 * 并且是端对端的，用端口号来直接进行通信。方法的远程调用使用的是jdk的动态代理，参数的序列化也是使用的最简单的objectStream。
 *
 * 真实的rpc框架会对上面的实现方式进行替换，采用更快更稳定，更高可用易扩展，更适宜分布式场景的中间件，技术来替换。
 * 例如使用netty的nio特性达到非阻塞的通信，使用zookeeper统一管理服务注册与发现，解决了端对端不灵活的劣势。
 * 代理方式有cglib字节码技术。序列化方式有hession2，fastjson等等。不过梁飞大大的博客使用原生的jdk api就展现给各位读者一个生动形象的rpc demo，实在是强。
 * rpc框架解决的不仅仅是技术层面的实现，还考虑到了rpc调用中的诸多问题，重试机制，超时配置…这些就需要去了解成熟的rpc框架是如果考虑这些问题的了。
 *
 * 推荐一个轻量级的rpc框架：motan。weibo团队在github开源的一个rpc框架，有相应的文档，用起来感觉比dubbo要轻量级，易上手。
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            String hello = service.hello("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
