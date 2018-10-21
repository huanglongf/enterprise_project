package com.lmis.pos.whsRateFillin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsRateFillin.model.PosWhsRecomRate;
import com.lmis.pos.whsRateFillin.service.PosWhsRecomRateServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "补货商品（F）仓库推荐比例分析")
@RestController
@RequestMapping(value="/fillin")
public class PosWhsRecomRateController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsRecomRateServiceImpl")
	PosWhsRecomRateServiceInterface<PosWhsRecomRate> posWhsRecomRateService;
	
	@ApiOperation(value="查询补货商品（F）仓库推荐比例分析")
	@RequestMapping(value = "/queryRecomRate", method = RequestMethod.POST)
    public String selectPosWhsRecomRate(@ModelAttribute PosWhsRecomRate recomRate,
    		@ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsRecomRateService.executeSelect(recomRate, pageObject));
    }
	
}
