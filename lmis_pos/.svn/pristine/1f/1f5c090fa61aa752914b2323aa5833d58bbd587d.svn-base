package com.lmis.pos.whsSkutypeOutrate.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsSkutypeOutrate.model.PosWhsSkutypeOutrate;
import com.lmis.pos.whsSkutypeOutrate.service.PosWhsSkutypeOutrateServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsSkutypeOutrateController
 * @Description: TODO(仓库-鞋服配出库占比控制层类)
 * @author codeGenerator
 * @date 2018-06-05 16:49:38
 * 
 */
@Api(value = "仓库-鞋服配出库占比")
@RestController
@RequestMapping(value="/pos")
public class PosWhsSkutypeOutrateController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsSkutypeOutrateServiceImpl")
	PosWhsSkutypeOutrateServiceInterface<PosWhsSkutypeOutrate> posWhsSkutypeOutrateService;
	
	@ApiOperation(value="查询仓库-鞋服配出库占比")
	@RequestMapping(value = "/selectPosWhsSkutypeOutrate", method = RequestMethod.POST)
    public String selectPosWhsSkutypeOutrate(@ModelAttribute PosWhsSkutypeOutrate skutypeOutrate, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsSkutypeOutrateService.selectPosWhsSkutypeOutrate(skutypeOutrate, pageObject));
    }

}
