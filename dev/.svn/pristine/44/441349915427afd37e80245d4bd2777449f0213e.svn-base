package com.bt.workOrder.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.model.Group;
import com.bt.workOrder.dao.FollowUpRecordMapper;
import com.bt.workOrder.model.FollowUpRecord;
import com.bt.workOrder.service.FollowUpRecordService;
@Service
public class FollowUpRecordServiceImpl<T> extends ServiceSupport<T> implements FollowUpRecordService<T> {

	@Autowired
    private FollowUpRecordMapper<T> mapper;

	public FollowUpRecordMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public Map<String, Object> querysGroupByGroupId(int id) {
		// TODO Auto-generated method stub
		return mapper.querysGroupByGroupId(id);
	}

	@Override
	public List<Map<String, Object>> querysShopGroupStorePowerByGroupId(int group) {
		// TODO Auto-generated method stub
		return mapper.querysShopGroupStorePowerByGroupId(group);
	}

	@Override
	public Map<String, Object> querysStoreByStoreCode(String store_code) {
		// TODO Auto-generated method stub
		return mapper.querysStoreByStoreCode(store_code);
	}

	@Override
	public void insert(FollowUpRecord followUpRecord) {
		// TODO Auto-generated method stub
		mapper.insert(followUpRecord);
	}

	@Override
	public List<FollowUpRecord> querysFollowUpRecordByGroupwoId(String woId) {
		// TODO Auto-generated method stub
		return mapper.querysFollowUpRecordByGroupwoId(woId);
	}
		
	
}
