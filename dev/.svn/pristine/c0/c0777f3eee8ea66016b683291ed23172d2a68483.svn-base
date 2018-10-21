package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.CarrierUsedSummary;

/**
 * @Title:CarrierUsedSummaryMapper
 * @Description: TODO(被使用承运商汇总DAO)
 * @author Ian.Huang 
 * @date 2016年8月22日下午2:35:11
 */
public interface CarrierUsedSummaryMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(获取被使用承运商汇总)
	 * @param con_id
	 * @param balance_month
	 * @return Map<String, Object>
	 * @author Ian.Huang 
	 * @date 2016年8月22日下午2:44:54
	 */
	public List<Map<String, Object>> getCarrierUsedSummary(@Param("con_id")Integer con_id, @Param("balance_subject")String balance_subject, @Param("balance_month")String balance_month);
	
	/**
	 * 
	 * @Description: TODO(新建被使用承运商汇总记录)
	 * @param carrierUsedSummary
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月22日下午2:41:14
	 */
	public Integer addCarrierUsedSummary(CarrierUsedSummary carrierUsedSummary);
	
	/**
	 * 
	 * @Description: TODO
	 * @param balance_month
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午1:27:42
	 */
	public Integer judgeSummaryExistOrNot(@Param("balance_month")String balance_month);
}
