package com.movies.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.common.enums.REnum;
import com.movies.common.model.base.R;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sentinel自动配置类
 * @author lx Zhang.
 * @date 2021/3/9 11:07 下午
 */
@Slf4j
public class SentinelAutoConfigure {

    public class CustomLimiterBlockHandler implements BlockExceptionHandler{
        /**
         * Sentinel 熔断、限流、降级统一响应处理
         * @Author lx Zhang.
         * @Date 2021/3/9 11:10 下午
         * @Param [httpServletRequest, httpServletResponse, e]
         **/
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
            R r = null;
            log.warn("系统繁忙,限流 【Path:{}】",request.getRequestURI());

            //根据不同的异常类型,响应不同的消息
            if (e instanceof FlowException){
                //限流
                r = R.Failed(REnum.RATE_LIMITER);
            }else if (e instanceof DegradeException){
                //降级
                r = R.Failed(REnum.FALLBACK);
            }

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            //spring mvc自带的json操作工具，jackson
            new ObjectMapper().writeValue(response.getWriter(),r);
        }
    }
}
