package com.bt.workOrder.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreEmployee;
import com.bt.lmis.model.WoGroupMember;
import com.bt.lmis.page.QueryParameter;

public interface ShopGroupMapper<T> {
	public List<Group> querysGroup(GroupParam groupPar);
	public int countsGroup(GroupParam groupPar);
	public ArrayList<?> querySeniorQueryGroupSup(GroupParam groupPar);
	
	public ArrayList<?> queryWkGroupSup();
	public ArrayList<?> findWorkOrderType();
	public ArrayList<?> queryLogisticsCode();
	public ArrayList<?> findStore();
	public List<?> workOrderAndLevel(@Param("code")String code);
	
	
	public Group querysGroupById(Map<String, Object> paramMap);
	public ArrayList<?> queryWkGroupSupAndUpdate(@Param("id")String id);
	
	public List<T> getSrorePage(QueryParameter queryParameter);
	public Integer selectStoreCount(QueryParameter queryParameter);
	
	public List<T> getEmpPage(QueryParameter queryParameter);
	public Integer selectEmpCount(QueryParameter queryParameter);
	
	public void updateGroupModel(Map<String, Object> pram);
	
	public ArrayList<Map<String,String>>get_team_emp(Map<String,String>param);
	
	public int insertEmp(List<StoreEmployee> param);
	
	public void checkImport(Map<String,String>param);
	
	public List<Map<String, Object>> query_export(String bat_id);
	
	public void addGroupModel(Map<String,Object>param);
	
	public void updateDataRole(Map<String,Object>param);
	
	public int checkCountWorkCode(Map<String, Object> pram);
	
	public int checkCountWork(Map<String, Object> pram);
	
	public void addStore(Map<String, String> param);
	
	public Integer checkCountStore(Map<String,String>param);
	
	public void opEmp(Map<String,String>param);
	
	public Map<String,Object> checkCountEmp(Map<String,String>param);
	
	public Map<String,Object> getGroupData(@Param("id")String id);
	
	public ArrayList<?> findWkType();
	
	public Integer checkCountWorkType(Map<String, String> param);
	
	public void addWorkType(Map<String, String> param);
	
	public ArrayList<Map<String,String>> getWorkPowerBywkType(Map<String, String> param);
	
	public void delWorkPower(String ids);
	
	public Integer delBatchWorkPower(Integer[] ids);
	
	public Map<String, Object> getGroupTypeById(@Param("id")String id);
	
	public List<Group> queryGroup(GroupParam groupPar);
	
	public int countGroup(GroupParam groupPar);

	public ArrayList<Map<String, String>> getSOteam(@Param("groupType")String groupType);

	public Integer delBatchStorePower(Integer[] ids);
	
	public Integer getIdByUsername(@Param("username")String username);
	
	public void addWoGroupMember(WoGroupMember wgm);
	
	public Integer addEmployee(StoreEmployee se);
	
	public void addEmpRole(@Param("empid")Integer empid);
	
	public int addEmpimportError(StoreEmployee storeEmployee);
	
	public Integer getCountByUserId(WoGroupMember wgm);
	
	public void opAuto(Map<String, String> pram);
	
	public List<String> queryTeamIdById(@Param("empid")Integer empid);
	
	public String queryCurrentGroupByWoId(@Param("woId")String woId);
	
	public String getGroupTypeByUserIduserId(@Param("userId") Integer userId);
	
	public void addGroupMember(Map<String, Object> param);
	
	public void delEmp(Map<String, String> pram);

	public String getCodeByName(@Param("storeName")String storeName);
	/**
	 * @param employee_number
	 * @return
	 */
	public int getCountByEmpNum(@Param("employee_number")String employee_number);
    /**
     * @param string
     * @return
     */
    public void delWkType(@Param("groupId")String string);

	int switchShopGroup(@Param("status") String status,@Param("id") String id);

	
	
}
