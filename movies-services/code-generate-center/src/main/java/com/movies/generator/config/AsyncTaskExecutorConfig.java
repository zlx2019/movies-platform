package com.movies.generator.config;

import com.movies.common.config.DefaultAsyncTaskConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 启用自定义的异步线程池
 * @author lx Zhang.
 * @date 2021/3/2 3:46 下午
 */
@Configuration
public class AsyncTaskExecutorConfig extends DefaultAsyncTaskConfig {
}
