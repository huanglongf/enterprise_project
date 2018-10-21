package com.bt.lmis.balance.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;

/** 
 * @ClassName: BalanceService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月7日 下午3:55:24 
 * 
 */

public interface BalanceService {
	/**
	 * @Title: rebalance
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param data
	 * @param conId
	 * @return: List<DiffBilldeatils>
	 * @author: Ian.Huang
	 * @date: 2017年8月7日 下午8:26:39
	 */
	List<DiffBilldeatils> rebalance (List<DiffBilldeatils> data, Integer conId);
	
	/**
	 * @Title: getDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年8月7日 下午7:31:45
	 */
	// 'table_name','account_id'换成master_id,'contract_id'
	// 顺丰-key产品类型CODE value-折扣
	// 非顺丰-keyALL value-折扣
	Map<String,Object> getDiscount(Map<String,String> param);
	
	/**
	 * @Title: getSummary
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: CollectionMaster
	 * @author: Ian.Huang
	 * @date: 2017年8月7日 下午7:32:26
	 */
	List<CollectionMaster> getSummary (Map<String,String> param);
	
}
