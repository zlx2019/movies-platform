package com.movies.gateway.handler;

import com.movies.common.enums.REnum;
import com.movies.common.model.base.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关路由超时、服务异常统一降级
 * @author lx Zhang.
 * @date 2021/4/2 9:30 下午
 */
@RestController

public class DefaultFallbackFactory {

    /**
     * 网关统一 降级
     * @return
     */
    @RequestMapping("/default/fallback")
    public R back(){
        R<Object> result = R.Failed(REnum.FALLBACK);
        return result;
    }
}
