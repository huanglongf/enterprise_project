package com.lmis.pos.addrLib.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.addrLib.model.PosAddrLib;
import com.lmis.pos.addrLib.service.PosAddrLibServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosAddrLibController
 * @Description: TODO(地址库表（基础数据）控制层类)
 * @author codeGenerator
 * @date 2018-05-29 10:46:34
 * 
 */
@Api(value = "地址库表（基础数据）")
@RestController
@RequestMapping(value="/pos")
public class PosAddrLibController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posAddrLibServiceImpl")
	PosAddrLibServiceInterface<PosAddrLib> posAddrLibService;
	
	@ApiOperation(value="查询地址库表（基础数据）")
	@RequestMapping(value = "/selectPosAddrLib", method = RequestMethod.POST)
    public String selectPosAddrLib(@ModelAttribute PosAddrLib posAddrLib, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posAddrLibService.executeSelect(posAddrLib, pageObject));
    }

	@ApiOperation(value="新增地址库表（基础数据）")
	@RequestMapping(value = "/addPosAddrLib", method = RequestMethod.POST)
    public String addPosAddrLib(@ModelAttribute PosAddrLib posAddrLib) throws Exception {
        return JSON.toJSONString(posAddrLibService.executeInsert(posAddrLib));
    }

	@ApiOperation(value="修改地址库表（基础数据）")
	@RequestMapping(value = "/updatePosAddrLib", method = RequestMethod.POST)
    public String updatePosAddrLib(@ModelAttribute PosAddrLib posAddrLib) throws Exception {
        return JSON.toJSONString(posAddrLibService.executeUpdate(posAddrLib));
    }

	@ApiOperation(value="删除地址库表（基础数据）")
	@RequestMapping(value = "/deletePosAddrLib", method = RequestMethod.POST)
    public String deletePosAddrLib(@ModelAttribute PosAddrLib posAddrLib) throws Exception {
		return JSON.toJSONString(posAddrLibService.deletePosAddrLib(posAddrLib));
    }
	
	@ApiOperation(value="查看地址库表（基础数据）")
	@RequestMapping(value = "/checkPosAddrLib", method = RequestMethod.POST)
    public String checkPosAddrLib(@ModelAttribute PosAddrLib posAddrLib) throws Exception {
        return JSON.toJSONString(posAddrLibService.executeSelect(posAddrLib));
    }
	
}
