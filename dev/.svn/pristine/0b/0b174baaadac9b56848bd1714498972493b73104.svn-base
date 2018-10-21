package com.bt.lmis.base.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bt.lmis.model.ScheduleJob;

/** 
* @ClassName: QuartzJobFactoryDisallowConcurrentExecution 
* @Description: TODO(若一个方法一次执行不完下次轮转时则等待该方法执行完后才执行下一次操作) 
* @author Yuriy.Jiang
* @date 2016年7月13日 上午11:36:49 
*  
*/
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskUtils.invokMethod((ScheduleJob)context.getMergedJobDataMap().get("scheduleJob"));

	}
	
}