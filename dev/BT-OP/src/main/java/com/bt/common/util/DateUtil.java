package com.bt.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: DateUtil 
* @Description: TODO(日期工具类) 
* @author Yuriy.Jiang
* @date 2016年6月28日 下午2:24:33 
*  
*/
public class DateUtil {
	/**
	 * 返回起始时间
	 */
	public final static String start ="start";
	/**
	 * 返回结束时间
	 */
	public final static String end ="end";
	
	/**
	* @Title: getMonth
	* @Description: TODO(获取月)
	* @param @param day
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String getMonth(Calendar day){
		return day.get(Calendar.YEAR) + "-" + (day.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 
	 * @Description: TODO(获取一个月前日期字符串)
	 * @param today
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年7月20日下午2:32:14
	 */
	public static String getBeforeMToString(Calendar today){
		today.add(Calendar.DATE, 1);//得到后一天
		today.add(Calendar.MONTH, -1);//得到前一个月
		return getCalendarToString(today);
	}
	
	/**
	 * 
	 * @Description: TODO(获取当前日期字符串)
	 * @param day
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年7月20日下午2:26:34
	 */
	public static String getCalendarToString(Calendar day){
		return day.get(Calendar.YEAR) + "-" + (day.get(Calendar.MONTH) + 1) + "-" + day.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @Description: TODO(获取昨天的日期)
	 * @param today
	 * @return
	 * @return: Calendar  
	 * @author Ian.Huang 
	 * @date 2016年7月21日下午3:20:50
	 */
	public static Calendar getYesterDay(){
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
		return today;
	}
	
	/**
	 * 获取当前周
	 * @param date
	 * @return
	 */
	 public static int getCurrentWeek(Date date) {
		 Calendar c=Calendar.getInstance();
		 c.setFirstDayOfWeek(Calendar.MONDAY);
		 c.setTime (date);
		 return c.get(Calendar.WEEK_OF_YEAR);
	}
	 
//	 /**
//	  * 获取前一周
//	  * @param date
//	  * @return
//	  */
//	 public static int getPreviousWeek(Date date) {
//		 Calendar c=Calendar.getInstance();
//		 c.setFirstDayOfWeek(Calendar.MONDAY);
//		 c.setTime (date);
//		 return c.get(Calendar.WEEK_OF_YEAR)-1;
//	 }
	/** 
	* @Title: getCurrentYear 
	* @Description: TODO(根据传入的时间，获取年份） 
	* @param @param date
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getCurrentYear(Date date){
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 return cal.get(Calendar.YEAR);
	 }


	public static int getYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	/** 
	* @Title: getMonth 
	* @Description: TODO(根据传入的时间，获取月份) 
	* @param @param date
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/** 
	* @Title: getMonth 
	* @Description: TODO(根据传入的时间，获取今天 ) 
	* @param @param date
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}
	
	/**
	 * 格式化时期
	 */
	public static String formatDate(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd");
		return da.format(date);
	 }
	
	public static String formatDateMmSs(Date date,String model){
		SimpleDateFormat da=new SimpleDateFormat(model);
		return da.format(date);
	 }
	
	public static String formatSS(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyyMMddHHmmss");
		return da.format(date);
	}
	
	/** 
	* @Title: formatyyyyMM_dd 
	* @Description: TODO(根据结算日期，返回结算时间段) 
	* @param @param dd
	* @param @return    设定文件 
	* @return Map<String,Date>    返回类型 
	* @throws 
	*/
	public static Map<String,Date> formatyyyyMM_dd(int dd){
		int yyyy = getCurrentYear(new Date());
		int MM = getMonth(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		Map<String, Date> returnMap = new HashMap<String,Date>();
		try {
			start = format.parse(yyyy+"-"+(MM)+"-"+(dd+1)+" 00:00:00");
			end = format.parse(yyyy+"-"+(MM+1)+"-"+dd+" 23:59:59");
			returnMap.put("start", start);
			returnMap.put("end", end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	public static String format(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return da.format(date);
	 }
	
	/**
	 * 获取一周前的起始时间和结束时间
	 * @return  Map 
	 * 			startday 	起始时间
	 * 			endday 		结束时间
	 */
	public static Map<String,Date> getLastWeekMondaySunDay(){ 
		Map<String,Date> map = new HashMap<String,Date>();
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		currentDate.add(Calendar.DATE, -7);
		map.put("start", currentDate.getTime());
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		map.put("end", currentDate.getTime());
		return map;
	}
	
	/**
	 * 获取两周前的起始时间和结束时间
	 * @return  Map 
	 * 			startday 	起始时间
	 * 			endday 		结束时间
	 */
	public static Map<String,Date> getLastLastWeekMondaySunDay(){
		Map<String,Date> map = new HashMap<String,Date>();
		//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		currentDate.add(Calendar.DATE, -14);
		map.put("start", currentDate.getTime());
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		map.put("end", currentDate.getTime());
		return map;
	}
	
	/**
	 * 获取当前周的起始时间和结束时间
	 * @return  Map 
	 * 			startday 	起始时间
	 * 			endday 		结束时间
	 */
	public static Map<String,Date> getCurrentWeekMondaySunDay(){ 
		Map<String,Date> map = new HashMap<String,Date>();
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		map.put("start", currentDate.getTime());
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		map.put("end", currentDate.getTime());
		return map;
	}
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
	public static Date StrToYMDHMS(String str) {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}
	
	
	public static Date StrToYMDHMSDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	

	public static Date StrToTime(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	/** 
	* @Title: isTime 
	* @Description: TODO(判断STR3是否在ST1和ST2之间) 
	* @param @param str1
	* @param @param str2
	* @param @param str3
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	public final static boolean isTime(String str1,String str2,String str3) {
		// 定义区间值
		Date dateAfter = DateUtil.StrToDate(str1);
		Date dateBefor = DateUtil.StrToDate(str2);
		// 接收要判断的Date
		Date time = DateUtil.StrToDate(str3);
		// 判断time是否在XX之后，并且 在XX之前
		if (time.getTime()>=dateAfter.getTime() && time.getTime()<=dateBefor.getTime()) {
			System.out.println(DateUtil.formatDate(time) + "在此区间");
			return true;
		}else{
			System.out.println(DateUtil.formatDate(time) + "不在此区间");
			return false;
		}
	}
	public static Date MoveDate(Date d,int timeInt) {
		  Calendar calendar = new GregorianCalendar();
	        calendar.setTime(d);
		        calendar.add(calendar.DATE,timeInt);
		   return calendar.getTime();
		}
	public static Date MoveTime(Date d,int timeInt) {
		  Calendar calendar = new GregorianCalendar();
	        calendar.setTime(d);
		        calendar.add(calendar.HOUR,timeInt);
		   return calendar.getTime();
		}
	public static Date MoveMin(Date d,int timeInt) {
		  Calendar calendar = new GregorianCalendar();
	        calendar.setTime(d);
		        calendar.add(calendar.MINUTE,timeInt);
		   return calendar.getTime();
		}
	
	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
		String date=format.format(new Date());
		return date;
	}
	
	
	/**
	 * 
	* @Title: checkData 
	* @Description: TODO(获取当前的时间，到毫秒) 
	* @param @param dateStr
	* @param @return    设定文件 
	* @author likun   
	* @return boolean    返回类型 
	* @throws
	 */
	public static String getData(){
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		 String today=format.format(new Date());
		 return today;
	}
	
	public static long time(String s_time,String e_time){
		if (null!=s_time && !s_time.equals("") && null!=e_time && !e_time.equals("")) {
			if (s_time.length()!=10) {
				Date a = StrToTime(s_time);
				Date b = StrToTime(e_time);
				return b.getTime()-a.getTime();
			}else{
				Date a = StrToDate(s_time);
				Date b = StrToDate(e_time);
				return b.getTime()-a.getTime();
			}
			
		}
		return 0;
	}
	
	public static boolean isValidDate(String str) {
	      boolean convertSuccess=true;
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	       try {
	          format.setLenient(false);
	          format.parse(str);
	       } catch (ParseException e) {
	           convertSuccess=false;
	       } 
	       return convertSuccess;
	}
	
	public static void main(String[] args) {
		System.out.println(isValidDate("2018-03-30"));
	}
	
}
