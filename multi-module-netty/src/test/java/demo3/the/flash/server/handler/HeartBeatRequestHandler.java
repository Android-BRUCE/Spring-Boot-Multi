package demo3.the.flash.server.handler;

import demo3.the.flash.protocol.request.HeartBeatRequestPacket;
import demo3.the.flash.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: Spring-Boot-Multi
 * @description: 响应客户端的心跳检测
 * @author: Brucezheng
 * @create: 2018-10-09 14:03
 **/
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {



    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    /**
     * 只是简单地回复一个 HeartBeatResponsePacket 数据包。客户端在检测到假死连接之后，断开连接，然后可以有一定的策略去重连，重新登录等等
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
