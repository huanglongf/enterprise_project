package com.bt.radar.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.radar.model.InterfaceRouteinfo;
import com.bt.radar.service.InterfaceRouteinfoService;
/**
 * 路由信息借口表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/interfaceRouteinfoController")
public class InterfaceRouteinfoController extends BaseController {

	private static final Logger logger = Logger.getLogger(InterfaceRouteinfoController.class);
	
	/**
	 * 路由信息借口表服务类
	 */
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
}
