package com.bt.workOrder.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseMapper;
import com.bt.workOrder.controller.form.CaimantWKParam;
import com.bt.workOrder.controller.form.CheckWKParam;
import com.bt.workOrder.controller.form.WkTypeQueryParam;
import com.bt.workOrder.model.WkType;

/**
* @ClassName: WkTypeMapper
* @Description: TODO(WkTypeMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WkTypeMapper<T> extends BaseMapper<T> {
	
	public List<WkType> findAll(WkTypeQueryParam param);
	
	public List<Map<String,Object>> findWk_type_level(Map<String,String> param);

	public void parseCaldate();

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
	* @Title: queryCHECKWK 
	* @Description: TODO(查件工单报表分页) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> queryCHECKWK(CheckWKParam queryParam);
	
	/** 
	* @Title: countCHECKWK 
	* @Description: TODO(查件工单报表分页页数统计) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer countCHECKWK(CheckWKParam queryParam);
	
	public List<Map<String, Object>> queryCAIMANTWK(CaimantWKParam queryParam);
	
    public List<Map<String, Object>> queryCAIMANTWK2(Parameter parameter);
	
	public Integer countCAIMANTWK(CaimantWKParam queryParam);

	public String getWkTypeByCode(String wkTypeCode);

    public int countCAIMANTWK2(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> exportCaimantWKExcel(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> queryCHECKWK2(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public int countCHECKWK2(Parameter parameter);

    /**
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> exportCheckWKExcel(Parameter parameter);

}
