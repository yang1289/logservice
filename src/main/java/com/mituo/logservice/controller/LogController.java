package com.mituo.logservice.controller;


import com.mituo.logservice.entity.InterLog;
import com.mituo.logservice.service.InterLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    private static Logger logger=LoggerFactory.getLogger(LogController.class);

    @Autowired
    private InterLogService interLogService;

    @GetMapping("/test")
    public String test() {
        return "rest serivce";
    }

    @GetMapping("/find")
    public String findByNumId(Integer id){
        InterLog interLog=interLogService.findByNumId(id);
        return interLog.getRequester();
    }
}
