package com.lmis.jbasis.expenses.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.expenses.model.TbExpressReturnStorage;
import com.lmis.jbasis.expenses.model.TbOperationfeeData;
import com.lmis.jbasis.expenses.model.TbStorageExpendituresData;
import com.lmis.jbasis.expenses.model.ViewTbWarehouseExpressData;
import com.lmis.jbasis.expenses.service.TbExpensesServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "原始数据")
@RestController
@RequestMapping(value="/tb")
public class TbExpensesController{
    @Value("${base.page.pageNum}")
    int defPageNum;
    
    @Value("${base.page.pageSize}")
    int defPageSize;
    
    @Resource(name="tbExpensesServiceImpl")
    TbExpensesServiceInterface<?> tbExpensesService;
    
    @ApiOperation(value="查询退换货数据表")
    @RequestMapping(value = "/selectTbExpressReturnStorage", method = RequestMethod.POST)
    public String selectJbasisTransportVendor(@ModelAttribute DynamicSqlParam<TbExpressReturnStorage> tbExpressReturnStorage, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(tbExpensesService.selectTbExpressReturnStorage(tbExpressReturnStorage, pageObject));
    }
    @ApiOperation(value="查询运费")
    @RequestMapping(value = "/selectTbWarehouseExpressData", method = RequestMethod.POST)
    public String selectTbWarehouseExpressData(@ModelAttribute DynamicSqlParam<ViewTbWarehouseExpressData> tbWarehouseExpressData, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(tbExpensesService.selectTbWarehouseExpressData(tbWarehouseExpressData, pageObject));
    }
    @ApiOperation(value="查询仓储费")
    @RequestMapping(value = "/selectTbStorageExpendituresData", method = RequestMethod.POST)
    public String selectTbStorageExpendituresData(@ModelAttribute DynamicSqlParam<TbStorageExpendituresData> tbStorageExpendituresData, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(tbExpensesService.selectTbStorageExpendituresData(tbStorageExpendituresData, pageObject));
    }
    @ApiOperation(value="查询操作费")
    @RequestMapping(value = "/selectTbOperationfeeData", method = RequestMethod.POST)
    public String selectTbOperationfeeData(@ModelAttribute DynamicSqlParam<TbOperationfeeData> tbOperationfeeData, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(tbExpensesService.selectTbOperationfeeData(tbOperationfeeData, pageObject));
    }
}
