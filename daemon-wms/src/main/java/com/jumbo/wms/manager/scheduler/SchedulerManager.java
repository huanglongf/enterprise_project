package com.jumbo.wms.manager.scheduler;

import java.util.Date;

public interface SchedulerManager {

    /**
     * 添加定时任务
     * 
     * @param taskInstance 任务的实例
     * @param taskMethod 调用任务的方法
     * @param date 在什么时间执行
     * @param jobName 任务名称(不能重复)，一般按照业务逻辑再加上一定的参数进行命名
     */
    public void addTask(Object taskInstance, String taskMethod, Date date, String args, String jobName, String group) throws Exception;


    /**
     * 添加定时任务
     * 
     * @param taskInstance 任务的实例
     * @param taskMethod 调用任务的方法
     * @param timeExp 执行时间表达式
     * @param jobName 任务名称(不能重复)，一般按照业务逻辑再加上一定的参数进行命名
     */
    public void addTask(Object taskInstance, String taskMethod, String timeExp, String args, String jobName, String group) throws Exception;

    /**
     * 添加定时任务
     * 
     * @param taskInstance
     * @param taskMethod
     * @param timeExp
     * @param args 对象列表
     * @param jobName
     * @throws Exception
     */
    public void addTask(Object taskInstance, String taskMethod, String timeExp, Object[] args, String jobName, String group) throws Exception;

    /**
     * 删除定时任务
     * 
     * @param jobName
     */
    public void removeTask(String jobName, String group) throws Exception;

    /**
     * 清除定时任务
     * 
     * @throws Exception
     */
    public void timerClean(String taskType) throws Exception;

    /**
     * 此方法不能丢,用作scheduler的默认任务
     * 
     * @throws Exception
     */
    public void test() throws Exception;
}
