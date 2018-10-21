package com.jumbo.dao.wmsInterface;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceLineCfm;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface IntfcInvoiceLineCfmDao extends GenericEntityDao<IntfcInvoiceLineCfm, Long> {
    @NamedQuery
    List<IntfcInvoiceLineCfm> getIntfcInvoiceLineCfmByIicId(@QueryParam("iicId") Long iicId);
}
