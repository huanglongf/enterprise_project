package com.bt.lmis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.SearchBean;
import com.bt.lmis.page.QueryResult;

/** 
* @ClassName: ContractBasicinfoService 
* @Description: TODO(合同Service) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:07:02 
*  
*/
public interface ContractBasicinfoService<T> extends BaseService<T> {

	public List<ContractBasicinfo> CsToCBID(@Param("contract_owner")String contract_owner);
	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询合同分页) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return QueryResult<ContractBasicinfo>    返回类型 
	* @throws 
	*/
	public QueryResult<ContractBasicinfo> findAll(ContractBasicinfoQueryParam queryParam);

	/** 
	* @Title: save 
	* @Description: TODO(新增) 
	* @param @param cb
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
//	public int save(ContractBasicinfo cb);
	/** 
	* @Title: update 
	* @Description: TODO(修改) 
	* @param @param cb    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
//	public void update(ContractBasicinfo cb);
	public List<ContractBasicinfo> find_by_cb();
	public List<Map<String, Object>> set_SQL(@Param("sql")String sql);
	public int get_count(@Param("sql")String sql);
	public void update_cb_validity(Map<String, Object> param)throws Exception;
	/** 
	* @Title: findById 
	* @Description: TODO(根据ID获取合同信息)   
	* @param @param id
	* @param @return    设定文件 
	* @return ContractBasicinfo    返回类型 
	* @throws 
	*/
	public ContractBasicinfo findById(@Param("id")int id);
	public ArrayList getSearchParam(Map<String, Object> map);
	public ArrayList getShowParam(Map<String, Object> map);
	public ArrayList<ContractBasicinfo>getPageInfo(HashMap param);
	public ArrayList<SearchBean>getCurrentParam(HashMap param);
	public ArrayList<SearchBean>getCurrentParamForSearch(HashMap param);
	public void addParam(Map param)throws RuntimeException;
	public void delParam(Map param)throws RuntimeException;
	public void upParam(Map param)throws RuntimeException;
	
	/** 
	* @Title: findZZFWFList 
	* @Description: TODO(增值服务费汇总查询) 
	* @param @param ym
	* @param @param client_code
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findZZFWFList(@Param("ym")String ym,@Param("client_code")String client_code);
	
	/** 
	* @Title: findCCFList 
	* @Description: TODO(仓储费汇总查询) 
	* @param @param ym
	* @param @param client_code
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findCCFList(@Param("ym")String ym,@Param("client_code")String client_code);

	/** 
	* @Title: findHCFList 
	* @Description: TODO(耗材费汇总查询) 
	* @param @param ym
	* @param @param client_code
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findHCFList(@Param("ym")String ym,@Param("client_code")String client_code);
	
	public List<Map<String, Object>> findWlPoolList(@Param("ym")String ym,@Param("client_code")String client_code);
	
	public List<Map<String, Object>> findDBFList(@Param("yy")String yy,@Param("mm")String mm,@Param("client_code")String client_code);
	
	List<Map<String, Object>> findSectionDBF(Map<String, Object> param);
}
