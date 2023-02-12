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
}
