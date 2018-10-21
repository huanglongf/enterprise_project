package com.jumbo.wms.daemon;

public interface TaskOms {

    /**
     * OMS外包仓销售出库定时任务入口
     */
    public void vmiOMSOutbound();

    /**
     * oms出库接口优化定时任务入口
     */
    public void runThreadOutBound();

    /**
     * 邮件通知定时任务-- 每小时一次 汇总错误次数超过10次仍旧没有成功的单据且没有进行邮件通知
     */
    public void emailNotice();

    /**
     * 邮件通知定时任务-- 每小时一次 所有超过6小时的通知OMS创建SKU而OMS仍未创建的邮件通知（同一商品不重复发送邮件通知）
     */
    public void emailNoticeOms();

    /**
     * 上传退货入文档至FTP
     */
    public void uploadRtnInDocToFtp();
    /**
     * pacs库存同步优化 (每半个小时执行一次)
     */
    public void insertOmsOutBound();
    /**
     * 更新t_wh_order_status_oms表createTime是空的数据
     */
    public void modifyWmsOrderStatusOmsDate();
}
