package com.baozun.easytask.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	public static Map<String, String> getOvOvTimePointStr(Date beginTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

		Map<String, String> map = new HashMap<String, String>();

		Calendar expireDate = Calendar.getInstance();
		expireDate.setTime(beginTime);
		expireDate.set(Calendar.SECOND, 0);
		expireDate.set(Calendar.MINUTE, 0);
		expireDate.set(Calendar.MILLISECOND, 0);
		Date endTime = expireDate.getTime();

		expireDate.setTime(endTime);
		expireDate.set(Calendar.HOUR_OF_DAY, expireDate.get(Calendar.HOUR_OF_DAY) - 1);

		Date startTime = expireDate.getTime();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startTime);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.MILLISECOND, 0);
		map.put("startTimeStr", sdf.format(startTime));
		int year = startCal.get(Calendar.YEAR);// 获取年份
		int month = startCal.get(Calendar.MONTH);// 获取月份
		int day = startCal.get(Calendar.DATE);// 获取日
		int hour1 = startCal.get(Calendar.HOUR_OF_DAY);
		int hour2 = endCal.get(Calendar.HOUR_OF_DAY);
		StringBuffer sb = new StringBuffer();
		sb.append(year);
		sb.append(month + 1);
		sb.append(day);
		sb.append("-");
		sb.append(hour1);
		sb.append("点");
		sb.append("至");
		sb.append(hour2);
		sb.append("点数据");
		map.put("str", sb.toString());
		map.put("endTimeStr", sdf.format(endTime));

		return map;
	}

	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.err.println(sdf.format(new Date()));
		System.out.println(getSpecifiedDayBefore("2017-12-01"));
		System.out.println(getSpecifiedDayAfter("2017-12-01"));
	}
}
