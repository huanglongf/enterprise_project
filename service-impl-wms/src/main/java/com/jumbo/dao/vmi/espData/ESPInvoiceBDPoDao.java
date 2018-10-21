package com.jumbo.dao.vmi.espData;



import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPInvoiceBDPo;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface ESPInvoiceBDPoDao extends GenericEntityDao<ESPInvoiceBDPo, Long> {


    @NamedQuery
    ESPInvoiceBDPo findByInvoiceAndPo(@QueryParam("po") String po, @QueryParam("invoice") String invoice);
    

    @NamedQuery
    ESPInvoiceBDPo findByInvoice(@QueryParam("invoice") String invoice);

    /**
     * 根据PO查寻发票系数
     * @param po
     * @param invoice
     * @return
     */
    @NamedQuery
    List<ESPInvoiceBDPo> findByPo(@QueryParam("po") String po);

    @NativeQuery(pagable = true)
    Pagination<ESPInvoiceBDPo> findESPPoInvoiceBD(int start, int size, @QueryParam(value = "po") String po, @QueryParam(value = "invoiceNum") String invoiceNum, Sort[] sorts, RowMapper<ESPInvoiceBDPo> rowMapper);
}
