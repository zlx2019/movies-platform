package com.movies.rpc;

import org.springframework.cloud.netflix.ribbon.DefaultPropertiesFactory;
import org.springframework.context.annotation.Bean;

/**
 * Ribbon扩展配置类
 * @author lx Zhang.
 * @date 2021/3/15 10:38 下午
 */
public class RibbonAutoConfigure {
    @Bean
    public DefaultPropertiesFactory defaultPropertiesFactory() {
        return new DefaultPropertiesFactory();
    }
}
