package com.bt.workOrder.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.StoreService;
import com.bt.radar.model.Routecode;
import com.bt.radar.service.RoutecodeService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.bean.WorkBaseInfoBean;
import com.bt.workOrder.controller.form.CaimantWKParam;
import com.bt.workOrder.controller.form.CheckWKParam;
import com.bt.workOrder.service.WkTypeService;
import com.bt.workOrder.service.WorkBaseInfoService;

@Controller
@RequestMapping("/control/exportWKController")
public class ExportWKController {

	@Resource(name="workBaseInfoServiceImpl")
	private WorkBaseInfoService<WorkBaseInfoBean> workBaseInfoServiceImpl;
	
	@Resource(name="employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;

	@Resource(name="storeServiceImpl")
	private StoreService<Store> storeServiceImpl;
	
	@Resource(name = "routecodeServiceImpl")
	private RoutecodeService<Routecode> routecodeServiceImpl;

	@Resource(name = "wkTypeServiceImpl")
	private WkTypeService<T> wkTypeServiceImpl;
	/** 
	* @Title: exportCheckWK 
	* @Description: TODO(跳转查件导出页面) 
	* @param @param queryParam
	* @param @param request
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/exportCheckWK.do")
	public String exportCheckWK(CheckWKParam queryParam,ModelMap map, HttpServletRequest request) throws Exception{
		List<Employee> work_emp_list = employeeServiceImpl.findByPro("workorder");
		List<Map<String, Object>> store_list = storeServiceImpl.findAll();
		List<Routecode> carrier_list = routecodeServiceImpl.selectTransport_vender();
		List<Map<String, Object>> wktype_list = wkTypeServiceImpl.findByWKTYPE();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=queryParam.getS_time() && !queryParam.getS_time().equals("") && !queryParam.getS_time().equals("NaN")){
			Date date = new Date(Long.valueOf(queryParam.getS_time()));
			queryParam.setS_time(sdf.format(date));
		}
		if(null!=queryParam.getE_time() && !queryParam.getE_time().equals("") && !queryParam.getE_time().equals("NaN")){
			Date date = new Date(Long.valueOf(queryParam.getE_time()));
			queryParam.setE_time(sdf.format(date));
		}
		for (int i = 0; i < wktype_list.size(); i++) {
			Map<String, Object> maps = new HashMap<String, Object>();
			List<Map<String, Object>> wkerror_list = wkTypeServiceImpl.findByWKERROR(wktype_list.get(i).get("code").toString());
			maps.put("type_code", wktype_list.get(i).get("code").toString());
			maps.put("wkerror_list",wkerror_list);
			lists.add(maps);
		}
		map.addAttribute("lists", lists);
		map.addAttribute("wktype_list", wktype_list);
		map.addAttribute("carrier_list", carrier_list);
		map.addAttribute("work_emp_list", work_emp_list);
		map.addAttribute("store_list", store_list);

		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = wkTypeServiceImpl.query(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		
		return "/work_order/check_wk";
	}

	/** 
	* @Title: exportCaimantWK 
	* @Description: TODO(跳转索赔导出页面) 
	* @param @param queryParam
	* @param @param request
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/exportCaimantWK.do")
	public String exportCaimantWK(CaimantWKParam queryParam, ModelMap map,HttpServletRequest request) throws Exception{

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=queryParam.getS_time() && !queryParam.getS_time().equals("") && !queryParam.getS_time().equals("NaN")){
			Date date = new Date(Long.valueOf(queryParam.getS_time()));
			queryParam.setS_time(sdf.format(date));
		}
		if(null!=queryParam.getE_time() && !queryParam.getE_time().equals("") && !queryParam.getE_time().equals("NaN")){
			Date date = new Date(Long.valueOf(queryParam.getE_time()));
			queryParam.setE_time(sdf.format(date));
		}
		List<Employee> work_emp_list = employeeServiceImpl.findByPro("workorder");
		List<Map<String, Object>> store_list = storeServiceImpl.findAll();
		List<Routecode> carrier_list = routecodeServiceImpl.selectTransport_vender();
		List<Map<String, Object>> wktype_list = wkTypeServiceImpl.findByWKTYPE();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < wktype_list.size(); i++) {
			Map<String, Object> maps = new HashMap<String, Object>();
			List<Map<String, Object>> wkerror_list = wkTypeServiceImpl.findByWKERROR(wktype_list.get(i).get("code").toString());
			maps.put("type_code", wktype_list.get(i).get("code").toString());
			maps.put("wkerror_list",wkerror_list);
			lists.add(maps);
		}
		map.addAttribute("lists", lists);
		map.addAttribute("wktype_list", wktype_list);
		map.addAttribute("carrier_list", carrier_list);
		map.addAttribute("work_emp_list", work_emp_list);
		map.addAttribute("store_list", store_list);

		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = wkTypeServiceImpl.query(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/work_order/caimant_wk";
	}
	

	/** 
	* @Title: expCheckWK 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param res
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/expCheckWK.do")
	public void expCheckWK(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Employee iflogin = SessionUtils.getEMP(request);
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		try{
			out = res.getWriter();
			String s_time = request.getParameter("s_time");
			String e_time = request.getParameter("e_time");
			Map<String, String> param = new HashMap<String, String>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=s_time && !s_time.equals("") && !s_time.equals("NaN")){
				Date date = new Date(Long.valueOf(s_time));
				s_time=sdf.format(date);
			}
			if(null!=e_time && !e_time.equals("") && !e_time.equals("NaN")){
				Date date = new Date(Long.valueOf(e_time));
				e_time=sdf.format(date);
			}
			String create_by = request.getParameter("create_by");
			String processor = request.getParameter("processor");
			String store = request.getParameter("store");
			String carrier = request.getParameter("carrier");
			String wo_type = request.getParameter("wo_type");
			String exception_type = request.getParameter("exception_type");
			String express_number = request.getParameter("express_number");
			String wo_no = request.getParameter("wo_no");
			param.put("s_time", s_time);
			param.put("e_time", e_time);
			param.put("create_by", create_by);
			param.put("processor", processor);
			param.put("store", store);
			param.put("carrier", carrier);
			param.put("wo_type", wo_type);
			param.put("exception_type", exception_type);
			param.put("express_number", express_number);
			param.put("wo_no", wo_no);
			List<Map<String,Object>> mList = wkTypeServiceImpl.exportWK(param);
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("工单号", "工单号");
			cMap.put("创建时间", "创建时间");
			cMap.put("jwarehouse_name", "物理仓");
			cMap.put("iwarehouse_name", "系统仓");
			cMap.put("创建人", "创建人");
			cMap.put("查询人", "查询人");
			cMap.put("店铺名称", "店铺名称");
			cMap.put("快递公司", "快递公司");
			cMap.put("快递单号", "快递单号");
			cMap.put("出库时间", "出库时间");
			cMap.put("省", "省");
			cMap.put("市", "市");
			cMap.put("详细地址", "详细地址");
			cMap.put("工单类型", "工单类型");
			cMap.put("异常类型", "异常类型");
			cMap.put("处理人", "处理人");
			cMap.put("跟进结果", "跟进结果");
			cMap.put("工单状态", "工单状态");
			cMap.put("处理时间", "处理时间");
			BigExcelExport.excelDownLoadDatab(mList, cMap, iflogin.getName()+"查件工单报表.xlsx");
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
			result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ iflogin.getName()+"查件工单报表.xlsx");
		}catch(Exception e){
			e.printStackTrace();
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/expCheckWKs.do")
	public void expCheckWKs(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Employee iflogin = SessionUtils.getEMP(request);
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		try{
			out = res.getWriter();
			Map<String, String> param = new HashMap<String, String>();
			String s_time = request.getParameter("s_time");
			String e_time = request.getParameter("e_time");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=s_time && !s_time.equals("") && !s_time.equals("NaN")){
				Date date = new Date(Long.valueOf(s_time));
				s_time=sdf.format(date);
			}
			if(null!=e_time && !e_time.equals("") && !e_time.equals("NaN")){
				Date date = new Date(Long.valueOf(e_time));
				e_time=sdf.format(date);
			}
			String create_by = request.getParameter("create_by");
			String processor = request.getParameter("processor");
			String store = request.getParameter("store");
			String carrier = request.getParameter("carrier");
			String express_number = request.getParameter("express_number");
			String wo_no = request.getParameter("wo_no");
			String sp_type = request.getParameter("sp_type");
			
			param.put("s_time", s_time);
			param.put("e_time", e_time);
			param.put("create_by", create_by);
			param.put("processor", processor);
			param.put("store", store);
			param.put("carrier", carrier);
			param.put("express_number", express_number);
			param.put("wo_no", wo_no);
			param.put("sp_type", sp_type);
			List<Map<String,Object>> mList = wkTypeServiceImpl.exportWKSP(param);
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("工单号", "工单号");
			cMap.put("创建日期", "创建日期");
			cMap.put("物理仓", "物理仓");
			cMap.put("系统仓", "系统仓");
			cMap.put("出库时间", "出库时间");
			cMap.put("行业", "行业");
			cMap.put("店铺名称", "店铺名称");
			cMap.put("平台订单号", "平台订单号");
			cMap.put("前置单据号", "前置单据号");
			cMap.put("商品SKU编码", "商品SKU编码");
			cMap.put("商品名称/型号", "商品名称/型号");
			cMap.put("商品数量", "商品数量");
			cMap.put("商品金额", "商品金额");
			cMap.put("附加金额", "附加金额");
			cMap.put("申请赔付金额", "申请赔付金额");
			cMap.put("实际赔付总计", "实际赔付总计");
			cMap.put("物流部赔付金额", "物流部赔付金额");
			cMap.put("快递赔付金额", "快递赔付金额");
			cMap.put("店铺赔付金额", "店铺赔付金额");
			cMap.put("索赔状态", "索赔状态");
			cMap.put("索赔分类", "索赔分类");
			cMap.put("索赔原因", "索赔原因");
			cMap.put("快递公司", "快递公司");
			cMap.put("快递单号", "快递单号");
			cMap.put("退货单号", "退货单号");
			cMap.put("完成时间", "完成时间");
			cMap.put("备注", "备注");
			cMap.put("收货省份", "收货省份");
			cMap.put("收货市区", "收货市区");
			cMap.put("收货区县", "收货区县");
			cMap.put("详细地址", "详细地址");
			cMap.put("jcolumn_value", "赔偿人");
			cMap.put("是否已结案", "是否已结案");
			BigExcelExport.excelDownLoadDatab(mList, cMap, iflogin.getName()+"索赔工单报表.xlsx");
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
			result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + iflogin.getName()+"索赔工单报表.xlsx");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		
	}
	
}
