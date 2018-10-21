package com.jumbo.webservice.ids.manager;

import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;

public interface IdsManagerProxy extends BaseManager {
    void idsInventorySyn(String Source);

    void receiveAEOSkuMasterByMq(String message);

    /**
     * 获得品牌信息
     * 
     * @param source
     * @return
     */
    IdsServerInformation findServerInformationBySource(String source);

    // public void salesOrderToIds();
    // public void salesReturnOrderToIds();
    // public void salesOrderCancelToIds() ;

    /**
     * 订单状态更新接口
     * 
     * @param resultXml
     */
    void outOrderReceiveConfirm(String resultXml);

    void wmsOrderToIds(String source, String sourceWh);

    void returnNotifyRequesformLF(String source);

    void orderCancelResponseToLF(String source);

    void deleteSoLog();

    /**
     * 取消订单推送LF，实时接口
     * 
     * @param source
     */
    ConfirmResult siginOrderCancelResponseToLF(MsgOutboundOrderCancel order, String source);

    /**
     * 接口配置信息查询
     * 
     * @return
     */
    Map<String, IdsServerInformation> findIdsServiceInfo();

    void wmsAeoOrderToIds(String source, String sourceWh);

    void inboundIdsNike(String source);

    void idsNikeFeedbackTask(String source);

}
