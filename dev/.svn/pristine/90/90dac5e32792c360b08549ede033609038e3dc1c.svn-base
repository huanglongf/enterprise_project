package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.InternalPrice;
import com.bt.lmis.service.InternalPriceService;
/**
 * 首重续重价格表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/internalPriceController")
public class InternalPriceController extends BaseController {

	private static final Logger logger = Logger.getLogger(InternalPriceController.class);
	
	/**
	 * 首重续重价格表服务类
	 */
	@Resource(name = "internalPriceServiceImpl")
	private InternalPriceService<InternalPrice> internalPriceService;
}
