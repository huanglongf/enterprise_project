package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnStockInItemInventory;

@Transactional
public interface CnStockInItemInventoryDao extends GenericEntityDao<CnStockInItemInventory, Long> {
    /**
     * 通过入库明细查询对应库存
     * 
     * @param lineId
     * @return
     */
    @NamedQuery
    List<CnStockInItemInventory> getByStockInItemLineId(@QueryParam(value = "lineId") long lineId);
}
