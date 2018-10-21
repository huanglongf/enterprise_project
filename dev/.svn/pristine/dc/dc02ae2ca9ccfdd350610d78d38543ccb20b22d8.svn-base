package com.bt.vims.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * 日期操作类
 * @author 刘庆强
 * @version V0.1
 * @date 2018.04.18
 *
 */
public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static final String YMD1 = "yyyy-MM-dd";
	public static final String YMD2 = "dd/MM/yy";
	public static final String YMD3 = "yyyyMMdd";
	public static final String YMD4 = "ddMMyyyy";
	public static final String YMD5 = "yyyy-MM";
	public static final String YMD6 = "HH";
	public static final String YMD_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD7 = "yyyyMMddHHmmss";
	public static final String YMD8 = "yyyy年MM月dd日";
	public static final String NUMBER_INT = "###";
	public static final String NUMBER_FLOAT = "####.##";
	/** 标准日期格式 */
	protected static final DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");

	public static Date formatDateFromDate(Date date, String format) {
		String dateStr = convertToString(date, format);
		Date convertDate = null;
		try {
			convertDate = convertFromString(dateStr, format);
		} catch (Exception e) {
			logger.error("formatDateFromDate()", e);
		}
		return convertDate;
	}

	/**
	 * Convert date type from String to Date
	 * 
	 * @param date
	 *            Sample:2016-10-12
	 * @return Date
	 */
	public static String convertToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String convertToString(Date date) {
		return convertToString(date, YMD_FULL);
	}

	public static Date trimDate(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);

		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);

		return rightNow.getTime();
	}

	public static Date getCurrentTrimDate() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());

		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
		rightNow.set(Calendar.MILLISECOND, 0);

		return rightNow.getTime();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static DateFormat getCnDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	@SuppressWarnings("finally")
	public static Date convertFromStringTwo(String date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null) {
			format = YMD_FULL;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date convertDate = null;

		try {
			convertDate = sdf.parse(date);
			return convertDate;
		} catch (ParseException e) {
			logger.error("convertFromStringTwo()", e);
		} finally {
			return convertDate;
		}
	}

	public static Date convertFromString(String date, String format) {
		return convertFromStringTwo(date, format);
	}

	public static Date convertFromString(Long timestamp) {
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp);
	}

	public static double normalMultiply(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(value1.toString());
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.multiply(b2).doubleValue();
	}

	public static double normalAdd(Double value1, Double value2) {
		BigDecimal b1 = new BigDecimal(value1.toString());
		BigDecimal b2 = new BigDecimal(value2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 加减日期
	 * 
	 * @param date
	 * @param dateCount
	 * @return
	 */
	public static Date plusOrReduceDate(Date date, int dateCount) {
		Date tmp = date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tmp);
		calendar.add(Calendar.DATE, dateCount);// 把日期往后增加一天.整数往后推,负数往前移动
		tmp = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return tmp;
	}
	/**
	 * 加减分钟
	 * 
	 * @param date
	 * @param dateCount
	 * @return
	 */
	public static Date plusOrReduceMinute(Date date, int minuteCount) {
		Date tmp = date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tmp);
		calendar.add(Calendar.MINUTE, minuteCount);// 把分钟往后增加一天.整数往后推,负数往前移动
		tmp = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return tmp;
	}

	/**
	 * 
	 */
	@SuppressWarnings("finally")
	public static Date stringToDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date time = null;
		try {
			time = sdf.parse(date);
		} catch (ParseException e) {
			logger.error("stringToDate()", e);
		} finally {
			return time;
		}
	}

	/**
	 * 将Calendar实例的时间调至当天的开始时刻，即0点0分0秒0毫秒。
	 * 
	 * @param calendar
	 *            要调整时间的Calendar实例。
	 * @return 调整后的Calendar实例(和输入参数calendar是同一对象)。
	 */
	public static Calendar toStart(Calendar calendar) {
		calendar.set(Calendar.AM_PM, Calendar.AM);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 0);
		return calendar;
	}

	/**
	 * 将Calendar实例的时间时刻调至当天的终止时刻，即23点59分59秒999毫秒。
	 * 
	 * @param calendar
	 *            要调整时间的Calendar实例。
	 * @return 调整后的Calendar实例(和输入参数calendar是同一对象)。
	 */
	public static Calendar toEnd(Calendar calendar) {
		calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}

	/**
	 * 将Date实例的时间调至当天的开始时刻，即0点0分0秒。
	 * 
	 * @param date
	 *            要调整时间的Date实例。
	 * @return 调整后的Date实例(和输入参数date是同一对象)。
	 */
	public static Date toStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		toStart(calendar);
		date.setTime(calendar.getTimeInMillis());
		return date;
	}

	/**
	 * 将Date实例的时间调至当天的终止时刻，即23点59分59秒。
	 * 
	 * @param date
	 *            要调整时间的Date实例。
	 * @return 调整后的Date实例(和输入参数date是同一对象)。
	 */
	public static Date toEnd(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			toEnd(calendar);
			date.setTime(calendar.getTimeInMillis());
			return date;
		} else {
			return null;
		}

	}

	public static Date parseDate(String date) throws ParseException {
		if (date == null || date.length() == 0) {
			return null;
		}
		synchronized (dateFormate) {
			return dateFormate.parse(date);
		}
	}

	/**
	 * 日期比较
	 * 
	 * @param curDate
	 *            当前日期
	 * @param offDate
	 *            截止日期
	 * @param days
	 *            天数
	 * @return
	 */
	public static boolean compareDate(Date curDate, Date offDate, int days) {
		Date compareDate = plusOrReduceDate(curDate, days);
		if (compareDate.getTime() > offDate.getTime()) {
			return false;
		}
		return true;
	}

	public static List<Date> tradeDate(String start, String end) {
		// String start = "20161116";
		// String end = "20170101";
		Date startDate = stringToDate(start, YMD3);
		Date endDate = stringToDate(end, YMD3);

		Calendar startC = Calendar.getInstance();
		startC.setTime(startDate);
		List<Date> date = new ArrayList<Date>();
		while (startC.getTime().before(endDate)) {
			startC.add(Calendar.DATE, 1);
			if (startC.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& startC.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				date.add(startC.getTime());
			}
		}
		return date;
	}

}
