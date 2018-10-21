package com.lmis.pos.common.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.common.service.PosWhsAllocateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsAllocateController
 * @Description: TODO(PO分仓结果控制层类)
 * @author codeGenerator
 * @date 2018-05-30 13:26:28
 * 
 */
@Api(value = "PO分仓结果")
@RestController
@RequestMapping(value="/pos")
public class PosWhsAllocateController extends BaseController{

	@Resource(name="posWhsAllocateServiceImpl")
	PosWhsAllocateService<PosWhsAllocate> posWhsAllocateService;
	
	@ApiOperation(value="查询PO分仓结果")
	@RequestMapping(value = "/selectPosWhsAllocate", method = RequestMethod.POST)
    public String selectPosWhsAllocate(@ModelAttribute DynamicSqlParam<PosWhsAllocate> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsAllocateService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增PO分仓结果")
	@RequestMapping(value = "/addPosWhsAllocate", method = RequestMethod.POST)
    public String addPosWhsAllocate(@ModelAttribute DynamicSqlParam<PosWhsAllocate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsAllocateService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改PO分仓结果")
	@RequestMapping(value = "/updatePosWhsAllocate", method = RequestMethod.POST)
    public String updatePosWhsAllocate(@ModelAttribute DynamicSqlParam<PosWhsAllocate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsAllocateService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除PO分仓结果")
	@RequestMapping(value = "/deletePosWhsAllocate", method = RequestMethod.POST)
    public String deletePosWhsAllocate(@ModelAttribute PosWhsAllocate posWhsAllocate) throws Exception {
		return JSON.toJSONString(posWhsAllocateService.deletePosWhsAllocate(posWhsAllocate));
    }
	
	@ApiOperation(value="查看PO分仓结果")
	@RequestMapping(value = "/checkPosWhsAllocate", method = RequestMethod.POST)
    public String checkPosWhsAllocate(@ModelAttribute DynamicSqlParam<PosWhsAllocate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsAllocateService.executeSelect(dynamicSqlParam));
    }
	
}
