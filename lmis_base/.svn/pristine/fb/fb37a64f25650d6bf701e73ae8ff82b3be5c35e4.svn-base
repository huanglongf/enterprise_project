package com.lmis.sys.role.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.role.model.SysRole;
import com.lmis.sys.role.service.SysRoleServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysRoleController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 13:08:09
 * 
 */
@Api(value = "权限模块-4.2角色管理")
@RestController
@RequestMapping(value="/sys")
public class SysRoleController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysRoleServiceImpl")
	SysRoleServiceInterface<SysRole> sysRoleService;
	
	@ApiOperation(value="4.2.1角色查询")
	@RequestMapping(value = "/selectSysRole", method = RequestMethod.POST)
    public String selectSysRole(@ModelAttribute DynamicSqlParam<SysRole> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
 		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysRoleService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.2.2角色新增")
	@RequestMapping(value = "/addSysRole", method = RequestMethod.POST)
    public String addSysRole(@ModelAttribute DynamicSqlParam<SysRole> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysRoleService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.2.3角色修改")
	@RequestMapping(value = "/updateSysRole", method = RequestMethod.POST)
    public String updateSysRole(@ModelAttribute DynamicSqlParam<SysRole> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysRoleService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.2.4角色删除")
	@RequestMapping(value = "/deleteSysRole", method = RequestMethod.POST)
    public String deleteSysRole(@ModelAttribute SysRole sysRole) throws Exception {
		return JSON.toJSONString(sysRoleService.deleteSysRole(sysRole));
    }
	
	@ApiOperation(value="4.2.5角色查看")
	@RequestMapping(value = "/checkSysRole", method = RequestMethod.POST)
    public String checkSysRole(@ModelAttribute DynamicSqlParam<SysRole> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysRoleService.executeSelect(dynamicSqlParam));
    }
	
}
