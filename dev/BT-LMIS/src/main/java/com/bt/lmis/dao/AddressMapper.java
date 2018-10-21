package com.bt.lmis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.AddressQueryParam;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.JkErrorQuery;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.JkErrorAddressBean;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:AddressMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月16日上午10:24:58
 */
public interface AddressMapper<T> extends BaseMapper<T>{
	public List<Map<String,Object>>getContract(Map<String, Object>  param);
	public ArrayList<?> getTabData(Map param);
	public ArrayList<?> getGtTab(Map param);
	public ArrayList<?> getBusTypeTab(Map param);
	public void updateXzMainData(Map param);
	public void updateTable(Map param);
	public void updateGtTable(Map param);
	public void updateZcTable(Map param);
	public void delTabData(Map param);
	public void delTabData_gt(Map param);
	public void delTabData_bs(Map param);
	public void updateMainData(Map param);
	public void add(Map param);
	public List<T> findAllErrorData(QueryParameter queryParameter);
	public Integer selectErrorCount(QueryParameter queryParameter);
	public Integer getErrorCount();
	public List<Map<String,String>>getErrorData(Map<String,Object>param);
	public void update_data(Map<String,String>param);
	
	public List<T> findAllDetailData(QueryParameter queryParameter);
	public Integer selectDetailCount(QueryParameter queryParameter);
	public void  transDataPro_add(Map<String,String>param);
	public void add_error_address(JkErrorAddressBean bean);
	public Integer get_if_exist_address(JkErrorAddressBean bean);
	public ArrayList<Map<String,String>>get_address_exist_system(Map<String,String>param);
	
	
	public void updateErrorData(Map param);
	public List<Map<String, Object>>query_export(JkErrorQuery queryParam);
	public List<Map<String, Object>>exportData(JkErrorQuery queryParam);
	
	
	public  List<T> getImportMainInfo(ErrorAddressQueryParam queryParam);
	public Integer getImportMainInfoCount(ErrorAddressQueryParam queryParameter);
	
	
	
	 public void insertOrder(List<ErrorAddress>sub_list);
	 public void update_iscomplete(Map<String,String>param);
	 public Map<String,String>transDataPro(Map<String,String>param);
	 public void deleteImport(String bat_id);
	 public List<ErrorAddress> getImportInfo(String bat_id);
	/**
	 * 
	 * @Description: TODO
	 * @param addressQueryParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午7:17:19
	 */
	public List<Map<String, Object>> exportAddress(AddressQueryParam addressQueryParam);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午4:29:40
	 */
	public Map<String, Object> getAddress(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午3:49:09
	 */
	public Integer deleteBatch(Integer[] ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月16日上午10:27:15
	 */
	public Integer count(QueryParameter queryParameter);
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParameter
	 * @return: List<T>  
	 * @author Ian.Huang 
	 * @date 2016年12月16日上午10:27:10
	 */
	public List<T> query(QueryParameter queryParameter);
	
}
