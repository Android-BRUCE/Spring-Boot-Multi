package demo2.flash.protocol.request;

import lombok.Data;
import demo2.flash.protocol.Packet;

import static demo2.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
