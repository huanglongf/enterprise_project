package com.jumbo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池工具类
 * 
 * @author sjk
 * 
 */
public class ThreadPoolUtil {

    protected static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);

    /**
     * 获取线程池
     * 
     * @param threadCount
     * @return
     */
    public static ThreadPoolExecutor getPool(int threadCount) {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
        return tx;
    }

    /**
     * 线程池增加执行线程
     * 
     * @param tpe 指定线程池
     * @param t 指定执行线程
     */
    public static void executeThread(ThreadPoolExecutor tpe, Thread t) {
        try {
            while (true) {
                long todoTotal = tpe.getTaskCount() - tpe.getCompletedTaskCount();
                if (todoTotal >= 1000) {
                    try {
                        Thread.sleep(500L);
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
     * 线程池增加执行线程
     * 
     * @param tpe 指定线程池
     * @param t 指定执行线程
     * @param threadCount 执行数量
     */
    public static void executeThread(ThreadPoolExecutor tpe, Thread t, Integer threadCount) {
        try {
            while (true) {
                long todoTotal = tpe.getTaskCount() - tpe.getCompletedTaskCount();
                if (todoTotal >= threadCount) {
                    try {
                        Thread.sleep(500L);
                        log.info(threadCount + " executeThread, thread todoTotal is " + todoTotal);
                    } catch (InterruptedException e) {
                        log.error(threadCount + "executeThread sleep error");
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
     * 关闭线程池
     * 
     * @param tpe
     */
    public static void closeThreadPool(ThreadPoolExecutor tpe) {
        tpe.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            ExecutorService pool = (ExecutorService) tpe;
            isFinish = pool.isTerminated();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("InterruptedException majorThread error");
            }
        }
    }
}
