package com.lmis.pos.accReport.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.accReport.model.PosPlanWareAnalyAreaScale;
import com.lmis.pos.accReport.service.PosPlanWareAnalyServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日
 */
@Api(value = "")
@RestController
@RequestMapping(value="/pos")
public class PosPlanWareAnalyController {
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posPlanWareAnalyServiceImpl")
	private PosPlanWareAnalyServiceInterface<PosPlanWareAnalyAreaScale> posPlanWareAnalyService;
	
    
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectPosPlanWareAnalyAreaScale", method = RequestMethod.POST)
    public String selectPosPlanWareAnalyAreaScale(
    		@RequestParam(value="beginDateTime",required=false) String beginDateTime,
    		@RequestParam(value="endDateTime",required=false) String endDateTime,
    		@RequestParam(value="bu",required=false) String bu,
    		@RequestParam(value="whsName",required=false) String whsName,
    		@ModelAttribute DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam,
    		@ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posPlanWareAnalyService.selectPosPlanWareAnalyAreaScale(dynamicSqlParam, pageObject,beginDateTime,endDateTime,bu,whsName));
    }
	
	@ApiOperation(value="仓库名称")
	@RequestMapping(value = "/selectWhsallocate", method = RequestMethod.POST)
    public String selectWhsallocate(
    		@ModelAttribute DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPlanWareAnalyService.selectWhsallocate(dynamicSqlParam));
	}
	
	
	@ApiOperation(value="导出PO计划入库分析数据")
	@RequestMapping(value = "/exportPosPlanWareAnalyAreaScale", method = RequestMethod.POST)
    public String exportPosPlanWareAnalyAreaScale(
    		@RequestParam(value="templeteId",required=false) String templeteId,
    		@RequestParam(value="fileName",required=false) String fileName,
    		@RequestParam(value="suffixName",required=false) String suffixName,
    		@RequestParam(value="beginDateTime",required=false) String beginDateTime,
    		@RequestParam(value="endDateTime",required=false) String endDateTime,
    		@RequestParam(value="bu",required=false) String bu,
    		@RequestParam(value="whsName",required=false) String whsName,
    		@ModelAttribute DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPlanWareAnalyService.exportPosPlanWareAnalyAreaScale(templeteId,suffixName,fileName,dynamicSqlParam,beginDateTime,endDateTime,bu,whsName));
	}
}
