package com.movies.file.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 文件服务属性配置
 * @author lx Zhang.
 * @date 2021/4/2 6:18 下午
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "movies.file-conf")
public class FileServerProperties {
    /** 云存储类型*/
    private String type;
    /** 阿里云oss 属性对象*/
    OssProperties oss = new OssProperties();
}
