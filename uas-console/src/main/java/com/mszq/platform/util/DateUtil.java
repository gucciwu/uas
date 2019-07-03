package com.mszq.platform.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //private static final Logger logger = Logger.getLogger(DateConverter.class);

    public static String getLastDayOfMonth(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }
    
    public static Date getFirstDayOfSeason(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        switch (month) {
        case Calendar.JANUARY:
        case Calendar.FEBRUARY:
        case Calendar.MARCH:
            month = Calendar.JANUARY;
            break;
        case Calendar.APRIL:
        case Calendar.MAY:
        case Calendar.JUNE:
            month = Calendar.APRIL;
            break;
        case Calendar.JULY:
        case Calendar.AUGUST:
        case Calendar.SEPTEMBER:
            month = Calendar.JULY;
            break;
        case Calendar.OCTOBER:
        case Calendar.NOVEMBER:
        case Calendar.DECEMBER:
            month = Calendar.OCTOBER;
            break;
        default:
            break;
        }
        // 设置月份
        cal.set(Calendar.MONTH, month);
        // 获取某月最大天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.getTime();
    }
    
    public static String getLastDayOfSeason(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        switch (month) {
        case Calendar.JANUARY:
        case Calendar.FEBRUARY:
        case Calendar.MARCH:
            month = Calendar.MARCH;
            break;
        case Calendar.APRIL:
        case Calendar.MAY:
        case Calendar.JUNE:
            month = Calendar.JUNE;
            break;
        case Calendar.JULY:
        case Calendar.AUGUST:
        case Calendar.SEPTEMBER:
            month = Calendar.SEPTEMBER;
            break;
        case Calendar.OCTOBER:
        case Calendar.NOVEMBER:
        case Calendar.DECEMBER:
            month = Calendar.DECEMBER;
            break;
        default:
            break;
        }
        // 设置月份
        cal.set(Calendar.MONTH, month);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
        case Calendar.JANUARY:
        case Calendar.FEBRUARY:
        case Calendar.MARCH:
            season = 1;
            break;
        case Calendar.APRIL:
        case Calendar.MAY:
        case Calendar.JUNE:
            season = 2;
            break;
        case Calendar.JULY:
        case Calendar.AUGUST:
        case Calendar.SEPTEMBER:
            season = 3;
            break;
        case Calendar.OCTOBER:
        case Calendar.NOVEMBER:
        case Calendar.DECEMBER:
            season = 4;
            break;
        default:
            break;
        }
        return season;
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getCron(Date date) {
        return Constants.SYS_CRON_TIME + getDay(date) + " " + getMonth(date) + " ? " + getYear(date);
    }

    public static Date getSpecifiedDayByDay(Date date, int dayOffset){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dayOffset);
        return cal.getTime();
    }
    public static Date getSpecifiedDayByMonth(Date date, int monthOffset){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthOffset);
        return cal.getTime();
    }
    
    public static boolean equals(Date d1, Date d2) {
        if (d1 == d2) {
            return true;
        }
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.compareTo(d2) == 0;
    }

    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String dateToStrDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PATTERN_DATE);
        return simpleDateFormat.format(date);
    }

    public static String dateToStrTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.PATTERN_DATETIME);
        return simpleDateFormat.format(date);
    }

    public static Date strToDate(String str,String format) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    public static Date getDate(Date date,String format) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sdf.format(date));
    }
}
