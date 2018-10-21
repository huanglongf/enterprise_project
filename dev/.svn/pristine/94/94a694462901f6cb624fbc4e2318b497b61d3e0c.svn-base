package com.bt.common.model;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bt.utils.ScheduleUtils;

/**
 * @Title:QuartzJobFactoryDisallowConcurrentExecution
 * @Description: TODO(非并发)
 * @author Ian.Huang 
 * @date 2017年4月5日下午5:03:21
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		ScheduleUtils.invoke((Schedule)paramJobExecutionContext.getMergedJobDataMap().get("schedule"));

	}

}
