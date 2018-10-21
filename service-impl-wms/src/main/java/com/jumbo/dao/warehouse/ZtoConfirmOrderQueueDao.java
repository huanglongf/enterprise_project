package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ZtoConfirmOrderQueue;

import loxia.dao.GenericEntityDao;
import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
/**
 * 
 * @author 任朝琪
 *
 */
@Transactional
public interface ZtoConfirmOrderQueueDao extends GenericEntityDao<ZtoConfirmOrderQueue, Long> {
    @NamedQuery
    public List<ZtoConfirmOrderQueue> findStaZtoQueue();
    
    @NamedQuery
    List<ZtoConfirmOrderQueue> findExtOrder(@QueryParam("count") Long count);
}
