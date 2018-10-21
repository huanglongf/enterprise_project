package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueueLog;

/**
 * 接口/类说明：CXC 队列日志DAO
 * @ClassName: CxcConfirmOrderQueueLogDao
 * @author LuYingMing
 * @date 2016年6月12日 下午12:16:04
 */
@Transactional
public interface CxcConfirmOrderQueueLogDao extends GenericEntityDao<CxcConfirmOrderQueueLog, Long> {


    
}
