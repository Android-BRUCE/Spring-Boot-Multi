package demo3.the.flash.protocol.request;

import demo3.the.flash.protocol.Packet;
import lombok.Data;

import static demo3.the.flash.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-10-09 08:38
 **/
@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
