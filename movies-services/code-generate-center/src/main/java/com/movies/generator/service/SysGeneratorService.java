package com.movies.generator.service;

import com.movies.common.model.base.K;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成接口
 * @author mr.zhang
 * @date 2019/8/27
 */
@Service
public interface SysGeneratorService{

     /**
      * 分页查询数据库所有表
      */
     K queryList(Map<String, Object> map);

     /**
      * 根据表名查询数据表信息
      * @param tableName
      * @return
      */
     Map<String, String> queryTable(String tableName);


     /**
      * 根据表名查询 表下的所有字段属性
      * @param tableName
      * @return
      */
     List<Map<String, String>> queryColumns(String tableName);

     /**
      * 生成代码
      * @param tableNames
      * @return
      */
     byte[] generatorCode(String[] tableNames);
}
