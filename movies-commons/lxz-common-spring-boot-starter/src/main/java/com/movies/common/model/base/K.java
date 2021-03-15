package com.movies.common.model.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.movies.common.enums.REnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一分页对象
 * @author lx Zhang.
 * @date 2021/3/15 11:41 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class K<T> {

    /** 响应体列表*/
    private List<T> dataList;
    /** 响应消息*/
    private String message;
    /** 响应码  0:正常  1:异常*/
    private Integer code;
    /** 数据总量*/
    private long total;
    /** 页容量*/
    private long limit;
    /** 总页数*/
    private long pages;
    /** 当前页*/
    private long curPage;
    /** 当前页数据数量*/
    private int curPageSize;

    /** 是否可以下一页*/
    private boolean nextPage = Boolean.TRUE;
    /** 是否可以上一页*/
    private boolean upPage = Boolean.TRUE;

    /**
     * 假分页
     * @author L
     * @date 2020/9/15 16:50
     * @param {list:数据集, curPage:页码, limit:叶容量}
     * @return
     **/
    public K(List<T> list, int curPage, int limit){
        List<T> dataList = list.stream().skip(limit * (curPage - 1)).limit(limit).collect(Collectors.toList());
        this.setTotal(list.size());
        this.setPages((list.size() - 1) / limit + 1);
        this.setCurPage(curPage);
        this.setLimit(limit);
        this.setDataList(dataList);
        this.setCurPageSize(dataList.size());
        this.setNextPage(this.getCurPage() != this.getPages());
        this.setUpPage(this.getCurPage() != 1);
    }

    /**
     * 封装响应分页对象
     * @param page mp的分页属性
     * @param dataList 结果集
     * @return
     */
    public static <T> K <T> build(IPage page, List<T> dataList){
        K result = new K();
        result.setDataList(dataList);
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setCurPage(page.getCurrent());
        result.setLimit(page.getSize());
        result.setCurPageSize(dataList.size());
        result.setCode(REnum.SUCCESS.getCode());
        result.setMessage(REnum.SUCCESS.getMsg());
        result.setNextPage(result.getCurPage() != result.getPages());
        result.setUpPage(result.getCurPage() != 1);
        return result;
    }
}
