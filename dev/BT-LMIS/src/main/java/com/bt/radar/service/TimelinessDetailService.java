package com.bt.radar.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.TimelinessDetailQueryParam;
import com.bt.radar.model.TimelinessDetail;

/**
 * @Title:TimelinessDetailService
 * @Description: TODO(时效明细Service) 
 * @author Ian.Huang 
 * @date 2016年8月30日上午10:59:03
 */
@Service
public interface TimelinessDetailService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(移动时效明细节点)
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月31日上午10:00:01
	 */
	public JSONObject move(JSONObject result, HttpServletRequest request) throws Exception;
	
	/**
	* @Title: shiftStatus
	* @Description: TODO(停用时效明细)
	* @param @param result
	* @param @param request
	* @param @return
	* @param @throws Exception    设定文件
	* @return JSONObject    返回类型
	* @throws
	*/ 
	public JSONObject shiftStatus(JSONObject result, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询需要显示的值)
	 * @param request
	 * @return
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午3:33:50
	 */
	public HttpServletRequest toForm(HttpServletRequest request);
	
	/**
	 * 
	 * @Description: TODO(保存记录)
	 * @param result
	 * @param timelinessDetail
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午1:22:57
	 */
	public JSONObject save(JSONObject result, TimelinessDetail timelinessDetail, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除记录)
	 * @param result
	 * @param request
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月26日下午5:20:51
	 */
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(显示揽件截止时间列表)
	 * @param TimelinessDetailQueryParam
	 * @return
	 * @return: TimelinessDetailParam  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午2:00:10
	 */
	public QueryResult<Map<String, Object>> toList(TimelinessDetailQueryParam timelinessDetailParam);
	
}
