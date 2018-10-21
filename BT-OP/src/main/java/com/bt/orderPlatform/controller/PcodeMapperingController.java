package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.orderPlatform.model.PcodeMappering;
import com.bt.orderPlatform.service.PcodeMapperingService;
/**
 * 测试临时表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/pcodeMapperingController")
public class PcodeMapperingController {

	private static final Logger logger = Logger.getLogger(PcodeMapperingController.class);
	
	/**
	 * 测试临时表服务类
	 */
	@Resource(name = "pcodeMapperingServiceImpl")
	private PcodeMapperingService<PcodeMappering> pcodeMapperingService;
}
