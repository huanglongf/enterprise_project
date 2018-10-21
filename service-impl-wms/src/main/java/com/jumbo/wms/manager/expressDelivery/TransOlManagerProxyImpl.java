package com.jumbo.wms.manager.expressDelivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.StaCancelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

@Service("transOlManagerProxy")
public class TransOlManagerProxyImpl extends BaseManagerImpl implements TransOlManagerProxy {

    private static final long serialVersionUID = -1957966619629476018L;

    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private StaCancelManager staCancelManager;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private BiChannelDao biChannelDao;

    /**
     * 匹配快递运单号
     */
    @Override
    public void matchingTransNo(Long plId, Long locId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        try {
            boolean isError = false;
            BusinessException root = new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {""});
            List<StockTransApplication> stas = wareHouseManager.findStaByPickingList(pl.getId(), pl.getWarehouse().getId());
            for (StockTransApplication sta : stas) {
                try {
                    transOlManager.matchingTransNo(sta.getId(), sta.getStaDeliveryInfo().getLpCode(), pl.getWarehouse().getId());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        setLinkedBusinessException(root, be);
                        if (be.getErrorCode() == ErrorCode.SF_INTERFACE_ERROR || be.getErrorCode() == ErrorCode.YTO_INTERFACE_ERROR || ErrorCode.PROVIDER_NO_TRANS_OR_ERROR == be.getErrorCode()) {
                            // 顺丰或者圆通接口无法接通，不处理
                            // 或者获取运单的物流商站点无运单号时也不处理，此时需通知物流商已无运单号
                            isError = true;
                        } else if (be.getErrorCode() == ErrorCode.TRANS_CAN_NOT_SEND) {
                            BiChannel bi = biChannelDao.getByCode(sta.getOwner());
                            // 物流不可达，不取消订单，转EMS，剔除配货批
                            if (bi != null && bi.getTransErrorToEms() != null && bi.getTransErrorToEms()) {
                                staCancelManager.transErrorToEms(sta.getId(), locId, pl.getWarehouse().getId());
                            } else {
                                // 顺丰无法送到处理取消订单调用OMS接口
                                staCancelManager.cancelStaForTransOlOrder(sta.getId());
                                isError = true;
                            }
                        } else {
                            isError = true;
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                    } else {
                        isError = true;
                        log.error("", e);
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                }
            }
            if (isError) {
                throw root;
            }
        } catch (Exception e) {
            // 失败修改配货清单物流无法送达
            wareHouseManager.updateStaPickingListStatus(pl.getId(), PickingListStatus.FAILED_SEND);
            // 删除为空的配货清单
            pickingListDao.deleteBlankPickingById(pl.getId());
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    @Override
    public void matchingTransNoByPackage(Long plId) {
        if (log.isDebugEnabled()) {
            log.debug("----- matchingTransNoByPackage-----" + plId);
        }
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        try {
            boolean isError = false;
            BusinessException root = new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {""});
            List<StockTransApplication> stas = wareHouseManager.findStaByPickingList(pl.getId(), pl.getWarehouse().getId());
            try {
                transOlManager.matchingTransNoByPackage(pl.getId(), stas.get(0).getStaDeliveryInfo().getLpCode(), pl.getWarehouse().getId());
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    setLinkedBusinessException(root, be);
                    if (be.getErrorCode() == ErrorCode.SF_INTERFACE_ERROR) {
                        // 顺丰接口无法接通，不处理
                        isError = true;
                    } else if (be.getErrorCode() == ErrorCode.TRANS_CAN_NOT_SEND) {
                        // 顺丰无法送到处理取消订单调用OMS接口
                        for (StockTransApplication sta : stas) {
                            staCancelManager.cancelStaForTransOlOrder(sta.getId());
                        }
                        isError = true;
                    } else {
                        isError = true;
                        log.error("", e);
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                } else {
                    isError = true;
                    log.error("", e);
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
            if (isError) {
                throw root;
            }
        } catch (Exception e) {
            // 失败修改配货清单物流无法送达
            wareHouseManager.updateStaPickingListStatus(pl.getId(), PickingListStatus.FAILED_SEND);
            // 删除为空的配货清单
            pickingListDao.deleteBlankPickingById(pl.getId());
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    @Override
    public void matchingTransNoByInvoiceOrder(List<Long> wioId) {
        try {
            boolean isError = false;
            BusinessException root = new BusinessException(ErrorCode.TRANS_CAN_NOT_SEND, new Object[] {""});
            List<WmsInvoiceOrderCommand> wioList = wareHouseManager.findWmsInvoiceOrderByWioIdlist(wioId);
            for (WmsInvoiceOrderCommand w : wioList) {
                try {
                    StockTransApplication sta = stockTransApplicationDao.getByCode(w.getOrderCode());
                    transOlManager.invoiceOrderMatchingTransNo(w.getId(), w.getLpCode(), sta.getMainWarehouse().getId());
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        setLinkedBusinessException(root, be);
                        if (be.getErrorCode() == ErrorCode.SF_INTERFACE_ERROR) {
                            // 顺丰或者圆通接口无法接通，不处理
                            isError = true;
                        } else if (be.getErrorCode() == ErrorCode.TRANS_CAN_NOT_SEND) {
                            // 顺丰无法送到处理取消订单调用OMS接口
                            // staCancelManager.cancelStaForTransOlOrder(sta.getId());
                            isError = true;
                        } else {
                            isError = true;
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                    } else {
                        isError = true;
                        log.error("", e);
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                }
            }
            if (isError) {
                throw root;
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }

    }


}
