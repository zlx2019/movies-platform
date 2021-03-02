package com.movies.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.movies.common.lock.DistributedLock;

/**
 * 公共业务接口,实现一些公共API
 * @author L
 * @date 2020/10/14
 */
public interface ISuperService<T> extends IService<T> {

    /**
     * 幂等性新增数据
     *
     * @param model     实体对象
     * @param lock      分布式锁
     * @param lockKey   锁的key
     * @param conditionWrapper 是否已存在的条件Wrapper
     * @param message   提示信息
     * @return
     */
    boolean constantSave(T model, DistributedLock lock, String lockKey, Wrapper<T> conditionWrapper, String message);


    /**
     *  幂等性新增或者修改数据
     *
     * @param model     实体对象
     * @param lock      分布式锁
     * @param lockKey   锁的Key
     * @param conditionWrapper  是否已存在的条件Wrapper
     * @param message   提示信息
     * @return
     */
    boolean constantSaveOrModify(T model,DistributedLock lock, String lockKey, Wrapper<T> conditionWrapper,String message);
}
