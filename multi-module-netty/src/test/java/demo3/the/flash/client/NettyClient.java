package demo3.the.flash.client;

import demo3.the.flash.client.handler.*;
import demo3.the.flash.server.handler.IMIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import demo3.the.flash.client.console.ConsoleCommandManager;
import demo3.the.flash.client.console.LoginConsoleCommand;
import demo3.the.flash.codec.PacketDecoder;
import demo3.the.flash.codec.PacketEncoder;
import demo3.the.flash.codec.Spliter;
import demo3.the.flash.util.SessionUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

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
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());

                        // 添加加群响应处理器
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
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
        final ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        final LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        final Scanner scanner = new Scanner(System.in);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (!Thread.interrupted()) {
                            if (!SessionUtil.hasLogin(channel)) {
                                loginConsoleCommand.exec(scanner, channel);
                            } else {
                                consoleCommandManager.exec(scanner, channel);
                            }
                        }
                    }
                }).start();
    }
}
