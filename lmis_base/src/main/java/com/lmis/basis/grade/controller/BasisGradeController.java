package com.lmis.basis.grade.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.basis.grade.model.BasisGrade;
import com.lmis.basis.grade.service.BasisGradeServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: BasisGradeController
 * @Description: TODO(职级控制层类)
 * @author codeGenerator
 * @date 2018-01-19 16:11:26
 * 
 */
@Api(value = "基础模块-4.5职级")
@RestController
@RequestMapping(value="/basis")
public class BasisGradeController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="basisGradeServiceImpl")
	BasisGradeServiceInterface<BasisGrade> basisGradeService;
	
	@ApiOperation(value="4.5.1查询职级")
	@RequestMapping(value = "/selectBasisGrade", method = RequestMethod.POST)
    public String selectBasisGrade(@ModelAttribute DynamicSqlParam<BasisGrade> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(basisGradeService.executeSelect(dynamicSqlParam, pageObject));
    }

	@ApiOperation(value="4.5.2新增职级")
	@RequestMapping(value = "/addBasisGrade", method = RequestMethod.POST)
    public String addBasisGrade(@ModelAttribute DynamicSqlParam<BasisGrade> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisGradeService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="4.5.3修改职级")
	@RequestMapping(value = "/updateBasisGrade", method = RequestMethod.POST)
    public String updateBasisGrade(@ModelAttribute DynamicSqlParam<BasisGrade> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisGradeService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="4.5.4删除职级")
	@RequestMapping(value = "/deleteBasisGrade", method = RequestMethod.POST)
    public String deleteBasisGrade(@ModelAttribute BasisGrade basisGrade) throws Exception {
		return JSON.toJSONString(basisGradeService.deleteBasisGrade(basisGrade));
    }
	
	@ApiOperation(value="4.5.5查看职级")
	@RequestMapping(value = "/checkBasisGrade", method = RequestMethod.POST)
    public String checkBasisGrade(@ModelAttribute DynamicSqlParam<BasisGrade> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(basisGradeService.executeSelect(dynamicSqlParam));
    }
	@ApiOperation(value="4.5.6启用职级/4.5.7禁用职级")
	@RequestMapping(value = "/switchBasisGrade", method = RequestMethod.POST)
    public String switchBasisGrade(@ModelAttribute BasisGrade basisGrade) throws Exception {
		return JSON.toJSONString(basisGradeService.switchBasisGrade(basisGrade));
    }
}
