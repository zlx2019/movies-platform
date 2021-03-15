package com.movies.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RestTemplate 配置属性类
 * @author L
 * @date 2020/10/15
 */
@ConfigurationProperties(prefix = "movies.rest-template")
@Getter
@Setter
public class RestTemplateProperties {
    /**
     * 最大链接数
     */
    private int maxTotal = 200;
    /**
     * 同路由最大并发数
     */
    private int maxPerRoute = 50;
    /**
     * 读取超时时间 ms
     */
    private int readTimeout = 35000;
    /**
     * 链接超时时间 ms
     */
    private int connectTimeout = 10000;
}