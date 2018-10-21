package com.bt.lmis.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.ItemtypePrice;
import com.bt.lmis.service.ItemtypePriceService;
/**
 * 产品价格表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/itemtypePriceController")
public class ItemtypePriceController extends BaseController {

	private static final Logger logger = Logger.getLogger(ItemtypePriceController.class);
	
	/**
	 * 产品价格表服务类
	 */
	@Resource(name = "itemtypePriceServiceImpl")
	private ItemtypePriceService<ItemtypePrice> itemtypePriceService;
}
