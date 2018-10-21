package com.jumbo.wms.daemon;

/**
 * 
 * 
 * 项目名称：Wms 类名称：MergeStaTask 类描述：合并作业单定时 创建人：bin.hu 创建时间：2014-7-22 下午03:04:11
 * 
 * @version
 * 
 */
public interface MergeStaTask {

    /**
     * 定时任务-每30分钟执行一次，合并对应的STA生成新的STA
     */
    public void mergeSta(Long whOuId);
}
