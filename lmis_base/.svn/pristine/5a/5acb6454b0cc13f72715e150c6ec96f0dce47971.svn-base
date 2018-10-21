package com.lmis.sys.roleFunctionButton.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dataFormat.JsonUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.functionButton.model.SysFunctionButton;
import com.lmis.sys.functionButton.service.SysFunctionButtonServiceInterface;
import com.lmis.sys.roleFunctionButton.model.SysRoleFunctionButton;
import com.lmis.sys.roleFunctionButton.service.SysRoleFunctionButtonServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: SysRoleFunctionButtonController
 * @Description: TODO(控制层类)
 * @author codeGenerator
 * @date 2018-01-09 17:35:11
 * 
 */
@Api(value = "")
@RestController
@RequestMapping(value="/sys")
public class SysRoleFunctionButtonController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="sysRoleFunctionButtonServiceImpl")
	SysRoleFunctionButtonServiceInterface<SysRoleFunctionButton> sysRoleFunctionButtonService;
	
	@Resource(name="sysFunctionButtonServiceImpl")
	SysFunctionButtonServiceInterface<SysFunctionButton> sysFunctionButtonService;
	
	
	@ApiOperation(value="4.2.6角色与功能菜单查询")
	@RequestMapping(value = "/selectRoleFunctionButton", method = RequestMethod.POST)
    public String selectRoleFunctionButton(@ModelAttribute SysRoleFunctionButton sysRoleFunctionButton) throws Exception {
		Map<String,Object> resultMap=new HashMap<>();
		List<SysRoleFunctionButton> roleFBList = sysRoleFunctionButtonService.sysRoleFunctionButton(sysRoleFunctionButton);
		resultMap.put("roleFBList", roleFBList);
		SysFunctionButton sysFB = new SysFunctionButton();
		List<SysFunctionButton> fBList=sysFunctionButtonService.findFunctionButton(sysFB);
		resultMap.put("fBList", fBList);
		LmisResult<Map<String,Object>> lmisResult = new LmisResult<>();
		lmisResult.setData(resultMap);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return JSON.toJSONString(lmisResult);
    }
	
	
	@ApiOperation(value="4.2.7角色与功能菜单保存")
	@RequestMapping(value = "/addRoleFunctionButton", method = RequestMethod.POST)
    public String addRoleFunctionButton(String fbList) throws Exception{
		List<SysRoleFunctionButton> roleFbList = JsonUtils.json2List(fbList, SysRoleFunctionButton.class);
        return JSON.toJSONString(sysRoleFunctionButtonService.addRoleFunctionButton(roleFbList));
    }
	
	
}
