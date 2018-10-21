package com.bt.lmis.summary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.StorageDataGroupMapper;
import com.bt.lmis.dao.StorageExpendituresDataMapper;
import com.bt.lmis.dao.StorageExpendituresDataSettlementMapper;
import com.bt.lmis.model.AllArea;
import com.bt.lmis.model.AllTray;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.StorageDataGroup;
import com.bt.lmis.model.StorageExpendituresData;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.TotalArea;
import com.bt.lmis.model.TotalTray;
import com.bt.lmis.service.AllAreaService;
import com.bt.lmis.service.AllTrayService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.StorageChargeService;
import com.bt.lmis.service.StorageDataGroupService;
import com.bt.lmis.service.StorageExpendituresDataService;
import com.bt.lmis.service.StorageExpendituresDataSettlementService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.TotalAreaService;
import com.bt.lmis.service.TotalTrayService;
import com.bt.utils.BaseConst;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/**
 * @ClassName: StorageChargeSettlement
 * @Description: TODO(仓储费结算)
 * @author Yuriy.Jiang
 * @date 2016年7月12日 上午10:06:21
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class StorageChargeSettlement {

	private static final Logger logger = Logger.getLogger(StorageChargeSettlement.class);
	ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService = (ContractBasicinfoService<ContractBasicinfo>)SpringUtils.getBean("contractBasicinfoServiceImpl");
	StorageExpendituresDataService<StorageExpendituresData> storageExpendituresDataService = (StorageExpendituresDataService<StorageExpendituresData>)SpringUtils.getBean("storageExpendituresDataServiceImpl");
	StorageExpendituresDataSettlementService<StorageExpendituresDataSettlement> storageExpendituresDataSettlementService = (StorageExpendituresDataSettlementService<StorageExpendituresDataSettlement>)SpringUtils.getBean("storageExpendituresDataSettlementServiceImpl");
	StorageChargeService<StorageCharge> storageChargeService = (StorageChargeService<StorageCharge>)SpringUtils.getBean("storageChargeServiceImpl");
	ExpressContractService<T> expressContractService = (ExpressContractService<T>)SpringUtils.getBean("expressContractServiceImpl");
	StoreService<Store> storeServiceImpl = (StoreService<Store>)SpringUtils.getBean("storeServiceImpl");
	ClientService<Client> clientServiceImpl = (ClientService<Client>)SpringUtils.getBean("clientServiceImpl");
	TotalAreaService<TotalArea> totalAreaServiceImpl = (TotalAreaService<TotalArea>)SpringUtils.getBean("totalAreaServiceImpl");
	AllAreaService<AllArea> allAreaServiceImpl = (AllAreaService<AllArea>)SpringUtils.getBean("allAreaServiceImpl");
	TotalTrayService<TotalTray> totalTrayServiceImpl = (TotalTrayService<TotalTray>)SpringUtils.getBean("totalTrayServiceImpl");
	AllTrayService<AllTray> allTrayServiceImpl = (AllTrayService<AllTray>)SpringUtils.getBean("allTrayServiceImpl");
	StorageDataGroupService<StorageDataGroup> storageDataGroupServiceImpl = (StorageDataGroupService<StorageDataGroup>)SpringUtils.getBean("storageDataGroupServiceImpl");
	@Autowired
    private StorageExpendituresDataMapper<T> storageExpendituresDatamapper;
	@Autowired
    private	StorageExpendituresDataSettlementMapper<T> storageExpendituresDataSettlementMapper;
	@Autowired
	private  StorageDataGroupMapper<T> storageDataGroupMapper;
	/** 
	* @Title: findStorageCharge 
	* @Description: TODO(结算) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void ReckonStorageCharge(){
		//合同信息
		List<ContractBasicinfo> cbList = contractBasicinfoService.find_by_cb();
		for (int i = 0; i < cbList.size(); i++) {
			System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name()+ "-仓储费结算开始...");
			try {
				if(DateUtil.isTime(cbList.get(i).getContract_start(), cbList.get(i).getContract_end(), DateUtil.formatDate(new Date()))){
					//主体
					String contract_owner = cbList.get(i).getContract_owner();
					String contract_type = cbList.get(i).getContract_type();
					//仓储费信息
					List<StorageCharge> scList = storageChargeService.findByCBID(cbList.get(i).getId());
					List<Store> storeList = new ArrayList<Store>();
					if(contract_type.equals("3")){
						Store store = storeServiceImpl.findByContractOwner(contract_owner);
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
						Client client = clientServiceImpl.findByContractOwner(contract_owner);
						List<Store> sList = storeServiceImpl.selectByClient(client.getId());
						if(null!=sList && sList.size()!=0){
							for (int j = 0; j < sList.size(); j++) {
								storeList.add(sList.get(j));
							}
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.get(i).getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.get(i).getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
							continue ;
						}
					}
					//循环客户下的所有店铺
					for (int j1 = 0; j1 < storeList.size(); j1++) {
						if(null!=scList && scList.size()!=0){
							for (int j2 = 0; j2 < scList.size(); j2++) {
								//仓库
								String whname = scList.get(j2).getSsc_whs_name();
								//区域
								String areaname = scList.get(j2).getSsc_area_name();
								//类型
								String itemtypename = scList.get(j2).getSsc_item_type_name();
								//合同结算类型[0固定费用 1按面积结算 2按体积结算 3按商品的体积推算 4按件数结算 5按件数反推所占面积 6按托盘结算 7按单个托盘的存放数量推算]
								int ssc_sc_type = scList.get(j2).getSsc_sc_type();
								StorageExpendituresData storageExpendituresData = new StorageExpendituresData();
								storageExpendituresData.setStore_name(storeList.get(j1).getStore_name());
								storageExpendituresData.setWarehouse_name(whname);
								storageExpendituresData.setArea_name(areaname);
								storageExpendituresData.setItem_type(itemtypename);
								storageExpendituresData.setSettle_flag(0);
								storageExpendituresData.setStorage_type(ssc_sc_type);

								Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(DateUtil.getMonth(DateUtil.getYesterDay()), Integer.parseInt(cbList.get(i).getSettle_date()));
								storageExpendituresData.setStart_time(balance_cycle.get("balance_start_date").toString());
								storageExpendituresData.setEnd_time(balance_cycle.get("balance_end_date").toString());
								//需要结算的数据
								List<StorageExpendituresData> seiDateList = storageExpendituresDataService.findDate(storageExpendituresData);
								System.out.println(cbList.get(i).getContract_name()+"查到结算数据:"+seiDateList.size()+"条.");
								for (int j = 0; j < seiDateList.size(); j++) {
									//原始数据 存储类型(0:固定，1:面积，2:体积，3:托盘)
									switch (ssc_sc_type) {
									case 0:
										//0固定费用
										Fixed_Cost(seiDateList.get(j),scList.get(j2),cbList.get(i));
										continue;
									case 1:
										//1按面积结算
										Settlement_By_Area(seiDateList.get(j),scList.get(j2),cbList.get(i));
										continue;
									case 2:
										//2按体积结算
										continue;
									case 3:
										//3按商品的体积推算
										continue;
									case 4:
										//4按件数结算
										Settlement_By_Piece(0,seiDateList.get(j),scList.get(j2),cbList.get(i));
										continue;
									case 5:
										//5按件数反推所占面积 	
										Settlement_By_Piece(1,seiDateList.get(j),scList.get(j2),cbList.get(i));
										continue;
									case 6:
										//6按托盘结算 
										Settlement_By_Tray(seiDateList.get(j),scList.get(j2),cbList.get(i));
										continue;
									case 7:
										//7按单个托盘的存放数量推算
										
										continue;
									default:
										continue;
									}
								}
							}
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.get(i).getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.get(i).getId()+"],"+storeList.get(j1).getStore_name()+"没有绑定数据！", null, null, null, null, new Date()));
						}
					}	
				}else{
					//判断时间不再该生效合同周期内，合同修改为失效
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("validity", 0);
					map.put("update_user", BaseConst.SYSTEM_JOB_CREATE);
					map.put("id", cbList.get(i).getId());
					contractBasicinfoService.update_cb_validity(map);
					BalanceErrorLog bEL = new BalanceErrorLog();
					bEL.setContract_id(Integer.valueOf(cbList.get(i).getId()));
					bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
					bEL.setPro_result_info(cbList.get(i).getId()+"合同已过期！");
					expressContractService.addBalanceErrorLog(bEL);
					System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name()+ "-仓储费结算异常,合同过期.");
				}
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name()+ "-仓储费结算异常.");
			}
			System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.get(i).getContract_name()+ "-仓储费结算结束.");
		}
	}
   
	public void ReckonStorageCharge_by_con_id_before(String id, String  dateStr){
		//重新结算的预处理工作 step  1:  修改 tb_storage_expenditures_data表中要重算的月份数据的flag  set  0
		// step 2: 删除已经得出的结论  tb_storage_expenditures_data_settlement中相关合同的结算结果
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(id));
		try {
			//主体
			String contract_owner = cbList.getContract_owner();
			String contract_type = cbList.getContract_type();
			//仓储费信息
			List<StorageCharge> scList = storageChargeService.findByCBID(cbList.getId());
			List<Store> storeList = new ArrayList<Store>();
			if(contract_type.equals("3")){
				Store store = storeServiceImpl.findByContractOwner(contract_owner);
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
				Client client = clientServiceImpl.findByContractOwner(contract_owner);
				List<Store> sList = storeServiceImpl.selectByClient(client.getId());
				if(null!=sList && sList.size()!=0){
					for (int j = 0; j < sList.size(); j++) {
						storeList.add(sList.get(j));
					}
				}else{
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
					return;
				}
			}
			//删除汇总信息
			StorageDataGroup deleteEntity = new StorageDataGroup();
			deleteEntity.setContract_id(Integer.parseInt(id));
			deleteEntity.setBilling_cycle(dateStr);
			storageDataGroupMapper.delete_readyForResettle(deleteEntity);
			//循环客户下的所有店铺
			Date d=new Date();
			for (int j1 = 0; j1 < storeList.size(); j1++) {
				if(null!=scList && scList.size()!=0){
					for (int j2 = 0; j2 < scList.size(); j2++) {
						//合同结算类型[0固定费用 1按面积结算 2按体积结算 3按商品的体积推算 4按件数结算 5按件数反推所占面积 6按托盘结算 7按单个托盘的存放数量推算]
						StorageExpendituresData storageExpendituresData = new StorageExpendituresData();
						storageExpendituresData.setStore_name(storeList.get(j1).getStore_name());
						storageExpendituresData.setUpdate_time(d);
						Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
						//以下为要修改的部分
						storageExpendituresData.setStart_time(balance_cycle.get("balance_start_date").toString());
						storageExpendituresData.setEnd_time(balance_cycle.get("balance_end_date").toString());
						storageExpendituresDatamapper.update_readyForResettle(storageExpendituresData);
						StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
						storageExpendituresDataSettlement.setStore_name(storeList.get(j1).getStore_name());
						storageExpendituresDataSettlement.setStart_time(balance_cycle.get("balance_start_date").toString());
						storageExpendituresDataSettlement.setEnd_time(balance_cycle.get("balance_end_date").toString());
						storageExpendituresDataSettlementMapper.delete_readyForResettle(storageExpendituresDataSettlement);
						//结算数据恢复工作完成
						
					}
					
				}
				
			}	
	
		} catch (Exception e) {}
		System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "-仓储费结算结束.");
		
	}
	
	
	
	public void ReckonStorageCharge_by_con_id(String id,String  dateStr){
		//合同信息
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(id));
			System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "-仓储费结算开始...");
			try {
				if(true){
					//主体
					String contract_owner = cbList.getContract_owner();
					String contract_type = cbList.getContract_type();
					//仓储费信息
					List<StorageCharge> scList = storageChargeService.findByCBID(cbList.getId());
					List<Store> storeList = new ArrayList<Store>();
					if(contract_type.equals("3")){
						Store store = storeServiceImpl.findByContractOwner(contract_owner);
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
						Client client = clientServiceImpl.findByContractOwner(contract_owner);
						List<Store> sList = storeServiceImpl.selectByClient(client.getId());
						if(null!=sList && sList.size()!=0){
							for (int j = 0; j < sList.size(); j++) {
								storeList.add(sList.get(j));
							}
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
							return;
						}
					}
					//循环客户下的所有店铺
					for (int j1 = 0; j1 < storeList.size(); j1++) {
						if(null!=scList && scList.size()!=0){
							for (int j2 = 0; j2 < scList.size(); j2++) {
								//仓库
								String whname = scList.get(j2).getSsc_whs_name();
								//区域
								String areaname = scList.get(j2).getSsc_area_name();
								//类型
								String itemtypename = scList.get(j2).getSsc_item_type_name();
								//合同结算类型[0固定费用 1按面积结算 2按体积结算 3按商品的体积推算 4按件数结算 5按件数反推所占面积 6按托盘结算 7按单个托盘的存放数量推算]
								int ssc_sc_type = scList.get(j2).getSsc_sc_type();
								StorageExpendituresData storageExpendituresData = new StorageExpendituresData();
								storageExpendituresData.setStore_name(storeList.get(j1).getStore_name());
								storageExpendituresData.setWarehouse_name(whname);
								storageExpendituresData.setArea_name(areaname);
								storageExpendituresData.setItem_type(itemtypename);
								storageExpendituresData.setSettle_flag(0);
								storageExpendituresData.setStorage_type(ssc_sc_type);

								Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
								//以下为要修改的部分
								storageExpendituresData.setStart_time(balance_cycle.get("balance_start_date").toString());
								storageExpendituresData.setEnd_time(balance_cycle.get("balance_end_date").toString());
								//需要结算的数据
								List<StorageExpendituresData> seiDateList = storageExpendituresDataService.findDate(storageExpendituresData);
								System.out.println(cbList.getContract_name()+"查到结算数据:"+seiDateList.size()+"条.");
								for (int j = 0; j < seiDateList.size(); j++) {
									//原始数据 存储类型(0:固定，1:面积，2:体积，3:托盘)
									switch (ssc_sc_type) {
									case 0:
										//0固定费用
										Fixed_Cost(seiDateList.get(j),scList.get(j2),cbList);
										continue;
									case 1:
										//1按面积结算
										Settlement_By_Area(seiDateList.get(j),scList.get(j2),cbList);
										continue;
									case 2:
										//2按体积结算
										continue;
									case 3:
										//3按商品的体积推算
										continue;
									case 4:
										//4按件数结算
										Settlement_By_Piece(0,seiDateList.get(j),scList.get(j2),cbList);
										continue;
									case 5:
										//5按件数反推所占面积 	
										Settlement_By_Piece(1,seiDateList.get(j),scList.get(j2),cbList);
										continue;
									case 6:
										//6按托盘结算 
										Settlement_By_Tray(seiDateList.get(j),scList.get(j2),cbList);
										continue;
									case 7:
										//7按单个托盘的存放数量推算
										
										continue;
									default:
										continue;
									}
								}
							}
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+storeList.get(j1).getStore_name()+"没有绑定数据！", null, null, null, null, new Date()));
						}
					}	
				}
				
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "-仓储费结算异常.");
			}
			System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "-仓储费结算结束.");
		
	}
	
	/**
	 * 
	 * @Description: TODO(仓储费汇总)
	 * @param cb
	 * @param ym
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午1:59:02
	 */
	public void reckonStorageGroup(ContractBasicinfo cb, String ym) throws Exception {
		List<StorageCharge> scList= storageChargeService.findByCBID(cb.getId());
		List<Store> storeList= new ArrayList<Store>();
		//主体
		String contract_owner= cb.getContract_owner();
		String contract_type= cb.getContract_type();
		if(contract_type.equals("3")){
			Store store= storeServiceImpl.findByContractOwner(contract_owner);
			if(null!= store){
				storeList.add(store);
			}else{
				BalanceErrorLog bEL = new BalanceErrorLog();
				bEL.setContract_id(Integer.valueOf(cb.getId()));
				bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
				bEL.setPro_result_info("合同ID["+ cb.getId()+ "],店铺不存在！");
				expressContractService.addBalanceErrorLog(bEL);
			}
		}else if(contract_type.equals("4")){
			Client client = clientServiceImpl.findByContractOwner(contract_owner);
			List<Store> sList = storeServiceImpl.selectByClient(client.getId());
			if(null!=sList && sList.size()!=0){
				for (int j = 0; j < sList.size(); j++) {
					storeList.add(sList.get(j));
					
				}
				
			} else {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cb.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+ cb.getId()+ "],"+ client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
				
			}
			
		}
		//循环客户下的所有店铺
		if(null!=scList && scList.size()!=0){
			for (int j2 = 0; j2 < scList.size(); j2++) {
				//仓库
				String whname = scList.get(j2).getSsc_whs_name();
				//区域
				String areaname = scList.get(j2).getSsc_area_name();
				//类型
				String itemtypename = scList.get(j2).getSsc_item_type_name();
				StorageDataGroup sdg = new StorageDataGroup();
				sdg.setContract_id(Integer.valueOf(cb.getId()));
				sdg.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
				List<StorageDataGroup> sdgList = storageDataGroupServiceImpl.findBySDG(sdg);
				if(sdgList.size()==0){
					//仓储费信息
					//仓库
					//需要结算的数据
					List<StorageExpendituresDataSettlement> list = new ArrayList<>();
					for (int j1 = 0; j1 < storeList.size(); j1++) {
						StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
						storageExpendituresDataSettlement.setWarehouse_name(whname);
						storageExpendituresDataSettlement.setArea_name(areaname);
						storageExpendituresDataSettlement.setItem_type(itemtypename);
						storageExpendituresDataSettlement.setStore_name(storeList.get(j1).getStore_name());
						Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(ym, Integer.parseInt(cb.getSettle_date()));
						storageExpendituresDataSettlement.setStart_time(balance_cycle.get("balance_start_date").toString());
						storageExpendituresDataSettlement.setEnd_time(balance_cycle.get("balance_end_date").toString());
						List<StorageExpendituresDataSettlement> lists = storageExpendituresDataSettlementService.findBySEDS(storageExpendituresDataSettlement);
						for (int j = 0; j < lists.size(); j++) {
							list.add(lists.get(j));
							
						}
						
					}
					BigDecimal sumMouth= new BigDecimal(0.00);
					BigDecimal sumQTY= new BigDecimal(0.00);
					String unit= "暂无";
					for (int j = 0; j < list.size(); j++) {
						BigDecimal day = list.get(j).getStorage_fee();
						BigDecimal qty = list.get(j).getStorage_acreage();
						sumMouth = sumMouth.add(day);
						sumQTY = sumQTY.add(qty);
						unit = list.get(j).getAcreage_unit();
					}
					StorageDataGroup saveEntity = new StorageDataGroup();
					int ssc_sc_type = scList.get(0).getSsc_sc_type();
					switch (ssc_sc_type) {
						case 0:
							//0固定费用
							saveEntity.setFixed_qty(sumQTY);
							saveEntity.setFixed_cost(sumMouth);
							saveEntity.setFixed_unit(unit);
							break;
						case 1:
							//1按面积结算
							saveEntity.setArea_qty(sumQTY);
							saveEntity.setArea_cost(sumMouth);
							saveEntity.setArea_costunit(unit);
							break;
						case 2:
							//2按体积结算
							break;
						case 3:
							//3按商品的体积推算
							break;
						case 4:
							//4按件数结算
							saveEntity.setPiece_qty(sumQTY);
							saveEntity.setPiece_cost(sumMouth);
							saveEntity.setPiece_unit(unit);
							break;
						case 5:
							//5按件数反推所占面积 
							saveEntity.setPiece_qty(sumQTY);
							saveEntity.setPiece_cost(sumMouth);
							saveEntity.setPiece_unit(unit);
							break;
						case 6:
							//6按托盘结算 
							saveEntity.setTray_qty(sumQTY);
							saveEntity.setTray_cost(sumMouth);
							saveEntity.setTray_qtyunit(unit);
							break;
						case 7:
							//7按单个托盘的存放数量推算
							saveEntity.setTray_qty(sumQTY);
							saveEntity.setTray_cost(sumMouth);
							saveEntity.setTray_qtyunit(unit);
							break;
						default:
							break;
					}
					saveEntity.setCreate_time(new Date());
					String batch= DateUtil.getYear(new Date())+""+DateUtil.getMonth(new Date())+cb.getId();
					saveEntity.setBatch(batch);
					saveEntity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
					saveEntity.setContract_id(Integer.valueOf(cb.getId()));
					saveEntity.setBilling_cycle(DateUtil.getMonth(DateUtil.getYesterDay()));
					//汇总数据
					if(sumMouth.compareTo(new BigDecimal(0.00)) != 0){
						storageDataGroupServiceImpl.saveAUpdate(saveEntity, list);
						
					}
					
				}
				
			}
			
		}
	
	}
	
	/**
	 * @Title: Fixed_Cost 
	 * @Description: TODO(固定费用结算)
	 * @param 设定文件 
	 * @return void
	 * 返回类型 @throws
	 */
	private void Fixed_Cost(StorageExpendituresData storageExpendituresData,StorageCharge sc,ContractBasicinfo cb) {
		//固定费用价格
		String ssc_fixedcost = sc.getSsc_fixedcost();
		//固定费用
		BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
		BigDecimal multiply = new BigDecimal(1);
		//固定费用 * 1/月
		multiply = multiply.multiply(fixedcost);
		StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
		try {
			storageExpendituresDataSettlement.setCreate_time(new Date());
			storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlement.setStorage_fee(multiply);
			if(cb.getContract_type().equals("3")){
				Store store = storeServiceImpl.selectById(Integer.valueOf(cb.getContract_owner()));
				storageExpendituresDataSettlement.setStore_name(store.getStore_name());
			}else{
				
			}

			storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
			storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
			storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
			storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
			storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
			storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
			storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
			storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
			//插入明细记录
			storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
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
	private void Settlement_By_Area(StorageExpendituresData storageExpendituresData,StorageCharge sc,ContractBasicinfo cb) {
		if (sc.getSsc_occupied_area_type() == 1) {
			// 无阶梯
			String ssc_fixedcost = sc.getSsc_area_price();
			//固定费用
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//固定费用 * 1/月
			multiply = multiply.multiply(fixedcost);
			StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
			try {
				storageExpendituresDataSettlement.setCreate_time(new Date());
				storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlement.setStorage_fee(multiply);
				storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
				//插入明细记录
				storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
				storageExpendituresData.setSettle_flag(1);
				//修改原始数据状态
				storageExpendituresDataService.update(storageExpendituresData);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
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
			List<Map<String, Object>> totalAreaList = totalAreaServiceImpl.findByCBID(tableid);
			countCGBFData(totalAreaList, "sta_section", "sta_price", multiply, storageExpendituresData, sc);
		} else if (sc.getSsc_occupied_area_type() == 3) {
			// 总占用面积阶梯
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_total_area_tableid();
			List<Map<String, Object>> aaList = allAreaServiceImpl.findByCBID(tableid);
			countALLData(aaList, "saa_section", "saa_price", multiply, storageExpendituresData, sc);;
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
	private void Settlement_By_Piece(Integer type,StorageExpendituresData storageExpendituresData,StorageCharge sc,ContractBasicinfo cb) {
		if (type == 0) {
			//按件数结算
			String ssc_fixedcost = sc.getSsc_number_price();
			//件数单价
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//件数单价 * 原始数据值
			multiply = multiply.multiply(fixedcost).setScale(2,BigDecimal.ROUND_HALF_UP);
			StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
			try {
				storageExpendituresDataSettlement.setCreate_time(new Date());
				storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlement.setStorage_fee(multiply);
				storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
				//插入明细记录
				storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
				storageExpendituresData.setSettle_flag(1);
				//修改原始数据状态
				storageExpendituresDataService.update(storageExpendituresData);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
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
			StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
			try {
				storageExpendituresDataSettlement.setCreate_time(new Date());
				storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlement.setStorage_fee(multiply);
				storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
				//插入明细记录
				storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
				storageExpendituresData.setSettle_flag(1);
				//修改原始数据状态
				storageExpendituresDataService.update(storageExpendituresData);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
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
	private void Settlement_By_Tray(StorageExpendituresData storageExpendituresData,StorageCharge sc,ContractBasicinfo cb) {
		if (sc.getSsc_tray_balance_type() == 1) {
			// 无阶梯
			String ssc_fixedcost = sc.getSsc_tray_price();
			BigDecimal fixedcost = new BigDecimal(ssc_fixedcost);
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			multiply = multiply.multiply(fixedcost).setScale(2,BigDecimal.ROUND_HALF_UP);
			StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
			try {
				storageExpendituresDataSettlement.setCreate_time(new Date());
				storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
				storageExpendituresDataSettlement.setStorage_fee(multiply);
				storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
				storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
				storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
				storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
				storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
				storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
				storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
				storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
				//插入明细记录
				storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
				storageExpendituresData.setSettle_flag(1);
				//修改原始数据状态
				storageExpendituresDataService.update(storageExpendituresData);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				try {
					expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
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
			List<Map<String,Object>> lists =  totalTrayServiceImpl.findByCBID(tableid);
			countCGBFData(lists, "stt_section", "stt_price", multiply, storageExpendituresData, sc);
		} else if (sc.getSsc_tray_balance_type() == 3) {
			// 总占用面积阶梯
			BigDecimal multiply = storageExpendituresData.getStorage_acreage();
			//表格ID
			String tableid = sc.getSsc_over_part_tray_tableid();
			List<Map<String,Object>> lists = allTrayServiceImpl.findByCBID(tableid);
			countALLData(lists, "sat_section", "sat_price", multiply, storageExpendituresData, sc);;
		}
	
	}
	
	/** 
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
	private void countCGBFData(List<Map<String, Object>> list,String section,String price,BigDecimal multiply,StorageExpendituresData storageExpendituresData1,StorageCharge sc){
		
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
								insert(storageExpendituresData1, add, multiply,sc);
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
	
	private void insert(StorageExpendituresData storageExpendituresData1,BigDecimal add,BigDecimal multiply,StorageCharge sc){
		try {
			StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
			storageExpendituresDataSettlement.setCreate_time(new Date());
			storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlement.setStorage_fee(add);
			storageExpendituresDataSettlement.setStore_name(storageExpendituresData1.getStore_name());
			storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData1.getWarehouse_name());
			storageExpendituresDataSettlement.setArea_name(storageExpendituresData1.getArea_name());
			storageExpendituresDataSettlement.setItem_type(storageExpendituresData1.getItem_type());
			storageExpendituresDataSettlement.setStorage_acreage(multiply);
			storageExpendituresDataSettlement.setStart_time(storageExpendituresData1.getStart_time());
			storageExpendituresDataSettlement.setStorage_type(storageExpendituresData1.getStorage_type());
			storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData1.getAcreage_unit());
			storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData1.getStorage_acreage());
			//插入明细记录
			storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
			storageExpendituresData1.setSettle_flag(1);
			//修改原始数据状态
			storageExpendituresDataService.update(storageExpendituresData1);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/** 
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
	private void countALLData(List<Map<String, Object>> list,String section,String price,BigDecimal multiply,StorageExpendituresData storageExpendituresData,StorageCharge sc){
		StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
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
			storageExpendituresDataSettlement.setCreate_time(new Date());
			storageExpendituresDataSettlement.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
			storageExpendituresDataSettlement.setStorage_fee(multiply.multiply(new BigDecimal(priceA)).setScale(2,BigDecimal.ROUND_HALF_UP));
			storageExpendituresDataSettlement.setStore_name(storageExpendituresData.getStore_name());
			storageExpendituresDataSettlement.setWarehouse_name(storageExpendituresData.getWarehouse_name());
			storageExpendituresDataSettlement.setArea_name(storageExpendituresData.getArea_name());
			storageExpendituresDataSettlement.setItem_type(storageExpendituresData.getItem_type());
			storageExpendituresDataSettlement.setStorage_acreage(multiply);
			storageExpendituresDataSettlement.setStart_time(storageExpendituresData.getStart_time());
			storageExpendituresDataSettlement.setStorage_type(storageExpendituresData.getStorage_type());
			storageExpendituresDataSettlement.setAcreage_unit(storageExpendituresData.getAcreage_unit());
			storageExpendituresDataSettlement.setStorage_acreage(storageExpendituresData.getStorage_acreage());
			//插入明细记录
			storageExpendituresDataSettlementService.save(storageExpendituresDataSettlement);
			storageExpendituresData.setSettle_flag(1);
			//修改原始数据状态
			storageExpendituresDataService.update(storageExpendituresData);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			try {
				expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, sc.getSsc_cb_id(), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+sc.getSsc_cb_id()+"],插入数据异常！", null, null, null, null, new Date()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	public void ReReckonStorageGroup(String con_id,String dateStr){
		//合同信息 where (contract_type=3 or contract_type=4)
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
			try {
			//	if(DateUtil.judgeSummaryOrNot(Integer.parseInt(cbList.getSettle_date()))){
					
					List<StorageCharge> scList = storageChargeService.findByCBID(cbList.getId());
					List<Store> storeList = new ArrayList<Store>();
					//主体
					String contract_owner = cbList.getContract_owner();
					String contract_type = cbList.getContract_type();
					if(contract_type.equals("3")){
						Store store = storeServiceImpl.findByContractOwner(contract_owner);
						if(null!=store){
							storeList.add(store);
						}else{
							BalanceErrorLog bEL = new BalanceErrorLog();
							bEL.setContract_id(Integer.valueOf(cbList.getId()));
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
							expressContractService.addBalanceErrorLog(bEL);
						}
					}else if(contract_type.equals("4")){
						Client client = clientServiceImpl.findByContractOwner(contract_owner);
						List<Store> sList = storeServiceImpl.selectByClient(client.getId());
						if(null!=sList && sList.size()!=0){
							for (int j = 0; j < sList.size(); j++) {
								storeList.add(sList.get(j));
							}
						}else{
							expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
						}
					}
					//循环客户下的所有店铺
					if(null!=scList && scList.size()!=0){
						for (int j2 = 0; j2 < scList.size(); j2++) {
							//仓库
							String whname = scList.get(j2).getSsc_whs_name();
							//区域
							String areaname = scList.get(j2).getSsc_area_name();
							//类型
							String itemtypename = scList.get(j2).getSsc_item_type_name();
							StorageDataGroup sdg = new StorageDataGroup();
							sdg.setContract_id(Integer.valueOf(cbList.getId()));
							sdg.setBilling_cycle(dateStr);
							List<StorageDataGroup> sdgList = storageDataGroupServiceImpl.findBySDG(sdg);
							if(sdgList.size()==0){
								//仓储费信息
								//仓库
								//需要结算的数据
								List<StorageExpendituresDataSettlement> list = new ArrayList<>();
								for (int j1 = 0; j1 < storeList.size(); j1++) {
									StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
									storageExpendituresDataSettlement.setWarehouse_name(whname);
									storageExpendituresDataSettlement.setArea_name(areaname);
									storageExpendituresDataSettlement.setItem_type(itemtypename);
									storageExpendituresDataSettlement.setStore_name(storeList.get(j1).getStore_name());

									Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
									storageExpendituresDataSettlement.setStart_time(balance_cycle.get("balance_start_date").toString());
									storageExpendituresDataSettlement.setEnd_time(balance_cycle.get("balance_end_date").toString());
									List<StorageExpendituresDataSettlement> lists = storageExpendituresDataSettlementService.findBySEDS(storageExpendituresDataSettlement);
									for (int j = 0; j < lists.size(); j++) {
										list.add(lists.get(j));
									}
								}
								BigDecimal sumMouth = new BigDecimal(0.00);
								BigDecimal sumQTY = new BigDecimal(0.00);
								String unit = "暂无";
								for (int j = 0; j < list.size(); j++) {
									BigDecimal day = list.get(j).getStorage_fee();
									BigDecimal qty = list.get(j).getStorage_acreage();
									sumMouth = sumMouth.add(day);
									sumQTY = sumQTY.add(qty);
									unit = list.get(j).getAcreage_unit();
								}
								StorageDataGroup saveEntity = new StorageDataGroup();
								int ssc_sc_type = scList.get(0).getSsc_sc_type();
								switch (ssc_sc_type) {
									case 0:
										//0固定费用
										saveEntity.setFixed_qty(sumQTY);
										saveEntity.setFixed_cost(sumMouth);
										saveEntity.setFixed_unit(unit);
										break;
									case 1:
										//1按面积结算
										saveEntity.setArea_qty(sumQTY);
										saveEntity.setArea_cost(sumMouth);
										saveEntity.setArea_costunit(unit);
										break;
									case 2:
										//2按体积结算
										break;
									case 3:
										//3按商品的体积推算
										break;
									case 4:
										//4按件数结算
										saveEntity.setPiece_qty(sumQTY);
										saveEntity.setPiece_cost(sumMouth);
										saveEntity.setPiece_unit(unit);
										break;
									case 5:
										//5按件数反推所占面积 
										saveEntity.setPiece_qty(sumQTY);
										saveEntity.setPiece_cost(sumMouth);
										saveEntity.setPiece_unit(unit);
										break;
									case 6:
										//6按托盘结算 
										saveEntity.setTray_qty(sumQTY);
										saveEntity.setTray_cost(sumMouth);
										saveEntity.setTray_qtyunit(unit);
										break;
									case 7:
										//7按单个托盘的存放数量推算
										saveEntity.setTray_qty(sumQTY);
										saveEntity.setTray_cost(sumMouth);
										saveEntity.setTray_qtyunit(unit);
										break;
									default:
										break;
								}
								saveEntity.setCreate_time(new Date());
								String batch = dateStr.split("-")[0]+dateStr.split("-")[1]+cbList.getId();
								saveEntity.setBatch(batch);
								saveEntity.setCreate_user(BaseConst.SYSTEM_CREATE_USER);
								saveEntity.setContract_id(Integer.valueOf(cbList.getId()));
								saveEntity.setBilling_cycle(dateStr);
								//汇总数据
								if(sumMouth.compareTo(new BigDecimal(0.00))!=0){
									storageDataGroupServiceImpl.saveAUpdate(saveEntity, list);
								}
							}
						}
					}
//				}else{
//					//判断时间不再该生效合同周期内，合同修改为失效	
//					Map<String, Object> errorMap = new HashMap<String, Object>();
//					errorMap.put("validity", 1);
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
			logger.error(e);
			e.printStackTrace();
		}
			
	}
	public boolean  checkDataForResettle_warsehoueCharge(String con_id,String dateStr){
		//检测仓储费结算和汇总数据是否已经完全初始化
		ContractBasicinfo cbList = contractBasicinfoService.findById(Integer.parseInt(con_id));
		try {
			if(true){
				//主体
				String contract_owner = cbList.getContract_owner();
				String contract_type = cbList.getContract_type();
				//仓储费信息
				List<StorageCharge> scList = storageChargeService.findByCBID(cbList.getId());
				List<Store> storeList = new ArrayList<Store>();
				if(contract_type.equals("3")){
					Store store = storeServiceImpl.findByContractOwner(contract_owner);
					if(null!=store){
						storeList.add(store);
					}else{
						BalanceErrorLog bEL = new BalanceErrorLog();
						bEL.setContract_id(Integer.valueOf(cbList.getId()));
						bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
						bEL.setPro_result_info("合同ID["+cbList.getId()+"],店铺不存在！");
						expressContractService.addBalanceErrorLog(bEL);
					}
				}else if(contract_type.equals("4")){
					Client client = clientServiceImpl.findByContractOwner(contract_owner);
					List<Store> sList = storeServiceImpl.selectByClient(client.getId());
					if(null!=sList && sList.size()!=0){
						for (int j = 0; j < sList.size(); j++) {
							storeList.add(sList.get(j));
						}
					}else{
						expressContractService.addBalanceErrorLog(new BalanceErrorLog(0, null, Integer.valueOf(cbList.getId()), BaseConst.PRO_RESULT_FLAG_ERROR, "合同ID["+cbList.getId()+"],"+client.getClient_name()+"客户没有绑定店铺！", null, null, null, null, new Date()));
					}
				}
				//删除汇总信息
				StorageDataGroup sdg = new StorageDataGroup();
				sdg.setContract_id(Integer.parseInt(con_id));
				sdg.setBilling_cycle(dateStr);
				List<StorageDataGroup> sdgList = storageDataGroupServiceImpl.findBySDG(sdg);
				if(sdgList!=null&&sdgList.size()!=0)return false;
				//循环客户下的所有店铺
				for (int j1 = 0; j1 < storeList.size(); j1++) {
					if(null!=scList && scList.size()!=0){
						for (int j2 = 0; j2 < scList.size(); j2++) {
							//仓库
							String whname = scList.get(j2).getSsc_whs_name();
							//区域
							String areaname = scList.get(j2).getSsc_area_name();
							//类型
							String itemtypename = scList.get(j2).getSsc_item_type_name();
							//合同结算类型[0固定费用 1按面积结算 2按体积结算 3按商品的体积推算 4按件数结算 5按件数反推所占面积 6按托盘结算 7按单个托盘的存放数量推算]
							int ssc_sc_type = scList.get(j2).getSsc_sc_type();
							StorageExpendituresData storageExpendituresData = new StorageExpendituresData();
							storageExpendituresData.setStore_name(storeList.get(j1).getStore_name());
							storageExpendituresData.setWarehouse_name(whname);
							storageExpendituresData.setArea_name(areaname);
							storageExpendituresData.setItem_type(itemtypename);
							storageExpendituresData.setSettle_flag(1);
							storageExpendituresData.setStorage_type(ssc_sc_type);

							Map<String, Object> balance_cycle = DateUtil.getBalanceCycle(dateStr, Integer.parseInt(cbList.getSettle_date()));
							storageExpendituresData.setStart_time(balance_cycle.get("balance_start_date").toString());
							storageExpendituresData.setEnd_time(balance_cycle.get("balance_end_date").toString());
							//需要结算的数据
							List<StorageExpendituresData> seiDateList = storageExpendituresDataService.findDate(storageExpendituresData);
							if(seiDateList!=null&&seiDateList.size()!=0)return false;
							StorageExpendituresDataSettlement storageExpendituresDataSettlement = new StorageExpendituresDataSettlement();
							storageExpendituresDataSettlement.setArea_name(areaname);
							storageExpendituresDataSettlement.setItem_type(itemtypename);
							storageExpendituresDataSettlement.setStore_name(storeList.get(j1).getStore_name());
							storageExpendituresDataSettlement.setStorage_type(ssc_sc_type);
							storageExpendituresDataSettlement.setWarehouse_name(whname);
							storageExpendituresDataSettlement.setStart_time(balance_cycle.get("balance_start_date").toString());
							storageExpendituresDataSettlement.setEnd_time(balance_cycle.get("balance_end_date").toString());
							List<StorageExpendituresDataSettlement> lists = storageExpendituresDataSettlementService.findBySEDS(storageExpendituresDataSettlement);
							//结算数据恢复工作完成
							if(lists!=null&&lists.size()!=0)return false;

						}
					}
					
				}
				
			}

		} catch (Exception e) {return false;}
		System.out.println("时间：［" + DateUtil.formatS(new Date()) + "］ " + cbList.getContract_name()+ "-仓储费结算结束.");
		return true;
		
	}
	
}