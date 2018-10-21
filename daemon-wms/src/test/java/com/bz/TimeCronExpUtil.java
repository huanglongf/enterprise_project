package com.bz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 通过表达式获取任务计划时间列表
* 注意：最后生成的计划时间，会包含startDate,不会包含endDate
* 比如 
* 	startDate=2015-07-06 00:00:00
*  endDate=2015-07-07 00:00:00
*  如果每秒钟触发一次
*  结果返回的时间计划列表应该是从2015-07-06 00:00:00 到 2015-07-06 23:59:59秒
*/

public class TimeCronExpUtil {

	protected static final Logger logger = LoggerFactory.getLogger(TimeCronExpUtil.class);
	/**
	 * 通过表达式获取任务计划时间列表
	 * 注意：最后生成的计划时间，会包含startDate,不会包含endDate
	 * @param startDate
	 * @param endDate
	 * @param timeExp
	 * @return
	 */
	public static List<Date> makeTimePlan(Date startDate,Date endDate,String timeExp){
		List<Date> list=new ArrayList<Date>();
		try {
			//startDate有可能也是一个有效的定时触发时间，为了包含进去，则将startDate前一秒钟
			startDate.setTime(startDate.getTime()-1*1000);
			
			CronExpression ce=new CronExpression(timeExp);
			while(true){
				startDate=ce.getNextValidTimeAfter(startDate);
				//开始时间大于等于结束时间，则退出
				if(startDate.getTime()>=endDate.getTime()){
					break;
				}
				list.add(startDate);
			}
			
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}
	
	/**
	 * 获取下一次的触发,有可能包含当前startDate
	 * @param startDate
	 * @param timeExp
	 * @return
	 */
	public static Date findNextTrigger(Date startDate,String timeExp){
		
		try {
			//startDate有可能也是一个有效的定时触发时间，为了包含进去，则将startDate前一秒钟
			startDate.setTime(startDate.getTime()-1*1000);
			
			CronExpression ce=new CronExpression(timeExp);
			
								
			return ce.getNextValidTimeAfter(startDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			
		}
		
		return null;
	}
	
	public static List<Date> makeTimePlanForTomorrow(String timeExp){
		
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DATE, 1);
		
		Date startDate=calendar.getTime();
		
		calendar.add(Calendar.DATE, 1);
		
		Date endDate=calendar.getTime();
		
		return makeTimePlan(startDate,endDate,timeExp);
	}
	
	
	public static List<Date> makeTimePlanForToday(String timeExp){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date startDate=calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date endDate=calendar.getTime();
		return makeTimePlan(startDate,endDate,timeExp);
	}
	
	/**
	 * 检查是否正确的时间表达式
	 * @param timeExp
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkTimeExp(String timeExp)throws Exception{
		try {
//			CronExpression ce=
					new CronExpression(timeExp);
			return true;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}
	
	
	public static void main(String[] args)throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate=sdf.parse("2016-12-01 00:00:00");
		Date endDate=sdf.parse("2017-01-01 20:10:00");
		String cronExp="0 0 0 20/1  * ?";
		List<Date> list= makeTimePlan(startDate, endDate, cronExp);
//		List<Date> list=makeTimePlanForTomorrow(cronExp);
		for(Date date:list){
			System.out.println(sdf.format(date));
		}
		System.out.println("exp-check:" + checkTimeExp("0 0/1 0 0  * ?"));
	}

}
