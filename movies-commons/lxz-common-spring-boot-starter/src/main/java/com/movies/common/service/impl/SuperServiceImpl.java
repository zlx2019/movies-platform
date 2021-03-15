package com.movies.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.movies.common.enums.ExceptionMessage;
import com.movies.common.exception.BusinessException;
import com.movies.common.lock.DistributedLock;
import com.movies.common.model.base.K;
import com.movies.common.service.ISuperService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 公共业务接口实现类
 * @author L
 * @date 2020/10/14
 */
@Slf4j
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements ISuperService<T> {

    /**
     * 幂等性新增数据
     *
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param model     实体对象
     * @param lock      分布式锁
     * @param lockKey   锁的key
     * @param conditionWrapper 是否已存在的条件Wrapper
     * @param message   提示信息
     * @return
     */
    @Override
    public boolean constantSave(T model, DistributedLock lock, String lockKey, Wrapper<T> conditionWrapper, String message) {
        Optional.ofNullable(lock).orElseThrow(()-> new BusinessException(ExceptionMessage.LOCK_NULL.getMessage()));
        Optional.ofNullable(lockKey).orElseThrow(()-> new BusinessException(ExceptionMessage.LOCK_KEY_NULL.getMessage()));
        try {
            boolean upLock = lock.lock(lockKey);//加锁
            if (upLock){
                //是否已存在记录,
                if (super.count(conditionWrapper) == 0){
                    return super.save(model);
                }else {
                    //已存在
                    return false;
                }
            }else {
                //加锁超时
                throw new BusinessException(ExceptionMessage.LOCK_TIME_OUT.getMessage());
            }
        }finally {
            //必须释放锁
            lock.releaseLock(lockKey);
        }
    }

    /**
     *  幂等性新增或者修改数据
     *  例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param model     实体对象
     * @param lock      分布式锁
     * @param lockKey   锁的Key
     * @param conditionWrapper  是否已存在的条件Wrapper
     * @param message   提示信息
     * @return
     */
    @Override
    public boolean constantSaveOrModify(T model, DistributedLock lock, String lockKey, Wrapper<T> conditionWrapper, String message) {
        if (model == null) return false;
        Class<?> cls = model.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        if (null == tableInfo || StringUtils.isBlank(tableInfo.getKeyProperty())){
            throw new BusinessException("Error:  Can not execute. Could not find @TableId.");
        }
        Object idVal = ReflectionKit.getFieldValue(model, tableInfo.getKeyProperty());
        if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
            if (StrUtil.isEmpty(message)) message = "数据已存在";
            return this.constantSave(model, lock, lockKey, conditionWrapper, message);
        }else {
            return updateById(model);
        }
    }

    /**
     * 公共分页查询
     * @Author lx Zhang.
     * @Date 2021/3/15 11:49 下午
     * @Param [current, size]
     * @return com.movies.common.model.base.K<T>
     **/
    @Override
    public K<T> listPage(Integer current, Integer size) {
        Page<Map<String, Object>> maps = super.pageMaps(new Page<>(current, size),null);
        K<Map<String, Object>> k = K.build(maps, maps.getRecords());
        return (K<T>) k;
    }
}
