package com.bt.lmis.balance.estimate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.balance.model.StorageDataEstimate;
import com.bt.lmis.balance.model.StorageExpendituresDataSettlementEstimate;
import com.bt.lmis.balance.service.StorageDataEstimateService;
import com.bt.lmis.balance.service.StorageExpendituresDataSettlementEstimateService;
import com.bt.lmis.model.AllArea;
import com.bt.lmis.model.AllTray;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.NvitationRealuseanmountData;
import com.bt.lmis.model.NvitationUseanmountData;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.StorageExpendituresData;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.TotalArea;
import com.bt.lmis.model.TotalTray;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.service.AllAreaService;
import com.bt.lmis.service.AllTrayService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.StorageChargeService;
import com.bt.lmis.service.StorageExpendituresDataService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.TotalAreaService;
import com.bt.lmis.service.TotalTrayService;
import com.bt.lmis.summary.StorageChargeSettlement;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/** 
 * @ClassName: StorageFeeEstimate
 * @Description: TODO(仓储费预估)
 * @author Ian.Huang
 * @date 2017年5月4日 下午5:08:03 
 * 
 */
@Service
public class StorageFeeEstimate extends Estimate {
	
	private static final Logger logger = Logger.getLogger(StorageFeeEstimate.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Resource(name = "storageChargeServiceImpl")
	private StorageChargeService<StorageCharge> storageChargeService;
	@Resource(name = "storeServiceImpl")
	private StoreService<Store> storeService;
	@Resource(name = "clientServiceImpl")
	private ClientService<Client> clientService;
	@Resource(name = "expressContractServiceImpl")
	private ExpressContractService<BalanceErrorLog> expressContractService;
	@Resource(name = "storageExpendituresDataServiceImpl")
	private StorageExpendituresDataService<StorageExpendituresData> storageExpendituresDataService;
	@Resource(name = "totalAreaServiceImpl")
	private TotalAreaService<TotalArea> totalAreaService;
	@Resource(name = "allAreaServiceImpl")
	private AllAreaService<AllArea> allAreaService;
	@Resource(name = "totalTrayServiceImpl")
	private TotalTrayService<TotalTray> totalTrayService;
	@Resource(name = "allTrayServiceImpl")
	private AllTrayService<AllTray> allTrayService;
	@Resource(name = "storageExpendituresDataSettlementEstimateServiceImpl")
	private StorageExpendituresDataSettlementEstimateService storageExpendituresDataSettlementEstimateService;
	@Resource(name = "storageDataEstimateServiceImpl")
	private StorageDataEstimateService storageDataEstimateService;
	
	
	@SuppressWarnings("unused")
	@Override
	public EstimateResult estimate(EstimateParam param) {

		Object errorObject = null;
		
		EstimateResult estimateResult = new EstimateResult();
		Map<String,Object> msg = new HashMap<String,Object>();
		// 获取合同
		Contract cb=param.getContract();
		
		logger.debug("时间：［"+ DateUtil.formatS(new Date())+ "］ "+ cb.getContractName()+ "仓储费预估开始...");
		
		try {
			if(DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(), param.getFromDate())
					&& DateUtil.isTime(cb.getContractStart(), cb.getContractEnd(), param.getToDate())){
				//主体
				String contract_owner = cb.getContractOwner();
				String contract_type = cb.getContractType();
				//仓储费信息
				List<StorageCharge> scList = storageChargeService.findByCBID(String.valueOf(cb.getId()));
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
						estimateResult.setFlag(false);
						return estimateResult;
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
							estimateResult.setFlag(false);
							return estimateResult;
						}
					}
				}
				
				if(null!=scList && scList.size()!=0){
					//结算
					//循环客户下的所有店铺
					for (int j1 = 0; j1 < storeList.size(); j1++) {
						
						StorageExpendituresData countParam = new StorageExpendituresData();
						countParam.setStore_name(storeList.get(j1).getStore_name());
						countParam.setStart_time(param.getFromDate());
						countParam.setEnd_time(param.getToDate());
						Integer count = storageExpendituresDataService.countDateSE(countParam);
						
						Integer selectCount = 0;
						
						for (int j2 = 0; j2 < scList.size(); j2++) {
							//仓库
							//String whname = scList.get(j2).getSsc_whs_name();
							String whcode = scList.get(j2).getSsc_whs_code();
							//区域
							String areaname = scList.get(j2).getSsc_area_name();
							//类型
							String itemtypename = scList.get(j2).getSsc_item_type_name();
							//合同结算类型
							//[0固定费用 1按面积结算 2按体积结算 3按商品的体积推算 4按件数结算
							//5按件数反推所占面积 6按托盘结算 7按单个托盘的存放数量推算]
							//选取结算时间范围不在scList.get(j2)有效期ssc_starttime、ssc_endtime范围内，则不按规则汇总预估
//							if(DateUtil.isTime(scList.get(j2).getSsc_starttime(), scList.get(j2).getSsc_endtime(),
//									param.getFromDate()) && DateUtil.isTime(scList.get(j2).getSsc_starttime(), 
//												scList.get(j2).getSsc_endtime(), param.getToDate())){
								
								int ssc_sc_type = scList.get(j2).getSsc_sc_type();
								StorageExpendituresData storageExpendituresData = new StorageExpendituresData();
								storageExpendituresData.setStore_name(storeList.get(j1).getStore_name());
								storageExpendituresData.setWarehouse_code(whcode);
								storageExpendituresData.setArea_name(areaname);
								storageExpendituresData.setItem_type(itemtypename);
								storageExpendituresData.setStorage_type(ssc_sc_type);
	
								//Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(DateUtil.getMonth(DateUtil.getYesterDay()), Integer.parseInt(cb.getSettle_date()));
								storageExpendituresData.setStart_time(param.getFromDate());
								storageExpendituresData.setEnd_time(param.getToDate());
								//需要结算的数据
								List<StorageExpendituresData> seiDateList = storageExpendituresDataService.findDateSE(storageExpendituresData);
								selectCount += seiDateList.size();
								logger.error(cb.getContractName()+"查到结算数据:"+seiDateList.size()+"条.");
//								msg.put("msg", cb.getContractName()+"查到结算数据:"+seiDateList.size()+"条.");
//								estimateResult.setMsg(msg);
								for (int j = 0; j < seiDateList.size(); j++) {
									//原始数据 存储类型(0:固定，1:面积，2:体积，3:托盘)
									switch (ssc_sc_type) {
									case 0:
										//0固定费用
										Fixed_Cost(seiDateList.get(j),scList.get(j2),param);
										continue;
									case 1:
										//1按面积结算
										Settlement_By_Area(seiDateList.get(j),scList.get(j2),param);
										continue;
									case 2:
										//2按体积结算
										continue;
									case 3:
										//3按商品的体积推算
										continue;
									case 4:
										//4按件数结算
										Settlement_By_Piece(0,seiDateList.get(j),scList.get(j2),param);
										continue;
									case 5:
										//5按件数反推所占面积 	
										Settlement_By_Piece(1,seiDateList.get(j),scList.get(j2),param);
										continue;
									case 6:
										//6按托盘结算 
										Settlement_By_Tray(seiDateList.get(j),scList.get(j2),param);
										continue;
									case 7:
										//7按单个托盘的存放数量推算
										
										continue;
									default:
										continue;
									}
								}
//							} else {
//								expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
//										Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, 
//										"选取时间fromDate:" + param.getFromDate() + "toDate:" + param.getToDate()
//										+ "不在仓储费信息" + scList.get(j2).getSsc_id() + "时间范围ssc_starttime：" + 
//										scList.get(j2).getSsc_starttime() + "ssc_endtime:" 
//										+ scList.get(j2).getSsc_endtime(), 
//										null, null, null, null, new Date()));
//							}
						}
						
						if (!count.equals(selectCount)) {
							logger.info("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()+ "-仓储费预估信息.店铺:" + storeList.get(j1).getStore_name() + ",部分数据未结算，缺少数据数量:" + (count - selectCount));
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(cb.getId());
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID[" + cb.getId() + "],仓储费预估异常！");
							bEL.setDefault1("仓储费");
							bEL.setDefault2(param.getBatchNumber());
							bEL.setRemark("原始数据无关:"+ cb.getContractName()+ "-仓储费预估信息.店铺:" + storeList.get(j1).getStore_name() + "部分数据未结算,缺少数据数量:" + (count - selectCount));
							try {
								expressContractService.addBalanceErrorLog(bEL);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
//							estimateResult.setFlag(false);
						}
						
					}

					//预估
					//循环客户下的所有店铺
					for (int j2 = 0; j2 < scList.size(); j2++) {
						//仓库
//						String whname = scList.get(j2).getSsc_whs_name();
						String whcode = scList.get(j2).getSsc_whs_code();
						//区域
						String areaname = scList.get(j2).getSsc_area_name();
						//类型
						String itemtypename = scList.get(j2).getSsc_item_type_name();
						//仓储费信息
						//仓库
						//需要结算的数据
						//选取结算时间范围不在scList.get(j2)有效期ssc_starttime、ssc_endtime范围内，则不按规则汇总预估
//						if(DateUtil.isTime(scList.get(j2).getSsc_starttime(), scList.get(j2).getSsc_endtime(),
//								param.getFromDate()) && DateUtil.isTime(scList.get(j2).getSsc_starttime(), 
//											scList.get(j2).getSsc_endtime(), param.getToDate())){
							List<StorageExpendituresDataSettlementEstimate> list = new ArrayList<>();
							for (int j1 = 0; j1 < storeList.size(); j1++) {
								StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
										new StorageExpendituresDataSettlementEstimate();
								storageExpendituresDataSettlementEstimate.setWarehouseCode(whcode);
								storageExpendituresDataSettlementEstimate.setAreaName(areaname);
								storageExpendituresDataSettlementEstimate.setItemType(itemtypename);
								storageExpendituresDataSettlementEstimate.setStoreName(storeList.get(j1).getStore_name());
								storageExpendituresDataSettlementEstimate.setStorageType(scList.get(j2).getSsc_sc_type());
								storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
								storageExpendituresDataSettlementEstimate.setContractId(cb.getId());
								//时间范围
//								storageExpendituresDataSettlementEstimate.setStartTimeStr(scList.get(j2).getSsc_starttime());
//								storageExpendituresDataSettlementEstimate.setEndTimeStr(scList.get(j2).getSsc_endtime());
								List<StorageExpendituresDataSettlementEstimate> lists = 
										storageExpendituresDataSettlementEstimateService
										.findBySEDS(storageExpendituresDataSettlementEstimate);
								for (int j = 0; j < lists.size(); j++) {
									list.add(lists.get(j));
								}
							}
							BigDecimal sumMouth= new BigDecimal(0.00);
							BigDecimal sumQTY= new BigDecimal(0.00);
							String unit= "暂无";
							for (int j = 0; j < list.size(); j++) {
								BigDecimal day = list.get(j).getStorageFee();
								BigDecimal qty = list.get(j).getStorageAcreage();
								sumMouth = sumMouth.add(day);
								sumQTY = sumQTY.add(qty);
								unit = list.get(j).getAcreageUnit();
							}
							StorageDataEstimate saveEntity = new StorageDataEstimate();
							int ssc_sc_type = scList.get(j2).getSsc_sc_type();
							switch (ssc_sc_type) {
								case 0:
									//0固定费用
									saveEntity.setFixedQty(sumQTY.intValue());
									saveEntity.setFixedCost(sumMouth);
									saveEntity.setFixedUnit(unit);
									break;
								case 1:
									//1按面积结算
									saveEntity.setAreaQty(sumQTY);
									saveEntity.setAreaCost(sumMouth);
									saveEntity.setAreaCostunit(unit);
									break;
								case 2:
									//2按体积结算
									break;
								case 3:
									//3按商品的体积推算
									break;
								case 4:
									//4按件数结算
									saveEntity.setPieceQty(sumQTY);
									saveEntity.setPieceCost(sumMouth);
									saveEntity.setPieceUnit(unit);
									break;
								case 5:
									//5按件数反推所占面积 
									saveEntity.setPieceQty(sumQTY);
									saveEntity.setPieceCost(sumMouth);
									saveEntity.setPieceUnit(unit);
									break;
								case 6:
									//6按托盘结算 
									saveEntity.setTrayQty(sumQTY.intValue());
									saveEntity.setTrayCost(sumMouth);
									saveEntity.setTrayQtyunit(unit);
									break;
								case 7:
									//7按单个托盘的存放数量推算
									saveEntity.setTrayQty(sumQTY.intValue());
									saveEntity.setTrayCost(sumMouth);
									saveEntity.setTrayQtyunit(unit);
									break;
								default:
									break;
							}
							saveEntity.setCreateTime(new Date());
							saveEntity.setBatchNumber(param.getBatchNumber());
							saveEntity.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
							saveEntity.setContractId(Integer.valueOf(cb.getId()));
							saveEntity.setSscId(scList.get(j2).getSsc_id());
							//汇总数据
							if(sumMouth.compareTo(new BigDecimal(0.00)) != 0){
								storageDataEstimateService.save(saveEntity);
							}
							estimateResult.setFlag(true);
//						} else {
//							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
//									Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, 
//									"选取时间fromDate:" + param.getFromDate() + "toDate:" + param.getToDate()
//									+ "不在仓储费信息" + scList.get(j2).getSsc_id() + "时间范围ssc_starttime：" + 
//									scList.get(j2).getSsc_starttime() + "ssc_endtime:" 
//									+ scList.get(j2).getSsc_endtime(), 
//									null, null, null, null, new Date()));
//						}
					}
				} else {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
							Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, 
							"合同ID["+cb.getId()+"]没有绑定tb_storage_charge数据！", 
							null, null, null, null, new Date()));
//					estimateResult.setFlag(false);
				}
			} else {
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(Integer.valueOf(cb.getId()));
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info(cb.getId()+"预估选取时间超出合同时间范围！");
				expressContractService.addBalanceErrorLog(bEL);
				logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()
				+ "-仓储费预估异常,预估选取时间超出合同时间范围.");
				msg.put("error", "仓储费预估选取时间超出合同时间范围，fromDate="+param.getFromDate()+",toDate="+param.getToDate());
				estimateResult.setMsg(msg);
				estimateResult.setFlag(false);
			}
		} catch (Exception e) {
			logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()+ "-仓储费预估异常.message:" + e);

			BalanceErrorLog bEL = new BalanceErrorLog();
			bEL.setContract_id(cb.getId());
			bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
			bEL.setPro_result_info("合同ID[" + cb.getId() + "],仓储费预估异常！");
			bEL.setDefault1("仓储费");
			bEL.setDefault2(param.getBatchNumber());
			if (errorObject != null) {
				if (errorObject instanceof StorageExpendituresData) {
					bEL.setRemark("tb_storage_expenditures_data表数据id:" 
							+ ((StorageExpendituresData)errorObject).getId().toString());
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
			
			estimateResult.setFlag(false);
			e.printStackTrace();
		}
		logger.error("时间：［" + DateUtil.formatS(new Date()) + "］ " + cb.getContractName()+ "-仓储费预估结束.");
		return estimateResult;
	}
	
	/**
	 * @Title: Fixed_Cost 
	 * @Description: TODO(固定费用结算)
	 * @param 设定文件 
	 * @return void
	 * 返回类型 @throws
	 */
	private void Fixed_Cost(StorageExpendituresData storageExpendituresData,StorageCharge sc,EstimateParam param) {
		//固定费用价格
		String ssc_fixedcost = sc.getSsc_fixedcost();
		//固定费用
		BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
		BigDecimal multiply = new BigDecimal(1);
		
		Contract cb = param.getContract();
				
		//固定费用 * 1/月
		multiply = multiply.multiply(fixedcost);
		StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
				new StorageExpendituresDataSettlementEstimate();
		try {
			storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
			storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlementEstimate.setStorageFee(multiply);
			if(cb.getContractType().equals("3")){
				Store store = storeService.selectById(Integer.valueOf(cb.getContractOwner()));
				storageExpendituresDataSettlementEstimate.setStoreName(store.getStore_name());
			}else{
				storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
			}
			
			storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
			storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
			storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
			storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
			storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
			storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
			storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
			storageExpendituresDataSettlementEstimate.setWarehouseCode(storageExpendituresData.getWarehouse_code());
			storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
			storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
			storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
			storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
			storageExpendituresDataSettlementEstimate.setStorageAcreage(storageExpendituresData.getStorage_acreage());
			storageExpendituresDataSettlementEstimate.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
			storageExpendituresDataSettlementEstimate.setContractId(cb.getId());
			storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
			//插入明细记录
			storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
						sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
						"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 。
	 * @Title: Settlement_By_Area
	 * @Description: TODO(按面积结算) 
	 * @param 设定文件 @return
	 * void 返回类型 
	 * @throws
	 */
	private void Settlement_By_Area(StorageExpendituresData storageExpendituresData,StorageCharge sc,EstimateParam param) {
		if (sc.getSsc_occupied_area_type() == 1) {
			// 无阶梯
			String ssc_fixedcost = sc.getSsc_area_price();
			//固定费用
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			
			Contract cb = param.getContract();
					
			//固定费用 * 1/月
			multiply = multiply.multiply(fixedcost);
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
					new StorageExpendituresDataSettlementEstimate();
			try {
				storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
				storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlementEstimate.setStorageFee(multiply);
				storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
				storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
				storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
				storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
				storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
				storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
				storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlementEstimate.setWarehouseCode(storageExpendituresData.getWarehouse_code());
				storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlementEstimate
													.setStorageAcreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlementEstimate
													.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
				storageExpendituresDataSettlementEstimate.setContractId(cb.getId());
				storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
				//插入明细记录
				storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
//				storageExpendituresData.setSettle_flag(1);
				//修改原始数据状态
//				storageExpendituresDataService.update(storageExpendituresData);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
							sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
							"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (sc.getSsc_occupied_area_type() == 2) {
			// 超过部分阶梯
			//原始数据 仓储费值
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_over_part_area_tableid();
			List<Map<String, Object>> totalAreaList = totalAreaService.findByCBID(tableid);
			countCGBFData(totalAreaList, "sta_section", "sta_price", multiply, storageExpendituresData, sc,param);
		} else if (sc.getSsc_occupied_area_type() == 3) {
			// 总占用面积阶梯
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_total_area_tableid();
			List<Map<String, Object>> aaList = allAreaService.findByCBID(tableid);
			countALLData(aaList, "saa_section", "saa_price", multiply, storageExpendituresData, sc,param);;
		}
	}
	
	/** 
	* @Title: compareTo 
	* @Description: TODO(区间值比较) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	private boolean compareTo(String str1,String str2){
		Map<String,Integer> sectionMap1 = IntervalValidationUtil.strToMap(str1);
		Map<String,Integer> sectionMap2 = IntervalValidationUtil.strToMap(str2);
		if(sectionMap2.get("endNum")>sectionMap1.get("endNum")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @Title: Settlement_By_Piece 
	 * @Description: TODO(按件数结算) 
	 * @param 设定文件 
	 * @return
	 * void 返回类型 
	 * @throws
	 */
	private void Settlement_By_Piece(Integer type,StorageExpendituresData storageExpendituresData,StorageCharge sc,EstimateParam param) {
		if (type == 0) {
			//按件数结算
			String ssc_fixedcost = sc.getSsc_number_price();
			//件数单价
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//件数单价 * 原始数据值
			multiply = multiply.multiply(fixedcost).setScale(2,BigDecimal.ROUND_HALF_UP);
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
					new StorageExpendituresDataSettlementEstimate();
			try {
				storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
				storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlementEstimate.setStorageFee(multiply);
				storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
				storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
				storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
				storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
				storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
				storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
				storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlementEstimate.setWarehouseCode(storageExpendituresData.getWarehouse_code());
				storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlementEstimate
													.setStorageAcreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlementEstimate
													.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
				storageExpendituresDataSettlementEstimate.setContractId(param.getContract().getId());
				storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
				//插入明细记录
				storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
							sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
							"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (type == 1) {
			// 案件数反推面积结算
			//单平方所容纳件数
			String ssc_square_hold_the_number = sc.getSsc_square_hold_the_number();
			//平方单价
			String ssc_square_price = sc.getSsc_square_price();
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//件数单价 * 原始数据值
			BigDecimal[] bigList = multiply.divideAndRemainder(new BigDecimal(ssc_square_hold_the_number));
			if(bigList[1].compareTo(new BigDecimal(0.00))==0){
				multiply=bigList[0].multiply(new BigDecimal(ssc_square_price)).setScale(2,BigDecimal.ROUND_HALF_UP);
			}else{
				multiply=(bigList[0].add(new BigDecimal(1))).multiply(bigList[0]).setScale(2,BigDecimal.ROUND_HALF_UP);
			}
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
					new StorageExpendituresDataSettlementEstimate();
			try {
				storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
				storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlementEstimate.setStorageFee(multiply);
				storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
				storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
				storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
				storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
				storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
				storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
				storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlementEstimate
													.setStorageAcreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlementEstimate
													.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
				storageExpendituresDataSettlementEstimate.setContractId(param.getContract().getId());
				storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
				//插入明细记录
				storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
							sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
							"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/** 
	* @Title: Settlement_By_Tray 
	* @Description: TODO(按托盘数结算) 
	* @param @param storageExpendituresData
	* @param @param sc
	* @param @param cb    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void Settlement_By_Tray(StorageExpendituresData storageExpendituresData,StorageCharge sc,EstimateParam param) {
		if (sc.getSsc_tray_balance_type() == 1) {
			// 无阶梯
			String ssc_fixedcost = sc.getSsc_tray_price();
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			multiply = multiply.multiply(fixedcost).setScale(2,BigDecimal.ROUND_HALF_UP);
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
					new StorageExpendituresDataSettlementEstimate();
			try {
				storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
				storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlementEstimate.setStorageFee(multiply);
				storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
				storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
				storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
				storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
				storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
				storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
				storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlementEstimate.setWarehouseCode(storageExpendituresData.getWarehouse_code());
				storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlementEstimate.setStorageAcreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlementEstimate.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
				storageExpendituresDataSettlementEstimate.setContractId(param.getContract().getId());
				storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
				//插入明细记录
				storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
							sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
							"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (sc.getSsc_tray_balance_type() == 2) {
			// 超过部分阶梯
			//原始数据 仓储费值
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_total_tray_tableid();
			List<Map<String,Object>> lists =  totalTrayService.findByCBID(tableid);
			countCGBFData(lists, "stt_section", "stt_price", multiply, storageExpendituresData, sc,param);
		} else if (sc.getSsc_tray_balance_type() == 3) {
			// 总占用面积阶梯
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_over_part_tray_tableid();
			List<Map<String,Object>> lists = allTrayService.findByCBID(tableid);
			countALLData(lists, "sat_section", "sat_price", multiply, storageExpendituresData, sc,param);
		}
	
	}
	
	/**
	 * @param param  
	* @Title: countCGBFData 
	* @Description: TODO(计算超过部分阶梯) 
	* @param @param list		阶梯集合
	* @param @param section		区间字段
	* @param @param price		区间单价字段
	* @param @param multiply	原始数据仓储费值
	* @param @param storageExpendituresData	原始数据
	* @param @param sc    设定文件 仓储费合同
	* @return void    返回类型 
	* @throws 
	*/
	private void countCGBFData(List<Map<String, Object>> list,String section,String price,BigDecimal multiply,StorageExpendituresData storageExpendituresData1,StorageCharge sc, EstimateParam param){
		
		for (int i = 0; i < list.size(); i++) {
			BigDecimal add = new BigDecimal(0);
			Map<String,Object> map = list.get(i);
			String sections = map.get(section).toString();
			String prices = map.get(price).toString();
			Map<String,Integer> sectionMap = IntervalValidationUtil.strToMap(sections);
			if(sectionMap.get("type")==0){
				//大于OR小于
			}else{
				//区间
//				Integer startSymbol = sectionMap.get("startSymbol");
				Integer startNum = sectionMap.get("startNum");
//				Integer endSymbol = sectionMap.get("endSymbol");
				Integer endNum = sectionMap.get("endNum");
				
				//原始数据大于区间起始值
//				if(startSymbol==0){
					//startNum大于
					if(multiply.compareTo(new BigDecimal(startNum))==1){
//						if(endSymbol==0){
							if(multiply.compareTo(new BigDecimal(endNum))==1){
								
							}else{
								add=storageExpendituresData1.getStorage_acreage().multiply(new BigDecimal(prices).setScale(2,BigDecimal.ROUND_HALF_UP));
								insert(storageExpendituresData1, add, multiply,sc,param);
							}
//						}else{
//							if(multiply.compareTo(new BigDecimal(endNum))>=0){
//							}else{
//								add=storageExpendituresData1.getStorage_acreage().multiply(new BigDecimal(prices));
//								insert(storageExpendituresData1, add, multiply,sc);
//							}
//						}
//					}else{
//						//startNum大于等于
//						if(endSymbol==0){
//							if(multiply.compareTo(new BigDecimal(endNum))==1){
//								add=storageExpendituresData1.getStorage_acreage().multiply(new BigDecimal(prices));
//								insert(storageExpendituresData1, add, multiply,sc);
//							}else{
//							}
//						}else{
//							if(multiply.compareTo(new BigDecimal(endNum))>=0){
//								add=storageExpendituresData1.getStorage_acreage().multiply(new BigDecimal(prices));
//								insert(storageExpendituresData1, add, multiply,sc);
//							}else{
//							}
//						}
//						
//					}
				}
			}
		}
		
	}
	
	private void insert(StorageExpendituresData storageExpendituresData1,BigDecimal add,BigDecimal multiply,StorageCharge sc, EstimateParam param){
		try {
			StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
					new StorageExpendituresDataSettlementEstimate();
			storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
			storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlementEstimate.setStorageFee(add);
			storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData1.getPark_code());
			storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData1.getPark_name());
			storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData1.getPark_cost_center());
			storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData1.getAcreage_size());
			storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData1.getVolume_size());
			storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData1.getQty_size());
			storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData1.getStore_name());
			storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData1.getWarehouse_name());
			storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData1.getArea_name());
			storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData1.getItem_type());
			storageExpendituresDataSettlementEstimate.setStorageAcreage(multiply);
			storageExpendituresDataSettlementEstimate.setStartTime(sdf.parse(storageExpendituresData1.getStart_time()));
			storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData1.getStorage_type());
			storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData1.getAcreage_unit());
			storageExpendituresDataSettlementEstimate.setStorageAcreage(storageExpendituresData1.getStorage_acreage());
			storageExpendituresDataSettlementEstimate.setContractId(param.getContract().getId());
			storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
			//插入明细记录
			storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
						sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
						"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * @param param  
	* @Title: countALLData 
	* @Description: TODO(计算总占用部分阶梯) 
	* @param @param list		阶梯集合
	* @param @param section		区间
	* @param @param price		区间单价
	* @param @param multiply	原始数据值
	* @param @param storageExpendituresData 原始数据
	* @param @param sc    设定文件 	仓储费合同
	* @return void    返回类型 
	* @throws 
	*/
	private void countALLData(List<Map<String, Object>> list,String section,String price,BigDecimal multiply,StorageExpendituresData storageExpendituresData,StorageCharge sc, EstimateParam param){
		StorageExpendituresDataSettlementEstimate storageExpendituresDataSettlementEstimate = 
				new StorageExpendituresDataSettlementEstimate();
		String sectionA = "";
		String priceA = "";
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map = list.get(i);
			String sections = map.get(section).toString();
			String prices = map.get(price).toString();
			Map<String,Integer> sectionMap = IntervalValidationUtil.strToMap(sections);
			if(sectionMap.get("type")==0){
				//大于OR小于
			}else{
				//区间
				Integer startSymbol = sectionMap.get("startSymbol");
				Integer startNum = sectionMap.get("startNum");
				Integer endSymbol = sectionMap.get("endSymbol");
				Integer endNum = sectionMap.get("endNum");
				BigDecimal a1 = multiply;
				BigDecimal b1 = new BigDecimal(startNum);
				BigDecimal b2 = new BigDecimal(endNum);
				//小括号开始
				if(startSymbol==0){
					//小括号结束
					if(endSymbol==0){
						if(a1.compareTo(b1)==1 && a1.compareTo(b2)==-1){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}else{
						//中括号结束
						if(a1.compareTo(b1)==1 && a1.compareTo(b2)<=0){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}
				}
				//中括号开始
				if(startSymbol==1){
					//中括号结束
					if(endSymbol==0){
						if(a1.compareTo(b1)>=0 && a1.compareTo(b2)<=0){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}else{
						//小括号结束
						if(a1.compareTo(b1)>=0 && a1.compareTo(b2)==-1){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}
				}
			}
		}
		try {
			storageExpendituresDataSettlementEstimate.setCreateTime(new Date());
			storageExpendituresDataSettlementEstimate.setCreateUser(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlementEstimate.setStorageFee(multiply.multiply(new BigDecimal(priceA)).setScale(2,BigDecimal.ROUND_HALF_UP));
			storageExpendituresDataSettlementEstimate.setParkCode(storageExpendituresData.getPark_code());
			storageExpendituresDataSettlementEstimate.setParkName(storageExpendituresData.getPark_name());
			storageExpendituresDataSettlementEstimate.setParkCostCenter(storageExpendituresData.getPark_cost_center());
			storageExpendituresDataSettlementEstimate.setAcreageSize(storageExpendituresData.getAcreage_size());
			storageExpendituresDataSettlementEstimate.setVolumeSize(storageExpendituresData.getVolume_size());
			storageExpendituresDataSettlementEstimate.setQtySize(storageExpendituresData.getQty_size());
			storageExpendituresDataSettlementEstimate.setStoreName(storageExpendituresData.getStore_name());
			storageExpendituresDataSettlementEstimate.setWarehouseName(storageExpendituresData.getWarehouse_name());
			storageExpendituresDataSettlementEstimate.setAreaName(storageExpendituresData.getArea_name());
			storageExpendituresDataSettlementEstimate.setItemType(storageExpendituresData.getItem_type());
			storageExpendituresDataSettlementEstimate.setStorageAcreage(multiply);
			storageExpendituresDataSettlementEstimate.setStartTime(sdf.parse(storageExpendituresData.getStart_time()));
			storageExpendituresDataSettlementEstimate.setStorageType(storageExpendituresData.getStorage_type());
			storageExpendituresDataSettlementEstimate.setAcreageUnit(storageExpendituresData.getAcreage_unit());
			storageExpendituresDataSettlementEstimate.setStorageAcreage(storageExpendituresData.getStorage_acreage());
			storageExpendituresDataSettlementEstimate.setContractId(param.getContract().getId());
			storageExpendituresDataSettlementEstimate.setBatchNumber(param.getBatchNumber());
			//插入明细记录
			storageExpendituresDataSettlementEstimateService.save(storageExpendituresDataSettlementEstimate);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, 
						sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, 
						"合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}