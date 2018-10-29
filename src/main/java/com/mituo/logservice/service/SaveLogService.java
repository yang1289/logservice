package com.mituo.logservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mituo.logservice.dao.CmptFullLogDao;
import com.mituo.logservice.dao.InterLogDAO;
import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.entity.InterLog;
import com.mituo.logservice.util.DateUtil;
import com.mituo.logservice.util.SavePathUtil;
import com.mituo.logservice.util.StringUtil;
import javafx.scene.input.DataFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
        DateUtil dateUtil=new DateUtil();
        Date startDate=dateUtil.getDateFromCalendar(-1,0,0,0,0);
        Date endDate=dateUtil.getDateFromCalendar(-1,23,59,59,999);
        Flux<CmptFullLog> fullLogFlux=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);
        SavePathUtil savePathUtil=new SavePathUtil();
        List<CmptFullLog> fullLogs= fullLogFlux.buffer().blockLast();
        List<InterLog> interLogs=getInterLogs(fullLogs);
        try {
            String jsonbody=JSONObject.toJSONString(interLogs);
            FileWriter writer=new FileWriter(savePathUtil.getSavePath(rootPath));
            writer.write(jsonbody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void saveLogFileByTime(String startTime,String endTime){
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Date startDate=null;
        Date endDate=null;
        try {
            startDate =dateFormat.parse(startTime);
            endDate=dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Flux<CmptFullLog> fullLogFlux=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);
        List<InterLog> interLogs=getInterLogs(fullLogFlux.buffer().blockLast());
        saveFile(JSON.toJSONString(interLogs),testRootPath);

    }

    private void saveFile(String savedata,String savepath){
        SavePathUtil savePathUtil=new SavePathUtil();
        try {
            FileWriter writer=new FileWriter(savePathUtil.getSavePath(savepath));
            writer.write(savedata);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<InterLog> getInterLogs(List<CmptFullLog> cmptFullLogs){
        List<InterLog> interLogs=new ArrayList<>();
        for(CmptFullLog cmptFullLog:cmptFullLogs){

            DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
            String requestTime=dateFormat.format(cmptFullLog.getRequestTime());

            boolean isOk=true;
            if(!"".equals(cmptFullLog.getResponseErrorInfo()) && null!=cmptFullLog.getResponseErrorInfo()){
                isOk=false;
            }

            String username="";
            String interfaceCondition="";
            String sfzh="";
            String requester="";
            String usernameRegex="\"*xm\"*\\s*[:|=]\\s*\"*([a-zA-Z0-9_]*)\"*";
            String sfzhRegex="\"*sfzh\"*\\s*[:|=]\\s*\"*([a-zA-Z0-9_]*)\"*";
            String conditionRegex="\"*condition\"*\\s*[:|=]\\s*([a-zA-Z0-9_,./{}:;\\[\\]\"'?]+)";
            String jgdmRegex="\"*jgdm\"*\\s*[:|=]\\s*\"*([a-zA-Z0-9_]*)\"*";
            Pattern usernamePattern=Pattern.compile(usernameRegex);
            Pattern sfzhPattern=Pattern.compile(sfzhRegex);
            Pattern conditionPattern=Pattern.compile(conditionRegex);
            StringUtil stringUtil=new StringUtil();
            if(null!=cmptFullLog.getRequestParams() && !"".equals(cmptFullLog.getRequestParams())){
                String requestParamsData=stringUtil.clearBlankWrapTabsLowCase(cmptFullLog.getRequestParams());
                logger.info("requestParamData"+requestParamsData);
                try{
                    username=stringUtil.getSubString(requestParamsData,usernameRegex,1);
                    sfzh=stringUtil.getSubString(requestParamsData,sfzhRegex,1);
                    if(!"".equals(cmptFullLog.getApiName()) && null!=cmptFullLog.getApiName()){
                        interfaceCondition="接口名称:"+cmptFullLog.getApiName()+" 查询内容:"+stringUtil.getSubString(requestParamsData,conditionRegex,1);
                    }else{
                        interfaceCondition="接口名称:无 "+" 查询内容:"+stringUtil.getSubString(requestParamsData,conditionRegex,1);
                    }
                    requester="单位:"+stringUtil.getSubString(requestParamsData,jgdmRegex,1);
                }catch(Exception e){
                    logger.error(e.getMessage());
                }
            }else if(null!=cmptFullLog.getRequestPostParams() && !"".equals(cmptFullLog.getRequestPostParams())){
                String requestPostParamsData=stringUtil.clearBlankWrapTabsLowCase(cmptFullLog.getRequestPostParams());
                logger.info("requestPostParamsData"+requestPostParamsData);
                try{
                    username=stringUtil.getSubString(requestPostParamsData,usernameRegex,1);
                    sfzh=stringUtil.getSubString(requestPostParamsData,sfzhRegex,1);
                    if(!"".equals(cmptFullLog.getApiName()) && null!=cmptFullLog.getApiName()){
                        interfaceCondition="接口名称:"+cmptFullLog.getApiName()+" 查询内容:"+stringUtil.getSubString(requestPostParamsData,conditionRegex,1);
                    }else{
                        interfaceCondition="接口名称:无 "+" 查询内容:"+stringUtil.getSubString(requestPostParamsData,conditionRegex,1);
                    }
                    requester="单位:"+stringUtil.getSubString(requestPostParamsData,jgdmRegex,1);
                }catch(Exception e){
                    logger.error(e.getMessage());
                }
            }else if(null!=cmptFullLog.getRequestBody() && !"".equals(cmptFullLog.getRequestBody())){
                String requestBodyData=stringUtil.clearBlankWrapTabsLowCase(cmptFullLog.getRequestBody());
                logger.info("requestBodyData"+requestBodyData);
                try{
                    JSONObject bodyJsonObject= JSON.parseObject(requestBodyData);
                    JSONObject endUser=(JSONObject) bodyJsonObject.get("enduser");
                    String xm=endUser.getString("xm");
                    String sfz=(String)endUser.getString("sfzh");
                    String jgdm=(String)endUser.getString("jgdm");
                    if(null!=xm&&!"".equals(xm)){
                        username=xm;
                    }

                    if(null!=sfz&&!"".equals(sfz)){
                        sfzh=sfz;
                    }
                    if(null!=jgdm&&!"".equals(jgdm)){
                        requester="单位:"+jgdm;
                    }

                    String condition=bodyJsonObject.getString("condition");
                    if(null!=condition&&!"".equals(condition)){
                        interfaceCondition="接口名称:"+cmptFullLog.getApiName()+" 查询内容:"+condition;
                    }else{
                        interfaceCondition="接口名称:无 查询内容:无";
                    }

                }catch(Exception e){
                    logger.error("requestbody json格式解析错误");
                    logger.error(e.getMessage());
                }
            }else {
                logger.info("没有请求参数");
            }



            InterLog interLog=new InterLog(requestTime,requester,cmptFullLog.getRequestClientIp(),
                    interfaceCondition,isOk,username,sfzh,"","");
            interLogs.add(interLog);
        }
        return interLogs;
    }
}
