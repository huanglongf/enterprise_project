package com.lmis.pos.common.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.model.ServiceDataFileLog;
import com.lmis.pos.common.service.ServiceDataFileLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: ServiceDataFileLogController
 * @Description: TODO(文件管存记录表控制层类)
 * @author codeGenerator
 * @date 2018-04-11 15:58:57
 * 
 */
@Api(value = "公共模块-2.3文件管存记录表")
@RestController
@RequestMapping(value="/dataFileLog")
public class ServiceDataFileLogController extends BaseController{

	@Resource(name="serviceDataFileLogServiceImpl")
	ServiceDataFileLogService<ServiceDataFileLog> serviceDataFileLogService;
	
	@ApiOperation(value="2.3.1查询文件管存记录表")
	@RequestMapping(value = "/selectServiceDataFileLog", method = RequestMethod.POST)
    public String selectServiceDataFileLog(@ModelAttribute DynamicSqlParam<ServiceDataFileLog> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(serviceDataFileLogService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="2.3.2新增文件管存记录表")
	@RequestMapping(value = "/addServiceDataFileLog", method = RequestMethod.POST)
    public String addServiceDataFileLog(@ModelAttribute DynamicSqlParam<ServiceDataFileLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(serviceDataFileLogService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="2.3.3修改文件管存记录表")
	@RequestMapping(value = "/updateServiceDataFileLog", method = RequestMethod.POST)
    public String updateServiceDataFileLog(@ModelAttribute DynamicSqlParam<ServiceDataFileLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(serviceDataFileLogService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="2.3.4删除文件管存记录表")
	@RequestMapping(value = "/deleteServiceDataFileLog", method = RequestMethod.POST)
    public String deleteServiceDataFileLog(@ModelAttribute ServiceDataFileLog serviceDataFileLog) throws Exception {
		return JSON.toJSONString(serviceDataFileLogService.deleteServiceDataFileLog(serviceDataFileLog));
    }
	
	@ApiOperation(value="2.3.5查看文件管存记录表")
	@RequestMapping(value = "/checkServiceDataFileLog", method = RequestMethod.POST)
    public String checkServiceDataFileLog(@ModelAttribute DynamicSqlParam<ServiceDataFileLog> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(serviceDataFileLogService.executeSelect(dynamicSqlParam));
    }
	
}
