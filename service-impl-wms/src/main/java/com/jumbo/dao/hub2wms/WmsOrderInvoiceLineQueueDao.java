package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceLineQueue;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
public interface WmsOrderInvoiceLineQueueDao extends GenericEntityDao<WmsOrderInvoiceLineQueue, Long> {
    @NativeQuery
    public List<WmsOrderInvoiceLineQueue> findByInvoiceId(@QueryParam(value = "InvoiceId") long InvoiceId, RowMapper<WmsOrderInvoiceLineQueue> beanPropertyRowMapper);

    /**
     * 
     * @param id
     * @param lineNo
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WmsOrderInvoiceLineQueue> getAllInvoiceLineByLineNo(@QueryParam("orderId") Long id, @QueryParam("lineNo") String lineNo, BeanPropertyRowMapper<WmsOrderInvoiceLineQueue> beanPropertyRowMapper);

    @NativeUpdate
    public void cleanDataByOrderId(@QueryParam("id") Long id);

}
