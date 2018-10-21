package com.bt.workOrder.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.workOrder.model.GenerationRule;
import com.bt.workOrder.service.GenerationRuleService;
/**
 * 工单生成规则控制器
 *
 */
@Controller
@RequestMapping("/control/workOrder/generationRuleController")
public class GenerationRuleController extends BaseController {

	private static final Logger logger = Logger.getLogger(GenerationRuleController.class);
	
	/**
	 * 工单生成规则服务类
	 */
	@Resource(name = "generationRuleServiceImpl")
	private GenerationRuleService<GenerationRule> generationRuleService;
}
