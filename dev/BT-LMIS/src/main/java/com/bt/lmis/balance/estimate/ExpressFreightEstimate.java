package com.bt.lmis.balance.estimate;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.ExpressFreightEstimateMapper;
import com.bt.lmis.balance.model.ConsumerCarrier;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: ExpressFreightEstimate
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 上午11:43:27 
 * 
 */
@Service("expressFreightEstimate")
public class ExpressFreightEstimate extends Estimate {

	@Autowired
	private ExpressFreightEstimateMapper<T> expressFreightEstimateMapper;
	
	@Override
	public EstimateResult estimate(EstimateParam param) {
		EstimateResult estimateResult=new EstimateResult(true);
		// 查询合同下使用快递情况
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同下使用快递情况查询");
		List<ConsumerCarrier> express=expressFreightEstimateMapper.ensureConsumerExpress(param);
		if(CommonUtils.checkExistOrNot(express)) {
			for(int i=0; i<express.size(); i++) {
				param.getParam().put("express", express.get(i).getCarrierCode());
				param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"使用"+express.get(i).getCarrierName()+"于圈定时间范围内结算明细汇总");
				expressFreightEstimateMapper.expressFreightEstimateSummary(param);
				
			}
			
		}
		return estimateResult;
		
	}

}