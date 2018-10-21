package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.WarehouseQueryParam;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.QueryResult;

public interface WarehouseService<T> extends BaseService<T> {

	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @param request
	 * @return
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午8:03:33
	 */
	public HttpServletRequest form(Warehouse warehouse, HttpServletRequest request);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouse
	 * @param result
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午6:32:51
	 */
	public JSONObject save(Warehouse warehouse, HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO
	 * @param warehouseQueryParam
	 * @return
	 * @return: QueryResult<T>  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午4:12:57
	 */
	public QueryResult<Map<String, Object>> findAllWareHouse(WarehouseQueryParam warehouseQueryParam);
	
	public List<Map<String, Object>> findAll();
	
	/**
	 * 根据name查询
	 * @param warehouseName
	 * @return
	 */
	public Warehouse selectByWarehouseName(String warehouseName);

	/**
	 * 根据code查询
	 * @param warehouseName
	 * @return
	 */
	public Warehouse selectByWarehouseCode(String warehouseCode);

	
	/**
	 * 根据name查询
	 * @param warehouseName
	 * @return
	 */
	public List<Map<String, Object>> download(WarehouseQueryParam warehouseQueryParam);
}
