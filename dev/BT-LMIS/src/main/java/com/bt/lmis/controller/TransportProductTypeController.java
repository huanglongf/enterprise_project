package com.bt.lmis.controller;

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
import com.bt.lmis.controller.form.TransportProductTypeQueryParam;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.page.PageView;
import com.bt.lmis.service.TransportProductTypeService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

/**
 * @Title:TransportProductTypeController
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月14日上午9:52:41
 */
@Controller
@RequestMapping("/control/transportProductTypeController")
public class TransportProductTypeController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(TransportProductTypeController.class);
	
	@Resource(name= "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportVendor> transportProductTypeServiceImpl;
	
	@RequestMapping("/getProductTypeByTranportVendor")
	public void getProductTypeByTranportVendor(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/xml; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= transportProductTypeServiceImpl.getProductTypeByTranportVendor(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
		try {
			out= response.getWriter();
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
	 * @param store
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:12:28
	 */
	@RequestMapping("/save.do")
	public void save(TransportProductType transportProductType, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= transportProductTypeServiceImpl.save(request, transportProductType, result);
			
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
			
		}
		out.close();
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午3:05:37
	 */
	@RequestMapping("/getProductType")
	public void getProductType(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/xml; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= transportProductTypeServiceImpl.getProductType(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
		try {
			out= response.getWriter();
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
	 * @date 2016年12月14日上午11:42:01
	 */
	@RequestMapping("/del")
	public void delete(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= transportProductTypeServiceImpl.deleteProductTypes(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作异常，异常原因:" + CommonUtils.getExceptionStack(e));
			
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
	 * @param queryParam
	 * @param request
	 * @throws Exception
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月14日上午9:56:38
	 */
	@RequestMapping("/query")
	public String query(TransportProductTypeQueryParam queryParam, HttpServletRequest request) throws Exception{
		
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize() == 0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			
			pageView.setQueryResult(transportProductTypeServiceImpl.query(queryParam), queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/transport_vendor/product_type_list";
		
	}
	
}
