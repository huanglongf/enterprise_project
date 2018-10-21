package com.jumbo.dao.pms.parcel;

import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;

@Transactional
public interface SysInterfaceQueueDao extends GenericEntityDao<SysInterfaceQueue, Long> {
    
    @NamedQuery
    List<SysInterfaceQueue> findByStatusAndTypeWithSys(@QueryParam("status") SysInterfaceQueueStatus status, @QueryParam("type") SysInterfaceQueueType type,  @QueryParam("targetSys") SysInterfaceQueueSysType targetSys);
    
    @NamedQuery
    List<SysInterfaceQueue> findByStatusAndTypeWithSysAlsoCode(@QueryParam("status") SysInterfaceQueueStatus status, @QueryParam("type") SysInterfaceQueueType type,  @QueryParam("targetSys") SysInterfaceQueueSysType targetSys);
    
    @NativeUpdate
    int updateByLpcodeAndMailNo(@QueryParam("mailNo") String mailNo, @QueryParam("lpcode") String lpcode, @QueryParam("remark") String remark,@QueryParam("status") Integer status,@QueryParam("errorCode") String errorCode);
    
    @NativeUpdate
    int updateByCode(@QueryParam Map<String, Object> map);
    
}