package com.bt.workOrder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.dao.EmployeeInGroupMapper;
import com.bt.lmis.dao.StoreMapper;
import com.bt.lmis.model.StoreBean;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.param.GroupQueryParam;
import com.bt.workOrder.dao.GroupMapper;
import com.bt.workOrder.model.Group;
import com.bt.workOrder.model.GroupStorePower;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.service.GroupService;
import com.bt.workOrder.utils.WorkOrderUtils;
@Service
public class GroupServiceImpl<T> implements GroupService<T> {

	@Autowired
	private EmployeeInGroupMapper<T> employeeInGroupMapper;
	@Autowired
	private GroupMapper<T> groupMapper;
	@Autowired
	private StoreMapper<T> storeMapper;
	
	public EmployeeInGroupMapper<T> getEmployeeInGroupMapper(){
		return employeeInGroupMapper;
		
	}
	public GroupMapper<T> getGroupMapper(){
		return groupMapper;
		
	}
	
	@Override
	public List<Group> findAllGroups() throws Exception {
		return groupMapper.findAllGroups();
		
	}
	
	@Override
	public JSONObject saveGroup(Group group, HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(CommonUtils.checkExistOrNot(group.getSuperior())) {
			// 提交了上级ID
			if(CommonUtils.checkExistOrNot(group.getId()) && group.getId().equals(group.getSuperior())) {
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别更新失败,失败原因:任何组别不能为自身上级组别！");
				return result;
				
			}
			if(WorkOrderUtils.judgeSubordinateGroupOrNot(group.getSuperior(), group.getId())) {
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别更新失败,失败原因:任何组别不能将自身下级组别作为上级组别！");
				return result;
				
			}
			
		}
		if(CommonUtils.checkExistOrNot(group.getId())){
			// 更新
			Group temp = groupMapper.selectByCode(group.getGroup_code());
			if(CommonUtils.checkExistOrNot(temp) && !temp.getId().equals(group.getId())){
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别更新失败,失败原因:组别代码已存在！");
				return result;
				
			}
			temp = groupMapper.selectByName(group.getGroup_name());
			if(CommonUtils.checkExistOrNot(temp) && !temp.getId().equals(group.getId())){
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别更新失败,失败原因:组别名称已存在！");
				return result;
				
			}
			group.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
			groupMapper.update(group);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "组别更新成功！");
			
		} else {
			// 新增
			if(groupMapper.checkCodeExist(group.getGroup_code()) != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别新增失败,失败原因:组别代码已存在！");
				return result;
				
			}
			if(groupMapper.checkNameExist(group.getGroup_name()) != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别新增失败,失败原因:组别名称已存在！");
				return result;
				
			}
			group.setCreate_by(SessionUtils.getEMP(request).getId().toString());
			groupMapper.add(group);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "组别新增成功！");
			
		}
		return result;
		
	}
	
	@Override
	public JSONObject delGroups(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		Integer[] iA = CommonUtils.strToIntegerArray(request.getParameter("privIds"));
		Integer id = null;
		Group group = null;
		StringBuffer errorMessage = null;
		for(int i = 0; i < iA.length; i++){
			id = iA[i];
			group = (Group)groupMapper.selectById(id);
			if(employeeInGroupMapper.checkEmployeeExist(group.getId()) != 0){
				if(!CommonUtils.checkExistOrNot(errorMessage)){
					// 当没有错误信息时
					errorMessage = new StringBuffer();
					errorMessage.append(group.getGroup_name());
					
				} else {
					errorMessage.append(",").append(group.getGroup_name());
					
				}
				continue;
				
			}
			// 删除构造
			groupMapper.update(new Group(id, SessionUtils.getEMP(request).getId().toString(), "0"));
			
		}
		// 当错误信息不为空时
		if(CommonUtils.checkExistOrNot(errorMessage)){
			result.put("result_code", "BUG");
			errorMessage.append("下存在用户，请清空组别后删除！");
			result.put("result_content", errorMessage);
			
		} else {
			result.put("result_code", "SUCCESS");
			result.put("result_content", "组别删除成功！");
			
		}
		return result;
		
	}
	
	@Override
	public HttpServletRequest toForm(HttpServletRequest request) throws Exception {
		request.setAttribute("superior", groupMapper.findAllGroups());
		//根据ID判断是新增还说修改
		if(CommonUtils.checkExistOrNot(request.getParameter("id"))){
			Integer group= Integer.valueOf(request.getParameter("id"));
			request.setAttribute("group", groupMapper.selectById(group));
			request.setAttribute("workPower", groupMapper.queryWorkPower(group));
			request.setAttribute("storePower", groupMapper.queryStorePower(group));
			request.setAttribute("store", groupMapper.getStore());
			request.setAttribute("carrier", groupMapper.getCarrier());
			
		}
		return request;
		
	}
	
	@Override
	public JSONObject updateStatus(Group group, HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(group.getStatus().equals("1")){
			result.put("result_content", "组别启用成功！");
			
		} else {
			if(employeeInGroupMapper.checkEmployeeExist(group.getId()) != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "组别停用失败！失败原因：组别下存在用户，请清空组别后停用！");
				return result;
				
			} else {
				result.put("result_content", "组别停用成功！");
				
			}
			
		}
		group.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
		groupMapper.update(group);
		result.put("result_code", "SUCCESS");
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> query(GroupQueryParam gQP) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(groupMapper.query(gQP));
		qr.setTotalrecord(groupMapper.count(gQP));
		return qr;
		
	}
	@Override
	public JSONObject delWorkPower(GroupWorkPower groupWorkPower, JSONObject result) throws Exception {
		result= new JSONObject();
		groupMapper.updateWorkPower(groupWorkPower);
		result.put("result_code", "SUCCESS");
		result.put("result_content", "删除成功！");
		return result;
	}
	@Override
	public JSONObject delStorePower(GroupStorePower groupStorePower, JSONObject result) throws Exception {
		result= new JSONObject();
		groupMapper.updateStorePower(groupStorePower);
		result.put("result_code", "SUCCESS");
		result.put("result_content", "删除成功！");
		return result;
		
	}
	@Override
	public HttpServletRequest queryWorkPower(HttpServletRequest request) throws Exception {
		request.setAttribute("workPower", groupMapper.queryWorkPower(Integer.parseInt(request.getParameter("id"))));
		return request;
		
	}
	@Override
	public HttpServletRequest queryStorePower(HttpServletRequest request) throws Exception {
		request.setAttribute("storePower", groupMapper.queryStorePower(Integer.parseInt(request.getParameter("id"))));
		return request;
		
	}
	@Override
	public JSONObject saveStorePower(GroupStorePower groupStorePower, HttpServletRequest request, JSONObject result)
			throws Exception {
		result= new JSONObject();
		if(CommonUtils.checkExistOrNot(groupStorePower.getId())) {
			// 更新
			String temp= groupMapper.getStorePowerId(groupStorePower);
			if(CommonUtils.checkExistOrNot(temp) && !temp.equals(groupStorePower.getId())) {
				result.put("result_code", "FAILURE");
				result.put("result_content", "店铺权限更新失败,失败原因:该店铺权限已存在！");
				return result;
				
			}
			groupStorePower.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
			groupMapper.updateStorePower(groupStorePower);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "店铺权限更新成功！");
			
		} else {
			// 新增
			if(groupMapper.judgeStoreExistOrNot(groupStorePower) != 0) {
				result.put("result_code", "FAILURE");
				result.put("result_content", "店铺权限新增失败,失败原因:该店铺权限已存在！");
				return result;
				
			}
			groupStorePower.setId(UUID.randomUUID().toString());
			groupStorePower.setCreate_by(SessionUtils.getEMP(request).getId().toString());
			groupMapper.addStorePower(groupStorePower);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "店铺权限新增成功！");
			
		}
		return result;
		
	}
	@Override
	public JSONObject getStorePower(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("storePower", groupMapper.getStorePowerById(request.getParameter("id")));
		result.put("store", groupMapper.getStore());
		return result;
		
	}
	@Override
	public QueryResult<T> findAllStoreData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(groupMapper.getSrorePage(qr));
		queryResult.setTotalrecord(groupMapper.selectStoreCount(qr));
		return queryResult;
	}
	@Override
	public void insertGE(String employee, int group, String create_by, String create_time, String update_by, String update_time) {
		groupMapper.insertGE(employee, group, create_by, create_time, update_by, update_time);
	}
	@Override
	public void deleteGE(int groupid) {
		groupMapper.deleteGE(groupid);
	}
	@Override
	public List<Map<String, Object>> selectGE(int group) {
		return groupMapper.selectGE(group);
	}
	@Override
	public List<Map<String, Object>> getWKEMP(int group) {
		return groupMapper.getWKEMP(group);
	}
	@Override
	public List<Map<String, Object>> selectGroup(String id) {
		return groupMapper.selectGroup(id);
	}
	@Override
	public List<Map<String, Object>> checkEMP(String ids,@Param("group")String group) {
		return groupMapper.checkEMP(ids,group);
	}
	
	@Override
	public int addBatchStore(List<StoreBean> list) {
		
		return groupMapper.addBatchStore(list);
	}
	@Override
	public boolean existStore(String sotreName,StoreBean storeBean) {
		// TODO Auto-generated method stub
		List list =storeMapper.StoreByStoreName(sotreName);
		if(list==null||list.size()==0)return false;
		Map<String,Object> map=(Map<String, Object>) list.get(0);
		storeBean.setStore(map.get("store_code").toString());
		return true;
	}
	
	@Override
	public boolean existRecord(String sotreName, String groupId) {
		// TODO Auto-generated method stub
		Map<String,String> param =new HashMap<String,String>();
		param.put("store", sotreName);
		param.put("group", groupId);
		List list =storeMapper.existRecord(param);
		if(list==null||list.size()==0)return false;
		return true;
	}

	
}
