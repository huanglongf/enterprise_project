package com.bt.dataReport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bt.common.controller.result.Result;
import com.bt.dataReport.service.OredeShippSummaryService;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Controller
@RequestMapping("/control/datareport/oredeshippsummarycontroller")
public class OredeShippSummaryController {
   
	@Resource
	private OredeShippSummaryService oredeShippSummaryService;
	
	@RequestMapping("/selectOredeShippSummaryBean")
	@ResponseBody
	public String selectOredeShippSummaryBean(
			@RequestParam(value="warehouseName",required=false) String warehouseName,
			@RequestParam(value="storeName",required=false) String storeName,
			@RequestParam(value="transportName",required=false) String transportName,
			@RequestParam(value="beginDateTime",required=false) String beginDateTime,
			@RequestParam(value="endDateTime",required=false) String endDateTime
			                 ) {
		Result result = new Result();
		try {
			result.setData(oredeShippSummaryService.selectOredeShippSummaryBean());
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
	
	@RequestMapping("/exportOredeShippSummaryBean")
	@ResponseBody
	public String exportOredeShippSummaryBean(
			HttpServletRequest req,
			@RequestParam(value="warehouseName",required=false) String warehouseName,
			@RequestParam(value="storeName",required=false) String storeName,
			@RequestParam(value="transportName",required=false) String transportName,
			@RequestParam(value="beginDateTime",required=false) String beginDateTime,
			@RequestParam(value="endDateTime",required=false) String endDateTime
			                 ) {
		String path = req.getSession().getServletContext().getRealPath("/exportFile");
		Result result = new Result();
		try {
			result.setData(oredeShippSummaryService.exportOredeShippSummaryBean(path,warehouseName,storeName,transportName,beginDateTime,endDateTime));
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
}
