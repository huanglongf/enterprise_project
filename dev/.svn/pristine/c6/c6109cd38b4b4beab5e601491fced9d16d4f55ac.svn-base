package com.bt.lmis.balance;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.balance.task.OperatingCostDetailTask;

public class OperatingCostDetailTaskTest extends EstimateAbstract {

	@Test
	@Transactional
	@Rollback(false)
	public void consumerRevenueEstimateServiceImplTest(){
		

		System.out.println("测试开始");
		
		OperatingCostDetailTask operatingCostDetailTask = new OperatingCostDetailTask();
		operatingCostDetailTask.invoke();
		
		
		System.out.println("测试结束");
	}
	
	
	
	
}
