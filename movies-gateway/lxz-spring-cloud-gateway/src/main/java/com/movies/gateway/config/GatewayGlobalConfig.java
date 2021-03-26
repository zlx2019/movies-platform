package com.movies.gateway.config;

import com.movies.gateway.handler.DefaultSentinelBlockRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 全局配置类
 * @author lx Zhang.
 * @date 2021/3/26 3:06 下午
 */
@Configuration
@Slf4j
public class GatewayGlobalConfig {


    /**
     * 注入Sentinel统一异常处理类
     * @Author lx Zhang.
     * @Date 2021/3/26 3:07 下午
     * @return com.movies.gateway.handler.DefaultSentinelBlockRequestHandler
     **/
    @Bean
    @Order(1)
    public DefaultSentinelBlockRequestHandler defaultSentinelBlockRequestHandler(){
        return new DefaultSentinelBlockRequestHandler();
    }
}
