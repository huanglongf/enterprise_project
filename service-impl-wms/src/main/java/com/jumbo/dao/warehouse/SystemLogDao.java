package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.SystemLog;

@Transactional
public interface SystemLogDao extends GenericEntityDao<SystemLog, Long> {
    
    @NativeUpdate
    void insertSystemLog(@QueryParam(value = "type") String type, @QueryParam(value = "node") String node, @QueryParam(value = "status") String status);
}
