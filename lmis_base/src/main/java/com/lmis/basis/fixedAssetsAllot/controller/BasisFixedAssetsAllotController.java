package com.lmis.basis.fixedAssetsAllot.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.fixedAssetsAllot.model.BasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.model.ViewBasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.service.BasisFixedAssetsAllotServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisFixedAssetsAllotController
 * @Description: TODO(固定资产调拨日志控制层类)
 * @author codeGenerator
 * @date 2018-04-02 10:47:58
 * 
 */
@Api(value = "固定资产调拨日志")
@RestController
@RequestMapping(value="/basis")
public class BasisFixedAssetsAllotController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisFixedAssetsAllotServiceImpl")
	BasisFixedAssetsAllotServiceInterface<BasisFixedAssetsAllot> basisFixedAssetsAllotService;
	
	@ApiOperation(value="4.16.3查询固定资产调拨日志")
	@RequestMapping(value = "/selectBasisFixedAssetsAllot", method = RequestMethod.POST)
    public String selectBasisFixedAssetsAllot(@ModelAttribute ViewBasisFixedAssetsAllot allot, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisFixedAssetsAllotService.selectBasisFixedAssetsAllot(allot, pageObject));
    }

	@ApiOperation(value="新增固定资产调拨日志")
	@RequestMapping(value = "/addBasisFixedAssetsAllot", method = RequestMethod.POST)
    public String addBasisFixedAssetsAllot(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAllot> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAllotService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改固定资产调拨日志")
	@RequestMapping(value = "/updateBasisFixedAssetsAllot", method = RequestMethod.POST)
    public String updateBasisFixedAssetsAllot(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAllot> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAllotService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除固定资产调拨日志")
	@RequestMapping(value = "/deleteBasisFixedAssetsAllot", method = RequestMethod.POST)
    public String deleteBasisFixedAssetsAllot(@ModelAttribute BasisFixedAssetsAllot basisFixedAssetsAllot) throws Exception {
		return JSON.toJSONString(basisFixedAssetsAllotService.deleteBasisFixedAssetsAllot(basisFixedAssetsAllot));
    }
	
	@ApiOperation(value="查看固定资产调拨日志")
	@RequestMapping(value = "/checkBasisFixedAssetsAllot", method = RequestMethod.POST)
    public String checkBasisFixedAssetsAllot(@ModelAttribute DynamicSqlParam<BasisFixedAssetsAllot> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsAllotService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.16.4固定资产调拨")
	@RequestMapping(value = "/transferFixedAssets", method = RequestMethod.POST)
    public String transferFixedAssets(@ModelAttribute BasisFixedAssetsAllot basisFixedAssetsAllot) throws Exception {
		return JSON.toJSONString(basisFixedAssetsAllotService.transferFixedAssets(basisFixedAssetsAllot));
    }
	
}
