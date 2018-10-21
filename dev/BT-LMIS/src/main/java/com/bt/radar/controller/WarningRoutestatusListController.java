package com.bt.radar.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.radar.model.WarningRoutestatusList;
import com.bt.radar.service.WarningRoutestatusListService;
/**
 * 预警状态维护列表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/warningRoutestatusListController")
public class WarningRoutestatusListController extends BaseController {

	private static final Logger logger = Logger.getLogger(WarningRoutestatusListController.class);
	
	/**
	 * 预警状态维护列表服务类
	 */
	@Resource(name = "warningRoutestatusListServiceImpl")
	private WarningRoutestatusListService<WarningRoutestatusList> warningRoutestatusListService;
}
