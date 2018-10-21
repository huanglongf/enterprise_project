package com.bt.lmis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.model.ExpressBalancedData;

/**
 * @Title:ExpressBalancedDataMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年10月31日下午5:41:34
 */
public interface ExpressBalancedDataMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(导出明细)
	 * @param queryParam
	 * @return: List<?>  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午3:20:32
	 */
	public List<?> findDetail(ExpressBalanceDetialQueryParam queryParam);
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细数量)
	 * @param eQP
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月27日上午10:23:38
	 */
	public Integer countAllBalancedData(ExpressBalanceDetialQueryParam eQP);
	
	/**
	 * @Title: getExpressSettlementDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param eQP
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月16日 上午10:44:10
	 */
	List<Map<String, Object>> getExpressSettlementDetail(ExpressBalanceDetialQueryParam eQP);
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细集合)
	 * @param eQP
	 * @return
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月27日上午10:17:04
	 */
	public List<Map<String, Object>> selectAllBalancedData(ExpressBalanceDetialQueryParam eQP);
	
	/**
	 * 
	 * @Description: TODO(获取汇总（顺丰不包含折扣，非顺丰不包含总运费折扣）)
	 * @param transport_name
	 * @param balance_start_date
	 * @param balance_end_date
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年10月12日下午12:43:51
	 */
	public List<Map<String, Object>> getSummary(
			@Param("transport_name")String transport_name,
			@Param("balance_start_date")String balance_start_date,
			@Param("balance_end_date")String balance_end_date);
	
	/**
	 * 
	 * @Description: TODO
	 * @param discount
	 * @param transport_name
	 * @param product_code
	 * @param balance_start_date
	 * @param balance_end_date
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午8:09:45
	 */
	public Integer updateBatch(
			@Param("discount")BigDecimal discount,
			@Param("transport_name")String transport_name,
			@Param("product_code")String product_code,
			@Param("balance_start_date")String balance_start_date,
			@Param("balance_end_date")String balance_end_date);
	
	/**
	* @Title: insertBatch
	* @Description: TODO(批量插入)
	* @param eBDs
	* @return Integer 返回类型
	* @throws
	*/ 
	public Integer insertBatch(List<ExpressBalancedData> eBDs);
	
}
