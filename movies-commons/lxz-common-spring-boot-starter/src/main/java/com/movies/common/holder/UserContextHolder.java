package com.movies.common.holder;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 每个线程存储User信息的holder
 * @author lx Zhang.
 * @date 2021/3/2 3:01 下午
 */
public class UserContextHolder {
    /**
     * @TransmittableThreadLocal
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(String usrId) {
        CONTEXT.set(usrId);
    }

    public static String getUser() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
