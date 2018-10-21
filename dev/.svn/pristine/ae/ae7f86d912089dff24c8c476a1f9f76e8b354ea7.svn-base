package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.task.QuartzJobFactory;
import com.bt.lmis.base.task.QuartzJobFactoryDisallowConcurrentExecution;
import com.bt.lmis.dao.ScheduleJobMapper;
import com.bt.lmis.dao.ServiceMapper;
import com.bt.lmis.model.ScheduleJob;
import com.bt.lmis.model.Services;
import com.bt.lmis.service.JobTaskService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

/** 
* @ClassName: JobTaskService 
* @Description: TODO(计划任务管理) 
* @author Yuriy.Jiang
* @date 2016年7月13日 上午11:23:47 
*  
*/
@Service
public class JobTaskServiceImpl implements JobTaskService {
	
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	@Autowired
	private ServiceMapper serviceMapper;
	
	public static String service_mac=null;
	
	/**
	 * 添加到数据库中 区别于addJob
	 */
	@Override
	public void addTask(ScheduleJob job) {
		job.setCreateTime(new Date());
		scheduleJobMapper.insertSelective(job);
		
	}
	
	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	@Override
	public List<ScheduleJob> getAllTask(ScheduleJob scheduleJob) {
		return scheduleJobMapper.getAll(scheduleJob);
		
	}
	
	/**
	 * 从数据库中查询job
	 */
	@Override
	public ScheduleJob getTaskById(Long jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
		
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler= schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs= scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList= new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob: executingJobs) {
			ScheduleJob job= new ScheduleJob();
			JobKey jobKey= executingJob.getJobDetail().getKey();
			Trigger trigger= executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			job.setJobStatus(scheduler.getTriggerState(trigger.getKey()).name());
			if (trigger instanceof CronTrigger) {
				job.setCronExpression(((CronTrigger)trigger).getCronExpression());
				
			}
			jobList.add(job);
			
		}
		return jobList;
		
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler= schedulerFactoryBean.getScheduler();
		List<ScheduleJob> jobList= new ArrayList<ScheduleJob>();
		for (JobKey jobKey: scheduler.getJobKeys(GroupMatcher.anyJobGroup())) {
			List<? extends Trigger> triggers= scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger: triggers) {
				ScheduleJob job= new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				job.setJobStatus(scheduler.getTriggerState(trigger.getKey()).name());
				if (trigger instanceof CronTrigger) {
					job.setCronExpression(((CronTrigger) trigger).getCronExpression());
					
				}
				jobList.add(job);
				
			}
			
		}
		return jobList;
		
	}
	
	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public boolean runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		if(scheduleJob.getIsConcurrent().equals(ScheduleJob.CONCURRENT_NOT)){
			List<ScheduleJob> schedules= getRunningJob();
			for(int i= 0; i< schedules.size(); i++){
				if((schedules.get(i).getJobGroup() + schedules.get(i).getJobName()).equals(scheduleJob.getJobGroup() + scheduleJob.getJobName())){
					// 正在运行
					return true;
					
				}
				
			}
			// 没有运行则立即执行
			schedulerFactoryBean.getScheduler().triggerJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
			
		} else {
			schedulerFactoryBean.getScheduler().triggerJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
			
		}
		return false;
		
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
		
	}
	
	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	@Override
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		ScheduleJob job= getTaskById(jobId);
		if (job == null) {
			return;
			
		}
		job.setCronExpression(cron);
		if (ScheduleJob.STATUS_START.equals(job.getJobStatus())) {
			updateJobCron(job);
			
		}
		scheduleJobMapper.updateByPrimaryKeySelective(job);
		
	}
	
	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler= schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey= TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger trigger= (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder= CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		trigger= trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
		
	}

	/**
	 * 更新任务状态
	 * 
	 * @throws SchedulerException
	 */
	@Override
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		ScheduleJob job= getTaskById(jobId);
		if (job == null) {
			return;
			
		}
		if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_START);
			addJob(job);
			
		} else if ("stop".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_STOP);
			deleteJob(job);
		
		} 
		scheduleJobMapper.updateByPrimaryKeySelective(job);
		
	}
	
	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		schedulerFactoryBean.getScheduler().deleteJob(JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup()));
		
	}
	
	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_START.equals(job.getJobStatus())) {
			return;
		}
		Scheduler scheduler= schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey= TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		CronTrigger trigger= (CronTrigger)scheduler.getTrigger(triggerKey);
		if (CommonUtils.checkExistOrNot(trigger)) {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder= CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build());
			
		} else {
			// 不存在，创建一个
			Class<? extends Job> clazz= ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent())? QuartzJobFactory.class: QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail= JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			scheduler.scheduleJob(jobDetail, TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).build());
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO(初始化定时任务)
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年11月24日下午5:45:33
	 */
	@PostConstruct
	public void init() throws Exception {
		CommonUtils util=new CommonUtils();
		Map<String, Object> map= util.getLocalMac();
		if(null!=map){
			String service_name= map.get("service_name").toString();
			service_mac= map.get("service_mac").toString();
			Services entity= new Services();
			entity.setService_mac(service_mac);
			entity.setService_name(service_name);
			List<Services> list= serviceMapper.findByMac(entity);
			if(list.size() == 0) {
				entity.setId(UUID.randomUUID().toString());
				entity.setCreate_time(new Date());
				entity.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
				entity.setHeart_beat(new Date());
				serviceMapper.insert(entity);
				System.out.println("计算机名:" + service_name);
				System.out.println("计算机MAC地址:" + service_mac);
				System.out.println("计算机" + service_name + "已注册！");
				
			} else {
				entity= list.get(0);
				entity.setHeart_beat(new Date());
				entity.setUpdate_time(new Date());
				entity.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
				serviceMapper.update(entity);
				System.out.println("计算机名:" + service_name);
				System.out.println("计算机MAC地址:" + service_mac);
				System.out.println("计算机" + service_name + "已同步！");
				
			}
			ScheduleJob sb= new ScheduleJob();
			sb.setJobGroup(service_mac);
			sb.setJobStatus(ScheduleJob.STATUS_START);
			// 这里获取任务信息数据
			List<ScheduleJob> jobList= scheduleJobMapper.getAll(sb);
			for (ScheduleJob job: jobList) {
				addJob(job);
				
			}
			System.out.println("查询到" + jobList.size() + "个定时器!");
			
		}
		
	}
	
}
