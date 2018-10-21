package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.orderPlatform.model.CostCenter;
import com.bt.orderPlatform.service.CostCenterService;
/**
 * 成本中心表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/costCenterController")
public class CostCenterController {

	private static final Logger logger = Logger.getLogger(CostCenterController.class);
	
	/**
	 * 成本中心表服务类
	 */
	@Resource(name = "costCenterServiceImpl")
	private CostCenterService<CostCenter> costCenterService;
}
