package com.bt.lmis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.NvitationUseanmountDataSettlementMapper;
import com.bt.lmis.model.NvitationUseanmountDataSettlement;
import com.bt.lmis.service.NvitationUseanmountDataSettlementService;
@Service
public class NvitationUseanmountDataSettlementServiceImpl<T> extends ServiceSupport<T> implements NvitationUseanmountDataSettlementService<T> {

	@Autowired
    private NvitationUseanmountDataSettlementMapper<T> mapper;

	public NvitationUseanmountDataSettlementMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
}
