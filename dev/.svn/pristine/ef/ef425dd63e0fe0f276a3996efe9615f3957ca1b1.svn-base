package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Menu;

public interface PowerService<T> extends BaseService<T>{
	
	
	/** 
	* @Title: getEmployeeList 
	* @Description: TODO(查询所有的权限信息) 
	* @param @param employee
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>>getPowerList(Menu menu);

	/** 
	* @Title: findPowerById 
	* @Description: TODO(根据ID查询权限) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>> findPowerById(@Param("id")int id);
	
	/** 
	* @Title: upStatus 
	* @Description: TODO(根据菜单ID修改用户状态0停用1启用) 
	* @param @param id
	* @param @param status
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public void upStatus(@Param("id")int id,@Param("status")int status);
	
	/**
	 * 登录名唯一校验
	 */
	public Long checkUser(Employee employee);
	
}
