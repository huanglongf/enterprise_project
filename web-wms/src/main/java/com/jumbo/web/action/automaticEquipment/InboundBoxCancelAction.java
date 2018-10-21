package com.jumbo.web.action.automaticEquipment;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;



/**
 * @author NCGZ-DZ- 货箱流向取消
 */
public class InboundBoxCancelAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -3487421319484901836L;

    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;

    private String code;


    public String initInboundBoxCancelPage() {

        return SUCCESS;
    }

    public String cancelAutoBox() {
        JSONObject result = new JSONObject();
        Long msgId = autoBaseInfoManager.cancelAutoBox(code);
        try {
            MsgToWcsThread wt = new MsgToWcsThread(WcsInterfaceType.SQuxiaoRongQi.getValue(), null, msgId, null);
            new Thread(wt).start();
            result.put("result", SUCCESS);
        } catch (Exception e) {
            // e.printStackTrace()
            if (log.isErrorEnabled()) {
                log.error("sShouRongQi error:" + msgId, e);
            };
            // log.error("sShouRongQi error:" + msgId);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



}
