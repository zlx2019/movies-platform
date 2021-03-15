package com.movies.rpc.annotation;

import com.movies.rpc.interceptor.GlobalFeignInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在服务主启动类中启用该注解,启用Feign拦截器,服务调用时传递token
 * @author lx Zhang.
 * @date 2021/3/15 10:19 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(GlobalFeignInterceptor.class)
public @interface EnableFeignInterceptor {
}
