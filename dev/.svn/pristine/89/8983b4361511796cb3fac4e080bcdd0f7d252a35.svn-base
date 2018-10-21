package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.EmployeeInGroupMapper;
import com.bt.lmis.model.EmployeeInGroup;
import com.bt.lmis.service.EmployeeInGroupService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class EmployeeInGroupServiceImpl<T> extends ServiceSupport<T> implements EmployeeInGroupService<T> {

	@Autowired
	private EmployeeInGroupMapper<T> employeeInGroupMapper;
	
	public EmployeeInGroupMapper<T> getEmployeeInGroupMapper(){
		return employeeInGroupMapper;
	}
	
	@Override
	public List<Map<String, Object>> findGroups(Integer employee_id) {
		return employeeInGroupMapper.selectByEmployee(employee_id);
		
	}
	
	@Override
	public void addRelations(HttpServletRequest req, Integer employee_id) throws Exception {
		employeeInGroupMapper.deleteRelation(employee_id);
		String groups= req.getParameter("groups");
		if(CommonUtils.checkExistOrNot(groups)){
			Integer[] group_id = CommonUtils.strToIntegerArray(groups);
			EmployeeInGroup eIG = null;
			for(int i = 0; i < group_id.length; i++){
				eIG = new EmployeeInGroup();
				eIG.setEmployee(employee_id);
				eIG.setGroup(group_id[i]);
				eIG.setCreate_by(SessionUtils.getEMP(req).getId().toString());
				employeeInGroupMapper.addRelations(eIG);
				
			}
			
		}
		
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
