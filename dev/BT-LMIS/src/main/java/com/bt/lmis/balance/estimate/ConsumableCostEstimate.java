package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.InvitationUseanmountDataGroupdetailEstimate;
import com.bt.lmis.balance.model.InvitationdataEstimate;
import com.bt.lmis.balance.service.InvitationUseanmountDataGroupdetailEstimateService;
import com.bt.lmis.balance.service.InvitationdataEstimateService;
import com.bt.lmis.model.Ain;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.NvitationRealuseanmountData;
import com.bt.lmis.model.NvitationUseanmountData;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.AinService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.NvitationRealuseanmountDataService;
import com.bt.lmis.service.NvitationUseanmountDataService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;


/** 
* @ClassName: ConsumableCostEstimate 
* @Description: TODO(耗材费预估) 
* @author Jindong.lin
* @date 2017年5月15日 下午2:06 
*  
*/
@Service
public class ConsumableCostEstimate extends Estimate {

	private static final Logger logger = Logger.getLogger(ConsumableCostEstimate.class);
	
	// 快递合同服务类 ［调用错误日志插入服务］
	@Resource(name = "expressContractServiceImpl")
	ExpressContractService<T> expressContractService;
	// 店铺服务类
	@Resource(name = "storeServiceImpl")
	StoreService<Store> storeService;
	// 客户服务类
	@Resource(name = "clientServiceImpl")
	ClientService<Client> clientService;
	//打包费服务类
	@Resource(name = "packageChargeServiceImpl")
	private PackageChargeService<PackagePrice> packageChargeService;
	//耗材采购原始数据服务类
	@Resource(name = "nvitationUseanmountDataServiceImpl")
	private NvitationUseanmountDataService<NvitationUseanmountData> nvitationUseanmountDataService;
	//耗材实际使用量原始数据
	@Resource(name = "nvitationRealuseanmountDataServiceImpl")
	private NvitationRealuseanmountDataService<NvitationRealuseanmountData> nvitationRealuseanmountDataService; 
	//耗材数据汇总明细服务类
	@Resource(name = "invitationUseanmountDataGroupdetailEstimateServiceImpl")
	private InvitationUseanmountDataGroupdetailEstimateService invitationUseanmountDataGroupdetailEstimateService;
	//快递原始数据表服务类
	@Resource(name = "warehouseExpressDataServiceImpl")
	private WarehouseExpressDataService<WarehouseExpressData> warehouseExpressDataService;
	//耗材费服务类
	@Resource(name = "ainServiceImpl")
	private AinService<Ain> ainService;
	
	
	@Resource(name = "invitationdataEstimateServiceImpl")
	private InvitationdataEstimateService invitationdataEstimateService;
	

	@Override
	public EstimateResult estimate(EstimateParam param){

		Object errorObject = null;
		
		EstimateResult resultParam = new EstimateResult();
		Map<String,Object> msg = new HashMap<String,Object>();
		
		Contract cb = param.getContract();
		try {
			//生成明细
	
			logger.debug("时间：［"+ DateUtil.formatS(new Date())+ "］ "+ cb.getContractName()+ "耗材费预估开始...");
			
			
			if(DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(), param.getFromDate())
					&& DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(), param.getToDate())){
				//主体
				String contract_owner = cb.getContractOwner();
				String contract_type = cb.getContractType(); 
				List<Store> storeList = new ArrayList<Store>();
				if(contract_type.equals("3")){
					Store store = storeService.findByContractOwner(contract_owner);
					if(null!=store){
						storeList.add(store);
					}else{
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(Integer.valueOf(cb.getId()));
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("合同ID["+cb.getId()+"],店铺不存在！");
						bEL.setError_type("3");
						expressContractService.addBalanceErrorLog(bEL);

						resultParam.setFlag(false);
					}
				}else if(contract_type.equals("4")){
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
							resultParam.setFlag(false);
						}
					}
				}
				//打包费信息
				PackagePrice pp = packageChargeService.getPackagePrice(Integer.valueOf(cb.getId()));
				//null!=?(Integer.valueOf(pp.getConsumable())==1?false:true):false
				if(null!=pp && pp.getConsumable().equals("0")){

				}else{
					for (int j = 0; j < storeList.size(); j++) {
						//耗材费规则
						List<Ain> list = ainService.findByCBID(String.valueOf(cb.getId()));
						Map<Integer,Object> catType = new HashMap<Integer,Object>();
						if (list != null && list.size() > 0) {
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
//								entity.setSettle_flag(0);
								entity.setStart_time(param.getFromDate());
								entity.setEnd_time(param.getToDate());
								if(cat_type==1){
									
									int count = nvitationRealuseanmountDataService.countSE(entity);
									int firstResult= 0;
									for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {

										entity.setFirstResult(firstResult);
										entity.setMaxResult(BaseConst.excel_pageSize);
										
										//耗材实际使用量原始数据
										List<NvitationRealuseanmountData> hcList = nvitationRealuseanmountDataService.listSE(entity);
										for (int l = 0; l < hcList.size(); l++) {
											errorObject = hcList.get(l);
											BigDecimal use_amount = hcList.get(l).getUse_amount();
											BigDecimal a1 = use_amount.multiply(hc_price);
											InvitationUseanmountDataGroupdetailEstimate detail = 
													new InvitationUseanmountDataGroupdetailEstimate();
											detail.setId(UUID.randomUUID().toString());
											detail.setCreateTime(new Date());
											detail.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
											detail.setCatType(cat_type);
											detail.setContractId(Integer.valueOf(cb.getId()));
											detail.setBatchNumber(param.getBatchNumber());
											detail.setSkuCode(hc_no);
											detail.setSkuName(hc_name);
											detail.setSkuType(hc_type.toString());
											detail.setQty(use_amount);
											detail.setQtyUnit(hcList.get(l).getUse_amountunit());
											detail.setPrice(hc_price);
											detail.setTotalAmount(a1);
											detail.setParkCode(hcList.get(l).getPark_code());
											detail.setParkName(hcList.get(l).getPark_name());
											detail.setParkCostCenter(hcList.get(l).getPark_cost_center());
											invitationUseanmountDataGroupdetailEstimateService.save(detail);
										}
										errorObject = null;
										
										firstResult= pageNo*BaseConst.excel_pageSize;
									}
								}else if(catType.get(cat_type) == null && cat_type==2){
									catType.put(cat_type, cat_type);
									//耗材费按订单量计算
									WarehouseExpressData data = new WarehouseExpressData();
									data.setStore_name(storeList.get(j).getStore_name());
									data.setTstart_time(param.getFromDate());
									data.setTend_time(param.getToDate());
									
									int count = warehouseExpressDataService.countByEntitySE(data);
//									List<WarehouseExpressData> dataList = warehouseExpressDataService.queryAllSE(data);
									if(count!=0){
										//单量=原本数据总数量/每单数量   取整(进一)
										BigDecimal a1 = new BigDecimal(count).divide(bill_num,0,BigDecimal.ROUND_UP);
										BigDecimal result = (a1).multiply(hc_price);
//										System.out.println(result);
										InvitationUseanmountDataGroupdetailEstimate detail = 
												new InvitationUseanmountDataGroupdetailEstimate();
										detail.setId(UUID.randomUUID().toString());
										detail.setCreateTime(new Date());
										detail.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
										detail.setCatType(cat_type);
										detail.setContractId(Integer.valueOf(cb.getId()));
										detail.setBatchNumber(param.getBatchNumber());
										detail.setSkuCode(hc_no);
										detail.setSkuName(hc_name);
										detail.setSkuType(hc_type.toString());
										detail.setQty(a1);
										detail.setQtyUnit("单");
										detail.setPrice(result);
										detail.setTotalAmount(result);
										
										invitationUseanmountDataGroupdetailEstimateService.save(detail);
									}
								}else if(cat_type==3){
									//耗材费按实际采购量计算
									NvitationUseanmountData data = new NvitationUseanmountData();
									data.setStore_name(storeList.get(j).getStore_name());
									data.setBz_number(hc_no);
									data.setStarttime(param.getFromDate());
									data.setEndtime(param.getToDate());
									
									int count = nvitationUseanmountDataService.countSE(data);
									int firstResult= 0;
									for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {

										data.setFirstResult(firstResult);
										data.setMaxResult(BaseConst.excel_pageSize);
										
										List<NvitationUseanmountData> dataList = nvitationUseanmountDataService.listSE(data);
										for (int l = 0; l < dataList.size(); l++) {
											errorObject = dataList.get(l);
											BigDecimal inbound_qty = new BigDecimal(dataList.get(l).getInbound_qty());
											BigDecimal a1 = inbound_qty.multiply(hc_price);
											InvitationUseanmountDataGroupdetailEstimate detail = 
													new InvitationUseanmountDataGroupdetailEstimate();
											detail.setId(UUID.randomUUID().toString());
											detail.setCreateTime(new Date());
											detail.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
											detail.setCatType(cat_type);
											detail.setContractId(Integer.valueOf(cb.getId()));
											detail.setBatchNumber(param.getBatchNumber());
											detail.setSkuCode(hc_no);
											detail.setSkuName(hc_name);
											detail.setSkuType(hc_type.toString());
											detail.setQty(inbound_qty);
											detail.setPrice(hc_price);
											detail.setTotalAmount(a1);
											detail.setParkCode(dataList.get(l).getPark_code());
											detail.setParkName(dataList.get(l).getPark_name());
											detail.setParkCostCenter(dataList.get(l).getPark_cost_center());
											invitationUseanmountDataGroupdetailEstimateService.save(detail);
										}
										errorObject = null;
										
										firstResult= pageNo*BaseConst.excel_pageSize;
									}
								}else if(cat_type==4){
									//耗材费按商品件数计算
									WarehouseExpressData data = new WarehouseExpressData();
									data.setStore_name(storeList.get(j).getStore_name());
									data.setTstart_time(param.getFromDate());
									data.setTend_time(param.getToDate());

									int count = warehouseExpressDataService.countExpressAllSE(data);
									int firstResult= 0;
									for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {

										data.setFirstResult(firstResult);
										data.setMaxResult(BaseConst.excel_pageSize);
										
										List<WarehouseExpressData> hcf_list = warehouseExpressDataService.queryExpressAllSE(data);
										if(null!=hcf_list && hcf_list.size()!=0){
											for (int s = 0; s < hcf_list.size(); s++) {
												errorObject = hcf_list.get(s);
												BigDecimal a1 = new BigDecimal(hcf_list.get(s).getQty());
												BigDecimal result = a1.multiply(hc_price);
												InvitationUseanmountDataGroupdetailEstimate detail = 
														new InvitationUseanmountDataGroupdetailEstimate();
												detail.setId(UUID.randomUUID().toString());
												detail.setCreateTime(new Date());
												detail.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
												detail.setCatType(cat_type);
												detail.setContractId(Integer.valueOf(cb.getId()));
												detail.setBatchNumber(param.getBatchNumber());
												detail.setSkuCode(hc_no);
												detail.setSkuName(hc_name);
												detail.setSkuType(hc_type.toString());
												detail.setQty(a1);
												detail.setQtyUnit("单-耗材费按商品件数计算");
												detail.setPrice(result);
												detail.setTotalAmount(result);
												detail.setParkCode(hcf_list.get(s).getPark_code());
												detail.setParkName(hcf_list.get(s).getPark_name());
												detail.setParkCostCenter(hcf_list.get(s).getPark_cost_center());
												invitationUseanmountDataGroupdetailEstimateService.save(detail);
											}
											errorObject = null;
										}
										
										firstResult= pageNo*BaseConst.excel_pageSize;
									}
								}
							}
						} else {
							logger.info("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + "耗材费预估异常!");
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(cb.getId());
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID[" + cb.getId() + "],耗材费预估异常！");
							bEL.setDefault1("耗材费");
							bEL.setDefault2(param.getBatchNumber());
							bEL.setRemark("原始数据无关,"  + cb.getContractName() +"耗材费规则未维护。");
							
							try {
								expressContractService.addBalanceErrorLog(bEL);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							resultParam.setFlag(false);
						}
					}
				}
			} else {
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(Integer.valueOf(cb.getId()));
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info(cb.getId()+"预估选取时间超出合同时间范围！");
				expressContractService.addBalanceErrorLog(bEL);
				logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()
				+ "-耗材费费结算异常,预估选取时间超出合同时间范围.");
				msg.put("error", "耗材费预估选取时间超出合同时间范围，fromDate="+param.getFromDate()+",toDate="+param.getToDate());
				resultParam.setMsg(msg);
				resultParam.setFlag(false);
			}
			
			//生成预估
			InvitationUseanmountDataGroupdetailEstimate invitationUseanmountDataGroupdetailEstimate = 
					new InvitationUseanmountDataGroupdetailEstimate();
			invitationUseanmountDataGroupdetailEstimate.setBatchNumber(param.getBatchNumber());
			invitationUseanmountDataGroupdetailEstimate.setContractId(cb.getId());
			
			BigDecimal a_Qty= new BigDecimal(0.00);
			BigDecimal a_total_amount= new BigDecimal(0.00);
			BigDecimal b_Qty= new BigDecimal(0.00);
			BigDecimal b_total_amount= new BigDecimal(0.00);

			int count = invitationUseanmountDataGroupdetailEstimateService
					.countByCBIDBN(invitationUseanmountDataGroupdetailEstimate);
			int firstResult= 0;
			for(int pageNo= 1; pageNo< forCount(count, BaseConst.excel_pageSize)+ 1; pageNo++) {

				invitationUseanmountDataGroupdetailEstimate.setFirstResult(firstResult);
				invitationUseanmountDataGroupdetailEstimate.setMaxResult(BaseConst.excel_pageSize);

				List<InvitationUseanmountDataGroupdetailEstimate> list= invitationUseanmountDataGroupdetailEstimateService
														.findByCBIDBN(invitationUseanmountDataGroupdetailEstimate);
				
				for (int j= 0; j< list.size(); j++) {
					InvitationUseanmountDataGroupdetailEstimate NUDG= list.get(j);
					BigDecimal qty= NUDG.getQty();
					BigDecimal total_amount= NUDG.getTotalAmount();
					if(NUDG.getSkuType().equals("2")) {
						//0辅材
						a_Qty= a_Qty.add(qty);
						a_total_amount= a_total_amount.add(total_amount);
						
					}else{
						//1主材
						b_Qty= b_Qty.add(qty);
						b_total_amount= b_total_amount.add(total_amount);
						
					}
					
				}
				
				firstResult= pageNo*BaseConst.excel_pageSize;
			}
			
			//辅材汇总
			InvitationdataEstimate entityA= new InvitationdataEstimate();
			entityA.setCreateTime(new Date());
			entityA.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
			entityA.setContractId(Integer.valueOf(cb.getId()));
			entityA.setBatchNumber(param.getBatchNumber());
			entityA.setSkuType("2");
			entityA.setQty(a_Qty);
			entityA.setTotalAmount(a_total_amount);
			//主材汇总
			InvitationdataEstimate entityB = new InvitationdataEstimate();
			entityB.setCreateTime(new Date());
			entityB.setCreateUser(BaseConst.SYSTEM_JOB_CREATE);
			entityB.setContractId(Integer.valueOf(cb.getId()));
			entityB.setBatchNumber(param.getBatchNumber());
			entityB.setSkuType("1");
			entityB.setQty(b_Qty);
			entityB.setTotalAmount(b_total_amount);
			invitationdataEstimateService.save(entityA);
			invitationdataEstimateService.save(entityB);
			resultParam.setFlag(true);
		} catch (Exception e) {
			logger.debug("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName() + "耗材费预估异常!");
			BalanceErrorLog bEL = new BalanceErrorLog();
			bEL.setContract_id(cb.getId());
			bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
			bEL.setPro_result_info("合同ID[" + cb.getId() + "],耗材费预估异常！");
			bEL.setDefault1("耗材费");
			bEL.setDefault2(param.getBatchNumber());
			if (errorObject != null) {
				if (errorObject instanceof NvitationUseanmountData) {
					bEL.setRemark("tb_invitation_useanmount_data表数据id:" 
							+ ((NvitationUseanmountData)errorObject).getId().toString());
				} else if (errorObject instanceof NvitationRealuseanmountData) {
					bEL.setRemark("tb_invitation_realuseanmount_data表数据id:" 
							+ ((NvitationRealuseanmountData)errorObject).getId().toString());
				} else if (errorObject instanceof WarehouseExpressData) {
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
			resultParam.setFlag(false);
			e.printStackTrace();
		}
		
		return resultParam;
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
