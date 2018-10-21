package com.bt.radar.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ReceiveDeadlineQueryParam;
import com.bt.radar.model.ReceiveDeadline;

/**
 * @Title:ReceiveDeadlineService
 * @Description: TODO(揽件截止时间Service)
 * @author Ian.Huang 
 * @date 2016年8月25日下午1:58:04
 */
@Service
public interface ReceiveDeadlineService<T> extends BaseService<T> {
	
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
	 * @param receiveDeadline
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午1:22:57
	 */
	public JSONObject save(JSONObject result, ReceiveDeadline receiveDeadline, HttpServletRequest request) throws Exception;
	
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
	 * @param receiveDeadlineQueryParam
	 * @return
	 * @return: ReceiveDeadlineQueryParam  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午2:00:10
	 */
	public QueryResult<Map<String, Object>> toList(ReceiveDeadlineQueryParam receiveDeadlineQueryParam);
	
}
