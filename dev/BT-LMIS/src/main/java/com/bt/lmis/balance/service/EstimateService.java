package com.bt.lmis.balance.service;

import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.model.EstimateResult;

/** 
 * @ClassName: ExpressExpenditureEstimateService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月15日 下午7:14:42 
 * 
 */
public interface EstimateService {
	
	/**
	 * @Title: cleanEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchEstimate
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午6:21:43
	 */
	void cleanEstimate(BatchEstimate batchEstimate);
	
	/**
	 * @Title: batchEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchEstimate
	 * @throws Exception
	 * @return: EstimateResult
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午8:27:31
	 */
	EstimateResult batchEstimate(BatchEstimate batchEstimate) throws Exception;
	
}
