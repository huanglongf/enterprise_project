package com.lmis.jbasis.contractBasicinfo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfo;
import com.lmis.jbasis.contractBasicinfo.service.JbasisContractBasicinfoServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisContractBasicinfoController
 * @Description: TODO(合同控制层类)
 * @author codeGenerator
 * @date 2018-05-04 15:06:45
 * 
 */
@Api(value = "合同")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisContractBasicinfoController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="jbasisContractBasicinfoServiceImpl")
	JbasisContractBasicinfoServiceInterface<JbasisContractBasicinfo> jbasisContractBasicinfoService;
	
	@ApiOperation(value="查询合同")
	@RequestMapping(value = "/selectJbasisContractBasicinfo", method = RequestMethod.POST)
    public String selectJbasisContractBasicinfo(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfo> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisContractBasicinfoService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增合同")
	@RequestMapping(value = "/addJbasisContractBasicinfo", method = RequestMethod.POST)
    public String addJbasisContractBasicinfo(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfo> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改合同")
	@RequestMapping(value = "/updateJbasisContractBasicinfo", method = RequestMethod.POST)
    public String updateJbasisContractBasicinfo(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfo> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除合同")
	@RequestMapping(value = "/deleteJbasisContractBasicinfo", method = RequestMethod.POST)
    public String deleteJbasisContractBasicinfo(@ModelAttribute JbasisContractBasicinfo jbasisContractBasicinfo) throws Exception {
		return JSON.toJSONString(jbasisContractBasicinfoService.deleteJbasisContractBasicinfo(jbasisContractBasicinfo));
    }
	
	@ApiOperation(value="查看合同")
	@RequestMapping(value = "/checkJbasisContractBasicinfo", method = RequestMethod.POST)
    public String checkJbasisContractBasicinfo(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfo> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="启用合同/禁用合同")
    @RequestMapping(value = "/switchJbasisContractBasicinfo", method = RequestMethod.POST)
    public String switchJbasisContractBasicinfo(@ModelAttribute JbasisContractBasicinfo contractBasicinfo) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoService.switchContractBasicinfo(contractBasicinfo));
    }
	
}
