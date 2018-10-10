package com.mituo.logservice.service;


import com.mituo.logservice.dao.InterLogDao;
import com.mituo.logservice.entity.InterLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterLogService {
    @Autowired
    private InterLogDao interLogDao;

    public InterLog findByNumId(Integer numId){
        return interLogDao.findByNumId(numId);
    }
}
