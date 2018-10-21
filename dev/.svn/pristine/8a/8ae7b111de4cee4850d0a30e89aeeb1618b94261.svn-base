package com.bt.common.model;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bt.utils.ScheduleUtils;

/**
 * @Title:QuartzJobFactory
 * @Description: TODO(可并发)
 * @author Ian.Huang 
 * @date 2017年4月5日下午5:02:32
 */
public class QuartzJobFactory implements Job {

	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		ScheduleUtils.invoke((Schedule)paramJobExecutionContext.getMergedJobDataMap().get("schedule"));

	}

}
