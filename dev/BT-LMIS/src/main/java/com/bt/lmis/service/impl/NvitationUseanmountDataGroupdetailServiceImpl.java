package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.NvitationUseanmountDataGroupdetailMapper;
import com.bt.lmis.model.NvitationUseanmountDataGroupdetail;
import com.bt.lmis.service.NvitationUseanmountDataGroupdetailService;
@Service
public class NvitationUseanmountDataGroupdetailServiceImpl<T> extends ServiceSupport<T> implements NvitationUseanmountDataGroupdetailService<T> {

	@Autowired
    private NvitationUseanmountDataGroupdetailMapper<T> mapper;

	public NvitationUseanmountDataGroupdetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<NvitationUseanmountDataGroupdetail> findByCBID(String cbid) {
		return mapper.findByCBID(cbid);
	}
		
	
}
