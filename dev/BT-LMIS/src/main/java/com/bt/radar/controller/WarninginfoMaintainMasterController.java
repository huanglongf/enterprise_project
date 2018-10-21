package com.bt.radar.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.service.WarninginfoMaintainMasterService;
/**
 * 快递预警信息配置主表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/warninginfoMaintainMasterController")
public class WarninginfoMaintainMasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(WarninginfoMaintainMasterController.class);
	
	/**
	 * 快递预警信息配置主表服务类
	 */
	@Resource(name = "warninginfoMaintainMasterServiceImpl")
	private WarninginfoMaintainMasterService<WarninginfoMaintainMaster> warninginfoMaintainMasterService;
}
