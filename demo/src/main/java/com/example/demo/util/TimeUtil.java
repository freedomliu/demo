package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间帮助类
 * @author liuxiangtao90
 *
 */
public final class TimeUtil {

	private final static Logger log = LoggerFactory.getLogger(TimeUtil.class);

	// 命名为日期格式最后一级 按照常用分为1 2 3...
	public static final String	DAY1="yyyy-MM-dd";
	public static final String  DAY2="yyyy/MM/dd";
	public static final String  DAY3="yyyy.MM.dd";
	public static final String	SECOND1="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 比较两个日期
	 * @param pattern 日期格式
	 * @param time1	
	 * @param time2
	 * @return 负数小于 0相等 正数大于
	 * @throws ParseException
	 */
	public static int compare(String pattern, String time1, String time2) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(time1).compareTo(format.parse(time2));
	}
	
	/**
	 * 和当前日期做比较
	 * @param pattern 日期格式
	 * @param time 时间
	 * @return 负数小于 0相等 正数大于
	 * @throws ParseException
	 */
	public static int compareToNow(String pattern, String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return  format.parse(time).compareTo(new Date());
	}
	
	/**
	 * 日期格式化
	 * @param pattern
	 * @param date
	 * @return 格式化
	 * @throws ParseException
	 */
	public static String format(String pattern, Object date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		}
		catch(Exception e) {
			log.error("{},{}","日期格式化异常",e);
			return null;
		}
	}
	
	/**
	 * 时间戳转字符串
	 * @param pattern 字符串格式
	 * @param timeStamp 时间戳
	 * @return 时间字符
	 */
	public static String timeStamp2Str(String pattern, long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(timeStamp*1000);
        return simpleDateFormat.format(date);
	}
	
	/**
	 * 时间字符串转时间戳
	 * @param data
	 * @param pattern
	 * @return 时间戳
	 */
	public static Integer str2timeStamp(String data, String pattern) {
		try {
		    long time = new SimpleDateFormat(pattern).parse(data).getTime();
			return Integer.valueOf(time/1000+"");
		} catch (ParseException e) {
			log.error("{},{}","日期格式化异常",e);
			return null;
		} 
	}
	
	/**
	 * 字符串转时间
	 * @author liuxiangtao  
	 * @date 2018年5月2日 下午7:51:36
	 * @param data
	 * @param pattern
	 * @return 日期
	 */
	public static Date str2Date(String data, String pattern) {
		try {
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		    return simpleDateFormat.parse(data);
		} catch (ParseException e) {
			log.error("{},{}","日期格式化异常",e);
			return null;
		} 
	}

	public static Date timeStamp2data(Long timeStamp) {
		return new Date(timeStamp);
	}
	
	/**
	 * 格式化当前日期
	 * @param pattern
	 * @return 日期字符串
	 */
	public static String now2Str(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        return simpleDateFormat.format(date);
	}

	/**
	 * 现在距离过去某时间的天数
	 * @author liuxiangtao  
	 * @date 2018年3月27日 下午4:03:46
	 * @param pattern
	 * @param timeStr
	 * @return 时间差
	 */
	public static long distanceNow(String pattern, String timeStr) {
		LocalDate startDate = LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
        LocalDate endDate = LocalDate.now();
        return ChronoUnit.DAYS.between(startDate, endDate);
	}
	
	/**
	 * 现在距离过去某时间天数
	 * @author liuxiangtao  
	 * @date 2018年4月27日 下午5:22:25
	 * @param future
	 * @return long
	 */
	public static long distanceNow(Date future) {
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime startDateTime = LocalDateTime.ofInstant(future.toInstant(), zone);
        LocalDateTime endDateTime = LocalDateTime.now();
        return ChronoUnit.DAYS.between(startDateTime, endDateTime);
	}
	
	/**
	 * 
	 * @Title: distanceNowAndOld   
	 * @Description: 距离现在天数 当天不算   
	 * @author Rong
	 * @date 2018年11月15日 上午11:36:25 
	 * @param future
	 * @return
	 * @throws ParseException      
	 * @return: int      
	 * @throws
	 */
	public static long distanceNowAndOld(Date future) throws ParseException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Date futureDate = sdf.parse(sdf.format(future));
        Date now = sdf.parse(sdf.format(new Date()));
        
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(futureDate);
		long day1 = aCalendar.getTimeInMillis();
		aCalendar.setTime(now);
		long day2 = aCalendar.getTimeInMillis();
		return (day2-day1)/(1000*3600*24);
	}
	/**
	 * 过去距离现在的小时数
	 * @author liuxiangtao  
	 * @date 2018年4月3日 上午10:17:56
	 *  
	 * @param future
	 * @return long
	 */
	public static long futureToNowH(Date future) {
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime endDateTime = LocalDateTime.ofInstant(future.toInstant(), zone);
	    LocalDateTime startDateTime = LocalDateTime.now();
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
	}

	/** 当前时间添加小时
	 * @Decription:
	 * @Author: liuxiangtao90
	 * @CreateDate: Created in 2018/12/29 14:53
	 * @param:
	 * @param hour
	 * @Return: java.util.Date
	 */
	public static Date addHour(long hour) {
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localTime= LocalDateTime.now().plusHours(hour);
		ZonedDateTime zdt = localTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	/** 字符串转date
	 * @Decription:
	 * @Author: liuxiangtao90
	 * @CreateDate: Created in 2018/12/29 14:52
	 * @param:
	 * @param date
	 * @param pattern
	 * @Return: java.util.Date
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		}
		catch (Exception e) {
			log.error("{},{}","日期格式化异常",e);
			return null;
		}
	}
}
