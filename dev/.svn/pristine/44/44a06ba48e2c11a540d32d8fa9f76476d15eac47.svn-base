package com.bt.radar.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.radar.model.ExpressinfoDetail;
import com.bt.radar.service.ExpressinfoDetailService;
/**
 * 运单明细表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/expressinfoDetailController")
public class ExpressinfoDetailController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressinfoDetailController.class);
	
	/**
	 * 运单明细表服务类
	 */
	@Resource(name = "expressinfoDetailServiceImpl")
	private ExpressinfoDetailService<ExpressinfoDetail> expressinfoDetailService;
}
