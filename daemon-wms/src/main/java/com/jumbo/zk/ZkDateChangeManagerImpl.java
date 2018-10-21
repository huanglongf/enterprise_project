package com.jumbo.zk;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baozun.zkpro.bean.ZkDateChangeManager;
import com.jumbo.task.ThreadPoolService;
import com.jumbo.util.ZkRootConstants;
import com.jumbo.wms.bean.AppParamConstants;
import com.jumbo.wms.bean.TaskControl;
import com.jumbo.wms.manager.scheduler.SystemConfigMangaer;
import com.jumbo.wms.manager.scheduler.SystemTaskInitManager;

public class ZkDateChangeManagerImpl implements ZkDateChangeManager {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ZkDateChangeManagerImpl.class);

    @Value("${zk.config.app.root}")
    private String sysconfigPath;

    @Value("${zk.notice.task.root}")
    private String taskConfigPath;

    @Value("${zk.notice.log.change}")
    private String logChangePath;

    @Value("${zk.create.sta.root}")
    private String createStaRoot;
    
    @Value("${zk.create.sta.cfg}")
    private String createStaCfg;
    

    /**
     * 线程池配置修改通知路径
     */
    @Value("${path.cfg.thread.pool}")
    private String threadPoolCfgPath;

    // @Autowired(required = false)
    // private SchedulerFactoryBean scheduler;

    @Autowired
    private SystemConfigMangaer systemConfigMangaer;
    @Autowired
    private SystemTaskInitManager systemTaskInitManager;
    @Autowired
    private ThreadPoolService threadPoolService;



    @Override
    public void changeData(String dataPath, Object data) {
        if (dataPath.equals(sysconfigPath)) {
            // 应用参数的更新
            // systemConfigMangaer.init();
        } else if (dataPath.equals(taskConfigPath)) {
            // 系统定时任务更新
            taskConfig(data);
        } else if (dataPath.equals(logChangePath) && data != null) {
            changeLogLevel(data);
        } else if (dataPath.equals(threadPoolCfgPath)) {
            changeThreadPoolCfg(data);
        } else if (dataPath.equals(createStaRoot) ) {
            if(data == null){
                ZkRootConstants.setCreateRoot(createStaCfg);
            } else{
                ZkRootConstants.setCreateRoot(data.toString());
            }
            
            
            
        }
    }

    @Override
    public void deleteData(String dataPath) {

    }

    /**
     * 修改线程池配置
     * 
     * @author jingkai
     * @param data
     */
    private void changeThreadPoolCfg(Object data) {
        if (data != null) {
            String tmp = (String) data;
            LOGGER.info("pool change : {}", tmp);
            String[] cfg = tmp.split(",");
            if (cfg.length == 2) {
                threadPoolService.changeThreadCount(new Long(cfg[0].trim()), new Integer(cfg[1].trim()));
            }
        }
    }

    /**
     * 系统定任务配置
     * 
     * @author jingkai
     * @param data
     */
    private void taskConfig(Object data) {
        // 如果是启动第一次调用，需要先暂停所有任务
        if (data == null) {
            TaskControl.off();
        }
        // 如果开关关闭，则不会执行
        String value = systemConfigMangaer.getValue(AppParamConstants.SYS_TASK_SWITCH);
        if (AppParamConstants.OFF.equals(value)) {return;}
        systemTaskInitManager.taskInit(data);
    }

    /**
     * 动态调整日志
     * 
     * @param data
     */
    private void changeLogLevel(Object data) {
        String strSource = (String) data;
        String[] strTops = strSource.split(";");
        for (int i = 0; i < strTops.length; i++) {
            String[] strs = strTops[i].split("-");
            if (strs.length > 1) {
                String packagePath = strs[0];
                String strlevel = strs[1];
                Level level = Level.toLevel(strlevel);
                org.apache.log4j.Logger logger = LogManager.getLogger(packagePath);
                logger.setLevel(level);
            } else if (strs.length == 1) {
                Level level = Level.toLevel(strSource);
                LogManager.getRootLogger().setLevel(level);
            }
        }
        LOGGER.info(" change log level :" + strSource);
    }

}
