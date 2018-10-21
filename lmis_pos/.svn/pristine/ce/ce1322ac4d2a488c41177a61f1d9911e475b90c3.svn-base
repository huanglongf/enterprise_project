package com.lmis.pos.common.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.common.service.PosPurchaseOrderMainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosPurchaseOrderMainController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-30 13:41:07
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/pos")
public class PosPurchaseOrderMainController extends BaseController{

	@Resource(name="posPurchaseOrderMainServiceImpl")
	PosPurchaseOrderMainService<PosPurchaseOrderMain> posPurchaseOrderMainService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectPosPurchaseOrderMain", method = RequestMethod.POST)
    public String selectPosPurchaseOrderMain(@ModelAttribute DynamicSqlParam<PosPurchaseOrderMain> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posPurchaseOrderMainService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addPosPurchaseOrderMain", method = RequestMethod.POST)
    public String addPosPurchaseOrderMain(@ModelAttribute DynamicSqlParam<PosPurchaseOrderMain> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderMainService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updatePosPurchaseOrderMain", method = RequestMethod.POST)
    public String updatePosPurchaseOrderMain(@ModelAttribute DynamicSqlParam<PosPurchaseOrderMain> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderMainService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deletePosPurchaseOrderMain", method = RequestMethod.POST)
    public String deletePosPurchaseOrderMain(@ModelAttribute PosPurchaseOrderMain posPurchaseOrderMain) throws Exception {
		return JSON.toJSONString(posPurchaseOrderMainService.deletePosPurchaseOrderMain(posPurchaseOrderMain));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkPosPurchaseOrderMain", method = RequestMethod.POST)
    public String checkPosPurchaseOrderMain(@ModelAttribute DynamicSqlParam<PosPurchaseOrderMain> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderMainService.executeSelect(dynamicSqlParam));
    }
	
}
