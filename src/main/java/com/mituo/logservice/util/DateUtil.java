package com.mituo.logservice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private Calendar calendar;
    private Date date;
    public DateUtil(){
        calendar=Calendar.getInstance();
    }

    public void setDate(int dayofmonth){
        calendar.add(Calendar.DAY_OF_MONTH,dayofmonth);
    }

    public Date getDateFromCalendar(int hourofday,int minute,int second,int millisecond){
        calendar.set(Calendar.HOUR_OF_DAY,hourofday);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millisecond);
        return calendar.getTime();
    }

    public Date getDateFromDate(String day,int hourofday,int minute,int second,int millisecond){
        DateFormat dayformat=new SimpleDateFormat("yyyyMMdd");
        try {
            Date dayDate=dayformat.parse(day);
            calendar.setTime(dayDate);
            calendar.set(Calendar.HOUR_OF_DAY,hourofday);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,second);
            calendar.set(Calendar.MILLISECOND,millisecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }


}
