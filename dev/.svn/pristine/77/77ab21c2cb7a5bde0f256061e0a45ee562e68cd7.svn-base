package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.WarehouseQueryParam;
import com.bt.lmis.dao.WarehouseMapper;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseService;
import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.model.Area;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class WarehouseServiceImpl<T> extends ServiceSupport<T> implements WarehouseService<T> {
	
	@Autowired
	private AreaRadarMapper<T> areaRadarMapper;
	@Autowired
	private WarehouseMapper<T> warehouseMapper;
	
	@Override
	public HttpServletRequest form(Warehouse warehouse, HttpServletRequest request) {
		// 到达省
		Area area = new Area();
		area.setLevel(1);
		request.setAttribute("province", areaRadarMapper.findRecords(area));
		// 
		if(CommonUtils.checkExistOrNot(warehouse.getId())){
			// 更新页面
			Warehouse whs = warehouseMapper.getByCondition(warehouse);
			request.setAttribute("citys", areaRadarMapper.getRecords(whs.getProvince() + ""));
			if(CommonUtils.checkExistOrNot(whs.getCity())){
				// 城市存在
				request.setAttribute("states", areaRadarMapper.getRecords(whs.getCity() + ""));
				if(CommonUtils.checkExistOrNot(whs.getState() + "")){
					// 区县存在
					request.setAttribute("streets", areaRadarMapper.getRecords(whs.getState() + ""));
				}
			}
			request.setAttribute("whs", whs);
		}
		return request;
	}

	
	@Override
	public JSONObject save(Warehouse warehouse, HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		// 参数类
		Warehouse param = new Warehouse();
		if(CommonUtils.checkExistOrNot(warehouse.getId())){
			// 更新
			if(CommonUtils.checkExistOrNot(warehouse.getWarehouse_code())){
				// 更新数据
				// 校验
				param.setWarehouse_code(warehouse.getWarehouse_code());
				param = warehouseMapper.getByCondition(param);
				if(CommonUtils.checkExistOrNot(param) && !(param.getId().equals(warehouse.getId()))){
					// 
					result.put("result_code", "FAILURE");
					result.put("failure_reason", "逻辑仓代码已存在！");
					return result;
				}
				param = new Warehouse();
				param.setWarehouse_name(warehouse.getWarehouse_name());
				param = warehouseMapper.getByCondition(param);
				if(CommonUtils.checkExistOrNot(param) && !(param.getId().equals(warehouse.getId()))){
					// 
					result.put("result_code", "FAILURE");
					result.put("failure_reason", "逻辑仓名称已存在！");
					return result;
				}
			}
			warehouse.setUpdate_user(String.valueOf(SessionUtils.getEMP(request).getId()));
			warehouseMapper.update(warehouse);
			result.put("result_code", "SUCCESS");
			
		} else {
			param.setWarehouse_code(warehouse.getWarehouse_code());
			// 新增
			if(CommonUtils.checkExistOrNot(warehouseMapper.getByCondition(param))){
				result.put("result_code", "FAILURE");
				result.put("failure_reason", "逻辑仓代码已存在！");
				return result;
			}
			param = new Warehouse();
			param.setWarehouse_name(warehouse.getWarehouse_name());
			if(CommonUtils.checkExistOrNot(warehouseMapper.getByCondition(param))){
				result.put("result_code", "FAILURE");
				result.put("failure_reason", "逻辑仓名称已存在！");
				return result;
			}
			warehouse.setCreate_user(SessionUtils.getEMP(request).getId() + "");
			warehouseMapper.add(warehouse);
			result.put("result_code", "SUCCESS");
		}
		return result;
	}
	
	@Override
	public QueryResult<Map<String, Object>> findAllWareHouse(WarehouseQueryParam warehouseQueryParam) {
		QueryResult<Map<String, Object>> queryResult = new QueryResult<Map<String, Object>>();
		queryResult.setResultlist(warehouseMapper.listAllWarehouse(warehouseQueryParam));
		queryResult.setTotalrecord(warehouseMapper.countList(warehouseQueryParam));
		return queryResult;
	}
	
	@Override
	public List<Map<String, Object>> findAll() {
		return warehouseMapper.findAll();
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Warehouse selectByWarehouseName(String warehouseName) {
		// TODO Auto-generated method stub
		return warehouseMapper.selectByWarehouseName(warehouseName);
	}


	@Override
	public Warehouse selectByWarehouseCode(String warehouseCode) {
		// TODO Auto-generated method stub
		return warehouseMapper.selectByWarehouseCode(warehouseCode);
	}


	@Override
	public List<Map<String, Object>> download(WarehouseQueryParam warehouseQueryParam) {
		// TODO Auto-generated method stub
		return warehouseMapper.download(warehouseQueryParam);
	}
}
