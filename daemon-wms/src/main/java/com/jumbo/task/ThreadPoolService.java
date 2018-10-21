package com.jumbo.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * 线程池线程执行超时计算器
     */
    private Map<String, ThreadOvertimeCalculator> threadCalcuator = new HashMap<String, ThreadOvertimeCalculator>();

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
        if (tpe == null) {
            ThreadConfig cfg = threadUtilManager.getByCodeAndSysKey(key, ThreadConfig.SYS_KEY_DEAMON);
            if (cfg != null) {
                ExecutorService pool = Executors.newFixedThreadPool(cfg.getThreadCount());
                ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
                pools.put(cfg.getThreadCode(), tx);
                return tx;
            } else {
                log.error("getPool error,thread pool code:{}", key);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
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
                        log.info("msgToMcsTask, thread todoTotal is {},key : {}", todoTotal, key);
                    } catch (InterruptedException e) {
                        log.error("msgToMcsTask sleep error");
                    }
                } else {
                    break;
                }
            }
            tpe.execute(t);
        } catch (Exception e) {
            log.error("add thread error,"+key, e);
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
     * 等待线程池内所有任务完成2
     * 
     * @author jingkai
     * @param key 线程池编码
     */
    public void waitToFinish2(String key) {
        log.debug("thread pool : {} ,wait to finish.", key);
        ThreadPoolExecutor tpe = getPool(key);
        // 停止线程池
        tpe.shutdown();
        // 判断线程池是否结束
        boolean isFinish = false;
        while (!isFinish) {
            // System.out.println(tpe.getTaskCount() + "================" +
            // tpe.getCompletedTaskCount());
            try {
                isFinish = tpe.awaitTermination(1, TimeUnit.SECONDS);
                // 如线程池中某个线程执行超时不完成，则中断本次任务
                com.jumbo.task.ThreadOvertimeCalculator cal = threadCalcuator.get(key);
                long curTaskCount = tpe.getTaskCount();
                long curCompletedTaksCount = tpe.getCompletedTaskCount();
                //为空或者完成数量小于当前数量任务新建任务
                if (cal == null){
                    genNewCalcuator(key, tpe,null);
                } else {
                    // 待执行数量与已执行数量未变则更新最后修改时间
                    boolean isUpdated = (cal.getLastModifyTime()<0 || !(cal.getTaskCount() == curTaskCount && cal.getCompletedTaskCount() == curCompletedTaksCount));
                    if (isUpdated) {
                        cal.setLastModifyTime(System.currentTimeMillis());
                        cal.setCompletedTaskCount(tpe.getCompletedTaskCount());
                        cal.setTaskCount(tpe.getTaskCount());
                    }
                    // 判断超时时间超过10分钟则中断任务
                    long tmp = System.currentTimeMillis() - cal.getLastModifyTime();
                    if (tmp > 10 * 60 * 1000){
                        //创建新线程池，抛出异常
                        resetThreadPool(key, tpe,"1");
                        // 结束线程池
                        tpe.shutdownNow();
                        log.error("thread run time out key:"+key+",curTaskCount:"+curTaskCount+",curCompletedTaksCount:"+curCompletedTaksCount+"-"+cal.getTaskCount()+"-"+cal.getCompletedTaskCount());
                        throw new BusinessException("thread run time out");
                    }
                    threadCalcuator.put(key, cal);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 如果线程池完成任务，则新建线程池下次使用
            if (isFinish) {
                resetThreadPool(key, tpe,"1");
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

    /***
     * 新建计数器
     * 
     * @param key
     */
    private void genNewCalcuator(String key, ThreadPoolExecutor tpe,String type) {
        ThreadOvertimeCalculator cal = new ThreadOvertimeCalculator();
        cal.setCompletedTaskCount(tpe.getCompletedTaskCount());
        cal.setTaskCount(tpe.getTaskCount());
        if("1".equals(type)){//重置时间
            cal.setLastModifyTime(-1);
        } else if("2".equals(type)){
            cal.setLastModifyTime(System.currentTimeMillis());
        }else{
            cal.setLastModifyTime(System.currentTimeMillis());
        }
        threadCalcuator.put(key, cal);
    }

    private void resetThreadPool(String key, ThreadPoolExecutor tpe,String type) {
        ExecutorService pool = Executors.newFixedThreadPool(tpe.getCorePoolSize());
        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        pools.put(key, tx);
        genNewCalcuator(key, tpe,type);
    }

}
