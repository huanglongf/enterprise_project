package com.bt.dataReport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bt.common.controller.result.Result;
import com.bt.dataReport.service.OredeShippFlowSummaryService;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Controller
@RequestMapping("/control/datareport/oredeshippflowsummarycontroller")
public class OredeShippFlowSummaryController {
	@Resource
	private OredeShippFlowSummaryService oredeShippFlowSummaryService;
	
	@RequestMapping("/selectOredeFlowShippSummaryBean")
	@ResponseBody
	public String selectOredeFlowShippSummaryBean(
			@RequestParam(value="warehouseName",required=false) String warehouseName,
			@RequestParam(value="storeName",required=false) String storeName,
			@RequestParam(value="transportName",required=false) String transportName,
			@RequestParam(value="beginDateTime",required=false) String beginDateTime,
			@RequestParam(value="endDateTime",required=false) String endDateTime) {
		Result result = new Result();
		try {
			result.setData(oredeShippFlowSummaryService.selectOredeShippFlowSummaryBean());
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
	
	@RequestMapping("/exportOredeFlowShippSummaryBean")
	@ResponseBody
	public String exportOredeFlowShippSummaryBean(
			HttpServletRequest req,
			@RequestParam(value="warehouseName",required=false) String warehouseName,
			@RequestParam(value="storeName",required=false) String storeName,
			@RequestParam(value="transportName",required=false) String transportName,
			@RequestParam(value="beginDateTime",required=false) String beginDateTime,
			@RequestParam(value="endDateTime",required=false) String endDateTime) {
		Result result = new Result();
		String path = req.getSession().getServletContext().getRealPath("/exportFile");
		try {
			result.setData(oredeShippFlowSummaryService.exportOredeShippFlowSummaryBean(path,warehouseName,storeName,transportName,beginDateTime,endDateTime));
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
}
