package com.jumbo.webservice.outsourcingWH.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.BindingType;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.webservice.outsourcingWH.BhSyncRequest;
import com.jumbo.webservice.outsourcingWH.BhSyncResponse;
import com.jumbo.webservice.outsourcingWH.command.OrderListConfirm;
import com.jumbo.webservice.outsourcingWH.command.SkuListConfirm;
import com.jumbo.webservice.outsourcingWH.manager.OutsourcingWHManager;

@Path("/")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@WebService(serviceName = "OutsourcingWHService", targetNamespace = "http://www.jumbomart.cn/webservice/")
public class OutsourcingWHService {

    protected static final Logger log = LoggerFactory.getLogger(OutsourcingWHService.class);

    @Autowired
    private OutsourcingWHManager outsourcingWHManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;

    @WebMethod
    @POST
    @Path(value = "/outsourcingWH")
    @Produces(value = MediaType.APPLICATION_XML)
    @Consumes(value = MediaType.APPLICATION_XML)
    public BhSyncResponse outsourcingWH(BhSyncRequest request) throws UnsupportedEncodingException {
        log.debug("==============request==============" + request);
        log.debug("==============PayLoad============== \r\n " + request.getPayload());

        String responseXml = null;// 反馈XML
        BhSyncResponse rs = new BhSyncResponse();
        String errorCode = null;
        String errorMsg = null;

        String secretKey = request.getSecretKey();
        String callDate = request.getCallDate();

        //
        InterfaceSecurityInfo s = wareHouseManagerExe.findUseringUserBySource(request.getSource(), new Date());
        boolean flag = false;
        if (s != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(s.getUsername());
            sb.append(s.getPassword());
            sb.append(callDate);
            flag = AppSecretUtil.isEqualsSecret(sb.toString(), secretKey);
        }
        if (flag) {
            String resultXml = null;
            if (request.getPayload() != null && !"".equals(request.getPayload())) {
                resultXml = new String(Base64.decodeBase64(request.getPayload().getBytes("UTF-8")), "UTF-8");// 请求XML
            }
            if (SyncConstants.OPCODE_PULL_SO.equals(request.getOpCode())) {
                // 获取销售单据
                responseXml = outsourcingWHManager.getPullSo(request.getSource());
            } else if (SyncConstants.OPCODE_CF_ORDER.equals(request.getOpCode())) {
                // 确认接收单据request
                log.error("============================================================================================DLY_ERROR:" + resultXml);
                OrderListConfirm confirm = null;
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
                log.error("============================================================================================DLY_ERROR:" + confirm);
                if (confirm != null) {
                    try {
                        outsourcingWHManager.serviceCfOrder(confirm);
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
                } else {
                    errorCode = (ErrorCode.OUTSOURCING_CF_ORDER_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_CF_ORDER_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else if (SyncConstants.OPCODE_OB_SO.equals(request.getOpCode())) {
                // 销售单据出库request
                List<MsgRtnOutbound> msgRtnOutboundList = null;
                try {
                    msgRtnOutboundList = outsourcingWHManager.findoutboundOrder(resultXml, request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (msgRtnOutboundList == null) {
                    errorCode = (ErrorCode.OUTSOURCING_OUT_ORDER_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_OUT_ORDER_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else if (SyncConstants.OPCODE_PULL_CO.equals(request.getOpCode())) {
                // 获取取消单据request
                responseXml = outsourcingWHManager.getCancelOrderBound(request.getSource());
            } else if (SyncConstants.OPCODE_CF_CO.equals(request.getOpCode())) {
                try {
                    outsourcingWHManager.vimExecuteCreateCancelOrder(resultXml, request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
            } else if (SyncConstants.OPCODE_PULL_RO.equals(request.getOpCode())) {
                // 获取退货单据Request
                try {
                    responseXml = outsourcingWHManager.findinboundReturnRequest(request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
            } else if (SyncConstants.OPCODE_IB_RO.equals(request.getOpCode())) {
                // 退货单据入库request
                List<MsgRtnInboundOrder> rtnlist = null;
                try {
                    rtnlist = outsourcingWHManager.transactionRtnInbound(resultXml, request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (rtnlist == null) {
                    errorCode = (ErrorCode.OUTSOURCING_RETURN_IN_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_RETURN_IN_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else if (SyncConstants.OPCODE_PULL_IN_BOUND.equals(request.getOpCode())) {
                // 获取入库单据
                responseXml = outsourcingWHManager.pullInBound(request.getSource());
            } else if (SyncConstants.OPCODE_IB_IN_BOUND.equals(request.getOpCode())) {
                // 入库单入库
                List<MsgRtnInboundOrder> rtnlist = null;
                try {
                    rtnlist = outsourcingWHManager.ibInbound(resultXml, request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (rtnlist == null) {
                    errorCode = (ErrorCode.OUTSOURCING_IN_BOUND_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_IN_BOUND_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else if (SyncConstants.OPCODE_PULL_OUT_BOUND.equals(request.getOpCode())) {
                // 获取出库单据
                responseXml = outsourcingWHManager.pullOutBound(request.getSource());
            } else if (SyncConstants.OPCODE_OB_OUT_BOUND.equals(request.getOpCode())) {
                // 出库单据出库
                List<MsgRtnOutbound> rtnlist = null;
                try {
                    rtnlist = outsourcingWHManager.findoutboundOrder(resultXml, request.getSource());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (rtnlist == null) {
                    errorCode = (ErrorCode.OUTSOURCING_BO_OUT_BOUND_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_BO_OUT_BOUND_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else if (SyncConstants.OPCODE_PULL_SKU.equals(request.getOpCode())) {
                // 获取商品信息
                responseXml = outsourcingWHManager.pullSku(request.getSource());
            } else if (SyncConstants.OPCODE_CF_SKU.equals(request.getOpCode())) {
                // 获取商品信息
                SkuListConfirm confirm = null;
                try {
                    confirm = (SkuListConfirm) MarshallerUtil.buildJaxb(OrderListConfirm.class, resultXml);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorCode = (be.getErrorCode() - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                    } else {
                        log.error("=========outSourcingWH ERROR===========");
                        log.error("", e);
                        errorCode = (ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.IDS_BASE_ERROR_CODE_SYSTEM_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                    }
                }
                if (confirm != null) {
                    outsourcingWHManager.serviceCfSku(confirm);
                } else {
                    errorCode = (ErrorCode.OUTSOURCING_CF_SKU_IS_NULL - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                    errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_CF_SKU_IS_NULL), null, Locale.SIMPLIFIED_CHINESE);
                }
            } else {
                errorCode = (ErrorCode.OUTSOURCING_OP_CODE_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
                errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_OP_CODE_ERROR), new Object[] {request.getOpCode()}, Locale.SIMPLIFIED_CHINESE);
            }
        } else {
            errorCode = (ErrorCode.OUTSOURCING_OP_CODE_ERROR - ErrorCode.IDS_BASE_ERROR_CODE) + "";
            errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.OUTSOURCING_OP_CODE_ERROR), null, Locale.SIMPLIFIED_CHINESE);
        }
        rs.setOpCode(request.getOpCode() + SyncConstants.RSP);
        if (errorCode != null) {
            log.debug("outSourcingWH error! error msg:" + errorMsg);
            rs.setErrorCode(errorCode);
            rs.setErrorMsg(errorMsg);
        }
        if (responseXml != null) {
            rs.setResult(new String(Base64.encodeBase64(responseXml.getBytes("UTF-8")), "UTF-8"));
        }
        return rs;
    }
}
