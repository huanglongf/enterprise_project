package com.lmis.basis.staff.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.staff.model.BasisStaff;
import com.lmis.basis.staff.service.BasisStaffServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisStaffController
 * @Description: TODO(员工控制层类)
 * @author codeGenerator
 * @date 2018-01-19 16:11:56
 * 
 */
@Api(value = "基础模块-4.6员工")
@RestController
@RequestMapping(value="/basis")
public class BasisStaffController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisStaffServiceImpl")
	BasisStaffServiceInterface<BasisStaff> basisStaffService;
	
	@ApiOperation(value="4.6.1查询员工")
	@RequestMapping(value = "/selectBasisStaff", method = RequestMethod.POST)
    public String selectBasisStaff(@ModelAttribute DynamicSqlParam<BasisStaff> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisStaffService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.6.2新增员工")
	@RequestMapping(value = "/addBasisStaff", method = RequestMethod.POST)
    public String addBasisStaff(@ModelAttribute DynamicSqlParam<BasisStaff> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStaffService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.6.3修改员工")
	@RequestMapping(value = "/updateBasisStaff", method = RequestMethod.POST)
    public String updateBasisStaff(@ModelAttribute DynamicSqlParam<BasisStaff> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStaffService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.6.4删除员工")
	@RequestMapping(value = "/deleteBasisStaff", method = RequestMethod.POST)
    public String deleteBasisStaff(@ModelAttribute BasisStaff basisStaff) throws Exception {
		return JSON.toJSONString(basisStaffService.deleteBasisStaff(basisStaff));
    }
	
	@ApiOperation(value="4.6.5查看员工")
	@RequestMapping(value = "/checkBasisStaff", method = RequestMethod.POST)
    public String checkBasisStaff(@ModelAttribute DynamicSqlParam<BasisStaff> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStaffService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.6.6启用员工/4.6.7禁用员工")
	@RequestMapping(value = "/switchBasisStaff", method = RequestMethod.POST)
    public String switchBasisStaff(@ModelAttribute BasisStaff basisStaff) throws Exception {
		return JSON.toJSONString(basisStaffService.switchBasisStaff(basisStaff));
    }
	
	@ApiOperation(value="4.6.10查询成本中心外可选员工")
	@RequestMapping(value = "/filterBasisStaff", method = RequestMethod.POST)
	public String filterBasisStaff(@ModelAttribute DynamicSqlParam<BasisStaff> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		/*return JSON.toJSONString(basisStaffService.filterBasisStaff(basisStaff));*/
		
		pageObject.setDefaultPage(defPageNum, defPageSize);
		return JSON.toJSONString(basisStaffService.filterBasisStaff(dynamicSqlParam,pageObject));
    }
	
}
