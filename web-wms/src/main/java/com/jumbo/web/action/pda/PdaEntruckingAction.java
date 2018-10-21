package com.jumbo.web.action.pda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.pda.base.PdaBaseAction;
import com.jumbo.web.action.pda.receive.PdaReceiveAction;
import com.jumbo.wms.manager.pda.PdaReceiveManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;

import loxia.support.json.JSONObject;
public class PdaEntruckingAction extends PdaBaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6765037722051905063L;

    protected static final Logger logger = LoggerFactory.getLogger(PdaReceiveAction.class);
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private PdaReceiveManager pdaReceiveManager;
    private String licensePlateNumber;
    private String trackingNo;
    public String menu() {
        return SUCCESS;
    }


    public String findLicensePlateNumber() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findLicensePlateNumber()));
        return JSON;
    }


    public String trucking() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.trucking(licensePlateNumber, trackingNo);
        try {
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("trucking" + e.toString());

        }
        request.put(JSON, result);
        return JSON;
    }

    public String truckingConfirm() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.truckingConfirm(licensePlateNumber, trackingNo);
        try {
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("trucking" + e.toString());

        }
        request.put(JSON, result);
        return JSON;
    }


    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }


    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }


    public String getTrackingNo() {
        return trackingNo;
    }


    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }


}
