package com.movies.cache.lock;

import com.movies.common.lock.abstracts.AbstractDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现 (Redis)
 * @author lx Zhang.
 * @date 2021/3/9 10:57 下午
 */
@Slf4j
public class CacheDistributedLock extends AbstractDistributedLock {

    /** 用于存放钥匙 线程隔离,防止删除其他线程的锁*/
    private ThreadLocal<String> tags = new ThreadLocal<>();
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /** 使用Lua脚本释放锁*/
    private static final String UNLOCK_LAU;
    static {
        UNLOCK_LAU = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }


    /**
     * 循环加锁,直到重试次数用完 or 加锁成功
     * @Param [key, expire:有效时间, spinNum:失败后重试加锁次数, sleepMillis:每次重试间隔时间]
     * @return boolean 成功/失败
     **/
    @Override
    public boolean lock(String key, long expire, int spinNum, long sleepMillis) {
        boolean lockResult = upLock(key, expire);
        //加锁失败且有剩余次数 进行自旋
        while ((!lockResult) && spinNum-- > 0){
            try {
                log.debug("【{}】加锁失败,剩余重试次数:{}",key,spinNum);
                TimeUnit.MILLISECONDS.sleep(sleepMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lockResult = upLock(key,expire);
        }
        log.info("加锁完成，tag:{}",tags.get());
        return lockResult;
    }

    /**
     * 加锁
     * @Param [key, expire:占有时间]
     * @return boolean
     **/
    private boolean upLock(final String key,final long expire){
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection->{
                //UUID 作为释放锁的唯一标识
                String tag = UUID.randomUUID().toString();
                tags.set(tag);
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] keyBytes = serializer.serialize(key);
                byte[] tagBytes = serializer.serialize(tag);
                return connection.set(keyBytes,tagBytes, Expiration.from(expire, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.ifAbsent());
            });
        } catch (Exception e) {
            log.error("redis lock error:",e);
        }
        return false;
    }

    /**
     * 释放锁
     * @param key key值
     * @return
     */
    @Override
    public boolean releaseLock(String key) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection->{
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] luaBytes = redisTemplate.getStringSerializer().serialize(UNLOCK_LAU);
                // 使用lua脚本删除redis中匹配tag的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
                // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
                return connection.eval(luaBytes, ReturnType.BOOLEAN, 1, serializer.serialize(key), serializer.serialize(tags.get()));
            });
        } catch (Exception e) {
            log.error("key:{},释放锁异常:{}",key,e);
        } finally {
            tags.remove();//移除tag
        }
        return false;
    }
}
