package com.movies.actor.controller;

import com.movies.actor.service.IBannerService;
import com.movies.cache.lock.CacheDistributedLock;
import com.movies.common.dto.ExampleDto;
import com.movies.common.feign.SearchService;
import com.movies.common.model.base.K;
import com.movies.common.model.base.R;
import com.movies.common.so.LogIndexSo;
import com.movies.common.utils.SmallTool;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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
    @Autowired
    private TaskExecutor taskExecutor;

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

    /**
     * 数据校验demo
     * @Author lx Zhang.
     * @Date 2021/3/26 1:24 下午
     * @Param [dto]
     * @return com.movies.common.model.base.R
     **/
    @PostMapping("/check/data")
    public R checkData(@RequestBody @Validated ExampleDto dto){
        return R.Success(dto);
    }

    @GetMapping("/test/task")
    public R testTask(){
        //开启异步任务执行 CompletableFuture.supplyAsync
        CompletableFuture<String> task  = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            return "番茄炒蛋";
            //上一个任务结束后将结果传递给下一个异步任务
        },taskExecutor).thenApplyAsync(param-> {
            SmallTool.printTimeAndThread("服务员蒸饭");
            return param + "米饭";
        },taskExecutor);
        return R.Success(task.join());
    }

}
