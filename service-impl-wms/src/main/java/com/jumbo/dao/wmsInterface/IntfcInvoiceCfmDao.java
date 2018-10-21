package com.jumbo.dao.wmsInterface;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceCfm;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface IntfcInvoiceCfmDao extends GenericEntityDao<IntfcInvoiceCfm, Long> {

    @NamedQuery
    List<IntfcInvoiceCfm> getIntfcInvoiceCfmByIcId(@QueryParam("icId") Long icId);

}
