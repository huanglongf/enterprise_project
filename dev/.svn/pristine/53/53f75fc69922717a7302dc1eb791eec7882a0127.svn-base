package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.OperationfeeDataDailySettlementEstimate;
import com.bt.lmis.balance.model.OperationfeeDataEstimate;
import com.bt.lmis.balance.service.OperationfeeDataDailySettlementEstimateService;
import com.bt.lmis.balance.service.OperationfeeDataEstimateService;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.PerationSettlementFixed;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.PerationfeeDatas;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.PerationSettlementFixedService;
import com.bt.lmis.service.PerationfeeDataService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/** 
 * @ClassName: OperatingCostEstimate
 * @Description: TODO(操作费预估)
 * @author jindong.lin
 * @date 2017年5月9日 下午13:21:00
 * 
 */
@Service
public class OperatingCostEstimate extends Estimate {

	private static final Logger logger = Logger.getLogger(OperatingCostEstimate.class);
	
	// 快递合同服务类 ［调用错误日志插入服务］
	@Resource(name = "expressContractServiceImpl")
	ExpressContractService<T> expressContractService;
	// 店铺服务类
	@Resource(name = "storeServiceImpl")
	StoreService<Store> storeService;
	// 客户服务类
	@Resource(name = "clientServiceImpl")
	ClientService<Client> clientService;
	// 操作费结算规则表服务类
	@Resource(name = "operationSettlementRuleServiceImpl")
	OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService;
	// 操作费明细数据服务类
	@Resource(name = "operationfeeDataDailySettlementEstimateServiceImpl")
	OperationfeeDataDailySettlementEstimateService operationfeeDataDailySettlementEstimateService;
	// 操作费原始数据服务类
	@Resource(name = "perationfeeDataServiceImpl")
	PerationfeeDataService<PerationfeeData> perationfeeDataService;
	// 操作费明细预估服务类
	@Resource(name = "operationfeeDataEstimateServiceImpl")
	OperationfeeDataEstimateService operationfeeDataEstimateService;
	// 打包费服务类
	@Resource(name = "packageChargeServiceImpl")
	PackageChargeService<PackagePrice> packageChargeService;
	// 固定费用超过部分阶梯服务类
	@Resource(name = "perationSettlementFixedServiceImpl")
	PerationSettlementFixedService<PerationSettlementFixed> perationSettlementFixedService;
	//
	@Resource(name = "warehouseExpressDataServiceImpl")
	WarehouseExpressDataService<WarehouseExpressData> warehouseExpressDataService;
	
	
	
	
	
	@Override
	public EstimateResult estimate(EstimateParam param) {
		
		Object errorObject = null;
		
		EstimateResult result = new EstimateResult();

		Contract cb = param.getContract();
		
		try {
			logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()+ "操作费预估开始...");
			if (DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(),param.getFromDate()) && 
					DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(),param.getToDate())) {
				// 打包费信息
				PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cb.getId()));
				// 操作费开关是否开
				if (null != pp && pp.getOperation().equals("0")) {
					result.setFlag(false);
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(cb.getId());
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
					bEL.setDefault1("操作费");
					bEL.setDefault2(param.getBatchNumber());
					bEL.setRemark("原始数据无关:"+
							"批次号:[" + param.getBatchNumber() + "],操作费估异常!操作费开关未打开!");
					try {
						expressContractService.addBalanceErrorLog(bEL);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// 主体
					// 添加店铺集合
					String contract_owner = cb.getContractOwner();
					String contract_type = cb.getContractType();
					List<Store> storeList = new ArrayList<Store>();
					if (contract_type.equals("3")) {
						Store store = storeService.findByContractOwner(contract_owner);
						if (null != store) {
							storeList.add(store);
						} else {
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(cb.getId());
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID[" + cb.getId() + "],店铺不存在！");
							bEL.setError_type("3");
							expressContractService.addBalanceErrorLog(bEL);
							result.setFlag(false);
							return result;
						}
					} else if (contract_type.equals("4")) {
						Client client = clientService.findByContractOwner(contract_owner);
						if(null!=client){
							List<Store> sList = storeService.selectByClient(client.getId());
							if (null != sList && sList.size() != 0) {
								for (int j = 0; j < sList.size(); j++) {
									storeList.add(sList.get(j));
								}
							} else {
								expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
										cb.getId(),BaseConst.PRO_RESULT_FLAG_ERROR, 
										"合同ID[" + cb.getId()+ "]," + client.getClient_name() + "客户没有绑定店铺！",
										null, null, null, null, new Date(),"4"));
								result.setFlag(false);
								return result;
							}
						}
					}
					// 获取操作费规则
					List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(
							String.valueOf(cb.getId()));
					if (oSRList != null && oSRList.size() >= 1) {
						OperationSettlementRule oSR = oSRList.get(0);
						// 结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]
						if (null != oSR.getOsr_setttle_method()) {
							int osr_setttle_method = oSR.getOsr_setttle_method();
							switch (osr_setttle_method) {
							case 0:
								// 固定费用结算
								fixedCost(param, oSR, storeList,errorObject);
								break;
							case 1:
								// 按销售额百分比结算
								salesPercentage(param, oSR, storeList,errorObject);
								break;
							case 2:
								// 按实际发生费用结算
								actuallyHappen(param, oSR, storeList,errorObject);
								break;
							default:
								logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + ",批次号:[" + param.getBatchNumber() + "],操作费估异常!未知的结算方式!");
								BalanceErrorLog bEL = new BalanceErrorLog();
								bEL.setContract_id(cb.getId());
								bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
								bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
								bEL.setDefault1("操作费");
								bEL.setDefault2(param.getBatchNumber());
								bEL.setRemark("原始数据无关:"+
										"批次号:[" + param.getBatchNumber() + "],操作费估异常!未知的结算方式!" +
										"osr_setttle_method=" + osr_setttle_method);
								try {
									expressContractService.addBalanceErrorLog(bEL);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								break;
							}
						}
					} else {
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(cb.getId());
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
						bEL.setDefault1("操作费");
						bEL.setDefault2(param.getBatchNumber());
						bEL.setRemark("原始数据无关:"+
								"批次号:[" + param.getBatchNumber() + "],操作费估异常!结算规则未维护!");
						try {
							expressContractService.addBalanceErrorLog(bEL);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				
				
				// 主体
				// 添加店铺集合
				String contract_owner = cb.getContractOwner();
				String contract_type = cb.getContractType();
				List<Store> storeList = new ArrayList<Store>();
				if (contract_type.equals("3")) {
					Store store = storeService.findByContractOwner(contract_owner);
					if (null != store) {
						storeList.add(store);
					} else {
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(cb.getId());
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("合同ID[" + cb.getId() + "],店铺不存在！");
						bEL.setError_type("3");
						expressContractService.addBalanceErrorLog(bEL);
						result.setFlag(false);
						return result;
					}
				} else if (contract_type.equals("4")) {
					Client client = clientService.findByContractOwner(contract_owner);
					if(null!=client){
						List<Store> sList = storeService.selectByClient(client.getId());
						if (null != sList && sList.size() != 0) {
							for (int j = 0; j < sList.size(); j++) {
								storeList.add(sList.get(j));
							}
						} else {
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
									cb.getId(),BaseConst.PRO_RESULT_FLAG_ERROR, 
									"合同ID[" + cb.getId()+ "]," + client.getClient_name() + "客户没有绑定店铺！",
									null, null, null, null, new Date(),"4"));
							result.setFlag(false);
							return result;
						}
					}
				}
				// 获取操作费规则
				List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(
						String.valueOf(cb.getId()));

				if (oSRList != null && oSRList.size() >= 1) {
					//获取操作费规则
					OperationSettlementRule oSR = oSRList.get(0);
					
				
					BigDecimal inboundSKU = new BigDecimal(0.00);//操作费
					BigDecimal inboundSKUNum = new BigDecimal(0.00);//几种sku(商品条码)
					Integer osr_ibop_fee = oSR.getOsr_ibop_fee();
					//1.入库操作费
					if(null!=osr_ibop_fee && osr_ibop_fee==1){
						storeList= null;
						if(contract_type.equals("3")){
							Store store = storeService.findByContractOwner(contract_owner);
							if(CommonUtils.checkExistOrNot(store)){
								storeList= new ArrayList<Store>();
								storeList.add(store);
							}else{
								BalanceErrorLog bEL = new BalanceErrorLog();
								bEL.setContract_id(Integer.valueOf(cb.getId()));
								bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
								bEL.setPro_result_info("合同ID["+cb.getId()+"],店铺不存在！");
								bEL.setError_type("3");
								try {
									expressContractService.addBalanceErrorLog(bEL);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								result.setFlag(false);
							}
						}else if(contract_type.equals("4")){
							Client client = clientService.findByContractOwner(contract_owner);
							List<Store> temp = storeService.selectByClient(client.getId());
							if(CommonUtils.checkExistOrNot(temp)){
								storeList= temp;
							} else {
								try {
									expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, 
											null, Integer.valueOf(cb.getId()), 
											BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cb.getId()+"],"
											+client.getClient_name()+"客户没有绑定店铺！", 
											null, null, null, null, new Date(),"4"));
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	
								result.setFlag(false);
							}
						}
						//循环所有店铺 店铺为最小单位
						for (int j= 0; j< storeList.size(); j++) {
							// 查询该店铺下所有对应时间范围内的入库原始数据
	//						List<PerationfeeData> list = perationfeeDataService
	//						.findByEntityGroupBySKUYM("'VMI移库入库','代销入库','结算经销入库'"
	//						,storeList.get(j).getStore_name(), String.valueOf(DateUtil.getYesterDay()
	//						.get(Calendar.YEAR)), String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1));
							
							int count = perationfeeDataService.countByEntityGroupBySKUSE(
									"'VMI移库入库','代销入库','结算经销入库'",storeList.get(j).getStore_name(), 
									param.getFromDate(),param.getToDate());
							int firstResult= 0;
							BigDecimal osr_ibop_skuprice = oSR.getOsr_ibop_skuprice();
							BigDecimal osr_ibop_itemprice = oSR.getOsr_ibop_itemprice();
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKUSE(
										"'VMI移库入库','代销入库','结算经销入库'",storeList.get(j).getStore_name(), 
										param.getFromDate(),param.getToDate(),firstResult,BaseConst.excel_pageSize);
								for (int k = 0; k < list.size(); k++) {
									PerationfeeData map = list.get(k);
									errorObject = map;
									BigDecimal sku_count = new BigDecimal(map.getSku_count());
									BigDecimal in_num = new BigDecimal(null != map.getIn_num() ? 
											map.getIn_num().toString() : "0");
									inboundSKU = inboundSKU.add((osr_ibop_itemprice.multiply(in_num))
											.add(osr_ibop_skuprice.multiply(sku_count)));
									inboundSKUNum = inboundSKUNum.add(sku_count);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
						}
					}
					//没有生成数据
					BigDecimal qty1 = new BigDecimal(0.00);
					BigDecimal qty2 = new BigDecimal(0.00);
					BigDecimal qty3 = new BigDecimal(0.00);
					BigDecimal qty4 = new BigDecimal(0.00);
					BigDecimal qty5 = new BigDecimal(0.00);
					BigDecimal qty6 = new BigDecimal(0.00);
					BigDecimal fee1 = new BigDecimal(0.00);
					BigDecimal fee2 = new BigDecimal(0.00);
					BigDecimal fee3 = new BigDecimal(0.00);
					BigDecimal fee4 = new BigDecimal(0.00);
					BigDecimal fee5 = new BigDecimal(0.00);
					BigDecimal fee6 = new BigDecimal(0.00);
					//结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]
					if(CommonUtils.checkExistOrNot(oSR.getOsr_setttle_method())){
						int	osr_setttle_method= oSR.getOsr_setttle_method();
						if(osr_setttle_method == 0) {
							
						} else if (osr_setttle_method==1) {
							
						} else if (osr_setttle_method==2) {
							//按实际发生费用结算
							//2.B2C出库操作费
							Integer osr_btcobop_fee= oSR.getOsr_btcobop_fee();
							if (null!=osr_btcobop_fee && osr_btcobop_fee==2) {
								List<PerationfeeDatas> list2= null;
								storeList= null;
								if(contract_type.equals("3")){
									Store store= storeService.findByContractOwner(contract_owner);
									if(CommonUtils.checkExistOrNot(store)){
										storeList= new ArrayList<Store>();
										storeList.add(store);
									} else {
										BalanceErrorLog bEL = new BalanceErrorLog();
										bEL.setContract_id(Integer.valueOf(cb.getId()));
										bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
										bEL.setPro_result_info("合同ID["+cb.getId()+"],店铺不存在！");
										bEL.setError_type("3");
										try {
											expressContractService.addBalanceErrorLog(bEL);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										result.setFlag(false);
									}
								} else if (contract_type.equals("4")){
									Client client = clientService.findByContractOwner(contract_owner);
									List<Store> temp = storeService.selectByClient(client.getId());
									if(CommonUtils.checkExistOrNot(temp)) {
										storeList= temp;
									} else {
										try {
											expressContractService.addBalanceErrorLog(
													new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()),
															BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["
													+ cb.getId()+ "],"+client.getClient_name()
													+"客户没有绑定店铺！", null, null, null, null, new Date(),"4"));
										} catch (NumberFormatException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
	
										result.setFlag(false);
									}
								}
								BigDecimal B2COutboundNum = new BigDecimal(0.00);
								BigDecimal B2COutbound = new BigDecimal(0.00);
								BigDecimal B2BOutbound = new BigDecimal(0.00);
								BigDecimal B2BOutboundNum = new BigDecimal(0.00);
								for (int j= 0; j< storeList.size(); j++) {
									PerationfeeData entity= new PerationfeeData();
									entity.setJob_type("'销售出库','退换货出库'");
									entity.setStore_name(storeList.get(j).getStore_name());
									entity.setStart_time(param.getFromDate());
									entity.setEnd_time(param.getToDate());
									
									int count = perationfeeDataService.countByEntityGroupByOutNumSE(entity);
									int firstResult= 0;
									for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
										
										entity.setFirstResult(firstResult);
										entity.setMaxResult(BaseConst.excel_pageSize);
										//查询该店铺下所有入库原始数据
										
										list2= perationfeeDataService.findByEntityGroupByOutNumSE(entity);//自定义时间范围过滤
										
										//是否转B2B
										Integer osr_tobtb = oSR.getOsr_tobtb();
										List<PerationfeeDatas> list = new ArrayList<>();//非转b2b原始数据集合
										List<PerationfeeDatas> list3 = new ArrayList<>();//转b2b原始数据集合
										for (int l = 0; l < list2.size(); l++) {
											BigDecimal out_num = list2.get(l).getOut_num();
											if (osr_tobtb == 1) {
												// 转B2B
												BigDecimal osr_btcobopbill_tobtb_js = 
														null != oSR.getOsr_btcobopbill_tobtb_js() 
														&& !oSR.getOsr_btcobopbill_tobtb_js().equals("") ? 
																new BigDecimal(oSR.getOsr_btcobopbill_tobtb_js()) 
																: new BigDecimal(0.00);
												if (out_num.compareTo(osr_btcobopbill_tobtb_js) >= 0) {
													list3.add(list2.get(l));
												}else{
													list.add(list2.get(l));
												}
											}else{
												list.add(list2.get(l));
											}
										}
										//是否转B2B
										if(list3.size()!=0){
											// 3.B2B出库操作费
											Integer osr_btbobop_fee = oSR.getOsr_btbobop_fee();
											if (osr_btbobop_fee == 1) {
												// 按件出库
												for (int o = 0; o < list3.size(); o++) {
													errorObject = list3.get(o);
													BigDecimal sku_number = list3.get(o).getOut_num()
															.multiply(new BigDecimal
																	(String.valueOf(list3.get(o).getCtnum())));
													BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
													B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
													B2BOutboundNum = B2BOutboundNum.add(sku_number);
												}
												errorObject = null;
											}
										}
										
										//维护的区间值
										List<Map<String, Object>> tbbtList = operationSettlementRuleService
												.queryTBBTList(String.valueOf(oSR.getOsr_btcobopbill_tableid()));
										for (int k = 0; k < tbbtList.size(); k++) {
											Map<String,Object> map = tbbtList.get(k);
											String setions = map.get("bbt_interval").toString();
											Map<String,Integer> sectionMap = IntervalValidationUtil.strToMap(setions);
											//区间开始值
											Integer startSymbol = sectionMap.get("startSymbol");
											Integer startNum = sectionMap.get("startNum");
											//区间结束值
											Integer endSymbol = sectionMap.get("endSymbol");
											Integer endNum = sectionMap.get("endNum");
											//单数单价
											String d_prices = map.get("bbt_bill_price").toString();
											//件数单价
											String j_prices = map.get("bbt_piece_price").toString();
											for (int q = 0; q < list.size(); q++) {
												BigDecimal out_num = list.get(q).getOut_num();
												
												//0小于 1等于
												if(startSymbol==0 && endSymbol==0){
													//()
													if(out_num.compareTo(new BigDecimal(startNum.toString()))==1  
															&& out_num.compareTo(new BigDecimal(endNum.toString()))<0){
														B2COutboundNum=B2COutboundNum
																.add(new BigDecimal(list.get(q).getCtnum()));
														BigDecimal a = new BigDecimal(d_prices);
														BigDecimal b = new BigDecimal(j_prices);
														BigDecimal c = list.get(q).getOut_num();
														BigDecimal d = new BigDecimal(list.get(q).getCtnum());
														BigDecimal e = (((c.subtract(new BigDecimal(startNum)))
																.multiply(b)).add(a)).multiply(d);
														B2COutbound=B2COutbound.add(e);
													}
												}else if(startSymbol==1 && endSymbol==1){
													//[]
													if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 
															&& out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
														B2COutboundNum=B2COutboundNum
																.add(new BigDecimal(list.get(q).getCtnum()));
														BigDecimal a = new BigDecimal(d_prices);
														BigDecimal b = new BigDecimal(j_prices);
														BigDecimal c = list.get(q).getOut_num();
														BigDecimal d = new BigDecimal(list.get(q).getCtnum());
														BigDecimal e = (((c.subtract(new BigDecimal(startNum)))
																.multiply(b)).add(a)).multiply(d);
														B2COutbound=B2COutbound.add(e);
													}
												}else if(startSymbol==0 && endSymbol==1){
													//(]
													if(out_num.compareTo(new BigDecimal(startNum.toString()))==1 && 
															out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
														B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
														BigDecimal a = new BigDecimal(d_prices);
														BigDecimal b = new BigDecimal(j_prices);
														BigDecimal c = list.get(q).getOut_num();
														BigDecimal d = new BigDecimal(list.get(q).getCtnum());
														BigDecimal e = (((c.subtract(new BigDecimal(startNum)))
																.multiply(b)).add(a)).multiply(d);
														B2COutbound=B2COutbound.add(e);
													}
												}else if(startSymbol==1 && endSymbol==0){
													//[)
													if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 && 
															out_num.compareTo(new BigDecimal(endNum.toString()))<0){
														B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
														BigDecimal a = new BigDecimal(d_prices);
														BigDecimal b = new BigDecimal(j_prices);
														BigDecimal c = list.get(q).getOut_num();
														BigDecimal d = new BigDecimal(list.get(q).getCtnum());
														BigDecimal e = (((c.subtract(new BigDecimal(startNum)))
																.multiply(b)).add(a)).multiply(d);
														B2COutbound=B2COutbound.add(e);
													}
												}
											}
										}
										
										firstResult= pageNo*BaseConst.excel_pageSize;
									}
									
								}
								if(null!=oSR.getOsr_btcobopbill_discount_type() && 
										!oSR.getOsr_btcobopbill_discount_type().equals("")){
									if(oSR.getOsr_btcobopbill_discount_type().equals("1")){
										//阶梯折扣按件结算
										List<Map<String, Object>> taList = operationSettlementRuleService
												.queryBTBDList(oSR.getOsr_btcobopbill_discount_tableid());
										Map<String, Object> map = getPrice(taList, B2COutboundNum, "1",
												"btcbd_interval", "btcbd_price",B2COutbound,null);
										if (Integer.valueOf(map.get("status").toString())==360) {
											B2COutbound=new BigDecimal(map.get("msg").toString());
										}
									}
								}
								BigDecimal allZK = new BigDecimal(StringUtils.isNotBlank(oSR.getAllzk()) ? oSR.getAllzk() : "1");
								OperationfeeDataDailySettlementEstimate insertMain = 
										new OperationfeeDataDailySettlementEstimate();
								insertMain.setCreateTime(new Date());
								insertMain.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
								insertMain.setContractId(Integer.valueOf(cb.getId()));
								insertMain.setBatchNumber(param.getBatchNumber());
	
								insertMain.setBtcQty(B2COutboundNum);
								insertMain.setBtcQtyunit("");
								insertMain.setBtcFee(B2COutbound.multiply(allZK));
	
								insertMain.setBtbQty(B2BOutboundNum);
								insertMain.setBtbQtyunit("");
								insertMain.setBtbFee(B2BOutbound.multiply(allZK));
	
								insertMain.setReturnQty(new BigDecimal(0.00));
								insertMain.setReturnQtyunit("");
								insertMain.setReturnFee(new BigDecimal(0.00).multiply(allZK));
	
								insertMain.setIbQty(inboundSKUNum);
								insertMain.setIbQtyunit("");
								insertMain.setIbFee(inboundSKU.multiply(allZK));
								
								
								if (inboundSKU.compareTo(new BigDecimal(0.00)) != 0
										||B2COutbound.compareTo(new BigDecimal(0.00)) != 0
										|| B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
										) {
									operationfeeDataDailySettlementEstimateService.save(insertMain);
								}
							}
						}else{
							logger.error("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContractName()
							+"操作费预估异常!未知的结算方式!");
						}
					}
	
					//退换货入库费按件数计算 是否有阶梯
					Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
					if(null==osr_btcrtib_piece){
						qty3 = new BigDecimal(0.00);
						fee3 = new BigDecimal(0.00);
					}else if (osr_btcrtib_piece==1) {
						
					}else{
						storeList= new ArrayList<Store>();
						if(contract_type.equals("3")){
							Store store = storeService.findByContractOwner(contract_owner);
							if(null!=store){
								storeList.add(store);
							}else{
								BalanceErrorLog bEL = new BalanceErrorLog();
								bEL.setContract_id(Integer.valueOf(cb.getId()));
								bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
								bEL.setPro_result_info("合同ID["+ cb.getId()+ "],店铺不存在！");
								bEL.setError_type("3");
								try {
									expressContractService.addBalanceErrorLog(bEL);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								result.setFlag(false);
							}
							
						} else if (contract_type.equals("4")) {
							Client client = clientService.findByContractOwner(contract_owner);
							List<Store> sList = storeService.selectByClient(client.getId());
							if(null!=sList && sList.size()!=0){
								for (int k = 0; k < sList.size(); k++) {
									storeList.add(sList.get(k));
									
								}
								
							}else{
								try {
									expressContractService.addBalanceErrorLog(new BalanceErrorLog(0,
											null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, 
											"合同ID["+cb.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", 
											null, null, null, null, new Date(),"4"));
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	
								result.setFlag(false);
							}
							
						}
						BigDecimal inb = new BigDecimal(0.00);
						BigDecimal oub = new BigDecimal(0.00);
						for (int k = 0; k < storeList.size(); k++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货入库','消费者退换货入库'");
							entity.setStore_name(storeList.get(k).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);

								//查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								for (int l = 0; l < list.size(); l++) {
									errorObject = list.get(l);
									inb = inb.add(list.get(l).getIn_num());
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							PerationfeeData entitys = new PerationfeeData();
							entitys.setJob_type("'销售出库','退换货出库'");
							entitys.setStore_name(storeList.get(k).getStore_name());
							entitys.setStart_time(param.getFromDate());
							entitys.setEnd_time(param.getToDate());
							
							count = perationfeeDataService.countByEntitySE(entitys);
							firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entitys.setFirstResult(firstResult);
								entitys.setMaxResult(BaseConst.excel_pageSize);

								//查询该店铺下所有入库原始数据
								List<PerationfeeData> lists = perationfeeDataService.findByEntitySE(entitys);
								for (int m = 0; m < lists.size(); m++) {
									errorObject = lists.get(m);
									oub = oub.add(lists.get(m).getOut_num());
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							if (oub.compareTo(new BigDecimal(0.00))==0) {
								qty3=qty3.add(new BigDecimal(0.00));
								fee3=fee3.add(new BigDecimal(0.00));
							}else{
								BigDecimal div = inb.divide(oub,2,BigDecimal.ROUND_HALF_EVEN)
										.multiply(new BigDecimal(100));
								String osr_btcrti_tableid = oSR.getOsr_btcrti_tableid();
								if(null!=osr_btcrti_tableid && !osr_btcrti_tableid.equals("")){
									List<Map<String, Object>> yjt_list = operationSettlementRuleService
											.queryTBTList(osr_btcrti_tableid);	
									Map<String, Object> map = getPrice(yjt_list, div, "2", "btcrti_interval",
											"btcrt_price",inb,null);
									if (Integer.valueOf(map.get("status").toString())==360) {
										qty3=qty3.add(inb);
										fee3=fee3.add(new BigDecimal(map.get("msg").toString()));
									}
								}
							}
						}
					}
					//汇总累加(预估)
					OperationfeeDataDailySettlementEstimate pfd = new OperationfeeDataDailySettlementEstimate();
					pfd.setBatchNumber(param.getBatchNumber());
					pfd.setContractId(Integer.valueOf(cb.getId()));
					


					int count = operationfeeDataDailySettlementEstimateService
							.countByEntity(pfd);
					int firstResult= 0;
					for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {

						pfd.setFirstResult(firstResult);
						pfd.setMaxResult(BaseConst.excel_pageSize);

						//根据批次号与合同号查找明细记录
						List<OperationfeeDataDailySettlementEstimate> pfdList = operationfeeDataDailySettlementEstimateService
								.findByEntity(pfd);
						for (int j = 0; j < pfdList.size(); j++) {
							OperationfeeDataDailySettlementEstimate pfds = pfdList.get(j);
							
							qty1=qty1.add(null!=pfds.getBtcQty()?pfds.getBtcQty():new BigDecimal(0.00));
							fee1=fee1.add(null!=pfds.getBtcFee()?pfds.getBtcFee():new BigDecimal(0.00));
		
							qty2=qty2.add(null!=pfds.getBtbQty()?pfds.getBtbQty():new BigDecimal(0.00));
							fee2=fee2.add(null!=pfds.getBtbFee()?pfds.getBtbFee():new BigDecimal(0.00));
		
							qty3=qty3.add(null!=pfds.getReturnQty()?pfds.getReturnQty():new BigDecimal(0.00));
							fee3=fee3.add(null!=pfds.getReturnFee()?pfds.getReturnFee():new BigDecimal(0.00));
							
							qty4=qty4.add(null!=pfds.getIbQty()?pfds.getIbQty():new BigDecimal(0.00));
							fee4=fee4.add(null!=pfds.getIbFee()?pfds.getIbFee():new BigDecimal(0.00));
							
							qty5=qty5.add(null!=pfds.getGdQty()?pfds.getGdQty():new BigDecimal(0.00));
							fee5=fee5.add(null!=pfds.getGdFee()?pfds.getGdFee():new BigDecimal(0.00));
							
							qty6=qty6.add(null!=pfds.getXseQty()?pfds.getXseQty():new BigDecimal(0.00));
							fee6=fee6.add(null!=pfds.getXseFee()?pfds.getXseFee():new BigDecimal(0.00));
						}
						
						firstResult= pageNo*BaseConst.excel_pageSize;
					}
					OperationfeeDataEstimate entity = new OperationfeeDataEstimate();
					entity.setCreateTime(new Date());
					entity.setCreateUser("1");
					entity.setContractId(Integer.valueOf(cb.getId()));
					entity.setBatchNumber(param.getBatchNumber());
					entity.setBtcQty(qty1);
					entity.setBtcFee(fee1);
					entity.setBtbQty(qty2);
					entity.setBtbFee(fee2);
					entity.setReturnQty(qty3);
					entity.setReturnFee(fee3);
					entity.setIbQty(qty4.add(inboundSKUNum));
					entity.setIbFee(fee4.add(inboundSKU));
					entity.setXseQty(qty6);
					entity.setXseFee(fee6);
					entity.setGdQty(qty5);
					entity.setGdFee(fee5);
					if(fee1.compareTo(new BigDecimal(0.00))!=0 ||
							fee2.compareTo(new BigDecimal(0.00))!=0 ||
							fee3.compareTo(new BigDecimal(0.00))!=0 ||
							fee4.compareTo(new BigDecimal(0.00))!=0 ||
							fee5.compareTo(new BigDecimal(0.00))!=0 ||
							fee6.compareTo(new BigDecimal(0.00))!=0) {
						operationfeeDataEstimateService.save(entity);
	
						result.setFlag(true);
					}
					logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContractName()+"操作费预估完成!");

				} else {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(cb.getId());
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
					bEL.setDefault1("操作费");
					bEL.setDefault2(param.getBatchNumber());
					bEL.setRemark("原始数据无关:"+
							"批次号:[" + param.getBatchNumber() + "],操作费估异常!结算规则未维护!");
					try {
						expressContractService.addBalanceErrorLog(bEL);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(cb.getId());
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
				bEL.setDefault1("操作费");
				bEL.setDefault2(param.getBatchNumber());
				bEL.setRemark("原始数据无关:"+
						"批次号:[" + param.getBatchNumber() + "],操作费估异常!所选时间不在合同起止时间范围内!" );
				try {
					expressContractService.addBalanceErrorLog(bEL);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + "操作费预估异常!");
			BalanceErrorLog bEL = new BalanceErrorLog();
			bEL.setContract_id(cb.getId());
			bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
			bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费预估异常！");
			bEL.setDefault1("操作费");
			bEL.setDefault2(param.getBatchNumber());
			if (errorObject != null) {
				if (errorObject instanceof PerationfeeData) {
					bEL.setRemark("tb_operationfee_data表数据id:" + ((PerationfeeData)errorObject).getId().toString());
				} else if (errorObject instanceof WarehouseExpressData) {
					bEL.setRemark("tb_warehouse_express_data表数据id:" + ((WarehouseExpressData)errorObject).getId().toString());
				}
			} else {
				bEL.setRemark("原始数据无关:"+
						e.getMessage().substring(0,e.getMessage().length()>200?200:e.getMessage().length()));
			}
			
			try {
				expressContractService.addBalanceErrorLog(bEL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result.setFlag(false);
			return result;
		}
		return result;
	}



	/** 
	* @Title: getPrice 
	* @Description: TODO(判断如参要取那个阶梯价格) 
	* @param @param list	阶梯表格
	* @param @param inListString 	需要比较的参数 
	* @param @param status	1.超过部分阶梯  2.总占用部分阶梯
	* @param @param section
	* @param @param price
	* @param @param pol
	* @param @param zks
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public Map<String, Object> getPrice(List<Map<String, Object>> list, BigDecimal inListString, String status,String section, String price, BigDecimal pol, String zks) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status", 500);
		returnMap.put("msg", inListString + "输入参数不在区间内!");
		BigDecimal sum = new BigDecimal(0.00);
		BigDecimal inListStringLS = inListString;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String sections = map.get(section).toString();
			String prices = map.get(price).toString();
			// B2C出库按件收费 总件数阶梯折扣
			Map<String, Object> sectionMap = IntervalValidationUtil.strToMapS(sections);
			if (status.equals("1")) {
				if (Integer.valueOf(sectionMap.get("type").toString()) == 1) {
					// 区间
					Integer startSymbol = Integer.valueOf(sectionMap.get("type").toString());
					BigDecimal startNum = new BigDecimal(sectionMap.get("startNum").toString());
					Integer endNum = Integer.valueOf(sectionMap.get("endNum").toString());
					if (startSymbol == 0) {
						// startNum大于 (
						if (inListString.compareTo(startNum) == 1) {
							// (]
							if (inListStringLS.compareTo(new BigDecimal(endNum)) >= 0) {
								sum = sum.add((new BigDecimal(endNum).subtract(startNum)).multiply(new BigDecimal(prices)));
							} else {
								sum = sum.add((inListStringLS.subtract(startNum)).multiply(new BigDecimal(prices)));
							}
						}
					} else {
						// startNum大于等于
						if (inListString.compareTo(startNum) >= 0) {
							// []
							if (inListStringLS.compareTo(new BigDecimal(endNum)) >= 0) {
								sum = sum.add((new BigDecimal(endNum).subtract(startNum)).multiply(new BigDecimal(prices)));
							} else {
								sum = sum.add((inListStringLS.subtract(startNum)).multiply(new BigDecimal(prices)));
							}
						}
					}
				}
			} else if (status.equals("2")) {
				if (Integer.valueOf(sectionMap.get("type").toString()) == 1) {
					// 区间
					Integer startSymbol = Integer.valueOf(sectionMap.get("startSymbol").toString());
					BigDecimal startNum = new BigDecimal(sectionMap.get("startNum").toString());
					Integer endSymbol = Integer.valueOf(sectionMap.get("endSymbol").toString());
					BigDecimal endNum = new BigDecimal(sectionMap.get("endNum").toString());
					if (startSymbol == 0) {
						// startNum大于 (
						if (inListString.compareTo(startNum) == 1) {
							if (endSymbol == 1) {
								// (]
								if (inListString.compareTo(endNum) <= 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							} else {
								// ()
								if (inListString.compareTo(endNum) < 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							}
						}
					} else {
						// startNum大于等于
						if (inListString.compareTo(startNum) >= 0) {
							if (endSymbol == 1) {
								// []
								if (inListString.compareTo(endNum) >= 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							} else {
								// [)
								if (inListString.compareTo(endNum) < 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		

		returnMap.put("status", 360);
		returnMap.put("msg", sum);
		return returnMap;
	}
	
	
	
	

	/**
	 * 固定费用结算
	 * @param param
	 * @param oSR
	 * @param storeList
	 * @param errorObject 
	 */
	public void fixedCost(EstimateParam param,OperationSettlementRule oSR, List<Store> storeList
			, Object errorObject) {
		if (oSR.getOsr_fixed_type() == 1) {
			// 无阶梯
			Contract cb = param.getContract();
			try {
				OperationfeeDataDailySettlementEstimate PFD = new OperationfeeDataDailySettlementEstimate();
				PFD.setContractId(cb.getId());
				PFD.setBatchNumber(param.getBatchNumber());
				PFD.setCreateTime(new Date());
				PFD.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
				PFD.setGdQty(new BigDecimal(0.00));
				PFD.setGdQtyunit("-");
				PFD.setGdFee(new BigDecimal(oSR.getOsr_fixed_price()).multiply(new BigDecimal(oSR.getAllzk())));
				PFD.setGdRemark("元");
				if (PFD.getGdFee().compareTo(new BigDecimal(0.00)) != 0) {
					operationfeeDataDailySettlementEstimateService.save(PFD);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (oSR.getOsr_fixed_type() == 2) {
			// 超过部分阶梯
			List<PerationfeeData> list = new ArrayList<PerationfeeData>();
			BigDecimal a1 = new BigDecimal(0.00);
			for (int i = 0; i < storeList.size(); i++) {
				PerationfeeData entity = new PerationfeeData();
				entity.setStore_name(storeList.get(i).getStore_name());
				entity.setStart_time(param.getFromDate());
				entity.setEnd_time(param.getToDate());
				
				int count = perationfeeDataService.countByEntitySE(entity);
				int a= 0;
				for(int j= 1; j< forCount(count, BaseConst.excel_pageSize)+ 1; j++) {
					
					entity.setFirstResult(a);
					entity.setMaxResult(BaseConst.excel_pageSize);

					list = perationfeeDataService.findByEntitySE(entity);
					list = list == null ? new ArrayList<PerationfeeData>() : list;
					
					for (int k = 0; k < list.size(); k++) {
						errorObject = list.get(k);
						if (list.get(k).getIn_num().compareTo(new BigDecimal(0)) != 0) {
							a1 = a1.add(list.get(k).getIn_num());
						} else {
							a1 = a1.add(list.get(k).getOut_num());
						}
					}
					
					a= j*BaseConst.excel_pageSize;
				}
				
				errorObject = null;
			}
			// 固定费用超过部分阶梯－表格ID
			String osr_fixed_exceed_tableid = oSR.getOsr_fixed_exceed_tableid();
			PerationSettlementFixed entity = new PerationSettlementFixed();
			entity.setTosf_tab_id(osr_fixed_exceed_tableid);
			List<Map<String, Object>> entityList = perationSettlementFixedService.findByEntity(entity);
			Map<String, Object> map = getPrice(entityList, a1, "1", "tosf_section", "tosf_price", null, oSR.getAllzk());
			if (Integer.valueOf(map.get("status").toString()) == 360) {
				try {
					// 插入明细记录
					OperationfeeDataDailySettlementEstimate insertData = new OperationfeeDataDailySettlementEstimate();
					insertData.setContractId(oSR.getContract_id());
					insertData.setBatchNumber(param.getBatchNumber());
					insertData.setCreateTime(new Date());
					insertData.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setUpdateTime(new Date());
					insertData.setUpdateUser(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setGdQty(a1);
					insertData.setGdQtyunit("件");
					insertData.setGdFee(new BigDecimal(map.get("msg").toString()).add(new BigDecimal(null!=oSR.getOsr_fixed_price()&&!oSR.getOsr_fixed_price().equals("")?oSR.getOsr_fixed_price():"0.00")));
					insertData.setGdRemark("元");
//					for (int i = 0; i < list.size(); i++) {
//						PerationfeeData d = list.get(i);
//						d.setSettle_flag(1);
//						// 修改原始数据状态
//						perationfeeDataService.update(d);
//					}
//					if(list.size()!=0){
//						PerationfeeData data = new PerationfeeData();
//						data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
//						data.setStore_name(list.get(0).getStore_name());
//						data.setSettle_flag(0);
//						perationfeeDataService.update_settleflag(data);
//					}
					if (insertData.getGdFee().compareTo(new BigDecimal(0.00)) != 0) {
						operationfeeDataDailySettlementEstimateService.save(insertData);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, oSR.getContract_id(),
								BaseConst.PRO_RESULT_FLAG_ERROR,"合同ID[" + oSR.getContract_id() + "],插入数据异常！", 
								null, null, null, null, new Date()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	



	/**
	 * @param errorPerationfeeData  
	* @Title: salesPercentage 
	* @Description: TODO(按销售额百分比结算) 
	* @param @param param
	* @param @param oSR
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void salesPercentage(EstimateParam param, OperationSettlementRule oSR, List<Store> storeList
			, Object errorObject) {
		//判断是否为结算日
		Contract cb = param.getContract();
		
		try {
			List<WarehouseExpressData> list = new ArrayList<>();
			BigDecimal sum = new BigDecimal(0.00);
			for (int i = 0; i < storeList.size(); i++) {
				WarehouseExpressData data = new WarehouseExpressData();
				//该店铺下的所有原始数据	dataList
				data.setTstart_time(param.getFromDate());
				data.setTend_time(param.getToDate());
				data.setStore_name(storeList.get(i).getStore_name());
				
				int count = warehouseExpressDataService.countByEntitySE(data);
				int firstResult= 0;
				for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
					
					data.setFirstResult(firstResult);
					data.setMaxResult(BaseConst.excel_pageSize);

					list=warehouseExpressDataService.queryAllSE(data);

					list = list == null ? new ArrayList<WarehouseExpressData>() : list;
					for (int j = 0; j < list.size(); j++) {
						errorObject = list.get(j);
						BigDecimal a = list.get(j).getOrder_amount();
						BigDecimal b = new BigDecimal(0.00);
						BigDecimal c = new BigDecimal(0.00);
						if (null != oSR.getOsr_sales_percent() && !oSR.getOsr_sales_percent().equals("")) {
							b = new BigDecimal(oSR.getOsr_sales_percent());
						}
						if (null != oSR.getOsr_tax_point() && !oSR.getOsr_tax_point().equals("")) {
							c = new BigDecimal(oSR.getOsr_tax_point());
						}
						sum = sum.add(a.multiply(b).multiply(c));
					}
					errorObject = null;
					
					firstResult= pageNo*BaseConst.excel_pageSize;
				}
			}

			if (sum.compareTo(new BigDecimal(0.00)) != 0) {
				OperationfeeDataDailySettlementEstimate PFD = new OperationfeeDataDailySettlementEstimate();
				PFD.setCreateTime(new Date());
				PFD.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
				PFD.setUpdateTime(new Date());
				PFD.setUpdateUser(BaseConst.SYSTEM_JOB_CREATE);
				PFD.setContractId(cb.getId());
				PFD.setBatchNumber(param.getBatchNumber());
				PFD.setXseQty(new BigDecimal(1.00));
				PFD.setXseQtyunit("单");
				PFD.setXseFee(sum.multiply(new BigDecimal(oSR.getAllzk())));
				PFD.setXseRemark("");
				if (PFD.getXseFee().compareTo(new BigDecimal(0.00)) != 0) {
					operationfeeDataDailySettlementEstimateService.save(PFD);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			BalanceErrorLog bEL = new BalanceErrorLog();
			bEL.setContract_id(cb.getId());
			bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
			bEL.setPro_result_info("合同ID[" + cb.getId() + "],预估异常！");
			bEL.setDefault1("操作费");
			bEL.setDefault2(param.getBatchNumber());
			if (errorObject != null) {
				if (errorObject instanceof WarehouseExpressData) {
					bEL.setRemark("tb_warehouse_express_data表数据id:" 
							+ ((WarehouseExpressData)errorObject).getId().toString());
				}
			} else {
				bEL.setRemark("原始数据无关:"+
						e.getMessage().substring(0,e.getMessage().length()>200?200:e.getMessage().length()));
			}
			
			try {
				expressContractService.addBalanceErrorLog(bEL);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @param errorObject  
	* @Title: actuallyHappen 
	* @Description: TODO(按实际发生费用结算) 
	* @param @param param
	* @param @param cb
	* @param @param oSR
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void actuallyHappen(EstimateParam param, OperationSettlementRule oSR, List<Store> storeList
			, Object errorObject) {
		Contract cb = param.getContract();
			try {
				// 入库按Sku
				BigDecimal inboundSKU = new BigDecimal(0.00);
				BigDecimal inboundSKUNum = new BigDecimal(0.00);
				BigDecimal B2BOutbound = new BigDecimal(0.00);
				BigDecimal B2BOutboundNum = new BigDecimal(0.00);
				BigDecimal B2CInbound = new BigDecimal(0.00);
				BigDecimal B2CInboundNum = new BigDecimal(0.00);
				BigDecimal B2COutbound = new BigDecimal(0.00);
				BigDecimal B2COutboundNum = new BigDecimal(0.00);
				// 1.入库操作费
				Integer osr_ibop_fee = oSR.getOsr_ibop_fee();
				if (osr_ibop_fee == 1) {
					// 按SKU类型收取
	//				for (int i = 0; i < storeList.size(); i++) {
	//					PerationfeeData entity = new PerationfeeData();
	//					entity.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
	//					entity.setStore_name(storeList.get(i).getStore_name());
	//					entity.setSettle_flag(0);
	//					// 查询该店铺下所有入库原始数据
	//					List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKU(entity);
	//					BigDecimal sum2 = new BigDecimal(0);
	//					BigDecimal sum2Num = new BigDecimal(0);
	//					BigDecimal osr_ibop_skuprice = oSR.getOsr_ibop_skuprice();
	//					BigDecimal osr_ibop_itemprice = oSR.getOsr_ibop_itemprice();
	//					for (int j = 0; j < list.size(); j++) {
	//						PerationfeeData map = list.get(j);
	//						BigDecimal sku_number = new BigDecimal(list.size());
	//						Integer in_num = Integer.valueOf(null != map.getIn_num() ? map.getIn_num().toString() : "0");
	//						sum2 = sum2.add(osr_ibop_itemprice.multiply(new BigDecimal(in_num)));
	//						sum2Num = sum2Num.add(sku_number);
	//						map.setSettle_flag(1);
	//						perationfeeDataService.update(map);
	//					}
	//					inboundSKU = sum2.add(osr_ibop_skuprice.multiply(new BigDecimal(list.size())));
	//					inboundSKUNum = inboundSKUNum.add(new BigDecimal(list.size()));
	//				}
				} else if (osr_ibop_fee == 2) {
					// 按入库数量收取
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(param.getFromDate());
						entity.setEnd_time(param.getToDate());
						
						BigDecimal sum2 = new BigDecimal(0);
						BigDecimal sum2Num = new BigDecimal(0);
						
						int count = perationfeeDataService.countByEntitySE(entity);
						int firstResult= 0;
						for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
							
							entity.setFirstResult(firstResult);
							entity.setMaxResult(BaseConst.excel_pageSize);
							
							// 查询该店铺下所有入库原始数据
							List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
							list = list == null ? new ArrayList<PerationfeeData>() : list;
							for (int j = 0; j < list.size(); j++) {
								PerationfeeData map = list.get(j);
								errorObject = map;
								BigDecimal sku_number = map.getIn_num();
								BigDecimal osr_ibop_qtyprice = oSR.getOsr_ibop_qtyprice();
								sum2 = sku_number.multiply(osr_ibop_qtyprice);
								sum2Num = sku_number;
								inboundSKU = inboundSKU.add(sum2);
								inboundSKUNum = inboundSKUNum.add(sum2Num);
							}
							errorObject = null;
							
							firstResult= pageNo*BaseConst.excel_pageSize;
						}
					}
				}
				// 2.B2C出库操作费
				Integer osr_btcobop_fee = oSR.getOsr_btcobop_fee();
				if (osr_btcobop_fee == 1) {
					// 按件出库
					Integer osr_btcobop_numfee = oSR.getOsr_btcobop_numfee();
					if (osr_btcobop_numfee == 1) {
						// 无阶梯
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							BigDecimal sum2 = new BigDecimal(0);
							BigDecimal sum2Num = new BigDecimal(0);
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);
								
								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								list = list == null ? new ArrayList<PerationfeeData>() : list;
								for (int j = 0; j < list.size(); j++) {
									errorObject = list.get(j);
									BigDecimal sku_number = list.get(j).getOut_num();
									BigDecimal osr_btcobop_numprice = oSR.getOsr_btcobop_numprice();
									BigDecimal osr_btcobop_numdc = oSR.getOsr_btcobop_numdc();
									sum2 = sku_number.multiply(osr_btcobop_numprice).multiply((osr_btcobop_numdc));
									sum2Num = sku_number;
									B2COutbound = B2COutbound.add(sum2);
									B2COutboundNum = B2COutboundNum.add(sum2Num);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
						}
					} else if (osr_btcobop_numfee == 2) {
						// 超过部分阶梯
						String osr_btcobopnum_tableid = oSR.getOsr_btcobopnum_tableid();
						List<Map<String, Object>> tbtList = operationSettlementRuleService
								.queryTBNTList(osr_btcobopnum_tableid);
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);
								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								list = list == null ? new ArrayList<PerationfeeData>() : list;
								for (int j = 0; j < list.size(); j++) {
									errorObject = list.get(j);
									BigDecimal sku_number = list.get(j).getOut_num();
									B2COutboundNum = B2COutboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnt_interval", "bnt_discount",null, null);
							if (Integer.valueOf(map.get("status").toString()) == 360) {
								B2COutbound = new BigDecimal(map.get("msg").toString());
							}
						}
					} else if (osr_btcobop_numfee == 3) {
						// 总件数阶梯
						String osr_btcobopbill_tobtb_tableid = oSR.getOsr_btcobopbill_tobtb_tableid();
						List<Map<String, Object>> tbtList = operationSettlementRuleService
								.queryTOTList(osr_btcobopbill_tobtb_tableid);
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);
								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								list = list == null ? new ArrayList<PerationfeeData>() : list;
								for (int j = 0; j < list.size(); j++) {
									errorObject = list.get(j);
									BigDecimal sku_number = list.get(j).getOut_num();
									B2COutboundNum = B2COutboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "2", "obt_interval", "obt_price",null, "obt_discount");
							if (Integer.valueOf(map.get("status").toString()) == 360) {
								B2COutbound = new BigDecimal(map.get("msg").toString());
							}
						}
					}
				} else if (osr_btcobop_fee == 2) {
					// 按单内件数阶梯计费
				} else if (osr_btcobop_fee == 3) {
					// 按单阶梯计费
					Integer osr_btcobop_numfees = null != oSR.getOsr_btcobop_numfees() && 
							!oSR.getOsr_btcobop_numfees().equals("") ? 
									Integer.valueOf(oSR.getOsr_btcobop_numfees()) : 0;
					if (osr_btcobop_numfees == 1) {
						// 无阶梯
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int orderCount = perationfeeDataService.countByEntityOrderNoSE(entity);
							BigDecimal osr_btcobop_numprices = oSR.getOsr_btcobop_numprices();
							BigDecimal osr_btcobop_numdcs = oSR.getOsr_btcobop_numdcs();
							B2COutbound = B2COutbound.add(new BigDecimal(orderCount).multiply(osr_btcobop_numprices).multiply((osr_btcobop_numdcs)));
							B2COutboundNum = B2COutboundNum.add(new BigDecimal(orderCount));
//							// 查询该店铺下所有入库原始数据
//							List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNoSE(entity);
//							BigDecimal osr_btcobop_numprices = oSR.getOsr_btcobop_numprices();
//							BigDecimal osr_btcobop_numdcs = oSR.getOsr_btcobop_numdcs();
//							B2COutbound = B2COutbound.add(new BigDecimal(list.size()).multiply(osr_btcobop_numprices).multiply((osr_btcobop_numdcs)));
//							B2COutboundNum = B2COutboundNum.add(new BigDecimal(list.size()));
						}
					} else if (osr_btcobop_numfees == 2) {
						// 超过部分阶梯
						String osr_btcobopnum_tableids = oSR.getOsr_btcobopnum_tableids();
						List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBBTLists(osr_btcobopnum_tableids);
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());

							int orderCount = perationfeeDataService.countByEntityOrderNoSE(entity);
							BigDecimal sku_number = new BigDecimal(orderCount);
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							// 查询该店铺下所有入库原始数据
//							List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNoSE(entity);
//							BigDecimal sku_number = new BigDecimal(list.size());
//							B2COutboundNum = B2COutboundNum.add(sku_number);
						}
						Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnts_interval", "bnts_discount", null, null);
						if (Integer.valueOf(map.get("status").toString()) == 360) {
							B2COutbound = new BigDecimal(map.get("msg").toString());
						}
					} else if (osr_btcobop_numfees == 3) {
						// 总件数阶梯
						// 超过部分阶梯
						String osr_btcobopbill_tobtb_tableidss = oSR.getOsr_btcobopnum_tableidss();
						List<Map<String, Object>> tbtList = operationSettlementRuleService
								.queryTBBTListss(osr_btcobopbill_tobtb_tableidss);
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货出库','销售出库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());

							int orderCount = perationfeeDataService.countByEntityOrderNoSE(entity);
							BigDecimal sku_number = new BigDecimal(orderCount);
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							// 查询该店铺下所有入库原始数据
//							List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNoSE(entity);
//							BigDecimal sku_number = new BigDecimal(list.size());
//							B2COutboundNum = B2COutboundNum.add(sku_number);
						}
						Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "2", "obts_interval", "obts_price", null, "obts_discount");
						if (Integer.valueOf(map.get("status").toString()) == 360) {
							B2COutbound = new BigDecimal(map.get("msg").toString());
						}
					}
	
				}
				// 3.B2B出库操作费
				Integer osr_btbobop_fee = oSR.getOsr_btbobop_fee();
				if (osr_btbobop_fee == 1) {
					// 按件出库
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						if(String.valueOf(cb.getId()).equals("264")){
							entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
						}else{
							entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						}
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(param.getFromDate());
						entity.setEnd_time(param.getToDate());
						
						int count = perationfeeDataService.countByEntitySE(entity);
						int firstResult= 0;
						for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
							
							entity.setFirstResult(firstResult);
							entity.setMaxResult(BaseConst.excel_pageSize);
							// 查询该店铺下所有入库原始数据
							List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
							list = list == null ? new ArrayList<PerationfeeData>() : list;
							for (int j = 0; j < list.size(); j++) {
								errorObject = list.get(j);
								BigDecimal sku_number = list.get(j).getOut_num();
								BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
								B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
								B2BOutboundNum = B2BOutboundNum.add(sku_number);
							}
							errorObject = null;
							
							firstResult= pageNo*BaseConst.excel_pageSize;
						}
					}
				} else if (osr_btbobop_fee == 2) {
					// 按SKU类型收取
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						if(String.valueOf(cb.getId()).equals("264")){
							entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
						}else{
							entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						}
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(param.getFromDate());
						entity.setEnd_time(param.getToDate());
						
						int count = perationfeeDataService.countByEntityGroupBySKUStartEnd(entity);
						int firstResult= 0;
						for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
							
							entity.setFirstResult(firstResult);
							entity.setMaxResult(BaseConst.excel_pageSize);
							// 查询该店铺下所有入库原始数据
							List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKUStartEnd(entity);
							list = list == null ? new ArrayList<PerationfeeData>() : list;
							for (int j = 0; j < list.size(); j++) {
								PerationfeeData map = list.get(j);
								errorObject = map;
								Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
								BigDecimal osr_ibop_itemprice = oSR.getOsr_btbobop_itemprice();
								B2BOutbound = B2BOutbound.add(((new BigDecimal(out_num).multiply(osr_ibop_itemprice))));
							}
							errorObject = null;
							
							firstResult= pageNo*BaseConst.excel_pageSize;
						}
						BigDecimal sku_number = new BigDecimal(count);
						B2BOutboundNum = B2BOutboundNum.add(sku_number);
						BigDecimal osr_btbobop_skuprice = oSR.getOsr_btbobop_skuprice();
						B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_skuprice));
					}
				}
				// 4.B2B退仓操作费
				Integer osr_btbrtop_fee = oSR.getOsr_btbrtop_fee();
				if (osr_btbobop_fee == 0 && osr_btbrtop_fee != null) {
					
					if (osr_btbrtop_fee == 1) {
						// 按SKU计算
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							if(String.valueOf(cb.getId()).equals("264")){
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
							}else{
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
							}
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntityGroupBySKUStartEnd(entity);
							int firstResult= 0;
							BigDecimal sku_number = new BigDecimal(count);
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);

								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKUStartEnd(entity);
								list = list == null ? new ArrayList<PerationfeeData>() : list;
								for (int j = 0; j < list.size(); j++) {
									PerationfeeData map = list.get(j);
									errorObject = map;
									Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
									BigDecimal osr_btbrtop_sku_skuprice = oSR.getOsr_btbrtop_sku_skuprice();
									BigDecimal osr_btbrtop_sku_billprice = oSR.getOsr_btbrtop_sku_billprice();
									BigDecimal a = sku_number.multiply(osr_btbrtop_sku_skuprice);
									BigDecimal b = new BigDecimal(out_num).multiply(osr_btbrtop_sku_billprice);
									B2BOutbound = B2BOutbound.add(a.add(b));
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							B2BOutboundNum = B2BOutboundNum.add(sku_number);
						}
					} else if (osr_btbrtop_fee == 2) {
						// 按件数计算
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							if(String.valueOf(cb.getId()).equals("264")){
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
							}else{
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
							}
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);
								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								for (int j = 0; j < list.size(); j++) {
									errorObject = list.get(j);
									BigDecimal sku_number = list.get(j).getOut_num();
									BigDecimal osr_btbobop_billprice = oSR.getOsr_btbrtop_bill_billprice();
									B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
									B2BOutboundNum = B2BOutboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
						}
					}
				}
				// 5.B2C退换货入库费
				Integer osr_btcrtib_fee = oSR.getOsr_btcrtib_fee();
				if (osr_btcrtib_fee == 1) {
					// 按单计算
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货入库','消费者退换货入库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(param.getFromDate());
						entity.setEnd_time(param.getToDate());
						

						int orderCount = perationfeeDataService.countByEntityOrderNoSE(entity);
						BigDecimal sku_number = new BigDecimal(orderCount);
						BigDecimal osr_btcrtib_bill_billprice = oSR.getOsr_btcrtib_bill_billprice();
						B2CInbound = B2CInbound.add(osr_btcrtib_bill_billprice.multiply(new BigDecimal(orderCount)));
						B2CInboundNum = B2CInboundNum.add(sku_number);
						
//						// 查询该店铺下所有入库原始数据
//						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNoSE(entity);
//						BigDecimal sku_number = new BigDecimal(list.size());
//						BigDecimal osr_btcrtib_bill_billprice = oSR.getOsr_btcrtib_bill_billprice();
//						B2CInbound = B2CInbound.add(osr_btcrtib_bill_billprice.multiply(new BigDecimal(list.size())));
//						B2CInboundNum = B2CInboundNum.add(sku_number);
					}
				} else if (osr_btcrtib_fee == 2) {
					// 按件计算
					// 是否有阶梯
					Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
					if (osr_btcrtib_piece == 1) {
						// 无阶梯
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货入库','消费者退换货入库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setStart_time(param.getFromDate());
							entity.setEnd_time(param.getToDate());
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {
								
								entity.setFirstResult(firstResult);
								entity.setMaxResult(BaseConst.excel_pageSize);
								// 查询该店铺下所有入库原始数据
								List<PerationfeeData> list = perationfeeDataService.findByEntitySE(entity);
								for (int k = 0; k < list.size(); k++) {
									errorObject = list.get(k);
									BigDecimal sku_number = null != list.get(k).getIn_num() ? list.get(k).getIn_num() : new BigDecimal(0.00);
									BigDecimal osr_btcrtib_piece_pieceprice = null != oSR.getOsr_btcrtib_piece_pieceprice() ? oSR.getOsr_btcrtib_piece_pieceprice() : new BigDecimal(0.00);
									B2CInbound = B2CInbound.add(sku_number.multiply(osr_btcrtib_piece_pieceprice));
									B2CInboundNum = B2CInboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
						}
					} else if (osr_btcrtib_piece == 2) {
						// 有阶梯
						// B2C退换货按件阶梯表格id
						// String osr_btcrti_tableid = oSR.getOsr_btcrti_tableid();
						// if(null!=osr_btcrti_tableid &&
						// !osr_btcrti_tableid.equals("")){
						// List<Map<String, Object>> yjt_list =
						// operationSettlementRuleService.queryTBTList(osr_btcrti_tableid);
						// BigDecimal a = new BigDecimal(0.00);
						// for (int i = 0; i < storeList.size(); i++) {
						// PerationfeeData entity = new PerationfeeData();
						// entity.setJob_type("'退换货出库','消费者退换货入库'");
						// entity.setStore_name(storeList.get(i).getStore_name());
						// entity.setSettle_flag(0);
						// //查询该店铺下所有入库原始数据
						// List<PerationfeeData> list =
						// perationfeeDataService.findByEntity(entity);
						// BigDecimal sum2Num = new BigDecimal(0.00);
						// for (int k = 0; k < list.size(); k++) {
						// BigDecimal sku_number = list.get(k).getIn_num();
						// sum2Num=sku_number;
						// }
						// a.add(sum2Num);
						// }
						// }
					}
				}
				BigDecimal allZK = new BigDecimal(StringUtils.isNotBlank(oSR.getAllzk()) ? oSR.getAllzk() : "1");
				OperationfeeDataDailySettlementEstimate insertMain = new OperationfeeDataDailySettlementEstimate();
				insertMain.setCreateTime(new Date());
				insertMain.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
				insertMain.setContractId(Integer.valueOf(cb.getId()));
				insertMain.setBatchNumber(param.getBatchNumber());
				insertMain.setUpdateTime(new Date());
				insertMain.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
				insertMain.setBtcQty(B2COutboundNum);
				insertMain.setBtcQtyunit("");
				insertMain.setBtcFee(B2COutbound.multiply(allZK));
	
				insertMain.setBtbQty(B2BOutboundNum);
				insertMain.setBtbQtyunit("");
				insertMain.setBtbFee(B2BOutbound.multiply(allZK));
	
				insertMain.setReturnQty(B2CInboundNum);
				insertMain.setReturnQtyunit("");
				insertMain.setReturnFee(B2CInbound.multiply(allZK));
	
				insertMain.setIbQty(inboundSKUNum);
				insertMain.setIbQtyunit("");
				insertMain.setIbFee(inboundSKU.multiply(allZK));
				if (B2COutbound.compareTo(new BigDecimal(0.00)) != 0 || B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
						|| B2CInbound.compareTo(new BigDecimal(0.00)) != 0
						|| inboundSKU.compareTo(new BigDecimal(0.00)) != 0) {
					operationfeeDataDailySettlementEstimateService.save(insertMain);
				}
			} catch (Exception e) {
				e.printStackTrace();
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(cb.getId());
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID[" + cb.getId() + "],预估异常！");
				bEL.setDefault1("操作费");
				bEL.setDefault2(param.getBatchNumber());
				if (errorObject != null) {
					if (errorObject instanceof PerationfeeData) {
						bEL.setRemark("tb_operationfee_data表数据id:" + 
								((PerationfeeData)errorObject).getId().toString());
					} else if (errorObject instanceof WarehouseExpressData) {
						bEL.setRemark("tb_warehouse_express_data表数据id:" + 
								((WarehouseExpressData)errorObject).getId().toString());
					}
				} else {
					bEL.setRemark("原始数据无关:"+
							e.getMessage().substring(0,e.getMessage().length()>200?200:e.getMessage().length()));
				}
				
				try {
					expressContractService.addBalanceErrorLog(bEL);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	
	/**
	 * @Title: forCount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param count
	 * @param max
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午4:42:27
	 */
	private static int forCount(int count, int max) {
		int i= 1;
		if(count> max) {
			if((count% max)!= 0) {
				i= (count/ max)+ 1;
				
			}else{
				i= (count/ max);
				
			}
			
		}
		return i;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
