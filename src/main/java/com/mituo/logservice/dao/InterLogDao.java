package com.mituo.logservice.dao;

import com.mituo.logservice.entity.InterLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterLogDao extends JpaRepository<InterLog,Integer> {
    InterLog findByNumId(Integer numId);
}
