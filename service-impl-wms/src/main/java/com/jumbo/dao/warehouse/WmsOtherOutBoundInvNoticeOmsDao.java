package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

/**
 * WMS其他出库通知OMS中间表
 * 
 * @author PUCK SHEN
 * 
 */
@Transactional
public interface WmsOtherOutBoundInvNoticeOmsDao extends GenericEntityDao<WmsOtherOutBoundInvNoticeOms, Long> {
	
	@NativeUpdate
	void updateOtherOutBoundInvNoticeOmsByStaCode(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "status") Long status);
	
	/**
     * 校验作业单是否存在
     * 
     * @return
     */
	@NamedQuery
	WmsOtherOutBoundInvNoticeOms findOtherOutInvNoticeOmsByStaCode(@QueryParam(value = "staCode") String staCode);
	
	/**
	 * flag 3种状态标志，flag = "occupation"为占用库存，flag = "cancel"为取消占用库存 ，flag = "finish"为通知oms释放库存
	 */
	@NativeQuery
	List<Long> findAllOtherOutInvNoticeOms(@QueryParam(value = "flag") String flag, SingleColumnRowMapper<Long> singleColumnRowMapper);
	
	
	@NativeUpdate
	void deleteAllNoUsedData();
	
	@NativeQuery
	List<Long> findAllVmiAdjustNoticeOms(@QueryParam(value = "flag") String flag, SingleColumnRowMapper<Long> singleColumnRowMapper);
	
	
}
