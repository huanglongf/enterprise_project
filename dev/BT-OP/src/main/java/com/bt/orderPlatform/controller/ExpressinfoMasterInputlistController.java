package com.bt.orderPlatform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.base.BaseConstant;
import com.bt.orderPlatform.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.ExpressinfoMasterInputlistService;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.bt.sys.model.Account;
import com.bt.sys.model.BusinessPower;
import com.bt.sys.util.SysUtil;

import net.sf.json.JSONObject;
/**
 * 运单信息临时表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/expressinfoMasterInputlistController")
public class ExpressinfoMasterInputlistController{

	private static final Logger logger = Logger.getLogger(ExpressinfoMasterInputlistController.class);
	
	/**
	 * 运单信息临时表服务类
	 */
	@Resource(name = "expressinfoMasterInputlistServiceImpl")
	private ExpressinfoMasterInputlistService<ExpressinfoMasterInputlist> expressinfoMasterInputlistService;
	@Resource(name = "waybilMasterDetailServiceImpl")
	private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
	
	@RequestMapping("/page")
	public String page(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		BusinessPower power=SysUtil.getPowerSession(request);
		queryParam.setCreate_user(power.getOrg_code_list());
		Account user=SysUtil.getAccountInSession(request);
		PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr= expressinfoMasterInputlistService.findAllExpressinfoMasterInputlist(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/expressinfoMasterInputlist_page";
	}
	
	
	
	@RequestMapping("/uploadresult")
	public String pagetest(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		BusinessPower power=SysUtil.getPowerSession(request);
		queryParam.setCreate_user(power.getOrg_code_list());
		Account user=SysUtil.getAccountInSession(request);
		PageView<Map<String, Object>> pageView= new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConstant.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr= expressinfoMasterInputlistService.findAllExpressinfoMasterInputlist(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "op/expressinfoMasterInputlist";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/deleteWaybilMasterDetail")
	public JSONObject deleteWaybilMasterDetail(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids").replaceAll(";", "");
		Integer flag = expressinfoMasterInputlistService.selectByBatId(id).get(0).getFlag();
		try{
			if(flag==3){
				obj.put("data", 0);
				return obj;
			}
			expressinfoMasterInputlistService.deletetByBatId(id);
			waybilMasterDetailService.deletetByBatId(id);
			obj.put("data", 1);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("data", 2);
		}
		return obj;
	}
	@ResponseBody
	@RequestMapping("/placeWaybilMaster")
	public JSONObject placeWaybilMaster(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids").replaceAll(";", "");
		Integer flag = expressinfoMasterInputlistService.selectByBatId(id).get(0).getFlag();
		obj.put("data", 2);
		try{
			if(flag==4){
				obj.put("data", 1);
				return obj;
			}else if(flag==5){
				obj.put("data", 3);
				return obj;
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("data", 2);
		}
		return obj;
	}
	@ResponseBody
	@RequestMapping("/selectResult")
	public JSONObject selectResult(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids").replaceAll(";", "");
		Integer flag = expressinfoMasterInputlistService.selectByBatId(id).get(0).getFlag();
		obj.put("data", 2);
		try{
			if(flag==4){
				obj.put("data", 3);
				return obj;
			}else if(flag==5){
				obj.put("data", 1);
				return obj;
			}
		}catch(Exception e){
			e.printStackTrace();
			obj.put("data", 2);
		}
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/transformationWaybilMasterDetail")
	public JSONObject transformationWaybilMasterDetail(ExpressinfoMasterInputlistQueryParam queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids").replaceAll(";", "");
		ExpressinfoMasterInputlist expressinfoMasterInputlist = expressinfoMasterInputlistService.selectByBatId(id).get(0);
		Integer isflag = expressinfoMasterInputlist.getFlag();
		if(isflag!=0){
			obj.put("data", 0);
			return obj;
		}else if(isflag==2){
			obj.put("data", 3);
			return obj;
		}
		Integer flag = 3;
		waybilMasterDetailService.updateByBatId(id,flag);
		expressinfoMasterInputlistService.updateByBatId(id,flag);
		try{
			List<WaybilMasterDetail> waybilMasterDetail1 =waybilMasterDetailService.selectByBatIdAndCustomer(id);
			for (WaybilMasterDetail waybilMasterDetail : waybilMasterDetail1) {
				WaybillMasterDetail queryByCustomerNum = waybillMasterServiceImpl.queryByCustomerNum(waybilMasterDetail.getCustomer_number());
				if(queryByCustomerNum==null){
					waybillMasterServiceImpl.insetWaybilMasterDetailByCustomer(waybilMasterDetail);
				}else{
					Integer flag1 = 0;
					waybilMasterDetailService.updateByBatId(id,flag1);
					expressinfoMasterInputlistService.updateByBatId(id,flag1);
					obj.put("data", 4);
					return obj;
				}
			}
			List<WaybilMasterDetail> waybilMasterDetail = waybilMasterDetailService.selectByBatId(id);
			for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetail) {
				waybillMasterServiceImpl.insetByWaybilMasterDetail(waybilMasterDetail2);
			}
			obj.put("data", 1);
			Integer flag1 = 4;
			waybilMasterDetailService.updateByBatId(id,flag1);
			expressinfoMasterInputlistService.updateByBatId(id,flag1);
			expressinfoMasterInputlistService.updateByBatIdAndSuccess(id,expressinfoMasterInputlist.getTotal_num());
		}catch(Exception e){
			e.printStackTrace();
			Integer flag1 = 0;
			waybilMasterDetailService.updateByBatId(id,flag1);
			expressinfoMasterInputlistService.updateByBatId(id,flag1);
			obj.put("data", 2);
		}
		return obj;
	}
	
}
