package com.lmis.basis.supplier.controller;

import com.alibaba.fastjson.JSON;
import com.lmis.basis.supplier.model.BasisSupplier;
import com.lmis.basis.supplier.service.BasisSupplierServiceInterface;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** 
 * @ClassName: BasisSupplierController
 * @Description: TODO(供应商控制层类)
 * @author codeGenerator
 * @date 2018-03-12 14:22:46
 * 
 */
@Api(value = "供应商")
@RestController
@RequestMapping(value="/basis")
public class BasisSupplierController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisSupplierServiceImpl")
    BasisSupplierServiceInterface<BasisSupplier> basisSupplierService;
	
	@ApiOperation(value="4.1.1查询供应商")
	@RequestMapping(value = "/selectBasisSupplier", method = RequestMethod.POST)
    public String selectBasisSupplier(@ModelAttribute DynamicSqlParam<BasisSupplier> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisSupplierService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.1.2新增供应商")
	@RequestMapping(value = "/addBasisSupplier", method = RequestMethod.POST)
    public String addBasisSupplier(@ModelAttribute DynamicSqlParam<BasisSupplier> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisSupplierService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.1.3修改供应商")
	@RequestMapping(value = "/updateBasisSupplier", method = RequestMethod.POST)
    public String updateBasisSupplier(@ModelAttribute DynamicSqlParam<BasisSupplier> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisSupplierService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.1.4删除供应商")
	@RequestMapping(value = "/deleteBasisSupplier", method = RequestMethod.POST)
    public String deleteBasisSupplier(@ModelAttribute BasisSupplier basisSupplier) throws Exception {
		return JSON.toJSONString(basisSupplierService.deleteBasisSupplier(basisSupplier));
    }

    @ApiOperation(value="4.1.5查看供应商")
    @RequestMapping(value = "/checkBasisSupplier", method = RequestMethod.POST)
    public String checkBasisSupplier(@ModelAttribute DynamicSqlParam<BasisSupplier> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisSupplierService.executeSelect(dynamicSqlParam));
    }
    
    @ApiOperation(value="4.13.6启用供应商/4.13.7禁用供应商")
    @RequestMapping(value = "/switchBasisSupplier", method = RequestMethod.POST)
    public String switchBudgetMaster(@ModelAttribute BasisSupplier basisSupplier) throws Exception {
        return JSON.toJSONString(basisSupplierService.switchBasisSupplier(basisSupplier));
    }

	
}
