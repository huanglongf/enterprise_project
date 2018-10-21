package com.bt.lmis.summary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.AddservicefeeBilldataCollectMapper;
import com.bt.lmis.dao.AddservicefeeDataMapper;
import com.bt.lmis.dao.AddservicefeeDataSettlementMapper;
import com.bt.lmis.model.AddservicefeeBilldataCollect;
import com.bt.lmis.model.AddservicefeeData;
import com.bt.lmis.model.AddservicefeeDataSettlement;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.ValueAddedServiceDetail;
import com.bt.lmis.service.AddservicefeeBilldataCollectService;
import com.bt.lmis.service.AddservicefeeDataService;
import com.bt.lmis.service.AddservicefeeDataSettlementService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.ValueAddedServiceDetailService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;

/** 
* @ClassName: ValueAddedBalance 
* @Description: TODO(增值服务) 
* @author Yuriy.Jiang
* @date 2016年8月31日 上午10:05:36 
*  
*/
@SuppressWarnings("unchecked")
@Service
public class ValueAddedBalance {
	
	//操作费结算规则表服务类
	OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService = (OperationSettlementRuleService<OperationSettlementRule>)SpringUtils.getBean("operationSettlementRuleServiceImpl"); 
	//合同服务类
	ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService = (ContractBasicinfoService<ContractBasicinfo>)SpringUtils.getBean("contractBasicinfoServiceImpl");
	//店铺服务类
	StoreService<Store> storeService = (StoreService<Store>)SpringUtils.getBean("storeServiceImpl");
	//快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>)SpringUtils.getBean("expressContractServiceImpl");
	//客户服务类
	ClientService<Client> clientService = (ClientService<Client>)SpringUtils.getBean("clientServiceImpl");
	//增值服务费原始数据服务类
	AddservicefeeDataService<AddservicefeeData> addservicefeeDataService = (AddservicefeeDataService<AddservicefeeData>)SpringUtils.getBean("addservicefeeDataServiceImpl");
	//增值服务费结算服务类
	AddservicefeeDataSettlementService<AddservicefeeDataSettlement> addservicefeeDataSettlementService = (AddservicefeeDataSettlementService<AddservicefeeDataSettlement>)SpringUtils.getBean("addservicefeeDataSettlementServiceImpl");
	//增值服务费汇总服务类
	AddservicefeeBilldataCollectService<AddservicefeeBilldataCollect> addservicefeeBilldataCollectService = (AddservicefeeBilldataCollectService<AddservicefeeBilldataCollect>)SpringUtils.getBean("addservicefeeBilldataCollectServiceImpl");
	ValueAddedServiceDetailService<ValueAddedServiceDetail> valueAddedServiceDetailService = (ValueAddedServiceDetailService<ValueAddedServiceDetail>)SpringUtils.getBean("valueAddedServiceDetailServiceImpl");
	@Autowired
	private AddservicefeeBilldataCollectMapper<T> abcMapper;
	@Autowired
	private AddservicefeeDataSettlementMapper<T> adsMapper;
	@Autowired
	private AddservicefeeDataMapper<T> adMapper;
	/** 
	* @Title: settlement 
	* @Description: TODO(增值服务费结算) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void settlement(){
		//合同信息
		List<ContractBasicinfo> cbList = contractBasicinfoService.find_by_cb();
		if(null!=cbList && cbList.size()!=0){
			//遍历合同
			for (int i = 0; i < cbList.size(); i++) {
				try {
						if(DateUtil.isTime(cbList.get(i).getContract_start(), cbList.get(i).getContract_end(), DateUtil.formatDate(new Date()))){
							//合同周期
							//Map<String, Date> mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.get(i).getSettle_date()));
							//主体 
							//添加店铺集合
							System.out.println(cbList.get(i).getContract_name()+"合同开始结算");
							String contract_owner = cbList.get(i).getContract_owner();
							String contract_type = cbList.get(i).getContract_type();
							List<Store> storeList = new ArrayList<Store>();
							if(contract_type.equals("3")){
								Store store = storeService.findByContractOwner(contract_owner);
								if(null!=store){
									storeList.add(store);
								}else{
									BalanceErrorLog bEL = new BalanceErrorLog();
									bEL.setContract_id(Integer.valueOf(cbList.get(i).getId()));
									bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
									bEL.setPro_result_info("合同ID["+cbList.get(i).getId()+"],店铺不存在！");
									expressContractService.addBalanceErrorLog(bEL);
									continue ;
								}
							}else if(contract_type.equals("4")){
								Client client = clientService.findByContractOwner(contract_owner);
								List<Store> sList = storeService.selectByClient(client.getId());
								if(null!=sList && sList.size()!=0){
									for (int j = 0; j < sList.size(); j++) {
										storeList.add(sList.get(j));
									}
								}else{
									expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.get(i).getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.get(i).getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
									continue ;
								}
							}
							//获取操作费规则
							OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cbList.get(i).getId()).size()!=0?operationSettlementRuleService.findByCBID(cbList.get(i).getId()).get(0):null;
							//增值服务费开关
							if(null!=oSR){
								Integer osr_addservice_fee = oSR.getOsr_addservice_fee();
								if(osr_addservice_fee==1){
									for (int k = 0; k < storeList.size(); k++) {
										Store store = storeList.get(k);
										AddservicefeeData afd = new AddservicefeeData();
										afd.setStore_name(store.getStore_name());
										afd.setCost_code(store.getCost_center());
										afd.setSettle_flag(0);
										List<AddservicefeeData> afdList = addservicefeeDataService.findByAll(afd);
										for (int j = 0; j < afdList.size(); j++) {
											AddservicefeeDataSettlement entity = new AddservicefeeDataSettlement();
											//QC费用
											Integer osr_qc_fee = oSR.getOsr_qc_fee();
											BigDecimal price = new BigDecimal(0.00);
											String unit = "";
											if(osr_qc_fee==1 && afdList.get(j).getAddservice_name().equals("QC费用")){
												price = oSR.getOsr_qc_pieceprice();
												unit = oSR.getOsr_qc_pieceprice_unit();
											}
											//放赠品费用
											Integer osr_gift_fee = oSR.getOsr_gift_fee();
											if(osr_gift_fee==1 && afdList.get(j).getAddservice_name().equals("放赠品费用")){
												price = oSR.getOsr_gift_billprice();
												unit = oSR.getOsr_gift_billprice_unit();
											}
											//测量尺寸
											Integer osr_mesurement = oSR.getOsr_mesurement();
											if(osr_mesurement==1 && afdList.get(j).getAddservice_name().equals("测量尺寸")){
												price = oSR.getOsr_mesurement_price();
												unit = oSR.getOsr_mesurement_price_unit();
											}
											//代贴防伪码
											Integer osr_security_code = oSR.getOsr_security_code();
											if(osr_security_code==1 && afdList.get(j).getAddservice_name().equals("代贴防伪码")){
												price = oSR.getOsr_security_code_price();
												unit = oSR.getOsr_security_code_price_unit();
											}
											//代贴条码
											Integer osr_itemnum = oSR.getOsr_itemnum();
											if(osr_itemnum==1 && afdList.get(j).getAddservice_name().equals("代贴条码")){
												price = oSR.getOsr_itemnum_price();
												unit = oSR.getOsr_itemnum_price_unit();
											}
											//吊牌拍照
											Integer osr_drop_photo = oSR.getOsr_drop_photo();
											if(osr_drop_photo==1 && afdList.get(j).getAddservice_name().equals("吊牌拍照")){
												price = oSR.getOsr_drop_photo_price();
												unit = oSR.getOsr_drop_photo_price_unit();
											}
											//短信服务费
											Integer osr_message = oSR.getOsr_message();
											if(osr_message==1 && afdList.get(j).getAddservice_name().equals("短信服务费")){
												price = oSR.getOsr_message_price();
												unit = oSR.getOsr_message_price_unit();
											}
											//额外税费及管理费税
											Integer osr_extra_fee = oSR.getOsr_extra_fee();
											if(osr_extra_fee==1 && afdList.get(j).getAddservice_name().equals("额外税费及管理费税")){
												price = oSR.getOsr_extra_fee_price();
												unit = oSR.getOsr_extra_fee_price_unit();
											}
											//更改包装
											Integer osr_change_package = oSR.getOsr_change_package();
											if(osr_change_package==1 && afdList.get(j).getAddservice_name().equals("更改包装")){
												price = oSR.getOsr_change_package_price();
												unit = oSR.getOsr_change_package_price_unit();
											}
											//非工作时间作业
											Integer osr_notworkingtime = oSR.getOsr_notworkingtime();
											if(osr_notworkingtime==1 && afdList.get(j).getAddservice_name().equals("非工作时间作业")){
												price = oSR.getOsr_notworkingtime_price();
												unit = oSR.getOsr_notworkingtime_price_unit();
											}
											//货票同行
											Integer osr_waybillpeer = oSR.getOsr_waybillpeer();
											if(osr_waybillpeer==1 && afdList.get(j).getAddservice_name().equals("货票同行")){
												price = oSR.getOsr_waybillpeer_price();
												unit = oSR.getOsr_waybillpeer_price_unit();
											}
											//紧急收货
											Integer osr_emergency_receipt = oSR.getOsr_emergency_receipt();
											if(osr_emergency_receipt==1 && afdList.get(j).getAddservice_name().equals("紧急收货")){
												price = oSR.getOsr_emergency_receipt_price();
												unit = oSR.getOsr_emergency_receipt_price_unit();
											}
											//取消订单拦截
											Integer osr_cancelorder = oSR.getOsr_cancelorder();
											if(osr_cancelorder==1 && afdList.get(j).getAddservice_name().equals("取消订单拦截")){
												price = oSR.getOsr_cancelorder_price();
												unit = oSR.getOsr_cancelorder_price_unit();
											}
											//条码制作人工费
											Integer osr_makebarfee = oSR.getOsr_makebarfee();
											if(osr_makebarfee==1 && afdList.get(j).getAddservice_name().equals("条码制作人工费")){
												price = oSR.getOsr_makebarfee_price();
												unit = oSR.getOsr_makebarfee_price_unit();
											}
											//卸货费
											Integer osr_landfee = oSR.getOsr_landfee();
											if(osr_landfee==1 && afdList.get(j).getAddservice_name().equals("卸货费")){
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
											entity.setPhysical_warehouse(afdList.get(j).getPhysical_warehouse());
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
											addservicefeeDataService.update_settleFlag(afdList.get(j).getId().toString());
											//新增增值服务费
											addservicefeeDataSettlementService.save(entity);
										}
									}
								}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description: TODO(增值服务费汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午2:47:10
	 */
	public Boolean summary(ContractBasicinfo cb, String ym) throws Exception {
		if(cb.getId().equals("288")){
			System.out.println(cb);
			
		}
		String contract_owner= cb.getContract_owner();
		String contract_type= cb.getContract_type();
		List<Store> storeList= null;
		if(contract_type.equals("3")){
			Store store= storeService.findByContractOwner(contract_owner);
			if(CommonUtils.checkExistOrNot(store)){
				storeList= new ArrayList<Store>();
				storeList.add(store);
				
			}else{
				BalanceErrorLog bEL= new BalanceErrorLog();
				bEL.setContract_id(Integer.valueOf(cb.getId()));
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID["+ cb.getId()+ "],店铺不存在！");
				expressContractService.addBalanceErrorLog(bEL);
				return false;
				
			}
			
		}else if(contract_type.equals("4")){
			Client client= clientService.findByContractOwner(contract_owner);
			List<Store> temp = storeService.selectByClient(client.getId());
			if(CommonUtils.checkExistOrNot(temp)){
				storeList= temp;
					
			}else{
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+ cb.getId()+ "],"+ client.getClient_name()+ "客户没有绑定店铺！", null, null, null, null, new Date()));
				return false;
				
			}
			
		}
		//判断是否为结算日
		Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(ym, Integer.parseInt(cb.getSettle_date()));
		AddservicefeeData afd= new AddservicefeeData();
		afd.setStart_time(balance_cycle.get("balance_start_date").toString());
		afd.setEnd_time(balance_cycle.get("balance_end_date").toString());
		List<Map<String, String>> ASNList= addservicefeeDataService.findGroupByServiceName(afd);
		for (int k= 0; k< ASNList.size(); k++) {
			BigDecimal sumPrice= new BigDecimal(0.00);
			BigDecimal sumQty= new BigDecimal(0);
			for (int l= 0; l< storeList.size(); l++) {
				AddservicefeeDataSettlement afs = new AddservicefeeDataSettlement();
				String yy= String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR));
				String mm= String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1);
				afs.setStarttime(yy);
				afs.setEndtime(mm);
				afs.setAddservice_name(ASNList.get(k).get("addservice_name"));
				afs.setStore_name(storeList.get(l).getStore_name());
				List<AddservicefeeDataSettlement> afsList = addservicefeeDataSettlementService.findByAll(afs);
				for (int j = 0; j < afsList.size(); j++) {
					AddservicefeeDataSettlement a = afsList.get(j);
					sumPrice = sumPrice.add(a.getAmount());
					sumQty = sumQty.add(a.getQty());
					
				}
				
			}
			//获取操作费规则
			OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cb.getId()).size()!=0?operationSettlementRuleService.findByCBID(cb.getId()).get(0):null;
			if(CommonUtils.checkExistOrNot(oSR)){
				try {
					AddservicefeeBilldataCollect entity = new AddservicefeeBilldataCollect();
					entity.setCreate_time(new Date());
					entity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
					entity.setContract_id(oSR.getContract_id().toString());
					entity.setQty(sumQty.intValue());
					entity.setAmount(sumPrice);
					entity.setAddservice_name(ASNList.get(k).get("addservice_name"));
					entity.setTime(ym);
					//插入汇总记录
					AddservicefeeBilldataCollect afb = new AddservicefeeBilldataCollect();
					afb.setContract_id(oSR.getContract_id().toString());
					afb.setTime(ym);
					afb.setAddservice_name(ASNList.get(k).get("addservice_name"));
					List<AddservicefeeBilldataCollect> afbList = addservicefeeBilldataCollectService.findByList(afb);
					if(afbList.size()==0 && sumPrice.compareTo(new BigDecimal(0))!=0){
						addservicefeeBilldataCollectService.save(entity);
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					try {
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(oSR.getContract_id()) , BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+oSR.getContract_id()+"],插入数据异常！", null, null, null, null, new Date()));
						
					} catch (Exception e1) {
						e1.printStackTrace();
						
					}
				}
//				String osr_zzfwf_status = oSR.getOsr_zzfwf_status();
//				String osr_zzfwf_detail = oSR.getOsr_zzfwf_detail();
				// 阶梯
//				if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("0")){
//									
//				}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("1")){
//									
//				}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("2")){
//					List<Map<String, Object>> list = valueAddedServiceDetailService.findByCBID(osr_zzfwf_detail);
//					countALLData(list, "vasd_section", "vasd_value", sumPrice, oSR,sumPrice,sumQty,ASNList.get(k).get("addservice_name"));
//				}
			}
			
		}
		return true;
			
	}
	
	public void Resettlement(String con_id,String dateStr){
		//合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
				try {
   						    //Map<String, Date> mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cbList.get(i).getSettle_date()));
							//主体 
							//添加店铺集合
							System.out.println(cbList.getContract_name()+"合同开始重新结算");
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
									for (int j = 0; j < sList.size(); j++) {
										storeList.add(sList.get(j));
									}
								}else{
									expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
									return ;
								}
							}
							//获取操作费规则
							OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cbList.getId()).size()!=0?operationSettlementRuleService.findByCBID(cbList.getId()).get(0):null;
							//增值服务费开关
							if(null!=oSR){
								Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
								Integer osr_addservice_fee = oSR.getOsr_addservice_fee();
								if(osr_addservice_fee==1){
									for (int k = 0; k < storeList.size(); k++) {
										Store store = storeList.get(k);
										AddservicefeeData afd = new AddservicefeeData();
										afd.setStore_name(store.getStore_name());
										afd.setCost_code(store.getCost_center());
										afd.setSettle_flag(0);
										afd.setStart_time(balance_cycle.get("balance_start_date").toString());
						                afd.setEnd_time(balance_cycle.get("balance_end_date").toString());
										List<AddservicefeeData> afdList = addservicefeeDataService.findByAll(afd);
										for (int j = 0; j < afdList.size(); j++) {
											AddservicefeeDataSettlement entity = new AddservicefeeDataSettlement();
											//QC费用
											Integer osr_qc_fee = oSR.getOsr_qc_fee();
											BigDecimal price = new BigDecimal(0.00);
											String unit = "";
											if(osr_qc_fee==1 && afdList.get(j).getAddservice_name().equals("QC费用")){
												price = oSR.getOsr_qc_pieceprice();
												unit = oSR.getOsr_qc_pieceprice_unit();
											}
											//放赠品费用
											Integer osr_gift_fee = oSR.getOsr_gift_fee();
											if(osr_gift_fee==1 && afdList.get(j).getAddservice_name().equals("放赠品费用")){
												price = oSR.getOsr_gift_billprice();
												unit = oSR.getOsr_gift_billprice_unit();
											}
											//测量尺寸
											Integer osr_mesurement = oSR.getOsr_mesurement();
											if(osr_mesurement==1 && afdList.get(j).getAddservice_name().equals("测量尺寸")){
												price = oSR.getOsr_mesurement_price();
												unit = oSR.getOsr_mesurement_price_unit();
											}
											//代贴防伪码
											Integer osr_security_code = oSR.getOsr_security_code();
											if(osr_security_code==1 && afdList.get(j).getAddservice_name().equals("代贴防伪码")){
												price = oSR.getOsr_security_code_price();
												unit = oSR.getOsr_security_code_price_unit();
											}
											//代贴条码
											Integer osr_itemnum = oSR.getOsr_itemnum();
											if(osr_itemnum==1 && afdList.get(j).getAddservice_name().equals("代贴条码")){
												price = oSR.getOsr_itemnum_price();
												unit = oSR.getOsr_itemnum_price_unit();
											}
											//吊牌拍照
											Integer osr_drop_photo = oSR.getOsr_drop_photo();
											if(osr_drop_photo==1 && afdList.get(j).getAddservice_name().equals("吊牌拍照")){
												price = oSR.getOsr_drop_photo_price();
												unit = oSR.getOsr_drop_photo_price_unit();
											}
											//短信服务费
											Integer osr_message = oSR.getOsr_message();
											if(osr_message==1 && afdList.get(j).getAddservice_name().equals("短信服务费")){
												price = oSR.getOsr_message_price();
												unit = oSR.getOsr_message_price_unit();
											}
											//额外税费及管理费税
											Integer osr_extra_fee = oSR.getOsr_extra_fee();
											if(osr_extra_fee==1 && afdList.get(j).getAddservice_name().equals("额外税费及管理费税")){
												price = oSR.getOsr_extra_fee_price();
												unit = oSR.getOsr_extra_fee_price_unit();
											}
											//更改包装
											Integer osr_change_package = oSR.getOsr_change_package();
											if(osr_change_package==1 && afdList.get(j).getAddservice_name().equals("更改包装")){
												price = oSR.getOsr_change_package_price();
												unit = oSR.getOsr_change_package_price_unit();
											}
											//非工作时间作业
											Integer osr_notworkingtime = oSR.getOsr_notworkingtime();
											if(osr_notworkingtime==1 && afdList.get(j).getAddservice_name().equals("非工作时间作业")){
												price = oSR.getOsr_notworkingtime_price();
												unit = oSR.getOsr_notworkingtime_price_unit();
											}
											//货票同行
											Integer osr_waybillpeer = oSR.getOsr_waybillpeer();
											if(osr_waybillpeer==1 && afdList.get(j).getAddservice_name().equals("货票同行")){
												price = oSR.getOsr_waybillpeer_price();
												unit = oSR.getOsr_waybillpeer_price_unit();
											}
											//紧急收货
											Integer osr_emergency_receipt = oSR.getOsr_emergency_receipt();
											if(osr_emergency_receipt==1 && afdList.get(j).getAddservice_name().equals("紧急收货")){
												price = oSR.getOsr_emergency_receipt_price();
												unit = oSR.getOsr_emergency_receipt_price_unit();
											}
											//取消订单拦截
											Integer osr_cancelorder = oSR.getOsr_cancelorder();
											if(osr_cancelorder==1 && afdList.get(j).getAddservice_name().equals("取消订单拦截")){
												price = oSR.getOsr_cancelorder_price();
												unit = oSR.getOsr_cancelorder_price_unit();
											}
											//条码制作人工费
											Integer osr_makebarfee = oSR.getOsr_makebarfee();
											if(osr_makebarfee==1 && afdList.get(j).getAddservice_name().equals("条码制作人工费")){
												price = oSR.getOsr_makebarfee_price();
												unit = oSR.getOsr_makebarfee_price_unit();
											}
											//卸货费
											Integer osr_landfee = oSR.getOsr_landfee();
											if(osr_landfee==1 && afdList.get(j).getAddservice_name().equals("卸货费")){
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
											entity.setPhysical_warehouse(afdList.get(j).getPhysical_warehouse());
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
											addservicefeeDataService.update_settleFlag(afdList.get(j).getId().toString());
											//新增增值服务费
											addservicefeeDataSettlementService.save(entity);
										}
									}
								}
						}
				
				} catch (Exception e) {
					e.printStackTrace();
				}		
	}
	
	public void reSummary(String con_id,String dateStr){
		//合同信息 where (contract_type=3 or contract_type=4)
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
			try {
//				Map<String, Date> map = new HashMap<String,Date>();
				// 结算开始日期
//				String balance_start_date = DateUtil.getBeforeMToString(DateUtil.getYesterDay());
				// 结算结束日期
//				String balance_end_date = DateUtil.getCalendarToString(DateUtil.getYesterDay());
//				map.put("start", DateUtil.StrToDate(balance_start_date));
//				map.put("end", DateUtil.StrToDate(balance_end_date));
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
								for (int j = 0; j < sList.size(); j++) {
									storeList.add(sList.get(j));
								}
							}else{
								expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
								return ;
							}
						}
						//判断是否为结算日
						Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
						AddservicefeeData afd=new AddservicefeeData();
						afd.setStart_time(balance_cycle.get("balance_start_date").toString());
						afd.setEnd_time(balance_cycle.get("balance_end_date").toString());
						List<Map<String, String>> ASNList = addservicefeeDataService.findGroupByServiceName(afd);
						for (int k = 0; k < ASNList.size(); k++) {
							BigDecimal sumPrice = new BigDecimal(0.00);
							BigDecimal sumQty = new BigDecimal(0);
							for (int l = 0; l < storeList.size(); l++) {
								AddservicefeeDataSettlement afs = new AddservicefeeDataSettlement();
								String yy = dateStr.split("-")[0];
								String mm = dateStr.split("-")[1];
								afs.setStarttime(yy);
								afs.setEndtime(mm);
								afs.setAddservice_name(ASNList.get(k).get("addservice_name"));
								afs.setStore_name(storeList.get(l).getStore_name());
								List<AddservicefeeDataSettlement> afsList = addservicefeeDataSettlementService.findByAll(afs);
								for (int j = 0; j < afsList.size(); j++) {
									AddservicefeeDataSettlement a = afsList.get(j);
									sumPrice = sumPrice.add(a.getAmount());
									sumQty = sumQty.add(a.getQty());
								}
							}
							//获取操作费规则
							OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cbList.getId()).size()!=0?operationSettlementRuleService.findByCBID(cbList.getId()).get(0):null;
							if(null!=oSR){
								try {
									AddservicefeeBilldataCollect entity = new AddservicefeeBilldataCollect();
									entity.setCreate_time(new Date());
									entity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
									entity.setContract_id(oSR.getContract_id().toString());
									entity.setQty(sumQty.intValue());
									entity.setAmount(sumPrice);
									entity.setAddservice_name(ASNList.get(k).get("addservice_name"));
									entity.setTime(dateStr);
									//插入汇总记录
									AddservicefeeBilldataCollect afb = new AddservicefeeBilldataCollect();
									afb.setContract_id(oSR.getContract_id().toString());
									afb.setTime(dateStr);
									afb.setAddservice_name(ASNList.get(k).get("addservice_name"));
									List<AddservicefeeBilldataCollect> afbList = addservicefeeBilldataCollectService.findByList(afb);
									if(afbList.size()==0 && sumPrice.compareTo(new BigDecimal(0))!=0){
										addservicefeeBilldataCollectService.save(entity);
									}
								} catch (Exception e) {
									e.printStackTrace();
									try {
										expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(oSR.getContract_id()) , BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+oSR.getContract_id()+"],插入数据异常！", null, null, null, null, new Date()));
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
//								String osr_zzfwf_status = oSR.getOsr_zzfwf_status();
//								String osr_zzfwf_detail = oSR.getOsr_zzfwf_detail();
//								//阶梯
//								if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("0")){
//									
//								}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("1")){
//									
//								}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("2")){
//									List<Map<String, Object>> list = valueAddedServiceDetailService.findByCBID(osr_zzfwf_detail);
//									countALLData(list, "vasd_section", "vasd_value", sumPrice, oSR,sumPrice,sumQty,ASNList.get(k).get("addservice_name"));
//								}
							}
						}
					
//				}else{
//					//判断时间不再该生效合同周期内，合同修改为失效
//					Map<String, Object> errorMap = new HashMap<String, Object>();
//					errorMap.put("validity", 0);
//					errorMap.put("update_user", BaseConst.SYSTEM_JOB_CREATE);
//					errorMap.put("id", cbList.getId());
//					contractBasicinfoService.update_cb_validity(errorMap);
//					BalanceErrorLog bEL = new BalanceErrorLog();
//					bEL.setContract_id(Integer.valueOf(cbList.getId()));
//					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
//					bEL.setPro_result_info(cbList.getId()+"合同已过期！");
//					expressContractService.addBalanceErrorLog(bEL);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void recover(String con_id,String dateStr){
		//合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
		try {
			String contract_owner = cbList.getContract_owner();
			String contract_type = cbList.getContract_type();
		//删除AddservicefeeBilldataCollect
			AddservicefeeBilldataCollect entity = new AddservicefeeBilldataCollect();
			entity.setContract_id(con_id);
			entity.setTime(dateStr);
			abcMapper.delete_by_con(entity);
			List<Store> storeList = new ArrayList<Store>();
			if(contract_type.equals("3")){
				Store store = storeService.findByContractOwner(contract_owner);
				if(null!=store){
					storeList.add(store);
				} else {
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cbList.getId()));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
					expressContractService.addBalanceErrorLog(bEL);
					return ;
					
				}
				
			} else if(contract_type.equals("4")) {
				Client client = clientService.findByContractOwner(contract_owner);
				List<Store> sList = storeService.selectByClient(client.getId());
				if(null!=sList && sList.size()!=0){
					for (int j = 0; j < sList.size(); j++) {
						storeList.add(sList.get(j));
						
					}
					
				}else{
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
					return ;
					
				}
				
			}		
			Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
			for (int k = 0; k < storeList.size(); k++) {
				AddservicefeeDataSettlement settle = new AddservicefeeDataSettlement();
				settle.setStore_name(storeList.get(k).getStore_name());
				settle.setStarttime(balance_cycle.get("balance_start_date").toString());
				settle.setEndtime(balance_cycle.get("balance_end_date").toString());
				adsMapper.delete_by_con(settle);
				Store store = storeList.get(k);
				AddservicefeeData afd = new AddservicefeeData();
				afd.setStore_name(store.getStore_name());
				afd.setStart_time(balance_cycle.get("balance_start_date").toString());
                afd.setEnd_time(balance_cycle.get("balance_end_date").toString());
                adMapper.update_settleFlagToZero(afd);
                
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
}
