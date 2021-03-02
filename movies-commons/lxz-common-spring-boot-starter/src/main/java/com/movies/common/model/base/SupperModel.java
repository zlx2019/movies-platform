package com.movies.common.model.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MP 基础实体
 * @author lx Zhang.
 * @date 2021/3/1 10:46 下午
 */
@Getter
@Setter
public class SupperModel<T extends Model<?>> extends Model<T> implements Serializable {

    /**主键 id*/
    @TableId(type = IdType.INPUT)
    private Long id;

    /**创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除 0:未删除 1:已删除*/
    @TableLogic
    private Boolean isDeleted;

}
