package com.lmis.basis.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.basis.warehouse.model.BasisWarehouse;
import com.lmis.basis.warehouse.service.BasisWarehouseServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisWarehouseController
 * @Description: TODO(仓库控制层类)
 * @author codeGenerator
 * @date 2018-01-19 15:05:00
 * 
 */
@Api(value = "基础模块-4.3仓库")
@RestController
@RequestMapping(value="/basis")
public class BasisWarehouseController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisWarehouseServiceImpl")
	BasisWarehouseServiceInterface<BasisWarehouse> basisWarehouseService;
	
	@ApiOperation(value="4.3.1查询仓库")
	@RequestMapping(value = "/selectBasisWarehouse", method = RequestMethod.POST)
    public String selectBasisWarehouse(@ModelAttribute DynamicSqlParam<BasisWarehouse> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisWarehouseService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.3.2新增仓库")
	@RequestMapping(value = "/addBasisWarehouse", method = RequestMethod.POST)
    public String addBasisWarehouse(@ModelAttribute DynamicSqlParam<BasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisWarehouseService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.3.3修改仓库")
	@RequestMapping(value = "/updateBasisWarehouse", method = RequestMethod.POST)
    public String updateBasisWarehouse(@ModelAttribute DynamicSqlParam<BasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisWarehouseService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.3.4删除仓库")
	@RequestMapping(value = "/deleteBasisWarehouse", method = RequestMethod.POST)
    public String deleteBasisWarehouse(@ModelAttribute BasisWarehouse basisWarehouse) throws Exception {
		return JSON.toJSONString(basisWarehouseService.deleteBasisWarehouse(basisWarehouse));
    }
	
	@ApiOperation(value="4.3.5查看仓库")
	@RequestMapping(value = "/checkBasisWarehouse", method = RequestMethod.POST)
    public String checkBasisWarehouse(@ModelAttribute DynamicSqlParam<BasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisWarehouseService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.3.6启用仓库/4.3.7禁用仓库")
	@RequestMapping(value = "/switchBasisWarehouse", method = RequestMethod.POST)
    public String switchBasisWarehouse(@ModelAttribute BasisWarehouse basisWarehouse) throws Exception {
		return JSON.toJSONString(basisWarehouseService.switchBasisWarehouse(basisWarehouse));
    }
	
}
