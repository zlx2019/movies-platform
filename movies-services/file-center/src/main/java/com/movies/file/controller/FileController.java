package com.movies.file.controller;

import com.movies.common.enums.REnum;
import com.movies.common.model.base.K;
import com.movies.common.model.base.R;
import com.movies.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 文件上传
 * @author lx Zhang.
 * @date 2021/4/2 7:40 下午
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 分页查询文件列表
     * @Author lx Zhang.
     * @Date 2021/4/2 8:54 下午
     * @Param [params]
     * @return com.movies.common.model.base.K
     **/
    @GetMapping
    public K list(@RequestParam Map<String, Object> params) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return fileService.findList(params);
    }

    /**
     * 文件上传
     * @Author lx Zhang.
     * @Date 2021/4/2 7:43 下午
     * @Param [file]
     * @return com.movies.common.model.File
     **/
    @PostMapping
    public R uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        return fileService.upload(file);
    }

    /**
     * 文件删除
     * @Author lx Zhang.
     * @Date 2021/4/2 8:27 下午
     * @Param [id]
     * @return com.movies.common.model.base.R
     **/
    @DeleteMapping("/{id}")
    public R deleteFile(@PathVariable String id){
        try {
            fileService.delete(id);
            return R.Success(REnum.SUCCESS);
        }catch (Exception e){
            return R.Failed(REnum.ERROR);
        }
    }
}
