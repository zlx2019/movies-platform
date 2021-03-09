package com.movies.actor.controller;

import com.movies.actor.service.IBannerService;
import com.movies.cache.lock.CacheDistributedLock;
import com.movies.common.model.base.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx Zhang.
 * @date 2021/3/2 5:32 下午
 */
@RestController
public class ActorController {
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private CacheDistributedLock cacheDistributedLock;

    @GetMapping("/test/log")
    public String testLog(){
        return "testlog";
    }

    @GetMapping("/lock")
    public R lock(){
        boolean lock = cacheDistributedLock.lock("name1");
        if (lock)
            System.out.println("加锁成功");
        boolean releaseLock = cacheDistributedLock.releaseLock("name1");
        if (releaseLock)
            System.out.println("释放锁成功");
        return R.Success(1);
    }

}
