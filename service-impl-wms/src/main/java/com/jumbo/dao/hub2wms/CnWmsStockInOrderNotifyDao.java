package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderNotify;

@Transactional
public interface CnWmsStockInOrderNotifyDao extends GenericEntityDao<CnWmsStockInOrderNotify, Long> {

    /**
     * 根据状态查找菜鸟入库中间表中入库单
     * 
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NamedQuery
    List<CnWmsStockInOrderNotify> getInboundOrdersByStatus(@QueryParam(value = "status") Integer status);

    /**
     * 根据订单号查找菜鸟入库中间表中入库单
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    CnWmsStockInOrderNotify getByOrderCode(@QueryParam(value = "orderCode") String orderCode);
}
