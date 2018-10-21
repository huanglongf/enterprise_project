package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlDhl")
@Transactional
public class TransOlDhl implements TransOlInterface {
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        return null;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        return null;
    }

    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {
        
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
