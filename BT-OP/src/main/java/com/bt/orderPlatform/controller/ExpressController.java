package com.bt.orderPlatform.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.interf.sf.SfInterface;
import com.bt.login.controller.form.UserQueryParam;
import com.bt.login.model.Org;
import com.bt.login.service.OrgService;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.dao.WaybillMasterMapper;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.WaybillMasterService;

@Controller
@RequestMapping("/control/btradar/expressController")

public class ExpressController {
	@Autowired
	private SfInterface sfInterface;
	@Resource(name = "waybillMasterServiceImpl")
	private  WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
	@RequestMapping("/sfintest")
	@ResponseBody
	public JSONObject sf(HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj=new JSONObject();
	try {
		WaybillMaster queryParam=new WaybillMaster();
		queryParam.setWaybill(request.getParameter("waybill").toString());
		List<WaybillMaster>  list=new ArrayList<WaybillMaster>(); 
		list=waybillMasterServiceImpl.selectByWaybillMaster(queryParam);
		String v=	sfInterface.getRequestXmlsTest(list);
		obj.put("code", v);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return obj;
	}
	
	
	@RequestMapping("/sfinsert")
	@ResponseBody
	public JSONObject test(HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj=new JSONObject();
	try {
		WaybillMaster queryParam=new WaybillMaster();
		queryParam.setWaybill(request.getParameter("waybill").toString());
		List<WaybillMaster>  list=new ArrayList<WaybillMaster>(); 
		list=waybillMasterServiceImpl.selectByWaybillMaster(queryParam);
			sfInterface.getRequestXmls(list);
		obj.put("code", "");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return obj;
	}
	
}
