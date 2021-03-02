package com.movies.common.utils;


import com.movies.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author lvz
 * @date 2020/1/17
 */
public class PagingUtils {

    public static <E> List<E> getPaging(int curPage, int limit, List<E> messageList) {
        if (curPage -1 > messageList.size()/limit) {
            throw new BusinessException("请核对页数！");
        }
        int index = 0;
        List<E> pageMessage = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            index = (curPage - 1) * limit + i;
            if (index >= messageList.size()) {
                break;
            }
            pageMessage.add(messageList.get(index));
        }

        return pageMessage;
    }

}
