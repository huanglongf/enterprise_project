package com.bt.lmis.balance.dao;

import com.bt.lmis.balance.model.EstimateParam;

/** 
 * @ClassName: TempTableMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月8日 下午7:45:00 
 * 
 * @param <T>
 */
public interface TempTableMapper<T> {
	
	/**
	 * @Title: createTempExpressSettlementDetailTable
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月9日 下午7:42:56
	 */
	void createTempExpressSettlementDetailTable(EstimateParam param);
	
	/**
	 * @Title: dropTempExpressSettlementDetailTable
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月9日 下午7:43:45
	 */
	void dropTempExpressSettlementDetailTable(EstimateParam param);
	
}