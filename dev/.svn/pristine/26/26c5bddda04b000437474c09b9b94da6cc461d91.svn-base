package com.bt.workOrder.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreEmployee;

/**
 * @Title:GroupService
 * @Description: TODO(组别Service)
 * @author Ian.Huang 
 * @date 2016年8月8日下午6:48:56
 */
@Service
public interface ShopGroupService<T> {
	public QueryResult<Group> querysGroup(GroupParam groupPar);
	public ArrayList<?> querySeniorQueryGroupSup(GroupParam groupPar);
	public ArrayList<?> queryWkGroupSup();
	public ArrayList<?> findWorkOrderType();
	public ArrayList<?> findWkType();
	public ArrayList<?> queryLogisticsCode();
	public ArrayList<?> findStore();
	public List<?> workOrderAndLevel(String code);
	public ArrayList<?> queryWkGroupSupAndUpdate(String id);
	public Group querysGroupById(Map<String, Object> paramMap);
	public QueryResult<T> findAllStoreData(QueryParameter qr);
	public void updateGroupModel(Map<String, Object> pram);
	public ArrayList<Map<String,String>>get_team_emp(Map<String,String>param);
	
	public QueryResult<T> findAllEmpData(QueryParameter qr);
	
	public void insertEmp(List<StoreEmployee> param);
	
	public Map<String,String> checkImport(Map<String,String>param);
	
	List<Map<String, Object>> query_export(String bat_id);
	
	public void addGroupModel(Map<String,Object>param);
	
	public int checkCountWorkCode(Map<String, Object> pram);
	
	public int checkCountWork(Map<String, Object> pram);
	
	public void addStore(Map<String,String>param);
	
	public void addWorkType(Map<String,String>param);
	
	public Integer checkCountStore(Map<String,String>param);
	
	public Integer checkCountWorkType(Map<String,String>param);
	
	
	public void delGroup(@Param("id")String id);
	public void delWorkPower(@Param("id")String id);
	public void delStorePower(@Param("id")String id);

	int switchShopGroup(String status,String id);
	
	public void opEmp(Map<String,String>param);
	
	public Map<String, Object> getGroupData(@Param("id")String id);
	
	public ArrayList<?> getWorkPowerBywkType(Map<String, String> param);
	
	public Integer delBatchWorkPower(Integer[] ids);
	
	public Integer delBatchStorePower(Integer[] ids);
	
	public Map<String, Object> getGroupTypeById(@Param("id") String id);
	
	public QueryResult<Group> queryGroup(GroupParam groupPar);

	public ArrayList<Map<String, String>> getSOteam(String groupType);
	
	public Map<String,Object> checkImport(List<StoreEmployee> oo_list, String groupId);
	
	public void opAuto(Map<String, String> pram);
	
	public List<String> queryTeamIdById(Integer id);
	
	public String queryCurrentGroupByWoId(@Param("woId") String woId);
	
	public void delEmp(Map<String, String> pram);

	public String getCodeByName(String storeName);
    /**
     * @param string
     */
    public void delWkType(String string);
	
}
