package com.bt.lmis.service;



import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.controller.form.InvitationQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.InvitationBean;
import com.bt.lmis.page.QueryResult;

/** 
* @ClassName: ContractBasicinfoService 
* @Description: TODO(合同Service) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:07:02 
*  
*/
public interface InvitationDataService<T> extends BaseService<T> {

	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询合同分页) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return QueryResult<ContractBasicinfo>    返回类型 
	* @throws 
	*/
	public QueryResult<InvitationBean> findAll(InvitationQueryParam queryParam);


}
