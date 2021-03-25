package com.movies.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movies.common.constant.CommonConst;
import com.movies.search.common.IndexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志索引映射对象
 * @author lx Zhang.
 * @date 2021/3/25 3:51 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = IndexEnum.LOG_INDEX_NAME,shards = 1,replicas = 1)
public class LogIndex implements Serializable {

    /** ID，与_id保持一致*/
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    /** 访问ip*/
    @Field(type = FieldType.Keyword)
    private String ip;

    /** 请求path*/
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String path;

    /** http method*/
    @Field(type = FieldType.Keyword)
    private String method;

    /** 执行资源全路径*/
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String sourceClassPath;

    /** 执行时间*/
    @Field(type = FieldType.Long)
    private Long costTimes;

    /** 访问时间*/
    @Field(type = FieldType.Date,format = DateFormat.date_time)
    @JsonFormat(pattern = CommonConst.DATETIME_FORMAT)
    private LocalDateTime accessTime;

    /** 请求参数*/
    @Field(type = FieldType.Keyword)
    private String params;


}
