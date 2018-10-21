package com.lmis.pos.whsRateLoadin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadin;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadinProportion;
import com.lmis.pos.whsRateLoadin.service.PosWhsRateLoadinServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsRateLoadinController
 * @Description: TODO(新货商品分配仓库比例分析控制层类)
 * @author codeGenerator
 * @date 2018-05-30 17:12:37
 * 
 */
@Api(value = "新货商品分配仓库比例分析")
@RestController
@RequestMapping(value="/pos")
public class PosWhsRateLoadinController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsRateLoadinServiceImpl")
	PosWhsRateLoadinServiceInterface<PosWhsRateLoadin> posWhsRateLoadinService;
	
	@ApiOperation(value="查询新货商品分配仓库比例分析")
	@RequestMapping(value = "/selectPosWhsRateLoadin", method = RequestMethod.POST)
    public String selectPosWhsRateLoadin(@ModelAttribute DynamicSqlParam<PosWhsRateLoadin> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsRateLoadinService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增新货商品分配仓库比例分析")
	@RequestMapping(value = "/addPosWhsRateLoadin", method = RequestMethod.POST)
    public String addPosWhsRateLoadin(@ModelAttribute DynamicSqlParam<PosWhsRateLoadin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateLoadinService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改新货商品分配仓库比例分析")
	@RequestMapping(value = "/updatePosWhsRateLoadin", method = RequestMethod.POST)
    public String updatePosWhsRateLoadin(@ModelAttribute DynamicSqlParam<PosWhsRateLoadin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateLoadinService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除新货商品分配仓库比例分析")
	@RequestMapping(value = "/deletePosWhsRateLoadin", method = RequestMethod.POST)
    public String deletePosWhsRateLoadin(@ModelAttribute PosWhsRateLoadin posWhsRateLoadin) throws Exception {
		return JSON.toJSONString(posWhsRateLoadinService.deletePosWhsRateLoadin(posWhsRateLoadin));
    }
	
	@ApiOperation(value="查看新货商品分配仓库比例分析")
	@RequestMapping(value = "/checkPosWhsRateLoadin", method = RequestMethod.POST)
    public String checkPosWhsRateLoadin(@ModelAttribute DynamicSqlParam<PosWhsRateLoadin> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsRateLoadinService.executeSelect(dynamicSqlParam));
    }
		   
    @ApiOperation(value="仓库推荐比例分析查询")
    @RequestMapping(value = "/selectPosWhsRateLoadinProportion", method = RequestMethod.POST)
    public String selectPosWhsRateLoadinProportion(@ModelAttribute PosWhsRateLoadinProportion posWhsRateLoadinProportion, @ModelAttribute LmisPageObject pageObject) throws Exception {
        return JSON.toJSONString(posWhsRateLoadinService.selectPosWhsRateLoadinProportion(posWhsRateLoadinProportion,pageObject));
    }
	
}
