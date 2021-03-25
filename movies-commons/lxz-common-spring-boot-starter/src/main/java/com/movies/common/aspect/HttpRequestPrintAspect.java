package com.movies.common.aspect;

import com.alibaba.fastjson.JSON;
import com.movies.common.feign.SearchService;
import com.movies.common.so.LogIndexSo;
import com.movies.common.utils.IdGenerator;
import com.movies.common.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    /*@Before("commonController()")
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
*/

    /**
     * 环绕增强
     * @Author lx Zhang.
     * @Date 2021/3/25 4:42 下午
     * @Param [point]
     * @return java.lang.Object
     **/
    @Around("commonController()")
    public Object around(ProceedingJoinPoint point){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        //获取信息
        String url = request.getRequestURL().toString();//URL
        String path = request.getRequestURI();//请求path
        String ip = request.getRemoteAddr();//ip
        String methodType = request.getMethod();//请求类型
        String sourceClassPath = signature.getDeclaringTypeName(); //class
        String controllerHandler = signature.getName();//handler

        sourceClassPath = sourceClassPath + "." + controllerHandler;
        //获取参数
        Object param = getParam(method,point.getArgs());

        SearchService searchService = SpringUtil.getBean(SearchService.class);

        //执行原程序
        try {
            //计算花费时间
            Long start = System.currentTimeMillis();
            Object proceed = point.proceed();
            Long end = System.currentTimeMillis();
            Long costTime = end - start;
            LogIndexSo indexSo = new LogIndexSo(IdGenerator.getId(), ip, path, methodType, sourceClassPath, costTime, LocalDateTime.now(), JSON.toJSONString(param));
            //将日志持久化到es
            searchService.save(indexSo);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    /**
     * 解析参数
     * @Author lx Zhang.
     * @Date 2021/3/25 4:46 下午
     * @Param [method, args]
     * @return java.lang.Object
     **/
    private Object getParam(Method method, Object[] args) {
        List<Object> obj = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        List<Parameter> parameterList = Stream.of(parameters).collect(Collectors.toList());
        for (int i = 0;i < parameterList.size();i++) {
            if (i >= args.length){
                break;
            }
            Parameter parameter = parameterList.get(i);
            Object arg = args[i];
            Map<String,Object> map = new HashMap<>();
            map.put(parameter.getName(),arg);
            obj.add(map);
/*            if (parameter.isAnnotationPresent(RequestBody.class)){
                map.put(parameter.getName(),arg);
                obj.add(map);
            }
            if (parameter.isAnnotationPresent(RequestParam.class)){
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String key = parameter.getName();
                if (StringUtils.isNotBlank(requestParam.value())){
                    key = requestParam.value();
                }
                map.put(key,arg);
                obj.add(map);
            }
            if (parameter.isAnnotationPresent(PathVariable.class)){
                map.put(parameter.getName(),arg);
                obj.add(map);
            }*/
        }
        return obj;
    }

}
