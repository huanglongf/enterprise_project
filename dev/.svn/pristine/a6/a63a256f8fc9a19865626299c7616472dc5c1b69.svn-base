package com.bt.radar.service.impl;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ReceiveDeadlineQueryParam;
import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.dao.ReceiveDeadlineMapper;
import com.bt.radar.model.ReceiveDeadline;
import com.bt.radar.service.ReceiveDeadlineService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class ReceiveDeadlineServiceImpl<T> extends ServiceSupport<T> implements ReceiveDeadlineService<T> {

	@Autowired
	private AreaRadarMapper<T> areaRadarMapper;
	@Autowired
	private TransportProductTypeMapper<T> transportProductTypeMapper;
	@Autowired
	private ReceiveDeadlineMapper<T> receiveDeadlineMapper;
	
	@Override
	public HttpServletRequest toForm(HttpServletRequest request) {
		ReceiveDeadline receiveDeadline = receiveDeadlineMapper.getById(request.getParameter("pid"));
		request.setAttribute("receiveDeadline", receiveDeadline);
		if(receiveDeadline.getExpress_code().equals("SF")){
			request.setAttribute("productType", transportProductTypeMapper.findByTransportVendor(true, receiveDeadline.getExpress_code()));
			
		}
		request.setAttribute("citys", areaRadarMapper.getRecords(receiveDeadline.getProvince_code()));
		if(CommonUtils.checkExistOrNot(receiveDeadline.getCity_code())){
			// 城市存在
			request.setAttribute("states", areaRadarMapper.getRecords(receiveDeadline.getCity_code()));
			if(CommonUtils.checkExistOrNot(receiveDeadline.getState_code())){
				// 区县存在
				request.setAttribute("streets", areaRadarMapper.getRecords(receiveDeadline.getState_code()));
				
			}
			
		}
		return request;
		
	}
	
	@Override
	public JSONObject save(JSONObject result, ReceiveDeadline receiveDeadline, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		if(CommonUtils.checkExistOrNot(receiveDeadline.getId())){
			// 更新
			ReceiveDeadline temp = receiveDeadlineMapper.findAllExist(receiveDeadline);
			if(CommonUtils.checkExistOrNot(temp) && !receiveDeadline.getId().equals(temp.getId())){
				// 当能够查询到记录且为不同记录时（ID不同）
				// 则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新失败,失败原因:揽件截止时间已存在！");
			} else {
				receiveDeadline.setUpdate_user(SessionUtils.getEMP(request).getUsername());
				receiveDeadline.setDl_flag(1);
				receiveDeadlineMapper.updateReceiveDeadline(receiveDeadline);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "更新揽件截止时间成功！");
			}
		} else {
			// 新增
			if(CommonUtils.checkExistOrNot(receiveDeadlineMapper.findAllExist(receiveDeadline))){
				// 若存在则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增失败,失败原因:揽件截止时间已存在！");
			} else {
				// id
				receiveDeadline.setId(UUID.randomUUID().toString());
				// 创建人，更新人相同
				receiveDeadline.setCreate_user(SessionUtils.getEMP(request).getUsername());
				receiveDeadlineMapper.insertReceiveDeadline(receiveDeadline);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增揽件截止时间成功！");
				result.put("id", receiveDeadline.getId());
			}
		}
		return result;
	}
	
	@Override
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		String[] ids = CommonUtils.toStringArray(request.getParameter("privIds"));
		ReceiveDeadline receiveDeadline = new ReceiveDeadline();
		receiveDeadline.setDl_flag(0);
		for(int i = 0; i < ids.length; i++){
			receiveDeadline.setId(ids[i]);
			receiveDeadlineMapper.updateReceiveDeadline(receiveDeadline);
		}
		result.put("result_code", "SUCCESS");
		return result;
	}
	
	@Override
	public QueryResult<Map<String, Object>> toList(ReceiveDeadlineQueryParam receiveDeadlineQueryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(receiveDeadlineMapper.toList(receiveDeadlineQueryParam));
		qr.setTotalrecord(receiveDeadlineMapper.countAllExist(receiveDeadlineQueryParam));
		return qr;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
