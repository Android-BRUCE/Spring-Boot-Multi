package demo2.flash.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import demo2.flash.protocol.Packet;

import static demo2.flash.protocol.command.Command.MESSAGE_REQUEST;

/**
 * 客户端发送至服务端的消息对象
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
