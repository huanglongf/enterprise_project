package com.bt.lmis.base.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bt.lmis.model.ScheduleJob;

/** 
* @ClassName: QuartzJobFactory 
* @Description: TODO(计划任务执行处 无状态) 
* @author Yuriy.Jiang
* @date 2016年7月13日 上午11:36:36 
*  
*/
public class QuartzJobFactory implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskUtils.invokMethod((ScheduleJob)context.getMergedJobDataMap().get("scheduleJob"));
		
	}
	
}