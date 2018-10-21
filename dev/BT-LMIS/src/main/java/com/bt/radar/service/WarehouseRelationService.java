package com.bt.radar.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.WarehouseRelationQueryParam;
import com.bt.radar.model.WarehouseRelation;

/**
 * @Title:WarehouseRelationService
 * @Description: TODO(物理仓逻辑仓关系Service)
 * @author Ian.Huang 
 * @date 2016年8月31日下午3:03:35
 */
public interface WarehouseRelationService<T> extends BaseService<T> {

	/**
	 * 
	 * @Description: TODO(保存记录)
	 * @param result
	 * @param warehouseRelation
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午1:22:57
	 */
	public JSONObject save(JSONObject result, WarehouseRelation warehouseRelation, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载编辑页面值)
	 * @param request
	 * @param result
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月20日下午9:05:14
	 */
	public JSONObject toForm(HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO(删除一条物理仓逻辑仓关系记录)
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午2:23:01
	 */
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouseRelationQueryParam
	 * @return
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:13:25
	 */
	public QueryResult<Map<String, Object>> toList(WarehouseRelationQueryParam warehouseRelationQueryParam);
	
}
