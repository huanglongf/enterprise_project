package com.jumbo.web.action.system;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.system.SchedulerTaskManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.system.SysSchedulerTask;


/**
 * 
 * @author jinggang.chen
 *
 */
public class SchedulerTaskAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1294639938197482857L;

    @Autowired
    private SchedulerTaskManager schedulerTaskManager;

    private SysSchedulerTask ssTask;

    public String code;

    private Long id;



    public String showTimedTaskManager() {
        return SUCCESS;
    }

    public String getSchedulerTaskList() {
        setTableConfig();
        Pagination<SysSchedulerTask> SysSchedulerTaskList = schedulerTaskManager.findSysSchedulerTaskPage(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), ssTask);
        request.put(JSON, toJson(SysSchedulerTaskList));
        return JSON;
    }


    public String findSkubySkuCode() {
        setTableConfig();
        Pagination<SkuCommand> SysSchedulerTaskList = schedulerTaskManager.findSkubySkuCode(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), code);
        request.put(JSON, toJson(SysSchedulerTaskList));
        return JSON;
    }

    public String deleteSkuType() {
        JSONObject result = new JSONObject();
        boolean msg = schedulerTaskManager.deleteSkuType(id);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("deleteSkuType Exception", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateSkuCode() {
        JSONObject result = new JSONObject();
        String msg = schedulerTaskManager.updateSkuCode(code);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updateSkuCode Exception" + code, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateSchedulerTask() throws JSONException {
        JSONObject result = new JSONObject();
        SysSchedulerTask schedulerTask = null;
        // 验证修改后的是否启用是否合法
        Integer integer=ssTask.getNode();
        if (integer==null) {
        	result.put("result", "error");
            request.put(JSON, result);
		}else{
			if ((integer==0)||(integer==1)) {
				// 验证修改后的时间配置是否合法
		        boolean b = org.quartz.CronExpression.isValidExpression(ssTask.getTimeExp());
		        if (b) {
		            SysSchedulerTask sysSchedulerTask = schedulerTaskManager.findSchedulerTaskById(ssTask.getId());
		            sysSchedulerTask.setTimeExp(ssTask.getTimeExp());
		            sysSchedulerTask.setNode(ssTask.getNode());
		            schedulerTask = schedulerTaskManager.saveSchedulerTask(sysSchedulerTask);
		        } else {
		            result.put("result", "error");
		            request.put(JSON, result);
		        }
		        if (schedulerTask != null) {
		            // Object object = zkClient.readData(znode, stat);
		            result.put("result", "success");
		            request.put(JSON, result);
		        }
				
			}else {
				result.put("result", "error");
	            request.put(JSON, result);
			}
		}
        
        return JSON;
    }

    public SysSchedulerTask getSsTask() {
        return ssTask;
    }

    public void setSsTask(SysSchedulerTask ssTask) {
        this.ssTask = ssTask;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
