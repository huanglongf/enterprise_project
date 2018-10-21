package com.lmis.pos.common.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
import com.lmis.pos.common.service.PosPurchaseOrderDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosPurchaseOrderDetailController
 * @Description: TODO(NIKE-PO单主表控制层类)
 * @author codeGenerator
 * @date 2018-05-30 16:28:41
 * 
 */
@Api(value = "NIKE-PO单主表")
@RestController
@RequestMapping(value="/pos")
public class PosPurchaseOrderDetailController extends BaseController{

	@Resource(name="posPurchaseOrderDetailServiceImpl")
	PosPurchaseOrderDetailService<PosPurchaseOrderDetail> posPurchaseOrderDetailService;
	
	@ApiOperation(value="查询NIKE-PO单主表")
	@RequestMapping(value = "/selectPosPurchaseOrderDetail", method = RequestMethod.POST)
    public String selectPosPurchaseOrderDetail(@ModelAttribute DynamicSqlParam<PosPurchaseOrderDetail> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posPurchaseOrderDetailService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增NIKE-PO单主表")
	@RequestMapping(value = "/addPosPurchaseOrderDetail", method = RequestMethod.POST)
    public String addPosPurchaseOrderDetail(@ModelAttribute DynamicSqlParam<PosPurchaseOrderDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderDetailService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改NIKE-PO单主表")
	@RequestMapping(value = "/updatePosPurchaseOrderDetail", method = RequestMethod.POST)
    public String updatePosPurchaseOrderDetail(@ModelAttribute DynamicSqlParam<PosPurchaseOrderDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderDetailService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除NIKE-PO单主表")
	@RequestMapping(value = "/deletePosPurchaseOrderDetail", method = RequestMethod.POST)
    public String deletePosPurchaseOrderDetail(@ModelAttribute PosPurchaseOrderDetail posPurchaseOrderDetail) throws Exception {
		return JSON.toJSONString(posPurchaseOrderDetailService.deletePosPurchaseOrderDetail(posPurchaseOrderDetail));
    }
	
	@ApiOperation(value="查看NIKE-PO单主表")
	@RequestMapping(value = "/checkPosPurchaseOrderDetail", method = RequestMethod.POST)
    public String checkPosPurchaseOrderDetail(@ModelAttribute DynamicSqlParam<PosPurchaseOrderDetail> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPurchaseOrderDetailService.executeSelect(dynamicSqlParam));
    }
	
}
