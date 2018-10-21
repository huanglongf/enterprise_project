package com.lmis.sys.codeRule.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.codeRule.model.SysCoderuleData;
import com.lmis.sys.codeRule.service.SysCoderuleDataServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysCoderuleDataController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-17 13:16:02
 * 
 */
@Api(value = "编号管理(配置数据)")
@RestController
@RequestMapping(value="/sys")
public class SysCoderuleDataController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysCoderuleDataServiceImpl")
	SysCoderuleDataServiceInterface<SysCoderuleData> sysCoderuleDataService;
	@Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysCoderuleData> dynamicSqlService;
	
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectSysCoderuleData", method = RequestMethod.POST)
    public String selectSysCoderuleData(@ModelAttribute DynamicSqlParam<SysCoderuleData> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysCoderuleDataService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addSysCoderuleData", method = RequestMethod.POST)
    public String addSysCoderuleData(@ModelAttribute DynamicSqlParam<SysCoderuleData> dynamicSqlParam) throws Exception {
	    SysCoderuleData sysCoderuleData = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleData>) dynamicSqlParam, new SysCoderuleData()).getTableModel();
	    return JSON.toJSONString(sysCoderuleDataService.executeInsert(sysCoderuleData));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateSysCoderuleData", method = RequestMethod.POST)
    public String updateSysCoderuleData(@ModelAttribute DynamicSqlParam<SysCoderuleData> dynamicSqlParam) throws Exception {
        SysCoderuleData sysCoderuleData = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleData>) dynamicSqlParam, new SysCoderuleData()).getTableModel();
	    return JSON.toJSONString(sysCoderuleDataService.executeUpdate(sysCoderuleData));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteSysCoderuleData", method = RequestMethod.POST)
    public String deleteSysCoderuleData(@ModelAttribute SysCoderuleData sysCoderuleData) throws Exception {
		return JSON.toJSONString(sysCoderuleDataService.deleteSysCoderuleData(sysCoderuleData));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkSysCoderuleData", method = RequestMethod.POST)
    public String checkSysCoderuleData(@ModelAttribute DynamicSqlParam<SysCoderuleData> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleDataService.executeSelect(dynamicSqlParam));
    }
	
	
	
	
	
	
}
