package com.lmis.pos.whsRateFillin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsRateFillin.model.PosWhsRateFillin;
import com.lmis.pos.whsRateFillin.service.PosWhsRateFillinServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsRateFillinController
 * @Description: TODO(补货商品分配仓库比例分析控制层类)
 * @author codeGenerator
 * @date 2018-05-30 17:03:06
 * 
 */
@Api(value = "补货商品分配仓库比例分析")
@RestController
@RequestMapping(value="/pos")
public class PosWhsRateFillinController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsRateFillinServiceImpl")
	PosWhsRateFillinServiceInterface<PosWhsRateFillin> posWhsRateFillinService;
	
	@ApiOperation(value="查询补货商品分配仓库比例分析")
	@RequestMapping(value = "/selectPosWhsRateFillin", method = RequestMethod.POST)
    public String selectPosWhsRateFillin(@ModelAttribute DynamicSqlParam<PosWhsRateFillin> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsRateFillinService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增补货商品分配仓库比例分析")
	@RequestMapping(value = "/addPosWhsRateFillin", method = RequestMethod.POST)
    public String addPosWhsRateFillin(@ModelAttribute DynamicSqlParam<PosWhsRateFillin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateFillinService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改补货商品分配仓库比例分析")
	@RequestMapping(value = "/updatePosWhsRateFillin", method = RequestMethod.POST)
    public String updatePosWhsRateFillin(@ModelAttribute DynamicSqlParam<PosWhsRateFillin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateFillinService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除补货商品分配仓库比例分析")
	@RequestMapping(value = "/deletePosWhsRateFillin", method = RequestMethod.POST)
    public String deletePosWhsRateFillin(@ModelAttribute PosWhsRateFillin posWhsRateFillin) throws Exception {
		return JSON.toJSONString(posWhsRateFillinService.deletePosWhsRateFillin(posWhsRateFillin));
    }
	
	@ApiOperation(value="查看补货商品分配仓库比例分析")
	@RequestMapping(value = "/checkPosWhsRateFillin", method = RequestMethod.POST)
    public String checkPosWhsRateFillin(@ModelAttribute DynamicSqlParam<PosWhsRateFillin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateFillinService.executeSelect(dynamicSqlParam));
    }
	
}
