package com.bt.lmis.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.AreaQueryParam;
import com.bt.lmis.model.Area;
import com.bt.lmis.model.Employee;
import com.bt.lmis.service.AreaService;
import com.bt.radar.service.AreaRadarService;
import com.bt.utils.SessionUtils;

import net.sf.json.JSONArray;
/**
 * 省市区管理表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/areaController")
public class AreaController extends BaseController {

	private static final Logger logger = Logger.getLogger(AreaController.class);
	
	/**
	 * 
	 */
	@Resource(name = "areaRadarServiceImpl")
	private AreaRadarService<Area> areaRadarServiceImpl;
	
	/**
	 * 省市区管理表服务类
	 */
	@Resource(name = "areaServiceImpl")
	private AreaService<Area> areaService;
	
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request) throws Exception{
		map.addAttribute("menu", JSONArray.fromObject(areaService.findChildren(new HashMap<String,String>())));
		return "/lmis/area";
		
	}
	
	@RequestMapping("/toForm1")
	public String toForm1(ModelMap map, HttpServletRequest request) throws Exception{
		map.addAttribute("menu", JSONArray.fromObject(areaService.findChildren(new HashMap<String,String>())));
		return "/lmis/area1";
		
	}
	
	
	@ResponseBody
	@RequestMapping("/operate")
	public JSONObject deleteParamters(ModelMap map, HttpServletRequest request,Area area,AreaQueryParam parma) throws Exception{
		JSONObject  json= new JSONObject();
		String operType= request.getParameter("operationType");
		Employee user= SessionUtils.getEMP(request);
		//0 删除  1  新增  2 修改   
		synchronized(this){
			try{
				switch (operType){ 
				case "0": areaService.deleteTree(parma.getId());  break;
				case "1": area.setLevel(area.getLevel()+1);area.setUpdate_user(Integer.toString(user.getId()));area.setCreate_user(Integer.toString(user.getId()));areaService.addTree(area);  break;
				case "2": parma.setUpdate_user(Integer.toString(user.getId()));areaService.updateTree(parma); break;
				}
				json.put("code", "1");
			} catch(Exception e) {
				json.put("code", "0");
				logger.error(e); 
				
			}
		
		}
		return json;
	  
	}
	
	@RequestMapping("/findOne*")
	public String toFormq(ModelMap map, HttpServletRequest request) throws Exception{
		String path= request.getRequestURI();
		path= path.split("findOne")[1];
		path= path.split(".do")[0];
		map.put("area", areaService.selectById(Integer.parseInt(request.getParameter("id"))));
		return "/lmis/" + path;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午2:10:39
	 */
	@RequestMapping("/getArea")
	public void getArea(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = new JSONObject();
			result.put("area", areaRadarServiceImpl.getArea(request.getParameter("area_code")));
			
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
	
}
