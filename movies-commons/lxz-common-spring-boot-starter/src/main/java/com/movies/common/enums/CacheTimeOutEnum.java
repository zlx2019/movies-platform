package com.movies.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存过期时间枚举
 * @author L
 * @date 2020/7/10
 */
@AllArgsConstructor
@Getter
public enum CacheTimeOutEnum {
    TOPIC_TIME_OUT("题目已做标识","七天", 60 * 60 * 24 * 7L,"秒"),
    PROVE_ANALYSIS_TIME_OUT("证明题步骤已做标识","七天",60 * 60 * 24 * 7L,"秒"),
    TOPIC_ANALYSIS_TIME_OUT("题目解析步骤已做标识","七天",60 * 60 * 24 * 7L,"秒"),
    ORDER_TIME_OUT("订单有效时间","30分钟",60 * 30L,"秒"),
    USER_LOGIN_FAILED_TIME_OUT("登陆失败次数有效时间","30分钟",30L,"秒"),
    ;
    private String msg;
    private String description;
    private Long time;
    private String unit;

}
