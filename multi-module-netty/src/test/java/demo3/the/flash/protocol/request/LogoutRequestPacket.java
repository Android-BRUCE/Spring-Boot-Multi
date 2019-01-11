package demo3.the.flash.protocol.request;

import lombok.Data;
import demo3.the.flash.protocol.Packet;

import static demo3.the.flash.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
