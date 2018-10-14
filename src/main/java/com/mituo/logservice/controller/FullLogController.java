package com.mituo.logservice.controller;

import com.mituo.logservice.dao.CmptFullLogDao;

import com.mituo.logservice.dto.StatusDTO;
import com.mituo.logservice.entity.CmptFullLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class FullLogController {

    @Autowired
    private CmptFullLogDao cmptFullLogDao;

    private static Logger logger=LogManager.getLogger(FullLogController.class);

    @RequestMapping("/saveFullLog")
    public Mono<StatusDTO> saveFullLog(@RequestBody final CmptFullLog cmptFullLog){
        Mono<StatusDTO> statusDTO;
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
