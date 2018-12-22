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
public class DateUtil {

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

	private DateUtil() {
	}

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
	 * @param date
	 * @return
	 */
	public static String format(final Date date) {

		checkDateNotNull(date);
		return getDateFormat().format(date);
	}

	/**
	 * 时间毫秒数转换为字符串格式化输出 2018-12-12 12:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static String format(final long timeMills) {

		return format(new Date(timeMills));
	}

	/**
	 * 日期转换为指定格式字符串输出
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String format(final Date date, final String dateFormat) {

		checkDateNotNull(date);
		if (StringUtil.isBlank(dateFormat)) {
			return format(date);
		}
		DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date);
	}

	/**
	 * 时间毫秒数转换为指定格式字符串输出
	 * 
	 * @param timeMills
	 * @param dateFormat
	 * @return
	 */
	public static String format(final long timeMills, final String dateFormat) {

		if (StringUtil.isBlank(dateFormat)) {
			return format(timeMills);
		}
		DateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(new Date(timeMills));
	}

	/**
	 * 形如2018-12-12 12:00:00格式的字符串转换为日期
	 * 
	 * @param dateStr
	 * @return
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
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Date parse(final String dateStr, final String dateFormat) {

		if (StringUtil.isBlank(dateFormat)) {
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
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date plusYears(final Date date, final int year) {

		return addTime(date, Calendar.YEAR, year);
	}

	/**
	 * 日期加month月后的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date plusMonths(final Date date, final int month) {

		return addTime(date, Calendar.MONTH, month);
	}

	/**
	 * 日期加day天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date plusDays(final Date date, final int day) {

		return addTime(date, Calendar.DAY_OF_YEAR, day);
	}

	/**
	 * 日期加hour小时后的时间
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date plusHours(final Date date, final int hour) {

		return addTime(date, Calendar.HOUR, hour);
	}

	/**
	 * 日期加minute分后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date plusMinutes(final Date date, final int minute) {

		return addTime(date, Calendar.MINUTE, minute);
	}

	/**
	 * 日期加second秒后的时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date plusSeconds(final Date date, final int second) {

		return addTime(date, Calendar.SECOND, second);
	}

	/**
	 * 日期加millSecond毫秒后的时间
	 * 
	 * @param date
	 * @param milliSecond
	 * @return
	 */
	public static Date plusMilliSeconds(final Date date, final int milliSecond) {

		return addTime(date, Calendar.MILLISECOND, milliSecond);
	}

	/**
	 * 日期减year年后的时间
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date minusYears(final Date date, final int year) {

		return minusTime(date, Calendar.YEAR, year);
	}

	/**
	 * 日期减month月后的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date minusMonths(final Date date, final int month) {

		return minusTime(date, Calendar.MONTH, month);
	}

	/**
	 * 日期减day天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date minusDays(final Date date, final int day) {

		return minusTime(date, Calendar.DAY_OF_YEAR, day);
	}

	/**
	 * 日期减hour小时后的时间
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date minusHours(final Date date, final int hour) {

		return minusTime(date, Calendar.HOUR, hour);
	}

	/**
	 * 日期减minute分后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date minusMinutes(final Date date, final int minute) {

		return minusTime(date, Calendar.MINUTE, minute);
	}

	/**
	 * 日期减second秒后的时间
	 * 
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date minusSeconds(final Date date, final int second) {

		return minusTime(date, Calendar.SECOND, second);
	}

	/**
	 * 日期减millSecond毫秒后的时间
	 * 
	 * @param date
	 * @param milliSecond
	 * @return
	 */
	public static Date minusMilliSeconds(final Date date, final int milliSecond) {

		return minusTime(date, Calendar.MILLISECOND, milliSecond);
	}

	/**
	 * 获取一天中的开始,如2018-12-12 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartOfDay(final Date date) {

		checkDateNotNull(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, INIT_VALUE);
		calendar.set(Calendar.MINUTE, INIT_VALUE);
		calendar.set(Calendar.SECOND, INIT_VALUE);
		calendar.set(Calendar.MILLISECOND, INIT_VALUE);

		return calendar.getTime();
	}

	/**
	 * 获取一月中的开始,如2018-12-01 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartOfMonth(final Date date) {

		checkDateNotNull(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, MIN_DAY_OF_MONTH);
		calendar.set(Calendar.HOUR_OF_DAY, INIT_VALUE);
		calendar.set(Calendar.MINUTE, INIT_VALUE);
		calendar.set(Calendar.SECOND, INIT_VALUE);
		calendar.set(Calendar.MILLISECOND, INIT_VALUE);

		return calendar.getTime();
	}

	/**
	 * 获取一天中的结束,如2018-12-12 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(final Date date) {

		checkDateNotNull(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, MAX_HOUR_VALUE);
		calendar.set(Calendar.MINUTE, MAX_MINUTE_VALUE);
		calendar.set(Calendar.SECOND, MAX_SECOND_VALUE);
		calendar.set(Calendar.MILLISECOND, MAX_MILLI_SECOND_VALUE);

		return calendar.getTime();
	}

	/**
	 * 获取一月中的结束,如2018-12-31 23:59:59
	 * 
	 * @param date
	 * @return
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
	 * 比较两个时间的大小,相等时返回0,date1>date2时返回1,date1<date2时返回-1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareTo(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);

		long diff = date1.getTime() - date2.getTime();

		return (diff == 0) ? 0 : ((diff > 0) ? 1 : -1);
	}

	/**
	 * 两个时间是否相同
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameTime(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);

		return compareTo(date1, date2) == 0;
	}

	/**
	 * 两个时间是否同一年
	 * 
	 * @param date1
	 * @param date2
	 * @return
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
	 * @param date1
	 * @param date2
	 * @return
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
	 * @param date1
	 * @param date2
	 * @return
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
	 * @param date1
	 * @param date2
	 * @return
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
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameMinute(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
				&& calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
				&& calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY)
				&& calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE);
	}

	/**
	 * 两个时间相差多少年(假设一年365天)
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int yearsBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return (int) ((date1.getTime() - date2.getTime()) / YEAR_OF_MILLI_SECONDS);
	}

	/**
	 * 两个时间相差多少天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return (int) ((date1.getTime() - date2.getTime()) / DAY_OF_MILLI_SECONDS);
	}

	/**
	 * 两个时间相差多少小时
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int hoursBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return (int) ((date1.getTime() - date2.getTime()) / HOUR_OF_MILLI_SECONDS);
	}

	/**
	 * 两个时间相差多少分钟
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int minutesBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return (int) ((date1.getTime() - date2.getTime()) / MINUTE_OF_MILLI_SECONDS);
	}

	/**
	 * 两个时间相差多少秒
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int secondsBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return (int) ((date1.getTime() - date2.getTime()) / SECOND_OF_MILLI_SECONDS);
	}

	/**
	 * 两个时间相差多少毫秒
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long milliSecondsBetween(final Date date1, final Date date2) {

		checkDateNotNull(date1, date2);
		return date1.getTime() - date2.getTime();
	}

}
