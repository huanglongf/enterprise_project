package com.bt.lmis.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.bt.lmis.model.ScheduleJob;

public interface JobTaskService {
	public List<ScheduleJob> getAllTask(ScheduleJob scheduleJob);
	public void addTask(ScheduleJob job);
	public ScheduleJob getTaskById(Long jobId);
	public void changeStatus(Long jobId, String cmd) throws SchedulerException;
	public void updateCron(Long jobId, String cron) throws SchedulerException;
	public void addJob(ScheduleJob job) throws SchedulerException;
	public List<ScheduleJob> getAllJob() throws SchedulerException;
	public List<ScheduleJob> getRunningJob() throws SchedulerException;
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
	public boolean runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;
}
