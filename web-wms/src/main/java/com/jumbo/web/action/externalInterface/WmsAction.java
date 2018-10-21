package com.jumbo.web.action.externalInterface;

import java.util.List;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.StaCancelManager;

public class WmsAction extends BaseInterfaceAction {
    private static final long serialVersionUID = 9036112424674530159L;
    @Autowired
    private StaCancelManager staCancelManager;


    private List<String> codes;
    private String code;
    private Long ouid;

    public static final String WAIT = "wait";

    /*
     * public String createStaBySo() throws JSONException { JSONObject json = new JSONObject(); if
     * (!validateSecretkey()) { log.debug("WmsAction no role to exe"); json.put(RESULT, ERROR);
     * json.put(MESSAGE, getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.NO_ROLE_TO_ACCESS)); }
     * else { try { List<ActionMsgCommand> msgs = new ArrayList<ActionMsgCommand>(); Map<String,
     * String> map = wareHouseManagerProxy.soToWarehouse(codes, ouid); for (Entry<String, String>
     * entry : map.entrySet()) { ActionMsgCommand msg = new ActionMsgCommand();
     * msg.setCode(entry.getKey()); if (WareHouseManagerProxy.CODE_SUCCESS.equals(entry.getValue()))
     * { msg.setResult(WareHouseManagerProxy.CODE_SUCCESS); } else {
     * msg.setResult(WareHouseManagerProxy.CODE_ERROR); } msg.setMessage(entry.getValue());
     * msgs.add(msg); } json.put(RESULT, SUCCESS); json.put(MESSAGE,
     * JsonUtil.collection2json(msgs)); } catch (Exception e) { if (e instanceof BusinessException)
     * { BusinessException le = (BusinessException) e; json.put(RESULT, ERROR); json.put(MESSAGE,
     * getMessage(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode(), le.getArgs())); } else {
     * json.put(RESULT, ERROR); json.put(MESSAGE, e.getMessage()); log.error("", e); } } }
     * request.put(JSON, json); return JSON; }
     */
    /**
     * 取消销售出库
     * 
     * @return
     * @throws JSONException
     */
    public String cancelSalesStaBySo() throws JSONException {
        JSONObject json = new JSONObject();
        // System.out.println(codes);
        if (!validateSecretkey()) {
            log.debug("WmsAction no role to exe");
            json.put(RESULT, ERROR);
            json.put(MESSAGE, getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.NO_ROLE_TO_ACCESS));
        } else {
            try {
                log.error("so code is :[{}]", code);
                boolean result = staCancelManager.cancelSalesStaBySlipCode(code);
                if (result) {
                    json.put(RESULT, SUCCESS);
                    json.put(MESSAGE, SUCCESS);
                } else {
                    json.put(RESULT, WAIT);
                    json.put(MESSAGE, WAIT);
                }
            } catch (BusinessException e) {
                json.put(RESULT, ERROR);
                json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                log.error("cancel sta error : error code is : [{}]", e.getErrorCode());
                BusinessException le = e.getLinkedException();
                if (le != null) {
                    log.error("cancel sta error : error code is : [{}]", le.getErrorCode());
                    json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode(), le.getArgs()));
                }
            }
        }
        log.error("rtn:cancelSalesStaBySo:" + code + ";return:" + json.get(RESULT));
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 取消退换货入库
     * 
     * @return
     * @throws JSONException
     */
    public String cancelRtnInboundByRa() throws JSONException {
        JSONObject json = new JSONObject();
        // System.out.println(codes);
        if (!validateSecretkey()) {
            log.debug("WmsAction no role to exe");
            json.put(RESULT, ERROR);
            json.put(MESSAGE, getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.NO_ROLE_TO_ACCESS));
        } else {
            try {
                boolean result = staCancelManager.cancelSalesStaBySlipCode(code);
                if (result) {
                    json.put(RESULT, SUCCESS);
                    json.put(MESSAGE, SUCCESS);
                } else {
                    json.put(RESULT, WAIT);
                    json.put(MESSAGE, WAIT);
                }
            } catch (BusinessException e) {
                json.put(RESULT, ERROR);
                json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                BusinessException le = e.getLinkedException();
                if (le != null) {
                    json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode(), le.getArgs()));
                }
            }
        }
        request.put(JSON, json);
        return JSON;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Long getOuid() {
        return ouid;
    }

    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
