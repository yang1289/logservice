package com.mituo.logservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mituo.logservice.dao.CmptFullLogDao;
import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.dto.InterLogDTO;
import com.mituo.logservice.util.DateUtil;
import com.mituo.logservice.util.SavePathUtil;
import com.mituo.logservice.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SaveLogService {
    @Autowired
    private CmptFullLogDao cmptFullLogDao;

    private static Logger logger= LogManager.getLogger(SaveLogService.class);
    @Value("${log.testRootPath}")
    private String testRootPath;

    @Value("${log.rootPath}")
    private String rootPath;

    public String saveLogFile(){
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        DateUtil dateUtil=new DateUtil();
        dateUtil.setDate(-1);
        Date startDate=dateUtil.getDateFromCalendar(0,0,0,0);
        Date endDate=dateUtil.getDateFromCalendar(23,59,59,999);
        logger.info("startDate:"+format.format(startDate));
        logger.info("endDate:"+format.format(endDate));

        List<CmptFullLog> fullLogList=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);
        logger.info("fullloglist:"+fullLogList.size());
        SavePathUtil savePathUtil=new SavePathUtil();
        if(null!=fullLogList){
            List<InterLogDTO> interLogs=getInterLogs(fullLogList);
            try {
                String jsonbody=JSONObject.toJSONString(interLogs);
                FileWriter writer=new FileWriter(savePathUtil.getSavePath(rootPath,endDate));
                writer.write(jsonbody);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "保存文件成功";
        }else{
            return "保存文件失败";
        }

    }

    public String saveLogFileByDay(String day) {
        DateUtil dateUtil=new DateUtil();
        Date startDate=dateUtil.getDateFromDate(day,0,0,0,0);
        Date endDate=dateUtil.getDateFromDate(day,23,59,59,999);
        List<CmptFullLog> fullLogList=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);
        List<InterLogDTO> interLogs=getInterLogs(fullLogList);
        return saveFile(interLogs,testRootPath,endDate);

    }

    public String saveLogFileByTime(String startTime,String endTime){
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Date startDate=null;
        Date endDate=null;
        try {
            startDate =dateFormat.parse(startTime);
            endDate=dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<CmptFullLog> fullLogList=cmptFullLogDao.findByRequestTimeBetween(startDate,endDate);
        List<InterLogDTO> interLogs=getInterLogs(fullLogList);
        return saveFile(interLogs,testRootPath,endDate);
    }

    public CmptFullLog findone(String id){
        Optional<CmptFullLog> optionalCmptFullLog=cmptFullLogDao.findById(id);
        if(optionalCmptFullLog.isPresent()){
            return optionalCmptFullLog.get();
        }else{
            return null;
        }
    }
    public CmptFullLog findByDate(long timestamp){
        Date queryDate=new Date(timestamp);
        CmptFullLog  cmptFullLog=cmptFullLogDao.findByRequestTime(queryDate);
        return cmptFullLog;
    }

    private String saveFile(List<InterLogDTO> savedata,String savepath,Date date){
        if(null!=savedata){
            SavePathUtil savePathUtil=new SavePathUtil();
            FileWriter writer= null;
            String result="保存文件成功";
            try {
                writer = new FileWriter(savePathUtil.getSavePath(savepath,date));
                writer.write(JSON.toJSONString(savedata));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                result="保存文件失败";
            }
            return result;
        }else{
            return "保存文件失败";
        }



    }

    private List<InterLogDTO> getInterLogs(List<CmptFullLog> cmptFullLogs){
        List<InterLogDTO> interLogs=new ArrayList<>();
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



            InterLogDTO interLog=new InterLogDTO(requestTime,requester,cmptFullLog.getRequestClientIp(),
                    interfaceCondition,isOk,username,sfzh,"","");
            interLogs.add(interLog);
        }
        return interLogs;
    }
}
