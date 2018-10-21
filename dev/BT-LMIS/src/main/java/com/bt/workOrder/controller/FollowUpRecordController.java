package com.bt.workOrder.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.CommonUtils;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.workOrder.common.Constant;
import com.bt.workOrder.model.FollowUpRecord;
import com.bt.workOrder.model.WorkOrderStore;
import com.bt.workOrder.service.FollowUpRecordService;
import com.bt.workOrder.service.ShopGroupService;
import com.bt.workOrder.service.WorkOrderPlatformStoreService;
/**
 * 跟进信息表控制器
 *
 */
@Controller
@RequestMapping("/control/followUpRecordController")
public class FollowUpRecordController extends BaseController {

	/**
	 * 跟进信息表服务类
	 */
	@Resource(name = "followUpRecordServiceImpl")
	private FollowUpRecordService<FollowUpRecord> followUpRecordService;
	
	@Resource(name = "shopGroupServiceImpl")
	private ShopGroupService<T> shopGroupServiceImpl;
	
	@Resource(name="workOrderPlatformStoreServiceImpl")
	private WorkOrderPlatformStoreService<T> workOrderPlatformStoreService;
	
	@RequestMapping("/addfollowUpRecord")
	public void addfollowUpRecord(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
		// 参数构造
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Result result = null;
		parameter = generateParameter(parameter, req);
		try {
			out = res.getWriter();
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("woId"))) {
				Date date = new Date();
				String woId = parameter.getParam().get("woId").toString();
				if(workOrderPlatformStoreService.getWorkOrderStoreById(woId).getFollowUpFlag()==0){
					if(CommonUtils.checkExistOrNot(parameter.getParam().get("version"))){
						int version = Integer.parseInt(parameter.getParam().get("version").toString());
						WorkOrderStore workOrderStore2 = new WorkOrderStore();
						workOrderStore2.setVersion(version);
						workOrderStore2.setId(woId);
						workOrderStore2.setFollowUpFlag(1);
						workOrderPlatformStoreService.updateWoStoreMaster(workOrderStore2);
					}
				}
				FollowUpRecord followUpRecord = new FollowUpRecord();
				followUpRecord.setId(UUID.randomUUID().toString());
				followUpRecord.setWo_store_id(woId);
				followUpRecord.setFollow_up_record(parameter.getParam().get("followuprecord").toString());
				String groupid = shopGroupServiceImpl.queryCurrentGroupByWoId(woId);
				//int groupid = Integer.parseInt(parameter.getCurrentAccount().getTeam_id());
				Map<String, Object> listgroup = followUpRecordService.querysGroupByGroupId(Integer.parseInt(groupid));
				followUpRecord.setCreate_by_group(Integer.parseInt(groupid));
				followUpRecord.setCreate_by(parameter.getCurrentAccount().getUsername());
				followUpRecord.setCreate_by_display(parameter.getCurrentAccount().getName());
				followUpRecord.setUpdate_by(parameter.getCurrentAccount().getUsername());
				followUpRecord.setCreate_time(date);
				followUpRecord.setUpdate_time(date);
				if(listgroup.size()>0){
					int group_type = Integer.parseInt(listgroup.get("group_type").toString());
					String group_name = listgroup.get("group_name").toString();
					followUpRecord.setCreate_by_group_display(group_name);
					if(group_type==0){
						List<Map<String, Object>> listShopGroupStorePower= followUpRecordService.querysShopGroupStorePowerByGroupId(Integer.parseInt(groupid));
						if(listShopGroupStorePower.size()>0){
							String store_code = listShopGroupStorePower.get(0).get("store").toString();
							followUpRecord.setCreate_by_department(store_code);
							Map<String, Object> liststore =followUpRecordService.querysStoreByStoreCode(store_code);
							String store_name = liststore.get("store_name").toString();
							followUpRecord.setCreate_by_department_display(store_name);
						}
					}else if(group_type==1){
						followUpRecord.setCreate_by_department("SO");
						followUpRecord.setCreate_by_department_display("销售运营部");
					}
				}
				followUpRecordService.insert(followUpRecord);
				result=new Result(true, Constant.RESULT_SUCCESS_MSG );
			}
		} catch (Exception e) {
			e.printStackTrace();
			result=new Result(false, Constant.RESULT_FAILURE_MSG + e.getMessage());
		}
		
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	
	@RequestMapping("/followUpRecordList")
	public String followUpRecordList(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
		parameter = generateParameter(parameter, req);
		String woId = parameter.getParam().get("woId").toString();
		List<FollowUpRecord> list = followUpRecordService.querysFollowUpRecordByGroupwoId(woId);
		req.setAttribute("list", list);
		return "work_order/wo_platform_store/followUpRecordList_page";
	}
}
