package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineOms;

@Transactional
public interface WmsOrderInvoiceLineOmsDao extends GenericEntityDao<WmsOrderInvoiceLineOms, Long> {
    @NativeQuery
    public List<WmsOrderInvoiceLineOms> findInvoiceId(@QueryParam(value = "invoiceId") Long invoiceId, RowMapper<WmsOrderInvoiceLineOms> rowMapper);

}
