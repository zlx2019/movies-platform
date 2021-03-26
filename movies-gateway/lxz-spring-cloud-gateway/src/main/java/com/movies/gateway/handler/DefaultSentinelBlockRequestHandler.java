package com.movies.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.fastjson.JSON;
import com.movies.common.enums.REnum;
import com.movies.common.model.base.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Sentinel 统一熔断限流响应
 * @author lx Zhang.
 * @date 2021/3/26 2:58 下午
 */
@Slf4j
public class DefaultSentinelBlockRequestHandler implements BlockRequestHandler {


    /**
     * 自定义响应
     * @Author lx Zhang.
     * @Date 2021/3/26 2:59 下午
     * @Param [serverWebExchange, throwable]
     * @return reactor.core.publisher.Mono<org.springframework.web.reactive.function.server.ServerResponse>
     **/
    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
        R<Object> result = R.Failed(REnum.GATEWAY_FLOW);
        log.error("【网关限流】访问过滤频繁,请稍后再试!");
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS) // 状态码
                .contentType(MediaType.APPLICATION_JSON) // 内容类型
                .bodyValue(result); // 错误提示
    }
}
