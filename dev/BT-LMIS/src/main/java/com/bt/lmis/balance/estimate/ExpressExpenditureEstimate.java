package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.dao.ExpressExpenditureEstimateMapper;
import com.bt.lmis.balance.dao.TempTableMapper;
import com.bt.lmis.balance.model.DomainModel;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.ExpressExpenditureDiscountSwitch;
import com.bt.lmis.balance.model.ExpressExpenditureEstimateProductTypeSummary;
import com.bt.lmis.balance.model.TotalFreightDiscountDomainModel;
import com.bt.lmis.balance.util.CommonUtil;
import com.bt.lmis.balance.util.EstimateUtil;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: ExpressExpenditureEstimate
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月9日 下午1:51:22 
 * 
 */
@Service("expressExpenditureEstimate")
public class ExpressExpenditureEstimate extends Estimate {
	
	private static final Logger logger=Logger.getLogger(ExpressExpenditureEstimate.class);
	
	@Autowired
	private TempTableMapper<T> tempTableMapper;
	@Autowired
	private ExpressExpenditureEstimateMapper<T> expressExpenditureEstimateMapper;
	
	@Override
	public EstimateResult estimate(EstimateParam param) {
		//
		EstimateResult estimateResult=new EstimateResult(true);
		try {
			// 结算明细表名
			param.getParam().put("detailTable", Constant.EXPRESS_SETTLEMENT_DETAIL);
			//
			if(param.getContract().getContractOwner().equals(Constant.SF)) {
				// 顺丰汇总使用临时表
				param.getParam().put("detailTable", Constant.EXPRESS_SETTLEMENT_DETAIL_TEMP+Constant.SYM_UNDERLINE+param.getBatchNumber());
				// 创建批次临时表
				param.getParam().put("logTitle","顺丰快递结算明细批次临时表创建");
				tempTableMapper.createTempExpressSettlementDetailTable(param);
				// 复制结算明细
				param.getParam().put("logTitle","顺丰快递结算明细向批次临时表复制");
				expressExpenditureEstimateMapper.copyDetailToTempInDomain(param);
				// 还原数据折扣
				param.getParam().put("logTitle","批次临时表顺丰快递结算明细折扣重置");
				expressExpenditureEstimateMapper.resetDiscount(param);
				
			}
			// 汇总
			param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"于圈定时间范围内结算明细汇总");
		    expressExpenditureEstimateMapper.expressExpenditureEstimateSummary(param);
			// 折扣
			// 按产品类型划分汇总
			param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"明细汇总按产品类型划分汇总");
			List<ExpressExpenditureEstimateProductTypeSummary> eeepts=expressExpenditureEstimateMapper.summaryByProductType(param);
			if(CommonUtils.checkExistOrNot(eeepts)) {
				// 折扣开关
				param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"汇总折扣开关查询");
				ExpressExpenditureDiscountSwitch eeds=expressExpenditureEstimateMapper.getDiscountSwitch(param);
				// 是否配置运单折扣启用条件（顺丰才有）
				if(eeds.isWaybillDiscount()) {
					for(int i=0; i<eeepts.size(); i++) {
						param.getParam().put("productType",eeepts.get(i).getProductType());
						param.getParam().put("logTitle","顺丰快递"+eeepts.get(i).getProductTypeName()+"运单折扣阶梯查询");
						List<DomainModel> waybillDiscountDomain=expressExpenditureEstimateMapper.ensureWaybillDiscountDomain(param);
						if(CommonUtils.checkExistOrNot(waybillDiscountDomain)) {
							for(int j=0; j<waybillDiscountDomain.size(); j++) {
								if(CommonUtil.isInDomain(waybillDiscountDomain.get(j), eeepts.get(i).getAfterDiscount())) {
									param.getParam().put("discount", waybillDiscountDomain.get(j).getDomainValue().divide(new BigDecimal(100)));
									param.getParam().put("logTitle","顺丰快递"+eeepts.get(i).getProductTypeName()+"结算明细折扣更新");
									expressExpenditureEstimateMapper.detailDiscount(param);
									param.getParam().put("logTitle","顺丰快递"+eeepts.get(i).getProductTypeName()+"结算明细汇总折扣更新");
									expressExpenditureEstimateMapper.summaryDiscount(param);
									break;
									
								}
								
							}
							
						}
						
					}
					
				}
				if(eeds.isTotalFreightDiscount()) {
					param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"总运费折扣阶梯查询");
					List<TotalFreightDiscountDomainModel> totalFreightDiscountDomainModel=expressExpenditureEstimateMapper.ensureTotalFreightDiscountDomain(param);
					if(CommonUtils.checkExistOrNot(totalFreightDiscountDomainModel)) {
						BigDecimal discount=EstimateUtil.ensureTotalFreightDiscount(totalFreightDiscountDomainModel, eeepts.get(0));
						if(CommonUtils.checkExistOrNot(discount)) {
							param.getParam().put("discount",discount);
							param.getParam().put("insuranceContain",totalFreightDiscountDomainModel.get(0).getInsuranceContain());
							param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"结算明细汇总折扣更新");
							expressExpenditureEstimateMapper.summaryDiscount(param);
							
						}
						
					}
					
				}
				
			}
			// 生成预估账单报表
			EstimateUtil.generateExpressExpenditureEstimate(param);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			//
			estimateResult.setFlag(false);
			//
			Map<String, Object> errorMsg= new HashMap<String, Object>();
			errorMsg.put("errorInfo", CommonUtils.getExceptionStack(e));
			estimateResult.setMsg(errorMsg);
			
		} finally {
			if(!param.getParam().get("detailTable").equals(Constant.EXPRESS_SETTLEMENT_DETAIL)) {
				param.getParam().put("logTitle", "顺丰快递结算明细批次临时表删除");
				expressExpenditureEstimateMapper.killTempDetail(param);
				
			}
			param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"快递支出预估删除");
			expressExpenditureEstimateMapper.delExpressExpenditureEstimateByBatchNumber(param);
			
		}
		return estimateResult;
		
	}

}