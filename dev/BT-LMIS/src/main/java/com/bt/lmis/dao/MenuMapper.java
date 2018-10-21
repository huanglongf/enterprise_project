package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Menu;

/** 
* @ClassName: MenuMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月24日 下午3:21:47 
* 
* @param <T> 
*/
public interface MenuMapper<T> extends BaseMapper<T> {
	
	/** 
	* @Title: upStatus 
	* @Description: TODO(根据菜单ID修改菜单状态0停用1启用) 
	* @param @param id
	* @param @param status
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public void upStatus(@Param("id")int id,@Param("status")int status);

	/** 
	* @Title: getMenuTree 
	* @Description: TODO(根据父类ID查询菜单) 
	* @param @param pid
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getMenuTree(Menu menu);
	

	/** 
	* @Title: getMenuTree 
	* @Description: TODO(根据角色ID查询菜单) 
	* @param @param pid
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getRoleMenuTree(@Param("roleid")int roleid);
	
	/** 
	* @Title: getMenu 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getMenu(Menu menu);
	
}
