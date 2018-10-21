package com.jumbo.wms.manager.outwh.biaogan;

import com.jumbo.wms.manager.BaseManager;

/**
 * 
 * @author sjk
 * 
 */
public interface BiaoGanManagerProxy extends BaseManager {
    /**
     * 发送发票
     */
    void sendInvoices();

    /**
     * 发送订单
     */
    void sendSalesOutOrder();

    /**
     * 同步商品
     */
    void singleSkuToWmsProxy();

    /**
     * 保存每天销售出库反馈未记录单据信息
     */
    void orderDetailQueryToday();

    /**
     * 其他出库通知
     */
    void outBoundReturn();

}
