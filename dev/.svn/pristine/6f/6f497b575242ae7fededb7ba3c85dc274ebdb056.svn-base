package com.bt.orderPlatform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.OrganizationInformationMapper;
import com.bt.orderPlatform.model.OrganizationInformation;
import com.bt.orderPlatform.service.OrganizationInformationService;
@Service
public class OrganizationInformationServiceImpl<T>  implements OrganizationInformationService<T> {

	@Autowired
    private OrganizationInformationMapper<T> mapper;

	public OrganizationInformationMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public OrganizationInformation selectById(String orgid) {
		// TODO Auto-generated method stub
		return mapper.selectById(orgid);
	}

	@Override
	public List queryAll() {
		// TODO Auto-generated method stub
		return mapper.findAll();
	}

	@Override
	public List<OrganizationInformation> findOrgName(String org_name) {
		// TODO Auto-generated method stub
		return mapper.findOrgName(org_name);
	}

	@Override
	public List queryAllBypid(String id) {
		// TODO Auto-generated method stub
		return mapper.queryAllBypid(id);
	}

	@Override
	public List queryAll(String id) {
		// TODO Auto-generated method stub
		return mapper.queryAll(id);
	}

	
		
	
}
