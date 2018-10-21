package com.jumbo.wms.manager.mq;

import com.jumbo.wms.manager.BaseManager;

public interface MqManagerProxy extends BaseManager {

    /**
     * 接收吴江发票回传信息
     * 
     * @param message
     */
    void receiveMqInvoicePrintMsgWj(String message);
    
    public void mqSendGDVSalesOrder();

}
