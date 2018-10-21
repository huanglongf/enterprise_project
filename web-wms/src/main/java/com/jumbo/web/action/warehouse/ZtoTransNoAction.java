package com.jumbo.web.action.warehouse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.task.stoorder.StoOrderTaskManager;
import com.jumbo.web.action.BaseJQGridAction;

public class ZtoTransNoAction extends BaseJQGridAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4245164397243182078L;
	@Autowired
    private StoOrderTaskManager stoOrderTaskManager;
	public void olTransNo(){
		Long transNo = stoOrderTaskManager.getTranNoByLpcodeList();
		String page = "可用单号数："+transNo;
		returnMsg(page);
    }
    protected void returnMsg(String s){
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(s);
        } catch (IOException e) {
            log.error("",e);
        }
    }
}
