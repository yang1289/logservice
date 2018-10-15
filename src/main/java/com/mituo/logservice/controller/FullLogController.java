package com.mituo.logservice.controller;

import com.mituo.logservice.dao.CmptFullLogDao;

import com.mituo.logservice.dto.StatusDTO;
import com.mituo.logservice.entity.CmptFullLog;
import com.mituo.logservice.entity.InterLog;
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

    private static Logger logger=LogManager.getLogger(FullLogController.class);

    @RequestMapping("/saveFullLog")
    public Mono<StatusDTO> saveFullLog(@RequestBody final CmptFullLog cmptFullLog){
        Mono<StatusDTO> statusDTO;
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTime=dateFormat.format(cmptFullLog.getRequestTime());
        String requester="";
        if(!"".equals(cmptFullLog.getAppName()) && null!=cmptFullLog.getAppName()){
            requester=cmptFullLog.getAppName();
        }
        boolean isOk=true;
        if(!"".equals(cmptFullLog.getResponseErrorInfo()) && null!=cmptFullLog.getResponseErrorInfo()){
            isOk=false;
        }

        String username="";
        String interfaceCondtion="";
        String sfzh="";
        String usernameRegex="\\\"*xm\\\"*\\\\s*[:|=]\\\\s*\\\"*([a-zA-Z0-9_]+)\\\"*";
        String sfzhRegex="\\\"*sfzh\\\"*\\\\s*[:|=]\\\\s*\\\"*([a-zA-Z0-9_]+)\\\"*";
        String conditionRegex="\\\"*condition\\\"*\\\\s*[:|=]\\\\s*([a-zA-Z0-9\\,\\.\\\\\\/\\{\\}\\:\\;\\[\\]\\\"\\\']\\\\?+)";
        Pattern usernamePattern=Pattern.compile(usernameRegex);
        if(null!=cmptFullLog.getRequestParams() && !"".equals(cmptFullLog.getRequestParams())){
            try{
                Matcher usernameMatcher=usernamePattern.matcher(cmptFullLog.getRequestParams());
                if(usernameMatcher.find()){
                    username=usernameMatcher.group(1);
                }
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }else if(null!=cmptFullLog.getRequestPostParams() && !"".equals(cmptFullLog.getRequestPostParams())){
            try{
                Matcher usernameMatcher=usernamePattern.matcher(cmptFullLog.getRequestPostParams());
                if(usernameMatcher.find()){
                    username=usernameMatcher.group(1);
                }
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }else if(null!=cmptFullLog.getRequestBody() && !"".equals(cmptFullLog.getRequestBody())){
            try{
                Matcher usernameMatcher=usernamePattern.matcher(cmptFullLog.getRequestBody());
                if(usernameMatcher.find()) {
                    username = usernameMatcher.group(1);
                }
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }else{
            logger.info("没有请求参数");
        }
        String interfaceCondition="";



//        InterLog interLog=new InterLog(requestTime,requester,cmptFullLog.getRequestClientIp(),)

        Mono<CmptFullLog> fullLog=cmptFullLogDao.insert(cmptFullLog);
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

}
