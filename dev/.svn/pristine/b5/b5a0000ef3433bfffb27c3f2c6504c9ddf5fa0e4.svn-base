package com.bt.lmis.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:ExpressUsedBalanceService
 * @Description: TODO(被使用快递结算Service) 
 * @author Ian.Huang 
 * @date 2016年7月28日下午1:14:11
 */
@Service
public interface ExpressUsedBalanceService {
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午8:19:15
	 */
	public JSONObject exportErrorWaybill(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: File  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午7:04:31
	 */
	public File exportUsedDetailExcel(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细记录)
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月1日下午1:11:28
	 */
	public QueryResult<Map<String, Object>> findAllBalanceDetail(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request)
			throws Exception;
	
	/**
	 * 
	 * @Description: TODO(按月份结算主体合同查询记录)
	 * @param con_id
	 * @param balance_month
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月1日上午10:43:59
	 */
	public List<Map<String, Object>> selectRecordsBySubject(String con_id, String balance_month);
	
	/**
	 * 
	 * @Description: TODO(被使用快递结算汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午3:00:52
	 */
	public Boolean expressUsedBalanceSummary(ContractBasicinfo cb, String ym) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(客户店铺快递结算)
	 * @param pool
	 * @param maxThreadNum
	 * @param recommendedSingleThreadProcessingNum
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月1日下午5:34:35
	 */
	public void expressUsedBalance(ExecutorService pool, Integer maxThreadNum, Integer recommendedSingleThreadProcessingNum) throws Exception;
		
}