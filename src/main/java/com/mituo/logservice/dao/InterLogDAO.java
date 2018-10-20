package com.mituo.logservice.dao;

import com.mituo.logservice.entity.InterLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface InterLogDAO extends ReactiveMongoRepository<InterLog,String> {
    Flux<InterLog> findByInterFaceTimeBetween(String StartDate, String endDate);
}
