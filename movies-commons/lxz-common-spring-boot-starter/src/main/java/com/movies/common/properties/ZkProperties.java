package com.movies.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper配置属性类
 * @author lx Zhang.
 * @date 2021/3/16 11:26 下午
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "zk")
public class ZkProperties {

    /** zookeeper连接*/
    private String ip;
    /** 断开连接后重试次数*/
    private int reConnCount;
    /** 重试间隔时间*/
    private int reConnTimeMs;
    /** 会话超时时间*/
    private int sessionTimeoutMs;
    /** 连接超时时间*/
    private int connectionTimeoutMs;
    /** 命名空间*/
    private String namespace;
}
