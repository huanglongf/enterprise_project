package com.bt.lmis.balance.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.balance.model.PackageCharageEstimate;
import com.bt.lmis.balance.service.PackageCharageEstimateService;
/**
 * 打包费预估数据控制器
 *
 */
@Controller
@RequestMapping("/control/balance/packageCharageEstimateController")
public class PackageCharageEstimateController extends BaseController {

	private static final Logger logger = Logger.getLogger(PackageCharageEstimateController.class);
	
	/**
	 * 打包费预估数据服务类
	 */
	@Resource(name = "packageCharageEstimateServiceImpl")
	private PackageCharageEstimateService<PackageCharageEstimate> packageCharageEstimateService;
}
