package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;

/**
 * @Title:FreightSospService
 * @Description: TODO(sosp运费)
 * @author Ian.Huang 
 * @date 2016年7月6日下午3:51:28
 */
@Service
public interface FreightSospService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(加载配置)
	 * @param request
	 * @param result
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月7日下午1:36:48
	 */
	public JSONObject loadConfigure(HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO(加载承运商)
	 * @param con_id
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午7:26:54
	 */
	public List<Map<String, Object>> loadCarrier(int con_id);
	
	/**
	 * 
	 * @Description: TODO(删除承运商)
	 * @param request
	 * @param result
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午5:01:47
	 */
	public JSONObject delCarrier(HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO(添加承运商)
	 * @param request
	 * @param result
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午4:59:53
	 */
	public JSONObject addCarrier(HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO(查询供应商)
	 * @param request
	 * @param res
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午3:46:47
	 */
	public JSONObject searchCarrier(HttpServletRequest request, JSONObject result);
	
}
