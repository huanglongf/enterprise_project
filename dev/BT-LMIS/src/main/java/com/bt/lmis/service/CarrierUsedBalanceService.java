package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bt.lmis.model.ContractBasicinfo;

/**
 * @Title:CarrierUsedBalanceService
 * @Description: TODO(客户店铺使用承运商结算)
 * @author Ian.Huang 
 * @date 2016年8月22日下午1:31:06
 */
@Service
public interface CarrierUsedBalanceService {

	/**
	 * 
	 * @Description: TODO(查询相应记录)
	 * @param con_id
	 * @param balance_month
	 * @throws Exception
	 * @return List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年9月23日上午9:32:21
	 */
	public List<Map<String, Object>> getRecords(String con_id, String balance_month);
	
	/**
	 * 
	 * @Description: TODO(被使用承运商结算汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午3:05:13
	 */
	public void carrierUsedSummary(ContractBasicinfo cb, String ym) throws Exception;
		
}
