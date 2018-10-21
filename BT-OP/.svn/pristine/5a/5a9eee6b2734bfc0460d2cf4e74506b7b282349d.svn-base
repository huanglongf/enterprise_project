package com.bt.orderPlatform.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.orderPlatform.model.FtpFileName;
import com.bt.orderPlatform.service.FtpFileNameService;
/**
 * 测试临时表控制器
 *
 */
@Controller
@RequestMapping("/control/orderPlatform/ftpFileNameController")
public class FtpFileNameController {

	private static final Logger logger = Logger.getLogger(FtpFileNameController.class);
	
	/**
	 * 测试临时表服务类
	 */
	@Resource(name = "ftpFileNameServiceImpl")
	private FtpFileNameService<FtpFileName> ftpFileNameService;
}
