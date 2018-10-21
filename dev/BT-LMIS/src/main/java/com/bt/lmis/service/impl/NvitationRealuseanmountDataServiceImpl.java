package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.NvitationRealuseanmountDataMapper;
import com.bt.lmis.model.NvitationRealuseanmountData;
import com.bt.lmis.service.NvitationRealuseanmountDataService;
@Service
public class NvitationRealuseanmountDataServiceImpl<T> extends ServiceSupport<T> implements NvitationRealuseanmountDataService<T> {

	@Autowired
    private NvitationRealuseanmountDataMapper<T> mapper;

	public NvitationRealuseanmountDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<NvitationRealuseanmountData> list(NvitationRealuseanmountData entity) {
		return mapper.list(entity);
	}

	@Override
	public List<NvitationRealuseanmountData> listSE(NvitationRealuseanmountData entity) {
		// TODO Auto-generated method stub
		return mapper.listSE(entity);
	}

	@Override
	public int countSE(NvitationRealuseanmountData entity) {
		// TODO Auto-generated method stub
		return mapper.countSE(entity);
	}
}
