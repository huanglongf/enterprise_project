package com.jumbo.wms.newInventoryOccupy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderCommand;
import com.jumbo.wms.model.pickZone.OcpInvConstants;


public class StaOcpBatchThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(StaOcpBatchThread.class);
    private NewInventoryOccupyManager newInventoryOccupyManager;
    private WareHouseManagerProxy wareHouseManagerProxy;
    private Long wooId;

    public StaOcpBatchThread(Long wooId, NewInventoryOccupyManager newInventoryOccupyManager, WareHouseManagerProxy wareHouseManagerProxy) {
        this.wooId = wooId;
        this.newInventoryOccupyManager = newInventoryOccupyManager;
        this.wareHouseManagerProxy = wareHouseManagerProxy;
    }

    @Override
    public void run() {
        try {
            staInventoryOcpByBatch(wooId);
        } catch (Exception e) {
            newInventoryOccupyManager.staOcpException(wooId);
            log.error(wooId + " StaOcpBatchThread thread error:", e);
        }
    }

    private void staInventoryOcpByBatch(Long wooId) {
        if (log.isInfoEnabled()) {
            log.info(wooId + " staInventoryOcpByBatch start..........");
        }
        WhOcpOrderCommand woo = newInventoryOccupyManager.getWhocpOrder(wooId);
        // 查询待占用订单
        List<Long> staIdList = newInventoryOccupyManager.findOcpStaIdByOcpCod(woo.getOuId(), woo.getCode());
        for (Long staId : staIdList) {
            try {
                wareHouseManagerProxy.newOccupiedInventoryBySta(staId, null, woo.getCode());
            } catch (BusinessException e) {
                log.warn(staId + " staInventoryOcpByBatch error : {}", e.getErrorCode());
            } catch (Exception e) {
                log.error(staId + " staInventoryOcpByBatch error..........", e);
            }
        }
        // 释放多余的库存
        newInventoryOccupyManager.releaseInventoryByOcpCode(woo.getCode());
        Long conut = newInventoryOccupyManager.queryInventoryByOcpCode(woo.getCode());
        newInventoryOccupyManager.updateStaIsNeedOcpByOcpId(1l, wooId); // 作业单打标需要占用库存
        if (conut > 0) {
            log.error(woo.getCode() + " staInventoryOcpByBatch ocp error..........");
            throw new BusinessException(ErrorCode.OCP_ORDER_BATCH_ERROR);
        }
        newInventoryOccupyManager.updateWhOcpOrderStatus(wooId, OcpInvConstants.OCP_ORDER_STATUS_OVER); // 更新批次状态为占用结束
        if (log.isInfoEnabled()) {
            log.info(wooId + " staInventoryOcpByBatch end..........");
        }
    }
}
