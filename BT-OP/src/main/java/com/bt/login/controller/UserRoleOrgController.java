package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.UserRoleOrg;
import com.bt.login.service.UserRoleOrgService;
/**
 * 用户/角色与组织机构关系表控制器
 *
 */
@Controller
@RequestMapping("/control/login/userRoleOrgController")
public class UserRoleOrgController{

	private static final Logger logger = Logger.getLogger(UserRoleOrgController.class);
	
	/**
	 * 用户/角色与组织机构关系表服务类
	 */
	@Resource(name = "userRoleOrgServiceImpl")
	private UserRoleOrgService<UserRoleOrg> userRoleOrgService;
}
