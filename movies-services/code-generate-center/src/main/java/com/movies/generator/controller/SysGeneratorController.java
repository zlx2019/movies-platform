package com.movies.generator.controller;

import com.movies.common.model.base.K;
import com.movies.generator.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成
 * @author lx Zhang.
 * @date 2021/3/31 10:25 下午
 */
@RestController
@RequestMapping("/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 查看数据库所有表信息
     * @Author lx Zhang.
     * @Date 2021/3/31 10:31 下午
     * @Param [params]
     * @return K
     **/
    @ResponseBody
    @GetMapping("/list")
    public K getTableList(@RequestParam Map<String, Object> params) {
        return sysGeneratorService.queryList(params);
    }


    /**
     * 根据表名,生成对应的Controller、Model、Service.impl
     * 注意修改generator.properties文件配置
     * @Author lx Zhang.
     * @Date 2021/3/31 10:42 下午
     * @Param [tables, response]
     * @return void
     **/
    @GetMapping("/code")
    public void makeCode(String tables, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
