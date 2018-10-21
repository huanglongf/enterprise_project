package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PowerMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Menu;
import com.bt.lmis.service.PowerService;

@Service
public class PowerServiceImpl<T> extends ServiceSupport<T> implements PowerService<T> {
	
	@Autowired
    private PowerMapper<T> mapper;

	public PowerMapper<T> getMapper() {
		return mapper;
	}
	


	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		mapper.batchDelete(ids);
	}


	@Override
	public List<Map<String, Object>> getPowerList(Menu menu) {
		// TODO Auto-generated method stub
		return mapper.getPowerList(menu);
	}

	@Override
	public T selectById(Integer id) throws Exception {
		return mapper.selectById(id);
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
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectCount(param);
	}



	@Override
	public List<Map<String, Object>> findPowerById(int id) {
		return mapper.findPowerById(id);
	}

}
