package com.lmis.sys.userRole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dataFormat.JsonUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.role.model.SysRole;
import com.lmis.sys.role.service.SysRoleServiceInterface;
import com.lmis.sys.userRole.model.SysUserRole;
import com.lmis.sys.userRole.service.SysUserRoleServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysUserRoleController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 16:43:38
 * 
 */
@Api(value = "权限模块-4.1用户管理")
@RestController
@RequestMapping(value="/sys")
public class SysUserRoleController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysUserRoleServiceImpl")
	SysUserRoleServiceInterface<SysUserRole> sysUserRoleService;
	
	@Resource(name="sysRoleServiceImpl")
	SysRoleServiceInterface<SysRole> sysRoleService;

	@ApiOperation(value="4.1.7用户角色关系新增")
	@RequestMapping(value = "/addSysUserRole", method = RequestMethod.POST)
    public String addSysUserRole(String userRoles) throws Exception {
		List<SysUserRole> userRoleList = JsonUtils.json2List(userRoles, SysUserRole.class);
        return JSON.toJSONString(sysUserRoleService.addUserRole(userRoleList));
    }


	@ApiOperation(value="4.1.8用户角色关系删除")
	@RequestMapping(value = "/deleteSysUserRole", method = RequestMethod.POST)
    public String deleteSysUserRole(String userRoles) throws Exception {
		List<SysUserRole> userRoleList = JsonUtils.json2List(userRoles, SysUserRole.class);
		return JSON.toJSONString(sysUserRoleService.deleteUserRole(userRoleList));
    }
	
	
	@ApiOperation(value="4.1.6用户角色关系查询")
	@RequestMapping(value = "/findSysUserRole", method = RequestMethod.POST)
    public String findSysUserRole(@ModelAttribute SysUserRole  sysUserRole) throws Exception {
		Map<String,Object> resultMap=new HashMap<>();
		//用户对应的角色信息
		List<SysUserRole> userRoleList=sysUserRoleService.findUserRoleList(sysUserRole);
		resultMap.put("userRoleList", userRoleList);
		//角色信息
		List<SysRole> roleList=sysRoleService.findRole();
		resultMap.put("roleList", roleList);
		LmisResult<Map<String,Object>> lmisResult=new LmisResult<>();
		lmisResult.setData(resultMap);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
	
}
