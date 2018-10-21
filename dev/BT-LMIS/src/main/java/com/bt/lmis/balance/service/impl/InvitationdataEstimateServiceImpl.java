package com.bt.lmis.balance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.InvitationdataEstimateMapper;
import com.bt.lmis.balance.model.InvitationdataEstimate;
import com.bt.lmis.balance.service.InvitationdataEstimateService;

@Service
public class InvitationdataEstimateServiceImpl implements InvitationdataEstimateService {
	
	@Autowired
	private InvitationdataEstimateMapper dao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(InvitationdataEstimate record) {
		return dao.insert(record);
	}

	@Override
	public int insertSelective(InvitationdataEstimate record) {
		return dao.insertSelective(record);
	}

	@Override
	public InvitationdataEstimate selectByPrimaryKey(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(InvitationdataEstimate record) {
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(InvitationdataEstimate record) {
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public int save(InvitationdataEstimate record) {
		return dao.insert(record);
	}

}
