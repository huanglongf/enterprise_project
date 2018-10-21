package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.PackageCharageEstimate;
import com.bt.lmis.balance.model.WarehouseExpressDataStoreSettlementEstimate;
import com.bt.lmis.balance.service.PackageCharageEstimateService;
import com.bt.lmis.balance.service.WarehouseExpressDataStoreSettlementEstimateService;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.controller.form.WarehouseExpressDataQueryParam;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.PackagePriceLadder;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.PackagePriceLadderService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;

@SuppressWarnings("unchecked")
@Service("packageFeeEstimate")
public class PackageFeeEstimate extends Estimate {

	// 打包费预估服务类
	PackageCharageEstimateService<PackageCharageEstimate> packageCharageEstimateService = (PackageCharageEstimateService<PackageCharageEstimate>) SpringUtils
			.getBean("packageCharageEstimateServiceImpl");
	// 打包费服务类
	PackageChargeService<PackagePrice> packageChargeService = (PackageChargeService<PackagePrice>) SpringUtils
			.getBean("packageChargeServiceImpl");
	// 合同服务类
	ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService = (ContractBasicinfoService<ContractBasicinfo>) SpringUtils
			.getBean("contractBasicinfoServiceImpl");
	// 店铺服务类
	StoreService<Store> storeService = (StoreService<Store>) SpringUtils.getBean("storeServiceImpl");
	// 快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>) SpringUtils
			.getBean("expressContractServiceImpl");
	// 客户服务类
	ClientService<Client> clientService = (ClientService<Client>) SpringUtils.getBean("clientServiceImpl");
	// 快递原始数据表服务类
	WarehouseExpressDataService<T> warehouseExpressDataService = (WarehouseExpressDataService<T>) SpringUtils
			.getBean("warehouseExpressDataServiceImpl");
	// 快递结算阶梯表服务类
	PackagePriceLadderService<PackagePriceLadder> packagePriceLadderService = (PackagePriceLadderService<PackagePriceLadder>) SpringUtils
			.getBean("packagePriceLadderServiceImpl");
	// 快递结算明细表服务类
	WarehouseExpressDataStoreSettlementEstimateService<WarehouseExpressDataStoreSettlementEstimate> warehouseExpressDataStoreSettlementEstimateService = (WarehouseExpressDataStoreSettlementEstimateService<WarehouseExpressDataStoreSettlementEstimate>) SpringUtils
			.getBean("warehouseExpressDataStoreSettlementEstimateServiceImpl");

	@Override
	public EstimateResult estimate(EstimateParam param) {
		settlement(param);
		EstimateResult estimateResult = new EstimateResult();
		estimateResult.setFlag(false);
		// 获取合同
		Contract cb = param.getContract();
		// 打包费信息
		PackagePrice pp = packageChargeService.getPackagePrice(cb.getId());
		if (CommonUtils.checkExistOrNot(pp)) {
			// 根据合同主信息查询
			if (pp.getCarrier_charge() == 0 || pp.getOperation() == 0 || pp.getStorage() == 0
					|| pp.getConsumable() == 0) {
				// 打包费集合
				Map<String, Object> map = new HashMap<>();
				map.put("clientCode", cb.getId());
				map.put("batch_number", param.getBatchNumber());
				List<Map<String, Object>> dbfList = contractBasicinfoService.findSectionDBF(map);
				for (int j = 0; j < dbfList.size(); j++) {
					PackageCharageEstimate pcs = new PackageCharageEstimate();
					pcs.setId(UUID.randomUUID().toString().replace("-", ""));
					pcs.setCreate_time(new Date());
					pcs.setUpdate_time(new Date());
					pcs.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					pcs.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
					pcs.setBatch_number(param.getBatchNumber());
					pcs.setCbid(String.valueOf(dbfList.get(j).get("cbid")));
					pcs.setClient_code(String.valueOf(dbfList.get(j).get("client_code")));
					pcs.setClient_name(String.valueOf(dbfList.get(j).get("client_name")));
					pcs.setDbf_qty(String.valueOf(dbfList.get(j).get("cnt")));
					pcs.setTotal_price(String.valueOf(dbfList.get(j).get("package_price")));
					try {
						packageCharageEstimateService.save(pcs);
					} catch (Exception e) {
						e.printStackTrace();
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(cb.getId());
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("bal_package_charage_estimate: 添加预估异常！");
						bEL.setDefault1("打包费");
						bEL.setDefault2(param.getBatchNumber());
						try {
							expressContractService.addBalanceErrorLog(bEL);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						return estimateResult;
					}
				}
			}
		}
		estimateResult.setFlag(true);
		return estimateResult;
	}
	
	/**
	 * 结算
	 */
	public void settlement(EstimateParam param) {
		String id = null;
		Contract contract = param.getContract();
		try {
				String contract_owner = contract.getContractOwner();
				String contract_type = contract.getContractType();
				List<Store> storeList = new ArrayList<Store>();
				if (contract_type.equals("3")) {
					Store store = storeService.findByContractOwner(contract_owner);
					if (null != store) {
						storeList.add(store);
					} else {
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(contract.getId());
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("合同ID[" + contract.getId() + "],店铺不存在！");
						bEL.setDefault1("打包费");
						bEL.setDefault2(param.getBatchNumber());
						expressContractService.addBalanceErrorLog(bEL);
						return;
					}
				} else if (contract_type.equals("4")) {
					System.out.println("根绝合同查询客户");
					Client client = clientService.findByContractOwner(contract_owner);
					System.out.println("根据客户查询店铺");
					List<Store> sList = storeService.selectByClient(client.getId());
					if (null != sList && sList.size() != 0) {
						for (int j = 0; j < sList.size(); j++) {
							storeList.add(sList.get(j));
						}
					} else {
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null,
								contract.getId(), BaseConst.PRO_RESULT_FLAG_ERROR,
								"合同ID[" + contract.getId() + "]," + client.getClient_name() + "客户没有绑定店铺！", null,
								"打包费", param.getBatchNumber(), null, new Date()));
						return;
					}
				}
				// 打包费信息
				PackagePrice pp = packageChargeService.getPackagePrice(contract.getId());
				if (null == pp) {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(contract.getId());
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info(contract.getId() + "合同，打包费未维护！");
					bEL.setDefault1("打包费");
					bEL.setDefault2(param.getBatchNumber());
					expressContractService.addBalanceErrorLog(bEL);
					return;
				}
				// //承运商费用 0开 1关
				int carrier_charge = pp.getCarrier_charge();
				// //操作费 0开 1关
				int operation = pp.getOperation();
				// //仓储费 0开 1关
				int storage = pp.getStorage();
				// //耗材费 0开 1关
				int consumable = pp.getConsumable();
				// 打包费单价
				BigDecimal unit_price = new BigDecimal(pp.getUnit_price());
				// 保价费阶梯类型 1无阶梯 2总费用阶梯 3超出部分阶梯
				int insurance = pp.getInsurance();
				// 根据合同主信息查询
				if (carrier_charge == 0 || operation == 0 || storage == 0 || consumable == 0) {
					for (int j = 0; j < storeList.size(); j++) {
						WarehouseExpressDataQueryParam param2 = new WarehouseExpressDataQueryParam();
						param2.setStore_name(storeList.get(j).getStore_name());
						param2.setStart_time(param.getFromDate());
						param2.setEnd_time(param.getToDate());
						int num = warehouseExpressDataService.countSection(param2);
						Map<String,Object> data = new HashMap<>();
						data.put("store_name",storeList.get(j).getStore_name());
						// 获取结算周期
						data.put("start_time",param.getFromDate());
						data.put("end_time",param.getToDate());
						
						int pageSize = 300000;
						List<WarehouseExpressData> dataList = new ArrayList<>();
						for(int page = 0;page*pageSize<num;page++){
							int pageBegin = pageSize*page;
							data.put("page",pageBegin);
							data.put("page_size",pageSize);
							// 该店铺下的所有原始数据 dataList
							dataList = warehouseExpressDataService.querySection(data);
						}
						
						for (int k = 0; k < dataList.size(); k++) {
							id = String.valueOf(dataList.get(k).getId());
							BigDecimal SstoreSum = new BigDecimal(0.00);
							BigDecimal order_amount = dataList.get(k).getOrder_amount();
							SstoreSum = unit_price;
							WarehouseExpressDataStoreSettlementEstimate dataSettlement = new WarehouseExpressDataStoreSettlementEstimate();
							dataSettlement.setEpistatic_order(dataList.get(k).getEpistatic_order());
							dataSettlement.setOrder_type(dataList.get(k).getOrder_type());
							dataSettlement.setId(UUID.randomUUID().toString());
							dataSettlement.setCreate_time(new Date());
							dataSettlement.setLength(dataList.get(k).getLength());
							dataSettlement.setInsurance_flag(dataList.get(k).getInsurance_flag());
							dataSettlement.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
							dataSettlement.setTransport_direction(null != dataList.get(k).getTransport_direction()
									&& dataList.get(k).getTransport_direction().equals("正向运输") ? "0" : "1");
							dataSettlement.setWarehouse(dataList.get(k).getWarehouse());
							dataSettlement.setDelivery_address(dataList.get(k).getDelivery_address());
							dataSettlement.setItemtype_code(dataList.get(k).getItemtype_code());
							dataSettlement.setItemtype_name(dataList.get(k).getItemtype_name());
							dataSettlement.setTransport_code(dataList.get(k).getTransport_code());
							dataSettlement.setTransport_name(dataList.get(k).getTransport_name());
							dataSettlement.setDelivery_order(dataList.get(k).getDelivery_order());
							dataSettlement.setBus_date(dataList.get(k).getBus_date());
							dataSettlement.setTransport_time(dataList.get(k).getTransport_time());
							dataSettlement.setExpress_number(dataList.get(k).getExpress_number());
							dataSettlement.setCost_center(dataList.get(k).getCost_center());
							dataSettlement.setStore_code(dataList.get(k).getStore_code());
							dataSettlement.setStore_name(dataList.get(k).getStore_name());
							dataSettlement.setWeight(dataList.get(k).getWeight());
							dataSettlement.setSku_number(dataList.get(k).getSku_number());
							dataSettlement.setLength(dataList.get(k).getLength());
							dataSettlement.setWidth(dataList.get(k).getWidth());
							dataSettlement.setHigth(dataList.get(k).getHigth());
							dataSettlement.setVolumn(dataList.get(k).getVolumn());
							dataSettlement.setOrder_amount(dataList.get(k).getOrder_amount());
							dataSettlement.setProvince(dataList.get(k).getProvince());
							dataSettlement.setCity(dataList.get(k).getCity());
							dataSettlement.setState(dataList.get(k).getState());
							dataSettlement.setStreet(dataList.get(k).getStreet());
							dataSettlement.setCod_flag(dataList.get(k).getCod_flag().toString());
							dataSettlement.setBalance_subject(dataList.get(k).getBalance_subject());
							dataSettlement.setCharged_weight(new BigDecimal(dataList.get(k).getPacking_charge_flag()));
							dataSettlement.setPackage_price(SstoreSum);
							dataSettlement.setTime(DateUtil.getMonth(DateUtil.getYesterDay()));
							if (insurance == 0) {
								// 0.请选择
								dataSettlement.setPackage_price(unit_price);
								dataSettlement.setInsurance_fee(new BigDecimal("0.00"));
								dataSettlement.setTotal_fee(unit_price);
							} else if (insurance == 1) {
								// 1无阶梯
								dataSettlement.setPackage_price(new BigDecimal(pp.getUnit_price()));
								PackagePriceLadder packagePriceLadder = new PackagePriceLadder();
								packagePriceLadder.setCon_id(contract.getId());
								Byte return_flag = Byte.parseByte("1");
								packagePriceLadder.setReturn_flag(return_flag);
								List<PackagePriceLadder> list = packagePriceLadderService
										.findByCBID(packagePriceLadder);
								BigDecimal charge_percent = new BigDecimal(list.get(0).getCharge_percent());
								dataSettlement.setInsurance_fee(
										order_amount.multiply(charge_percent.divide(new BigDecimal(100))));
								dataSettlement.setTotal_fee(
										dataSettlement.getPackage_price().add(dataSettlement.getInsurance_fee()));
							} else if (insurance == 2) {
								// 2总费用阶梯
								PackagePriceLadder packagePriceLadder = new PackagePriceLadder();
								packagePriceLadder.setCon_id(contract.getId());
								Byte return_flag = Byte.parseByte("1");
								packagePriceLadder.setReturn_flag(return_flag);
								List<PackagePriceLadder> list = packagePriceLadderService
										.findByCBID(packagePriceLadder);
								for (int l = 0; l < list.size(); l++) {
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("compare_1", list.get(l).getCompare_1());
									map.put("num_1", list.get(l).getNum_1());
									map.put("compare_2", list.get(l).getCompare_2());
									map.put("num_2", list.get(l).getNum_2());
									// 当前值在区间中
									if (CommonUtils.inRegionOrNot(map, order_amount)) {
										if (null != list.get(l).getCharge_percent()
												&& !list.get(l).getCharge_percent().equals("")) {
											dataSettlement.setInsurance_fee(order_amount
													.multiply(new BigDecimal(list.get(l).getCharge_percent())));
										} else {
											dataSettlement.setInsurance_fee(new BigDecimal(list.get(l).getCharge()));
										}
										dataSettlement.setTotal_fee(dataSettlement.getPackage_price()
												.add(dataSettlement.getInsurance_fee()));
									}
								}
							} else if (insurance == 3) {
								// 3超出部分阶梯
								PackagePriceLadder packagePriceLadder = new PackagePriceLadder();
								packagePriceLadder.setCon_id(contract.getId());
								Byte return_flag = Byte.parseByte("1");
								packagePriceLadder.setReturn_flag(return_flag);
								List<PackagePriceLadder> list = packagePriceLadderService
										.findByCBID(packagePriceLadder);
								for (int l = 0; l < list.size(); l++) {
									Integer compare_1 = list.get(l).getCompare_1();
									Integer compare_2 = list.get(l).getCompare_2();
									Double num_1 = list.get(l).getNum_1();
									Double num_2 = list.get(l).getNum_2();
									if (null != compare_1 && null != compare_2) {
										if (compare_1 == 0) {
											if (compare_2 == 0) {
												// ()
												BigDecimal a1 = new BigDecimal(list.get(l).getCharge());
												BigDecimal a2 = new BigDecimal(num_1);
												BigDecimal a3 = new BigDecimal(num_2);
												if (a1.compareTo(a2) == 1 && a1.compareTo(a3) == -1) {
													if (a1.compareTo(a3) > 0) {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement
																	.setInsurance_fee(((a1.subtract(a3)).subtract(a2))
																			.multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													} else {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement.setInsurance_fee(
																	(a1.subtract(a2)).multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													}
												}
											} else {
												// (]
												BigDecimal a1 = new BigDecimal(list.get(l).getCharge());
												BigDecimal a2 = new BigDecimal(num_1);
												BigDecimal a3 = new BigDecimal(num_2);
												if (a1.compareTo(a2) >= 0 && a1.compareTo(a3) == -1) {
													if (a1.compareTo(a3) > 0) {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement
																	.setInsurance_fee(((a1.subtract(a3)).subtract(a2))
																			.multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													} else {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement.setInsurance_fee(
																	(a1.subtract(a2)).multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													}
												}

											}
										} else {
											if (compare_2 == 0) {
												// [)
												BigDecimal a1 = new BigDecimal(list.get(l).getCharge());
												BigDecimal a2 = new BigDecimal(num_1);
												BigDecimal a3 = new BigDecimal(num_2);
												if (a1.compareTo(a2) >= 0 && a1.compareTo(a3) == -1) {
													if (a1.compareTo(a3) > 0) {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement
																	.setInsurance_fee(((a1.subtract(a3)).subtract(a2))
																			.multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													} else {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement.setInsurance_fee(
																	(a1.subtract(a2)).multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													}
												}

											} else {
												// []
												BigDecimal a1 = new BigDecimal(list.get(l).getCharge());
												BigDecimal a2 = new BigDecimal(num_1);
												BigDecimal a3 = new BigDecimal(num_2);
												if (a1.compareTo(a2) >= 0 && a1.compareTo(a3) >= 0) {
													if (a1.compareTo(a3) > 0) {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement
																	.setInsurance_fee(((a1.subtract(a3)).subtract(a2))
																			.multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													} else {
														if (null != list.get(l).getCharge_percent()
																&& !list.get(l).getCharge_percent().equals("")) {
															// 原始数据超过区间
															// ((原始数据-区间结束值)－区间开始值)*价格或区间
															dataSettlement.setInsurance_fee(
																	(a1.subtract(a2)).multiply(order_amount));
														} else {
															dataSettlement.setInsurance_fee(
																	new BigDecimal(list.get(l).getCharge()));
														}
													}
												}

											}
										}
										dataSettlement.setTotal_fee(dataSettlement.getPackage_price()
												.add(dataSettlement.getInsurance_fee()));
									}
								}
							}
							dataSettlement.setData_id(dataList.get(k).getId());
							dataSettlement.setDFlag(0);
							dataSettlement.setContract_id(contract.getId());
							dataSettlement.setS_weight(new BigDecimal(0));
							dataSettlement.setS_weight(new BigDecimal(0));
							dataSettlement.setBatch_number(param.getBatchNumber());
							warehouseExpressDataStoreSettlementEstimateService.save(dataSettlement);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(contract.getId());
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("tb_warehouse_express_data: " + id+" 添加结算明细异常！");
				bEL.setDefault1("打包费");
				bEL.setDefault2(param.getBatchNumber());
				bEL.setRemark("tb_warehouse_express_data: "+id);
				expressContractService.addBalanceErrorLog(bEL);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
