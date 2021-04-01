package com.movies.common.config;

import com.movies.common.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 定时任务执行器配置
 * @author lx Zhang.
 * @date 2021/4/1 11:06 下午
 */
@EnableConfigurationProperties(XxlJobProperties.class)
@Slf4j
public class DefaultXJobConfig {

    /**
     * 注入执行器
     * @Author lx Zhang.
     * @Date 2021/4/1 10:25 下午
     * @Param []
     * @return com.xxl.job.core.executor.impl.XxlJobSpringExecutor
     **/
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties) {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(properties.getExecutorAppname());
        xxlJobSpringExecutor.setAddress(properties.getExecutorAddress());
        xxlJobSpringExecutor.setIp(properties.getExecutorIp());
        xxlJobSpringExecutor.setPort(properties.getExecutorPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(properties.getLogretentiondays());
        return xxlJobSpringExecutor;
    }
}
