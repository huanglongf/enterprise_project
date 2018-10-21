package com.bt.workOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.param.GroupQueryParam;
import com.bt.workOrder.model.Group;
import com.bt.workOrder.model.GroupStorePower;
import com.bt.workOrder.model.GroupWorkPower;
import com.bt.workOrder.service.GroupService;

@Controller
@RequestMapping("/control/groupController")
public class GroupController  extends BaseController{

	private static final Logger logger = Logger.getLogger(GroupController.class);

	@Resource(name = "groupServiceImpl")
	private GroupService<Group> groupServiceImpl;
	
	@RequestMapping("/getStorePower")
	public void getStorePower(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= groupServiceImpl.getStorePower(request, result); 
			
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
	
	@RequestMapping("/saveStorePower")
	public void saveStorePower(GroupStorePower groupStorePower, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result= groupServiceImpl.saveStorePower(groupStorePower, request, result);
			
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
	 * 
	 * @Description: TODO
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:40:10
	 */
	@RequestMapping("/qeuryWorkPower")
	public String qeuryWorkPower(HttpServletRequest request){
		try {
			request= groupServiceImpl.queryWorkPower(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/work_order/wo_group/work_power";
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupWorkPower
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:38:51
	 */
	@RequestMapping("/delWorkPower")
	public void delWorkPower(GroupWorkPower groupWorkPower, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			groupWorkPower.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
			result = groupServiceImpl.delWorkPower(groupWorkPower, result);
			
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
	 * 
	 * @Description: TODO
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:37:18
	 */
	@RequestMapping("/qeuryStorePower")
	public String qeuryStorePower(HttpServletRequest request){
		try {
			request= groupServiceImpl.queryStorePower(request);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/work_order/wo_group/store_power";
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param groupStorePower
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:20:00
	 */
	@RequestMapping("/delStorePower")
	public void delStorePower(GroupStorePower groupStorePower, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			groupStorePower.setUpdate_by(SessionUtils.getEMP(request).getId().toString());
			result = groupServiceImpl.delStorePower(groupStorePower, result);
			
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
	 * 
	 * @Description: TODO
	 * @param group
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午7:41:01
	 */
	@RequestMapping("/saveGroup")
	public void saveGroup(Group group, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = groupServiceImpl.saveGroup(group, request, result);
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
	 * 
	 * @Description: TODO(删除组别)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月9日下午2:22:34
	 */
	@RequestMapping("/delGroups")
	public void delGroups(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = groupServiceImpl.delGroups(request, result);
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
	 * 
	 * @Description: TODO(跳转编辑页面)
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午7:57:59
	 */
	@RequestMapping("/toForm")
	public String toForm(HttpServletRequest request){
		try {
			request = groupServiceImpl.toForm(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/work_order/wo_group/group_form";
	}
	
	/**
	 * 
	 * @Description: TODO(更新组别状态)
	 * @param group
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午2:32:37
	 */
	@RequestMapping("/updateStatus")
	public void updateStatus(Group group, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = groupServiceImpl.updateStatus(group, request, result);
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
	 * 
	 * @Description: TODO
	 * @param gQP
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月10日上午10:28:47
	 */
	@RequestMapping("/list")
	public String list(GroupQueryParam gQP, HttpServletRequest request){
		String url= null;
		if(gQP.getQueryType().equals("init")) {
			url= "/work_order/wo_group/group_management";
			
		} else {
			url= "/work_order/wo_group/group_list";
			
		}
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(gQP.getPageSize()==0?BaseConst.pageSize:gQP.getPageSize(), gQP.getPage());
		gQP.setFirstResult(pageView.getFirstResult());
		gQP.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = groupServiceImpl.query(gQP);
		pageView.setQueryResult(qr, gQP.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("gQP", gQP);
		try {
			request.setAttribute("superior", groupServiceImpl.findAllGroups());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return url;
		
	}
}
