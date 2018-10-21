package com.jumbo.wms.manager.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.util.SchedulerTaskConstant;
import com.jumbo.util.Validator;
import com.jumbo.wms.bean.TaskControl;
import com.jumbo.wms.manager.system.SchedulerTaskManager;
import com.jumbo.wms.model.system.SysSchedulerTask;

@Service("systemTaskInitManager")
public class SystemTaskInitManagerImpl implements SystemTaskInitManager {

    protected static final Logger log = LoggerFactory.getLogger(SystemTaskInitManagerImpl.class);

    @Autowired
    private SchedulerManager schedulerManager;
    @Autowired
    private SchedulerTaskManager sdkSchedulerTaskManager;
    @Autowired
    private ApplicationContext applicationContext;
    private static boolean INITFINISH = false;

    // 当前作为spring scheduler中的文件监控,通过id更新时，考虑到补偿与非补偿的切换，更新时，一定要删除当前相同id的任务
    // map key为sys task id
    private static Map<Long, String> curSysTask = new HashMap<Long, String>();

    @Override
    public void taskInit(Object obj) {
        try {
            String data = (String) obj;
            // 获取变量， 双daemon改造by fxl
            String nodeStr = System.getProperty("daemon.node");
            log.info("daemon.node valus is : " + nodeStr);
            Integer node = null;
            try {
                if (nodeStr != null && StringUtils.hasLength(nodeStr)) {
                    node = Integer.parseInt(nodeStr);
                }
            } catch (Exception e) {
                log.error("taskInit get daemon.node is errir:", e);
            }
            boolean isNum = false;
            // 先验证不为空
            if (data != null) {
                isNum = data.matches("^\\d+$");
            }
            // 如果等于0或是不为数字，则全部刷新
            if (!isNum || data.equals("0")) {
                List<SysSchedulerTask> taskList = sdkSchedulerTaskManager.findAllEffectSchedulerTaskList(node);
                // 先清理掉所有的定时任务
                schedulerManager.timerClean(SchedulerTaskConstant.SYSTEM_TASK_TYPE);
                // 遍历所有启用的任务，加载到定时计划中
                if (Validator.isNotNullOrEmpty(taskList)) {
                    for (SysSchedulerTask task : taskList) {
                        if (!task.getNeedCompensate()) {// 不需要补偿的任务才会进行注册
                            log.info("daemon.node task id is : " + task.getId());
                            handleTask(task);
                        }
                    }
                }
            }
            // 只刷新某个id的内容
            else {
                Long id = Long.parseLong(data);
                String jobName = curSysTask.get(id);
                // 先删除当前任务
                if (jobName != null) {
                    schedulerManager.removeTask(jobName, SchedulerTaskConstant.SYSTEM_TASK_TYPE);
                }
                SysSchedulerTask schedulerTask = sdkSchedulerTaskManager.findSchedulerTaskById(id);
                // 只有不需要补偿，才需要继续注册任务
                if (null != schedulerTask && !schedulerTask.getNeedCompensate() && node == schedulerTask.getNode()) {
                    log.info("handling task " + schedulerTask.getCode() + " begin");
                    handleTask(schedulerTask);
                    log.info("handling task " + schedulerTask.getCode() + " end");
                } else {
                    log.warn("can't find taskinfo or need compensate ,task id is " + id);
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("-----------system task init");
    }

    private void handleTask(SysSchedulerTask schedulerTask) throws Exception {
        try {
            Integer lifeCycle = schedulerTask.getLifecycle();
            String taskBeanName = schedulerTask.getBeanName();
            String methodName = schedulerTask.getMethodName();
            String jobName = SchedulerTaskConstant.SYSTEM_TASK_TYPE + "-" + schedulerTask.getCode();
            String timeExp = schedulerTask.getTimeExp();
            String args = schedulerTask.getArgs();
            if (SysSchedulerTask.LIFECYCLE_NORMAL.equals(lifeCycle)) {// 如果是启用状态 1
                schedulerManager.removeTask(jobName, SchedulerTaskConstant.SYSTEM_TASK_TYPE);
                Object taskInstance = applicationContext.getBean(taskBeanName);
                schedulerManager.addTask(taskInstance, methodName, timeExp, args, jobName, SchedulerTaskConstant.SYSTEM_TASK_TYPE);
                curSysTask.put(schedulerTask.getId(), jobName); // 添加已注册的task
            } else if (SysSchedulerTask.LIFECYCLE_DELETED.equals(lifeCycle) || SysSchedulerTask.LIFECYCLE_DISABLE.equals(lifeCycle)) {// 如果是禁用0或者逻辑删除2
                schedulerManager.removeTask(jobName, SchedulerTaskConstant.SYSTEM_TASK_TYPE);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void initAllTask() {
        log.error("--------inti start---------");
        if (!INITFINISH) {
            log.error("initAllTask start");
            try {
                schedulerManager.removeTask("GROUP_INIT_TRIGGER", "GROUP_INIT");
                log.error("initAllTask remove init trigger success");
            } catch (Exception e) {
                log.error("remove task error");
                log.error("", e);;
            }
            // 获取变量， 双daemon改造by fxl
            String nodeStr = System.getProperty("daemon.node");
            log.info("daemon.node values is : " + nodeStr);
            Integer node = null;
            try {
                if (nodeStr != null && StringUtils.hasLength(nodeStr)) {
                    node = Integer.parseInt(nodeStr);
                }
            } catch (Exception e) {
                log.error("taskInit get daemon.node is errir:", e);
            }
            List<SysSchedulerTask> schedulers = sdkSchedulerTaskManager.findAllEffectSchedulerTaskList(node);
            for (SysSchedulerTask scheduler : schedulers) {
                try {
                    handleTask(scheduler);
                    log.info("daemon.node task id is : " + scheduler.getId());
                } catch (Exception e) {
                    log.error("error init scheduler,scheduler is : " + scheduler.getCode());
                    log.error("", e);
                }
            }
            TaskControl.on();
            log.error("initAllTask end");
            INITFINISH = true;
        } else {
            log.error("##inti error,pleans stop daemon!!---------##inti error,pleans stop daemon!!---------");
        }
    }
}
