package com.bt.utils;

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
	
	public final static String yyyy_MM_dd ="yyyy-MM-dd";
	
	/**
	 * @Title: getPastDate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param past
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:52:56
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());  
   }
	
	/**
	 * 
	 * @Description: TODO(判断是否为闰年)
	 * @param year
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年10月24日上午11:45:47
	 */
	public static Boolean judgeLeapYearOrNot(Integer year){
		// 第一步，判断年份是否被400整除，能的话，就是闰年。比如1600、2000、2400年是闰年。
		// 第二步，在第一步不成立的基础上，判断年份能否被100整除，如果是，则不是闰年。比如1900、2100、2200年不是闰年。
		// 第三步，在第二步不成立的基础上，判断年份能否被4整除，如果是，则是闰年。比如1996、2004、2008年是闰年。
		// 第四步，在第三步不成立的基础上，则不是闰年。比如1997、2001、2002年不是闰年。
		if(year%4 == 0 && year%100 != 0 && year%400 == 0){
			// 闰年
			return true;
		}
		// 不是闰年
		return false;
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
	
	/**
	 * 
	 * @Description: TODO(根据结算日划定结算周期)
	 * @param settle_date
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @throws ParseException 
	 * @date 2016年10月24日上午11:15:43
	 */
	public static Map<String, Object> getBalanceCycle(String balance_ym, Integer settle_date) throws ParseException{
		// 结算周期
		Map<String, Object> balance_cycle= new HashMap<String, Object>();
		// 获取结算年份
		Integer balance_year= Integer.parseInt(balance_ym.substring(0, balance_ym.indexOf("-")));
		// 获取结算月份
		Integer balance_month= Integer.parseInt(balance_ym.substring(balance_ym.indexOf("-") + 1));
		if(balance_month== 2){
			// 2月
			if(judgeLeapYearOrNot(balance_year)){
				// 闰年
				if(settle_date == 30 || settle_date == 31) {
					settle_date = 29;
				}
			} else {
				// 不是闰年
				if(settle_date == 29 || settle_date == 30 || settle_date == 31) {
					settle_date = 28;
					
				}
				
			}
		} else {
			// 2月外
			switch(balance_month){
			// 所有的小月需要做判断
			case 4:;
			case 6:;
			case 9:;
			case 11:
				if(settle_date == 31){
					settle_date = 30;
					
				};
				break;
				
			}
			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(balance_ym + "-" + settle_date);
		Calendar balance_date = Calendar.getInstance();
		balance_date.setTime(date);
		balance_cycle.put("balance_end_date", getCalendarToString(balance_date));
		balance_cycle.put("balance_start_date", getBeforeMToString(balance_date));
		return balance_cycle;
		
	}
	
	/**
	 * 
	 * @Description: TODO(判断合同当前日期是否需要汇总)
	 * @param settle_date
	 * @return
	 * @return: boolean  
	 * @author Ian.Huang 
	 * @date 2016年10月1日下午4:51:40
	 */
	public static boolean judgeSummaryOrNot(Integer settle_date) {
		// 获取今天的日期
		Calendar today = Calendar.getInstance();
		if(today.get(Calendar.DAY_OF_MONTH) == 1){
			// 当前日期为1号
			if(getYesterDay().get(Calendar.MONTH) == 1){
				// 上月月份为2月
				if(settle_date == 28 || settle_date == 29 || settle_date == 30 || settle_date == 31){
					return true;
				}
			} else {
				// 上月月份非2月
				if(settle_date == 30 || settle_date == 31){
					return true;
				}
			}
		} else {
			// 当前日期不为1号
			if(settle_date == getYesterDay().get(Calendar.DAY_OF_MONTH)){
				// 昨天日与结算日相等即可
				return true;
			}
		}
		return false;
	}
	
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
	public static String getCalendarToString(Calendar day) {
		return day.get(Calendar.YEAR) + "-" + (day.get(Calendar.MONTH) + 1) + "-" + day.get(Calendar.DAY_OF_MONTH);
		
	}
	
	/**
	 * 
	 * @Description: TODO(解决格式问题)
	 * @param calendar
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月9日上午11:32:56
	 */
	public static String getCalendarToStr(Calendar calendar) {
		Integer year = calendar.get(Calendar.YEAR);
		Integer month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = null;
		if(month < 10) {
			monthStr = "0" + month;
			
		} else {
			monthStr = month + "";
			
		}
		Integer day= calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = null;
		if(day < 10) {
			dayStr = "0" + day;
			
		} else {
			dayStr = day + "";
			
		}
		return year + "-" + monthStr + "-" + dayStr;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param dateStr
	 * @throws ParseException
	 * @return: Calendar  
	 * @author Ian.Huang 
	 * @date 2017年1月9日上午11:26:04
	 */
	public static Calendar getStrToCalendar(String dateStr) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
		return calendar;
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取昨天的日期)
	 * @param today
	 * @return: Calendar  
	 * @author Ian.Huang 
	 * @date 2016年7月21日下午3:20:50
	 */
	public static Calendar getYesterDay(){
		Calendar today= Calendar.getInstance();
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
	public static String formatDate2(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(CommonUtils.checkExistOrNot(date)){
			return da.format(date);
		}else{
			return null;
		}
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

	/** 
	* @Title: formatNowStartAndEnd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<String,Date>    返回类型 
	* @throws 
	*/
	public static Map<String, String> formatNowStartAndEnd(){
		Map<String, String> returnMap = new HashMap<String,String>();
		returnMap.put("start", DateUtil.getCalendarToString(getYesterDay())+" 00:00:00");
		returnMap.put("end", DateUtil.getCalendarToString(getYesterDay())+" 23:59:59");
		return returnMap;
	}
	
	public static String format(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return da.format(date);
	 }
	
	public static String formatS(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		return da.format(date);
	 }
	
	public static String format(Date date,String format){
		SimpleDateFormat da=new SimpleDateFormat(format);
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
	

	public static Date StrToTime(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	
	//2016/1/1 13:00:00
	public static Date StrToTime_other(String str) {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(StrToTime_other("2016/1/1 13:00:00"));
	}

	public static Date StrToTime59(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
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
	public final static boolean isTime(String str1, String str2, String str3) {
		// 定义区间值
		Date dateAfter= DateUtil.StrToDate(str1);
		Date dateBefor= DateUtil.StrToDate(str2);
		// 接收要判断的Date
		Date time= DateUtil.StrToDate(str3);
		// 判断time是否在XX之后，并且 在XX之前
		if(time.getTime()>= dateAfter.getTime() && time.getTime()<= dateBefor.getTime()) {
			System.out.println(DateUtil.formatDate(time) + "在此区间");
			return true;
			
		} else {
			System.out.println(DateUtil.formatDate(time) + "不在此区间");
			return false;
			
		}
		
	}
	
	public static  String getBalanceCycleFmt(String balance_ym, Integer settle_date) throws ParseException{
		// 获取结算年份
		Integer balance_year= Integer.parseInt(balance_ym.substring(0, balance_ym.indexOf("-")));
		// 获取结算月份
		Integer balance_month= Integer.parseInt(balance_ym.substring(balance_ym.indexOf("-") + 1));
		if(balance_month== 2){
			// 2月
			if(judgeLeapYearOrNot(balance_year)){
				// 闰年
				if(settle_date == 30 || settle_date == 31) {
					settle_date = 29;
				}
			} else {
				// 不是闰年
				if(settle_date == 29 || settle_date == 30 || settle_date == 31) {
					settle_date = 28;
				}
			}
		} else {
			// 2月外
			switch(balance_month){
			// 所有的小月需要做判断
			case 4:;
			case 6:;
			case 9:;
			case 11:
				if(settle_date == 31){
					settle_date = 30;
				};
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(balance_ym + "-" + settle_date);
		Calendar balance_date = Calendar.getInstance();
		balance_date.setTime(date);
		return getCalendarToString(balance_date)+"_"+getBeforeMToString(balance_date);
	}
	/***
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public  static  String getFmtDate(String date){
		String year = date.split("-")[0];
		String mouth = date.split("-")[1]; 
		String day = date.split("-")[2];
        if(mouth.length()==1){
        	mouth = "0"+mouth;
		}
        if(day.length()==1){
			day = "0"+day;
		}
        return year+"-"+mouth+"-"+day;		
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
	
	public static int getDaySub(String beginDateStr,String endDateStr){
		 int day=0;
	        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
	        java.util.Date beginDate;
	        java.util.Date endDate;
	        try
	        {
	            beginDate = format.parse(beginDateStr);
	            endDate= format.parse(endDateStr);    
	            day=(int) ((endDate.getTime()-beginDate.getTime())/(24*60*60*1000));    
	        } catch (ParseException e)
	        {
	            e.printStackTrace();
	        }   
	        return day;
	}
}
