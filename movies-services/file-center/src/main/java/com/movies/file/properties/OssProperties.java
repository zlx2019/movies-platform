package com.movies.file.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 阿里云Oss配置
 * @author lx Zhang.
 * @date 2021/4/2 6:06 下午
 */
@Setter
@Getter
public class OssProperties {
    /**
     * 密钥key
     */
    private String accessKey;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 端点
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 访问域名前缀
     */
    private String domain;
}
