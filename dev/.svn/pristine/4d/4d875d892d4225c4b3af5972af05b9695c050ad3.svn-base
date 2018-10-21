package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.OperatorQueryParam;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.OperatorBean;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:AddressMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月16日上午10:24:58
 */
public interface JkErrorMapper<T> extends BaseMapper<T>{
	public List<T> getJkErrorOrder(QueryParameter queryParameter);
	public Integer selectJkCount(QueryParameter queryParameter);
	public List<T> getJkDataInfo(QueryParameter queryParameter);
	public Integer getJkDataCount(QueryParameter queryParameter);
	
	public Map<String,String> dealErrorDataPro(Map<String,String>param);
	public Map<String,String> dealjkDataPro(Map<String,String>param);
	public Map<String,String> refreshjkDataPro(Map<String,String>param);
	
	public void upStatus(Map<String,String>param);
	public Map<String,Object> getStatus(Map<String,String>param);
	
	
	/**操作费**/
	public List<T> findOperatorData(QueryParameter queryParameter);
	public Integer selecOperatorCount(QueryParameter queryParameter);
	public  List<T> getImportMainInfo(OperatorQueryParam queryParam);
	public Integer getImportMainInfoCount(OperatorQueryParam queryParameter);
	public List<Map<String, Object>>query_export(OperatorQueryParam queryParam);
	public List<Map<String, Object>>exportData(OperatorQueryParam queryParam);
	public void update_iscomplete(Map<String,String>param);
	public void insertOper(List<OperatorBean>sub_list);
	public List<T> findAllDetailData(QueryParameter queryParameter);
	public Integer selectDetailCount(QueryParameter queryParameter);
	
}
