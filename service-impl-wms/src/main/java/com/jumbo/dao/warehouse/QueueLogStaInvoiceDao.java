package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.QueueLogStaInvoice;
@Transactional
public interface QueueLogStaInvoiceDao extends GenericEntityDao<QueueLogStaInvoice, Long> {
	

}
