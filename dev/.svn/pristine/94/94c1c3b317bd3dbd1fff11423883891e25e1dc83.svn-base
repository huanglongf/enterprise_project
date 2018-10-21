package com.bt.lmis.balance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.DomainModel;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.ExpressExpenditureDiscountSwitch;
import com.bt.lmis.balance.model.ExpressExpenditureEstimateProductTypeSummary;
import com.bt.lmis.balance.model.ExpressExpenditureEstimateSummary;
import com.bt.lmis.balance.model.TotalFreightDiscountDomainModel;

/** 
 * @ClassName: ExpressExpenditureEstimateMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月9日 下午2:28:18 
 * 
 * @param <T>
 */
public interface ExpressExpenditureEstimateMapper<T> {
	
	/**
	 * @Title: ensureContractById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Contract
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午8:02:31
	 */
	Contract ensureContractById(@Param("id")Integer id);
	
	/**
	 * @Title: copyDetailToTempInDomain
	 * @Description: TODO(结算明细向临时表复制)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月9日 下午7:52:25
	 */
	void copyDetailToTempInDomain(EstimateParam param);
	
	/**
	 * @Title: resetDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月27日 下午5:00:27
	 */
	void resetDiscount(EstimateParam param);
	
	/**
	 * @Title: expressExpenditureEstimateSummary
	 * @Description: TODO(于圈定时间范围内结算明细汇总)
	 * @param param
	 * @return: List<ExpressExpenditureSummary>
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 上午10:50:34
	 */
	void expressExpenditureEstimateSummary(EstimateParam param);
	
	/**
	 * @Title: summaryByProductType
	 * @Description: TODO(明细汇总按产品类型划分汇总)
	 * @param param
	 * @return: List<ExpressExpenditureEstimateProductTypeSummary>
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午7:56:11
	 */
	List<ExpressExpenditureEstimateProductTypeSummary> summaryByProductType(EstimateParam param);
	
	/**
	 * @Title: getDiscountSwitch
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: ExpressExpenditureDiscountSwitch
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午5:41:46
	 */
	ExpressExpenditureDiscountSwitch getDiscountSwitch(EstimateParam param);
	
	/**
	 * @Title: ensureWaybillDiscountDomain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<DomainModel>
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午8:47:54
	 */
	List<DomainModel> ensureWaybillDiscountDomain(EstimateParam param);
	
	/**
	 * @Title: detailDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月11日 上午10:27:32
	 */
	void detailDiscount(EstimateParam param);
	
	/**
	 * @Title: summaryDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月11日 上午10:39:44
	 */
	void summaryDiscount(EstimateParam param);
	
	/**
	 * @Title: ensureTotalFreightDiscountDomain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<TotalFreightDiscountDomainModel>
	 * @author: Ian.Huang
	 * @date: 2017年5月11日 下午1:58:21
	 */
	List<TotalFreightDiscountDomainModel> ensureTotalFreightDiscountDomain(EstimateParam param);
	
	/**
	 * @Title: getExpressExpenditureEstimateSummary
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<ExpressExpenditureEstimateSummary>
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午2:37:51
	 */
	List<ExpressExpenditureEstimateSummary> getExpressExpenditureEstimateSummary(EstimateParam param);
	
	/**
	 * @Title: getExpressExpenditureEstimateDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午3:03:53
	 */
	List<Map<String, Object>> getExpressExpenditureEstimateDetail(EstimateParam param);
	
	/**
	 * @Title: countExpressExpenditureEstimateDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午3:12:27
	 */
	Integer countExpressExpenditureEstimateDetail(EstimateParam param);
	
	/**
	 * @Title: delDetailInTempByBatchNumber
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月12日 下午4:34:56
	 */
	void killTempDetail(EstimateParam param);
	
	/**
	 * @Title: delExpressExpenditureEstimateByBatchNumber
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月12日 下午7:29:37
	 */
	void delExpressExpenditureEstimateByBatchNumber(EstimateParam param);
	
	/**
	 * @Title: cleanEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param batchNumber
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午9:03:26
	 */
	void cleanEstimate(@Param("batchNumber")String batchNumber);
	
}