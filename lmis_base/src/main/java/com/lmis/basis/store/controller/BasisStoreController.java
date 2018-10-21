package com.lmis.basis.store.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.basis.store.model.BasisStore;
import com.lmis.basis.store.service.BasisStoreServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisStoreController
 * @Description: TODO(店铺控制层类)
 * @author codeGenerator
 * @date 2018-01-19 16:10:57
 * 
 */
@Api(value = "基础模块-4.4店铺")
@RestController
@RequestMapping(value="/basis")
public class BasisStoreController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisStoreServiceImpl")
	BasisStoreServiceInterface<BasisStore> basisStoreService;
	
	@ApiOperation(value="4.4.1查询店铺")
	@RequestMapping(value = "/selectBasisStore", method = RequestMethod.POST)
    public String selectBasisStore(@ModelAttribute DynamicSqlParam<BasisStore> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisStoreService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.4.2新增店铺")
	@RequestMapping(value = "/addBasisStore", method = RequestMethod.POST)
    public String addBasisStore(@ModelAttribute DynamicSqlParam<BasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStoreService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.4.3修改店铺")
	@RequestMapping(value = "/updateBasisStore", method = RequestMethod.POST)
    public String updateBasisStore(@ModelAttribute DynamicSqlParam<BasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStoreService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.4.4删除店铺")
	@RequestMapping(value = "/deleteBasisStore", method = RequestMethod.POST)
    public String deleteBasisStore(@ModelAttribute BasisStore basisStore) throws Exception {
		return JSON.toJSONString(basisStoreService.deleteBasisStore(basisStore));
    }
	
	@ApiOperation(value="4.4.5查看店铺")
	@RequestMapping(value = "/checkBasisStore", method = RequestMethod.POST)
    public String checkBasisStore(@ModelAttribute DynamicSqlParam<BasisStore> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisStoreService.executeSelect(dynamicSqlParam));
    }
	
	@ApiOperation(value="4.4.6启用店铺/4.4.7禁用店铺")
	@RequestMapping(value = "/switchBasisStore", method = RequestMethod.POST)
    public String switchBasisStore(@ModelAttribute BasisStore basisStore) throws Exception {
		return JSON.toJSONString(basisStoreService.switchBasisStore(basisStore));
    }

	@ApiOperation(value="4.4.10过滤已选店铺")
	@RequestMapping(value = "/filterCheckedStore", method = RequestMethod.POST)
	public String filterCheckedStore(@ModelAttribute DynamicSqlParam<BasisStore> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
		return JSON.toJSONString(basisStoreService.filterCheckedStore(dynamicSqlParam, pageObject));
	}
}
