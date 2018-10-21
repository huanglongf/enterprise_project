package com.bt.lmis.service;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.OperatorQueryParam;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.OperatorBean;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;

/** 
* @ClassName: EmployeeService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:15 
* 
* @param <T> 
*/
public interface JkErrorService<T> extends BaseService<T>{
	public QueryResult<T> findAllErrorData(QueryParameter qr);
	public Map<String,String> updateErrorData(Map<String, String> map);
	public Map<String,String> dealjkDataPro(Map<String, String> map);
	public QueryResult<T> findjKData(QueryParameter qr);
	public void upStatus(Map<String, String> map);
	
	/**操作费**/
	public QueryResult<T> findOperatorData(QueryParameter qr);
	public List<Map<String, Object>>query_export(OperatorQueryParam queryParam);
	public List<Map<String, Object>>exportData(OperatorQueryParam queryParam);
	public QueryResult<T> getImportMainInfo(OperatorQueryParam queryParam);
	public void checkImport(String param);
	public void insertOper(List<OperatorBean>sub_list);
	public QueryResult<T> getImportDetailInfo(OperatorQueryParam queryParam);
}
