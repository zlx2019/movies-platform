package com.movies.common.so;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志映射So
 * @author lx Zhang.
 * @date 2021/3/25 4:18 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogIndexSo implements Serializable {
    /** ID，与_id保持一致*/
    private Long id;

    /** 访问ip*/
    private String ip;

    /** 请求path*/
    private String path;

    /** http method*/
    private String method;

    /** 执行资源全路径*/
    private String sourceClassPath;

    /** 执行时间*/
    private Long costTimes;

    /** 访问时间*/
    private LocalDateTime accessTime;

    /** 请求参数*/
    private String params;
}
