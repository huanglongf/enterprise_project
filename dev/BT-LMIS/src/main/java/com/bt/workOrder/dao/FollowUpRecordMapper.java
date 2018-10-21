package com.bt.workOrder.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Group;
import com.bt.workOrder.model.FollowUpRecord;

/**
* @ClassName: FollowUpRecordMapper
* @Description: TODO(FollowUpRecordMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface FollowUpRecordMapper<T> extends BaseMapper<T> {

	Map<String, Object> querysGroupByGroupId(@Param("id")int id);

	List<Map<String, Object>> querysShopGroupStorePowerByGroupId(@Param("group")int group);

	Map<String, Object> querysStoreByStoreCode(@Param("store_code")String store_code);

	void insert(FollowUpRecord followUpRecord);

	List<FollowUpRecord> querysFollowUpRecordByGroupwoId(@Param("wo_store_id")String woId);
	
	
}
