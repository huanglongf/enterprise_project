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
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransportProductTypeService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.radar.controller.form.ReceiveDeadlineQueryParam;
import com.bt.radar.controller.form.TimelinessDetailQueryParam;
import com.bt.radar.model.Area;
import com.bt.radar.model.ReceiveDeadline;
import com.bt.radar.model.TimelinessDetail;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.service.AreaRadarService;
import com.bt.radar.service.PhysicalWarehouseService;
import com.bt.radar.service.ReceiveDeadlineService;
import com.bt.radar.service.TimelinessDetailService;
import com.bt.radar.service.WarninginfoMaintainMasterService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;

@Controller
@RequestMapping("/controller/receiveDeadlineController")
public class ReceiveDeadlineController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ReceiveDeadlineController.class);
	
	@Resource(name = "warninginfoMaintainMasterServiceImpl")
	private WarninginfoMaintainMasterService<?> warninginfoMaintainMasterServiceImpl;
	
	@Resource(name = "timelinessDetailServiceImpl")
	private TimelinessDetailService<?> timelinessDetailServiceImpl;
	
	@Resource(name = "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportProductType> transportProductTypeServiceImpl;
	
	@Resource(name = "areaRadarServiceImpl")
	private AreaRadarService<Area> areaRadarServiceImpl;
	
	@Resource(name = "physicalWarehouseServiceImpl")
	private PhysicalWarehouseService<?> physicalWarehouseServiceImpl;
	
	@Resource(name = "transportVendorServiceImpl")
	private TransportVendorService<?> transportVendorServiceImpl;
	
	@Resource(name = "receiveDeadlineServiceImpl")
	private ReceiveDeadlineService<?> receiveDeadlineServiceImpl;
	
	@RequestMapping("/moveDetail")
	public void moveDetail(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = timelinessDetailServiceImpl.move(result, request);
			
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
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月31日上午9:56:47
	 */
	@RequestMapping("/pauseOrStartDetail")
	public void pauseOrStartDetail(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = timelinessDetailServiceImpl.shiftStatus(result, request);
			
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
	 * @param receiveDeadline
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午5:48:13
	 */
	@RequestMapping("/saveDetail")
	public void saveDetail(TimelinessDetail timelinessDetail, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = timelinessDetailServiceImpl.save(result, timelinessDetail, request);
			
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
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:35:18
	 */
	@RequestMapping("/toDetailForm")
	public String toDetailForm(ModelMap map, HttpServletRequest request){
		try{
			request.setAttribute("pid", request.getParameter("pid"));
			// 节点省
			request.setAttribute("node_province", areaRadarServiceImpl.findArea(new Area(1)));
			// 预警类型
			WarninginfoMaintainMaster warninginfoMaintainMaster = new WarninginfoMaintainMaster();
			warninginfoMaintainMaster.setWarning_category("1");
			request.setAttribute("warninginfoMaintainMasters", warninginfoMaintainMasterServiceImpl.getWarninginfoMaintainMasterService(warninginfoMaintainMaster));
			if(CommonUtils.checkExistOrNot(request.getParameter("id"))){
				request = timelinessDetailServiceImpl.toForm(request);
				
			}
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/receive_deadline/timeliness_detail_form";
		
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
			result = timelinessDetailServiceImpl.del(result, request);
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
	 * @Description: TODO(子表列表局部加载)
	 * @param timelinessDetailqueryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:29
	 */
	@RequestMapping("/divDetailList")
	public String divDetailList(TimelinessDetailQueryParam timelinessDetailqueryParam, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(timelinessDetailqueryParam.getPageSize()==0 ? BaseConst.pageSize : timelinessDetailqueryParam.getPageSize(), timelinessDetailqueryParam.getPage());
			timelinessDetailqueryParam.setFirstResult(pageView.getFirstResult());
			timelinessDetailqueryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = timelinessDetailServiceImpl.toList(timelinessDetailqueryParam);
			pageView.setQueryResult(qr, timelinessDetailqueryParam.getPage());
			request.setAttribute("totalLength", qr.getTotalrecord());
			request.setAttribute("pageView", pageView);
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/receive_deadline/timeliness_detail_list";
		
	}
	
	/**
	 * 
	 * @Description: TODO(保存主表数据)
	 * @param receiveDeadline
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:14
	 */
	@RequestMapping("/save")
	public void save(ReceiveDeadline receiveDeadline, HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = receiveDeadlineServiceImpl.save(result, receiveDeadline, request);
			
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
	 * @param timelinessDetailQueryParam
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:34:00
	 */
	@RequestMapping("/toForm")
	public String toForm(TimelinessDetailQueryParam timelinessDetailQueryParam, HttpServletRequest request){
		try{
			// 快递集合
			request.setAttribute("expressList", transportVendorServiceImpl.getAllExpress());
			// 产品类型
			// 揽件仓库
			request.setAttribute("warehouses", physicalWarehouseServiceImpl.getPhysicalWarehouse());
			// 到达省
			request.setAttribute("destination_province", areaRadarServiceImpl.findArea(new Area(1)));
			// 加载子表内容
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(timelinessDetailQueryParam.getPageSize() == 0? BaseConst.pageSize: timelinessDetailQueryParam.getPageSize(), timelinessDetailQueryParam.getPage());
			if(CommonUtils.checkExistOrNot(request.getParameter("pid"))){
				timelinessDetailQueryParam.setFirstResult(pageView.getFirstResult());
				timelinessDetailQueryParam.setMaxResult(pageView.getMaxresult());
				QueryResult<Map<String, Object>> qr= timelinessDetailServiceImpl.toList(timelinessDetailQueryParam);
				pageView.setQueryResult(qr, timelinessDetailQueryParam.getPage());
				request.setAttribute("totalLength", qr.getTotalrecord());
				request= receiveDeadlineServiceImpl.toForm(request);
				
			}
			request.setAttribute("pageView", pageView);
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/receive_deadline/receive_deadline_form";
		
	}
	
	/**
	 * 
	 * @Description: TODO(删除主表记录)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:33:46
	 */
	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = receiveDeadlineServiceImpl.del(result, request);
			
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
	 * @Description: TODO(主表列表局部加载)
	 * @param receiveDeadlinequeryParam
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年8月30日下午3:33:20
	 */
	@RequestMapping("/divList")
	public String divList(ReceiveDeadlineQueryParam receiveDeadlinequeryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(receiveDeadlinequeryParam.getPageSize()==0 ? BaseConst.pageSize : receiveDeadlinequeryParam.getPageSize(), receiveDeadlinequeryParam.getPage());
			receiveDeadlinequeryParam.setFirstResult(pageView.getFirstResult());
			receiveDeadlinequeryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = receiveDeadlineServiceImpl.toList(receiveDeadlinequeryParam);
			pageView.setQueryResult(qr, receiveDeadlinequeryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", receiveDeadlinequeryParam);
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/receive_deadline/receive_deadline_list_div";
		
	}
	
	/**
	* @Title: getProductType
	* @Description: TODO(获取指定快递的产品类型)
	* @param @param request
	* @param @param res    设定文件
	* @return void    返回类型
	* @throws
	*/ 
	@RequestMapping("/getProductType")
	public void getProductType(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = new JSONObject();
			result.put("product_type", transportProductTypeServiceImpl.getProductTypeByTranportVendor(request.getParameter("express_code")));
			
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
	public String list(ReceiveDeadlineQueryParam receiveDeadlinequeryParam, ModelMap map, HttpServletRequest request){
		try{
			// 快递集合
			request.setAttribute("expressList", transportVendorServiceImpl.getAllExpress());
			// 产品类型
			// 揽件仓库
			request.setAttribute("warehouses", physicalWarehouseServiceImpl.getPhysicalWarehouse());
			// 到达省
			request.setAttribute("destination_province", areaRadarServiceImpl.findArea(new Area(1)));
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(receiveDeadlinequeryParam.getPageSize()==0 ? BaseConst.pageSize : receiveDeadlinequeryParam.getPageSize(), receiveDeadlinequeryParam.getPage());
			receiveDeadlinequeryParam.setFirstResult(pageView.getFirstResult());
			receiveDeadlinequeryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String, Object>> qr = receiveDeadlineServiceImpl.toList(receiveDeadlinequeryParam);
			pageView.setQueryResult(qr, receiveDeadlinequeryParam.getPage());
			request.setAttribute("pageView", pageView);
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			
		}
		return "/radar/receive_deadline/receive_deadline_list";
		
	}
	
	@RequestMapping("/edit")
	public String ExpressAlarmEdit(){
		return "/radar/Express_alarm_edit";
		
	}
	
	@ResponseBody
	@RequestMapping("/reFresh")
	public JSONObject reFresh(){
		JSONObject obj=new JSONObject();
		try{
			physicalWarehouseServiceImpl.reFresh();
			obj.put("code", 1);
		}catch(Exception e){
	      obj.put("code", 0);
			e.printStackTrace();
		}
		return obj;
		
	}
	
	
	
}
