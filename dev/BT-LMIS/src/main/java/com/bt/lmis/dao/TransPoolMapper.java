
package com.bt.lmis.dao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ErCalMasterParam;
import com.bt.lmis.controller.form.ErExpressinfoAasterParam;
import com.bt.lmis.controller.form.ErExpressinfoDetailParam;
import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.controller.form.TbInvitationRealuseanmountDataParam;
import com.bt.lmis.controller.form.TbInvitationUseanmountDataParam;
import com.bt.lmis.controller.form.TbOperationfeeDataParam;
import com.bt.lmis.controller.form.TbStorageExpendituresDataParam;
import com.bt.lmis.controller.form.TransPoolQueryParam;
import com.bt.lmis.controller.form.WarehouseExpressDataParam;
import com.bt.lmis.model.ErCalMaster;
import com.bt.lmis.model.ErExpressinfoAaster;
import com.bt.lmis.model.ErExpressinfoDetail;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.SettleInvitationBean;
import com.bt.lmis.model.SettleOperatorBean;
import com.bt.lmis.model.SettleOrderBean;
import com.bt.lmis.model.SettleOrderDetailBean;
import com.bt.lmis.model.SettleStorageBean;
import com.bt.lmis.model.SettleUseBean;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.TbInvitationRealuseanmountData;
import com.bt.lmis.model.TbInvitationUseanmountData;
import com.bt.lmis.model.TbOperationfeeData;
import com.bt.lmis.model.TbStorageExpendituresData;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.page.QueryParameter;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.model.WkType;

/**
 * 
* @ClassName: TransPoolMapper 
* @Description: TODO(物流运费汇总报表) 
* @author likun
* @date 2016年8月25日 上午9:27:24 
* 
* @param <T>
 */
public interface TransPoolMapper<T> extends BaseMapper<T>{
	public List<Map<String,Object>>getContract(Map<String, Object>  param);
	public List<T> findAllData(QueryParameter queryParameter);
	public Integer selectCount(QueryParameter queryParameter);
	public Map findAllData(Map param);
	public List<T> findAllDataByGt(QueryParameter queryParameter);
	public Integer selectCountByGt(QueryParameter queryParameter);
	
	public List<T> getPoolMain(QueryParameter queryParameter);
	public Integer selectCountPoolMain(QueryParameter queryParameter);
	public List<SettleInvitationBean> querySettleInvitation(TransPoolQueryParam queryParam);
	public List<SettleOperatorBean> querySettleOperator(TransPoolQueryParam queryParam);
	public List<SettleOrderBean> querySettleOrder(TransPoolQueryParam queryParam);
	public List<SettleOrderDetailBean> queryOrderDetail(TransPoolQueryParam queryParam);
	public List<SettleStorageBean> queryStorage(TransPoolQueryParam queryParam);
	public List<SettleUseBean> queryUse(TransPoolQueryParam queryParam);
	public List<WarehouseExpressData> queryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public int countQueryExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public List<ErCalMaster> queryErCalMasterAll(ErCalMasterParam erCalMasterPar);
	public int countQueryErCalMasterAll(ErCalMasterParam erCalMasterPar);
	public List<TbInvitationUseanmountData> querySuppliesDetailAll(
			TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar);
	public int countQuerySuppliesDetailAll(TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar);
	public List<TbOperationfeeData> queryOperatingCostAll(TbOperationfeeDataParam tbOperationfeeDataPar);
	public int countOperatingCostAll(TbOperationfeeDataParam tbOperationfeeDataPar);
	public List<TbStorageExpendituresData> queryWarehousingAll(
			TbStorageExpendituresDataParam tbStorageExpendituresDataPar);
	public int countWarehousingtAll(TbStorageExpendituresDataParam tbStorageExpendituresDataPar);
	public List<ErExpressinfoDetail> queryExpressInforAll(ErExpressinfoDetailParam erExpressinfoDetailPar);
	public int countExpressInforAll(ErExpressinfoDetailParam erExpressinfoDetailPar);
	public List<ErExpressinfoAaster> queryRadarMainAll(ErExpressinfoAasterParam erExpressinfoAasterPar);
	public int countRadarMainAll(ErExpressinfoAasterParam erExpressinfoAasterPar);
	public List<ErCalMaster> queryRadarAll(ErCalMasterParam erCalMasterPar);
	public int countRadarAll(ErCalMasterParam erCalMasterPar);
	public List<WarehouseExpressData> queryRawExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public int countRawExpressAll(WarehouseExpressDataParam warehouseExpressDataPar);
	public List<TbInvitationRealuseanmountData> querySuppliesAmountAll(
			TbInvitationRealuseanmountDataParam tbInvitationRealuseanmountDataPar);
	public int countSuppliesAmountAll(TbInvitationRealuseanmountDataParam tbInvitationRealuseanmountDataPar);
	public List<?> warehousingExport(Map<String, Object> param);
	public List<Map<String, Object>> queryRadarExport(@Param("waybill")String waybill, @Param("warehouse_code")String warehouse_code);
	public List<Map<String, Object>> findInvitationUseanmount(@Param("store_name")String store_name, @Param("inbound_no")String inbound_no, @Param("bz_number")String bz_number,
			@Param("item_name")String item_name);
	public List<Map<String, Object>> findOperatingCostExport(@Param("store_name")String store_name, @Param("job_orderno")String job_orderno, @Param("art_no")String art_no,
			@Param("inventory_status")String inventory_status);
	public List<Map<String, Object>> queryExpressExport(@Param("store_name")String store_name, @Param("cost_center")String cost_center, @Param("store_code")String store_code,
			@Param("create_time")String create_time, @Param("update_time")String update_time);
	public List<Map<String, Object>> findRadarMainExport(@Param("store_code")String store_code, @Param("warehouse_code")String warehouse_code, @Param("waybill")String waybill,
			@Param("create_time")String create_time, @Param("update_time")String update_time);
	public List<Map<String, Object>> QueryexpressInformationExport(@Param("barcode")String barcode, @Param("waybill")String waybill, @Param("item_name")String item_name,
			@Param("create_time")String create_time, @Param("update_time")String update_time);
	public List<Map<String, Object>> findSuppliesAmountExport(@Param("store_name")String store_name, @Param("sku_code")String sku_code, @Param("cost_center")String cost_center,
			@Param("sku_name")String sku_name, @Param("create_time")String create_time, @Param("update_time")String update_time);
	public List<Map<String, Object>> findInvitationUseanmount(@Param("store_name")String store_name, @Param("inbound_no")String inbound_no, @Param("bz_number")String bz_number,
			@Param("item_name")String item_name, @Param("create_time")String create_time, @Param("update_time")String update_time);
	public List<Group> querysGroup(GroupParam groupPar);
	public int countsGroup(GroupParam groupPar);
	public void updateStatus(@Param("id")String id, @Param("status")String status);
	public void deleteGroup(String id);
	public void saveGroup(Group groupPar);
	public ArrayList<?> findWorkOrderType();
	public Object addwkGroup(Map<String, Object> paramMap);
	public ArrayList<?> queryWorkOrderType(Map<String, Object> paramMap);
	public void delWorkOrder(Map<String, Object> paramMap);
	public ArrayList<?> findStore();
	public ArrayList<?> findStore(Map<String, Object> paramMap);
	public ArrayList<?> queryStore(Map<String, Object> paramMap);
	public void deleteStoreAndGroup(Map<String, Object> paramMap);
	public ArrayList<?> queryTransportVendorGroup(Map<String, Object> paramMap);
	public ArrayList<?> queryLogisticsCode();
	public void deleteVendorGroup(Map<String, Object> paramMap);
	public void updateWkGroup(Map<String, Object> paramMap);
	public ArrayList<?> queryWkGroupSup();
	public WkType findLevelById(Map<String, Object> map);
	public void addsGroupModel(Map<String, Object> pram);
	public void updateGroupModel(Map<String, Object> pram);
	public void updateStoreGroupPage(Map<String, Object> paramMap);
	public void updateTransportVendor(Map<String, Object> paramMap);
	public int checkCountWork(Map<String, Object> pram);
	public ArrayList<?> queryfindLevelAndJb(@Param("id")String id);
	public Object addsGroupModelisNull(Map<String, Object> pram);
	public List<TransportVendor> queryLogisticsCodeName();
	public void addTransportVendorGroup(String transportName, String g_id);
	public int checkCountTransportName(String transportName);
	public List<Store> queryStoreCode();
	public int checkCountStoreCode(String storeCode);
	public void addtbStore(String storeCode, String g_id);
	public List<WkType> queryWorkOrderName();
	public int checkCountWkName(String name);
	public int queryWkTypeById(String name);
	public List<?> findwkTypeAndwkLevel(String id);
	public void addsGroupModelAndMost(String name, String g_id, int level);
	public void addStoreGroup(@Param("id")String id,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by, @Param("update_time")String update_time, @Param("group")int group, @Param("selfwarehouse")String selfwarehouse,
			@Param("outsourcedwarehouse")String outsourcedwarehouse, @Param("store")String store);
	public void updateStoreGroupPage(@Param("id")String id,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by, @Param("update_time")String update_time, @Param("group")int group, @Param("selfwarehouse")String selfwarehouse,
			@Param("outsourcedwarehouse")String outsourcedwarehouse, @Param("store")String store);
	public int checkWoGroupStorepower(@Param("outsourcedwarehouse")String outsourcedwarehouse, @Param("selfwarehouse")String selfwarehouse, @Param("store")String store,@Param("group")int group);
	public void addTransportVendor(@Param("id")String id, @Param("create_by")String create_by, @Param("update_by")String update_by, @Param("group")int group, @Param("carrier")String carrier,
			@Param("wo_type")String wo_type, @Param("wo_level")String wo_level);
	public void deleteGroupWorkPower(@Param("group")int group, @Param("carrier")String carrier);
	public ArrayList<?> displayWorkOrder(Map<String, Object> paramMap);
	public ArrayList<?> queryWkGroupSupAndUpdate(@Param("id")String id);
	public List<?> workOrderAndLevel(@Param("code")String code);
	public ArrayList<?> querySeniorQueryGroupSup();
	public Group querysGroupById(Map<String, Object> paramMap);
	public int dindwoGroupWorkPower(@Param("gId")String gId, @Param("tCode")String tCode);
	public int checkCountWorkCode(Map<String, Object> pram);
	public List<GroupWorkPower> queryWorkPower(@Param("carrier")String carrier,@Param("group")int group);
	public Object addTransportVendorTime(@Param("id")String id, @Param("create_by")String create_by, @Param("create_time")Date create_time, @Param("update_by")String update_by, @Param("group")int group,
			@Param("carrier")String carrier, @Param("wo_type")String wo_type, @Param("wo_level")String wo_level);
	public JSONObject findStoreAll();
	public Object findLogtics(@Param("id")String id);
	public void delWorkPower(@Param("id")String id);
	public void delStorePower(@Param("id")String id);
	/**
	 * @param map
	 * @return
	 */
	public List<Group> querysGroup2(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	public int countsGroup2(Map<String, Object> map);
	
}
