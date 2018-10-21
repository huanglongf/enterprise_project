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
 */
package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.InventoryLockType;
import com.jumbo.wms.model.warehouse.InventoryOccupyCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("inventoryOccupyManager")
public class InventoryOccupyManagerImpl extends BaseManagerImpl implements InventoryOccupyManager {

    private static final long serialVersionUID = 7041154334645202747L;
    private EventObserver eventObserver;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StockTransTxLogDao stLogDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private HubWmsService hubWmsService;

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private StockTransApplication createInventoryLockSta(StockTransVoucher stvr, String slipCode, Integer lockType, String memo, List<StvLine> list, Long userId, Long ouid) {
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.INVENTORY_LOCK_LINE_EMPTY);
        }
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_LOCK);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_LOCK_NOT_FOUND);
        }
        String channelCode = list.get(0).getOwner();
        BiChannel channel = biChannelDao.getByCode(channelCode);
        if (null == channel) {
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        User user = userId == null ? null : userDao.getByPrimaryKey(userId);
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouid);
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        if (slipCode != null) sta.setRefSlipCode(slipCode);
        sta.setCreator(user);
        sta.setIsNeedOccupied(false);
        sta.setMainWarehouse(ou);
        sta.setMemo(memo);
        sta.setIsLocked(true);
        sta.setLockType(InventoryLockType.valueOf(lockType));
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setType(StockTransApplicationType.INVENTORY_LOCK);
        sta.setOwner(channelCode);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        StockTransVoucher stv = new StockTransVoucher();
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        String stvCode = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        stv.setCode(stvCode);
        stv.setCreateTime(new Date());
        stv.setLastModifyTime(new Date());
        stv.setCreator(user);
        stv.setDirection(TransactionDirection.OUTBOUND);
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setTransactionType(type);
        stv.setWarehouse(ou);
        Warehouse wh = warehouseDao.getByOuId(ouid);
        Long customerId = null;
        if (wh != null && wh.getCustomer() != null) {
            customerId = wh.getCustomer().getId();
        }
        // 合并重复明细行
        List<StvLine> lineList = new ArrayList<StvLine>();
        for (StvLine stvl : list) {
            Sku sku = skuDao.getByPrimaryKey(stvl.getSku().getId());
            if (sku == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {stvl.getSku().getBarCode()});
            } else {
                if (!sku.getCustomer().getId().equals(customerId)) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {sku.getBarCode()});
                }
            }
            WarehouseLocation loc = warehouseLocationDao.getByPrimaryKey(stvl.getLocation().getId());
            if (loc == null) {
                throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND);
            }
            InventoryStatus sts = inventoryStatusDao.getByPrimaryKey(stvl.getInvStatus().getId());
            if (sts == null) {
                throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
            }
            if (!StringUtils.hasText(stvl.getOwner())) {
                throw new BusinessException(ErrorCode.OWNER_IS_NULL);
            }
            stvl.setLocation(loc);
            stvl.setSku(sku);
            stvl.setInvStatus(sts);
            stvl.setDistrict(stvl.getLocation().getDistrict());
            stvl.setId(null);
            stvl.setDirection(TransactionDirection.OUTBOUND);
            stvl.setStv(stv);
            stvl.setTransactionType(type);
            stvl.setWarehouse(ou);
            boolean exist = false;
            for (StvLine l : lineList) {
                if (stvl.getSku().getId().equals(l.getSku().getId()) && stvl.getLocation().getId().equals(l.getLocation().getId()) && stvl.getInvStatus().getId().equals(l.getInvStatus().getId())) {
                    if ((stvl.getOwner() == null && l.getOwner() == null) || (stvl.getOwner() != null && stvl.getOwner().equals(l.getOwner()))) {
                        l.setQuantity(stvl.getQuantity() + l.getQuantity());
                        exist = true;
                    }
                }
            }
            if (!exist) {
                lineList.add(stvl);
            }
        }
        Map<String, StaLine> mapStaLine = new HashMap<String, StaLine>();
        for (StvLine line : lineList) {
            String key = line.getOwner() + "_" + line.getSku().getId() + "_" + line.getInvStatus().getId();
            StaLine stal = null;
            if (mapStaLine.containsKey(key)) {
                stal = mapStaLine.get(key);
                stal.setQuantity(stal.getQuantity() + line.getQuantity());
                if (null != stal) {
                    line.setStaLine(stal);
                }
            } else {
                stal = new StaLine();
                stal.setInvStatus(line.getInvStatus());
                stal.setOwner(line.getOwner());
                stal.setQuantity(line.getQuantity());
                stal.setSku(line.getSku());
                stal.setSta(sta);
                line.setStaLine(stal);
                mapStaLine.put(key, stal);
            }
            staLineDao.save(stal);
            staLineDao.flush();
        }
        stv.setStvLines(lineList);
        stvDao.save(stv);
        stvr.setId(stv.getId());
        staDao.updateSkuQtyById(sta.getId());
        staDao.flush();
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        return sta;
    }

    /**
     * 库存锁定：创库存锁定作业单，占用库存并生产增量库存
     */
    @Override
    public StockTransApplication inventoryLock(String slipCode, Integer lockType, String memo, List<StvLine> list, Long userId, Long ouid) {
        // 创库存锁定作业单
        StockTransApplication sta = null;
        StockTransVoucher stv = new StockTransVoucher();
        sta = createInventoryLockSta(stv, slipCode, lockType, memo, list, userId, ouid);
        // 库存占用逻辑
        if ((null == sta || null == sta.getId()) || (null == stv.getId())) {
            throw new BusinessException(ErrorCode.STA_CREATE_ERROR);
        }
        inventoryOccupyBySta(sta.getId(), userId, null, stv.getId(), true);
        // 同步增量库存
        stLogDao.insertIncrementInv(sta.getId());
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        StockTransApplicationCommand stac = new StockTransApplicationCommand();
        stac.setId(sta.getId());
        stac.setCode(sta.getCode());
        return stac;
    }

    /**
     * 查询库存锁定作业单列表
     */
    @Override
    public Pagination<StockTransApplicationCommand> findInventoryLockStaByPage(int start, int pageSize, StockTransApplicationCommand staCmd, Integer lockType, Long ouId, Sort[] sort) {
        String code = null;
        String creater = null;
        if (StringUtils.hasText(staCmd.getCode())) {
            code = staCmd.getCode() + "%";
        }
        if (StringUtils.hasText(staCmd.getCreater())) {
            creater = "%" + staCmd.getCreater() + "%";
        }
        return staDao.findInventoryLockStaByPage(start, pageSize, staCmd.getCreateTime(), staCmd.getFinishTime(), code, creater, lockType, ouId, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class), sort);
    }

    /**
     * 根据staId查询作业单占用明细
     */
    @Override
    public List<StaLineCommand> findOccupiedStaLineByStaId(Long staId) {
        return staLineDao.findOccupiedStaLineByStaId(staId, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
    }

    /**
     * 库存解锁
     */
    @Override
    public void inventoryUnlock(StockTransApplicationCommand staCmd, Long userId) {
        Long staId = staCmd.getId();
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (null == sta) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (StockTransApplicationStatus.OCCUPIED != sta.getStatus()) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
        }
        if (StockTransApplicationType.INVENTORY_LOCK != sta.getType()) {
            throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
        }
        if (true != sta.getIsLocked()) {
            throw new BusinessException(ErrorCode.ERROR_STA_NOT_LOCK);
        }
        // 更新单据状态
        Date date = new Date();
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        sta.setFinishTime(date);
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        for (StockTransVoucher stv : stvs) {
            StockTransVoucher v = stvDao.getByPrimaryKey(stv.getId());
            v.setStatus(StockTransVoucherStatus.FINISHED);
            v.setFinishTime(date);
            stvDao.save(v);
        }
        staDao.save(sta);
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 同步增量库存
        stLogDao.insertIncrementInv2(sta.getId());
        // 释放库存
        staDao.updateReleaseInventoryByStaId(staId);
        hubWmsService.insertOccupiedAndRelease(staId);
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 通用库存占用逻辑
     */
    @Override
    public void inventoryOccupyCommon(StockTransApplication sta, String transactionCode, Boolean isStv) {
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            throw new BusinessException(ErrorCode.ERROR_STA_ISMERGE, new Object[] {sta.getCode()});
        }
        if (!StringUtil.isEmpty(transactionCode)) {
            TransactionType type = transactionTypeDao.findByCode(transactionCode.trim());
            if (type == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
        }
        if (null == isStv) isStv = false;
        // 根据作业单查询库存（关联库存状态，含销售与非销售库存）
        List<InventoryOccupyCommand> list = null;
        if (true == isStv) {
            list = inventoryDao.findForOccupyInventoryCommon(transactionCode, sta.getId(), WarehouseDistrictType.RECEIVING.getValue(), new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        } else {
            list = inventoryDao.findForOccupyInventory(transactionCode, sta.getId(), WarehouseDistrictType.RECEIVING.getValue(), new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        }
        Long skuId = null;
        Long tqty = null;
        Long statusId = null;
        Date expireDate = null;
        Long locationId = null;
        // 占用库存,按商品明细行的SKU与OWNER确认需要占用的库存
        for (InventoryOccupyCommand cmd : list) {
            // 商品是排序的，如果商品不同则说明之前商品已经占用完成
            // 切换商品，初始化待占用数
            Boolean isLocationIdEqual = ((true == isStv) && (null != locationId) ? locationId.equals(cmd.getLocationId()) : true);
            if (skuId == null || !(skuId.equals(cmd.getSkuId()) && ((expireDate == null && cmd.getExpDate() == null) || (expireDate != null && expireDate.equals(cmd.getExpDate()))) && statusId.equals(cmd.getStatusId()) && isLocationIdEqual)) {
                skuId = cmd.getSkuId();
                statusId = cmd.getStatusId();
                expireDate = cmd.getExpDate();
                if (true == isStv) {
                    locationId = cmd.getLocationId();
                }
                log.debug("============change sku,from {} to {}", skuId, cmd.getSkuId());
                tqty = cmd.getPlanOccupyQty() == null ? 0L : cmd.getPlanOccupyQty();
            }
            log.debug("to occupy inv, sku : {},inv qty : " + cmd.getQuantity() + ", paln qty : " + cmd.getPlanOccupyQty(), cmd.getLineKey());
            log.debug("to occupy inv,owner : {} ,inv status : {}", cmd.getOrderOwner(), cmd.getStatusId());
            // 如待占用量小于0忽略该行库存
            if (tqty.longValue() <= 0L) {
                log.debug("tqty <= 0 continue");
                continue;
            }
            if (cmd.getQuantity().longValue() > tqty.longValue()) {
                // 库存数量大于待占用量拆分库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), tqty);
                log.debug("inv qty > tqty, to occupy tqty : {}", tqty);
                // 插入新库存份
                wareHouseManager.saveInventoryForOccupy(cmd, cmd.getQuantity().longValue() - tqty.longValue(), null);
                // 重置待占用量
                tqty = 0L;
                log.debug("final tqty : {}", tqty);
            } else {
                // 库存数量小于等于待占用量,直接占用库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), cmd.getQuantity().longValue());
                log.debug("inv qty <= tqty, to occupy qty : {}", cmd.getQuantity().longValue());
                tqty = tqty - cmd.getQuantity().longValue();
                log.debug("final tqty : {}", tqty);
            }
        }
        inventoryDao.flush();
        log.debug("validate occupy");
        wareHouseManager.validateOccupy(sta.getId());
    }

    /**
      * 通用库存占用逻辑
     */
    @Override
    public void inventoryOccupyCommon(StockTransApplication sta, String transactionCode, Long stvId) {
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            throw new BusinessException(ErrorCode.ERROR_STA_ISMERGE, new Object[] {sta.getCode()});
        }
        if (!StringUtil.isEmpty(transactionCode)) {
            TransactionType type = transactionTypeDao.findByCode(transactionCode.trim());
            if (type == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
        }
        Boolean isStv = true;
        if (null == stvId) isStv = false;
        // 根据作业单查询库存（关联库存状态，含销售与非销售库存）
        List<InventoryOccupyCommand> list = null;
        if (true == isStv) {
            list = inventoryDao.findForOccupyInventoryCommon(transactionCode, sta.getId(), WarehouseDistrictType.RECEIVING.getValue(), new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        } else {
            list = inventoryDao.findForOccupyInventory(transactionCode, sta.getId(), WarehouseDistrictType.RECEIVING.getValue(), new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        }
        Long skuId = null;
        Long tqty = null;
        Long statusId = null;
        Date expireDate = null;
        Long locationId = null;
        // 占用库存,按商品明细行的SKU与OWNER确认需要占用的库存
        for (InventoryOccupyCommand cmd : list) {
            // 商品是排序的，如果商品不同则说明之前商品已经占用完成
            // 切换商品，初始化待占用数
            Boolean isLocationIdEqual = ((true == isStv) && (null != locationId) ? locationId.equals(cmd.getLocationId()) : true);
            if (skuId == null || !(skuId.equals(cmd.getSkuId()) && ((expireDate == null && cmd.getExpDate() == null) || (expireDate != null && expireDate.equals(cmd.getExpDate()))) && statusId.equals(cmd.getStatusId()) && isLocationIdEqual)) {
                skuId = cmd.getSkuId();
                statusId = cmd.getStatusId();
                expireDate = cmd.getExpDate();
                if (true == isStv) {
                    locationId = cmd.getLocationId();
                }
                if (log.isDebugEnabled()) {
                    log.debug("============change sku,from {} to {}", skuId, cmd.getSkuId());
                }
                tqty = cmd.getPlanOccupyQty() == null ? 0L : cmd.getPlanOccupyQty();
            }
            if (log.isDebugEnabled()) {
                log.debug("to occupy inv, sku : {},inv qty : " + cmd.getQuantity() + ", paln qty : " + cmd.getPlanOccupyQty(), cmd.getLineKey());
                log.debug("to occupy inv,owner : {} ,inv status : {}", cmd.getOrderOwner(), cmd.getStatusId());
            }
            // 如待占用量小于0忽略该行库存
            if (tqty.longValue() <= 0L) {
                if (log.isDebugEnabled()) {
                    log.debug("tqty <= 0 continue");
                }
                continue;
            }
            if (cmd.getQuantity().longValue() > tqty.longValue()) {
                // 库存数量大于待占用量拆分库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), tqty);
                if (log.isDebugEnabled()) {
                    log.debug("inv qty > tqty, to occupy tqty : {}", tqty);
                }
                // 插入新库存份
                wareHouseManager.saveInventoryForOccupy(cmd, cmd.getQuantity().longValue() - tqty.longValue(), null);
                // 重置待占用量
                tqty = 0L;
                if (log.isDebugEnabled()) {
                    log.debug("final tqty : {}", tqty);
                }
            } else {
                // 库存数量小于等于待占用量,直接占用库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), cmd.getQuantity().longValue());
                if (log.isDebugEnabled()) {
                    log.debug("inv qty <= tqty, to occupy qty : {}", cmd.getQuantity().longValue());
                }
                tqty = tqty - cmd.getQuantity().longValue();
                if (log.isDebugEnabled()) {
                    log.debug("final tqty : {}", tqty);
                }
            }
        }
        inventoryDao.flush();
        log.info("validate occupy");
        wareHouseManager.validateOccupy(sta.getId());
    }

    /**
     * 通用作业单库存占用逻辑
     */
    @Override
    public void inventoryOccupyBySta(Long staId, Long userId, String transactionCode) {
        // 作业单校验
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 占用库存
        inventoryOccupyCommon(sta, transactionCode, false);
        // 更新作业单状态
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        // 作业单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 创建执行单
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        BigDecimal ttid = null;
        ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
        if (ttid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
        stvLineDao.createByStaId(sta.getId());
    }

    /**
       * 通用作业单库存占用逻辑
     */
    @Override
    public void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Long stvId) {
        // 作业单校验
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 占用库存
        inventoryOccupyCommon(sta, transactionCode, stvId);// 非销售单
        // 更新作业单状态
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        // 作业单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 创建执行单
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        BigDecimal ttid = null;
        ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
        if (ttid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        if (null != stvId) {
            StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
            if (null == stv) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            }
            // 重建stvl
            stv.getStvLines().clear();
            stvDao.save(stv);
            stvDao.flush();
            stvLineDao.createTIOutByStaId(sta.getId(), stv.getId());
        } else {
            if (null == stvId) {
                stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
                stvLineDao.createByStaId(sta.getId());
            }
        }

    }

    /**
     * 通用作业单库存占用逻辑
     */
    @Override
    public void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Long stvId, Boolean isStv) {
        // 作业单校验
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 占用库存
        inventoryOccupyCommon(sta, transactionCode, isStv);// 非销售单
        // 更新作业单状态
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        // 作业单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 创建执行单
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        BigDecimal ttid = null;
        ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
        if (ttid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        if (true == isStv) {
            if (null == stvId) new BusinessException(ErrorCode.STV_NOT_FOUND);
            StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
            if (null == stv) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            }
            // 重建stvl
            stv.getStvLines().clear();
            stvDao.save(stv);
            stvDao.flush();
            stvLineDao.createTIOutByStaId(sta.getId(), stv.getId());
        } else {
            if (null == stvId) {
                stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
                stvLineDao.createByStaId(sta.getId());
            }
        }
    }

    /**
     * 通用作业单库存占用逻辑
     */
    @Override
    public void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Boolean isStv) {
        // 作业单校验
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 占用库存
        inventoryOccupyCommon(sta, transactionCode, isStv);// 非销售单
        // 更新作业单状态
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        // 作业单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 创建执行单
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        BigDecimal ttid = null;
        ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
        if (ttid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        if (true == isStv) {
            // 重建stvl
        } else {
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
            stvLineDao.createByStaId(sta.getId());
        }

    }

    /**
     * 通用作业单库存占用逻辑
     */
    @Override
    public void inventoryOccupyBySta(Long staId, Long userId, String transactionCode, Boolean isSale, Boolean isStv) {
        // 作业单校验
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 占用库存
        if (null == isSale) isSale = false;
        if (false == isSale) {
            inventoryOccupyCommon(sta, transactionCode, isStv);// 非销售单
        } else {
            wareHouseManager.occupyInventoryForSales(sta, null, false);// 销售单
        }
        // 更新作业单状态
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        sta.setLastModifyTime(new Date());
        staDao.save(sta);
        // 作业单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        // 创建执行单
        int tdType = TransactionDirection.OUTBOUND.getValue();
        String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
        BigDecimal ttid = null;
        ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
        if (ttid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        if (true == isStv) {
            // 重建stvl
        } else {
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
            stvLineDao.createByStaId(sta.getId());
        }
    }

}
