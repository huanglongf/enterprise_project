package com.lmis.sys.user.controller;

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
import com.lmis.sys.user.model.SysUser;
import com.lmis.sys.user.service.SysUserServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysUserController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 12:51:12
 * 
 */
@Api(value = "权限模块-4.1用户管理")
@RestController
@RequestMapping(value="/sys")
public class SysUserController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name = "dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SysUser> dynamicSqlService;
	
	@Resource(name="sysUserServiceImpl")
	SysUserServiceInterface<SysUser> sysUserService;
	
	@ApiOperation(value="4.1.1用户查询")
	@RequestMapping(value = "/selectSysUser", method = RequestMethod.POST)
    public String selectSysUser(@ModelAttribute DynamicSqlParam<SysUser> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysUserService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.1.2用户新增")
	@RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public String addSysUser(@ModelAttribute DynamicSqlParam<SysUser> dynamicSqlParam) throws Exception {
		SysUser sysUser = dynamicSqlService.generateTableModel(dynamicSqlParam, new SysUser()).getTableModel();
        return JSON.toJSONString(sysUserService.executeInsert(sysUser));
    }

	@ApiOperation(value="4.1.3用户修改")
	@RequestMapping(value = "/updateSysUser", method = RequestMethod.POST)
    public String updateSysUser(@ModelAttribute DynamicSqlParam<SysUser> dynamicSqlParam) throws Exception {
		SysUser sysUser = dynamicSqlService.generateTableModel(dynamicSqlParam, new SysUser()).getTableModel();
        return JSON.toJSONString(sysUserService.executeUpdate(sysUser));
    }

	@ApiOperation(value="4.1.4用户删除")
	@RequestMapping(value = "/deleteSysUser", method = RequestMethod.POST)
    public String deleteSysUser(@ModelAttribute SysUser sysUser) throws Exception {
		return JSON.toJSONString(sysUserService.deleteSysUser(sysUser));
    }
	
	@ApiOperation(value="4.1.5用户查看")
	@RequestMapping(value = "/checkSysUser", method = RequestMethod.POST)
    public String checkSysUser(@ModelAttribute DynamicSqlParam<SysUser> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysUserService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.1.11修改密码")
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public String updatePwd(@ModelAttribute SysUser user) throws Exception {
        return JSON.toJSONString(sysUserService.updatePwd(user));
    }
}
