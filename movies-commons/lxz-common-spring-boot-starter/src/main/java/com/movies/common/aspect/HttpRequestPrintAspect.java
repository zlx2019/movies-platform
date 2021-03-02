package com.movies.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * http请求切面
 * 拦截请求服务的http请求,将日志输出、持久化
 * @author lx Zhang.
 * @date 2021/3/2 5:17 下午
 */
@Aspect
@Slf4j
public class HttpRequestPrintAspect {

    DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*切入点*/
    @Pointcut("execution(* com.movies.*.controller..*(..))")
    public void commonController(){}

    /**
     * 前置增强
     * 日志格式 2021-03-02 18:02:45[SpringCloud]	0:0:0:0:0:0:0:1		GET		/test/log	params:{"names":["1231"],"jju":["odwa"]}
     * @Author lx Zhang.
     * @Date 2021/3/2 5:23 下午
     * @Param [point]
     **/
    @Before("commonController()")
    public void before(JoinPoint point){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LocalDateTime data = LocalDateTime.now();
        String remoteAddr = request.getRemoteAddr();                 //请求ip
        String method = request.getMethod();                         //请求类型
        String requestURI = request.getRequestURI();                 //请求路径
        String params = JSON.toJSONString(request.getParameterMap());//请求参数
        String log = StrUtil.format("{}[SpringCloud]\t{}\t\t{}\t\t{}\tparams:{}",
                dataFormat.format(data),
                remoteAddr,
                method,
                requestURI,
                params);
        System.out.println(log);
    }


}
