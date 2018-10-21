package com.jumbo.wms.daemon;

/**
 * 过仓定时器
 * 
 * @author cheng.su
 * 
 */
public interface TaskCreateSta {

    void createSta();

    void createSta1();

    /**
     * 根据仓库创建作业单
     * 
     * @param strWhId
     */
    void createStaByWh(String strWhId);

    /**
     * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
     */
    void emailToOmsForFailure();

    /**
     * 过仓确认接口
     */
    void wmsConfirmOrderService();

    void outboudNoticePacSystenKey();


    /**
     * 超时没有生成作业单邮件通知优化
     */
    void newSendCreateStaTimeOutEmail();
}
