package com.bt.lmis.balance.dao;

import java.util.List;

import com.bt.lmis.balance.model.ConsumerCarrier;
import com.bt.lmis.balance.model.EstimateParam;

/** 
 * @ClassName: ExpressFreightEstimateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午1:22:02 
 * 
 * @param <T>
 */
public interface ExpressFreightEstimateMapper<T> {
	
	/**
	 * @Title: ensureConsumerExpress
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<ConsumerCarrier>
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 下午1:49:05
	 */
	List<ConsumerCarrier> ensureConsumerExpress(EstimateParam param);
	
	/**
	 * @Title: expressFreightEstimateSummary
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 下午2:36:19
	 */
	void expressFreightEstimateSummary(EstimateParam param);
	
}