package com.bt.workOrder.quartz;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bt.base.redis.RedisUtils;
import com.bt.common.CommonUtils;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.workOrder.dao.WorkOrderPlatformStoreMapper;
import com.bt.workOrder.service.WorkOrderPlatformStoreService;

public class AutoAllocation {
	private static final String AUTO_GROUP_WORK="AUTO_GROUP_WORK";
	private static final String AUTO_EMP_WORK="AUTO_EMP_WORK";
	WorkOrderPlatformStoreMapper<T> workOrderPlatformStoreMapper = (WorkOrderPlatformStoreMapper)SpringUtils.getBean("workOrderPlatformStoreMapper");
	WorkOrderPlatformStoreService<T> WorkOrderPlatformStoreServiceImpl = (WorkOrderPlatformStoreService)SpringUtils.getBean("workOrderPlatformStoreServiceImpl");
	
	//自动分配
	public  void   autoAllocation() throws Exception{
		String RedisIds = RedisUtils.get(1,AUTO_GROUP_WORK);
		if(!CommonUtils.checkExistOrNot(RedisIds)) {
			List<String> SqlIds=workOrderPlatformStoreMapper.findWorkStoreByCur();
			
			if(SqlIds.size()>0) {
				RedisUtils.set(1,AUTO_GROUP_WORK, JSON.toJSONString(SqlIds));
			}
		}
		
		for (;;) {
			String woIds = RedisUtils.get(1,AUTO_GROUP_WORK);
			if(CommonUtils.checkExistOrNot(woIds)) {
				List<String> woIdList = JSONArray.parseArray(woIds,String.class);
				if(woIdList.size()>0) {
					String woId= woIdList.get(0);
					WorkOrderPlatformStoreServiceImpl.autoAllocation(woId);
				}else {
					RedisUtils.remove(1,AUTO_GROUP_WORK);
					break;
				}
			}else {
				RedisUtils.remove(1,AUTO_GROUP_WORK);
				break;
			}
		}
	}	
	
	
	
	
public  void   autoWorkOrderEmp() throws Exception{
		
		String RedisIds = RedisUtils.get(1,AUTO_EMP_WORK);
		if(!CommonUtils.checkExistOrNot(RedisIds)) {
			List<String> SqlIds=workOrderPlatformStoreMapper.findWoIdByEmp();
			
			if(SqlIds.size()>0) {
				RedisUtils.set(1,AUTO_EMP_WORK, JSON.toJSONString(SqlIds));
			}
		}
		
		for (;;) {
			String woIds = RedisUtils.get(1,AUTO_EMP_WORK);
			if(CommonUtils.checkExistOrNot(woIds)) {
				List<String> woIdList = JSONArray.parseArray(woIds,String.class);
				if(woIdList.size()>0) {
					String woId= woIdList.get(0); 
					WorkOrderPlatformStoreServiceImpl.autoWorkOrderEmp(woId);
				}else {
					RedisUtils.remove(1,AUTO_EMP_WORK);
					break;
				}
			}else {
				RedisUtils.remove(1,AUTO_EMP_WORK);
				break;
			}
		}
	}	
	
	
	
}
