package com.bt.lmis.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.OperationFeeDataSettlement;

/**
 * @Title:OperationFeeDataSettlementMapper
 * @Description: TODO(操作费结算费用DAO)
 * @author Ian.Huang 
 * @date 2016年8月2日下午1:58:58
 */
public interface OperationFeeDataSettlementMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(查询操作费结算记录)
	 * @param contract_id
	 * @param settle_period
	 * @return
	 * @return: OperationFeeDataSettlement  
	 * @author Ian.Huang 
	 * @date 2016年8月2日下午2:00:19
	 */
	public OperationFeeDataSettlement selectRecord(@Param("contract_id")int contract_id, @Param("settle_period")String settle_period);
	
}
