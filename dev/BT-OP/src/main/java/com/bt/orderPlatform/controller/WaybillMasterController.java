package com.bt.orderPlatform.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bt.base.BaseConstant;
import com.bt.common.util.BaseConst;
import com.bt.common.util.BigExcelExport;
import com.bt.common.util.CSVutil;
import com.bt.common.util.CommonUtil;
import com.bt.common.util.CommonUtils;
import com.bt.common.util.CookiesUtil;
import com.bt.common.util.FileUtil;
import com.bt.common.util.OSinfo;
import com.bt.common.util.OneBarcodeUtil;
import com.bt.common.util.XLSXCovertCSVReader;
import com.bt.interf.rfd.RFDInterface;
import com.bt.interf.sf.SfInterface;
import com.bt.interf.yl.YlInterface;
import com.bt.interf.zto.ZTOInterface;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.Area;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.CostCenter;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.OrganizationInformation;
import com.bt.orderPlatform.model.PayPath;
import com.bt.orderPlatform.model.TransportProductType;
import com.bt.orderPlatform.model.TransportVender;
import com.bt.orderPlatform.model.ViewArea;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterBackups;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.AreaService;
import com.bt.orderPlatform.service.CostCenterService;
import com.bt.orderPlatform.service.ExpressinfoMasterInputlistService;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.OrganizationInformationService;
import com.bt.orderPlatform.service.PayPathService;
import com.bt.orderPlatform.service.TransportProductTypeService;
import com.bt.orderPlatform.service.TransportVenderService;
import com.bt.orderPlatform.service.ViewAreaService;
import com.bt.orderPlatform.service.WaybilDetailBackupsService;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
import com.bt.orderPlatform.service.WaybillMasterBackupsService;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.bt.sys.model.Account;
import com.bt.sys.model.BusinessPower;
import com.bt.sys.util.SysUtil;

import net.sf.json.JSONObject;

/**
 * 运单信息主表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/waybillMasterController")
public class WaybillMasterController {

	private static final Logger logger = Logger.getLogger(WaybillMasterController.class);

	/**
	 * 运单信息主表服务类
	 */
	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;
	@Resource(name = "viewAreaServiceImpl")
	private ViewAreaService<ViewArea> viewAreaService;
	@Resource(name = "waybillMasterServiceImpl")
	private  WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
	@Resource(name = "organizationInformationServiceImpl")
	private OrganizationInformationService<OrganizationInformation> organizationInformationServiceImpl;
	@Resource(name = "transportVenderServiceImpl")
	private TransportVenderService<TransportVender> transportVenderService;
	@Resource(name = "areaServiceImpl")
	private AreaService<TransportVender> areaService;
	@Resource(name = "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportProductType> transportProductTypeService;
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoServiceImpl;
	@Resource(name = "expressinfoMasterInputlistServiceImpl")
	private ExpressinfoMasterInputlistService<ExpressinfoMasterInputlist> expressinfoMasterInputlistService;
	@Resource(name = "waybilMasterDetailServiceImpl")
	private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
	@Autowired
	private YlInterface ylInterface;
	@Autowired
	private SfInterface sfInterface;
	@Autowired
	private RFDInterface rfdInterface;
	@Autowired
	private ZTOInterface zTOInterface;
	@Resource(name = "waybillMasterBackupsServiceImpl")
	private WaybillMasterBackupsService<WaybillMasterBackups> waybillMasterBackupsService;
	@Resource(name = "waybilDetailBackupsServiceImpl")
	private WaybilDetailBackupsService<WaybilDetail> waybilDetailBackupsService;
	@Resource(name = "costCenterServiceImpl")
	private CostCenterService<CostCenter> costCenterService;
	@Resource(name = "payPathServiceImpl")
	private PayPathService<PayPath> payPathService;
	
	/**
	 * 跳转新增页面
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/addlogistics")
	public String placeOrder(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		WaybillMaster waybill = new WaybillMaster();
		request.setAttribute("queryParam", waybill);
		List<TransportVender> list = transportVenderService.getAllVender(power.carrier);
		request.setAttribute("venders", list);
		Account temp = SysUtil.getAccountInSession(request);
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		request.setAttribute("org", org);
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		OrganizationInformation queryOrg = new OrganizationInformation();
		request.setAttribute("queryOrg", queryOrg);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		if (org.getOrg_province_code() != null && !"".equals(org.getOrg_province_code())) {
			area.setPid(2);
			area.setArea_code(org.getOrg_province_code());
			request.setAttribute("city", areaService.findRecordsByP_code(area));
		}
		if (org.getOrg_city_code() != null && !"".equals(org.getOrg_city_code())) {
			area.setPid(3);
			area.setArea_code(org.getOrg_city_code());
			request.setAttribute("state", areaService.findRecordsByP_code(area));
		}
		return "op/placeanorder";
	}

	/**
	 * 跳转新增页面
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/addlogisticsa")
	public String placeOrdera(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		WaybillMaster waybill = new WaybillMaster();
		request.setAttribute("queryParam", waybill);
		Account temp = SysUtil.getAccountInSession(request);
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		request.setAttribute("org", org);
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		OrganizationInformation queryOrg = new OrganizationInformation();
		request.setAttribute("queryOrg", queryOrg);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		if (org.getOrg_province_code() != null && !"".equals(org.getOrg_province_code())) {
			area.setPid(2);
			area.setArea_code(org.getOrg_province_code());
			request.setAttribute("city", areaService.findRecordsByP_code(area));
		}
		if (org.getOrg_city_code() != null && !"".equals(org.getOrg_city_code())) {
			area.setPid(3);
			area.setArea_code(org.getOrg_city_code());
			request.setAttribute("state", areaService.findRecordsByP_code(area));
		}
		
		return "op/placeanorder2";
	}
	@RequestMapping("/baozunaddlogistics")
	public String baozunaddlogistics(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		WaybillMaster waybill = new WaybillMaster();
		request.setAttribute("queryParam", waybill);
		Account temp = SysUtil.getAccountInSession(request);
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		request.setAttribute("org", org);
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		List<PayPath> listpayPath =payPathService.queryAll();
		request.setAttribute("pay_path", listpayPath);
		OrganizationInformation queryOrg = new OrganizationInformation();
		request.setAttribute("queryOrg", queryOrg);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		if (org.getOrg_province_code() != null && !"".equals(org.getOrg_province_code())) {
			area.setPid(2);
			area.setArea_code(org.getOrg_province_code());
			request.setAttribute("city", areaService.findRecordsByP_code(area));
		}
		if (org.getOrg_city_code() != null && !"".equals(org.getOrg_city_code())) {
			area.setPid(3);
			area.setArea_code(org.getOrg_city_code());
			request.setAttribute("state", areaService.findRecordsByP_code(area));
		}
		List<CostCenter> list =costCenterService.selectAll();
		request.setAttribute("cost_center", list);
		List<TransportVender> venders = transportVenderService.getAllVender(power.carrier);
		request.setAttribute("venders", venders);
		if(CommonUtils.checkExistOrNot(queryParam.getExpressCode())){
			List<TransportProductType> list2 = transportProductTypeService.findByExpresscodeAndcarrier_type(queryParam.getExpressCode(),power.carrier_type);
			request.setAttribute("type", list2);
		}
		return "op/placeanorder4";
	}

	/**
	 * 获取城市
	 * 
	 * @param entity
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getArea")
	public JSONObject getArea(Area entity, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		List<Area> areas = areaService.getArea(entity.getArea_code());
		if (areas != null && areas.size() != 0) {
			obj.put("area", areas);
			obj.put("code", 1);

		} else {

			obj.put("code", 0);
		}
		return obj;
	}

	/**
	 * 分页
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/alllogistics_page")
	public String alllogistics_page(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
			queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
		}
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		CookiesUtil.setCookie(response, "page", paraJsonStr, 5*1000);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/alllogistics_page";
	}
	/**
	 * 分页
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	public String page(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
			queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
		}
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		CookiesUtil.setCookie(response, "page", paraJsonStr, 5*1000);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/logistics_page";
	}

	@RequestMapping("/pagea")
	public String pagea(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
			queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
		}
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		CookiesUtil.setCookie(response, "pagea", paraJsonStr, 5*1000);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/logistics_page2";
	}
	@RequestMapping("/baozunpage")
	public String baozunpage(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
			queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
		}
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		CookiesUtil.setCookie(response, "baozunpage", paraJsonStr, 5*1000);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/baozunlogistics_page";
	}

	@RequestMapping("/querytest")
	public String querytest() {

		return "op/main";
	}

	/**
	 * 运单界面
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/initail")
	public String waybillOrder(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		String paraJson = JSON.toJSONString(null);
		CookiesUtil.setCookie(response, "waybillOrder", paraJsonStr, 5*1000);
		CookiesUtil.setCookie(response, "waybillOrdera", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunwaybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "toForm", paraJson, 0);
		CookiesUtil.setCookie(response, "page", paraJson, 0);
		CookiesUtil.setCookie(response, "pagea", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunpage", paraJson, 0);
		
		Account temp = SysUtil.getAccountInSession(request);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		List<TransportVender> list = transportVenderService.getAllVender(power.carrier);
		request.setAttribute("venders", list);
		if(CommonUtils.checkExistOrNot(queryParam.getExpressCode())){
			List<TransportProductType> list2 = transportProductTypeService.findByExpresscodeAndcarrier_type(queryParam.getExpressCode(),power.carrier_type);
			request.setAttribute("type", list2);
		}
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/logistics";
	}

	/**
	 * 门店运单界面
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/initaila")
	public String waybillOrdera(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		Account temp = SysUtil.getAccountInSession(request);
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		String paraJson = JSON.toJSONString(null);
		CookiesUtil.setCookie(response, "waybillOrdera", paraJsonStr, 5*1000);
		CookiesUtil.setCookie(response, "waybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "toForm", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunwaybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "page", paraJson, 0);
		CookiesUtil.setCookie(response, "pagea", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunpage", paraJson, 0);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		List<TransportVender> list = transportVenderService.getAllVender(power.carrier);
		request.setAttribute("venders", list);
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/logistics2";
	}
	
	/**
	 * 宝尊运单界面
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/baozuninitaila")
	public String baozuninitaila(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		Account temp = SysUtil.getAccountInSession(request);
	    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		queryParam.setEss_time(formater.format(new Date())+ " 00:00:00");
		if(queryParam.getStatus()==null){
			queryParam.setOutStatus("1");
		}
		String paraJsonStr = JSON.toJSONString(queryParam);
		String paraJson = JSON.toJSONString(null);
		CookiesUtil.setCookie(response, "baozunwaybillOrder", paraJsonStr, 5*1000);
		CookiesUtil.setCookie(response, "waybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "waybillOrdera", paraJson, 0);
		CookiesUtil.setCookie(response, "toForm", paraJson, 0);
		CookiesUtil.setCookie(response, "page", paraJson, 0);
		CookiesUtil.setCookie(response, "pagea", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunpage", paraJson, 0);
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/baozunlogistics";
	}

	/**
	 * 运单快速查询
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/toForm")
	public String toForm(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		Account temp = SysUtil.getAccountInSession(request);
		
		String paraJsonStr = JSON.toJSONString(queryParam);
		String paraJson = JSON.toJSONString(null);
		CookiesUtil.setCookie(response, "toForm", paraJsonStr, 5*1000);
		CookiesUtil.setCookie(response, "baozunwaybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "waybillOrdera", paraJson, 0);
		CookiesUtil.setCookie(response, "waybillOrder", paraJson, 0);
		CookiesUtil.setCookie(response, "page", paraJson, 0);
		CookiesUtil.setCookie(response, "pagea", paraJson, 0);
		CookiesUtil.setCookie(response, "baozunpage", paraJson, 0);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/alllogistics";
	}

	/**
	 * 新增运单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getName());
		queryParam.setUpdate_user(temp.getName());
		UUID uuid = UUID.randomUUID();
		queryParam.setId(uuid.toString());
		queryParam.setFrom_orgnization_code(temp.getOrgid());
		Date date = new Date();
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(org.getPay_path())){
				queryParam.setPay_path(org.getPay_path());
			}
		}
		queryParam.setStore_code(org.getStore_code());
		queryParam.setCustid(org.getCustid());
		queryParam.setFrom_orgnization(org.getOrg_name());
		queryParam.setCreate_time(date);
		queryParam.setUpdate_time(date);
		queryParam.setPrint_code("0");
		queryParam.setCost_center("0");
		queryParam.setPay_path("3");
		queryParam.setExpress_name(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getExpress_name());
		queryParam.setIs_docall(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getIs_docall());
		queryParam.setProducttype_name(
				(transportProductTypeService.selectByProducttype_code(queryParam.getProducttype_code(),
						queryParam.getExpressCode())).getProduct_type_name());
		queryParam.setFrom_province(areaService.queryByAreaCode(queryParam.getFrom_province_code()));
		queryParam.setFrom_city(areaService.queryByAreaCode(queryParam.getFrom_city_code()));
		queryParam.setFrom_state(areaService.queryByAreaCode(queryParam.getFrom_state_code()));
		queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
		queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
		queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));
		queryParam.setStatus("1");
		queryParam.setNew_source(1);
		JSONArray arr = JSON.parseArray(request.getParameter("json").toString());
		waybillMasterServiceImpl.insertOrder(queryParam, arr, date);
		obj.put("data", 0);
		return obj;
	}
	@ResponseBody
	@RequestMapping("/baozunadd")
	public JSONObject baozunadd(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getName());
		queryParam.setUpdate_user(temp.getName());
		UUID uuid = UUID.randomUUID();
		queryParam.setId(uuid.toString());
		queryParam.setFrom_orgnization_code(temp.getOrgid());
		Date date = new Date();
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(org.getPay_path())){
				queryParam.setPay_path(org.getPay_path());
			}
		}
		queryParam.setCustid(org.getCustid());
		queryParam.setStore_code(org.getStore_code());
		queryParam.setFrom_orgnization(org.getOrg_name());
		queryParam.setCreate_time(date);
		queryParam.setUpdate_time(date);
		queryParam.setPrint_code("0");
		queryParam.setExpress_name(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getExpress_name());
		queryParam.setIs_docall(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getIs_docall());
		queryParam.setProducttype_name(
				(transportProductTypeService.selectByProducttype_code(queryParam.getProducttype_code(),
						queryParam.getExpressCode())).getProduct_type_name());
		queryParam.setFrom_province(areaService.queryByAreaCode(queryParam.getFrom_province_code()));
		queryParam.setFrom_city(areaService.queryByAreaCode(queryParam.getFrom_city_code()));
		queryParam.setFrom_state(areaService.queryByAreaCode(queryParam.getFrom_state_code()));
		queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
		queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
		queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));
		queryParam.setStatus("1");
		queryParam.setNew_source(3);
		JSONArray arr = JSON.parseArray(request.getParameter("json").toString());	
		waybillMasterServiceImpl.insertOrder(queryParam, arr, date);

		obj.put("data", 0);
		obj.put("id", queryParam.getId());
		return obj;
	}

	
	/**
	 * 新增运单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adda")
	public JSONObject adda(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getName());
		queryParam.setUpdate_user(temp.getName());
		UUID uuid = UUID.randomUUID();
		queryParam.setId(uuid.toString());
		OrganizationInformation organizationInformation = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(organizationInformation.getPay_path())){
				queryParam.setPay_path(organizationInformation.getPay_path());
			}
		}
		queryParam.setStore_code(organizationInformation.getStore_code());
		queryParam.setCustid(organizationInformation.getCustid());
		queryParam.setFrom_orgnization_code(organizationInformation.getOrganization_code());
		queryParam.setFrom_orgnization(organizationInformation.getOrg_name());
		queryParam.setFrom_contacts(organizationInformation.getOrg_contacts());
		queryParam.setFrom_address(organizationInformation.getOrg_address());
		queryParam.setFrom_city(organizationInformation.getOrg_city());
		queryParam.setFrom_city_code(organizationInformation.getOrg_city_code());
		queryParam.setFrom_orgnization_code(organizationInformation.getId());
		queryParam.setFrom_phone(organizationInformation.getOrg_phone());
		queryParam.setFrom_province(organizationInformation.getOrg_province());
		queryParam.setFrom_province_code(organizationInformation.getOrg_province_code());
		queryParam.setFrom_state(organizationInformation.getOrg_state());
		queryParam.setFrom_state_code(organizationInformation.getOrg_state_code());
		queryParam.setFrom_street(organizationInformation.getOrg_street());
		if(queryParam.getTo_contacts()==null||queryParam.getTo_phone()==null||queryParam.getTo_province_code()==null||
				queryParam.getTo_address()==null||queryParam.getTo_city_code()==null||queryParam.getTo_state_code()==null){
			List<OrganizationInformation> findOrgName = organizationInformationServiceImpl.findOrgName(queryParam.getTo_orgnization());
			if(findOrgName.size()==1){
				OrganizationInformation organizationInformation2 = findOrgName.get(0);
				queryParam.setTo_address(organizationInformation2.getOrg_address());
				queryParam.setTo_phone(organizationInformation2.getOrg_phone());
				queryParam.setTo_province(organizationInformation2.getOrg_province());
				queryParam.setTo_province_code(organizationInformation2.getOrg_province_code());
				queryParam.setTo_city(organizationInformation2.getOrg_city());
				queryParam.setTo_city_code(organizationInformation2.getOrg_city_code());
				queryParam.setTo_state(organizationInformation2.getOrg_state());
				queryParam.setTo_state_code(organizationInformation2.getOrg_state_code());
				queryParam.setTo_contacts(organizationInformation2.getOrg_contacts());
			}
		}
		queryParam.setProducttype_code("1");
		queryParam.setProducttype_name("顺丰标准");
		queryParam.setExpress_name("顺丰");
		queryParam.setExpressCode("SFSM");
		queryParam.setPayment("月付");
		queryParam.setNew_source(2);
		queryParam.setStatus("1");
		queryParam.setTotal_weight(new BigDecimal(1));
		Date date = new Date();
		queryParam.setCreate_time(date);
		queryParam.setUpdate_time(date);
		queryParam.setPrint_code("0");
		queryParam.setIs_docall((transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getIs_docall());
		queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
		queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
		queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));

		String result=waybillMasterServiceImpl.get_business_code();
		//System.out.println(result);
		queryParam.setOrder_id(result);
		waybillMasterServiceImpl.insertByObj(queryParam);
		WaybilDetail waybillDetail = new WaybilDetail();
		waybillDetail.setId(UUID.randomUUID().toString());
		waybillDetail.setOrder_id(queryParam.getOrder_id());
		waybillDetail.setCreate_time(date);
		waybillDetail.setUpdate_time(date);
		waybillDetail.setCreate_user(queryParam.getCreate_user());
		waybillDetail.setUpdate_user(queryParam.getCreate_user());
		waybillDetail.setQty(queryParam.getTotal_qty());
		waybillDetail.setStatus("1");
		waybilDetailService.insert(waybillDetail);
		
		obj.put("data", 0);
		obj.put("id", queryParam.getId());
		return obj;
	}

	/**
	 * 更新运单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateLogistics")
	public JSONObject updateLogistics(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setUpdate_user(temp.getName());
		Date date = new Date();
		queryParam.setFrom_orgnization_code(temp.getOrgid());
		queryParam.setUpdate_time(date);
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(org.getPay_path())){
				queryParam.setPay_path(org.getPay_path());
			}
		}
		queryParam.setStore_code(org.getStore_code());
		queryParam.setCustid(org.getCustid());
		queryParam.setPayment("月付");
		queryParam.setPrint_code("0");
		queryParam.setExpress_name(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getExpress_name());
		queryParam.setIs_docall(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getIs_docall());
		queryParam.setProducttype_name(
				(transportProductTypeService.selectByProducttype_code(queryParam.getProducttype_code(),
						queryParam.getExpressCode())).getProduct_type_name());
		if(queryParam.getFrom_province()!=null){
			queryParam.setFrom_province_code(queryParam.getFrom_province());
			queryParam.setFrom_province_code(queryParam.getFrom_province());
			queryParam.setFrom_city_code(queryParam.getFrom_city());
			queryParam.setFrom_state_code(queryParam.getFrom_state());
			queryParam.setTo_province_code(queryParam.getTo_province());
			queryParam.setTo_city_code(queryParam.getTo_city());
			queryParam.setTo_state_code(queryParam.getTo_state());
		}
		
		queryParam.setFrom_province(areaService.queryByAreaCode(queryParam.getFrom_province()));
		queryParam.setFrom_city(areaService.queryByAreaCode(queryParam.getFrom_city()));
		queryParam.setFrom_state(areaService.queryByAreaCode(queryParam.getFrom_state()));
		queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
		queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
		queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));
		String string = request.getParameter("json").toString();
		JSONArray arr = JSON.parseArray(request.getParameter("json").toString());
		waybillMasterServiceImpl.updateOrder(queryParam, arr, date);
		obj.put("data", 0);
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/cancelLogisticsupdate")
	public JSONObject cancelLogisticsupdate(WaybillMaster queryParam, HttpServletRequest request) {
		WaybillMaster waybillMaster = waybillMasterServiceImpl.selectById(queryParam.getId());
		waybillMasterBackupsService.insert(waybillMaster);
		List<WaybilDetail> listWaybilDetail = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
		waybilDetailBackupsService.insert(listWaybilDetail);
		waybilDetailService.deletByOrder_Id(waybillMaster.getOrder_id());
		waybillMasterServiceImpl.delete(queryParam.getId());
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getName());
		queryParam.setUpdate_user(temp.getName());
		UUID uuid = UUID.randomUUID();
		queryParam.setId(uuid.toString());
		queryParam.setFrom_orgnization_code(temp.getOrgid());
		Date date = new Date();
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(org.getPay_path())){
				queryParam.setPay_path(org.getPay_path());
			}
		}
		queryParam.setStore_code(org.getStore_code());
		queryParam.setFrom_orgnization(org.getOrg_name());
		queryParam.setCustid(org.getCustid());
		queryParam.setCreate_time(date);
		queryParam.setUpdate_time(date);
		queryParam.setPrint_code("0");
		queryParam.setExpress_name(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getExpress_name());
		queryParam.setIs_docall(
				(transportVenderService.selectByProducttype_code(queryParam.getExpressCode())).getIs_docall());
		queryParam.setProducttype_name(
				(transportProductTypeService.selectByProducttype_code(queryParam.getProducttype_code(),
						queryParam.getExpressCode())).getProduct_type_name());
		queryParam.setFrom_province(areaService.queryByAreaCode(queryParam.getFrom_province_code()));
		queryParam.setFrom_city(areaService.queryByAreaCode(queryParam.getFrom_city_code()));
		queryParam.setFrom_state(areaService.queryByAreaCode(queryParam.getFrom_state_code()));
		queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
		queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
		queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));
		queryParam.setStatus("1");
		queryParam.setNew_source(1);
		queryParam.setWaybill(null);
		JSONArray arr = JSON.parseArray(request.getParameter("json").toString());
		waybillMasterServiceImpl.insertOrder(queryParam, arr, date);
		obj.put("data", 0);
		return obj;
	}

	@ResponseBody
	@RequestMapping("/updateLogisticsa")
	public JSONObject updateLogisticsa(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setUpdate_user(temp.getName());
		Date date = new Date();
		OrganizationInformation organizationInformation = organizationInformationServiceImpl.selectById(temp.getOrgid());
		if(!CommonUtils.checkExistOrNot(queryParam.getPay_path())){
			if(CommonUtils.checkExistOrNot(organizationInformation.getPay_path())){
				queryParam.setPay_path(organizationInformation.getPay_path());
			}
		}
		queryParam.setStore_code(organizationInformation.getStore_code());
		queryParam.setCustid(organizationInformation.getCustid());
		queryParam.setFrom_orgnization_code(temp.getOrgid());
		queryParam.setFrom_orgnization(organizationInformation.getOrg_name());
		queryParam.setUpdate_time(date);
		queryParam.setPrint_code("0");
		if(queryParam.getTo_contacts()==null||queryParam.getTo_phone()==null||queryParam.getTo_province_code()==null||
				queryParam.getTo_address()==null||queryParam.getTo_city_code()==null||queryParam.getTo_state_code()==null){
			List<OrganizationInformation> findOrgName = organizationInformationServiceImpl.findOrgName(queryParam.getTo_orgnization());
			if(findOrgName.size()==1){
				OrganizationInformation organizationInformation2 = findOrgName.get(0);
				queryParam.setTo_address(organizationInformation2.getOrg_address());
				queryParam.setTo_phone(organizationInformation2.getOrg_phone());
				queryParam.setTo_province(organizationInformation2.getOrg_province());
				queryParam.setTo_province_code(organizationInformation2.getOrg_province_code());
				queryParam.setTo_city(organizationInformation2.getOrg_city());
				queryParam.setTo_city_code(organizationInformation2.getOrg_city_code());
				queryParam.setTo_state(organizationInformation2.getOrg_state());
				queryParam.setTo_state_code(organizationInformation2.getOrg_state_code());
				queryParam.setTo_contacts(organizationInformation2.getOrg_contacts());
			}
		}else{
			queryParam.setTo_province(areaService.queryByAreaCode(queryParam.getTo_province_code()));
			queryParam.setTo_city(areaService.queryByAreaCode(queryParam.getTo_city_code()));
			queryParam.setTo_state(areaService.queryByAreaCode(queryParam.getTo_state_code()));
		}
		WaybillMaster selectById = waybillMasterServiceImpl.selectById(queryParam.getId());
		waybillMasterServiceImpl.updateOrdera(queryParam);
		WaybilDetail waybillDetail = new WaybilDetail();
		waybillDetail.setId(UUID.randomUUID().toString());
		waybillDetail.setOrder_id(selectById.getOrder_id());
		waybillDetail.setCreate_time(date);
		waybillDetail.setUpdate_time(date);
		waybillDetail.setCreate_user(temp.getName());
		waybillDetail.setUpdate_user(temp.getName());
		waybillDetail.setQty(queryParam.getTotal_qty());
		waybillDetail.setStatus("1");
		waybilDetailService.deletById(selectById.getOrder_id());
		waybilDetailService.insert(waybillDetail);
		obj.put("data", 0);
		obj.put("id", queryParam.getId());
		return obj;
	}

	/**
	 * 跳转更新页面
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatelogistics")
	public String toUpdate(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		Account temp = SysUtil.getAccountInSession(request);
		OrganizationInformation org = organizationInformationServiceImpl.selectById(temp.getOrgid());
		request.setAttribute("org", org);
		List<TransportVender> list = transportVenderService.getAllVender(power.carrier);
		request.setAttribute("venders", list);
		List orgs1 = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		List orgs = organizationInformationServiceImpl.queryAll(queryParam.getOrg_cde());
		orgs.addAll(orgs1);
		request.setAttribute("orgs", orgs);
		Area area = new Area();
		area.setPid(1);
		List areas = areaService.findArea(area);
		request.setAttribute("areas", areas);
		List<WaybillMaster> queryOrder = waybillMasterServiceImpl.queryOrder(queryParam);
		WaybillMaster waybillMaster = queryOrder.get(0);
		List type = transportProductTypeService.findByExpresscode(waybillMaster.getExpressCode());
		request.setAttribute("type", type);
		List<InterfaceRouteinfo> interfaceRouteinfo = null;
		if (waybillMaster.getWaybill() != null) {
			interfaceRouteinfo = interfaceRouteinfoServiceImpl.selectByWaybill(waybillMaster.getWaybill());
		}
		request.setAttribute("interfaceRouteinfo", interfaceRouteinfo);
		List<WaybilDetail> queryOrderByOrderId = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
		request.setAttribute("wd", queryOrderByOrderId);
		if (waybillMaster.getTo_province_code() != null && !"".equals(waybillMaster.getTo_province_code())) {
			area.setPid(2);
			area.setArea_code(waybillMaster.getTo_province_code());
			request.setAttribute("tocity", areaService.findRecordsByP_code(area));
		}
		if (waybillMaster.getTo_city_code() != null && !"".equals(waybillMaster.getTo_city_code())) {
			area.setPid(3);
			area.setArea_code(waybillMaster.getTo_city_code());
			request.setAttribute("tostate", areaService.findRecordsByP_code(area));
		}
		if (waybillMaster.getFrom_province_code() != null && !"".equals(waybillMaster.getFrom_province_code())) {
			area.setPid(2);
			area.setArea_code(waybillMaster.getFrom_province_code());
			request.setAttribute("fromcity", areaService.findRecordsByP_code(area));
		}
		if (waybillMaster.getFrom_city_code() != null && !"".equals(waybillMaster.getFrom_city_code())) {
			area.setPid(3);
			area.setArea_code(waybillMaster.getFrom_city_code());
			request.setAttribute("fromstate", areaService.findRecordsByP_code(area));
		}
		List<PayPath> listpayPath =payPathService.queryAll();
		request.setAttribute("pay_path", listpayPath);
		List<CostCenter> listc =costCenterService.selectAll();
		request.setAttribute("cost_center", listc);
		request.setAttribute("queryParam", waybillMaster);
		if(waybillMaster.getNew_source()!=null){
			if (waybillMaster.getNew_source() == 2) {
				return "op/updatelogistics2";
			}else if(waybillMaster.getNew_source() == 3){
				return "op/updatelogistics4";
			}
		}
		
		return "op/updatelogistics";
	}

	/**
	 * 查询运单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/fastprintOrder")
	public JSONObject fastprintOrder(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		try {
			if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
				queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
			}
			List<WaybillMaster> listWaybillMaster = waybillMasterServiceImpl.selectByQuery_test(queryParam.getQuery_test(),
					queryParam.getOrg_cde(), null);
			if (listWaybillMaster.size() > 0) {
				String status = "1,2";
				List<WaybillMaster> list = waybillMasterServiceImpl.selectByQuery_test(queryParam.getQuery_test(),
						queryParam.getOrg_cde(), status);
				if (list.size() == 1) {
					WaybillMaster waybillmaster = list.get(0);
					if(waybillmaster.getStatus()=="1"||waybillmaster.getStatus().equals("1")){
						waybillMasterServiceImpl.confirmOrdersById(waybillmaster.getId(),"4");
						try {
							if (waybillmaster.getExpressCode().equals("BSB")) {
								ylInterface.createOrder(waybillmaster,
										waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
							} else if (waybillmaster.getExpressCode().equals("BSC")) {
								ylInterface.createOrder(waybillmaster,
										waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
							} else if (waybillmaster.getExpressCode().equals("SF")) {
								sfInterface.placeOrder(waybillmaster);
							} else if (waybillmaster.getExpressCode().equals("SFSM")) {
								sfInterface.placeOrder(waybillmaster);
							} else if (waybillmaster.getExpressCode().equals("RFD")) {
								rfdInterface.insertByObj(waybillmaster,
										waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
							}
						} catch (Exception e1) {
							
						}
						WaybillMaster waybillMaster2 = waybillMasterServiceImpl.selectById(waybillmaster.getId());
						String status1 = waybillMaster2.getStatus();
						if (status1 == "4" || status1.equals("4")) {
							try {
								if (waybillmaster.getExpressCode().equals("BSB")) {
									ylInterface.createOrder(waybillmaster,
											waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
								} else if (waybillmaster.getExpressCode().equals("BSC")) {
									ylInterface.createOrder(waybillmaster,
											waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
								} else if (waybillmaster.getExpressCode().equals("SF")) {
									sfInterface.placeOrder(waybillmaster);
								} else if (waybillmaster.getExpressCode().equals("SFSM")) {
									sfInterface.placeOrder(waybillmaster);
								} else if (waybillmaster.getExpressCode().equals("RFD")) {
									rfdInterface.insertByObj(waybillmaster,
											waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
								}
							} catch (Exception e) {
								
							}
							WaybillMaster waybillMaster3 = waybillMasterServiceImpl.selectById(waybillmaster.getId());
							String status2 = waybillMaster3.getStatus();
							if (status2 == "4" || status2.equals("4")) {
								obj.put("code", 4);
							} else if(status2=="1"||status2.equals("1")){
								obj.put("data", 8);
								return obj;
							} else {
								obj.put("id", waybillmaster.getId());
							}
						} else if(status1=="1"||status1.equals("1")){
							obj.put("data", 8);
							return obj;
						} else {
							obj.put("id", waybillmaster.getId());
						}
					}else{
						if(waybillmaster.getPrint_code() == "0" || waybillmaster.getPrint_code().equals("0")){
							obj.put("id", waybillmaster.getId());
						}else{
							obj.put("data", 3);
							obj.put("id", waybillmaster.getId());
							return obj;
						}
					}
				} else if (list.size() == 0) {
					obj.put("data", 7);
					return obj;
				} else {
					obj.put("data", 6);
					return obj;
				}
			} else if (listWaybillMaster.size() == 0) {
				obj.put("data", 5);
				return obj;
			} 
			obj.put("data", 1);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;

	}

	@RequestMapping("/fastprintOrderByquery_test")
	public String fastprintOrderByquery_test(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		if (queryParam.getQuery_test() != null && queryParam.getQuery_test() != "") {
			queryParam.setQuery_test(queryParam.getQuery_test().replaceAll(" ", ""));
		}
		String status = "1,2";
		List<WaybillMaster> list = waybillMasterServiceImpl.selectByQuery_test(queryParam.getQuery_test(),
				queryParam.getOrg_cde(), status);
		request.setAttribute("list", list);
		return "op/fastprintOrder_page";
	}

	@ResponseBody
	@RequestMapping("/confirmfastprintbyId")
	public JSONObject confirmfastprintbyId(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(queryParam.getId());
		try {
			if(waybillmaster.getStatus() == "1" || waybillmaster.getStatus().equals("1")){
				waybillMasterServiceImpl.confirmOrdersById(waybillmaster.getId(),"4");
				if (waybillmaster.getExpressCode().equals("BSB")) {
					ylInterface.createOrder(waybillmaster,
							waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
				} else if (waybillmaster.getExpressCode().equals("BSC")) {
					ylInterface.createOrder(waybillmaster,
							waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
				} else if (waybillmaster.getExpressCode().equals("SF")) {
					sfInterface.placeOrder(waybillmaster);
				} else if (waybillmaster.getExpressCode().equals("SFSM")) {
					sfInterface.placeOrder(waybillmaster);
				} else if (waybillmaster.getExpressCode().equals("RFD")) {
					rfdInterface.insertByObj(waybillmaster,
							waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
				}
				WaybillMaster waybillMaster2 = waybillMasterServiceImpl.selectById(waybillmaster.getId());
				String status1 = waybillMaster2.getStatus();
				if (status1 == "4" || status1.equals("4")) {
					if (waybillmaster.getExpressCode().equals("BSB")) {
						ylInterface.createOrder(waybillmaster,
								waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
					} else if (waybillmaster.getExpressCode().equals("BSC")) {
						ylInterface.createOrder(waybillmaster,
								waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
					} else if (waybillmaster.getExpressCode().equals("SF")) {
						sfInterface.placeOrder(waybillmaster);
					} else if (waybillmaster.getExpressCode().equals("SFSM")) {
						sfInterface.placeOrder(waybillmaster);
					} else if (waybillmaster.getExpressCode().equals("RFD")) {
						rfdInterface.insertByObj(waybillmaster,
								waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()));
					}
					WaybillMaster waybillMaster3 = waybillMasterServiceImpl.selectById(waybillmaster.getId());
					String status2 = waybillMaster3.getStatus();
					if (status2 == "4" || status2.equals("4")) {
						obj.put("code", 4);
					} else if(status2=="1"||status2.equals("1")){
						obj.put("data", 8);
						return obj;
					} else {
						obj.put("id", waybillmaster.getId());
					}
				}else if( status1=="1" || status1.equals("1")){
					obj.put("data", 8);
					return obj;
				} else {
					obj.put("id", waybillmaster.getId());
				}
			}else{
				if(waybillmaster.getPrint_code()!="0" && !waybillmaster.getPrint_code().equals("0")){
					obj.put("id", waybillmaster.getId());
					obj.put("code", 5);
				}else{
					obj.put("id", waybillmaster.getId());
				}
			}
			obj.put("data", 1);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;

	}

	@RequestMapping("/fastprintbyId")
	public String fastprintbyId(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(queryParam.getId());
		byte[] b = OneBarcodeUtil.createBarcodeDefault(waybillmaster.getWaybill());
		OneBarcodeUtil.byte2image(b, BaseConst.file_path + waybillmaster.getWaybill() + ".png");
		waybillmaster.setCustid("****"+waybillmaster.getCustid().substring(6));
		if(waybillmaster.getTo_contacts().length()>26){
			waybillmaster.setTo_contacts(waybillmaster.getTo_contacts().substring(0, 25));
		}
		request.setAttribute("queryParam", waybillmaster);
		waybillmaster.setPrint_code("1");
		Date date = new Date();
		waybillmaster.setPrint_time(date);
		waybillMasterServiceImpl.setPrint_code(waybillmaster);
		if (waybillmaster.getExpressCode().equals("SF") || waybillmaster.getExpressCode().equals("SFSM")) {
			List<WaybilDetail> list = waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id());
			request.setAttribute("list", list);
			return "op/sfprint";
			//return "print/JQprint";
		}
		return "op/print";

	}

	/**
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/waybiluploade")
	public String waybilupload(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		return "op/waybiluploade";
	}

	@RequestMapping("/fastprint")
	public String fastprint(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		return "op/fastprint";
	}

	/**
	 * 打印运单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping("/print")
	public String print(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		String id = request.getParameter("ids");
		
		/*if(1==1){
		    return "redirect:/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids="+id;
		}*/
		
		WaybillMaster waybillMaster = waybillMasterServiceImpl.selectById(id);
		byte[] b = OneBarcodeUtil.createBarcodeDefault(waybillMaster.getWaybill());
		OneBarcodeUtil.byte2image(b, BaseConst.file_path + waybillMaster.getWaybill() + ".png");
		byte[] b1 = OneBarcodeUtil.createBarcodeDefault(waybillMaster.getCustomer_number());
		OneBarcodeUtil.byte2image(b1, BaseConst.file_path + waybillMaster.getCustomer_number() + ".png");
		waybillMaster.setCustid("****"+waybillMaster.getCustid().substring(6));
		if(waybillMaster.getTo_contacts().length()>26){
			waybillMaster.setTo_contacts(waybillMaster.getTo_contacts().substring(0, 25));
		}
		request.setAttribute("queryParam", waybillMaster);
		waybillMaster.setPrint_code("1");
		Date date = new Date();
		waybillMaster.setPrint_time(date);
		waybillMasterServiceImpl.setPrint_code(waybillMaster);
		if (waybillMaster.getExpressCode().equals("SF") || waybillMaster.getExpressCode().equals("SFSM")) {
			List<WaybilDetail> list = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
			request.setAttribute("list", list);
			return "op/sfprint1015";
		}
		return "op/print";
	}

	@ResponseBody
	@RequestMapping("/printOrder")
	public JSONObject printOrder(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		try {
			String id = request.getParameter("ids");
			WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			String print_code = waybillmaster.getPrint_code();
			if (print_code != "0" && !print_code.equals("0")) {
				obj.put("code", 4);
			}
			if (waybillmaster.getStatus().equals("1")||waybillmaster.getStatus().equals("0")) {
				obj.put("data", 0);
				return obj;
			} else if (waybillmaster.getExpressCode().equals("BSC")) {
				obj.put("data", 3);
				return obj;
			} else if (waybillmaster.getStatus().equals("10")) {
				obj.put("data", 5);
				return obj;
			} else if (waybillmaster.getStatus().equals("11")) {
				obj.put("data", 6);
				return obj;
			} else if (waybillmaster.getStatus().equals("4")) {
				obj.put("data", 7);
				return obj;
			}else if (waybillmaster.getStatus().equals("5")||waybillmaster.getStatus().equals("2")) {
				obj.put("data", 1);
				return obj;
			}else if (waybillmaster.getStatus().equals("6")) {
				obj.put("data", 8);
				return obj;
			}else if (waybillmaster.getStatus().equals("7")) {
				obj.put("data", 9);
				return obj;
			}else if (waybillmaster.getStatus().equals("8")) {
				obj.put("data", 10);
				return obj;
			}else if (waybillmaster.getStatus().equals("9")) {
				obj.put("data", 11);
				return obj;
			}
			obj.put("data", 1);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;

	}
	@ResponseBody
	@RequestMapping("/batchprintPdf")
	public JSONObject batchprintPdf(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		try {
			String ids = request.getParameter("ids");
			String[] split = ids.split(";");
			if(split==null||split.length==0){
			    obj.put("data", 2);
			    return obj;
			}
			
			WaybillMaster waybillmaster=null;
			int flag=0;
			for (String id : split) {
				waybillmaster = waybillMasterServiceImpl.selectById(id);
				if(CommonUtils.checkExistOrNot(waybillmaster)){
					if(CommonUtils.checkExistOrNot(waybillmaster.getStatus())){
						if(!waybillmaster.getStatus().equals("5")){
							if(!waybillmaster.getStatus().equals("2")){
								flag=1;
							}
						}
					}
				}
			}
			if(flag==0){
				obj.put("data", 1);
				return obj;
			}
			//WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			/*String print_code = waybillmaster.getPrint_code();
			if (print_code != "0" && !print_code.equals("0")) {
				obj.put("code", 4);
			}
			if (waybillmaster.getStatus().equals("1")||waybillmaster.getStatus().equals("0")) {
				obj.put("data", 0);
				return obj;
			} else if (waybillmaster.getExpressCode().equals("BSC")) {
				obj.put("data", 3);
				return obj;
			} else if (waybillmaster.getStatus().equals("10")) {
				obj.put("data", 5);
				return obj;
			} else if (waybillmaster.getStatus().equals("11")) {
				obj.put("data", 6);
				return obj;
			} else if (waybillmaster.getStatus().equals("4")) {
				obj.put("data", 7);
				return obj;
			}else if (waybillmaster.getStatus().equals("5")||waybillmaster.getStatus().equals("2")) {
				obj.put("data", 1);
				return obj;
			}else if (waybillmaster.getStatus().equals("6")) {
				obj.put("data", 8);
				return obj;
			}else if (waybillmaster.getStatus().equals("7")) {
				obj.put("data", 9);
				return obj;
			}else if (waybillmaster.getStatus().equals("8")) {
				obj.put("data", 10);
				return obj;
			}else if (waybillmaster.getStatus().equals("9")) {
				obj.put("data", 11);
				return obj;
			}*/
			obj.put("data", 3);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;
		
	}

	/**
	 * 运单作废
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelOrder")
	public JSONObject CancelOrder(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		List<String> list = new ArrayList<String>();
		for (String id : split) {
			WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			if (!waybillmaster.getStatus().equals("1")) {
				obj.put("data", 0);
				return obj;
			}
			list.add(waybillmaster.getOrder_id());
		}
		try {
			WaybillMaster waybillmaster = new WaybillMaster();
			waybillmaster.setStatus("0");
			waybillMasterServiceImpl.updatestatus(list, waybillmaster);
			obj.put("data", 1);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;

	}

	/**
	 * 确认下单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmOrders")
	public JSONObject confirmOrders(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		logger.debug("confirmOrders");
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		List<WaybillMaster> list = new ArrayList<WaybillMaster>();
		List<WaybillMaster> clist = new ArrayList<WaybillMaster>();
		List<WaybillMaster> sflist = new ArrayList<WaybillMaster>();
		List<WaybillMaster> rfdlist = new ArrayList<WaybillMaster>();
		List<WaybillMaster> ztolist = new ArrayList<WaybillMaster>();
		for (String id : split) {
			WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			if (!waybillmaster.getStatus().equals("1")&&!waybillmaster.getStatus().equals("2")) {
				obj.put("data", 0);
				return obj;
			}else if(waybillmaster.getStatus().equals("2")){
				obj.put("data", 1);
				return obj;
			}
			String stauts = "4";
			waybillMasterServiceImpl.confirmOrdersById(id, stauts);
			if (waybillmaster.getExpressCode().equals("BSB")) {
				list.add(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("BSC")) {
				list.add(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("SF")) {
				sflist.add(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("SFSM")) {
				sflist.add(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("RFD")) {
				rfdlist.add(waybillmaster);
			}else if (waybillmaster.getExpressCode().equals("ZTO")) {
				ztolist.add(waybillmaster);
			}

		}
		try {
			for (int i = 0; i < list.size(); i++) {
				WaybillMaster waybillMaster = list.get(i);
				ylInterface.createOrder(waybillMaster,
						waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()));
			}
			for (int i = 0; i < clist.size(); i++) {
				WaybillMaster waybillMaster = clist.get(i);
				ylInterface.insertByObj(waybillMaster);
			}
			for (int i = 0; i < sflist.size(); i++) {
				WaybillMaster waybillMaster = sflist.get(i);
				sfInterface.placeOrder(waybillMaster);
			}
			for (int i = 0; i < rfdlist.size(); i++) {
				WaybillMaster waybillMaster = rfdlist.get(i);
				rfdInterface.insertByObj(waybillMaster,
						waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()));
			}
			for (int i = 0; i < ztolist.size(); i++) {
				WaybillMaster waybillMaster = ztolist.get(i);
				zTOInterface.createOrder(waybillMaster,
						waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()),0);
			}
			obj.put("data", 1);
		} catch (Exception e) {
			logger.error("------confirmOrders",e);
			obj.put("data", 2);
		}
		return obj;
	}

	/**
	 * 取消下单
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelOrderLogistics")
	public JSONObject cancelOrderLogistics(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		logger.debug("cancelOrderLogistics");
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		String id = split[0];
		WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
		if (waybillmaster.getStatus().equals("1")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("0")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("7")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("8")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("9")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("4")) {
			obj.put("data", 0);
			return obj;
		}
		if (waybillmaster.getStatus().equals("10")) {
			obj.put("data", 0);
			return obj;
		}
		try {
			if (waybillmaster.getExpressCode().equals("BSB")) {
				ylInterface.cancelOrder("BS", waybillmaster.getWaybill(), waybillmaster.getWaybill(), 1,
						waybillmaster.getOrder_id(), waybillmaster.getStatus());
			} else if (waybillmaster.getExpressCode().equals("BSC")) {
				ylInterface.cancelOrder("BS", waybillmaster.getWaybill(), waybillmaster.getWaybill(), 1,
						waybillmaster.getOrder_id(), waybillmaster.getStatus());
			} else if (waybillmaster.getExpressCode().equals("SF")) {
				sfInterface.CancelOrder(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("SFSM")) {
				sfInterface.CancelOrder(waybillmaster);
			} else if (waybillmaster.getExpressCode().equals("RFD")) {

			}else if (waybillmaster.getExpressCode().equals("ZTO")) {
				zTOInterface.createOrder(waybillmaster,
						waybilDetailService.queryOrderByOrderId(waybillmaster.getOrder_id()),2);
			}
			obj.put("data", 1);
		} catch (Exception e) {
			waybillMasterServiceImpl.confirmOrdersById(id, waybillmaster.getStatus());
			logger.error("------confirmOrders",e);
			obj.put("data", 2);
		}
		return obj;
	}

	/**
	 * 订单恢复
	 * 
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/recoveryOrders")
	public JSONObject recoveryOrders(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		List<String> list = new ArrayList<String>();
		for (String id : split) {
			WaybillMaster waybillmaster = waybillMasterServiceImpl.selectById(id);
			if (!waybillmaster.getStatus().equals("0")) {
				obj.put("data", 0);
				return obj;
			}
			list.add(waybillmaster.getOrder_id());
		}
		try {
			WaybillMaster waybillmaster = new WaybillMaster();
			waybillmaster.setStatus("1");
			waybillMasterServiceImpl.updatestatus(list, waybillmaster);
			obj.put("data", 1);
		} catch (Exception e) {
			obj.put("data", 2);
		}
		return obj;

	}

	/**
	 * 订单导出
	 * 
	 * @param queryParam
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/exportwmd")
	public void exportwmd(WaybillMasterQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason", "错误");
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		List<Map<String, Object>> list = waybillMasterServiceImpl.selectByQuery(queryParam);
		try {
			out = response.getWriter();
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("create_time", "制单日期");
			cMap.put("status", "运单状态");
			cMap.put("order_id", "运单号");
			cMap.put("customer_number", "客户单号");
			cMap.put("from_orgnization", "发货机构");
			cMap.put("express_name", "快递公司");
			cMap.put("producttype_name", "快递业务类型");
			cMap.put("waybill", "快递单号");
			cMap.put("pay_path", "支付方式");
			cMap.put("memo", "备注");
			cMap.put("amount_flag", "是否保价");
			cMap.put("insured", "保价金额");
			cMap.put("cost_center", "成本中心");
			cMap.put("from_contacts", "发货联系人");
			cMap.put("from_phone", "发货联系电话");
			cMap.put("from_address", "发货地址");
			cMap.put("to_orgnization", "收货机构");
			cMap.put("to_province", "省");
			cMap.put("to_city", "市");
			cMap.put("to_state", "区");
			cMap.put("to_street", "街道");
			cMap.put("to_contacts", "收货联系人");
			cMap.put("to_phone", "收货联系电话");
			cMap.put("to_address", "收货地址");
			cMap.put("total_qty", "总件数");
			cMap.put("total_weight", "总毛重");
			cMap.put("total_volumn", "总体积");
			cMap.put("total_amount", "总金额");
			cMap.put("order_time", "下单日期");
			Date time = new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			String string = date.format(time);
			BigExcelExport.excelDownLoadDatab(list, cMap, string + "运单信息表.xlsx");
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
			result.put("path", CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + string + "运单信息表.xlsx");
		} catch (Exception e) {
		}
		out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	/**
	 * 订单导入
	 * 
	 * @param file
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public synchronized JSONObject upload(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// 获取当前操作用户
		BusinessPower power = SysUtil.getPowerSession(req);
		Account user = SysUtil.getAccountInSession(req);
		// 返回值
		JSONObject result = new JSONObject();
		// 将上传文件写入本地
		// 文件不为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = CommonUtil.getAllMessage("config",
						"BALANCE_UPLOAD_WAYBILLMASTER_" + OSinfo.getOSname())
						+ new StringBuffer(file.getOriginalFilename()).insert(file.getOriginalFilename().indexOf("."),
								"_" + user.getCreateUser() + "_"
										+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				// 校验本地文件是否已存在
				// 如果有重名文件存在，就删除文件
				// 这个对象对应的硬盘必须删不能存在，如果已经存在则会抛出IOException异常
				FileUtil.isExistFile(filePath);
				// 转存文件，写入硬盘
				// 这个本质还是一样的打开流传文件， 需要注意 file对应的硬盘中的文件不能存在 需要删除，
				// 否则会抛出文件已经存在且不能删除异常
				file.transferTo(new File(filePath));
				// 保存EXCEL数据
				List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 27);
				List<WaybilMasterDetail> wbd_list = new ArrayList<WaybilMasterDetail>();
				List<WaybilMasterDetail> wbd_list1 = new ArrayList<WaybilMasterDetail>();
				list.remove(list.get(0));
				String flag = "0";
				String uuid = UUID.randomUUID().toString();
				if (list == null || list.isEmpty() || list.size() == 0) {
					result.put("result_code", "FIE");
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
					return result;
				}
				OrganizationInformation org = organizationInformationServiceImpl.selectById(user.getOrgid());

				for (int i = 0; i < list.size(); i++) {
					WaybilMasterDetail wbd = new WaybilMasterDetail(list.get(i));
					Date date = new Date();
					wbd.setFrom_orgnization_code(user.getOrgid());
					List<OrganizationInformation> findOrgName = new ArrayList<OrganizationInformation>();
					if(wbd.getTo_orgnization()!=null && wbd.getTo_orgnization()!=""){
						findOrgName = organizationInformationServiceImpl.findOrgName(wbd.getTo_orgnization());
					}
					if(wbd.getTo_phone()==null && findOrgName.size()>0){
						wbd.setTo_phone(findOrgName.get(0).getOrg_phone());
					}
					if (wbd.getTo_contacts()==null && findOrgName.size()>0) {
						wbd.setTo_contacts(findOrgName.get(0).getOrg_contacts());
					}
					if (wbd.getFrom_contacts()==null) {
						wbd.setFrom_contacts(org.getOrg_contacts());
					}
					if(wbd.getFrom_phone()==null){
						wbd.setFrom_phone(org.getOrg_phone());
					}
					wbd.setCustid(org.getCustid());
					wbd.setFrom_orgnization(org.getOrg_name());
					if (wbd.getCustomer_number() == null) {
						if (wbd.getTo_province() != null && wbd.getTo_province() != "" && wbd.getTo_city() != null
								&& wbd.getTo_city() != "" && wbd.getTo_state() != null && wbd.getTo_state() != "") {
			              
						    /********2018-03-20*******/ 
                            ViewArea viewArea = new ViewArea();
                            viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
                            viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
                            viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
                            //viewAreaService.selectByViewArea(viewArea);
                            List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
                             /********2018-03-20*******/ 
							if(list2.size()==1){
								wbd.setTo_province_code(list2.get(0).getP_code());
								wbd.setTo_city_code(list2.get(0).getS_code());
								wbd.setTo_state_code(list2.get(0).getQ_code());
								wbd.setTo_city(list2.get(0).getS_name());
								wbd.setTo_province(list2.get(0).getP_name());
								wbd.setTo_state(list2.get(0).getQ_name());
							}
							else {
								wbd.setRemark(wbd.getRemark() + "收货城市逻辑错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}else if(wbd.getTo_province() == null && wbd.getTo_city() == null && wbd.getTo_state() == null && wbd.getTo_address() ==null && findOrgName.size()>0){
							wbd.setTo_province_code(findOrgName.get(0).getOrg_province_code());
							wbd.setTo_city_code(findOrgName.get(0).getOrg_city_code());
							wbd.setTo_state_code(findOrgName.get(0).getOrg_state_code());
							wbd.setTo_city(findOrgName.get(0).getOrg_city());
							wbd.setTo_province(findOrgName.get(0).getOrg_province());
							wbd.setTo_state(findOrgName.get(0).getOrg_state());
							wbd.setTo_address(findOrgName.get(0).getOrg_address());
						}else {
							wbd.setRemark(wbd.getRemark() + "收货城市信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						if (wbd.getFrom_province() == null && wbd.getFrom_city() == null && wbd.getFrom_state() == null && wbd.getFrom_address() == null) {
							wbd.setFrom_province_code(org.getOrg_province_code());
							wbd.setFrom_city_code(org.getOrg_city_code());
							wbd.setFrom_state_code(org.getOrg_state_code());
							wbd.setFrom_city(org.getOrg_city());
							wbd.setFrom_province(org.getOrg_province());
							wbd.setFrom_state(org.getOrg_state());
							wbd.setFrom_address(org.getOrg_address());
						} else {
							if (wbd.getFrom_province() != null && wbd.getFrom_province() != ""
									&& wbd.getFrom_city() != null && wbd.getFrom_city() != ""
									&& wbd.getFrom_state() != null && wbd.getFrom_state() != "") {
								
								
				                  /********2018-03-20*******/ 
	                            ViewArea viewArea = new ViewArea();
	                            viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
	                            viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
	                            viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
	                            //viewAreaService.selectByViewArea(viewArea);
	                            List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
	                             /********2018-03-20*******/ 
								
				
								if (list2.size()==1) {
									wbd.setFrom_province_code(list2.get(0).getP_code());
									wbd.setFrom_city_code(list2.get(0).getS_code());
									wbd.setFrom_state_code(list2.get(0).getQ_code());
									wbd.setFrom_city(list2.get(0).getS_name());
									wbd.setFrom_province(list2.get(0).getP_name());
									wbd.setFrom_state(list2.get(0).getQ_name());
								} else {
									wbd.setRemark(wbd.getRemark() + "发货城市逻辑错误");
									wbd.setFlag("2");
									flag = "2";
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "发货城市信息缺失错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}
						if (wbd.getExpress_name() != null) {
							TransportVender transportVender = transportVenderService
									.selectByProducttype_name(wbd.getExpress_name(), power.carrier);
							if (transportVender != null) {
								wbd.setExpress_code(transportVender.getExpress_code());
								wbd.setIs_docall(transportVender.getIs_docall());
								if (wbd.getPackage_name() != null) {
									TransportProductType transportProductType = transportProductTypeService
											.selectByProducttype_name(wbd.getExpress_code(), wbd.getProducttype_name(),
													power.getCarrier_type());
									if (transportProductType != null) {
										wbd.setProducttype_code(transportProductType.getProduct_type_code());
									} else {
										wbd.setRemark(wbd.getRemark() + "快递信息逻辑错误");
										wbd.setFlag("2");
										flag = "2";
									}
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "快递公司信息错误");
								wbd.setFlag("2");
								flag = "2";
							}
						} else {
							wbd.setRemark(wbd.getRemark() + "快递公司信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						wbd.setCreate_time(date);
						wbd.setUpdate_time(date);
						wbd.setCreate_user(user.getCreateUser());
						wbd.setUpdate_user(user.getCreateUser());
						wbd.setBat_id(uuid);
						String id = UUID.randomUUID().toString();
						wbd.setId(id);
						if (wbd.getRemark() != null) {
							String text = "第" + (i + 2) + "行;" + wbd.getRemark();
							wbd.setRemark(text);
						} else {
							String text = "第" + (i + 2) + "行";
							wbd.setRemark(text);
						}
						if (wbd.getExpress_name() == null || wbd.getExpress_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递公司不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getProducttype_name() == null || wbd.getProducttype_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递业务类型不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_address() == null || wbd.getTo_address() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-地址不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_state() == null || wbd.getTo_state() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-区不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_city() == null || wbd.getTo_city() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-市不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_province() == null || wbd.getTo_province() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-省不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_phone() == null || wbd.getTo_phone() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人电话不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_contacts() == null || wbd.getTo_contacts() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getQty() == null || wbd.getQty() == 0) {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "货物件数不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getFlag() == null) {
							wbd.setFlag("0");
						}
						
				         /*****20180320****/
                        wbd.setOrdinal(i);
                        /*****20180320****/
						
						wbd_list1.add(wbd);
					} else {
						if (wbd.getTo_province() != null && wbd.getTo_province() != "" && wbd.getTo_city() != null
								&& wbd.getTo_city() != "" && wbd.getTo_state() != null && wbd.getTo_state() != "") {
			                 
						    /********2018-03-20*******/ 
                            ViewArea viewArea = new ViewArea();
                            viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
                            viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
                            viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
                            //viewAreaService.selectByViewArea(viewArea);
                            List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
                             /********2018-03-20*******/ 
							
                            if(list2.size()==1){
								wbd.setTo_province_code(list2.get(0).getP_code());
								wbd.setTo_city_code(list2.get(0).getS_code());
								wbd.setTo_state_code(list2.get(0).getQ_code());
								wbd.setTo_city(list2.get(0).getS_name());
								wbd.setTo_province(list2.get(0).getP_name());
								wbd.setTo_state(list2.get(0).getQ_name());
							}else {
								wbd.setRemark(wbd.getRemark() + "收货城市逻辑错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}else if(wbd.getTo_province() == null && wbd.getTo_city() == null && wbd.getTo_state() == null && wbd.getTo_address() ==null && findOrgName.size()>0){
							wbd.setTo_province_code(findOrgName.get(0).getOrg_province_code());
							wbd.setTo_city_code(findOrgName.get(0).getOrg_city_code());
							wbd.setTo_state_code(findOrgName.get(0).getOrg_state_code());
							wbd.setTo_city(findOrgName.get(0).getOrg_city());
							wbd.setTo_province(findOrgName.get(0).getOrg_province());
							wbd.setTo_state(findOrgName.get(0).getOrg_state());
							wbd.setTo_address(findOrgName.get(0).getOrg_address());
						}else {
							wbd.setRemark(wbd.getRemark() + "收货城市信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						if (wbd.getFrom_province() == null && wbd.getFrom_city() == null && wbd.getFrom_state() == null && wbd.getFrom_address() == null) {
							wbd.setFrom_province_code(org.getOrg_province_code());
							wbd.setFrom_city_code(org.getOrg_city_code());
							wbd.setFrom_state_code(org.getOrg_state_code());
							wbd.setFrom_city(org.getOrg_city());
							wbd.setFrom_province(org.getOrg_province());
							wbd.setFrom_state(org.getOrg_state());
							wbd.setFrom_address(org.getOrg_address());
						} else {
							if (wbd.getFrom_province() != null && wbd.getFrom_province() != ""
									&& wbd.getFrom_city() != null && wbd.getFrom_city() != ""
									&& wbd.getFrom_state() != null && wbd.getFrom_state() != "") {
				             
							    
							    /********2018-03-20*******/ 
	                            ViewArea viewArea = new ViewArea();
	                            viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
	                            viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
	                            viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
	                            //viewAreaService.selectByViewArea(viewArea);
	                            List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
	                             /********2018-03-20*******/ 
								
	                            if(list2.size()==1){
									wbd.setFrom_province_code(list2.get(0).getP_code());
									wbd.setFrom_city_code(list2.get(0).getS_code());
									wbd.setFrom_state_code(list2.get(0).getQ_code());
									wbd.setFrom_city(list2.get(0).getS_name());
									wbd.setFrom_province(list2.get(0).getP_name());
									wbd.setFrom_state(list2.get(0).getQ_name());
								} else {
									wbd.setRemark(wbd.getRemark() + "发货城市逻辑错误");
									wbd.setFlag("2");
									flag = "2";
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "收货城市信息缺失错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}
						if (wbd.getExpress_name() != null) {
							TransportVender transportVender = transportVenderService
									.selectByProducttype_name(wbd.getExpress_name(), power.carrier);
							if (transportVender != null) {
								wbd.setExpress_code(transportVender.getExpress_code());
								if (wbd.getPackage_name() != null) {
									TransportProductType transportProductType = transportProductTypeService
											.selectByProducttype_name(wbd.getExpress_code(), wbd.getProducttype_name(),
													power.getCarrier_type());
									if (transportProductType != null) {
										wbd.setProducttype_code(transportProductType.getProduct_type_code());
									} else {
										wbd.setRemark(wbd.getRemark() + "快递信息逻辑错误");
										wbd.setFlag("2");
										flag = "2";
									}
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "快递公司信息错误");
								wbd.setFlag("2");
								flag = "2";
							}
						} else {
							wbd.setRemark(wbd.getRemark() + "快递公司信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						wbd.setCreate_time(date);
						wbd.setUpdate_time(date);
						wbd.setCreate_user(user.getCreateUser());
						wbd.setUpdate_user(user.getCreateUser());
						wbd.setBat_id(uuid);
						String id = UUID.randomUUID().toString();
						wbd.setId(id);
						if (wbd.getRemark() != null) {
							String text = "第" + (i + 2) + "行;" + wbd.getRemark();
							wbd.setRemark(text);
						} else {
							String text = "第" + (i + 2) + "行";
							wbd.setRemark(text);
						}
						if (wbd.getExpress_name() == null || wbd.getExpress_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递公司不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getProducttype_name() == null || wbd.getProducttype_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递业务类型不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_address() == null || wbd.getTo_address() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-地址不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_state() == null || wbd.getTo_state() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-区不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_city() == null || wbd.getTo_city() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-市不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_province() == null || wbd.getTo_province() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-省不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_phone() == null || wbd.getTo_phone() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人电话不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_contacts() == null || wbd.getTo_contacts() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getQty() == null || wbd.getQty() == 0) {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "货物件数不能为空";
							wbd.setRemark(remark);
						}
						WaybillMasterDetail queryByCustomerNum = waybillMasterServiceImpl
								.queryByCustomerNum(wbd.getCustomer_number());
						if (queryByCustomerNum != null) {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "客户单号已存在";
							wbd.setRemark(remark);
						}
						if (wbd.getFlag() == null) {
							wbd.setFlag("0");
						}
						
						 /*****20180320****/
                        wbd.setOrdinal(i);
                        /*****20180320****/
						wbd.setStore_code(org.getStore_code());
						wbd_list.add(wbd);
					}
				}
				ExpressinfoMasterInputlist expressinfoMasterInputlist = new ExpressinfoMasterInputlist();
				expressinfoMasterInputlist.setCreate_time(new Date());
				expressinfoMasterInputlist.setBat_id(uuid);
				expressinfoMasterInputlist.setCreate_user(user.getOrgid());
				expressinfoMasterInputlist.setUpdate_time(new Date());
				expressinfoMasterInputlist.setUpddate_user(user.getCreateUser());
				expressinfoMasterInputlist.setTotal_num(wbd_list.size() + wbd_list1.size());
				expressinfoMasterInputlist.setFlag(Integer.parseInt(flag));
				expressinfoMasterInputlist.setFail_num(0);
				expressinfoMasterInputlist.setSuccess_num(0);
				expressinfoMasterInputlistService.insertExpressinfoMasterInputlist(expressinfoMasterInputlist);

				waybillMasterServiceImpl.insertWayBilMasterDetail(wbd_list);
				waybillMasterServiceImpl.insertWayBilMasterDetail(wbd_list1);
				if (flag == "2" || flag.equals("2")) {
					result.put("result_code", "ULF");
					result.put("result_content", "上传文件有误！");
					return result;
				}
				Integer flag1 = 3;
				waybilMasterDetailService.updateByBatId(uuid, flag1);
				expressinfoMasterInputlistService.updateByBatId(uuid, flag1);
				try {
					List<WaybilMasterDetail> waybilMasterDetail1 = waybilMasterDetailService
							.selectByBatIdAndCustomer(uuid);
					for (WaybilMasterDetail waybilMasterDetail : waybilMasterDetail1) {
						WaybillMasterDetail queryByCustomerNum = waybillMasterServiceImpl
								.queryByCustomerNum(waybilMasterDetail.getCustomer_number());
						if (queryByCustomerNum == null) {
							waybillMasterServiceImpl.insetWaybilMasterDetailByCustomer(waybilMasterDetail);
						} else {
							Integer flag2 = 0;
							waybilMasterDetailService.updateByBatId(uuid, flag2);
							expressinfoMasterInputlistService.updateByBatId(uuid, flag2);
							result.put("result_code", "ULF");
							result.put("result_content", "上传文件有误！");
							return result;
						}
					}
					List<WaybilMasterDetail> waybilMasterDetail = waybilMasterDetailService.selectByBatId(uuid);
					for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetail) {
						waybillMasterServiceImpl.insetByWaybilMasterDetail(waybilMasterDetail2);
					}
					result.put("result_code", "ULF");
					result.put("result_content", "上传文件有误！");
					Integer flag2 = 4;
					waybilMasterDetailService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatIdAndSuccess(uuid,
							expressinfoMasterInputlist.getTotal_num());
				} catch (Exception e) {
					Integer flag2 = 0;
					waybilMasterDetailService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatId(uuid, flag2);
					result.put("result_code", "ULF");
					result.put("result_content", "上传文件有误！");
				}
				result.put("result_code", "SUCCESS");
				result.put("success_records", list.size());
			} catch (Exception e) {
				logger.error(e);
				result.put("result_code", "ULF");
				if (e instanceof NoSuchMethodException) {
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");

				} else {
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");

				}

			}

		} else {
			// file is empty-文件为空
			result.put("result_code", "FIE");
		}
		return result;

	}
	@ResponseBody
	@RequestMapping(value = "/baozunupload")
	public synchronized JSONObject baozunupload(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// 获取当前操作用户
		BusinessPower power = SysUtil.getPowerSession(req);
		Account user = SysUtil.getAccountInSession(req);
		// 返回值
		JSONObject result = new JSONObject();
		// 将上传文件写入本地
		// 文件不为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				String filePath = CommonUtil.getAllMessage("config",
						"BALANCE_UPLOAD_WAYBILLMASTER_" + OSinfo.getOSname())
						+ new StringBuffer(file.getOriginalFilename()).insert(file.getOriginalFilename().indexOf("."),
								"_" + user.getCreateUser() + "_"
										+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				// 校验本地文件是否已存在
				// 如果有重名文件存在，就删除文件
				// 这个对象对应的硬盘必须删不能存在，如果已经存在则会抛出IOException异常
				FileUtil.isExistFile(filePath);
				// 转存文件，写入硬盘
				// 这个本质还是一样的打开流传文件， 需要注意 file对应的硬盘中的文件不能存在 需要删除，
				// 否则会抛出文件已经存在且不能删除异常
				file.transferTo(new File(filePath));
				// 保存EXCEL数据
				List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 27);
				//List<WaybilMasterDetail> wbd_list = new ArrayList<WaybilMasterDetail>();
				List<BaoZunWaybilMasterDetail> wbd_list1 = new ArrayList<BaoZunWaybilMasterDetail>();
				list.remove(list.get(0));
				list.remove(list.get(0));
				String flag = "0";
				String uuid = UUID.randomUUID().toString();
				if (list == null || list.isEmpty() || list.size() == 0) {
					result.put("result_code", "FIE");
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
					return result;
				}
				OrganizationInformation org = organizationInformationServiceImpl.selectById(user.getOrgid());
				
				for (int i = 0; i < list.size(); i++) {
					BaoZunWaybilMasterDetail wbd = new BaoZunWaybilMasterDetail(list.get(i));
					flag = wbd.getFlag();
					Date date = new Date();
					wbd.setFrom_orgnization_code(user.getOrgid());
					wbd.setStore_code(org.getStore_code());
					
					if(wbd.getPay_path()!=null){
                        PayPath payPath =payPathService.selectByname(wbd.getPay_path());
                        if(CommonUtils.checkExistOrNot(payPath)){
                            wbd.setPay_path(payPath.getPay_path());
                        }else{
                            wbd.setRemark(wbd.getRemark() + "支付方式错误");
                            wbd.setFlag("2");
                            flag = "2";
                        }
                    }else{
                        if(CommonUtils.checkExistOrNot(org.getPay_path())){
                            wbd.setPay_path(org.getPay_path());
                        }else{
                            wbd.setRemark(wbd.getRemark() + "支付方式不能为空");
                            wbd.setFlag("2");
                            flag = "2";
                        }
                    }
					
					if(wbd.getCost_center()!=null){
						CostCenter costCenter =costCenterService.selectByName(wbd.getCost_center());
						if(CommonUtils.checkExistOrNot(costCenter)){
							wbd.setCost_center(costCenter.getCost_center());
						}else if(!CommonUtils.checkExistOrNot(costCenter)){
							CostCenter costCenterById =costCenterService.selectByCostCenter(wbd.getCost_center());
							if(!CommonUtils.checkExistOrNot(costCenterById)){
							     wbd.setRemark(wbd.getRemark() + "成本中心错误");
		                         wbd.setFlag("2");
		                         flag = "2";
							}else{
							    wbd.setCost_center(costCenterById.getCost_center());
							}
						}
					}else{
						if(org!=null&&!wbd.getPay_path().equals("3")){
							wbd.setRemark(wbd.getRemark() + "成本中心必填");
							wbd.setFlag("2");
							flag = "2";
						}
					}
					if(wbd.getAmount_flag()!=null){
						if(wbd.getAmount_flag().equals("是")){
							wbd.setAmount_flag("1");
						}else if(wbd.getAmount_flag().equals("否")){
							wbd.setAmount_flag("0");
						}else{
							wbd.setAmount_flag("0");
						}
					}else{
						wbd.setAmount_flag("0");
					}
					
					
					List<OrganizationInformation> findOrgName = new ArrayList<OrganizationInformation>();
					if(wbd.getTo_orgnization()!=null && wbd.getTo_orgnization()!=""){
						findOrgName = organizationInformationServiceImpl.findOrgName(wbd.getTo_orgnization());
					}
					if(wbd.getTo_phone()==null && findOrgName.size()>0){
						wbd.setTo_phone(findOrgName.get(0).getOrg_phone());
					}
					if (wbd.getTo_contacts()==null && findOrgName.size()>0) {
						wbd.setTo_contacts(findOrgName.get(0).getOrg_contacts());
					}
					if (wbd.getFrom_contacts()==null) {
						wbd.setFrom_contacts(org.getOrg_contacts());
					}
					if(wbd.getFrom_phone()==null){
						wbd.setFrom_phone(org.getOrg_phone());
					}
					wbd.setNew_source(3);
					wbd.setCustid(org.getCustid());
					wbd.setFrom_orgnization(org.getOrg_name());
						if (wbd.getTo_province() != null && wbd.getTo_province() != "" && wbd.getTo_city() != null
								&& wbd.getTo_city() != "" && wbd.getTo_state() != null && wbd.getTo_state() != "" && wbd.getTo_address()!=null &&  wbd.getTo_address()!="") {
						    
	                         /********2018-03-20*******/ 
							ViewArea viewArea = new ViewArea();
							viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
							viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
							viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
							//viewAreaService.selectByViewArea(viewArea);
							List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
	                         /********2018-03-20*******/ 
							
							if(list2.size()==1){
								wbd.setTo_province_code(list2.get(0).getP_code());
								wbd.setTo_city_code(list2.get(0).getS_code());
								wbd.setTo_state_code(list2.get(0).getQ_code());
								wbd.setTo_city(list2.get(0).getS_name());
								wbd.setTo_province(list2.get(0).getP_name());
								wbd.setTo_state(list2.get(0).getQ_name());
							}else {
								wbd.setRemark(wbd.getRemark() + "收货城市逻辑错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}else if(wbd.getTo_province() == null && wbd.getTo_city() == null && wbd.getTo_state() == null && wbd.getTo_address() ==null && findOrgName.size()>0){
							wbd.setTo_province_code(findOrgName.get(0).getOrg_province_code());
							wbd.setTo_city_code(findOrgName.get(0).getOrg_city_code());
							wbd.setTo_state_code(findOrgName.get(0).getOrg_state_code());
							wbd.setTo_city(findOrgName.get(0).getOrg_city());
							wbd.setTo_province(findOrgName.get(0).getOrg_province());
							wbd.setTo_state(findOrgName.get(0).getOrg_state());
							wbd.setTo_address(findOrgName.get(0).getOrg_address());
						}else {
							wbd.setRemark(wbd.getRemark() + "收货城市信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						if (wbd.getFrom_province() == null && wbd.getFrom_city() == null && wbd.getFrom_state() == null && wbd.getFrom_address() == null) {
							wbd.setFrom_province_code(org.getOrg_province_code());
							wbd.setFrom_city_code(org.getOrg_city_code());
							wbd.setFrom_state_code(org.getOrg_state_code());
							wbd.setFrom_city(org.getOrg_city());
							wbd.setFrom_province(org.getOrg_province());
							wbd.setFrom_state(org.getOrg_state());
							wbd.setFrom_address(org.getOrg_address());
						} else {
							if (wbd.getFrom_province() != null && wbd.getFrom_province() != ""
									&& wbd.getFrom_city() != null && wbd.getFrom_city() != ""
									&& wbd.getFrom_state() != null && wbd.getFrom_state() != "" && wbd.getFrom_address()!=null &&  wbd.getFrom_address()!="") {
								
								
				                  /********2018-03-20*******/ 
	                            ViewArea viewArea = new ViewArea();
	                            viewArea.setP_name(CommonUtil.handleViewArea(wbd.getTo_province()));
	                            viewArea.setS_name(CommonUtil.handleViewArea(wbd.getTo_city()));
	                            viewArea.setQ_name(CommonUtil.handleViewArea(wbd.getTo_state()));
	                            //viewAreaService.selectByViewArea(viewArea);
	                            List<ViewArea> list2 = viewAreaService.selectByProcessedViewArea(viewArea);
	                             /********2018-03-20*******/ 
	                            
								if (list2.size()==1) {
									wbd.setFrom_province_code(list2.get(0).getP_code());
									wbd.setFrom_city_code(list2.get(0).getS_code());
									wbd.setFrom_state_code(list2.get(0).getQ_code());
									wbd.setFrom_city(list2.get(0).getS_name());
									wbd.setFrom_province(list2.get(0).getP_name());
									wbd.setFrom_state(list2.get(0).getQ_name());
								} else {
									wbd.setRemark(wbd.getRemark() + "发货城市逻辑错误");
									wbd.setFlag("2");
									flag = "2";
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "发货城市信息缺失错误");
								wbd.setFlag("2");
								flag = "2";
							}
						}
						if (wbd.getExpress_name() != null) {
							TransportVender transportVender = transportVenderService
									.selectByProducttype_name(wbd.getExpress_name(), power.carrier);
							if (transportVender != null) {
								wbd.setExpress_code(transportVender.getExpress_code());
								wbd.setIs_docall(transportVender.getIs_docall());
								if (wbd.getProducttype_name() != null) {
									TransportProductType transportProductType = transportProductTypeService
											.selectByProducttype_name(wbd.getExpress_code(), wbd.getProducttype_name(),
													power.getCarrier_type());
									if (transportProductType != null) {
										wbd.setProducttype_code(transportProductType.getProduct_type_code());
									} else {
										wbd.setRemark(wbd.getRemark() + "快递信息逻辑错误");
										wbd.setFlag("2");
										flag = "2";
									}
								}
							} else {
								wbd.setRemark(wbd.getRemark() + "快递公司信息错误");
								wbd.setFlag("2");
								flag = "2";
							}
						} else {
							wbd.setRemark(wbd.getRemark() + "快递公司信息缺失错误");
							wbd.setFlag("2");
							flag = "2";
						}
						wbd.setCreate_time(date);
						wbd.setUpdate_time(date);
						wbd.setCreate_user(user.getCreateUser());
						wbd.setUpdate_user(user.getCreateUser());
						wbd.setBat_id(uuid);
						String id = UUID.randomUUID().toString();
						wbd.setId(id);
						if (wbd.getRemark() != null) {
							String text = "第" + (i + 3) + "行;" + wbd.getRemark();
							wbd.setRemark(text);
						} else {
							String text = "第" + (i + 3) + "行";
							wbd.setRemark(text);
						}
						if (wbd.getExpress_name() == null || wbd.getExpress_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递公司不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getProducttype_name() == null || wbd.getProducttype_name() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "快递业务类型不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_address() == null || wbd.getTo_address() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-地址不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_state() == null || wbd.getTo_state() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-区不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_city() == null || wbd.getTo_city() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-市不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_province() == null || wbd.getTo_province() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货-省不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_phone() == null || wbd.getTo_phone() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人电话不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getTo_contacts() == null || wbd.getTo_contacts() == "") {
							wbd.setFlag("2");
							flag = "2";
							String remark = wbd.getRemark() + "收货人不能为空";
							wbd.setRemark(remark);
						}
						if (wbd.getQty() == null || wbd.getQty() == 0) {
							wbd.setQty(1);
							wbd.setTotal_qty(1);
						}
						if (wbd.getFlag() == null) {
							wbd.setFlag("0");
						}
						
						/*****20180320****/
						wbd.setOrdinal(i);
						/*****20180320****/
						
						wbd_list1.add(wbd);
				}
				ExpressinfoMasterInputlist expressinfoMasterInputlist = new ExpressinfoMasterInputlist();
				expressinfoMasterInputlist.setCreate_time(new Date());
				expressinfoMasterInputlist.setBat_id(uuid);
				expressinfoMasterInputlist.setCreate_user(user.getOrgid());
				expressinfoMasterInputlist.setUpdate_time(new Date());
				expressinfoMasterInputlist.setUpddate_user(user.getCreateUser());
				expressinfoMasterInputlist.setTotal_num(wbd_list1.size());
				if(flag==null){
				    flag ="0";
				}
				expressinfoMasterInputlist.setFlag(Integer.parseInt(flag));
				expressinfoMasterInputlist.setFail_num(0);
				expressinfoMasterInputlist.setSuccess_num(0);
				expressinfoMasterInputlistService.insertExpressinfoMasterInputlist(expressinfoMasterInputlist);
				//waybillMasterServiceImpl.insertWayBilMasterDetail(wbd_list);
				waybillMasterServiceImpl.insertBaoZunWayBilMasterDetail(wbd_list1);
				if (flag == "2" || flag.equals("2")) {
					result.put("result_code", "ULF");
					result.put("result_content", "上传文件有误！");
					return result;
				}
				Integer flag1 = 3;
				waybilMasterDetailService.updateByBatId(uuid, flag1);
				expressinfoMasterInputlistService.updateByBatId(uuid, flag1);
				try {
					
					List<WaybilMasterDetail> waybilMasterDetail = waybilMasterDetailService.baozunselectByBatId(uuid);
					for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetail) {
						waybillMasterServiceImpl.insetByWaybilMasterDetail(waybilMasterDetail2);
					}
					Integer flag2 = 4;
					waybilMasterDetailService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatIdAndSuccess(uuid,
							expressinfoMasterInputlist.getTotal_num());
				} catch (Exception e) {
				    e.printStackTrace();
					Integer flag2 = 0;
					waybilMasterDetailService.updateByBatId(uuid, flag2);
					expressinfoMasterInputlistService.updateByBatId(uuid, flag2);
					result.put("result_code", "ULF");
					result.put("result_content", "上传文件有误！");
					return result;
				}
				result.put("result_code", "SUCCESS");
				result.put("success_records", list.size());
			} catch (Exception e) {
				logger.error(e);
				// upload file failure-文件上传失败
				result.put("result_code", "ULF");
				if (e instanceof NoSuchMethodException) {
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
					
				} else {
					result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
					
				}
				
			}
			
		} else {
			// file is empty-文件为空
			result.put("result_code", "FIE");
		}
		return result;
		
	}

	/**
	 * // 订单导出
	 * 
	 * @param queryParam
	 * @param request
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping("/csvexportwmd")
	public void csvexportwmd(WaybillMasterQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		// Date time = formater.parse((new Date()).toString());

		Map<String, String> result = new HashMap<String, String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason", "错误");
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		List<Map<String, Object>> list = waybillMasterServiceImpl.selectByQueryCSV(queryParam);
		try {
			out = response.getWriter();
			if (list.size() > 0) {
				LinkedHashMap<String, String> cMap = new LinkedHashMap<String, String>();
				cMap.put("waybill", "Package Number");
				cMap.put("customer_number", "Package Reference");
				cMap.put("sku_name", "CRN Description");
				cMap.put("total_weight", "Package Weight");
				cMap.put("producttype_name", "Shipment Number");
				cMap.put("count", "Total Packages");
//				cMap.put("expected_from_date","Expected Delivery Date");
				CSVutil.createCSVFile(list, cMap,
						"vans_report_" + list.get(0).get("customer_number").toString().substring(0, 10));
				result.put("out_result", "1");
				result.put("out_result_reason", "成功");
				result.put("path", CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + "vans_report_"
						+ list.get(0).get("customer_number").toString().substring(0, 10) + ".csv");
			}
		} catch (Exception e) {
		}
		out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 返回main页面
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("backToMain")
	public String backToMain(WaybillMasterQueryParam parameter,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		Cookie waybillOrder = CookiesUtil.getCookieByName(request,"waybillOrder");
		Cookie waybillOrdera = CookiesUtil.getCookieByName(request,"waybillOrdera");
		Cookie baozunwaybillOrder = CookiesUtil.getCookieByName(request,"baozunwaybillOrder");
		Cookie toForm = CookiesUtil.getCookieByName(request,"toForm");
		if(CommonUtils.checkExistOrNot(toForm)){
			
			Cookie page = CookiesUtil.getCookieByName(request,"page");
			if(CommonUtils.checkExistOrNot(page)){
				String res = URLDecoder.decode(page.getValue(),"UTF-8");
				parameter = JSON.parseObject(res,parameter.getClass());
				return toForm(parameter,request,response);
			}
			String res = URLDecoder.decode(toForm.getValue(),"UTF-8");
			parameter = JSON.parseObject(res,parameter.getClass());
			return toForm(parameter,request,response);
		}else if(CommonUtils.checkExistOrNot(waybillOrder)){
			
			Cookie page = CookiesUtil.getCookieByName(request,"page");
			if(CommonUtils.checkExistOrNot(page)){
				String res = URLDecoder.decode(page.getValue(),"UTF-8");
				parameter = JSON.parseObject(res,parameter.getClass());
				return waybillOrder(parameter,request,response);
			}
			
			String res = URLDecoder.decode(waybillOrder.getValue(),"UTF-8");
			parameter = JSON.parseObject(res,parameter.getClass());
			return waybillOrder(parameter,request,response);
		}else if(CommonUtils.checkExistOrNot(waybillOrdera)){
			
			Cookie pagea = CookiesUtil.getCookieByName(request,"pagea");
			if(CommonUtils.checkExistOrNot(pagea)){
				String res = URLDecoder.decode(pagea.getValue(),"UTF-8");
				parameter = JSON.parseObject(res,parameter.getClass());
				return waybillOrdera(parameter,request,response);
			}
			
			String res = URLDecoder.decode(waybillOrdera.getValue(),"UTF-8");
			parameter = JSON.parseObject(res,parameter.getClass());
			return waybillOrdera(parameter,request,response);
		}else if(CommonUtils.checkExistOrNot(baozunwaybillOrder)){
		
			Cookie pagea = CookiesUtil.getCookieByName(request,"baozunpage");
			if(CommonUtils.checkExistOrNot(pagea)){
				String res = URLDecoder.decode(pagea.getValue(),"UTF-8");
				parameter = JSON.parseObject(res,parameter.getClass());
				return baozuninitaila(parameter,request,response);
			}
			
			String res = URLDecoder.decode(baozunwaybillOrder.getValue(),"UTF-8");
			parameter = JSON.parseObject(res,parameter.getClass());
			return baozuninitaila(parameter,request,response);
		}
		return toForm(parameter,request,response);
	}
	
	@ResponseBody
	@RequestMapping("/placeBaozunWaybilMaster")
	public JSONObject placeBaozunWaybilMaster(WaybillMaster queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Account temp = SysUtil.getAccountInSession(request);
		queryParam.setCreate_user(temp.getName());
		queryParam.setUpdate_user(temp.getName());
		String bat_id=request.getParameter("ids").replaceAll(";", "");
		if(bat_id!=null){
			List<WaybillMaster> baozunlist =waybillMasterServiceImpl.selectByBatIdAndStatus(bat_id);
			List<WaybillMaster> list = new ArrayList<WaybillMaster>();
			List<WaybillMaster> clist = new ArrayList<WaybillMaster>();
			List<WaybillMaster> sflist = new ArrayList<WaybillMaster>();
			List<WaybillMaster> rfdlist = new ArrayList<WaybillMaster>();
			List<WaybillMaster> ztolist = new ArrayList<WaybillMaster>();
			for (WaybillMaster waybillMaster : baozunlist) {
				/*if (!waybillMaster.getStatus().equals("1")) {
					obj.put("data", 1);
					return obj;
				}
				String stauts = "4";
				waybillMasterServiceImpl.confirmOrdersById(waybillMaster.getId(), stauts);*/
				if (waybillMaster.getExpressCode().equals("BSB")) {
					list.add(waybillMaster);
				} else if (waybillMaster.getExpressCode().equals("BSC")) {
					list.add(waybillMaster);
				} else if (waybillMaster.getExpressCode().equals("SF")) {
					sflist.add(waybillMaster);
				} else if (waybillMaster.getExpressCode().equals("SFSM")) {
					sflist.add(waybillMaster);
				} else if (waybillMaster.getExpressCode().equals("RFD")) {
					rfdlist.add(waybillMaster);
				}else if (waybillMaster.getExpressCode().equals("ZTO")) {
					ztolist.add(waybillMaster);
				}
				
			}
			expressinfoMasterInputlistService.updateByBatId(bat_id, 5);
			try {
				for (int i = 0; i < list.size(); i++) {
					WaybillMaster waybillMaster = list.get(i);
					ylInterface.createOrder(waybillMaster,
							waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()));
				}
				for (int i = 0; i < clist.size(); i++) {
					WaybillMaster waybillMaster = clist.get(i);
					ylInterface.insertByObj(waybillMaster);
				}
				for (int i = 0; i < sflist.size(); i++) {
					WaybillMaster waybillMaster = sflist.get(i);
					sfInterface.placeOrder(waybillMaster);
				}
				for (int i = 0; i < rfdlist.size(); i++) {
					WaybillMaster waybillMaster = rfdlist.get(i);
					rfdInterface.insertByObj(waybillMaster,
							waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()));
				}
				for (int i = 0; i < ztolist.size(); i++) {
					WaybillMaster waybillMaster = ztolist.get(i);
					zTOInterface.createOrder(waybillMaster,
							waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id()),0);
				}
			} catch (Exception e) {
			}
				
		}
		obj.put("data", 0);
		return obj;
	}
	
	/**
	 * 跳转导入下单失败的错误条数页面
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/baozunuploade")
	public String baozunuploade(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		queryParam.setStatus("4");
		Account temp = SysUtil.getAccountInSession(request);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/baozunupload";
	}
	
	/**
	 * 跳转导入下单失败的错误条数页面
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/baozunupload_page")
	public String baozunuploade_page(WaybillMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		BusinessPower power = SysUtil.getPowerSession(request);
		queryParam.setOrg_cde(power.getOrg_code_list());
		queryParam.setStatus("4");
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = waybillMasterServiceImpl.findAllWaybillMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/baozunupload_page";
	}
	
	
	@ResponseBody
	@RequestMapping("/interceptingTime")
	public JSONObject interceptingTime(WaybillMasterQueryParam queryParam, HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		obj.put("data", 1);
		Calendar s = Calendar.getInstance();
        s.set(Calendar.HOUR_OF_DAY, 8);
        s.set(Calendar.MINUTE, 0);
        s.set(Calendar.SECOND, 0);
		Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 18);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date cinterceptingTime = c.getTime();
        Date sinterceptingTime = s.getTime();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cformat = formater.format(cinterceptingTime);
		String sformat = formater.format(sinterceptingTime);
		try {
			Date cparse = formater.parse(cformat);
			Date sparse = formater.parse(sformat);
			Date date = new Date();
			String format2 = formater.format(date);
			Date parse2 = formater.parse(format2);
			if(cparse.getTime()<parse2.getTime() || sparse.getTime()>parse2.getTime()){
				obj.put("data", 2);
			}
		} catch (ParseException e) {
			
		}
	      obj.put("data", 1);
		return obj;

	}
	
	public static void main(String[] args) {
		
		String s="0217928502";
		String substring = "******"+s.substring(6);
		System.out.println(substring);
		
	}
	
}
