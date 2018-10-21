package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.param.GroupQueryParam;
import com.bt.workOrder.model.Group;
import com.bt.workOrder.model.GroupStorePower;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.lmis.model.StoreBean;;

/**
 * @Title:GroupService
 * @Description: TODO(组别Service)
 * @author Ian.Huang 
 * @date 2016年8月8日下午6:48:56
 */
@Service
public interface GroupService<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月10日下午3:10:21
	 */
	JSONObject getStorePower(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午11:51:09
	 */
	JSONObject saveStorePower(GroupStorePower groupStorePower, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:15:50
	 */
	JSONObject delWorkPower(GroupWorkPower groupWorkPower, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:25:49
	 */
	HttpServletRequest queryWorkPower(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:25:36
	 */
	HttpServletRequest queryStorePower(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:15:46
	 */
	JSONObject delStorePower(GroupStorePower groupStorePower, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询所有启用组别)
	 * @return
	 * @throws Exception
	 * @return: List<Group>  
	 * @author Ian.Huang 
	 * @date 2016年8月9日下午5:03:31
	 */
	List<Group> findAllGroups() throws Exception;
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午7:41:23
	 */
	JSONObject saveGroup(Group group, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(删除组别)
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月9日下午1:33:58
	 */
	JSONObject delGroups(HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(加载编辑页面)
	 * @param request
	 * @throws Exception
	 * @return: HttpServletRequest  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午7:57:23
	 */
	HttpServletRequest toForm(HttpServletRequest request) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(更新组别状态)
	 * @param group
	 * @param request
	 * @param result
	 * @throws Exception
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午2:20:48
	 */
	JSONObject updateStatus(Group group, HttpServletRequest request, JSONObject result) throws Exception;
	
	/**
	 * 
	 * @Description: TODO(查询所有组别)
	 * @param gQP
	 * @return: QueryResult<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月8日下午7:27:19
	 */
	QueryResult<Map<String, Object>> query(GroupQueryParam gQP);
	public QueryResult<T> findAllStoreData(QueryParameter qr);
	
	/** 
	* @Title: getWKEMP 
	* @Description: TODO(获取所有工单用户) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getWKEMP(@Param("group")int group);
	
	/** 
	* @Title: insertGE 
	* @Description: TODO(新增组别关联用户) 
	* @param @param employee
	* @param @param group
	* @param @param create_by
	* @param @param create_time
	* @param @param update_by
	* @param @param update_time    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertGE(@Param("employee")String employee,@Param("group")int group,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by,@Param("update_time")String update_time);
	
	/** 
	* @Title: deleteGE 
	* @Description: TODO(删除组下的用户) 
	* @param @param groupid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteGE(@Param("group")int group);
	
	/** 
	* @Title: selectGE 
	* @Description: TODO(查询组下用户信息) 
	* @param @param group
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> selectGE(@Param("group")int group);

	/** 
	* @Title: selectGroup 
	* @Description: TODO(根据ID查询组) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> selectGroup(@Param("id")String id);
	
	/** 
	* @Title: checkEMP 
	* @Description: TODO(校验用户是否已经绑定组别) 
	* @param @param ids
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> checkEMP(@Param("ids")String ids,@Param("group")String group);

	int addBatchStore(List<StoreBean> list);
	
	boolean existStore(String sotreName,StoreBean storeBean);

	boolean existRecord(String cellValue, String groupId);
}
