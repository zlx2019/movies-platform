package com.movies.common.constant;

/**
 * 全局公共常量
 * @author lx Zhang.
 * @date 2021/3/2 2:57 下午
 */
public interface CommonConst {

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";

    /**
     * 统一响应消息、响应码
     */
    String SUCCESS = "操作成功!";
    Integer SUCCESS_CODE = 0;
    String FAILED = "操作失败!";
    Integer FAILED_CODE = 1;


    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SECOND_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";


    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";
    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";
    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userId-header";


}
