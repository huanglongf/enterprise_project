package com.lmis.setup.constantSql.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.setup.constantSql.model.SetupConstantSql;
import com.lmis.setup.constantSql.service.SetupConstantSqlServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SetupConstantSqlController
 * @Description: TODO(配置选项控制层)
 * @author Ian.Huang
 * @date 2017年12月11日 下午4:11:03 
 * 
 */
@Api(value = "公共配置-4.2页面显示选项")
@RestController
@RequestMapping(value="/setup")
public class SetupConstantSqlController {

	@Resource(name="setupConstantSqlServiceImpl")
	SetupConstantSqlServiceInterface<SetupConstantSql> setupConstantSqlService;
	
	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@ApiOperation(value="4.2.1查询页面显示选项")
	@RequestMapping(value = "/selectSetupConstantSql", method = RequestMethod.POST)
    public String selectSetupConstantSql(@ModelAttribute DynamicSqlParam<SetupConstantSql> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(setupConstantSqlService.executeSelect(dynamicSqlParam, pageObject));
    }
	
	@ApiOperation(value="4.2.2新增页面显示选项")
	@RequestMapping(value = "/addSetupConstantSql", method = RequestMethod.POST)
    public String addSetupConstantSql(@ModelAttribute DynamicSqlParam<SetupConstantSql> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupConstantSqlService.executeInsert(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.2.3修改页面显示选项")
	@RequestMapping(value = "/updateSetupConstantSql", method = RequestMethod.POST)
    public String updateSetupConstantSql(@ModelAttribute DynamicSqlParam<SetupConstantSql> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupConstantSqlService.executeUpdate(dynamicSqlParam));
    }
	
	@ApiOperation(value="执行页面显示选项")
	@RequestMapping(value = "/executeSetupConstantSql", method = RequestMethod.POST)
    public String executeSetupConstantSql(@ModelAttribute SetupConstantSql setupConstantSql) throws Exception {
        return JSON.toJSONString(setupConstantSqlService.executeSetupConstantSql(setupConstantSql));
    }
	
	@ApiOperation(value="4.2.4删除页面显示选项")
	@RequestMapping(value = "/deleteSetupConstantSql", method = RequestMethod.POST)
    public String deleteSetupConstantSql(@ModelAttribute SetupConstantSql setupConstantSql) throws Exception {
        return JSON.toJSONString(setupConstantSqlService.deleteSetupConstantSql(setupConstantSql));
    }
	
	@ApiOperation(value="4.2.5查看页面显示选项")
	@RequestMapping(value = "/getSetupConstantSql", method = RequestMethod.POST)
    public String getSetupConstantSql(@ModelAttribute DynamicSqlParam<SetupConstantSql> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(setupConstantSqlService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="验证 sql 语句是否是合法的 select 语句")
	@RequestMapping(value = "/validSql", method = RequestMethod.POST)
	public String validSql(@RequestParam String sql) throws Exception {
		return JSON.toJSONString( setupConstantSqlService.checkValidSQL(sql));
	}
	
}
