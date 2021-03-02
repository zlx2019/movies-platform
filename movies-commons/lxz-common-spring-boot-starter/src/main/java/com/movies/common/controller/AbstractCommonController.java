package com.movies.common.controller;

import com.movies.common.holder.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用抽象控制层
 * @author lx Zhang.
 * @date 2021/3/2 3:01 下午
 */
@Slf4j
public abstract class AbstractCommonController {

    public Long getCurrentUserId(){
        String user = UserContextHolder.getUser();
        if (StringUtils.isBlank(user)){
            //TODO
//            log.error("[公共控制层模块] 获取用户信息失败!");
//            throw new ResponseException(ExceptionCodeEnum.NOT_AUTHORITY);
        }
        return Long.valueOf(user);
    }
}
