package com.lmis.pos.poSplitCollect.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.poSplitCollect.service.PoSplitCollectServiceInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: PosSoldtoRuleController
 * @Description: TODO(Po拆分汇总分析控制层类)
 * @author codeGenerator
 * @date 2018-06-27 09:09:27
 * 
 */
@Api(value = "PO拆分汇总分析")
@RestController
@RequestMapping(value = "/pos")
public class PoSplitCollectController{

    @Value("${base.page.pageNum}")
    int defPageNum;

    @Value("${base.page.pageSize}")
    int defPageSize;

    @Resource(name = "poSplitCollectServiceImpl")
    private PoSplitCollectServiceInterface poSplitCollectServiceInterface;

    @ApiOperation(value = "查询po拆分汇总信息")
    @RequestMapping(value = "/selectPoSplitCollectData",method = RequestMethod.POST)
    public String selectPoSplitCollectData(@ModelAttribute PosPurchaseOrderMain posPurchaseOrderMain) throws Exception{
        return JSONObject.toJSONString(poSplitCollectServiceInterface.selectPoSplitCollectData(posPurchaseOrderMain),SerializerFeature.WriteMapNullValue);
    }

}
