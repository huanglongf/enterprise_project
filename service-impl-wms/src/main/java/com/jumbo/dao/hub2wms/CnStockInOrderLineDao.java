package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnStockInOrderLine;

@Transactional
public interface CnStockInOrderLineDao extends GenericEntityDao<CnStockInOrderLine, Long> {
    /**
     * 通过入库确认单id查询菜鸟入库确认明细行
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    List<CnStockInOrderLine> getByStockInOrderConfirmId(@QueryParam(value = "stockInOrderConfirmId") long stockInOrderConfirmId);

}
