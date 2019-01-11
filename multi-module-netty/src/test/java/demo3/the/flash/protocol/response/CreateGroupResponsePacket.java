package demo3.the.flash.protocol.response;

import lombok.Data;
import demo3.the.flash.protocol.Packet;

import java.util.List;

import static demo3.the.flash.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
