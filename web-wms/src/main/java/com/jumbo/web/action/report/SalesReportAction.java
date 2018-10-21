package com.jumbo.web.action.report;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.report.ReportManager;

public class SalesReportAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 5194154372299861060L;
    @Autowired
    public ReportManager reportManager;

    public String entry() {
        return SUCCESS;
    }

    public String queryByReport() {
        setTableConfig();
        request.put(JSON, toJson(reportManager.queryByouId(userDetails.getCurrentOu())));
        return JSON;
    }
}
