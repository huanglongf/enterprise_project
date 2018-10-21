package com.bt.lmis.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bt.lmis.model.Employee;
import com.bt.lmis.service.EmployeeService;
import com.bt.utils.DateUtil;

public class QuartzJob{


	@Resource 
	private EmployeeService<Employee> employeeServiceImpl;
	
	public void work(){
		System.out.println("date:" + DateUtil.format(new Date()));  
		List<Map<String, Object>> list = employeeServiceImpl.findEMR(1);
		System.out.println(list.get(0).get("id"));
		
	}
	
}
