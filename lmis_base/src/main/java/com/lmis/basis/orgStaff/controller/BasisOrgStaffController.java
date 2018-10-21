package com.lmis.basis.orgStaff.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.orgStaff.model.BasisOrgStaff;
import com.lmis.basis.orgStaff.model.ViewBasisOrgStaff;
import com.lmis.basis.orgStaff.service.BasisOrgStaffServiceInterface;
import com.lmis.common.dataFormat.JsonUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.org.model.SysOrg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisOrgStaffController
 * @Description: TODO(成本中心与员工关系控制层类)
 * @author codeGenerator
 * @date 2018-01-24 10:05:26
 * 
 */
@Api(value = "基础模块-4.7成本中心与员工关系")
@RestController
@RequestMapping(value="/basis")
public class BasisOrgStaffController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisOrgStaffServiceImpl")
	BasisOrgStaffServiceInterface<BasisOrgStaff> basisOrgStaffService;
	
	
	@ApiOperation(value="4.7.1查看组织机构")
	@RequestMapping(value = "/checkSysOrg", method = RequestMethod.POST)
    public String checkSysOrg(@ModelAttribute SysOrg sysOrg) throws Exception {
		return JSON.toJSONString(basisOrgStaffService.checkSysOrg(sysOrg));
    }
	
	
	@ApiOperation(value="4.7.3查询组织机构员工")
	@RequestMapping(value = "/selectBasisOrgStaff", method = RequestMethod.POST)
    public String selectBasisOrgStaff(@ModelAttribute DynamicSqlParam<BasisOrgStaff> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisOrgStaffService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.7.4新增组织机构员工")
	@RequestMapping(value = "/addBasisOrgStaff", method = RequestMethod.POST)
    public String addBasisOrgStaff(String jsonList) throws Exception {
		List<BasisOrgStaff> orgStaffList = JsonUtils.json2List(jsonList, BasisOrgStaff.class);
        return JSON.toJSONString(basisOrgStaffService.addBasisOrgStaff(orgStaffList));
    }


	@ApiOperation(value="4.7.5删除组织机构员工")
	@RequestMapping(value = "/deleteBasisOrgStaff", method = RequestMethod.POST)
    public String deleteBasisOrgStaff(@ModelAttribute BasisOrgStaff basisOrgStaff) throws Exception {
		return JSON.toJSONString(basisOrgStaffService.deleteBasisOrgStaff(basisOrgStaff));
    }
	
	@ApiOperation(value="4.7.6清空组织机构员工")
	@RequestMapping(value = "/clearBasisOrgStaff", method = RequestMethod.POST)
    public String clearBasisOrgStaff(@ModelAttribute BasisOrgStaff basisOrgStaff) throws Exception {
        return JSON.toJSONString(basisOrgStaffService.clearBasisOrgStaff(basisOrgStaff));
    }
	
	@ApiOperation(value="4.7.8查询组织机构员工（下拉框用）")
	@RequestMapping(value = "/findOrgStaffByOrgId", method = RequestMethod.POST)
    public String findOrgStaffByOrgId(@ModelAttribute ViewBasisOrgStaff basisOrgStaff) throws Exception {
        return JSON.toJSONString(basisOrgStaffService.findOrgStaffByOrgId(basisOrgStaff));
    }
	
}
