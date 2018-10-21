package com.bt.lmis.balance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.balance.dao.ContractMapper;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.OperationfeeDataDetail;
import com.bt.lmis.balance.service.OperationfeeDataDetailService;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.PerationfeeDataService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/**
 * 操作费明细生成任务逻辑类，抽象单个合同执行逻辑
 * @author jindong.lin
 *
 */
public class OperatingCostDetailCallable implements Callable<String> {

	// 定义日志
	private static final Logger logger=Logger.getLogger(OperatingCostDetailCallable.class);
	
	private Integer contractId;
	
	private ContractMapper contractMapper = SpringUtils.getBean("contractMapper");
	
	// 快递合同服务类 ［调用错误日志插入服务］
	private ExpressContractService<T> expressContractService = SpringUtils.getBean("expressContractServiceImpl");
	// 店铺服务类
	private StoreService<Store> storeService = SpringUtils.getBean("storeServiceImpl");
	// 客户服务类
	private ClientService<Client> clientService = SpringUtils.getBean("clientServiceImpl");
	// 操作费结算规则表服务类
	private OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService 
				= SpringUtils.getBean("operationSettlementRuleServiceImpl");
	// 操作费原始数据服务类
	private PerationfeeDataService<PerationfeeData> perationfeeDataService
									= SpringUtils.getBean("perationfeeDataServiceImpl");
	// 打包费服务类
	private PackageChargeService<PackagePrice> packageChargeService = SpringUtils.getBean("packageChargeServiceImpl");
	
	private OperationfeeDataDetailService<OperationfeeDataDetail> operationfeeDataDetailService
										= SpringUtils.getBean("operationfeeDataDetailServiceImpl");
	
	
	public OperatingCostDetailCallable(){
		
	}
	
	public OperatingCostDetailCallable(Integer contractId){
		this.contractId = contractId;
	}

	@SuppressWarnings("unused")
	@Override
	public String call() throws Exception {
		
		Object errorObject = null;
		
		EstimateResult result = new EstimateResult();

		Contract cb = contractMapper.selectByPrimaryKey(contractId);
		
		EstimateParam param = new EstimateParam(
				DateUtil.formatSS(new Date()),null,null,cb
				);
		
		try {
			logger.info("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()+ "操作费明细生成任务开始...");
			// 打包费信息
			PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cb.getId()));
			// 操作费开关是否开
			if (null != pp && pp.getOperation().equals("0")) {
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(cb.getId());
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费明细生成任务异常！");
				bEL.setDefault1("操作费");
				bEL.setDefault2(param.getBatchNumber());
				bEL.setRemark("原始数据无关:"+
						"批次号:[" + param.getBatchNumber() + "],操作费明细生成任务异常!操作费开关未打开!");
				try {
					expressContractService.addBalanceErrorLog(bEL);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				result.setFlag(false);
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
						return "合同：" + contractId + "完成操作费明细生成任务失败,店铺不存在！";
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
							return "合同：" + contractId + "完成操作费明细生成任务失败,客户没有绑定店铺！";
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
							break;
						case 1:
							break;
						case 2:
							// 按实际发生费用结算
							actuallyHappen(param, oSR, storeList,errorObject);
							break;
						default:
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(cb.getId());
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费明细生成任务失败！");
							bEL.setDefault1("操作费");
							bEL.setDefault2(param.getBatchNumber());
							bEL.setRemark("原始数据无关:"+
									"批次号:[" + param.getBatchNumber() + "],操作费明细生成任务失败!未知的结算方式!");
							try {
								expressContractService.addBalanceErrorLog(bEL);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return "时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + "操作费明细生成任务失败!未知的结算方式!";
						}
					}
				}
			}

			logger.info("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContractName()+"操作费明细生成任务完成!");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + "操作费明细生成任务异常!");
			BalanceErrorLog bEL = new BalanceErrorLog();
			bEL.setContract_id(cb.getId());
			bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
			bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费明细生成任务异常！");
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
			return "合同：" + contractId + "完成操作费明细生成任务失败，操作费明细生成任务异常!";
		}
		
		return "合同：" + contractId + "完成操作费明细生成任务成功";
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
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
		Date settleDate = new Date();						
		logger.info("Settle_date="+settleDate);

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
						entity.setSettle_date(settleDate);
						entity.setSettle_flag(0);
						
						BigDecimal sum2 = new BigDecimal(0);
						BigDecimal sum2Num = new BigDecimal(0);
						
						int count = perationfeeDataService.countByEntitySE(entity);
						int firstResult= 0;
						OperationfeeDataDetail operationfeeDataDetail = new OperationfeeDataDetail();
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
								
								operationfeeDataDetail.setOperationfeeData(map);
								operationfeeDataDetail.setExpenses(sum2);
								operationfeeDataDetail.setType("osr_ibop_fee=2");
								operationfeeDataDetailService.save(operationfeeDataDetail);
								
								inboundSKU = inboundSKU.add(sum2);
								inboundSKUNum = inboundSKUNum.add(sum2Num);
							}
							errorObject = null;
							
							firstResult= pageNo*BaseConst.excel_pageSize;
						}

						perationfeeDataService.update_settleflag(entity);
						
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
							entity.setSettle_date(settleDate);
							entity.setSettle_flag(0);
							
							int count = perationfeeDataService.countByEntitySE(entity);
							int firstResult= 0;
							BigDecimal sum2 = new BigDecimal(0);
							BigDecimal sum2Num = new BigDecimal(0);
							OperationfeeDataDetail operationfeeDataDetail = new OperationfeeDataDetail();
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
									
									operationfeeDataDetail.setOperationfeeData(list.get(j));
									operationfeeDataDetail.setExpenses(sum2);
									operationfeeDataDetail.setType("osr_btcobop_fee=1&osr_btcobop_numfee=1");
									operationfeeDataDetailService.save(operationfeeDataDetail);
									
									B2COutbound = B2COutbound.add(sum2);
									B2COutboundNum = B2COutboundNum.add(sum2Num);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}

							perationfeeDataService.update_settleflag(entity);
							
						}
					} else if (osr_btcobop_numfee == 2) {
						// 超过部分阶梯
					} else if (osr_btcobop_numfee == 3) {
						// 总件数阶梯
					}
				} else if (osr_btcobop_fee == 2) {
					// 按单内件数阶梯计费
				} else if (osr_btcobop_fee == 3) {
					// 按单阶梯计费
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
						entity.setSettle_date(settleDate);
						entity.setSettle_flag(0);
						
						int count = perationfeeDataService.countByEntitySE(entity);
						int firstResult= 0;
						OperationfeeDataDetail operationfeeDataDetail = new OperationfeeDataDetail();
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
								BigDecimal sum2 = sku_number.multiply(osr_btbobop_billprice);
								
								operationfeeDataDetail.setOperationfeeData(list.get(j));
								operationfeeDataDetail.setExpenses(sum2);
								operationfeeDataDetail.setType("osr_btbobop_fee=1");
								operationfeeDataDetailService.save(operationfeeDataDetail);
								
								B2BOutbound = B2BOutbound.add(sum2);
								B2BOutboundNum = B2BOutboundNum.add(sku_number);
							}
							errorObject = null;
							
							firstResult= pageNo*BaseConst.excel_pageSize;
						}

						perationfeeDataService.update_settleflag(entity);
						
					}
				} else if (osr_btbobop_fee == 2) {
					// 按SKU类型收取
				}
				// 4.B2B退仓操作费
				Integer osr_btbrtop_fee = oSR.getOsr_btbrtop_fee();
				if (osr_btbobop_fee == 0 && osr_btbrtop_fee != null) {
					
					if (osr_btbrtop_fee == 1) {
						// 按SKU计算
					} else if (osr_btbrtop_fee == 2) {
						// 按件数计算
						OperationfeeDataDetail operationfeeDataDetail = new OperationfeeDataDetail();
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							if(String.valueOf(cb.getId()).equals("264")){
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
							}else{
								entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
							}
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setSettle_date(settleDate);
							entity.setSettle_flag(0);
							
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
									BigDecimal sum2 = sku_number.multiply(osr_btbobop_billprice);
									
									operationfeeDataDetail.setOperationfeeData(list.get(j));
									operationfeeDataDetail.setExpenses(sum2);
									operationfeeDataDetail.setType("osr_btbrtop_fee=2");
									operationfeeDataDetailService.save(operationfeeDataDetail);
									
									B2BOutbound = B2BOutbound.add(sum2);
									B2BOutboundNum = B2BOutboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}
							
							perationfeeDataService.update_settleflag(entity);
							
						}
					}
				}
				// 5.B2C退换货入库费
				Integer osr_btcrtib_fee = oSR.getOsr_btcrtib_fee();
				if (osr_btcrtib_fee == 1) {
					// 按单计算
				} else if (osr_btcrtib_fee == 2) {
					// 按件计算
					// 是否有阶梯
					Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
					if (osr_btcrtib_piece == 1) {
						// 无阶梯
						OperationfeeDataDetail operationfeeDataDetail = new OperationfeeDataDetail();
						for (int i = 0; i < storeList.size(); i++) {
							PerationfeeData entity = new PerationfeeData();
							entity.setJob_type("'退换货入库'");
							entity.setStore_name(storeList.get(i).getStore_name());
							entity.setSettle_date(settleDate);
							entity.setSettle_flag(0);
							
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
									BigDecimal sum2 = sku_number.multiply(osr_btcrtib_piece_pieceprice);
									
									operationfeeDataDetail.setOperationfeeData(list.get(k));
									operationfeeDataDetail.setExpenses(sum2);
									operationfeeDataDetail.setType("osr_btcrtib_fee=2&osr_btcrtib_piece=1");
									operationfeeDataDetailService.save(operationfeeDataDetail);
									
									B2CInbound = B2CInbound.add(sum2);
									B2CInboundNum = B2CInboundNum.add(sku_number);
								}
								errorObject = null;
								
								firstResult= pageNo*BaseConst.excel_pageSize;
							}

							perationfeeDataService.update_settleflag(entity);
							
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
						// entity.setJob_type("'退换货出库'");
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
			} catch (Exception e) {
				e.printStackTrace();
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(cb.getId());
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID[" + cb.getId() + "],操作费明细生成异常！");
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
