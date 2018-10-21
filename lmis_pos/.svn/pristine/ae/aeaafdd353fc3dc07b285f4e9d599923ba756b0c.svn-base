package com.lmis.pos.common.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.model.PosDataFileTemplete;
import com.lmis.pos.common.service.PosDataFileTempleteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosDataFileTempleteController
 * @Description: TODO(下载上传excel模板统一记录表控制层类)
 * @author codeGenerator
 * @date 2018-05-29 15:38:54
 * 
 */
@Api(value = "公共模块-2.2下载上传excel模板统一记录表")
@RestController
@RequestMapping(value="/dataFileTemplete")
public class PosDataFileTempleteController extends BaseController{

	@Resource(name="posDataFileTempleteServiceImpl")
	PosDataFileTempleteService<PosDataFileTemplete> posDataFileTempleteService;
	
	@ApiOperation(value="2.2.1查询下载上传excel模板统一记录表")
	@RequestMapping(value = "/selectPosDataFileTemplete", method = RequestMethod.POST)
    public String selectPosDataFileTemplete(@ModelAttribute DynamicSqlParam<PosDataFileTemplete> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posDataFileTempleteService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="2.2.2新增下载上传excel模板统一记录表")
	@RequestMapping(value = "/addPosDataFileTemplete", method = RequestMethod.POST)
    public String addPosDataFileTemplete(@ModelAttribute DynamicSqlParam<PosDataFileTemplete> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posDataFileTempleteService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="2.2.3修改下载上传excel模板统一记录表")
	@RequestMapping(value = "/updatePosDataFileTemplete", method = RequestMethod.POST)
    public String updatePosDataFileTemplete(@ModelAttribute DynamicSqlParam<PosDataFileTemplete> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posDataFileTempleteService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="2.2.4删除下载上传excel模板统一记录表")
	@RequestMapping(value = "/deletePosDataFileTemplete", method = RequestMethod.POST)
    public String deletePosDataFileTemplete(@ModelAttribute PosDataFileTemplete posDataFileTemplete) throws Exception {
		return JSON.toJSONString(posDataFileTempleteService.deletePosDataFileTemplete(posDataFileTemplete));
    }
	
	@ApiOperation(value="2.2.5查看下载上传excel模板统一记录表")
	@RequestMapping(value = "/checkPosDataFileTemplete", method = RequestMethod.POST)
    public String checkPosDataFileTemplete(@ModelAttribute DynamicSqlParam<PosDataFileTemplete> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posDataFileTempleteService.executeSelect(dynamicSqlParam));
    }
	
}
