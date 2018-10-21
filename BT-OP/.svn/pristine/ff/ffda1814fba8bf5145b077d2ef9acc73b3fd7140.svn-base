package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.orderPlatform.model.ViewArea;
import com.bt.orderPlatform.service.ViewAreaService;
/**
 * 运单信息临时表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/viewAreaController")
public class ViewAreaController {

	private static final Logger logger = Logger.getLogger(ViewAreaController.class);
	
	/**
	 * 运单信息临时表服务类
	 */
	@Resource(name = "viewAreaServiceImpl")
	private ViewAreaService<ViewArea> viewAreaService;
}
