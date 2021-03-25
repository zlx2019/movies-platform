package com.movies;

import com.movies.rpc.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 搜索服务启动类
 * @author lx Zhang.
 * @date 2021/3/25 3:47 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignInterceptor
@EnableFeignClients
public class MoviesSearchApplication {
    public static void main(String[] args) {
        new SpringApplication(MoviesSearchApplication.class).run(args);
    }
}
