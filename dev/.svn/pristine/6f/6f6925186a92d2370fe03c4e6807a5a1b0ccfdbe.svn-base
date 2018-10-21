package com.bt.lmis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.controller.form.WareImportTaskQueryParam;
import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.dao.StoreMapper;
import com.bt.lmis.dao.WareImportErrorMapper;
import com.bt.lmis.dao.WareImportTaskMapper;
import com.bt.lmis.dao.WareParkMapper;
import com.bt.lmis.dao.WareRelationMapper;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WareImportError;
import com.bt.lmis.model.WareImportTask;
import com.bt.lmis.model.WareRelation;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WareImportTaskService;

@Service
@Transactional(readOnly=true)
public class WareImportTaskServiceImpl implements WareImportTaskService {
	
	@Autowired
	private WareImportTaskMapper wareImportTaskMapper;
	@Autowired
	private WareImportErrorMapper wareImportErrorMapper;
	@Autowired
	private WareParkMapper wareParkMapper;
	@Autowired
	private WareRelationMapper wareRelationMapper;
	@Autowired
	private StoreMapper<T> storeMapper;
	@Autowired
	private WarehouseMapper<T> warehouseMapper;

	@Override
	@Transactional(readOnly=false)
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int insert(WareImportTask record) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int insertSelective(WareImportTask record) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.insertSelective(record);
	}

	@Override
	public WareImportTaskQueryParam selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKeySelective(WareImportTask record) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKey(WareImportTask record) {
		// TODO Auto-generated method stub
		return wareImportTaskMapper.updateByPrimaryKey(record);
	}

	@Override
	public QueryResult<WareImportTaskQueryParam> getList(WareImportTaskQueryParam wareImportTaskQueryParam) {
		QueryResult<WareImportTaskQueryParam> queryResult = new QueryResult<WareImportTaskQueryParam>();
		queryResult.setResultlist(wareImportTaskMapper.getList(wareImportTaskQueryParam));
		queryResult.setTotalrecord(wareImportTaskMapper.getListCount(wareImportTaskQueryParam));
		return queryResult;
	}

	@Override
	@Transactional(readOnly=false)
	public int delByIds(String ids) {
		if(StringUtils.isNotBlank(ids)){
			for (String id : ids.split(",")) {
				wareImportTaskMapper.deleteByPrimaryKey(id);
			}
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	@Transactional(readOnly=false)
	public Map<String, String> imoprtWarePark(List<String[]> list, WareImportTask wareImportTask, Employee user) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		for (int i = 0; i < list.size(); i++) {
			
			String[] items = list.get(i);
			
			StringBuffer errorCode = new StringBuffer();//1.园区编码为空2.园区编码未维护3.仓库编码为空4.仓库编码未维护5.店铺编码为空6.店铺编码未维护7.插入异常8.相同的仓-店铺组合已存在9.未通过校验
			StringBuffer errorMessage = new StringBuffer();
			Boolean isError = false;

			WareRelation wareRelation = new WareRelation();
			wareRelation.setWarehouseCode(items[1]);
			wareRelation.setStoreCode(items[2]);

			if(StringUtils.isBlank(items[3])){
				isError = true;
				errorCode.append(",1");
				errorMessage.append(",园区编码为空");
			} else {
				WareParkQueryParam newWarePark = new WareParkQueryParam();
				newWarePark.setParkCode(items[3]);
				newWarePark = wareParkMapper.getBySelective(newWarePark);
				if(newWarePark == null){
					isError = true;
					errorCode.append(",2");
					errorMessage.append(",园区编码未维护");
				} else {
					wareRelation.setParkId(newWarePark.getId());
				}
			}
			
			
			if(StringUtils.isNotBlank(items[1])){
				Warehouse warehouse = new Warehouse();
				warehouse.setWarehouse_code(items[1]);
				warehouse = warehouseMapper.getByCondition(warehouse);
				if(warehouse==null){
					isError = true;
					errorCode.append(",4");
					errorMessage.append(",仓库编码未维护");
				}
			} else {
				isError = true;
				errorCode.append(",3");
				errorMessage.append(",仓库编码为空");
			}
			
			if(StringUtils.isNotBlank(items[2])){
				Store store = new Store();
				store.setStore_code(items[2]);
				store = storeMapper.selectBySelective(store);
				if(store == null){
					isError = true;
					errorCode.append(",6");
					errorMessage.append(",店铺编码未维护");
				} else {
					wareRelation.setClientId(store.getClient_id());
				}
			} else {
				isError = true;
				errorCode.append(",5");
				errorMessage.append(",店铺编码为空");
			}


			Map<String, Long> checkWareRelationResult = wareRelationMapper.checkWareRelationParam(wareRelation);
			if(checkWareRelationResult != null 
					&& (checkWareRelationResult.get("wareRelationResult").compareTo(0l) == 0) && !isError){
				wareRelation.setCreateTime(new Date());
				wareRelation.setCreateUser(user.getId().toString());
				try {
					wareRelationMapper.insertSelective(wareRelation);
				} catch (Exception e) {
					isError = true;
					errorCode.append(",7");
					errorMessage.append(",插入异常");
				}
			} else if (checkWareRelationResult.get("wareRelationResult").compareTo(0l) > 0) {
				isError = true;
				errorCode.append(",8");
				errorMessage.append(",相同的仓-店铺组合已存在");
			} else {
				isError = true;
				errorCode.append(",9");
				errorMessage.append(",未通过校验");
			}
			
			if(isError){
				wareImportTask.setStatus("0");
				WareImportError wareImportError = new WareImportError();
				
				wareImportError.setTwitId(wareImportTask.getId());
				wareImportError.setSort(items[0]);
				wareImportError.setWarehouseCode(items[1]);
				wareImportError.setStoreCode(items[2]);
				wareImportError.setParkCode(items[3]);
				wareImportError.setErrorCode(errorCode.substring(1, errorCode.length()));
				wareImportError.setErrorMessage(errorMessage.substring(1, errorMessage.length()));
				wareImportError.setCreateTime(new Date());
				wareImportError.setCreateUser(user.getId().toString());
				wareImportError.setUpdateTime(new Date());
				wareImportError.setUpdateUser(user.getId().toString());
				
				wareImportErrorMapper.insertSelective(wareImportError);
			}
			
			
		}
		
		if(!wareImportTask.getStatus().equals("0")){
			wareImportTask.setStatus("1");
		}
		wareImportTask.setUpdateTime(new Date());
		wareImportTask.setUpdateUser(user.getId().toString());
		wareImportTaskMapper.updateByPrimaryKeySelective(wareImportTask);
		
		result.put("out_result", "1");
		result.put("out_result_reason", "导入完毕");
		return result;
	}

}
