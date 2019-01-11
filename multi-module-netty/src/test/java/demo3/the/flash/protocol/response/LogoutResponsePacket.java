package demo3.the.flash.protocol.response;

import lombok.Data;
import demo3.the.flash.protocol.Packet;

import static demo3.the.flash.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
