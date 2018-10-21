package com.bt.radar.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.PhysicalWarehouseQueryParam;
import com.bt.radar.model.PhysicalWarehouse;

/**
* @ClassName: PhysicalWarehouseService
* @Description: TODO(物理仓Service)
* @author Ian.Huang
* @date 2016年8月26日 上午12:07:54
*
* @param <T>
*/
public interface PhysicalWarehouseService<T> extends BaseService<T> {

	/**
	 * 
	 * @Description: TODO(保存记录)
	 * @param result
	 * @param physicalWarehouse
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午1:22:57
	 */
	public JSONObject save(JSONObject result, PhysicalWarehouse physicalWarehouse, HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载编辑页面值)
	 * @param request
	 * @return
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午3:33:50
	 */
	public HttpServletRequest toForm(HttpServletRequest request);
	
	/**
	 * 
	 * @Description: TODO(删除一条物理仓记录)
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
	 * @param physicalWarehouseQueryParam
	 * @return
	 * @return: QueryResult<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:13:25
	 */
	public QueryResult<Map<String, Object>> toList(PhysicalWarehouseQueryParam physicalWarehouseQueryParam);
	
	/**
	* @Title: getPhysicalWarehouse
	* @Description: TODO(获取所有存在物理仓)
	* @param @return    设定文件
	* @return List<PhysicalWarehouse>    返回类型
	* @throws
	*/ 
	public List<PhysicalWarehouse> getPhysicalWarehouse();
	
	public  void  reFresh();
}
