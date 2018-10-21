package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

/**
 * 补开发票业务逻辑接口(有事务)
 * 
 * @author jinlong.ke
 * 
 */
public interface FillInInvoiceManager extends BaseManager {

    void setLpCodeAndTransNo(Long id);

    String setBatchCodeForList(List<Long> wioIdlist);

    void invoiceOrderInterface();

}
