package demo3.the.flash.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @program: flash-netty
 * @description: 服务端空闲检测
 *  空闲检测指的是每隔一段时间，检测这段时间内是否有数据读写，
 *  简化一下，我们的服务端只需要检测一段时间内，是否收到过客户端发来的数据即可，
 *  Netty 自带的 IdleStateHandler 就可以实现这个功能。
 * @author: Brucezheng
 * @create: 2018-10-09 13:51
 **/
public class IMIdleStateHandler extends IdleStateHandler {
    /**
     * 首先，我们观察一下 IMIdleStateHandler 的构造函数，
     * 他调用父类 IdleStateHandler 的构造函数，有四个参数，
     * 其中第一个表示读空闲时间，指的是在这段时间内如果没有数据读到，就表示连接假死；
     * 第二个是写空闲时间，指的是 在这段时间如果没有写数据，就表示连接假死；
     * 第三个参数是读写空闲时间，表示在这段时间内如果没有产生数据读或者写，就表示连接假死。写空闲和读写空闲为0，表示我们不关心者两类条件；
     * 最后一个参数表示时间单位。在我们的例子中，表示的是：如果 15 秒内没有读到数据，就表示连接假死。
     * 连接假死之后会回调 channelIdle() 方法，我们这个方法里面打印消息，并手动关闭连接。...
     *
     *
     *
     * 接下来，我们把这个 handler 插入到服务端 pipeline 的最前面
     *
     *
     *
     * 服务端在一段时间内没有收到客户端的数据，这个现象产生的原因可以分为以下两种：
     *
     * 1.连接假死。
     * 2.非假死状态下确实没有发送数据。
     *
     * https://juejin.im
     * 掘金 — 一个帮助开发者成长的社区
     */
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
