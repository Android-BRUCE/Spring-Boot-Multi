package demo3.the.flash.serialize;

import demo3.the.flash.serialize.impl.JSONSerializer;

/**
 * 定义序列化接口
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法（获取具体的序列化算法标识）
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
