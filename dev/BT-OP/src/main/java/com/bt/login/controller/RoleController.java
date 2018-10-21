package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.Role;
import com.bt.login.service.RoleService;
/**
 * 角色表控制器
 *
 */
@Controller
@RequestMapping("/control/login/roleController")
public class RoleController{

	private static final Logger logger = Logger.getLogger(RoleController.class);
	
	/**
	 * 角色表服务类
	 */
	@Resource(name = "roleServiceImpl")
	private RoleService<Role> roleService;
}
