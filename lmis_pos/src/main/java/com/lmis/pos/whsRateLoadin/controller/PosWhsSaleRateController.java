package com.lmis.pos.whsRateLoadin.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
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
@Api(value = "区域销售占比维护")
@RestController
@RequestMapping(value="/whsSaleRate")
public class PosWhsSaleRateController extends BaseController{

    @Resource(name="posWhsServiceImpl")
    PosWhsServiceInterface<PosWhs> posWhsService;
	
	@ApiOperation(value="查询区域仓库销售占比")
	@RequestMapping(value = "/queryPosWhsSaleRate", method = RequestMethod.POST)
    public String selectPosWhs() throws Exception {
        return JSON.toJSONString(posWhsService.queryWhsToSetSaleRate());
    }

    @ApiOperation(value="更改区域仓库销售占比")
	@RequestMapping(value = "/updatePosWhsSaleRate", method = RequestMethod.POST)
    public String addPosWhs(String data) throws Exception {
	    if(data==null) throw new Exception("参数异常[data不能为空]");
	    
	    List<PosWhs>params = com.alibaba.fastjson.JSONArray.parseArray(data,PosWhs.class);
	    BigDecimal total = BigDecimal.ZERO;
	    for (PosWhs posWhs : params){
	        
            if(posWhs.getId()==null) throw new Exception("参数异常[传入仓库ID不能为空]");
            
            total = total.add(posWhs.getSaleRate());
        }
	    
	    if(total.compareTo(new BigDecimal(1))!=0) throw new Exception("参数异常[区域仓库销售占比之和应为100%]");
	    
        return JSON.toJSONString(posWhsService.updateWhsSaleRate(params));
    }
	
}
