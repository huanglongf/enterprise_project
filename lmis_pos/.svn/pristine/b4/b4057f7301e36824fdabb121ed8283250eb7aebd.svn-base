package com.lmis.pos.skuBase.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.skuBase.model.PosSkuBase;
import com.lmis.pos.skuBase.service.PosSkuBaseServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosSkuBaseController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-06-11 16:43:53
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/pos")
public class PosSkuBaseController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posSkuBaseServiceImpl")
	PosSkuBaseServiceInterface posSkuBaseService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectPosSkuBase", method = RequestMethod.POST)
    public String selectPosSkuBase(@ModelAttribute PosSkuBase posSkuBase, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posSkuBaseService.executeSelect(posSkuBase, pageObject));
    }
}
