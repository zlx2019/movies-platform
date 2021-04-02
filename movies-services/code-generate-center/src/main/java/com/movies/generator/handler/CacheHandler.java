package com.movies.generator.handler;

import com.movies.cache.template.CacheTemplate;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存相关定时任务
 * @author lx Zhang.
 * @date 2021/4/2 11:57 上午
 *
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 *      1、任务开发：在Spring Bean实例中，开发Job方法；
 *      2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *      3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 *      4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
@Slf4j
public class CacheHandler {

    @Autowired
    private CacheTemplate cacheTemplate;

    /**
     * 清除Redis缓存数据
     * @Author lx Zhang.
     * @Date 2021/4/2 11:58 上午
     * @return void
     **/
    @XxlJob(value = "flushCacheHandler")
    public void flushCache(){
        log.info("[flushCacheHandler]任务开始");
        XxlJobHelper.log("[flushCacheHandler]任务开始");
        cacheTemplate.flushDB();
        log.info("[flushCacheHandler]任务结束");
    }
}
