package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueue;

@Transactional
public interface KerryConfirmOrderQueueDao extends GenericEntityDao<KerryConfirmOrderQueue, Long> {
    
    @NamedQuery
    List<KerryConfirmOrderQueue> findExtOrder(@QueryParam("count") Long count);
    
    @NamedQuery
    List<KerryConfirmOrderQueue> findKerryOrderByType(@QueryParam("type") Integer type, @QueryParam("count") Long count);
    
    @NamedQuery
    KerryConfirmOrderQueue findKerryOrderByStaCode(@QueryParam("staCode") String staCode);
}
