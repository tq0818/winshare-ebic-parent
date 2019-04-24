package com.winshare.edu.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 
 * @描述: 对日期进行操作
 * @作者: benny.he
 * @版本: 1.0 .
 */
public class DateUtils {
	public final static int SECOND = 1000;
	public final static int MINUTE = SECOND * 60;
	public final static int HOUR = MINUTE * 60;
	public final static int DAY = HOUR * 24;
	public final static int DAYOFMIDMONTH = 15;
	public final static String COMMON_TIME_PATTERN = "HH:mm";
	public final static String COMMON_DATE_PATTERN = "yyyy-MM-dd";
	public final static String COMMON_DATETIME_PATTERN = COMMON_DATE_PATTERN + " " + COMMON_TIME_PATTERN;
	public final static String COMMON_DATETIME_PATTERN_HHMMSS = COMMON_DATE_PATTERN + " HH:mm:ss";

	private DateUtils() {
		// nothing
	}

	/**
	 * 将date转换成String
	 * 
	 * @return
	 */
	public static String dateToStr(Date date) {
		return DateUtils.dateToStr(date, null);
	}

	/**
	 * 将date转换成String
	 * 
	 * @return
	 */
	public static String dateToStr(Date date, String aMask) {
		String ret = null;
		String mask = aMask;
		if (StringUtils.isBlank(mask))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		if (date == null) {
			ret = "";
		} else {
			ret = sdf.format(date);
		}
		return ret;
	}

	/**
	 * 将String转换成Date
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String date) throws ParseException {
		return DateUtils.strToDate(date, null);
	}

	/**
	 * 将String转换成Date
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String date, String aMask) throws ParseException {
		Date ret = null;
		String mask = aMask;
		if (mask == null || "".equals(mask))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		ret = sdf.parse(date);
		return ret;
	}

	/**
	 * 获取当前时间点的日期对象
	 * 
	 * @return 日期对象
	 */
	public static Date getNow() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 得到year年month月day日的的0点0分0秒的日期对象
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return 日期对象
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到当天的0点0分0秒的日期
	 * 
	 * @return Date 日期对象
	 */
	public static Date getTodayZeroClock() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 比较两个start日期是否在end日期之前
	 * 
	 * @param start
	 *            日期
	 * @param end
	 *            日期
	 * @return boolean
	 */
	public static boolean isBefore(Date start, Date end) {
		return start.getTime() - end.getTime() < 0 ? true : false;
	}

	/**
	 * 比较两个start日期是否在end日期之前或者相等
	 * 
	 * @param start
	 *            日期
	 * @param end
	 *            日期
	 * @return boolean
	 */
	public static boolean isBeforeOrEqual(Date start, Date end) {
		return start.getTime() - end.getTime() <= 0 ? true : false;
	}

	/**
	 * 今天之前的日期
	 * 
	 * @return boolean
	 */
	public static boolean isBeforeToday(Date date) {
		Date todayZero = getTodayZeroClock();
		return isBefore(date, todayZero);
	}

	/**
	 * 今天或者今天之前的日期
	 * 
	 * @return boolean
	 */
	public static boolean isBeforeOrEqualToday(Date date) {
		Date todayZero = getTodayZeroClock();
		return isBeforeOrEqual(date, todayZero);
	}

	/**
	 * 该日期是否在上半月之内，即15日之前
	 * 
	 * @param date
	 *            待判断的日期
	 * @return boolean
	 */
	public static boolean isBeforeMidMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day <= DAYOFMIDMONTH ? true : false;
	}

	/**
	 * 取某个时间点前几个月的某个时间点
	 * 
	 * @param d
	 *            原日期
	 * @param count
	 *            几个月前
	 * @return 目标日期
	 */
	public static Date beforeMonths(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - count);
		return c.getTime();
	}

	/**
	 * 取某个时间点前几天的某个时间点
	 * 
	 * @param d
	 *            原日期
	 * @param count
	 *            天数
	 * @return 目标日期
	 */
	public static Date beforeDays(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.DAY_OF_MONTH) - count);
		return c.getTime();
	}

	/**
	 * 取当前日期的前几天的某个时间点
	 * 
	 * @param count
	 *            天数
	 * @return 目标日期
	 */
	public static Date beforeDays(int count) {
		return beforeDays(getNow(), count);
	}

	/**
	 * 取当前时间点前几个小时的日期对象
	 * 
	 * @param count
	 *            小时数
	 * @return 目标日期
	 */
	public static Date beforeHours(int count) {
		return beforeHours(getNow(), count);
	}

	/**
	 * 取某时间点前几个小时的日期对象
	 * 
	 * @param d
	 *            原日期
	 * @param count
	 *            小时数
	 * @return 目标日期
	 */
	public static Date beforeHours(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) - count);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: beforeYears
	 * @Description: 获取count年前的当前时间点
	 * @param d
	 * @param count
	 * @return
	 * @Return: Date
	 * @Throws:
	 * @Author: bieby
	 * @Date: 2013-12-6
	 */
	public static Date beforeYears(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - count);
		return c.getTime();
	}

	/**
	 * 取某个时间点前几分钟的某个时间点
	 * 
	 * @param d
	 * @param count
	 * @return
	 */
	public static Date beforeMinutes(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - count);
		return c.getTime();
	}

	/**
	 * 取某个时间点后几分钟的某个时间点
	 * 
	 * @param d
	 *            原日期
	 * @param count
	 *            分钟数
	 * @return 目标日期
	 */
	public static Date afterMinutes(Date d, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + count);
		return c.getTime();
	}

	/**
	 * 返回两个日期之间的毫秒数的差距，负数时取绝对值
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return 毫秒数
	 */
	public static long countMilliSecondsBetween(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			throw new IllegalArgumentException("参数d1或d2不能是null对象!");
		}
		return Math.abs(d1.getTime() - d2.getTime());
	}

	/**
	 * 获得两个日期之间相差的秒数
	 * 
	 * @param d1
	 *            日期1
	 * @param d2
	 *            日期2
	 * @return 秒数
	 */
	public static long countSecondsBetween(Date d1, Date d2) {
		return countMilliSecondsBetween(d1, d2) / SECOND;
	}

	/**
	 * 获得两个日期之间相差的分钟数
	 * 
	 * @param d1
	 * @param d2
	 * @return 分钟数
	 */
	public static long countMinutesBetween(Date d1, Date d2) {
		return countMilliSecondsBetween(d1, d2) / MINUTE;
	}

	/**
	 * 获得两个日期之间相差的小时数
	 * 
	 * @param d1
	 * @param d2
	 * @return 小时数
	 */
	public static long countHoursBetween(Date d1, Date d2) {
		return countMilliSecondsBetween(d1, d2) / HOUR;
	}

	/**
	 * 获得两个日期之间相差的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return 天数
	 */
	public static long countDaysBetween(Date d1, Date d2) {
		return countMilliSecondsBetween(d1, d2) / DAY;
	}

	public static Date getThisYearBeginning() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 今年之前的日期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isBeforeThisYear(Date date) {
		Date yearBeginning = getThisYearBeginning();
		return isBefore(date, yearBeginning);
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		return currentYear;
	}

	/**
	 * 比较两个日期间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}
		// 构建calendar对象
		Calendar calendar = Calendar.getInstance();
		int zone_offset = calendar.get(Calendar.ZONE_OFFSET);
		int dst_offset = calendar.get(Calendar.DST_OFFSET);
		long d1 = date1.getTime() + zone_offset + dst_offset;
		long d2 = date2.getTime() + zone_offset + dst_offset;
		int intDaysFirst = (int) (d1 / (60 * 60 * 1000 * 24)); // 60*60*1000
		int intDaysSecond = (int) (d2 / (60 * 60 * 1000 * 24));
		return intDaysFirst > intDaysSecond ? intDaysFirst - intDaysSecond : intDaysSecond - intDaysFirst;
	}

	/**
	 * 获取当前时间与指定时间的差值
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		StringBuffer time = new StringBuffer();
		Date date2 = new Date();
		long temp = date2.getTime() - date.getTime();
		long days = temp / 1000 / 3600 / 24; // 相差小时数
		if (days > 0) {
			time.append(days + "天");
		}
		long temp1 = temp % (1000 * 3600 * 24);
		long hours = temp1 / 1000 / 3600; // 相差小时数
		if (days > 0 || hours > 0) {
			time.append(hours + "小时");
		}
		long temp2 = temp1 % (1000 * 3600);
		long mins = temp2 / 1000 / 60; // 相差分钟数
		time.append(mins + "分钟");
		return time.toString();
	}

	/**
	 * 格式化日期，格式为yyyy-MM-dd
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化字串
	 */
	public static String formatDate(Date aDate) {
		return format(aDate, DateUtils.COMMON_DATE_PATTERN);
	}

	/**
	 * 格式化日期，格式为yyyy-MM-dd HH:mm
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化字串
	 */
	public static String formatDateTime(Date aDate) {
		return format(aDate, COMMON_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化字串
	 */
	public static String formatDateTimeByHhmmss(Date aDate) {
		return format(aDate, COMMON_DATETIME_PATTERN_HHMMSS);
	}

	/**
	 * 转换日期对象为字串
	 * 
	 * @param aDate
	 *            日期对象
	 * @param pattern
	 *            格式化样式，默认为yyyy-MM-dd
	 * @return
	 */
	public static String format(Date aDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(aDate);
	}

	/**
	 * 解析字串为日期对象，格式为pattern
	 * 
	 * @param pattern
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date parse(String strDate, String pattern) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(pattern);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 解析字串为日期对象，格式为yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		return parse(dateStr, COMMON_DATE_PATTERN);
	}

	/**
	 * 解析字串为日期对象，格式为yyyy-MM-dd HH:mm
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateStr) throws ParseException {
		return parse(dateStr, COMMON_DATETIME_PATTERN);
	}

	/**
	 * @Title: getCurrDate
	 * @Description: 得到当前时间 格式 yyyyMMddhhmm
	 * @return String
	 * @throws ParseException
	 * @Return: String
	 * @Throws:
	 * @Author: yl
	 * @Date: 2014-6-25
	 */
	public static String getCurrDate() throws ParseException {
		SimpleDateFormat formart = new SimpleDateFormat("yyyyMMddhhmm");
		return formart.format(new Date());
	}

	/**
	 * 得到当前日期字符串格式
	 * 
	 * @return
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}
}
