package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Employee;

/** 
* @ClassName: EmployeeMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:11 
* 
* @param <T> 
*/
public interface EmployeeMapper<T> extends BaseMapper<T>{
	
	/**
	 * 
	 * @Description: TODO
	 * @param username
	 * @return
	 * @return: Employee  
	 * @author Ian.Huang 
	 * @date 2016年8月10日下午2:47:19
	 */
	public Employee selectByUserName(@Param("username")String username);
	
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
	 * 
	 * @Description: TODO
	 * @return
	 * @return: Employee  
	 * @author Ian.Huang 
	 * @date 2016年8月10日下午2:46:47
	 */
	public Employee selectByEmployeeNumber(@Param("employee_number")String employee_number);
	
	/**
	 * 
	 * @Description: TODO(工号唯一性校验)
	 * @param employee
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月9日上午10:46:44
	 */
	public Integer checkEmployeeNumber(Employee employee);
	
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
	public List<Map<String, Object>> getMenuTree(@Param("id")int id,@Param("pid")int pid);
	

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
	* @Title: ifnode 
	* @Description: TODO(根据ID判断是否为根节点) 
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public Map<String, Object> findMenuById(@Param("id")int id);
	
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
	* @Title: getEmployee 
	* @Description: TODO(查询所有的用户信息) 
	* @param @param employee
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>>getEmployee(Employee employee);
	
	/**
	 * 根据id查找用户信息
	 * @param id
	 * @return
	 */
	public Map<String,Object>findEmpById(@Param("id")int id);
	
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
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param employee    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertER(@Param("employeeid")int employeeid,@Param("roleid")int roleid);
	
	/**
	 * 单点登陆验证
	 */
	public void update_token_mark(Map<String,String>param);
	
	public Map<String,String> get_token_mark(Map<String,String>param);

	/**
	 * @param email
	 * @return
	 */
	public Employee getEmpByEmail(@Param("email")String email);
	public int getCountByEmail(@Param("email")String email);

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
	public int getByEmailNoId(@Param("email")String email, @Param("id")Integer id);

	/**
	 * @param user
	 * @return
	 */
	public int updateProfile(Employee user);

	//public Integer getOwnGroupCount(Employee employee);
	
}
