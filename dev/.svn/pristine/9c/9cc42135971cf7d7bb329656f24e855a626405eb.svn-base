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
import com.bt.lmis.dao.NvitationRealuseanmountDataMapper;
import com.bt.lmis.dao.NvitationUseanmountDataGroupdetailMapper;
import com.bt.lmis.dao.NvitationUseanmountDataMapper;
import com.bt.lmis.dao.NvitationdataCollectMapper;
import com.bt.lmis.dao.WarehouseExpressDataMapper;
import com.bt.lmis.model.Ain;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.NvitationRealuseanmountData;
import com.bt.lmis.model.NvitationUseanmountData;
import com.bt.lmis.model.NvitationUseanmountDataGroupdetail;
import com.bt.lmis.model.NvitationdataCollect;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.AinService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.NvitationRealuseanmountDataService;
import com.bt.lmis.service.NvitationUseanmountDataGroupdetailService;
import com.bt.lmis.service.NvitationUseanmountDataService;
import com.bt.lmis.service.NvitationdataCollectService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;

/** 
* @ClassName: ConsumableCost 
* @Description: TODO(耗材费结算) 
* @author Yuriy.Jiang
* @date 2016年7月31日 下午2:44:12 
*  
*/
@SuppressWarnings("unchecked")
@Service
public class ConsumableCost {

	//合同服务类
	ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService = (ContractBasicinfoService<ContractBasicinfo>)SpringUtils.getBean("contractBasicinfoServiceImpl");
	//快递合同服务类 ［调用错误日志插入服务］
	ExpressContractService<T> expressContractService = (ExpressContractService<T>)SpringUtils.getBean("expressContractServiceImpl");
	//店铺服务类
	StoreService<Store> storeService = (StoreService<Store>)SpringUtils.getBean("storeServiceImpl");
	//客户服务类
	ClientService<Client> clientService = (ClientService<Client>)SpringUtils.getBean("clientServiceImpl");
	//耗材实际使用量原始数据
	NvitationRealuseanmountDataService<NvitationRealuseanmountData> nvitationRealuseanmountDataService = (NvitationRealuseanmountDataService<NvitationRealuseanmountData>)SpringUtils.getBean("nvitationRealuseanmountDataServiceImpl"); 
	//打包费服务类
	PackageChargeService<PackagePrice> packageChargeService = (PackageChargeService<PackagePrice>)SpringUtils.getBean("packageChargeServiceImpl"); 
	//耗材费服务类
	AinService<Ain> ainService = (AinService<Ain>)SpringUtils.getBean("ainServiceImpl");
	//耗材数据汇总明细服务类
	NvitationUseanmountDataGroupdetailService<NvitationUseanmountDataGroupdetail> nvitationUseanmountDataGroupdetailService = (NvitationUseanmountDataGroupdetailService<NvitationUseanmountDataGroupdetail>)SpringUtils.getBean("nvitationUseanmountDataGroupdetailServiceImpl");
	//耗材采购原始数据服务类
	NvitationUseanmountDataService<NvitationUseanmountData> nvitationUseanmountDataService = (NvitationUseanmountDataService<NvitationUseanmountData>)SpringUtils.getBean("nvitationUseanmountDataServiceImpl");
	//快递原始数据表服务类
	WarehouseExpressDataService<WarehouseExpressData> warehouseExpressDataService = (WarehouseExpressDataService<WarehouseExpressData>)SpringUtils.getBean("warehouseExpressDataServiceImpl");
	//耗材费明细数据汇总服务类
	NvitationdataCollectService<NvitationdataCollect> nvitationdataCollectService = (NvitationdataCollectService<NvitationdataCollect>)SpringUtils.getBean("nvitationdataCollectServiceImpl");

	@Autowired
    private NvitationUseanmountDataGroupdetailMapper<T> nudgMapper;
	@Autowired
	private  NvitationRealuseanmountDataMapper<T> nrdMapper;
	@Autowired
	private  WarehouseExpressDataMapper<T> wedMapper;
    @Autowired
	private  NvitationUseanmountDataMapper<T> nudMapper;
    @Autowired
    private NvitationdataCollectMapper<T> ncmmapper;
	
	public void ReckonConsumableCost(){
		//合同信息
		List<ContractBasicinfo> cbList = contractBasicinfoService.find_by_cb();
		if(null!=cbList && cbList.size()!=0){
			try {
				//遍历合同
				for (int i = 0; i < cbList.size(); i++) {
					if(DateUtil.isTime(cbList.get(i).getContract_start(), cbList.get(i).getContract_end(), DateUtil.formatDate(new Date()))){
						//主体
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
						//打包费信息
						PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.get(i).getId()));
						//null!=?(Integer.valueOf(pp.getConsumable())==1?false:true):false
						if(null!=pp && pp.getConsumable().equals("0")){
							continue;
						}else{
							for (int j = 0; j < storeList.size(); j++) {
								List<Ain> list = ainService.findByCBID(cbList.get(i).getId());
								for (int k = 0; k < list.size(); k++) {
									Ain map = list.get(k);
									String hc_no = map.getHc_no();
									String hc_name = map.getHc_name();
									BigDecimal hc_price = map.getHc_price();
									Integer hc_type = map.getHc_type();
									Integer cat_type = map.getCat_type();
									BigDecimal bill_num = map.getBill_num();
									NvitationRealuseanmountData entity = new NvitationRealuseanmountData();
									entity.setStore_name(storeList.get(j).getStore_name());
									entity.setSku_code(hc_no);
									entity.setSettle_flag(0);
									if(cat_type==1){
										//耗材实际使用量原始数据
										List<NvitationRealuseanmountData> hcList = nvitationRealuseanmountDataService.list(entity);
										for (int l = 0; l < hcList.size(); l++) {
											BigDecimal use_amount = hcList.get(l).getUse_amount();
											BigDecimal a1 = use_amount.multiply(hc_price);
											NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
											detail.setId(UUID.randomUUID().toString());
											detail.setCreate_time(new Date());
											detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
											detail.setContract_id(Integer.valueOf(cbList.get(i).getId()));
											detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
											detail.setSku_code(hc_no);
											detail.setSku_name(hc_name);
											detail.setSku_type(hc_type.toString());
											detail.setQty(use_amount);
											detail.setQty_unit(hcList.get(l).getUse_amountunit());
											detail.setPrice(hc_price);
											detail.setTotal_amount(a1.toString());
											NvitationRealuseanmountData data = hcList.get(l);
											data.setSettle_flag(1);
											nvitationRealuseanmountDataService.update(data);
											nvitationUseanmountDataGroupdetailService.save(detail);
										}
									}else if(cat_type==2){
										//耗材费按订单量计算
										if(DateUtil.judgeSummaryOrNot(Integer.parseInt(cbList.get(i).getSettle_date()))){
											WarehouseExpressData data = new WarehouseExpressData();
											data.setStore_name(storeList.get(j).getStore_name());
											data.setHcf_settle_flag(0);
											// 获取结算周期
											//该店铺下的所有原始数据	dataList
											String yy = String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR));
											String mm = String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1);
											data.setTstart_time(yy);
											data.setTend_time(mm);
											List<WarehouseExpressData> dataList = warehouseExpressDataService.queryAll(data);
											if(null!=dataList && dataList.size()!=0){
												BigDecimal a1 = new BigDecimal(dataList.size());
												BigDecimal result = (a1.divide(bill_num)).multiply(hc_price);
												System.out.println(result);
												NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
												detail.setId(UUID.randomUUID().toString());
												detail.setCreate_time(new Date());
												detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
												detail.setContract_id(Integer.valueOf(cbList.get(i).getId()));
												detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
												detail.setSku_code(hc_no);
												detail.setSku_name(hc_name);
												detail.setSku_type(hc_type.toString());
												detail.setQty(a1);
												detail.setQty_unit("单");
												detail.setPrice(result);
												detail.setTotal_amount(result.toString());
												for (int l = 0; l < dataList.size(); l++) {
													WarehouseExpressData warehouseExpressData = dataList.get(l);
													warehouseExpressData.setHcf_settle_flag(1);
													warehouseExpressDataService.update(warehouseExpressData);
												}
												nvitationUseanmountDataGroupdetailService.save(detail);
											}
										}
									}else if(cat_type==3){
										//耗材费按实际采购量计算
										NvitationUseanmountData data = new NvitationUseanmountData();
										data.setSettle_flag(0);
										data.setStore_name(storeList.get(j).getStore_name());
										data.setBz_number(hc_no);
										List<NvitationUseanmountData> dataList = nvitationUseanmountDataService.list(data);
										for (int l = 0; l < dataList.size(); l++) {
											BigDecimal inbound_qty = new BigDecimal(dataList.get(l).getInbound_qty());
											BigDecimal a1 = inbound_qty.multiply(hc_price);
											NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
											detail.setId(UUID.randomUUID().toString());
											detail.setCreate_time(new Date());
											detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
											detail.setContract_id(Integer.valueOf(cbList.get(i).getId()));
											detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
											detail.setSku_code(hc_no);
											detail.setSku_name(hc_name);
											detail.setSku_type(hc_type.toString());
											detail.setQty(inbound_qty);
											detail.setPrice(hc_price);
											detail.setTotal_amount(a1.toString());
											NvitationUseanmountData datas = dataList.get(l);
											datas.setSettle_flag(1);
											nvitationUseanmountDataService.update(datas);
											nvitationUseanmountDataGroupdetailService.save(detail);
										}
									}else if(cat_type==4){
										//耗材费按商品件数计算
										WarehouseExpressData data = new WarehouseExpressData();
										data.setStore_name(storeList.get(j).getStore_name());
										data.setHcf_settle_flag(0);
										// 获取结算周期
										Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(DateUtil.getMonth(DateUtil.getYesterDay()), Integer.parseInt(cbList.get(i).getSettle_date()));
										data.setTstart_time(balance_cycle.get("balance_start_date").toString());
										data.setTend_time(balance_cycle.get("balance_end_date").toString());
										List<WarehouseExpressData> hcf_list = warehouseExpressDataService.queryExpressAll(data);
										if(null!=hcf_list && hcf_list.size()!=0){
											for (int s = 0; s < hcf_list.size(); s++) {
												BigDecimal a1 = new BigDecimal(hcf_list.get(s).getQty());
												BigDecimal result = a1.multiply(hc_price);
												NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
												detail.setId(UUID.randomUUID().toString());
												detail.setCreate_time(new Date());
												detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
												detail.setContract_id(Integer.valueOf(cbList.get(i).getId()));
												detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
												detail.setSku_code(hc_no);
												detail.setSku_name(hc_name);
												detail.setSku_type(hc_type.toString());
												detail.setQty(a1);
												detail.setQty_unit("单-耗材费按商品件数计算");
												detail.setPrice(result);
												detail.setTotal_amount(result.toString());
												WarehouseExpressData warehouseExpressData = hcf_list.get(s);
												warehouseExpressData.setHcf_settle_flag(1);
												warehouseExpressDataService.update(warehouseExpressData);
												nvitationUseanmountDataGroupdetailService.save(detail);
											}
										}
									}
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
	
	/**
	 * 
	 * @Description: TODO(耗材费汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午2:29:48
	 */
	public void summary(ContractBasicinfo cb, String ym) throws Exception{
		List<NvitationUseanmountDataGroupdetail> list= nvitationUseanmountDataGroupdetailService.findByCBID(cb.getId());
		BigDecimal a_Qty= new BigDecimal(0.00);
		BigDecimal a_total_amount= new BigDecimal(0.00);
		BigDecimal b_Qty= new BigDecimal(0.00);
		BigDecimal b_total_amount= new BigDecimal(0.00);
		for (int j= 0; j< list.size(); j++) {
			NvitationUseanmountDataGroupdetail NUDG= list.get(j);
			BigDecimal qty= NUDG.getQty();
			String total_amount= NUDG.getTotal_amount();
			if(NUDG.getSku_type().equals("2")) {
				//0辅材
				a_Qty= a_Qty.add(qty);
				a_total_amount= a_total_amount.add(new BigDecimal(total_amount));
				
			}else{
				//1主材
				b_Qty= b_Qty.add(qty);
				b_total_amount= b_total_amount.add(new BigDecimal(total_amount));
				
			}
			
		}
		//辅材汇总
		NvitationdataCollect entityA= new NvitationdataCollect();
		entityA.setCreate_time(new Date());
		entityA.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
		entityA.setContract_id(Integer.valueOf(cb.getId()));
		entityA.setBilling_cycle(ym);
		entityA.setSku_type("2");
		entityA.setQty(a_Qty);
		entityA.setTotal_amount(a_total_amount);
		//主材汇总
		NvitationdataCollect entityB = new NvitationdataCollect();
		entityB.setCreate_time(new Date());
		entityB.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
		entityB.setContract_id(Integer.valueOf(cb.getId()));
		entityB.setBilling_cycle(ym);
		entityB.setSku_type("1");
		entityB.setQty(b_Qty);
		entityB.setTotal_amount(b_total_amount);
		nvitationdataCollectService.save(entityA);
		nvitationdataCollectService.save(entityB);
				
	}
	
	public void ReReckonConsumableCost(String con_id, String dateStr) {
		//合同信息
		ContractBasicinfo cbList= contractBasicinfoService.findById(Integer.parseInt(con_id));
		try {
			Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
			//遍历合同 获得客户
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
			//打包费信息
			PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.getId()));
			//null!=?(Integer.valueOf(pp.getConsumable())==1?false:true):false
			if(null!=pp && pp.getConsumable().equals("0")){
				return;
			}else{
				for (int j = 0; j < storeList.size(); j++) {
					List<Ain> list = ainService.findByCBID(cbList.getId());
					for (int k = 0; k < list.size(); k++) {
						Ain map = list.get(k);
						String hc_no = map.getHc_no();
						String hc_name = map.getHc_name();
						BigDecimal hc_price = map.getHc_price();
						Integer hc_type = map.getHc_type();
						Integer cat_type = map.getCat_type();
						BigDecimal bill_num = map.getBill_num();
						NvitationRealuseanmountData entity = new NvitationRealuseanmountData();
						entity.setStore_name(storeList.get(j).getStore_name());
						entity.setSku_code(hc_no);
						entity.setSettle_flag(0);
						entity.setStart_time(balance_cycle.get("balance_start_date").toString());
		                entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
						if(cat_type==1){
							//耗材实际使用量原始数据
							List<NvitationRealuseanmountData> hcList = nvitationRealuseanmountDataService.list(entity);
							for (int l = 0; l < hcList.size(); l++) {
								BigDecimal use_amount = hcList.get(l).getUse_amount();
								BigDecimal a1 = use_amount.multiply(hc_price);
								NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
								detail.setId(UUID.randomUUID().toString());
								detail.setCreate_time(new Date());
								detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
								detail.setContract_id(Integer.valueOf(cbList.getId()));
								detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
								detail.setSku_code(hc_no);
								detail.setSku_name(hc_name);
								detail.setSku_type(hc_type.toString());
								detail.setQty(use_amount);
								detail.setQty_unit(hcList.get(l).getUse_amountunit());
								detail.setPrice(hc_price);
								detail.setTotal_amount(a1.toString());
								NvitationRealuseanmountData data = hcList.get(l);
								data.setSettle_flag(1);
								nvitationRealuseanmountDataService.update(data);
								nvitationUseanmountDataGroupdetailService.save(detail);
							}
						}else if(cat_type==2){
							//耗材费按订单量计算
							if(DateUtil.judgeSummaryOrNot(Integer.parseInt(cbList.getSettle_date()))){
								WarehouseExpressData data = new WarehouseExpressData();
								data.setStore_name(storeList.get(j).getStore_name());
								data.setHcf_settle_flag(0);
								// 获取结算周期
								//该店铺下的所有原始数据	dataList
								String yy = dateStr.split("-")[0];
								String mm = dateStr.split("-")[1];
								data.setTstart_time(yy);
								data.setTend_time(mm);
								List<WarehouseExpressData> dataList = warehouseExpressDataService.queryAll(data);
								if(null!=dataList && dataList.size()!=0){
									BigDecimal a1 = new BigDecimal(dataList.size());
									BigDecimal result = (a1.divide(bill_num)).multiply(hc_price);
									System.out.println(result);
									NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
									detail.setId(UUID.randomUUID().toString());
									detail.setCreate_time(new Date());
									detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
									detail.setContract_id(Integer.valueOf(cbList.getId()));
									detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
									detail.setSku_code(hc_no);
									detail.setSku_name(hc_name);
									detail.setSku_type(hc_type.toString());
									detail.setQty(a1);
									detail.setQty_unit("单");
									detail.setPrice(result);
									detail.setTotal_amount(result.toString());
									for (int l = 0; l < dataList.size(); l++) {
										WarehouseExpressData warehouseExpressData = dataList.get(l);
										warehouseExpressData.setHcf_settle_flag(1);
										warehouseExpressDataService.update(warehouseExpressData);
									}
									nvitationUseanmountDataGroupdetailService.save(detail);
								}
							}
						}else if(cat_type==3){
							//耗材费按实际采购量计算
							NvitationUseanmountData data = new NvitationUseanmountData();
							data.setSettle_flag(0);
							data.setStore_name(storeList.get(j).getStore_name());
							data.setBz_number(hc_no);
							data.setStarttime(balance_cycle.get("balance_start_date").toString());
							data.setEndtime(balance_cycle.get("balance_end_date").toString());
							List<NvitationUseanmountData> dataList = nvitationUseanmountDataService.list(data);
							for (int l = 0; l < dataList.size(); l++) {
								BigDecimal inbound_qty = new BigDecimal(dataList.get(l).getInbound_qty());
								BigDecimal a1 = inbound_qty.multiply(hc_price);
								NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
								detail.setId(UUID.randomUUID().toString());
								detail.setCreate_time(new Date());
								detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
								detail.setContract_id(Integer.valueOf(cbList.getId()));
								detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
								detail.setSku_code(hc_no);
								detail.setSku_name(hc_name);
								detail.setSku_type(hc_type.toString());
								detail.setQty(inbound_qty);
								detail.setPrice(hc_price);
								detail.setTotal_amount(a1.toString());
								NvitationUseanmountData datas = dataList.get(l);
								datas.setSettle_flag(1);
								nvitationUseanmountDataService.update(datas);
								nvitationUseanmountDataGroupdetailService.save(detail);
							}
						}else if(cat_type==4){
							//耗材费按商品件数计算
							WarehouseExpressData data = new WarehouseExpressData();
							data.setStore_name(storeList.get(j).getStore_name());
							data.setHcf_settle_flag(0);
							// 获取结算周期
							data.setTstart_time(balance_cycle.get("balance_start_date").toString());
							data.setTend_time(balance_cycle.get("balance_end_date").toString());
							List<WarehouseExpressData> hcf_list = warehouseExpressDataService.queryAll(data);
							if(null!=hcf_list && hcf_list.size()!=0){
								for (int s = 0; s < hcf_list.size(); s++) {
									BigDecimal a1 = new BigDecimal(hcf_list.get(s).getQty());
									BigDecimal result = a1.multiply(hc_price);
									NvitationUseanmountDataGroupdetail detail = new NvitationUseanmountDataGroupdetail();
									detail.setId(UUID.randomUUID().toString());
									detail.setCreate_time(new Date());
									detail.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
									detail.setContract_id(Integer.valueOf(cbList.getId()));
									detail.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
									detail.setSku_code(hc_no);
									detail.setSku_name(hc_name);
									detail.setSku_type(hc_type.toString());
									detail.setQty(a1);
									detail.setQty_unit("单-耗材费按商品件数计算");
									detail.setPrice(result);
									detail.setTotal_amount(result.toString());
									WarehouseExpressData warehouseExpressData = hcf_list.get(s);
									warehouseExpressData.setHcf_settle_flag(1);
									warehouseExpressDataService.update(warehouseExpressData);
									nvitationUseanmountDataGroupdetailService.save(detail);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	public void ReSummary(String con_id,String dateStr){
		//合同信息 where (contract_type=3 or contract_type=4)
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
			try {
				NvitationUseanmountDataGroupdetail param=new NvitationUseanmountDataGroupdetail();
				param.setContract_id(Integer.parseInt(con_id));
				param.setBilling_cycle(dateStr);
						//该合同下的所有数据
						List<NvitationUseanmountDataGroupdetail> list = nudgMapper.findAll(param);
						BigDecimal a_Qty = new BigDecimal(0.00);
						BigDecimal a_total_amount = new BigDecimal(0.00);
						BigDecimal b_Qty = new BigDecimal(0.00);
						BigDecimal b_total_amount = new BigDecimal(0.00);
						for (int j = 0; j < list.size(); j++) {
							NvitationUseanmountDataGroupdetail NUDG = list.get(j);
							BigDecimal qty = NUDG.getQty();
							String total_amount = NUDG.getTotal_amount();
							if(NUDG.getSku_type().equals("2")){
								//0辅材
								a_Qty = a_Qty.add(qty);
								a_total_amount = a_total_amount.add(new BigDecimal(total_amount));
							}else{
								//1主材
								b_Qty = b_Qty.add(qty);
								b_total_amount = b_total_amount.add(new BigDecimal(total_amount));
							}
						}
						//辅材汇总
						NvitationdataCollect entityA = new NvitationdataCollect();
						entityA.setCreate_time(new Date());
						entityA.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
						entityA.setContract_id(Integer.valueOf(cbList.getId()));
						entityA.setBilling_cycle(dateStr);
						entityA.setSku_type("2");
						entityA.setQty(a_Qty);
						entityA.setTotal_amount(a_total_amount);
						//主材汇总
						NvitationdataCollect entityB = new NvitationdataCollect();
						entityB.setCreate_time(new Date());
						entityB.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
						entityB.setContract_id(Integer.valueOf(cbList.getId()));
						entityB.setBilling_cycle(dateStr);
						entityB.setSku_type("1");
						entityB.setQty(b_Qty);
						entityB.setTotal_amount(b_total_amount);
						nvitationdataCollectService.save(entityA);
						nvitationdataCollectService.save(entityB);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	public  void  recover_data(String  con_id,String dateStr){

		//合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
			try {
				Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
				//遍历合同
						//主体
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
						//打包费信息
						PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cbList.getId()));
						//null!=?(Integer.valueOf(pp.getConsumable())==1?false:true):false
						if(null!=pp && pp.getConsumable().equals("0")){
							return;
						}else{
							for (int j = 0; j < storeList.size(); j++) {
								NvitationRealuseanmountData entity = new NvitationRealuseanmountData();
								entity.setStore_name(storeList.get(j).getStore_name());
								entity.setStart_time(balance_cycle.get("balance_start_date").toString());
								entity.setEnd_time(balance_cycle.get("balance_end_date").toString());
								nrdMapper.update_settleflag(entity);
								WarehouseExpressData data = new WarehouseExpressData();
								data.setStore_name(storeList.get(j).getStore_name());
								data.setHcf_settle_flag(0);
								// 获取结算周期
								//该店铺下的所有原始数据	dataList
								String yy = String.valueOf(DateUtil.getYesterDay().get(Calendar.YEAR));
								String mm = String.valueOf(DateUtil.getYesterDay().get(Calendar.MONTH) + 1);
								data.setTstart_time(yy);
								data.setTend_time(mm);
								wedMapper.update_Hcf_settle_flag(data);
								NvitationUseanmountData  data1 =new NvitationUseanmountData();
								data1.setStarttime(balance_cycle.get("balance_start_date").toString());
								data1.setEndtime(balance_cycle.get("balance_end_date").toString());
								data1.setStore_name(storeList.get(j).getStore_name());
								nudMapper.update_settle_flag(data1);
								NvitationUseanmountDataGroupdetail detail=new NvitationUseanmountDataGroupdetail();
								detail.setContract_id(Integer.parseInt(con_id));
								detail.setBilling_cycle(dateStr);
								nudgMapper.delete_by_condition(detail);
							}
					
							
							
						}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		//汇总恢复
			NvitationdataCollect entityB = new NvitationdataCollect();
			entityB.setContract_id(Integer.parseInt(con_id));
			entityB.setBilling_cycle(dateStr);
			entityB.setSku_type("1");
			ncmmapper.delete_by_cond(entityB);
			entityB.setSku_type("2");
			ncmmapper.delete_by_cond(entityB);
	}
	
	
}
