package com.jumbo.wms.manager.vmi.warehouse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.webservice.etam.client.ScaleApp;
import com.jumbo.webservice.etam.client.ScaleAppSoap;
import com.jumbo.webservice.etam.client.SecurityHeaderImpl;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseItochuUA extends AbstractVmiWarehouse {
    /**
     * 
     */
    private static final long serialVersionUID = -2041859691596331698L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;

    private static final Logger log = LoggerFactory.getLogger(VmiWarehouseItochu.class);

    private static final QName SERVICE_NAME = new QName("http://www.bpl.com.cn/", "ScaleApp");

    @Transactional
    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    @Transactional
    public void inboundReturnRequestAnsToWms(StockTransApplication sta, Warehouse wh) {
        /*
         * staDao.updateStaStatusById(StockTransApplicationStatus.CREATED.getValue(),
         * sta.getRefSlipCode()); MsgInboundOrder msgInboundOrder = whManager.msgInorder(sta, wh);
         * itochuManager.rtnInBoundNotify(msgInboundOrder, sta);
         */

    }

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {}

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        boolean flag = false;
        if (staCode == null) return flag;
        StockTransApplication sta = staDao.findStaByCode(staCode);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        URL wsdlURL = ScaleApp.WSDL_LOCATION;
        ScaleApp ss = new ScaleApp(wsdlURL, SERVICE_NAME);

        ss.setHandlerResolver(new HandlerResolver() {
            @SuppressWarnings("rawtypes")
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> handlerList = new ArrayList<Handler>();
                // 添加认证信息
                handlerList.add(new SecurityHeaderImpl());
                return handlerList;
            }
        });

        // ScaleAppHttpPost port = ss.getScaleAppHttpPost();
        // ScaleAppHttpGet port = ss.getScaleAppHttpGet();
        ScaleAppSoap port = ss.getScaleAppSoap();
        log.debug("Invoking orderCancel...");
        String result = port.orderCancel(staCode);
        flag = true;
        if (result != null && result.startsWith("T")) {// 订单取消成功
            // do nothing
            msgOutboundOrderCancelDao.save(msg);
            return true;
        } else if (result != null && result.startsWith("F01")) {// 不能进行操作，订单已发货
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_STA_HAS_OUT_BOUND);
        } else if (result != null && result.startsWith("F02")) {
            // 不能进行操作，当前状态：订单取消或者关闭
            msgOutboundOrderCancelDao.save(msg);
            return true;
        } else if (result != null && result.startsWith("F03")) {// WMS中订单未找到
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_STA_NOT_FOUND);
        } else if (result != null && result.startsWith("F91")) {// 令牌无效
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_TONKEN_INVALIDATION);
        } else if (result != null && result.startsWith("F92")) {// 订单编号为空
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_STA_ORDER_CODE_IS_NULL);
        } else if (result != null && result.startsWith("F99")) {// 系统异常，请重试
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_SYSTEM_ERROR);
        } else if (result == null || "".equals(result)) {// 错误信息为空
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_FEEBACK_MESSAGE_IS_NULL);
        } else {// 错误信息不匹配
            throw new BusinessException(ErrorCode.STA_CANCEL_FAILURE_NO_FEEBACK_MESSAGE);
        }
    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }
}
