package com.movies.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常消息枚举
 * @author L
 * @date 2020/10/14
 */
@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    LOCK_TIME_OUT("加锁超时!"),
    LOCK_NULL("分布式锁为空!"),
    LOCK_KEY_NULL("锁Key为空,无法加锁!"),
    CONSTANT_ACTION("幂等性操作失败,已存在记录!"),


    OBJECT_NULL("对象为空!"),
    ;

    private String message;
}
