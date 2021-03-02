package com.movies.common.exception;

/**
 * 自定义幂等性异常
 * @author lx Zhang.
 * @date 2021/3/2 3:29 下午
 */
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public IdempotencyException(String message) {
        super(message);
    }
}
