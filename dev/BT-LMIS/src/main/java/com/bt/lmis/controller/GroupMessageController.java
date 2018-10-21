package com.bt.lmis.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreBean;
import com.bt.lmis.model.WorkOrderAndLevel;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransPoolService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.model.WkType;
import com.bt.workOrder.service.GroupService;

@Controller
@RequestMapping("/control/groupMessageController")
public class GroupMessageController extends BaseController {

	private static final Logger logger = Logger.getLogger(GroupMessageController.class);

	@Resource(name = "transPoolServiceImpl")
	private TransPoolService<Group> transPoolServiceImpl;
	
	@Resource(name = "groupServiceImpl")
	private GroupService<StoreBean> groupServiceImpl;
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
		if ("".equals(request.getParameter("status")) || null == request.getParameter("status")) {

		} else {
			groupPar.setStatus(request.getParameter("status"));
		}
		if ("".equals(request.getParameter("superior")) || null == request.getParameter("superior")) {

		} else {
			groupPar.setSuperior(request.getParameter("superior"));
		}
		groupPar.setIf_allot(request.getParameter("if_allot"));
		QueryResult<Group> qrRaw = transPoolServiceImpl.querysGroup(groupPar);
		ArrayList<?> seniorQuery = transPoolServiceImpl.querySeniorQueryGroupSup();
		pageView.setQueryResult(qrRaw, groupPar.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("groupPar", groupPar);
		request.setAttribute("groupCode", request.getParameter("group_code"));
		request.setAttribute("groupName", request.getParameter("group_name"));
		request.setAttribute("status", request.getParameter("status"));
		request.setAttribute("superior", request.getParameter("superior"));
		request.setAttribute("if_allot", request.getParameter("if_allot"));
		request.setAttribute("seniorQuery", seniorQuery);
		return "/lmis/listGroup";
	}

	/****
	 * 组别关联工单查询(新增与编辑)
	 * @param groupPar
	 * @param request
	 * @return
	 */
	@RequestMapping("newlyGroupWord")
	public String newlyGroupWord(GroupParam groupPar, HttpServletRequest request) {
		ArrayList<?> list = transPoolServiceImpl.queryWkGroupSup();
		ArrayList<?> listWL = transPoolServiceImpl.findWorkOrderType();//工单类型
		ArrayList<?> listLog = transPoolServiceImpl.queryLogisticsCode();//物流商
		ArrayList<?> listDP = transPoolServiceImpl.findStore();//店铺
		
		List<WorkOrderAndLevel> listjb = new ArrayList<>();
		for(int j=0;j<listWL.size();j++){
			Map<String,Integer> map = (Map) listWL.get(j);
			String name =String.valueOf(map.get("name"));
			String code =String.valueOf(map.get("CODE"));
			
			List<?> levelList = transPoolServiceImpl.workOrderAndLevel(code);
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
		request.setAttribute("list", list);
		request.setAttribute("listWL", listjb);
		request.setAttribute("listDP", listDP);
		request.setAttribute("listLog", listLog);
		return "/lmis/groupWordOrder";
	}

	/***
	 * 修改状态
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateStatus")
	public void updateStatus(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String ids = req.getParameter("id").trim();
			String id = ids.split("_")[0].trim();
//			String status = ids.split("_")[1].trim();
			String status = req.getParameter("status");
			if (status != null) {
//				if ("true".equals(status)) {
//					status = "0";
//				} else {
//					status = "1";
//				}
				transPoolServiceImpl.updateStatus(id, status);
				out.write("true");
			} else {
				out.write("false");
			}
		} catch (Exception e) {
			logger.error(e);
			out.write("false");
		}
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
			String id = null;
			out = res.getWriter();
			String ids = req.getParameter("privIds");
			if(ids.contains("true")){
				out.write("no");
			}else{
				if(ids.contains(",")){
					String [] spString = ids.split(",");
					for(int i=0;i<spString.length;i++){
						String pjString = spString[i].trim();
						id = pjString.split("-")[0].trim();
						transPoolServiceImpl.delGroup(id);
						//同时删除关联别物流商，店铺
						transPoolServiceImpl.delWorkPower(id);
						transPoolServiceImpl.delStorePower(id);
					}
				}else{
					id = ids.split("_")[0].trim();
					String start = ids.split("_")[1].trim();
					if(start.equals("false")){
						transPoolServiceImpl.delGroup(id);
						transPoolServiceImpl.delWorkPower(id);
						transPoolServiceImpl.delStorePower(id);
					}else{
						out.write("false");
					}
				}
				out.write("true");
			}
		} catch (Exception e) {
			logger.error(e);
			out.write("false");
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
			
			if (pram.get("group_code") == null || pram.get("group_code").equals("")) {
				throw new RuntimeException("组别编码为空");
			}
			
			if (pram.get("group_name") == null || pram.get("group_name").equals("")) {
				throw new RuntimeException("组别名称为空");
			}
			
			
			if (pram.get("groupType").equals("1")) {
				pram.put("id", "");
				//组别编码唯一验证
				int countCode = transPoolServiceImpl.checkCountWorkCode(pram);
				if (countCode >= 1) {
					throw new RuntimeException("组别编码重复");
				}
				int count = transPoolServiceImpl.checkCountWork(pram);
				if (count >= 1) {
					throw new RuntimeException("组别名称重复");
				}
				if (pram.get("superior") == null || "".equals(pram.get("superior"))) {
					transPoolServiceImpl.addsGroupModelisNull(pram);
				} else {
					transPoolServiceImpl.addsGroupModel(pram);
				}
			} else {
				transPoolServiceImpl.updateGroupModel(pram);
			}
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
	 * 工单类型列表
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryWorkOrderType")
	public void queryWorkOrderType(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = null;
			if ("".equals(getParamMap(request).get("g_id")) || getParamMap(request).get("g_id") == null) {
				list = new ArrayList<>();
			} else {
				list = transPoolServiceImpl.queryWorkOrderType(getParamMap(request));
			}
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}

	/***
	 * 店铺
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryStore")
	public void queryStore(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = null;
			if ("".equals(getParamMap(request).get("g_id")) || getParamMap(request).get("g_id") == null) {
				list = new ArrayList<>();
			} else {
				list = transPoolServiceImpl.queryStore(getParamMap(request));
			}
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	
	
	
	@RequestMapping("/queryStoreList.do")
	public String queryStoreList(StoreQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<StoreBean> pageView = new PageView<StoreBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<StoreBean> qrs = groupServiceImpl.findAllStoreData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/lmis/detail_page";
	}

	/***
	 * 物流商
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryLogistics")
	public void queryLogistics(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = null;
			if ("".equals(getParamMap(request).get("g_id")) || getParamMap(request).get("g_id") == null) {
				list = new ArrayList<>();
			} else {
				list = transPoolServiceImpl.queryTransportVendorGroup(getParamMap(request));
			}
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
	@RequestMapping("findWorkType")
	public void findWorkType(HttpServletRequest request, HttpServletResponse res) {
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = transPoolServiceImpl.findWorkOrderType();
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****
	 * 新增维护工单 
	 * @param request
	 * @param res
	 */
	@RequestMapping("addWkGroup")
	public void addWkGroup(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			if (this.getParamMap(request).get("validateBox").equals("1")) {
				List<WkType> listWktype = transPoolServiceImpl.queryWorkOrderName();
				String g_id = (String) this.getParamMap(request).get("g_id");
				for(int k=0;k<listWktype.size();k++){
					String name = listWktype.get(k).getName().trim();
					int countWk = transPoolServiceImpl.checkCountWkName(name);
					if (countWk >= 1) {
						continue;
					}
					String id = String.valueOf(transPoolServiceImpl.queryWkTypeById(name));
					this.getwkGroup(id,g_id,name);
				}
			} else {
				if (this.getParamMap(request).get("workType").equals("1")) {
					transPoolServiceImpl.addwkGroup(this.getParamMap(request));
				} else {
					transPoolServiceImpl.updateWkGroup(this.getParamMap(request));
				}
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	public void getwkGroup(String id,String g_id,String name){
		List<?>  listLevel = transPoolServiceImpl.findwkTypeAndwkLevel(id);
		for(int j=0;j<listLevel.size();j++){
			Map<String,Integer> map = (Map) listLevel.get(j);
			int level =map.get("sort");
			transPoolServiceImpl.addsGroupModelAndMost(name,g_id,level);
		}
	}

	/***
	 * 删除可处理工单维护数据
	 * @param request
	 * @param res
	 */
	@RequestMapping("delwork")
	public void delwork(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			transPoolServiceImpl.delWorkOrder(getParamMap(request));
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	/***
	 * 新增店铺
	 * @param request
	 * @param res
	 */
	@RequestMapping("addStoreGroup")
	public void addStoreGroup(HttpServletRequest request, HttpServletResponse res) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String depot = (String) this.getParamMap(request).get("depot");
			String store = (String) this.getParamMap(request).get("store");
			int group = (int) Integer.parseInt((String) this.getParamMap(request).get("g_id"));
			
			String outsourcedwarehouse = null;
			String selfwarehouse = null;
			if(depot.contains(",")){
				if(depot.contains("0")&&depot.contains("1")){
					outsourcedwarehouse = "1";
					selfwarehouse = "1";
				}else{
					selfwarehouse = "1";
					outsourcedwarehouse = "0";
				}
			}else{
				if("1".equals(depot.trim())){
					outsourcedwarehouse = "1";
					selfwarehouse = "0";
				}
			}
			String create_by = SessionUtils.getEMP(request).getId().toString();
			String update_by = SessionUtils.getEMP(request).getId().toString();
			Date create_time_new = new Date();
			String create_time = fmt.format(create_time_new);
			String update_time = create_time;
			if (this.getParamMap(request).get("storeType").equals("1")) {
				String ids = UUID.randomUUID().toString();
				int countSt = transPoolServiceImpl.checkWoGroupStorepower(outsourcedwarehouse,selfwarehouse,store,group);
				if(countSt>= 1){
					throw new RuntimeException("店铺重复");
				}else{
					transPoolServiceImpl.addStoreGroup(ids,create_by,create_time,update_by,update_time,group,selfwarehouse,outsourcedwarehouse,store);
				}
			} else {
				String id = (String) this.getParamMap(request).get("id");
				transPoolServiceImpl.updateStoreGroupPage(id,create_by,create_time,update_by,update_time,group,selfwarehouse,outsourcedwarehouse,store);
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", "店铺重复");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	/***
	 * 删除店铺
	 * @param request
	 * @param res
	 */
	@RequestMapping("deleteStore")
	public void deleteStore(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			transPoolServiceImpl.deleteStoreAndGroup(getParamMap(request));
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
    public Date getCreateTime(String carrier,int group){
    	Date newCode = null;
    	List<GroupWorkPower> listT = transPoolServiceImpl.queryWorkPower(carrier,group);
    	for(int j=0;j<listT.size();j++){
    		GroupWorkPower dx =  listT.get(j);
    		if(j==0){
        		newCode = dx.getCreate_time();
        		break;
    		}
    	}
    	return newCode;
    }
	/***
	 * 新增物流商
	 * @param request
	 * @param res
	 */
	@RequestMapping("addTransportVendor")
	public void addTransportVendor(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			int group = Integer.parseInt(this.getParamMap(request).get("g_id").toString());
			String carrier = this.getParamMap(request).get("t_code").toString(); 
			String create_by = SessionUtils.getEMP(request).getId().toString();
			String update_by = create_by;
			if (this.getParamMap(request).get("logType").equals("1")) {
				//判断物流商是否重复
				String  gId= this.getParamMap(request).get("g_id").toString();
				String  tCode = this.getParamMap(request).get("t_code").toString();
				int countCheck = transPoolServiceImpl.dindwoGroupWorkPower(gId,tCode);
				if (countCheck >= 1) {
					throw new RuntimeException("物流商重复");
				}else{
					String leven = this.getParamMap(request).get("leven").toString();
	                if(leven.contains(",")){
						String lev = leven.substring(0, leven.length()-1);
						String [] newLevel = lev.split(",");
						for(int i=0;i<newLevel.length;i++){
							UUID uuid = UUID.randomUUID();  
					        String id = uuid.toString(); 
							String str = newLevel[i];
							String wo_type = str.split("_")[0].trim();
							String wo_level = this.formatLevel(str.split("_")[1].trim());
							transPoolServiceImpl.addTransportVendor(id,create_by,update_by,group,carrier,wo_type,wo_level);
						}
					}
				}
			} else {	
				String leven = this.getParamMap(request).get("leven").toString();
				Date create_time = this.getCreateTime(carrier,group);
				transPoolServiceImpl.deleteGroupWorkPower(group,carrier);
                if(leven.contains(",")){
					String lev = leven.substring(0, leven.length()-1);
					String [] newLevel = lev.split(",");
					for(int i=0;i<newLevel.length;i++){
						UUID uuid = UUID.randomUUID();  
				        String id = uuid.toString(); 
						String str = newLevel[i];
						String wo_type = str.split("_")[0].trim();
						String wo_level = this.formatLevel(str.split("_")[1].trim());
						transPoolServiceImpl.addTransportVendorTime(id,create_by,create_time,update_by,group,carrier,wo_type,wo_level);
					}
				}
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			logger.error(e);
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	public  String formatLevel(String str){
		String levl = null;
		if("1".equals(str)){
			levl = "level01";
		}else if("2".equals(str)){
			levl = "level02";
		}else if("3".equals(str)){
			levl = "level03";
		}else if("4".equals(str)){
			levl = "level04";
		}else if("5".equals(str)){
			levl = "level05";
		}else if("6".equals(str)){
			levl = "level06";
		}else if("7".equals(str)){
			levl = "level07";
		}else if("8".equals(str)){
			levl = "level08";
		}else{
			levl = "level09";
		}
		return levl;
	}
	/***
	 * 删除物流商
	 * @param request
	 * @param res
	 */
	@RequestMapping("deleteVendorGroup")
	public void deleteVendorGroup(HttpServletRequest request, HttpServletResponse res) {
		Map<String, String> result = new HashMap<String, String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			transPoolServiceImpl.deleteVendorGroup(getParamMap(request));
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	/***
	 * 查询工单级别
	 */
	@RequestMapping("findLevel")
	public void findLevel(HttpServletRequest request, HttpServletResponse res) {
		WkType model = transPoolServiceImpl.findLevelById(getParamMap(request));
		String id = model.getId();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = transPoolServiceImpl.queryfindLevelAndJb(id);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			ArrayList<?> list = transPoolServiceImpl.queryWkGroupSupAndUpdate(id);
			ArrayList<?> listWL = transPoolServiceImpl.findWorkOrderType();//工单类型
			ArrayList<?> listDP = transPoolServiceImpl.findStore();//店铺
			ArrayList<?> listLog = transPoolServiceImpl.queryLogisticsCode();//物流商
			
			List<WorkOrderAndLevel> listjb = new ArrayList<>();
			for(int j=0;j<listWL.size();j++){
				Map<String,Integer> map = (Map) listWL.get(j);
				String name =String.valueOf(map.get("name"));
				String code =String.valueOf(map.get("CODE"));
				
				List<?> levelList = transPoolServiceImpl.workOrderAndLevel(code);
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
			Group modelSup = transPoolServiceImpl.querysGroupById(getParamMap(request));
			request.setAttribute("listWL", listjb);
			request.setAttribute("list", list);
			request.setAttribute("listDP", listDP);
			request.setAttribute("listLog", listLog);
			request.setAttribute("obj", getParamMap(request));
			request.setAttribute("typeUpdate", "2");
			request.setAttribute("modelSup", modelSup);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/groupWordOrder";
	}
	/***
	 * 修改工单显示问题
	 * @param request
	 * @param res
	 */
	@RequestMapping("displayWorkOrder")
	public void displayWorkOrder(HttpServletRequest request, HttpServletResponse res) {
		PrintWriter out = null;
		try {
			out = res.getWriter();
			ArrayList<?> list = transPoolServiceImpl.displayWorkOrder(getParamMap(request));
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/***
	 * 店铺模糊查询
	 * @param request
	 * @param res
	 */
	@RequestMapping("findtbStore")
	public void findtbStore(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= transPoolServiceImpl.findStoreAll(request,result);//店铺
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();
	}
	/***
	 * 物流商模糊查询
	 * @param request
	 * @param res
	 */
	@RequestMapping("queryTransportVendorSelect")
	public void queryTransportVendorSelect(HttpServletRequest request, HttpServletResponse res) {
		String carrier =  this.getParamMap(request).get("carrier").toString();
		//int  group = (int)this.getParamMap(request).get("group");
		int  group = Integer.parseInt(this.getParamMap(request).get("group").toString());
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= transPoolServiceImpl.queryLogisticsCodeSelect(request,result,carrier,group);//物流商
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();
	}
	

	/** 
	* @Title: getWKEmployee 
	* @Description: TODO(获取工单的用户-Yuriy) 
	* @param @param res
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/getWKEmployee.do")
	public String getWKEmployee(ModelMap map,HttpServletResponse res, HttpServletRequest request) {
		try {
			List<Map<String, Object>> new_list = new ArrayList<>();
			List<Map<String, Object>> list = groupServiceImpl.getWKEMP(Integer.valueOf(request.getParameter("groupid")));
			for (int j = 0; j < list.size(); j++) {
				Map<String, Object> new_map = list.get(j);
				new_map.put("id", list.get(j).get("aid"));
				new_map.put("name", list.get(j).get("name"));
				if (null!=list.get(j).get("bid") && !list.get(j).get("bid").equals("")) {
					new_map.put("checked", "true");
				}
				new_list.add(new_map);
			}
			net.sf.json.JSONArray menuJson =net.sf.json.JSONArray.fromObject(new_list); 
			map.addAttribute("menu",menuJson);
			map.addAttribute("groupid",request.getParameter("groupid"));
			List<Map<String, Object>> gropList = groupServiceImpl.selectGroup(request.getParameter("groupid"));
			map.addAttribute("group",gropList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/groupSetEmployee";
	}
	

	/** 
	* @Title: setEMP 
	* @Description: TODO(绑定用户) 
	* @param @param request
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("setEMP")
	public void setEMP(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		String msg = "操作失败!";
		try {
			out = res.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			msg = "操作失败!";
		}
		try {
			Employee iflogin = SessionUtils.getEMP(request);
			String ids = request.getParameter("ids");
			String groupid = request.getParameter("groupid");
			String[] idss = ids.split(",");
			if(null==ids || ids.equals("")){
				groupServiceImpl.deleteGE(Integer.valueOf(groupid));
				msg = "操作成功,已清除所有用户!";
			}else{
				//List<Map<String, Object>> checkList = groupServiceImpl.checkEMP(ids,groupid);
				//if(checkList.size()>0){
					groupServiceImpl.deleteGE(Integer.valueOf(groupid));
					for (int i = 0; i < idss.length; i++) {
						groupServiceImpl.insertGE(idss[i], Integer.valueOf(groupid), String.valueOf(iflogin.getId()), "", String.valueOf(iflogin.getId()), "");
						msg = "操作成功!";
					}
//				}else{
//					msg = "操作失败!,[";
//					for (int i = 0; i < checkList.size(); i++) {
//						msg=msg+checkList.get(i).get("name");
//					}
//					msg=msg+"]";
//				}
			}
			out.write(msg.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			msg = "操作失败!";
			out.write(msg.toString());
			out.flush();
			logger.error(e);
		}
		out.close();
	}
	/*
	 * 批量上传模版下载
	 * 
	 */
	@ResponseBody
	@RequestMapping("downloadModelOfStore")
	public JSONObject downloadModelOfStore(ModelMap map,HttpServletResponse res, HttpServletRequest request) {
		JSONObject obj  = new JSONObject();
		String path="store_model.xls";
		File tempFile=null;
		String filePath = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+path;
		File f=new File(filePath);
		try{
		if(f.exists()){
			obj.put("path", "store_model.xlsx");
		}else{
			tempFile=new File(CommonUtils.getAllMessage("config", "COMMON_TEMPLET_"+OSinfo.getOSname())+path);
			FileUtil.copyFile(CommonUtils.getAllMessage("config", "COMMON_TEMPLET_"+OSinfo.getOSname())+path, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+path,true);	
		}
		obj.put("code", 1);
		obj.put("path", "store_model.xls");
		}catch(Exception e){
			obj.put("code", 0);
		}
		
		return obj;
	}
	
	@ResponseBody
	@RequestMapping(value="/upload.do")
	public JSONObject upload(@RequestParam("file") MultipartFile file,ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException, EncryptedDocumentException, InvalidFormatException{
		JSONObject obj =new JSONObject();
		List<StoreBean> storeList=new ArrayList<StoreBean>();
		String groupId=req.getParameter("group").toString();
		StoreBean storeBean=null;
		boolean flag=true;
		String logs="";
		if (!file.isEmpty()) {
			//复制文件
			//File webFile=new File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP"));
			  file.transferTo(new File(CommonUtils.getAllMessage("config", "WO_UPLOAD_"+OSinfo.getOSname())+file.getOriginalFilename()));
			  FileInputStream is = new FileInputStream(CommonUtils.getAllMessage("config", "WO_UPLOAD_"+OSinfo.getOSname())+file.getOriginalFilename());	//文件流
   			   Workbook workbook = WorkbookFactory.create(is);
   			   String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
   			   SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				Map<String ,Integer> title=new HashMap<String,Integer>();
				for (int s = 0; s < 1; s++) {
					Sheet sheet = workbook.getSheetAt(s);  
		            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数  
		            //遍历每一行  
		            int cellCount=0;
		            boolean ForFlag=false;
		            for (int r = 2; r < rowCount; r++) { 
		            	if(ForFlag)break;
		            	storeBean =new StoreBean();
		            	storeBean.setGroup(groupId);
		            	storeBean.setCreate_by(
		            	SessionUtils.getEMP(req).getEmployee_number());
		            	storeBean.setUpdate_by(
				            	SessionUtils.getEMP(req).getEmployee_number());
		                Row row = sheet.getRow(r); 
		                //遍历每一列
		                String cellValues0 = "";
		                String cellValues = "";
		                cellCount = row.getPhysicalNumberOfCells();  
		                for (int c = 0; c < cellCount; c++) {
		                	
		                	if(row==null)continue;
		                    Cell cell = row.getCell(c);  
		                    if(cell==null){
		                    	cellValues += "---" + "    ";
		                    	continue;
		                    }
		                    Integer cellType = cell.getCellType(); 
		                    String cellValue = null;  
		                    switch(cellType) {  
		                        case Cell.CELL_TYPE_STRING: //文本  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
		                            if(DateUtil.isCellDateFormatted(cell)) {  
		                                cellValue = fmt.format(cell.getDateCellValue()); //日期型  
		                            }  
		                            else {  
		                                cellValue = String.valueOf(cell.getNumericCellValue()); //数字  
		                            }  
		                            break;  
		                        case Cell.CELL_TYPE_BOOLEAN: //布尔型  
		                            cellValue = String.valueOf(cell.getBooleanCellValue());  
		                            break;  
		                        case Cell.CELL_TYPE_BLANK: //空白  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_ERROR: //错误  
		                            cellValue = "错误";  
		                            break;  
		                        case Cell.CELL_TYPE_FORMULA: //公式  
		                            cellValue = "错误";  
		                            break;  
		                        default:  
		                            cellValue = "错误";  
		                    } 
		                   if(c==0) {
		                	   if("".equals(cellValue)){ 
		                		   ForFlag=true;
		                		   break;
		                	   }
		                	   if(!groupServiceImpl.existStore(cellValue,storeBean)){
		                		   logs+="第"+(r+1)+"行 第"+(c+1)+"列,店铺名字不匹配;";
		                		   flag=false; 
		                		   continue;
		                	   }else if(groupServiceImpl.existRecord(cellValue,groupId)){  
		                		   logs+="第"+(r+1)+"行 第"+(c+1)+"列,此店铺已经存在;";
		                		   flag=false; 
		                		   continue;  
		                	   }
		                	   storeBean.setStore_name(cellValue);
		                	   }
		                   if(c==1) {
		                	   if(!"".equals(cellValue)&&!"1".equals(cellValue)&&"0".equals(cellValue)){
		                		   logs+="第"+(r+1)+"行 第"+(c+1)+"列,外包仓上有异常值;";
		                		   flag=false;   
		                		   continue;
		                	   }
		                	   storeBean.setOutsourcedwarehouse("是".equals(cellValue)?"1":"0");  	 
		                	   }
		                   if(c==2) {
		                	   if(!"".equals(cellValue)&&!"1".equals(cellValue)&&"0".equals(cellValue)&&!"1".equals(cellValue)&&"0".equals(cellValue)){
		                		   logs+="第"+(r+1)+"行 第"+(c+1)+"列,自营仓上有异常值;";
		                		   flag=false; 
		                		   continue;
		                	   }
		                	   storeBean.setSelfwarehouse("是".equals(cellValue)?"1":"0");
		                	   }
		                   
		                } 
		                if(!ForFlag)
		                storeList.add(storeBean);
		            }
				}
				//如果有错误不必导入数据
				if(flag&&storeList.size()!=0){
					//倒入数据
					groupServiceImpl.addBatchStore(storeList);
					obj.put("code", 1);
				}else{
					createCell(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+str+"storebatchinputlog.xls",logs.split(";"));
					obj.put("code", 3);
					obj.put("path", str+"storebatchinputlog.xls");
				}
				
		}else{
			obj.put("code", 0);
		}
		
		return obj;
	}
	
	
	
	
	
	private  String createCell(String url,String[] a) {
		FileOutputStream fileOut= null;
		//创建行 & 创建列 start
		Workbook wb=new HSSFWorkbook(); 											// 定义一个新的工作簿
		Sheet sheet=wb.createSheet("第一个Sheet页");  	
		for (int i = 0; i < a.length; i++) {
			// 创建第一个Sheet页
			Row row = sheet.createRow(i); 												// 创建一个行
			row.setHeightInPoints(20);
			Cell cell=row.createCell((short)0);  										// 创建单元格(short)0 (short)1 (short)2 (short)3
			//创建行 & 创建列 end
			CellStyle cellStyle=wb.createCellStyle(); 									// 创建单元格样式
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  						// 设置单元格水平方向对其方式 ALIGN_CENTER ALIGN_FILL ALIGN_LEFT ALIGN_RIGHT
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM); 				// 设置单元格垂直方向对其方式 HSSFCellStyle.VERTICAL_BOTTOM VERTICAL_CENTER VERTICAL_TOP VERTICAL_TOP
			cell.setCellStyle(cellStyle); 												// 设置单元格样式
			cell.setCellValue(new HSSFRichTextString(a[i]));  							// 设置值
			try {
				fileOut = new FileOutputStream(url);
				wb.write(fileOut);
				fileOut.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return  "ok";
	}
}
