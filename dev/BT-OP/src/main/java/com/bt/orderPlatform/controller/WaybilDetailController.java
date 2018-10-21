package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bt.orderPlatform.model.WaybilDetail;
/**
 * 快递明细表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/waybilDetailController")
public class WaybilDetailController {

	private static final Logger logger = Logger.getLogger(WaybilDetailController.class);
	
	/**
	 * 快递明细表服务类
	 */
/*	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;*/
}
