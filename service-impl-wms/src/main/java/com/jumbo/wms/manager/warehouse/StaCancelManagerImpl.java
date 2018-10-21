package com.jumbo.wms.manager.warehouse;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseFactory;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseInterface;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoStatus;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;


/**
 * 作业单取消
 * 
 * @author sjk
 * 
 */
@Transactional
@Service("staCancelManager")
public class StaCancelManagerImpl extends BaseManagerImpl implements StaCancelManager {

    private static final long serialVersionUID = 4899260998991315238L;
    protected static final Logger logger = LoggerFactory.getLogger(StaCancelManagerImpl.class);

    @Autowired
    private EventObserver eventObserver;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private VmiWarehouseFactory vmiWarehouseFactory;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private TransAliWaybill transAliWaybill;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PackageInfoDao packingInfoDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    AdvanceOrderAddInfoDao advanceOrderAddInfoDao;
    @Autowired
    private TransOlManager transOlManager;



    /**
     * 取消销售作业单 when cancel failed then throw exception
     * 
     * @param slipCode 相关单据号
     * @param type 单据类型 * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     */
    public boolean cancelSalesStaBySlipCode(String slipCode) {
        // 首先判断OMS系统订单取消，slipcode在WMS系统数据库中是否存在
        List<StockTransApplication> isStaExist = staDao.findBySlipCode(slipCode);
        StockTransApplicationType type = null;
        if (isStaExist == null || isStaExist.size() == 0) {
            // 判断单据是否存在
        //    throw new BusinessException(ErrorCode.STA_NOT_FOUND, new Object[] {slipCode});
            throw new BusinessException(ErrorCode.STA_NOT_FOUND1);
        } else {
            List<StockTransApplication> stas = staDao.findStaListBySlipCode(slipCode);
            if (stas == null || stas.size() == 0) {
                // 如果存在取消未处理且标志需要在释放库存后重置到新建，则将此标示去掉
                staDao.updateResetToCreateFlag(slipCode);
                return true;
            } else {
                for (StockTransApplication sta : stas) {
                    if ("1".equals(sta.getIsPreSale())) {// 预售订单
                        AdvanceOrderAddInfo ad = advanceOrderAddInfoDao.findOrderInfoByOrderCode(sta.getRefSlipCode(), new BeanPropertyRowMapperExt<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
                        if (ad != null && ad.getStatus() == 5) {
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL);
                        }
                        if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL);
                        }
                    } else {
                        // 作业单已核对、已转出、已完成状态不能取消
                       /* if (StockTransApplicationStatus.CHECKED.equals(sta.getStatus()) || // 作业单已核对
                                StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus()) || // 作业单已转出
                                StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) { // 作业单已完成
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL);
                        }*/
                        
                        if (StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) { // 作业单已核对
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL1);
                        }
                        if (StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) { // 作业单已转出
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL2);
                        }
                        
                        if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) { // 作业单已完成
                            throw new BusinessException(ErrorCode.STA_CANNOT_CANCEL3);
                        }
                        
                        
                        
                        
                    }
                }
                if (StockTransApplicationType.OUTBOUND_SALES.equals(stas.get(0).getType())) {
                    type = StockTransApplicationType.OUTBOUND_SALES;
                } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(stas.get(0).getType()) || StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(stas.get(0).getType())) {
                    type = StockTransApplicationType.INBOUND_RETURN_REQUEST;
                } else {
                    throw new BusinessException(ErrorCode.STA_NOT_FOUND);
                }
            }
        }
        return cancelSalesStaBySlipCode(slipCode, type);
    }

    public boolean cancelSalesStaBySlipCode(String slipCode, StockTransApplicationType type) {
        List<StockTransApplication> stas = staDao.findBySlipCodeByType(slipCode, type);
        if (stas == null || stas.size() == 0) {
            // 判断单据是否存在
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        } else {
            // 查询单据是否都已经取消
            for (StockTransApplication sta : stas) {
                if (StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                    // 如果存在取消未处理且标志需要在释放库存后重置到新建，则将此标示去掉
                    staDao.updateResetToCreateFlag(slipCode);
                    continue;
                } else {
                    return cancelStaForSales(sta);
                }
            }
        }
        return true;
    }

    /**
     * 所有销售相关作业单取消，取消后修改单据状态
     * 
     * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     * 
     */
    public boolean cancelStaForSales(StockTransApplication sta) {
        // 所有销售相关作业单取消验证
        if (!checkCancelStaForSales(sta)) {
            return false;
        }
        // 取消修改作业单状态
        cancelSalesStaToUpdate(sta);
        return true;
    }

    /**
     * 当取消订单为合并订单子订单处理
     * 
     * @param sta
     */
    private void cancelChildSalesStaToUpdate(StockTransApplication sta) {
        // 修改合并的订单状态
        cancelSalesStaToUpdateStatus(sta.getGroupSta());
        // 已占用库存则修改去除关联作业单还原为新建状态
        List<StockTransApplication> childrenStas = staDao.getChildStaByGroupId(sta.getGroupSta().getId());
        if (childrenStas != null) {
            for (StockTransApplication child : childrenStas) {
                // 如果是原被取消子订单跳过
                if (child.getId().equals(sta.getId())) {
                    continue;
                } else {
                    // 如果主订单未拣货，其他未取消的单子直接改为新建，解除关系，否则改为取消未处理，需要释放库存
                    if ((null == sta.getGroupSta().getPickingList() || (null != sta.getGroupSta().getPickingList() && (null == sta.getGroupSta().getPickingList().getIsHavePrint() || !sta.getGroupSta().getPickingList().getIsHavePrint())))) {
                        // 修改其他子订单状态,还原新建状态
                        // 取消stv
                        StockTransVoucher stv = stvDao.findStvCreatedByStaId(child.getId());
                        if (stv != null) {
                            int i = inventoryDao.releaseInventoryByStaId(child.getId());
                            if (i == 0) {
                                throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {child.getCode()});
                            }
                            // 重置sn号
                            skuSnDao.updateSNStatusByStvIdSql(stv.getId(), SkuSnStatus.USING.getValue());
                            stvLineDao.deleteByStvId(stv.getId());
                            stvDao.flush();
                            stvDao.delete(stv);
                        }
                        child.setStatus(StockTransApplicationStatus.CREATED);
                        child.setGroupSta(null);
                        child.getStaDeliveryInfo().setTrackingNo(null);
                        whInfoTimeRefDao.insertWhInfoTime(child.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null);
                    } else {
                        child.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
                        child.setResetToCreate(true);
                        // 订单状态与账号关联
                        whInfoTimeRefDao.insertWhInfoTime(child.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), null);
                    }
                }
            }
        }
        staDao.flush();
    }

    /**
     * 取消作业单修改数据
     * 
     * @param sta
     */
    private void cancelSalesStaToUpdate(StockTransApplication sta) {
        // 合并发货子订单处理
        if (sta.getGroupSta() != null) {
            cancelChildSalesStaToUpdate(sta);
        }
        cancelSalesStaToUpdateStatus(sta);
        // 调用 staListener取消事件
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 预售订单已转出上位系统发起取消 1：把库存补进去 2：在库存日志中也要补库存记录
     * 
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void releaseInventoryPreSale(StockTransApplication sta) {
        TransactionType transactionType = transactionTypeDao.findByCode("Pre-Inbound");
        List<StockTransTxLog> listLog = stockTransTxLogDao.findByOccupiedCodeLog(sta.getCode());
        if (listLog.size() == 0) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR_PRE, new Object[] {sta.getCode()});
        } else {
            // 验证防止并发
            List<StockTransTxLogCommand> ls = stockTransTxLogDao.findLogListByStaCodeAndtransId(transactionType.getId(), sta.getCode(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
            if (ls.size() > 0) {
                log.info("releaseInventoryPreSale 并发 :" + sta.getCode());
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR_PRE, new Object[] {sta.getCode()});
            }
            StockTransVoucher stv = stvDao.findStvCreatedByStaId2(sta.getId());
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stvDao.save(stv);
            inventoryDao.insertNewInventoryPre(sta.getCode());// 还原库存
            for (StockTransTxLog log : listLog) {// 补入库
                StockTransTxLog newLog = new StockTransTxLog();
                try {
                    BeanUtils.copyProperties(newLog, log);
                } catch (IllegalAccessException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("releaseInventoryPreSale IllegalAccessException:" + sta.getCode(), e);
                    }
                    throw new BusinessException(ErrorCode.STA_STATUS_ERROR_PRE, new Object[] {sta.getCode()});
                } catch (InvocationTargetException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("releaseInventoryPreSale InvocationTargetException:" + sta.getCode(), e);
                    }
                    throw new BusinessException(ErrorCode.STA_STATUS_ERROR_PRE, new Object[] {sta.getCode()});
                }
                newLog.setDirection(TransactionDirection.INBOUND);
                newLog.setTransactionType(transactionType);
                newLog.setId(null);
                stockTransTxLogDao.save(newLog);
                stockTransTxLogDao.flush();
            }
        }

    }

    /**
     * 更新取消取消作业单状态
     * 
     * @param sta
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void cancelSalesStaToUpdateStatus(StockTransApplication sta) {
        // 正常销售订单-新建与配货失败 ->取消已处理
        if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType()) && (StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }

        // 正常销售订单-配货中
        // 未加入配货批\加入配货批未拣货-> 取消已处理
        // 已拣货取消未处理
        // 上述逻辑如果是合并单，以主单为准
        else if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType()) && (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()))) {
            if ((sta.getGroupSta() == null && (null == sta.getPickingList() || (null != sta.getPickingList() && (sta.getPickingList().getIsHavePrint() == null || !sta.getPickingList().getIsHavePrint()))))
                    || (sta.getGroupSta() != null && (null == sta.getGroupSta().getPickingList() || (null != sta.getGroupSta().getPickingList() && (null == sta.getGroupSta().getPickingList().getIsHavePrint() || !sta.getGroupSta().getPickingList()
                            .getIsHavePrint()))))) { // 未加入配货清单
                releaseInventory2(sta); // 释放库存
                wareHouseManager.cancelAgv(sta.getCode(), sta.getMainWarehouse().getId());// AGV取消
            } else { // 已加入配货清单
                sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
                wareHouseManager.cancelAgv(sta.getCode(), sta.getMainWarehouse().getId());// AGV取消
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            }
        } else if ("1".equals(sta.getIsPreSale())) {
            // 预售订单 销售单据 已核对和已转出 还原库存数量以及 插入库存日志
            if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType()) && StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) {// 核对
                sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
                // 移除配货批
                sta.setPickingList(null);// 设置空
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            } else if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType()) && StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {// 已转出
                releaseInventoryPreSale(sta);
                sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            }
        }
        // 退换货入库作业单 ->取消已处理
        else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            // 取消退货入库作业单同时取消换货出库作业单,换货出只存在新建的
            StockTransApplication outSta = staDao.getStaByOwner(sta.getOwner(), sta.getRefSlipCode(), StockTransApplicationType.OUTBOUND_RETURN_REQUEST, StockTransApplicationStatus.CREATED);
            if (outSta != null) {
                outSta.setLastModifyTime(new Date());
                outSta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(outSta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                outSta.setCancelTime(new Date());
                try {
                    eventObserver.onEvent(new TransactionalEvent(outSta));
                } catch (BusinessException e) {
                    throw e;
                }
            }
        }
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(false);
        sta.setCancelTime(new Date());
        sta.setLastModifyTime(new Date());

        transAliWaybill.cancelTransNoByStaId(sta.getId());

        // 判断是否外包仓，如果是外包仓则表示外包仓取消通知已经完成
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
            msgOutboundOrderCancelDao.updateStaById(sta.getCode(), DefaultStatus.FINISHED.getValue());
        }
        staDao.flush();
    }

    /**
     * 销售作业单取消
     * 
     * @param sta
     * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     */
    public boolean checkCancelStaForSales(StockTransApplication sta) {
        // 通用状态验证
        checkNormalStaStatusForCancel(sta);
        // 内部退换货特殊判断(如果之 后存在换货转退货则可以取消，去掉该逻辑)
        if ("1".equals(TOMS_42_CANCEL) && StockTransApplicationType.OUTBOUND_RETURN_REQUEST == sta.getType()) {
            // 外包仓验证
            if (checkOutWhStaForCancel(sta, true) == false) {
                return false;
            }
            // 拆包验证
            checkSlipPackageStaForCancel(sta);
        } else if (SlipType.RETURN_REQUEST.toString().equals(sta.getRefSlipType().toString())) {
            // 查询关联退货入库作业单
            StockTransApplication inboundSta = staDao.getStaByOwner(sta.getOwner(), sta.getRefSlipCode(), StockTransApplicationType.INBOUND_RETURN_REQUEST, StockTransApplicationStatus.FINISHED);
            // 存在完成的退货入库作业单，换货出库作业单不能取消
            if (inboundSta != null) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
            // 获取换货入库单 进行取消
            List<StockTransApplication> list = staDao.findBySlipCodeByType(sta.getRefSlipCode(), StockTransApplicationType.INBOUND_RETURN_REQUEST);
            if (list == null || list.size() == 0) {
                throw new BusinessException(ErrorCode.SO_CODE_IS_NULL, new Object[] {sta.getCode()});
            }
            // 外包仓退货取消
            if (!checkRAWhStaForCancel(list.get(0))) {
                return false;
            }
        }
        // 销售出库流程取消验证
        else if (StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.toString().equals(sta.getStatus().toString()) || StockTransApplicationType.OUTBOUND_SALES.toString().equals(sta.getType().toString())
                || StockTransApplicationType.OUTBOUND_RETURN_REQUEST.toString().equals(sta.getType().toString())) {
            // 外包仓验证
            if (checkOutWhStaForCancel(sta, false) == false) {
                return false;
            }
            // 拆包验证
            checkSlipPackageStaForCancel(sta);
        }
        // 取消订单为合并发货子订单则验证其合并订单是否支持取消
        if (sta.getGroupSta() != null) {
            // 检查父订单能否取消
            if (checkCancelStaForSales(sta.getGroupSta()) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 外包仓退换货入库单外包仓反馈信息确定是否能够取消
     * 
     * @param sta
     */
    private boolean checkRAWhStaForCancel(StockTransApplication sta) {
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
            // 记录外包仓退换货取消日志
            MsgRaCancel msg = new MsgRaCancel();
            msg.setCreateTime(new Date());
            msg.setStaCode(sta.getCode());
            msg.setSlipCode(sta.getRefSlipCode());
            msg.setSource(wh.getVmiSource());
            msg.setSourceWh(wh.getVmiSourceWh());
            msg.setStatus(DefaultStatus.CREATED);
            msgRaCancelDao.save(msg);
            // 通过来源区分不同外包仓调用不同实现接口
            VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(wh.getVmiSource());
            if (vw != null) {
                // 外包仓取消，如果取消成功返回TRUE，需要等待返回FALSE，取消失败抛出异常
                return vw.cancelReturnRequest(msg.getId());
            }
        }
        return true;
    }

    /**
     * 通用取消订单状态检查
     * 
     * @param sta
     */
    private void checkNormalStaStatusForCancel(StockTransApplication sta) {
        if ("1".equals(sta.getIsPreSale())) {// 预售
            if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                // 作业单已完成
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            } else if (StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
                // 作业单部分入库
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
        } else {
            if (StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) {
                // 作业单已核对
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            } else if (StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
                // 作业单已出库
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            } else if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                // 作业单已完成
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            } else if (StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
                // 作业单部分入库
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
        }
    }

    /**
     * 外包仓订单通过外包仓反馈信息确定是否能够取消
     * 
     * @param sta
     */
    private boolean checkOutWhStaForCancel(StockTransApplication sta, Boolean b) {
        Boolean a = true;
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        // 判断是否外包仓作业单
        if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
            if (b) {
                List<StockTransApplication> stas = staDao.findBySlipCodeByType(sta.getRefSlipCode(), StockTransApplicationType.OUTBOUND_RETURN_REQUEST);
                for (StockTransApplication sta2 : stas) {
                    if (sta2.getIsLocked()) {
                        a = false;
                    } else {
                        a = true;
                        break;
                    }
                }
            }
            if (a) {
                // 记录取消日志
                MsgOutboundOrderCancel mc = msgOutboundOrderCancelDao.getByStaCode(sta.getCode());
                if (mc == null || mc.getStatus().equals(MsgOutboundOrderCancelStatus.FINISHED)) {
                    mc = new MsgOutboundOrderCancel();
                    Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                    mc.setUuid(id);
                    mc.setStaCode(sta.getCode());
                    mc.setSystemKey(sta.getSystemKey());
                    mc.setIsKey(0);
                    mc.setStaId(sta.getId());
                    mc.setSource(wh.getVmiSource());
                    mc.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                    mc.setCreateTime(new Date());
                    msgOutboundOrderCancelDao.save(mc);
                }
                // 通过来源区分不同外包仓调用不同实现接口
                VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(wh.getVmiSource());
                if (vw != null) {
                    // 外包仓取消，如果取消成功返回TRUE，需要等待返回FALSE，取消失败抛出异常
                    return vw.cancelSalesOutboundSta(sta.getCode(), mc);
                }
            }
        }
        return true;
    }


    /**
     * 销售作业单如果存在拆包情况，只要有1个包裹已经出库，则作业单不允许取消
     * 
     * @param sta
     */
    private void checkSlipPackageStaForCancel(StockTransApplication sta) {
        // 查询作业单所有包裹过
        List<PackageInfo> pglist = packageInfoDao.findByStaId(sta.getId());
        if ("1".equals(sta.getIsPreSale())) {
            if (pglist != null && pglist.size() > 0) {
                for (PackageInfo pg : pglist) { // 预售订单
                    // 如果包裹存在完成则作业单不允许取消
                    if (PackageInfoStatus.FINISHED.equals(pg.getStatus())) {
                        throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
                    }
                }
            }
        } else {
            if (pglist != null && pglist.size() > 0) {
                for (PackageInfo pg : pglist) {
                    // 如果包裹存在出库或者完成则作业单不允许取消
                    if (PackageInfoStatus.OUTBOUND.equals(pg.getStatus()) || PackageInfoStatus.FINISHED.equals(pg.getStatus())) {
                        throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
                    }
                }
            }
        }
    }

    /**
     * 外包仓取消作业单，释放库存占用，作业单取消已处理
     * 
     * @param sta
     */
    private void cancelSalesStaToUpdateForOutWh(StockTransApplication sta) {
        inventoryDao.releaseInventoryByStaId(sta.getId());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setCancelTime(new Date());
        sta.setIsNeedOccupied(false);
    }


    public boolean cancelStaBySlipCode(String soCode) {
        List<StockTransApplication> stas = staDao.findBySlipCodeByType(soCode, StockTransApplicationType.OUTBOUND_SALES);
        // 判断单据是否存在
        if (stas == null || stas.size() == 0) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        for (StockTransApplication sta : stas) {
            // 存在已出库OR完成作业单抛出异常无法取消
            if (StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus()) || StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
        }
        // 查询单据是否都已经取消
        for (StockTransApplication sta : stas) {
            // 存在非取消作业单
            if (!StockTransApplicationStatus.CANCELED.equals(sta.getStatus()) && !StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus())) {
                Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                // 判断是否需要通知外包仓
                if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
                    // 记录取消日志
                    MsgOutboundOrderCancel mc = msgOutboundOrderCancelDao.getByStaCode(sta.getCode());
                    // 存在取消的则不再记录
                    if (mc == null) {
                        mc = new MsgOutboundOrderCancel();
                        Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                        mc.setUuid(id);
                        mc.setStaCode(sta.getCode());
                        mc.setStaId(sta.getId());
                        mc.setSource(wh.getVmiSource());
                        mc.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                        mc.setCreateTime(new Date());
                        msgOutboundOrderCancelDao.save(mc);
                    }
                    VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(wh.getVmiSource());
                    if (vw != null) {
                        // 外包仓取消，如果取消成功返回TRUE，需要等待返回FALSE，取消失败抛出异常
                        boolean rs = vw.cancelSalesOutboundSta(sta.getCode(), mc);
                        if (rs == true) {
                            // 外包仓取消成功，取消作业单，取消库存占用
                            cancelSalesStaToUpdateForOutWh(sta);
                        }
                        return rs;
                    } else {
                        // 未定义外包仓取消接口，说明走自营仓逻辑
                        return cancelSalesSta(sta);
                    }
                } else {
                    // 自营仓取消
                    // 取消成功修改仓库单据状态，取消失败抛出异常
                    return cancelSalesSta(sta);
                }
            }
        }
        // 执行到这说明未执行上面所有逻辑，系统异常直接抛出
        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    }

    private boolean cancelSalesSta(StockTransApplication sta) {
        if (StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus())) {
            // 新建与配货失败 直接取消已处理，取消数量占用
            sta.setStatus(StockTransApplicationStatus.CANCELED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setIsNeedOccupied(false);
        } else if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()) || StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) {
            // 配货中、已核对 取消未处理
            sta.setStatus(StockTransApplicationStatus.CANCEL_UNDO);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCEL_UNDO.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode(), sta.getStatus()});
        }
        sta.setCancelTime(new Date());
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        staDao.flush();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
            msgOutboundOrderCancelDao.updateStaById(sta.getCode(), DefaultStatus.FINISHED.getValue());
        }
        // 更新批状态
        if (sta.getPickingList() != null) {
            wareHouseManager.updatePickinglistToFinish2(sta.getId(), sta.getPickingList().getId(), sta.getMainWarehouse().getId());
        }
        return true;
    }

    /**
     * 电子面单无法送达取消后记录通知OMS数据
     * 
     * @param staId
     */
    public void cancelStaForTransOlOrder(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 非合并订单处理流程
        if (!sta.getIsMerge() && sta.getGroupSta() == null) {
            // 判断是否销售订单
            if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
                if (sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                    releaseInventory(sta); // 释放库存
                }
                // 取消作业单
                if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus())) {
                    sta.setStatus(StockTransApplicationStatus.CANCELED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                }
                sta.setLastModifyTime(new Date());
                sta.setCancelTime(new Date());
                staDao.flush();
                // 作业单从配货清单移出
                staDao.remveCanceledStaPickingByStaId(staId);
                // 记录通知OMS数据
                wareHouseManagerExecute.addStaCancelNoticeOmsCount(sta.getId());
                try {
                    eventObserver.onEvent(new TransactionalEvent(sta));
                } catch (BusinessException e) {
                    throw e;
                }
            }
        } else {
            // 合并订单处理流程
            if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
                // 如果是子订单，则取主订单的ID
                if (sta.getGroupSta() != null) {
                    staId = sta.getGroupSta().getId();
                }
                // 根据ID查询合并订单的主订单和子订单
                List<StockTransApplication> staList = staDao.getMergeStaById(staId);
                for (StockTransApplication stas : staList) {
                    if (stas.getIsMerge() && stas.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                        releaseInventory(stas); // 释放库存
                    }
                    // 取消作业单
                    if (StockTransApplicationStatus.OCCUPIED.equals(stas.getStatus()) || StockTransApplicationStatus.FAILED.equals(stas.getStatus())) {
                        stas.setStatus(StockTransApplicationStatus.CANCELED);
                        // 订单状态与账号关联
                        whInfoTimeRefDao.insertWhInfoTime2(stas.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                    }
                    stas.setLastModifyTime(new Date());
                    stas.setCancelTime(new Date());
                    staDao.flush();
                    // 主作业单从配货清单移出
                    if (stas.getIsMerge()) {
                        staDao.remveCanceledStaPickingByStaId(stas.getId());
                    } else {
                        // 子订单记录通知OMS数据
                        wareHouseManagerExecute.addStaCancelNoticeOmsCount(stas.getId());
                    }
                    try {
                        eventObserver.onEvent(new TransactionalEvent(stas));
                    } catch (BusinessException e) {
                        throw e;
                    }
                }
            }

        }

    }

    /**
     * 物流不可达转EMS
     */
    public void transErrorToEms(Long staId, Long locId, Long ouId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 非合并订单处理流程
        if (!sta.getIsMerge() && sta.getGroupSta() == null) {
            // 作业单从配货清单移出
            if (null != locId) {
                // staDao.updatePickingListIdById(staId);
                staDeliveryInfoDao.updateByPrimaryKey(staId, Constants.LPCODE_EMS, null);
                try {
                    transOlManager.matchingTransNo(sta.getId(), Constants.LPCODE_EMS, ouId);
                } catch (Exception e) {
                    logger.error("transErrorToEms" + e.toString());
                    staDao.updatePickingListIdById(staId);
                }
            } else {
                staDao.updatePickingListIdById(staId);
                staDeliveryInfoDao.updateByPrimaryKey(staId, Constants.LPCODE_EMS, null);
            }

        } else {
            // 合并订单处理流程
            // 如果是子订单，则取主订单的ID
            if (sta.getGroupSta() != null) {
                staId = sta.getGroupSta().getId();
            }
            // 根据ID查询合并订单的主订单和子订单
            List<StockTransApplication> staList = staDao.getMergeStaById(staId);
            for (StockTransApplication stas : staList) {
                staDao.updatePickingListIdById(stas.getId());
                staDeliveryInfoDao.updateByPrimaryKey(stas.getId(), Constants.LPCODE_EMS, null);
            }
        }
    }


    /**
     * 根据相关单据号 和 配货清单编号查询Sta
     * 
     * @param id pickingList ID
     * @param code slipCode
     * @param beanPropertyRowMapper
     * @return
     */
    public StockTransApplication findStaBySlipCodeAndPid(Long id, String code) {
        return staDao.findStaBySlipCodeAndPid(id, code, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
    }

    private void releaseInventory(StockTransApplication sta) {
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            // 如果是合并的作业单，需要子作业单ID取消占用库存
            List<StockTransApplication> oldStaList = staDao.findStaByNewStaId(sta.getId());
            for (StockTransApplication s : oldStaList) {
                if (s.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                    staDao.updateReleaseInventoryByStaId(s.getId());
                    staDao.deleteStvLineByStaId(s.getId());
                    staDao.deleteStvByStaId(s.getId());
                    staDao.updateStaToStatusByStaId(s.getId());
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse().getId());
                }
            }
            if (sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                staDao.deleteStvLineByStaId(sta.getId());
                staDao.deleteStvByStaId(sta.getId());
                staDao.updateStaToStatusByStaId(sta.getId());
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse().getId());
            }
        } else {
            // 不是合并的作业单
            if (sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                staDao.updateReleaseInventoryByStaId(sta.getId());
                staDao.deleteStvLineByStaId(sta.getId());
                staDao.deleteStvByStaId(sta.getId());
                staDao.updateStaToStatusByStaId(sta.getId());
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse().getId());
            }
        }
    }


    /**
     * 释放作业单库存
     * 
     * @param sta
     */
    public void releaseInventory2(StockTransApplication sta) {
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
        if (stv == null) {
            List<Inventory> invList = inventoryDao.findByOccupiedCode(sta.getCode());
            if (invList != null && invList.size() != 0) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            } else {
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                return;
            }
        }
        // 是否合并单
        if (null != sta.getIsMerge() && true == sta.getIsMerge()) {
            // 如果是合并订单，则主订单不占用库存
        } else {
            int i = inventoryDao.releaseInventoryByStaId(sta.getId());
            if (null != sta && null != sta.getOwner() && sta.getOwner().toLowerCase().contains("adidas")) {

            } else {
                // 释放库存
                if (i == 0) {
                    throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
                }
            }

        }
        // 取消stv
        stv.setStatus(StockTransVoucherStatus.CANCELED);
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);
        // 更新sta状态，取消数量占用
        sta.setStatus(StockTransApplicationStatus.CANCELED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setLastModifyTime(new Date());
        sta.setIsNeedOccupied(false);
        // 重置sn号
        snDao.updateSNStatusByStvIdSql(stv.getId(), SkuSnStatus.USING.getValue());
    }

    /**
     * 重置作业单状态为新建
     * 
     * @param staId
     */
    public String updateStaStatusToCreate(String staCode, Long ouId) {
        if (staCode != null && !"".equals(staCode)) {
            StockTransApplication sta = staDao.getByCode(staCode);
            if (sta == null) {
                return "此作业单编码不存在！";
            }
            if (!ouId.equals(sta.getMainWarehouse().getId())) {
                return "作业单不在此仓库！";
            }
            if (sta.getType() != StockTransApplicationType.OUTBOUND_SALES && sta.getType() != StockTransApplicationType.OUTBOUND_RETURN_REQUEST) {
                return "只能重置销售出或换货出的作业单！";
            }
            if (sta.getStatus() != StockTransApplicationStatus.CHECKED && sta.getStatus() != StockTransApplicationStatus.OCCUPIED) {
                return "此作业单不可重置为新建状态！";
            }
            if ((sta.getIsMerge() == null || sta.getIsMerge() == false) && sta.getGroupSta() == null) {
                // 非合并订单
                resetStaToCreate(sta);
            } else {
                // 合并订单
                if (sta.getGroupSta() != null) {
                    sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
                }
                List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta.getId());
                if (staList != null && staList.size() > 0) {
                    for (StockTransApplication s : staList) {
                        resetStaToCreate(s);
                        staDao.save(s);
                    }
                }
                // 清除stv和STVLine
                StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
                List<StvLine> stvLineList = stvLineDao.findStvLineListByStvId(stv.getId());
                stvLineDao.deleteAll(stvLineList);
                stvDao.delete(stv);
                // 清除包裹信息
                List<PackageInfo> piList = packingInfoDao.findByStaId(sta.getId());
                if (piList != null && piList.size() > 0) {
                    packingInfoDao.deleteAll(piList);
                }
                sta.setStatus(StockTransApplicationStatus.CANCELED);
            }
            // staDao.save(sta);
            return "";
        } else {
            return "作业单编码不可为空！";
        }
    }

    public String updateStaRestore(String staCode, Long ouId) {
        StockTransApplication sta = staDao.getByCode(staCode);
        if (sta == null) {
            StockTransApplication sta1 = staDao.findByStaCodeByArc(staCode, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            if (sta1 == null) {
                return "2";// 没有改单据
            } else {
                Map<String, Object> invparams = new HashMap<String, Object>();
                invparams.put("v_id", sta1.getId());
                SqlParameter[] invSqlP = {new SqlParameter("v_id", java.sql.Types.NUMERIC)};
                staDao.executeSp("DATA_RESTORE_STA_INFO", invSqlP, invparams);
            }
        } else {
            return "3";// 该作业单已经还原
        }
        return "1";
    }


    private void resetStaToCreate(StockTransApplication sta) {
        // 清除包裹信息
        List<PackageInfo> piList = packingInfoDao.findByStaId(sta.getId());
        if (piList != null && piList.size() > 0) {
            packingInfoDao.deleteAll(piList);
        }
        // 重置核对数量
        List<StaLine> slList = staLineDao.findByStaId(sta.getId());
        if (slList != null && slList.size() > 0) {
            for (StaLine sl : slList) {
                sl.setCompleteQuantity(0L);
                staLineDao.save(sl);
            }
        }
        // 清除stv和STVLine
        StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
        List<StvLine> stvLineList = stvLineDao.findStvLineListByStvId(stv.getId());
        stvLineDao.deleteAll(stvLineList);
        stvDao.delete(stv);

        // 清除库存占用
        inventoryDao.releaseInventoryByOpcOcde(sta.getCode());
        // 作业单状态修改
        sta.setPickingList(null);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setGroupSta(null);
    }
}
