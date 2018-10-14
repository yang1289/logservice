package com.mituo.logservice.serivce;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mituo.logservice.dao.CmptFullLogDao;
import com.mituo.logservice.entity.CmptFullLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SaveLogService {
    @Autowired
    private CmptFullLogDao cmptFullLogDao;

    private static Logger logger= LogManager.getLogger(SaveLogService.class);

    public void saveLogFile(){
        Calendar startTime=Calendar.getInstance();
        startTime.add(Calendar.DAY_OF_MONTH,-1);
        startTime.set(Calendar.HOUR_OF_DAY,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MILLISECOND,0);
        Date startDate=startTime.getTime();

        Calendar endTime=Calendar.getInstance();
        endTime.add(Calendar.DAY_OF_MONTH,-1);
        endTime.set(Calendar.HOUR_OF_DAY,23);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.SECOND,59);
        endTime.set(Calendar.MILLISECOND,999);
        Date endDate=endTime.getTime();
        Flux<CmptFullLog> fullLogFlux=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);

        File logPath=new File("d:/logfile");
        if(!logPath.exists()){
            logPath.mkdirs();
        }
        Calendar savelogCalendar=Calendar.getInstance();
        savelogCalendar.add(Calendar.DAY_OF_MONTH,-1);
        Date saveLogDay=savelogCalendar.getTime();
        DateFormat formmat=new SimpleDateFormat("yyyy_MM_dd");
        logger.info("startDate:"+startDate);
        logger.info("endDate:"+endDate);
        try {
            List<CmptFullLog> fullLogs= fullLogFlux.buffer().blockLast();
            String jsonbody=JSONObject.toJSONString(fullLogs);
            FileWriter writer=new FileWriter("d:/logfile/api_"+formmat.format(saveLogDay)+".log");
            writer.write(jsonbody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
