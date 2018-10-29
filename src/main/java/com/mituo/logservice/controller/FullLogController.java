package com.mituo.logservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mituo.logservice.dao.CmptFullLogDao;

import com.mituo.logservice.dao.InterLogDAO;
import com.mituo.logservice.dto.StatusDTO;
import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.entity.InterLog;
import com.mituo.logservice.service.SaveLogService;
import com.mituo.logservice.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class FullLogController {

    @Autowired
    private CmptFullLogDao cmptFullLogDao;
    @Autowired
    private SaveLogService saveLogService;

    private static Logger logger=LogManager.getLogger(FullLogController.class);

    @RequestMapping("/saveFullLog")
    public  Mono<StatusDTO> saveFulllog(@RequestBody final CmptFullLog cmptFullLog){
        Mono<StatusDTO> statusDTO;
        Mono<CmptFullLog> cmptFullLogMono=cmptFullLogDao.save(cmptFullLog);
        statusDTO=cmptFullLogMono.then(Mono.just(new StatusDTO("success","保存日志成功",null)));
        statusDTO.onErrorResume(error->Mono.just(new StatusDTO("failed","保存日志出错",error)));
        return statusDTO;
    }


    @GetMapping("/findall")
    public Flux<CmptFullLog> findAll(){
        return cmptFullLogDao.findAll();
    }

    @GetMapping("/savelogbytime/{startTime}/{endTime}")
    public String saveLogByTime(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime){
        saveLogService.saveLogFileByTime(startTime,endTime);
        return "按时间保存日志成功";
    }



}
