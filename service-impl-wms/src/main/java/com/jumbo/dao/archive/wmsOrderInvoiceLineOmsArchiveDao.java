package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.wmsOrderInvoiceLineOmsArchive;

@Transactional
public interface wmsOrderInvoiceLineOmsArchiveDao extends GenericEntityDao<wmsOrderInvoiceLineOmsArchive, Long> {

}
