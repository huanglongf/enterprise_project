package com.lmis.basis.costCenterOrg.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.costCenterOrg.model.BasisCostCenterOrg;
import com.lmis.basis.costCenterOrg.service.BasisCostCenterOrgServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisCostCenterOrgController
 * @Description: TODO(成本中心与组织机构关系控制层类)
 * @author codeGenerator
 * @date 2018-02-09 11:07:28
 * 
 */
@Api(value = "成本中心-4.8成本中心与组织机构关系")
@RestController
@RequestMapping(value="/basis")
public class BasisCostCenterOrgController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisCostCenterOrgServiceImpl")
	BasisCostCenterOrgServiceInterface<BasisCostCenterOrg> basisCostCenterOrgService;
	
	@ApiOperation(value="查询成本中心与组织机构关系")
	@RequestMapping(value = "/selectBasisCostCenterOrg", method = RequestMethod.POST)
    public String selectBasisCostCenterOrg(@ModelAttribute DynamicSqlParam<BasisCostCenterOrg> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisCostCenterOrgService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.8.11 新增成本中心与组织机构关系")
	@RequestMapping(value = "/addBasisCostCenterOrg", method = RequestMethod.POST)
    public String addBasisCostCenterOrg(@ModelAttribute BasisCostCenterOrg costCenterOrg) throws Exception {
		LmisResult<BasisCostCenterOrg> result=new LmisResult<BasisCostCenterOrg>();
		basisCostCenterOrgService.addBasisCostCenterOrg(costCenterOrg);
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return JSON.toJSONString(result);
    }

	@ApiOperation(value="4.8.12 修改成本中心与组织机构关系")
	@RequestMapping(value = "/updateBasisCostCenterOrg", method = RequestMethod.POST)
    public String updateBasisCostCenterOrg(@ModelAttribute BasisCostCenterOrg costCenterOrg) throws Exception {
		LmisResult<BasisCostCenterOrg> result=new LmisResult<BasisCostCenterOrg>();
		basisCostCenterOrgService.updateBasisCostCenterOrg(costCenterOrg);
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(result);
    }

	@ApiOperation(value="删除成本中心与组织机构关系")
	@RequestMapping(value = "/deleteBasisCostCenterOrg", method = RequestMethod.POST)
    public String deleteBasisCostCenterOrg(@ModelAttribute BasisCostCenterOrg basisCostCenterOrg) throws Exception {
		LmisResult<BasisCostCenterOrg> result=new LmisResult<BasisCostCenterOrg>();
		basisCostCenterOrgService.deleteBasisCostCenterOrg(basisCostCenterOrg);
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(result);
    }
	
	@ApiOperation(value="查看成本中心与组织机构关系")
	@RequestMapping(value = "/checkBasisCostCenterOrg", method = RequestMethod.POST)
    public String checkBasisCostCenterOrg(@ModelAttribute DynamicSqlParam<BasisCostCenterOrg> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisCostCenterOrgService.executeSelect(dynamicSqlParam));
    }

}
