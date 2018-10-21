package com.bt.lmis.service;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.controller.form.SummaryQueryParam;
import com.bt.lmis.model.ExpressSummary;
import com.bt.lmis.model.tbContractBasicinfo;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:ExpressBalanceService
 * @Description: TODO(快递结算Service)
 * @author Ian.Huang 
 * @date 2016年11月9日上午11:50:56
 */
@Service
public interface ExpressBalanceService {
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年12月20日下午5:06:51
	 */
	public JSONObject exportErrorWaybill(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询所有结算明细记录)
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: QueryResult<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月27日上午9:42:06
	 */
	public QueryResult<Map<String, Object>> findAllBalanceDetail(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request)  throws Exception; 
	
	/**
	* @Title: findAllSummary
	* @Description: TODO(查询所有汇总数据)
	* @param request
	* @throws Exception
	* @return ExpressSummary
	* @throws
	*/ 
	public ExpressSummary findAllSummary(HttpServletRequest request) throws Exception; 
	
	/**
	* @Title: findAll
	* @Description: TODO(查询月汇总记录)
	* @param queryParam
	* @throws Exception
	* @return QueryResult<Summary>
	*/ 
	public QueryResult<Map<String, Object>> findAllByMonth(SummaryQueryParam queryParam) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(快递汇总)
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年11月27日下午12:05:39
	 */
	public void expressBalanceSummary();
	
	/**
	 * 
	 * @Description: TODO(快递结算)
	 * @param pool
	 * @param maxThreadNum
	 * @param recommendedSingleThreadProcessingNum
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月1日下午5:33:57
	 */
	public void expressBalance(ExecutorService pool, Integer maxThreadNum, Integer recommendedSingleThreadProcessingNum) throws Exception;

	public tbContractBasicinfo querytbContractBasicinfo(String con_id);
		
}