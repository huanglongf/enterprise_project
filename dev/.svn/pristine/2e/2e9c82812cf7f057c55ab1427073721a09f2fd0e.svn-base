package com.bt.orderPlatform.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.orderPlatform.controller.form.OrganizationInformationQueryParam;
import com.bt.orderPlatform.model.Area;
import com.bt.orderPlatform.model.OrganizationInformation;
import com.bt.orderPlatform.model.TransportProductType;
import com.bt.orderPlatform.model.TransportVender;
import com.bt.orderPlatform.service.AreaService;
import com.bt.orderPlatform.service.OrganizationInformationService;
/**
 * 组织机构信息表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/organizationInformationController")
public class OrganizationInformationController  {

	private static final Logger logger = Logger.getLogger(OrganizationInformationController.class);
	
	/**
	 * 组织机构信息表服务类
	 */
	@Resource(name = "organizationInformationServiceImpl")
	private OrganizationInformationService<OrganizationInformation> organizationInformationService;
	@Resource(name = "areaServiceImpl")
	private AreaService<TransportVender> areaService;
	
	
	
	@ResponseBody
	@RequestMapping("/toOrgnization")
	public  JSONObject getProductTypeCode(OrganizationInformationQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		Area area=new Area();
		area.setPid(1);
		List areas=areaService.findArea(area);
		//request.setAttribute("areas", areas);
		obj.put("areas", areas);
		List<OrganizationInformation> qr=
				organizationInformationService.findOrgName(queryParam.getOrg_name());
		obj.put("organizationInformation", qr.get(0));
		if(qr.get(0).getOrg_province_code()!=null&&!"".equals(qr.get(0).getOrg_province_code())){
			List<Area> list1 = areaService.getArea(qr.get(0).getOrg_province_code());
			obj.put("city", areaService.getArea(qr.get(0).getOrg_province_code()));
		}
		if(qr.get(0).getOrg_city_code()!=null&&!"".equals(qr.get(0).getOrg_city_code())){
			List<Area> list = areaService.getArea(qr.get(0).getOrg_city_code());
			obj.put("state", areaService.getArea(qr.get(0).getOrg_city_code()));	
		}
		return  obj;
	}
	
}
