package com.movies.rpc.interceptor;

import com.movies.common.constant.CommonConst;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign全局拦截器,服务调用时,向下游传递数据(token、userInfo等)
 * @author lx Zhang.
 * @date 2021/3/15 10:21 下午
 */
public class GlobalFeignInterceptor {

    /**
     * 注入全局拦截器 feign.RequestInterceptor
     * @Author lx Zhang.
     * @Date 2021/3/15 10:33 下午
     **/
    @Bean
    public RequestInterceptor requestInterceptor(){
        return nextRequest -> {
            //获取Request,默认是无法获取,因为hystrix是默认是线程隔离,所以需要重写隔离策略后再传递
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            //传递token
            String token = request.getHeader(CommonConst.TOKEN_HEADER);
            if (StringUtils.isNotBlank(token)){
                nextRequest.header(CommonConst.TOKEN_HEADER,token);
            }
            //TODO 传递其他...
        };
    }
}
