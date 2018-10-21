package com.jumbo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.thread.ThreadUtilManager;
import com.jumbo.wms.model.config.ThreadConfig;


@Service("threadPoolService")
public class ThreadPoolService {

    protected static final Logger log = LoggerFactory.getLogger(ThreadPoolService.class);
    private Map<String, ThreadPoolExecutor> pools = new HashMap<String, ThreadPoolExecutor>();

    @Autowired
    private ThreadUtilManager threadUtilManager;

    @PostConstruct
    public void initPools() {
        List<ThreadConfig> list = threadUtilManager.getAllThreadPools(ThreadConfig.SYS_KEY_DEAMON);
        if (list != null) {
            for (ThreadConfig cfg : list) {
                ExecutorService pool = Executors.newFixedThreadPool(cfg.getThreadCount());
                ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
                pools.put(cfg.getThreadCode(), tx);
            }
        }
    }

    /**
     * 获取线程池
     * 
     * @param key 线程池编码
     * @return
     */
    public ThreadPoolExecutor getPool(String key) {
        ThreadPoolExecutor tpe = pools.get(key);
        if(tpe == null){
            ThreadConfig cfg = threadUtilManager.getByCodeAndSysKey(key,ThreadConfig.SYS_KEY_DEAMON);
            if (cfg != null) {
                ExecutorService pool = Executors.newFixedThreadPool(cfg.getThreadCount());
                ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
                pools.put(cfg.getThreadCode(), tx);
                return tx;
            }else{
                log.error("getPool error,thread pool code:{}",key);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }else{
            return tpe;
        }
    }

    /**
     * 执行线程
     * 
     * @author jingkai
     * @param key 线程池编码
     * @param t 线程
     */
    public void executeThread(String key, Thread t) {
        ThreadPoolExecutor tpe = getPool(key);
        try {
            while (true) {
                long todoTotal = tpe.getTaskCount() - tpe.getCompletedTaskCount();
                if (todoTotal >= 5000) {
                    try {
                        Thread.sleep(100L);
                        log.info("msgToMcsTask, thread todoTotal is " + todoTotal);
                    } catch (InterruptedException e) {
                        log.error("msgToMcsTask sleep error");
                    }
                } else {
                    break;
                }
            }
            tpe.execute(t);
        } catch (Exception e) {
            log.error("add thread error,", e);
        }
    }

    /**
     * 等待线程池内所有任务完成
     * 
     * @author jingkai
     * @param key 线程池编码
     */
    public void waitToFinish(String key) {
        log.debug("thread pool : {} ,wait to finish.", key);
        ThreadPoolExecutor tpe = getPool(key);
        boolean isFinish = false;
        while (!isFinish) {
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                log.warn("thread sleep error", e);
            }
            if (tpe.getTaskCount() == tpe.getCompletedTaskCount()) {
                isFinish = true;
                log.info("thread pool : {} ,is finished.", key);
                break;
            }
        }
    }

    /**
     * 修改线程池当前配置，保存数据库配置
     * 
     * @author jingkai
     * @param id
     * @param count
     */
    public void changeThreadCount(Long id, int count) {
        ThreadConfig cfg = threadUtilManager.getThreadConfigById(id);
        if (cfg != null) {
            // 修改当前线程池
            ThreadPoolExecutor tpe = getPool(cfg.getThreadCode());
            tpe.setCorePoolSize(count);
            tpe.setMaximumPoolSize(count);
            // 修改数据库记录
            threadUtilManager.updateThreadCount(id, count);
        }
    }
}
