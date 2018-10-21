package com.lmis.sys.codeRule.controller;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.codeRule.model.SysCoderuleInfo;
import com.lmis.sys.codeRule.service.SysCoderuleInfoServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysCoderuleInfoController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-17 13:16:34
 * 
 */
@Api(value = "编号管理")
@RestController
@RequestMapping(value="/sys")
public class SysCoderuleInfoController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysCoderuleInfoServiceImpl")
	SysCoderuleInfoServiceInterface<SysCoderuleInfo> sysCoderuleInfoService;
	
	@Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysCoderuleInfo> dynamicSqlService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectSysCoderuleInfo", method = RequestMethod.POST)
    public String selectSysCoderuleInfo(@ModelAttribute DynamicSqlParam<SysCoderuleInfo> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysCoderuleInfoService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addSysCoderuleInfo", method = RequestMethod.POST)
    public String addSysCoderuleInfo(@ModelAttribute DynamicSqlParam<SysCoderuleInfo> dynamicSqlParam) throws Exception {
	    SysCoderuleInfo sysCoderuleRule = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleInfo>) dynamicSqlParam, new SysCoderuleInfo()).getTableModel();
	    return JSON.toJSONString(sysCoderuleInfoService.executeInsert(sysCoderuleRule));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateSysCoderuleInfo", method = RequestMethod.POST)
    public String updateSysCoderuleInfo(@ModelAttribute DynamicSqlParam<SysCoderuleInfo> dynamicSqlParam) throws Exception {
	    SysCoderuleInfo sysCoderuleRule = dynamicSqlService.generateTableModel((DynamicSqlParam<SysCoderuleInfo>) dynamicSqlParam, new SysCoderuleInfo()).getTableModel();
	    return JSON.toJSONString(sysCoderuleInfoService.executeUpdate(sysCoderuleRule));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteSysCoderuleInfo", method = RequestMethod.POST)
    public String deleteSysCoderuleInfo(@ModelAttribute SysCoderuleInfo sysCoderuleInfo) throws Exception {
		return JSON.toJSONString(sysCoderuleInfoService.deleteSysCoderuleInfo(sysCoderuleInfo));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkSysCoderuleInfo", method = RequestMethod.POST)
    public String checkSysCoderuleInfo(@ModelAttribute DynamicSqlParam<SysCoderuleInfo> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysCoderuleInfoService.executeSelect(dynamicSqlParam));
    }
	@ApiOperation(value="样例")
    @RequestMapping(value = "/demoSysCoderuleInfo", method = RequestMethod.POST)
    public String demoSysCoderuleInfo(@Param("code") String code) throws Exception {
        return JSON.toJSONString(sysCoderuleInfoService.TestCodeRule(code));
    }
	
}
