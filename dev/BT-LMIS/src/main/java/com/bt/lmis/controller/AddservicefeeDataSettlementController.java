package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.AddservicefeeDataSettlement;
import com.bt.lmis.service.AddservicefeeDataSettlementService;
/**
 * 增值服务费结算控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/addservicefeeDataSettlementController")
public class AddservicefeeDataSettlementController extends BaseController {

	private static final Logger logger = Logger.getLogger(AddservicefeeDataSettlementController.class);
	
	/**
	 * 增值服务费结算服务类
	 */
	@Resource(name = "addservicefeeDataSettlementServiceImpl")
	private AddservicefeeDataSettlementService<AddservicefeeDataSettlement> addservicefeeDataSettlementService;
}
