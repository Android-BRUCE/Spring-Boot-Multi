package demo2.flash.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 定义通信过程中的 Java 对象
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

/**
 * 指令
 */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
