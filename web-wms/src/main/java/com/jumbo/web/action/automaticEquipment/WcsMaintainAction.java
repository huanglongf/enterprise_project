package com.jumbo.web.action.automaticEquipment;

import java.util.HashMap;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.automaticEquipment.MsgToWcsManager;
import com.jumbo.wms.model.command.automaticEquipment.MsgToWcsCommand;


/**
 * 接口/类说明： WCS 数据维护
 * @ClassName: WcsMaintainAction 
 * @author LuYingMing
 * @date 2016年7月19日 下午3:12:23
 */
public class WcsMaintainAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    
    @Autowired
    private MsgToWcsManager msgToWcsManager;
    
    private String idStr;
    /**
     * WCS通讯实体
     */
    private MsgToWcsCommand msgToWcsCommand;
    
    

    public String initWcsMaintainPage() {
        return SUCCESS;
    }
    
    /**
     * 方法说明：初始化查询
     * @author LuYingMing
     * @date 2016年7月19日 下午5:43:58 
     * @return
     */
    public String findMsgToWcsByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (msgToWcsCommand != null) {
            if (!"".equals(msgToWcsCommand.getPickingListCode())) {
                params.put("pickingListCode", msgToWcsCommand.getPickingListCode());
            }
            if (!"".equals(msgToWcsCommand.getContainerCode())) {
                params.put("containerCode", msgToWcsCommand.getContainerCode());
            }
        }
        Pagination<MsgToWcsCommand> wcsDataList = msgToWcsManager.findMsgToWcsByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(wcsDataList));
        return JSON;
    }

    /**
     * 方法说明：一键重置
     * @author LuYingMing
     * @date 2016年7月21日 上午9:58:58 
     * @return
     * @throws JSONException
     */
    public String resetAll() throws JSONException {
    	JSONObject result = new JSONObject();
        	try {
				msgToWcsManager.resetErrorCount();
				result.put("msg", SUCCESS);
			} catch (Exception e) {
				log.error("", e);
				result.put("msg", ERROR);
			}
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 方法说明：删除并保存日志
     * @author LuYingMing
     * @date 2016年7月21日 上午11:11:48 
     * @return
     * @throws JSONException
     */
    public String deleteDataById() throws JSONException {
    	JSONObject result = new JSONObject();
        if (null != idStr && !"".equals(idStr)) {
        	try {
        		Long msgId = Long.parseLong(idStr);
				msgToWcsManager.deleteMsgToWcsById(msgId);
				result.put("msg", SUCCESS);
			} catch (Exception e) {
				log.error("", e);
				result.put("msg", ERROR);
			}
        }else {
        	result.put("msg", NONE);
		}
        request.put(JSON, result);
        return JSON;
    }


    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

	public MsgToWcsCommand getMsgToWcsCommand() {
		return msgToWcsCommand;
	}

	public void setMsgToWcsCommand(MsgToWcsCommand msgToWcsCommand) {
		this.msgToWcsCommand = msgToWcsCommand;
	}
}
