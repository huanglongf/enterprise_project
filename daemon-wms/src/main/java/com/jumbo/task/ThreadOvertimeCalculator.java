package com.jumbo.task;

import java.util.Date;

/**
 * 线程池执行超时计算器
 * 
 * @author SSH0045
 *
 */
public class ThreadOvertimeCalculator{

    private String threadKey;

    private long taskCount;

    private long completedTaskCount;

    private long lastModifyTime;

    public String getThreadKey(){
        return threadKey;
    }

    public void setThreadKey(String threadKey){
        this.threadKey = threadKey;
    }

    public long getTaskCount(){
        return taskCount;
    }

    public void setTaskCount(long taskCount){
        this.taskCount = taskCount;
    }

    public long getCompletedTaskCount(){
        return completedTaskCount;
    }

    public void setCompletedTaskCount(long completedTaskCount){
        this.completedTaskCount = completedTaskCount;
    }

    public long getLastModifyTime(){
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime){
        this.lastModifyTime = lastModifyTime;
    }

}
