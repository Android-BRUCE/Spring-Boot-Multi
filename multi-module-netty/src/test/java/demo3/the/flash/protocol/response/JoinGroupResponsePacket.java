package demo3.the.flash.protocol.response;

import demo3.the.flash.protocol.Packet;
import lombok.Data;

import static demo3.the.flash.protocol.command.Command.JOIN_GROUP_RESPONSE;


@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}
