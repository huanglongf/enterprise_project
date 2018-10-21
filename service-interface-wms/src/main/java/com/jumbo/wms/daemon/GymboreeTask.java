package com.jumbo.wms.daemon;

/**
 * 金宝贝品牌对接方法
 * 
 * @author jinlong.ke
 * 
 */
public interface GymboreeTask {

    /**
     * 零售单指示定时任务入口
     */
    public void outboundNotice();

    /**
     * 取消零售单指示定时任务入口
     */
    public void cancelOutbound();

    /**
     * 金宝贝零售单实绩，执行出库
     */
    public void exeGymboreeOutboundOrder();

    /**
     * 店存入库接收
     * 
     * @param msg
     */
    public void createInboundOrderAndExe(String msg) throws Exception;

    /**
     * 店存入库创建执行
     */
    public void createStaByReceive();

    /**
     * 金宝贝退货指示
     */
    public void rtnInboundNotice();

    /**
     * 金宝贝入库执行 店存入库，退货入库
     */
    public void exeInboundOrder();

    /**
     * 金宝贝店存出库接收
     * 
     * @param msg
     */
    public void rtnOutboundRecieveAndCreate(String msg) throws Exception;

    /**
     * 金宝贝店存出库创单执行
     */
    public void rtnOutboundCreateSta();

    /**
     * 金宝贝店存出库确认
     */
    public void sendRtnOutboundResult();

    /**
     * 金宝贝店存入库确认
     */
    public void sendRtnInboundResult();

    /**
     * 金宝贝其他出入库
     * 
     * @param msg
     * @throws Exception
     */
    public void receiveOtherInOut(String msg) throws Exception;

    /**
     * 金宝贝其他出入库创建执行
     */
    public void exeInventoryChange();

    /**
     * 其他入库反馈HUB
     */
    public void otherInboundRtn();

    /**
     * 其他出库反馈HUB
     */
    public void otherOutboundRtn();
}
