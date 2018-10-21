package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.FunctionButton;
import com.bt.login.service.FunctionButtonService;
/**
 * 功能菜单表控制器
 *
 */
@Controller
@RequestMapping("/control/login/functionButtonController")
public class FunctionButtonController {

	private static final Logger logger = Logger.getLogger(FunctionButtonController.class);
	
	/**
	 * 功能菜单表服务类
	 */
	@Resource(name = "functionButtonServiceImpl")
	private FunctionButtonService<FunctionButton> functionButtonService;
	
	
}
