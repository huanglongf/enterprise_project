package com.bt.orderPlatform.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.orderPlatform.controller.form.AreaQueryParam;
import com.bt.orderPlatform.model.Area;
import com.bt.orderPlatform.service.AreaService;

import net.sf.json.JSONArray;

/**
 * 城市信息主表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/areaController")
public class AreaController {

	private static final Logger logger = Logger.getLogger(AreaController.class);
	
	/**
	 * 城市信息主表服务类
	 */
	@Resource(name = "areaServiceImpl")
	private AreaService<Area> areaService;
	
	
	
	
	
	
	/**
	 * 显示城市列表
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request) throws Exception{
		map.addAttribute("menu", JSONArray.fromObject(areaService.findChildren(new HashMap<String,String>())));
		return "/op/area";
		
	}
	
	/**
	 * 单个城市查询操作
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOne*")
	public String toFormq(ModelMap map, HttpServletRequest request) throws Exception{
		String path= request.getRequestURI();
		path= path.split("findOne")[1];
		path= path.split(".do")[0];
		
		map.put("area", areaService.selectById(Integer.parseInt(request.getParameter("id"))));
		return "/op/" + path;
		
	}
	
	@ResponseBody
	@RequestMapping("/operate")
	public JSONObject deleteParamters(ModelMap map, HttpServletRequest request,Area area,AreaQueryParam parma) throws Exception{
		JSONObject  json= new JSONObject();
		String operType= request.getParameter("operationType");
		//0 删除  1  新增  2 修改   
		synchronized(this){
			try{
				switch (operType){ 
				case "0": areaService.deleteTree(parma.getId());  break;
				case "1": areaService.addTree(area);  break;
				case "2": areaService.updateTree(parma); break;
				}
				json.put("code", "1");
			} catch(Exception e) {
				json.put("code", "0");
				logger.error(e); 
				
			}
		
		}
		return json;
	  
	}
	
	
}
