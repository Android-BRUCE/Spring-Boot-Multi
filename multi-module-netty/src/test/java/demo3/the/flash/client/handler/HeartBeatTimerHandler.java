package demo3.the.flash.client.handler;

import demo3.the.flash.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @program: flash-netty
 * @description: 客户端发送心跳包
 * @author: Brucezheng
 * @create: 2018-10-09 13:55
 **/
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;

    /**
     * ctx.executor() 返回的是当前的 channel 绑定的 NIO 线程，
     * 不理解没关系，只要记住就行，然后，NIO 线程有一个方法，scheduleAtFixedRate()，
     * 类似 jdk 的定时任务机制，可以每隔一段时间执行一个任务，而我们这边是实现了每隔 5 秒，
     * 向服务端发送一个心跳数据包，这个时间段通常要比服务端的空闲检测时间的一半要短一些，
     * 我们这里直接定义为空闲检测时间的三分之一，主要是为了排除公网偶发的秒级抖动。
     *
     * 实际在生产环境中，我们的发送心跳间隔时间和空闲检测时间可以略长一些，可以设置为几分钟级别，具体应用可以具体对待，没有强制的规定。...
     *
     * https://juejin.im
     * 掘金 — 一个帮助开发者成长的社区
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        ctx.writeAndFlush(new HeartBeatRequestPacket());
                    }
                },HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }
}
