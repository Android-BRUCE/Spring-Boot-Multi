package demo3.the.flash.protocol.response;

import lombok.Data;
import demo3.the.flash.protocol.Packet;

import static demo3.the.flash.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * 服务端发送至客户端的消息对象定义
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
