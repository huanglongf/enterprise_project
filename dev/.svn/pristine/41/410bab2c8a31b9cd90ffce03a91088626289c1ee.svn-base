package com.bt.lmis.summary;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.PerationfeeDataDailySettlementMapper;
import com.bt.lmis.dao.PerationfeeDataMapper;
import com.bt.lmis.dao.PerationfeeDataSettlementMapper;
import com.bt.lmis.model.AleamountData;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.NvitationRealuseanmountData;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.PerationSettlementFixed;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.PerationfeeDataDailySettlement;
import com.bt.lmis.model.PerationfeeDataSettlement;
import com.bt.lmis.model.PerationfeeDatas;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.AleamountDataService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.NvitationRealuseanmountDataService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.PerationSettlementFixedService;
import com.bt.lmis.service.PerationfeeDataDailySettlementService;
import com.bt.lmis.service.PerationfeeDataService;
import com.bt.lmis.service.PerationfeeDataSettlementService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/**
 * @ClassName: OperatingCost
 * @Description: TODO(操作费结算)
 * @author Yuriy.Jiang
 * @date 2016年8月1日 下午6:25:41
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class OperatingCost {

	private static final Logger logger = Logger.getLogger(OperatingCost.class);
	
	// 合同服务类
	ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService = (ContractBasicinfoService<ContractBasicinfo>) SpringUtils.getBean("contractBasicinfoServiceImpl");
	// 快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>) SpringUtils.getBean("expressContractServiceImpl");
	// 店铺服务类
	StoreService<Store> storeService = (StoreService<Store>) SpringUtils.getBean("storeServiceImpl");
	// 客户服务类
	ClientService<Client> clientService = (ClientService<Client>) SpringUtils.getBean("clientServiceImpl");
	// 耗材实际使用量原始数据
	NvitationRealuseanmountDataService<NvitationRealuseanmountData> nvitationRealuseanmountDataService = (NvitationRealuseanmountDataService<NvitationRealuseanmountData>) SpringUtils.getBean("nvitationRealuseanmountDataServiceImpl");
	// 打包费服务类
	PackageChargeService<PackagePrice> packageChargeService = (PackageChargeService<PackagePrice>) SpringUtils.getBean("packageChargeServiceImpl");
	// 操作费结算规则表服务类
	OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService = (OperationSettlementRuleService<OperationSettlementRule>) SpringUtils.getBean("operationSettlementRuleServiceImpl");
	// 操作费明细数据服务类
	PerationfeeDataDailySettlementService<PerationfeeDataDailySettlement> perationfeeDataDailySettlementService = (PerationfeeDataDailySettlementService<PerationfeeDataDailySettlement>) SpringUtils.getBean("perationfeeDataDailySettlementServiceImpl");
	// 操作费原始数据服务类
	PerationfeeDataService<PerationfeeData> perationfeeDataService = (PerationfeeDataService<PerationfeeData>) SpringUtils.getBean("perationfeeDataServiceImpl");
	// 固定费用超过部分阶梯服务类
	PerationSettlementFixedService<PerationSettlementFixed> perationSettlementFixedService = (PerationSettlementFixedService<PerationSettlementFixed>) SpringUtils.getBean("perationSettlementFixedServiceImpl");
	// 操作费明细汇总服务类
	PerationfeeDataSettlementService<PerationfeeDataSettlement> perationfeeDataSettlementService = (PerationfeeDataSettlementService<PerationfeeDataSettlement>) SpringUtils.getBean("perationfeeDataSettlementServiceImpl");
	// 操作费明细数据服务类
	AleamountDataService<AleamountData> aleamountDataService = (AleamountDataService<AleamountData>) SpringUtils.getBean("aleamountDataServiceImpl");
	//
	WarehouseExpressDataService<WarehouseExpressData> warehouseExpressDataService = (WarehouseExpressDataService<WarehouseExpressData>) SpringUtils.getBean("warehouseExpressDataServiceImpl");
	@Autowired
	PerationfeeDataMapper<T> pdmapper;
	@Autowired
    private PerationfeeDataDailySettlementMapper<T> pddsmapper;
	@Autowired
    private PerationfeeDataSettlementMapper<T> pdsmapper;
	
	/**
	 * @Title: settlement 
	 * @Description: TODO(结算) 
	 * @param 设定文件 
	 * @return void
	 * 返回类型 @throws
	 */
	public void settlement() {
		// 合同信息
		List<ContractBasicinfo> cbList = contractBasicinfoService.find_by_cb();
		if (null != cbList && cbList.size() != 0) {
			// 遍历合同
			for (int i = 0; i < cbList.size(); i++) {
				try {
					System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name()+ "操作费结算开始...");
					if (DateUtil.isTime(cbList.get(i).getContract_start(), cbList.get(i).getContract_end(),DateUtil.formatDate(new Date()))) {
						// 打包费信息
						PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.get(i).getId()));
						// 操作费开关是否开
						if (null != pp && pp.getOperation().equals("0")) {
							continue;
						} else {
							// 合同周期
							Map<String, Date> mapss = new HashMap<String, Date>();
							mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.get(i).getSettle_date()));
							// 主体
							// 添加店铺集合
							String contract_owner = cbList.get(i).getContract_owner();
							String contract_type = cbList.get(i).getContract_type();
							List<Store> storeList = new ArrayList<Store>();
							if (contract_type.equals("3")) {
								Store store = storeService.findByContractOwner(contract_owner);
								if (null != store) {
									storeList.add(store);
								} else {
									BalanceErrorLog bEL = new BalanceErrorLog();
									bEL.setContract_id(Integer.valueOf(cbList.get(i).getId()));
									bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
									bEL.setPro_result_info("合同ID[" + cbList.get(i).getId() + "],店铺不存在！");
									expressContractService.addBalanceErrorLog(bEL);
									continue;
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
										expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.get(i).getId()),BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID[" + cbList.get(i).getId()+ "]," + client.getClient_name() + "客户没有绑定店铺！",null, null, null, null, new Date()));
										continue;
									}
								}
							}
							// 获取操作费规则
							List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(cbList.get(i).getId());
							if (oSRList.size() >= 1) {
								OperationSettlementRule oSR = oSRList.get(0);
								// 结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]
								if (null != oSR.getOsr_setttle_method()) {
									int osr_setttle_method = oSR.getOsr_setttle_method();
									switch (osr_setttle_method) {
									case 0:
										// 固定费用结算
										fixedCost(cbList.get(i), oSR, mapss, storeList);
										continue;
									case 1:
										// 按销售额百分比结算
 										salesPercentage(cbList.get(i), oSR, storeList);
										continue;
									case 2:
										// 按实际发生费用结算
										actuallyHappen(cbList.get(i), oSR, storeList);
										continue;
									default:
										logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name() + "操作费结算异常!未知的结算规则!");
										continue;
									}
								}
							}
						}
					}
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name() + "操作费结算完成!");
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name() + "操作费结算异常!");
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description: TODO(汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午2:26:31
	 */
	public Boolean summary(ContractBasicinfo cb, String ym) throws Exception {
		logger.debug("时间：［"+ DateUtil.formatS(new Date())+ "］ "+ cb.getContract_name()+ "操作费汇总开始...");
		//获取操作费规则
		OperationSettlementRule oSR= operationSettlementRuleService.findByCBID(cb.getId()).size()!=0? operationSettlementRuleService.findByCBID(cb.getId()).get(0): null;
		
		if (CommonUtils.checkExistOrNot(oSR)) {
			//查询操作费结算信息
			PerationfeeDataSettlement dataSettlement= new PerationfeeDataSettlement();
			dataSettlement.setSettle_period(ym);
			dataSettlement.setContract_id(Integer.valueOf(cb.getId()));
			//查询汇总数据是否存在
			List<PerationfeeDataSettlement> dataSettlements= perationfeeDataSettlementService.findByList(dataSettlement);
			//合同开始时间和结束时间
			Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(ym, Integer.parseInt(cb.getSettle_date()));
			//判断该合同结算月是否生成过操作费数据
			if(dataSettlements.size()==0){
				BigDecimal inboundSKU = new BigDecimal(0.00);
				BigDecimal inboundSKUNum = new BigDecimal(0.00);
				Integer osr_ibop_fee = oSR.getOsr_ibop_fee();
				//1.入库操作费
				if(null!=osr_ibop_fee && osr_ibop_fee==1){
					String contract_owner= cb.getContract_owner();
					String contract_type= cb.getContract_type();
					List<Store> storeList= null;
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
							expressContractService.addBalanceErrorLog(bEL);
							return false;
						}
					}else if(contract_type.equals("4")){
						Client client = clientService.findByContractOwner(contract_owner);
						List<Store> temp = storeService.selectByClient(client.getId());
						if(CommonUtils.checkExistOrNot(temp)){
							storeList= temp;
						} else {
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cb.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
							return false;
						}
					}
					//循环所有店铺 店铺为最小单位
					for (int j= 0; j< storeList.size(); j++) {
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKUYM("'VMI移库入库','代销入库','结算经销入库'",storeList.get(j).getStore_name(), String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR)), String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1));
						BigDecimal osr_ibop_skuprice = oSR.getOsr_ibop_skuprice();
						BigDecimal osr_ibop_itemprice = oSR.getOsr_ibop_itemprice();
						for (int k = 0; k < list.size(); k++) {
							PerationfeeData map = list.get(k);
							BigDecimal sku_count = new BigDecimal(map.getSku_count());
							BigDecimal in_num = new BigDecimal(null != map.getIn_num() ? map.getIn_num().toString() : "0");
							inboundSKU = inboundSKU.add((osr_ibop_itemprice.multiply(in_num)).add(osr_ibop_skuprice.multiply(sku_count)));
							inboundSKUNum = inboundSKUNum.add(sku_count);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
							data.setStore_name(storeList.get(j).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
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
							String contract_owner= cb.getContract_owner();
							String contract_type= cb.getContract_type();
							List<Store> storeList= null;
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
									expressContractService.addBalanceErrorLog(bEL);
									return false;
								}
							} else if (contract_type.equals("4")){
								Client client = clientService.findByContractOwner(contract_owner);
								List<Store> temp = storeService.selectByClient(client.getId());
								if(CommonUtils.checkExistOrNot(temp)) {
									storeList= temp;
								} else {
									expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+ cb.getId()+ "],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
									return false;
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
								entity.setSettle_flag(0);
								entity.setStart_time(balance_cycle.get("balance_start_date").toString().substring(0, 4));
								entity.setEnd_time(balance_cycle.get("balance_start_date").toString().substring(4, 7).replace("-0", "-").replace("-", ""));
								//查询该店铺下所有入库原始数据
								list2= perationfeeDataService.findByEntityGroupByOutNum(entity);
								//是否转B2B
								Integer osr_tobtb = oSR.getOsr_tobtb();
								List<PerationfeeDatas> list = new ArrayList<>();
								List<PerationfeeDatas> list3 = new ArrayList<>();
								for (int l = 0; l < list2.size(); l++) {
									BigDecimal out_num = list2.get(l).getOut_num();
									if (osr_tobtb == 1) {
										// 转B2B
										BigDecimal osr_btcobopbill_tobtb_js = null != oSR.getOsr_btcobopbill_tobtb_js() && !oSR.getOsr_btcobopbill_tobtb_js().equals("") ? new BigDecimal(oSR.getOsr_btcobopbill_tobtb_js()) : new BigDecimal(0.00);
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
											BigDecimal sku_number = list3.get(o).getOut_num().multiply(new BigDecimal(String.valueOf(list3.get(o).getCtnum())));
											BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
//																	PerationfeeDatas map1 = list3.get(o);
//																	map1.setSettle_flag(1);
//																	perationfeeDataService.updates(map1);
											B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
											B2BOutboundNum = B2BOutboundNum.add(sku_number);
										}
										if(list3.size()!=0){
											PerationfeeDatas data = new PerationfeeDatas();
											data.setJob_type("'销售出库','退换货出库'");
											data.setStore_name(storeList.get(j).getStore_name());
											data.setSettle_flag(0);
											perationfeeDataService.update_settleflags(data);
										}
									}
								}
								
								//维护的区间值
								List<Map<String, Object>> tbbtList = operationSettlementRuleService.queryTBBTList(String.valueOf(oSR.getOsr_btcobopbill_tableid()));
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
											if(out_num.compareTo(new BigDecimal(startNum.toString()))==1  && out_num.compareTo(new BigDecimal(endNum.toString()))<0){
												B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
												BigDecimal a = new BigDecimal(d_prices);
												BigDecimal b = new BigDecimal(j_prices);
												BigDecimal c = list.get(q).getOut_num();
												BigDecimal d = new BigDecimal(list.get(q).getCtnum());
												BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
												B2COutbound=B2COutbound.add(e);
											}
										}else if(startSymbol==1 && endSymbol==1){
											//[]
											if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 && out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
												B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
												BigDecimal a = new BigDecimal(d_prices);
												BigDecimal b = new BigDecimal(j_prices);
												BigDecimal c = list.get(q).getOut_num();
												BigDecimal d = new BigDecimal(list.get(q).getCtnum());
												BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
												B2COutbound=B2COutbound.add(e);
											}
										}else if(startSymbol==0 && endSymbol==1){
											//(]
											if(out_num.compareTo(new BigDecimal(startNum.toString()))==1 && out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
												B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
												BigDecimal a = new BigDecimal(d_prices);
												BigDecimal b = new BigDecimal(j_prices);
												BigDecimal c = list.get(q).getOut_num();
												BigDecimal d = new BigDecimal(list.get(q).getCtnum());
												BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
												B2COutbound=B2COutbound.add(e);
											}
										}else if(startSymbol==1 && endSymbol==0){
											//[)
											if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 && out_num.compareTo(new BigDecimal(endNum.toString()))<0){
												B2COutboundNum=B2COutboundNum.add(new BigDecimal(list.get(q).getCtnum()));
												BigDecimal a = new BigDecimal(d_prices);
												BigDecimal b = new BigDecimal(j_prices);
												BigDecimal c = list.get(q).getOut_num();
												BigDecimal d = new BigDecimal(list.get(q).getCtnum());
												BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
												B2COutbound=B2COutbound.add(e);
											}
										}
//																PerationfeeDatas maps = list.get(q);
//																maps.setSettle_flag(1);
//																perationfeeDataService.updates(maps);
									}
									if(list3.size()!=0){
										PerationfeeDatas data = new PerationfeeDatas();
										data.setJob_type("'销售出库','退换货出库'");
										data.setStore_name(storeList.get(j).getStore_name());
										data.setSettle_flag(0);
										perationfeeDataService.update_settleflags(data);
									}
								}
							}
							if(null!=oSR.getOsr_btcobopbill_discount_type() && !oSR.getOsr_btcobopbill_discount_type().equals("")){
								if(oSR.getOsr_btcobopbill_discount_type().equals("1")){
									//阶梯折扣按件结算
									List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBDList(oSR.getOsr_btcobopbill_discount_tableid());
									Map<String, Object> map = getPrice(taList, B2COutboundNum, "1", "btcbd_interval", "btcbd_price",B2COutbound,null);
									if (Integer.valueOf(map.get("status").toString())==360) {
										B2COutbound=new BigDecimal(map.get("msg").toString());
									}
								}
//														else if(oSR.getOsr_btcobopbill_discount_type().equals("2")){
//															//阶梯折扣按单结算
//															List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBD2List(oSR.getOsr_btcobopbill_discount_tableid2());
//															Map<String, Object> map = getPrice(taList, dNum, "2", "btcbd2_interval", "btcbd2_price",B2COutbound,null);
//															if (Integer.valueOf(map.get("status").toString())==360) {
//																B2COutbound=new BigDecimal(map.get("msg").toString());
//															}
//														}
							}
							PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
							insertMain.setCreate_time(new Date());
							insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
							insertMain.setContract_id(Integer.valueOf(cb.getId()));
							insertMain.setData_date(ym);

							insertMain.setBtc_qty(B2COutboundNum);
							insertMain.setBtc_qtyunit("");
							insertMain.setBtc_fee(B2COutbound);

							insertMain.setBtb_qty(B2BOutboundNum);
							insertMain.setBtb_qtyunit("");
							insertMain.setBtb_fee(B2BOutbound);

							insertMain.setReturn_qty(new BigDecimal(0.00));
							insertMain.setReturn_qtyunit("");
							insertMain.setReturn_fee(new BigDecimal(0.00));

							insertMain.setIb_qty(inboundSKUNum);
							insertMain.setIb_qtyunit("");
							insertMain.setIb_fee(inboundSKU);
							if (inboundSKU.compareTo(new BigDecimal(0.00)) != 0
									||B2COutbound.compareTo(new BigDecimal(0.00)) != 0
									|| B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
									) {
								perationfeeDataDailySettlementService.save(insertMain);
							}
						}
					}else{
						logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContract_name()+"操作费结算异常!未知的结算规则!");
						
					}
				
				}

				//退换货入库费按件数计算 是否有阶梯
				Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
				if(null==osr_btcrtib_piece){
					qty3 = new BigDecimal(0.00);
					fee3 = new BigDecimal(0.00);
				}else if (osr_btcrtib_piece==1) {
					
				}else{
					String contract_owner= cb.getContract_owner();
					String contract_type= cb.getContract_type();
					List<Store> storeList= new ArrayList<Store>();
					if(contract_type.equals("3")){
						Store store = storeService.findByContractOwner(contract_owner);
						if(null!=store){
							storeList.add(store);
						}else{
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(Integer.valueOf(cb.getId()));
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID["+ cb.getId()+ "],店铺不存在！");
							expressContractService.addBalanceErrorLog(bEL);
							return false;
							
						}
						
					} else if (contract_type.equals("4")) {
						Client client = clientService.findByContractOwner(contract_owner);
						List<Store> sList = storeService.selectByClient(client.getId());
						if(null!=sList && sList.size()!=0){
							for (int k = 0; k < sList.size(); k++) {
								storeList.add(sList.get(k));
								
							}
							
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cb.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
							return false;
							
						}
						
					}
					BigDecimal inb = new BigDecimal(0.00);
					BigDecimal oub = new BigDecimal(0.00);
					for (int k = 0; k < storeList.size(); k++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货入库'");
						entity.setStore_name(storeList.get(k).getStore_name());
						entity.setSettle_flag(0);
						//查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int l = 0; l < list.size(); l++) {
							inb = inb.add(list.get(l).getIn_num());
						}
						PerationfeeData entitys = new PerationfeeData();
						entitys.setJob_type("'销售出库','退换货出库'");
						entitys.setStore_name(storeList.get(k).getStore_name());
						entitys.setSettle_flag(0);
						//查询该店铺下所有入库原始数据
						List<PerationfeeData> lists = perationfeeDataService.findByEntity(entitys);
						for (int m = 0; m < lists.size(); m++) {
							oub = oub.add(lists.get(m).getOut_num());
						}
						if (oub.compareTo(new BigDecimal(0.00))==0) {
							qty3=qty3.add(new BigDecimal(0.00));
							fee3=fee3.add(new BigDecimal(0.00));
						}else{
							BigDecimal div = inb.divide(oub,2,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
							String osr_btcrti_tableid = oSR.getOsr_btcrti_tableid();
							if(null!=osr_btcrti_tableid && !osr_btcrti_tableid.equals("")){
								List<Map<String, Object>> yjt_list = operationSettlementRuleService.queryTBTList(osr_btcrti_tableid);	
								Map<String, Object> map = getPrice(yjt_list, div, "2", "btcrti_interval", "btcrt_price",inb,null);
								if (Integer.valueOf(map.get("status").toString())==360) {
									qty3=qty3.add(inb);
									fee3=fee3.add(new BigDecimal(map.get("msg").toString()));
								}
							}
						}
					}
				}
				//汇总累加
				PerationfeeDataDailySettlement pfd = new PerationfeeDataDailySettlement();
				pfd.setData_date(ym);
				pfd.setContract_id(Integer.valueOf(cb.getId()));
				List<PerationfeeDataDailySettlement> pfdList = perationfeeDataDailySettlementService.findByEntity(pfd);
				for (int j = 0; j < pfdList.size(); j++) {
					PerationfeeDataDailySettlement pfds = pfdList.get(j);
					
					qty1=qty1.add(null!=pfds.getBtc_qty()?pfds.getBtc_qty():new BigDecimal(0.00));
					fee1=fee1.add(null!=pfds.getBtc_fee()?pfds.getBtc_fee():new BigDecimal(0.00));

					qty2=qty2.add(null!=pfds.getBtb_qty()?pfds.getBtb_qty():new BigDecimal(0.00));
					fee2=fee2.add(null!=pfds.getBtb_fee()?pfds.getBtb_fee():new BigDecimal(0.00));

					qty3=qty3.add(null!=pfds.getReturn_qty()?pfds.getReturn_qty():new BigDecimal(0.00));
					fee3=fee3.add(null!=pfds.getReturn_fee()?pfds.getReturn_fee():new BigDecimal(0.00));
					
					qty4=qty4.add(null!=pfds.getIb_qty()?pfds.getIb_qty():new BigDecimal(0.00));
					fee4=fee4.add(null!=pfds.getIb_fee()?pfds.getIb_fee():new BigDecimal(0.00));
					
					qty5=qty5.add(null!=pfds.getGd_qty()?pfds.getGd_qty():new BigDecimal(0.00));
					fee5=fee5.add(null!=pfds.getGd_fee()?pfds.getGd_fee():new BigDecimal(0.00));
					
					qty6=qty6.add(null!=pfds.getXse_qty()?pfds.getXse_qty():new BigDecimal(0.00));
					fee6=fee6.add(null!=pfds.getXse_fee()?pfds.getXse_fee():new BigDecimal(0.00));
				}
				PerationfeeDataSettlement entity = new PerationfeeDataSettlement();
				entity.setCreate_time(new Date());
				entity.setCreate_user("1");
				entity.setContract_id(Integer.valueOf(cb.getId()));
				entity.setSettle_period(ym);
				entity.setBtc_qty(qty1);
				entity.setBtc_fee(fee1);
				entity.setBtb_qty(qty2);
				entity.setBtb_fee(fee2);
				entity.setReturn_qty(qty3);
				entity.setReturn_fee(fee3);
				entity.setIb_qty(qty4.add(inboundSKUNum));
				entity.setIb_fee(fee4.add(inboundSKU));
				entity.setXse_qty(qty6);
				entity.setXse_fee(fee6);
				entity.setGd_qty(qty5);
				entity.setGd_fee(fee5);
				if(fee1.compareTo(new BigDecimal(0.00))!=0 ||
						fee2.compareTo(new BigDecimal(0.00))!=0 ||
						fee3.compareTo(new BigDecimal(0.00))!=0 ||
						fee4.compareTo(new BigDecimal(0.00))!=0 ||
						fee5.compareTo(new BigDecimal(0.00))!=0 ||
						fee6.compareTo(new BigDecimal(0.00))!=0) {
					perationfeeDataSettlementService.save(entity);
					
				}
				logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContract_name()+"操作费汇总完成!");
				
			}
			
		}
 		return true;
		
	}

	/** 
	* @Title: fixedCost 
	* @Description: TODO(固定费用) 
	* @param @param cb
	* @param @param oSR
	* @param @param mapps
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void fixedCost(ContractBasicinfo cb, OperationSettlementRule oSR, Map<String, Date> mapps,List<Store> storeList) {
		if (oSR.getOsr_fixed_type() == 1) {
			// 无阶梯
			String data_data = DateUtil.getMonth(DateUtil.getYesterDay());
			Integer cbid = Integer.valueOf(cb.getId());
			try {
				PerationfeeDataDailySettlement PFD = new PerationfeeDataDailySettlement();
				PFD.setContract_id(cbid);
				PFD.setData_date(data_data);
				List<PerationfeeDataDailySettlement> pfdList = perationfeeDataDailySettlementService.findByEntity(PFD);
				// 该合同当月没有创建数据
				if (pfdList.size() == 0) {
					PFD.setCreate_time(new Date());
					PFD.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					PFD.setGd_qty(new BigDecimal(0.00));
					PFD.setGd_qtyunit("-");
					PFD.setGd_fee(new BigDecimal(oSR.getOsr_fixed_price()).multiply(new BigDecimal(oSR.getAllzk())));
					PFD.setGd_remark("元");
					if (PFD.getGd_fee().compareTo(new BigDecimal(0.00)) != 0) {
						perationfeeDataDailySettlementService.save(PFD);
					}
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
				entity.setSettle_flag(0);
				list = perationfeeDataService.findByEntity(entity);
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getIn_num().compareTo(new BigDecimal(0)) != 0) {
						a1 = a1.add(list.get(j).getIn_num());
					} else {
						a1 = a1.add(list.get(j).getOut_num());
					}
				}
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
					PerationfeeDataDailySettlement insertData = new PerationfeeDataDailySettlement();
					insertData.setContract_id(oSR.getContract_id());
					insertData.setData_date(DateUtil.getMonth(DateUtil.getYesterDay()));
					insertData.setCreate_time(new Date());
					insertData.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setUpdate_time(new Date());
					insertData.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setGd_qty(a1);
					insertData.setGd_qtyunit("件");
					insertData.setGd_fee(new BigDecimal(map.get("msg").toString()).add(new BigDecimal(null!=oSR.getOsr_fixed_price()&&!oSR.getOsr_fixed_price().equals("")?oSR.getOsr_fixed_price():"0.00")));
					insertData.setGd_remark("元");
//					for (int i = 0; i < list.size(); i++) {
//						PerationfeeData d = list.get(i);
//						d.setSettle_flag(1);
//						// 修改原始数据状态
//						perationfeeDataService.update(d);
//					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
						data.setStore_name(list.get(0).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
					if (insertData.getGd_fee().compareTo(new BigDecimal(0.00)) != 0) {
						perationfeeDataDailySettlementService.save(insertData);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, oSR.getContract_id(), BaseConst.PRO_RESULT_FLAG_ERROR,"合同ID[" + oSR.getContract_id() + "],插入数据异常！", null, null, null, null, new Date()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}


	/** 
	* @Title: salesPercentage 
	* @Description: TODO(按销售额百分比结算) 
	* @param @param cb
	* @param @param oSR
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void salesPercentage(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList) {
		//判断是否为结算日
		if(DateUtil.judgeSummaryOrNot(Integer.parseInt(cb.getSettle_date()))){
			try {
				List<WarehouseExpressData> list = new ArrayList<>();
				for (int i = 0; i < storeList.size(); i++) {
					WarehouseExpressData data = new WarehouseExpressData();
					//该店铺下的所有原始数据	dataList
					String yy = String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR));
					String mm = String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1);
					data.setTstart_time(yy);
					data.setTend_time(mm);
					data.setOperating_flag(0);
					data.setStore_name(storeList.get(i).getStore_name());
					list = warehouseExpressDataService.queryAll(data);
				}
				if (null!=list && list.size() != 0) {
					BigDecimal sum = new BigDecimal(0.00);
					for (int i = 0; i < list.size(); i++) {
						BigDecimal a = list.get(i).getOrder_amount();
						BigDecimal b = new BigDecimal(0.00);
						BigDecimal c = new BigDecimal(0.00);
						if (null != oSR.getOsr_sales_percent() && !oSR.getOsr_sales_percent().equals("")) {
							b = new BigDecimal(oSR.getOsr_sales_percent());
						}
						if (null != oSR.getOsr_tax_point() && !oSR.getOsr_tax_point().equals("")) {
							c = new BigDecimal(oSR.getOsr_tax_point());
						}
						sum = sum.add(a.multiply(b).multiply(c));
						WarehouseExpressData entity = list.get(i);
						entity.setOperating_flag(1);
						if (a.compareTo(new BigDecimal(0.00)) != 0 || c.compareTo(new BigDecimal(0.00)) != 0 || b.compareTo(new BigDecimal(0.00)) != 0) {
							warehouseExpressDataService.update(entity);
						}
					}
					if (sum.compareTo(new BigDecimal(0.00)) != 0) {
						PerationfeeDataDailySettlement PFD = new PerationfeeDataDailySettlement();
						PFD.setCreate_time(new Date());
						PFD.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
						PFD.setUpdate_time(new Date());
						PFD.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
						PFD.setContract_id(Integer.valueOf(cb.getId()));
						PFD.setData_date(DateUtil.getMonth(DateUtil.getYesterDay()));
						PFD.setXse_qty(new BigDecimal(1.00));
						PFD.setXse_qtyunit("单");
						PFD.setXse_fee(sum.multiply(new BigDecimal(oSR.getAllzk())));
						PFD.setXse_remark("");
						if (PFD.getXse_fee().compareTo(new BigDecimal(0.00)) != 0) {
							perationfeeDataDailySettlementService.save(PFD);
						}
					}
				} else {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cb.getId()));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info(cb.getId() + "该合同在" + DateUtil.getCalendarToString(DateUtil.getYesterDay())+ " 没有查询到需要结算的数据,请查看是否已经生成结算数据!");
					expressContractService.addBalanceErrorLog(bEL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	* @Title: actuallyHappen 
	* @Description: TODO(按实际发生费用结算) 
	* @param @param cb
	* @param @param oSR
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
public void actuallyHappen(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList) {
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
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
					BigDecimal sum2 = new BigDecimal(0);
					BigDecimal sum2Num = new BigDecimal(0);
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						BigDecimal sku_number = map.getIn_num();
						BigDecimal osr_ibop_qtyprice = oSR.getOsr_ibop_qtyprice();
						sum2 = sku_number.multiply(osr_ibop_qtyprice);
						sum2Num = sku_number;
						inboundSKU = inboundSKU.add(sum2);
						inboundSKUNum = inboundSKUNum.add(sum2Num);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
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
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						BigDecimal sum2 = new BigDecimal(0);
						BigDecimal sum2Num = new BigDecimal(0);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							BigDecimal osr_btcobop_numprice = oSR.getOsr_btcobop_numprice();
							BigDecimal osr_btcobop_numdc = oSR.getOsr_btcobop_numdc();
							sum2 = sku_number.multiply(osr_btcobop_numprice).multiply((osr_btcobop_numdc));
							sum2Num = sku_number;
							B2COutbound = B2COutbound.add(sum2);
							B2COutboundNum = B2COutboundNum.add(sum2Num);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
						}
					}
				} else if (osr_btcobop_numfee == 2) {
					// 超过部分阶梯
					String osr_btcobopnum_tableid = oSR.getOsr_btcobopnum_tableid();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBNTList(osr_btcobopnum_tableid);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
						}
						Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnt_interval", "bnt_discount",null, null);
						if (Integer.valueOf(map.get("status").toString()) == 360) {
							B2COutbound = new BigDecimal(map.get("msg").toString());
						}
					}
				} else if (osr_btcobop_numfee == 3) {
					// 总件数阶梯
					String osr_btcobopbill_tobtb_tableid = oSR.getOsr_btcobopbill_tobtb_tableid();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTOTList(osr_btcobopbill_tobtb_tableid);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
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
				Integer osr_btcobop_numfees = null != oSR.getOsr_btcobop_numfees() && !oSR.getOsr_btcobop_numfees().equals("") ? Integer.valueOf(oSR.getOsr_btcobop_numfees()) : 0;
				if (osr_btcobop_numfees == 1) {
					// 无阶梯
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal osr_btcobop_numprices = oSR.getOsr_btcobop_numprices();
						BigDecimal osr_btcobop_numdcs = oSR.getOsr_btcobop_numdcs();
						B2COutbound = B2COutbound.add(new BigDecimal(list.size()).multiply(osr_btcobop_numprices).multiply((osr_btcobop_numdcs)));
						B2COutboundNum = B2COutboundNum.add(new BigDecimal(list.size()));
					}
				} else if (osr_btcobop_numfees == 2) {
					// 超过部分阶梯
					String osr_btcobopnum_tableids = oSR.getOsr_btcobopnum_tableids();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBBTLists(osr_btcobopnum_tableids);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal sku_number = new BigDecimal(list.size());
						B2COutboundNum = B2COutboundNum.add(sku_number);
					}
					Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnts_interval", "bnts_discount", null, null);
					if (Integer.valueOf(map.get("status").toString()) == 360) {
						B2COutbound = new BigDecimal(map.get("msg").toString());
					}
				} else if (osr_btcobop_numfees == 3) {
					// 总件数阶梯
					// 超过部分阶梯
					String osr_btcobopbill_tobtb_tableidss = oSR.getOsr_btcobopnum_tableidss();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBBTListss(osr_btcobopbill_tobtb_tableidss);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal sku_number = new BigDecimal(list.size());
						B2COutboundNum = B2COutboundNum.add(sku_number);
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
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);

					for (int j = 0; j < list.size(); j++) {
						BigDecimal sku_number = list.get(j).getOut_num();
						BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
						B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
						B2BOutboundNum = B2BOutboundNum.add(sku_number);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
				}
			} else if (osr_btbobop_fee == 2) {
				// 按SKU类型收取
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKU(entity);
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
						BigDecimal osr_ibop_itemprice = oSR.getOsr_btbobop_itemprice();
						B2BOutbound = B2BOutbound.add(((new BigDecimal(out_num).multiply(osr_ibop_itemprice))));
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
					BigDecimal sku_number = new BigDecimal(list.size());
					B2BOutboundNum = B2BOutboundNum.add(sku_number);
					BigDecimal osr_btbobop_skuprice = oSR.getOsr_btbobop_skuprice();
					B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_skuprice));
				}
			}
			// 4.B2B退仓操作费
			Integer osr_btbrtop_fee = oSR.getOsr_btbrtop_fee();
			if (osr_btbrtop_fee == 1) {
				// 按SKU计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKU(entity);
					BigDecimal sku_number = new BigDecimal(list.size());
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
						BigDecimal osr_btbrtop_sku_skuprice = oSR.getOsr_btbrtop_sku_skuprice();
						BigDecimal osr_btbrtop_sku_billprice = oSR.getOsr_btbrtop_sku_billprice();
						BigDecimal a = sku_number.multiply(osr_btbrtop_sku_skuprice);
						BigDecimal b = new BigDecimal(out_num).multiply(osr_btbrtop_sku_billprice);
						B2BOutbound = B2BOutbound.add(a.add(b));
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
					B2BOutboundNum = B2BOutboundNum.add(sku_number);
				}
			} else if (osr_btbrtop_fee == 2) {
				// 按件数计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
					for (int j = 0; j < list.size(); j++) {
						BigDecimal sku_number = list.get(j).getOut_num();
						BigDecimal osr_btbobop_billprice = oSR.getOsr_btbrtop_bill_billprice();
						B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
						B2BOutboundNum = B2BOutboundNum.add(sku_number);
//						PerationfeeData map = list.get(j);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						if(cb.getId().equals("264")){
							data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
						}else{
							data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						}
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}

				}
			}
			// 5.B2C退换货入库费
			Integer osr_btcrtib_fee = oSR.getOsr_btcrtib_fee();
			if (osr_btcrtib_fee == 1) {
				// 按单计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					entity.setJob_type("'退换货入库'");
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//					for (int j = 0; j < list.size(); j++) {
//						PerationfeeData map = list.get(j);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
//					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'退换货入库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
					BigDecimal sku_number = new BigDecimal(list.size());
					BigDecimal osr_btcrtib_bill_billprice = oSR.getOsr_btcrtib_bill_billprice();
					B2CInbound = B2CInbound.add(osr_btcrtib_bill_billprice.multiply(new BigDecimal(list.size())));
					B2CInboundNum = B2CInboundNum.add(sku_number);
				}
			} else if (osr_btcrtib_fee == 2) {
				// 按件计算
				// 是否有阶梯
				Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
				if (osr_btcrtib_piece == 1) {
					// 无阶梯
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货入库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int k = 0; k < list.size(); k++) {
							BigDecimal sku_number = null != list.get(k).getIn_num() ? list.get(k).getIn_num() : new BigDecimal(0.00);
							BigDecimal osr_btcrtib_piece_pieceprice = null != oSR.getOsr_btcrtib_piece_pieceprice() ? oSR.getOsr_btcrtib_piece_pieceprice() : new BigDecimal(0.00);
							B2CInbound = B2CInbound.add(sku_number.multiply(osr_btcrtib_piece_pieceprice));
							B2CInboundNum = B2CInboundNum.add(sku_number);
//							PerationfeeData map = list.get(k);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货入库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							perationfeeDataService.update_settleflag(data);
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
			PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
			insertMain.setCreate_time(new Date());
			insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
			insertMain.setContract_id(Integer.valueOf(cb.getId()));
			insertMain.setData_date(DateUtil.getMonth(DateUtil.getYesterDay()));
			insertMain.setUpdate_time(new Date());
			insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
			insertMain.setBtc_qty(B2COutboundNum);
			insertMain.setBtc_qtyunit("");
			insertMain.setBtc_fee(B2COutbound);

			insertMain.setBtb_qty(B2BOutboundNum);
			insertMain.setBtb_qtyunit("");
			insertMain.setBtb_fee(B2BOutbound);

			insertMain.setReturn_qty(B2CInboundNum);
			insertMain.setReturn_qtyunit("");
			insertMain.setReturn_fee(B2CInbound);

			insertMain.setIb_qty(inboundSKUNum);
			insertMain.setIb_qtyunit("");
			insertMain.setIb_fee(inboundSKU);
			if (B2COutbound.compareTo(new BigDecimal(0.00)) != 0 || B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
					|| B2CInbound.compareTo(new BigDecimal(0.00)) != 0
					|| inboundSKU.compareTo(new BigDecimal(0.00)) != 0) {
				perationfeeDataDailySettlementService.save(insertMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Title: settlement 
	 * @Description: TODO(结算) 
	 * @param 设定文件 
	 * @return void
	 * 返回类型 @throws
	 */
	public void reSettlement(String con_id,String dateStr) {
		// 合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
		if (null != cbList) {
			// 遍历合同
				try {
					System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "操作费结算开始...");
					if (true) {
						// 打包费信息
						PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.getId()));
						// 操作费开关是否开
						if (null != pp && pp.getOperation().equals("0")) {
					
						} else {
							// 合同周期
							Map<String, Date> mapss = new HashMap<String, Date>();
							mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.getSettle_date()));
							// 主体
							// 添加店铺集合
							String contract_owner = cbList.getContract_owner();
							String contract_type = cbList.getContract_type();
							List<Store> storeList = new ArrayList<Store>();
							if (contract_type.equals("3")) {
								Store store = storeService.findByContractOwner(contract_owner);
								if (null != store) {
									storeList.add(store);
								} else {
									BalanceErrorLog bEL = new BalanceErrorLog();
									bEL.setContract_id(Integer.valueOf(cbList.getId()));
									bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
									bEL.setPro_result_info("合同ID[" + cbList.getId() + "],店铺不存在！");
									expressContractService.addBalanceErrorLog(bEL);
									return;
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
										expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()),BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID[" + cbList.getId()+ "]," + client.getClient_name() + "客户没有绑定店铺！",null, null, null, null, new Date()));
								        return;
									}
								}
							}
							// 获取操作费规则
							List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(cbList.getId());
							if (oSRList.size() >= 1) {
								OperationSettlementRule oSR = oSRList.get(0);
								// 结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]
								if (null != oSR.getOsr_setttle_method()) {
									int osr_setttle_method = oSR.getOsr_setttle_method();
									switch (osr_setttle_method) {
									case 0:
										// 固定费用结算
										reFixedCost(cbList, oSR, mapss, storeList,dateStr);
				
									case 1:
										// 按销售额百分比结算
 										reSalesPercentage(cbList, oSR, storeList,dateStr);
				
									case 2:
										// 按实际发生费用结算
										reActuallyHappen(cbList, oSR, storeList,dateStr);
								
									default:
										logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算异常!未知的结算规则!");
							
									}
								}
							}
						}
					}
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算完成!");
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算异常!");
				}
			
		}
	}
	
	public void reFixedCost(ContractBasicinfo cb, OperationSettlementRule oSR, Map<String, Date> mapps,List<Store> storeList,String data_data) throws NumberFormatException, ParseException {
		
		if (oSR.getOsr_fixed_type() == 1) {
			// 无阶梯
			//String data_data = DateUtil.getMonth(DateUtil.getYesterDay());
			Integer cbid = Integer.valueOf(cb.getId());
			try {
				PerationfeeDataDailySettlement PFD = new PerationfeeDataDailySettlement();
				PFD.setContract_id(cbid);
				PFD.setData_date(data_data);
				List<PerationfeeDataDailySettlement> pfdList = perationfeeDataDailySettlementService.findByEntity(PFD);
				// 该合同当月没有创建数据
				if (pfdList.size() == 0) {
					PFD.setCreate_time(new Date());
					PFD.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					PFD.setGd_qty(new BigDecimal(0.00));
					PFD.setGd_qtyunit("-");
					PFD.setGd_fee(new BigDecimal(oSR.getOsr_fixed_price()).multiply(new BigDecimal(oSR.getAllzk())));
					PFD.setGd_remark("元");
					if (PFD.getGd_fee().compareTo(new BigDecimal(0.00)) != 0) {
						perationfeeDataDailySettlementService.save(PFD);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (oSR.getOsr_fixed_type() == 2) {
			// 超过部分阶梯
			Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(data_data, Integer.parseInt(cb.getSettle_date()));
			List<PerationfeeData> list = new ArrayList<PerationfeeData>();
			BigDecimal a1 = new BigDecimal(0.00);
			for (int i = 0; i < storeList.size(); i++) {
				PerationfeeData entity = new PerationfeeData();
				entity.setStore_name(storeList.get(i).getStore_name());
				entity.setSettle_flag(0);
				entity.setStart_time(balance_cycle.get("balance_start_date").toString());
                entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
				list = perationfeeDataService.findByEntity(entity);
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getIn_num().compareTo(new BigDecimal(0)) != 0) {
						a1 = a1.add(list.get(j).getIn_num());
					} else {
						a1 = a1.add(list.get(j).getOut_num());
					}
				}
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
					PerationfeeDataDailySettlement insertData = new PerationfeeDataDailySettlement();
					insertData.setContract_id(oSR.getContract_id());
					insertData.setData_date(DateUtil.getMonth(DateUtil.getYesterDay()));
					insertData.setCreate_time(new Date());
					insertData.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setUpdate_time(new Date());
					insertData.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
					insertData.setGd_qty(a1);
					insertData.setGd_qtyunit("件");
					insertData.setGd_fee(new BigDecimal(map.get("msg").toString()).add(new BigDecimal(null!=oSR.getOsr_fixed_price()&&!oSR.getOsr_fixed_price().equals("")?oSR.getOsr_fixed_price():"0.00")));
					insertData.setGd_remark("元");
//					for (int i = 0; i < list.size(); i++) {
//						PerationfeeData d = list.get(i);
//						d.setSettle_flag(1);
//						// 修改原始数据状态
//						perationfeeDataService.update(d);
//					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
						data.setStore_name(list.get(0).getStore_name());
						data.setSettle_flag(0);
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
	                    data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						perationfeeDataService.update_settleflag(data);
					}
					if (insertData.getGd_fee().compareTo(new BigDecimal(0.00)) != 0) {
						perationfeeDataDailySettlementService.save(insertData);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, oSR.getContract_id(), BaseConst.PRO_RESULT_FLAG_ERROR,"合同ID[" + oSR.getContract_id() + "],插入数据异常！", null, null, null, null, new Date()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	public void reSalesPercentage(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList,String dateStr) {
		//判断是否为结算日
			try {
				List<WarehouseExpressData> list = new ArrayList<>();
				for (int i = 0; i < storeList.size(); i++) {
					WarehouseExpressData data = new WarehouseExpressData();
					//该店铺下的所有原始数据	dataList
					String yy =dateStr.split("-")[0];
					String mm = dateStr.split("-")[1];
					data.setTstart_time(yy);
					data.setTend_time(mm);
					data.setOperating_flag(0);
					data.setStore_name(storeList.get(i).getStore_name());
					list = warehouseExpressDataService.queryAll(data);
				}
				if (list.size() != 0) {
					BigDecimal sum = new BigDecimal(0.00);
					for (int i = 0; i < list.size(); i++) {
						BigDecimal a = list.get(i).getOrder_amount();
						BigDecimal b = new BigDecimal(0.00);
						BigDecimal c = new BigDecimal(0.00);
						if (null != oSR.getOsr_sales_percent() && !oSR.getOsr_sales_percent().equals("")) {
							b = new BigDecimal(oSR.getOsr_sales_percent());
						}
						if (null != oSR.getOsr_tax_point() && !oSR.getOsr_tax_point().equals("")) {
							c = new BigDecimal(oSR.getOsr_tax_point());
						}
						sum = sum.add(a.multiply(b).multiply(c));
						WarehouseExpressData entity = list.get(i);
						entity.setOperating_flag(1);
						if (a.compareTo(new BigDecimal(0.00)) != 0 || c.compareTo(new BigDecimal(0.00)) != 0 || b.compareTo(new BigDecimal(0.00)) != 0) {
							warehouseExpressDataService.update(entity);
						}
					}
					if (sum.compareTo(new BigDecimal(0.00)) != 0) {
						PerationfeeDataDailySettlement PFD = new PerationfeeDataDailySettlement();
						PFD.setCreate_time(new Date());
						PFD.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
						PFD.setUpdate_time(new Date());
						PFD.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
						PFD.setContract_id(Integer.valueOf(cb.getId()));
						PFD.setData_date(dateStr);
						PFD.setXse_qty(new BigDecimal(1.00));
						PFD.setXse_qtyunit("单");
						PFD.setXse_fee(sum.multiply(new BigDecimal(oSR.getAllzk())));
						PFD.setXse_remark("");
						if (PFD.getXse_fee().compareTo(new BigDecimal(0.00)) != 0) {
							perationfeeDataDailySettlementService.save(PFD);
						}
					}
				} else {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cb.getId()));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info(cb.getId() + "该合同在" + DateUtil.getCalendarToString(DateUtil.getYesterDay())+ " 没有查询到需要结算的数据,请查看是否已经生成结算数据!");
					expressContractService.addBalanceErrorLog(bEL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	
	}

	
	
	
	public void reSalesPercentage_recover(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList,String dateStr) {
		//判断是否为结算日
			try {
				for (int i= 0; i< storeList.size(); i++) {
					WarehouseExpressData data = new WarehouseExpressData();
					//该店铺下的所有原始数据	dataList
					//String yy = String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR));
					//String mm = String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1);
					data.setTstart_time(dateStr.split("-")[0]);
					data.setTend_time(dateStr.split("-")[1]);
					data.setOperating_flag(0);
					data.setStore_name(storeList.get(i).getStore_name());
					warehouseExpressDataService.update(data);
					
				}
				
				PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
				insertMain.setContract_id(Integer.valueOf(cb.getId()));
				insertMain.setData_date(dateStr);
				pddsmapper.delete_by_condition(insertMain);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/** 
	* @Title: actuallyHappen 
	* @Description: TODO(按实际发生费用结算) 
	* @param @param cb
	* @param @param oSR
	* @param @param storeList    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void reActuallyHappen(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList,String dateStr) {
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
			Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cb.getSettle_date()));
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
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
                    entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
					BigDecimal sum2 = new BigDecimal(0);
					BigDecimal sum2Num = new BigDecimal(0);
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						BigDecimal sku_number = map.getIn_num();
						BigDecimal osr_ibop_qtyprice = oSR.getOsr_ibop_qtyprice();
						sum2 = sku_number.multiply(osr_ibop_qtyprice);
						sum2Num = sku_number;
						inboundSKU = inboundSKU.add(sum2);
						inboundSKUNum = inboundSKUNum.add(sum2Num);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
	                    data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						perationfeeDataService.update_settleflag(data);
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
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
	                    entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						BigDecimal sum2 = new BigDecimal(0);
						BigDecimal sum2Num = new BigDecimal(0);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							BigDecimal osr_btcobop_numprice = oSR.getOsr_btcobop_numprice();
							BigDecimal osr_btcobop_numdc = oSR.getOsr_btcobop_numdc();
							sum2 = sku_number.multiply(osr_btcobop_numprice).multiply((osr_btcobop_numdc));
							sum2Num = sku_number;
							B2COutbound = B2COutbound.add(sum2);
							B2COutboundNum = B2COutboundNum.add(sum2Num);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
		                    data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
						}
					}
				} else if (osr_btcobop_numfee == 2) {
					// 超过部分阶梯
					String osr_btcobopnum_tableid = oSR.getOsr_btcobopnum_tableid();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBNTList(osr_btcobopnum_tableid);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
		                    data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
						}
						Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnt_interval", "bnt_discount",null, null);
						if (Integer.valueOf(map.get("status").toString()) == 360) {
							B2COutbound = new BigDecimal(map.get("msg").toString());
						}
					}
				} else if (osr_btcobop_numfee == 3) {
					// 总件数阶梯
					String osr_btcobopbill_tobtb_tableid = oSR.getOsr_btcobopbill_tobtb_tableid();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTOTList(osr_btcobopbill_tobtb_tableid);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int j = 0; j < list.size(); j++) {
							BigDecimal sku_number = list.get(j).getOut_num();
							B2COutboundNum = B2COutboundNum.add(sku_number);
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
							data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
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
				Integer osr_btcobop_numfees = null != oSR.getOsr_btcobop_numfees() && !oSR.getOsr_btcobop_numfees().equals("") ? Integer.valueOf(oSR.getOsr_btcobop_numfees()) : 0;
				if (osr_btcobop_numfees == 1) {
					// 无阶梯
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
							data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal osr_btcobop_numprices = oSR.getOsr_btcobop_numprices();
						BigDecimal osr_btcobop_numdcs = oSR.getOsr_btcobop_numdcs();
						B2COutbound = B2COutbound.add(new BigDecimal(list.size()).multiply(osr_btcobop_numprices).multiply((osr_btcobop_numdcs)));
						B2COutboundNum = B2COutboundNum.add(new BigDecimal(list.size()));
					}
				} else if (osr_btcobop_numfees == 2) {
					// 超过部分阶梯
					String osr_btcobopnum_tableids = oSR.getOsr_btcobopnum_tableids();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBBTLists(osr_btcobopnum_tableids);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setSettle_flag(0);
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
							data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal sku_number = new BigDecimal(list.size());
						B2COutboundNum = B2COutboundNum.add(sku_number);
					}
					Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "1", "bnts_interval", "bnts_discount", null, null);
					if (Integer.valueOf(map.get("status").toString()) == 360) {
						B2COutbound = new BigDecimal(map.get("msg").toString());
					}
				} else if (osr_btcobop_numfees == 3) {
					// 总件数阶梯
					// 超过部分阶梯
					String osr_btcobopbill_tobtb_tableidss = oSR.getOsr_btcobopnum_tableidss();
					List<Map<String, Object>> tbtList = operationSettlementRuleService.queryTBBTListss(osr_btcobopbill_tobtb_tableidss);
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货出库','销售出库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//						for (int j = 0; j < list.size(); j++) {
//							PerationfeeData map = list.get(j);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
//						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货出库','销售出库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
							data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
						}
						BigDecimal sku_number = new BigDecimal(list.size());
						B2COutboundNum = B2COutboundNum.add(sku_number);
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
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);

					PerationfeeData entity2 = new PerationfeeData();
					entity2.setJob_type("'销售出库'");
					entity2.setStore_name(storeList.get(i).getStore_name());
					entity2.setSettle_flag(0);
					entity2.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity2.setEnd_time(balance_cycle.get("balance_end_date").toString());
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list2 = perationfeeDataService.findByEntity(entity2);
					Integer osr_tobtb = oSR.getOsr_tobtb();
					for (int j = 0; j < list2.size(); j++) {
						BigDecimal out_num = list2.get(j).getOut_num();
						if (null!=osr_tobtb && osr_tobtb == 1) {
							// 转B2B
							BigDecimal osr_btcobopbill_tobtb_js = null != oSR.getOsr_btcobopbill_tobtb_js() && !oSR.getOsr_btcobopbill_tobtb_js().equals("") ? new BigDecimal(oSR.getOsr_btcobopbill_tobtb_js()) : new BigDecimal(0.00);
							if (out_num.compareTo(osr_btcobopbill_tobtb_js) >= 0) {
								list.add(list2.get(j));
							}
						}
					}
					for (int j = 0; j < list.size(); j++) {
						BigDecimal sku_number = list.get(j).getOut_num();
						BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
//						PerationfeeData map = list.get(j);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
						B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
						B2BOutboundNum = B2BOutboundNum.add(sku_number);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'销售出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
						data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						perationfeeDataService.update_settleflag(data);
					}
				}
			} else if (osr_btbobop_fee == 2) {
				// 按SKU类型收取
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKU(entity);
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
						BigDecimal osr_ibop_itemprice = oSR.getOsr_btbobop_itemprice();
						B2BOutbound = B2BOutbound.add(((new BigDecimal(out_num).multiply(osr_ibop_itemprice))));
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
						data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}
					BigDecimal sku_number = new BigDecimal(list.size());
					B2BOutboundNum = B2BOutboundNum.add(sku_number);
					BigDecimal osr_btbobop_skuprice = oSR.getOsr_btbobop_skuprice();
					B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_skuprice));
				}
			}
			// 4.B2B退仓操作费
			Integer osr_btbrtop_fee = oSR.getOsr_btbrtop_fee();
			if (osr_btbrtop_fee == 1) {
				// 按SKU计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKU(entity);
					BigDecimal sku_number = new BigDecimal(list.size());
					for (int j = 0; j < list.size(); j++) {
						PerationfeeData map = list.get(j);
						Integer out_num = Integer.valueOf(null != map.getOut_num() ? map.getOut_num().toString() : "0");
						BigDecimal osr_btbrtop_sku_skuprice = oSR.getOsr_btbrtop_sku_skuprice();
						BigDecimal osr_btbrtop_sku_billprice = oSR.getOsr_btbrtop_sku_billprice();
						BigDecimal a = sku_number.multiply(osr_btbrtop_sku_skuprice);
						BigDecimal b = new BigDecimal(out_num).multiply(osr_btbrtop_sku_billprice);
						B2BOutbound = B2BOutbound.add(a.add(b));
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
						data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						perationfeeDataService.update_settleflag(data);
					}
					B2BOutboundNum = B2BOutboundNum.add(sku_number);
				}
			} else if (osr_btbrtop_fee == 2) {
				// 按件数计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					if(cb.getId().equals("264")){
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
					}else{
						entity.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
					}
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
					for (int j = 0; j < list.size(); j++) {
						BigDecimal sku_number = list.get(j).getOut_num();
						BigDecimal osr_btbobop_billprice = oSR.getOsr_btbrtop_bill_billprice();
						B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
						B2BOutboundNum = B2BOutboundNum.add(sku_number);
//						PerationfeeData map = list.get(j);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						if(cb.getId().equals("264")){
							data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'");
						}else{
							data.setJob_type("'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'");
						}
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
						data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						perationfeeDataService.update_settleflag(data);
					}

				}
			}
			// 5.B2C退换货入库费
			Integer osr_btcrtib_fee = oSR.getOsr_btcrtib_fee();
			if (osr_btcrtib_fee == 1) {
				// 按单计算
				for (int i = 0; i < storeList.size(); i++) {
					PerationfeeData entity = new PerationfeeData();
					entity.setJob_type("'退换货入库'");
					entity.setStore_name(storeList.get(i).getStore_name());
					entity.setStart_time(balance_cycle.get("balance_start_date").toString());
					entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
					entity.setSettle_flag(0);
					// 查询该店铺下所有入库原始数据
					List<PerationfeeData> list = perationfeeDataService.findByEntityOrderNo(entity);
//					for (int j = 0; j < list.size(); j++) {
//						PerationfeeData map = list.get(j);
//						map.setSettle_flag(1);
//						perationfeeDataService.update(map);
//					}
					if(list.size()!=0){
						PerationfeeData data = new PerationfeeData();
						data.setJob_type("'退换货入库'");
						data.setStore_name(storeList.get(i).getStore_name());
						data.setSettle_flag(0);
						data.setStart_time(balance_cycle.get("balance_start_date").toString());
						data.setEnd_time(balance_cycle.get("balance_end_date").toString());
						perationfeeDataService.update_settleflag(data);
					}
					BigDecimal sku_number = new BigDecimal(list.size());
					BigDecimal osr_btcrtib_bill_billprice = oSR.getOsr_btcrtib_bill_billprice();
					B2CInbound = B2CInbound.add(osr_btcrtib_bill_billprice.multiply(new BigDecimal(list.size())));
					B2CInboundNum = B2CInboundNum.add(sku_number);
				}
			} else if (osr_btcrtib_fee == 2) {
				// 按件计算
				// 是否有阶梯
				Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
				if (osr_btcrtib_piece == 1) {
					// 无阶梯
					for (int i = 0; i < storeList.size(); i++) {
						PerationfeeData entity = new PerationfeeData();
						entity.setJob_type("'退换货入库'");
						entity.setStore_name(storeList.get(i).getStore_name());
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
						entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						entity.setSettle_flag(0);
						// 查询该店铺下所有入库原始数据
						List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
						for (int k = 0; k < list.size(); k++) {
							BigDecimal sku_number = null != list.get(k).getIn_num() ? list.get(k).getIn_num() : new BigDecimal(0.00);
							BigDecimal osr_btcrtib_piece_pieceprice = null != oSR.getOsr_btcrtib_piece_pieceprice() ? oSR.getOsr_btcrtib_piece_pieceprice() : new BigDecimal(0.00);
							B2CInbound = B2CInbound.add(sku_number.multiply(osr_btcrtib_piece_pieceprice));
							B2CInboundNum = B2CInboundNum.add(sku_number);
//							PerationfeeData map = list.get(k);
//							map.setSettle_flag(1);
//							perationfeeDataService.update(map);
						}
						if(list.size()!=0){
							PerationfeeData data = new PerationfeeData();
							data.setJob_type("'退换货入库'");
							data.setStore_name(storeList.get(i).getStore_name());
							data.setSettle_flag(0);
							data.setStart_time(balance_cycle.get("balance_start_date").toString());
							data.setEnd_time(balance_cycle.get("balance_end_date").toString());
							perationfeeDataService.update_settleflag(data);
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
			PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
			insertMain.setCreate_time(new Date());
			insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
			insertMain.setContract_id(Integer.valueOf(cb.getId()));
			insertMain.setData_date(dateStr);
			insertMain.setUpdate_time(new Date());
			insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
			insertMain.setBtc_qty(B2COutboundNum);
			insertMain.setBtc_qtyunit("");
			insertMain.setBtc_fee(B2COutbound);

			insertMain.setBtb_qty(B2BOutboundNum);
			insertMain.setBtb_qtyunit("");
			insertMain.setBtb_fee(B2BOutbound);

			insertMain.setReturn_qty(B2CInboundNum);
			insertMain.setReturn_qtyunit("");
			insertMain.setReturn_fee(B2CInbound);

			insertMain.setIb_qty(inboundSKUNum);
			insertMain.setIb_qtyunit("");
			insertMain.setIb_fee(inboundSKU);
			if (B2COutbound.compareTo(new BigDecimal(0.00)) != 0 || B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
					|| B2CInbound.compareTo(new BigDecimal(0.00)) != 0
					|| inboundSKU.compareTo(new BigDecimal(0.00)) != 0) {
				perationfeeDataDailySettlementService.save(insertMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reActuallyHappen_recover(ContractBasicinfo cb, OperationSettlementRule oSR, List<Store> storeList,String date_date) {
		try {
			PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
			insertMain.setContract_id(Integer.valueOf(cb.getId()));
			insertMain.setData_date(date_date);
			pddsmapper.delete_by_condition(insertMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	* @Title: summary 
	* @Description: TODO(汇总) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void reSummary(String con_id,String dateStr){
		//合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
		if(null!=cbList){
					try{
						logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+cbList.getContract_name()+"操作费汇总开始...");
						if(true){
							if(true){
								//获取操作费规则
								Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
								OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cbList.getId()).size()!=0?operationSettlementRuleService.findByCBID(cbList.getId()).get(0):null;
								if (oSR!=null) {
									PerationfeeDataSettlement dataSettlement = new PerationfeeDataSettlement();
									dataSettlement.setSettle_period(dateStr);
									dataSettlement.setContract_id(Integer.valueOf(cbList.getId()));
									List<PerationfeeDataSettlement> dataSettlements = perationfeeDataSettlementService.findByList(dataSettlement);
									//判断该合同结算月是否生成过数据 
									if(dataSettlements.size()==0){
										BigDecimal inboundSKU = new BigDecimal(0.00);
										BigDecimal inboundSKUNum = new BigDecimal(0.00);
										Integer osr_ibop_fee = oSR.getOsr_ibop_fee();
										//1.入库操作费
										if(null!=osr_ibop_fee && osr_ibop_fee==1){
											String contract_owner = cbList.getContract_owner();
											String contract_type = cbList.getContract_type();
											List<Store> storeList = new ArrayList<Store>();
											if(contract_type.equals("3")){
												Store store = storeService.findByContractOwner(contract_owner);
												if(null!=store){
													storeList.add(store);
												}else{
													BalanceErrorLog bEL = new BalanceErrorLog();
													bEL.setContract_id(Integer.valueOf(cbList.getId()));
													bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
													bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
													expressContractService.addBalanceErrorLog(bEL);
													return ;
												}
											}else if(contract_type.equals("4")){
												Client client = clientService.findByContractOwner(contract_owner);
												List<Store> sList = storeService.selectByClient(client.getId());
												if(null!=sList && sList.size()!=0){
													for (int k = 0; k < sList.size(); k++) {
														storeList.add(sList.get(k));
													}
												}else{
													expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
													return ;
												}
											}
											for (int j = 0; j < storeList.size(); j++) {
												// 查询该店铺下所有入库原始数据
												List<PerationfeeData> list = perationfeeDataService.findByEntityGroupBySKUYM("'VMI移库入库','代销入库','结算经销入库'",storeList.get(j).getStore_name(), String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR)), String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1));

												BigDecimal osr_ibop_skuprice = oSR.getOsr_ibop_skuprice();
												BigDecimal osr_ibop_itemprice = oSR.getOsr_ibop_itemprice();
												for (int k = 0; k < list.size(); k++) {
													PerationfeeData map = list.get(k);
													BigDecimal sku_count = new BigDecimal(map.getSku_count());
													BigDecimal in_num = new BigDecimal(null != map.getIn_num() ? map.getIn_num().toString() : "0");
													inboundSKU = inboundSKU.add((osr_ibop_itemprice.multiply(in_num)).add(osr_ibop_skuprice.multiply(sku_count)));
													inboundSKUNum = inboundSKUNum.add(sku_count);
												}
												if(list.size()!=0){
													PerationfeeData data = new PerationfeeData();
													data.setJob_type("'VMI移库入库','代销入库','结算经销入库'");
													data.setStore_name(storeList.get(j).getStore_name());
													data.setSettle_flag(0);
													data.setStart_time(balance_cycle.get("balance_start_date").toString());
													data.setEnd_time(balance_cycle.get("balance_end_date").toString());
													perationfeeDataService.update_settleflag(data);
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
										if(null!=oSR.getOsr_setttle_method()){
											int	 osr_setttle_method = oSR.getOsr_setttle_method();
											if(osr_setttle_method==0){
												
											}else if(osr_setttle_method==1){
												
											}else if(osr_setttle_method==2){

												//按实际发生费用结算
												
												//2.B2C出库操作费
												Integer osr_btcobop_fee = oSR.getOsr_btcobop_fee();
												if (null!=osr_btcobop_fee && osr_btcobop_fee==2) {
													
													List<PerationfeeDatas> list2 = null;
													String contract_owner = cbList.getContract_owner();
													String contract_type = cbList.getContract_type();
													List<Store> storeList = new ArrayList<Store>();
													if(contract_type.equals("3")){
														Store store = storeService.findByContractOwner(contract_owner);
														if(null!=store){
															storeList.add(store);
														}else{
															BalanceErrorLog bEL = new BalanceErrorLog();
															bEL.setContract_id(Integer.valueOf(cbList.getId()));
															bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
															bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
															expressContractService.addBalanceErrorLog(bEL);
															return ;
														}
													}else if(contract_type.equals("4")){
														Client client = clientService.findByContractOwner(contract_owner);
														List<Store> sList = storeService.selectByClient(client.getId());
														if(null!=sList && sList.size()!=0){
															for (int k = 0; k < sList.size(); k++) {
																storeList.add(sList.get(k));
															}
														}else{
															expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
															return ;
														}
													}
													BigDecimal B2COutboundNum = new BigDecimal(0.00);
													BigDecimal B2COutbound = new BigDecimal(0.00);
													BigDecimal B2BOutbound = new BigDecimal(0.00);
													BigDecimal B2BOutboundNum = new BigDecimal(0.00);
													for (int j = 0; j < storeList.size(); j++) {
														PerationfeeData entity = new PerationfeeData();
														entity.setJob_type("'销售出库','退换货出库'");
														entity.setStore_name(storeList.get(j).getStore_name());
														entity.setStart_time(balance_cycle.get("balance_start_date").toString().substring(0, 4));
														entity.setEnd_time(balance_cycle.get("balance_start_date").toString().substring(4, 7).replace("-0", "-").replace("-", ""));
														entity.setSettle_flag(0);
														//查询该店铺下所有入库原始数据
														list2 = perationfeeDataService.findByEntityGroupByOutNum(entity);
														//是否转B2B
														Integer osr_tobtb = oSR.getOsr_tobtb();
														List<PerationfeeDatas> list = new ArrayList<>();
														List<PerationfeeDatas> list3 = new ArrayList<>();
														for (int l = 0; l < list2.size(); l++) {
															BigDecimal out_num = list2.get(l).getOut_num();
															if (osr_tobtb == 1) {
																// 转B2B
																BigDecimal osr_btcobopbill_tobtb_js = null != oSR.getOsr_btcobopbill_tobtb_js() && !oSR.getOsr_btcobopbill_tobtb_js().equals("") ? new BigDecimal(oSR.getOsr_btcobopbill_tobtb_js()) : new BigDecimal(0.00);
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
																	BigDecimal sku_number = list3.get(o).getOut_num().multiply(new BigDecimal(String.valueOf(list3.get(o).getCtnum())));
																	BigDecimal osr_btbobop_billprice = oSR.getOsr_btbobop_billprice();
//																	PerationfeeDatas map1 = list3.get(o);
//																	map1.setSettle_flag(1);
//																	perationfeeDataService.updates(map1);
																	B2BOutbound = B2BOutbound.add(sku_number.multiply(osr_btbobop_billprice));
																	B2BOutboundNum = B2BOutboundNum.add(sku_number);
																}
																if(list3.size()!=0){
																	PerationfeeDatas data = new PerationfeeDatas();
																	data.setJob_type("'销售出库','退换货出库'");
																	data.setStore_name(storeList.get(j).getStore_name());
																	data.setSettle_flag(0);
																	perationfeeDataService.update_settleflags(data);
																}
															}
														}
														
														//维护的区间值
														List<Map<String, Object>> tbbtList = operationSettlementRuleService.queryTBBTList(String.valueOf(oSR.getOsr_btcobopbill_tableid()));
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
																	if(out_num.compareTo(new BigDecimal(startNum.toString()))==1  && out_num.compareTo(new BigDecimal(endNum.toString()))<0){
																		B2COutboundNum=B2COutboundNum.add(list.get(q).getOut_num());
																		BigDecimal a = new BigDecimal(d_prices);
																		BigDecimal b = new BigDecimal(j_prices);
																		BigDecimal c = list.get(q).getOut_num();
																		BigDecimal d = new BigDecimal(list.get(q).getCtnum());
																		BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
																		B2COutbound=B2COutbound.add(e);
																	}
																}else if(startSymbol==1 && endSymbol==1){
																	//[]
																	if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 && out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
																		B2COutboundNum=B2COutboundNum.add(list.get(q).getOut_num());
																		BigDecimal a = new BigDecimal(d_prices);
																		BigDecimal b = new BigDecimal(j_prices);
																		BigDecimal c = list.get(q).getOut_num();
																		BigDecimal d = new BigDecimal(list.get(q).getCtnum());
																		BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
																		B2COutbound=B2COutbound.add(e);
																	}
																}else if(startSymbol==0 && endSymbol==1){
																	//(]
																	if(out_num.compareTo(new BigDecimal(startNum.toString()))==1 && out_num.compareTo(new BigDecimal(endNum.toString()))<=0){
																		B2COutboundNum=B2COutboundNum.add(list.get(q).getOut_num());
																		BigDecimal a = new BigDecimal(d_prices);
																		BigDecimal b = new BigDecimal(j_prices);
																		BigDecimal c = list.get(q).getOut_num();
																		BigDecimal d = new BigDecimal(list.get(q).getCtnum());
																		BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
																		B2COutbound=B2COutbound.add(e);
																	}
																}else if(startSymbol==1 && endSymbol==0){
																	//[)
																	if(out_num.compareTo(new BigDecimal(startNum.toString()))>=0 && out_num.compareTo(new BigDecimal(endNum.toString()))<0){
																		B2COutboundNum=B2COutboundNum.add(list.get(q).getOut_num());
																		BigDecimal a = new BigDecimal(d_prices);
																		BigDecimal b = new BigDecimal(j_prices);
																		BigDecimal c = list.get(q).getOut_num();
																		BigDecimal d = new BigDecimal(list.get(q).getCtnum());
																		BigDecimal e = (((c.subtract(new BigDecimal(startNum))).multiply(b)).add(a)).multiply(d);
																		B2COutbound=B2COutbound.add(e);
																	}
																}
//																PerationfeeDatas maps = list.get(q);
//																maps.setSettle_flag(1);
//																perationfeeDataService.updates(maps);
															}
															if(list3.size()!=0){
																PerationfeeDatas data = new PerationfeeDatas();
																data.setJob_type("'销售出库','退换货出库'");
																data.setStore_name(storeList.get(j).getStore_name());
																data.setSettle_flag(0);
																perationfeeDataService.update_settleflags(data);
															}
														}
													}
													if(null!=oSR.getOsr_btcobopbill_discount_type() && !oSR.getOsr_btcobopbill_discount_type().equals("")){
														if(oSR.getOsr_btcobopbill_discount_type().equals("1")){
															//阶梯折扣按件结算
															List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBDList(oSR.getOsr_btcobopbill_discount_tableid());
															Map<String, Object> map = getPrice(taList, B2COutboundNum, "1", "btcbd_interval", "btcbd_price",B2COutbound,null);
															if (Integer.valueOf(map.get("status").toString())==360) {
																B2COutbound=new BigDecimal(map.get("msg").toString());
															}
														}
//														else if(oSR.getOsr_btcobopbill_discount_type().equals("2")){
//															//阶梯折扣按单结算
//															List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBD2List(oSR.getOsr_btcobopbill_discount_tableid2());
//															Map<String, Object> map = getPrice(taList, dNum, "2", "btcbd2_interval", "btcbd2_price",B2COutbound,null);
//															if (Integer.valueOf(map.get("status").toString())==360) {
//																B2COutbound=new BigDecimal(map.get("msg").toString());
//															}
//														}
													}
													PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
													insertMain.setCreate_time(new Date());
													insertMain.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
													insertMain.setContract_id(Integer.valueOf(cbList.getId()));
													insertMain.setData_date(DateUtil.getMonth(DateUtil.getYesterDay()));

													insertMain.setBtc_qty(B2COutboundNum);
													insertMain.setBtc_qtyunit("");
													insertMain.setBtc_fee(B2COutbound);

													insertMain.setBtb_qty(B2BOutboundNum);
													insertMain.setBtb_qtyunit("");
													insertMain.setBtb_fee(B2BOutbound);

													insertMain.setReturn_qty(new BigDecimal(0.00));
													insertMain.setReturn_qtyunit("");
													insertMain.setReturn_fee(new BigDecimal(0.00));

													insertMain.setIb_qty(inboundSKUNum);
													insertMain.setIb_qtyunit("");
													insertMain.setIb_fee(inboundSKU);
													if (inboundSKU.compareTo(new BigDecimal(0.00)) != 0
															||B2COutbound.compareTo(new BigDecimal(0.00)) != 0
															|| B2BOutbound.compareTo(new BigDecimal(0.00)) != 0
															) {
														perationfeeDataDailySettlementService.save(insertMain);
													}
												}
											}else{
												logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+cbList.getContract_name()+"操作费结算异常!未知的结算规则!");
											}
										
										}

										//退换货入库费按件数计算 是否有阶梯
										Integer osr_btcrtib_piece = oSR.getOsr_btcrtib_piece();
										if(null==osr_btcrtib_piece){
											qty3 = new BigDecimal(0.00);
											fee3 = new BigDecimal(0.00);
										}else if (osr_btcrtib_piece==1) {
											
										}else{
											String contract_owner = cbList.getContract_owner();
											String contract_type = cbList.getContract_type();
											List<Store> storeList = new ArrayList<Store>();
											if(contract_type.equals("3")){
												Store store = storeService.findByContractOwner(contract_owner);
												if(null!=store){
													storeList.add(store);
												}else{
													BalanceErrorLog bEL = new BalanceErrorLog();
													bEL.setContract_id(Integer.valueOf(cbList.getId()));
													bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
													bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
													expressContractService.addBalanceErrorLog(bEL);
													return;
												}
											}else if(contract_type.equals("4")){
												Client client = clientService.findByContractOwner(contract_owner);
												List<Store> sList = storeService.selectByClient(client.getId());
												if(null!=sList && sList.size()!=0){
													for (int k = 0; k < sList.size(); k++) {
														storeList.add(sList.get(k));
													}
												}else{
													expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
													return ;
												}
											}
											BigDecimal inb = new BigDecimal(0.00);
											BigDecimal oub = new BigDecimal(0.00);
											for (int k = 0; k < storeList.size(); k++) {
												PerationfeeData entity = new PerationfeeData();
												entity.setJob_type("'退换货入库'");
												entity.setStore_name(storeList.get(k).getStore_name());
												entity.setSettle_flag(0);
												//查询该店铺下所有入库原始数据
												List<PerationfeeData> list = perationfeeDataService.findByEntity(entity);
												for (int l = 0; l < list.size(); l++) {
													inb = inb.add(list.get(l).getIn_num());
												}
//												WarehouseExpressData data = new WarehouseExpressData();
//												Map<String, Date> mapss = new HashMap<String,Date>();
//												mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.get(i).getSettle_date()));
//												data.setStart_time(DateUtil.format(mapss.get("start")));
//												data.setEnd_time(DateUtil.format(mapss.get("end")));
////												data.setOperating_flag(0);
//												data.setStore_name(storeList.get(k).getStore_name());
//												List<WarehouseExpressData> weList = warehouseExpressDataService.queryAll(data);
												PerationfeeData entitys = new PerationfeeData();
												entitys.setJob_type("'销售出库','退换货出库'");
												entitys.setStore_name(storeList.get(k).getStore_name());
												//查询该店铺下所有入库原始数据
												List<PerationfeeData> lists = perationfeeDataService.findByEntity(entitys);
												for (int m = 0; m < lists.size(); m++) {
													oub = oub.add(lists.get(m).getOut_num());
												}
												if (oub.compareTo(new BigDecimal(0.00))==0) {
													qty3=qty3.add(new BigDecimal(0.00));
													fee3=fee3.add(new BigDecimal(0.00));
												}else{
													BigDecimal div = inb.divide(oub,2,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
													String osr_btcrti_tableid = oSR.getOsr_btcrti_tableid();
													if(null!=osr_btcrti_tableid && !osr_btcrti_tableid.equals("")){
														List<Map<String, Object>> yjt_list = operationSettlementRuleService.queryTBTList(osr_btcrti_tableid);	
														Map<String, Object> map = getPrice(yjt_list, div, "2", "btcrti_interval", "btcrt_price",inb,null);
														if (Integer.valueOf(map.get("status").toString())==360) {
															qty3=qty3.add(new BigDecimal(0.00));
															fee3=fee3.add(new BigDecimal(map.get("msg").toString()));
														}
													}
												}
											}
										}
										//汇总累加
										PerationfeeDataDailySettlement pfd = new PerationfeeDataDailySettlement();
										pfd.setData_date(dateStr);
										pfd.setContract_id(Integer.valueOf(cbList.getId()));
										List<PerationfeeDataDailySettlement> pfdList = perationfeeDataDailySettlementService.findByEntity(pfd);
										for (int j = 0; j < pfdList.size(); j++) {
											PerationfeeDataDailySettlement pfds = pfdList.get(j);
											
											qty1=qty1.add(null!=pfds.getBtc_qty()?pfds.getBtc_qty():new BigDecimal(0.00));
											fee1=fee1.add(null!=pfds.getBtc_fee()?pfds.getBtc_fee():new BigDecimal(0.00));
		
											qty2=qty2.add(null!=pfds.getBtb_qty()?pfds.getBtb_qty():new BigDecimal(0.00));
											fee2=fee2.add(null!=pfds.getBtb_fee()?pfds.getBtb_fee():new BigDecimal(0.00));
		
											qty3=qty3.add(null!=pfds.getReturn_qty()?pfds.getReturn_qty():new BigDecimal(0.00));
											fee3=fee3.add(null!=pfds.getReturn_fee()?pfds.getReturn_fee():new BigDecimal(0.00));
											
											qty4=qty4.add(null!=pfds.getIb_qty()?pfds.getIb_qty():new BigDecimal(0.00));
											fee4=fee4.add(null!=pfds.getIb_fee()?pfds.getIb_fee():new BigDecimal(0.00));
											
											qty5=qty5.add(null!=pfds.getGd_qty()?pfds.getGd_qty():new BigDecimal(0.00));
											fee5=fee5.add(null!=pfds.getGd_fee()?pfds.getGd_fee():new BigDecimal(0.00));
											
											qty6=qty6.add(null!=pfds.getXse_qty()?pfds.getXse_qty():new BigDecimal(0.00));
											fee6=fee6.add(null!=pfds.getXse_fee()?pfds.getXse_fee():new BigDecimal(0.00));
										}
										PerationfeeDataSettlement entity = new PerationfeeDataSettlement();
										entity.setCreate_time(new Date());
										entity.setCreate_user("1");
										entity.setContract_id(Integer.valueOf(cbList.getId()));
										entity.setSettle_period(DateUtil.getMonth(DateUtil.getYesterDay()));
										entity.setBtc_qty(qty1);
										entity.setBtc_fee(fee1);
										entity.setBtb_qty(qty2);
										entity.setBtb_fee(fee2);
										entity.setReturn_qty(qty3);
										entity.setReturn_fee(fee3);
										entity.setIb_qty(qty4.add(inboundSKUNum));
										entity.setIb_fee(fee4.add(inboundSKU));
										entity.setXse_qty(qty6);
										entity.setXse_fee(fee6);
										entity.setGd_qty(qty5);
										entity.setGd_fee(fee5);
										if(fee1.compareTo(new BigDecimal(0.00))!=0 ||
												fee2.compareTo(new BigDecimal(0.00))!=0 ||
												fee3.compareTo(new BigDecimal(0.00))!=0 ||
												fee4.compareTo(new BigDecimal(0.00))!=0 ||
												fee5.compareTo(new BigDecimal(0.00))!=0 ||
												fee6.compareTo(new BigDecimal(0.00))!=0){
											perationfeeDataSettlementService.save(entity);
										}
										logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+cbList.getContract_name()+"操作费汇总完成!");
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+cbList.getContract_name()+"操作费汇总异常!");
					}
			}
		}

	
	public void reCoverData(String con_id, String dateStr) {
		// TODO Auto-generated method stub
		//根据逻辑恢复数据
		//根据 合同id 获得合同下的店铺
		// 合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
		if (null != cbList) {
			// 遍历合同
				try {
					System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "操作费结算开始...");
					if (true) {
						// 打包费信息
						PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.getId()));
						// 操作费开关是否开
						if (null != pp && pp.getOperation().equals("0")) {
					
						} else {
							// 合同周期
							Map<String, Date> mapss = new HashMap<String, Date>();
							mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.getSettle_date()));
							// 主体
							// 添加店铺集合
							String contract_owner = cbList.getContract_owner();
							String contract_type = cbList.getContract_type();
							List<Store> storeList = new ArrayList<Store>();
							if (contract_type.equals("3")) {
								Store store = storeService.findByContractOwner(contract_owner);
								if (null != store) {
									storeList.add(store);
								} else {
									BalanceErrorLog bEL = new BalanceErrorLog();
									bEL.setContract_id(Integer.valueOf(cbList.getId()));
									bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
									bEL.setPro_result_info("合同ID[" + cbList.getId() + "],店铺不存在！");
									expressContractService.addBalanceErrorLog(bEL);
									return;
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
										expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()),BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID[" + cbList.getId()+ "]," + client.getClient_name() + "客户没有绑定店铺！",null, null, null, null, new Date()));
								        return;
									}
								}
							}
							// 获取操作费规则
							List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(cbList.getId());
							if (oSRList.size() >= 1) {
								OperationSettlementRule oSR = oSRList.get(0);
								// 结算方式[1固定费用结算2按销售额百分比结算3.按实际发生费用结算]
								if (null != oSR.getOsr_setttle_method()) {
									int osr_setttle_method = oSR.getOsr_setttle_method();
									switch (osr_setttle_method) {
									case 0:
										// 固定费用结算
										reFixedCost_recover(cbList, oSR, mapss, storeList,dateStr);
				
									case 1:
										// 按销售额百分比结算
 										reSalesPercentage_recover(cbList, oSR, storeList,dateStr);
				
									case 2:
										// 按实际发生费用结算
										reActuallyHappen_recover(cbList, oSR, storeList,dateStr);
								
									default:
										logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算异常!未知的结算规则!");
							
									}
								}
							}
							PerationfeeDataSettlement entity = new PerationfeeDataSettlement();
							entity.setContract_id(Integer.parseInt(con_id));
							entity.setSettle_period(dateStr);
							pdsmapper.delete_by_condition(entity);
						}
					}
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算完成!");
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name() + "操作费结算异常!");
				}
			
		}
	
	}

	/** 
	* @Title: new_summary 
	* @Description: TODO(重新汇总) 
	* @param @param cb
	* @param @param ym
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public Boolean new_summary(ContractBasicinfo cb, String ym) throws Exception {
		logger.debug("时间：［"+ DateUtil.formatS(new Date())+ "］ "+ cb.getContract_name()+ "操作费汇总开始...");
		//获取操作费规则
		OperationSettlementRule oSR= operationSettlementRuleService.findByCBID(cb.getId()).size()!=0? operationSettlementRuleService.findByCBID(cb.getId()).get(0): null;
		if (CommonUtils.checkExistOrNot(oSR)) {
			
		}
		logger.debug("时间：［"+DateUtil.formatS(new Date())+"］ "+ cb.getContract_name()+"操作费汇总完成!");
 		return true;
	}
	
	public void reFixedCost_recover(ContractBasicinfo cb, OperationSettlementRule oSR, Map<String, Date> mapps,List<Store> storeList,String data_data) throws Exception {	
		if (oSR.getOsr_fixed_type() == 2) {
    		// 超过部分阶梯
			Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(data_data, Integer.parseInt(cb.getSettle_date()));
			for (int i = 0; i < storeList.size(); i++) {
				PerationfeeData entity = new PerationfeeData();
				entity.setStore_name(storeList.get(i).getStore_name());
				entity.setStart_time(balance_cycle.get("balance_start_date").toString());
                entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
			    pdmapper.update_settleflag_zero(entity);
			    
			}
			
		}
		PerationfeeDataDailySettlement insertMain = new PerationfeeDataDailySettlement();
		insertMain.setContract_id(Integer.valueOf(cb.getId()));
		insertMain.setData_date(data_data);
		pddsmapper.delete_by_condition(insertMain);
	 
	}
	
	public static void main (String args[]){
		String data_data = DateUtil.getMonth(DateUtil.getYesterDay());
		System.out.println(data_data);
	}


}
