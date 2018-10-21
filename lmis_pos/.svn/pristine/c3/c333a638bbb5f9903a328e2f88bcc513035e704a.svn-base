package com.lmis.pos.whsSurplusSc.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusScByDate;
import com.lmis.pos.whsSurplusSc.service.PosWhsSurplusScServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: PosWhsSurplusScController
 * @Description: TODO(仓库剩余入库能力分析控制层类)
 * @author codeGenerator
 * @date 2018-05-30 17:16:40
 * 
 */
@Api(value = "仓库剩余入库能力分析")
@RestController
@RequestMapping(value="/pos")
public class PosWhsSurplusScController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsSurplusScServiceImpl")
	PosWhsSurplusScServiceInterface<PosWhsSurplusSc> posWhsSurplusScService;
	
	@ApiOperation(value="查询仓库剩余入库能力分析")
	@RequestMapping(value = "/selectPosWhsSurplusSc", method = RequestMethod.POST)
    public String selectPosWhsSurplusSc(@ModelAttribute DynamicSqlParam<PosWhsSurplusSc> dynamicSqlParam, @ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsSurplusScService.executeSelect(dynamicSqlParam, pageObject));
    }
	@ApiOperation(value="查询仓库剩余入库能力分析")
	@RequestMapping(value = "/selectPosWhsSurplusScList", method = RequestMethod.POST)
	public String selectPosWhsSurplusScList(@ModelAttribute PosWhsSurplusScByDate posWhsSurplusSc, @ModelAttribute LmisPageObject pageObject) throws Exception {
	    pageObject.setDefaultPage(defPageNum, defPageSize);
	    return JSON.toJSONString(posWhsSurplusScService.selectPosWhsSurplusScList(posWhsSurplusSc, pageObject));
	}

	@ApiOperation(value="新增仓库剩余入库能力分析")
	@RequestMapping(value = "/addPosWhsSurplusSc", method = RequestMethod.POST)
    public String addPosWhsSurplusSc(@ModelAttribute DynamicSqlParam<PosWhsSurplusSc> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsSurplusScService.executeInsert(dynamicSqlParam));
    }

	@ApiOperation(value="修改仓库剩余入库能力分析")
	@RequestMapping(value = "/updatePosWhsSurplusSc", method = RequestMethod.POST)
    public String updatePosWhsSurplusSc(@ModelAttribute DynamicSqlParam<PosWhsSurplusSc> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsSurplusScService.executeUpdate(dynamicSqlParam));
    }

	@ApiOperation(value="删除仓库剩余入库能力分析")
	@RequestMapping(value = "/deletePosWhsSurplusSc", method = RequestMethod.POST)
    public String deletePosWhsSurplusSc(@ModelAttribute PosWhsSurplusSc posWhsSurplusSc) throws Exception {
		return JSON.toJSONString(posWhsSurplusScService.deletePosWhsSurplusSc(posWhsSurplusSc));
    }
	
	@ApiOperation(value="查看仓库剩余入库能力分析")
	@RequestMapping(value = "/checkPosWhsSurplusSc", method = RequestMethod.POST)
    public String checkPosWhsSurplusSc(@ModelAttribute DynamicSqlParam<PosWhsSurplusSc> dynamicSqlParam) throws Exception {
        return JSON.toJSONString(posWhsSurplusScService.executeSelect(dynamicSqlParam));
    }
	
}
