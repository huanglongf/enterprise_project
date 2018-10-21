package com.lmis.jbasis.client.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.client.model.JbasisClient;
import com.lmis.jbasis.client.service.JbasisClientServiceInterface;
import com.lmis.jbasis.store.model.JbasisStore;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: JbasisClientController
 * @Description: TODO(客户信息(基础数据)控制层类)
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 */
@Api(value = "客户信息(基础数据)")
@RestController
@RequestMapping(value = "/jbasis")
public class JbasisClientController{

    @Value("${base.page.pageNum}")
    int defPageNum;

    @Value("${base.page.pageSize}")
    int defPageSize;

    @Resource(name = "jbasisClientServiceImpl")
    JbasisClientServiceInterface<JbasisClient> jbasisClientService;

    @ApiOperation(value = "查询客户信息(基础数据)")
    @RequestMapping(value = "/selectJbasisClient",method = RequestMethod.POST)
    public String selectJbasisClient(@ModelAttribute DynamicSqlParam<JbasisClient> dynamicSqlParam,@ModelAttribute LmisPageObject pageObject) throws Exception{
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisClientService.executeSelect(dynamicSqlParam, pageObject));
    }

    @ApiOperation(value = "新增客户信息(基础数据)")
    @RequestMapping(value = "/addJbasisClient",method = RequestMethod.POST)
    public String addJbasisClient(@ModelAttribute DynamicSqlParam<JbasisClient> dynamicSqlParam) throws Exception{
        return JSON.toJSONString(jbasisClientService.executeInsert(dynamicSqlParam));
    }

    @ApiOperation(value = "修改客户信息(基础数据)")
    @RequestMapping(value = "/updateJbasisClient",method = RequestMethod.POST)
    public String updateJbasisClient(@ModelAttribute DynamicSqlParam<JbasisClient> dynamicSqlParam) throws Exception{
        return JSON.toJSONString(jbasisClientService.executeUpdate(dynamicSqlParam));
    }

    @ApiOperation(value = "删除客户信息(基础数据)")
    @RequestMapping(value = "/deleteJbasisClient",method = RequestMethod.POST)
    public String deleteJbasisClient(@ModelAttribute JbasisClient jbasisClient) throws Exception{
        return JSON.toJSONString(jbasisClientService.deleteJbasisClient(jbasisClient));
    }

    @ApiOperation(value = "查看客户信息(基础数据)")
    @RequestMapping(value = "/checkJbasisClient",method = RequestMethod.POST)
    public String checkJbasisClient(@ModelAttribute DynamicSqlParam<JbasisClient> dynamicSqlParam) throws Exception{
        return JSON.toJSONString(jbasisClientService.executeSelect(dynamicSqlParam));
    }

    @ApiOperation(value = "启用客户/禁用客户（基础数据）")
    @RequestMapping(value = "/switchJbasisClient",method = RequestMethod.POST)
    public String switchJbasisClient(@ModelAttribute JbasisClient jbasisClient) throws Exception{
        return JSON.toJSONString(jbasisClientService.switchJbasisClient(jbasisClient));
    }

}
