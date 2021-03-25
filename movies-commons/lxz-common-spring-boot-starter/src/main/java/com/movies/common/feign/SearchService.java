package com.movies.common.feign;

import com.movies.common.constant.ServiceConst;
import com.movies.common.feign.fallback.SearchServiceFallbackFactory;
import com.movies.common.so.LogIndexSo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志远程调用服务
 * @author lx Zhang.
 * @date 2021/3/25 4:23 下午
 */
@FeignClient(name = ServiceConst.SEARCH_SERVICE,fallbackFactory = SearchServiceFallbackFactory.class,decode404 = true)
public interface SearchService {

    /**
     * 日志记录远程调用API
     * @Author lx Zhang.
     * @Date 2021/3/25 4:22 下午
     * @Param [logIndexSo]
     **/
    @PostMapping("/log")
    void save(@RequestBody LogIndexSo logIndexSo);

}
