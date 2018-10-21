package com.bt.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.bt.common.model.Schedule;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.base.task.QuartzJobFactory;
import com.bt.lmis.base.task.QuartzJobFactoryDisallowConcurrentExecution;

public class ScheduleUtils {
	
	// 日志
	public final static Logger logger = Logger.getLogger(ScheduleUtils.class);
	// 调度器
	private static Scheduler scheduler= ((SchedulerFactoryBean)SpringUtils.getBean("schedulerFactoryBean")).getScheduler();
	
	/**
	 * 
	 * @Description: TODO(添加任务)
	 * @param schedule
	 * @throws SchedulerException
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月5日下午4:13:19
	 */
	public static void addSchedule(Schedule schedule) throws SchedulerException {
		// 触发器KEY
		TriggerKey triggerKey= TriggerKey.triggerKey(schedule.getName(), schedule.getGroup());
		// 触发器
		CronTrigger trigger= (CronTrigger)scheduler.getTrigger(triggerKey);
		if (CommonUtils.checkExistOrNot(trigger)) {
			// 当前KEY对应的触发器存在
			scheduler.rescheduleJob(triggerKey, trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCronExpression())).build());
			
		} else {
			// 当前KEY对应的触发器不存在
			JobDetail jobDetail= JobBuilder.newJob(schedule.isConcurrent()? QuartzJobFactory.class: QuartzJobFactoryDisallowConcurrentExecution.class).withIdentity(schedule.getName(), schedule.getGroup()).build();
			jobDetail.getJobDataMap().put("schedule", schedule);
			scheduler.scheduleJob(jobDetail, TriggerBuilder.newTrigger().withIdentity(schedule.getName(), schedule.getGroup()).withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCronExpression())).build());
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param schedule
	 * @return: void  
	 * @author Ian.Huang 
	 * @throws SchedulerException 
	 * @date 2017年4月5日下午4:17:45
	 */
	public static void delSchedule(Schedule schedule) throws SchedulerException {
		scheduler.deleteJob(JobKey.jobKey(schedule.getName(), schedule.getGroup()));
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param schedule
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月5日下午5:28:48
	 */
	public static void invoke(Schedule schedule) {
		try {
			Class<?> clazz= Class.forName(schedule.getClassName());
			clazz.getDeclaredMethod(schedule.getMethodName()).invoke(clazz.newInstance());
			
		} catch (ClassNotFoundException e) {
			logger.error("触发器名["+ schedule.getName()+ "]启动失败，失败原因：" + schedule.getClassName() + "不存在--------------------------------------");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			logger.error("触发器名["+ schedule.getName()+ "]启动失败，失败原因：" + schedule.getMethodName() + "不存在--------------------------------------");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		System.out.println("触发器名[" + schedule.getName() + "]启动成功--------------------------------------");
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param schedule
	 * @return: void  
	 * @author Ian.Huang 
	 * @throws SchedulerException 
	 * @date 2017年4月5日下午8:23:38
	 */
	public static void run(Schedule schedule) throws SchedulerException {
		scheduler.triggerJob(JobKey.jobKey(schedule.getName(), schedule.getGroup()));
		
	}
	
}