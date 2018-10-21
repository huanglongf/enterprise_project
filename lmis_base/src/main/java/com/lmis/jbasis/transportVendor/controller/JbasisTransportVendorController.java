package com.lmis.jbasis.transportVendor.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.transportVendor.model.JbasisTransportVendor;
import com.lmis.jbasis.transportVendor.service.JbasisTransportVendorServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisTransportVendorController
 * @Description: TODO(运输供应商(基础数据)控制层类)
 * @author codeGenerator
 * @date 2018-04-19 11:14:05
 * 
 */
@Api(value = "运输供应商(基础数据)")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisTransportVendorController {

    @Value("${base.page.pageNum}")
    int defPageNum;
    
    @Value("${base.page.pageSize}")
    int defPageSize;
    
    @Resource(name="jbasisTransportVendorServiceImpl")
    JbasisTransportVendorServiceInterface<JbasisTransportVendor> jbasisTransportVendorService;
    
    @ApiOperation(value="查询运输供应商(基础数据)")
    @RequestMapping(value = "/selectJbasisTransportVendor", method = RequestMethod.POST)
    public String selectJbasisTransportVendor(@ModelAttribute DynamicSqlParam<JbasisTransportVendor> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
        pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisTransportVendorService.executeSelect(dynamicSqlParam, pageObject));
    }

    @ApiOperation(value="新增运输供应商(基础数据)")
    @RequestMapping(value = "/addJbasisTransportVendor", method = RequestMethod.POST)
    public String addJbasisTransportVendor(@ModelAttribute DynamicSqlParam<JbasisTransportVendor> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.executeInsert(dynamicSqlParam));
    }

    @ApiOperation(value="修改运输供应商(基础数据)")
    @RequestMapping(value = "/updateJbasisTransportVendor", method = RequestMethod.POST)
    public String updateJbasisTransportVendor(@ModelAttribute DynamicSqlParam<JbasisTransportVendor> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.executeUpdate(dynamicSqlParam));
    }

    @ApiOperation(value="删除运输供应商(基础数据)")
    @RequestMapping(value = "/deleteJbasisTransportVendor", method = RequestMethod.POST)
    public String deleteJbasisTransportVendor(@ModelAttribute JbasisTransportVendor jbasisTransportVendor) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.deleteJbasisTransportVendor(jbasisTransportVendor));
    }
    
    @ApiOperation(value="查看运输供应商(基础数据)")
    @RequestMapping(value = "/checkJbasisTransportVendor", method = RequestMethod.POST)
    public String checkJbasisTransportVendor(@ModelAttribute DynamicSqlParam<JbasisTransportVendor> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.executeSelect(dynamicSqlParam));
    }
    
    @ApiOperation(value="是否启用(基础数据)")
    @RequestMapping(value = "/isDiableJbasisTransportVendor", method = RequestMethod.POST)
    public String isDiableJbasisTransportVendor(@ModelAttribute JbasisTransportVendor dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.isDiable(dynamicSqlParam));
    }
    
    @ApiOperation(value="查询运输供应商类型")
    @RequestMapping(value = "/selectJbasisTransportVendorType", method = RequestMethod.POST)
    public String selectJbasisTransportVendorType(String transportCode) throws Exception {
        return JSON.toJSONString(jbasisTransportVendorService.executeSelectType(transportCode));
    }
    
}
