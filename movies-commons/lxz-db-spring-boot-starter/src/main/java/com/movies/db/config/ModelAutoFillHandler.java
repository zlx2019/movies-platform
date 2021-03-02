package com.movies.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.movies.common.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Mybatis-Plus 数据自动填充,实现com.baomidou.mybatisplus.core.handlers.MetaObjectHandler接口
 * @author lx Zhang.
 * @date 2021/3/1 8:28 下午
 */
@Slf4j
public class ModelAutoFillHandler implements MetaObjectHandler {

    /**
     * 自动填充五位字段
     * id        : ID
     * createTime: 创建时间
     * createBy  : 创建者ID
     * updateBy  : 最后一次修改者ID
     * updateTime: 最后一次修改时间
     */
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";
    private final static String CREATE_BY = "createBy";
    private final static String UPDATE_BY = "updateBy";
    private final static String ID = "id";

    /**
     * 新增填充
     * @Author lx Zhang.
     * @Date 2021/3/1 8:33 下午
     * @Param [metaObject]
     * @return void
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createBy = getFieldValByName(CREATE_BY, metaObject);
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object updateBy = getFieldValByName(UPDATE_BY, metaObject);
        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        Object id = getFieldValByName(ID, metaObject);
        //获取唯一ID
        Long ID_ = IdGenerator.getId();
        //获取当前系统时间
        LocalDateTime data = LocalDateTime.now();
        log.info("fill data:{}",data);
        //非空判断并且自动填充
        //ID主键
//        Optional.ofNullable(id).orElseGet(()-> this.strictInsertFill(metaObject,ID,Long.class,ID_));新版本
        Optional.ofNullable(id).orElseGet(()-> setFieldValByName(ID,ID_,metaObject)); //旧版本填充ID
        //创建时间
        Optional.ofNullable(createTime).orElseGet(()-> this.strictInsertFill(metaObject,CREATE_TIME,LocalDateTime.class,data));
        //修改时间
        Optional.ofNullable(updateTime).orElseGet(()-> this.strictInsertFill(metaObject,UPDATE_TIME,LocalDateTime.class,data));
        //TODO 创建ID者、修改者ID 目前无法获取,暂时不填充,后续再填充
    }

    /**
     * 更新填充
     * @Author lx Zhang.
     * @Date 2021/3/1 9:02 下午
     * @Param [metaObject]
     * @return void
     **/
    @Override
    public void updateFill(MetaObject metaObject) {
        //TODO 暂时只填充修改时间
        this.strictInsertFill(metaObject,UPDATE_TIME,LocalDateTime.class,LocalDateTime.now());
    }
}
