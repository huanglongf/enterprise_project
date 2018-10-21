package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderLine;

@Transactional
public interface CnWmsStockInOrderLineDao extends GenericEntityDao<CnWmsStockInOrderLine, Long> {

    /**
     * 查询菜鸟入库明细列表
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    List<CnWmsStockInOrderLine> getByStoreInOrderId(@QueryParam(value = "storeInOrderId") long storeInOrderId);

    /**
     * 通过订单号和入库明细行id查询菜鸟入库明细行
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    CnWmsStockInOrderLine getByOrderCodeAndItemId(@QueryParam(value = "orderCode") String refSlipCode, @QueryParam(value = "orderItemId") String orderLineNo);
}
