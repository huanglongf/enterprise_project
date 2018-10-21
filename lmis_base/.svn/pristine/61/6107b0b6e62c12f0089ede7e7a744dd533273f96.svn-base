package com.lmis.basis.fixedAssets.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.fixedAssets.model.BasisFixedAssets;
import com.lmis.basis.fixedAssets.model.ViewBasisFixedAssets;
import com.lmis.basis.fixedAssets.service.BasisFixedAssetsServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisFixedAssetsController
 * @Description: TODO(固定资产控制层类)
 * @author codeGenerator
 * @date 2018-01-22 13:28:46
 * 
 */
@Api(value = "基础模块-4.8固定资产")
@RestController
@RequestMapping(value="/basis")
public class BasisFixedAssetsController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisFixedAssetsServiceImpl")
	BasisFixedAssetsServiceInterface<BasisFixedAssets> basisFixedAssetsService;
	
	@ApiOperation(value="4.8.1查询固定资产")
	@RequestMapping(value = "/selectBasisFixedAssets", method = RequestMethod.POST)
    public String selectBasisFixedAssets(@ModelAttribute ViewBasisFixedAssets assets) throws Exception {
        List<ViewBasisFixedAssets> assetsList = basisFixedAssetsService.findAssetsView(assets);
		LmisResult<List<ViewBasisFixedAssets>> lmisResult = new LmisResult<>();
		lmisResult.setData(assetsList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
	}
	@ApiOperation(value="4.8.1查询固定资产")
	@RequestMapping(value = "/selectLazyfindAssetsView", method = RequestMethod.POST)
	public String selectLazyfindAssetsView(@ModelAttribute ViewBasisFixedAssets assets) throws Exception {
		List<ViewBasisFixedAssets> assetsList = basisFixedAssetsService.lazyfindAssetsView(assets);
		LmisResult<List<ViewBasisFixedAssets>> lmisResult = new LmisResult<>();
		lmisResult.setData(assetsList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return JSON.toJSONString(lmisResult);
	}


	@ApiOperation(value="4.8.2新增固定资产")
	@RequestMapping(value = "/addBasisFixedAssets", method = RequestMethod.POST)
    public String addBasisFixedAssets(@ModelAttribute DynamicSqlParam<BasisFixedAssets> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.8.3修改固定资产")
	@RequestMapping(value = "/updateBasisFixedAssets", method = RequestMethod.POST)
    public String updateBasisFixedAssets(@ModelAttribute DynamicSqlParam<BasisFixedAssets> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.8.4删除固定资产")
	@RequestMapping(value = "/deleteBasisFixedAssets", method = RequestMethod.POST)
    public String deleteBasisFixedAssets(@ModelAttribute BasisFixedAssets basisFixedAssets) throws Exception {
		return JSON.toJSONString(basisFixedAssetsService.deleteBasisFixedAssets(basisFixedAssets));
    }
	
	@ApiOperation(value="4.8.5查看固定资产")
	@RequestMapping(value = "/checkBasisFixedAssets", method = RequestMethod.POST)
    public String checkBasisFixedAssets(@ModelAttribute DynamicSqlParam<BasisFixedAssets> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisFixedAssetsService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.8.6启用固定资产/4.8.7启用固定资产")
	@RequestMapping(value = "/switchBasisFixedAssets", method = RequestMethod.POST)
    public String switchBasisFixedAssets(@ModelAttribute BasisFixedAssets basisFixedAssets) throws Exception {
		return JSON.toJSONString(basisFixedAssetsService.switchBasisFixedAssets(basisFixedAssets));
    }
	
	@ApiOperation(value="4.8.10下拉框查询固定资产")
	@RequestMapping(value = "/findFixedAssets", method = RequestMethod.POST)
    public String findFixedAssets(@ModelAttribute BasisFixedAssets assets) throws Exception {
        List<BasisFixedAssets> assetsList = basisFixedAssetsService.findAssets(assets);
		LmisResult<List<BasisFixedAssets>> lmisResult = new LmisResult<>();
		lmisResult.setData(assetsList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
	}
	
	@RequestMapping(value = "/exportFixedAssets", method = RequestMethod.POST)
    public String exportFixedAssets(int pageSize) throws Exception {
        return JSON.toJSONString(basisFixedAssetsService.exportFixedAssets(pageSize));
    }
}
