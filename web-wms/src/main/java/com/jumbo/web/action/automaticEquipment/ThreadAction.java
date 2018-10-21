package com.jumbo.web.action.automaticEquipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.thread.ThreadUtilManager;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.system.SysSchedulerTask;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;


/**
 * @author 
 *
 * @createDate 
 */
public class ThreadAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    @Autowired
    private ThreadUtilManager threadUtilManager;
	private ThreadConfig threadConfig;
    
    private String path;
    
	public void setPath(String path) {
		this.path = path;
	}
	public ThreadConfig getThreadConfig() {
		return threadConfig;
	}
	public void setThreadConfig(ThreadConfig threadConfig) {
		this.threadConfig = threadConfig;
	}
    public String getAllThreadPools() {
    	setTableConfig();
    	Pagination<ThreadConfig> threadList = threadUtilManager.getAllThreadConfigPools(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
    	//List<ThreadConfig> threadList=threadUtilManager.getAllThreadConfigPools();
		request.put(JSON, toJson(threadList));
        
        return JSON;
    }
    public String addUpdateThreadConfig() throws JSONException {
        JSONObject result = new JSONObject();
        if (threadConfig != null&&!"".equals(path)&&path!=null) {
            try {
                String flag = threadUtilManager.saveThreadConfig(threadConfig,path);
                if ("".equals(flag)||flag == null) {
                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }
        request.put(JSON, result);
        return JSON;
    }
}
