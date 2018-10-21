package com.bt.workOrder.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.common.controller.model.TableFunctionConfig;
import com.bt.common.controller.param.Parameter;
import com.bt.common.service.TempletService;
import com.bt.common.util.ExcelExportUtil;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.StoreService;
import com.bt.radar.model.Routecode;
import com.bt.radar.service.RoutecodeService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.workOrder.bean.WorkBaseInfoBean;
import com.bt.workOrder.common.Constant;
import com.bt.workOrder.service.WkTypeService;
import com.bt.workOrder.service.WorkBaseInfoService;

@Controller
@RequestMapping("/control/exportWKController2")
public class ExportWKController2 extends BaseController{
    
    @Resource(name="templetServiceImpl")
    private TempletService<T> templetService;

    @Resource(name="workBaseInfoServiceImpl")
    private WorkBaseInfoService<WorkBaseInfoBean> workBaseInfoServiceImpl;
    
    @Resource(name="employeeServiceImpl")
    private EmployeeService<Employee> employeeServiceImpl;

    @Resource(name="storeServiceImpl")
    private StoreService<Store> storeServiceImpl;
    
    @Resource(name = "routecodeServiceImpl")
    private RoutecodeService<Routecode> routecodeServiceImpl;

    @Resource(name = "wkTypeServiceImpl")
    private WkTypeService<T> wkTypeServiceImpl;

    /**
     * &tableName=wo_caimant_wk
     */
    @RequestMapping("/exportCaimantWK")
    public String exportCaimantWK(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
        // 参数构造
        parameter = generateParameter(parameter, req);
        String url = null;
        String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))?parameter.getParam().get("tableName").toString():"";
        // 配置表头 参数 tableName columnName
        req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
        // 表格功能配置
        req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
        String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))?parameter.getParam().get("pageName").toString():"";
        // 分页控件
        PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
        parameter.setFirstResult(pageView.getFirstResult());
        parameter.setMaxResult(pageView.getMaxresult());
        if("table".equals(pageName) ){
            url = "/templet/table2";
            pageView.setQueryResult(wkTypeServiceImpl.queryCaimantWK(parameter), parameter.getPage()); 
        }else{
            url = "/work_order/caimant_wk";
            QueryResult<Map<String, Object>> qr = new QueryResult<>();
            pageView.setQueryResult(qr, parameter.getPage()); 
            
            List<Employee> work_emp_list = employeeServiceImpl.findByPro("workorder");
            List<Map<String, Object>> store_list = storeServiceImpl.findAll();
            List<Routecode> carrier_list = routecodeServiceImpl.selectTransport_vender();
            List<Map<String, Object>> wktype_list = wkTypeServiceImpl.findByWKTYPE();
            List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < wktype_list.size(); i++) {
                Map<String, Object> maps = new HashMap<String, Object>();
                List<Map<String, Object>> wkerror_list = wkTypeServiceImpl.findByWKERROR(wktype_list.get(i).get("code").toString());
                maps.put("type_code", wktype_list.get(i).get("code").toString());
                maps.put("wkerror_list",wkerror_list);
                lists.add(maps);
            }
            req.setAttribute("lists", lists);
            req.setAttribute("wktype_list", wktype_list);
            req.setAttribute("carrier_list", carrier_list);
            req.setAttribute("work_emp_list", work_emp_list);
            req.setAttribute("store_list", store_list);
        }
        
        req.setAttribute("pageView", pageView);
        // 返回页面
        return  url;
    }
    
    /**
     * &tableName=wo_check_wk
     */
    @RequestMapping("/exportCheckWK")
    public String exportCheckWK(Parameter parameter, HttpServletRequest req,HttpServletResponse res) {
        // 参数构造
        parameter = generateParameter(parameter, req);
        String url = null;
        String tableName = CommonUtils.checkExistOrNot(parameter.getParam().get("tableName"))?parameter.getParam().get("tableName").toString():"";
        // 配置表头 参数 tableName columnName
        req.setAttribute("tableColumnConfig", templetService.loadingTableColumnConfig(parameter));
        // 表格功能配置
        req.setAttribute("tableFunctionConfig", JSONObject.toJSONString(new TableFunctionConfig(tableName, true, null)));
        String pageName = CommonUtils.checkExistOrNot(parameter.getParam().get("pageName"))?parameter.getParam().get("pageName").toString():"";
        // 分页控件
        PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(parameter.getPageSize() == 0 ? BaseConst.pageSize : parameter.getPageSize(), parameter.getPage());
        parameter.setFirstResult(pageView.getFirstResult());
        parameter.setMaxResult(pageView.getMaxresult());
        if("table".equals(pageName) ){
            url = "/templet/table2";
            pageView.setQueryResult(wkTypeServiceImpl.queryCheckWK(parameter), parameter.getPage()); 
        }else{
            url = "/work_order/check_wk";
            QueryResult<Map<String, Object>> qr = new QueryResult<>();
            pageView.setQueryResult(qr, parameter.getPage()); 
            
            List<Employee> work_emp_list = employeeServiceImpl.findByPro("workorder");
            List<Map<String, Object>> store_list = storeServiceImpl.findAll();
            List<Routecode> carrier_list = routecodeServiceImpl.selectTransport_vender();
            List<Map<String, Object>> wktype_list = wkTypeServiceImpl.findByWKTYPE();
            List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < wktype_list.size(); i++) {
                Map<String, Object> maps = new HashMap<String, Object>();
                List<Map<String, Object>> wkerror_list = wkTypeServiceImpl.findByWKERROR(wktype_list.get(i).get("code").toString());
                maps.put("type_code", wktype_list.get(i).get("code").toString());
                maps.put("wkerror_list",wkerror_list);
                lists.add(maps);
            }
            req.setAttribute("lists", lists);
            req.setAttribute("wktype_list", wktype_list);
            req.setAttribute("carrier_list", carrier_list);
            req.setAttribute("work_emp_list", work_emp_list);
            req.setAttribute("store_list", store_list);
        }
        
        req.setAttribute("pageView", pageView);
        // 返回页面
        return  url;
    }
    
    @RequestMapping("exportCaimantWKExcel")
    public void exportCaimantWKExcel(HttpServletRequest request, HttpServletResponse response) {
        Parameter parameter = generateParameter(request);
        List<Map<String, Object>> titleList = templetService.loadingTableColumnConfig(parameter);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        for(int i = 0; i < titleList.size(); i++) {
            title.put(titleList.get(i).get("column_code").toString(), titleList.get(i).get("column_name").toString());
        }
        try {
            ExcelExportUtil.exportExcelByStream("索赔工单导出_" + parameter.getCurrentAccount().getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, Constant.EXPORT_NAME, title, wkTypeServiceImpl.exportCaimantWKExcel(parameter), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("exportCheckWKExcel")
    public void exportCheckWKExcel(HttpServletRequest request, HttpServletResponse response) {
        Parameter parameter = generateParameter(request);
        List<Map<String, Object>> titleList = templetService.loadingTableColumnConfig(parameter);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        for(int i = 0; i < titleList.size(); i++) {
            title.put(titleList.get(i).get("column_code").toString(), titleList.get(i).get("column_name").toString());
        }
        try {
            ExcelExportUtil.exportExcelByStream("查件工单导出_" + parameter.getCurrentAccount().getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, Constant.EXPORT_NAME, title, wkTypeServiceImpl.exportCheckWKExcel(parameter), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
