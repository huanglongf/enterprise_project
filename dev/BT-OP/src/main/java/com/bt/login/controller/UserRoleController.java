package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.UserRole;
import com.bt.login.service.UserRoleService;
/**
 * 用户角色关系表控制器
 *
 */
@Controller
@RequestMapping("/control/login/userRoleController")
public class UserRoleController{

	private static final Logger logger = Logger.getLogger(UserRoleController.class);
	
	/**
	 * 用户角色关系表服务类
	 */
	@Resource(name = "userRoleServiceImpl")
	private UserRoleService<UserRole> userRoleService;
}
