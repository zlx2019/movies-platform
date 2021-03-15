package com.movies;

import com.movies.common.annotations.EnableRequestLog;
import com.movies.rpc.annotation.EnableFeignInterceptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 演员模块启动类
 * @author lx Zhang.
 * @date 2021/3/1 9:42 下午
 *  annotation info
 *  @EnableRequestLog: 开启日志输出
 *  @EnableFeignInterceptor: 开启feign拦截器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRequestLog //开启http请求日志
@EnableFeignInterceptor
@EnableFeignClients
public class MoviesActorApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MoviesActorApplication.class)
                .run(args);
    }
}
