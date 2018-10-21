package com.lmis.pos.common.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.job.InventoryScheduled;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "公共模块-入库能力表初始化方法")
@RestController
@RequestMapping(value="/posWhsSurplusSc")
public class InventoryScheduledController extends BaseController {
	
	@Resource(name = "inventoryScheduled")
	private InventoryScheduled inventoryScheduled;

	
	@ApiOperation(value="入库能力表初始化方法")
	@RequestMapping(value = "/doInit", method = RequestMethod.POST)
    public String downLoadTemplete(){
		try {
			inventoryScheduled.timer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        return JSON.toJSONString(new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"初始化异常"));
		}
        return JSON.toJSONString(new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS,""));
    }
}
