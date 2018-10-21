package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.EmployeeMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.service.EmployeeService;

/** 
* @ClassName: EmployeeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:08 
* 
* @param <T> 
*/
@Service
public class EmployeeServiceImpl<T> extends ServiceSupport<T> implements EmployeeService<T> {
	
	@Autowired
    private EmployeeMapper<T> mapper;

	public EmployeeMapper<T> getMapper() {
		return mapper;
	}
	
	@Override
	public Employee loginCheck(Employee employee) {
//		DataSourceContextHolder.setDbType("ds_salver");
		return mapper.loginCheck(employee);
	}

	@Override
	public List<Map<String, Object>> getMenuTree(int id,int pid) {
		return mapper.getMenuTree(id,pid);
	}

	@Override
	public Map<String, Object> findMenuById(int id) {
		return mapper.findMenuById(id);
	}

	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		mapper.batchDelete(ids);
	}


	@Override
	public List<Map<String, Object>> getEmployeeList(Employee employee) {
		// TODO Auto-generated method stub
//		DataSourceContextHolder.setDbType("ds_slave");
		return mapper.getEmployee(employee);
	}

	@Override
	public T selectById(Integer id) throws Exception {
		return mapper.selectById(id);
	}



	@Override
	public Map<String, Object> findEmpById(int id) {
		// TODO Auto-generated method stub
		return mapper.findEmpById(id);
	}

	@Override
	public void upStatus(int id, int status) {
		// TODO Auto-generated method stub
		mapper.upStatus(id, status);
	}

	@Override
	public Long checkUser(Employee employee) {
		// TODO Auto-generated method stub
		Long count=mapper.checkUser(employee);
		return count;
	}

	@Override
	public Long selectCount(Map<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delER(int id) {
		mapper.delER(id);
	}

	@Override
	public void insertER(int employeeid, int roleid) {
		mapper.insertER(employeeid, roleid);
	}

	@Override
	public void updateER(T entity,int employeeid, int roleid)throws Exception {
		mapper.update(entity);
		mapper.delER(employeeid);
		if(roleid!=0){
			mapper.insertER(employeeid, roleid);
		}
	}

	@Override
	public List<Map<String, Object>> selectER(int id) {
		return mapper.selectER(id);
	}

	@Override
	public List<Map<String, Object>> findEMR(int id) {
		return mapper.findEMR(id);
	}

	@Override
	public Integer checkEmployeeNumber(Employee employee) {
		return mapper.checkEmployeeNumber(employee);
	}

	@Override
	public Employee selectByUserName(String username) {
		return mapper.selectByUserName(username);
	}

	@Override
	public Employee selectByEmployeeNumber(String employee_number) {
		return mapper.selectByEmployeeNumber(employee_number);
	}

	@Override
	public List<Employee> findByPro(String project_id) {
		return mapper.findByPro(project_id);
	}

	@Override
	public void update_token_mark(Map<String, String> param) {
		// TODO Auto-generated method stub
		mapper.update_token_mark(param);
	}

/**
 * 
    * <p>Title:@see com.bt.lmis.service.EmployeeService#get_token_mark(java.util.Map)</p>
    * <p>Description: </p>
    * <p>Company: </p>
    * @author:Administrator
    * @date:2017年8月16日
 */
	@Override
	public Map<String, String> get_token_mark(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.get_token_mark(param);
	}

@Override
public List<Map<String, String>> findMenu(int id) {
	// TODO Auto-generated method stub
	return mapper.findMenu(id);
}

@Override
public Employee getEmpByEmail(String email) {
	return mapper.getEmpByEmail(email);
}
@Override
public int getCountByEmail(String email) {
	return mapper.getCountByEmail(email);
}

@Override
public int updatePW(Employee emp) {
	return mapper.updatePW(emp);
}

@Override
public int getByEmailNoId(String email, Integer id) {
	return mapper.getByEmailNoId(email,id);
}

/*@Override
public Integer getOwnGroupCount(Employee employee) {
	// TODO Auto-generated method stub
	return mapper.getOwnGroupCount(employee);
}*/
@Override
public int updateProfile(Employee user){
	return mapper.updateProfile(user);
}
	
}
