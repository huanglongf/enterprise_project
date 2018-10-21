package com.jumbo.dao.vmi.espData;


import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface ESPInvoicePercentageDao extends GenericEntityDao<ESPInvoicePercentage, Long> {

    @NamedQuery
    ESPInvoicePercentage findByInvoice(@QueryParam("invoice") String invoice);

    @DynamicQuery(pagable = true)
    Pagination<ESPInvoicePercentage> findInvoicePercentList(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("invoiceNum") String invoiceNum,
            @QueryParam("dutyPercentage") Double dutyPercentage, @QueryParam("miscFeePercentage") Double miscFeePercentage,@QueryParam("commPercentage") Double commPercentage, Sort[] sorts);



    @NativeQuery
    List<ESPInvoicePercentage> findESPInvoiceByPo(@QueryParam("po") String po, RowMapper<ESPInvoicePercentage> rowMapper);
    
    @NativeQuery
    List<ESPInvoicePercentage> findESPInvoiceByPoOrderByCreateTime1(@QueryParam("po") String po, RowMapper<ESPInvoicePercentage> rowMapper);

    @NativeQuery
    List<ESPInvoicePercentage> findESPInvoiceByPoOrderByCreateTime(@QueryParam("po") String po, RowMapper<ESPInvoicePercentage> rowMapper);

}
