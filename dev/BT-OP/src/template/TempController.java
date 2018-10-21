package ${modulePackage}.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import ${modulePackage}.model.${className};
import ${modulePackage}.service.${className}Service;
/**
 * ${codeName}控制器
 *
 */
@Controller
@RequestMapping("/control/${moduleSimplePackage}/${lowerName}Controller")
public class ${className}Controller extends BaseController {

	private static final Logger logger = Logger.getLogger(${className}Controller.class);
	
	/**
	 * ${codeName}服务类
	 */
	@Resource(name = "${lowerName}ServiceImpl")
	private ${className}Service<${className}> ${lowerName}Service;
}
