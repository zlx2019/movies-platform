package com.movies.common.config;

import com.movies.common.interceptor.TenantInterceptor;
import com.movies.common.interceptor.TraceInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 默认SpringMVC拦截器
 * @author lx Zhang.
 * @date 2021/3/2 3:34 下午
 */
public class DefaultWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器
     * @Author lx Zhang.
     * @Date 2021/3/2 3:35 下午
     * @Param [registry]
     **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加日志链路追踪拦截器
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**");

        //添加用户拦截器
        registry.addInterceptor(new TenantInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
