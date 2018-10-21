package com.lmis.pos.whsCover.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.pos.common.controller.BaseController;
import com.lmis.pos.whsCover.model.PosWhsCover;
import com.lmis.pos.whsCover.service.PosWhsCoverServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsCoverController
 * @Description: TODO(仓库覆盖区域设置控制层类)
 * @author codeGenerator
 * @date 2018-05-29 16:30:53
 * 
 */
@Api(value = "仓库覆盖区域设置")
@RestController
@RequestMapping(value="/pos")
public class PosWhsCoverController extends BaseController{

	@Resource(name="posWhsCoverServiceImpl")
	PosWhsCoverServiceInterface<PosWhsCover> posWhsCoverService;
	
	@ApiOperation(value="覆盖区域查询")
    @RequestMapping(value = "/whsAreaSearch", method = RequestMethod.POST)
    public String whsAreaSearch(@ModelAttribute PosWhsCover poWhsCover) throws Exception {
        return JSON.toJSONString(posWhsCoverService.whsAreaSearch(poWhsCover));
    }
    @ApiOperation(value="新增覆盖区域")
    @RequestMapping(value = "/whsBindingArea", method = RequestMethod.POST)
    public String whsBindingArea(String whsCode,String province,String citys) throws Exception {
        return JSON.toJSONString(posWhsCoverService.whsBindingArea(whsCode,province,citys));
    }
    @ApiOperation(value="删除覆盖区域")
    @RequestMapping(value = "/deleteBindingArea", method = RequestMethod.POST)
    public String deleteBindingArea(@ModelAttribute PosWhsCover poWhsCover) throws Exception {
        return JSON.toJSONString(posWhsCoverService.deleteBindingArea(poWhsCover));
    }
	
}
