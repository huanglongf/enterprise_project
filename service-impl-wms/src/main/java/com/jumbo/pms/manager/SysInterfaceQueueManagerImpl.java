package com.jumbo.pms.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.pms.parcel.SysInterfaceQueueDao;
import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;
import com.jumbo.wms.manager.BaseManagerImpl;


public class SysInterfaceQueueManagerImpl extends BaseManagerImpl implements SysInterfaceQueueManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger log = LoggerFactory.getLogger(SysInterfaceQueueManagerImpl.class);

	@Autowired
	private SysInterfaceQueueDao sysInterfaceQueueDao;

	@Override
	public int updateByCode(String mailNo, String lpCode, String omsOrderCode, String remark, String errorCode, Integer status) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mailNo", mailNo);
        params.put("lpCode", lpCode);
        params.put("omsOrderCode", omsOrderCode);
        params.put("remark", remark);
        params.put("errorCode", errorCode);
        params.put("status", status);
		return sysInterfaceQueueDao.updateByCode(params);
	}

    @Override
    public List<SysInterfaceQueue> getSysInterfaceQueueList(SysInterfaceQueueStatus status, SysInterfaceQueueType type, SysInterfaceQueueSysType targetSys) {
        List<SysInterfaceQueue> queueList = sysInterfaceQueueDao.findByStatusAndTypeWithSysAlsoCode(status, type, targetSys); 
        if(queueList == null || queueList.size() <= 0){
            log.warn("getSysInterfaceQueueList queueList.size<=0");
         }
        return queueList;
    }

    @Override
    public void saveSysInterfaceQueue(String mailNo, String lpcode, String omsOrderCode, String outerOrderCode, SysInterfaceQueueType type, SysInterfaceQueueSysType targetType, SysInterfaceQueueStatus status) {
        SysInterfaceQueue queue = new SysInterfaceQueue();
        queue.setMailNo(mailNo);
        queue.setLpcode(lpcode);
        queue.setOmsOrderCode(omsOrderCode);
        queue.setOuterOrderCode(outerOrderCode);
        queue.setTargetSys(targetType);
        queue.setType(type);
        queue.setStatus(status);
        sysInterfaceQueueDao.save(queue);
    }
}
