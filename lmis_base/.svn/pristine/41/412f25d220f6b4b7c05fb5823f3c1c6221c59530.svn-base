package com.lmis.sys.functionButton.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.functionButton.model.SysFunctionButton;
import com.lmis.sys.functionButton.service.SysFunctionButtonServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysFunctionButtonController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 13:09:08
 * 
 */
@Api(value = "权限模块-4.4功能菜单/按钮")
@RestController
@RequestMapping(value="/sys")
public class SysFunctionButtonController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysFunctionButtonServiceImpl")
	SysFunctionButtonServiceInterface<SysFunctionButton> sysFunctionButtonService;
	
	@ApiOperation(value="4.4.1菜单查询")
	@RequestMapping(value = "/selectSysFunctionButton", method = RequestMethod.POST)
    public String selectSysFunctionButton(@ModelAttribute SysFunctionButton sysFunctionButton) throws Exception {
		List<SysFunctionButton> fbList = sysFunctionButtonService.findFunctionButton(sysFunctionButton);
		LmisResult<List<SysFunctionButton>> lmisResult = new LmisResult<>();
		lmisResult.setData(fbList);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }

	
	@ApiOperation(value="4.4.2菜单保存/修改")
	@RequestMapping(value = "/addOrUpdateSysFunctionButton", method = RequestMethod.POST)
    public String addOrUpdateSysFunctionButton(@ModelAttribute SysFunctionButton functionButton) throws Exception{
        return JSON.toJSONString(sysFunctionButtonService.addOrUpdateSysFunctionButton(functionButton));
    }
	

	@ApiOperation(value="4.4.3菜单删除")
	@RequestMapping(value = "/deleteSysFunctionButton", method = RequestMethod.POST)
    public String deleteSysFunctionButton(@ModelAttribute SysFunctionButton sysFunctionButton) throws Exception{
		return JSON.toJSONString(sysFunctionButtonService.deleteSysFunctionButton(sysFunctionButton));
    }
	
	@ApiOperation(value="4.4.4菜单查看")
	@RequestMapping(value = "/checkSysFunctionButton", method = RequestMethod.POST)
    public String checkSysFunctionButton(@ModelAttribute DynamicSqlParam<SysFunctionButton> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(sysFunctionButtonService.executeSelect(dynamicSqlParam));
    }
	
}
