package com.mituo.logservice.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavePathUtil {

    public String getSavePath(String rootPath,Date date) {
        Calendar yesterdayCalendar = Calendar.getInstance();
        File rootPathFile = new File(rootPath);
        if (!rootPathFile.exists()) {
            rootPathFile.mkdirs();
        }
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
        DateFormat targetFormat = new SimpleDateFormat("yyyy_MM_dd");
        File yearFile = new File(rootPathFile.getAbsolutePath() + "/" + yearFormat.format(date));
        if (!yearFile.exists()) {
            yearFile.mkdirs();
        }

        File monthFile = new File(yearFile.getAbsolutePath() + "/" + monthFormat.format(date));
        if (!monthFile.exists()) {
            monthFile.mkdirs();
        }

        File targetFile = new File(monthFile.getAbsolutePath() +
                "/" + "scga_api_" + targetFormat.format(date) + ".log");
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile.getAbsolutePath();
    }
}
