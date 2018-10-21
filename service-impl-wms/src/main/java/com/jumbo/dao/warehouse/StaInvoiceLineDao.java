package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.StaInvoiceLine;

@Transactional
public interface StaInvoiceLineDao extends GenericEntityDao<StaInvoiceLine, Long> {

    /**
     * 根据发票头查询发票明细
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<StaInvoiceLine> getByStaInvoiceId(@QueryParam("staInvoiceId") Long staInvoiceId);

    /**
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaInvoiceLine> findSkuAndQtyByInvoice(@QueryParam("invoiceId") Long id, BeanPropertyRowMapper<StaInvoiceLine> beanPropertyRowMapper);

}
