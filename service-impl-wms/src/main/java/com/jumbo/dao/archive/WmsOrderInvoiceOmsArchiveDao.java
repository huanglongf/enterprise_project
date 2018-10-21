package com.jumbo.dao.archive;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.archive.WmsOrderInvoiceOmsArchive;

@Transactional
public interface WmsOrderInvoiceOmsArchiveDao  extends GenericEntityDao<WmsOrderInvoiceOmsArchive, Long> {

}
