package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOmsLog;

/**
 * oms出库通知中间日志表
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface WmsIntransitNoticeOmsLogDao extends GenericEntityDao<WmsIntransitNoticeOmsLog, Long> {
	
}
