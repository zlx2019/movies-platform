package com.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关启动类
 * @author lx Zhang.
 * @date 2021/3/26 2:03 下午
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MoviesGatewayApplication {
    public static void main(String[] args) {
        System.setProperty("csp.sentinel.app.type","1");
        new SpringApplication(MoviesGatewayApplication.class).run(args);
    }
}
