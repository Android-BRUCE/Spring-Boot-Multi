package demo2.flash.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import demo2.flash.codec.PacketDecoder;
import demo2.flash.codec.PacketEncoder;
import demo2.flash.codec.Spliter;
import demo2.flash.server.handler.AuthHandler;
import demo2.flash.server.handler.LoginRequestHandler;
import demo2.flash.server.handler.MessageRequestHandler;

import java.util.Date;

/**
 * 测试AuthHandler：
 * 1.未登录情况：客户端连接上,服务端显示handlerAdded()-channelRegistered-channelActive;客户端发送消息后 显示：channelRead-channelReadComplete-channelInactive-channelUnregistered-handlerRemoved
 * 2.登录：连接上后：handlerAdded()-channelRegistered-channelActive-channelReadComplete; 发送消息后：channelRead-handlerRemoved...
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());

                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });


        bind(serverBootstrap, PORT);
    }
//    future -> {
//        if (future.isSuccess()) {
//            System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
//        } else {
//            System.err.println("端口[" + port + "]绑定失败!");
//        }
//    });
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                }
            }
        });

    }
}
