package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Role;

/** 
* @ClassName: RoleService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:40:07 
* 
* @param <T> 
*/
public interface RoleService<T> extends BaseService<T> {

	/** 
	* @Title: findByList 
	* @Description: TODO(获取角色列表) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findByList();
	
	public List<Map<String, Object>> findByListSelective(T entity);
	
	/** 
	* @Title: upStatus 
	* @Description: TODO(根据角色ID修改菜单状态0停用1启用) 
	* @param @param id
	* @param @param status
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public void upStatus(@Param("id")int id,@Param("status")int status);

	/** 
	* @Title: findRoleEmployeeByRoleId 
	* @Description: TODO(根据角色ID查询是否被使用) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findRoleEmployeeByRoleId(@Param("id")int id);
	

	/**
	 * @throws Exception  
	* @Title: insertRoleMenu 
	* @Description: TODO(新增角色) 
	* @param @param role
	* @param @param ids    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertRoleMenu(Role role,Integer[] ids) throws Exception;
	

	/**
	 * @throws Exception  
	* @Title: insertRoleMenu 
	* @Description: TODO(修改角色) 
	* @param @param role
	* @param @param ids    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateRoleMenu(T entity,int id,Integer[] ids) throws Exception;

}
