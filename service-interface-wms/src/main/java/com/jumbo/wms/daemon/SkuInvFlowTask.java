package com.jumbo.wms.daemon;

/**
 * wms3.0-to-IM相关定时任务
 *
 */
public interface SkuInvFlowTask {

    /**
     * 移动库存流水过期数据到日志表
     */
    public void transferExpireDataIntoLog();

    /**
     * 移动占用或取消释放数据到日志表
     */
    public void transferOcciedExpireDataIntoLog();

    /**
     * 库存流水推送IM
     */
    public void skuInvFlowToImByMQ();

    /**
     * 占用或取消数据推送IM
     */
    public void occupiedAndReleaseToImByMQ();

    /**
     * 流水或者占用推送失败发送预警邮件
     */
    public void sendEmailWhenSendToIMError();

}
