package com.ccoins.coins.utils;

import java.time.LocalDateTime;

public class DateUtils {

    public static final String AUTO_DATE = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP";

    public static final String HH_MM = "HH:mm";

    public static final String DDMMYYYY_HHMM = "DD/MM/YYYY HH:mm";
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-DD HH:mm:ss";
    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    public static boolean isAfterLocalDateTime(LocalDateTime time, LocalDateTime start){
        return time.isAfter(start);
    }

    public static boolean isAfterNow(LocalDateTime time){
        return DateUtils.isAfterLocalDateTime(time,DateUtils.now());
    }

    public static boolean isBeforeLocalDateTime(LocalDateTime time, LocalDateTime start){
        return time.isBefore(start);
    }

    public static boolean isBeforeNow(LocalDateTime time){
        return DateUtils.isBeforeLocalDateTime(time,DateUtils.now());
    }
}
