package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsInvChangeToOmsLog;

/**
 * wms库存变更日志数据库接口
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface WmsInvChangeToOmsLogDao extends GenericEntityDao<WmsInvChangeToOmsLog, Long> {


}
