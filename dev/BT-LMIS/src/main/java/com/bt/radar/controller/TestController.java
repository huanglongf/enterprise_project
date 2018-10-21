package com.bt.radar.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.radar.service.PhysicalWarehouseService;
import com.bt.workOrder.quartz.WkLevelUp;

@Controller
@RequestMapping("/testController")
public class TestController {
	
	@Resource(name = "physicalWarehouseServiceImpl")
	private PhysicalWarehouseService<?> physicalWarehouseServiceImpl;
	
	@RequestMapping("/test")
	public  String test() throws Exception{
		/*WkLevelUp up=new WkLevelUp();//雷达推工单
		up.startGenWkleve();*/
		WkLevelUp up=new WkLevelUp();
		up.startLevelUp();
		return null;
	}
	

}
