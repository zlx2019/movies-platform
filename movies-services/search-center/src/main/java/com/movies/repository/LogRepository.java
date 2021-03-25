package com.movies.repository;

import com.movies.index.LogIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * LogIndex 接口
 * @author lx Zhang.
 * @date 2021/3/25 4:13 下午
 */
public interface LogRepository extends ElasticsearchRepository<LogIndex,Long> {
}
