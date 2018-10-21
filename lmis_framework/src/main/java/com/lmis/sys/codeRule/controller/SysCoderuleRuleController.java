package com.lmis.sys.codeRule.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.constant.BaseConstant;
import com.lmis.sys.codeRule.model.SysCoderuleRule;
import com.lmis.sys.codeRule.service.SysCoderuleRuleServiceInterface;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysCoderuleRuleController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-18 16:01:27
 * 
 */
@Api(value = "规则管理")
@RestController
@RequestMapping(value="/sys")
public class SysCoderuleRuleController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysCoderuleRuleServiceImpl")
	SysCoderuleRuleServiceInterface<SysCoderuleRule> sysCoderuleRuleService;
	
	@Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysCoderuleRule> dynamicSqlService;
	@Autowired
	private BaseUtils baseUtils;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectSysCoderuleRule", method = RequestMethod.POST)
    public String selectSysCoderuleRule(@ModelAttribute DynamicSqlParam<SysCoderuleRule> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysCoderuleRuleService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addSysCoderuleRule", method = RequestMethod.POST)
    public String addSysCoderuleRule(@ModelAttribute DynamicSqlParam<SysCoderuleRule> dynamicSqlParam) throws Exception {
	 // TODO(业务校验)
        SysCoderuleRule sysUser = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleRule>) dynamicSqlParam, new SysCoderuleRule()).getTableModel();
	    return JSON.toJSONString(sysCoderuleRuleService.executeInsert(sysUser));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateSysCoderuleRule", method = RequestMethod.POST)
    public String updateSysCoderuleRule(@ModelAttribute DynamicSqlParam<SysCoderuleRule> dynamicSqlParam) throws Exception {
	    SysCoderuleRule sysUser = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleRule>) dynamicSqlParam, new SysCoderuleRule()).getTableModel();
	    return JSON.toJSONString(sysCoderuleRuleService.executeUpdate(sysUser));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteSysCoderuleRule", method = RequestMethod.POST)
    public String deleteSysCoderuleRule(@ModelAttribute SysCoderuleRule sysCoderuleRule) throws Exception {
		return JSON.toJSONString(sysCoderuleRuleService.deleteSysCoderuleRule(sysCoderuleRule));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkSysCoderuleRule", method = RequestMethod.POST)
    public String checkSysCoderuleRule(@ModelAttribute DynamicSqlParam<SysCoderuleRule> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleRuleService.executeSelect(dynamicSqlParam));
    }
	
	/**
     * @Description:获取规则数据类型和名称、编号
     * @author: xuyisu
     * @return
	 * @throws Exception 
     * @date: 2018-05-23 10:32:25
     */
	@ApiOperation(value="获取规则名称")
    @RequestMapping(value = "/getRuleDataType", method = RequestMethod.POST)
	 public String getRuleDataType()
	 {
	    return JSON.toJSONString(sysCoderuleRuleService.getRuleDataType());
	 }
	
	
	
	
	/**
	 * 仅仅测试异常
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/testException", method = RequestMethod.POST)
    public String testException() throws Exception
    {
	    System.out.println(baseUtils.GetMessage(BaseConstant.EBASE000006));
       //throw new Exception(BaseConstant.EBASE000006);
	    System.out.println(baseUtils.GetCodeRule("A01"));
	    LmisResult<String>  lmisResult=new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS,baseUtils.GetMessage(BaseConstant.EBASE000006));
	    return JSONUtil.toJsonStr(lmisResult);
    }
	
}
