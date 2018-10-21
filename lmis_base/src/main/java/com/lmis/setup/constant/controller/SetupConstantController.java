package com.lmis.setup.constant.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.setup.constant.model.SetupConstant;
import com.lmis.setup.constant.service.SetupConstantServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Api(value = "公共配置-4.1系统常量")
@RestController
@RequestMapping(value="/setup")
public class SetupConstantController {

	@Resource(name="setupConstantServiceImpl")
	SetupConstantServiceInterface<SetupConstant> setupConstantService;
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;

	@ApiOperation(value="4.1.1查询系统常量")
	@RequestMapping(value = "/selectSetupConstant", method = RequestMethod.POST)
    public String selectSetupConstant(@ModelAttribute SetupConstant setupConstant, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupConstantService.queryPage(setupConstant, pageObject));
    }

	@ApiOperation(value="4.1.2新增系统常量")
	@RequestMapping(value = "/addSetupConstant", method = RequestMethod.POST)
    public String addSetupConstant(@ModelAttribute SetupConstant setupConstant) throws Exception {
        return JSON.toJSONString(setupConstantService.addSetupConstant(setupConstant));
    }

	@ApiOperation(value="4.1.3修改系统常量")
	@RequestMapping(value = "/updateSetupConstant", method = RequestMethod.POST)
    public String updateSetupConstant(@ModelAttribute SetupConstant setupConstant) throws Exception {
        return JSON.toJSONString(setupConstantService.updateSetupConstant(setupConstant));
    }

	@ApiOperation(value="4.1.4删除系统常量")
	@RequestMapping(value = "/deleteSetupConstant", method = RequestMethod.POST)
    public String deleteSetupConstant(@ModelAttribute SetupConstant setupConstant) throws Exception {
        return JSON.toJSONString(setupConstantService.deleteSetupConstant(setupConstant));
    }
	
	@ApiOperation(value="4.1.5查看系统常量")
	@RequestMapping(value = "/checkSetupConstant", method = RequestMethod.POST)
    public String checkSetupConstant(@ModelAttribute SetupConstant setupConstant) throws Exception {
        return JSON.toJSONString(setupConstantService.checkSetupConstant(setupConstant));
    }
	
}
