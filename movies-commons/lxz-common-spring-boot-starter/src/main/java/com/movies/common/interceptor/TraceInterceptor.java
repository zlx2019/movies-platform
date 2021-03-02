package com.movies.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.movies.common.constant.CommonConst;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志链路追踪拦截器
 *
 * @author mr.zhang
 * @date 2019/8/10
 */
public class TraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(CommonConst.TRACE_ID_HEADER);
        if (StrUtil.isNotEmpty(traceId)) {
            MDC.put(CommonConst.LOG_TRACE_ID, traceId);
        }
        return true;
    }
}
