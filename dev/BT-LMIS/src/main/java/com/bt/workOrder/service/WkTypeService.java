package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.form.CaimantWKParam;
import com.bt.workOrder.controller.form.CheckWKParam;

public interface WkTypeService<T> extends BaseService<T> {

	/** 
	* @Title: findByWKTYPE 
	* @Description: TODO(查询所有工单类型) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findByWKTYPE();
	
	/** 
	* @Title: findByWKERROR 
	* @Description: TODO(查询所有工单异常) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findByWKERROR(@Param("code")String code);

	/** 
	* @Title: exportWK 
	* @Description: TODO(查件工单报表导出) 
	* @param @param param
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>> exportWK(Map<String,String> param);
	public List<Map<String,Object>> exportWKSP(Map<String,String> param);

	/** 
	* @Title: query 
	* @Description: TODO(分页查询查件工单) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public QueryResult<Map<String, Object>> query(CheckWKParam queryParam);
	public QueryResult<Map<String, Object>> query(CaimantWKParam queryParam);

	public String getWkTypeByCode(String wkTypeCode);


    /**
     * @param parameter
     * @return
     */
    public QueryResult<Map<String, Object>> queryCaimantWK(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> exportCaimantWKExcel(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public QueryResult<Map<String, Object>> queryCheckWK(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> exportCheckWKExcel(Parameter parameter);
	
}
