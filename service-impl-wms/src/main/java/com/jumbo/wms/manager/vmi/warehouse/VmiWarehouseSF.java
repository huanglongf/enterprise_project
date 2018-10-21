package com.jumbo.wms.manager.vmi.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.webservice.sf.SfOrderClientInter;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderRequest;
import com.jumbo.webservice.sfwarehouse.command.WmsCancelSailOrderResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public class VmiWarehouseSF extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 4051039184502883134L;
    protected static final Logger log = LoggerFactory.getLogger(VmiWarehouseSF.class);
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private SfOrderClientInter sfOrderClient;

    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }

    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        msgOutboundOrderDao.flush();
        MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        // 如果未给外包仓直接标记为取消,取消单据
        if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
            msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
            return true;
        }
        WmsCancelSailOrderRequest request = new WmsCancelSailOrderRequest();
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("sfUp", "sfUp");
        if (op != null && op.getOptionValue() == null) {// 老逻辑
            request.setCheckword(SF_CHECKWORD);
        } else {// 新
            request.setCheckword(op.getOptionValue());
        }
        request.setCompany(SF_COMPANY);
        request.setOrderid(staCode);
        try {
            // WmsCancelSailOrderResponse response =
            // SfWarehouseWebserviceClient.wmsCanclSailOrder(request);
            WmsCancelSailOrderResponse response = sfOrderClient.wmsCanclSailOrder(request);
            // 当调用接口反馈SF已作废的情况，单子执行取消操作
            if (response.getResult().equals("1") || (response.getResult().equals("2") && response.getRemark().contains("订单已作废不允许取消"))) {
                msg.setMsg(response.getRemark());
                log.debug("cancelOrderNoticeSf---->Success!" + response.getOrderid() + ":" + response.getRemark());
                return true;
            } else {
                log.error("cancelOrderNoticeSf---->Failed!" + response.getOrderid() + ":" + response.getRemark());
                msg.setMsg(response.getRemark());
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
            }
        } catch (BusinessException e) {
            log.debug(e.getMessage());
            log.error("", e);
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("cancelSalesOutboundSta Exception:" + staCode, e);
            }
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR);
        }
    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

}
