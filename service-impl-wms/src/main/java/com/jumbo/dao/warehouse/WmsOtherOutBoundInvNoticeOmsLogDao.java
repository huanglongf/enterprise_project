package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOmsLog;

/**
 * WMS其他出库通知OMS中间表
 * 
 * @author PUCK SHEN
 * 
 */
@Transactional
public interface WmsOtherOutBoundInvNoticeOmsLogDao extends GenericEntityDao<WmsOtherOutBoundInvNoticeOmsLog, Long> {
	
	
}
