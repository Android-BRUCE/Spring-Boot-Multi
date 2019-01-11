package demo2.flash.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import demo2.flash.client.handler.LoginResponseHandler;
import demo2.flash.client.handler.MessageResponseHandler;
import demo2.flash.codec.PacketDecoder;
import demo2.flash.codec.PacketEncoder;
import demo2.flash.codec.Spliter;
import demo2.flash.protocol.request.LoginRequestPacket;
import demo2.flash.protocol.request.MessageRequestPacket;
import demo2.flash.util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author 闪电侠
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;


    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {
        bootstrap.connect(host, port).addListener(
                new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                            Channel channel = ((ChannelFuture) future).channel();
                            startConsoleThread(channel);
                        } else if (retry == 0) {
                            System.err.println("重试次数已用完，放弃连接！");
                        } else {
                            // 第几次重连
                            int order = (MAX_RETRY - retry) + 1;
                            // 本次重连的间隔
                            int delay = 1 << order;
                            System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                            bootstrap.config().group().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    connect(bootstrap, host, port, retry - 1);
                                }
                            }, delay, TimeUnit.SECONDS);
                        }
                    }
                }
        );
    }


    private static void startConsoleThread(final Channel channel) {
        final Scanner sc = new Scanner(System.in);
        final LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (!Thread.interrupted()) {
/*                            //后续所有的channelHandler都需要验证是否登录，比较麻烦。所以在登陆后的handler插入AuthHandler 使得后续的handler都不要进行验证了。
                           // if (LoginUtil.hasLogin(channel)) {
                                System.out.println("输入消息发送至服务端: ");
                                Scanner sc = new Scanner(System.in);
                                String line = sc.nextLine();
                                //channel.writeAndFlush(new MessageRequestPacket(line));
                           // }*/


                            if (!SessionUtil.hasLogin(channel)) {
                                System.out.print("输入用户名登录: ");
                                String username = sc.nextLine();
                                loginRequestPacket.setUserName(username);

                                // 密码使用默认的
                                loginRequestPacket.setPassword("pwd");

                                // 发送登录数据包
                                channel.writeAndFlush(loginRequestPacket);
                                waitForLoginResponse();
                            } else {
                                String toUserId = sc.next();
                                String message = sc.next();
                                channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                            }
                        }
                    }
                }).start();
    }


    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

}
