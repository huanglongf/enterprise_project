package com.jumbo.pms.manager;

import java.util.List;

import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;

/**
 * 
 * @author ShengGe
 * 
 */
public interface SysInterfaceQueueManager {

    /**
     * 修改状态
     */
    int updateByCode(String mailNo, String lpCode, String omsOrderCode, String remark, String errorCode, Integer status);
    /**
     * 
     * @param status
     * @param type
     * @param targetSys
     * @return
     */
    List<SysInterfaceQueue> getSysInterfaceQueueList(SysInterfaceQueueStatus status, SysInterfaceQueueType type, SysInterfaceQueueSysType targetSys);
    
    /**
     * insert
     * @param parcelCode
     * @param type
     * @param targetType
     * @param status
     */
    void saveSysInterfaceQueue(String mailNo, String lpcode, String omsOrderCode, String outerOrderCode, SysInterfaceQueueType type, SysInterfaceQueueSysType targetType, SysInterfaceQueueStatus status);
    
}
