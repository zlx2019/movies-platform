package com.movies.rpc;

import com.movies.rpc.config.FeignCustomIsolationConfig;
import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

/**
 * Feign 自动配置类
 * @author lx Zhang.
 * @date 2021/3/15 10:35 下午
 */
public class FeignAutoConfigure {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 自定义feign隔离策略,SEMAPHORE 并非官方推荐策略,对网络资源消耗较大,自定义实现即可
     * @return
     */
    @Bean
    public FeignCustomIsolationConfig feignCustomIsolationConfig(){
        return new FeignCustomIsolationConfig();
    }

}
