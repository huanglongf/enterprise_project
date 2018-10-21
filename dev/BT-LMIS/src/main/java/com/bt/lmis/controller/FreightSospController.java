package com.bt.lmis.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.service.FreightSospService;

/**
 * @Title:FreightSospController
 * @Description: TODO(sosp运费) 
 * @author Ian.Huang 
 * @date 2016年7月6日下午3:51:51
 */
@Controller
@RequestMapping("/control/FreightSospController")
public class FreightSospController extends BaseController {

	private static final Logger logger = Logger.getLogger(FreightSospController.class);
	@Resource(name = "freightSospServiceImpl")
	private FreightSospService<?> freightSospServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(加载配置)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月7日下午1:34:47
	 */
	@RequestMapping("/loadConfigure")
	public void loadConfigure(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = freightSospServiceImpl.loadConfigure(request, result);
		} catch(Exception e) {
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
	 * @Description: TODO(删除承运商)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午8:15:17
	 */
	@RequestMapping("/delCarrier")
	public void delCarrier(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = freightSospServiceImpl.delCarrier(request, result);
		} catch(Exception e) {
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
	 * @Description: TODO(添加承运商)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午4:58:52
	 */
	@RequestMapping("/addCarrier")
	public void addCarrier(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = freightSospServiceImpl.addCarrier(request, result);
		} catch(Exception e) {
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
	 * @Description: TODO(查询承运商)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午3:52:01
	 */
	@RequestMapping("/searchCarrier")
	public void searchCarrier(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = freightSospServiceImpl.searchCarrier(request, result);
		} catch(Exception e) {
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
