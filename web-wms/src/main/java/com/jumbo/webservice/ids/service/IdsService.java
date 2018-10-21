package com.jumbo.webservice.ids.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.jumbo.Constants;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.webservice.ids.BhSyncRequest;
import com.jumbo.webservice.ids.BhSyncResponse;
import com.jumbo.webservice.ids.command.OrderListConfirm;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.CommonLogRecordManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.CommonLogRecord;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;

@Path("/")
public class IdsService {

    protected static final Logger log = LoggerFactory.getLogger(IdsService.class);

    @Autowired
    private IdsManager idsManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerExe warehouseManagerExe;
    @Autowired
    private CommonLogRecordManager crManager;

    @WebMethod
    @POST
    @Path(value = "/ids")
    @Produces(value = MediaType.APPLICATION_XML)
    @Consumes(value = MediaType.APPLICATION_XML)
    public BhSyncResponse ids(BhSyncRequest request) throws UnsupportedEncodingException {
        log.debug("==============request==============" + request);
        log.debug("==============PayLoad============== \r\n " + request.getPayload());
        String resultXml = new String(Base64.decodeBase64(request.getPayload().getBytes("UTF-8")), "UTF-8");// 请求XML
        String responseXml = null;// 反馈XML
        BhSyncResponse rs = new BhSyncResponse();
        String errorCode = null;
        String errorMsg = null;

        String secretKey = request.getSecretKey();
        String callDate = request.getCallDate();
        String filename = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ds = sdf.format(new Date());
        InterfaceSecurityInfo s = warehouseManagerExe.findUseringUserBySource(Constants.VIM_WH_SOURCE_IDS, new Date());
        boolean flag = false;
        if (s != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(s.getUsername());
            sb.append(s.getPassword());
            sb.append(callDate);
            flag = AppSecretUtil.isEqualsSecret(sb.toString(), secretKey);
        }
        if (flag) {
            if (SyncConstants.OPCODE_IDS_PULL_SO.equals(request.getOpCode())) {

                log.error("=========IDS OPCODE_IDS_PULL_SO START===========");
                // 获取销售单据
                idsManager.updateFixNumberData();
                responseXml = idsManager.iDSPullSo();
                crManager.saveLog(ds, CommonLogRecord.LV_SO_RESONSE, responseXml);
                log.error("=========IDS OPCODE_IDS_PULL_SO END===========");
            } else if (SyncConstants.OPCODE_IDS_CF_ORDER.equals(request.getOpCode())) {
                // 确认接收单据request
                OrderListConfirm confirm = null;
                log.error("=========IDS OPCODE_IDS_CF_ORDER START===========");
                try {
                    confirm = (OrderListConfirm) MarshallerUtil.buildJaxb(OrderListConfirm.class, resultXml);

                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========IDS ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                crManager.saveLog(ds, CommonLogRecord.LV_SO_CONFIRM, resultXml);
                idsManager.iDSCfOrder(confirm, filename);
            } else if (SyncConstants.OPCODE_IDS_OB_SO.equals(request.getOpCode())) {
                // 销售单据出库request
                List<MsgRtnOutbound> msgRtnOutboundList = null;
                log.error("=========IDS OPCODE_IDS_OB_SO START===========");
                try {
                    msgRtnOutboundList = idsManager.findoutboundOrder(resultXml);

                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========IDS ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (msgRtnOutboundList != null) {
                    // idsManager.outOrderToSale(msgRtnOutboundList, filename);
                }
            } else if (SyncConstants.OPCODE_IDS_PULL_CO.equals(request.getOpCode())) {
                // 获取取消单据request
                responseXml = idsManager.vimIdsnoticeCancelOrderBound();
            } else if (SyncConstants.OPCODE_IDS_CF_CO.equals(request.getOpCode())) {
                // 确认取消单据request
                log.error("=========IDS OPCODE_IDS_CF_CO START===========");
                try {
                    idsManager.vimExecuteCreateCancelOrder(resultXml);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========IDS ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
            } else if (SyncConstants.OPCODE_IDS_PULL_RO.equals(request.getOpCode())) {
                // 获取退货单据Request
                log.error("=========IDS OPCODE_IDS_PULL_RO START===========");
                try {
                    responseXml = idsManager.findinboundReturnRequest();
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========IDS ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
            } else if (SyncConstants.OPCODE_IDS_IB_RO.equals(request.getOpCode())) {
                // 退货单据入库request
                List<MsgRtnInboundOrder> rtnlist = null;

                log.error("=========IDS OPCODE_IDS_IB_RO START===========");
                try {
                    rtnlist = idsManager.transactionRtnInbound(resultXml);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========IDS ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (rtnlist != null) {
                    wareHouseManagerProxy.inboundToBz(rtnlist);
                }
            }
        } else {
            errorCode = (ErrorCode.IDS_STA_NOT_PURVIEW - ErrorCode.IDS_BASE_ERROR_CODE) + "";
            errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_STA_NOT_PURVIEW), null, Locale.SIMPLIFIED_CHINESE);
        }
        rs.setOpCode(request.getOpCode() + SyncConstants.RSP);
        if (errorCode != null) {
            rs.setErrorCode(errorCode);
            rs.setErrorMsg(errorMsg);
        }
        if (responseXml != null) {

            rs.setResult(new String(Base64.encodeBase64(responseXml.getBytes("UTF-8")), "UTF-8"));

        }
        return rs;
    }
}
