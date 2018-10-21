package com.bt.wms.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.wms.model.User;

public interface UserService {
	
	/** 
	* @Title: query_user_by_id 
	* @Description: TODO(根据ID查询用户) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> query_user_by_id(@Param("id")int id);
	
	/** 
	* @Title: login_check 
	* @Description: TODO(登陆验证) 
	* @param @param user
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public User login_check(User user);

	/** 
	* @Title: update 
	* @Description: TODO(修改用户信息) 
	* @param @param user    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void update(User user);
}
