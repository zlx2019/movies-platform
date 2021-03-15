package com.movies.actor.controller;

import com.movies.actor.service.IBannerService;
import com.movies.cache.lock.CacheDistributedLock;
import com.movies.common.model.base.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演员业务-控制层
 * @author lx Zhang.
 * @date 2021/3/2 5:32 下午
 */
@RestController
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private CacheDistributedLock cacheDistributedLock;

    @GetMapping("/page/{current}/{size}")
    public K findAllActor(@PathVariable Integer current, @PathVariable Integer size){
        return bannerService.listPage(current,size);
    }

}
