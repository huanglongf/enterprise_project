package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.BalanceSummaryUsedExMapper;
import com.bt.lmis.dao.CarrierFeeFlagMapper;
import com.bt.lmis.dao.CarrierUsedSummaryMapper;
import com.bt.lmis.dao.ManagementECMapper;
import com.bt.lmis.dao.SePoolTransMapper;
import com.bt.lmis.dao.TotalFreightDiscountMapper;
import com.bt.lmis.model.CarrierFeeFlag;
import com.bt.lmis.model.CarrierUsedSummary;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.service.CarrierUsedBalanceService;
import com.bt.lmis.utils.ExpressBalanceUtils;
import com.bt.utils.CommonUtils;
@Service
public class CarrierUsedBalanceServiceImpl implements CarrierUsedBalanceService {
	@Autowired
	private ManagementECMapper<T> managementECMapper;
	@Autowired
	private TotalFreightDiscountMapper<T> totalFreightDiscountMapper;
	@Autowired
	private SePoolTransMapper<T> sePoolTransMapper;
	@Autowired
	private BalanceSummaryUsedExMapper<T> balanceSummaryUsedExMapper;
	@Autowired
	private CarrierFeeFlagMapper<T> carrierFeeFlagMapper;
	@Autowired
	private CarrierUsedSummaryMapper<T> carrierUsedSummaryMapper;
	
	@Override
	public List<Map<String, Object>> getRecords(String con_id, String balance_month) {
		return carrierUsedSummaryMapper.getCarrierUsedSummary(Integer.parseInt(con_id), null, balance_month);
		
	}
	
	@Override
	public void carrierUsedSummary(ContractBasicinfo cb, String ym) throws Exception {
		Integer con_id= Integer.parseInt(cb.getId());
		// 获取总运费折扣及管理费信息
		CarrierFeeFlag carrierFeeFlag= carrierFeeFlagMapper.selectByConId(con_id);
		// 当此合同需要结算总运费折扣及管理费
		if(carrierFeeFlag.getTotalFreightDiscount_flag() || carrierFeeFlag.getManagementFee_flag()){
			String contract_owner= cb.getContract_owner();
			String contract_type= null;
			CarrierUsedSummary cUS= null;
			BigDecimal total_order_num= new BigDecimal(0);
			BigDecimal total_freight= new BigDecimal(0);
			BigDecimal total_insurance= new BigDecimal(0);
			List<Map<String, Object>> summarys= null;
			Map<String, Object> summary= null;
			BigDecimal total_freight_discount= new BigDecimal(0);
			BigDecimal management_fee= new BigDecimal(0);
			// 当该快递该月份汇总已做过
			contract_type = cb.getContract_type();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("con_id", con_id);
			param.put("belong_to", "ALL");
			// 区分汇总管理费规则不同的客户，决定汇总流程
			if(carrierFeeFlag.getManagementFee_flag() && (managementECMapper.checkCombination(param) == 0)){
				// 管理费开关打开
				// 并且管理费规则快递分别指定规则 - NIKE
				// 特殊流程
				// 快递汇总
				summarys = balanceSummaryUsedExMapper.getSummary(con_id, ym, 1);
				if(CommonUtils.checkExistOrNot(summarys)){
					for(int j= 0; j < summarys.size(); j++){
						summary= summarys.get(j);
						total_order_num= new BigDecimal(summary.get("order_num").toString());
						total_freight= new BigDecimal(summary.get("total_freight").toString());
						total_insurance= new BigDecimal(summary.get("total_insurance").toString());
						if(total_order_num.compareTo(new BigDecimal("0")) != 0){
							cUS = new CarrierUsedSummary();
							cUS.setCon_id(con_id);
							cUS.setBalance_subject(contract_owner);
							cUS.setSubject_type(Integer.parseInt(contract_type));
							cUS.setCarrier_used(summary.get("express").toString());
							cUS.setBalance_month(ym);
							cUS.setFee_type(1);
							management_fee= ExpressBalanceUtils.getManagerCost(total_freight, total_insurance, total_freight_discount, managementECMapper.selectAllManEC(con_id, summary.get("express").toString()));
							cUS.setFee(management_fee);
							carrierUsedSummaryMapper.addCarrierUsedSummary(cUS);
							
						}
						
					}
					
				}
				
			} else {
				// 所有承运商统一管理费规则 - 其他客户
				// 统一流程
				//　快递汇总
				summary = balanceSummaryUsedExMapper.getSummary(con_id, ym, 0).get(0);
				if(CommonUtils.checkExistOrNot(summary)){
					total_order_num= new BigDecimal(summary.get("order_num").toString());
					total_freight= new BigDecimal(summary.get("total_freight").toString());
					total_insurance= new BigDecimal(summary.get("total_insurance").toString());
					
				}
				// 物流汇总
				summary = sePoolTransMapper.getSummary(con_id, ym);
				if(CommonUtils.checkExistOrNot(summary)){
					total_order_num = new BigDecimal(summary.get("order_num").toString());
					total_freight = new BigDecimal(summary.get("total_fee").toString());
					
				}
				if(total_order_num.compareTo(new BigDecimal("0")) != 0){
					// 总运费折扣开关打开 
					if(carrierFeeFlag.getTotalFreightDiscount_flag()){
						cUS = new CarrierUsedSummary();
						cUS.setCon_id(con_id);
						cUS.setBalance_subject(contract_owner);
						cUS.setSubject_type(Integer.parseInt(contract_type));
						cUS.setCarrier_used("ALL");
						cUS.setBalance_month(ym);
						cUS.setFee_type(0);
						total_freight_discount= ExpressBalanceUtils.getTotalFreightDiscount(total_freight, total_insurance, total_order_num, totalFreightDiscountMapper.selectAllTFD(con_id, null, null));
						cUS.setFee(total_freight_discount);
						carrierUsedSummaryMapper.addCarrierUsedSummary(cUS);
						
					}
					// 管理费开关打开
					if(carrierFeeFlag.getManagementFee_flag()){
						cUS = new CarrierUsedSummary();
						cUS.setCon_id(con_id);
						cUS.setBalance_subject(contract_owner);
						cUS.setSubject_type(Integer.parseInt(contract_type));
						cUS.setCarrier_used("ALL");
						cUS.setBalance_month(ym);
						cUS.setFee_type(1);
						management_fee= ExpressBalanceUtils.getManagerCost(total_freight, total_insurance, total_freight_discount, managementECMapper.selectAllManEC(con_id, null));
						cUS.setFee(management_fee);
						carrierUsedSummaryMapper.addCarrierUsedSummary(cUS);
						
					}
					
				}
				
			}
			
		}
						
	}
	
}