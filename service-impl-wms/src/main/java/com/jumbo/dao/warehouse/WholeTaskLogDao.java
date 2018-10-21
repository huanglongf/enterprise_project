package com.jumbo.dao.warehouse;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WholeTaskLog;

@Transactional
public interface WholeTaskLogDao extends GenericEntityDao<WholeTaskLog, Long> {
    @NativeUpdate
    void insertTaskLog(@QueryParam(value = "type") int type, @QueryParam(value = "status") int node);
    
    @NamedQuery
    WholeTaskLog getByDate();
}
