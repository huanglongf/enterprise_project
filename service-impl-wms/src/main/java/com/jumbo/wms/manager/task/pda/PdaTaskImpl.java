/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.task.pda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.PdaHandOverLineDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PdaOrderLineDao;
import com.jumbo.dao.warehouse.PdaOrderLineSnDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.webservice.pda.manager.PdaOperationManager;
import com.jumbo.wms.daemon.PdaTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.pda.PdaReceiveManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.pda.InboundOnShelvesDetailCommand;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderLineSn;
import com.jumbo.wms.model.pda.PdaOrderType;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;

/**
 * @author laodai
 * 
 */

public class PdaTaskImpl extends BaseManagerImpl implements PdaTask {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8144816981726955010L;

    protected static final Logger logger = LoggerFactory.getLogger(PdaTaskImpl.class);

    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PdaOperationManager pdaOperationManager;

    @Autowired
    private PdaTaskManager pdaTaskManager;
    @Autowired
    private PdaOrderLineDao pdaOrderLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private PdaOrderLineSnDao pdaOrderLineSnDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private InventoryCheckLineDao inventoryCheckLineDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private PdaHandOverLineDao pdaHandOverLineDao;

    @Autowired
    private PdaReceiveManager pdaReceiveManager;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void pdaOrderTask() {
        // PDA入库与收货
        taskPdaInbound();
        // PDA库内移动
        executeInitiativeMoveInbound();
    }

    /**
     * PDA定时任务入库执行
     */
    public void taskPdaInbound() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.Inbound));
        // pdaList.addAll(pdaOrderDao.findNeedToPerformByType(PdaOrderType.INBOUND_SHELEVES));
        String errorMsg = null;
        for (PdaOrder pdaOrder : pdaList) {
            if (pdaOrder.getOrderCode() != null) {
                try {
                    pdaInboundValedate(pdaOrder);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode()), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                        log.error("", errorMsg);
                    } else {
                        errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_IN_BOUND_ERROR), null, Locale.SIMPLIFIED_CHINESE);
                        log.error("", errorMsg);
                    }
                    pdaOrder = pdaOrderDao.getByPrimaryKey(pdaOrder.getId());
                    pdaOperationManager.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.ERROR, errorMsg);
                }
            }
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void pdaReturnTask() {
        returnOrderExecute();
        inventoryCheckOrderExecute();
        handOverListExecute();
    }

    /**
     * PDA物流交接
     */
    private void handOverListExecute() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.HANDOVER));
        for (PdaOrder pdaOrder : pdaList) {
            try {
                exeHandOver(pdaOrder);
            } catch (BusinessException e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            } catch (Exception e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_EXECUTE_FAILED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            }
        }
    }

    /**
     * 执行物流交接
     * 
     * @param pdaOrder
     */
    @SuppressWarnings("unchecked")
    private void exeHandOver(PdaOrder pdaOrder) {
        List<String> transNo = pdaHandOverLineDao.getDetailList(pdaOrder.getId(), new SingleColumnRowMapper<String>(String.class));
        if (transNo.isEmpty()) {
            // 执行失败：PDA上传明细不存在
            throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
        }
        List<PackageInfoCommand> plist = null;
        Map<String, Object> resultMap = wareHouseManager.hoListCreateByHandStep1(transNo, pdaOrder.getLpCode(), Long.parseLong(pdaOrder.getOuId()), null);
        if (resultMap.get("removeByTrackingNo") != null || resultMap.get("removeBylpcode") != null || resultMap.get("removeBySta") != null) {
            throw new BusinessException(ErrorCode.PDA_HAVE_ERROR_ORDER);
        } else {
            plist = (List<PackageInfoCommand>) resultMap.get("pclist");
        }
        if (plist != null) {
            List<Long> idList = new ArrayList<Long>();
            for (PackageInfoCommand pc : plist) {
                idList.add(pc.getId());
            }
            HandOverList hand = wareHouseManagerExecute.createHandOverList(pdaOrder.getLpCode(), idList, Long.parseLong(pdaOrder.getOuId()), null, false);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_HAND_OVER_SUCCESS), new Object[] {hand.getCode()}, Locale.SIMPLIFIED_CHINESE);
            pdaOrderDao.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.FINISHED.getValue(), errorMsg);
        }
    }

    /**
     * PDA库内移动
     */
    public void pdaInnerMove() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.InnerMove));
        for (PdaOrder pdaOrder : pdaList) {
            try {
                exeInnerMove(pdaOrder);
            } catch (Exception e) {}
        }
    }

    public void exeInnerMove(PdaOrder po) {
        try {
            pdaOperationManager.exeInnerMove(po);
        } catch (BusinessException e) {
            log.error("", e);
            if (po != null) {
                pdaOperationManager.updatePdaOrderStatus(po.getId(), DefaultStatus.ERROR, e.getMessage());
            }
        } catch (Exception e) {
            log.error("", e);
            if (po != null) {
                pdaOperationManager.updatePdaOrderStatus(po.getId(), DefaultStatus.ERROR, e.getMessage());
            }
        }
    }

    /**
     * PDA入库
     */
    public void pdaInbound(List<PdaOrder> pdaList) {
        for (PdaOrder pdaOrder : pdaList) {
            if (pdaOrder.getOrderCode() != null) {
                pdaInboundValedate(pdaOrder);
            }
        }
    }

    private void pdaInboundValedate(PdaOrder pdaOrder) {
        StockTransApplication sta = staDao.findStaByCode(pdaOrder.getOrderCode());
        if (sta.getStatus().equals(StockTransApplicationStatus.CREATED) || sta.getStatus().equals(StockTransApplicationStatus.PARTLY_RETURNED)) {
            StockTransApplicationType type = sta.getType();
            if (type.equals(StockTransApplicationType.INBOUND_CONSIGNMENT) // 代销入库
                    || type.equals(StockTransApplicationType.INBOUND_PURCHASE) // 采购入库
                    || type.equals(StockTransApplicationType.INBOUND_SETTLEMENT)// 结算经销入库
                    || type.equals(StockTransApplicationType.INBOUND_OTHERS) // 其他入库
                    || type.equals(StockTransApplicationType.INBOUND_GIFT) // 赠品入库
                    || type.equals(StockTransApplicationType.INBOUND_MOBILE)// 移库入库
                    || type.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST) // 退换货申请-退货入库
                    || type.equals(StockTransApplicationType.GI_PUT_SHELVES) // GI
                                                                             // 上架
                    || type.equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)// VMI移库入库
                    || type.equals(StockTransApplicationType.VMI_ADJUSTMENT_INBOUND)) // VMI库存调整入库
            {
                execute(sta, pdaOrder);
            } else {
                throw new BusinessException(ErrorCode.DIFFERENCE_NUMBER_ERROR, new Object[] {pdaOrder.getOrderCode()});
            }
        } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
            // 作业单完成的状态默认将设置中间表数据设完成
            throw new BusinessException(ErrorCode.STA_ALREADY_FINISHED, new Object[] {pdaOrder.getOrderCode()});
        } else {
            // 状态错误 将信息记录
            throw new BusinessException(ErrorCode.PDA_STA_STATUS_ERROR, new Object[] {pdaOrder.getOrderCode()});
        }
    }

    /**
     * 执行单据
     * 
     * @param sta
     * @param pdaOrder
     * @return
     */
    public void execute(StockTransApplication sta, PdaOrder pdaOrder) {
        // 获取所有的作业明细单
        List<StockTransVoucher> stvList = stvDao.findStvCreatedListByStaId(sta.getId());
        StockTransVoucher stv = null;
        boolean isError = false;
        if (stvList != null && stvList.size() > 0) {
            for (StockTransVoucher tempStv : stvList) {
                // 判断是上架单据 还是 收货单 如果类型不存在(老逻辑创建的作业明细单可能不存在类型)作业明细单默认判断为收货单
                if (tempStv.getType() != null && tempStv.getType().equals(StockTransVoucherType.INBOUND_SHELVES)) {
                    throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_A, new Object[] {stv.getCode()});
                } else {
                    if (tempStv.getStatus().equals(StockTransVoucherStatus.CREATED)) {
                        if (pdaOrder.getType().equals(PdaOrderType.INBOUND_SHELEVES)) {
                            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR, new Object[] {stv.getCode()});
                        } else {
                            stv = tempStv;
                        }
                    } else if (tempStv.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
                        // 已经确认
                        throw new BusinessException(ErrorCode.STV_STRUTS_ERROR, new Object[] {stv.getCode()});
                    } else if (tempStv.getStatus().equals(StockTransVoucherStatus.CHECK)) {
                        // 核对状态的单据
                        if (pdaOrder.getType().equals(PdaOrderType.Inbound)) {
                            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR, new Object[] {stv.getCode()});
                        } else {
                            stv = tempStv;
                        }
                    }
                }
            }
        }
        if (!isError) {
            if (pdaOrder.getType().equals(PdaOrderType.Inbound)) {
                // 创建收货确认
                stv = pdaTaskManager.inboundConfirm(sta, stv, pdaOrder);
                pdaOrder.setStatus(DefaultStatus.FINISHED);
                pdaOrder.setFinishTime(new Date());
                pdaOrder.setMemo("完成");
                pdaOrderDao.save(pdaOrder);
            } else if (pdaOrder.getType().equals(PdaOrderType.INBOUND_SHELEVES)) {
                if (stv == null) {
                    throw new BusinessException(ErrorCode.PDA_NOT_FIND_STV, new Object[] {pdaOrder.getOrderCode()});
                }
                // 上架
                pdaTaskManager.inboundSheleves(sta, stv, pdaOrder, null, null);
                pdaOrder.setStatus(DefaultStatus.FINISHED);
                pdaOrder.setFinishTime(new Date());
                pdaOrder.setMemo("完成");
                pdaOrderDao.save(pdaOrder);
            }
        }
    }

    /**
     * PDA主动移库入库数据
     */
    public void executeInitiativeMoveInbound() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.InnerMove));
        for (PdaOrder pdaOrder : pdaList) {
            try {
                executeInitiative(pdaOrder);
            } catch (Exception e) {}
        }
    }

    private void executeInitiative(PdaOrder pdaOrder) {
        try {
            pdaOperationManager.executeInitiative(pdaOrder);
        } catch (BusinessException e) {
            log.error("", e);
            if (pdaOrder != null) {
                if (e.getMessage() == null) {
                    pdaOperationManager.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.CANCELED, e.getMessage());
                } else {
                    pdaOperationManager.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.ERROR, e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("", e);
            if (pdaOrder != null) {
                pdaOperationManager.updatePdaOrderStatus(pdaOrder.getId(), DefaultStatus.ERROR, e.getMessage());
            }
        }
    }

    /**
     * 定时任务执行PDA退仓单数据入口方法
     */
    public void returnOrderExecute() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.RETURN));
        for (PdaOrder po : pdaList) {
            try {
                executeReturnOrder(po);
            } catch (BusinessException e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            } catch (Exception e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            }
        }
    }

    /**
     * 按单执行PDA退仓单 创箱 执行出库
     * 
     * @param po
     */
    private void executeReturnOrder(PdaOrder po) {
        StockTransApplication sta = staDao.findStaByCode(po.getOrderCode());
        if (sta == null || sta.getStatus().getValue() != 2) {
            // PDA上传的STA:{0}不存在或者已执行!
            throw new BusinessException(ErrorCode.PDA_RETURN_STA_ERROR, new Object[] {sta == null ? "" : sta.getCode()});
        } else {
            List<PdaOrderLine> lineList = pdaOrderLineDao.findLineByPdaOrderId(po.getId());
            if (lineList.size() == 0) {
                // 执行失败，PDA单据明细不存在!
                throw new BusinessException(ErrorCode.PDA_DETAIL_NOT_FOUND);
            } else {
                // 按箱号汇总pdaOrderLine
                Map<String, List<PdaOrderLine>> map = new HashMap<String, List<PdaOrderLine>>();
                // 查询退仓明细行
                List<InboundOnShelvesDetailCommand> detail = inventoryDao.findReturnOrderDetail(po.getOrderCode(), false, new BeanPropertyRowMapper<InboundOnShelvesDetailCommand>(InboundOnShelvesDetailCommand.class));
                // 合并PDA上传明细行
                List<PdaOrderLine> line = pdaOrderLineDao.findReturnOrderDetail(po.getId(), new BeanPropertyRowMapper<PdaOrderLine>(PdaOrderLine.class));
                // 数据有效性校验 判断是否计划量大于执行量 或者存在计划外数据
                for (PdaOrderLine pl : line) {
                    boolean b = false;
                    for (InboundOnShelvesDetailCommand cd : detail) {
                        if (pl.getSkuCode().equals(cd.getSkuCode()) && pl.getLocationCode().equals(cd.getLocation())) {
                            if (pl.getQty() > Long.parseLong(cd.getQty())) {
                                // PDA上传数据中商品{0}在{1}库位上的执行量大于计划量!
                                throw new BusinessException(ErrorCode.PDA_RETURN_EXE_BIGTHAN_PLAN, new Object[] {pl.getSkuCode(), pl.getLocationCode()});
                            } else {
                                b = true;
                                break;
                            }
                        }
                    }
                    if (!b) {
                        // PDA上传数据中商品{0}为计划外数据!
                        throw new BusinessException(ErrorCode.PDA_RETURN_HAVE_SKU_OUTPLAN, new Object[] {pl.getSkuCode()});
                    }

                }
                // 数据有效性校验 sn号验证
                // 辅助判断sn号是否重复逻辑
                Map<String, String> snMap = new HashMap<String, String>();
                for (PdaOrderLine pol : lineList) {
                    List<PdaOrderLineSn> snList = pdaOrderLineSnDao.findSnByPdaOrderLineId(pol.getId(), new BeanPropertyRowMapper<PdaOrderLineSn>(PdaOrderLineSn.class));
                    if (!snList.isEmpty()) {
                        if (pol.getQty().intValue() != snList.size()) {
                            // PDA上传数据明细行{0}数量信息有误！
                            throw new BusinessException(ErrorCode.PDA_RETURN_LINE_QTY_ERROR, new Object[] {pol.getId()});
                        }
                        List<SkuSn> list = skuSnDao.getSkuSnByWhId(pol.getSkuCode(), Long.parseLong(po.getOuId()), new BeanPropertyRowMapper<SkuSn>(SkuSn.class));
                        if (list.isEmpty()) {
                            // PDA上传数据明细行{0}中sn无效！
                            throw new BusinessException(ErrorCode.PDA_RETURN_LINE_SN_ERROR, new Object[] {pol.getId()});
                        } else {
                            for (PdaOrderLineSn sn : snList) {
                                if (snMap.get(sn.getSnCode()) == null) {
                                    snMap.put(sn.getSnCode(), sn.getSnCode());
                                } else {
                                    // PDA上传数据明细行{0}中sn号{1}存在重复!
                                    throw new BusinessException(ErrorCode.PDA_RETURN_SN_REPEAT, new Object[] {pol.getId(), sn.getSnCode()});
                                }
                                boolean b = false;
                                for (SkuSn sn1 : list) {
                                    if (sn.getSnCode().equals(sn1.getSn())) {
                                        b = true;
                                    }
                                }
                                if (!b) {
                                    // PDA上传数据中明细行{0}SN号：{1}无效!
                                    throw new BusinessException(ErrorCode.PDA_RETURN_SN_ERROR, new Object[] {pol.getId(), sn.getSnCode()});
                                }
                            }
                        }
                    }
                }
                for (PdaOrderLine pl : lineList) {
                    if (map.get(pl.getIndex()) == null) {
                        List<PdaOrderLine> plList = new ArrayList<PdaOrderLine>();
                        plList.add(pl);
                        map.put(pl.getIndex(), plList);
                    } else {
                        map.get(pl.getIndex()).add(pl);
                    }
                }
                // 执行出库
                // try {
                // 占用sn号
                try {
                    pdaOperationManager.occupiedSn(sta.getId(), po.getId());
                } catch (BusinessException e) {
                    throw e;
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.PDA_RETURN_OCCUPIED_SN_ERROR);
                }
                pdaOperationManager.executeOutBound(po, sta, map);
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_FINISHED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.FINISHED.getValue(), errorMsg);
                // } catch (BusinessException e) {
                // po.setMemo("执行时出现异常异常代码为" + e.getErrorCode());
                // po.setStatus(DefaultStatus.CANCELED);
                // pdaOrderDao.updatePdaOrderStatus(po.getId(),
                // po.getStatus().getValue(),
                // po.getMemo());
                // } catch (Exception e) {
                // po.setMemo("执行出库系统异常");
                // po.setStatus(DefaultStatus.CANCELED);
                // pdaOrderDao.updatePdaOrderStatus(po.getId(),
                // po.getStatus().getValue(),
                // po.getMemo());
                // }
            }
        }
    }

    /**
     * 定时任务执行PDA盘点数据入口方法
     */
    public void inventoryCheckOrderExecute() {
        List<PdaOrder> pdaList = new ArrayList<PdaOrder>(pdaOrderDao.findNeedToPerformByType(PdaOrderType.INVENTORYCHECK));
        for (PdaOrder po : pdaList) {
            try {
                executeInventoryCheckOrder(po);
            } catch (BusinessException e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            } catch (Exception e) {
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
                pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.CANCELED.getValue(), errorMsg);
            }
        }
    }

    private void executeInventoryCheckOrder(PdaOrder po) {
        InventoryCheck ic = inventoryCheckDao.findByCode(po.getOrderCode());
        // 判断单据是否存在，且为新建单据
        if (ic == null || ic.getStatus().getValue() != 1) {
            // PDA上传单据对应的作业单不存在或者状态已变更!
            throw new BusinessException(ErrorCode.PDA_ORDER_NOTEXIST_OR_ERROR);
        } else {
            Customer customer = customerDao.getByWhouid(ic.getOu().getId());
            Map<String, Long> importData = new HashMap<String, Long>();
            // 校验数据
            validatePdaOrderData(po, ic.getId(), importData, customer);
            // 执行盘点操作，计算差异量
            pdaOperationManager.executeInventoryCheck(ic, importData);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_FINISHED), new Object[] {""}, Locale.SIMPLIFIED_CHINESE);
            pdaOrderDao.updatePdaOrderStatus(po.getId(), DefaultStatus.FINISHED.getValue(), errorMsg);
        }
    }

    /**
     * 验证PDA单据数据有效性
     * 
     * @param po
     */
    private void validatePdaOrderData(PdaOrder po, Long invCkId, Map<String, Long> importData, Customer customer) {
        Map<String, Long> locMap = inventoryCheckLineDao.findLocMap(invCkId, new MapRowMapper());
        // 查询合并后的盘点明细
        List<PdaOrderLine> pdList = pdaOrderLineDao.findInventoryCheckDetail(po.getId(), new BeanPropertyRowMapper<PdaOrderLine>(PdaOrderLine.class));
        // 明细判断 验证库位是否为当前盘点库位 商品只用判断存在与否即可(盘盈，盘亏)
        Long customerid = customer == null ? null : customer.getId();
        for (PdaOrderLine pl : pdList) {
            String barCode = pl.getSkuCode();
            Sku sku = skuDao.getByBarcode(barCode, customerid);
            if (sku == null) {
                SkuBarcode sbc = skuBarcodeDao.findByBarCode(barCode, customerid);
                if (sbc == null || sbc.getSku() == null) {
                    // PDA上传明细中明细行+pl.getId()+中条形码+pl.getSkuCode()+对应的SKU不存在！
                    throw new BusinessException(ErrorCode.PDA_LINE_BARCODE_SKU_NOT_EXISTS, new Object[] {pl.getSkuCode()});
                } else {
                    sku = sbc.getSku();
                }
            }
            if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                // 保质期商品不支持PDA盘点
                throw new BusinessException(ErrorCode.PDA_SHELF_MANAGEMENT_ERROR, new Object[] {pl.getSkuCode()});
            }
            Long locId = locMap.get(pl.getLocationCode());
            if (locId == null) {
                // PDA上传明细中明细行+pl.getId()+中库位+pl.getLocationCode()+非本次盘点库位!
                throw new BusinessException(ErrorCode.PDA_LINE_LOCATION_ERROR, new Object[] {pl.getLocationCode()});
            }
            String key = sku.getId() + "-" + locId;
            importData.put(key, pl.getQty());
        }
    }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void deletePDAReceiveMongoDb() {
        try {
            pdaReceiveManager.findAsnReceiveByTime();
        } catch (Exception e) {
            logger.error("deletePDAReceiveMongoDb" + e.toString());
        }

    }
}
