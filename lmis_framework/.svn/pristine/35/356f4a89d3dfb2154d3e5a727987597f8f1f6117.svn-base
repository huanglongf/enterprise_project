package com.lmis.sys.codeRule.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.codeRule.model.SysCoderuleDeposit;
import com.lmis.sys.codeRule.service.SysCoderuleDepositServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysCoderuleDepositController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-17 13:16:21
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/sys")
public class SysCoderuleDepositController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysCoderuleDepositServiceImpl")
	SysCoderuleDepositServiceInterface<SysCoderuleDeposit> sysCoderuleDepositService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectSysCoderuleDeposit", method = RequestMethod.POST)
    public String selectSysCoderuleDeposit(@ModelAttribute DynamicSqlParam<SysCoderuleDeposit> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysCoderuleDepositService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addSysCoderuleDeposit", method = RequestMethod.POST)
    public String addSysCoderuleDeposit(@ModelAttribute DynamicSqlParam<SysCoderuleDeposit> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleDepositService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateSysCoderuleDeposit", method = RequestMethod.POST)
    public String updateSysCoderuleDeposit(@ModelAttribute DynamicSqlParam<SysCoderuleDeposit> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleDepositService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteSysCoderuleDeposit", method = RequestMethod.POST)
    public String deleteSysCoderuleDeposit(@ModelAttribute SysCoderuleDeposit sysCoderuleDeposit) throws Exception {
		return JSON.toJSONString(sysCoderuleDepositService.deleteSysCoderuleDeposit(sysCoderuleDeposit));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkSysCoderuleDeposit", method = RequestMethod.POST)
    public String checkSysCoderuleDeposit(@ModelAttribute DynamicSqlParam<SysCoderuleDeposit> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleDepositService.executeSelect(dynamicSqlParam));
    }
	
}
