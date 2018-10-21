package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaLine;

public interface CreateStaManagerExecu {
    public void createStaForSales(QueueSta queueSta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines, List<QueueStaInvoice> invoices);

    public void sendCreateResultToOms(StaCreatedResponse createdResponse, Long qstaId);

    public void createSta(QueueSta queueSta, QueueStaDeliveryInfo deliveryInfo, List<QueueStaLine> staLines, List<QueueStaInvoice> invoices);

    public void createLogSta(Long staId, StaCreatedResponse rs);

}
