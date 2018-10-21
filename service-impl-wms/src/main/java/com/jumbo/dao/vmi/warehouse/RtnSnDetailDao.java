package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.RtnSnDetail;

/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public interface RtnSnDetailDao extends GenericEntityDao<RtnSnDetail, Long>{
	/**
	 * 查询出RtnSnDetail中isSend状态为空，并且inLineId有值的数据，去重复
	 * @param ids
	 * @return
	 */
	@NativeQuery
	List<Long> findRtnSnDetailSendIsNullAndInLine(SingleColumnRowMapper<Long> ids);
	
	/**
	 * 查询出RtnSnDetail中isSend状态为空，并且OutId有值的数据，去重复
	 * @param ids
	 * @return
	 */
	@NativeQuery
	List<Long> findRtnSnDetailSendIsNullAndOutLine(SingleColumnRowMapper<Long> ids);
	
	/**
	 * 根据RtnSnDetail的Id更新isSend状态为1
	 * @param rtnSnId
	 */
	@NativeUpdate
	void updateIsSendOkByRtnId(@QueryParam(value = "rtnSnId") Long rtnSnId);
}
