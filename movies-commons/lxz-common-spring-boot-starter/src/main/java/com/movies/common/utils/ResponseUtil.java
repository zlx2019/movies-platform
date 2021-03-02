package com.movies.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.common.holder.UserContextHolder;
import com.movies.common.model.base.R;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 使用流 响应客户端工具
 * @author lx Zhang
 * @date 2019/8/24
 */
public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response
     * @param msg          返回信息
     * @param httpStatus   返回状态码
     * @throws IOException
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, int httpStatus) throws IOException {
        R r = R.Success(httpStatus,msg);
        responseWrite(objectMapper, response, r);
    }

    /**
     * 通过流写到前端
     * @param objectMapper 对象序列化
     * @param response
     * @param obj
     */
    public static void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, Object obj) throws IOException {
        R result = R.Success(obj);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     * @param objectMapper
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void responseFailed(ObjectMapper objectMapper, HttpServletResponse response, String msg) throws IOException {
        R result = R.Failed(msg);
        responseWrite(objectMapper, response, result);
    }

    private static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, R result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(result));
            String user = UserContextHolder.getUser();
            writer.flush();
        }
    }
}
