package com.jumbo.web.action.rfid;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.rfid.RfidManager;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RfidAction extends BaseJQGridProfileAction {

    /**
     * rfid
     */
    private static final long serialVersionUID = -5764884066005435637L;

    @Autowired
    private RfidManager rfidManager;
    private String barCode;
    private String rf1;
    private String rf2;
    public String toRfidPage() {
     
        return SUCCESS;
    }

    public String saveRfid(){
        List<String> rflist = new ArrayList<>();
        rflist.add(rf1);
        rflist.add(rf2);
        Map<String, Object> result = rfidManager.saveRfidAndLog(barCode, rflist);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result",result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.put(JSON,jsonObject);
        return JSON;
    }
    public String toPrintRfid() {

        return SUCCESS;
    }
    public String createRfid(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result",rfidManager.createRfid(barCode));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.put(JSON,jsonObject);
        return JSON;

    }
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getRf1() {
        return rf1;
    }

    public void setRf1(String rf1) {
        this.rf1 = rf1;
    }

    public String getRf2() {
        return rf2;
    }

    public void setRf2(String rf2) {
        this.rf2 = rf2;
    }
}
