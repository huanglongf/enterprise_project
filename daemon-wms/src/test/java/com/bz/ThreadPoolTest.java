package com.bz;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.jumbo.task.DaemonTask;

public class ThreadPoolTest {

    static ExecutorService pool = Executors.newFixedThreadPool(10);
    static ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;

    public static void main(String[] args) throws InterruptedException {
        int ct = 1;
        for (int j = 1; j <= 6; j++) {
            long d = new Date().getTime();
            // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
            for (int i = 1; i <= 20; i++) {
                Thread t = new MyThread();
                t.setName("线程：" + i);
                pool.execute(t);
                // System.out.println(tx.getActiveCount() + " " + tx.getTaskCount() + " " +
                // tx.getCompletedTaskCount());
                Thread.sleep(20L);
                // System.out.println("task count : " + tx.getTaskCount());
            }
            // System.out.println("finish");
            // pool.
            // 关闭线程池
            // pool.shutdown();
            // System.out.println(tx.getActiveCount() + " " + tx.getTaskCount() + " " +
            // tx.getCompletedTaskCount());
            boolean isFinish = false;
            if (ct == 3) {
                
                tx.setCorePoolSize(15);
                tx.setMaximumPoolSize(15);
            }
            if (ct == 5) {
                
                tx.setCorePoolSize(2);
                tx.setMaximumPoolSize(2);
            }


            while (!isFinish) {
                Thread.sleep(300L);
                System.out.println(tx.getPoolSize());
                if (tx.getTaskCount() == tx.getCompletedTaskCount()) {
                    isFinish = true;
                    System.out.println("====================finish " + ct++ + "  " + (new Date().getTime() - d) / 1000 + "======================");
                    break;
                }
            }
        }



        // while (!isFinish) {
        // System.out.println(tx.getActiveCount() + " " + tx.getTaskCount() + " " +
        // tx.getCompletedTaskCount());
        // isFinish = pool.isTerminated();
        // try {
        // Thread.sleep(1000L);
        //// System.out.println("active count : " + tx.getTaskCount());
        // System.out.println("active count : " + tx.getCompletedTaskCount());
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
        // System.out.println("finish\t" + pool.isShutdown());
        // List<String > s = null;
    }

}
