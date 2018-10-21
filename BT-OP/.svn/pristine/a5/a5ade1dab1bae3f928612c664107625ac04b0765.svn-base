package com.bt.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.login.model.Org;
import com.bt.login.service.OrgService;
/**
 * 组织机构表控制器
 *
 */
@Controller
@RequestMapping("/control/login/orgController")
public class OrgController{

	private static final Logger logger = Logger.getLogger(OrgController.class);
	
	/**
	 * 组织机构表服务类
	 */
	@Resource(name = "orgServiceImpl")
	private OrgService<Org> orgService;
}
