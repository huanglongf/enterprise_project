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
import com.bt.lmis.controller.form.TransportVendorQueryParam;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.page.PageView;
import com.bt.lmis.service.TransportProductTypeService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

/**
 * @Title:TransportVendorController
 * @Description: TODO(物流商管理)
 * @author Ian.Huang 
 * @date 2016年12月12日下午2:52:51
 */
@Controller
@RequestMapping("/control/transportVendorController")
public class TransportVendorController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(TransportVendorController.class);
	
	@Resource(name= "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportVendor> transportProductTypeServiceImpl;
	
	@Resource(name= "transportVendorServiceImpl")
	private TransportVendorService<TransportVendor> transportVendorServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param transportVendor
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:37:46
	 */
	@RequestMapping("/save.do")
	public void save(TransportVendor transportVendor, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= transportVendorServiceImpl.save(request, transportVendor, result);
			
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
	 * @param qr
	 * @param request
	 * @throws Exception
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月13日上午11:03:56
	 */
	@RequestMapping("/toForm.do")
	public String toForm(TransportVendorQueryParam qr, HttpServletRequest request) {
		try {
			if(CommonUtils.checkExistOrNot(qr.getId())) {
				TransportVendor transportVendor= transportVendorServiceImpl.getById(qr.getId());
				// 物流商ID存在
				request.setAttribute("transportVendor", transportVendor);
				// 加载子表内容
				TransportProductTypeQueryParam transportProductTypeQueryParam= new TransportProductTypeQueryParam();
				transportProductTypeQueryParam.setVendor_code(transportVendor.getTransport_code());
				PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(transportProductTypeQueryParam.getPageSize() == 0? BaseConst.pageSize: transportProductTypeQueryParam.getPageSize(), transportProductTypeQueryParam.getPage());
				transportProductTypeQueryParam.setFirstResult(pageView.getFirstResult());
				transportProductTypeQueryParam.setMaxResult(pageView.getMaxresult());
				pageView.setQueryResult(transportProductTypeServiceImpl.query(transportProductTypeQueryParam), transportProductTypeQueryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", transportProductTypeQueryParam);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/transport_vendor/transport_vendor_form";
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param req
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月12日下午8:11:44
	 */
	@RequestMapping("/del")
	public void del(HttpServletRequest req, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= transportVendorServiceImpl.deleteTransportVendors(req, result);
			
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
	 * @date 2016年12月12日下午4:41:18
	 */
	@RequestMapping("/query.do")
	public String query(TransportVendorQueryParam queryParam, HttpServletRequest request) throws Exception{
		
		try{
			//根据条件查询合同集合
			PageView<TransportVendor> pageView= new PageView<TransportVendor>(queryParam.getPageSize() == 0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			
			pageView.setQueryResult(transportVendorServiceImpl.query(queryParam), queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/transport_vendor/transport_vendor_list";
		
	}
	
}
