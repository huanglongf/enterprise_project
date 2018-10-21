package com.bt.lmis.balance.task;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.EstimateMapper;
import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.model.Estimate;
import com.bt.lmis.balance.service.EstimateService;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: EstimateTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月24日 下午5:18:17 
 * 
 */
@Service("estimateTask")
public class EstimateTask {

	@Autowired
	private EstimateMapper<T> estimateMapper;
	@Resource(name="expressExpenditureEstimateServiceImpl")
	EstimateService expressExpenditureEstimateService;
	@Resource(name="consumerRevenueEstimateServiceImpl")
	EstimateService consumerRevenueEstimateService;
	
	/**
	 * @Title: estimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午8:01:32
	 */
	public void estimate() {
		System.out.println("**********结算预估任务开始**********");
		Estimate estimate=estimateMapper.ensurePriorityEstimate();
		while(CommonUtils.checkExistOrNot(estimate)) {
			try {
				BatchEstimate batchEstimate=new BatchEstimate(estimate.getBatchNumber(), estimate.getDomainFrom(), estimate.getDomainTo(), estimateMapper.ensureContractId(estimate.getId()));
				if(estimate.getEstimateType()==0) {
					// 支出
					expressExpenditureEstimateService.batchEstimate(batchEstimate);
					
				} else if(estimate.getEstimateType()==1) {
					// 收入
					consumerRevenueEstimateService.batchEstimate(batchEstimate);
					
				}
				// 当前预估批次收尾
				estimateMapper.finEstimate(estimate.getBatchNumber());
				
			} catch(Exception e) {
				e.printStackTrace();
				// 异常状态更新并踢出队列
				estimateMapper.errEstimate(estimate.getBatchNumber());
				
			}
			estimate=estimateMapper.ensurePriorityEstimate();
			
		}
		System.out.println("**********结算预估任务退出**********");
		
	}
	
}