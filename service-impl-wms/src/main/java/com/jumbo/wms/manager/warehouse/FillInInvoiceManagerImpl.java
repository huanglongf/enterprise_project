package com.jumbo.wms.manager.warehouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

/**
 * 补寄发票业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("fillInInvoiceManager")
public class FillInInvoiceManagerImpl extends BaseManagerImpl implements FillInInvoiceManager {

    /**
     * 
     */
    private static final long serialVersionUID = 6627784040632218770L;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private SequenceManager sequenceManager;

    @Override
    public void setLpCodeAndTransNo(Long id) {
        WmsInvoiceOrder wo = wmsInvoiceOrderDao.getByPrimaryKey(id);
        TransOlInterface transOlInterface = transOnLineFactory.getTransOnLineForFillInInvoice(wo.getLpCode());
        if (transOlInterface != null) {
            transOlInterface.matchingTransNoForInvoiceOrder(id);
        }
    }

    @Override
    public String setBatchCodeForList(List<Long> wioIdlist) {
        String batchCode = sequenceManager.getCode(WmsInvoiceOrder.class.getName(), new WmsInvoiceOrder());
        for (int i = 0; i < wioIdlist.size(); i++) {
            WmsInvoiceOrder wo = wmsInvoiceOrderDao.getByPrimaryKey(wioIdlist.get(i));
            wo.setBatchCode(batchCode);
            wo.setPgIndex(i + 1);
        }
        return batchCode;
    }

    @Override
    public void invoiceOrderInterface() {
        List<Long> wioIdList = wmsInvoiceOrderDao.getAllInvoiceOrderForId(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : wioIdList) {
            try {
                setLpCodeAndTransNo(id);
            } catch (BusinessException e) {
                log.warn("invoiceOrderInterface match failed, id : {}", id);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }


}
