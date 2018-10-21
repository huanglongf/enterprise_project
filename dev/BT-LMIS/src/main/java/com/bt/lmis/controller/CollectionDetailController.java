package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.CollectionDetail;
import com.bt.lmis.service.CollectionDetailService;
/**
 * 汇总明细表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/collectionDetailController")
public class CollectionDetailController extends BaseController {

	private static final Logger logger = Logger.getLogger(CollectionDetailController.class);
	
	/**
	 * 汇总明细表服务类
	 */
	@Resource(name = "collectionDetailServiceImpl")
	private CollectionDetailService<CollectionDetail> collectionDetailService;
}
