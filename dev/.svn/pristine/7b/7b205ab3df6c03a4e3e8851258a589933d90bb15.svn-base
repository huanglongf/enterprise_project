package com.bt.workOrder.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.workOrder.bean.WoFollowupResultinfo;
import com.bt.workOrder.service.WoFollowupResultinfoService;
import com.bt.workOrder.service.WorkOrderManagementService;

@Controller
@RequestMapping("/control/woFollowupResultController")
public class WoFollowupResultController extends BaseController{
    
    @Resource(name="templetServiceImpl")
    private TempletService<T> templetService;

    @Resource(name = "woFollowupResultinfoServiceImpl")
    private WoFollowupResultinfoService woFollowupResultinfoService;

    @Resource(name= "workOrderManagementServiceImpl")
    private WorkOrderManagementService<T> workOrderManagementServiceImpl;
    /**
     * &tableName=wo_followup_resultinfo
     */
    @RequestMapping("/getFollowupResult")
    public String getFollowupResult(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
        // 参数构造
        parameter = generateParameter(parameter, req);
        String url = null;
        String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))?parameter.getParam().get("tableName").toString():"";
        // 配置表头 参数 tableName columnName
        req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
        // 表格功能配置
        req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
        req.setAttribute("wo_type", workOrderManagementServiceImpl.getWOType());
        
        String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))?parameter.getParam().get("pageName").toString():"";
        // 分页控件
        PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
        parameter.setFirstResult(pageView.getFirstResult());
        parameter.setMaxResult(pageView.getMaxresult());
        pageView.setQueryResult(woFollowupResultinfoService.query(parameter), parameter.getPage()); 
        if("table".equals(pageName) ){
            url = "/templet/table";
        }else{
            url = "/work_order/wo_management/followup_resultinfo";
        }
        req.setAttribute("pageView", pageView);
        // 返回页面
        return  url;
    }
    
    @ResponseBody
    @RequestMapping("/addFR")
    public String addFR(Parameter parameter, HttpServletRequest req) {
        parameter = generateParameter(parameter, req);
        Employee user = parameter.getCurrentAccount();
        WoFollowupResultinfo record=new WoFollowupResultinfo();
        record.setCreateBy(user.getName());
        record.setUpdateBy(user.getName());
        record.setExceptionType("");
        record.setWk_type(parameter.getParam().get("wkType").toString());
        record.setResultinfo(parameter.getParam().get("resultinfo").toString());
        JSONObject res = new JSONObject();
        if(1==woFollowupResultinfoService.insert(record)){
            res.put("msg", "添加成功！");
        }else{
            res.put("msg", "失败！");
        }
        return res.toJSONString();
    }
    
    @ResponseBody
    @RequestMapping("/delFR")
    public String delFR(String ids, HttpServletRequest req) {
        String [] idss = ids.split(",");
        int j=0;
        for(String id:idss){
            if(CommonUtils.checkExistOrNot(id)){
                if(1==woFollowupResultinfoService.deleteByPrimaryKey(Integer.valueOf(id))){
                  j++;  
                }
            }
        }
        JSONObject res = new JSONObject();
        res.put("msg", j+"条删除成功");
        return res.toJSONString();
    }
    
    
    
}
