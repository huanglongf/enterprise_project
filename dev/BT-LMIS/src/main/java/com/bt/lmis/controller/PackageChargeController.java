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
import com.bt.lmis.service.PackageChargeService;

/**
 * @Title:PackageChargeController
 * @Description: TODO(打包费控制器)
 * @author Ian.Huang 
 * @date 2016年7月4日下午4:17:14
 */
@Controller
@RequestMapping("/control/packageChargeController")
public class PackageChargeController extends BaseController {

	private static final Logger logger = Logger.getLogger(PackageChargeController.class);
	@Resource(name = "packageChargeServiceImpl")
	private PackageChargeService<?> packageChargeServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(保存打包价)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午3:24:16
	 */
	@RequestMapping("/savePackagePrice")
	public void savePackagePrice(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = packageChargeServiceImpl.savePackagePrice(request, result);
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
	 * @Description: TODO(删除阶梯)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:02:21
	 */
	@RequestMapping("/delLadder")
	public void delLadder(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = packageChargeServiceImpl.delLadder(request, result);
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
	 * @Description: TODO(加载阶梯内容)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:01:44
	 */
	@RequestMapping("/loadLadder")
	public void loadLadder(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = packageChargeServiceImpl.loadLadder(request, result);
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
	 * @Description: TODO(保存打包费阶梯)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:01:22
	 */
	@RequestMapping("/savePackagePriceLadder")
	public void savePackagePriceLadder(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = packageChargeServiceImpl.savePackagePriceLadder(request, result);
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
	 * @Description: TODO(判断是否存在记录)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:00:52
	 */
	@RequestMapping("/judgeExistRecord")
	public void judgeExistRecord(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = packageChargeServiceImpl.judgeExistRecord(request, result);
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
