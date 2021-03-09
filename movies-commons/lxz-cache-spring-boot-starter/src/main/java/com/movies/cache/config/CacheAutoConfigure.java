package com.movies.cache.config;

import com.movies.cache.lock.CacheDistributedLock;
import com.movies.cache.template.CacheTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存自动配置类
 * @author lx Zhang.
 * @date 2021/3/9 10:59 下午
 */
@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class CacheAutoConfigure {

    /**
     * 初始化RedisTemplate
     * @date 2020/10/12 9:38
     * @Param [factory] 基于Lettuce实现操作redis操作
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        RedisObjectSerializer valueSerializer = new RedisObjectSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        //初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 注入CacheTemplate工具类
     */
    @Bean
    @ConditionalOnMissingBean //ioc不存在该bean 则注入
    public CacheTemplate cacheTemplate(RedisTemplate<String, Object> redisTemplate){
        return new CacheTemplate(redisTemplate);
    }


    /**
     * 注入分布式锁(Redis)
     * @Author lx Zhang.
     * @Date 2021/3/9 11:01 下午
     * @return com.movies.cache.lock.CacheDistributedLock
     **/
    @Bean
    @ConditionalOnMissingBean
    public CacheDistributedLock cacheDistributedLock(){
        return new CacheDistributedLock();
    }
}
