package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.orderPlatform.model.PayPath;
import com.bt.orderPlatform.service.PayPathService;
/**
 * 支付方式表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/payPathController")
public class PayPathController {

	private static final Logger logger = Logger.getLogger(PayPathController.class);
	
	/**
	 * 支付方式表服务类
	 */
	@Resource(name = "payPathServiceImpl")
	private PayPathService<PayPath> payPathService;
}
