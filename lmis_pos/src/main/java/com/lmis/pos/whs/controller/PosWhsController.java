package com.lmis.pos.whs.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.controller.BaseController;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whs.service.PosWhsServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsController
 * @Description: TODO(仓库主表控制层类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 */
@Api(value = "仓库主表")
@RestController
@RequestMapping(value="/pos")
public class PosWhsController extends BaseController{

	@Resource(name="posWhsServiceImpl")
	PosWhsServiceInterface<PosWhs> posWhsService;
	
	@ApiOperation(value="查询仓库主表")
	@RequestMapping(value = "/selectPosWhs", method = RequestMethod.POST)
    public String selectPosWhs(@ModelAttribute PosWhs poWhs, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsService.executeSelect(poWhs, pageObject));
    }

	@ApiOperation(value="新增仓库主表")
	@RequestMapping(value = "/addPosWhs", method = RequestMethod.POST)
    public String addPosWhs(@ModelAttribute PosWhs poWhs) throws Exception {
        return JSON.toJSONString(posWhsService.executeInsert(poWhs));
    }
	
	@ApiOperation(value="判断code,name重复")
	@RequestMapping(value = "/checkCodeName", method = RequestMethod.POST)
	public String checkCodeName(@ModelAttribute PosWhs poWhs) throws Exception {
	    return JSON.toJSONString(posWhsService.checkCodeName(poWhs));
	}

	@ApiOperation(value="修改仓库主表")
	@RequestMapping(value = "/updatePosWhs", method = RequestMethod.POST)
    public String updatePosWhs(@ModelAttribute PosWhs poWhs) throws Exception {
        return JSON.toJSONString(posWhsService.executeUpdate(poWhs));
    }

	@ApiOperation(value="删除仓库主表")
	@RequestMapping(value = "/deletePosWhs", method = RequestMethod.POST)
    public String deletePosWhs(@ModelAttribute PosWhs posWhs) throws Exception {
		return JSON.toJSONString(posWhsService.deletePosWhs(posWhs));
    }
	
	@ApiOperation(value="查看仓库主表")
	@RequestMapping(value = "/checkPosWhs", method = RequestMethod.POST)
    public String checkPosWhs(@ModelAttribute PosWhs poWhs) throws Exception {
        return JSON.toJSONString(posWhsService.executeSelect(poWhs));
    }
	
	@ApiOperation(value="启用/禁用")
    @RequestMapping(value = "/switchWhs", method = RequestMethod.POST)
    public String switchWhs(@ModelAttribute PosWhs poWhs) throws Exception {
        return JSON.toJSONString(posWhsService.switchWhs(poWhs));
    }
	
}
