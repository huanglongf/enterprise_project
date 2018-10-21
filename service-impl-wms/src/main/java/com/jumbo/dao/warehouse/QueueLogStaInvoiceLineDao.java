package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.QueueLogStaInvoiceLine;
@Transactional
public interface QueueLogStaInvoiceLineDao  extends GenericEntityDao<QueueLogStaInvoiceLine, Long> {

}
