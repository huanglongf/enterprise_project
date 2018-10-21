package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.NvitationUseanmountDataMapper;
import com.bt.lmis.model.NvitationUseanmountData;
import com.bt.lmis.service.NvitationUseanmountDataService;
@Service
public class NvitationUseanmountDataServiceImpl<T> extends ServiceSupport<T> implements NvitationUseanmountDataService<T> {

	@Autowired
    private NvitationUseanmountDataMapper<T> mapper;

	public NvitationUseanmountDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<NvitationUseanmountData> list(NvitationUseanmountData data) {
		return mapper.list(data);
	}

	@Override
	public List<NvitationUseanmountData> listSE(NvitationUseanmountData data) {
		// TODO Auto-generated method stub
		return mapper.listSE(data);
	}

	@Override
	public int countSE(NvitationUseanmountData data) {
		// TODO Auto-generated method stub
		return mapper.countSE(data);
	}
		
	
}
