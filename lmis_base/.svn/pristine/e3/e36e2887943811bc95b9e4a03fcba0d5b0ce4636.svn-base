package com.lmis.basis.fixedAssetsAmt.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.basis.fixedAssetsAmt.model.BasisFixedAssetsAmt;
import com.lmis.basis.fixedAssetsAmt.service.BasisFixedAssetsAmtServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisFixedAssetsAmtController
 * @Description: TODO(固定资产摊销控制层类)
 * @author codeGenerator
 * @date 2018-02-26 16:49:11
 * 
 */
@Api(value = "固定资产摊销")
@RestController
@RequestMapping(value="/basis")
public class BasisFixedAssetsAmtController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisFixedAssetsAmtServiceImpl")
	BasisFixedAssetsAmtServiceInterface<BasisFixedAssetsAmt> basisFixedAssetsAmtService;
	
	@ApiOperation(value="4.9.11查询固定资产摊销明细")
	@RequestMapping(value = "/selectBasisFixedAssetsAmt", method = RequestMethod.POST)
    public String selectBasisFixedAssetsAmt(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAmt> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisFixedAssetsAmtService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.9.11查询固定资产摊销明细")
	@RequestMapping(value = "/findAllAmtOrg", method = RequestMethod.POST)
    public String findAllAmtOrg(String assetsCode) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAmtService.findAllAmtOrg(assetsCode));
    }
	
	
	@ApiOperation(value="新增固定资产摊销")
	@RequestMapping(value = "/addBasisFixedAssetsAmt", method = RequestMethod.POST)
    public String addBasisFixedAssetsAmt(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAmt> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAmtService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改固定资产摊销")
	@RequestMapping(value = "/updateBasisFixedAssetsAmt", method = RequestMethod.POST)
    public String updateBasisFixedAssetsAmt(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAmt> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAmtService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除固定资产摊销")
	@RequestMapping(value = "/deleteBasisFixedAssetsAmt", method = RequestMethod.POST)
    public String deleteBasisFixedAssetsAmt(@ModelAttribute BasisFixedAssetsAmt basisFixedAssetsAmt) throws Exception {
		return JSON.toJSONString(basisFixedAssetsAmtService.deleteBasisFixedAssetsAmt(basisFixedAssetsAmt));
    }
	
	@ApiOperation(value="查看固定资产摊销")
	@RequestMapping(value = "/checkBasisFixedAssetsAmt", method = RequestMethod.POST)
    public String checkBasisFixedAssetsAmt(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAmt> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAmtService.executeSelect(dynamicSqlParam));
    }
	
}
