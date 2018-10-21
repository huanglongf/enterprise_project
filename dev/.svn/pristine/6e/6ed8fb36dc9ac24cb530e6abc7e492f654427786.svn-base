package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.workOrder.model.FollowUpRecord;

public interface FollowUpRecordService<T> extends BaseService<T> {

	Map<String, Object> querysGroupByGroupId(int group_id);

	List<Map<String, Object>> querysShopGroupStorePowerByGroupId(int group);

	Map<String, Object> querysStoreByStoreCode(String store_code);

	void insert(FollowUpRecord followUpRecord);

	List<FollowUpRecord> querysFollowUpRecordByGroupwoId(String woId);

}
