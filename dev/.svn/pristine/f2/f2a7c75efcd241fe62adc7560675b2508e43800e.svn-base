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
import com.bt.radar.controller.form.TimelinessDetailQueryParam;
import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.dao.TimelinessDetailMapper;
import com.bt.radar.model.TimelinessDetail;
import com.bt.radar.service.TimelinessDetailService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class TimelinessDetailServiceImpl<T> extends ServiceSupport<T> implements TimelinessDetailService<T> {

	@Autowired
	private AreaRadarMapper<T> areaRadarMapper;
	@Autowired
	private TimelinessDetailMapper<T> timelinessDetailMapper;
	
	public AreaRadarMapper<T> getAreaRadarMapper(){
		return areaRadarMapper;
	}
	
	public TimelinessDetailMapper<T> getTimelinessDetailMapper(){
		return timelinessDetailMapper;
	}
	
	@Override
	public JSONObject move(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		TimelinessDetail timelinessDetail = timelinessDetailMapper.getById(request.getParameter("id"));
		// 当前位置
		Integer position = timelinessDetail.getNumber();
		// 当前节点新位置
		int change_position = 0;
		// 动作
		String action = request.getParameter("action");
		// 
		String str = "";
		// 需要更新的另一个节点
		TimelinessDetail another = null;
		// 查询参数
		TimelinessDetail param = new TimelinessDetail();
		if(action.equals("up")) {
			change_position = position - 1;
			str = "上移";
			
		} else if(action.equals("down")) {
			change_position = position + 1;
			str = "下移";
		}
		// 先要查出当前操作记录的关联记录
		param.setNumber(change_position);
		param.setPid(request.getParameter("pid"));
		another = timelinessDetailMapper.findAllExist(param);
		another.setNumber(position);
		// 操作当前节点新位置
		timelinessDetail.setNumber(change_position);
		timelinessDetailMapper.updateTimelinessDetail(timelinessDetail);
		// 
		timelinessDetailMapper.updateTimelinessDetail(another);
		result.put("result_code", "SUCCESS");
		result.put("result_content", "时效明细节点" + str + "成功！");
		return result;
	}
	
	@Override
	public JSONObject shiftStatus(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		TimelinessDetail timelinessDetail = new TimelinessDetail();
		String flag = request.getParameter("flag");
		timelinessDetail.setId(request.getParameter("id"));
		timelinessDetail.setValibity(flag);
		timelinessDetailMapper.updateTimelinessDetail(timelinessDetail);
		result.put("result_code", "SUCCESS");
		if(flag.equals("0")){
			result.put("result_content", "停用成功！");
		} else if(flag.equals("1")){
			result.put("result_content", "启用成功！");
		}
		return result;
	}
	
	@Override
	public JSONObject save(JSONObject result, TimelinessDetail timelinessDetail, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		if(CommonUtils.checkExistOrNot(timelinessDetail.getId())){
			// 更新
			TimelinessDetail temp = timelinessDetailMapper.findAllExist(timelinessDetail);
			if(CommonUtils.checkExistOrNot(temp) && !timelinessDetail.getId().equals(temp.getId())){
				// 当能够查询到记录且为不同记录时（ID不同）
				// 则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新失败,失败原因:时效明细已存在！");
			} else {
				timelinessDetail.setUpdate_user(SessionUtils.getEMP(request).getUsername());
				timelinessDetailMapper.updateTimelinessDetail(timelinessDetail);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "更新时效明细成功！");
			}
		} else {
			// 新增
			if(CommonUtils.checkExistOrNot(timelinessDetailMapper.findAllExist(timelinessDetail))){
				// 若存在则冲突
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增失败,失败原因:时效明细已存在！");
			} else {
				// 获取主表id下最大节点序号
				Integer maxNumber = timelinessDetailMapper.getMaxNumber(timelinessDetail.getPid());
				if(CommonUtils.checkExistOrNot(maxNumber)){
					// 有最大值
					timelinessDetail.setNumber(maxNumber + 1);
				} else {
					timelinessDetail.setNumber(1);
				}
				// id
				timelinessDetail.setId(UUID.randomUUID().toString());
				// 创建人，更新人相同
				timelinessDetail.setCreate_user(SessionUtils.getEMP(request).getUsername());
				timelinessDetailMapper.insertTimelinessDetail(timelinessDetail);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增时效明细成功！");
				result.put("id", timelinessDetail.getId());
				result.put("number", timelinessDetail.getNumber());
			}
		}
		return result;
	}
	
	@Override
	public HttpServletRequest toForm(HttpServletRequest request) {
		TimelinessDetail timelinessDetail = timelinessDetailMapper.getById(request.getParameter("id"));
		request.setAttribute("timelinessDetail", timelinessDetail);
		request.setAttribute("citys", areaRadarMapper.getRecords(timelinessDetail.getProvince_code()));
		if(CommonUtils.checkExistOrNot(timelinessDetail.getCity_code())){
			// 城市存在
			request.setAttribute("states", areaRadarMapper.getRecords(timelinessDetail.getCity_code()));
			if(CommonUtils.checkExistOrNot(timelinessDetail.getState_code())){
				// 区县存在
				request.setAttribute("streets", areaRadarMapper.getRecords(timelinessDetail.getState_code()));
			}
		}
		return request;
	}
	
	@Override
	public JSONObject del(JSONObject result, HttpServletRequest request) throws Exception {
		result = new JSONObject();
		// 获取当前结点
		TimelinessDetail current_node = timelinessDetailMapper.getById(request.getParameter("id"));
		// 删除节点
		TimelinessDetail param = new TimelinessDetail();
		param.setDl_flag("0");
		param.setId(request.getParameter("id"));
		timelinessDetailMapper.updateTimelinessDetail(param);
		// 查询删除节点后的节点集合
		List<TimelinessDetail> nodes = timelinessDetailMapper.getAfterNodes(current_node.getPid(), current_node.getNumber());
		TimelinessDetail node = null;
		for(int i = 0; i < nodes.size(); i++){
			node = nodes.get(i);
			node.setNumber(node.getNumber() - 1);
			timelinessDetailMapper.updateTimelinessDetail(node);
		}
		result.put("result_code", "SUCCESS");
		return result;
	}
	
	@Override
	public QueryResult<Map<String, Object>> toList(TimelinessDetailQueryParam timelinessDetailQueryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(timelinessDetailMapper.toList(timelinessDetailQueryParam));
		qr.setTotalrecord(timelinessDetailMapper.countAllExist(timelinessDetailQueryParam));
		return qr;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
