package com.movies;

import com.movies.file.properties.FileServerProperties;
import com.movies.rpc.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 文件服务启动类
 * @author lx Zhang.
 * @date 2021/4/2 5:48 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignInterceptor
@EnableFeignClients
@EnableConfigurationProperties(FileServerProperties.class)
public class MoviesFileApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MoviesFileApplication.class, args);
    }
}
