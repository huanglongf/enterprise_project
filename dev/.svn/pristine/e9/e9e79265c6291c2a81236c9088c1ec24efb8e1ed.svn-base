package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.service.CollectionMasterService;
/**
 * 汇总明细表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/collectionMasterController")
public class CollectionMasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(CollectionMasterController.class);
	
	/**
	 * 汇总明细表服务类
	 */
	@Resource(name = "collectionMasterServiceImpl")
	private CollectionMasterService<CollectionMaster> collectionMasterService;
}
