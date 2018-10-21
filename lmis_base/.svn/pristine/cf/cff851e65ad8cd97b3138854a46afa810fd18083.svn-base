package com.lmis.jbasis.warePark.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.warePark.model.JbasisWarePark;
import com.lmis.jbasis.warePark.service.JbasisWareParkServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisWareParkController
 * @Description:(控制层类)
 * @author codeGenerator
 * @date 2018-04-12 09:57:26
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisWareParkController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="jbasisWareParkServiceImpl")
	JbasisWareParkServiceInterface<JbasisWarePark> jbasisWareParkService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectJbasisWarePark", method = RequestMethod.POST)
    public String selectJbasisWarePark(@ModelAttribute DynamicSqlParam<JbasisWarePark> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisWareParkService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addJbasisWarePark", method = RequestMethod.POST)
    public String addJbasisWarePark(@ModelAttribute DynamicSqlParam<JbasisWarePark> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWareParkService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateJbasisWarePark", method = RequestMethod.POST)
    public String updateJbasisWarePark(@ModelAttribute DynamicSqlParam<JbasisWarePark> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWareParkService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteJbasisWarePark", method = RequestMethod.POST)
    public String deleteJbasisWarePark(@ModelAttribute JbasisWarePark jbasisWarePark) throws Exception {
		return JSON.toJSONString(jbasisWareParkService.deleteJbasisWarePark(jbasisWarePark));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkJbasisWarePark", method = RequestMethod.POST)
    public String checkJbasisWarePark(@ModelAttribute DynamicSqlParam<JbasisWarePark> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWareParkService.executeSelect(dynamicSqlParam));
    }
	
//	@RequestMapping(value = "/exportCostCenter", method = RequestMethod.POST)
//    public String exportCostCenter() throws Exception {
//        return JSON.toJSONString(jbasisWareParkService.exportCostCenter());
//    }
}
