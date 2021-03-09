package com.movies.cache.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 工具类 封装了Redis 基础API 可自己扩展
 * @author L
 * @date 2020/10/12
 */
@Slf4j
public class CacheTemplate {
    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * key序列化
     */
    private static final StringRedisSerializer KEY_SERIALIZER = new StringRedisSerializer();

    /**
     * value 序列化
     */
    private static final JdkSerializationRedisSerializer VALUE_SERIALIZER = new JdkSerializationRedisSerializer();

    /**
     * Spring Redis Template
     */
    private RedisTemplate<String, Object> redisTemplate;

    //构造初始化
    public CacheTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(KEY_SERIALIZER);
        this.redisTemplate.setValueSerializer(VALUE_SERIALIZER);
    }

    /**
     * 获取 RedisSerializer
     */
    protected RedisSerializer<String> getKeySerializer() {
        return redisTemplate.getStringSerializer();
    }

    /*-----------------------------------------------------API-------------------------------------------------------*/

    /**
     * 添加缓存
     */
    public Boolean set(final String key,final Object value){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->{
            byte[] keyBytes = getKeySerializer().serialize(key);
            byte[] valueBytes = VALUE_SERIALIZER.serialize(value);
            Boolean result = conn.set(keyBytes, valueBytes);
            return result;
        });
    }

    /**
     * 添加缓存
     */
    public Boolean set(final byte[] key,final byte[] value){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->conn.set(key, value));
    }


    /**
     * 添加缓存 设置有效时间(<byte[],byte[]>)
     *
     * @param key   键
     * @param value 值
     * @param time 有效时间
     * @return
     */
    public Boolean setExpire(final byte[] key,final byte[] value,final long time){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->conn.setEx(key,time,value));
    }

    /**
     * 添加缓存 设置有效时间(<String,Object>)
     * @param key   键
     * @param value 值
     * @param time  有效时间
     * @return
     */
    public Boolean setExpire(final String key,final Object value,final long time){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->{
            byte[] keyBytes = getKeySerializer().serialize(key);
            byte[] valueBytes = VALUE_SERIALIZER.serialize(value);
            return conn.setEx(keyBytes,time,valueBytes);
        });
    }

    /**
     * 批量添加缓存
     * @param keys   键列表
     * @param values 值列表
     * @return
     */
    public Boolean setExpireList(final List<String> keys, final List<Object> values){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->{
            RedisSerializer<String> keySerializer = getKeySerializer();
            List<Boolean> resultList = new ArrayList<>();
            for (int i = 0;i < keys.size();i++){
                byte[] keyBytes = keySerializer.serialize(keys.get(i));
                byte[] valueBytes = VALUE_SERIALIZER.serialize(values.get(i));
                resultList.add(conn.set(keyBytes,valueBytes));
            }
            return resultList.stream().allMatch(Boolean::booleanValue);
        });
    }

    /**
     * 批量添加缓存
     * @param keys   键列表
     * @param values 值列表
     * @param time   有效时间
     * @return
     */
    public Boolean setExpireList(final List<String> keys, final List<Object> values, final long time){
        return redisTemplate.execute((RedisCallback<Boolean>) conn->{
            RedisSerializer<String> keySerializer = getKeySerializer();
            List<Boolean> resultList = new ArrayList<>();
            for (int i = 0;i < keys.size();i++){
                byte[] keyBytes = keySerializer.serialize(keys.get(i));
                byte[] valueBytes = VALUE_SERIALIZER.serialize(values.get(i));
                resultList.add(conn.setEx(keyBytes,time,valueBytes));
            }
            return resultList.stream().allMatch(Boolean::booleanValue);
        });
    }

    /**
     * 根据key,获取剩余的有效时间
     * @param key 键
     * @param unit 返回时间单位
     **/
    public Long getExpire(final String key,TimeUnit unit){
        return redisTemplate.execute((RedisCallback<Long>)connection ->{
            RedisSerializer<String> serializer = getKeySerializer();
            return connection.ttl(serializer.serialize(key),unit);
        });
    }

    /**
     * 查询在以keyPatten的所有  key
     * @param keyPatten the key patten
     * @return the set
     */
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> redisTemplate.keys(keyPatten + "*"));
    }


    /**
     * 根据key获取对象
     *
     * @param keyPatten the key patten
     * @return the keys values
     */
    public Map<String, Object> getKeysValues(final String keyPatten) {
        log.debug("[redisTemplate redis]  getValues()  patten={} ", keyPatten);
        return redisTemplate.execute((RedisCallback<Map<String, Object>>) connection -> {
            RedisSerializer<String> serializer = getKeySerializer();
            Map<String, Object> maps = new HashMap<>(16);
            Set<String> keys = redisTemplate.keys(keyPatten + "*");
            if (CollectionUtils.isNotEmpty(keys)) {
                for (String key : keys) {
                    byte[] bKeys = serializer.serialize(key);
                    byte[] bValues = connection.get(bKeys);
                    Object value = VALUE_SERIALIZER.deserialize(bValues);
                    maps.put(key, value);
                }
            }
            return maps;
        });
    }

    /**
     * 根据Key 获取 value
     * @param key
     * @return
     */
    public Object get(final String key){
        return redisTemplate.execute((RedisCallback<Object>) connection->{
            byte[] value = connection.get(getKeySerializer().serialize(key));
            return VALUE_SERIALIZER.deserialize(value);
        });
    }

    /**
     * 根据key 获取 value
     * @param key
     * @return
     */
    public Object get(final byte[] key){
        return redisTemplate.execute((RedisCallback<byte[]>) conn->conn.get(key));
    }


    /**
     * Ops for hash hash operations.
     *
     * @return the hash operations
     */
    public HashOperations<String, String, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对HashMap操作
     *
     * @param key       the key
     * @param hashKey   the hash key
     * @param hashValue the hash value
     */
    public void putHashValue(String key, String hashKey, Object hashValue) {
        log.debug("[redisTemplate redis]  putHashValue()  key={},hashKey={},hashValue={} ", key, hashKey, hashValue);
        opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 获取单个field对应的值
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the hash values
     */
    public Object getHashValues(String key, String hashKey) {
        log.debug("[redisTemplate redis]  getHashValues()  key={},hashKey={}", key, hashKey);
        return opsForHash().get(key, hashKey);
    }

    /**
     * 根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    public void delHashValues(String key, Object... hashKeys) {
        log.debug("[redisTemplate redis]  delHashValues()  key={}", key);
        opsForHash().delete(key, hashKeys);
    }

    /**
     * key只匹配map
     *
     * @param key the key
     * @return the hash value
     */
    public Map<String, Object> getHashValue(String key) {
        log.debug("[redisTemplate redis]  getHashValue()  key={}", key);
        return opsForHash().entries(key);
    }

    /**
     * 批量添加
     *
     * @param key the key
     * @param map the map
     */
    public void putHashValues(String key, Map<String, Object> map) {
        opsForHash().putAll(key, map);
    }

    /**
     * 集合数量
     *
     * @return the long
     */
    public long dbSize() {
        return redisTemplate.execute(RedisServerCommands::dbSize);
    }

    /**
     * 清空redis存储的数据
     *
     * @return the string
     */
    public String flushDB() {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key.getBytes(DEFAULT_CHARSET)));
    }


    /**
     * 删除key
     *
     * @param keys the keys
     * @return the long
     */
    public long del(final String... keys) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            long result = 0;
            for (String key : keys) {
                result = connection.del(key.getBytes(DEFAULT_CHARSET));
            }
            return result;
        });
    }

    /**
     * 对某个主键对应的值加一,value值必须是全数字的字符串
     *
     * @param key the key
     * @return the long
     */
    public long incr(final String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> redisSerializer = getKeySerializer();
            return connection.incr(redisSerializer.serialize(key));
        });
    }

    /**
     * redis List 引擎
     *
     * @return the list operations
     */
    public ListOperations<String, Object> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long leftPush(String key, Object value) {
        return opsForList().leftPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     *
     * @param key the key
     * @return the string
     */
    public Object leftPop(String key) {
        return opsForList().leftPop(key);
    }

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long in(String key, Object value) {
        return opsForList().rightPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     *
     * @param key the key
     * @return the string
     */
    public Object rightPop(String key) {
        return opsForList().rightPop(key);
    }


    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    public Long length(String key) {
        return opsForList().size(key);
    }


    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    public void remove(String key, long i, Object value) {
        opsForList().remove(key, i, value);
    }

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void set(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public List<Object> getList(String key, int start, int end) {
        return opsForList().range(key, start, end);
    }

    /**
     * redis List数据结构 : 批量存储
     *
     * @param key  the key
     * @param list the list
     * @return the long
     */
    public Long leftPushAll(String key, List<String> list) {
        return opsForList().leftPushAll(key, list);
    }

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void insert(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }


    /**
     * 清空DB
     * @param node redis 节点
     */
    public void flushDB(RedisClusterNode node) {
        this.redisTemplate.opsForCluster().flushDb(node);
    }

}
