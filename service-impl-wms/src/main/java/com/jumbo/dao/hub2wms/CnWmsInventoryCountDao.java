package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnWmsInventoryCount;

@Transactional
public interface CnWmsInventoryCountDao extends GenericEntityDao<CnWmsInventoryCount, Long> {

    /**
     * 通过仓储编码查询盘点单
     * 
     * @param orderCode
     * @return
     */
    @NamedQuery
    List<CnWmsInventoryCount> getByStoreCode(@QueryParam(value = "storeCode") String storeCode, @QueryParam(value = "status") String status);

    /**
     * 通过状态查询盘点单
     * 
     * @param status
     * @return
     */
    @NamedQuery
    List<CnWmsInventoryCount> getByStatus(@QueryParam(value = "status") String status);

}
