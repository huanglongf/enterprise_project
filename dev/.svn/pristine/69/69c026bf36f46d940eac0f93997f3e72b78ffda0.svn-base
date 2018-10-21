package com.bt.lmis.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.lmis.base.RetObj;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.ScheduleJob;
import com.bt.lmis.service.JobTaskService;
import com.bt.utils.CommonUtils;

@Controller
@RequestMapping("/control/jobTaskController")
public class JobTaskController {
	// 日志记录器
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="jobTaskServiceImpl")
	private JobTaskService jobTaskServiceImpl;

	/**
	 * 
	 * @Description: TODO(轮询监控任务运行状态)
	 * @param request
	 * @return
	 * @return: RetObj  
	 * @author Ian.Huang 
	 * @date 2016年11月25日上午11:08:29
	 */
	@RequestMapping("/pollingRunningStatus")
	@ResponseBody
	public RetObj pollingRunningStatus(HttpServletRequest request){
		RetObj retObj= new RetObj();
		retObj.setFlag(false);
		// 获取所有运行中的任务
		try {
			retObj.setObj(jobTaskServiceImpl.getRunningJob());
			retObj.setMsg("SUCCESS");
			retObj.setFlag(true);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			retObj.setMsg("ERROR: " + CommonUtils.getExceptionStack(e));
			
		}
		return retObj;
		
	}
	
	/**
	 * 
	 * @Description: TODO(立即运行任务)
	 * @param request
	 * @param scheduleJob
	 * @return: RetObj  
	 * @author Ian.Huang 
	 * @date 2016年11月24日下午5:52:48
	 */
	@RequestMapping("/runTask")
	@ResponseBody
	public RetObj runTask(HttpServletRequest request, ScheduleJob scheduleJob){
		RetObj retObj= new RetObj();
		retObj.setFlag(false);
		try {
			if(jobTaskServiceImpl.runAJobNow(scheduleJob)) {
				retObj.setMsg("任务正在运行！");
				return retObj;
				
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			retObj.setMsg("任务执行失败！");
			return retObj;
			
		}
		retObj.setFlag(true);
		retObj.setMsg("任务执行成功！");
		return retObj;
		
	}

	/**
	 * 
	 * @Description: TODO(切换任务状态)
	 * @param request
	 * @param jobId
	 * @param cmd
	 * @return: RetObj  
	 * @author Ian.Huang 
	 * @date 2016年11月24日下午5:50:44
	 */
	@RequestMapping("/changeJobStatus")
	@ResponseBody
	public RetObj changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			jobTaskServiceImpl.changeStatus(jobId, cmd);
			
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			retObj.setMsg("任务状态改变失败！");
			return retObj;
			
		}
		retObj.setFlag(true);
		return retObj;
		
	}

	/**
	 * 
	 * @Description: TODO(更新cron表达式)
	 * @param request
	 * @param jobId
	 * @param cron
	 * @return: RetObj  
	 * @author Ian.Huang 
	 * @date 2016年11月24日下午5:49:48
	 */
	@RequestMapping("/updateCron")
	@ResponseBody
	public RetObj updateCron(HttpServletRequest request, Long jobId, String cron) {
		RetObj retObj = new RetObj();
		retObj.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(cron);
			
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
			
		}
		try {
			jobTaskServiceImpl.updateCron(jobId, cron);
			
		} catch (SchedulerException e) {
			retObj.setMsg("cron更新失败！");
			return retObj;
			
		}
		retObj.setFlag(true);
		return retObj;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param scheduleJob
	 * @return: RetObj  
	 * @author Ian.Huang 
	 * @date 2016年11月24日下午5:47:58
	 */
	@RequestMapping("/add")
	@ResponseBody
	public RetObj taskList(HttpServletRequest request, ScheduleJob scheduleJob) {
		RetObj retObj= new RetObj();
		retObj.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			
		} catch (Exception e) {
			retObj.setMsg("cron表达式有误，不能被解析！");
			return retObj;
			
		}
		Object obj= null;
		try {
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj= SpringUtils.getBean(scheduleJob.getSpringId());
				
			} else {
				obj= Class.forName(scheduleJob.getBeanClass()).newInstance();
				
			}
			
		} catch (Exception e) {
			// do nothing.........
			
		}
		if (obj == null) {
			retObj.setMsg("未找到目标类！");
			return retObj;
			
		} else {
			Method method= null;
			try {
				method= obj.getClass().getMethod(scheduleJob.getMethodName());
				
			} catch (Exception e) {
				// do nothing.....
				
			}
			if (method == null) {
				retObj.setMsg("未找到目标方法！");
				return retObj;
				
			}
			
		}
		try {
			jobTaskServiceImpl.addTask(scheduleJob);
			
		} catch (Exception e) {
			e.printStackTrace();
			retObj.setFlag(false);
			retObj.setMsg("保存失败，检查 name group 组合是否有重复！");
			return retObj;
			
		}
		retObj.setFlag(true);
		return retObj;
		
	}
	
	/**
	 * 
	 * @Description: TODO(任务列表查询)
	 * @param scheduleJob
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年10月14日下午1:53:55
	 */
	@RequestMapping("/taskList")
	public String taskList(ScheduleJob scheduleJob, HttpServletRequest request) {
		try {
			CommonUtils util=new CommonUtils();
			scheduleJob.setJobGroup(util.getLocalMac().get("service_mac").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			
		}
		List<ScheduleJob> taskList = jobTaskServiceImpl.getAllTask(scheduleJob);
		request.setAttribute("taskList", taskList);
		request.setAttribute("jobType", scheduleJob.getJobType());
		return "/lmis/task_list";
		
	}
	
}
