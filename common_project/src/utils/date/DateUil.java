package utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUil  extends org.apache.commons.lang3.time.DateUtils {
	
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	
	
	/** yyyyMM */
	public final static String PATTERN_SHORTMONTH = "yyyyMM";
	/** yyyy-MM */
	public final static String PATTERN_YEAR_MONTH = "yyyy-MM";
	/** yyyy-MM-dd */
	public final static String PATTERN_DATE = "yyyy-MM-dd";
	/** yyyyMMdd */
	public final static String PATTERN_SHORTDATE = "yyyyMMdd";
	/** yyyy-MM-dd HH:mm:ss */
	public final static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.f */
	public final static String PATTERN_DATETIME_DOT = "yyyy-MM-dd HH:mm:ss.S";
	/** yyyyMMddHHmmss */
	public final static String PATTERN_SHORTDATETIME = "yyyyMMddHHmmss";
	/** yyyy-MM-dd HH:mm */
	public final static String PATTERN_DIVISION = "yyyy-MM-dd HH:mm";

	/**
	 *  "yyyy-MM-dd"
	 */
	public static SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE);
	
	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static SimpleDateFormat format1 = new SimpleDateFormat(PATTERN_DATETIME);
	
	/**
	 * "yyyy-MM-dd HH:mm"
	 */
	public static SimpleDateFormat format2 = new SimpleDateFormat(PATTERN_DIVISION);
	
	

	
	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}
	
	
	
	public static Timestamp getNowTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return date1>date2: 1 | date1<date2: -1 | date1==date2: 0
	 */
	public static int compareDate(Date date1, Date date2) throws Exception {
		if (null == date1 || null == date2) {
			throw new Exception("date1 and date2 can not be null");
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		if (year1 > year2) {
			return 1;
		} else if (year1 < year2) {
			return -1;
		} else {
			if (month1 > month2) {
				return 1;
			} else if (month1 < month2) {
				return -1;
			} else {
				if (day1 > day2) {
					return 1;
				} else if (day1 < day2) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * 获得两个日期相差天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDateDifference(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return 0;
		}

		try {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			int year1 = 0;
			int month1 = 0;
			int day1 = 0;

			c2.setTime(endDate);
			int year2 = c2.get(Calendar.YEAR);
			int month2 = c2.get(Calendar.MONTH);
			int day2 = c2.get(Calendar.DAY_OF_MONTH);

			int pointer = -1;
			int compareResult = compareDate(beginDate, endDate);
			switch (compareResult) {
			case 0:
				return 0;
			case -1:
				do {
					pointer++;
					c1.setTime(beginDate);
					c1.add(Calendar.DAY_OF_MONTH, pointer);
					year1 = c1.get(Calendar.YEAR);
					month1 = c1.get(Calendar.MONTH);
					day1 = c1.get(Calendar.DAY_OF_MONTH);
				} while (year1 != year2 || month1 != month2 || day1 != day2);
				return pointer;
			case 1:
				return 0;
			default:
				return 0;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期之间的天数差
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @author Bowen.Deng
	 * @creation 2015年9月1日 上午11:51:44
	 */
	public static int getDifferenceOfDays(Date start, Date end) {
		int differenceOfDays;
		if (start == null || end == null) {
			differenceOfDays = 0;
		} else {
			start = truncate(start, Calendar.DAY_OF_MONTH);
			end = truncate(end, Calendar.DAY_OF_MONTH);
			long startTime = start.getTime(), endTime = end.getTime();

			if (endTime < startTime) {
				differenceOfDays = 0;
			} else {
				differenceOfDays = (int) ((endTime - startTime) / (1000 * 3600 * 24)) + 1;
			}
		}
		return differenceOfDays;
	}

	public static void main(String[] args) throws Exception {
		
		/*System.out.println("获取是第多少天:" + ca.get(Calendar.DAY_OF_YEAR));//获取是第多少天

		System.out.println( (ca.get(Calendar.MONTH )+1) +"月----获取当前时间是一年的第几周：" + ca.get(Calendar.WEEK_OF_YEAR));//获取是第几周
		
		
		 
		System.out.println((ca.get(Calendar.MONTH )+1) +"月----获取当前月有几周：" + ca.getActualMaximum(Calendar.WEEK_OF_MONTH));
		System.out.println((ca.get(Calendar.MONTH )+1) + "月天数：" + ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		
		
		
		System.out.println((ca.get(Calendar.MONTH )+1) +"月----有几周：" + ca.getActualMaximum(Calendar.WEEK_OF_MONTH));
		System.out.println((ca.get(Calendar.MONTH )+1) + "月天数：" + ca.getActualMaximum(Calendar.DAY_OF_MONTH)); */
		
		
//		System.out.println( toNormalDate(lastMonthFirstDate(new Date()) ));
		
		
//		String time = "2017-04-12 11:51:30";
//		System.out.println(toShortDateTime(time));
		
		
//		Date tommrow =  DateUtils.getStartDate( DateUtils.addDays( new Date(),1));
//		Date date = DateUtils.addDays(tommrow, 1);
//		System.out.println( getStartDate(new Date()));
//		System.out.println( getFinallyDate( new Date()));
		
//		Date ab = new Date();
//		System.out.println( getStartDate(getFinallyDate( ab)));
		/*Calendar a =Calendar.getInstance();
		int nowYear =a.get(Calendar.YEAR);
		List<String> yearList = new ArrayList<String>();
		yearList.add(nowYear+"年");yearList.add((nowYear-1)+"年");yearList.add((nowYear-2)+"年");//近三年
		
		System.out.println(nowYear);*/
		/*Date start = format1.parse("2015-08-01 10:20:44");
		Date end = format1.parse("2015-09-01 10:33:44");
		System.out.println(getDifferenceOfDays(start, end));*/
	}
	public static HashMap<String,Integer> generateCurrentAndNextMonthWeekAndDay(String date) {
		HashMap<String, Integer> map = null;
		try {
			Calendar ca = Calendar.getInstance();//创建一个日期实例
			if(StringUtils.isNotEmpty(date)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ca.setTime(sdf.parse(date));
			}
			ca.setTime(new Date());//实例化一个日期
			ca.setFirstDayOfWeek(Calendar.SUNDAY); 
			
			int currentMonthWeekOfYear = ca.get(Calendar.WEEK_OF_YEAR);
			int weeksOfCurrentMonth = ca.getActualMaximum(Calendar.WEEK_OF_MONTH);
			int daysOfCurrentMonth = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
			int currentMonth = ca.get(Calendar.MONTH )+1;
			int dayOfCurrentMonth = ca.get(Calendar.DAY_OF_MONTH);
			ca.add(Calendar.MONTH, 1);//增加一个月   
			
			int nextMonth = ca.get(Calendar.MONTH )+1;
			int weeksOfNextMonth = ca.getActualMaximum(Calendar.WEEK_OF_MONTH);
			int daysOfNextMonth = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayOfNextMonth = ca.get(Calendar.DAY_OF_MONTH);
			
			int nextMonthEndWeekOfYear = currentMonthWeekOfYear + weeksOfCurrentMonth + weeksOfNextMonth;
			
			System.out.println("多少列:" + nextMonthEndWeekOfYear);
			
			map = new HashMap<String,Integer>();
			map.put("nextMonthEndWeekOfYear", nextMonthEndWeekOfYear);
			map.put("currentMonth", currentMonth);
			map.put("nextMonth",nextMonth);
			map.put("currentMonthWeekOfYear",currentMonthWeekOfYear);
			map.put("weeksOfCurrentMonth",weeksOfCurrentMonth);
			map.put("weeksOfNextMonth",weeksOfNextMonth);
			
			map.put("daysOfCurrentMonth",daysOfCurrentMonth);
			map.put("daysOfNextMonth",daysOfNextMonth);
			map.put("dayOfCurrentMonth",dayOfCurrentMonth);
			map.put("dayOfNextMonth",dayOfNextMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Date longToDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		Date date = cal.getTime();
		return date;
	}
	
	public static Date longToDatePlusHours(long time, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		Date date = cal.getTime();
		return date;
	}
	
	public static Date longToDatePlusminuts(long time, int minuts) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.MINUTE, minuts);
		Date date = cal.getTime();
		return date;
	}

	public static void printDate(Date date) {
		/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); */
		System.out.println(format1.format(date));
	}

	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		// printDate(cal.getTime());
		return cal.getTime();
	}

	public static Date nextDay(Date date) {
		long time = date.getTime() + 24 * 60 * 60 * 1000;
		return longToDate(time);
	}
	//获得前一天
	public static Date lastDay(Date date) {
		long time = date.getTime() - 24 * 60 * 60 * 1000;
		return longToDate(time);
	}

	/**
	 * yyyy-MM-dd to date
	 * 
	 * @param stringDate
	 * @return
	 */
	public static Date stringToDate(String stringDate) {
		if (StringUtils.isEmpty(stringDate))
			return null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = (Date) format.parse(stringDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获得shang个月一号
	 * 
	 * @return
	 */
	public static Date nextMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	/**
	 * 获得下个月一号
	 * 
	 * @return
	 */
	public static Date lastMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}

	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {
		/*
		 * int bDay=getDateField(begin,Calendar.DAY_OF_YEAR); int
		 * eDay=getDateField(end,Calendar.DAY_OF_YEAR); return (eDay-bDay)+;
		 */

		long time = getStartDate(end).getTime() - getStartDate(begin).getTime();
		int day = (int) (time / (24 * 60 * 60 * 1000));

		return day;
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	public static int getHour(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}



	
	//获得近三年的 下拉列表
	public static List<Integer> getLast3YearList(){
		Calendar a =Calendar.getInstance();
		int nowYear =a.get(Calendar.YEAR);
		List<Integer> yearList = new ArrayList<Integer>();
		yearList.add(nowYear);yearList.add((nowYear-1));yearList.add(nowYear-2);//近三年
		return yearList ;
	}
	//获得一年的12个月
	public static List<Integer> get12MonthPerYearList(){
		List<Integer> monthList = new ArrayList<Integer>();
		for(int i=1; i<=12 ;i++ ){
			monthList.add(i);
		}
		return monthList ;
	}
	
	/**
	 * 得到本月的最后一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMonthLastDay(Date date) {
		return getMonthLastDay(date, null);
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return pattern格式的字符串
	 */
	public static String getMonthLastDay(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (null==pattern) {
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}

	/** 计算出给定日期后n天的时间 */
	public static Date addDays(Date starttime, Integer days) {
		if (starttime == null || days == null) {
			return null;
		}
		// return new Date(new Long(starttime.getTime() + 86400000L*days)) ;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}
	
	/**
	 * 计算毫秒类型的时间距离现在的分钟差
	 * 
	 * @param millisTime
	 * @return 小于0：比现在早
	 */
	public static long getMinuteDifferenceToNow(Long millisTime) {
		if (millisTime == null) {
			return -1;
		}
		long currentMillis = System.currentTimeMillis();
		int diff = new Long((millisTime - currentMillis) / 1000l / 60)
				.intValue();
		return diff;
	}

	/**
	 * 计算两个Date类型的时间差，单位转换成秒
	 * 
	 * @param starttime
	 * @param endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getSecondDifference(Date starttime, Date endtime) {
		if (starttime == null || endtime == null) {
			return -1;
		}
		int diff = new Long((endtime.getTime() - starttime.getTime()) / 1000l)
				.intValue();
		return diff;
	}

	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串 */
	public static String toNormalDateTime(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME);
	}
	
	/** 返回yyyy-MM-dd HH:mm:ss格式的字符串 */
	public static String toNormalDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME);
	}
	
	// 3.24新增
	/** 返回yyyyMMddHHmmss格式的字符串 */
	public static String toShortDateTime(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATETIME);
	}

	/** 返回yyyyMMddHHmmss格式的字符串 */
	public static String toShortDateTime(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATETIME);
	}
	// yyyy-MM-dd HH:mm:ss 格式转换成 yyyy-MM-dd
	public static String toShortDateTime(String patternDatetime){
		String date = patternDatetime.substring(0,10).replaceAll("-", "");
		return date ;
	}
	
	//0525新增
	/** 返回yyyy-MM-dd格式的字符串 */
	public static String toNormalDate(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATE);
	}

	/** 返回yyyy-MM-dd格式的字符串 */
	public static String toNormalDate(Timestamp dateTime) {
		if (dateTime == null) {
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATE);
	}
	
	/** 
     * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date） 
     * @param protoDate 原来的时间（java.util.Date） 
     * @param dateOffset（向前移正数，向后移负数） 
     * @return 时间（java.util.Date） 
     */ 
    public static Date getOffsetMonthDate(Date protoDate,int monthOffset){
        Calendar cal = Calendar.getInstance();  
        cal.setTime(protoDate);  
//      cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);  //这种写法是错误的，这种偏移以30天为标准  
        cal.add(Calendar.MONTH, -monthOffset); //正确写法  
        return cal.getTime();  
    } 
    
    /**
	 * 得到本月的第一天
	 * 
	 * @return pattern格式的字符串
	 */
	public static String getMonthFirstDay(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}
	
}
