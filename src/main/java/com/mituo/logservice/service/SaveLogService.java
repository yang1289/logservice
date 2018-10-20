package com.mituo.logservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mituo.logservice.dao.CmptFullLogDao;
import com.mituo.logservice.dao.InterLogDAO;
import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.entity.InterLog;
import com.mituo.logservice.util.SavePathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private InterLogDAO interLogDAO;

    private static Logger logger= LogManager.getLogger(SaveLogService.class);
    @Value("${log.testRootPath}")
    private String testRootPath;

    @Value("${log.rootPath}")
    private String rootPath;

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
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Flux<InterLog> fullLogFlux=interLogDAO.findByInterFaceTimeBetween(dateFormat.format(startDate),dateFormat.format(endDate));
        SavePathUtil savePathUtil=new SavePathUtil();
        logger.info("startDate:"+startDate);
        logger.info("endDate:"+endDate);
        try {
            List<InterLog> fullLogs= fullLogFlux.buffer().blockLast();
            String jsonbody=JSONObject.toJSONString(fullLogs);
            FileWriter writer=new FileWriter(savePathUtil.getSavePath(rootPath));
            writer.write(jsonbody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testSaveLog(){
        SavePathUtil savePathUtil=new SavePathUtil();
        Calendar startTime=Calendar.getInstance();
        //startTime.add(Calendar.DAY_OF_MONTH,-1);
        startTime.set(Calendar.HOUR_OF_DAY,0);
        startTime.set(Calendar.MINUTE,0);
        startTime.set(Calendar.SECOND,0);
        startTime.set(Calendar.MILLISECOND,0);
        Date startDate=startTime.getTime();

        Calendar endTime=Calendar.getInstance();
       // endTime.add(Calendar.DAY_OF_MONTH,-1);
        endTime.set(Calendar.HOUR_OF_DAY,23);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.SECOND,59);
        endTime.set(Calendar.MILLISECOND,999);
        Date endDate=endTime.getTime();
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Flux<InterLog> fullLogFlux=interLogDAO.findByInterFaceTimeBetween(dateFormat.format(startDate),dateFormat.format(endDate));

        logger.info("startDate:"+startDate);
        logger.info("endDate:"+endDate);
        try {
            List<InterLog> fullLogs= fullLogFlux.buffer().blockLast();
            String jsonbody=JSONObject.toJSONString(fullLogs);
            FileWriter writer=new FileWriter(savePathUtil.getSavePath(testRootPath));
            writer.write(jsonbody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
