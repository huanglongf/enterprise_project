package com.bt.lmis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.controller.form.SummaryQueryParam;
import com.bt.lmis.model.BalanceSummaryEx;
import com.bt.lmis.model.tbContractBasicinfo;

/**
* @ClassName: BalanceSummaryExMapper
* @Description: TODO(结算汇总DAO)
* @author Ian.Huang
* @date 2016年7月21日 下午10:22:22
* @param <T>
*/
public interface BalanceSummaryExMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(查询所有结算汇总数据)
	 * @param param
	 * @return: List<BalanceSummaryEx>  
	 * @author Ian.Huang 
	 * @date 2016年7月26日上午10:08:32
	 */
	List<BalanceSummaryEx> selectAllBallanceSummary(Map<String, Object> param);
	
	/**
	* @Title: countRecordsByMonth
	* @Description: TODO(查询按月记录的数量)
	* @param @param queryParam
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	Integer countRecordsByMonth(SummaryQueryParam queryParam);
	
	/**
	* @Title: findAllByMonth
	* @Description: TODO(查询按月查询的所有记录)
	* @param @param queryParam
	* @param @return    设定文件
	* @return List<Map<String,Object>>    返回类型
	* @throws
	*/ 
	List<Map<String, Object>> findAllByMonth(SummaryQueryParam queryParam);
	
	/**
	 * 
	 * @Description: TODO(查询是否存在汇总记录)
	 * @param contract_owner
	 * @param balance_month
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月25日上午11:06:15
	 */
	Integer selectRecordsExist(@Param("contract_owner")String contract_owner, @Param("balance_month")String balance_month);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ver_id
	 * @param express
	 * @param balance_month
	 * @param product_type
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年10月13日上午11:52:47
	 */
	Map<String, Object> getSummary(
			@Param("ver_id")Integer ver_id, 
			@Param("contract_owner")String contract_owner, 
			@Param("ym")String ym,
			@Param("product_type")String product_type);
	/**
	 * @param ver_id
	 * @param contract_owner
	 * @param ym
	 * @return
	 */
	List<Map<String, Object>> getSummarySF(
			@Param("ver_id")Integer ver_id, 
			@Param("contract_owner")String contract_owner, 
			@Param("ym")String ym);
	
	Map<String, Object> getSummaryGroupbyType(
			@Param("contract_owner")String contract_owner, 
			@Param("product_type")String product_type,
			@Param("master_id")String master_id, 
			@Param("table_name")String table_name
			);
	
	/**
	 * 
	 * @Description: TODO
	 * @param discount
	 * @param ver_id
	 * @param contract_owner
	 * @param ym
	 * @param product_type
	 * @param insurance_contain
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午9:37:32
	 */
	Integer updateBatch(
			@Param("discount")BigDecimal discount, 
			@Param("ver_id")Integer ver_id, 
			@Param("contract_owner")String contract_owner,
			@Param("ym")String ym,
			@Param("product_type")String product_type,
			@Param("insurance_contain")Integer insurance_contain
			); 
	
	/**
	 * 
	 * @Description: TODO(更新结算汇总记录)
	 * @param dicount
	 * @param total_fee
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月22日下午5:39:25
	 */
	Integer updateBalanceSummaryEx(@Param("discount")BigDecimal discount, @Param("total_fee")BigDecimal total_fee, @Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param ver_id
	 * @param express
	 * @param balance_month
	 * @return
	 * @return: List<BalanceSummaryEx>  
	 * @author Ian.Huang 
	 * @date 2016年7月22日下午2:47:00
	 */
	List<BalanceSummaryEx> selectRecords(
			@Param("ver_id")Integer ver_id, 
			@Param("express")String express, 
			@Param("balance_month")String balance_month,
			@Param("product_type")String product_type,
			@Param("cost_center")String cost_center,
			@Param("store")String store,
			@Param("warehouse")String warehouse
			);
	
	/**
	 * 
	 * @Description: TODO(查询产品类型)
	 * @param ver_id
	 * @param express
	 * @param balance_month
	 * @return
	 * @return: List<String>  
	 * @author Ian.Huang 
	 * @date 2016年7月22日下午3:13:52
	 */
	List<String> selectProductType(
			@Param("ver_id")Integer ver_id, 
			@Param("express")String express, 
			@Param("balance_month")String balance_month
			);
	
	/**
	* @Title: selectCurrentVerId
	* @Description: TODO(查询当前版本号)
	* @param @param express
	* @param @param balance_month
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	Integer selectCurrentVerId(@Param("express")String express, @Param("balance_month")String balance_month);
	 
	/**
	 * 
	 * @Description: TODO(批量插入快递汇总记录)
	 * @param bSEs
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月17日下午3:23:32
	 */
	Integer insertBatch(List<BalanceSummaryEx> bSEs);

	tbContractBasicinfo querytbContractBasicinfo(@Param("con_id")String con_id);

	/**
	 * @param contract_owner
	 * @return
	 */
	List<String> selectProductTypeByExpress(
			@Param("table_name")String table_name,
			@Param("contract_owner")String contract_owner,
			@Param("master_id")String master_id);
	
	/**
	 * @Title: returnBill
	 * @Description: TODO(删除账单汇总数据)
	 * @param express
	 * @param ym
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年11月25日 下午5:46:10
	 */
	int deleteSummary(@Param("express")String express, @Param("ym")String ym);
	
	/**
	 * @Title: returnDetail
	 * @Description: TODO(还原账单明细数据)
	 * @param express
	 * @param exCycleStart
	 * @param exCycleEnd
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年11月25日 下午5:59:40
	 */
	int returnDetail(@Param("express")String express, @Param("billCycleStart")String billCycleStart, @Param("billCycleEnd")String billCycleEnd);
	
}
