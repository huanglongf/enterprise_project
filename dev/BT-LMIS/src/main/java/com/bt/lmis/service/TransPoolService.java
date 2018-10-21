package com.bt.lmis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
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
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.model.WkType;

/**
 * 
* @ClassName: TransPoolService 
* @Description: TODO(物流运费汇总报表) 
* @author likun
* @date 2016年8月25日 上午9:25:48 
* 
* @param <T>
 */
public interface TransPoolService<T> extends BaseService<T>{
	public QueryResult<T> findMainData(QueryParameter qr);
	public QueryResult<T> findAllData(QueryParameter qr);
	public QueryResult<T> findAllDataForGt(QueryParameter qr);

	public QueryResult<SettleOperatorBean> querySettleOperator(TransPoolQueryParam queryParam);
	public QueryResult<SettleOrderBean> querySettleOrder(TransPoolQueryParam queryParam);
	public QueryResult<SettleOrderDetailBean> queryOrderDetail(TransPoolQueryParam queryParam);
	public QueryResult<SettleStorageBean> queryStorage(TransPoolQueryParam queryParam);
	public QueryResult<SettleUseBean> queryUse(TransPoolQueryParam queryParam);
	public QueryResult<WarehouseExpressData> queryExpressAllTj(WarehouseExpressDataParam warehouseExpressDataPar);
	public QueryResult<SettleInvitationBean> querySettleInvitation(TransPoolQueryParam queryParam);
	public QueryResult<TbInvitationUseanmountData> querySuppliesDetail(
			TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar);
	public QueryResult<TbOperationfeeData> operatingCostDetail(TbOperationfeeDataParam tbOperationfeeDataPar);
	public QueryResult<TbStorageExpendituresData> queryWarehousing(
			TbStorageExpendituresDataParam tbStorageExpendituresDataPar);
	public QueryResult<ErExpressinfoDetail> queryExpressInfor(ErExpressinfoDetailParam erExpressinfoDetailPar);
	public QueryResult<ErExpressinfoAaster> queryRadarMain(ErExpressinfoAasterParam erExpressinfoAasterPar);
	public QueryResult<ErCalMaster> queryRadar(ErCalMasterParam erCalMasterPar);
	public QueryResult<WarehouseExpressData> queryRawExpress(WarehouseExpressDataParam warehouseExpressDataPar);
	public QueryResult<TbInvitationRealuseanmountData> querySuppliesAmount(
			TbInvitationRealuseanmountDataParam tbInvitationRealuseanmountDataPar);
	public List<?> warehousingExport(Map<String, Object> param);
	public List<Map<String, Object>> queryRadarExport(@Param("waybill")String waybill, @Param("warehouse_code")String warehouse_code);
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
	public QueryResult<Group> querysGroup(GroupParam groupPar);
	public void updateStatus(@Param("id")String string, @Param("status")String string2);
	public void deleteGroup(String id);
	public void save(Group groupPar);
	public void delGroup(@Param("id")String id);
	public ArrayList<?> findWorkOrderType();
	public void addwkGroup(Map<String, Object> paramMap);
	public ArrayList<?> queryWorkOrderType(Map<String, Object> paramMap);
	public void delWorkOrder(Map<String, Object> paramMap);
	public ArrayList<?> findStore();
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
	public void addsGroupModelisNull(Map<String, Object> pram);
	public List<TransportVendor> queryLogisticsCodeName();
	public void addTransportVendorGroup(@Param("transportName")String transportName, @Param("g_id")String g_id);
	public int checkCountTransportName(@Param("transportName")String transportName);
	public List<Store> queryStoreCode();
	public int checkCountStoreCode(@Param("storeCode")String storeCode);
	public void addtbStore(@Param("storeCode")String storeCode, @Param("g_id")String g_id);
	public List<WkType> queryWorkOrderName();
	public int checkCountWkName(@Param("name")String name);
	public int queryWkTypeById(@Param("name")String name);
	public List<?> findwkTypeAndwkLevel(@Param("id")String id);
	public void addsGroupModelAndMost(@Param("name")String name, @Param("g_id")String g_id, @Param("level")int level);
	public void addStoreGroup(@Param("id")String id,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by, @Param("update_time")String update_time, @Param("group")int group, @Param("selfwarehouse")String selfwarehouse,
			@Param("outsourcedwarehouse")String outsourcedwarehouse, @Param("store")String store);
	public void updateStoreGroupPage(@Param("id")String id,@Param("create_by")String create_by,@Param("create_time")String create_time,@Param("update_by")String update_by, @Param("update_time")String update_time, @Param("group")int group, @Param("selfwarehouse")String selfwarehouse,
			@Param("outsourcedwarehouse")String outsourcedwarehouse, @Param("store")String store);
	public void addTransportVendor(String id, String create_by, String update_by, int group, String carrier,
			String wo_type, String wo_level);
	public void deleteGroupWorkPower(int group, String carrier);
	public ArrayList<?> displayWorkOrder(Map<String, Object> paramMap);
	public ArrayList<?> queryWkGroupSupAndUpdate(String id);
	public List<?> workOrderAndLevel(String code);
	public int checkWoGroupStorepower(String outsourcedwarehouse, String selfwarehouse, String store, int group);
	public ArrayList<?> querySeniorQueryGroupSup();
	public Group querysGroupById(Map<String, Object> paramMap);
	public int dindwoGroupWorkPower(@Param("gId")String gId, @Param("tCode")String tCode);
	public int checkCountWorkCode(Map<String, Object> pram);
	public List<GroupWorkPower> queryWorkPower(@Param("carrier")String carrier,@Param("group")int group);
	public void addTransportVendorTime(String id, String create_by, Date create_time, String update_by, int group,
			String carrier, String wo_type, String wo_level);
	public JSONObject findStoreAll(HttpServletRequest request, JSONObject result);
	public JSONObject queryLogisticsCodeSelect(HttpServletRequest request, JSONObject result,String carrier,int group);
	public void delWorkPower(@Param("id")String id);
	public void delStorePower(@Param("id")String id);
	/**
	 * @param map
	 * @return
	 */
	public QueryResult<Group> querysGroup2(Map<String, Object> map);
}
