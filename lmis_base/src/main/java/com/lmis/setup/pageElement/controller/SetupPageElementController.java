package com.lmis.setup.pageElement.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageElement.service.SetupPageElementServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SetupPageElementController
 * @Description: TODO(页面元素控制层)
 * @author Ian.Huang
 * @date 2017年12月11日 上午11:29:04 
 * 
 */
@Api(value = "公共配置-4.4页面布局")
@RestController
@RequestMapping(value="/setup")
public class SetupPageElementController {

	@Resource(name="setupPageElementServiceImpl")
	SetupPageElementServiceInterface<SetupPageElement> setupPageElementService;
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@ApiOperation(value="4.4.6查询页面元素")
	@RequestMapping(value = "/selectSetupPageElement", method = RequestMethod.POST)
    public String selectSetupPageElement(@ModelAttribute ViewSetupPageElement viewSetupPageElement, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupPageElementService.queryPage(viewSetupPageElement, pageObject));
    }
	
	@ApiOperation(value="查询页面元素")
	@RequestMapping(value = "/selectSetupPageElementDynamic", method = RequestMethod.POST)
    public String selectSetupPageElement(@ModelAttribute DynamicSqlParam<SetupPageElement> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupPageElementService.executeSelect(dynamicSqlParam, pageObject));
    }
	
	@ApiOperation(value="4.4.7新增页面元素")
	@RequestMapping(value = "/addSetupPageElement", method = RequestMethod.POST)
    public String addSetupPageElement(@ModelAttribute SetupPageElement setupPageElement) throws Exception {
        return JSON.toJSONString(setupPageElementService.addSetupPageElement(setupPageElement));
    }
	
	@ApiOperation(value="4.4.7新增页面元素(动态传参)")
	@RequestMapping(value = "/addSetupPageElementDynamic", method = RequestMethod.POST)
    public String addSetupPageElementDynamic(@ModelAttribute DynamicSqlParam<SetupPageElement> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageElementService.executeInsert(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.4.8修改页面元素")
	@RequestMapping(value = "/updateSetupPageElement", method = RequestMethod.POST)
    public String updateSetupPageElement(@ModelAttribute SetupPageElement setupPageElement) throws Exception {
        return JSON.toJSONString(setupPageElementService.updateSetupPageElement(setupPageElement));
    }
	
	@ApiOperation(value="修改页面元素")
	@RequestMapping(value = "/updateSetupPageElementDynamic", method = RequestMethod.POST)
    public String updateSetupPageElement(@ModelAttribute DynamicSqlParam<SetupPageElement> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageElementService.executeUpdate(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.4.9删除页面元素")
	@RequestMapping(value = "/deleteSetupPageElement", method = RequestMethod.POST)
    public String deleteSetupPageElement(@ModelAttribute SetupPageElement setupPageElement) throws Exception {
        return JSON.toJSONString(setupPageElementService.deleteSetupPageElement(setupPageElement));
    }
	
	@ApiOperation(value="4.4.10查看页面元素")
	@RequestMapping(value = "/getSetupPageElement", method = RequestMethod.POST)
    public String getSetupPageElement(@ModelAttribute SetupPageElement setupPageElement) throws Exception {
        return JSON.toJSONString(setupPageElementService.getSetupPageElement(setupPageElement));
    }
	
	@ApiOperation(value="查看页面元素")
	@RequestMapping(value = "/getSetupPageElementDynamic", method = RequestMethod.POST)
    public String getSetupPageElement(@ModelAttribute DynamicSqlParam<SetupPageElement> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageElementService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="查看页面元素配置")
	@RequestMapping(value = "/checkSetupPageElement", method = RequestMethod.POST)
    public String checkSetupPageElement(@ModelAttribute DynamicSqlParam<SetupPageElement> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageElementService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.5.9同步页面元素数据")
	@RequestMapping(value = "/redisForPageElements", method = RequestMethod.POST)
    public String redisForPageElements() throws Exception {
        return JSON.toJSONString(setupPageElementService.redisForPageElementsMset());
    }
	
}
