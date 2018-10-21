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
import com.lmis.pos.common.model.PosPoImportLog;
import com.lmis.pos.common.service.PosPoImportLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosPoImportLogController
 * @Description: TODO(Po单导入统计日志表控制层类)
 * @author codeGenerator
 * @date 2018-06-06 17:08:44
 * 
 */
@Api(value = "Po单导入统计日志表")
@RestController
@RequestMapping(value="/pos")
public class PosPoImportLogController extends BaseController{

	@Resource(name="posPoImportLogServiceImpl")
	PosPoImportLogService<PosPoImportLog> posPoImportLogService;
	
	@ApiOperation(value="查询Po单导入统计日志表")
	@RequestMapping(value = "/selectPosPoImportLog", method = RequestMethod.POST)
    public String selectPosPoImportLog(@ModelAttribute DynamicSqlParam<PosPoImportLog> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posPoImportLogService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增Po单导入统计日志表")
	@RequestMapping(value = "/addPosPoImportLog", method = RequestMethod.POST)
    public String addPosPoImportLog(@ModelAttribute DynamicSqlParam<PosPoImportLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPoImportLogService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改Po单导入统计日志表")
	@RequestMapping(value = "/updatePosPoImportLog", method = RequestMethod.POST)
    public String updatePosPoImportLog(@ModelAttribute DynamicSqlParam<PosPoImportLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPoImportLogService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除Po单导入统计日志表")
	@RequestMapping(value = "/deletePosPoImportLog", method = RequestMethod.POST)
    public String deletePosPoImportLog(@ModelAttribute PosPoImportLog posPoImportLog) throws Exception {
		return JSON.toJSONString(posPoImportLogService.deletePosPoImportLog(posPoImportLog));
    }
	
	@ApiOperation(value="查看Po单导入统计日志表")
	@RequestMapping(value = "/checkPosPoImportLog", method = RequestMethod.POST)
    public String checkPosPoImportLog(@ModelAttribute DynamicSqlParam<PosPoImportLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posPoImportLogService.executeSelect(dynamicSqlParam));
    }
	
}
