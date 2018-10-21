package com.bt.lmis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.model.ExpressRawData;

/**
 * @Title:ExpressRawDataMapper
 * @Description: TODO(快递原始运单数据DAO)  
 * @author Ian.Huang 
 * @date 2016年9月14日下午2:36:13
 */
public interface ExpressRawDataMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午5:14:45
	 */
	public Integer countByFlag(ExpressBalanceDetialQueryParam queryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午3:26:52
	 */
	public List<Map<String, Object>> selectByFlag(ExpressBalanceDetialQueryParam queryParam);
	
	/**
	* @Title: insertRawData
	* @Description: TODO(插入原始数据)
	* @param expressRawData
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer insertRawData(ExpressRawData expressRawData);
	
	/**
	 * 
	 * @Description: TODO(更新原始数据结算状态)
	 * @param settle_flag
	 * @param settle_logistics_flag
	 * @param settle_client_flag
	 * @param settle_store_flag
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月28日下午2:56:12
	 */
	public Integer updateStatus(
			@Param("settle_flag")Integer settle_flag,
			@Param("settle_logistics_flag")Integer settle_logistics_flag,
			@Param("settle_client_flag")Integer settle_client_flag,
			@Param("settle_store_flag")Integer settle_store_flag,
			@Param("list")List<Integer> ids);
	
	/**
	 * 
	 * @Description: TODO(查询指定快递10000条合同周期内的有效数据)
	 * @param contract_type
	 * @param cost_center
	 * @param store_name
	 * @param transport_name
	 * @param contract_start_time
	 * @param contract_end_time
	 * @param type
	 * @param batch_num
	 * @return: List<ExpressRawData>  
	 * @author Ian.Huang 
	 * @date 2016年7月29日下午3:39:21
	 */
	public List<ExpressRawData> selectRecords(
			@Param("contract_type")Integer contract_type,
			@Param("cost_center")String cost_center,
			@Param("store_name")String store_name,
			@Param("transport_name")String transport_name, 
			@Param("contract_start_time")String contract_start_time,
			@Param("contract_end_time")String contract_end_time,
//			@Param("type")String type,
			@Param("type")int type,
			@Param("batch_num")int batch_num,
			@Param("jk_settle_flag_in_str")String jk_settle_flag_in_str
			);
	
	/**
	 * 
	 * @Description: TODO(查询指定快递合同周期内的有效数据量)
	 * @param contract_type
	 * @param cost_center
	 * @param store_name
	 * @param transport_name
	 * @param contract_start_time
	 * @param contract_end_time
	 * @param type
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年3月1日下午5:10:19
	 */
	public Integer countRecords(
			@Param("contract_type")Integer contract_type,
			@Param("cost_center")String cost_center,
			@Param("store_name")String store_name,
			@Param("transport_name")String transport_name, 
			@Param("contract_start_time")String contract_start_time,
			@Param("contract_end_time")String contract_end_time,
			@Param("type")int type,
			@Param("jk_settle_flag_in_str")String jk_settle_flag_in_str
			);

	/**
	 * @param map
	 * @return
	 */
	public BigDecimal getSumWeight(Map<String, String> map);
	
}
