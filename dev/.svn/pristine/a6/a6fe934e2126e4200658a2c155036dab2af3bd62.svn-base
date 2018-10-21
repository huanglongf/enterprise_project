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
public class ConsumerRevenueEstimateTask {
	
	EstimateService estimateService=(EstimateService) SpringUtils.getBean("consumerRevenueEstimateServiceImpl");
	
	public void invoke() {
		try {
			List<Integer> list = new ArrayList<Integer>();
			//294, 262, 231, 243
			list.add(294);
			list.add(262);
			list.add(231);
			list.add(243);
			estimateService.batchEstimate(new BatchEstimate("hhy", "2017-04-01", "2017-04-30", list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}