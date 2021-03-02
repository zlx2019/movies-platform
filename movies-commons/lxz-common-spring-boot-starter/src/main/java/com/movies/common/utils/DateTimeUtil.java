package com.movies.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间日期工具类
 * @author lx Zhang.
 * @date 2021/3/2 4:35 下午
 */
public class DateTimeUtil {

    // 获得上周一时间
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    // 获得本周一时间
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    // 获得下周一时间
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * @return java.time.LocalDate
     * @author L
     * @date 2020年8月24日 11:18:25
     * @Description 根据时间 获取本周开始时间和结束时间
     * @param: [today, isFirst true:开始时间  false:结束时间]
     */
    public static LocalDate getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst) {
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        DayOfWeek week = today.getDayOfWeek();
        int value = week.getValue();
        if (isFirst) {
            resDate = today.minusDays(value - 1);
        } else {
            resDate = today.plusDays(7 - value);
        }
        return resDate;
    }


    /**
     * @return java.time.LocalDate
     * @author L
     * @date 2020年8月24日 11:18:25
     * @Description 根据时间 获取本月开始时间和结束时间
     * @param: [today, isFirst true:开始时间  false:结束时间]
     */
    public static LocalDate getStartOrEndDayOfMonth(LocalDate today, Boolean isFirst) {
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        Month month = today.getMonth();
        int length = month.length(today.isLeapYear());
        if (isFirst) {
            resDate = LocalDate.of(today.getYear(), month, 1);
        } else {
            resDate = LocalDate.of(today.getYear(), month, length);
        }
        return resDate;
    }


    /**
     * 时间 ＋ 天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, long day) {
        // 得到指定日期的毫秒数
        long time = date.getTime();
        // 要加上的天数转换成毫秒数
        day = day * 24 * 60 * 60 * 1000;
        // 相加得到新的毫秒数
        time += day;
        // 将毫秒数转换成日期
        return new Date(time);
    }

    /**
     * 根据两个时间相减获取分钟数
     *
     * @param endTime
     * @param startTime
     * @return
     */
    public static long getMinBydifferDate(Date endTime, Date startTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = endTime.getTime() - startTime.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        return ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
    }


    public static String getSplitTimeToSMS(Date start, Date end) {
        long interval = (end.getTime() - start.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(interval);
    }

    public static Long convertTopicYearToLong(String yearName) {
        Long year = 0L;
        if (StringUtils.isNotBlank(yearName)) {
            if ("其它".equals(yearName)) {
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy");
                year = Long.valueOf(dtf2.format(LocalDate.now()));
            } else {
                year = Long.valueOf(yearName);
            }
        }
        return year;
    }

    /**
     * 日加一
     *
     * @param date
     * @return
     */
    public static Date dayAddFirst(Date date) {
        Calendar ct = Calendar.getInstance();
        ct.setTime(date);
        ct.add(Calendar.DATE, +1);
        return ct.getTime();
    }


}
