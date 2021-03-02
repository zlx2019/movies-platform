package com.movies.common.exception;

/**
 * 自定义分布式锁异常
 * @author lx Zhang.
 * @date 2021/3/2 3:30 下午
 */
public class LockException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public LockException(String message) {
        super(message);
    }
}
