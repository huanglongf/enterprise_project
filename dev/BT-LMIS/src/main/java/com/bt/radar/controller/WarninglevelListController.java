package com.bt.radar.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.radar.model.WarninglevelList;
import com.bt.radar.service.WarninglevelListService;
/**
 * 预警级别升级维护列表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/warninglevelListController")
public class WarninglevelListController extends BaseController {

	private static final Logger logger = Logger.getLogger(WarninglevelListController.class);
	
	/**
	 * 预警级别升级维护列表服务类
	 */
	@Resource(name = "warninglevelListServiceImpl")
	private WarninglevelListService<WarninglevelList> warninglevelListService;
}
