package com.bt.workOrder.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.workOrder.model.WkLevel;
import com.bt.workOrder.service.WkLevelService;
/**
 * 工单级别控制器
 *
 */
@Controller
@RequestMapping("/control/workOrder/wkLevelController")
public class WkLevelController extends BaseController {

	private static final Logger logger = Logger.getLogger(WkLevelController.class);
	
	/**
	 * 工单级别服务类
	 */
	@Resource(name = "wkLevelServiceImpl")
	private WkLevelService<WkLevel> wkLevelService;
}
