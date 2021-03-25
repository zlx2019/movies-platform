package com.movies.config;

import com.movies.common.exception.DefaultExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 统一异常处理启用类
 * @author lx Zhang.
 * @date 2021/3/2 3:44 下午
 */
@ControllerAdvice
public class ExceptionConfig extends DefaultExceptionAdvice {
}
