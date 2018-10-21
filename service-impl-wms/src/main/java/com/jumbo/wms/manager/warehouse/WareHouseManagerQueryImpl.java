/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransSfInfoDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.pda.PdaStaShelvesPlanDao;
import com.jumbo.dao.pda.StaCartonDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.DistributionRuleDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckMoveLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.ReturnPackageDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.InventoryCheckMoveLineCommand;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.mongodb.AsnShelves;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.mongodb.StaCheckRecord;
import com.jumbo.wms.model.mongodb.TwicePickingBarCode;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.DistributionRuleCommand;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.PickingMode;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;

@Transactional
@Service("wareHouseManagerQuery")
public class WareHouseManagerQueryImpl extends BaseManagerImpl implements WareHouseManagerQuery {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7535863018135625209L;

    @Resource(name = "staGIReader")
    private ExcelReader staGIReader;// 收货暂存区导入
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ReturnPackageDao returnPackageDao;
    @Autowired
    private InventoryCheckMoveLineDao checkMoveLineDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private DistributionRuleDao distributionRuleDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private WarehouseDistrictDao districtDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private WarehouseLocationDao locationDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    ChooseOptionDao chooseOptionDao;
    @Autowired
    private TransSfInfoDao transSfInfoDao;
    @Autowired
    private StaCartonDao staCartonDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private PdaStaShelvesPlanDao pdaStaShelvesPlanDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;

    /**
     * 根据仓库组织ID获取可销售库存状态
     * 
     * @param whouid
     * @return
     */
    public InventoryStatus getSalesInvStatusByWhou(Long whouid) {
        OperationUnit whou = ouDao.getByPrimaryKey(whouid);
        OperationUnit cmpOu = whou.getParentUnit().getParentUnit();
        return inventoryStatusDao.findInvStatusForSale(cmpOu.getId());
    }

    public List<OperationUnit> fandOperationUnitsByType(String type) {
        List<OperationUnit> ous = operationUnitDao.findAllByType(type);
        List<OperationUnit> result = new ArrayList<OperationUnit>(ous.size());
        for (OperationUnit ou : ous) {
            OperationUnit newOu = new OperationUnit();
            BeanUtils.copyProperties(ou, newOu);
            newOu.setChildrenUnits(null);
            newOu.setParentUnit(null);
            newOu.setOuType(null);
            result.add(newOu);
        }
        return result;
    }

    /**
     * 查询配货清单是否支持COD
     * 
     * @param plid
     * @return
     */
    public boolean isCodPickingList(Long plid) {
        PickingList pl = pickingListDao.getByPrimaryKey(plid);
        if (pl == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Transportator t = transportatorDao.findByCode(pl.getLpcode());
        return t == null ? false : t.getIsSupportCod() == null ? false : t.getIsSupportCod();
    }

    @SuppressWarnings({"unchecked"})
    @Transactional
    public ReadStatus staImportForGI(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            readStatus = null;

            readStatus = staGIReader.readAll(new FileInputStream(staFile), beans);

            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            // 验证导入数据是否正确
            staImportForGIValidate(readStatus, sta, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> list = new ArrayList<StvLine>();
            List<StvLine> sheet1 = (List<StvLine>) beans.get("stvLines");
            list.addAll(sheet1);
            StockTransVoucher stvCreated = createGIStv(sta, list, creator, null);
            whManager.purchaseReceiveStep2(stvCreated.getId(), list, false, creator, false, true);
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }

    public SkuSnCommand findSnBySkuId(Long skuId, String sn, SkuSnStatus status, Long ouid) {
        return snDao.findBySkuId(skuId, sn, ouid, status.getValue(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
    }

    /**
     * @see com.jumbo.wms.manager.warehouse.WareHouseManager#purchaseReceiveStep1(InboundStoreMode,
     *      Long, List)
     */
    private StockTransVoucher createGIStv(StockTransApplication sta, List<StvLine> stvLineList, User creator, Boolean isPda) {
        if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            log.error("......The StockTransApplication has been finished by others.");
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        StockTransVoucher stv = null;
        stv = stvDao.findStvCreatedByStaId(sta.getId());
        if (stv != null) {
            // business_exception_10009=作业申请单有未完成上架的作业明细单,在未完成之前不能进行收货操作
            log.error("......The StockTransApplication has StockTransVoucher created, you can do nothing before complete the stv.");
            throw new BusinessException(ErrorCode.STA_HAS_CREATED_STV);
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        InventoryStatus ins = inventoryStatusDao.findInvStatusForSale(operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId()).getParentUnit().getParentUnit().getId());
        // business_exception_stv_10103=请初始化可销售的库存状态数据后再尝试此操作
        if (ins == null) {
            throw new BusinessException(ErrorCode.STV_NO_INVENTORY_STATUS);
        }
        // 获取库区
        WarehouseDistrict dis = districtDao.findDistrictByCodeAndOu("GI", sta.getMainWarehouse().getId());
        if (dis == null) {
            dis = new WarehouseDistrict();
            dis.setCode("GI");
            dis.setName("收货暂存区");
            dis.setIntDistrictType(WarehouseDistrictType.RECEIVING.getValue());
            dis.setOu(sta.getMainWarehouse());
            districtDao.save(dis);
        }
        if (!WarehouseDistrictType.RECEIVING.equals(dis.getType())) {
            throw new BusinessException(ErrorCode.WH_DISTRICT_TYPE_ERROR);
        }
        stv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv.setBusinessSeqNo(biSeqNo.longValue());
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));

        // 创建库位
        WarehouseLocation loc = new WarehouseLocation();
        String code = "GI_" + sta.getMainWarehouse().getCode() + "_" + sta.getCode() + "_";
        String count = stv.getCode().replaceAll(sta.getCode(), "");
        code = code + count;
        loc.setCode(code);
        loc.setBarCode(code);
        loc.setManualCode(code);
        loc.setSysCompileCode(code);
        loc.setDimC("1");
        loc.setDimX("1");
        loc.setDimY("1");
        loc.setDimZ("1");
        loc.setOu(sta.getMainWarehouse());
        loc.setDistrict(dis);
        locationDao.save(loc);
        // 绑定作业类型
        // locationDao.createLocTransForLocAndTran(loc.getCode(),
        // Constants.TRANSACTION_TYPE_CODE_PURCHASE_INBOUND);

        stv.setMode(null);
        stv.setCreateTime(new Date());
        stv.setLastModifyTime(new Date());
        stv.setCreator(creator);
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setOwner(sta.getOwner());
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setIsPda(isPda);
        stv.setWarehouse(sta.getMainWarehouse());
        stv.setTransactionType(tranType);
        stv.setGiLocationCode(loc.getCode());
        stv = stvDao.save(stv);
        // stvDao.flush();
        String batchCode = Long.valueOf(new Date().getTime()).toString();
        List<StvLine> stvLines = new ArrayList<StvLine>();

        Long qty = 0L;
        for (StvLine stvLine : stvLineList) {
            if (stvLine == null) {
                continue;
            }
            StaLine staLine = null;
            if (stvLine.getStaLine() != null && stvLine.getStaLine().getId() != null) {
                staLine = staLineDao.getByPrimaryKey(stvLine.getStaLine().getId());
            } else {
                staLine = this.findStaLineByBarCodeOrCodeProps(stvLine.getSku(), stv.getSta().getId());
            }
            // 采购入库才需要设置库存状态,退换货入库、其他入库都有库存状态
            stvLine.setInvStatus(ins);
            // 采购入库、退换货入库需要设置库存成本,其他出入库根据页面数据保存
            stvLine.setSkuCost(staLine.getSkuCost());
            stvLine.setStaLine(staLine);
            stvLine.setBatchCode(batchCode);
            stvLine.setDirection(TransactionDirection.INBOUND);
            stvLine.setStv(stv);
            stvLine.setTransactionType(tranType);
            stvLine.setWarehouse(stv.getWarehouse());
            stvLine.setOwner(sta.getOwner());
            stvLine.setLocation(loc);
            stvLine.setDistrict(dis);
            qty += stvLine.getQuantity();
            stvLine = stvLineDao.save(stvLine);
            stvLines.add(stvLine);
        }
        stv.setSkuQty(qty);
        sta.setInboundOperator(creator);
        sta.setInboundTime(new Date());
        staDao.save(sta);
        stvDao.save(stv);
        stvDao.flush();
        return stv;
    }

    /**
     * 数据验证
     * 
     * @param rs
     * @param sta
     * @param beans
     */
    @SuppressWarnings({"unchecked"})
    private void staImportForGIValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }

        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLines");
        if (stvLines == null || stvLines.isEmpty()) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }

        /***************************** 验证sheet1数据 ***************************************/
        Iterator<StvLine> it = stvLines.iterator();
        int index = 2;
        // 对于相同skuId分组
        Map<Long, List<StvLine>> stvLineMap = new HashMap<Long, List<StvLine>>();
        // 对于StvLine按skuID进行分组
        while (it.hasNext()) {
            StvLine stvLine = it.next();
            index++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it.remove();
                    continue;
                } else {
                    // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                    if (stvLine.getQuantity() == null) {// 没有数量
                        errors.add(new BusinessException(ErrorCode.RSORDER_QTY_IS_EXIST, new Object[] {"1", index}));
                        continue;
                    }
                    StaLine staLine = findStaLineByBarCodeOrCodeProps(stvLine.getSku(), sta.getId());
                    if (staLine == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    stvLine.getStaLine().setId(staLine.getId());
                    stvLine.getStaLine().setSkuCost(staLine.getSkuCost());
                    // business_exception_10008=作业申请单行[{0}]条码[{1}]JMCode[{2}]实际本次执行量已经超出剩余待确认收货量
                    if (stvLine.getStaLine().getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                        errors.add(new BusinessException(ErrorCode.STA_QUANTITY_UNPLANNED, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                    if (sku.getIsSnSku() != null && sku.getIsSnSku() && !StringUtils.hasLength(stvLine.getSns())) {
                        errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getCode()}));
                        continue;
                    }
                    if (stvLineMap.containsKey(staLine.getSku().getId())) {
                        Long total = 0L;
                        for (StvLine each : stvLineMap.get(staLine.getSku().getId())) {
                            total += each.getQuantity();
                        }
                        // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                        if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        stvLineMap.get(staLine.getSku().getId()).add(stvLine);
                    } else {
                        if (stvLine.getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        List<StvLine> stvLineList = new ArrayList<StvLine>();
                        stvLineList.add(stvLine);
                        stvLineMap.put(staLine.getSku().getId(), stvLineList);
                    }
                    stvLine.setSku(sku);
                }
            } else {
                it.remove();
            }
        }
        for (Map.Entry<Long, List<StvLine>> entry : stvLineMap.entrySet()) {
            StvLine stvLine = entry.getValue().get(0);
            Long total = stvLine.getStaLine().getQuantity();
            for (StvLine each : entry.getValue()) {
                total -= each.getQuantity();
            }
            if (total != 0) {
                errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
            }
        }
        if (errors != null && !errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
    }

    private StaLine findStaLineByBarCodeOrCodeProps(Sku sku, Long staId) {
        StaLine stal =
                staLineDao.findStaLineByBarCodeOrCodePropsSql(StringUtils.hasLength(sku.getBarCode()) ? sku.getBarCode() : null, StringUtils.hasLength(sku.getJmCode()) ? sku.getJmCode() : null,
                        StringUtils.hasLength(sku.getKeyProperties()) ? sku.getKeyProperties() : null, staId, new BeanPropertyRowMapperExt<StaLine>(StaLine.class));
        if (stal == null) {
            stal = staLineDao.findStaLineByAddBarCodeSql(sku.getBarCode(), staId, new BeanPropertyRowMapperExt<StaLine>(StaLine.class));
        }
        return stal;
    }

    /**
     * 查询暂存区的库位商品
     * 
     * @return
     */
    public Pagination<WarehouseLocationCommand> findGILocation(int start, int pageSize, Long ouid, WarehouseLocationCommand loc, Sort[] sorts) {
        if (loc == null) {
            loc = new WarehouseLocationCommand();
        }
        return locationDao.findGILocation(start, pageSize, ouid, loc.setQueryLikeParam(), sorts, new BeanPropertyRowMapperExt<WarehouseLocationCommand>(WarehouseLocationCommand.class));
    }

    /**
     * 查寻暂存区上的商品
     * 
     * @return
     */
    public List<InventoryCommand> queryGILocSku(Long locId) {
        return inventoryDao.queryGILocSku(locId, new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
    }

    public Pagination<StockTransApplicationCommand> findInboundSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundSta(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }

    @Override
    public Pagination<StockTransApplicationCommand> findInboundStaVmi(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundStaVmi(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }

    public Pagination<StockTransApplicationCommand> findInboundStaFinish(int start, int pageSize, Long ouId, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        return staDao.findInboundStaFinish(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }

    public Pagination<StockTransApplicationCommand> findInboundStas(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundStas(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }

    public Pagination<StockTransApplicationCommand> findInboundStaQuery(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, Date finishTime, Date endfinishTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        map.put("finishTime", finishTime);
        map.put("endfinishTime", endfinishTime);
        return staDao.findInBoundStaAll(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }

    public Pagination<StaLineCommand> findInboundLine(int start, int pageSize, Long staId, Sort[] sorts) {
        return staLineDao.findInboundLineByStaId(start, pageSize, staId, null, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class), sorts);
    }

    public Pagination<SkuSnLogCommand> findInBoundSN(int start, int pageSize, Long staId, Sort[] sorts) {
        return snLogDao.findSnLogBySta(start, pageSize, staId, null, new BeanPropertyRowMapperExt<SkuSnLogCommand>(SkuSnLogCommand.class), sorts);
    }

    public Pagination<StockTransApplicationCommand> findInboundConfirmSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundAllSta(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CONFIRMED.getValue(), false, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    public Pagination<StvLineCommand> findInboundStvLine(int start, int pageSize, Long stvId, Sort[] sorts) {
        return stvLineDao.findInboundStvLine(start, pageSize, stvId, sorts, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
    }

    // 手动上架入库
    public List<StvLineCommand> findInboundStvLineHand(Long stvId) {
        return stvLineDao.findInboundStvLineHand(stvId, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
    }

    public Pagination<StockTransApplicationCommand> findMergeInboundStvLine(int start, int pageSize, Long ouId, List<Long> stvList, Sort[] sorts) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stvLine", stvList);
        return staDao.findInBoundAllSta(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CHECK.getValue(), false, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    public Pagination<StockTransApplicationCommand> findInboundShelvesSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundAllSta(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CHECK.getValue(), false, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    /**
     * pda上架审核
     */
    @Override
    public Pagination<StockTransApplicationCommand> findPdaShelvesSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findPdaShelvesSta(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CHECK.getValue(), false, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    /**
     * pda上架审核明细
     */
    @Override
    public Pagination<StockTransApplicationCommand> checkPdaShelvesStaLine(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.checkPdaShelvesStaLine(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CHECK.getValue(), commd.getId(), map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    public Pagination<StockTransApplicationCommand> findPdaInboundShelves(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundAllSta(start, pageSize, ouId, StockTransVoucherType.INBOUND_ORDER.getValue(), StockTransVoucherStatus.CHECK.getValue(), true, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

    /**
     * PDA上架明细
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @param isFindDif 是否查询差异
     * @param sorts
     * @return
     */
    public Pagination<StvLineCommand> findPdaInboundLine(int start, int pageSize, StvLineCommand line, Boolean isFindDif, Sort[] sorts) {
        if (line == null) {
            return null;
        }
        // 是否查询差异 为空默认不考虑差异
        if (isFindDif == null) {
            return stvLineDao.findPdaInboundLine(start, pageSize, line.getStvId(), line.getQueryMap(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class), sorts);
        }
        // 查询存在差异数据
        if (isFindDif) {
            return stvLineDao.findPdaInboundDifLine(start, pageSize, line.getStvId(), line.getQueryMap(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class), sorts);
        } else {// 查询 无差异明细
            return stvLineDao.findPdaInboundNotDifLine(start, pageSize, line.getStvId(), line.getQueryMap(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class), sorts);
        }
    }

    /**
     * 查询PDA上架
     * 
     * @param stvId
     * @return
     */
    public List<PdaOrder> findPdaUserName(String staCode) {
        if (StringUtil.isEmpty(staCode)) return null;
        List<PdaOrder> result = pdaOrderDao.findPdaUserName(staCode, new BeanPropertyRowMapperExt<PdaOrder>(PdaOrder.class));
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        for (int i = 0; i < result.size(); i++) {
            PdaOrder pda = result.get(i);
            pda.setUserName(pda.getUserName() + "[" + formatter.format(pda.getCreateTime()) + "]");
        }
        return result;
    }

    @Override
    public List<StaSpecialExecutedCommand> queryStaSpecialExecute(Long staId) {
        if (staId == null) return new ArrayList<StaSpecialExecutedCommand>();
        return staSpecialExecutedDao.findStaSpecialByStaId(staId, null, new BeanPropertyRowMapperExt<StaSpecialExecutedCommand>(StaSpecialExecutedCommand.class));
    }

    @Override
    public Pagination<StockTransApplicationCommand> queryCoachSta(int start, int pageSize, Long ouId, StockTransApplicationCommand sta) {
        if (sta != null) {
            String code = !StringUtil.isEmpty(sta.getCode().trim()) ? sta.getCode().trim() : null;
            String slipCode = !StringUtil.isEmpty(sta.getRefSlipCode().trim()) ? sta.getRefSlipCode().trim() : null;
            Integer status = sta.getIntStaStatus();
            Integer type = sta.getIntStaType();
            Date createTime = sta.getCreateTime();
            Date endCreateTime = sta.getEndCreateTime();
            return staDao.queryCoachSta(start, pageSize, code, slipCode, status, type, createTime, endCreateTime, ouId, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        } else {
            return staDao.queryCoachSta(start, pageSize, null, null, null, null, null, null, ouId, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        }

    }

    @Override
    public List<ReturnPackageCommand> findReturnPackageList(String batchCode) {
        if (StringUtil.isEmpty(batchCode)) {
            return new ArrayList<ReturnPackageCommand>();
        }
        return returnPackageDao.findReturnPackageList(batchCode.trim(), "yyyy-MM-DD hh24:mi:ss", new BeanPropertyRowMapperExt<ReturnPackageCommand>(ReturnPackageCommand.class));
    }

    @Override
    public Pagination<ReturnPackageCommand> findReturnPackage(int start, int pageSize, Long whOuId, ReturnPackageCommand comm, Sort[] sorts) {
        String staCode = StringUtil.isEmpty(comm.getStaCode()) ? null : comm.getStaCode().trim();
        String lpcode = StringUtil.isEmpty(comm.getLpcode()) ? null : comm.getLpcode().trim();
        String trackingNo = StringUtil.isEmpty(comm.getTrackingNo()) ? null : comm.getTrackingNo().trim();
        String batchCode = StringUtil.isEmpty(comm.getBatchCode()) ? null : comm.getBatchCode().trim();
        String rejectionReasons = StringUtil.isEmpty(comm.getRejectionReasons()) ? null : comm.getRejectionReasons().trim();
        String whName = StringUtil.isEmpty(comm.getWarehouseName()) ? null : (comm.getWarehouseName().trim());
        String userName = StringUtil.isEmpty(comm.getUserName()) ? null : (comm.getUserName().trim() + "%");
        String registerWHId = StringUtil.isEmpty(comm.getWhName()) ? null : comm.getWhName().trim();
        return returnPackageDao.findReturnPackage(start, pageSize, comm.getCreateTime(), comm.getEndCreateTime(), staCode, lpcode, trackingNo, batchCode, comm.getIntStatus(), rejectionReasons, whName, registerWHId, userName, sorts,
                new BeanPropertyRowMapperExt<ReturnPackageCommand>(ReturnPackageCommand.class));
    }

    @Override
    public String checkSalesSum() {
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCode("nikeOtwoO");
        if (chooseOption == null) {
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
        return chooseOption.getOptionKey();
    }

    @Override
    public GiftLine getGiftLineByStaLineIdAndType(Long staLineId, Integer type) {
        return giftLineDao.getGiftLineByStaLineIdAndType(staLineId, type, new BeanPropertyRowMapper<GiftLine>(GiftLine.class));
    }

    @Override
    public List<GiftLineCommand> getGiftLineByPackingIdAndStaId(Long plid, Long staid) {
        return giftLineDao.getGiftLineByPackingIdAndStaId(plid, staid, new BeanPropertyRowMapper<GiftLineCommand>(GiftLineCommand.class));
    }

    @Override
    public StaLine findBySkuSta(Long staId, Long skuId) {
        StaLine line = staLineDao.findBySkuSta(staId, skuId, new BeanPropertyRowMapper<StaLine>(StaLine.class));
        return line;
    }

    @Override
    public TransSfInfo findTransSfInfoDefault() {
        TransSfInfo result = transSfInfoDao.findTransSfInfoDefault(true);
        return result;
    }

    public Warehouse queryTotalPickingList(Long ouId) {
        return warehouseDao.getByOuId(ouId);
    }


    /**
     * 查找配货批下的 特殊处理明细
     */
    public List<StaLineCommand> findGiftMemoByPkId(Long pkId) {
        return staLineDao.findGiftMemoByPkId(pkId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

    public List<StaLineCommand> findStaLineByGiftMemo(String slipCode, Long staLineId) {
        return staLineDao.findStaLineByGiftMemo(slipCode, staLineId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

    @Override
    public Map<String, Object> checkShelves(Long staId, OperationUnit op, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 更新作业单下所有箱的状态为完成10
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        sta.setPdaUserId(user.getId());// pda审核人
        staDao.save(sta);
        List<StaCarton> list = staCartonDao.findStaCartonByStaId(staId, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        for (StaCarton staCarton : list) {
            StaCarton s = staCartonDao.getByPrimaryKey(staCarton.getId());
            s.setStatus(DefaultStatus.FINISHED);
            staCartonDao.save(s);
            // 释放箱号
            if (sta != null && null == sta.getContainerCode()) {
                WhContainer whc = whContainerDao.getWhContainerByCode(staCarton.getCode());
                if (whc != null) {
                    autoOutboundTurnboxManager.resetTurnoverBoxStatusPdaShelves(whc.getId(), user.getId());
                }
            }
        }
        pdaStaShelvesPlanDao.updatePlanOver(null, sta.getId(), 2L);// 上架完成
        for (StaCarton staCarton : list) { // 删除缓存
            mongoOperation.remove(new Query(Criteria.where("cartonCode").is(staCarton.getCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
        }
        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
        // 通用品牌定制逻辑
        if (!StringUtil.isEmpty(shop.getDefaultCode())) {
            VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
            vv.generateReceivingWhenClose(sta.getId());
        }
        return map;
    }

    public Long getCustomerByWarehouse(Warehouse wh) {
        if (wh != null && wh.getCustomer() != null) {
            return wh.getCustomer().getId();
        } else {
            return null;
        }
    }

    public Long getCustomerByWhouid(Long whouid) {
        Warehouse wh = warehouseDao.getByOuId(whouid);
        return getCustomerByWarehouse(wh);
    }

    public List<SkuBarcodeCommand> findAddSkuBarcodeByMainBarcode(List<String> barcodes, Long whouid) {
        Long customerId = getCustomerByWhouid(whouid);
        return skuBarcodeDao.findByMainBarcode(barcodes, customerId);
    }

    /**
     * 获取SKU
     * 
     * @param barcode
     * @param jmcode
     * @param keyProperties
     * @return
     */
    public Sku findSkuByParameter(String barcode, String jmcode, String keyProperties, Long whouId) {
        Long customerId = getCustomerByWhouid(whouId);
        Sku sku = skuDao.findSkuByParameter(barcode, jmcode, keyProperties, customerId);
        if (sku == null && StringUtils.hasText(barcode)) {
            SkuBarcode addcode = skuBarcodeDao.findByBarCode(barcode, customerId);
            if (addcode != null) {
                sku = addcode.getSku();
            }
        }
        if (sku == null) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {barcode + " " + jmcode});
        }
        SkuCommand sc = null;
        if (sku != null) {
            sc = new SkuCommand();
            try {
                org.springframework.beans.BeanUtils.copyProperties(sku, sc);
            } catch (Exception e) {
                log.error("Copy Bean properties error for sku");
                log.error("", e);
                throw new RuntimeException("Copy Bean properties error for sku");
            }
        }
        return sc;
    }

    public List<SkuBarcode> findSkuBarcodeBySkuId(String mainBarcode, Long whouid) {
        Long customerId = getCustomerByWhouid(whouid);
        return skuBarcodeDao.findByMainBarcode(mainBarcode, customerId);
    }

    public List<StaLineCommand> findOccpiedStaLineByPlId(Long plid, Sort[] sorts) {
        return staLineDao.findOccpiedStaLineByPlId(plid, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class), sorts);
    }

    public List<PickingList> findAfDeLiveryList(PickingMode pickingMode, Long ouId, Sort[] sorts) {
        return pickingListDao.findAfDeLiveryList(pickingMode, ouId, sorts);
    }

    public Pagination<DistributionRuleCommand> findAllDistributionRule(int start, int pageSize, Long ouid, String ruleName, Sort[] sorts) {
        String name = null;
        if (ruleName != null && !"".equalsIgnoreCase(ruleName)) {
            name = ruleName;
        }
        return distributionRuleDao.findAllDistributionRule(start, pageSize, ouid, name, new BeanPropertyRowMapperExt<DistributionRuleCommand>(DistributionRuleCommand.class), sorts);
    }

    public Pagination<InventoryCommand> findAllInventory(int start, int pageSize, Long whOuId, Sort[] sorts) {
        return inventoryDao.findAllByOuid(start, pageSize, whOuId, new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class), sorts);
    }

    /**
     * 二次分拣意见，查询配货详情 fanht
     * 
     * @param pickinglistId
     * @param ouid
     * @param sorts
     * @return
     */
    @Transactional(readOnly = true)
    public List<StaLineCommand> findStaLineByPickingId(Long pickinglistId, Long ouid, Sort[] sorts) {
        return staLineDao.findStaLineByPickingId(sorts, pickinglistId, ouid, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
    }

    @Transactional(readOnly = true)
    public List<StaLineCommand> findStaLineByPickingId2(Long pickinglistId, Long ouid, Sort[] sorts) {
        return staLineDao.findStaLineByPickingId2(sorts, pickinglistId, ouid, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
    }

    /**
     * 二次分拣明细查询
     * 
     * @return
     * @throws JSONException
     */
    @Transactional(readOnly = true)
    public Map<String, Object> findStaLineBySuggestion(Long pickinglistId, String pickingCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        PickingList pl = null;
        if (pickinglistId == null) {
            pl = pickingListDao.getByCode(pickingCode);
            if (pl == null) {
                WhContainer wcn = whContainerDao.getWhContainerByCode(pickingCode);
                if (wcn == null) {
                    WhPickingBatch pbList = whPickingBatchDao.getPlzListByCode(pickingCode);
                    if (pbList != null) {
                        pl = pbList.getPickingList();
                    }
                } else {
                    pl = wcn.getPickingList();
                }
            }
        } else {
            pl = pickingListDao.getByPrimaryKey(pickinglistId);
        }
        if (pl == null) {
            map.put("pickingCodeError", "pickingCodeError");
            return map;
        }
        if (!PickingListStatus.PACKING.equals(pl.getStatus()) && !PickingListStatus.PARTLY_RETURNED.equals(pl.getStatus())) {
            map.put("pickingErrorStatus", pl.getStatus().toString());
            return map;
        }
        if (PickingListCheckMode.PICKING_CHECK.equals(pl.getCheckMode())) {
            map.put("pickingErrorStatus", pl.getStatus().toString());
            return map;
        }

        pickinglistId = pl.getId();

        Pagination<PickingListCommand> pickList = pickingListDao.findPickingListInfo(-1, -1, null, pl.getCode(), null, null, pl.getWarehouse().getId(), new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
        if (pickList == null || pickList.getItems() == null || pickList.getSize() == 0) {
            map.put("pickingCodeError", "pickingCodeError");
            return map;
        }

        map.put("pickingData", pickList.getItems().get(0));

        List<String> barcodes = new ArrayList<String>();
        // 获取核对记录
        TwicePickingBarCode tpbcList = mongoOperation.findOne(new Query(Criteria.where("pickingId").is(pickinglistId)), TwicePickingBarCode.class);


        // 查询作业单明细
        List<StaLineCommand> slcList = staLineDao.findStaLinePickingList(pickinglistId, pl.getWarehouse().getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        Map<String, Integer> ruleCodeMap = new TreeMap<String, Integer>();
        Set<String> barcode = new HashSet<String>();
        Map<String, String> partCheckMap = new HashMap<String, String>();
        barcodes.addAll(barcode);
        for (StaLineCommand slc : slcList) {
            barcode.add(slc.getBarCode());
            ruleCodeMap.put(slc.getRuleCode(), slc.getIntStatus());
            if (slc.getIntStatus() == 1) {
                slc.setStatus("新建");
            } else if (slc.getIntStatus() == 2) {
                slc.setStatus("配货中");
            } else if (slc.getIntStatus() == 3) {
                slc.setStatus("已核对");
            } else if (slc.getIntStatus() == 4) {
                slc.setStatus("已转出");
            } else if (slc.getIntStatus() == 15) {
                slc.setStatus("取消未处理");
            } else if (slc.getIntStatus() == 17) {
                slc.setStatus("取消已处理");
            } else if (slc.getIntStatus() == 10) {
                slc.setStatus("已完成");
            } else if (slc.getIntStatus() == 20) {
                slc.setStatus("配货失败");
            } else if (slc.getIntStatus() == 25) {
                slc.setStatus("冻结");
            }

            // 是否行取消
            if (slc.getIntStatus() != 1 && slc.getIntStatus() != 2 && slc.getIntStatus() != 20) {
                List<StaLineCommand> lc = staLineDao.findCancelLineBySta(slc.getStaId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (lc != null && lc.size() > 0) {
                    partCheckMap.put(slc.getRuleCode(), "partCheck");
                }
            }
        }
        map.put("partCheck", partCheckMap);
        map.put("ruleCodeMap", ruleCodeMap);

        // 重置未核对数据
        if (slcList != null && tpbcList == null) {

            tpbcList = insertCheckData(slcList, pl);
        }

        if (slcList != null && tpbcList != null) {
            resetCheckData(slcList, tpbcList);
        }
        // 数量为0重置缓存
        if (tpbcList != null) {
            resetCancelQty(pickinglistId, tpbcList.getStaCheckRecordList());

        }

        if (tpbcList != null) {
            map.put("staLineCheckList", tpbcList.getStaCheckRecordList());
        } else {
            map.put("staLineCheckList", new ArrayList<StaCheckRecord>());
        }

        map.put("staLineList", slcList);

        // 查询多条码
        List<SkuBarcodeCommand> bars = findAddSkuBarcodeByMainBarcode(barcodes, pl.getWarehouse().getId());

        Map<String, String> barcodeMap = new HashMap<String, String>();
        for (SkuBarcodeCommand skubarcode : bars) {
            String addbarcodes = barcodeMap.get(skubarcode.getMainBarcode());
            if (addbarcodes == null) {
                if (skubarcode.getBarcode() != null) {
                    addbarcodes = skubarcode.getBarcode();
                }
                barcodeMap.put(skubarcode.getMainBarcode(), addbarcodes);
            } else {
                if (skubarcode.getBarcode() != null) {
                    addbarcodes += skubarcode.getBarcode() + ",";
                }
                barcodeMap.put(skubarcode.getMainBarcode(), addbarcodes);
            }
        }

        map.put("barcodeMap", barcodeMap);

        return map;
    }

    private void resetCancelQty(Long pickinglistId, List<StaCheckRecord> scrList) {
        TwicePickingBarCode tpbcList = mongoOperation.findOne(new Query(Criteria.where("pickingId").is(pickinglistId)), TwicePickingBarCode.class);
        List<StaCheckRecord> scrList2 = tpbcList.getStaCheckRecordList();
        for (StaCheckRecord scr : scrList) {
            for (StaCheckRecord scr2 : scrList2) {
                if (scr.getStaLineId().equals(scr2.getStaLineId()) && (scr.getQty() == null || scr.getQty() == 0)) {
                    scr2.setQty(0);

                    break;
                }
            }
        }

        mongoOperation.save(tpbcList);
    }

    private void resetCheckData(List<StaLineCommand> slcList, TwicePickingBarCode tpbc) {
        List<StaCheckRecord> scrList = tpbc.getStaCheckRecordList();
        for (StaCheckRecord scr : scrList) {
            int length = slcList.size();
            for (int i = 0; i < length; i++) {
                StaLineCommand slc = slcList.get(i);
                if (slc.getId().equals(scr.getStaLineId())) {
                    scr.setIntStatus(slc.getIntStatus());
                    scr.setStatusS(slc.getStatus());
                    scr.setQty(slc.getQuantity().intValue());
                    if (slc.getQuantity() <= (scr.getCompleteQty().longValue())) {
                        slcList.remove(i);
                        i--;
                        length--;
                    } else {
                        slc.setCompleteQuantity(scr.getCompleteQty().longValue());
                    }
                }
            }
        }
        for (StaLineCommand slc : slcList) {
            int length = scrList.size();
            for (int i = 0; i < length; i++) {
                if (slc.getId().equals(scrList.get(i).getStaLineId())) {
                    scrList.remove(i);
                    i--;
                    length--;
                }
            }
        }
    }

    private TwicePickingBarCode insertCheckData(List<StaLineCommand> slcList, PickingList pl) {
        TwicePickingBarCode tpbc = new TwicePickingBarCode();
        List<StaCheckRecord> staCheckRecordList = new ArrayList<StaCheckRecord>();
        tpbc.setId(pl.getId());
        tpbc.setPickingId(pl.getId());
        tpbc.setPickingListCode(pl.getCode());
        tpbc.setStaCheckRecordList(staCheckRecordList);

        for (StaLineCommand slc : slcList) {
            StaCheckRecord scr = new StaCheckRecord();
            scr.setCompleteQty(0);
            scr.setPgIndex(slc.getPgIndex().toString());
            scr.setQty(slc.getQuantity().intValue());
            scr.setRuleCode(slc.getRuleCode());
            scr.setSkuBarcode(slc.getBarCode());
            scr.setStaCode(slc.getStaCode());
            scr.setStaLineId(slc.getId());
            scr.setSkuCode(slc.getSkuCode());
            scr.setSkuName(slc.getSkuName());
            scr.setIntStatus(slc.getIntStatus());
            scr.setStatusS(slc.getStatus());
            scr.setStaId(slc.getStaId());
            staCheckRecordList.add(scr);
        }

        mongoOperation.save(tpbc);
        return tpbc;
    }

    public List<MsgRtnOutbound> findAllMsgRtnOutbound(String source) {
        return msgRtnOutboundDao.findAllVmiMsgOutbound(source, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));

    }

    public List<GiftLineCommand> findGiftBySta(Long staId, Integer giftType) {
        return giftLineDao.findGiftBySta(staId, giftType, new BeanPropertyRowMapperExt<GiftLineCommand>(GiftLineCommand.class));
    }

    public GiftLineCommand findGiftByStaLine(Long staLineId, Integer giftType) {
        return giftLineDao.findGiftByStaLine(staLineId, giftType, new BeanPropertyRowMapperExt<GiftLineCommand>(GiftLineCommand.class));
    }

    @Override
    public List<StaLineCommand> findGiftStaLineListByStaId(Long staId) {
        return staLineDao.findStaLineGiftByStaId(staId, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));

    }

    public Pagination<InventoryCheckCommand> findAllVMIInventoryCheckList(int start, int pageSize, InventoryCheckCommand iccommand, Long ouid, Sort[] sorts) {
        if (iccommand != null) {
            iccommand.setQueryLikeParam();
        } else {
            iccommand = new InventoryCheckCommand();

        }
        return inventoryCheckDao.findAllVMIinventoryCheckByPage(start, pageSize, iccommand.getStartDate(), iccommand.getEndDate(), iccommand.getIntStatus() == -1 ? null : iccommand.getIntStatus(), iccommand.getCode(),
                StringUtils.hasText(iccommand.getSlipCode()) ? iccommand.getSlipCode() : null, ouid,StringUtils.hasText(iccommand.getOwner()) ? Long.parseLong(iccommand.getOwner()) : null,sorts, new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
    }

    public Pagination<InventoryCheckCommand> findAllInventoryCheckList(int start, int pageSize, InventoryCheckCommand iccommand, Long ouid, Sort[] sorts) {
        if (iccommand != null) {
            iccommand.setQueryLikeParam();
        } else {
            iccommand = new InventoryCheckCommand();
        }
        if (iccommand.getIntStatus() != -1) {
            return inventoryCheckDao.findAllinventoryCheckByPage(start, pageSize, iccommand.getStartDate(), iccommand.getEndDate(), iccommand.getIntStatus(), iccommand.getCode(), iccommand.getCreatorName(), iccommand.getConfirmUser(), ouid, sorts,
                    new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
        } else {
            return inventoryCheckDao.findAllinventoryCheckByPage(start, pageSize, iccommand.getStartDate(), iccommand.getEndDate(), null, iccommand.getCode(), iccommand.getCreatorName(), iccommand.getConfirmUser(), ouid, sorts,
                    new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
        }
    }

    public Pagination<InventoryCheckMoveLineCommand> findInvCheckMoveLineId(int start, int pageSize, Long invCkId, Sort[] sort) {
        return checkMoveLineDao.findInvCheckMoveLineId(start, pageSize, invCkId, sort, new BeanPropertyRowMapper<InventoryCheckMoveLineCommand>(InventoryCheckMoveLineCommand.class));
    }

    public Pagination<InventoryCommand> findCurrentInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) {
        if (inv != null) {
            inv.setQueryLikeParam1();
        } else {
            inv = new InventoryCommand();
        }
        if (inv.getExtCode2() != null) {
            if ("".equals(inv.getExtCode2())) {
                inv.setExtCode2(null);
            }
        }
        if (inv.getExtCode1() != null) {
            if ("".equals(inv.getExtCode1())) {
                inv.setExtCode1(null);
            }
        }
        Pagination<InventoryCommand> list =
                inventoryDao.findCurrentInventoryByPage(start, pageSize, inv.getBarCode(), inv.getSkuCode(), inv.getExtCode2(), inv.getJmCode(), inv.getSkuName(), inv.getSupplierSkuCode(), inv.getBrandName(), inv.getInvOwner(), whOuId, companyid,
                        inv.getIsShowZero(), inv.getExtCode1(), inv.getNumberUp(), inv.getAmountTo(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class), sorts);
        return list;
    }

    public List<InventoryCommand> findInventoryBySku(Long skuId, String owner, Long whOuId, Sort[] sorts) {
        return inventoryDao.findInventoryBySku(skuId, StringUtils.hasText(owner) ? owner : null, whOuId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class), sorts);
    }

    public Pagination<InventoryCommand> findDetailsInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) {
        if (inv != null) {
            inv.setQueryLikeParam();
        } else {
            inv = new InventoryCommand();
        }
        if (inv.getExtCode2() != null) {
            if ("".equals(inv.getExtCode2())) {
                inv.setExtCode2(null);
            }
        }
        return inventoryDao.findDetailsInventoryByPage(start, pageSize, inv.getJmCode(), inv.getSkuCode(), inv.getExtCode2(), inv.getBarCode(), inv.getSkuName(), inv.getSupplierSkuCode(), inv.getLocationCode(), inv.getInvOwner(), whOuId, companyid,
                inv.getInventoryStatusId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class), sorts);
    }

    public Pagination<WarehouseLocationCommand> findLocationList(int start, int pageSize, WarehouseLocationCommand loc, Long ouid, Sort[] sorts) {
        if (loc != null) {
            loc.setQueryLikeParam();
        } else {
            loc = new WarehouseLocationCommand();
        }
        return warehouseLocationDao.findLocationListByPage(start, pageSize, loc.getBrand(), loc.getOwner(), loc.getDistrictCode(), loc.getDistrictName(), loc.getCode(), ouid, sorts, new BeanPropertyRowMapper<WarehouseLocationCommand>(
                WarehouseLocationCommand.class));
    }

    public InventoryCommand findQtyOccupiedInv(String ouId, Long skuId, String owner) {
        InventoryCommand ocInv = inventoryDao.findQtyOccupiedInv(Long.parseLong(ouId), skuId, owner, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        return ocInv;
    }

    public InventoryCommand findSalesQtyInv(String ouId, Long skuId, String owner) {
        InventoryCommand salesqty = inventoryDao.findSalesQtyInv(Long.parseLong(ouId), skuId, owner, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        return salesqty;
    }

    @Override
    public boolean checkIsAllowPrintMacaoHGD(Long staId, Long pickinglistId) {
        if (staId != null) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta != null && sta.getIsPrintMaCaoHGD()) {
                return true;
            }
        }
        if (pickinglistId != null) {
            Long count = staDao.findMacaoHGDByPickinglistId(pickinglistId, new SingleColumnRowMapper<Long>(Long.class));
            if (count != null && count.intValue() > 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Pagination<StockTransApplicationCommand> findInBoundCartonSta(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, StockTransApplicationCommand commd, Sort[] sorts) {
        Map<String, Object> map = (commd == null ? new StockTransApplicationCommand() : commd).setQueryLikeParam();
        map.put("createTime", createTime);
        map.put("endCreateTime", endCreateTime);
        return staDao.findInBoundCartonSta(start, pageSize, ouId, map, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class), sorts);
    }
}
