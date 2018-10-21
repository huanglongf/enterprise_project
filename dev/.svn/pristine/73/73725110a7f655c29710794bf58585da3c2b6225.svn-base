package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.service.HcService;
import com.bt.utils.SessionUtils;
/**
 * 
* @ClassName: shopController 
* @Description: 店铺管理Contorller
* @author Likun
* @date 2016年6月8日
*
 */
@Controller
@RequestMapping("/control/hcController")
public class HcController extends BaseController{
	@Resource(name="hcServiceImpl")
	private HcService<?> hcServiceImpl;
	
	/**
	 * 
	* @Title: billDetail 
	* @Description: 店铺查询 
	* @param @param map
	* @param @param request
	* @param @return
	* @param @throws Exception
	* @return String
	* @throws
	 */
	@RequestMapping("/saveData.do")
	public void saveData(ModelMap map, HttpServletRequest request,HttpServletResponse res) throws Exception{
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String, Object> param=getParamMap(request);
			Employee user=SessionUtils.getEMP(request);
			param.put("create_user", user.getId());
			hcServiceImpl.saveData(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("result_flag", "0");
			map.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}

	
	@RequestMapping("/getTable.do")
	public void getTable(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=hcServiceImpl.getTable(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/delTable.do")
	public void delTable(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			hcServiceImpl.delTable(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
}
