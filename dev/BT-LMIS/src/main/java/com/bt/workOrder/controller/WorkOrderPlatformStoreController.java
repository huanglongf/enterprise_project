package com.bt.workOrder.controller;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.common.XLSXCovertCSVReader;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.common.service.TempletService;
import com.bt.common.util.ExcelExportUtil;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.CookiesUtil;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.bean.Enclosure;
import com.bt.workOrder.common.Constant;
import com.bt.workOrder.model.WorkOrderStore;
import com.bt.workOrder.service.ShopGroupService;
import com.bt.workOrder.service.WorkOrderPlatformStoreService;

/** 
 * @ClassName: WorkOrderStorePlatformController
 * @author Ian.Huang
 * @date 2017年8月22日 下午5:12:09 
 * 
 */
@Controller
@RequestMapping("control/workOrderPlatformStoreController")
public class WorkOrderPlatformStoreController extends BaseController {

	private static final Logger logger = Logger.getLogger(WorkOrderPlatformStoreController.class);
	
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	@Resource(name="workOrderPlatformStoreServiceImpl")
	private WorkOrderPlatformStoreService<T> workOrderPlatformStoreService;
	
	@Resource(name = "shopGroupServiceImpl")
	private ShopGroupService<T> shopGroupServiceImpl;
	
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeServiceImpl;
	
	/**
	 * 加载附件
	 */
	@RequestMapping("loadingEnclosure")
	@ResponseBody
	public JSONObject loadingEnclosure(HttpServletRequest request) {
		List<Map<String, String>>  wList = workOrderPlatformStoreService.getEnclosureByWKID(request.getParameter("woId"));
		List<Enclosure> elist = null;
		JSONObject s = new JSONObject();
		try {
			if(null!=wList && wList.size()!=0){
				elist = new ArrayList<>();
				for (int i = 0; i < wList.size(); i++) {
					Enclosure e = new Enclosure(wList.get(i).get("log"),wList.get(i).get("process_remark"),wList.get(i).get("accessory"));
					elist.add(e);
				}
				s.put("code", "200");
				s.put("msg", "成功");
				s.put("enclosure", elist);
			}else{
				s.put("code", "300");
				s.put("msg", "没有数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			s.put("code", "400");
			s.put("msg", "失败");
		}
		return s;
		
	}
	
	/**
	 * 主页面跳转
	 */
	@RequestMapping("/platform")
	public String platform(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
		if(!CommonUtils.checkExistOrNot(parameter)||!CommonUtils.checkExistOrNot(parameter.getParam())){
			// 参数构造
			parameter = generateParameter(parameter, req);
		}
		String paraJsonStr = JSON.toJSONString(parameter);
		CookiesUtil.setCookie(res, "platform", paraJsonStr, 5*60*1000);
		req.setAttribute("paraMap", parameter.getParam());
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("tabNo"))){
			parameter.getParam().put("tabNo", "1");
		}
		// 配置表头
		req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, null)));
		// 分页控件
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
		parameter.setFirstResult(pageView.getFirstResult());
		parameter.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(workOrderPlatformStoreService.search(parameter), parameter.getPage());
		if(CommonUtils.checkExistOrNot(parameter.getCurrentAccount().getTeamId())) {
			Map<String,Object> countMap=workOrderPlatformStoreService.queryCount(parameter);
			req.setAttribute("countMap", countMap);
		}
		req.setAttribute("pageView", pageView);
		
		if(!"table".equals(parameter.getParam().get("pageName")) ){
			try{
				String teamId="";
				Employee emp = SessionUtils.getEMP(req);
				List<String> teamIdList = shopGroupServiceImpl.queryTeamIdById(emp.getId());
				if(teamIdList.size()>0) {
					teamId=teamIdList.get(0);
				}
				int userType = Integer.parseInt(workOrderPlatformStoreService.getStoreById(teamId).get("groupType").toString());
				if(parameter.getCurrentAccount().getUsername().equals("admin")) {
					userType=-1;
				}
				req.setAttribute("userType", userType);
				if(userType==1) {
					req.setAttribute("ifGroupShare", 0);
				}else {
					req.setAttribute("ifGroupShare", workOrderPlatformStoreService.getStoreById(teamId).get("ifShare"));
				}
			}catch (Exception e) {
				int userType = -1;
				req.setAttribute("userType", userType);
			}
			req.setAttribute("storeList", storeServiceImpl.findAll());
			//
			req.setAttribute("woType", workOrderPlatformStoreService.listWoType());
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("wo_type_display"))) {
				parameter.getParam().put("woType", parameter.getParam().get("wo_type_display"));
				req.setAttribute("errorType", workOrderPlatformStoreService.listErrorTypeByWoType(parameter));
			}
		}
		// 返回页面
		return "table".equals(parameter.getParam().get("pageName")) ? "/templet/table" : "work_order/wo_platform_store/"+parameter.getParam().get("pageName");
	}

	/**
	 * 跳转时间轴
	 */
	@RequestMapping("/toTimeLine")
	public String toTimeLine(String readonly,Parameter parameter, HttpServletRequest req){
		req.setAttribute("woId", req.getParameter("woId"));
		String url = "/work_order/wo_platform_store/timeline";
		if(CommonUtils.checkExistOrNot(readonly)){
			if("1".equals(readonly)){
				url = "/work_order/wo_platform_store/timeline2";
			}
			if("2".equals(readonly)){
				url = "/work_order/wo_platform_store/timeline3";
			}
		}
		return url;
	}
	
	@RequestMapping("/toNewer")
	public String toNewer(Parameter parameter, HttpServletRequest req){
	    return "/work_order/wo_platform_store/newer";
	}
	
	/**
	 * 新建跳转
	 */
	@RequestMapping("/toForm")
	public String toForm(Parameter parameter, HttpServletRequest req){
		parameter = generateParameter(parameter, req);
		String groupType = parameter.getParam().get("type").toString();
		String storeCode="";
		try{
			String teamId = parameter.getCurrentAccount().getTeamId();
			if(groupType.equals("0")) {
				//TODO 空判断
				storeCode=CommonUtils.checkExistOrNot(workOrderPlatformStoreService.findStroeCodeByGroupID(teamId))?workOrderPlatformStoreService.findStroeCodeByGroupID(teamId).get(0):"";
			}
			List<Map<String, Object>> groupList =workOrderPlatformStoreService.findGroupListByTeamid(teamId);
			req.setAttribute("storeCode", storeCode);
			req.setAttribute("groupType", groupType);
			req.setAttribute("woType", workOrderPlatformStoreService.listWoType());
			req.setAttribute("storeList", storeServiceImpl.findAll());
			req.setAttribute("groupList",groupList);
			req.setAttribute("groupListSize", groupList.size());
			//flag 判断跳转方式
			req.setAttribute("flag", 0);
			//判断add页面里的list
			req.setAttribute("sel_flag", 1);
			//判断暂存后修改没改的flag
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/work_order/wo_platform_store/add_form";
	}
	
	/**
	 * 获取运单
	 */
	@RequestMapping("/getOrder")
	public String getOrder(Parameter parameter, HttpServletRequest req){
		try{
			req.setAttribute("records", workOrderPlatformStoreService.listOrderByOrderNo(generateParameter(parameter, req).getParam().get("orderNo").toString()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/work_order/wo_platform_store/add_list";
	}
	
	/**
	 * main双击跳转
	 */
	@RequestMapping("/transJSP")
	public String transJSP(Parameter parameter, HttpServletRequest req){
		parameter = generateParameter(parameter, req);
		String url = null;
		try{
			Map<String,Object> map = parameter.getParam();
			String id = map.get("id").toString();
			WorkOrderStore workOrderStore = workOrderPlatformStoreService.getWorkOrderStoreById(id);
			int submitFlag = workOrderStore.getSubmitFlag();
			
			String teamId = parameter.getCurrentAccount().getTeamId();
			List<Map<String, Object>> groupList =workOrderPlatformStoreService.findGroupListByTeamid(teamId);
			
			// 0是未提交 1是已提交
			if(submitFlag == 0) {
				//TODO 还差附件
				req.setAttribute("woType", workOrderPlatformStoreService.listWoType());
				// 若异常类型存在则需加载
				if(CommonUtils.checkExistOrNot(workOrderStore.getWoType())) {
					parameter.getParam().put("woType", workOrderStore.getWoType());
					parameter.getParam().put("status", "1");
					req.setAttribute("errorType", workOrderPlatformStoreService.listErrorTypeByWoType(parameter));
				}
				Map<String, Object> group = workOrderPlatformStoreService.getStoreById(workOrderStore.getCreateByGroup());
				//
				req.setAttribute("groupType", group.get("groupType").toString());
				req.setAttribute("groupList", groupList);
				req.setAttribute("groupListSize", groupList.size());
				req.setAttribute("workOrderStore", workOrderStore);
				String expectationProcessTimeStr = DateUtil.formatDate2(workOrderStore.getExpectationProcessTime());
				req.setAttribute("expectationProcessTimeStr", expectationProcessTimeStr);
				req.setAttribute("records", workOrderPlatformStoreService.listWoOrderByWoId(id));
				req.setAttribute("sel_flag", 2);
				req.setAttribute("flag", 1);
				req.setAttribute("storeList", storeServiceImpl.findAll());
				url = "/work_order/wo_platform_store/add_form";
			}else if(submitFlag==1){
				url = "redirect://control/workOrderPlatformStoreController/workOrderHandle.do?woId="+id;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 运单详细
	 */
	@RequestMapping("/orderDetail")
	public String orderDetail(String readonly,Parameter parameter, HttpServletRequest req){
		parameter = generateParameter(parameter, req);
		String url="/work_order/wo_platform_store/package_list";
		try{
			if(CommonUtils.checkExistOrNot(readonly)){
				if("1".equals(readonly)){
					url = "/work_order/wo_platform_store/package_list2";
				}
				if("2".equals(readonly)){
					url = "/work_order/wo_platform_store/package_list3";
				}
			}
			Map<String,Object> map = parameter.getParam();
			String id = map.get("id").toString();
			//sel_flag=1是新建 sel_flag=2是编辑
			int sel_flag = Integer.parseInt(map.get("sel_flag").toString());
			String waybill = map.get("waybill").toString();
			
			List<Map<String,Object>> packages = new ArrayList<>();
			Map<String, Object> detail = new HashMap<>();
			if(sel_flag == 1) {
				//id 是tb_warehouse_express_data的id
				packages = workOrderPlatformStoreService.listOrderDetailByWaybill(waybill);
				detail = workOrderPlatformStoreService.getOrderById(id);
			}
			if(sel_flag == 2) {
				//id 是wo_order的id
				packages = workOrderPlatformStoreService.listOrderDetailByWoOrderId(id);
				detail = workOrderPlatformStoreService.getWoOrderById(id);
			}
			req.setAttribute("detail", detail);
			req.setAttribute("packages", packages);
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 工单操作
	 */
	@RequestMapping("/workOrderAction")
	public void workOrderAction(Parameter parameter, HttpServletRequest req, HttpServletResponse res){
		
		parameter = generateParameter(parameter, req);
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Result result = null;
		try{
			out = res.getWriter();
			result = workOrderPlatformStoreService.worderOrderAction(parameter);
//			List<Map<String,Object>> packages = null;
//			req.setAttribute("packages", packages);
		}catch(Exception e){
			e.printStackTrace();
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 成员获取工单操作
	 */
	@RequestMapping("/empGetWorkOrder")
	public void empGetWorkOrder(Parameter parameter, HttpServletRequest req, HttpServletResponse res){
		parameter = generateParameter(parameter, req);
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Result result = null;
		try{
			out = res.getWriter();
			result = workOrderPlatformStoreService.empGetWorkOrder(parameter);
		}catch(Exception e){
			e.printStackTrace();
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	/**
	 * 工单获取组内成员
	 */
	@RequestMapping("getEmpByTeamId")
	public void queryEmp(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//String groupId=SessionUtils.getEMP(request).getTeam_id();
			String teamId="";
			Employee emp=SessionUtils.getEMP(request);
			List<String> teamIdList = shopGroupServiceImpl.queryTeamIdById(emp.getId());
			if(teamIdList.size()>0) {
				teamId=teamIdList.get(0);
			}
			Map<String,String>param=new HashMap<String,String>();
			param.put("team_id", teamId);
			param.put("owerId", SessionUtils.getEMP(request).getId().toString());
			ArrayList<?> list = shopGroupServiceImpl.get_team_emp(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	/**
	 * 获取组
	 */
	@RequestMapping("getSOTeam")
	public void getSOTeam(String teamType,HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<Map<String,String>> list = new ArrayList<>();
			if(CommonUtils.checkExistOrNot(teamType)&&"ALL".equals(teamType)){
				list = shopGroupServiceImpl.getSOteam(null);
			}else{
				list = shopGroupServiceImpl.getSOteam("1");
			}
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	/**
	 * 获取组内成员
	 */
	@RequestMapping("getUserByTeam")
	public void getUserByTeam(Parameter parameter,HttpServletRequest request, HttpServletResponse res) {
		parameter = generateParameter(parameter, request);
		PrintWriter out = null;
		try {
			String teamId = CommonUtils.checkExistOrNot(parameter.getParam().get("team_id"))?parameter.getParam().get("team_id").toString():null;
			out = res.getWriter();
			Map<String,String>param=new HashMap<String,String>();
			param.put("team_id",teamId);
			param.put("owerId", SessionUtils.getEMP(request).getId().toString());
			ArrayList<?> list = shopGroupServiceImpl.get_team_emp(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 工单首页跳转
	 */
	@RequestMapping("workOrderIndex")
	public String workOrderIndex(HttpServletRequest request, HttpServletResponse response){
		return "work_order/work_order_index";
	}
	
	/**
	 * 工单处理跳转
	 */
	@RequestMapping("workOrderHandle")
	public String workOrderHandle(String woId,String readonly, HttpServletRequest request, HttpServletResponse response) {
		String url="";
		try{
			url = "work_order/wo_platform_store/work_order_handle";
			WorkOrderStore wos = workOrderPlatformStoreService.getWorkOrderStoreById(woId);
			if(CommonUtils.checkExistOrNot(readonly)){
				if("1".equals(readonly)){
					url = "work_order/wo_platform_store/work_order_handle2";
				}
				if("2".equals(readonly)){
					url = "work_order/wo_platform_store/work_order_handle3";
				}
			}
			// 工单主表信息
			request.setAttribute("workOrderStore", wos);
			String ownerGroup = wos.getOwnerGroup();
			Integer ownerGroupType = Integer.parseInt(workOrderPlatformStoreService.getStoreById(ownerGroup).get("groupType").toString());
			
			request.setAttribute("ownerGroupType", ownerGroupType);
			request.setAttribute("woTypeRe", workOrderPlatformStoreService.listWoTypeByDepartment(wos.getProcessDepartment()));
			
			String expectationProcessTimeStr = DateUtil.formatDate2(wos.getExpectationProcessTime());
			request.setAttribute("expectationProcessTimeStr", expectationProcessTimeStr);
			
			if(wos.getWoNo().split("-").length==1){
				List<Map<String,Object>> split_list = workOrderPlatformStoreService.getSplitList(wos.getWoNo()+"-");
				request.setAttribute("split_list", split_list);
				request.setAttribute("split_list_show", true);
				boolean opraFlag = true;
				for(Map<String,Object> map:split_list){
					String woStatus = map.get("wo_status").toString();
					if("0".equals(woStatus)||"1".equals(woStatus)){
						opraFlag=false;
						break;
					}
				}
				request.setAttribute("opraFlag", opraFlag);
				// 绑定订单明细
				request.setAttribute("records", workOrderPlatformStoreService.listWoOrderByWoId(woId));
			}else{
				request.setAttribute("split_list_show", false);
				String [] fatherWoNo = wos.getWoNo().split("-");
				String fatherWOId = workOrderPlatformStoreService.getIdByWoNo(fatherWoNo[0]);
				// 绑定订单明细
				request.setAttribute("records", workOrderPlatformStoreService.listWoOrderByWoId(fatherWOId));
			}
			request.setAttribute("flag", 1);
			// 当前用户所在组别类型
			Parameter parameter = generateParameter(request);
			String teamIds = parameter.getCurrentAccount().getTeamId();
			String[] teamIdArray = teamIds.split(",");
			String groupid = teamIdArray[0];
			Integer groupType = Integer.parseInt(workOrderPlatformStoreService.getStoreById(groupid).get("groupType").toString());
			//Integer groupType = Integer.parseInt(workOrderPlatformStoreService.getStoreById(SessionUtils.getEMP(request).getTeam_id()).get("groupType").toString());
			// 是否显示获取按钮
			// 处理部门为销售运营，当前用户组别为销售运营，工单状态不为已完结，当前处理人为空或当前处理人所在组别类型为销售运营且当前处理人不为自己
			request.setAttribute("gainFlag",
					wos.getProcessDepartment() == 1
					&& groupType == 1
					&& wos.getWoStatus() != 2
					&& wos.getWoStatus() != 3
					&& (!CommonUtils.checkExistOrNot(wos.getCurrentProcessor())
							|| 
							(
									Integer.parseInt(workOrderPlatformStoreService.getStoreById(wos.getCurrentProcessorGroup()).get("groupType").toString()) == 1
									&& Integer.parseInt(wos.getCurrentProcessor()) != SessionUtils.getEMP(request).getId()
							)
							)
					);
			// 是否能够处理
			boolean processFlag = workOrderPlatformStoreService.judgeProcessOrNot(generateParameter(request));
			request.setAttribute("processFlag", processFlag);
			if(wos.getCreateBy().equals(wos.getCurrentProcessor())){
				request.setAttribute("showUpdate", true);
			}
			request.setAttribute("groupType", groupType);
			if(processFlag) {
				// 能够处理
				// 工单类型
				request.setAttribute("woType", workOrderPlatformStoreService.listWoType());
				// 组别类型
				
				// 当时店铺客服时
				if(groupType == 0) {
					// 判断是否需要显示异常工单开关
					request.setAttribute("soReply", workOrderPlatformStoreService.judgeSOReplyOrNot(woId) > 0);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * @Title: loadingHome1
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午4:23:47
	 */
	@RequestMapping("loadingHome1")
	@ResponseBody
	public JSONObject loadingHome1(HttpServletRequest request) {
		return workOrderPlatformStoreService.loadingHome1(generateParameter(request));
	}
	
	/**
	 * @Title: loadingHome2
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年8月24日 下午5:17:30
	 */
	@RequestMapping("loadingHome2")
	@ResponseBody
	public JSONObject loadingHome2(HttpServletRequest request) {
		return workOrderPlatformStoreService.loadingHome2(generateParameter(request));
	}
	
	/**
	 * @Title: listErrorTypeByWoType
	 * @Description: 根据工单类型找异常类型
	 * @param request
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 上午11:06:18
	 */
	@RequestMapping("listErrorTypeByWoType")
	@ResponseBody
	public List<Map<String, Object>> listErrorTypeByWoType(HttpServletRequest request) {
		return workOrderPlatformStoreService.listErrorTypeByWoType(generateParameter(request));
	}
	
	/**
	 * 根据处理部门找工单类型
	 */
	@RequestMapping("listWoTypeByDepartment")
	@ResponseBody
	public List<Map<String, Object>> listWoTypeByDepartment(HttpServletRequest request) {
		return workOrderPlatformStoreService.listWoTypeByDepartment(Integer.parseInt(generateParameter(request).getParam().get("so_flag").toString()));
	}
	
	/**
	 * @Title: delTempWorkOrder
	 * @Description: 删除临时工单
	 * @param request
	 * @return: Result
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午12:37:31
	 */
	@RequestMapping("delTempWorkOrder")
	@ResponseBody
	public Result delTempWorkOrder(HttpServletRequest request) {
		return workOrderPlatformStoreService.delTempWorkOrder(request.getParameter("woId").toString());
	}
	
	/**
	 * @Title: exportWorkOrder
	 * @Description: 导出工单
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午10:43:07
	 */
	@RequestMapping("exportWorkOrder")
	public void exportWorkOrder(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = generateParameter(request);
		List<Map<String, Object>> titleList = templetService.loadingTableColumnConfig(parameter);
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for(int i = 0; i < titleList.size(); i++) {
			title.put(titleList.get(i).get("column_code").toString(), titleList.get(i).get("column_name").toString());
		}
		title.put("处理意见", "处理意见");
		try {
			ExcelExportUtil.exportExcelByStream(Constant.EXPORT_NAME + "_" + parameter.getCurrentAccount().getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, Constant.EXPORT_NAME, title, workOrderPlatformStoreService.exportWorkOrder(parameter), response);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: judgeShareGroupOrNot
	 * @Description: 判断是否组内共享
	 * @param request
	 * @return: Result
	 * @author: Ian.Huang
	 * @date: 2017年8月30日 上午10:55:48
	 */
	@RequestMapping("judgeShareGroupOrNot")
	@ResponseBody
	public Result judgeShareGroupOrNot(HttpServletRequest request) {
		
		String teamId="";
		Employee emp = SessionUtils.getEMP(request);
		List<String> teamIdList = shopGroupServiceImpl.queryTeamIdById(emp.getId());
		if(teamIdList.size()>0) {
			teamId=teamIdList.get(0);
		}
		int userType = Integer.parseInt(workOrderPlatformStoreService.getStoreById(teamId).get("groupType").toString());
		if(userType==1) {
			return (new Result(false,""));
		}
		return new Result(Integer.parseInt(workOrderPlatformStoreService.getStoreById(teamId).get("ifShare").toString()) == 1, "");
		//return new Result(Integer.parseInt(workOrderPlatformStoreService.getStoreById(SessionUtils.getEMP(request).getTeam_id()).get("ifShare").toString()) == 1, "");
	}
	
	/**
	 * 判断当前用户是销售运营还是物流客服
	 */
	@RequestMapping("getUserType")
	@ResponseBody
	public Result getUserType(HttpServletRequest request) {
		String teamId="";
		Employee emp = SessionUtils.getEMP(request);
		List<String> teamIdList = shopGroupServiceImpl.queryTeamIdById(emp.getId());
		if(teamIdList.size()>0) {
			teamId=teamIdList.get(0);
		}
		return new Result(Integer.parseInt(workOrderPlatformStoreService.getStoreById(teamId).get("groupType").toString()) == 1, "");
	}
	
	/**
	 * 增量提醒，获取数量
	 */
	@RequestMapping("countAllTab")
	@ResponseBody
	public Map<String, Integer> countAllTab(Parameter parameter,HttpServletRequest request) {
		parameter = generateParameter(parameter, request);
		Map<String,Integer> map = workOrderPlatformStoreService.countAllTab(parameter);
		return map;

	}
	/**
	 * 返回工单平台main页面
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("backToMain")
	public String backToMain(Parameter parameter,HttpServletRequest request,HttpServletResponse response) {
		Cookie cookie = CookiesUtil.getCookieByName(request,"platform");
		if(CommonUtils.checkExistOrNot(cookie)){
			String res = URLDecoder.decode(cookie.getValue());
			parameter = JSON.parseObject(res,parameter.getClass());
			parameter.getParam().put("pageName", "main");
		}
		return platform(parameter,request,response);
	}
	
	/**
	 * 一键导出工单
	 * @throws  
	 */
	@RequestMapping("aKeyExport")
	public void aKeyExport(Parameter parameter,HttpServletRequest request,HttpServletResponse response)  {
		try {
			parameter = generateParameter(parameter, request);
			String woId = parameter.getParam().get("woId").toString();
			workOrderPlatformStoreService.aKeyExport(woId,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("downloadZip")
	public void downloadZip(Parameter parameter,HttpServletRequest request,HttpServletResponse response)  {
		try {
			parameter = generateParameter(parameter, request);
			String woId = parameter.getParam().get("woId").toString();
			workOrderPlatformStoreService.downloadZip(woId,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新建页面里的 查询 根据工单号 模糊查询所有工单 用来关联
	 */
	@ResponseBody
	@RequestMapping("/getWorkOrderByWoNo")
	public JSONObject getWorkOrderByWoNo(HttpServletRequest request){
		JSONObject result=new JSONObject();
		try{
			String woNo = request.getParameter("woNo");
			String woId = request.getParameter("woId");
			result.put("result_code", "SUCCESS");
			List<Map<String, Object>> wo = new ArrayList<>();
			if(CommonUtils.checkExistOrNot(woId)){
				wo = workOrderPlatformStoreService.getWOByWoNoNoId(woNo,woId);
			}else{
				wo = workOrderPlatformStoreService.getWorkOrderByWoNo(woNo);
			}
			result.put("wo", wo);
		}catch(Exception e){
			result.put("result_code", "failed");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getIdByWoNos")
	public JSONObject getIdByWoNos(String woNoStr,HttpServletRequest request){
		JSONObject result=new JSONObject();
		try{
			String[] woNos = woNoStr.split(",");
			StringBuffer sb = new StringBuffer("'");
			for(String woNo:woNos){
				sb.append(woNo+"','");
			}
//			sb.deleteCharAt(sb.length() - 2);
			sb.delete(sb.length() - 2,sb.length());
			result.put("result_code", "SUCCESS");
			List<Map<String, Object>> wo = workOrderPlatformStoreService.getIdByWoNos(sb.toString());
			result.put("wo", wo);
		}catch(Exception e){
			result.put("result_code", "failed");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getSplitList")
	public JSONObject getSplitList(String woNo,HttpServletRequest request){
		JSONObject result=new JSONObject();
		try{
			result.put("result_code", "SUCCESS");
			List<Map<String, Object>> wo = workOrderPlatformStoreService.getSplitList(woNo+"-");
			result.put("wo", wo);
		}catch(Exception e){
			result.put("result_code", "failed");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 处理界面 关联工单
	 */
	@ResponseBody
	@RequestMapping("/woAssociation")
	public JSONObject woAssociation(Parameter parameter,HttpServletRequest request){
		parameter = generateParameter(parameter, request);
		JSONObject result=new JSONObject();
		result = workOrderPlatformStoreService.addStoreProcessLog(parameter);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/accessoryDownload")
	public JSONObject accessoryDownload(String serverName,String originName,HttpServletRequest request,HttpServletResponse resp){
		try{
			String filePathDir = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname());
			String filePath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				filePath=filePathDir+"/"+originName;
			} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
				filePath=filePathDir+"\\"+originName;
			}
			String root = CommonUtils.getAllMessage("config", "NGINX_FILE_DOWNLOAD");
			String serverPath = root+serverName;
			FileUtil.createImage(serverPath,filePath);
			FileUtil.downloadFile(filePath,originName,resp);
		}catch (Exception e) {
			System.out.println("downloadFile,异常error");
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/batchProcess", produces = "application/json;charset=UTF-8")
	public String batchProcess(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		Employee user = SessionUtils.getEMP(req);
		JSONObject obj = new JSONObject();
		StringBuffer results = new StringBuffer("");
		if (null != user) {
			try {
				String fileName = file.getOriginalFilename();
				String prefix = fileName.split("[.]")[fileName.split("[.]").length - 1];
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
				// 判断文件是否为空
				if (!file.isEmpty()) {
					if (prefix.equals("xlsx")) {
						// 文件保存路径
						String uuid = UUID.randomUUID().toString();
						String filePath = CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL")+ (uuid + "." + prefix);
						// 转存文件
						file.transferTo(new File(filePath));
						// 文件内容集合
						List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 60);
						if (list == null || list.size() == 0) {
							obj.put("code", "400");
							obj.put("msg", "文件内容有误!");
							return obj.toString();
						}
						String[] titles = list.get(0);
						int woNoNum = 0;
						int processNum = 0;
						for(int i=0;i<titles.length;i++){
							String title = titles[i];
							if("工单号".equals(title)){
								woNoNum=i;
							}
							if("处理意见".equals(title)){
								processNum=i;
							}
						}
						list.remove(list.get(0));
						
						for (String[] row : list) {
							String woNo = row[woNoNum];
							String processRemark = row[processNum];
							if(!CommonUtils.checkExistOrNot(woNo)||!CommonUtils.checkExistOrNot(processRemark)){
								continue;
							}
							
							Map<String,Object> wos = workOrderPlatformStoreService.getWOByWoNo(woNo);
							if(CommonUtils.checkExistOrNot(wos)){
								Map<String,Object> parms = new HashMap<>();
								parms.put("action", "REPLY");
								parms.put("woId", wos.get("id"));
								parms.put("processInfo", processRemark);
								parms.put("version", wos.get("version"));
								Parameter parameter = new Parameter(user, parms);
								boolean processFlag = workOrderPlatformStoreService.judgeProcessOrNot(parameter);
								if(processFlag){
									Result result = workOrderPlatformStoreService.worderOrderAction(parameter);
									results.append("工单号：（"+woNo+"）"+result.getMsg()+"\n");
								}else{
									results.append("工单号：（"+woNo+"）没有回复权限\n");
								}
							}else{
								results.append("工单号（"+woNo+"）工单不存在\n");
							}
						}
						obj.put("code", "200");
						obj.put("msg", "上传成功!");
						if(!CommonUtils.checkExistOrNot(results)){
							results.append("没有要回复的工单，请检查工单号和处理意见是否为空\n");
						}
						obj.put("msg", results);
					} else {
						obj.put("code", "400");
						obj.put("msg", "文件类型必须为[.xlsx],您上传的文件类型为[." + prefix + "]!");
						return obj.toString();
					}
				} else {
					obj.put("code", "500");
					obj.put("msg", "必填参数为空!");
					return obj.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
			}
		} else {
			obj.put("code", "400");
			obj.put("msg", "Session过期,请重新登陆后在操作!");
		}
		return obj.toString();
	}
}
