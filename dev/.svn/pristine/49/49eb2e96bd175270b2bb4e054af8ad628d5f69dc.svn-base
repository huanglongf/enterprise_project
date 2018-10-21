package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.model.AddservicefeeBilldataEstimate;
import com.bt.lmis.balance.model.AddservicefeeEstimateSettlement;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.service.AddservicefeeBilldataEstimateService;
import com.bt.lmis.balance.service.AddservicefeeEstimateSettlementService;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.AddservicefeeData;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.Store;
import com.bt.lmis.service.AddservicefeeDataService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

@SuppressWarnings("unchecked")
@Service("valueAddedFeeEstimate")
public class ValueAddedFeeEstimate extends Estimate {

	// 操作费结算规则表服务类
	OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService = (OperationSettlementRuleService<OperationSettlementRule>) SpringUtils
			.getBean("operationSettlementRuleServiceImpl");
	// 店铺服务类
	StoreService<Store> storeService = (StoreService<Store>) SpringUtils.getBean("storeServiceImpl");
	// 快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>) SpringUtils
			.getBean("expressContractServiceImpl");
	// 客户服务类
	ClientService<Client> clientService = (ClientService<Client>) SpringUtils.getBean("clientServiceImpl");
	// 增值服务费原始数据服务类
	AddservicefeeDataService<AddservicefeeData> addservicefeeDataService = (AddservicefeeDataService<AddservicefeeData>) SpringUtils
			.getBean("addservicefeeDataServiceImpl");

	// 增值服务费结算服务类
	AddservicefeeEstimateSettlementService<AddservicefeeEstimateSettlement> addservicefeeEstimateSettlementService = (AddservicefeeEstimateSettlementService<AddservicefeeEstimateSettlement>) SpringUtils
			.getBean("addservicefeeEstimateSettlementServiceImpl");
	// 增值服务费预估服务类
	AddservicefeeBilldataEstimateService<AddservicefeeBilldataEstimate> addservicefeeBilldataEstimateService = (AddservicefeeBilldataEstimateService<AddservicefeeBilldataEstimate>) SpringUtils
			.getBean("addservicefeeBilldataEstimateServiceImpl");

	@Override
	public EstimateResult estimate(EstimateParam param) {
		EstimateResult result = new EstimateResult(false);
		try {
			// 获取合同
			Contract contract = param.getContract();
			int cid = contract.getId();
			if (cid == 288) {
				System.out.println(contract);
			}
			String contractOwner = contract.getContractOwner();
			String contractType = contract.getContractType();
			List<Store> storeList = new ArrayList<Store>();
			if (contractType.equals("3")) {
				System.out.println("查询store信息--------");
				Store store = storeService.findByContractOwner(contractOwner);
				if (CommonUtils.checkExistOrNot(store)) {
					storeList.add(store);
				} else {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(cid);
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info("合同ID[" + cid + "],店铺不存在！");
					bEL.setDefault1("增值服务费");
					bEL.setDefault2(param.getBatchNumber());
					expressContractService.addBalanceErrorLog(bEL);
					return result;
				}
			} else if (contractType.equals("4")) {
				System.out.println("根据合同拥有者查询客户信息");
				Client client = clientService.findByContractOwner(contractOwner);
				System.out.println("根据客户查询商铺store信息");
				storeList = storeService.selectByClient(client.getId());
				if (!CommonUtils.checkExistOrNot(storeList)) {
					expressContractService.addBalanceErrorLog(
							new BalanceErrorLog(0, null, Integer.valueOf(cid), BaseConst.PRO_RESULT_FLAG_ERROR,
									"合同ID[" + cid + "]," + client.getClient_name() + "客户没有绑定店铺！", null, "增值服务费", param.getBatchNumber(),
									null, new Date()));
					return result;
				}
			}
			AddservicefeeData afd = new AddservicefeeData();
			afd.setStart_time(param.getFromDate());
			afd.setEnd_time(param.getToDate());
			// 查询原始数据
			System.out.println("addservicefeeData查询原始数据");
			List<Map<String, String>> ASNList = addservicefeeDataService.findGroupByServiceName(afd);
			// 获取操作费规则
			OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(String.valueOf(cid)).size() != 0
					? operationSettlementRuleService.findByCBID(String.valueOf(cid)).get(0) : null;
			//明细数据入库
			settlement(param, storeList, oSR);
			// 计算
			for (int k = 0; k < ASNList.size(); k++) {
				BigDecimal sumPrice = new BigDecimal(0.00);
				BigDecimal sumQty = new BigDecimal(0);
				for (int l = 0; l < storeList.size(); l++) {
					AddservicefeeEstimateSettlement afs = new AddservicefeeEstimateSettlement();
					afs.setAddservice_name(ASNList.get(k).get("addservice_name"));
					afs.setStore_name(storeList.get(l).getStore_name());
					afs.setContract_id(param.getContract().getId());
					afs.setBatch_number(param.getBatchNumber());
					System.out.println("查询明细AddservicefeeEstimateSettlement");
					List<AddservicefeeEstimateSettlement> afsList = addservicefeeEstimateSettlementService.findByAds(afs);
					for (AddservicefeeEstimateSettlement a : afsList) {
						sumPrice = sumPrice.add(a.getAmount());
						sumQty = sumQty.add(a.getQty());
					}
				}
				if (CommonUtils.checkExistOrNot(oSR) && sumQty.compareTo(new BigDecimal(0)) > 0) {
					try {
						AddservicefeeBilldataEstimate entity = new AddservicefeeBilldataEstimate();
						entity.setCreate_time(new Date());
						entity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
						entity.setContract_id(oSR.getContract_id().toString());
						entity.setQty(sumQty.intValue());
						entity.setAmount(sumPrice);
						entity.setAddservice_name(ASNList.get(k).get("addservice_name"));
						entity.setBatch_number(param.getBatchNumber());
						AddservicefeeBilldataEstimate afb = new AddservicefeeBilldataEstimate();
						afb.setContract_id(oSR.getContract_id().toString());
						afb.setBatch_number(param.getBatchNumber());
						afb.setAddservice_name(ASNList.get(k).get("addservice_name"));
						addservicefeeBilldataEstimateService.save(entity);
					} catch (Exception e) {
						e.printStackTrace();
						try {
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null,
									Integer.valueOf(oSR.getContract_id()), BaseConst.PRO_RESULT_FLAG_ERROR,
									"合同ID[" + oSR.getContract_id() + "],插入数据异常！", null, "增值服务费", param.getBatchNumber(), null, new Date()));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setFlag(true);
		return result;
	}

	/**
	 * 结算
	 */
	public void settlement(EstimateParam param,List<Store> storeList,OperationSettlementRule oSR) {
		String id = null;
		try {
			Contract contract = param.getContract();
				// 添加店铺集合
				System.out.println(contract.getContractName() + "合同开始结算");
				// 增值服务费开关
				if (null != oSR) {
					Integer osr_addservice_fee = oSR.getOsr_addservice_fee();
					if (osr_addservice_fee != null && osr_addservice_fee == 1) {
						for (int k = 0; k < storeList.size(); k++) {
							Store store = storeList.get(k);
							AddservicefeeData afd = new AddservicefeeData();
							afd.setStore_name(store.getStore_name());
							afd.setCost_code(store.getCost_center());
							afd.setStart_time(param.getFromDate());
							afd.setEnd_time(param.getToDate());
							List<AddservicefeeData> afdList = addservicefeeDataService.findByAll(afd);
							for (int j = 0; j < afdList.size(); j++) {
								id = String.valueOf(afdList.get(j).getId());
								AddservicefeeEstimateSettlement entity = new AddservicefeeEstimateSettlement();
								// QC费用
								Integer osr_qc_fee = oSR.getOsr_qc_fee();
								BigDecimal price = new BigDecimal(0.00);
								String unit = "";
								if (osr_qc_fee!=null && osr_qc_fee == 1 && afdList.get(j).getAddservice_name().equals("QC费用")) {
									price = oSR.getOsr_qc_pieceprice();
									unit = oSR.getOsr_qc_pieceprice_unit();
								}
								// 放赠品费用
								Integer osr_gift_fee = oSR.getOsr_gift_fee();
								if (osr_gift_fee!=null && osr_gift_fee == 1 && afdList.get(j).getAddservice_name().equals("放赠品费用")) {
									price = oSR.getOsr_gift_billprice();
									unit = oSR.getOsr_gift_billprice_unit();
								}
								// 测量尺寸
								Integer osr_mesurement = oSR.getOsr_mesurement();
								if (osr_mesurement!=null && osr_mesurement == 1 && afdList.get(j).getAddservice_name().equals("测量尺寸")) {
									price = oSR.getOsr_mesurement_price();
									unit = oSR.getOsr_mesurement_price_unit();
								}
								// 代贴防伪码
								Integer osr_security_code = oSR.getOsr_security_code();
								if (osr_security_code!=null && osr_security_code == 1 && afdList.get(j).getAddservice_name().equals("代贴防伪码")) {
									price = oSR.getOsr_security_code_price();
									unit = oSR.getOsr_security_code_price_unit();
								}
								// 代贴条码
								Integer osr_itemnum = oSR.getOsr_itemnum();
								if (osr_itemnum!=null && osr_itemnum == 1 && afdList.get(j).getAddservice_name().equals("代贴条码")) {
									price = oSR.getOsr_itemnum_price();
									unit = oSR.getOsr_itemnum_price_unit();
								}
								// 吊牌拍照
								Integer osr_drop_photo = oSR.getOsr_drop_photo();
								if (osr_drop_photo!=null && osr_drop_photo == 1 && afdList.get(j).getAddservice_name().equals("吊牌拍照")) {
									price = oSR.getOsr_drop_photo_price();
									unit = oSR.getOsr_drop_photo_price_unit();
								}
								// 短信服务费
								Integer osr_message = oSR.getOsr_message();
								if (osr_message!=null && osr_message == 1 && afdList.get(j).getAddservice_name().equals("短信服务费")) {
									price = oSR.getOsr_message_price();
									unit = oSR.getOsr_message_price_unit();
								}
								// 额外税费及管理费税
								Integer osr_extra_fee = oSR.getOsr_extra_fee();
								if (osr_extra_fee!=null && osr_extra_fee == 1 && afdList.get(j).getAddservice_name().equals("额外税费及管理费税")) {
									price = oSR.getOsr_extra_fee_price();
									unit = oSR.getOsr_extra_fee_price_unit();
								}
								// 更改包装
								Integer osr_change_package = oSR.getOsr_change_package();
								if (osr_change_package!=null && osr_change_package == 1 && afdList.get(j).getAddservice_name().equals("更改包装")) {
									price = oSR.getOsr_change_package_price();
									unit = oSR.getOsr_change_package_price_unit();
								}
								// 非工作时间作业
								Integer osr_notworkingtime = oSR.getOsr_notworkingtime();
								if (osr_notworkingtime!=null && osr_notworkingtime == 1 && afdList.get(j).getAddservice_name().equals("非工作时间作业")) {
									price = oSR.getOsr_notworkingtime_price();
									unit = oSR.getOsr_notworkingtime_price_unit();
								}
								// 货票同行
								Integer osr_waybillpeer = oSR.getOsr_waybillpeer();
								if (osr_waybillpeer != null && osr_waybillpeer == 1 && afdList.get(j).getAddservice_name().equals("货票同行")) {
									price = oSR.getOsr_waybillpeer_price();
									unit = oSR.getOsr_waybillpeer_price_unit();
								}
								// 紧急收货
								Integer osr_emergency_receipt = oSR.getOsr_emergency_receipt();
								if (osr_emergency_receipt!= null && osr_emergency_receipt == 1 && afdList.get(j).getAddservice_name().equals("紧急收货")) {
									price = oSR.getOsr_emergency_receipt_price();
									unit = oSR.getOsr_emergency_receipt_price_unit();
								}
								// 取消订单拦截
								Integer osr_cancelorder = oSR.getOsr_cancelorder();
								if (osr_cancelorder!=null && osr_cancelorder == 1 && afdList.get(j).getAddservice_name().equals("取消订单拦截")) {
									price = oSR.getOsr_cancelorder_price();
									unit = oSR.getOsr_cancelorder_price_unit();
								}
								// 条码制作人工费
								Integer osr_makebarfee = oSR.getOsr_makebarfee();
								if (osr_makebarfee!=null && osr_makebarfee == 1 && afdList.get(j).getAddservice_name().equals("条码制作人工费")) {
									price = oSR.getOsr_makebarfee_price();
									unit = oSR.getOsr_makebarfee_price_unit();
								}
								// 卸货费
								Integer osr_landfee = oSR.getOsr_landfee();
								if (osr_landfee!=null && osr_landfee == 1 && afdList.get(j).getAddservice_name().equals("卸货费")) {
									price = oSR.getOsr_landfee_price();
									unit = oSR.getOsr_landfee_price_util();
								}
								entity.setId(UUID.randomUUID().toString());
								entity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
								entity.setCreate_time(new Date());
								entity.setCost_code(afdList.get(j).getCost_code());
								entity.setCost_name(afdList.get(j).getCost_name());
								entity.setSystem_warehousename(afdList.get(j).getSystem_warehousename());
								entity.setNumer(afdList.get(j).getNumer());
								entity.setDate(afdList.get(j).getDate());
								entity.setArea(afdList.get(j).getArea());
								entity.setWarehouse_id(afdList.get(j).getWarehouse_id());
//								entity.setWarehouse_id("123");
								entity.setPhysical_warehouse(afdList.get(j).getPhysical_warehouse());
//								entity.setPhysical_warehouse("123");
								entity.setDepartment(afdList.get(j).getDepartment());
								entity.setStore_id(afdList.get(j).getStore_id());
								entity.setStore_name(afdList.get(j).getStore_name());
								entity.setAddservice_code(afdList.get(j).getAddservice_code());
								entity.setAddservice_name(afdList.get(j).getAddservice_name());
								entity.setService_instruction(afdList.get(j).getService_instruction());
								entity.setWmsaddservice_order(afdList.get(j).getWmsaddservice_order());
								entity.setPrice(afdList.get(j).getPrice());
								entity.setUnit(unit);
								entity.setQty(afdList.get(j).getQty());
								entity.setStore_applicant(afdList.get(j).getStore_applicant());
								entity.setWarehouse_confirmer(afdList.get(j).getWarehouse_confirmer());
								entity.setRemark(afdList.get(j).getRemark());
								entity.setAmount(price.multiply(afdList.get(j).getQty()));
								entity.setData_id(afdList.get(j).getId());
								entity.setBatch_number(param.getBatchNumber());
								entity.setContract_id(contract.getId());
								// 新增增值服务费
								addservicefeeEstimateSettlementService.save(entity);
							}
						}
					}
				}
		} catch (Exception e) {
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null,
						Integer.valueOf(oSR.getContract_id()), BaseConst.PRO_RESULT_FLAG_ERROR,
						"tb_addservicefee_data[" + id + "],添加结算明细异常！", "tb_addservicefee_data:"+id, "增值服务费", param.getBatchNumber(), null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	
		}
	}

}
