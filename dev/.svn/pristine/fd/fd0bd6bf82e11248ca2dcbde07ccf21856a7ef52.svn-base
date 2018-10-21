package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.model.ExpressBalancedData;

public interface ExpressUsedBalancedDataMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: List<?>  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午7:14:07
	 */
	public List<?> findDetail(ExpressBalanceDetialQueryParam queryParam);
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细数量)
	 * @param eQP
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月27日上午10:23:38
	 */
	public Integer countAllBalancedData(ExpressBalanceDetialQueryParam eQP);
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细集合)
	 * @param eQP
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月27日上午10:17:04
	 */
	public List<Map<String, Object>> selectAllBalancedData(ExpressBalanceDetialQueryParam eQP);
	
	/**
	 * 
	 * @Description: TODO(按合同，使用快递，产品类型获取汇总)
	 * @param con_id
	 * @param transport_name
	 * @param balance_start_date
	 * @param balance_end_date
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年10月19日上午11:00:54
	 */
	public List<Map<String, Object>> getSummary(
			@Param("con_id")Integer con_id,
			@Param("transport_name")String transport_name,
			@Param("balance_start_date")String balance_start_date,
			@Param("balance_end_date")String balance_end_date
			);
	
	/**
	 * 
	 * @Description: TODO(批量插入)
	 * @param eBDs
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月31日下午5:40:08
	 */
	public Integer insertBatch(List<ExpressBalancedData> eBDs);
	
}
