package com.lmis.basis.fixedAssetsDetail.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.fixedAssetsDetail.model.BasisFixedAssetsDetail;
import com.lmis.basis.fixedAssetsDetail.service.BasisFixedAssetsDetailServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisFixedAssetsDetailController
 * @Description: TODO(固定资产明细控制层类)
 * @author codeGenerator
 * @date 2018-03-23 17:12:34
 * 
 */
@Api(value = "固定资产明细")
@RestController
@RequestMapping(value="/basis")
public class BasisFixedAssetsDetailController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisFixedAssetsDetailServiceImpl")
	BasisFixedAssetsDetailServiceInterface<BasisFixedAssetsDetail> basisFixedAssetsDetailService;
	
	/*@ApiOperation(value="4.16.1查询固定资产明细")
	@RequestMapping(value = "/selectBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String selectBasisFixedAssetsDetail(@ModelAttribute ViewBasisFixedAssetsDetail detail, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisFixedAssetsDetailService.selectBasisFixedAssetsDetail(detail, pageObject));
    }*/
	@ApiOperation(value="4.16.1查询固定资产明细")
	@RequestMapping(value = "/selectBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String selectBasisFixedAssetsDetail(@ModelAttribute DynamicSqlParam<BasisFixedAssetsDetail> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisFixedAssetsDetailService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增固定资产明细")
	@RequestMapping(value = "/addBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String addBasisFixedAssetsDetail(@ModelAttribute DynamicSqlParam<BasisFixedAssetsDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsDetailService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改固定资产明细")
	@RequestMapping(value = "/updateBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String updateBasisFixedAssetsDetail(@ModelAttribute DynamicSqlParam<BasisFixedAssetsDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsDetailService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除固定资产明细")
	@RequestMapping(value = "/deleteBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String deleteBasisFixedAssetsDetail(@ModelAttribute BasisFixedAssetsDetail basisFixedAssetsDetail) throws Exception {
		return JSON.toJSONString(basisFixedAssetsDetailService.deleteBasisFixedAssetsDetail(basisFixedAssetsDetail));
    }
	
	@ApiOperation(value="查看固定资产明细")
	@RequestMapping(value = "/checkBasisFixedAssetsDetail", method = RequestMethod.POST)
    public String checkBasisFixedAssetsDetail(@ModelAttribute DynamicSqlParam<BasisFixedAssetsDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsDetailService.executeSelect(dynamicSqlParam));
    }

    @ApiOperation(value="固定资产报废")
    @RequestMapping(value = "/basisFixedAssetsScrap", method = RequestMethod.POST)
    public String basisFixedAssetsScrap(String assetsCode,String expiryDate,String version) throws Exception {
        return JSON.toJSONString(basisFixedAssetsDetailService.basisFixedAssetsScrap(assetsCode,expiryDate,version));
    }
}
