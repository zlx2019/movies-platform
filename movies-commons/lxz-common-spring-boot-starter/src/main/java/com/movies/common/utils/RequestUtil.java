package com.movies.common.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * @author lx Zhang.
 * @date 2019/8/24
 */
public class RequestUtil {
    private RequestUtil() {
        throw new IllegalStateException("Utility class");
    }


    private final static String UNKNOWN_STR = "unknown";


    /**
    *  获取IP地址
    * @Param [request]
    * @author lx Zhang
    * @date 2020/9/5 10:25 AM
    * @return java.lang.String  
    * @throws  
    */
    public static  String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (isEmptyIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (isEmptyIP(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (isEmptyIP(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (isEmptyIP(ip)) {
                        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                        if (isEmptyIP(ip)) {
                            ip = request.getRemoteAddr();
                        }
                    }
                }
            }
        }
        return ip;
    }

    private static boolean isEmptyIP(String ip) {
        if (StrUtil.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }
}
