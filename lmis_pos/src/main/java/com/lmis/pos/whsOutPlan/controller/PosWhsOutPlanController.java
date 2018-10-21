package com.lmis.pos.whsOutPlan.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlan;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;
import com.lmis.pos.whsOutPlan.service.PosWhsOutPlanServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * @ClassName: PosWhsOutPlanController
 * @Description: TODO(仓库出库计划控制层类)
 * @author codeGenerator
 * @date 2018-05-29 15:13:58
 * 
 */
@Api(value = "仓库出库计划")
@RestController
@RequestMapping(value="/pos")
public class PosWhsOutPlanController {

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Resource(name="posWhsOutPlanServiceImpl")
	PosWhsOutPlanServiceInterface<PosWhsOutPlan> posWhsOutPlanService;
	
	@ApiOperation(value="查询仓库出库计划")
	@RequestMapping(value = "/selectPosWhsOutPlan", method = RequestMethod.POST)
    public String selectPosWhsOutPlan(@ModelAttribute PosWhsOutPlan posWhsOutPlan,@ModelAttribute LmisPageObject pageObject) throws Exception {
		pageObject.setDefaultPage(defPageNum, defPageSize);
        return JSON.toJSONString(posWhsOutPlanService.executeSelect(posWhsOutPlan, pageObject));
    }
	@ApiOperation(value="查询导入日志")
	@RequestMapping(value = "/selectPosWhsOutPlanLog", method = RequestMethod.POST)
	public String selectPosWhsOutPlanLog(@ModelAttribute PosWhsOutPlanLog posWhsOutPlanLog,@ModelAttribute LmisPageObject pageObject) throws Exception {
	    pageObject.setDefaultPage(defPageNum, defPageSize);
	    return JSON.toJSONString(posWhsOutPlanService.selectPosWhsOutPlanLog(posWhsOutPlanLog, pageObject));
	}

	@ApiOperation(value="新增仓库出库计划")
	@RequestMapping(value = "/addPosWhsOutPlan", method = RequestMethod.POST)
    public String addPosWhsOutPlan(@ModelAttribute PosWhsOutPlan posWhsOutPlan) throws Exception {
        return JSON.toJSONString(posWhsOutPlanService.executeInsert(posWhsOutPlan));
    }
	@ApiOperation(value="导入")
	@RequestMapping(value = "/PosWhsOutPlanUpload", method = RequestMethod.POST)
	public String addPosWhsOutPlan(@RequestParam @ApiParam(value="上传文件",required=true) MultipartFile file) throws Exception {
	    return JSON.toJSONString(posWhsOutPlanService.PosWhsOutPlanUpload(file));
	}

	@ApiOperation(value="修改仓库出库计划")
	@RequestMapping(value = "/updatePosWhsOutPlan", method = RequestMethod.POST)
    public String updatePosWhsOutPlan(@ModelAttribute PosWhsOutPlan posWhsOutPlan) throws Exception {
        return JSON.toJSONString(posWhsOutPlanService.executeUpdate(posWhsOutPlan));
    }

	@ApiOperation(value="删除仓库出库计划")
	@RequestMapping(value = "/deletePosWhsOutPlan", method = RequestMethod.POST)
    public String deletePosWhsOutPlan(@ModelAttribute PosWhsOutPlan posWhsOutPlan) throws Exception {
		return JSON.toJSONString(posWhsOutPlanService.deletePosWhsOutPlan(posWhsOutPlan));
    }
	@ApiOperation(value="删除导入日志")
	@RequestMapping(value = "/deletePosWhsOutPlanLog", method = RequestMethod.POST)
	public String deletePosWhsOutPlanLog(@ModelAttribute PosWhsOutPlanLog posWhsOutPlanLog) throws Exception {
	    return JSON.toJSONString(posWhsOutPlanService.deletePosWhsOutPlanLog(posWhsOutPlanLog));
	}
	
	@ApiOperation(value="查看仓库出库计划")
	@RequestMapping(value = "/checkPosWhsOutPlan", method = RequestMethod.POST)
    public String checkPosWhsOutPlan(@ModelAttribute PosWhsOutPlan posWhsOutPlan) throws Exception {
        return JSON.toJSONString(posWhsOutPlanService.executeSelect(posWhsOutPlan));
    }
	@ApiOperation(value="获取仓库列表")
	@RequestMapping(value = "/checkPosWhsList", method = RequestMethod.POST)
	public String checkPosWhsList() throws Exception {
	    return JSON.toJSONString(posWhsOutPlanService.checkPosWhsList());
	}
	
}
