package com.mituo.logservice.job;


import com.mituo.logservice.serivce.SaveLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SaveLogFileTask {
    @Autowired
    private SaveLogService saveLogService;

    private static Logger logger=LogManager.getLogger(SaveLogService.class);

    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(fixedRate = 5000)
    public void savetask(){
        saveLogService.saveLogFile();
        logger.info("执行定时任务");
    }
}
