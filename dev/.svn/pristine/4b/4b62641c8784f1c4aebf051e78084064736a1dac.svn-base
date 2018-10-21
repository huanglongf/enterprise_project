package com.bt.radar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.PhysicalWarehouseQueryParam;
import com.bt.radar.controller.form.WarehouseRelationQueryParam;
import com.bt.radar.model.Area;
import com.bt.radar.model.PhysicalWarehouse;
import com.bt.radar.model.WarehouseRelation;
import com.bt.radar.service.AreaRadarService;
import com.bt.radar.service.PhysicalWarehouseService;
import com.bt.radar.service.WarehouseRelationService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

@Controller
@RequestMapping("/controller/physicalWarehouseController")
public class PhysicalWarehouseController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(PhysicalWarehouseController.class);
	
	@Resource(name = "warehouseRelationServiceImpl")
	private WarehouseRelationService<?> warehouseRelationServiceImpl;
	
	@Resource(name = "areaRadarServiceImpl")
	private AreaRadarService<Area> areaRadarServiceImpl;
	
	@Resource(name = "physicalWarehouseServiceImpl")
	private PhysicalWarehouseService<?> physicalWarehouseServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(子表列表局部加载)
	 * @param warehouseRelationQueryParam
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:29
	 */
	@RequestMapping("/divDetailList")
	public String divDetailList(WarehouseRelationQueryParam warehouseRelationQueryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(warehouseRelationQueryParam.getPageSize()==0 ? BaseConst.pageSize : warehouseRelationQueryParam.getPageSize(), warehouseRelationQueryParam.getPage());
			warehouseRelationQueryParam.setFirstResult(pageView.getFirstResult());
			warehouseRelationQueryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = warehouseRelationServiceImpl.toList(warehouseRelationQueryParam);
			pageView.setQueryResult(qr, warehouseRelationQueryParam.getPage());
			request.setAttribute("pageView", pageView);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/radar/physical_warehouse/logic_warehouse_list";
	}
	
	/**
	 * 
	 * @Description: TODO(删除子表记录)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:55
	 */
	@RequestMapping("/delDetail")
	public void delDetail(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = warehouseRelationServiceImpl.del(result, request);
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
	 * @Description: TODO(保存明细表数据)
	 * @param warehouseRelation
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午5:48:13
	 */
	@RequestMapping("/saveDetail")
	public void saveDetail(WarehouseRelation warehouseRelation, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = warehouseRelationServiceImpl.save(result, warehouseRelation, request);
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
	 * @Description: TODO(加载子表编辑页面)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月20日下午8:38:13
	 */
	@RequestMapping("/toDetailForm")
	public void toDetailForm(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= warehouseRelationServiceImpl.toForm(request, result);
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:" + e.getMessage());
			
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
	 * @Description: TODO(保存主表数据)
	 * @param physicalWarehouse
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:14
	 */
	@RequestMapping("/save")
	public void save(PhysicalWarehouse physicalWarehouse, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = physicalWarehouseServiceImpl.save(result, physicalWarehouse, request);
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
	 * @Description: TODO(加载编辑页面)
	 * @param warehouseRelationQueryParam
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:00
	 */
	@RequestMapping("/toForm")
	public String toForm(WarehouseRelationQueryParam warehouseRelationQueryParam, ModelMap map, HttpServletRequest request){
		try{
			// 到达省
			Area area = new Area();
			area.setLevel(1);
			request.setAttribute("province", areaRadarServiceImpl.findArea(area));
			// 加载子表内容
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(warehouseRelationQueryParam.getPageSize()==0 ? BaseConst.pageSize : warehouseRelationQueryParam.getPageSize(), warehouseRelationQueryParam.getPage());
			warehouseRelationQueryParam.setFirstResult(pageView.getFirstResult());
			warehouseRelationQueryParam.setMaxResult(pageView.getMaxresult());
			if(CommonUtils.checkExistOrNot(request.getParameter("pid"))){
				QueryResult<Map<String, Object>> qr = warehouseRelationServiceImpl.toList(warehouseRelationQueryParam);
				pageView.setQueryResult(qr, warehouseRelationQueryParam.getPage());
				request = physicalWarehouseServiceImpl.toForm(request);
				
			}
			request.setAttribute("pageView", pageView);
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/physical_warehouse/physical_warehouse_form";
		
	}
	
	/**
	 * 
	 * @Description: TODO(删除一条物理仓记录)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午2:22:02
	 */
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = physicalWarehouseServiceImpl.del(result, request);
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
	
	@RequestMapping("/list")
	public String list(PhysicalWarehouseQueryParam physicalWarehouseQueryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(physicalWarehouseQueryParam.getPageSize()==0 ? BaseConst.pageSize : physicalWarehouseQueryParam.getPageSize(), physicalWarehouseQueryParam.getPage());
			physicalWarehouseQueryParam.setFirstResult(pageView.getFirstResult());
			physicalWarehouseQueryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = physicalWarehouseServiceImpl.toList(physicalWarehouseQueryParam);
			pageView.setQueryResult(qr, physicalWarehouseQueryParam.getPage());
			request.setAttribute("pageView", pageView);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		String url = "";
		if(CommonUtils.checkExistOrNot(request.getParameter("flag"))){
			url = "/radar/physical_warehouse/physical_warehouse_list_div";
		} else {
			url = "/radar/physical_warehouse/physical_warehouse_list";
		}
		return url;
	}
	
	@ResponseBody
	@RequestMapping("/refresh")
	public JSONObject refresh(PhysicalWarehouseQueryParam physicalWarehouseQueryParam, ModelMap map, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		try{
			physicalWarehouseServiceImpl.reFresh();
			obj.put("code", 1);
		}catch(Exception e){
			logger.error(e);
			obj.put("code", 0);
			e.printStackTrace();
		}

		return obj;
	}
	
}
