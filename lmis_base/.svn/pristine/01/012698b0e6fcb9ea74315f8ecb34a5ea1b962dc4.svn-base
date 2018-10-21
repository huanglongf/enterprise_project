package com.lmis.sys.userRoleOrg.controller;

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
import com.lmis.sys.org.model.SysOrg;
import com.lmis.sys.org.service.SysOrgServiceInterface;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;
import com.lmis.sys.userRoleOrg.service.SysUserRoleOrgServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysUserRoleOrgController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 17:10:23
 * 
 */
@Api(value = "权限模块-4.1用户管理")
@RestController
@RequestMapping(value="/sys")
public class SysUserRoleOrgController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysUserRoleOrgServiceImpl")
	SysUserRoleOrgServiceInterface<SysUserRoleOrg> sysUserRoleOrgService;
	
	@Resource(name="sysOrgServiceImpl")
	SysOrgServiceInterface<SysOrg> sysOrgService;
	
	
	@ApiOperation(value="4.1.9用户与机构关系查询")
	@RequestMapping(value = "/selectUserRoleOrgAndOrg", method = RequestMethod.POST)
    public String selectUserRoleOrgAndOrg(@ModelAttribute SysUserRoleOrg sysUserRoleOrg) throws Exception {
		Map<String,Object> resultMap=new HashMap<>();
		List<SysUserRoleOrg> userRoleOrgList = sysUserRoleOrgService.selectUserRoleOrg(sysUserRoleOrg);
		resultMap.put("userRoleOrgList", userRoleOrgList);
		SysOrg sysOrg = new SysOrg();
		List<SysOrg> orgList=sysOrgService.findOrg(sysOrg);
		resultMap.put("orgList", orgList);
		LmisResult<Map<String,Object>> lmisResult = new LmisResult<>();
		lmisResult.setData(resultMap);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
	
	
	@ApiOperation(value="4.1.10用户与机构关系保存")
	@RequestMapping(value = "/addUserRoleOrg", method = RequestMethod.POST)
    public String addUserRoleOrg(String orgList) throws Exception {
		List<SysUserRoleOrg> userRoleOrgList = JsonUtils.json2List(orgList, SysUserRoleOrg.class);
        return JSON.toJSONString(sysUserRoleOrgService.addUserRoleOrg(userRoleOrgList));
    }
	
}
