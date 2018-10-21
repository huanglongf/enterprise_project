package com.bt.lmis.balance.dao;

import java.util.List;

import com.bt.lmis.balance.model.CarrierFeeFlag;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.ExpressFreightSummary;
import com.bt.lmis.balance.model.ManagementFeeDomainModel;
import com.bt.lmis.balance.model.TotalFreightDiscountDomainModel;

/** 
 * @ClassName: CarrierFeeEstimateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午4:23:19 
 * 
 * @param <T>
 */
public interface CarrierFeeEstimateMapper<T> {
	
	/**
	 * @Title: ensureCarrierFeeFlag
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: CarrierFeeFlag
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 下午4:37:35
	 */
	CarrierFeeFlag ensureCarrierFeeFlag(EstimateParam param);
	
	/**
	 * @Title: distinguishManagementFee
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 下午6:02:18
	 */
	Integer distinguishManagementFee(EstimateParam param);
	
	/**
	 * @Title: ensureExpressFreightEstimateSummary
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<ExpressFreightSummary>
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 上午10:11:01
	 */
	List<ExpressFreightSummary> ensureExpressFreightEstimateSummary(EstimateParam param);
	
	/**
	 * @Title: ensureTotalFreightDiscountDomain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<TotalFreightDiscountDomainModel>
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 下午5:28:54
	 */
	List<TotalFreightDiscountDomainModel> ensureTotalFreightDiscountDomain(EstimateParam param);
	
	/**
	 * @Title: ensureManagementFeeDomain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<ManagementDomainModel>
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 下午2:45:43
	 */
	List<ManagementFeeDomainModel> ensureManagementFeeDomain(EstimateParam param);
	
	/**
	 * @Title: addCarrierFeeEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 下午2:53:46
	 */
	void addCarrierFeeEstimate(EstimateParam param);
	
}