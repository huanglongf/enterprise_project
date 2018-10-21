package com.jumbo.wms.manager.vmi.warehouse;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.dao.vmi.ids.IdsServerInformationDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.webservice.ids.service.ServiceType;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;
import com.jumbo.wms.model.vmi.ids.ReturnConfirm;
import com.jumbo.wms.model.vmi.ids.ReturnConfirm.Results;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseIDSNBAUA extends AbstractVmiWarehouse {

    private static final long serialVersionUID = -3545584292691732116L;
    protected static final Logger log = LoggerFactory.getLogger(VmiWarehouseIDSNBAUA.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    @Autowired
    private IdsServerInformationDao idsServerInformationDao;
    @Autowired
    private IdsManager idsManager;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;

    /**
     * 出库单取消，外包仓NBA
     */
    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        log.debug("LF CaneleOrderToLFTask1..." + staCode);
        boolean isCancle = false;
        try {
            log.debug("LF CaneleOrderToLFTask..." + Constants.VIM_WH_SOURCE_NBAUA_IDS);
            // 调用LF接口，获取取消结果
            ConfirmResult result = idsManagerProxy.siginOrderCancelResponseToLF(msg, Constants.VIM_WH_SOURCE_NBAUA_IDS);
            if (result != null) {
                if (result.getSuccess().equals("true")) {
                    isCancle = true;
                } else if (result.getSuccess().equals("false")) {
                    if (result.getReason().equals("B11")) {
                        isCancle = true;
                    } else {
                        isCancle = false;
                    }
                }
                // 取消成功
                if (isCancle) {
                    msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
                    msg.setIsCanceled(true);
                    msg.setMsg(result.getDescription());
                    msg.setUpdateTime(new Date());
                    msg.setMsg("WMS推送LF实时取消");
                } else {
                    throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
        }
        return isCancle;
    }

    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {


    }

    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {


    }


    /**
     * 退换货取消通知NBA外包仓
     */
    @Override
    public boolean cancelReturnRequest(Long msgLog) {

        boolean isSuccess = false;
        // 查找外包仓服务信息
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(Constants.VIM_WH_SOURCE_NBAUA_IDS);
        // 将退货信息生成指定格式
        String respXml = idsManager.cancelReturnResponseLF(idsServerInformation, msgLog);
        if (StringUtils.hasText(respXml)) {
            try {
                String resultXml = ServiceType.sendMsgtoIds(ServiceType.LOGISTIC_RETURN_CANCEL, respXml, idsServerInformation.getServerUrl(), idsServerInformation.getKey(), idsServerInformation.getSign());
                log.info("-- NBAUA ReturnCancel Text--" + resultXml);
                if (StringUtils.hasText(resultXml)) {
                    ReturnConfirm orderConfirm = (ReturnConfirm) MarshallerUtil.buildJaxb(ReturnConfirm.class, resultXml);
                    if (orderConfirm != null) {
                        List<Results> resultList = orderConfirm.getResults();
                        for (Results result : resultList) {
                            MsgRaCancel raCancel = msgRaCancelDao.getByPrimaryKey(msgLog);
                            if (raCancel != null) {
                                if (result.getResult().get(0).getSuccess().equals("true")) {
                                    raCancel.setVersion(1);
                                    raCancel.setUpdateTime(new Date());
                                    raCancel.setStatus(DefaultStatus.FINISHED);
                                    isSuccess = true;
                                } else if (result.getResult().get(0).getSuccess().equals("false")) {
                                     if ("B11".equals(result.getResult().get(0).getReason()) ||
                                     "B13".equals(result.getResult().get(0).getReason())) {
                                     raCancel.setVersion(1);
                                     raCancel.setUpdateTime(new Date());
                                     raCancel.setStatus(DefaultStatus.FINISHED);
                                     isSuccess = true;
                                     } else {
                                        throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
                                    }
                                }
                                msgRaCancelDao.save(raCancel);
                            } else {
                                msgRaCancelDao.updateStatusById(msgLog, DefaultStatus.INEXECUTION.getValue());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
            }
        }
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
        } else {
            return isSuccess;
        }
    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {

        return null;
    }

}
