package com.mituo.logservice.dao;

import com.mituo.logservice.entity.CmptFullLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CmptFullLogDao extends JpaRepository<CmptFullLog,String> {
    List<CmptFullLog> findByRequestTimeBetween(Date startDate, Date stopDate);
    CmptFullLog findByRequestTime(Date date);
}
