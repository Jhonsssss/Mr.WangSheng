package com.statistical.time.tool;

import java.text.DecimalFormat;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class StringUtil {
    /**
     * return if str is empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTrimedString(String s) {
        return trim(s);
    }

    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    public static boolean equals(String str1, String str2) {
        return str1 == str2 || equalsNotNull(str1, str2);
    }

    public static boolean equalsNotNull(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }


    public static String int2chineseNum(int src) {
        final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String unit[] = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String dst = "";
        int count = 0;
        while(src > 0) {
            dst = (num[src % 10] + unit[count]) + dst;
            src = src / 10;
            count++;
        }
        return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }

    public static String getweekStr(int week) {
        String  text = "星期一";
       switch (week){
           case  1:
               text = "星期一";
               break;

           case  2:
               text = "星期二";
               break;

           case  3:
               text = "星期三";
               break;

           case  4:
               text = "星期四";
               break;

           case  5:
               text = "星期五";
               break;

           case  6:
               text = "星期六";
               break;

           case  0:
               text = "星期日";
               break;

       }

       return  text;

    }

    public static String getMonthyStr(int month) {
        String  monthStr ="一月";
      switch (month){
          case 1:
              monthStr ="一月";
              break;

          case 2:
              monthStr ="二月";
              break;

          case 3:
              monthStr ="三月";
              break;

          case 4:
              monthStr ="四月";
              break;

          case 5:
              monthStr ="五月";
              break;

          case 6:
              monthStr ="六月";
              break;

          case 7:
              monthStr ="七月";
              break;

          case 8:
              monthStr ="八月";
              break;

          case 9:
              monthStr ="九月";
              break;

          case 10:
              monthStr ="十月";
              break;

          case 11:
              monthStr ="冬月";
              break;

          case 12:
              monthStr ="腊月";
              break;

      }

      return monthStr;

    }
}
