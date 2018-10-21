package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

@Service("transOlCnyz")
@Transactional
public class TransOlCnyz implements TransOlInterface {
    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransAliWaybill transAliWaybill;

    @Resource(name = "transOlSto")
    private TransOlInterface sto;
    @Resource(name = "transOlYto")
    private TransOlInterface yto;

    protected static final Logger logger = LoggerFactory.getLogger(TransOlCnyz.class);
    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo sd = null;

        sd = transAliWaybill.getTransNoByStaId(staId);
        if (sd == null || StringUtil.isEmpty(sd.getTrackingNo())) {
            sd = null;
        }
        if (sd == null) {
            if ("YTO".equals(sta.getStaDeliveryInfo().getLpCode())) {
                sd = yto.matchingTransNo(staId);
            } else if ("STO".equals(sta.getStaDeliveryInfo().getLpCode())) {
                sd = sto.matchingTransNo(staId);
            }
        }
        return sd;
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {

        return matchingTransNo(staId);
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        return transAliWaybill.waybillGetByPackage(packId);
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


}
