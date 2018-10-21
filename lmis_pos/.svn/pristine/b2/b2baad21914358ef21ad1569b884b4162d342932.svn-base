package com.lmis.pos.soldtoRule.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;
import com.lmis.pos.soldtoRule.service.PosSoldtoRuleServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosSoldtoRuleController
 * @Description: TODO(Po-sold To控制层类)
 * @author codeGenerator
 * @date 2018-06-22 17:09:27
 * 
 */
@Api(value = "PO-Sold To维护")
@RestController
@RequestMapping(value="/pos")
public class PosSoldtoRuleController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posSoldtoRuleServiceImpl")
	private PosSoldtoRuleServiceInterface<PosSoldtoRule> posSoldtoRuleServiceImpl;
	
	
	@ApiOperation(value="查询Po-Sold To主表信息")
	@RequestMapping(value = "/selectPosSoldToRuleNoSplit", method = RequestMethod.POST)
    public String selectPosSoldToRuleNoSplit() throws Exception {
        return JSON.toJSONString(posSoldtoRuleServiceImpl.executeSelect());
    }

}
