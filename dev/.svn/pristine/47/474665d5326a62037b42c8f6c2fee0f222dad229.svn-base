package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AddservicefeeDataMapper;
import com.bt.lmis.model.AddservicefeeData;
import com.bt.lmis.service.AddservicefeeDataService;
@Service
public class AddservicefeeDataServiceImpl<T> extends ServiceSupport<T> implements AddservicefeeDataService<T> {

	@Autowired
    private AddservicefeeDataMapper<T> mapper;

	public AddservicefeeDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<AddservicefeeData> findByAll(AddservicefeeData afd) {
		return mapper.findByAll(afd);
	}

	@Override
	public List<Map<String, String>> findGroupByServiceName(AddservicefeeData afd) {
		return mapper.findGroupByServiceName( afd);
	}

	@Override
	public void update_settleFlag(String id) {
		mapper.update_settleFlag(id);
	}

	@Override
	public List<AddservicefeeData> findBySection(AddservicefeeData afd) {
		// TODO Auto-generated method stub
		return mapper.findBySection(afd);
	}
		
	
}
