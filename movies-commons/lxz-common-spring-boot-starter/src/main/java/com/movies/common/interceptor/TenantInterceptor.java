package com.movies.common.interceptor;

import com.movies.common.constant.CommonConst;
import com.movies.common.holder.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户拦截器
 * @author mr.zhang
 * @date 2019/8/5
 */
public class TenantInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //用户ID
        String userId = request.getHeader(CommonConst.USER_ID_HEADER);
        if (StringUtils.isNotBlank(userId)){
            //将用户ID,注入到全局上下文中
            UserContextHolder.setUser(userId);
        }
        return true;
    }

    /**
     * 释放上下文资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}
