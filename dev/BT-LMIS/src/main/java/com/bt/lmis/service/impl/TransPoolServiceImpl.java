package com.bt.lmis.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
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
import com.bt.lmis.dao.TransPoolMapper;
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
import com.bt.lmis.service.TransPoolService;
import com.bt.workOrder.dao.GroupMapper;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.model.WkType;

/**
 * 
* @ClassName: TransPoolServiceImpl 
* @Description: TODO(物流运费汇总报表) 
* @author likun
* @date 2016年8月25日 上午9:26:45 
* 
* @param <T>
 */
@Service
public class TransPoolServiceImpl<T> extends ServiceSupport<T> implements TransPoolService<T> {
	
	@Autowired
    private TransPoolMapper<T> mapper;
	@Autowired
	private GroupMapper<Group> groupMapper;

	public TransPoolMapper<T> getMapper() {
		return mapper;
	}

	public GroupMapper<Group> getGroupMapper() {
		return groupMapper;
	}

	public void setGroupMapper(GroupMapper<Group> groupMapper) {
		this.groupMapper = groupMapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResult<T> findAllData(QueryParameter queryParameter) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData(queryParameter));
		queryResult.setTotalrecord(mapper.selectCount(queryParameter));
		return queryResult;
	}

	@Override
	public QueryResult<T> findAllDataForGt(QueryParameter queryParameter) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllDataByGt(queryParameter));
		queryResult.setTotalrecord(mapper.selectCountByGt(queryParameter));
		return queryResult;
	}

	@Override
	public QueryResult<T> findMainData(QueryParameter queryParameter){
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getPoolMain(queryParameter));
		queryResult.setTotalrecord(mapper.selectCountPoolMain(queryParameter));
		return queryResult;
	}

	@Override
	public QueryResult<SettleInvitationBean> querySettleInvitation(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleInvitationBean> queryResult = new QueryResult<SettleInvitationBean>();
		queryResult.setResultlist(mapper.querySettleInvitation(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<SettleOperatorBean> querySettleOperator(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleOperatorBean> queryResult = new QueryResult<SettleOperatorBean>();
		queryResult.setResultlist(mapper.querySettleOperator(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<SettleOrderBean> querySettleOrder(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleOrderBean> queryResult = new QueryResult<SettleOrderBean>();
		queryResult.setResultlist(mapper.querySettleOrder(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<SettleOrderDetailBean> queryOrderDetail(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleOrderDetailBean> queryResult = new QueryResult<SettleOrderDetailBean>();
		queryResult.setResultlist(mapper.queryOrderDetail(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<SettleStorageBean> queryStorage(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleStorageBean> queryResult = new QueryResult<SettleStorageBean>();
		queryResult.setResultlist(mapper.queryStorage(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<SettleUseBean> queryUse(TransPoolQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<SettleUseBean> queryResult = new QueryResult<SettleUseBean>();
		queryResult.setResultlist(mapper.queryUse(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
	}

	@Override
	public QueryResult<WarehouseExpressData> queryExpressAllTj(WarehouseExpressDataParam warehouseExpressDataPar) {
		QueryResult<WarehouseExpressData> queryResult = new QueryResult<WarehouseExpressData>();
		queryResult.setResultlist(mapper.queryExpressAll(warehouseExpressDataPar));
		queryResult.setTotalrecord(mapper.countQueryExpressAll(warehouseExpressDataPar));
		return queryResult;
	}


	@Override
	public QueryResult<TbInvitationUseanmountData> querySuppliesDetail(
			TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar) {
		QueryResult<TbInvitationUseanmountData> queryResult = new QueryResult<TbInvitationUseanmountData>();
		queryResult.setResultlist(mapper.querySuppliesDetailAll(tbInvitationUseanmountDataPar));
		queryResult.setTotalrecord(mapper.countQuerySuppliesDetailAll(tbInvitationUseanmountDataPar));
		return queryResult;
	}

	@Override
	public QueryResult<TbOperationfeeData> operatingCostDetail(TbOperationfeeDataParam tbOperationfeeDataPar) {
		QueryResult<TbOperationfeeData> queryResult = new QueryResult<TbOperationfeeData>();
		queryResult.setResultlist(mapper.queryOperatingCostAll(tbOperationfeeDataPar));
		queryResult.setTotalrecord(mapper.countOperatingCostAll(tbOperationfeeDataPar));
		return queryResult;
	}

	@Override
	public QueryResult<TbStorageExpendituresData> queryWarehousing(
			TbStorageExpendituresDataParam tbStorageExpendituresDataPar) {
		QueryResult<TbStorageExpendituresData> queryResult = new QueryResult<TbStorageExpendituresData>();
		queryResult.setResultlist(mapper.queryWarehousingAll(tbStorageExpendituresDataPar));
		queryResult.setTotalrecord(mapper.countWarehousingtAll(tbStorageExpendituresDataPar));
		return queryResult;
	}

	@Override
	public QueryResult<ErExpressinfoDetail> queryExpressInfor(ErExpressinfoDetailParam erExpressinfoDetailPar) {
		QueryResult<ErExpressinfoDetail> queryResult = new QueryResult<ErExpressinfoDetail>();
		queryResult.setResultlist(mapper.queryExpressInforAll(erExpressinfoDetailPar));
		queryResult.setTotalrecord(mapper.countExpressInforAll(erExpressinfoDetailPar));
		return queryResult;
	}

	@Override
	public QueryResult<ErExpressinfoAaster> queryRadarMain(ErExpressinfoAasterParam erExpressinfoAasterPar) {
		// TODO Auto-generated method stub
		QueryResult<ErExpressinfoAaster> queryResult = new QueryResult<ErExpressinfoAaster>();
		queryResult.setResultlist(mapper.queryRadarMainAll(erExpressinfoAasterPar));
		queryResult.setTotalrecord(mapper.countRadarMainAll(erExpressinfoAasterPar));
		return queryResult;
	}

	@Override
	public QueryResult<ErCalMaster> queryRadar(ErCalMasterParam erCalMasterPar) {
		// TODO Auto-generated method stub
		QueryResult<ErCalMaster> queryResult = new QueryResult<ErCalMaster>();
		queryResult.setResultlist(mapper.queryRadarAll(erCalMasterPar));
		queryResult.setTotalrecord(mapper.countRadarAll(erCalMasterPar));
		return queryResult;
	}

	@Override
	public QueryResult<WarehouseExpressData> queryRawExpress(WarehouseExpressDataParam warehouseExpressDataPar) {
		QueryResult<WarehouseExpressData> queryResult = new QueryResult<WarehouseExpressData>();
		queryResult.setResultlist(mapper.queryRawExpressAll(warehouseExpressDataPar));
		queryResult.setTotalrecord(mapper.countRawExpressAll(warehouseExpressDataPar));
		return queryResult;
	}

	@Override
	public QueryResult<TbInvitationRealuseanmountData> querySuppliesAmount(
			TbInvitationRealuseanmountDataParam tbInvitationRealuseanmountDataPar) {
		QueryResult<TbInvitationRealuseanmountData> queryResult = new QueryResult<TbInvitationRealuseanmountData>();
		queryResult.setResultlist(mapper.querySuppliesAmountAll(tbInvitationRealuseanmountDataPar));
		queryResult.setTotalrecord(mapper.countSuppliesAmountAll(tbInvitationRealuseanmountDataPar));
		return queryResult;
	}


	@Override
	public List<?> warehousingExport(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.warehousingExport(param);
	}

	@Override
	public List<Map<String, Object>> queryRadarExport(String waybill, String warehouse_code) {
		// TODO Auto-generated method stub
		return mapper.queryRadarExport(waybill,warehouse_code);
	}

	@Override
	public List<Map<String, Object>> findOperatingCostExport(String store_name, String job_orderno, String art_no,
			String inventory_status) {
		return mapper.findOperatingCostExport(store_name,job_orderno,art_no,inventory_status);
	}

	@Override
	public List<Map<String, Object>> queryExpressExport(String store_name, String cost_center, String store_code,
			String create_time, String update_time) {
		// TODO Auto-generated method stub
		return mapper.queryExpressExport(store_name,cost_center,store_code,create_time,update_time);
	}

	@Override
	public List<Map<String, Object>> findRadarMainExport(String store_code, String warehouse_code, String waybill,
			String create_time, String update_time) {
		// TODO Auto-generated method stub
		return mapper.findRadarMainExport(store_code,warehouse_code,waybill,create_time,update_time);
	}

	@Override
	public List<Map<String, Object>> QueryexpressInformationExport(String barcode, String waybill, String item_name,
			String create_time, String update_time) {
		return mapper.QueryexpressInformationExport(barcode,waybill,item_name,create_time,update_time);
	}

	@Override
	public List<Map<String, Object>> findSuppliesAmountExport(String store_name, String sku_code, String cost_center,
			String sku_name, String create_time, String update_time) {
		return mapper.findSuppliesAmountExport(store_name,sku_code,cost_center,sku_name,create_time,update_time);
	}

	@Override
	public List<Map<String, Object>> findInvitationUseanmount(String store_name, String inbound_no, String bz_number,
			String item_name, String create_time, String update_time) {
		// TODO Auto-generated method stub
		return mapper.findInvitationUseanmount(store_name,inbound_no,bz_number,item_name,create_time,update_time);
	}

	@Override
	public QueryResult<Group> querysGroup(GroupParam groupPar) {
		QueryResult<Group> queryResult = new QueryResult<Group>();
		queryResult.setResultlist(mapper.querysGroup(groupPar));
		queryResult.setTotalrecord(mapper.countsGroup(groupPar));
		return queryResult;
	}
	@Override
	public QueryResult<Group> querysGroup2(Map<String, Object> map) {
		QueryResult<Group> queryResult = new QueryResult<Group>();
		queryResult.setResultlist(mapper.querysGroup2(map));
		queryResult.setTotalrecord(mapper.countsGroup2(map));
		return queryResult;
	}
	@Override
	public void updateStatus(String id, String status) {
		// TODO Auto-generated method stub
		mapper.updateStatus(id,status);
	}

	@Override
	public void deleteGroup(String id) {
		// TODO Auto-generated method stub
		mapper.deleteGroup(id);
	}

	@Override
	public void save(Group groupPar) {
		// TODO Auto-generated method stub
		mapper.saveGroup(groupPar);
	}

	@Override
	public void delGroup(String id) {
		// TODO Auto-generated method stub
		mapper.deleteGroup(id);
	}

	@Override
	public ArrayList<?> findWorkOrderType() {
		// TODO Auto-generated method stub
		return mapper.findWorkOrderType();
	}

	@Override
	public void addwkGroup(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		 mapper.addwkGroup(paramMap);
	}

	@Override
	public ArrayList<?> queryWorkOrderType(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.queryWorkOrderType(paramMap);
	}

	@Override
	public void delWorkOrder(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		 mapper.delWorkOrder(paramMap);
	}

	@Override
	public ArrayList<?> findStore() {
		// TODO Auto-generated method stub
		return mapper.findStore();
	}

	@Override
	public ArrayList<?> queryStore(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.queryStore(paramMap);
	}

	@Override
	public void deleteStoreAndGroup(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		mapper.deleteStoreAndGroup(paramMap);
	}

	@Override
	public ArrayList<?> queryTransportVendorGroup(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.queryTransportVendorGroup(paramMap);
	}

	@Override
	public ArrayList<?> queryLogisticsCode() {
		// TODO Auto-generated method stub
		return mapper.queryLogisticsCode();
	}

	@Override
	public void deleteVendorGroup(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		 mapper.deleteVendorGroup(paramMap);
	}

	@Override
	public void updateWkGroup(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		mapper.updateWkGroup(paramMap);
	}

	@Override
	public ArrayList<?> queryWkGroupSup() {
		// TODO Auto-generated method stub
		return mapper.queryWkGroupSup();
	}


	@Override
	public void addsGroupModel(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		
		mapper.addsGroupModel(pram);
	}

	@Override
	public void updateGroupModel(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		mapper.updateGroupModel(pram);
	}

	@Override
	public void updateStoreGroupPage(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		mapper.updateStoreGroupPage(paramMap);
	}

	@Override
	public void updateTransportVendor(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		mapper.updateTransportVendor(paramMap);
	}

	@Override
	public int checkCountWork(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return mapper.checkCountWork(pram);
	}

	@Override
	public WkType findLevelById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.findLevelById(map);
	}

	@Override
	public ArrayList<?> queryfindLevelAndJb(String id) {
		// TODO Auto-generated method stub
		return mapper.queryfindLevelAndJb(id);
	}

	@Override
	public void addsGroupModelisNull(Map<String, Object> pram) {
		// TODO Auto-generated method stub
	    mapper.addsGroupModelisNull(pram);
	}

	@Override
	public List<TransportVendor> queryLogisticsCodeName() {
		// TODO Auto-generated method stub
		return mapper.queryLogisticsCodeName();
	}

	@Override
	public void addTransportVendorGroup(String transportName, String g_id) {
		// TODO Auto-generated method stub
		mapper.addTransportVendorGroup(transportName,g_id);
	}

	@Override
	public int checkCountTransportName(String transportName) {
		// TODO Auto-generated method stub
		return mapper.checkCountTransportName(transportName);
	}

	@Override
	public List<Store> queryStoreCode() {
		// TODO Auto-generated method stub
		return mapper.queryStoreCode();
	}

	@Override
	public int checkCountStoreCode(String storeCode) {
		// TODO Auto-generated method stub
		 return mapper.checkCountStoreCode(storeCode);
	}

	@Override
	public void addtbStore(String storeCode, String g_id) {
		// TODO Auto-generated method stub
		mapper.addtbStore(storeCode,g_id);
	}

	@Override
	public List<WkType> queryWorkOrderName() {
		// TODO Auto-generated method stub
		return mapper.queryWorkOrderName();
	}

	@Override
	public int checkCountWkName(String name) {
		// TODO Auto-generated method stub
		return mapper.checkCountWkName(name);
	}

	@Override
	public int queryWkTypeById(String name) {
		// TODO Auto-generated method stub
		return mapper.queryWkTypeById(name);
	}

	@Override
	public List<?> findwkTypeAndwkLevel(String id) {
		// TODO Auto-generated method stub
		return mapper.findwkTypeAndwkLevel(id);
	}

	@Override
	public void addsGroupModelAndMost(String name, String g_id, int level) {
		// TODO Auto-generated method stub
		 mapper.addsGroupModelAndMost(name,g_id,level);
	}

	@Override
	public void addStoreGroup(String id,String create_by,String create_time, String update_by, String update_time,int group, String selfwarehouse,
			String outsourcedwarehouse, String store) {
		// TODO Auto-generated method stub
		mapper.addStoreGroup(id,create_by,create_time,update_by,update_time,group,selfwarehouse,outsourcedwarehouse,store);
	}

	@Override
	public void updateStoreGroupPage(String id, String create_by, String create_time, String update_by,
			String update_time, int group, String selfwarehouse, String outsourcedwarehouse, String store) {
		// TODO Auto-generated method stub
		mapper.updateStoreGroupPage(id,create_by,create_time,update_by,update_time,group,selfwarehouse,outsourcedwarehouse,store);
	}

	@Override
	public int checkWoGroupStorepower(String outsourcedwarehouse, String selfwarehouse, String store,int group) {
		// TODO Auto-generated method stub
		return mapper.checkWoGroupStorepower(outsourcedwarehouse,selfwarehouse,store,group);
	}

	@Override
	public void addTransportVendor(String id, String create_by, String update_by, int group, String carrier,
			String wo_type, String wo_level) {
		// TODO Auto-generated method stub
		mapper.addTransportVendor(id, create_by,update_by,group,carrier,wo_type,wo_level);
	}

	@Override
	public void deleteGroupWorkPower(int group, String carrier) {
		// TODO Auto-generated method stub
		mapper.deleteGroupWorkPower(group, carrier);
	}

	@Override
	public ArrayList<?> displayWorkOrder(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.displayWorkOrder(paramMap);
	}

	@Override
	public ArrayList<?> queryWkGroupSupAndUpdate(String id) {
		// TODO Auto-generated method stub
		return mapper.queryWkGroupSupAndUpdate(id);
	}

	@Override
	public List<?> workOrderAndLevel(String code) {
		// TODO Auto-generated method stub
		return mapper.workOrderAndLevel(code);
	}

	@Override
	public ArrayList<?> querySeniorQueryGroupSup() {
		// TODO Auto-generated method stub
		return mapper.querySeniorQueryGroupSup();
	}

	@Override
	public Group querysGroupById(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return mapper.querysGroupById(paramMap);
	}

	@Override
	public int dindwoGroupWorkPower(String gId, String tCode) {
		// TODO Auto-generated method stub
		return mapper.dindwoGroupWorkPower(gId,tCode);
	}

	@Override
	public int checkCountWorkCode(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return mapper.checkCountWorkCode(pram);
	}

	@Override
	public List<GroupWorkPower> queryWorkPower(String carrier,int group) {
		// TODO Auto-generated method stub
		return mapper.queryWorkPower(carrier,group);
	}

	@Override
	public void addTransportVendorTime(String id, String create_by, Date create_time, String update_by, int group,
			String carrier, String wo_type, String wo_level) {
		// TODO Auto-generated method stub
		mapper.addTransportVendorTime(id, create_by, create_time, update_by, group,carrier, wo_type, wo_level);
	}

	@Override
	public JSONObject findStoreAll(HttpServletRequest request, JSONObject result) {
		result= new JSONObject();
		result.put("storePower", groupMapper.getStorePowerById(request.getParameter("id")));
		result.put("store", mapper.findStore());
		return result;
	}

	@Override
	public JSONObject queryLogisticsCodeSelect(HttpServletRequest request, JSONObject result,String carrier,int group) {
		// TODO Auto-generated method stub
		List<GroupWorkPower> listT = mapper.queryWorkPower(carrier,group);
		String id = null;
		for(int j=0;j<listT.size();j++){
    		GroupWorkPower dx =  listT.get(j);
    		if(j==0){
        		id = dx.getId();
        		break;
    		}
    	}
		result= new JSONObject();
		result.put("storePower", mapper.findLogtics(id));
		result.put("transport", mapper.queryLogisticsCode());
		return result;
	}

	@Override
	public void delWorkPower(String id) {
		// TODO Auto-generated method stub
		mapper.delWorkPower(id);
	}

	@Override
	public void delStorePower(String id) {
		// TODO Auto-generated method stub
		mapper.delStorePower(id);
		groupMapper.delEmpGroup(id);
	}
	
}
