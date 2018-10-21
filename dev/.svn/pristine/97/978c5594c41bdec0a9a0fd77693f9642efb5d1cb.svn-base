package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.StoreBean;
import com.bt.lmis.page.QueryParameter;
import com.bt.workOrder.controller.param.GroupQueryParam;
import com.bt.workOrder.model.Group;
import com.bt.workOrder.model.GroupStorePower;
import com.bt.workOrder.model.GroupWorkPower;

/**
 * @Title:GroupMapper
 * @Description: TODO(ç»å«DAO) 
 * @author Ian.Huang 
 * @date 2016å¹´8æ8æ¥ä¸å6:55:05
 */
public interface GroupMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å4:14:19
	 */
	public List<Map<String, Object>> getAllWorkOrderTypeAndLevel();
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: GroupStorePower  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å2:37:32
	 */
	public GroupStorePower getStorePowerById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å1:03:42
	 */
	public String getCarrierPowerId(GroupWorkPower groupWorkPower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å12:56:03
	 */
	public Integer judgeCarrierExistOrNot(GroupWorkPower groupWorkPower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å1:02:12
	 */
	public String getStorePowerId(GroupStorePower groupStorePower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å12:56:00
	 */
	public Integer judgeStoreExistOrNot(GroupStorePower groupStorePower);
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å11:19:04
	 */
	public List<Map<String, Object>> getCarrier();
	
	/**
	 * 
	 * @Description: TODO
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å11:19:01
	 */
	public List<Map<String, Object>> getStore();
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å10:12:51
	 */
	public Integer updateWorkPower(GroupWorkPower groupWorkPower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å1:13:01
	 */
	public Integer addWorkPower(GroupWorkPower groupWorkPower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å10:12:56
	 */
	public Integer updateStorePower(GroupStorePower groupStorePower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ10æ¥ä¸å1:12:37
	 */
	public Integer addStorePower(GroupStorePower groupStorePower);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å10:33:16
	 */
	public List<Map<String, Object>> queryWorkPower(@Param("group")Integer group);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å10:32:51
	 */
	public List<Map<String, Object>> queryStorePower(@Param("group")Integer group);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @param superior
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å9:24:06
	 */
	public Integer judgeSubordinateGroupOrNot(@Param("id")Integer id, @Param("superior")Integer superior);
	
	/**
	 * 
	 * @Description: TODO
	 * @param superior
	 * @return: List<Integer>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å9:46:57
	 */
	public List<Integer> getSubordinateGroups(List<Integer> superior);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å2:13:44
	 */
	public Integer add(Group group);
	
	/**
	 * 
	 * @Description: TODO
	 * @param group
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å2:13:23
	 */
	public Integer update(Group group);
	
	/**
	 * 
	 * @Description: TODO
	 * @param ids
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å2:04:44
	 */
	public Integer batchDelete(Integer[] ids);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: Group  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å2:03:08
	 */
	public Group selectById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO(æ¥è¯¢ææç»å«è®°å½)
	 * @param gQP
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017å¹´2æ9æ¥ä¸å1:58:58
	 */
	public List<Map<String, Object>> query(GroupQueryParam gQP);
	
	/**
	 * 
	 * @Description: TODO(æ¥è¯¢è®°å½æ¡æ°)
	 * @param gQP
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ8æ¥ä¸å7:36:23
	 */
	public Integer count(GroupQueryParam gQP);
	
	/**
	 * 
	 * @Description: TODO(æ£éªç»å«åç§°æ¯å¦å­å¨)
	 * @param group_name
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ9æ¥ä¸å3:10:32
	 */
	public Integer checkNameExist(@Param("group_name")String group_name);
	
	/**
	 * 
	 * @Description: TODO(æ£éªç»å«ä»£ç æ¯å¦å­å¨)
	 * @param group_code
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ9æ¥ä¸å3:09:46
	 */
	public Integer checkCodeExist(@Param("group_code")String group_code);
	
	/**
	 * 
	 * @Description: TODO(ænameæ¥ç»å«)
	 * @param group_name
	 * @return: Group  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ9æ¥ä¸å3:19:39
	 */
	public Group selectByName(@Param("group_name")String group_name);
	
	/**
	 * 
	 * @Description: TODO(æcodeæ¥ç»å«)
	 * @param group_code
	 * @return: Group  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ9æ¥ä¸å3:19:06
	 */
	public Group selectByCode(@Param("group_code")String group_code);
	
	/**
	 * 
	 * @Description: TODO(æ¥è¯¢ææå¯ç¨ç»å«)
	 * @return: List<Group>  
	 * @author Ian.Huang 
	 * @date 2016å¹´8æ9æ¥ä¸å5:02:55
	 */
	public List<Group> findAllGroups();
	
	
	public void delEmpGroup(@Param("id")String id);
	public List<T> getSrorePage(QueryParameter queryParameter);
	public Integer selectStoreCount(QueryParameter queryParameter);

	/** 
	* @Title: getWKEMP 
	* @Description: TODO(è·åææå·¥åç¨æ·) 
	* @param @return    è®¾å®æä»¶ 
	* @return List<Map<String,Object>>    è¿åç±»å 
	* @throws 
	*/
	public List<Map<String, Object>> getWKEMP(@Param("group")int group);

	/** 
	* @Title: insertGE 
	* @Description: TODO(æ°å¢ç»å«å³èç¨æ·) 
	* @param @param employee
	* @param @param group
	* @param @param create_by
	* @param @param create_time
	* @param @param update_by
	* @param @param update_time    è®¾å®æä»¶ 
	* @return void    è¿åç±»å 
	* @throws 
	*/
	public void insertGE(@Param("employee")String employee,@Param("group")int group,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by,@Param("update_time")String update_time);

	/** 
	* @Title: deleteGE 
	* @Description: TODO(å é¤ç»ä¸çç¨æ·) 
	* @param @param groupid    è®¾å®æä»¶ 
	* @return void    è¿åç±»å 
	* @throws 
	*/
	public void deleteGE(@Param("group")int group);
	
	/** 
	* @Title: selectGE 
	* @Description: TODO(æ¥è¯¢ç»ä¸ç¨æ·ä¿¡æ¯) 
	* @param @param group
	* @param @return    è®¾å®æä»¶ 
	* @return List<Map<String,Object>>    è¿åç±»å 
	* @throws 
	*/
	public List<Map<String, Object>> selectGE(@Param("group")int group);

	/** 
	* @Title: selectGroup 
	* @Description: TODO(æ ¹æ®IDæ¥è¯¢ç») 
	* @param @param id
	* @param @return    è®¾å®æä»¶ 
	* @return List<Map<String,Object>>    è¿åç±»å 
	* @throws 
	*/
	public List<Map<String, Object>> selectGroup(@Param("id")String id);
	
	/** 
	* @Title: checkEMP 
	* @Description: TODO(æ ¡éªç¨æ·æ¯å¦å·²ç»ç»å®ç»å«) 
	* @param @param ids
	* @param @return    è®¾å®æä»¶ 
	* @return List<Map<String,Object>>    è¿åç±»å 
	* @throws 
	*/
	public List<Map<String, Object>> checkEMP(@Param("ids")String ids,@Param("group")String group);

	public int addBatchStore(List<StoreBean> list);

	public List<Map<String, Object>> isQaById(@Param("id") String id);
}
