package com.movies.controller;

import com.google.common.collect.Lists;
import com.movies.common.model.base.R;
import com.movies.common.so.LogIndexSo;
import com.movies.index.LogIndex;
import com.movies.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author lx Zhang.
 * @date 2021/3/25 4:15 下午
 */
@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    /**
     * 日志访问日志到es
     * @Author lx Zhang.
     * @Date 2021/3/25 4:22 下午
     * @Param [logIndexSo]
     * @return void
     **/
    @PostMapping
    public void save(@RequestBody LogIndexSo logIndexSo){
        LogIndex logs = new LogIndex();
        BeanUtils.copyProperties(logIndexSo,logs);
        logRepository.save(logs);
        log.info("save log is success!");
    }

    @GetMapping("/list")
    public R list(){
        Iterable<LogIndex> iterable = logRepository.findAll();
        ArrayList<LogIndex> list = Lists.newArrayList(iterable);
        return R.Success(list);
    }

}
