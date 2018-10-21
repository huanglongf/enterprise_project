package com.jumbo.web.action.externalInterface;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.web.action.BaseJQGridAction;
import com.jumbo.webservice.sfwarehouse.command.CommonResponse;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.task.TaskSfManager;

/**
 * 顺风接口
 * 
 * @author jinlong.ke
 * 
 */
public class SfWarehouseAction extends BaseJQGridAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7606765911532532328L;
    @Autowired
    private TaskSfManager taskSfManger;
    private String logistics_interface;
    private String data_digest;
    private String requestXml;

    public void wmsSendVender() {
        try {
            WmsVendorResponse response = taskSfManger.sendVendorToSf();
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (BusinessException e) {
            CommonResponse response = new CommonResponse();
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("wmsSendVender BusinessException:", e);
            };
            // log.error("",e);
            String msg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (Exception e) {
            CommonResponse response = new CommonResponse();
            // e.printStackTrace();
            // log.error("",e);
            if (log.isErrorEnabled()) {
                log.error("wmsSendVender Exception:", e);
            };
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR);
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        }
    }

    /**
     * 入库明细推送 保存
     * 
     * @return
     */
    public void wmsPurchaseOrderPushInfo() {
        CommonResponse response = new CommonResponse();
        try {
            validateFirst(logistics_interface, data_digest);
            taskSfManger.wmsPurchaseOrderPushInfo(requestXml);
            response.setSuccess("true");
            response.setReason("推送成功!");
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (BusinessException e) {
            // e.printStackTrace();
            // log.error("",e);
            if (log.isErrorEnabled()) {
                log.error("wmsPurchaseOrderPushInfo BusinessException:", e);
            };
            String msg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("",e);
            if (log.isErrorEnabled()) {
                log.error("wmsPurchaseOrderPushInfo Exception:", e);
            };
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR);
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        }
    }

    private void validateFirst(String logisticsInterface, String dataDigest) throws Exception {
        try {
            String request = logisticsInterface;
            log.error(request);
            log.error("原始的.........");
            String data = dataDigest;
            log.error(data);
            String sfCheckWord = taskSfManger.getSfCheckWork();
            String rightReqeust = new Base64().encodeToString(AppSecretUtil.getMD5(request + sfCheckWord));
            log.error(rightReqeust);
            if (!data.equals(rightReqeust)) {
                throw new BusinessException(ErrorCode.SF_CHECk_NOT_PASS);
            } else {
                requestXml = request;
            }
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 出库明细推送 保存
     * 
     * @return
     */
    public void wmsSailOrderPushInfo() {
        CommonResponse response = new CommonResponse();
        try {
            validateFirst(logistics_interface, data_digest);
            taskSfManger.wmsSailOrderPushInfo(requestXml);
            response.setSuccess("true");
            response.setReason("推送成功!");
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (BusinessException e) {
            // e.printStackTrace();
            // log.error("",e);
            if (log.isErrorEnabled()) {
                log.error("wmsSailOrderPushInfo BusinessException:", e);
            };
            String msg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("",e);
            if (log.isErrorEnabled()) {
                log.error("wmsSailOrderPushInfo Exception:", e);
            };
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR);
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        }
    }

    /**
     * 库存调整推送接口 数据保存
     * 
     * @return
     */
    public void wmsInventoryAdjustPushInfo() {
        CommonResponse response = new CommonResponse();
        try {
            validateFirst(logistics_interface, data_digest);
            taskSfManger.wmsInventoryAdjustPushInfo(requestXml);
            response.setSuccess("true");
            response.setReason("推送成功!");
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (BusinessException e) {
            String msg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));
        } catch (Exception e) {
            String msg = getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR);
            response.setSuccess("false");
            response.setReason(msg);
            log.error(MarshallerUtil.buildJaxb(response));
            returnMsg(MarshallerUtil.buildJaxb(response));

        }
    }

    protected void returnMsg(String s) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(s);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public String getLogistics_interface() {
        return logistics_interface;
    }

    public void setLogistics_interface(String logisticsInterface) {
        logistics_interface = logisticsInterface;
    }

    public String getData_digest() {
        return data_digest;
    }

    public void setData_digest(String dataDigest) {
        data_digest = dataDigest;
    }

    public String getRequestXml() {
        return requestXml;
    }

    public void setRequestXml(String requestXml) {
        this.requestXml = requestXml;
    }


}
