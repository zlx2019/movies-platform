package com.movies.common.annotations;

import com.movies.common.aspect.HttpRequestPrintAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启服务的请求日志
 * @author lx Zhang.
 * @date 2021/3/2 5:29 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpRequestPrintAspect.class)
public @interface EnableRequestLog {
}
