package com.bt.lmis.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.controller.form.WarehouseExpressDataParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.SearchBean;

/** 
* @ClassName: ContractBasicinfoMapper 
* @Description: TODO(合同Dao) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午2:50:48 
* 
* @param <T> 
*/
public interface ContractBasicinfoMapper<T> extends BaseMapper<T>{
	
	/**
	 * 
	 * @Description: TODO(获取合同名称集合key-id/value-name)
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年12月16日下午2:15:37
	 */
	public List<Map<String, Object>> getContractName();
	
	public List<ContractBasicinfo> CsToCBID(@Param("contract_owner")String contract_owner);
	public List<Map<String, Object>> set_SQL(@Param("sql")String sql);
	public int get_count(@Param("sql")String sql);
	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询[分页]内容) 
	* @param @param contractBasicinfoQueryParam
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<ContractBasicinfo> findCB(ContractBasicinfoQueryParam queryParam);
	/** 
	* @Title: countPowerRecords 
	* @Description: TODO(根据条件查询[分页]条数) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int countCBRecords(ContractBasicinfoQueryParam queryParam);

	/** 
	* @Title: insert 
	* @Description: TODO(插入) 
	* @param @param cb
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
//	public int insert(ContractBasicinfo cb);
	/** 
	* @Title: update 
	* @Description: TODO(修改) 
	* @param @param cb    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
//	public void update(ContractBasicinfo cb);
	/** 
	* @Title: findById 
	* @Description: TODO(根据ID获取合同信息) 
	* @param @param id
	* @param @return    设定文件 
	* @return ContractBasicinfo    返回类型 
	* @throws 
	*/
	public ContractBasicinfo findById(@Param("id")int id);
	public List<ContractBasicinfo> find_by_cb();
	public void update_cb_validity(Map<String, Object> param)throws Exception;
	public ArrayList getSearchParam(Map<String, Object> param);
	public ArrayList getShowParam(Map<String, Object> param);
	public ArrayList<ContractBasicinfo>getPageInfo(HashMap param);
	public ArrayList<SearchBean>getCurrentParam(HashMap param);
	public void addParam(Map param)throws RuntimeException;
	public void delParam(Map param)throws RuntimeException;
	public ArrayList<SearchBean>getCurrentParamForSearch(HashMap param);
	public void upParam(Map param)throws RuntimeException;
	public Integer check_param(Map param)throws RuntimeException;
	public ResultSet getReport(Map param)throws RuntimeException;
	public void textA(@Param("name")String name);
	public void textB(@Param("name")String name);
	public List<Map<String, Object>> findZZFWFList(@Param("ym")String ym,@Param("client_code")String client_code);
	public List<Map<String, Object>> findCCFList(@Param("ym")String ym,@Param("client_code")String client_code);
	public List<Map<String, Object>> findHCFList(@Param("ym")String ym,@Param("client_code")String client_code);
	public List<Map<String, Object>> findWlPoolList(@Param("ym")String ym,@Param("client_code")String client_code);
	public List<Map<String, Object>> findDBFList(@Param("yy")String yy,@Param("mm")String mm,@Param("client_code")String client_code);
	public List<Map<String, Object>> queryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public int countQueryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public List<Map<String, Object>> findSectionDBF(Map<String, Object> param);

	/**
	 * @param conId
	 * @return
	 */
	public List<Map<String, Object>> findValidContract(@Param("conId")Integer conId);
}
