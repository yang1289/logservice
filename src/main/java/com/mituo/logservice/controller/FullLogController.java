package com.mituo.logservice.controller;

import com.alibaba.fastjson.JSON;
import com.mituo.logservice.dao.CmptFullLogDao;


import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.service.SaveLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class FullLogController {

    @Autowired
    private CmptFullLogDao cmptFullLogDao;
    @Autowired
    private SaveLogService saveLogService;

    private static Logger logger=LogManager.getLogger(FullLogController.class);


    @GetMapping("/savelogbytime/{startTime}/{endTime}")
    public String saveLogByTime(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime){
        return saveLogService.saveLogFileByTime(startTime,endTime);
    }

    @RequestMapping("/saveFullLog/{day}")
    public String saveFullLogByDay(@PathVariable("day") String day){
        return saveLogService.saveLogFileByDay(day);
//        return saveLogService.saveLogFile();
    }

    @RequestMapping("/findone/{id}")
    public String findOne(@PathVariable("id") String id){
        CmptFullLog cmptFullLog=saveLogService.findone(id);
        logger.info("timestamp:"+cmptFullLog.getRequestTime());
        return JSON.toJSONString(cmptFullLog);
    }

    @RequestMapping("/findoneByDate/{time}")
    public String findOneByDate(@PathVariable("time") long timestamp){
        CmptFullLog cmptFullLog=saveLogService.findByDate(timestamp);
        return JSON.toJSONString(cmptFullLog);
    }



}
