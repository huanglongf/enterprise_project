package com.bt.lmis.balance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.controller.form.EstimateQueryParam;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.model.Estimate;
import com.bt.lmis.balance.model.EstimateContract;

/** 
 * @ClassName: EstimateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月19日 下午2:43:53 
 * 
 */
public interface EstimateMapper<T> {
	
	/**
	 * @Title: queryEstimateByContract
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param contract_id
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午5:49:04
	 */
	List<String> queryEstimateByContract(@Param("contract_id")String contract_id);
	
	/**
	 * @Title: queryEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午5:52:35
	 */
	List<Map<String, Object>> queryEstimate(EstimateQueryParam param);
	
	/**
	 * @Title: queryContractByEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param estimate_id
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午6:04:13
	 */
	List<String> queryContractByEstimate(@Param("estimate_id")String estimate_id);
	
	/**
	 * @Title: countEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午5:53:04
	 */
	int countEstimate(EstimateQueryParam param);
	
	/**
	 * @Title: shiftContractByType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年7月27日 下午1:48:48
	 */
	List<Map<String, Object>> shiftContractByType(Parameter parameter);
	
	/**
	 * @Title: ensureMaxRank
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午2:51:04
	 */
	Integer ensureMaxRank();
	
	/**
	 * @Title: add
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param eEstimate
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午3:35:51
	 */
	void add(Estimate estimate);
	
	/**
	 * @Title: addContract
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param contract
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午3:36:30
	 */
	void addContract(List<EstimateContract> contract);
	
	/**
	 * @Title: ensurePriorityEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: Estimate
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午5:37:56
	 */
	Estimate ensurePriorityEstimate();
	
	/**
	 * @Title: ensureContractId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param estimate_id
	 * @return: List<Integer>
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午5:45:53
	 */
	List<Integer> ensureContractId(@Param("estimate_id")String estimate_id);
	
	/**
	 * @Title: imgEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午8:53:07
	 */
	void ingEstimate(@Param("batchNumber")String batchNumber);
	
	/**
	 * @Title: finEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午8:51:27
	 */
	void finEstimate(@Param("batchNumber")String batchNumber);
	
	/**
	 * @Title: updateStatusAndExitEstimateQueue
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchStatus
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午7:59:46
	 */
	void errEstimate(@Param("batchNumber")String batchNumber);
	
	/**
	 * @Title: canEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param rank
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午8:55:01
	 */
	void canEstimate(@Param("rank")int rank, @Param("batchNumber")String batchNumber);
	
	/**
	 * @Title: ensureEstimateByBatchNumber
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchNumber
	 * @return: Estimate
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午2:19:32
	 */
	Estimate ensureEstimateByBatchNumber(@Param("batchNumber")String batchNumber);
	
	/**
	 * @Title: delEstimateById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午2:23:54
	 */
	void delEstimateById(@Param("id") String id);
	
	/**
	 * @Title: resEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param rank
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午6:35:25
	 */
	void resEstimate(@Param("rank")int rank, @Param("batchNumber")String batchNumber);
	
}