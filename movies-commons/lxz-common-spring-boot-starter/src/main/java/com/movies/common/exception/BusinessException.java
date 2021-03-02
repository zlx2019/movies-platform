package com.movies.common.exception;

/**
 * 自定义业务异常
 * @author lx Zhang.
 * @date 2020/10/10
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 6537761403724692400L;

    public BusinessException(String message) {
        super(message);
    }
}
