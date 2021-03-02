package com.movies.common.exception;

import com.movies.common.enums.REnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 * @author lx Zhang.
 * @date 2021/3/2 3:06 下午
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = -4502144501893084609L;

    /*响应枚举类*/
    private REnum exceptionCodeEnum;


}
