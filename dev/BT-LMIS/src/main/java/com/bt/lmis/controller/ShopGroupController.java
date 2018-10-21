package com.bt.lmis.controller;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.common.CommonUtils;
import com.bt.common.ExcelExportUtil;
import com.bt.common.XLSXCovertCSVReader;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreBean;
import com.bt.lmis.model.StoreEmployee;
import com.bt.lmis.model.WorkOrderAndLevel;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.service.ShopGroupService;

@Controller
@RequestMapping("/control/shopGroupController")
public class ShopGroupController extends BaseController {

	private static final Logger logger = Logger.getLogger(ShopGroupController.class);

	
	@Resource(name = "shopGroupServiceImpl")
	private ShopGroupService<T> shopGroupServiceImpl;
	
	@Resource(name="templetServiceImpl")
	private TempletService<T> templetService;
	
	@Resource(name = "shopGroupServiceImpl")
	private ShopGroupService<StoreBean> shopGroupServiceImpl2;
	
	@Resource(name = "shopGroupServiceImpl")
	private ShopGroupService<Employee> shopGroupServiceImpls;
	
	/***
	 * 组别信息查询列表
	 * @param groupPar
	 * @param request
	 * @return
	 */
	@RequestMapping("listGroup")
	public String listGroup(GroupParam groupPar, HttpServletRequest request) {
		PageView<Group> pageView = new PageView<Group>(groupPar.getPageSize() == 0 ? BaseConst.pageSize : groupPar.getPageSize(), groupPar.getPage());
		groupPar.setFirstResult(pageView.getFirstResult());
		groupPar.setMaxResult(pageView.getMaxresult());
		groupPar.setGroup_code(request.getParameter("group_code"));
		groupPar.setGroup_name(request.getParameter("group_name"));
		groupPar.setIf_allot(request.getParameter("if_allot"));
		QueryResult<Group> qrRaw = shopGroupServiceImpl.querysGroup(groupPar);
		ArrayList<?> seniorQuery = shopGroupServiceImpl.querySeniorQueryGroupSup(groupPar);
		pageView.setQueryResult(qrRaw, groupPar.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("groupPar", groupPar);
		request.setAttribute("groupCode", request.getParameter("group_code"));
		request.setAttribute("groupName", request.getParameter("group_name"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("superior", request.getParameter("superior"));
		request.setAttribute("if_allot", request.getParameter("if_allot"));
		request.setAttribute("seniorQuery", seniorQuery);
		return "/work_order/shop/listGroup";
	}


	/****
	 * 组别关联工单查询(新增与编辑)
	 * @param groupPar
	 * @param request
	 * @return
	 */
	
	

	
	@RequestMapping("newlyGroupWord")
	public String newlyGroupWord(GroupParam groupPar, HttpServletRequest request) {
		return "/work_order/shop/add_group";
	}
	
	
	
	/***
	 * 修改主表信息(s_group)
	 * @param res
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSgroup.do")
	public String updateSgroup(HttpServletResponse res, HttpServletRequest request) {
		try {
			String id = this.getParamMap(request).get("id").toString();
			ArrayList<?> list = shopGroupServiceImpl.queryWkGroupSupAndUpdate(id);
			ArrayList<?> listWL = shopGroupServiceImpl.findWorkOrderType();//工单类型
			ArrayList<?> listWkType = shopGroupServiceImpl.findWkType();//工单类型
			ArrayList<?> listDP = shopGroupServiceImpl.findStore();//店铺
			ArrayList<?> listLog = shopGroupServiceImpl.queryLogisticsCode();//物流商
			Map<String,Object> params=shopGroupServiceImpl.getGroupData(id);
			params.put("if_allot", params.get("if_Share").toString());
			
			List<WorkOrderAndLevel> listjb = new ArrayList<>();
			for(int j=0;j<listWL.size();j++){
				Map<String,Integer> map = (Map) listWL.get(j);
				String name =String.valueOf(map.get("name"));
				String code =String.valueOf(map.get("CODE"));
				
				List<?> levelList = shopGroupServiceImpl.workOrderAndLevel(code);
				String strList = "";
				for(int i=0;i<levelList.size();i++ ){
					Map<String,Integer> mapLev = (Map) levelList.get(i);
					String level =String.valueOf(mapLev.get("LEVEL")).trim();
					strList +=level+",";
				}
				WorkOrderAndLevel workLevel = new WorkOrderAndLevel();
				if(!"".equals(strList)){
					String lev = strList.substring(0, strList.length()-1);
		            if(lev.contains(",")){
		            	String  []  ListLev = lev.split(",");
		            	for(int u=0;u<ListLev.length;u++){
		            		if(ListLev[u].contains("01")){
		            			workLevel.setLevelOne("1");
		            		}else if(ListLev[u].contains("02")){
		            			workLevel.setLevelTwo("2");
		            		}else if(ListLev[u].contains("03")){
		            			workLevel.setLevelThree("3");
		            		}else if(ListLev[u].contains("04")){
		            			workLevel.setLevelFour("4");
		            		}else if(ListLev[u].contains("05")){
		            			workLevel.setLevelFive("5");
		            		}else if(ListLev[u].contains("06")){
		            			workLevel.setLevelSix("6");
		            		}else if(ListLev[u].contains("07")){
		            			workLevel.setLevelSeven("7");
		            		}else if(ListLev[u].contains("08")){
		            			workLevel.setLevelEight("8");
		            		}else if(ListLev[u].contains("09")){
		            			workLevel.setLevelNine("9");
		            		}else if(ListLev[u].contains("10")){
		            			workLevel.setLevelTen("10");
		            		}
		            	}
					}else{
						if(lev.contains("01")){
	            			workLevel.setLevelOne("1");
	            		}else if(lev.contains("02")){
	            			workLevel.setLevelTwo("2");
	            		}else if(lev.contains("03")){
	            			workLevel.setLevelThree("3");
	            		}else if(lev.contains("04")){
	            			workLevel.setLevelFour("4");
	            		}else if(lev.contains("05")){
	            			workLevel.setLevelFive("5");
	            		}else if(lev.contains("06")){
	            			workLevel.setLevelSix("6");
	            		}else if(lev.contains("07")){
	            			workLevel.setLevelSeven("7");
	            		}else if(lev.contains("08")){
	            			workLevel.setLevelEight("8");
	            		}else if(lev.contains("09")){
	            			workLevel.setLevelNine("9");
	            		}else if(lev.contains("10")){
	            			workLevel.setLevelTen("10");
	            		}
					}
				}
				workLevel.setOrderCode(code);
	            workLevel.setOrderName(name);
	            listjb.add(workLevel);
			}
			Group modelSup = shopGroupServiceImpl.querysGroupById(getParamMap(request));
			request.setAttribute("listWL", listjb);
			request.setAttribute("list", list);
			request.setAttribute("listWkType", listWkType);
			request.setAttribute("listDP", listDP);
			request.setAttribute("listLog", listLog);
			request.setAttribute("obj", params);
			request.setAttribute("typeUpdate", "2");
			request.setAttribute("modelSup", modelSup);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/work_order/shop/groupWordOrder";
	}
	
	

	
	@RequestMapping("/search")
	public String search(HttpServletRequest request,GroupParam groupPar) {
		QueryResult<Group> qrRaw=null;
		Map<String, Object> groupMap=null;
		String groupType ="";
		Parameter parameter = generateParameter(request);
		Employee emp=SessionUtils.getEMP(request);
		List<String> teamIdList = shopGroupServiceImpl.queryTeamIdById(emp.getId());
		String teamId=teamIdList.toString();
		teamId=teamId.replace("[", "");
		teamId=teamId.replace("]", "");
		if(!emp.getUsername().equals("admin")) {
			if(teamIdList.size()>0) {
				teamId=teamIdList.get(0);
			}
			if(teamId==null||"".equals(teamId)){
				teamId="-1";
			}
		}
		
		PageView<Group> pageView = new PageView<Group>(groupPar.getPageSize() == 0 ? BaseConst.pageSize : groupPar.getPageSize(), groupPar.getPage());
		groupPar.setFirstResult(pageView.getFirstResult());
		groupPar.setMaxResult(pageView.getMaxresult());
		groupPar.setGroup_code(request.getParameter("group_code"));
		groupPar.setGroup_name(request.getParameter("group_name"));
		groupPar.setIf_allot(request.getParameter("if_allot"));
		groupPar.setTeam_id(teamId);
		ArrayList<?> seniorQuery = shopGroupServiceImpl.querySeniorQueryGroupSup(groupPar);
		if(!emp.getUsername().equals("admin")){
			groupMap = shopGroupServiceImpl.getGroupTypeById(teamId);
			if(groupMap!=null){
				groupType = groupMap.get("group_type").toString();
			}
		}
		if(!emp.getUsername().equals("admin")&&groupMap!=null&&groupType.equals("1")){
			groupPar.setGroup_type(groupType);
			qrRaw = shopGroupServiceImpl.queryGroup(groupPar);
		}else{
			qrRaw = shopGroupServiceImpl.querysGroup(groupPar);
		}
		pageView.setQueryResult(qrRaw, groupPar.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("groupPar", groupPar);
		request.setAttribute("groupCode", request.getParameter("group_code"));
		request.setAttribute("groupName", request.getParameter("group_name"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("superior", request.getParameter("superior"));
		request.setAttribute("if_allot", request.getParameter("if_allot"));
		request.setAttribute("seniorQuery", seniorQuery);	
		request.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
		// 表格功能配置
		Map<String, Object> config = new HashMap<String, Object>();
	    request.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(parameter.getParam().get("tableName").toString(), true, config)));
//		return "/work_order/shop/"+parameter.getParam().get("pageName");
	    return "table".equals(parameter.getParam().get("pageName")) ? "/templet/table" : "/work_order/shop/"+parameter.getParam().get("pageName");
	}
	
	@RequestMapping("/queryStoreList.do")
	public String queryStoreList(StoreQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			String groupId=request.getParameter("g_id").toString();
			PageView<StoreBean> pageView = new PageView<StoreBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setG_id(groupId);
			QueryResult<StoreBean> qrs = shopGroupServiceImpl2.findAllStoreData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/work_order/shop/detail_page";
	}
	
	
//	@RequestMapping("/queryEmpList.do")
//	public String queryEmpList(EmpQueryParam queryParam, HttpServletRequest request) throws Exception{
//		try{
//			//根据条件查询合同集合
//			PageView<Employee> pageView = new PageView<Employee>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
//			queryParam.setFirstResult(pageView.getFirstResult());
//			queryParam.setMaxResult(pageView.getMaxresult());
//			QueryResult<Employee> qrs = shopGroupServiceImpls.findAllEmpData(queryParam);
//			pageView.setQueryResult(qrs, queryParam.getPage()); 
//			request.setAttribute("pageView", pageView);
//			request.setAttribute("queryParam", queryParam);
//		}catch(Exception e){
//			logger.error(e);
//			e.printStackTrace();
//			
//		}
//		return "/work_order/shop/detail_emp_page";
//	}
	
	
	/***
	 * 组内成员 
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryEmp")
	public void queryEmp(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String groupId=request.getParameter("gid").toString();
			Map<String,String>param=new HashMap<String,String>();
			param.put("team_id", groupId);
			param.put("owerId", SessionUtils.getEMP(request).getId().toString());
			ArrayList<?> list = shopGroupServiceImpl.get_team_emp(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	
	
	/***
	 * 工单类型
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryWorkType")
	public void queryWorkType(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String groupId=request.getParameter("gid").toString();
			Map<String,String>param=new HashMap<String,String>();
			param.put("group_id", groupId);
			ArrayList<?> list = shopGroupServiceImpl.getWorkPowerBywkType(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	
	
	/***
	 * 保存新增数据
	 * @param groupPar
	 * @param req
	 * @param res
	 */
	@RequestMapping("saveFromGroup")
	public void saveFromGroup(Group groupPar, HttpServletRequest req, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			
			Map<String, Object> pram = this.getParamMap(req);
			Map<String, Object> group = shopGroupServiceImpl.getGroupData(pram.get("id").toString());
			
			if(!group.get("group_type").toString().equals(pram.get("group_type").toString()) 
			                && "0".equals(pram.get("group_type").toString())){
			    shopGroupServiceImpl.delWkType(pram.get("id").toString());
			}
			
			shopGroupServiceImpl.updateGroupModel(pram);
			result.put("g_id", (String) pram.get("id"));
			result.put("out_result", "1");
			result.put("out_result_reason", "保存成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/***
	 * 保存新增数据
	 * @param groupPar
	 * @param req
	 * @param res
	 */
	@RequestMapping("addGroup")
	public void addGroup(HttpServletRequest req, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Employee emp=SessionUtils.getEMP(req);
			Map<String, Object> pram = this.getParamMap(req);
			
			int countCode = shopGroupServiceImpl.checkCountWorkCode(pram);
			if (countCode >= 1) {
				throw new RuntimeException("组别编码重复");
			}
			int count = shopGroupServiceImpl.checkCountWork(pram);
			if (count >= 1) {
				throw new RuntimeException("组别名称重复");
			}
			pram.put("id", "");
			pram.put("create_by", emp.getUsername());
			pram.put("update_by", emp.getUsername());
			shopGroupServiceImpl.addGroupModel(pram);
			//SessionUtils.getEMP(req).setTeam_id(emp.getTeam_id().concat(","+pram.get("id")));
			
			result.put("out_result", "1");
			result.put("out_result_reason", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	/**
	 * 
	 * 
	 * @param downloadTemplete
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("/downloadTemplete")
	public String downloadTemplete(String downloadTemplete,HttpServletRequest request,HttpServletResponse resp){
		try{
			String[] headNames = null;
			String fileName = "";
			if (StringUtils.isNotBlank(downloadTemplete)) {
				if (downloadTemplete.trim().equals("emp")) {
					headNames = new String[]{"行号","工号","登录名","昵称","手机号","邮箱","是否自动分配0-否1-是"};
					fileName = "店铺组内用户导入模板.xlsx";
				}else if(downloadTemplete.trim().equals("store")){
					headNames = new String[]{"店铺名称"};
					fileName = "店铺组绑定店铺导入模板.xlsx";
				}
				ExcelExportUtil.exportExcelData(new ArrayList<Map<String,Object>>(), headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	@ResponseBody
	@RequestMapping(value = "/import", produces = "application/json;charset=UTF-8")
	public String imports(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		Employee user = SessionUtils.getEMP(req);
		JSONObject obj = new JSONObject();
		String groupId=req.getParameter("gid").toString();
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
						List<StoreEmployee> oo_list = new ArrayList<StoreEmployee>();
						
						List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 60);
						if (list == null || list.size() == 0) {
							obj.put("code", "400");
							obj.put("msg", "文件内容有误!");
							return obj.toString();
						}
						list.remove(list.get(0));
						for (String[] row : list) {
							StoreEmployee oo = new StoreEmployee(row, uuid, user,groupId);
							oo_list.add(oo);
						}
						Map<String, Object> resultMap = shopGroupServiceImpl.checkImport(oo_list,groupId);
						
						/*System.out.println("开始调用多线程插入数据");
						int pageSize = 500;
						Map<Object, Object> th_param = new HashMap<Object, Object>();
						th_param.put("empData", oo_list);
						int t_size = oo_list.size() % pageSize != 0 ? (oo_list.size() / pageSize + 1):oo_list.size() / pageSize;
						List<Future<?>> data_result = ThreadUtil.task(th_param, new InsertData(), "insertEmpData", 1,t_size,2);
						for (Future<?> f : data_result) {
							 @SuppressWarnings("unchecked")
							Map<String,String>result=(Map<String,String>)net.sf.json.JSONObject.fromObject(f.get());
							 if(!"1".equals(result.get("out_result"))){
								 result_flag=0;
							 }
						}*/
						/*if(result_flag==1){
							Map<String,String>param=new HashMap<String,String>();
							param.put("out_result", "1");
							param.put("out_result_reason", "成功");
							param.put("bat_id", uuid);
							shopGroupServiceImpl.checkImport(param);
							obj.putAll(param);
						}else{
							obj.put("code", "500");
							obj.put("msg", "上传失败!");
							return obj.toString();
						}*/
						obj.put("code", "200");
						obj.put("msg", "上传成功!");
						obj.put("bat_id", uuid);
						obj.put("out_result", resultMap.get("out_result"));
						obj.put("message", resultMap.get("message").toString());
					} else {
						obj.put("code", "500");
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
	@ResponseBody
	@RequestMapping(value = "/import2", produces = "application/json;charset=UTF-8")
	public String import2(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		Employee user = SessionUtils.getEMP(req);
		JSONObject obj = new JSONObject();
		String groupId=req.getParameter("gid").toString();
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
						list.remove(list.get(0));
						StringBuffer message = new StringBuffer();
						int i=0;
						for (String[] row : list) {
							String storeName = row[0];
							if(!CommonUtils.checkExistOrNot(storeName)){
								continue;
							}
							String storeCode = shopGroupServiceImpl.getCodeByName(storeName);
							if(CommonUtils.checkExistOrNot(storeCode)){
								Map<String,String>param=new HashMap<String,String>();
								param.put("store_code", storeCode);
								param.put("group", groupId);
								param.put("create_by", String.valueOf(user.getId()));
								param.put("update_by", String.valueOf(user.getId()));
								Integer count=shopGroupServiceImpl.checkCountStore(param);
								if(count>0){
									message.append("店铺（"+storeName+"）已存在,\n");
								}else{
									shopGroupServiceImpl.addStore(param);
									i++;
								}
							}else{
								message.append("店铺（"+storeName+"）未匹配到店铺code,\n");
							}
						}
						obj.put("code", "200");
						obj.put("msg", "上传成功!");
						obj.put("message", message.append(i+"个店铺添加成功."));
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
	
	
	
	
	
	/**
	 * 导出Excel
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(String bat_id,HttpServletRequest request,HttpServletResponse resp){
		try{
			List<Map<String, Object>> qrs =shopGroupServiceImpl.query_export(bat_id);
			String[] headNames = {"行号","工号","登录名","昵称","手机号","邮箱","是否自动分配0-否1-是","原因"};
			String fileName = "导入失败结果.xls";
			ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	
	/***
	 * 新增店铺
	 * @param request
	 * @param res
	 */
	@RequestMapping("addStoreGroup")
	public void addStoreGroup(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String store = (String) this.getParamMap(request).get("store");
			String group =(String) this.getParamMap(request).get("g_id");
			String create_by = SessionUtils.getEMP(request).getId().toString();
			String update_by = SessionUtils.getEMP(request).getId().toString();
			Map<String,String>param=new HashMap<String,String>();
			param.put("store_code", store);
			param.put("group", group);
			param.put("create_by", create_by);
			param.put("update_by", update_by);
			Integer count=shopGroupServiceImpl.checkCountStore(param);
			if(count>0){
				throw new RuntimeException("店铺已存在,不可重复添加");
			}
			shopGroupServiceImpl.addStore(param);
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/***
	 * 新增工单类型
	 * @param request
	 * @param res
	 */
	@RequestMapping("addWorkGroup")
	public void addWorkGroup(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String work_type = (String) this.getParamMap(request).get("workType");
			String group_id =(String) this.getParamMap(request).get("g_id");
			String create_by = SessionUtils.getEMP(request).getId().toString();
			String update_by = SessionUtils.getEMP(request).getId().toString();
			Map<String,String>param=new HashMap<String,String>();
			param.put("work_type", work_type);
			param.put("group_id", group_id);
			param.put("create_by", create_by);
			param.put("update_by", update_by);
			Integer count=shopGroupServiceImpl.checkCountWorkType(param);
			if(count>0){
				throw new RuntimeException("工单类型已存在,不可重复添加");
			}
			shopGroupServiceImpl.addWorkType(param);
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	/***
	 * 删除组中工单类别
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteWorkType")
	public void deleteWorkType(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String,String>result=new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer success=0;
			Integer[] ids = CommonUtils.strToIntegerArray(req.getParameter("privIds"));
			success = shopGroupServiceImpl.delBatchWorkPower(ids);
			result.put("out_result", "1");
			result.put("out_result_reason", "共操作数据"+success+"条,成功:"+success+"条" );
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	/***
	 * 删除组中工单类别
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteStore")
	public void deleteStore(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String,String>result=new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer success=0;
			Integer[] ids = CommonUtils.strToIntegerArray(req.getParameter("privIds"));
			success = shopGroupServiceImpl.delBatchStorePower(ids);
			result.put("out_result", "1");
			result.put("out_result_reason", "共操作数据"+success+"条,成功:"+success+"条" );
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	
	
	/***
	 * 删除数据
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("opGroup")
	public void opGroup(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String,String>result=new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			int success=0;
			String error_mes="";
			String ids = req.getParameter("privIds");
			String status=req.getParameter("status");
			String [] spString = ids.split(",");
			for(int i=0;i<spString.length;i++){
			String pjString = spString[i].trim();
			Map<String,String> pram=new HashMap<String,String>();
			if(!"0".equals(status) &&! "1".equals(status) || (pjString==null || "".equals(pjString))){
			}else{
			 pram.put("id", pjString);
			 pram.put("status",status);
			 pram.put("out_result", "1");
			 pram.put("out_result_reason" ,"");
			 shopGroupServiceImpl.opEmp(pram);
			 if("0".equals(pram.get("out_result"))){
				 error_mes=error_mes+pram.get("out_result_reason")+",";
			 }else{
			    success=success+1;
			 }
			}
		}
			if(success<spString.length){
				error_mes=",失败原因:"+error_mes;
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "共操作数据"+spString.length+"条,成功:"+success+"条"+error_mes );
		} catch (Exception e) {
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	
	
	
	/***
	 * 是否自动分配
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("opAuto")
	public void opAuto(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String,String>result=new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			int success=0;
			String error_mes="";
			String ids = req.getParameter("privIds");
			String status=req.getParameter("status");
			String groupId=req.getParameter("gId");
			String [] spString = ids.split(",");
			for(int i=0;i<spString.length;i++){
				String pjString = spString[i].trim();
				Map<String,String> pram=new HashMap<String,String>();
				if(!"0".equals(status) &&! "1".equals(status) || (pjString==null || "".equals(pjString))){
				}else{
					 pram.put("id", pjString);
					 pram.put("status",status);
					 pram.put("groupId",groupId);
					 pram.put("out_result", "1");
					 pram.put("out_result_reason" ,"");
					 shopGroupServiceImpl.opAuto(pram);
				 if("0".equals(pram.get("out_result"))){
					 error_mes=error_mes+pram.get("out_result_reason")+",";
				 }else{
				    success=success+1;
				 }
				}
			}
			if(success<spString.length){
				error_mes=",失败原因:"+error_mes;
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "共操作数据"+spString.length+"条,成功:"+success+"条"+error_mes );
		} catch (Exception e) {
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	
	
	
	/***
	 * 删除绑定人员
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("delEmp")
	public void delEmp(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		Map<String,String>result=new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			int success=0;
			String error_mes="";
			String groupId=req.getParameter("gId");
			String ids = req.getParameter("privIds");
			String [] spString = ids.split(",");
			for(int i=0;i<spString.length;i++){
				String pjString = spString[i].trim();
				Map<String,String> pram=new HashMap<String,String>();
					 pram.put("groupId",groupId);
					 pram.put("id", pjString);
					 shopGroupServiceImpl.delEmp(pram);
				 if("0".equals(pram.get("out_result"))){
					 error_mes=error_mes+pram.get("out_result_reason")+",";
				 }else{
				    success=success+1;
				 }
			}
			if(success<spString.length){
				error_mes=",失败原因:"+error_mes;
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "共操作数据"+spString.length+"条,成功:"+success+"条"+error_mes );
		} catch (Exception e) {
			result.put("out_result", "0");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	
	
	/***
	 * 删除数据
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("deleteGroup")
	public void deleteGroup(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String ids = req.getParameter("privIds");
			String [] spString = ids.split(",");
			for(int i=0;i<spString.length;i++){
				String pjString = spString[i].trim();
				shopGroupServiceImpl.delGroup(pjString);
				//同时删除关联别物流商，店铺
				shopGroupServiceImpl.delWorkPower(pjString);
				shopGroupServiceImpl.delStorePower(pjString);
			}
			out.write("true");
		} catch (Exception e) {
			logger.error(e);
			out.write("false");
		}
	}

	/**
	 * 禁用or启用
	 * @param status
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/switchShopGroup")
	public String switchShopGroup( String status, String id) {
		return  shopGroupServiceImpl.switchShopGroup(status,id)+"";
	}
}
