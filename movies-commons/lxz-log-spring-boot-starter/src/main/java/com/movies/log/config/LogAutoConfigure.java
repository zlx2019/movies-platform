package com.movies.log.config;

import com.movies.log.properties.TraceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 开启链路追踪
 * @author L
 * @date 2020/10/16
 */
@EnableConfigurationProperties(TraceProperties.class)
public class LogAutoConfigure {

}
