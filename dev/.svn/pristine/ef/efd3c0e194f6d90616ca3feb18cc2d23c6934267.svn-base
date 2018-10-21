package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.EmployeeInGroup;

/**
 * @Title:EmployeeInGroupMapper
 * @Description: TODO(用户&组关系DAO)
 * @author Ian.Huang 
 * @date 2016年8月9日下午1:59:36
 */
public interface EmployeeInGroupMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询员工加入的所有组别)
	 * @param employee_id
	 * @return: List<Map<String,Object>>
	 * @author Ian.Huang 
	 * @date 2016年8月10日下午2:13:16
	 */
	public List<Map<String, Object>> selectByEmployee(@Param("employee_id")Integer employee_id);
	
	/**
	 * 
	 * @Description: TODO(新增用户&组关系)
	 * @param eIG
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月10日上午11:55:48
	 */
	public Integer addRelations(EmployeeInGroup eIG);
	
	/**
	 * 
	 * @Description: TODO(按员工ID删除关系)
	 * @param employee_id
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月10日上午11:49:00
	 */
	public void deleteRelation(@Param("employee_id")Integer employee_id);
	
	/**
	 * 
	 * @Description: TODO(校验组下是否存在员工)
	 * @param group_id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月9日下午2:00:46
	 */
	public Integer checkEmployeeExist(@Param("group_id")Integer group_id);
	
}
