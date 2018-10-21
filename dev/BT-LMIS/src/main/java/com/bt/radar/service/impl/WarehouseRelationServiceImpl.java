package com.bt.radar.service.impl;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.WarehouseRelationQueryParam;
import com.bt.radar.dao.LogicWarehouseMapper;
import com.bt.radar.dao.WarehouseRelationMapper;
import com.bt.radar.model.LogicWarehouse;
import com.bt.radar.model.WarehouseRelation;
import com.bt.radar.service.WarehouseRelationService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class WarehouseRelationServiceImpl<T> extends ServiceSupport<T> implements WarehouseRelationService<T> {

	@Autowired
    private WarehouseRelationMapper<T> warehouseRelationMapper;

	@Autowired
	private LogicWarehouseMapper<T> logicWarehouseMapper;
	
	@Override
	public JSONObject toForm(HttpServletRequest request, JSONObject result) {
		result= new JSONObject();
		result.put("logicWarehouses", logicWarehouseMapper.selectRecords(new LogicWarehouse("1")));
		if(CommonUtils.checkExistOrNot(request.getParameter("id"))){
			result.put("warehouseRelation", warehouseRelationMapper.findAllExist(new WarehouseRelation(request.getParameter("id"))));
			
		}
		return result;
		
	}
	
	@Override
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		String[] ids = CommonUtils.toStringArray(request.getParameter("privIds"));
		WarehouseRelation warehouseRelation = new WarehouseRelation();
		warehouseRelation.setDl_flag("0");
		for(int i = 0; i < ids.length; i++){
			warehouseRelation.setId(ids[i]);
			warehouseRelationMapper.updateWarehouseRelation(warehouseRelation);
		}
		result.put("result_code", "SUCCESS");
		return result;
	}
	
	@Override
	public JSONObject save(JSONObject result, WarehouseRelation warehouseRelation, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		WarehouseRelation param = new WarehouseRelation();
		param.setPhysical_code(warehouseRelation.getPhysical_code());
		param.setLogic_code(warehouseRelation.getLogic_code());
		if(CommonUtils.checkExistOrNot(warehouseRelation.getId())){
			// 更新
			WarehouseRelation temp = warehouseRelationMapper.findAllExist(param);
			if(CommonUtils.checkExistOrNot(temp) && !warehouseRelation.getId().equals(temp.getId())){
				// 当能够查询到记录且为不同记录时（ID不同）
				// 则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新失败,失败原因:物理仓对应逻辑仓关系已存在！");
			} else {
				warehouseRelation.setUpdate_user(SessionUtils.getEMP(request).getUsername());
				warehouseRelationMapper.updateWarehouseRelation(warehouseRelation);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "更新物理仓对应逻辑仓关系成功！");
			}
		} else {
			// 新增
			if(CommonUtils.checkExistOrNot(warehouseRelationMapper.findAllExist(param))){
				// 若存在则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增失败,失败原因:物理仓对应逻辑仓关系已存在！");
			} else {
				// id
				warehouseRelation.setId(UUID.randomUUID().toString());
				// 创建人，更新人相同
				warehouseRelation.setCreate_user(SessionUtils.getEMP(request).getUsername());
				warehouseRelationMapper.insertWarehouseRelation(warehouseRelation);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增物理仓对应逻辑仓关系成功！");
			}
		}
		return result;
	}
	
	@Override
	public QueryResult<Map<String, Object>> toList(WarehouseRelationQueryParam warehouseRelationQueryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(warehouseRelationMapper.toList(warehouseRelationQueryParam));
		qr.setTotalrecord(warehouseRelationMapper.countAllExist(warehouseRelationQueryParam));
		return qr;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}
}
