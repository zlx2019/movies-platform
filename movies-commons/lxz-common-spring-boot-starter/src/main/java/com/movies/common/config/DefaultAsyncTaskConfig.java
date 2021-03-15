package com.movies.common.config;

import com.movies.common.utils.CustomThreadPoolTaskExecutor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * SpringBoot Task异步任务线程池默认配置,每个服务继承该类即可
 * @author lx Zhang.
 * @date 2020/12/23 11:57 上午
 */
@Getter
@Setter
@EnableAsync(proxyTargetClass = true)
public class DefaultAsyncTaskConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize = 16;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 96;
    /**
     * 等待队列容量
     */
    private int queueCapacity = 10;
    /**
     * 线程池中的线程名称前缀
     */
    private String threadNamePrefix = "zero-async-";

    /**
     * 自定义异步线程池
     * @Author lx Zhang.
     * @Date 2020/12/23 12:04 下午
     * @Param []
     * @return org.springframework.core.task.TaskExecutor
     **/
    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new CustomThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        //设置拒绝策略,当pool线程数达到MaxPoolSize时,由调用者处理该任务  || CustomThreadCallHandler 自定义拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
