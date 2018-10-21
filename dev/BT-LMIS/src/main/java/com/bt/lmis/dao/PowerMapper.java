package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Menu;

public interface PowerMapper<T> extends BaseMapper<T>{

	public List<Map<String, Object>> getMenuTree(@Param("id")int id,@Param("pid")int pid);


	/** 
	* @Title: getPowerList 
	* @Description: TODO(查询所有的用户信息) 
	* @param @param Power
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>>getPowerList(Menu menu);
	
	/**
	 * 根据id查找用户信息
	 * @param id
	 * @return
	 */
	public Map<String,Object>findEmpById(@Param("id")int id);

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
	 * 注册用户登录名唯一校验
	 */
	public Long checkUser(Employee employee);
	
}
