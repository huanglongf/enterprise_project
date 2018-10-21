package com.lmis.setup.page.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.page.model.SetupPage;
import com.lmis.setup.page.service.SetupPageServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SetupPageController
 * @Description: TODO(配置页面控制类)
 * @author Ian.Huang
 * @date 2017年12月28日 下午4:15:02 
 * 
 */
@Api(value = "公共配置-4.3页面管理")
@RestController
@RequestMapping(value="/setup")
public class SetupPageController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="setupPageServiceImpl")
	SetupPageServiceInterface<SetupPage> setupPageService;
	
	@ApiOperation(value="4.3.1查询配置页面")
	@RequestMapping(value = "/selectSetupPage", method = RequestMethod.POST)
    public String selectSetupPage(@ModelAttribute DynamicSqlParam<SetupPage> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupPageService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.3.2新增配置页面")
	@RequestMapping(value = "/addSetupPage", method = RequestMethod.POST)
    public String addSetupPage(@ModelAttribute DynamicSqlParam<SetupPage> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.3.3修改配置页面")
	@RequestMapping(value = "/updateSetupPage", method = RequestMethod.POST)
    public String updateSetupPage(@ModelAttribute DynamicSqlParam<SetupPage> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.3.4删除配置页面")
	@RequestMapping(value = "/deleteSetupPage", method = RequestMethod.POST)
    public String deleteSetupPage(@ModelAttribute SetupPage setupPage) throws Exception {
		return JSON.toJSONString(setupPageService.deleteSetupPage(setupPage));
    }
	
	@ApiOperation(value="4.3.5查看配置页面")
	@RequestMapping(value = "/checkSetupPage", method = RequestMethod.POST)
    public String checkSetupPage(@ModelAttribute DynamicSqlParam<SetupPage> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.3.6复制页面布局")
	@RequestMapping(value = "/copySetupPage", method = RequestMethod.POST)
    public String copySetupPage(@RequestParam("currentPageId") String currentPageId, @RequestParam("orignPageId") String orignPageId) throws Exception {
		return JSON.toJSONString(setupPageService.copySetupPage(currentPageId,orignPageId));
    }
}
