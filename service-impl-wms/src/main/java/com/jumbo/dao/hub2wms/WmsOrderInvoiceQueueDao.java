package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import com.jumbo.wms.model.hub2wms.WmsOrderInvoiceQueue;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
public interface WmsOrderInvoiceQueueDao extends GenericEntityDao<WmsOrderInvoiceQueue, Long> {
    /**
     * 根据qStaId获取发票信息
     * 
     * @return
     */
    @NativeQuery
    public List<WmsOrderInvoiceQueue> findByRtnId(@QueryParam(value = "rtnId") Long rtnId, BeanPropertyRowMapperExt<WmsOrderInvoiceQueue> beanPropertyRowMapperExt);

    /**
     * 根据qStaId获取发票信息
     * 
     * @return
     */
    @NativeQuery
    public List<WmsOrderInvoiceQueue> findBySoId(@QueryParam(value = "soId") Long rtnId, BeanPropertyRowMapperExt<WmsOrderInvoiceQueue> beanPropertyRowMapperExt);

    @NativeUpdate
    public void cleanDataByOrderId(@QueryParam("id") Long id);

}
