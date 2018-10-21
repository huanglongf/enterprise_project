package com.bt.lmis.balance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** 
 * @ClassName: DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月4日 下午2:58:36 
 * 
 */
public class DateUtil {
	
	private static final String YMD="yyyy-MM-dd";
	private static final String YMD_HMS="yyyy-MM-dd HH:mm:ss";
	private static final String YMD_HMS_S="yyyy-MM-dd HH:mm:ss SSS";
	
	/**
	 * @Title: timestrapStr
	 * @Description: TODO()
	 * @param formt
	 * @param calendar
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月4日 下午3:15:04
	 */
	public static String timestrapStr(String formt, Calendar calendar) {
		return new SimpleDateFormat(formt).format(calendar.getTime());
		
	}
	
	/**
	 * @Title: presentTimestrapStr
	 * @Description: TODO()
	 * @param format
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月4日 下午3:17:52
	 */
	public static String presentTimestrapStr(String format) {
		return timestrapStr(format, Calendar.getInstance());
		
	}
	
	/**
	 * @Title: presentTimestrapStrYMDHMSS
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月4日 下午3:26:57
	 */
	public static String presentTimestrapStrYMDHMSS() {
		return timestrapStr(YMD_HMS_S, Calendar.getInstance());
		
	}
	/**
	 * @Title: presentTimestrapStrYMDHMS
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月4日 下午3:27:03
	 */
	public static String presentTimestrapStrYMDHMS() {
		return timestrapStr(YMD_HMS, Calendar.getInstance());
		
	}
	/**
	 * @Title: presentTimestrapStrYMD
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月4日 下午3:27:09
	 */
	public static String presentTimestrapStrYMD() {
		return timestrapStr(YMD, Calendar.getInstance());
		
	}
	
	/**
	 * @Title: milliSecondsInDomain
	 * @Description: TODO(时间区间内差异毫秒)
	 * @param fromTimestrap
	 * @param toTimestrap
	 * @return: long
	 * @author: Ian.Huang
	 * @date: 2017年5月5日 上午11:41:07
	 */
	public static long milliSecondsInDomain(Calendar fromTimestrap, Calendar toTimestrap) {
		return toTimestrap.getTimeInMillis()-fromTimestrap.getTimeInMillis();
		
	}
	
	/**
	 * @Title: milliSecondsInDomain
	 * @Description: TODO(重载：时间区间内差异毫秒)
	 * @param from
	 * @param to
	 * @throws ParseException
	 * @return: long
	 * @author: Ian.Huang
	 * @date: 2017年5月5日 上午11:39:46
	 */
	public static long milliSecondsInDomain(String formt, String from, String to) throws ParseException {
		Calendar fromTimestrap=Calendar.getInstance();
		fromTimestrap.setTime(new SimpleDateFormat(formt).parse(from));
		Calendar toTimestrap=Calendar.getInstance();
		toTimestrap.setTime(new SimpleDateFormat(formt).parse(to));
		return milliSecondsInDomain(fromTimestrap, toTimestrap);
		
	}
	
	/**
	 * @Title: milliSecondsInDomainYMDHMS
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param from
	 * @param to
	 * @throws ParseException
	 * @return: long
	 * @author: Ian.Huang
	 * @date: 2017年5月8日 下午1:09:57
	 */
	public static long milliSecondsInDomainYMDHMSS(String from, String to) throws ParseException {
		return milliSecondsInDomain(YMD_HMS_S, from, to);
		
	}
	
	/**
	 * @Title: milliSecondsInDomainYMDHMS
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param from
	 * @param to
	 * @throws ParseException
	 * @return: long
	 * @author: Ian.Huang
	 * @date: 2017年5月8日 下午1:10:01
	 */
	public static long milliSecondsInDomainYMDHMS(String from, String to) throws ParseException {
		return milliSecondsInDomain(YMD_HMS, from, to);
		
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.presentTimestrapStrYMD());
		System.out.println(DateUtil.presentTimestrapStrYMDHMS());
		System.out.println(DateUtil.presentTimestrapStrYMDHMSS());
	}
	
}