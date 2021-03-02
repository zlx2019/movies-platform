package com.movies.common.model.base;

import com.movies.common.constant.CommonConst;
import com.movies.common.enums.REnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应对象
 * @author lx Zhang.
 * @date 2021/3/1 9:53 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class R <T> implements Serializable {
    /** 响应体*/
    private T data;
    /** 响应消息*/
    private String message;
    /** 响应码  0:正常  1:异常*/
    private Integer code;

    /** Default 成功*/
    public static <T> R<T> Success(T data){
        return new R<T>(data,CommonConst.SUCCESS,CommonConst.SUCCESS_CODE);
    }
    public static <T> R<T> Success(Integer code,T data){
        return new R<T>(data,CommonConst.SUCCESS,CommonConst.SUCCESS_CODE);
    }


    /** Default 错误*/
    public static <T> R<T> Failed(){
        return new R<T>(null, CommonConst.FAILED,CommonConst.FAILED_CODE);
    }
    /** 携带错误消息的响应*/
    public static <T> R<T> Failed(String msg){
        return new R<T>(null,msg, REnum.ERROR.getCode());
    }
    /** 通过枚举指定响应错误消息*/
    public static <T> R<T> Failed(REnum rEnum){
        return new R<T>(null, rEnum.getMsg(), rEnum.getCode());
    }


}
