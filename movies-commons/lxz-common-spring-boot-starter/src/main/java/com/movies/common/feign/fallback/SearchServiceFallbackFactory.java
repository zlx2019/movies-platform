package com.movies.common.feign.fallback;

import com.movies.common.feign.SearchService;
import com.movies.common.so.LogIndexSo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 搜索服务降级工厂
 * @author lx Zhang.
 * @date 2021/3/25 4:27 下午
 */
@Slf4j
@Component
public class SearchServiceFallbackFactory implements FallbackFactory<SearchService> {
    @Override
    public SearchService create(Throwable throwable) {
        return new SearchService() {
            @Override
            public void save(LogIndexSo logIndexSo) {
                log.error("log save error:{}",logIndexSo);
            }
        };
    }
}
