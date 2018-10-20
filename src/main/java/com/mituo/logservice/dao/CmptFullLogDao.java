package com.mituo.logservice.dao;

import com.mituo.logservice.entity.CmptFullLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;

@Repository
public interface CmptFullLogDao extends ReactiveMongoRepository<CmptFullLog,String> {
    Flux<CmptFullLog> findByRequestTimeBetween(Date startDate,Date stopDate);
}
