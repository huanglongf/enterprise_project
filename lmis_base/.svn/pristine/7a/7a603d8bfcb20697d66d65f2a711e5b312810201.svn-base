package com.lmis.sys.org.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.org.model.SysOrg;
import com.lmis.sys.org.model.ViewSysOrg;
import com.lmis.sys.org.service.SysOrgServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysOrgController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 13:08:38
 * 
 */
@Api(value = "权限模块-4.3组织机构")
@RestController
@RequestMapping(value="/sys")
public class SysOrgController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysOrgServiceImpl")
	SysOrgServiceInterface<SysOrg> sysOrgService;
	
	@Resource(name = "dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SysOrg> dynamicSqlService;
	
	@ApiOperation(value="4.3.1机构查询")
	@RequestMapping(value = "/selectSysOrg", method = RequestMethod.POST)
    public String selectSysOrg(@ModelAttribute ViewSysOrg sysOrg) throws Exception {
		List<ViewSysOrg> orgList = sysOrgService.findOrg(sysOrg);
		LmisResult<List<ViewSysOrg>> lmisResult = new LmisResult<>();
		lmisResult.setData(orgList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }

	@ApiOperation(value="4.3.2机构保存/修改")
	@RequestMapping(value = "/addOrUpdateSysOrg", method = RequestMethod.POST)
    public String addOrUpdateSysOrg(@ModelAttribute DynamicSqlParam<SysOrg> dynamicSqlParam) throws Exception {
		SysOrg org = dynamicSqlService.generateTableModel(dynamicSqlParam, new SysOrg()).getTableModel();
        return JSON.toJSONString(sysOrgService.addOrUpdateSysOrg(org));
    }


	@ApiOperation(value="4.3.3机构删除")
	@RequestMapping(value = "/deleteSysOrg", method = RequestMethod.POST)
    public String deleteSysOrg(@ModelAttribute SysOrg sysOrg) throws Exception {
		return JSON.toJSONString(sysOrgService.deleteSysOrg(sysOrg));
    }
	
	@ApiOperation(value="4.3.4机构查看")
	@RequestMapping(value = "/checkSysOrg", method = RequestMethod.POST)
    public String checkSysOrg(@ModelAttribute DynamicSqlParam<SysOrg> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysOrgService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.7.6启用组织机构/4.7.7禁用组织机构")
	@RequestMapping(value = "/switchSysOrg", method = RequestMethod.POST)
    public String switchSysOrg(@ModelAttribute SysOrg SysOrg) throws Exception {
		return JSON.toJSONString(sysOrgService.switchSysOrg(SysOrg));
    }
	
	@ApiOperation(value="4.7.13下拉框查询组织机构")
	@RequestMapping(value = "/findSysOrg", method = RequestMethod.POST)
    public String findSysOrg(@ModelAttribute SysOrg sysOrg) throws Exception {
		List<SysOrg> orgList = sysOrgService.findOrg(sysOrg);
		LmisResult<List<SysOrg>> lmisResult = new LmisResult<>();
		lmisResult.setData(orgList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
	
	@ApiOperation(value="4.7.7查询下级组织机构")
	@RequestMapping(value = "/findSubordinateSysOrg", method = RequestMethod.POST)
    public String findSubordinateSysOrg(@ModelAttribute SysOrg sysOrg) throws Exception {
		List<SysOrg> orgList = sysOrgService.findSubordinateSysOrg(sysOrg);
		LmisResult<List<SysOrg>> lmisResult = new LmisResult<>();
		lmisResult.setData(orgList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
	
	
	@RequestMapping(value = "/exportOrg", method = RequestMethod.POST)
    public String exportOrg(int pageSize) throws Exception {
        return JSON.toJSONString(sysOrgService.exportOrg(pageSize));
    }
	
	@ApiOperation(value="4.13.39查询负责组织机构上级")
	@RequestMapping(value = "/getSysOrg", method = RequestMethod.POST)
    public String getSysOrg(@ModelAttribute SysOrg sysOrg) throws Exception {
		return JSON.toJSONString(sysOrgService.getSysOrg(sysOrg));
    }
	
}
