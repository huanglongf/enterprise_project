package com.bt.workOrder.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.workOrder.model.WkType;
import com.bt.workOrder.service.WkTypeService;
/**
 * 工单类型控制器
 *
 */
@Controller
@RequestMapping("/control/workOrder/wkTypeController")
public class WkTypeController extends BaseController {

	private static final Logger logger = Logger.getLogger(WkTypeController.class);
	
	/**
	 * 工单类型服务类
	 */
	@Resource(name = "wkTypeServiceImpl")
	private WkTypeService<WkType> wkTypeService;
}
