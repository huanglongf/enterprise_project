package com.lmis.common.getData.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.getData.model.ConstantData;
import com.lmis.common.getData.service.GetConstantDataServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Api(value = "公共配置-4.5公共方法")
@RestController
@RequestMapping(value="/common")
public class GetConstantDataController {
	
	private static Log logger = LogFactory.getLog(GetConstantDataController.class);

	@Resource(name="getConstantDataServiceImpl")
	GetConstantDataServiceInterface<ConstantData> getConstantDataService;
	
	@Autowired
	private HttpSession session;

	@ApiOperation(value="4.5.1获取下拉列表参数")
	@RequestMapping(value = "/getElementData", method = RequestMethod.POST)
    public String getElementData(@RequestParam String jsonStr) {
        return JSON.toJSONString(getConstantDataService.getConstantDate(jsonStr));
    }
	
	@ApiOperation(value="4.2.6同步页面常量数据")
	@RequestMapping(value = "/redisForConstantSql", method = RequestMethod.POST)
    public String redisForConstantSql() {
		logger.info("~~~~~~user id~~~~~~"+session.getAttribute("lmisUserId"));
        return JSON.toJSONString(getConstantDataService.redisForConstantSql());
    }

	@ApiOperation(value="4.5.2带条件获取数据")
	@RequestMapping(value = "/getElementValue", method = RequestMethod.POST)
    public String getElementValue(@ModelAttribute ConstantData constantData) {
        return JSON.toJSONString(getConstantDataService.getElementValue(constantData));
    }

}