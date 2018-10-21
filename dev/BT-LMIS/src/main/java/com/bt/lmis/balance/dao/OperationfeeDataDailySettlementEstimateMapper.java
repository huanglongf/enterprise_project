package com.bt.lmis.balance.dao;

import java.util.List;

import com.bt.lmis.balance.model.OperationfeeDataDailySettlementEstimate;
import com.bt.lmis.code.BaseMapper;

/**
 * 
 * @author jindong.lin
 *
 */
public interface OperationfeeDataDailySettlementEstimateMapper extends BaseMapper<OperationfeeDataDailySettlementEstimate> {
    int deleteByPrimaryKey(Integer id);

    int insert(OperationfeeDataDailySettlementEstimate record);

    int insertSelective(OperationfeeDataDailySettlementEstimate record);

    OperationfeeDataDailySettlementEstimate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationfeeDataDailySettlementEstimate record);

    int updateByPrimaryKey(OperationfeeDataDailySettlementEstimate record);

	List<OperationfeeDataDailySettlementEstimate> findByEntity(OperationfeeDataDailySettlementEstimate pfd);

	int countByEntity(OperationfeeDataDailySettlementEstimate pfd);
}