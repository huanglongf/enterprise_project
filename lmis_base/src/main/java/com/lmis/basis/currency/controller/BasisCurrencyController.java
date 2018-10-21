package com.lmis.basis.currency.controller;

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
import com.lmis.basis.currency.model.BasisCurrency;
import com.lmis.basis.currency.service.BasisCurrencyServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisCurrencyController
 * @Description: TODO(货币控制层类)
 * @author codeGenerator
 * @date 2018-01-18 14:46:24
 * 
 */
@Api(value = "基础模块-4.1货币")
@RestController
@RequestMapping(value="/basis")
public class BasisCurrencyController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisCurrency> dynamicSqlService;
	
	
	@Resource(name="basisCurrencyServiceImpl")
	BasisCurrencyServiceInterface<BasisCurrency> basisCurrencyService;
	
	@ApiOperation(value="4.1.1查询货币")
	@RequestMapping(value = "/selectBasisCurrency", method = RequestMethod.POST)
    public String selectBasisCurrency(@ModelAttribute DynamicSqlParam<BasisCurrency> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisCurrencyService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.1.2新增货币")
	@RequestMapping(value = "/addBasisCurrency", method = RequestMethod.POST)
    public String addBasisCurrency(@ModelAttribute DynamicSqlParam<BasisCurrency> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisCurrencyService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.1.3修改货币")
	@RequestMapping(value = "/updateBasisCurrency", method = RequestMethod.POST)
    public String updateBasisCurrency(@ModelAttribute DynamicSqlParam<BasisCurrency> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisCurrencyService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.1.4删除货币")
	@RequestMapping(value = "/deleteBasisCurrency", method = RequestMethod.POST)
    public String deleteBasisCurrency(@ModelAttribute BasisCurrency basisCurrency) throws Exception {
		return JSON.toJSONString(basisCurrencyService.deleteBasisCurrency(basisCurrency));
    }
	
	@ApiOperation(value="4.1.5查看货币")
	@RequestMapping(value = "/checkBasisCurrency", method = RequestMethod.POST)
    public String checkBasisCurrency(@ModelAttribute DynamicSqlParam<BasisCurrency> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisCurrencyService.executeSelect(dynamicSqlParam));
    }
	@ApiOperation(value="4.1.6启用货币/4.1.7禁用货币")
	@RequestMapping(value = "/switchBasisCurrency", method = RequestMethod.POST)
    public String switchBasisCurrency(@ModelAttribute BasisCurrency basisCurrency) throws Exception {
		return JSON.toJSONString(basisCurrencyService.switchBasisCurrency(basisCurrency));
    }

}
