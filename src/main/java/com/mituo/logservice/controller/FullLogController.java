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
    private InterLogDAO interLogDAO;
    @Autowired
    private SaveLogService saveLogService;

    private static Logger logger=LogManager.getLogger(FullLogController.class);

    @RequestMapping("/saveFullLog")
    public Mono<StatusDTO> saveFullLog(@RequestBody final CmptFullLog cmptFullLog){
        Mono<StatusDTO> statusDTO;
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

        Mono<InterLog> fullLog=interLogDAO.insert(interLog);
        statusDTO=fullLog.then(Mono.just(new StatusDTO("success","保存日志成功",null)));
        statusDTO.onErrorResume(error->Mono.just(new StatusDTO("failed","保存日志出错",null)));
//        Mono<StatusDTO> statusDTOMono=fullLog.onErrorResume()
//                .then(Mono.just(new StatusDTO("failed","保存日志出错",null)));
        //then(Mono.just(new StatusDTO("failed","保存日志出错",null)));
//        if(null!=fullLog){
//            statusDTO=Mono.just();
//        }else{
//            statusDTO=Mono.just(new StatusDTO("failed","保存日志出错",null));
//        }
        logger.info(fullLog);
        return statusDTO;
    }

    @GetMapping("/findall")
    public Flux<CmptFullLog> findAll(){
        return cmptFullLogDao.findAll();
    }

    @GetMapping("/savelog")
    public String saveLog2File(){
        saveLogService.testSaveLog();
        return "保存日志成功";
    }

    @GetMapping("/savelogbytime/{startTime}/{endTime}")
    public String saveLogByTime(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime){
        saveLogService.saveLogFileByTime(startTime,endTime);
        return "按时间保存日志成功";
    }



}
