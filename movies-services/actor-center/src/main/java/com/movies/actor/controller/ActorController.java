package com.movies.actor.controller;

import com.movies.actor.service.IBannerService;
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

    @GetMapping("/test/log")
    public String testLog(){
        return "testlog";
    }

}
