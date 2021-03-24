package com.movies.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author lx Zhang.
 * @date 2021/3/24 11:02 下午
 */
@ConfigurationProperties(prefix = "movies.es")
@Getter
@Setter
@RefreshScope
public class ElasticSearchProperties {

    private String scheme = "http";
    /**
     * 集群节点
     */
    private String nodes;

    /**
     *
     */
    private Integer connectionRequestTimeout = 500;

    /**
     * 请求超时时间 60s
     */
    private Integer socketTimeout = 1000 * 60;

    /**
     * 连接超时时间 5s
     */
    private Integer connectTimeout = 1000 * 5;
}
