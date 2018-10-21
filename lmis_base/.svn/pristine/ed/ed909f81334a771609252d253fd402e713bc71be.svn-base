package com.lmis.jbasis.store.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.store.model.JbasisStore;
import com.lmis.jbasis.store.service.JbasisStoreServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisStoreController
 * @Description: TODO(店铺信息（基础数据）控制层类)
 * @author codeGenerator
 * @date 2018-04-13 10:22:24
 * 
 */
@Api(value = "店铺信息（基础数据）")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisStoreController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="jbasisStoreServiceImpl")
	JbasisStoreServiceInterface<JbasisStore> jbasisStoreService;
	
	@ApiOperation(value="查询店铺信息（基础数据）")
	@RequestMapping(value = "/selectJbasisStore", method = RequestMethod.POST)
    public String selectJbasisStore(@ModelAttribute DynamicSqlParam<JbasisStore> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisStoreService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增店铺信息（基础数据）")
	@RequestMapping(value = "/addJbasisStore", method = RequestMethod.POST)
    public String addJbasisStore(@ModelAttribute DynamicSqlParam<JbasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisStoreService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改店铺信息（基础数据）")
	@RequestMapping(value = "/updateJbasisStore", method = RequestMethod.POST)
    public String updateJbasisStore(@ModelAttribute DynamicSqlParam<JbasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisStoreService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除店铺信息（基础数据）")
	@RequestMapping(value = "/deleteJbasisStore", method = RequestMethod.POST)
    public String deleteJbasisStore(@ModelAttribute JbasisStore jbasisStore) throws Exception {
		return JSON.toJSONString(jbasisStoreService.deleteJbasisStore(jbasisStore));
    }
	
	@ApiOperation(value="查看店铺信息（基础数据）")
	@RequestMapping(value = "/checkJbasisStore", method = RequestMethod.POST)
    public String checkJbasisStore(@ModelAttribute DynamicSqlParam<JbasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisStoreService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="启用店铺/禁用店铺（基础数据）")
	@RequestMapping(value = "/switchJbasisStore", method = RequestMethod.POST)
    public String switchJbasisStore(@ModelAttribute JbasisStore JbasisStore) throws Exception {
		return JSON.toJSONString(jbasisStoreService.switchJbasisStore(JbasisStore));
    }
	
}
