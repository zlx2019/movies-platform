package com.movies;

import com.movies.common.annotations.EnableRequestLog;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 演员模块启动类
 * @author lx Zhang.
 * @date 2021/3/1 9:42 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRequestLog //开启http请求日志
public class MoviesActorApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MoviesActorApplication.class)
                .run(args);
    }
}
