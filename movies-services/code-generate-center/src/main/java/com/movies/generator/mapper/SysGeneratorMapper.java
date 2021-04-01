package com.movies.generator.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movies.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据表、字段查询Mapper
 * @author lx Zhang.
 * @date 2021/3/31 10:20 下午
 */
@Component
@Mapper
public interface SysGeneratorMapper extends SuperMapper {

    List<Map<String, Object>> queryList(Page<Map<String, Object>> page, @Param("p") Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
