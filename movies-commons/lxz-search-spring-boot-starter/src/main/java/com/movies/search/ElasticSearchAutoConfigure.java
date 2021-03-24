package com.movies.search;

import com.movies.search.config.ElasticSearchClientConfigure;
import org.springframework.context.annotation.Import;

/**
 * ElasticSearch 自动配置类
 * @author lx Zhang.
 * @date 2021/3/24 10:46 下午
 */
@Import(ElasticSearchClientConfigure.class)
public class ElasticSearchAutoConfigure {
}
