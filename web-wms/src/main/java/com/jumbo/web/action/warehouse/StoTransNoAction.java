package com.jumbo.web.action.warehouse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.task.stoorder.StoOrderTaskManager;
import com.jumbo.web.action.BaseJQGridAction;

public class StoTransNoAction extends BaseJQGridAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4245164397243182078L;
	@Autowired
    private StoOrderTaskManager stoOrderTaskManager;
	private String key;
	public void olTransNo(){
		if(key == null){
			String page = "未传递参数或参数传递错误";
			returnMsg(page);
			return;
		}
		if("STOTN!O001".equals(key)){
			Long transNo = stoOrderTaskManager.getStoTranNoNum();
			String page = "可用单号数："+transNo;
			returnMsg(page);
		}else{
			String page = "未传递参数或参数传递错误";
			returnMsg(page);
			return;
		}
    }
    protected void returnMsg(String s){
        try {
            response.setCharacterEncoding("GBK");
            response.getWriter().print(s);
        } catch (IOException e) {
            log.error("",e);
        }
    }
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

    
}
