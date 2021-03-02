package com.movies.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 项目自主配置属性类
 * @author lx Zhang.
 * @date 2021/3/2 4:29 下午
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "mi")
@RefreshScope  //开启实时更新,结合配置中心
public class MoviesProperties {
    //TODO 后续添加
}
