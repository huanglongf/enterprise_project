package com.bt.lmis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.summary.ConsumableCost;
import com.bt.lmis.summary.OperatingCost;
import com.bt.lmis.summary.PackingChargeSettlement;
import com.bt.lmis.summary.StorageChargeSettlement;
import com.bt.lmis.summary.ValueAddedBalance;
@Controller
@RequestMapping("/control/lmis/reSettlementController")
public class ReSettlementController {
	@Autowired
	StorageChargeSettlement  storageservice;
	@Autowired
	OperatingCost OperatingCostservice;
	@Autowired 
	ConsumableCost  consumableCostService;
	@Autowired 
	PackingChargeSettlement  packingChargeSettlementService;
	@Autowired 
	ValueAddedBalance  valueAddedBalanceService;
	@ResponseBody
	@RequestMapping(value="/reStorage.do")
	public JSONObject ReStorage(ModelMap map, HttpServletRequest request){
		JSONObject obj=new 	JSONObject();
		//数据恢复
		storageservice.ReckonStorageCharge_by_con_id_before(request.getParameter("con_id"),request.getParameter("date"));
		//检查汇总和结算数据是否已经恢复完毕
		if(storageservice.checkDataForResettle_warsehoueCharge(request.getParameter("con_id"),request.getParameter("date")))
		//重新开始跑结算
		{//开始汇总
			obj.put("msg", "系统已经开始结算！");
			storageservice.ReckonStorageCharge_by_con_id(request.getParameter("con_id"),request.getParameter("date"));
			storageservice.ReReckonStorageGroup("200", "2016-11");
		}else{
			obj.put("msg", "要结算的数据没有还原完毕,建议您可以稍后再尝试或者选择手动还原数据！");
		}
		return obj;
	}
	@ResponseBody
	@RequestMapping(value="/valueAddedBalance.do")
	public JSONObject valueAddedBalance(ModelMap map, HttpServletRequest request) throws Exception{
		JSONObject obj=new 	JSONObject();
		valueAddedBalanceService.recover(request.getParameter("con_id"),request.getParameter("date"));
		valueAddedBalanceService.Resettlement(request.getParameter("con_id"),request.getParameter("date"));
		valueAddedBalanceService.reSummary(request.getParameter("con_id"),request.getParameter("date"));
		return obj;
	}
	@ResponseBody
	@RequestMapping(value="/reopertion.do")
	public JSONObject reoperation(ModelMap map, HttpServletRequest request) throws Exception{
		JSONObject obj=new 	JSONObject();
		OperatingCostservice.reCoverData(request.getParameter("con_id"),request.getParameter("date"));
		OperatingCostservice.reSettlement(request.getParameter("con_id"),request.getParameter("date"));
		OperatingCostservice.reSummary(request.getParameter("con_id"),request.getParameter("date"));
		return obj;
	}
	@ResponseBody
	@RequestMapping(value="/consumableCost.do")
	public JSONObject consumableCost(ModelMap map, HttpServletRequest request) throws Exception{
		JSONObject obj=new 	JSONObject();
		consumableCostService.recover_data(request.getParameter("con_id"),request.getParameter("date"));
		consumableCostService.ReReckonConsumableCost(request.getParameter("con_id"),request.getParameter("date"));
		consumableCostService.ReSummary(request.getParameter("con_id"),request.getParameter("date"));
		return obj;
	}
	@ResponseBody
	@RequestMapping(value="/repackingChargeSettlement.do")
	public JSONObject repackingChargeSettlement(ModelMap map, HttpServletRequest request) throws Exception{
		JSONObject obj=new 	JSONObject();
		packingChargeSettlementService.recover(request.getParameter("con_id"),request.getParameter("date"));
		packingChargeSettlementService.reSettlement(request.getParameter("con_id"),request.getParameter("date"));
		return obj;
	}
	
}
