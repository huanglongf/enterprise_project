package com.lmis.jbasis.warehouse.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.jbasis.warehouse.model.JbasisWarehouse;
import com.lmis.jbasis.warehouse.service.JbasisWarehouseServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: JbasisWarehouseController
 * @Description: TODO(仓库信息管理控制层类)
 * @author codeGenerator
 * @date 2018-04-17 09:52:14
 * 
 */
@Api(value = "仓库信息管理")
@RestController
@RequestMapping(value="/jbasis")
public class JbasisWarehouseController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="jbasisWarehouseServiceImpl")
	JbasisWarehouseServiceInterface<JbasisWarehouse> jbasisWarehouseService;
	
	@ApiOperation(value="查询仓库信息管理")
	@RequestMapping(value = "/selectJbasisWarehouse", method = RequestMethod.POST)
    public String selectJbasisWarehouse(@ModelAttribute DynamicSqlParam<JbasisWarehouse> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(jbasisWarehouseService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="新增仓库信息管理")
	@RequestMapping(value = "/addJbasisWarehouse", method = RequestMethod.POST)
    public String addJbasisWarehouse(@ModelAttribute DynamicSqlParam<JbasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWarehouseService.executeInsert(dynamicSqlParam,0));
    }
	
   @ApiOperation(value="导入仓库信息管理")
    @RequestMapping(value = "/ImportJbasisWarehouse", method = RequestMethod.POST)
    public String ImportJbasisWarehouse(@ModelAttribute DynamicSqlParam<JbasisWarehouse> dynamicSqlParam){
       try{
           return JSON.toJSONString(jbasisWarehouseService.executeInsert(dynamicSqlParam,1));
       }catch (Exception e) {
           return JSON.toJSONString( new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "导入数据错误"));
       }
    }

	@ApiOperation(value="修改仓库信息管理")
	@RequestMapping(value = "/updateJbasisWarehouse", method = RequestMethod.POST)
    public String updateJbasisWarehouse(@ModelAttribute DynamicSqlParam<JbasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWarehouseService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除仓库信息管理")
	@RequestMapping(value = "/deleteJbasisWarehouse", method = RequestMethod.POST)
    public String deleteJbasisWarehouse(@ModelAttribute JbasisWarehouse jbasisWarehouse) throws Exception {
		return JSON.toJSONString(jbasisWarehouseService.deleteJbasisWarehouse(jbasisWarehouse));
    }
	
	@ApiOperation(value="查看仓库信息管理")
	@RequestMapping(value = "/checkJbasisWarehouse", method = RequestMethod.POST)
    public String checkJbasisWarehouse(@ModelAttribute DynamicSqlParam<JbasisWarehouse> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(jbasisWarehouseService.executeSelect(dynamicSqlParam));
    }	
	
	@ApiOperation(value="启用仓库/禁用仓库")
	@RequestMapping(value = "/switchJbasisWarehouse", method = RequestMethod.POST)
    public String switchJbasisWarehouse(@ModelAttribute JbasisWarehouse jbasisWarehouse) throws Exception {
		return JSON.toJSONString(jbasisWarehouseService.switchJbasisWarehouse(jbasisWarehouse));
    }
	
	
}
