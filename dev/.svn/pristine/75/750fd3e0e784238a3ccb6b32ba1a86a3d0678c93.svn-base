package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bt.lmis.code.BaseService;

/**
 * @Title:EmployeeInGroupService
 * @Description: TODO(员工&组别关系Service)  
 * @author Ian.Huang 
 * @date 2016年8月10日上午11:58:56
 */
@Service
public interface EmployeeInGroupService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(查询工号下所有组别)
	 * @param employee_id
	 * @return: List<Map<String,Object>>
	 * @author Ian.Huang 
	 * @date 2016年8月10日下午2:14:38
	 */
	public List<Map<String, Object>> findGroups(Integer employee_id);
	
	/**
	 * 
	 * @Description: TODO(新增员工&组别关系)
	 * @param req
	 * @param employee_id
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月10日下午12:00:51
	 */
	public void addRelations(HttpServletRequest req, Integer employee_id) throws Exception;
	
}
