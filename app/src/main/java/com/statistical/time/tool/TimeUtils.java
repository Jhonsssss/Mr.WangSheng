package com.statistical.time.tool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());

        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeSecend(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());

        SimpleDateFormat format;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
