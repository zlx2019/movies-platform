package com.movies.zookeeper;

import com.movies.zookeeper.config.ZookeeperConfig;
import com.movies.zookeeper.lock.ZkDistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author lx Zhang.
 * @date 2021/3/16 11:48 下午
 */
@Import(ZookeeperConfig.class)
public class ZkAutoConfigure {

    /**
     * 注入分布式锁
     * @Author lx Zhang.
     * @Date 2021/3/17 12:37 上午
     * @Param [curatorFramework]
     * @return com.movies.zookeeper.lock.ZkDistributedLock
     **/
    @Bean
    @ConditionalOnMissingBean
    public ZkDistributedLock zkDistributedLock(CuratorFramework curatorFramework){
        return new ZkDistributedLock(curatorFramework);
    }
}
