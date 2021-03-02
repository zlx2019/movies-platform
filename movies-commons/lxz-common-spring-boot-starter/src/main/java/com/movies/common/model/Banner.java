package com.movies.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.movies.common.model.base.SupperModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 首页Banner实体
 * @author lx Zhang.
 * @date 2021/3/1 10:39 下午
 */
@TableName("crm_banner")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Banner extends SupperModel {

    /** 标题*/
    private String title;
    /** 图片地址*/
    private String imageUrl;
    /** 链接地址*/
    private String linkUrl;
    /** 排序*/
    private Integer sort;

}
