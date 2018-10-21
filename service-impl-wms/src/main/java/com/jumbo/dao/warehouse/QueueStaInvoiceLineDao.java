package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;

public interface QueueStaInvoiceLineDao extends GenericEntityDao<QueueStaInvoiceLine, Long> {
    @NativeQuery
    public List<QueueStaInvoiceLine> findByInvoiceId(@QueryParam(value = "InvoiceId") long InvoiceId, RowMapper<QueueStaInvoiceLine> beanPropertyRowMapper);

    /**
     * 删除数据
     * 
     * @param InvoiceId
     */
    @NativeUpdate
    void cleanDataByLineId(@QueryParam("id") Long id);

}
