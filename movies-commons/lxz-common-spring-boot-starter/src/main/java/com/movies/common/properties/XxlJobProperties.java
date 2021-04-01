package com.movies.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * XXl配置
 * @author lx Zhang.
 * @date 2021/4/1 11:07 下午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "xxl.job")
@RefreshScope
public class XxlJobProperties {

    /** 访问令牌*/
    private String accessToken;

    /** 调度中心地址*/
    private String adminAddresses;

    /** 调度平台Name*/
    private String executorAppname;

    /** 执行器注册地址*/
    private String executorAddress;

    /** 执行器IP*/
    private String executorIp;

    /** 执行器端口*/
    private Integer executorPort;

    /** 日志路径*/
    private String logpath;

    /** 日志保留天数*/
    private Integer logretentiondays;
}