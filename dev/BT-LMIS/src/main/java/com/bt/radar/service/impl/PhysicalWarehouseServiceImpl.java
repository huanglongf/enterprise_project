package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.PhysicalWarehouseQueryParam;
import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.dao.PhysicalWarehouseMapper;
import com.bt.radar.dao.ReceiveDeadlineMapper;
import com.bt.radar.model.PhysicalWarehouse;
import com.bt.radar.service.PhysicalWarehouseService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class PhysicalWarehouseServiceImpl<T> extends ServiceSupport<T> implements PhysicalWarehouseService<T> {

	@Autowired
	private AreaRadarMapper<T> areaRadarMapper;
	
	@Autowired
    private PhysicalWarehouseMapper<T> physicalWarehouseMapper;

	@Autowired
	ReceiveDeadlineMapper receiveDeadlineMapper;
	public AreaRadarMapper<T> getAreaRadarMapper() {
		return areaRadarMapper;
	}
	
	public PhysicalWarehouseMapper<T> getPhysicalWarehouseMapper() {
		return physicalWarehouseMapper;
	}

	@Override
	public JSONObject save(JSONObject result, PhysicalWarehouse physicalWarehouse, HttpServletRequest request)
			throws Exception {
		result = new JSONObject();
		PhysicalWarehouse param = new PhysicalWarehouse();
		if(CommonUtils.checkExistOrNot(physicalWarehouse.getId())){
			// 更新
			param.setWarehouse_code(physicalWarehouse.getWarehouse_code());
			PhysicalWarehouse temp = physicalWarehouseMapper.findAllExist(param);
			if(CommonUtils.checkExistOrNot(temp) && !physicalWarehouse.getId().equals(temp.getId())){
				// 当能够查询到记录且为不同记录时（ID不同）
				// 则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新失败,失败原因:此物理仓代码已存在！");
				return result;
			}
			param = new PhysicalWarehouse();
			param.setWarehouse_name(physicalWarehouse.getWarehouse_name());
			temp = physicalWarehouseMapper.findAllExist(param);
			if(CommonUtils.checkExistOrNot(temp) && !physicalWarehouse.getId().equals(temp.getId())){
				// 当能够查询到记录且为不同记录时（ID不同）
				// 则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新失败,失败原因:此物理仓名称已存在！");
				return result;
			}
			physicalWarehouse.setUpdate_user(SessionUtils.getEMP(request).getUsername());
			physicalWarehouseMapper.updatePhysicalWarehouse(physicalWarehouse);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "更新物理仓信息成功！");
		} else {
			// 新增
			param.setWarehouse_code(physicalWarehouse.getWarehouse_code());
			if(CommonUtils.checkExistOrNot(physicalWarehouseMapper.findAllExist(param))){
				// 若存在则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增失败,失败原因:此物理仓代码已存在！");
				return result;
			}
			param = new PhysicalWarehouse();
			param.setWarehouse_name(physicalWarehouse.getWarehouse_name());
			if(CommonUtils.checkExistOrNot(physicalWarehouseMapper.findAllExist(param))){
				// 若存在则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增失败,失败原因:此物理仓名称已存在！");
				return result;
			}
			// id
			physicalWarehouse.setId(UUID.randomUUID().toString());
			// 创建人，更新人相同
			physicalWarehouse.setCreate_user(SessionUtils.getEMP(request).getUsername());
			physicalWarehouseMapper.insertPhysicalWarehouse(physicalWarehouse);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增物理仓成功！");
			result.put("id", physicalWarehouse.getId());
		}
		return result;
	}
	
	@Override
	public HttpServletRequest toForm(HttpServletRequest request) {
		PhysicalWarehouse param = new PhysicalWarehouse();
		param.setId(request.getParameter("pid"));
		PhysicalWarehouse physicalWarehouse = physicalWarehouseMapper.findAllExist(param);
		request.setAttribute("physicalWarehouse", physicalWarehouse);
		request.setAttribute("citys", areaRadarMapper.getRecords(physicalWarehouse.getProvince_code()));
		if(CommonUtils.checkExistOrNot(physicalWarehouse.getCity_code())){
			// 城市存在
			request.setAttribute("states", areaRadarMapper.getRecords(physicalWarehouse.getCity_code()));
			if(CommonUtils.checkExistOrNot(physicalWarehouse.getState_code())){
				// 区县存在
				request.setAttribute("streets", areaRadarMapper.getRecords(physicalWarehouse.getState_code()));
				
			}
			
		}
		return request;
		
	}
	
	@Override
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		String[] ids = CommonUtils.toStringArray(request.getParameter("privIds"));
		PhysicalWarehouse physicalWarehouse = new PhysicalWarehouse();
		physicalWarehouse.setDl_flag("0");
		for(int i = 0; i < ids.length; i++){
			physicalWarehouse.setId(ids[i]);
			physicalWarehouseMapper.updatePhysicalWarehouse(physicalWarehouse);
		}
		result.put("result_code", "SUCCESS");
		return result;
	}
	
	@Override
	public QueryResult<Map<String, Object>> toList(PhysicalWarehouseQueryParam physicalWarehouseQueryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(physicalWarehouseMapper.toList(physicalWarehouseQueryParam));
		qr.setTotalrecord(physicalWarehouseMapper.countAllExist(physicalWarehouseQueryParam));
		return qr;
	}
	
	@Override
	public List<PhysicalWarehouse> getPhysicalWarehouse() {
		return physicalWarehouseMapper.getPhysicalWarehouse();
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public void reFresh() {
		// TODO Auto-generated method stub
		physicalWarehouseMapper.pro_radar_refresh_warehouse_temp();
		receiveDeadlineMapper.pro_radar_refresh_receive_temp();
	}
}
