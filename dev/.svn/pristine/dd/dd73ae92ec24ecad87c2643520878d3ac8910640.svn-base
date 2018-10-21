package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryParameter;
import com.bt.workOrder.controller.param.AccountParam;
import com.bt.workOrder.controller.param.WoHourInterimParam;
import com.bt.workOrder.model.Manhours;
import com.bt.workOrder.model.woHourInterim;

/**
 * @Title:WorkTimeMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月5日下午7:15:06
 */
public interface ManhoursMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param accountParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年4月19日上午11:09:11
	 */
	Integer countWorkOrderAccounts(AccountParam accountParam);
	
	/**
	 * 
	 * @Description: TODO(获取所有工单账号)
	 * @param accountParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月19日上午11:04:02
	 */
	List<Map<String, Object>> getWorkOrderAccounts(AccountParam accountParam); 
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Manhours  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午8:42:51
	 */
	public Manhours selectById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param manhours
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午6:05:28
	 */
	public Integer update(Manhours manhours);
	
	/**
	 * 
	 * @Description: TODO
	 * @param manhoursList
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:12:38
	 */
	public Integer add(List<Manhours> manhoursList);
	
	/**
	 * 
	 * @Description: TODO
	 * @param account
	 * @param date
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:12:42
	 */
	public Integer judgeUnique(@Param("account")Integer account, @Param("date")String date);
	
	/**
	 * 
	 * @Description: TODO(获取所有下级组中所有成员)
	 * @param groups
	 * @param accountParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月7日下午8:55:35
	 */
	public List<Map<String, Object>> getSubordinates(@Param("groups")Integer[] groups, @Param("accountParam")AccountParam accountParam);
	
	/**
	 * 
	 * @Description: TODO(获取当前组的下级组)
	 * @param groups
	 * @return: int[]  
	 * @author Ian.Huang 
	 * @date 2017年1月7日下午9:04:04
	 */
	public Integer[] getSubordinateGroups(Integer[] groups);
	
	/**
	 * 
	 * @Description: TODO(获取当前账户对应组)
	 * @param employee_id
	 * @return: int[]  
	 * @author Ian.Huang 
	 * @date 2017年1月7日下午9:05:32
	 */
	public Integer[] getGroups(Integer employee_id);
	
	/**
	 * 
	 * @Description: TODO(删除工时)
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月6日下午1:27:23
	 */
	public Integer delManhours(List<String> ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月6日下午1:22:58
	 */
	public Integer judgeManhours(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param updateBy
	 * @param status
	 * @param ids
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月19日下午1:31:37
	 */
	public void updateStatus(@Param("updateBy")String updateBy, @Param("status")Boolean status, @Param("ids")String[] ids);
	
	/**
	 * 
	 * @Description: TODO(列表计数)
	 * @param queryParam
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午7:55:10
	 */
	public Integer countQuery(QueryParameter queryParam);
	
	/**
	 * 
	 * @Description: TODO(列表查询)
	 * @param queryParam
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午7:55:01
	 */
	public List<Map<String, Object>> query(QueryParameter queryParam);

	public void insertManhours(List<Manhours> wList);

	public void insertManhours(@Param("import_status")String import_status, @Param("error_info")String error_info, @Param("work_number")String work_number, @Param("name")String name,
			@Param("data_time")String data_time, @Param("man_hour")String man_hour,@Param("to_delete")String to_delete);

	public List<woHourInterim> querysHours(WoHourInterimParam woHourInterimPar);

	public int countsHours(WoHourInterimParam woHourInterimPar);

	public void updateWoHourInterimToDelete();

	public List<woHourInterim> queryHourInterim();

	public void saveWoManhours(@Param("uuid")String uuid,@Param("createTime")String createTime, @Param("updateTime")String updateTime, @Param("ids")String ids, @Param("dataTime")String dataTime, @Param("manHour")String manHour,
			@Param("status")String status,@Param("userName")String userName, @Param("update_by")String update_by);

	public Employee findsEmployeeId(@Param("workNumber")String workNumber);

	public void deleteWoHourInterim(@Param("id")String id);

	public void addWoHourInterim(Map<String, Object> paramMap);

	public void updateWoHourInterim(@Param("id")String id, @Param("import_status")String import_status, @Param("error_info")String error_info, @Param("work_number")String work_number, @Param("name")String name,
			@Param("data_time")String data_time, @Param("man_hour")String man_hour);

	public List<Manhours>  selectValLatest(Map<String,Object> param);
}
