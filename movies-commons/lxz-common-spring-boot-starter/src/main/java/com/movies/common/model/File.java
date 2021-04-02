package com.movies.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author lx Zhang.
 * @date 2021/4/2 6:54 下午
 */
@TableName(value = "movies_file")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class File extends Model<File> {

    @TableId
    private String id;
    /**
     * 原始文件名
     */
    private String name;
    /**
     * 是否图片
     */
    private Boolean isImg;
    /**
     * 上传文件类型
     */
    private String contentType;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件在OSS中的名称
     */
    private String ossName;
    /**
     * oss访问路径 oss需要设置公共读
     */
    private String url;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
