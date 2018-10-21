package com.bt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.sys.model.Account;

/** 
* @ClassName: EmployeeMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:11 
* 
* @param <T> 
*/
public interface AccountMapper<T> {
	
	/**
	 * @Title: getAccount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param account
	 * @return: Account
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午10:09:24
	 */
	Account getAccount(Account account);
	
	/** 
	* @Title: getOrg 
	* @Description: TODO([业务权限]获取组织架构) 
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getOrg(@Param("id")String username);
	/** 
	* @Title: getCarrier 
	* @Description: TODO([业务权限]获取承运商) 
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getCarrier(@Param("id")String username);
	/** 
	* @Title: getCarrierType 
	* @Description: TODO([业务权限]获取承运商类型) 
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getCarrierType(@Param("id")String username);
	/** 
	* @Title: getWareHouse 
	* @Description: TODO([业务权限]获取仓库) 
	* @param @param username
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getWareHouse(@Param("id")String username);

	String selectByCode(@Param("code")String power);

	List<Map<String, Object>> selectBysuperior_id(@Param("superior_id")String superior_id);

	List<Map<String, Object>> getUser_id(@Param("power")String power);

	List<Map<String, Object>> selectById(@Param("id")String power);
}
