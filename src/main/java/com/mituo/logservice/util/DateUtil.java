package com.mituo.logservice.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private Calendar calendar;
    private Date date;
    public DateUtil(){
        calendar=Calendar.getInstance();
    }

    public Date getDateFromCalendar(int dayofmonth,int hourofday,int minute,int second,int millisecond){
        calendar.add(Calendar.DAY_OF_MONTH,dayofmonth);
        calendar.set(Calendar.HOUR_OF_DAY,hourofday);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millisecond);
        return calendar.getTime();
    }


}
