package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.NvitationdataCollectMapper;
import com.bt.lmis.model.NvitationdataCollect;
import com.bt.lmis.service.NvitationdataCollectService;
@Service
public class NvitationdataCollectServiceImpl<T> extends ServiceSupport<T> implements NvitationdataCollectService<T> {

	@Autowired
    private NvitationdataCollectMapper<T> mapper;

	public NvitationdataCollectMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<NvitationdataCollect> findByCBIDACYCLE(NvitationdataCollect data) {
		return mapper.findByCBIDACYCLE(data);
	}
		
	
}
