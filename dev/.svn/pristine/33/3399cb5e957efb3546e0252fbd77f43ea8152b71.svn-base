package com.bt.lmis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.StorageDataGroupMapper;
import com.bt.lmis.dao.StorageExpendituresDataSettlementMapper;
import com.bt.lmis.model.CustomserTransSettleBean;
import com.bt.lmis.model.StorageDataGroup;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.service.StorageDataGroupService;
@Service
public class StorageDataGroupServiceImpl<T> extends ServiceSupport<T> implements StorageDataGroupService<T> {

	@Autowired
    private StorageDataGroupMapper<T> mapper;

	@Autowired
    private StorageExpendituresDataSettlementMapper<T> mappers;
	
	public StorageDataGroupMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<StorageDataGroup> findBySDG(StorageDataGroup sdg) {
		return mapper.findBySDG(sdg);
	}

	@Override
	public void saveAUpdate(StorageDataGroup sdg, List<StorageExpendituresDataSettlement> list) throws Exception{
		mapper.save(sdg);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBatch(sdg.getBatch());
			mappers.updateSEDS(list.get(i));
		}
	}

	@Override
	public List<CustomserTransSettleBean> findByCSP(HashMap<String,Object>param) {
		// TODO Auto-generated method stub
		return mapper.findByCSP(param);
	}
		
	
}
