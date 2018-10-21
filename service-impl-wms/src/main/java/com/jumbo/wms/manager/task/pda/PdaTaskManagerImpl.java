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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PdaOrderLineDao;
import com.jumbo.dao.warehouse.PdaOrderLineSnDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderLineSn;
import com.jumbo.wms.model.pda.PdaOrderType;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * @author laodai
 * 
 */
@Transactional
@Service("pdaTaskManager")
public class PdaTaskManagerImpl extends BaseManagerImpl implements PdaTaskManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8144816981726955010L;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExecute whExecute;
    @Autowired
    private PdaOrderLineSnDao pdaOrderLineSnDao;
    @Autowired
    private PdaOrderLineDao pdaOrderLineDao;
    @Autowired
    private WarehouseLocationDao locDao;
    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;


    /**
     * 创建收货单
     * 
     * @param sta
     * @param pdaOrder
     * @return
     */
    public StockTransVoucher inboundConfirm(StockTransApplication sta, StockTransVoucher stv, PdaOrder pdaOrder) {
        if (stv != null) {
            stvLineDao.deleteByStvId(stv.getId());
            stvDao.delete(stv);
            stvDao.flush();
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            pdaOrder.setMemo("TransactionType not found!");
            pdaOrder.setStatus(DefaultStatus.CANCELED);
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        stv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv.setBusinessSeqNo(biSeqNo.longValue());
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        stv.setMode(null);
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setOwner(sta.getOwner());
        stv.setSta(sta);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CONFIRMED);
        stv.setType(StockTransVoucherType.INBOUND_ORDER);
        stv.setCreateTime(new Date());
        stv.setTransactionType(tranType);
        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())) {
            stv.setWarehouse(sta.getAddiWarehouse());
        } else {
            stv.setWarehouse(sta.getMainWarehouse());
        }
        stv = stvDao.save(stv);
        stvDao.flush();
        // 创建收货单明细
        List<StaLine> staLineList = staLineDao.findByStaId(sta.getId());
        // 根据每个skuCode的商品列表
        Map<String, List<StaLine>> map = new HashMap<String, List<StaLine>>();
        // 收货单的商品
        for (StaLine staLine : staLineList) {
            // PDA 过来的SKU数据里面可能商品编码 默认应是条码
            // 但是这边没有考虑到多条码的数据
            String key1 = staLine.getSku().getCode();
            String key2 = staLine.getSku().getBarCode();
            if (map.containsKey(key1)) {
                map.get(key1).add(staLine);
            } else if (map.containsKey(key2)) {
                map.get(key2).add(staLine);
            } else {
                List<StaLine> list = new ArrayList<StaLine>();
                list.add(staLine);
                map.put(key1, list);
                map.put(key2, list);
            }
        }
        // 批次生成
        String batchCode = Long.valueOf(new Date().getTime()).toString();
        List<PdaOrderLine> pdaLineList = pdaOrderLineDao.findLineByPdaOrderId(pdaOrder.getId());
        if (pdaLineList == null || pdaLineList.size() == 0) {
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
        Map<Long, StvLine> stvLineMap = new HashMap<Long, StvLine>();
        // 默认入库是良品
        InventoryStatus lp = inventoryStatusDao.findInvStatusForSale(wareHouseManager.findCompanyOUByWarehouseId(sta.getMainWarehouse().getId()).getId());
        List<StvLine> stvLineList = new ArrayList<StvLine>();
        Long skuQty = 0L;
        // 保存已收货量(这个已收货量 只是正对本次STV的已收货量)
        Map<Long, Long> lineQty = new HashMap<Long, Long>();
        for (PdaOrderLine pdaLine : pdaLineList) {
            if (pdaLine.getQty() == null || pdaLine.getQty() < 1) {
                log.debug("Qty Error!" + pdaLine.getQty());
                pdaOrder.setMemo("Qty Error!" + pdaLine.getQty());
                pdaOrder.setStatus(DefaultStatus.CANCELED);
                throw new BusinessException(ErrorCode.PDA_PLAN_QTY_LQ_ACTUAL);
            }
            List<StaLine> tempLine = map.get(pdaLine.getSkuCode());
            /**
             * 在pda列表里面可能存在一个SKU 只有一行数据 而我们StvLine 里面的SKU对应着多行，这时候就做PDA拆分
             * 也有可能在PDA列表里面一个SKU对应多行数据，而我们stvLine 里面却只有一行SKU ，也须要做合并
             */
            // 保存剩余数量
            Long residueQty = 0L;
            // 最后收剩余数量的Line
            StvLine residueLine = null;
            // 收货量超出计划量的单行收货量
            Long tempQty = pdaLine.getQty();
            if (tempLine != null) {
                boolean isSuccess = false;
                for (StaLine line : tempLine) {
                    if (line.getInvStatus() == null || StringUtil.isEmpty(pdaLine.getInvStatus()) || pdaLine.getInvStatus().equals(line.getInvStatus().getName())) {
                        Long key = line.getId();
                        // 获取对应StaLine 已经收货商品数量
                        Long qty = lineQty.containsKey(line.getId()) ? lineQty.get(line.getId()) : 0;
                        // 剩余计划量
                        Long remainPlanQty = line.getQuantity() - (line.getCompleteQuantity() == null ? 0 : line.getCompleteQuantity());
                        // 判断当前StaLine 是否已经有对应的stvLine 而且 计划量已经等于执行量
                        if (!remainPlanQty.equals(0L) && qty.equals(remainPlanQty)) {
                            /**
                             * !remainPlanQty.equals(0) 是有可能以前收货完成了 现在再次收货 在这边是认许的.
                             * 因为在审核的流程里面是可以做调整的 在 审核的流程里面做调整 是可以调整为0， 但是上架的时候不能对审核量为0 的数据做上架
                             */
                            residueLine = stvLineMap.get(key);
                            if (residueLine != null) {
                                isSuccess = true;
                            }
                            continue;
                        }
                        tempQty = 0L;
                        isSuccess = true;

                        // 到现在总共须要收多少商品
                        Long allQty = pdaLine.getQty() + residueQty;

                        /**
                         * 计算出此次Line 能收多少商品 remainPlanQty.equals(0) 如果计划量已经为0 了, 就不用判断收货多少商品
                         * 直接收货,等审核的时候再做调整. (remainPlanQty - qty) 是最大限度能收多少，（receiptQty） 实际收货量.
                         */
                        Long receiptQty = remainPlanQty.equals(0L) ? allQty : allQty < (remainPlanQty - qty) ? allQty : (remainPlanQty - qty);
                        // 计算出剩余收货量
                        residueQty = allQty - receiptQty;
                        // 保存上架总量
                        skuQty += receiptQty;
                        // 保存已收货量
                        lineQty.put(key, qty + receiptQty);
                        // 判断是否存在同库位同商品同状态同店铺的line信息 如果存在就做合并
                        if (stvLineMap.containsKey(key)) {
                            StvLine temp = stvLineMap.get(key);
                            temp.setReceiptQty(temp.getQuantity() + receiptQty);
                            residueLine = temp;
                            temp.setDifferenceQty(temp.getReceiptQty() - temp.getRemainPlanQty());
                            temp.setQuantity(temp.getReceiptQty() > temp.getRemainPlanQty() ? temp.getRemainPlanQty() : temp.getReceiptQty());
                        } else {
                            StvLine stvLine = new StvLine();
                            stvLine.setBatchCode(batchCode);
                            stvLine.setDirection(TransactionDirection.INBOUND);
                            stvLine.setOwner(line.getOwner() == null ? stv.getOwner() : line.getOwner());
                            stvLine.setSku(line.getSku());
                            stvLine.setInBoundTime(new Date());
                            stvLine.setStaLine(line);
                            stvLine.setSkuCost(line.getSkuCost());
                            stvLine.setStv(stv);
                            stvLine.setTransactionType(tranType);
                            stvLine.setVersion(0);
                            stvLine.setWarehouse(stv.getWarehouse());
                            // 获取库存状态
                            if (line.getInvStatus() == null && StringUtil.isEmpty(pdaLine.getInvStatus())) {
                                stvLine.setInvStatus(lp);
                            } else if (line.getInvStatus() == null && !StringUtil.isEmpty(pdaLine.getInvStatus())) {
                                stvLine.setInvStatus(inventoryStatusDao.findByName(pdaLine.getInvStatus(), stv.getWarehouse().getId()));
                            } else {
                                stvLine.setInvStatus(line.getInvStatus());
                            }
                            // 收获量
                            stvLine.setReceiptQty(receiptQty);
                            // 设置剩余计划量
                            stvLine.setRemainPlanQty(remainPlanQty);
                            // 设置差异量
                            stvLine.setDifferenceQty(receiptQty - remainPlanQty);
                            // 预算核对量(核对时 可修改)
                            stvLine.setQuantity(receiptQty > remainPlanQty ? remainPlanQty : receiptQty);
                            stvLineDao.save(stvLine);
                            stvLineMap.put(key, stvLine);
                            stvLineList.add(stvLine);
                            residueLine = stvLine;
                        }
                        if (residueQty == 0) break;
                    }
                }
                if (!isSuccess) {
                    log.debug("-----------SKU Ont find!" + pdaLine.getSkuCode());
                    pdaOrder.setMemo("INBOUND:SKU Ont find!" + pdaLine.getQty());
                    pdaOrder.setStatus(DefaultStatus.CANCELED);
                    throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
                }
                residueQty += tempQty != null ? tempQty : 0L;
                if (residueQty != 0) {
                    skuQty += residueQty;
                    residueLine.setReceiptQty(residueLine.getReceiptQty() + residueQty);
                    residueLine.setDifferenceQty(residueLine.getReceiptQty() - residueLine.getRemainPlanQty());
                    residueLine.setQuantity(residueLine.getReceiptQty() > residueLine.getRemainPlanQty() ? residueLine.getRemainPlanQty() : residueLine.getReceiptQty());
                }
            } else {
                log.debug("INBOUND:---SKU Ont find!" + pdaLine.getSkuCode());
                pdaOrder.setMemo("INBOUND:SKU Ont find!" + pdaLine.getQty());
                pdaOrder.setStatus(DefaultStatus.CANCELED);
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
            }
        }
        if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            for (StvLine l : stvLineList) {
                if (!l.getDifferenceQty().equals(0L)) {
                    pdaOrder.setMemo("in bound qty != remain plan qty");
                    pdaOrder.setStatus(DefaultStatus.CANCELED);
                    throw new BusinessException(ErrorCode.DIFFERENCE_NUMBER_ERROR);
                }
            }
        }
        stv.setSkuQty(skuQty);
        stvDao.save(stv);
        return stv;
    }

    /**
     * 上架单上架
     */
    public void inboundSheleves(StockTransApplication sta, StockTransVoucher stv, PdaOrder pdaOrder, List<PdaOrderLine> pdaLineList, User creator) {
        // 创建上架单
        if (sta == null || stv == null) {
            throw new BusinessException(ErrorCode.PDA_ORDER_NOTEXIST_OR_ERROR);
        }
        if (!stv.getType().equals(StockTransVoucherType.INBOUND_ORDER)) {
            throw new BusinessException(ErrorCode.PDA_NOT_FIND_STV, new Object[] {sta.getCode()});
        }
        if (!stv.getStatus().equals(StockTransVoucherStatus.CHECK)) {
            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR, new Object[] {stv.getCode()});
        }
        StockTransVoucher sheleves = null;
        if ((pdaLineList == null || pdaLineList.size() == 0)) {
            if (!PdaOrderType.INBOUND_SHELEVES.equals(pdaOrder.getType())) {
                throw new BusinessException(ErrorCode.IN_BOUND_SHELVES, new Object[] {stv.getCode()});
            }
            if (!(pdaOrder.getStatus().equals(DefaultStatus.CANCELED) || pdaOrder.getStatus().equals(DefaultStatus.CREATED) || pdaOrder.getStatus().equals(DefaultStatus.EXECUTING))) {
                throw new BusinessException(ErrorCode.PDA_POST_LOG_STATUS_ERROR);

            }
            if (pdaOrder != null) {
                pdaLineList = pdaOrderLineDao.findLineByCreate(pdaOrder.getId());
            }
            if (pdaLineList == null || pdaLineList.size() == 0) {
                throw new BusinessException(ErrorCode.PDA_NOT_FUND_DATA);
            }
        }

        sheleves = createInboundSheleves(sta, stv, pdaLineList, creator);
        stvDao.flush();
        // 判断当前上架单商品数量是否与计划量相同
        // List<StvLineCommand> list = stvLineDao.findConfirmShelevesError(stv.getId(),
        // sheleves.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        // if (list.size() > 0) {
        // log.error("SKU qty Error!!!!!");
        // pdaOrder.setMemo("in bound qty != added qty");
        // pdaOrder.setStatus(DefaultStatus.CANCELED);
        // throw new BusinessException(ErrorCode.PREDEFINED_OUT_BY_SKU_NUB_ERROR);
        // }
        // 执行上架单
        whExecute.executeInBoundShelves(sheleves, stv, creator, false);
        for (PdaOrderLine l : pdaLineList) {
            pdaOrderLineDao.updatePdaOrderLineStatus(l.getId(), DefaultStatus.FINISHED.getValue());
        }
        pdaOrderDao.flush();
        pdaOrderDao.updatePdaStatusByLine(sta.getCode(), PdaOrderType.INBOUND_SHELEVES.getValue());
    }

    public StockTransVoucher createInboundSheleves(StockTransApplication sta, StockTransVoucher stv, List<PdaOrderLine> pdaLineList, User creator) {
        if (stv == null) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        if (!StockTransVoucherStatus.CHECK.equals(stv.getStatus()) || !StockTransVoucherType.INBOUND_ORDER.equals(stv.getType())) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }

        StockTransVoucher shelvesStv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        shelvesStv.setBusinessSeqNo(biSeqNo.longValue());
        shelvesStv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        shelvesStv.setMode(InboundStoreMode.TOGETHER);
        shelvesStv.setDirection(TransactionDirection.INBOUND);
        if (StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            shelvesStv.setOwner(sta.getAddiOwner());
        } else {
            shelvesStv.setOwner(sta.getOwner());
        }
        shelvesStv.setCreator(creator);
        shelvesStv.setSta(sta);
        shelvesStv.setLastModifyTime(new Date());
        shelvesStv.setStatus(StockTransVoucherStatus.CREATED);
        shelvesStv.setType(StockTransVoucherType.INBOUND_SHELVES);
        shelvesStv.setCreateTime(new Date());
        shelvesStv.setTransactionType(tranType);
        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            shelvesStv.setWarehouse(sta.getAddiWarehouse());
        } else {
            shelvesStv.setWarehouse(sta.getMainWarehouse());
        }
        shelvesStv = stvDao.save(shelvesStv);
        stvDao.flush();
        // 根据每个skuCode的商品列表
        Map<String, List<StvLine>> map = new HashMap<String, List<StvLine>>();
        // 收货单的商品
        List<StvLine> stvLineList = stvLineDao.findStvLineListByStvId(stv.getId());

        for (StvLine stvLine : stvLineList) {
            // PDA 过来的SKU数据里面可能商品编码 默认应是条码
            // 但是这边没有考虑到多条码的数据
            String key1 = stvLine.getSku().getCode();

            if (map.containsKey(key1)) {
                map.get(key1).add(stvLine);
            } else {
                List<StvLine> list = new ArrayList<StvLine>();
                list.add(stvLine);
                map.put(key1, list);
            }
        }
        // PDA上架信息
        Map<String, StvLine> stvLineMap = new HashMap<String, StvLine>();
        // 库位
        Map<String, WarehouseLocation> locMap = new HashMap<String, WarehouseLocation>();
        Map<Long, Long> lineQty = new HashMap<Long, Long>();
        Long skuQty = 0L;
        for (PdaOrderLine pdaLine : pdaLineList) {
            boolean isSuccess = false;
            Sku sku = skuDao.getByCode(pdaLine.getSkuCode());
            // 保质期商品不支持PDA入库
            if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_SHELF_MANAGEMENT, new Object[] {sku.getBarCode()});
            }
            WarehouseLocation location = locMap.get(pdaLine.getLocationCode());
            // 获取商品信息
            if (location == null) {
                location = locDao.findByLocationCode(pdaLine.getLocationCode(), shelvesStv.getWarehouse().getId());
                if (location == null) {
                    log.error("Location Ont find!" + pdaLine.getLocationCode());
                    throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_FOUND);
                }
            }
            // 判断库位是否是暂存区商品 （一般上架是不允许）
            if (location.getDistrict().getType().equals(WarehouseDistrictType.RECEIVING)) {
                log.error("District type : RECEIVING! error!" + pdaLine.getLocationCode());
                throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_FOUND);
            }
            // 数量是否合格
            if (pdaLine.getQty() == null || pdaLine.getQty() < 1) {
                log.error("Qty Error!" + pdaLine.getQty());
                throw new BusinessException(ErrorCode.PDA_PLAN_QTY_LQ_ACTUAL);
            }

            List<StvLine> tempLine = map.get(pdaLine.getSkuCode());
            if (tempLine != null) {
                // 次行收货量
                Long residueQty = pdaLine.getQty();
                // SN号
                List<PdaOrderLineSn> snsList = pdaOrderLineSnDao.getByPdaOrderLine(pdaLine.getId());
                int SNindex = 0;
                for (StvLine line : tempLine) {
                    if (StringUtil.isEmpty(pdaLine.getInvStatus()) || pdaLine.getInvStatus().equals(line.getInvStatus().getName())) {
                        // 获取对应StaLine 已经上架商品数量
                        Long qty = lineQty.containsKey(line.getId()) ? lineQty.get(line.getId()) : 0;
                        // 剩余计划量
                        Long remainPlanQty = line.getQuantity() - (line.getAddedQty() == null ? 0 : line.getAddedQty());
                        // 判断当前StaLine 是否已经有对应的stvLine 而且 计划量已经等于执行量
                        if (remainPlanQty.equals(0L) || qty.equals(remainPlanQty)) {
                            /**
                             * remainPlanQty.equals(0) 是调整单有可能调整为0的商品 ，在车边不做上架
                             * qty.equals(remainPlanQty) 已上架商品
                             */
                            continue;
                        }
                        isSuccess = true;
                        /**
                         * 计算出此次Line 能收多少商品 (remainPlanQty - qty) 是最大限度能收多少，（receiptQty） 实际收货量.
                         */
                        Long receiptQty = residueQty < (remainPlanQty - qty) ? residueQty : (remainPlanQty - qty);
                        // 上架总量
                        skuQty += receiptQty;
                        // 计算出剩余上架量
                        residueQty -= receiptQty;
                        // 保存已上架量
                        lineQty.put(line.getId(), qty + receiptQty);
                        // 合并数量的Key值
                        String key = line.getStaLine().getId() + "_" + line.getSku().getId() + "_" + line.getInvStatus().getId() + "_" + line.getOwner() + "_" + location.getId();
                        // SN记录
                        StringBuffer sns = new StringBuffer();
                        // 判断是否是SN商品
                        if (sku.getIsSnSku() != null && sku.getIsSnSku()) {
                            if ((SNindex + receiptQty) <= snsList.size()) {
                                int SNindexTemp = SNindex;
                                for (; SNindex < (SNindexTemp + receiptQty); SNindex++) {
                                    sns.append(snsList.get(SNindex).getSnCode());
                                    sns.append(",");
                                }
                                sns = new StringBuffer(sns.substring(0, sns.length() - 1));
                            } else {
                                log.error("-----------SKU SN Not find!" + pdaLine.getSkuCode());
                                throw new BusinessException(ErrorCode.PDA_INBOUND_SKU_SN_QTY_ERROR, new Object[] {pdaLine.getSkuCode()});
                            }
                        }
                        // 判断是否存在同库位同商品同状态同店铺的line信息 如果存在就做合并
                        if (stvLineMap.containsKey(key)) {
                            StvLine temp = stvLineMap.get(key);
                            temp.setQuantity(temp.getQuantity() + receiptQty);
                            if (sku.getIsSnSku() != null && sku.getIsSnSku()) {
                                temp.setSns(temp.getSns() + "," + sns.toString());
                            }
                        } else {
                            StvLine stvLine;
                            try {
                                stvLine = line.clone();
                            } catch (CloneNotSupportedException e) {
                                throw new BusinessException(ErrorCode.PDA_IN_BOUND_ERROR, new Object[] {sta.getCode()});
                            }
                            stvLine.setStv(shelvesStv);
                            stvLine.setInBoundTime(new Date());
                            stvLine.setOwner(shelvesStv.getOwner());
                            stvLine.setWarehouse(shelvesStv.getWarehouse());
                            stvLine.setLocation(location);
                            stvLine.setDistrict(location.getDistrict());
                            stvLine.setQuantity(receiptQty);
                            stvLine.setSns(sns.toString());
                            stvLineDao.save(stvLine);
                            stvLineMap.put(key, stvLine);
                        }
                        if (residueQty == 0) break;
                    }
                }
                if (!isSuccess) {
                    log.error("-----------SKU Ont find!" + pdaLine.getSkuCode());
                    throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
                }
                // 如果数量不等于零 就证明上架量 大于 收货量
                if (residueQty != 0) {
                    log.error("-----------SKU Qty!" + pdaLine.getSkuCode());
                    throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND, new Object[] {sta.getCode()});
                }
            } else {
                log.error("SKU Ont find!" + pdaLine.getSkuCode());
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
            }
        }
        shelvesStv.setSkuQty(skuQty);
        stvDao.save(shelvesStv);
        return shelvesStv;
    }

    @Override
    public String checkSkuSn(String id) {
        PdaOrderLine p = pdaOrderLineDao.getByPrimaryKey(Long.parseLong(id));
        Sku s = skuDao.getByCode(p.getSkuCode());
        String result = "ERROR";
        if (s.getIsSnSku()) {
            result = "OK";
        }
        return result;
    }


    @Override
    public List<PdaOrderLineSn> findPdaLineSnList(String id) {
        List<PdaOrderLineSn> snList = pdaOrderLineSnDao.findPdaLineSnListById(Long.parseLong(id), new BeanPropertyRowMapper<PdaOrderLineSn>(PdaOrderLineSn.class));
        return snList;
    }

    @Override
    public void editPdaOrderSkuSn(String id, String sn) {
        PdaOrderLineSn p = pdaOrderLineSnDao.getByPrimaryKey(Long.parseLong(id));
        p.setSnCode(sn);
        pdaOrderLineSnDao.save(p);
    }

    @Override
    public void deletePdaOrderSkuSn(String id) {
        pdaOrderLineSnDao.deleteByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public void addPdaOrderSkuSn(String id, String sn) {
        PdaOrderLine p = pdaOrderLineDao.getByPrimaryKey(Long.parseLong(id));
        PdaOrderLineSn s = new PdaOrderLineSn();
        s.setSnCode(sn);
        s.setPdaOrderLine(p);
        pdaOrderLineSnDao.save(s);

    }

    @Override
    public List<StockTransApplication> findStaBySlipCode(String silpCode) {
        return staDao.findBySlipCodeStatus(silpCode);
    }
}
