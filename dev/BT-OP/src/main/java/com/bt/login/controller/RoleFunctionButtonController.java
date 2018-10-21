package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.RoleFunctionButton;
import com.bt.login.service.RoleFunctionButtonService;
/**
 * 角色与功能菜单关系表控制器
 *
 */
@Controller
@RequestMapping("/control/login/roleFunctionButtonController")
public class RoleFunctionButtonController {

	private static final Logger logger = Logger.getLogger(RoleFunctionButtonController.class);
	
	/**
	 * 角色与功能菜单关系表服务类
	 */
	@Resource(name = "roleFunctionButtonServiceImpl")
	private RoleFunctionButtonService<RoleFunctionButton> roleFunctionButtonService;
}
