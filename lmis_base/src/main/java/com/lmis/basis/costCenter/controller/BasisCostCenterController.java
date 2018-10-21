package com.lmis.basis.costCenter.controller;

import javax.annotation.Resource;

import com.lmis.basis.costCenter.model.ViewBasisCostCenter;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.basis.costCenter.model.BasisCostCenter;
import com.lmis.basis.costCenter.service.BasisCostCenterServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/** 
 * @ClassName: BasisCostCenterController
 * @Description: TODO(成本中心控制层类)
 * @author codeGenerator
 * @date 2018-01-31 15:43:21
 * 
 */
@Api(value = "基础模块-4.12成本中心")
@RestController
@RequestMapping(value="/basis")
public class BasisCostCenterController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisCostCenterServiceImpl")
	BasisCostCenterServiceInterface<BasisCostCenter> basisCostCenterService;

    @Resource(name = "dynamicSqlServiceImpl")
    DynamicSqlServiceInterface<BasisCostCenter> dynamicSqlService;
	
	@ApiOperation(value="4.12.1查询成本中心")
	@RequestMapping(value = "/selectBasisCostCenter", method = RequestMethod.POST)
    public String selectBasisCostCenter(@ModelAttribute ViewBasisCostCenter basisCostCenter) throws Exception {
        List<ViewBasisCostCenter> orgList = basisCostCenterService.findCostCenter(basisCostCenter);
        LmisResult<List<ViewBasisCostCenter>> lmisResult = new LmisResult<>();
        lmisResult.setData(orgList);
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }

	@ApiOperation(value="4.12.2成本中心保存/修改")
	@RequestMapping(value = "/addOrUpdateCostCenter", method = RequestMethod.POST)
    public String addOrUpdateCostCenter(@ModelAttribute BasisCostCenter costCenter) throws Exception {
        //BasisCostCenter costCenter = dynamicSqlService.generateTableModel(dynamicSqlParam, new BasisCostCenter()).getTableModel();
        return JSON.toJSONString(basisCostCenterService.addOrUpdateCostCenter(costCenter));
    }

	@ApiOperation(value="4.12.3删除成本中心")
	@RequestMapping(value = "/deleteBasisCostCenter", method = RequestMethod.POST)
    public String deleteBasisCostCenter(@ModelAttribute BasisCostCenter basisCostCenter) throws Exception {
		return JSON.toJSONString(basisCostCenterService.deleteBasisCostCenter(basisCostCenter));
    }
	
	@ApiOperation(value="4.12.4查看成本中心")
	@RequestMapping(value = "/checkBasisCostCenter", method = RequestMethod.POST)
    public String checkBasisCostCenter(@ModelAttribute DynamicSqlParam<BasisCostCenter> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisCostCenterService.executeSelect(dynamicSqlParam));
    }

    @ApiOperation(value="4.12.6启用成本中心/4.12.7禁用成本中心")
    @RequestMapping(value = "/switchBasisCostCenter", method = RequestMethod.POST)
    public String switchBasisCostCenter(@ModelAttribute BasisCostCenter basisCostCenter) throws Exception {
        return JSON.toJSONString(basisCostCenterService.switchBasisCostCenter(basisCostCenter));
    }

    @ApiOperation(value="4.7.13下拉框查询成本中心")
    @RequestMapping(value = "/findCostCenter", method = RequestMethod.POST)
    public String findCostCenter(@ModelAttribute ViewBasisCostCenter costCenter) throws Exception {
        List<ViewBasisCostCenter> centerList = basisCostCenterService.findCostCenter(costCenter);
        LmisResult<List<ViewBasisCostCenter>> lmisResult = new LmisResult<>();
        lmisResult.setData(centerList);
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
    
    @RequestMapping(value = "/exportCostCenter", method = RequestMethod.POST)
    public String exportCostCenter(int pageSize) throws Exception {
        return JSON.toJSONString(basisCostCenterService.exportCostCenter(pageSize));
    }
    
	
}
