package com.movies;

import com.movies.rpc.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 代码生成、定时任务执行器服务
 * @author lx Zhang.
 * @date 2021/3/31 10:01 下午
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableFeignInterceptor
@SpringBootApplication
public class CodeGenerateApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeGenerateApplication.class, args);
    }

}
