package com.movies.zookeeper.config;

import com.movies.common.properties.ZkProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Zookeeper配置
 * @author lx Zhang.
 * @date 2021/3/16 11:36 下午
 */
@Slf4j
@EnableConfigurationProperties(ZkProperties.class)
public class ZookeeperConfig {


    /**
     * 初始化CuratorFramework
     * @Author lx Zhang.
     * @Date 2021/3/16 11:43 下午
     * @Param [zkProperties]
     * @return org.apache.curator.framework.CuratorFramework
     **/
    @Bean
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework(ZkProperties zkProperties){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(zkProperties.getIp())
                .sessionTimeoutMs(zkProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zkProperties.getConnectionTimeoutMs())
                .retryPolicy(new ExponentialBackoffRetry(zkProperties.getReConnTimeMs(), zkProperties.getReConnCount()))
                .namespace(zkProperties.getNamespace()).build();
        curatorFramework.start();
        log.info("--------------------------------------Zookeeper CuratorFramework init success!");
        return curatorFramework;
    }
}
