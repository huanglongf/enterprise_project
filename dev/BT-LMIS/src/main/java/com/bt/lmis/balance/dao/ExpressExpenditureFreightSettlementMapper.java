package com.bt.lmis.balance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.ExpressExpenditureFreightSettlementRule;

/** 
 * @ClassName: ExpressExpenditureFreightSettlementMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月5日 下午4:04:37 
 * 
 * @param <T>
 */
public interface ExpressExpenditureFreightSettlementMapper<T> {
	
	/**
	 * @Title: ensureExpressExpenditureFreightSettlementRule
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param title
	 * @param conId
	 * @return: List<ExpressExpenditureFreightSettlementRule>
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午2:44:39
	 */
	List<ExpressExpenditureFreightSettlementRule> ensureExpressExpenditureFreightSettlementRule(String title,@Param("conId")Integer[] conId);
	
	/**
	 * @Title: cleanTempTable
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param title
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午3:21:05
	 */
	void cleanTempTable(String title);
	
	/**
	 * @Title: move2TempTable
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param title
	 * @param tableName
	 * @param deadline
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午2:44:47
	 */
	void move2TempTable(String title,@Param("tableName")String tableName,@Param("deadline")String deadline,@Param("batchNumber")int batchNumber);

	/**
	 * @Title: countTempTable
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param title
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午3:04:41
	 */
	int countTempTable(String title);
	
	/**
	 * @Title: endowOffer
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param title
	 * @param rule
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月7日 下午6:00:32
	 */
	void endowOffer(String title, @Param("rule")ExpressExpenditureFreightSettlementRule rule);
	
}
