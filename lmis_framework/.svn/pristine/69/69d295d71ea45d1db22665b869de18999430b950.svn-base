package com.lmis.sys.message.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.sys.message.model.SysMessage;
import com.lmis.sys.message.service.SysMessageServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysMessageController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-05-22 20:04:53
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/sys")
public class SysMessageController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysMessageServiceImpl")
	SysMessageServiceInterface<SysMessage> sysMessageService;
	
	@Resource(name="dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<SysMessage> dynamicSqlService;
	
	@ApiOperation(value="查询")
	@RequestMapping(value = "/selectSysMessage", method = RequestMethod.POST)
    public String selectSysMessage(@ModelAttribute DynamicSqlParam<SysMessage> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(sysMessageService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增")
	@RequestMapping(value = "/addSysMessage", method = RequestMethod.POST)
    public String addSysMessage(@ModelAttribute DynamicSqlParam<SysMessage> dynamicSqlParam) throws Exception {
        SysMessage sysMessage = dynamicSqlService.generateTableModel((DynamicSqlParam<SysMessage>) dynamicSqlParam, new SysMessage()).getTableModel();
	    return JSON.toJSONString(sysMessageService.executeInsert(sysMessage));
    }

	@ApiOperation(value="修改")
	@RequestMapping(value = "/updateSysMessage", method = RequestMethod.POST)
    public String updateSysMessage(@ModelAttribute DynamicSqlParam<SysMessage> dynamicSqlParam) throws Exception {
        SysMessage sysMessage = dynamicSqlService.generateTableModel((DynamicSqlParam<SysMessage>) dynamicSqlParam, new SysMessage()).getTableModel();
	    return JSON.toJSONString(sysMessageService.executeUpdate(sysMessage));
    }

	@ApiOperation(value="删除")
	@RequestMapping(value = "/deleteSysMessage", method = RequestMethod.POST)
    public String deleteSysMessage(@ModelAttribute SysMessage sysMessage) throws Exception {
		return JSON.toJSONString(sysMessageService.deleteSysMessage(sysMessage));
    }
	
	@ApiOperation(value="查看")
	@RequestMapping(value = "/checkSysMessage", method = RequestMethod.POST)
    public String checkSysMessage(@ModelAttribute DynamicSqlParam<SysMessage> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysMessageService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="查询多个消息")
    @RequestMapping(value = "/getSysMessages", method = RequestMethod.POST)
    public String getSysMessages(@ModelAttribute List<String> listCode) throws Exception {
        return JSON.toJSONString(sysMessageService.getSysMessages(listCode));
    }
	
}
