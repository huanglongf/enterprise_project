package com.jumbo.webservice.sfNew;

import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderConfirm;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.manager.BaseManager;


public interface SfOrderWebserviceClientInter extends BaseManager {

    SfResponse creSfOrder(SfOrder order, String sfClientCode, String checkword, int i);// 下单

    SfResponse comfirmOrder(SfOrderConfirm order, String sfClientCode, String checkword);// 确认订单
}
