package com.movies.actor.controller;

import com.movies.actor.service.IBannerService;
import com.movies.cache.lock.CacheDistributedLock;
import com.movies.common.feign.SearchService;
import com.movies.common.model.base.K;
import com.movies.common.model.base.R;
import com.movies.common.so.LogIndexSo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 演员业务-控制层
 * @author lx Zhang.
 * @date 2021/3/2 5:32 下午
 */
@Slf4j
@RestController
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private CacheDistributedLock cacheDistributedLock;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private SearchService searchService;

    @GetMapping("/page/{current}/{size}")
    public K findAllActor(@PathVariable Integer current, @PathVariable Integer size){
        return bannerService.listPage(current,size);
    }


//    @GetMapping("/zk/lock")
//    public R lock(String key,String value){
//        boolean lock = zkDistributedLock.lock(key, 10);
//        log.info("加锁:{}",lock);
//
//        boolean releaseLock = zkDistributedLock.releaseLock();
//        log.info("解锁:{}",releaseLock);
//        return R.Success();
//    }

    @GetMapping("/test/book")
    public R testBook(){
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        try {
            SearchResponse response = restHighLevelClient.search(new SearchRequest("books"), RequestOptions.DEFAULT);
            return R.Success(response.getHits().getHits());
        } catch (IOException e) {
            e.printStackTrace();
            return R.Failed();
        }
    }

    @GetMapping("/test/log")
    public R testLog(){
        LogIndexSo so = new LogIndexSo();
        so.setId(1000001L);
        so.setIp("127.0.0.1");
        so.setMethod("GET");
        searchService.save(so);
       return R.Success();
    }

}
