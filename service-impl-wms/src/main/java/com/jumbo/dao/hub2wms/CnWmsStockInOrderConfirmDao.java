package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderConfirm;

@Transactional
public interface CnWmsStockInOrderConfirmDao extends GenericEntityDao<CnWmsStockInOrderConfirm, Long> {
    /**
     * 通过订单号查询菜鸟入库确认单
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    CnWmsStockInOrderConfirm getByOrderCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "status") String status);

    /**
     * 通过状态查询菜鸟入库确认单
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    List<CnWmsStockInOrderConfirm> getByStatus(@QueryParam(value = "status") String status);
}
