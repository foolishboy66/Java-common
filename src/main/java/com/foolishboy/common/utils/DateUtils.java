package com.foolishboy.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 线程安全的日期转换工具类
 * 
 * @author wang
 *
 */
public class DateUtils {

    private static final int INIT_VALUE = 0;
    private static final int MIN_DAY_OF_MONTH = 1;
    private static final int MAX_HOUR_VALUE = 23;
    private static final int MAX_MINUTE_VALUE = 59;
    private static final int MAX_SECOND_VALUE = 59;
    private static final int MAX_MILLI_SECOND_VALUE = 999;

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_DATE_TIME_FORMAT = "yyyy-MM-ddTHH:mm:ss";

    public static final String DATE_FORMAT_YYYY = "yyyy";
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";
    public static final String DATE_TIME_FORMAT_YYYYMMDD = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";

    public static final long SECOND_OF_MILLI_SECONDS = 1000;
    public static final long MINUTE_OF_MILLI_SECONDS = 60 * SECOND_OF_MILLI_SECONDS;
    public static final long HOUR_OF_MILLI_SECONDS = 60 * MINUTE_OF_MILLI_SECONDS;
    public static final long DAY_OF_MILLI_SECONDS = 24 * HOUR_OF_MILLI_SECONDS;
    public static final long YEAR_OF_MILLI_SECONDS = 365 * DAY_OF_MILLI_SECONDS;

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    private DateUtils() {}

    private static DateFormat getDateFormat() {

        DateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }

    private static Date addTime(final Date date, final int field, final int amount) {

        checkDateNotNull(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    private static Date minusTime(final Date date, final int field, final int amount) {

        return addTime(date, field, -amount);
    }

    private static void checkDateNotNull(final Date... dates) {

        for (Date date : dates) {
            if (date == null) {
                throw new IllegalArgumentException("The date must not be null");
            }
        }
    }

    /**
     * 日期转换为字符串格式化输出 2018-12-12 12:00:00
     * 
     * @param date 要格式化的date
     * @return 指定格式的字符串
     */
    public static String format(final Date date) {

        checkDateNotNull(date);
        return getDateFormat().format(date);
    }

    /**
     * 时间毫秒数转换为字符串格式化输出 2018-12-12 12:00:00
     * 
     * @param timeMills 要格式化的long型毫秒时间戳
     * @return 指定格式的字符串
     */
    public static String format(final long timeMills) {

        return format(new Date(timeMills));
    }

    /**
     * 日期转换为指定格式字符串输出
     * 
     * @param date 要格式化的日期
     * @param dateFormat 日期要格式化的格式
     * @return 指定格式的字符串
     */
    public static String format(final Date date, final String dateFormat) {

        checkDateNotNull(date);
        if (StringUtils.isBlank(dateFormat)) {
            return format(date);
        }
        DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间毫秒数转换为指定格式字符串输出
     * 
     * @param timeMills 要格式化的long型毫秒时间戳
     * @param dateFormat 日期要格式化的格式
     * @return 指定格式的字符串
     */
    public static String format(final long timeMills, final String dateFormat) {

        if (StringUtils.isBlank(dateFormat)) {
            return format(timeMills);
        }
        DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(new Date(timeMills));
    }

    /**
     * 形如2018-12-12 12:00:00格式的字符串转换为日期
     * 
     * @param dateStr 日期字符串
     * @return 解析后的日期date
     */
    public static Date parse(final String dateStr) {

        try {
            return getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期转换失败!");
        }
    }

    /**
     * 字符串按照指定格式转换为日期
     * 
     * @param dateStr 日期字符串
     * @param dateFormat 日期字符串的格式
     * @return 解析后的日期date
     */
    public static Date parse(final String dateStr, final String dateFormat) {

        if (StringUtils.isBlank(dateFormat)) {
            return parse(dateStr);
        }

        DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期转换失败!");
        }
    }

    /**
     * 日期加year年后的时间
     * 
     * @param date 要操作的日期
     * @param year 年
     * @return 新的日期
     */
    public static Date plusYears(final Date date, final int year) {

        return addTime(date, Calendar.YEAR, year);
    }

    /**
     * 日期加month月后的时间
     *
     * @param date 要操作的日期
     * @param month 月
     * @return 新的日期
     */
    public static Date plusMonths(final Date date, final int month) {

        return addTime(date, Calendar.MONTH, month);
    }

    /**
     * 日期加day天后的时间
     * 
     * @param date 要操作的日期
     * @param day 日
     * @return 新的日期
     */
    public static Date plusDays(final Date date, final int day) {

        return addTime(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加hour小时后的时间
     * 
     * @param date 要操作的日期
     * @param hour 小时
     * @return 新的日期
     */
    public static Date plusHours(final Date date, final int hour) {

        return addTime(date, Calendar.HOUR, hour);
    }

    /**
     * 日期加minute分后的时间
     * 
     * @param date 要操作的日期
     * @param minute 分钟
     * @return 新的日期
     */
    public static Date plusMinutes(final Date date, final int minute) {

        return addTime(date, Calendar.MINUTE, minute);
    }

    /**
     * 日期加second秒后的时间
     * 
     * @param date 要操作的日期
     * @param second 秒
     * @return 新的日期
     */
    public static Date plusSeconds(final Date date, final int second) {

        return addTime(date, Calendar.SECOND, second);
    }

    /**
     * 日期加millSecond毫秒后的时间
     * 
     * @param date 要操作的日期
     * @param milliSecond 毫秒
     * @return 新的日期
     */
    public static Date plusMilliSeconds(final Date date, final int milliSecond) {

        return addTime(date, Calendar.MILLISECOND, milliSecond);
    }

    /**
     * 日期减year年后的时间
     * 
     * @param date 要操作的日期
     * @param year 年
     * @return 新的日期
     */
    public static Date minusYears(final Date date, final int year) {

        return minusTime(date, Calendar.YEAR, year);
    }

    /**
     * 日期减month月后的时间
     * 
     * @param date 要操作的日期
     * @param month 月
     * @return 新的日期
     */
    public static Date minusMonths(final Date date, final int month) {

        return minusTime(date, Calendar.MONTH, month);
    }

    /**
     * 日期减day天后的时间
     * 
     * @param date 要操作的日期
     * @param day 日
     * @return 新的日期
     */
    public static Date minusDays(final Date date, final int day) {

        return minusTime(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期减hour小时后的时间
     * 
     * @param date 要操作的日期
     * @param hour 小时
     * @return 新的日期
     */
    public static Date minusHours(final Date date, final int hour) {

        return minusTime(date, Calendar.HOUR, hour);
    }

    /**
     * 日期减minute分后的时间
     * 
     * @param date 要操作的日期
     * @param minute 分钟
     * @return 新的日期
     */
    public static Date minusMinutes(final Date date, final int minute) {

        return minusTime(date, Calendar.MINUTE, minute);
    }

    /**
     * 日期减second秒后的时间
     * 
     * @param date 要操作的日期
     * @param second 秒
     * @return 新的日期
     */
    public static Date minusSeconds(final Date date, final int second) {

        return minusTime(date, Calendar.SECOND, second);
    }

    /**
     * 日期减millSecond毫秒后的时间
     * 
     * @param date 要操作的日期
     * @param milliSecond 毫秒
     * @return 新的日期
     */
    public static Date minusMilliSeconds(final Date date, final int milliSecond) {

        return minusTime(date, Calendar.MILLISECOND, milliSecond);
    }

    /**
     * 获取一天中的开始,如2018-12-12 00:00:00
     * 
     * @param date 要操作的日期
     * @return 一天中的0时0分0秒的时间
     */
    public static Date getStartOfDay(final Date date) {

        checkDateNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, INIT_VALUE);
        calendar.set(Calendar.MINUTE, INIT_VALUE);
        calendar.set(Calendar.HOUR_OF_DAY, INIT_VALUE);
        calendar.set(Calendar.MILLISECOND, INIT_VALUE);

        return calendar.getTime();
    }

    /**
     * 获取一月中的开始,如2018-12-01 00:00:00
     * 
     * @param date 要操作的日期
     * @return 一个月中1日0时0分0秒的时间
     */
    public static Date getStartOfMonth(final Date date) {

        checkDateNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, MIN_DAY_OF_MONTH);
        calendar.set(Calendar.MILLISECOND, INIT_VALUE);
        calendar.set(Calendar.SECOND, INIT_VALUE);
        calendar.set(Calendar.MINUTE, INIT_VALUE);
        calendar.set(Calendar.HOUR_OF_DAY, INIT_VALUE);

        return calendar.getTime();
    }

    /**
     * 获取一天中的结束,如2018-12-12 23:59:59
     * 
     * @param date 要操作的日期
     * @return 一天中的23时59分59秒的时间
     */
    public static Date getEndOfDay(final Date date) {

        checkDateNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, MAX_HOUR_VALUE);
        calendar.set(Calendar.SECOND, MAX_SECOND_VALUE);
        calendar.set(Calendar.MILLISECOND, MAX_MILLI_SECOND_VALUE);
        calendar.set(Calendar.MINUTE, MAX_MINUTE_VALUE);

        return calendar.getTime();
    }

    /**
     * 获取一月中的结束,如2018-12-31 23:59:59
     * 
     * @param date 要操作的日期
     * @return 一个月中最后一天23时59分59秒的时间
     */
    public static Date getEndOfMonth(final Date date) {

        checkDateNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, MIN_DAY_OF_MONTH);
        calendar.set(Calendar.HOUR_OF_DAY, MAX_HOUR_VALUE);
        calendar.set(Calendar.MINUTE, MAX_MINUTE_VALUE);
        calendar.set(Calendar.SECOND, MAX_SECOND_VALUE);
        calendar.set(Calendar.MILLISECOND, MAX_MILLI_SECOND_VALUE);

        return minusDays(plusMonths(calendar.getTime(), 1), 1);
    }

    /**
     * 获取日期为一周的哪一天
     * <p>
     * 1-7分别代表周一到周日
     * </p>
     * 
     * @param date 要操作的日期
     * @return 1-7 分别代表周一到周日
     */
    public static int getDayOfWeek(final Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        int[] weekdays = {7, 1, 2, 3, 4, 5, 6};
        if (weekday < 0) {
            weekday = 0;
        }

        return weekdays[weekday];
    }

    /**
     * 比较两个时间的大小,相等时返回0,date1>date2时返回1,date1<date2时返回-1
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return 0:两个时间相同，1:时间1在时间2之后，-1:时间1在时间2之前
     */
    public static int compareTo(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        long diff = date1.getTime() - date2.getTime();

        return (diff == 0) ? 0 : ((diff > 0) ? 1 : -1);
    }

    /**
     * 两个时间是否相同
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间相同，false:两个时间不同
     */
    public static boolean isSameTime(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        return compareTo(date1, date2) == 0;
    }

    /**
     * 两个时间是否同一年
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间同年，false:两个时间不同年
     */
    public static boolean isSameYear(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
            && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    /**
     * 两个时间是否同一个月
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间同年同月，false:两个时间不是同年同月
     */
    public static boolean isSameMonth(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
            && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
            && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);

    }

    /**
     * 两个时间是否同一天
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间同年同月同日，false:两个时间不是同年同月同日
     */
    public static boolean isSameDay(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
            && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
            && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);

    }

    /**
     * 两个时间是否同一小时
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间同年同月同日同小时，false:两个时间不是同年同月同日同小时
     */
    public static boolean isSameHour(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
            && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
            && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
            && calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 两个时间是否同一分钟
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return true:两个时间同年同月同日同小时，false:两个时间不是同年同月同日同小时
     */
    public static boolean isSameMinute(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
            && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
            && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
            && calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY)
            && calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE);
    }

    /**
     * 两个时间相差多少年(假设一年365天)
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return int 两个时间相差的年份
     */
    public static int yearsBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return (int)((date1.getTime() - date2.getTime()) / YEAR_OF_MILLI_SECONDS);
    }

    /**
     * 两个时间相差多少天
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return int 两个时间相差的天数
     */
    public static int daysBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return (int)((date1.getTime() - date2.getTime()) / DAY_OF_MILLI_SECONDS);
    }

    /**
     * 两个时间相差多少小时
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return int 两个时间相差的小时数
     */
    public static int hoursBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return (int)((date1.getTime() - date2.getTime()) / HOUR_OF_MILLI_SECONDS);
    }

    /**
     * 两个时间相差多少分钟
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return int 两个时间相差的分钟数
     */
    public static int minutesBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return (int)((date1.getTime() - date2.getTime()) / MINUTE_OF_MILLI_SECONDS);
    }

    /**
     * 两个时间相差多少秒
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return int 两个时间相差的秒数
     */
    public static int secondsBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return (int)((date1.getTime() - date2.getTime()) / SECOND_OF_MILLI_SECONDS);
    }

    /**
     * 两个时间相差多少毫秒
     * 
     * @param date1 日期1
     * @param date2 日期2
     * @return long 两个时间相差的毫秒数
     */
    public static long milliSecondsBetween(final Date date1, final Date date2) {

        checkDateNotNull(date1, date2);
        return date1.getTime() - date2.getTime();
    }

}
