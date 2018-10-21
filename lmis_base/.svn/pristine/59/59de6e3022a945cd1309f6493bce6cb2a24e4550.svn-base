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
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfoDeatil;
import com.lmis.jbasis.contractBasicinfo.model.ViewJbasisContractBasicinfoDeatil;
import com.lmis.jbasis.contractBasicinfo.service.JbasisContractBasicinfoDeatilServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisContractBasicinfoDeatilController
 * @Description: TODO(计费协议明细控制层类)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 */
@Api(value = "计费协议明细")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisContractBasicinfoDeatilController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="jbasisContractBasicinfoDeatilServiceImpl")
	JbasisContractBasicinfoDeatilServiceInterface<JbasisContractBasicinfoDeatil> jbasisContractBasicinfoDeatilService;
	
	@ApiOperation(value="查询计费协议明细")
	@RequestMapping(value = "/selectJbasisContractBasicinfoDeatil", method = RequestMethod.POST)
    public String selectJbasisContractBasicinfoDeatil(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfoDeatil> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisContractBasicinfoDeatilService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增计费协议明细")
	@RequestMapping(value = "/addJbasisContractBasicinfoDeatil", method = RequestMethod.POST)
    public String addJbasisContractBasicinfoDeatil(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfoDeatil> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoDeatilService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改计费协议明细")
	@RequestMapping(value = "/updateJbasisContractBasicinfoDeatil", method = RequestMethod.POST)
    public String updateJbasisContractBasicinfoDeatil(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfoDeatil> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoDeatilService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除计费协议明细")
	@RequestMapping(value = "/deleteJbasisContractBasicinfoDeatil", method = RequestMethod.POST)
    public String deleteJbasisContractBasicinfoDeatil(@ModelAttribute JbasisContractBasicinfoDeatil jbasisContractBasicinfoDeatil) throws Exception {
		return JSON.toJSONString(jbasisContractBasicinfoDeatilService.deleteJbasisContractBasicinfoDeatil(jbasisContractBasicinfoDeatil));
    }
	
	@ApiOperation(value="查看计费协议明细")
	@RequestMapping(value = "/checkJbasisContractBasicinfoDeatil", method = RequestMethod.POST)
    public String checkJbasisContractBasicinfoDeatil(@ModelAttribute DynamicSqlParam<JbasisContractBasicinfoDeatil> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoDeatilService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="根据条件查看计费协议明细")
    @RequestMapping(value = "/checkJbasisContractBasicinfoDeatilList", method = RequestMethod.POST)
    public String checkJbasisContractBasicinfoDeatilList(@ModelAttribute ViewJbasisContractBasicinfoDeatil viewJbasisContractBasicinfoDeatil) throws Exception {
        return JSON.toJSONString(jbasisContractBasicinfoDeatilService.executeSelectList(viewJbasisContractBasicinfoDeatil));
    }
	
}
