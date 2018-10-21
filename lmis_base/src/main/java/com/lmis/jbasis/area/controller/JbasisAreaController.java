package com.lmis.jbasis.area.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.area.model.JbasisArea;
import com.lmis.jbasis.area.service.JbasisAreaServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisAreaController
 * @Description: TODO(地址库表（基础数据）控制层类)
 * @author codeGenerator
 * @date 2018-04-11 14:23:33
 * 
 */
@Api(value = "地址库表（基础数据）")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisAreaController {

    @Value("${base.page.pageNum}")
    int defPageNum;
    
    @Value("${base.page.pageSize}")
    int defPageSize;
    
    @Resource(name="jbasisAreaServiceImpl")
    JbasisAreaServiceInterface<JbasisArea> jbasisAreaService;
    
    @ApiOperation(value="查询地址库表（基础数据）")
    @RequestMapping(value = "/selectJbasisArea", method = RequestMethod.POST)
    public String selectJbasisArea(@ModelAttribute JbasisArea jbasisArea) throws Exception {
        return JSON.toJSONString(jbasisAreaService.executeSelect(jbasisArea));
    }

    @ApiOperation(value="新增地址库表（基础数据）")
    @RequestMapping(value = "/addJbasisArea", method = RequestMethod.POST)
    public String addJbasisArea(@ModelAttribute JbasisArea jbasisArea) throws Exception {
        return JSON.toJSONString(jbasisAreaService.executeInsert(jbasisArea));
    }

    @ApiOperation(value="修改地址库表（基础数据）")
    @RequestMapping(value = "/updateJbasisArea", method = RequestMethod.POST)
    public String updateJbasisArea(@ModelAttribute JbasisArea jbasisArea) throws Exception {
        return JSON.toJSONString(jbasisAreaService.executeUpdate(jbasisArea));
    }

    @ApiOperation(value="删除地址库表（基础数据）")
    @RequestMapping(value = "/deleteJbasisArea", method = RequestMethod.POST)
    public String deleteJbasisArea(@ModelAttribute JbasisArea jbasisArea) throws Exception {
        return JSON.toJSONString(jbasisAreaService.deleteJbasisArea(jbasisArea));
    }
    
    @ApiOperation(value="查看地址库表（基础数据）")
    @RequestMapping(value = "/checkJbasisArea", method = RequestMethod.POST)
    public String checkJbasisArea(@ModelAttribute DynamicSqlParam<JbasisArea> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisAreaService.executeSelect(dynamicSqlParam));
    }
    
}
