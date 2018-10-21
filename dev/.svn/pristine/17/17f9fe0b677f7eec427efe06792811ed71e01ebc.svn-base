package com.bt.lmis.basis.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.param.Parameter;
import com.bt.common.model.ResultMessage;
import com.bt.lmis.basis.service.ConsumerManagerService;
import com.bt.lmis.basis.service.StoreManagerService;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;

/** 
 * @ClassName: StoreController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月28日 下午3:46:03 
 * 
 */
@Controller
@RequestMapping("/control/storeController")
public class StoreController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(StoreController.class);
	
	@Resource(name="consumerManagerServiceImpl")
	private ConsumerManagerService<T> consumerManagerService;
	@Resource(name="storeManagerServiceImpl")
	private StoreManagerService<T> storeManagerService;
	
	/**
	 * @Title: dataView
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年7月31日 下午4:45:39
	 */
	@RequestMapping("/dataView.do")
	public String dataView(HttpServletRequest request) {
		//根据条件查询合同集合
		Parameter paremeter=generateParameter(request);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(paremeter.getPageSize() == 0? BaseConst.pageSize : paremeter.getPageSize(), paremeter.getPage());
		paremeter.setFirstResult(pageView.getFirstResult());
		paremeter.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(
				"true".equals(paremeter.getParam().get("isQuery"))?storeManagerService.listStoreView(paremeter):new QueryResult<Map<String, Object>>(), paremeter.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("customer", consumerManagerService.listValidConsumer());
		return "lmis/basis/store/"+paremeter.getParam().get("pageName");
		
	}
	
	/**
	 * @Title: download
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: JSONObject
	 * @author: 
	 * @date: 2017年7月31日 下午4:45:39
	 */
	@ResponseBody
	@RequestMapping("/download.do")
	public JSONObject download(HttpServletRequest request,StoreQueryParam storeQueryParam) {
		JSONObject obj=new JSONObject();
		try {
			LinkedHashMap<String, String> tableHeader= new LinkedHashMap<String, String>();
			tableHeader.put("cost_center", "成本中心");
			tableHeader.put("store_code", "店铺code");
			tableHeader.put("store_name", "店铺名称");
			tableHeader.put("client_name", "所属客户");
			tableHeader.put("store_type", "店铺类型");
			tableHeader.put("settlement_method", "结算方式");
			tableHeader.put("contact", "联系人");
			tableHeader.put("phone", "电话");
			tableHeader.put("address", "地址");
			tableHeader.put("company", "开票公司");
			tableHeader.put("invoice_type", "发票类型");
			tableHeader.put("invoice_info", "发票信息");
			tableHeader.put("remark", "备注");
			tableHeader.put("wo_flag", "雷达监控");

			Parameter paremeter=generateParameter(request);
			List<Map<String, Object>> list = storeManagerService.download(paremeter);
			File f=BigExcelExport.excelDownLoadDatab_Z(list, tableHeader,"", DateUtil.formatSS (new Date())+"导出店铺信息.xlsx");
			
			obj.put("msg","操作成功！");
			obj.put("code", 1);
			obj.put("path",  f.getName());
			return obj;
			
			
		}catch(Exception e){
			obj.put("msg","执行失败！"+e.getMessage());
			obj.put("code", 0);
			e.printStackTrace();
		}
		

		return obj;
		
	}
	
	
	
	/**
	 * @Title: getStore
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午3:09:05
	 */
	@RequestMapping("/getStore.do")
	public void getStore(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		try {
			JSONObject result= new JSONObject();
			result.put("consumer", consumerManagerService.listValidConsumer());
			result.put("store", storeManagerService.getStore(Integer.parseInt(request.getParameter("id"))));
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
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午3:17:11
	 */
	@RequestMapping("/save.do")
	public void save(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out=null;
		try {
			JSONObject result=new JSONObject();
			ResultMessage rm=storeManagerService.save(generateParameter(request));
			result.put("code",rm.getCode());
			result.put("message",rm.getMessage());
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
	 * @Title: del
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午6:44:56
	 */
	@RequestMapping("/del.do")
	public void del(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out=null;
		try {
			JSONObject result=new JSONObject();
			ResultMessage rm=storeManagerService.del(generateParameter(request));
			result.put("code",rm.getCode());
			result.put("message",rm.getMessage());
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
	
	@Resource(name = "storeServiceImpl")
	private StoreService<Store> storeServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param store
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月14日下午4:12:19
	 */
	@RequestMapping("/save2.do")
	public void save2(Store store, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= storeServiceImpl.save(request, store, result);
			
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
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月9日下午2:16:50
	 */
	@RequestMapping("/getStore2.do")
	public void getStore2(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/xml; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= storeServiceImpl.getStore(request, result);
			
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
			
		} finally {
			out.close();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午4:25:07
	 */
	@RequestMapping("/del2")
	public void del2(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= storeServiceImpl.deleteStores(request, result);
			
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
			
		} finally {
			out.close();
			
		}

	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param storeQueryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月8日下午4:25:11
	 */
	@RequestMapping("/query2")
	public String query2(StoreQueryParam storeQueryParam, HttpServletRequest request) {
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(storeQueryParam.getPageSize() == 0? BaseConst.pageSize: storeQueryParam.getPageSize(), storeQueryParam.getPage());
		storeQueryParam.setFirstResult(pageView.getFirstResult());
		storeQueryParam.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(storeServiceImpl.query(storeQueryParam), storeQueryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", storeQueryParam);
		return "/lmis/client_with_store_management/store_list";
		
	}

	/**
	 * 跳转到绑定物流商界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/storeSetTransport")
	public String storeSetTransport( HttpServletRequest request) {
		storeManagerService.getSomeParams(request);
		return "/lmis/basis/store/storeSetTransport";

	}
	@ResponseBody
	@RequestMapping("/addStoreTransport")
	public  String addStoreTransport(HttpServletRequest request,String transportCodes,String storeCode,String storebj){
		Map<String,Object> result = new HashMap<>();
		int i =storeManagerService.addStoreTransport(request,transportCodes,storeCode,storebj);
		if(i>0){
			result.put("msg","保存成功");
		}else {
			result.put("msg","保存失败");
		}
		return JSONObject.toJSONString(result);
	}
}
