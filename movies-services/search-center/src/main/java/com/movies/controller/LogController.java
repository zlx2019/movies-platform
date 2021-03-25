package com.movies.controller;

import com.movies.common.so.LogIndexSo;
import com.movies.index.LogIndex;
import com.movies.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
