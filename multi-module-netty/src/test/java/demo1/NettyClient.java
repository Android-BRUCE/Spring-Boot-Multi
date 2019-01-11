package demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: Spring-Boot-Multi
 * @description: 客户端与服务端双向通信
 * @author: Brucezheng
 * @create: 2018-09-27 14:37
 **/
public class NettyClient {
    private static int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                // 1.指定线程模型
                .group(group)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                     //   ch.pipeline().addLast(new one.unpack.FirstClientHandler());
                       // ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                })
                //ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        ;
// 4.建立连接  (1)
/*        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }*/

// 4.建立连接 (2)
//        ChannelFuture channelFuture = bootstrap.connect("juejin.im", 8000).addListener(new GenericFutureListener<Future<? super Void>>() {
//            @Override
//            public void operationComplete(Future<? super Void> future) throws Exception {
//                if (future.isSuccess()) {
//                    System.out.println("连接成功!");
//                } else {
//                    System.err.println("连接失败!");
//                }
//            }
//        });
//    }
// 4.建立连接 (3) 链接失败重试
        connect(bootstrap, "127.0.0.1", 8000, 5);
    }

    /**
     * 连接 抽取
     *
     * @param bootstrap
     * @param host
     * @param port
     */


    private static void connect(final Bootstrap bootstrap, final String host, final int port) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else {
                    System.err.println("连接失败，开始重连");
                    connect(bootstrap, host, port);
                }
            }
        });
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else if (retry == 0) {
                    System.err.println("重试次数已用完，放弃连接！");
                } else {
                    // 第几次重连
                    int order = (MAX_RETRY - retry) + 1;
                    // 本次重连的间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……,间隔："+delay);
                    bootstrap.config().group().schedule(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                connect(bootstrap, host, port, retry - 1);
                                                            }
                                                        }, delay, TimeUnit.SECONDS
                    );
                }
            }
        });
    }
}
