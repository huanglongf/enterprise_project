/**
 * 
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
package com.jumbo.wms.manager.pickZone;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.automaticEquipment.ZoonOcpSortDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.pickZone.WhOcpOrderDao;
import com.jumbo.dao.pickZone.WhOcpOrderExeLineDao;
import com.jumbo.dao.pickZone.WhOcpOrderLineDao;
import com.jumbo.dao.pickZone.WhPickZoneDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StaErrorLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderCommand;
import com.jumbo.wms.model.command.pickZone.WhOcpOrderLineCommand;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.pickZone.OcpInvConstants;
import com.jumbo.wms.model.pickZone.WhOcpOrder;
import com.jumbo.wms.model.pickZone.WhOcpOrderLine;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaErrorLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

/**
 * @author lihui
 * 
 *         2015年7月3日 上午10:56:39
 */
@Transactional
@Service("newInventoryOccupyManager")
public class NewInventoryOccupyManagerImpl extends BaseManagerImpl implements NewInventoryOccupyManager {
    protected static final Logger log = LoggerFactory.getLogger(NewInventoryOccupyManagerImpl.class);
    /**
     * 
     */
    private static final long serialVersionUID = -8991851173867275585L;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private WhOcpOrderDao whOcpOrderDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WhOcpOrderLineDao whOcpOrderLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WhOcpOrderExeLineDao whOcpOrderExeLineDao;
    @Autowired
    private WhPickZoneDao whPickZoneDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private StaErrorLineDao staErrorLineDao;
    @Autowired
    private ZoonOcpSortDao zoonOcpSortDao;
    @Autowired
    private ZoonDao zoonDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private SequenceManager sequenceManager;

    /**
     * 根据sku占用库存 返回实际占用库存
     */
    @Override
    public Long ocpInvBySku(Long skuId, Long qty, Long whId, String owner, String ocpCode, String sourceOcpCode, Long locationId, Long invStatusId, Boolean isForSales, Date fromExpDate, Date endExpDate, List<WarehouseDistrictType> wdtList,
            List<Long> transactionTypes, String ocpAreaCode) {
        if (log.isInfoEnabled()) {
            log.info(whId + " ocpInvBySku start.........." + skuId + "-" + qty);
        }
        Long countQty = 0L;
        if (skuId != null && qty != null && owner != null && whId != null) {

            // 封装参数
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("skuId", skuId);
            params.put("owner", owner);
            params.put("whId", whId);
            params.put("locationId", locationId);
            params.put("invStatusId", invStatusId);
            params.put("isForSales", isForSales);
            params.put("fromExpDate", fromExpDate);
            params.put("endExpDate", endExpDate);
            if (wdtList != null) {
                List<Integer> disType = new ArrayList<Integer>();
                for (WarehouseDistrictType wdt : wdtList) {
                    disType.add(wdt.getValue());
                }
                params.put("disType", disType);
            }

            // 获取现有可占用库存
            List<InventoryCommand> inventoryCommands = null;
            if (StringUtils.hasLength(ocpAreaCode)) {
                // 区域占用库存
                params.put("ocpAreaCode", ocpAreaCode);
                inventoryCommands = inventoryDao.findInventoryOccupyByOcpArea(params, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            } else {
                // 老的占用库存
                inventoryCommands = inventoryDao.findInventoryOccupyBySku(params, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            }

            Long countInv = 0L;// 计算当前库存是否充足
            // for (InventoryCommand inventoryCommand : inventoryCommands) {
            // countInv += inventoryCommand.getQuantity();
            // }
            if (inventoryCommands != null && inventoryCommands.size() > 0) {
                countInv = qty;
                // 开始占用库存
                for (InventoryCommand inventoryCommand : inventoryCommands) {
                    countInv = countInv - inventoryCommand.getQuantity();
                    if (countInv < 0) {
                        // 切割库存份
                        inventoryDao.addNewInventory(inventoryCommand.getId(), -countInv, null);

                        inventoryDao.modifyInventory(inventoryCommand.getId(), ocpCode, inventoryCommand.getQuantity() + countInv, sourceOcpCode);
                        countQty += inventoryCommand.getQuantity() + countInv;
                        break;
                    } else if (countInv == 0) {
                        inventoryDao.modifyInventory(inventoryCommand.getId(), ocpCode, null, sourceOcpCode);
                        countQty += inventoryCommand.getQuantity();
                        break;
                    } else {
                        inventoryDao.modifyInventory(inventoryCommand.getId(), ocpCode, null, sourceOcpCode);
                        countQty += inventoryCommand.getQuantity();
                    }
                }
                return countQty;
            }
        }
        if (log.isInfoEnabled()) {
            log.info(whId + " ocpInvBySku end.........." + skuId + "-" + qty);
        }
        return countQty;
    }

    /**
     * 处理异常未完成的占用批
     */
    public WhOcpOrderCommand exceptionOcpOrderDispose(Long ouId) {
        List<Integer> status = new ArrayList<Integer>();
        status.add(OcpInvConstants.OCP_ORDER_STATUS_PROCESS);
        status.add(OcpInvConstants.OCP_ORDER_STATUS_CREATED);
        // status.add(OcpInvConstants.OCP_ORDER_STATUS_EXCEPTION);
        List<WhOcpOrderCommand> woocList = findOcpOrderByParams(ouId, status);
        if (woocList != null && !woocList.isEmpty()) {
            WhOcpOrderCommand wooCommand = woocList.get(0);

            status.clear();
            status.add(StockTransApplicationStatus.OCCUPIED.getValue());
            status.add(StockTransApplicationStatus.INTRANSIT.getValue());
            status.add(StockTransApplicationStatus.PARTLY_RETURNED.getValue());
            status.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
            status.add(StockTransApplicationStatus.CANCELED.getValue());
            status.add(StockTransApplicationStatus.FROZEN.getValue());

            // 查看是否有已经取消的订单
            List<StaLineCommand> slcList = staLineDao.findStaLineByOcpCode(wooCommand.getCode(), null, status, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));

            // <SKUID,<OWNER,QTY>> 获取每个商品在每家店铺的数量
            Map<Long, Map<String, Long>> skuQty = new HashMap<Long, Map<String, Long>>();
            if (slcList != null && !slcList.isEmpty()) {
                Set<Long> staIds = new HashSet<Long>();
                for (StaLineCommand staLine : slcList) {
                    Long skuId = staLine.getSkuId();
                    staIds.add(staLine.getStaId());
                    // 获取每批次中每个SKU的总数量
                    if (skuQty.get(skuId) != null) {
                        Map<String, Long> ownerQty = skuQty.get(skuId);

                        ownerQty.put(staLine.getOwner(), staLine.getQuantity());
                    } else {
                        Map<String, Long> ownerQty = new HashMap<String, Long>();
                        ownerQty.put(staLine.getOwner(), staLine.getQuantity());
                        skuQty.put(skuId, ownerQty);
                    }
                }
                for (Long staId : staIds) {
                    stockTransApplicationDao.updateStaStatusByStaId(staId);
                    // 将此订单移除此占用批次
                    wareHouseManager.updateStaByOcpCode(wooCommand.getCode(), staId);
                }

            }

            // 释放被此批次占用的所有库存
            inventoryDao.releaseInventoryByOcpCode(wooCommand.getCode());
            // 将明细的实际占用库存量清零
            List<WhOcpOrderLineCommand> woolList = whOcpOrderLineDao.findWhOcpOrderLineByWooId(wooCommand.getId(), null, null, null, null, new BeanPropertyRowMapper<WhOcpOrderLineCommand>(WhOcpOrderLineCommand.class));
            List<Long> woolId = new ArrayList<Long>();
            for (WhOcpOrderLineCommand woolc : woolList) {
                WhOcpOrderLine wool = whOcpOrderLineDao.getByPrimaryKey(woolc.getId());
                wool.setConformQty(0);

                if (skuQty.get(woolc.getSkuId()) != null && skuQty.get(woolc.getSkuId()).get(woolc.getOwner()) != null) {
                    Long qty = skuQty.get(woolc.getSkuId()).get(woolc.getOwner());
                    // 修正预计占用量
                    wool.setQty(wool.getQty() - qty.intValue());
                }

                woolId.add(woolc.getId());
            }
            wooCommand.setWoolIds(woolId);

            return wooCommand;
        }
        return null;
    }

    /**
     * 根据占用批编码释放库存
     * 
     * @param ocpCode
     */
    public void releaseInventoryByOcpCode(String ocpCode) {
        inventoryDao.releaseInventoryByOcpCode(ocpCode);
    }


    public void releaseAllOcpCode() {
        try {
            inventoryDao.releaseStaByOcpCode();// 释放作业单占用异常单据
        } catch (Exception e) {
            log.error("releaseStaByOcpCode is error: ", e);
        }
        try {
            inventoryDao.releaseStaByOcpCode2();// 释放作业单占用异常单据
        } catch (Exception e) {
            log.error("releaseStaByOcpCode2 is error: ", e);
        }

    }


    public WhOcpOrderCommand getWhocpOrder(Long wooId) {
        WhOcpOrder woo = whOcpOrderDao.getByPrimaryKey(wooId);
        WhOcpOrderCommand cc = new WhOcpOrderCommand();
        cc.setCode(woo.getCode());
        cc.setOuId(woo.getOperationUnit().getId());
        cc.setId(woo.getId());
        return cc;
    }

    public List<Long> findOcpStaIdByOcpCod(Long ouId, String wooCode) {
        return whOcpOrderDao.findOcpStaIdByOcpCode(ouId, wooCode, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 将一定数量的订单设为一批
     * 
     * @param ocpBatchCode
     * @param ocpCode
     * @param ouId
     */
    public List<Long> setOcpCodeForSta(String ocpBatchCode, String ocpCode, Long ouId, Integer amount, String saleModle, String isYs, String areaCode) {
        // 判断仓库是否为自动配货
        Warehouse wh = warehouseDao.getByOuId(ouId);
        Boolean autoOcp = null;
        Integer ocpErrorQty = 5;
        if (wh != null) {
            if (wh.getIsAutoOcp() == null || !wh.getIsAutoOcp()) {
                autoOcp = true;
            }
            if (wh.getOcpErrorLimit() != null) {
                ocpErrorQty = wh.getOcpErrorLimit();
            }
        }
        if ("预售".equals(saleModle)) {
            isYs = "预售";
        }
        return wareHouseManager.ocpStaByOcpCode(ouId, autoOcp, amount, ocpErrorQty, saleModle, isYs, areaCode);
    }

    public void updateStaOcpByIdList(String ocpCode, List<Long> List) {
        Integer count = stockTransApplicationDao.updateStaOcpCodeById(ocpCode, List);
        if (log.isInfoEnabled()) {
            log.info(ocpCode + "createOcpOrder start.........." + count);
        }
    }

    /**
     * 根据sta创建占用批
     * 
     * @param stacList
     * @param ouId
     * @return
     * @throws Exception
     */
    public WhOcpOrderCommand createOcpOrder(String ocpCode, Long ouId, Boolean isNew) {
        if (log.isInfoEnabled()) {
            log.info(ouId + "createOcpOrder start.........." + ocpCode);
        }
        // 获取占用批明细
        List<StaLineCommand> slcList = staLineDao.findStaLineByOcpCode(ocpCode, null, null, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        if (slcList != null) {
            OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
            // 创建占用批
            WhOcpOrder woo = new WhOcpOrder();
            woo.setCode(ocpCode);
            woo.setLastModifyTime(new Date());
            woo.setStatus(OcpInvConstants.OCP_ORDER_STATUS_CREATED);
            woo.setCreateTime(new Date());
            woo.setOperationUnit(ou);
            whOcpOrderDao.save(woo);
            whOcpOrderDao.flush();
            List<Long> woolId = new ArrayList<Long>();
            if (!isNew) {

                // --------------------老逻辑-----------------------
                // <SKUID,<OWNER,QTY>> 获取每个商品在每家店铺的数量
                Map<Long, Map<String, Long>> skuQty = new HashMap<Long, Map<String, Long>>();
                for (StaLineCommand staLine : slcList) {
                    Long skuId = staLine.getSkuId();
                    // 获取每批次中每个SKU的总数量
                    if (skuQty.get(skuId) != null) {
                        Map<String, Long> ownerQty = skuQty.get(skuId);
                        if (ownerQty.get(staLine.getOwner()) != null) {
                            ownerQty.put(staLine.getOwner(), ownerQty.get(staLine.getOwner()) + staLine.getQuantity());
                        } else {
                            ownerQty.put(staLine.getOwner(), staLine.getQuantity());
                        }
                    } else {
                        Map<String, Long> ownerQty = new HashMap<String, Long>();
                        ownerQty.put(staLine.getOwner(), staLine.getQuantity());
                        skuQty.put(skuId, ownerQty);
                    }
                }
                // 创建占用批明细
                for (Long skuId : skuQty.keySet()) {
                    Sku sku = skuDao.getByPrimaryKey(skuId);
                    Map<String, Long> ownerQty = skuQty.get(skuId);
                    for (String owner : ownerQty.keySet()) {
                        int qty = ownerQty.get(owner).intValue();
                        WhOcpOrderLine wool = new WhOcpOrderLine();
                        wool.setSku(sku);
                        wool.setOwner(owner);
                        wool.setQty(qty);
                        wool.setWhOcpOrder(woo);
                        whOcpOrderLineDao.save(wool);
                        whOcpOrderLineDao.flush();
                        woolId.add(wool.getId());
                    }
                }
            } else {
                // ------------------区域占用新逻辑-----------------
                // 最终map格式<br>
                // SKU<br>
                // --店铺<br>
                // ----区域 -数量<br>
                Map<String, Integer> skuQtyNew = new HashMap<String, Integer>();// 商品集合
                // Map<Long, Map<String, Map<String, Integer>>> skuQty = new HashMap<Long,
                // Map<String, Map<String, Integer>>>(); // 商品集合
                // Map<String, Map<String, Integer>> ownerQty = null;// 店铺的库存数量
                // Map<String, Integer> ocpQty = null; // 区域占用库存的数量
                for (StaLineCommand staLine : slcList) {
                    Long skuId = staLine.getSkuId();
                    if (staLine.getOcpAreaMemo() == null) {
                        staLine.setOcpAreaMemo("");
                    }
                    String ocpArray[] = staLine.getOcpAreaMemo().split(";");
                    String lineOcpCode = "";
                    for (int i = 0; i < ocpArray.length; i++) {
                        // ownerQty = new HashMap<String, Map<String, Integer>>();
                        // ocpQty = new HashMap<String, Integer>();
                        String memo = ocpArray[i];
                        lineOcpCode = memo.substring(0, memo.indexOf(","));
                        String qty = memo.substring(memo.indexOf(",") + 1, memo.length());
                        String skuQtyKey = skuId + "⊥" + staLine.getOwner() + "⊥" + lineOcpCode;
                        if (skuQtyNew.get(skuQtyKey) != null) {
                            // 相同SKUID+OWNER+区域 累加数量
                            Integer totalQty = skuQtyNew.get(skuQtyKey) + Integer.parseInt(qty);
                            skuQtyNew.put(skuQtyKey, totalQty);
                        } else {
                            // 相同SKUID+OWNER+区域 放入Map
                            skuQtyNew.put(skuQtyKey, Integer.parseInt(qty));
                        }
                        // // 获取每批次中每个SKU的总数量
                        // if (skuQty.get(skuId) != null) {
                        // Map<String, Map<String, Integer>> ownerQtyMap = skuQty.get(skuId);
                        // if (ownerQtyMap.get(staLine.getOwner()) != null) {
                        // if (ocpQty.get(lineOcpCode) != null) {
                        // ocpQty.put(lineOcpCode, ocpQty.get(lineOcpCode) + Integer.parseInt(qty));
                        // } else {
                        // ocpQty.put(lineOcpCode, Integer.parseInt(qty));
                        // }
                        // } else {
                        // ocpQty.put(lineOcpCode, Integer.parseInt(qty));
                        // ownerQty.put(staLine.getOwner(), ocpQty);
                        // }
                        // } else {
                        // ocpQty.put(lineOcpCode, Integer.parseInt(qty));
                        // ownerQty.put(staLine.getOwner(), ocpQty);
                        // skuQty.put(skuId, ownerQty);
                        // }
                    }
                }
                // 创建占用批明细
                for (String skuQtyNewKey : skuQtyNew.keySet()) {
                    Long sid = Long.parseLong(skuQtyNewKey.split("⊥")[0]);// SKUID
                    String owner = skuQtyNewKey.split("⊥")[1];// 店铺
                    String oCode = skuQtyNewKey.split("⊥")[2];// 区域
                    Integer qty = skuQtyNew.get(skuQtyNewKey);
                    Sku sku = skuDao.getByPrimaryKey(sid);
                    WhOcpOrderLine wool = new WhOcpOrderLine();
                    wool.setSku(sku);
                    wool.setOwner(owner);
                    wool.setQty(qty);
                    wool.setOcpCode(oCode);
                    wool.setWhOcpOrder(woo);
                    whOcpOrderLineDao.save(wool);
                    whOcpOrderLineDao.flush();
                    woolId.add(wool.getId());
                }
                // for (Long skuId : skuQty.keySet()) {
                // Sku sku = skuDao.getByPrimaryKey(skuId);
                // Map<String, Map<String, Integer>> ownerQtyMap = skuQty.get(skuId);
                // for (String owner : ownerQtyMap.keySet()) {
                // Map<String, Integer> ocpQtyMap = ownerQtyMap.get(owner);
                // for (String lineOcpCode : ocpQtyMap.keySet()) {
                // Integer qty = ocpQtyMap.get(lineOcpCode);
                // WhOcpOrderLine wool = new WhOcpOrderLine();
                // wool.setSku(sku);
                // wool.setOwner(owner);
                // wool.setQty(qty);
                // wool.setOcpCode(lineOcpCode);
                // wool.setWhOcpOrder(woo);
                // whOcpOrderLineDao.save(wool);
                // whOcpOrderLineDao.flush();
                // woolId.add(wool.getId());
                // }
                // }
                // }
            }
            // 根据占用批编码设置ocpid
            stockTransApplicationDao.updateStaOcpIdByOcpCode(ocpCode, woo.getId());
            WhOcpOrderCommand wooc = new WhOcpOrderCommand();
            wooc.setCode(ocpCode);
            wooc.setId(woo.getId());
            wooc.setWoolIds(woolId);
            if (log.isInfoEnabled()) {
                log.info(ouId + "createOcpOrder start.........." + ocpCode);
            }
            return wooc;
        } else {
            log.error(ouId + "createOcpOrder null.........." + ocpCode);
            return null;
        }

    }

    /**
     * 修改占用批状态
     * 
     * @param wooId
     * @param status
     */
    public void updateWhOcpOrderStatus(Long wooId, Integer status) {
        WhOcpOrder woo = whOcpOrderDao.getByPrimaryKey(wooId);
        woo.setLastModifyTime(new Date());
        woo.setStatus(status);
    }

    /**
     * 修改作业单是否需要占用
     */
    public void updateStaIsNeedOcpByOcpId(Long isNeed, Long ocpId) {
        stockTransApplicationDao.updateStaIsNeedOcpByOcpId(isNeed, ocpId);
    }

    public void updateStaIsNeedOcpByOcpCode(Long isNeed, String code) {
        stockTransApplicationDao.updateStaIsNeedOcpByOcpCode(isNeed, code);
    }

    /**
     * 根据占用批明细ID更新库存实际占有量
     * 
     * @param woolId
     * @param conformQty
     */
    public void updateWoolConformQtyById(Long woolId, Long conformQty) {
        WhOcpOrderLine wool = whOcpOrderLineDao.getByPrimaryKey(woolId);
        wool.setConformQty(conformQty.intValue());
    }

    /**
     * 根据占用批ID获取
     * 
     * @param wooId
     * @param qtyCheck
     * @return
     */
    public List<WhOcpOrderLineCommand> findWhOcpOrderLineByWooId(Long wooId, Long skuId, String qtyCheck) {
        return whOcpOrderLineDao.findWhOcpOrderLineByWooId(wooId, skuId, qtyCheck, null, null, new BeanPropertyRowMapper<WhOcpOrderLineCommand>(WhOcpOrderLineCommand.class));

    }

    /**
     * 找出占用失败的作业单
     * 
     * @param skuStaMap
     */
    public void staQtyCheck(Long wooId) {
        // 获取获取库存占用异常的占用批明细
        List<WhOcpOrderLineCommand> woolcList = findWhOcpOrderLineByWooId(wooId, null, "true");
        if (woolcList != null && !woolcList.isEmpty()) {
            String ocpCode = woolcList.get(0).getOcpCode();
            List<StaLineCommand> slcList = staLineDao.findStaLineByOcpCode(null, wooId, null, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));

            // 根据订单获取每个店铺每个商品的数量
            Map<String, Map<Long, Long>> ownerSkuQty = new HashMap<String, Map<Long, Long>>();
            Map<Long, List<StaLineCommand>> staStalines = new HashMap<Long, List<StaLineCommand>>();
            getOwnerStaSkuQty(slcList, ownerSkuQty, staStalines);


            Set<Long> failSta = new HashSet<Long>();
            Map<Long, Map<Long, Long>> lackMap = new HashMap<Long, Map<Long, Long>>();

            for (WhOcpOrderLineCommand woolc : woolcList) {
                Map<Long, Long> staSkuQty = (Map<Long, Long>) ownerSkuQty.get(woolc.getOwner() + woolc.getSkuId());

                // 获取所以的订单ID并排序
                Long[] key = new Long[staSkuQty.size()];
                key = staSkuQty.keySet().toArray(key);
                Arrays.sort(key);

                Integer conformQty = woolc.getConformQty();
                if (conformQty == null) {
                    conformQty = 0;
                }
                // 计算差异
                Long difference = (long) (woolc.getQty() - conformQty);
                if (difference != 0L) {
                    // 找出失败的订单
                    for (int i = key.length - 1; i >= 0; i--) {
                        Long staId = key[i];
                        Map<Long, Long> skuLack = lackMap.get(staId);
                        if (skuLack == null) {
                            skuLack = new HashMap<Long, Long>();
                            lackMap.put(staId, skuLack);
                        }


                        Long qty = staSkuQty.get(staId);
                        if ((difference - qty) > 0) {
                            difference = difference - qty;
                            if (skuLack.get(woolc.getSkuId()) != null) {
                                skuLack.put(woolc.getSkuId(), skuLack.get(woolc.getSkuId()) + qty);
                            } else {
                                skuLack.put(woolc.getSkuId(), qty);
                            }
                            failSta.add(staId);
                        } else {
                            if ((difference - qty) == 0) {
                                if (skuLack.get(woolc.getSkuId()) != null) {
                                    skuLack.put(woolc.getSkuId(), skuLack.get(woolc.getSkuId()) + qty);
                                } else {
                                    skuLack.put(woolc.getSkuId(), qty);
                                }
                            } else {
                                if (skuLack.get(woolc.getSkuId()) != null) {
                                    skuLack.put(woolc.getSkuId(), skuLack.get(woolc.getSkuId()) + difference);
                                } else {
                                    skuLack.put(woolc.getSkuId(), difference);
                                }
                            }
                            failSta.add(staId);
                            break;
                        }
                    }
                }


            }

            woolcList = findWhOcpOrderLineByWooId(wooId, null, null);

            // 添加配货失败记录信息
            addStaErrorLine(lackMap);

            // 释放多余库存
            releaseQtyByStaAndOcp(failSta, wooId, staStalines, woolcList);

            for (Long staId : failSta) {
                wareHouseManager.updateStaByOcpCode(ocpCode, staId);
            }
        }
    }

    /**
     * 添加配货失败记录信息
     * 
     * @param lackMap
     */
    public void addStaErrorLine(Map<Long, Map<Long, Long>> lackMap) {
        for (Long staId : lackMap.keySet()) {
            Map<Long, Long> skuLackMap = lackMap.get(staId);
            for (Long skuId : skuLackMap.keySet()) {
                staErrorLineDao.deleteByStaErrorLine(staId, skuId);
                StaErrorLine sel = new StaErrorLine();
                sel.setQuantity(skuLackMap.get(skuId));
                sel.setSkuId(skuId);
                sel.setStaId(staId);
                staErrorLineDao.save(sel);
            }
        }
    }

    /**
     * 根据订单获取每个店铺每个商品的数量
     * 
     * 
     * @return
     */
    public void getOwnerStaSkuQty(List<StaLineCommand> staLines, Map<String, Map<Long, Long>> ownerSkuQty, Map<Long, List<StaLineCommand>> staStalines) {
        if (staLines != null) {
            for (StaLineCommand staLine : staLines) {
                Long skuId = staLine.getSkuId();
                String owner = staLine.getOwner();
                Long staId = staLine.getStaId();
                if (ownerSkuQty.get(owner + skuId) != null) {
                    Map<Long, Long> staQty = ownerSkuQty.get(owner + skuId);
                    if (staQty.get(staId) != null) {
                        staQty.put(staId, staQty.get(staId) + staLine.getQuantity());
                    } else {
                        staQty.put(staId, staLine.getQuantity());
                    }
                } else {
                    Map<Long, Long> staQty = new HashMap<Long, Long>();
                    staQty.put(staId, staLine.getQuantity());
                    ownerSkuQty.put(owner + skuId, staQty);
                }

                if (staStalines.get(staId) != null) {
                    List<StaLineCommand> slcList = staStalines.get(staId);
                    slcList.add(staLine);
                } else {
                    List<StaLineCommand> slcList = new ArrayList<StaLineCommand>();
                    slcList.add(staLine);
                    staStalines.put(staId, slcList);
                }
            }
        }
    }

    /**
     * 根据订单和占用批释放库存
     * 
     * @param staId
     * @param wooId
     */
    public void releaseQtyByStaAndOcp(Set<Long> staIdSet, Long wooId, Map<Long, List<StaLineCommand>> staStalines, List<WhOcpOrderLineCommand> woolcList) {
        if (staIdSet != null && wooId != null) {
            // 统计每个sku的库存占用量
            Map<Long, Map<String, Long>> skuQty = new HashMap<Long, Map<String, Long>>();

            for (Long staId : staIdSet) {
                List<StaLineCommand> staLines = staStalines.get(staId);
                if (staLines != null) {
                    for (StaLineCommand staLine : staLines) {
                        Long skuId = staLine.getSkuId();
                        String owner = staLine.getOwner();

                        // 获取每个SKU的总数量
                        if (skuQty.get(skuId) != null) {
                            Map<String, Long> ownerQty = skuQty.get(skuId);
                            if (ownerQty.get(owner) != null) {
                                ownerQty.put(owner, ownerQty.get(owner) + staLine.getQuantity());
                            } else {
                                ownerQty.put(owner, staLine.getQuantity());
                            }
                        } else {
                            Map<String, Long> ownerQty = new HashMap<String, Long>();
                            ownerQty.put(owner, staLine.getQuantity());
                            skuQty.put(skuId, ownerQty);
                        }
                    }
                }
            }

            // 计算每个占用明细需要释放的库存数量
            Map<WhOcpOrderLineCommand, Long> skuReleaseQty = new HashMap<WhOcpOrderLineCommand, Long>();
            for (WhOcpOrderLineCommand woolc : woolcList) {
                if ((skuQty.get(woolc.getSkuId()) != null) && (skuQty.get(woolc.getSkuId()).get(woolc.getOwner()) != null)) {
                    Long skuOwnerQty = skuQty.get(woolc.getSkuId()).get(woolc.getOwner());

                    Long conformQty = woolc.getConformQty().longValue();
                    Long qty = woolc.getQty().longValue();
                    // 算出需要释放的量
                    Long releaseQty = conformQty - (qty - skuOwnerQty);
                    if (releaseQty > 0) {
                        skuReleaseQty.put(woolc, releaseQty);
                    }
                }

            }

            // 释放多余库存
            if (!skuReleaseQty.isEmpty()) {
                WhOcpOrderLineCommand woolc = woolcList.get(0);
                Warehouse wh = warehouseDao.getByOuId(woolc.getOuId());
                releaseQty(skuReleaseQty, wh.getId());
            }

        }
    }

    /**
     * 根据skuID、占用批编码、仓库ID和需要释放的库存数量来释放库存
     * 
     * @param skuReleaseQty
     */
    public void releaseQty(Map<WhOcpOrderLineCommand, Long> skuReleaseQty, Long whId) {
        for (WhOcpOrderLineCommand woolc : skuReleaseQty.keySet()) {

            Long release = skuReleaseQty.get(woolc);

            // 修正占用批明细表中的实际占用量
            whOcpOrderLineDao.updateConformQtyByWoolId(woolc.getId(), release);
            // 获取已占用库存
            List<InventoryCommand> inventoryCommands = inventoryDao.findInventoryOccupyBySourceOcpCode(woolc.getOwner(), woolc.getSkuId(), woolc.getOcpCode(), whId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            for (int i = inventoryCommands.size() - 1; i >= 0; i--) {
                InventoryCommand invc = inventoryCommands.get(i);
                release = release - invc.getQuantity();
                if (release < 0) {
                    // 切割已被占用的库存
                    inventoryDao.addNewInventory(invc.getId(), invc.getQuantity() + release, null);

                    // 释放多余库存
                    inventoryDao.releaseInventoryById(invc.getId(), -release);
                    break;
                } else if (release == 0) {
                    // 释放多余库存
                    inventoryDao.releaseInventoryById(invc.getId(), null);
                    break;
                } else {
                    // 释放多余库存
                    inventoryDao.releaseInventoryById(invc.getId(), null);
                }
            }
        }

    }

    /**
     * 根据占用批明细占用库存
     * 
     * @param woolId
     */
    public void ocpInvByWoolId(Long woolId) {
        WhOcpOrderLine wool = whOcpOrderLineDao.getByPrimaryKey(woolId);
        Long skuId = wool.getSku().getId();
        Long qty = wool.getQty().longValue();
        Long ouId = wool.getWhOcpOrder().getOperationUnit().getId();
        Long whId = wareHouseManager.getByOuId(ouId).getId();
        String owner = wool.getOwner();
        String ocpCode = wool.getWhOcpOrder().getCode();
        String areaOcpCode = wool.getOcpCode(); // 区域占用编码
        // 实际占有量
        Long conformQty = ocpInvBySku(skuId, qty, whId, owner, ocpCode, null, null, null, null, null, null, null, null, areaOcpCode);
        wool.setConformQty(conformQty.intValue());
        whOcpOrderLineDao.save(wool);
        if (log.isInfoEnabled()) {
            log.info(woolId + " skuOcp start.........." + conformQty);
        }
    }

    /**
     * 根据参数查找占用批
     * 
     * @param ouId
     * @param status
     * @return
     */
    public List<WhOcpOrderCommand> findOcpOrderByParams(Long ouId, List<Integer> status) {
        return whOcpOrderDao.findOcpOrderByParams(ouId, status, new BeanPropertyRowMapper<WhOcpOrderCommand>(WhOcpOrderCommand.class));
    }

    public List<WhOcpOrderCommand> findOcpOrderStatus(Long ouId, Integer status) {
        return whOcpOrderDao.findOcpOrderStatus(ouId, status, new BeanPropertyRowMapper<WhOcpOrderCommand>(WhOcpOrderCommand.class));
    }


    public void staOcpInv(List<WhOcpOrderLineCommand> woolcList, Map<String, Map<Long, Long>> ownerSkuQty, Map<Long, List<StaLineCommand>> staStalines) {
        Map<Long, Set<Long>> staAndLocationId = new HashMap<Long, Set<Long>>();
        for (WhOcpOrderLineCommand woolc : woolcList) {
            Map<Long, Long> staQtyMap = ownerSkuQty.get(woolc.getOwner() + woolc.getSkuId());
            // 获取已占用库存
            List<InventoryCommand> inventoryCommands = inventoryDao.findInventoryOccupyBySourceOcpCode(woolc.getOwner(), woolc.getSkuId(), woolc.getOcpCode(), null, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            Set<InventoryCommand> invReserve = new HashSet<InventoryCommand>();
            Map<Long, Map<Long, Long>> staOcpLineMap = new HashMap<Long, Map<Long, Long>>();

            // 算出每个订单的每个SKU要占用的库存
            for (Long staId : staQtyMap.keySet()) {
                Long qty = staQtyMap.get(staId);
                Set<Long> locationIds = staAndLocationId.get(staId);
                if (locationIds == null) {
                    locationIds = new HashSet<Long>();
                    staAndLocationId.put(staId, locationIds);
                }

                for (int i = 0; i < inventoryCommands.size(); i++) {
                    InventoryCommand invc = inventoryCommands.get(i);

                    locationIds.add(invc.getLocationId());

                    qty = qty - invc.getQuantity();

                    Map<Long, Long> invIdAndQty = staOcpLineMap.get(staId);
                    if (invIdAndQty == null) {
                        invIdAndQty = new HashMap<Long, Long>();
                        staOcpLineMap.put(staId, invIdAndQty);
                    }

                    if (qty < 0) {

                        invIdAndQty.put(invc.getId(), qty + invc.getQuantity());

                        invc.setQuantity(-qty);
                        qty = 0L;
                        break;
                    } else if (qty == 0) {
                        invIdAndQty.put(invc.getId(), invc.getQuantity());
                        invReserve.add(invc);
                        inventoryCommands.remove(i);
                        i--;
                        break;
                    } else {
                        invIdAndQty.put(invc.getId(), invc.getQuantity());
                        invReserve.add(invc);
                        inventoryCommands.remove(i);
                        i--;
                    }
                }
                if (qty != 0L) {
                    log.error("订单占用库存----占用批占用的库存数据有异常！！订单ID为：" + staId);
                    throw new BusinessException(ErrorCode.OCP_ORDER_BATCH_ERROR);
                }
            }

            // 以删除新增的形式占用库存
            for (Long staId : staOcpLineMap.keySet()) {
                Map<Long, Long> invIdAndQty = staOcpLineMap.get(staId);
                for (Long invId : invIdAndQty.keySet()) {
                    // 添加占用执行明细
                    whOcpOrderExeLineDao.insertOoelByInv(invId, woolc.getOcpId(), invIdAndQty.get(invId));

                    // 占用库存
                    inventoryDao.addNewInventory(invId, invIdAndQty.get(invId), staStalines.get(staId).get(0).getStaCode());
                }
            }
            for (InventoryCommand inv : invReserve) {
                inventoryDao.deleteByPrimaryKey(inv.getId());
            }

        }
        // 根据库位ID更新作业单拣货区列表
        updateStaZoonList(staAndLocationId);

    }

    /**
     * 根据库位ID更新作业单拣货区列表
     * 
     * @param staAndLocationId
     */
    public void updateStaZoonList(Map<Long, Set<Long>> staAndLocationId) {
        for (Long staId : staAndLocationId.keySet()) {
            StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staId);
            List<Long> zoonList = whPickZoneDao.findZoonIdsByLocationIds(staAndLocationId.get(staId), new SingleColumnRowMapper<Long>(Long.class));
            String zoonStr = "";
            for (Long lId : zoonList) {
                zoonStr = zoonStr + lId + ",";
            }
            if (zoonStr.length() > 1) {
                zoonStr = zoonStr.substring(0, zoonStr.length() - 1);
            }
            // 根据订单ID更新拣货区列表
            // whPickZoneDao.updateStaZoonIdsByStaId(zoonStr, staId);
            sta.setZoonList(zoonStr);

            // 更新快递状态
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            sta.setLastModifyTime(new Date());
            // stockTransApplicationDao.save(sta);

            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());

            BigDecimal ttid = null;
            ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
            if (ttid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(staId, new SingleColumnRowMapper<String>());
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), null, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
            stvLineDao.createByStaId(sta.getId());
        }
    }


    /**
     * 占用批创建异常处理
     * 
     * @param stacList
     */
    public void ocpBatchCreateExceptionHandle(String ocpCode) {
        stockTransApplicationDao.updateStaStatusByOcpCode(ocpCode);
        whOcpOrderDao.updateOcpOrderStatusByOcpCode(ocpCode, OcpInvConstants.OCP_ORDER_STATUS_EXCEPTION);
        wareHouseManager.updateStaByOcpCode(ocpCode, null);
    }

    /**
     * 占用批异常重新处理
     * 
     * @param stacList 释放库存，清空作业单占用信息，修改批次为已处理
     */
    public void ocpBatchCreateExceptionHandleAgain(String ocpCode) {
        whOcpOrderDao.updateOcpOrderStatusByOcpCode(ocpCode, OcpInvConstants.OCP_ORDER_STATUS_EXCEPTION_RESOLVED);
        wareHouseManager.updateStaByOcpCode(ocpCode, null);
    }

    /**
     * 最后校验占用批明细里的占用量是否与实际占用量相等
     * 
     * @param wooId
     */
    public void checkOcpData(Long wooId) {
        List<WhOcpOrderLineCommand> woolcList = whOcpOrderLineDao.findWhOcpOrderLineByWooId(wooId, null, null, null, null, new BeanPropertyRowMapper<WhOcpOrderLineCommand>(WhOcpOrderLineCommand.class));
        if (woolcList != null) {

            for (WhOcpOrderLineCommand woolc : woolcList) {
                // 获取已占用库存
                List<InventoryCommand> inventoryCommands = inventoryDao.findInventoryOccupyBySourceOcpCode(woolc.getOwner(), woolc.getSkuId(), woolc.getOcpCode(), null, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                Long ocpInv = 0L;
                for (InventoryCommand ic : inventoryCommands) {
                    ocpInv += ic.getQuantity();
                }
                if (woolc.getConformQty().longValue() != ocpInv) {
                    throw new BusinessException(ErrorCode.OCP_ORDER_BATCH_ERROR);
                }
            }
        }
    }

    /**
     * 根据状态获取所有的占用批ID
     * 
     * @param ouId
     * @param status
     * @return
     */
    public List<Long> findOcpOrderIdsByStatus(Long ouId, Integer status) {
        return whOcpOrderDao.findOcpOrderIdsByStatus(ouId, status, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 订单占用异常处理
     * 
     * @param wooId
     */
    public void staOcpException(Long wooId) {
        WhOcpOrder oo = whOcpOrderDao.getByPrimaryKey(wooId);
        Integer errorCount = oo.getErrorCount();
        if (errorCount == null) {
            errorCount = 0;
        }
        errorCount = errorCount + 1;
        // 增加错误次数
        oo.setErrorCount(errorCount);
    }

    /**
     * 将超过一定占用失败次数的占用批以邮件的形式通知相关人员
     */
    public void exceptionOcpOrderInformEmail() {

        // 获取所有订单占用失败次数超过5次的占用批
        List<WhOcpOrderCommand> woocList = whOcpOrderDao.findExceptionOcpOrder(new BeanPropertyRowMapper<WhOcpOrderCommand>(WhOcpOrderCommand.class));

        if (woocList != null && woocList.size() > 0) {

            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(OcpInvConstants.OCP_CHOOSE_GROUP_SYS_MAIL);
            MailTemplate template = mailTemplateDao.findTemplateByCode(option.get(OcpInvConstants.OCP_CHOOSE_SYS_MAIL_ERROR_OCP_FAILED_TEMPLETE_CODE));
            String[] s = template.getMailBody().split("@");
            String prefix = s[0];
            String suffix = s[1];
            StringBuffer mailBody = new StringBuffer(prefix);
            for (int i = 0; i < woocList.size(); i++) {
                WhOcpOrderCommand wooc = woocList.get(i);
                String tr = MessageFormat.format(suffix, wooc.getOuId().toString(), wooc.getCode());
                mailBody = mailBody.append(tr);
            }
            mailBody.append(s[2]);
            MailUtil.sendMail(template.getSubject(), option.get(OcpInvConstants.OCP_CHOOSE_SYS_MAIL_ERROR_OCP_FAILED_RECIVER), null, mailBody.toString(), true, null);
            log.error("GymboreeTask sendMailForErrorReback end");
        }
    }

    @Override
    public List<Long> findNeedOcpOrderByOuId(Long ouId2) {
        return stockTransApplicationDao.findNeedOcpOrderByOuId(ouId2, new SingleColumnRowMapper<Long>(Long.class));
    }


    /**
     * 查询占用批的库存。验证占用量
     */
    public Long queryInventoryByOcpCode(String ocpCode) {
        return whOcpOrderDao.queryInventoryByOcpCode(ocpCode, new SingleColumnRowMapper<Long>(Long.class));
    }

    public List<String> findOcpAreaByOuId(Long ouId) {
        return whOcpOrderDao.findOcpAreaByOuId(ouId, new SingleColumnRowMapper<String>(String.class));
    }

    // public List<ZoonOcpSort> findZoonOcpSortListByOuId(Long ouId) {
    // return zoonOcpSortDao.findZoonOcpSortList(ouId, new
    // BeanPropertyRowMapper<ZoonOcpSort>(ZoonOcpSort.class));
    // }

    /**
     * 作业单发送mq
     * 
     * @param staId
     */
    public void sendStaToMqForAreaOcp(Long staId) {

    }

    @Override
    public Pagination<ZoonOcpSort> findZoonOcpSortListByZoonCodeAndOuid(int start, int pageSize, Sort[] sorts, Long ouId, String zoonCode) {
        if ("".equals(zoonCode)) {
            zoonCode = null;
        }
        return zoonOcpSortDao.findZoonOcpSortByzoonCode(start, pageSize, sorts, ouId, zoonCode, new BeanPropertyRowMapper<ZoonOcpSort>(ZoonOcpSort.class));
    }

    @Override
    public List<Zoon> getZoonList(Long ouId) {
        return zoonDao.findZoonByOuId(ouId, new BeanPropertyRowMapper<Zoon>(Zoon.class));
    }

    @Override
    public String updateZoonOcpSort(ZoonOcpSort zoonOcpSort, Long ouId) {
        Zoon zoon = zoonDao.getByPrimaryKey(Long.parseLong(zoonOcpSort.getZoonId()));
        int i = zoonOcpSortDao.findZoonOcpSortBySortAndsaleModel(ouId, zoonOcpSort.getSort(), zoonOcpSort.getSaleModle(), new SingleColumnRowMapper<Integer>(Integer.class));
        int j = zoonOcpSortDao.findZoonOcpSortByZoonCodeAndsaleModel(ouId, zoonOcpSort.getSaleModle(), zoon.getCode(), new SingleColumnRowMapper<Integer>(Integer.class));
        // 更新操作
        if (zoonOcpSort.getId() != null) {
            ZoonOcpSort zoonOcpSort1 = zoonOcpSortDao.getByPrimaryKey(zoonOcpSort.getId());
            if (zoonOcpSort1.getSaleModle().equals(zoonOcpSort.getSaleModle()) && !zoonOcpSort1.getZoonCode().equals(zoon.getCode()) && zoonOcpSort1.getSort().equals(zoonOcpSort.getSort())) {
                if (j > 0) {
                    return "同一订单类型同一仓库区域已存在维护数据";
                }
            } else if (zoonOcpSort1.getSaleModle().equals(zoonOcpSort.getSaleModle()) && zoonOcpSort1.getZoonCode().equals(zoon.getCode()) && !zoonOcpSort1.getSort().equals(zoonOcpSort.getSort())) {
                if (i > 0) {
                    return "同一订单类型下优先级不能重复";
                }
            } else {
                if (i > 0 || j > 0) {
                    return "同一订单类型下优先级或同一订单类型同一仓库区域不能重复";
                }
            }
            zoonOcpSort1.setZoonCode(zoon.getCode());
            zoonOcpSort1.setSaleModle(zoonOcpSort.getSaleModle());
            zoonOcpSort1.setSort(zoonOcpSort.getSort());
            zoonOcpSortDao.save(zoonOcpSort1);
        } else {
            // 新增
            if (i > 0) {
                return "同一订单类型下优先级不能重复";
            }
            if (j > 0) {
                return "同一订单类型同一仓库区域已存在维护数据";
            }
            ZoonOcpSort zoonOcpSort1 = new ZoonOcpSort();
            zoonOcpSort1.setOuId(ouId);
            zoonOcpSort1.setZoonId(zoon.getId() + "");
            zoonOcpSort1.setSort(zoonOcpSort.getSort());
            zoonOcpSort1.setZoonCode(zoon.getCode());
            zoonOcpSort1.setSaleModle(zoonOcpSort.getSaleModle());
            zoonOcpSortDao.save(zoonOcpSort1);
        }
        Constants.zoonOcpMap = null;
        return "";
    }

    @Override
    public void deleteZoonOcpSort(Long id) {
        zoonOcpSortDao.deleteByPrimaryKey(id);
        Constants.zoonOcpMap = null;
    }

    public List<String> findAllZoon(Long ouId) {
        return zoonDao.findAllZoonCode(ouId, new SingleColumnRowMapper<String>(String.class));
    }

    // 占用库存出库单丢入MQ
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void occupiedInventoryByStaToMq(Long staId) {
        if (log.isInfoEnabled()) {
            log.info("NewInventoryOccupyManagerImpl.occupiedInventoryByStaToMq staId:" + staId);
        }
        MessageCommond mes = new MessageCommond();
        StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staId);
        Date date = new Date();
        try {
            // String ocpCode = sequenceManager.getOcpBatchCode();// 获取占用批编码
            // 丢MQ成功 更新
            // String ocp = "MQ_" + ocpCode;
            // wareHouseManager.occupiedInventoryByStaToMqUpdateSta(sta, ocp);
            mes.setMsgId(System.currentTimeMillis() + "" + UUIDUtil.getUUID());
            mes.setIsMsgBodySend(false);
            mes.setMsgType("NewInventoryOccupyManagerImpl.occupiedInventoryByStaToMq");
            String msgBody = JsonUtil.writeValue(staId);
            mes.setMsgBody(msgBody);
            mes.setTags(sta.getMainWarehouse().getId() + "");// 仓库ID
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mes.setSendTime(sdf.format(date));
            producerServer.sendDataMsgConcurrently(MQ_WMS3_OCCUPY_INVENTORY, sta.getMainWarehouse().getId().toString(), mes);
        } catch (Exception e) {
            // 丢失败记录错误信息
            // 更新出库单OcpCode
            wareHouseManager.occupiedInventoryByStaToMqUpdateSta(sta, null);
            log.error("NewInventoryOccupyManagerImpl.occupiedInventoryByStaToMq sendDataMsgConcurrently error staId: " + staId);
        }
        try {
            // 保存进mongodb
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mes, mdbm);
            mdbm.setStaCode(sta.getOcpBatchCode());
            mdbm.setOtherUniqueKey(staId.toString());
            mdbm.setMsgBody(staId.toString());
            mdbm.setMemo(MQ_WMS3_OCCUPY_INVENTORY);
            mongoOperation.save(mdbm);
        } catch (Exception e) {
            // MQ失败记录日志
            log.error("NewInventoryOccupyManagerImpl.occupiedInventoryByStaToMq mongoOperation.save error staId: " + staId);
        }
    }

    @Override
    public void occupiedInventoryByStaToMq2(Long staId) {
        StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staId);
        String ocpCode = sequenceManager.getOcpBatchCode();// 获取占用批编码
        // 丢MQ成功 更新
        String ocp = "MQ_" + ocpCode;
        wareHouseManager.occupiedInventoryByStaToMqUpdateSta(sta, ocp);
    }

    @Override
    public void clearOccupiedInventoryByStaToMq(Long staId) {
        StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staId);
        sta.setOcpBatchCode(null);
        sta.setOcpCode(null);
        sta.setLastModifyTime(new Date());
        stockTransApplicationDao.save(sta);
    }
}
