package demo2.flash.protocol;

import io.netty.buffer.ByteBuf;
import demo2.flash.protocol.request.LoginRequestPacket;
import demo2.flash.protocol.request.MessageRequestPacket;
import demo2.flash.protocol.response.LoginResponsePacket;
import demo2.flash.protocol.response.MessageResponsePacket;
import demo2.flash.serialize.Serializer;
import demo2.flash.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static demo2.flash.protocol.command.Command.LOGIN_REQUEST;
import static demo2.flash.protocol.command.Command.LOGIN_RESPONSE;
import static demo2.flash.protocol.command.Command.MESSAGE_REQUEST;
import static demo2.flash.protocol.command.Command.MESSAGE_RESPONSE;

public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
   // private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        //获取处理json的解析器
        Serializer serializer = new JSONSerializer();
        //向map中存入key为json解析器，值为json解析器对象
        //此处应该还能存入其他的类型的解析器，方便后续取出
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 拿到该数据包的 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        //获取处理该数据的序列化器
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            //从二进制 返回java对象
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
