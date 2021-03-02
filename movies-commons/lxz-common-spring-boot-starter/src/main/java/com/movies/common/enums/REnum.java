package com.movies.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author L
 * @date 2020/10/13
 */
@Getter
@AllArgsConstructor
public enum REnum {
    SUCCESS(0,"操作成功!"),
    ERROR(1,"操作异常!"),
    NOT_AUTHORITY(401,"无权限!"),
    TIMEOUT(504,"连接超时!"),



    GATEWAY_FLOW(429,"【网关限流】系统繁忙,请稍后再试!"),
    RATE_LIMITER(429,"【限流】系统繁忙,请稍后再试!"),
    FALLBACK(500,"【降级】系统异常,请联系管理员")
    ;

    private Integer code;
    private String msg;
}
