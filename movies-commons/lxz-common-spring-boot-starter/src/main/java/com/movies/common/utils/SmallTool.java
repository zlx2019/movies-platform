package com.movies.common.utils;

import java.util.StringJoiner;

/**
 * @author lx Zhang.
 * @date 2021/3/26 10:20 下午
 */
public class SmallTool {
    public static void sleepMillis(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTimeAndThread(String tag){
        String result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(String.valueOf(Thread.currentThread().getName()))
                .add(tag).toString();
        System.out.println(result);
    }
}
