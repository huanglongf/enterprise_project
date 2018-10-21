package com.bt.workOrder.controller;

import java.io.File;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.param.AccountParam;
import com.bt.workOrder.controller.param.ManhoursParam;
import com.bt.workOrder.controller.param.WoHourInterimParam;
import com.bt.workOrder.model.Manhours;
import com.bt.workOrder.model.woHourInterim;
import com.bt.workOrder.service.ManhoursService;

@Controller
@RequestMapping("/control/manhoursController")
public class ManhoursController extends BaseController{

	private static final Logger logger = Logger.getLogger(ManhoursController.class);
	
	@Resource(name = "manhoursServiceImpl")
	private ManhoursService<?> manhoursServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param manhours
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:00:23
	 */
	@RequestMapping("/update.do")
	public void update(Manhours manhours, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= manhoursServiceImpl.update(manhours, request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
		}
		try {
			out= response.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月9日下午4:00:16
	 */
	@RequestMapping("/add.do")
	public void add(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= manhoursServiceImpl.add(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
		}
		try {
			out= response.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO(新建页面)
	 * @param accountParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月6日下午3:53:29
	 */
	@RequestMapping("/toAddForm")
	public String toAddForm(AccountParam accountParam, HttpServletRequest request) {
		
		String url= "";
		if(CommonUtils.checkExistOrNot(request.getParameter("query"))) {
			url= "/work_order/wo_manhours/employee_list";
			
		} else {
			url= "/work_order/wo_manhours/manhours_add";
			
		}
		try {
			PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(accountParam.getPageSize() == 0? BaseConst.pageSize: accountParam.getPageSize(), accountParam.getPage());
			accountParam.setFirstResult(pageView.getFirstResult());
			accountParam.setMaxResult(pageView.getMaxresult());
			// pageView.setQueryResult(manhoursServiceImpl.getSubordinates(accountParam, request), accountParam.getPage()); 
			pageView.setQueryResult(manhoursServiceImpl.getWorkOrderAccounts(accountParam), accountParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", accountParam);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return url;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月6日下午1:34:03
	 */
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result = manhoursServiceImpl.delWorkTime(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
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
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年1月6日上午11:23:16
	 */
	@RequestMapping("/shiftStatus")
	public void updateStatus(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= manhoursServiceImpl.shiftStatus(request, result);
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "ERROR");
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
	 * 
	 * @Description: TODO
	 * @param manhoursParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月5日下午5:55:19
	 */
	@RequestMapping("/query")
	public String query(ManhoursParam manhoursParam, HttpServletRequest request) {
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(manhoursParam.getPageSize() == 0? BaseConst.pageSize: manhoursParam.getPageSize(), manhoursParam.getPage());
			manhoursParam.setFirstResult(pageView.getFirstResult());
			manhoursParam.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(manhoursServiceImpl.query(manhoursParam), manhoursParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", manhoursParam);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/work_order/wo_manhours/manhours_list";
		
	}
	/***
	 * 导入工时列表
	 * @param manhoursParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/workingHoursList")
	public String workingHoursList(WoHourInterimParam woHourInterimPar, HttpServletRequest request){
		PageView<woHourInterim> pageView = new PageView<woHourInterim>(
				woHourInterimPar.getPageSize() == 0 ? BaseConst.pageSize : woHourInterimPar.getPageSize(), woHourInterimPar.getPage());
		woHourInterimPar.setFirstResult(pageView.getFirstResult());
		woHourInterimPar.setMaxResult(pageView.getMaxresult());
		QueryResult<woHourInterim> qrRaw = manhoursServiceImpl.queryHoursList(woHourInterimPar);
		pageView.setQueryResult(qrRaw, woHourInterimPar.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", woHourInterimPar);
		return "/work_order/wo_manhours/import_manhours_list";
	}
	/***
	 * 导入工时信息
	 * @param file
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/fileUploadHours.do")
	public JSONObject fileUploadHours(@RequestParam("file") MultipartFile file,HttpServletRequest req, HttpServletResponse res){
		JSONObject obj=new JSONObject();
		// 判断文件是否为空  
        if (!file.isEmpty()){
            try {
                // 文件保存路径
            	String fname = file.getOriginalFilename();  
                String filePath = "E:/upload"+"/"+fname;  
                // 转存文件
                file.transferTo(new File(filePath));
            	Map<Integer, String> maps = readExcels(filePath);
    			List<woHourInterim> wList = new ArrayList<woHourInterim>();
    			for (int i = 0; i < maps.size(); i++){
    				woHourInterim w = map2bean(maps.get(i));
    				if(null!=w)wList.add(w);
    			}
    			for(int j=0;j<wList.size();j++){
    				woHourInterim ma = wList.get(j);
    				manhoursServiceImpl.insertManhours(ma.getImport_status(),ma.getError_info(),ma.getWork_number(),ma.getName(),ma.getData_time(),ma.getMan_hour(),ma.getTo_delete());
    			}
    			obj.put("code", "200");
            	obj.put("msg", "成功!");
            } catch(Exception e) {  
                e.printStackTrace();  
                obj.put("code", "400");
            	obj.put("msg", "系统错误!");
            }
        }
        return obj;
	}
	public woHourInterim map2bean(String str){
    	if(null!=str && !str.equals("")){
    		woHourInterim  whfrd = new woHourInterim();
        	String[] map = str.split("    ");
        	if(map.length>0){
        		whfrd.setError_info("工时重复");
        		whfrd.setImport_status("正确");
        		whfrd.setTo_delete("0");
        		if(null!=map[0] && !map[0].equals(""))
            		whfrd.setData_time(map[0]);
            	if(null!=map[1] && !map[1].equals(""))
            		whfrd.setWork_number(map[1]);
            	if(null!=map[2] && !map[2].equals(""))
            		whfrd.setName(map[2]);
            	if(null!=map[3] && !map[3].equals(""))
            	    whfrd.setMan_hour(map[3]);
            	return whfrd;
        	}
    	}
    	return null;
    }
	/**
	 * 清空数据
	 * @param req
	 * @param res
	 */
	@RequestMapping("/updateWoHourInterim")
	public void updateWoHourInterim(HttpServletRequest req, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			manhoursServiceImpl.updateWoHourInterimToDelete();
			out.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			out.write("false");
		}
	} 
	/***
	 * 导入数据
	 * @param req
	 * @param res
	 */
	@RequestMapping("/importWoHourInterim")
	public void importWoHourInterim(HttpServletRequest req, HttpServletResponse res){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			List<woHourInterim> listHourInterim = manhoursServiceImpl.queryHourInterim();
			for(int i=0;i<listHourInterim.size();i++){
				woHourInterim mohour = listHourInterim.get(i);
				String workNumber = mohour.getWork_number().trim();
				String dataTimeNew = mohour.getData_time().trim().substring(0, 11);
				String dataTime = dataTimeNew.substring(0, 11);
				String manHour = mohour.getMan_hour().trim();
				String id = String.valueOf(mohour.getId()).trim();
				String createTime = fmt.format(new Date());
				String updateTime = fmt.format(new Date());
				String userName = String.valueOf(SessionUtils.getEMP(req).getId());
				String update_by = userName;
				String status = "1";
				String number=null;
				if(workNumber.contains(".")){
					 number = workNumber.substring(0, workNumber.indexOf("."));
				}else{
					 number = workNumber;
				}
				Employee model = manhoursServiceImpl.findsEmployeeId(number);
				String ids = null;
				if(model!=null){
					ids = String.valueOf(model.getId());
				}
				String uuid = UUID.randomUUID().toString();
				manhoursServiceImpl.saveWoManhours(uuid,createTime,updateTime,ids,dataTime,manHour,status,userName,update_by);//导入正式库
				manhoursServiceImpl.deleteWoHourInterim(id);//删除临时表
			}
			out.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			out.write("false");
		}
	}
	/***
	 * 添加行
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/addWoHourInterim")
	public void addWoHourInterim(HttpServletRequest req, HttpServletResponse res){
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		try {
			//String  import_status = null;
			String  error_info = null;
			out = res.getWriter();
			if(this.getParamMap(req).get("hourType").equals("2")){
				String id = (String) this.getParamMap(req).get("id");
				String import_status = (String) this.getParamMap(req).get("import_status");
				//String error_info = (String) this.getParamMap(req).get("error_info");
				String work_number = (String) this.getParamMap(req).get("work_number");
				String name = (String) this.getParamMap(req).get("name");
				String data_time = (String) this.getParamMap(req).get("data_time");
				String man_hour = (String) this.getParamMap(req).get("man_hour");
				/*import_status = "正确";
				if("正确".equals(import_status)){
					error_info = "";
				}*/
				manhoursServiceImpl.updateWoHourInterim(id,import_status,error_info,work_number,name,data_time,man_hour);
			}else{
				manhoursServiceImpl.addWoHourInterim(this.getParamMap(req));
			}
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
}
