package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.controller.form.BalanceErrorLogParam;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.JibanpaoConditionEx;
import com.bt.lmis.model.JipaoConditionEx;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;

/**
 * @Title:ExpressContractService
 * @Description: TODO(快递合同Service)  
 * @author Ian.Huang 
 * @date 2016年6月23日上午9:44:15
 */
@Service
public interface ExpressContractService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年10月11日上午11:19:16
	 */
	public Map<String, Object> findById(int con_id);
	
	/**
	 * 
	 * @Description: TODO(删除一条运费折扣启用条件记录)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月18日下午1:35:59
	 */
	public JSONObject deleteWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(新增运费折扣启用条件)
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午11:50:01
	 */
	public JSONObject saveWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载运费折扣启用条件)
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午11:11:34
	 */
	public JSONObject loadWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(新增结算错误日志)
	 * @param bEL
	 * @return
	 * @throws Exception
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月19日下午4:59:08
	 */
	public void addBalanceErrorLog(BalanceErrorLog bEL)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(检验快递配置有效性)
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月15日上午9:39:07
	 */
	public JSONObject checkUniqueValidity(HttpServletRequest request, JSONObject result)throws Exception;
	
	/**
	 * 
	 * @Description: TODO(判断是否存在记录)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月6日上午10:21:27
	 */
	public JSONObject judgeExist(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载管理费规则)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午1:49:37
	 */
	public JSONObject loadManEC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除管理费记录)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月6日上午11:56:29
	 */
	public JSONObject deleteManEC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(管理费展现)
	 * @param manECList
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午12:43:06
	 */
	public List<Map<String, Object>> manECListShow(List<Map<String, Object>> manECList);
	
	/**
	 * 
	 * @Description: TODO(保存管理费)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月3日上午10:04:23
	 */
	public JSONObject saveManEC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载特殊服务费)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月1日上午10:28:57
	 */
	public JSONObject loadSSE(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除一条保价费规则记录)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月29日上午10:59:18
	 */
	public JSONObject deleteInsuranceEC(HttpServletRequest request, JSONObject result) throws Exception;
	/**
	 * 
	 * @Description: TODO(加载保价费规则)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月29日上午10:31:18
	 */
	public JSONObject loadInsuranceEC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(保价费规则展现)
	 * @param tFDList
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午7:15:13
	 */
	public List<Map<String, Object>> iECListShow(List<Map<String, Object>> iECList);
	
	/**
	 * 
	 * @Description: TODO(保存保价费)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午3:22:20
	 */
	public JSONObject saveiEC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除一条总运费折扣规则记录)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午2:23:57
	 */
	public JSONObject deleteTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载总运费折扣)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午1:31:07
	 */
	public JSONObject loadTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(总运费折扣展现)
	 * @param tFDList
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午12:35:24
	 */
	public List<Map<String, Object>> tFDListShow(List<Map<String, Object>> tFDList);
	
	/**
	 * 
	 * @Description: TODO(新增总运费折扣)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月27日下午10:03:11
	 */
	public JSONObject saveTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载运费页面所有数据)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午1:39:47
	 */
	public JSONObject loadFreight(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除一条计费公式)
	 * @param param
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午11:05:36
	 */
	public JSONObject deletePricingFormula(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(根据ID获取一条计费公式)
	 * @param param
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午10:18:54
	 */
	public JSONObject getPricingFormula(Map<String, Object> param, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(计费公式展现)
	 * @param jbpcList
	 * @return
	 * @return: List<Map<String,String>>  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午1:59:55
	 */
	public List<Map<String, Object>> pfListShow(List<Map<String, Object>> pfList);

	/**
	 * 
	 * @Description: TODO(保存计费公式)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月10日下午9:19:13
	 */
	public JSONObject savePricingFormula(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除一条计半抛条件)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:48:44
	 */
	public JSONObject deleteJBPConditon(HttpServletRequest request, JSONObject result) throws Exception;
	public JSONObject deleteJQPConditon(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(计半抛条件展现)
	 * @param jbpcList
	 * @return
	 * @return: List<Map<String,String>>  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:49:31
	 */
	public List<Map<String, String>> jbpcListShow(List<JibanpaoConditionEx> jbpcList);
	
	/**
	 * 
	 * @Description: TODO(新增计半抛条件)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:49:52
	 */
	public JSONObject addJBPConditon(HttpServletRequest request, JSONObject result) throws Exception;
	public JSONObject addJQPConditon(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载合同信息)
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: ModelMap  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:51:46
	 */
	public ModelMap getEC(ModelMap map, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * @Description: TODO(配置快递合同 )
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月27日上午10:12:12
	 */
	public JSONObject saveECC(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(新增/更新合同主信息)
	 * @param param
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:52:49
	 */
	public JSONObject saveECM(HttpServletRequest request, JSONObject result) throws Exception;

	/**
	 * @param param
	 * @return
	 */
	QueryResult<Map<String,Object>> selectByPram(BalanceErrorLogParam param);

	/**
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryExport(YFSettlementVo map);

	/**
	 * @param map
	 * @return
	 */
	int getCount(YFSettlementVo map);

	/**
	 * @param conId
	 * @param belongTo
	 * @return
	 */
	List<JipaoConditionEx> selectAll(int conId, String belongTo);

	/**
	 * @param request
	 * @param result
	 * @return
	 */
	
}
