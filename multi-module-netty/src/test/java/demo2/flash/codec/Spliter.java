package demo2.flash.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import demo2.flash.protocol.PacketCodeC;
/**
 * 关于 粘包和拆包 的解决
 * 问题：
 *  一种是正常的字符串输出。
 * 一种是多个字符串“粘”在了一起，我们定义这种 ByteBuf 为粘包。
 * 一种是一个字符串被“拆”开，形成一个破碎的包，我们定义这种 ByteBuf 为半包。
 * 解析：
 * 我们需要知道，尽管我们在应用层面使用了 Netty，但是对于操作系统来说，只认 TCP 协议，
 * 尽管我们的应用层是按照 ByteBuf 为 单位来发送数据，但是到了底层操作系统仍然是按照字节流发送数据，
 * 因此，数据到了服务端，也是按照字节流的方式读入，然后到了 Netty 应用层面，
 * 重新拼装成 ByteBuf，而这里的 ByteBuf 与客户端按顺序发送的 ByteBuf 可能是不对等的。
 * 因此，我们需要在客户端根据自定义协议来组装我们应用层的数据包，然后在服务端根据我们的应用层的协议来组装数据包，
 * 这个过程通常在服务端称为拆包，而在客户端称为粘包。
 *
 * 拆包和粘包是相对的，一端粘了包，另外一端就需要将粘过的包拆开，
 * 举个栗子，发送端将三个数据包粘成两个 TCP 数据包发送到接收端，
 * 接收端就需要根据应用协议将两个数据包重新组装成三个数据包。...
 *
 * 具体：https://juejin.im/book/5b4bc28bf265da0f60130116/section/5b4db07fe51d451917170338
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        /**
         * 第一个参数指的是数据包的最大长度，
         * 第二个参数指的是长度域的偏移量，
         * 第三个参数指的是长度域的长度，这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。
         */
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
