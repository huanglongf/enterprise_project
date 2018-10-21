package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/** 
* @ClassName: RoleMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:39:32 
* 
* @param <T> 
*/
public interface RoleMapper<T> extends BaseMapper<T> {
	/** 
	* @Title: findByList 
	* @Description: TODO(获取角色列表) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findByList();
	

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
	* @Title: insertMR 
	* @Description: TODO(关联角色和菜单) 
	* @param @param menuid
	* @param @param roleid
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public void insertMR(@Param("menuid")int menuid,@Param("roleid")int roleid);
	
	/** 
	* @Title: deleteMR 
	* @Description: TODO(根据角色ID删除角色菜单关联) 
	* @param @param roleid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteMR(@Param("roleid")int roleid);


	public List<Map<String, Object>> findByListSelective(T entity);
}
