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
import com.bt.lmis.controller.form.ClientQueryParam;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.PageView;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

/**
 * @Title:ClientController
 * @Description: TODO(客户Controller) 
 * @author Ian.Huang 
 * @date 2016年12月6日下午1:05:16
 */
@Controller
@RequestMapping("/control/clientController")
public class ClientController extends BaseController{

	private static final Logger logger = Logger.getLogger(ClientController.class);
	
	@Resource(name= "storeServiceImpl")
	private StoreService<Store> storeServiceImpl;
	
	@Resource(name= "clientServiceImpl")
	private ClientService<Client> clientServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param client
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月7日上午10:58:56
	 */
	@RequestMapping("/editClient.do")
	public void editClient(Client client, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= clientServiceImpl.editClient(request, client, result);
			
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
	 * @param storeQueryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午3:00:15
	 */
	@RequestMapping("/toForm.do")
	public String toForm(StoreQueryParam storeQueryParam, HttpServletRequest request){
		try {
			if(CommonUtils.checkExistOrNot(storeQueryParam.getClient_id())){
				// 存在客户ID则说明为编辑
				request.setAttribute("client", clientServiceImpl.getById(storeQueryParam.getClient_id()));
				// 加载子表内容
				//根据条件查询合同集合
				PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(storeQueryParam.getPageSize() == 0? BaseConst.pageSize: storeQueryParam.getPageSize(), storeQueryParam.getPage());
				storeQueryParam.setFirstResult(pageView.getFirstResult());
				storeQueryParam.setMaxResult(pageView.getMaxresult());
				pageView.setQueryResult(storeServiceImpl.query(storeQueryParam), storeQueryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", storeQueryParam);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/client_with_store_management/client_form";
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午2:59:53
	 */
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= clientServiceImpl.deleteClient(request, result);
			
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
	 * @Description: TODO(客户查询)
	 * @param queryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月6日下午1:02:32
	 */
	@RequestMapping("/queryList.do")
	public String query(ClientQueryParam queryParam, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(clientServiceImpl.query(queryParam), queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/client_with_store_management/client_list";
		
	}
	
}
