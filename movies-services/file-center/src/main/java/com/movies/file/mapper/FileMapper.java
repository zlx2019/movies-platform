package com.movies.file.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.movies.common.model.File;
import com.movies.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 上传存储db
 *
 * @author mr.zhang
 * @date 219/8/27
 */
public interface FileMapper extends SuperMapper<File> {

    List<File> findList(Page<File> page, @Param("f") Map<String, Object> params);
}
