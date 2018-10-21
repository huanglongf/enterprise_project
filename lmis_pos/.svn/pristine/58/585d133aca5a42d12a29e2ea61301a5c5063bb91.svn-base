package com.lmis.pos.crdOut.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.pos.crdOut.model.PosCrdOut;
import com.lmis.pos.crdOut.service.PosCrdOutServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosCrdOutController
 * @Description: TODO(不拆分CRD设置控制层类)
 * @author codeGenerator
 * @date 2018-06-01 17:09:27
 * 
 */
@Api(value = "不拆分CRD设置")
@RestController
@RequestMapping(value="/pos")
public class PosCrdOutController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posCrdOutServiceImpl")
	private PosCrdOutServiceInterface<PosCrdOut> posCrdOutService;
	
	
	@ApiOperation(value="查询不拆分crd日期主表信息")
	@RequestMapping(value = "/selectPosCrdNoSplit", method = RequestMethod.POST)
    public String selectPosCrdNoSplit(@ModelAttribute PosCrdOut posCrdOut) throws Exception {
        return JSON.toJSONString(posCrdOutService.executeSelect(posCrdOut));
    }

	@ApiOperation(value="新增不拆分crd日期主表信息")
	@RequestMapping(value = "/addPosCrdNoSplit", method = RequestMethod.POST)
    public String addPosCrdNoSplit(@ModelAttribute PosCrdOut posCrdOut) throws Exception {
        return JSON.toJSONString(posCrdOutService.executeInsert(posCrdOut));
    }
	
	@ApiOperation(value="删除不拆分crd日期主表信息")
	@RequestMapping(value = "/deletePosCrdNoSplit", method = RequestMethod.POST)
    public String deletePosCrdNoSplit(@ModelAttribute PosCrdOut posCrdOut) throws Exception {
		return JSON.toJSONString(posCrdOutService.deletePosCrdData(posCrdOut));
    }
}
