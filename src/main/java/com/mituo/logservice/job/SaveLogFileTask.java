package com.mituo.logservice.job;


import com.mituo.logservice.service.SaveLogService;
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
//    @Scheduled(fixedRate = 10000)
    public void savetask(){
        String result=saveLogService.saveLogFile();
        logger.info("执行定时任务");
        logger.info(result);
    }
}
