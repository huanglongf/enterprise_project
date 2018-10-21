package com.jumbo.wms.manager.outwh;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;

public interface BaseOutWarehouseManager extends BaseManager {
    /**
     * 查询待发送发票
     * 
     * @param source
     * @param sourceWh
     * @return
     */
    Map<String, List<MsgInvoice>> findInvoicesBySourceGroup(String source, String sourceWh);

    /**
     * 更新发票通知表状态
     * 
     * @param msgId
     * @param status
     */
    void updateMsgInvoiceStatus(Long msgId, DefaultStatus status);

    /**
     * 根据外包仓编码查询所有相干仓库
     * 
     * @param source
     * @return
     */
    List<Warehouse> findOutWarehousesBySource(String source);
}
