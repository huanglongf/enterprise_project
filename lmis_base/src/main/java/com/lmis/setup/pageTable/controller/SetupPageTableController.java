package com.lmis.setup.pageTable.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;
import com.lmis.setup.pageTable.service.SetupPageTableServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SetupPageTableController
 * @Description: TODO(页面查询结果控制层)
 * @author Ian.Huang
 * @date 2017年12月12日 下午8:05:06 
 * 
 */
@Api(value = "公共配置-4.4页面布局")
@RestController
@RequestMapping(value="/setup")
public class SetupPageTableController {

	@Resource(name="setupPageTableServiceImpl")
	SetupPageTableServiceInterface<SetupPageTable> setupPageTableService;
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@ApiOperation(value="4.4.11查询查询列表")
	@RequestMapping(value = "/selectSetupPageTable", method = RequestMethod.POST)
    public String selectSetupPageTable(@ModelAttribute ViewSetupPageTable viewSetupPageTable, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupPageTableService.queryPage(viewSetupPageTable, pageObject));
    }
	
	@ApiOperation(value="查询查询列表")
	@RequestMapping(value = "/selectSetupPageTableDynamic", method = RequestMethod.POST)
    public String selectSetupPageTable(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupPageTableService.executeSelect(dynamicSqlParam, pageObject));
    }
	
	@ApiOperation(value="4.4.12新增查询列表")
	@RequestMapping(value = "/addSetupPageTable", method = RequestMethod.POST)
    public String addSetupPageTable(@ModelAttribute SetupPageTable setupPageTable) throws Exception {
        return JSON.toJSONString(setupPageTableService.addSetupPageTable(setupPageTable));
    }
	
	@ApiOperation(value="4.4.12新增查询列表(动态传参)")
	@RequestMapping(value = "/addSetupPageTableDynamic", method = RequestMethod.POST)
    public String addSetupPageTableDynamic(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageTableService.executeInsert(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.4.13修改查询列表")
	@RequestMapping(value = "/updateSetupPageTable", method = RequestMethod.POST)
    public String updateSetupPageTable(@ModelAttribute SetupPageTable setupPageTable) throws Exception {
        return JSON.toJSONString(setupPageTableService.updateSetupPageTable(setupPageTable));
    }
	
	@ApiOperation(value="修改查询列表")
	@RequestMapping(value = "/updateSetupPageTableDynamic", method = RequestMethod.POST)
    public String updateSetupPageTable(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageTableService.executeUpdate(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.4.14删除查询列表")
	@RequestMapping(value = "/deleteSetupPageTable", method = RequestMethod.POST)
    public String deleteSetupPageTable(@ModelAttribute SetupPageTable setupPageTable) throws Exception {
        return JSON.toJSONString(setupPageTableService.deleteSetupPageTable(setupPageTable));
    }
	
	@ApiOperation(value="4.4.15查看查询列表")
	@RequestMapping(value = "/getSetupPageTable", method = RequestMethod.POST)
    public String getSetupPageTable(@ModelAttribute SetupPageTable setupPageTable) throws Exception {
        return JSON.toJSONString(setupPageTableService.getSetupPageTable(setupPageTable));
    }
	
	@ApiOperation(value="查看查询列表")
	@RequestMapping(value = "/getSetupPageTableDynamic", method = RequestMethod.POST)
    public String getSetupPageTable(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageTableService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="查看查询列表配置")
	@RequestMapping(value = "/checkSetupPageTable", method = RequestMethod.POST)
    public String checkSetupPageTable(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageTableService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.5.10同步页面列表数据")
	@RequestMapping(value = "/redisForPageTables", method = RequestMethod.POST)
    public String redisForPageTables() throws Exception {
        return JSON.toJSONString(setupPageTableService.redisForPageTablesPipeline());
    }
	
	@ApiOperation(value="统计列表数据")
	@RequestMapping(value = "/getSetupPageTablesCount", method = RequestMethod.POST)
    public String getSetupPageTablesCount(@ModelAttribute DynamicSqlParam<SetupPageTable> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupPageTableService.getSetupPageTablesCount(dynamicSqlParam));
    }
	
}
