
package com.bt.dataReport.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bt.common.controller.result.Result;
import com.bt.dataReport.service.WareStoreTranSportService;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */

@Controller
@RequestMapping("/control/datareport/warestoretranasportcontroller")
public class WareStoreTranaSportController{
	
	@Resource
	private WareStoreTranSportService wareStoreTranSportService;
	
	
	@RequestMapping("/selectWare")
	@ResponseBody
	public String selectWare() {
		Result result = new Result();
		try {
			result.setData(wareStoreTranSportService.selectWare());
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
	
	@RequestMapping("/selectStore")
	@ResponseBody
	public String selectStore() {
		Result result = new Result();
		try {
			result.setData(wareStoreTranSportService.selectStore());
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
	
	@RequestMapping("/selectTranSport")
	@ResponseBody
	public String selectTranSport() {
		Result result = new Result();
		try {
			result.setData(wareStoreTranSportService.selectTranSport());
			result.setFlag(true);
		} catch (Exception e) {
			result.setFlag(false);
			result.setMsg("查询失败");
		}
		return JSON.toJSONString(result);	
	}
}
