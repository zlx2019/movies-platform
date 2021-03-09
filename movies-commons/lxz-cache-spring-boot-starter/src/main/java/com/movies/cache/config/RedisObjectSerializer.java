package com.movies.cache.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 自定义序列化器,如果被序列化的数据为class,该class必须实现序列化接口
 * @author L
 * @date 2020/10/12
 */
public class RedisObjectSerializer implements RedisSerializer<Object> {
    //空数组
    private static final byte[] EMPTY_ARR = new byte[0];
    // 为了方便进行对象与字节数组的转换，所以应该首先准备出两个转换器
    private Converter<Object, byte[]> serializingConverter = new SerializingConverter();
    private Converter<byte[], Object> deSerializingConverter = new DeserializingConverter();

    /**
     * 序列化
     * @Param [o] 要序列化的数据
     * @return byte[] 序列化后的字节数组
     **/
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null){
            return EMPTY_ARR;
        }
        //将对象序列化为字节数组
        return this.serializingConverter.convert(o);
    }

    /**
     * 反序列化
     * @param bytes 要反序列化的字节数组
     * @return object 反序列化后的对象
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0){
            return null;
        }
        return this.deSerializingConverter.convert(bytes);
    }
}
