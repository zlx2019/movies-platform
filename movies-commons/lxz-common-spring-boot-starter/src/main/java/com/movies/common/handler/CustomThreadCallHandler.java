package com.movies.common.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池拒绝策略
 * @author lx Zhang.
 * @date 2021/3/11 8:15 下午
 */
public class CustomThreadCallHandler implements RejectedExecutionHandler {
    
    /**
     * 进入自选状态,等待队列空出位置后,再将任务进行执行 -->不推荐使用
     * @Author lx Zhang.
     * @Date 2021/3/11 8:17 下午 
     * @Param [r, executor]
     * @return void
     **/
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()){
            while (executor.getQueue().remainingCapacity() == 0);
            executor.execute(r);

            //TODO 异常日志记录
            //TODO 报警处理
        }
    }
}
