package com.bt.lmis.dao;

import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.HandlingFeeRawDataQueryParam;
import com.bt.lmis.model.HandlingFeeRawData;

/** 
* @ClassName: ContractBasicinfoMapper 
* @Description: TODO(合同Dao) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午2:50:48 
* 
* @param <T> 
*/
public interface HandlingFeeRawDataMapper<T> extends BaseMapper<T>{
	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询[分页]内容) 
	* @param @param contractBasicinfoQueryParam
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<HandlingFeeRawData> findCB(HandlingFeeRawDataQueryParam queryParam);
	/** 
	* @Title: countPowerRecords 
	* @Description: TODO(根据条件查询[分页]条数) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int countCBRecords(HandlingFeeRawDataQueryParam queryParam);

}
