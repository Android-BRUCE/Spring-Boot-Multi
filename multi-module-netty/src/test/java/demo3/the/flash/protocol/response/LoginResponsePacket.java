package demo3.the.flash.protocol.response;

import lombok.Data;
import demo3.the.flash.protocol.Packet;

import static demo3.the.flash.protocol.command.Command.LOGIN_RESPONSE;

/**
 * 登录响应数据包
 */
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGIN_RESPONSE;
    }
}
