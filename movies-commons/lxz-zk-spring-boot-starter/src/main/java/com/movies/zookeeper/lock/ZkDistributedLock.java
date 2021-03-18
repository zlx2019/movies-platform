package com.movies.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现(Zookeeper)
 * @author lx Zhang.
 * @date 2021/3/17 12:07 上午
 */
@Slf4j
public class ZkDistributedLock {
    public ZkDistributedLock(CuratorFramework framework){
        this.curatorFramework = framework;
    }
    //锁目录
    private final static String LOCK_ROOT = "/locks/";
    private CuratorFramework curatorFramework;
    //锁
    ThreadLocal<InterProcessLock> local = new ThreadLocal<>();
    /**
     * 加锁
     * @Author lx Zhang.
     * @Date 2021/3/17 12:20 上午
     * @Param [key, timeout]
     * @return boolean
     **/
    public boolean lock(String key, long timeout) {
        //获取锁对象
        InterProcessLock lock = new InterProcessMutex(curatorFramework, LOCK_ROOT + key);
        try {
            //进行加锁
            boolean acquire = lock.acquire(timeout, TimeUnit.SECONDS);
            local.set(lock);
            return acquire;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     * @Author lx Zhang.
     * @Date 2021/3/17 12:29 上午
     * @Param [key]
     * @return boolean
     **/
    public boolean releaseLock(){
        try {
            local.get().release();
            local.remove();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
