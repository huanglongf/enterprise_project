package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Employee;

/** 
* @ClassName: EmployeeService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:15 
* 
* @param <T> 
*/
public interface EmployeeService<T> extends BaseService<T>{
	
	public Employee selectByUserName(String username);
	
	public Employee selectByEmployeeNumber(String employee_number);
	
	/**
	 * 
	 * @Description: TODO(校验工号唯一)
	 * @param employee
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月9日上午10:50:15
	 */
	public Integer checkEmployeeNumber(Employee employee);

	/** 
	* @Title: findEMR 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findEMR(@Param("id")int id);
	public List<Map<String, String>> findMenu(@Param("id")int id);
	
	
	/** 
	* @Title: loginCheck 
	* @Description: TODO(登录查询用户) 
	* @param @param employee
	* @param @return    设定文件 
	* @return Employee    返回类型 
	* @throws 
	*/
	public Employee loginCheck(Employee employee);
	
	/** 
	* @Title: getMenuTree 
	* @Description: TODO(根据用户ID获取用户菜单) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getMenuTree(int id,int pid);

	/** 
	* @Title: ifnode 
	* @Description: TODO(根据ID判断是否为根节点) 
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public Map<String, Object> findMenuById(int id);
	
	/** 
	* @Title: getEmployeeList 
	* @Description: TODO(查询所有的用户信息) 
	* @param @param employee
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>>getEmployeeList(Employee employee);
	
	/**
	 * 通过主键查找用户信息
	 * @param id
	 * @return
	 */
	public Map<String,Object>findEmpById(int id);
	
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
	

	
	/** 
	* @Title: delER 
	* @Description: TODO(根据角色ID删除用户角色) 
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void delER(@Param("id")int id);
	
	
	/** 
	* @Title: insertER 
	* @Description: TODO(插入用户角色表)
	* @param @param employee    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertER(@Param("employeeid")int employeeid,@Param("roleid")int roleid);
	
	/**
	 * @throws Exception  
	* @Title: updateER 
	* @Description: TODO(修改用户角色表) 
	* @param @param employeeid
	* @param @param roleid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateER(T entity,@Param("employeeid")int employeeid,@Param("roleid")int roleid) throws Exception;
	

	/** 
	* @Title: selectER 
	* @Description: TODO(根据用户ID查询用户角色) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> selectER(@Param("id")int id);
	
	/** 
	* @Title: findByPro 
	* @Description: TODO(根据项目编号查询) 
	* @param @param project_id
	* @param @return    设定文件 
	* @return Employee    返回类型 
	* @throws 
	*/
	public List<Employee> findByPro(@Param("project_id")String project_id);
	
	/**
	 * 单点登陆验证
	 */
	public void update_token_mark(Map<String,String>param);
	
	public Map<String,String> get_token_mark(Map<String,String>param);

	public Employee getEmpByEmail(String email);
	public int getCountByEmail(String email);

	/**
	 * @param emp
	 * @return
	 */
	public int updatePW(Employee emp);

	/**
	 * @param email
	 * @param id
	 * @return
	 */
	public int getByEmailNoId(String email, Integer id);

	/**
	 * @param user
	 * @return
	 */
	public int updateProfile(Employee user);

	//public Integer getOwnGroupCount(Employee employee);
}
