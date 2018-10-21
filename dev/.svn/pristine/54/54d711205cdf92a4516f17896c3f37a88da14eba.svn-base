package com.bt.lmis.balance.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;

/** 
 * @ClassName: ConsumerRevenueEstimateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午3:27:22 
 * 
 * @param <T>
 */
public interface ConsumerRevenueEstimateMapper<T> {
	/**
	 * @Title: ensureContractById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Contract
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 下午3:30:06
	 */
	Contract ensureContractById(@Param("id")int id);
	
	/**
	 * @Title: querySQL
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午2:04:47
	 */
	List<Map<String, Object>> querySQL(EstimateParam param);
	
	/**
	 * @Title: countSQL
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午4:12:11
	 */
	int countSQL(EstimateParam param);
	
	/**
	 * @Title: cleanEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param tableName
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午6:16:35
	 */
	void cleanEstimate(@Param("tableName")String tableName, @Param("batchNumber")String batchNumber);

	BigDecimal sumSQL(EstimateParam param);
	
}