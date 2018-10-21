package com.jumbo.wms.manager.outwh.biaogan;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;

public interface BiaoGanManager extends BaseManager {
    /**
     * 发送发票信息
     * 
     * @param invoices
     * @param source
     * @param sourceWh
     */
    public void sendInvoice(Map<String, List<MsgInvoice>> invoices, String source, String sourceWh);

    /**
     * 标杆销售出库通知
     * 
     * @param ids
     * @return
     */
    String outBoundOrder(List<Long> ids);

    /**
     * 同步商品
     * 
     * @param skus
     */
    void singleSkuToWms(List<MsgSKUSync> skus);

    /**
     * 保存每天销售出库反馈未记录单据信息
     * 
     * @param order
     * @param sdf
     */
    void outBoundToday(RtnOutBoundCommand order, SimpleDateFormat sdf);

    /**
     * 标杆其他出库通知
     * 
     * @param ids
     */
    void outBoundReturn(List<Long> ids);
}
