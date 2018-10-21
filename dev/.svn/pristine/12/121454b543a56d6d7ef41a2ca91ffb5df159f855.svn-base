package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.CarrierFeeEstimateMapper;
import com.bt.lmis.balance.model.CarrierFeeFlag;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.ExpressFreightSummary;
import com.bt.lmis.balance.model.ManagementFeeDomainModel;
import com.bt.lmis.balance.model.TotalFreightDiscountDomainModel;
import com.bt.lmis.balance.util.EstimateUtil;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: CarrierFreightEstimate
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月16日 下午3:58:34 
 * 
 */
@Service("carrierFeeEstimate")
public class CarrierFeeEstimate extends Estimate {

	@Autowired
	private CarrierFeeEstimateMapper<T> carrierFeeEstimateMapper;
	
	@Override
	public EstimateResult estimate(EstimateParam param) {
		//
		EstimateResult estimateResult=new EstimateResult(true);
		//
		BigDecimal totalFreightDiscount=new BigDecimal(0);
		//
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同下总费用折扣及管理费情况查询");
		CarrierFeeFlag carrierFeeFlag=carrierFeeEstimateMapper.ensureCarrierFeeFlag(param);
		// 当此合同需要结算总运费折扣或管理费
		if(carrierFeeFlag != null && (carrierFeeFlag.isTotalFreightDiscountFlag() || carrierFeeFlag.isManagementFeeFlag())) {
			// 区分汇总管理费规则不同的客户，决定汇总流程
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同运费汇总流程区分");
			if(carrierFeeFlag.isManagementFeeFlag() && (carrierFeeEstimateMapper.distinguishManagementFee(param)==0)) {
				// 管理费开关打开
				// 并且管理费规则快递分别指定规则 - NIKE
				// 特殊流程
				param.getParam().put("summaryFlow", "customization");
				param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同运费汇总查询");
				List<ExpressFreightSummary> efs=carrierFeeEstimateMapper.ensureExpressFreightEstimateSummary(param);
				if(CommonUtils.checkExistOrNot(efs)) {
					for(int i=0; i<efs.size(); i++) {
						if(efs.get(i).getOrderNum() != 0) {
							param.getParam().put("express", efs.get(i).getExpress());
							// 管理费开关打开
							if(carrierFeeFlag.isManagementFeeFlag()){
								param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同管理费阶梯查询");
								List<ManagementFeeDomainModel> managementFeeDomainModel=carrierFeeEstimateMapper.ensureManagementFeeDomain(param);
								if(CommonUtils.checkExistOrNot(managementFeeDomainModel)) {
									// 无总运费折扣
									BigDecimal managementFee=EstimateUtil.ensureManagementFee(managementFeeDomainModel, efs.get(i), totalFreightDiscount);
									if(CommonUtils.checkExistOrNot(managementFee)) {
										param.getParam().put("feeType", 1);
										param.getParam().put("fee", managementFee);
										param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同承运商管理费生成");
										carrierFeeEstimateMapper.addCarrierFeeEstimate(param);
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			} else {
				// 所有承运商统一管理费规则 - 其他客户
				// 统一流程
				param.getParam().put("summaryFlow", "common");
				param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同运费汇总查询");
				List<ExpressFreightSummary> efs=carrierFeeEstimateMapper.ensureExpressFreightEstimateSummary(param);
				if(CommonUtils.checkExistOrNot(efs)) {
					param.getParam().put("express", "ALL");
					for(int i=0; i<efs.size(); i++) {
						if(efs.get(i) != null && efs.get(i).getOrderNum() != 0) {
							// 总运费折扣开关打开 
							if(carrierFeeFlag.isTotalFreightDiscountFlag()) {
								param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同总运费折扣阶梯查询");
								List<TotalFreightDiscountDomainModel> totalFreightDiscountDomainModel=carrierFeeEstimateMapper.ensureTotalFreightDiscountDomain(param);
								if(CommonUtils.checkExistOrNot(totalFreightDiscountDomainModel)) {
									totalFreightDiscount=EstimateUtil.ensureTotalFreightDiscount(totalFreightDiscountDomainModel, efs.get(i));
									if(CommonUtils.checkExistOrNot(totalFreightDiscount)) {
										param.getParam().put("feeType", 0);
										param.getParam().put("fee", totalFreightDiscount);
										param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同承运商总运费折扣生成");
										carrierFeeEstimateMapper.addCarrierFeeEstimate(param);
										
									}
									
								}
								
							}
							// 管理费开关打开
							if(carrierFeeFlag.isManagementFeeFlag()) {
								param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同管理费阶梯查询");
								List<ManagementFeeDomainModel> managementFeeDomainModel=carrierFeeEstimateMapper.ensureManagementFeeDomain(param);
								if(CommonUtils.checkExistOrNot(managementFeeDomainModel)) {
									// 无总运费折扣
									BigDecimal managementFee=EstimateUtil.ensureManagementFee(managementFeeDomainModel, efs.get(i), totalFreightDiscount);
									if(CommonUtils.checkExistOrNot(managementFee)) {
										param.getParam().put("feeType", 1);
										param.getParam().put("fee", managementFee);
										param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"合同承运商管理费生成");
										carrierFeeEstimateMapper.addCarrierFeeEstimate(param);
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return estimateResult;
		
	}

}