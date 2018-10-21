package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.PackagePriceLadder;
import com.bt.lmis.service.PackagePriceLadderService;
/**
 * 快递结算阶梯表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/packagePriceLadderController")
public class PackagePriceLadderController extends BaseController {

	private static final Logger logger = Logger.getLogger(PackagePriceLadderController.class);
	
	/**
	 * 快递结算阶梯表服务类
	 */
	@Resource(name = "packagePriceLadderServiceImpl")
	private PackagePriceLadderService<PackagePriceLadder> packagePriceLadderService;
}
