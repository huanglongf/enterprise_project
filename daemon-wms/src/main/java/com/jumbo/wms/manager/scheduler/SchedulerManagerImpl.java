package com.jumbo.wms.manager.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.bean.AppsMethodInvokingJobDetailFactoryBean;



@Transactional
@Service("schedulerManager")
public class SchedulerManagerImpl implements SchedulerManager {

    private static final Logger log = LoggerFactory.getLogger(SchedulerManagerImpl.class);

    @Autowired(required = false)
    private SchedulerFactoryBean scheduler;



    @Override
    public void addTask(Object taskInstance, String taskMethod, Date date, String args, String jobName, String group) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("s m H d M ? yyyy");
        String strDate = sdf.format(date);
        addTask(taskInstance, taskMethod, strDate, args, jobName, group);
    }

    @Override
    public void removeTask(String jobName, String group) throws Exception {


        JobKey jk = new JobKey(jobName, group);

        scheduler.getScheduler().deleteJob(jk);
    }

    @Override
    public void timerClean(String taskType) throws Exception {


        GroupMatcher<TriggerKey> gm = GroupMatcher.triggerGroupEquals(taskType);

        Set<TriggerKey> triggerKey = scheduler.getScheduler().getTriggerKeys(gm);

        Iterator<TriggerKey> iterTriggerKey = triggerKey.iterator();

        while (iterTriggerKey.hasNext()) {

            TriggerKey tKey = iterTriggerKey.next();
            Trigger trigger = scheduler.getScheduler().getTrigger(tKey);

            if (trigger.getJobKey().getName().startsWith(taskType)) {
                removeTask(trigger.getJobKey().getName(), taskType);
            }
        }

        log.info("-----------task clean :" + taskType);
    }

    @Override
    public void addTask(Object taskInstance, String taskMethod, String timeExp, String args, String jobName, String group) throws Exception {

        this.addTask(taskInstance, taskMethod, timeExp, processArgs(args), jobName, group);
    }

    @Override
    public void addTask(Object taskInstance, String taskMethod, String timeExp, Object[] args, String jobName, String group) throws Exception {
        MethodInvokingJobDetailFactoryBean mifb = new AppsMethodInvokingJobDetailFactoryBean();

        mifb.setTargetObject(taskInstance);
        mifb.setTargetMethod(taskMethod);
        mifb.setConcurrent(false);
        mifb.setName(jobName);
        mifb.setGroup(group);
        mifb.setArguments(args);
        mifb.afterPropertiesSet();
        JobDetailImpl jobDetail = (JobDetailImpl) mifb.getObject();
        jobDetail.setJobClass(StatefulMethodInvokingJob.class);


        CronTriggerImpl ctb = new CronTriggerImpl();

        ctb.setCronExpression(timeExp);

        ctb.setName(jobName + "trigger");
        ctb.setGroup(group);

        scheduler.getScheduler().scheduleJob(jobDetail, ctb);
    }

    private String[] processArgs(String args) {

        if (StringUtils.isBlank(args)){ return null;}

        String[] strArray = null;
        try {
            strArray = args.split(",");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return strArray;
    }

    @Override
    public void test() throws Exception {
        // TODO Auto-generated method stub

    }

}
