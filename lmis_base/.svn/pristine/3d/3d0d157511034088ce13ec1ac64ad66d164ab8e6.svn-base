package com.lmis.basis.exchangeRate.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.basis.exchangeRate.model.BasisExchangeRate;
import com.lmis.basis.exchangeRate.service.BasisExchangeRateServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisExchangeRateController
 * @Description: TODO(汇率控制层类)
 * @author codeGenerator
 * @date 2018-01-18 15:00:45
 * 
 */
@Api(value = "基础模块-4.2汇率")
@RestController
@RequestMapping(value="/basis")
public class BasisExchangeRateController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisExchangeRateServiceImpl")
	BasisExchangeRateServiceInterface<BasisExchangeRate> basisExchangeRateService;
	
	@ApiOperation(value="4.2.1查询汇率")
	@RequestMapping(value = "/selectBasisExchangeRate", method = RequestMethod.POST)
    public String selectBasisExchangeRate(@ModelAttribute DynamicSqlParam<BasisExchangeRate> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisExchangeRateService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.2.2新增汇率")
	@RequestMapping(value = "/addBasisExchangeRate", method = RequestMethod.POST)
    public String addBasisExchangeRate(@ModelAttribute DynamicSqlParam<BasisExchangeRate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisExchangeRateService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.2.3修改汇率")
	@RequestMapping(value = "/updateBasisExchangeRate", method = RequestMethod.POST)
    public String updateBasisExchangeRate(@ModelAttribute DynamicSqlParam<BasisExchangeRate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisExchangeRateService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.2.4删除汇率")
	@RequestMapping(value = "/deleteBasisExchangeRate", method = RequestMethod.POST)
    public String deleteBasisExchangeRate(@ModelAttribute BasisExchangeRate basisExchangeRate) throws Exception {
		return JSON.toJSONString(basisExchangeRateService.deleteBasisExchangeRate(basisExchangeRate));
    }
	
	@ApiOperation(value="4.2.5查看汇率")
	@RequestMapping(value = "/checkBasisExchangeRate", method = RequestMethod.POST)
    public String checkBasisExchangeRate(@ModelAttribute DynamicSqlParam<BasisExchangeRate> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisExchangeRateService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.2.6启用汇率/4.2.7禁用汇率")
	@RequestMapping(value = "/switchBasisExchangeRate", method = RequestMethod.POST)
    public String switchBasisExchangeRate(@ModelAttribute BasisExchangeRate basisExchangeRate) throws Exception {
		return JSON.toJSONString(basisExchangeRateService.switchBasisExchangeRate(basisExchangeRate));
    }
	
}
