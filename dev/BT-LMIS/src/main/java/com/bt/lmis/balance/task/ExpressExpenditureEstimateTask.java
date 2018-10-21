package com.bt.lmis.balance.task;

import java.util.ArrayList;
import java.util.List;

import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.service.EstimateService;
import com.bt.lmis.base.spring.SpringUtils;

/** 
 * @ClassName: ExpressExpenditureEstimateTask
 * @Description: TODO(仓储费结算任务)
 * @author Ian.Huang
 * @date 2017年5月5日 下午1:43:42 
 * 
 */
public class ExpressExpenditureEstimateTask {
	
//	private static class LazyHolder {
//		private static final StorageFeeSettlement INSTANCE=new StorageFeeSettlement();    
//    
//	}
//	private StorageFeeSettlementTask () {}
//	public static final StorageFeeSettlement getInstance() {
//		return LazyHolder.INSTANCE;
//		
//	}
	
	EstimateService estimateService=(EstimateService) SpringUtils.getBean("expressExpenditureEstimateServiceImpl");
	
	public void invoke() {
		try {
			List<Integer> contractId=new ArrayList<Integer>();
			contractId.add(180);
			contractId.add(213);
			contractId.add(253);
			contractId.add(210);
			estimateService.batchEstimate(new BatchEstimate("hhy", "2017-03-25", "2017-04-24", contractId));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}