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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.SkuTypeDao;
import com.jumbo.dao.baseinfo.InterfaceSecurityInfoDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.dao.invflow.WmsIMOccupiedAndReleaseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryCheckMoveLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.InvetoryChangeDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PdaSkuLocationDao;
import com.jumbo.dao.warehouse.PickingListPackageDao;
import com.jumbo.dao.warehouse.ReturnApplicationDao;
import com.jumbo.dao.warehouse.ReturnApplicationLineDao;
import com.jumbo.dao.warehouse.ReturnPackageDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaCheckDetialDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.ExpiredDateInfo;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.pac.manager.extsys.wms.rmi.model.ShopInfoResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SnFeedbackInfo;
import com.jumbo.pac.manager.extsys.wms.rmi.model.WarrantyCardResp;
import com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.ExceptionPackageType;
import com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.PackageHead;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CnInterfaceTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.task.TransInfoManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.Default.VmiDefaultManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.SkuType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelStatus;
import com.jumbo.wms.model.baseinfo.BiChannelType;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.ExpressOrderCommand;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckMoveLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryOccupyCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.InvetoryChange;
import com.jumbo.wms.model.warehouse.PdaSkuLocation;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.ReturnApplication;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;
import com.jumbo.wms.model.warehouse.ReturnApplicationLine;
import com.jumbo.wms.model.warehouse.ReturnApplicationStatus;
import com.jumbo.wms.model.warehouse.ReturnPackage;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;
import com.jumbo.wms.model.warehouse.ReturnPackageStatus;
import com.jumbo.wms.model.warehouse.ReturnReasonType;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaCheckDetialCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;


@Transactional
@Service("wareHouseManagerExe")
public class WareHouseManagerExeImpl extends BaseManagerImpl implements WareHouseManagerExe {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6037631493630487114L;

    protected static final Logger log = LoggerFactory.getLogger(WareHouseManagerExeImpl.class);
    private EventObserver eventObserver;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private InvetoryChangeDao invetoryChangeDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private WareHouseManagerExecute wmExecute;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wmsOtherOutBoundInvNoticeOmsDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private ReturnApplicationDao returnApplicationDao;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private PdaSkuLocationDao pdaSkuLocationDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private StaCheckDetialDao staCheckDetialDao;

    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private InventoryCheckDifferenceLineDao icDifferenceLineDao;
    @Autowired
    private InventoryCheckMoveLineDao icMoveLineDao;
    @Autowired
    private InventoryCheckDao icDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private SkuTypeDao skuTypeDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private PickingListPackageDao pickingListPackageDao;
    @Autowired
    private ReturnApplicationLineDao returnApplicationLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuModifyLogDao skuModifyLogDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private InterfaceSecurityInfoDao interfaceSecurityInfoDao;
    @Autowired
    private ReturnPackageDao returnPackageDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private VmiDefaultManager vmiDefaultManager;
    @Autowired
    private CnInterfaceTask cnInterfaceTask;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Resource(name = "staReaderInboundShelvesImperfect")
    private ExcelReader staReaderInboundShelves;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private TransInfoManager transInfoManager;
    @Autowired
    private WmsIMOccupiedAndReleaseDao wmsIMOccupiedAndReleaseDao;

    TimeHashMap<String, ChooseOption> chooseOptionCache = new TimeHashMap<String, ChooseOption>();
    @Autowired
    private HubWmsService hubWmsService;


    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:WareHouseManagerImpl");
        }
    }

    public InterfaceSecurityInfo findUseringUserBySource(String str, Date date) {
        return interfaceSecurityInfoDao.findUseringUserBySource(str, date);
    }

    /**
     * 退换货入库
     */
    public void inboundReturn(Long staId, String staCode, List<StvLine> lineList, User user, Date inboundTime, String memo) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND, new Object[] {staCode});
        }
        if (!sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_TYPE_ERROR, new Object[] {sta.getCode()});
        }
        if (!sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StaLine> staLineList = staLineDao.findByStaId(sta.getId());

        // 记录收货量
        Map<Long, Long> qtyMap = new HashMap<Long, Long>();
        // 合并重复行
        List<StvLine> lines = new ArrayList<StvLine>();
        Map<String, StvLine> stvLineMap = new HashMap<String, StvLine>();
        for (StvLine l : lineList) {
            Long staLineId = l.getStaLine().getId();
            String key = staLineId + "_" + l.getLocation().getId();
            if (stvLineMap.containsKey(key)) {
                StvLine temp = stvLineMap.get(key);
                temp.setQuantity(temp.getQuantity() + l.getQuantity());
                String sn = temp.getSns() == null ? "" : temp.getSns();
                temp.setSns(sn + (l.getSns() == null ? "" : l.getSns()));
            } else {
                stvLineMap.put(key, l);
                lines.add(l);
            }
            qtyMap.put(staLineId, l.getQuantity() + (qtyMap.get(staLineId) == null ? 0 : qtyMap.get(staLineId)));
        }
        // 判断执行量是否等于收货量
        for (StaLine l : staLineList) {
            Long qty = qtyMap.get(l.getId());
            if (qty == null || !qty.equals(l.getQuantity())) {
                throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_QTY_ERROE, new Object[] {sta.getCode(), l.getSku().getBarCode(), l.getInvStatus().getName()});
            }
            qtyMap.remove(l.getId());
        }

        if (qtyMap.size() > 0) {
            throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_ERROE);
        }
        sta.setInboundTime(inboundTime);
        sta.setMemo(memo);
        staDao.save(sta);
        wareHouseManager.purchaseReceiveStep1(sta.getId(), lines, null, user, null, false, true, null);
    }

    /**
     * 解冻 换货出库（在换货入库单入库完成后就做换货出库单的解冻）
     * 
     * @param slipCode
     */
    public void deblockingOutboundReturnRequest(String slipCode) {
        List<StockTransApplication> outBound = staDao.findBySlipCodeByType(slipCode, StockTransApplicationType.OUTBOUND_RETURN_REQUEST);
        if (outBound != null && outBound.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("deblockingOutboundReturnRequest staSlipCode:{}, outBound size:{}", slipCode, outBound.size());
            }
            for (StockTransApplication sta : outBound) {
                if (sta.getIsLocked() != null && sta.getIsLocked()) {
                    sta.setIsLocked(false);
                    staDao.save(sta);
                }
            }
        }
    }

    /**
     * 解冻 换货出库（在换货入库单入库完成后就做换货出库单的解冻）
     * 
     * @param slipCode
     */
    public void updateReturnPackage(StockTransApplication sta, ReturnPackageStatus status) {
        StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        if (di != null && !StringUtil.isEmpty(di.getTrackingNo())) {
            returnPackageDao.updatePackageStatus(di.getTrackingNo().trim(), ReturnPackageStatus.ALREADY_IN_STORAGE.getValue(), sta.getId(), sta.getMainWarehouse().getId());
        }
    }

    /**
     * 相关收货凭证导入
     */
    public void importInboundCertificateOfeceipt(File file, String staCode, String fileName) {
        Map<String, String> config = configManager.getCommonFTPConfig(Constants.INBOUND_CERTIFICATE_SAVE_GROUP);
        String realpath = config.get(Constants.INBOUND_CERTIFICATE_SAVE_PATH) + "/" + staCode;
        File savefile = new File(new File(realpath), FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + "_" + fileName);
        if (!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();
        try {
            // 文件复制
            FileUtils.copyFile(file, savefile);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }


    /**
     * 创建SN序列号
     * 
     * @param stv
     * @param stvLines
     */
    public void createSN(StockTransVoucher stv, List<StvLine> stvLines) {
        List<BusinessException> errors = null;
        int line = 0;
        for (StvLine each : stvLines) {
            ++line;
            if (StringUtils.hasLength(each.getSns())) {
                String[] codes = each.getSns().split(",");
                if (codes.length != each.getQuantity()) {
                    if (errors == null) {
                        errors = new ArrayList<BusinessException>();
                    }
                    errors.add(new BusinessException(ErrorCode.ERROR_QUANTITY_SNS, new Object[] {line, each.getQuantity(), codes.length}));
                } else if (errors == null) {
                    List<String> errlist = new ArrayList<String>();
                    for (String code : codes) {
                        if (!StringUtils.hasText(code)) {
                            errlist.add(code);
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});
                    for (String code : codes) {
                        SkuSn sn = new SkuSn();
                        sn.setOu(stv.getWarehouse());
                        sn.setSku(each.getSku());
                        SkuSn exitSN = snDao.findSkuSnBySnSingle(code);
                        if (exitSN != null) {
                            throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {code});
                        }
                        sn.setSn(code);
                        sn.setStatus(SkuSnStatus.USING);
                        sn.setStv(stv);
                        sn.setVersion(0);
                        try {
                            snDao.save(sn);
                        } catch (Exception e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {2, code});
                        }
                    }
                }
            }
        }
        if (errors != null && !errors.isEmpty()) businessExceptionPost(errors);
    }

    /**
     * 更新SN信息
     */
    @Override
    public void updateSN(StockTransVoucher stv, List<StvLine> stvLines) {
        List<BusinessException> errors = null;
        int line = 0;
        for (StvLine each : stvLines) {
            ++line;
            if (StringUtils.hasLength(each.getSns())) {
                String[] codes = each.getSns().split(",");
                if (codes.length != each.getQuantity()) {
                    if (errors == null) {
                        errors = new ArrayList<BusinessException>();
                    }
                    errors.add(new BusinessException(ErrorCode.ERROR_QUANTITY_SNS, new Object[] {line, each.getQuantity(), codes.length}));
                } else if (errors == null) {
                    List<String> errlist = new ArrayList<String>();
                    for (String code : codes) {
                        if (!StringUtils.hasText(code)) {
                            errlist.add(code);
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});

                    for (String code : codes) {
                        if (null == stv.getSta() || null == stv.getWarehouse()) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        // 判断是否已存在
                        SkuSn skuSn = snDao.findSkuSnBySnAndStaId(code, stv.getWarehouse().getId(), stv.getSta().getId(), SkuSnStatus.USING);
                        if (null == skuSn) {
                            throw new BusinessException(ErrorCode.PURCHASE_SN_INFO_ERROR, new Object[] {code});
                        }
                        skuSn.setStv(stv);// 关联stv
                        skuSn.setVersion((null == skuSn.getVersion() ? 0 : (skuSn.getVersion()) + 1));
                        try {
                            snDao.save(skuSn);
                        } catch (Exception e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {2, code});
                        }
                    }
                }
            }
        }
        if (errors != null && !errors.isEmpty()) businessExceptionPost(errors);
    }

    /**
     * 库存占用
     * 
     * @param staId
     */
    public void occupyInventoryCheckMethod(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<InventoryOccupyCommand> list = inventoryDao.findForOccupyInventoryCheck(staId, new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        Long skuId = null;
        Long tqty = null;
        Long locId = null;
        Long statusId = null;
        Date expireDate = null;
        for (InventoryOccupyCommand cmd : list) {
            if (skuId == null
                    || !(skuId.equals(cmd.getSkuId()) && locId.equals(cmd.getLocationId()) && ((expireDate == null && cmd.getExpDate() == null) || (expireDate != null && expireDate.equals(cmd.getExpDate()))) && statusId.equals(cmd.getStatusId()))) {
                skuId = cmd.getSkuId();
                statusId = cmd.getStatusId();
                locId = cmd.getLocationId();
                expireDate = cmd.getExpDate();
                tqty = cmd.getPlanOccupyQty() == null ? 0L : cmd.getPlanOccupyQty();
            }
            // 待占用量小于等于0表示占用完毕，继续后续商品占用
            if (tqty.longValue() <= 0L) {
                continue;
            }
            if (cmd.getQuantity().longValue() > tqty.longValue()) {
                // 库存数量大于待占用量拆分库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), tqty);
                // 插入新库存份
                wareHouseManager.saveInventoryForOccupy(cmd, cmd.getQuantity().longValue() - tqty.longValue(), null);
                // 重置待占用量
                tqty = 0L;
            } else {
                // 库存数量小于等于待占用量,直接占用库存份
                inventoryDao.occupyInvById(cmd.getId(), sta.getCode(), cmd.getQuantity().longValue());
                tqty = tqty - cmd.getQuantity().longValue();
            }
        }
        inventoryDao.flush();
        // 验证占用量
        wareHouseManager.validateOccupy(sta.getId());
    }


    /**
     * 库存占用处理
     */
    public void occupyInventoryCheckSta(Long icId) {
        InventoryCheck ic = icDao.getByPrimaryKey(icId);
        if (!InventoryCheckStatus.CHECKWHMANAGER.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        List<StockTransApplication> staList = staDao.findBySlipCodeAndStatus(ic.getCode());
        if (staList != null && staList.size() > 0) {
            for (StockTransApplication sta : staList) {
                // 占用库存
                if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                    throw new BusinessException(ErrorCode.CANCEL_STA_STATUS_ERROR, new Object[] {sta.getCode(), sta.getStatus().getValue()});
                }
                if (!StockTransApplicationType.TRANSIT_INNER.equals(sta.getType()) && !StockTransApplicationType.INVENTORY_STATUS_CHANGE.equals(sta.getType()) && !StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE.equals(sta.getType())) {
                    throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_SUPPORT_TRANSTYPE);
                }
                occupyInventoryCheckMethod(sta.getId());
                // 更具占用情况重新生成stvLine
                Long outStvId = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND).get(0).getId();
                stvLineDao.deleteByStvId(outStvId);
                stvLineDao.createTIOutByStaId(sta.getId(), outStvId);
                stvLineDao.flush();
                setInBoundOwner(sta);
                // 库存占用成功 状态变更
                sta.setStatus(StockTransApplicationStatus.OCCUPIED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse().getId());
                staDao.save(sta);
            }
        }
    }

    /**
     * 设置店铺
     * 
     * @param sta
     */
    private void setInBoundOwner(StockTransApplication sta) {
        List<StockTransVoucher> outList = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND);
        if (outList == null || outList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        List<StockTransVoucher> inList = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.INBOUND);
        if (inList == null || inList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        List<StvLine> inLine = stvLineDao.findStvLineListByStvId(inList.get(0).getId());
        List<StvLine> outLine = stvLineDao.findStvLineListByStvId(outList.get(0).getId());
        setStatusChangeLineOwner(outLine, inLine, sta.getType());
    }

    /**
     * 设置调整对应入的Owner
     * 
     * @param outLine
     * @param inLine
     * @param type
     */
    private void setStatusChangeLineOwner(List<StvLine> outLine, List<StvLine> inLine, StockTransApplicationType type) {
        Map<Long, Long> qtyMap = new HashMap<Long, Long>();
        int rank = type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) ? 5 : 0;
        do {
            for (int j = 0; j < inLine.size(); j++) {
                StvLine in = inLine.get(j);
                for (int i = 0; i < outLine.size(); i++) {
                    StvLine out = outLine.get(i);
                    if (isCharacter(in, out, type, rank)) {
                        StvLine temp = null;
                        Long outQty = qtyMap.containsKey(out.getId()) ? qtyMap.get(out.getId()) : out.getQuantity();
                        if (in.getQuantity().equals(outQty)) {
                            temp = in;
                            outLine.remove(i);
                        } else if (in.getQuantity() < outQty) {
                            temp = in;
                            qtyMap.put(out.getId(), outQty - in.getQuantity());
                        } else {
                            temp = new StvLine();
                            temp.setLocation(in.getLocation());
                            temp.setSku(in.getSku());
                            temp.setInvStatus(in.getInvStatus());
                            temp.setDistrict(in.getDistrict());
                            temp.setDirection(in.getDirection());
                            temp.setStv(in.getStv());
                            temp.setTransactionType(in.getTransactionType());
                            temp.setWarehouse(in.getWarehouse());
                            temp.setExpireDate(in.getExpireDate());
                            temp.setQuantity(outQty);
                            stvLineDao.save(temp);
                            in.setQuantity(in.getQuantity() - outQty);
                            outLine.remove(i--);
                        }
                        setLineInfo(temp, out);
                        stvLineDao.save(temp);
                        if (temp == in) {
                            inLine.remove(j--);
                            break;
                        }
                    } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank > 3) {
                        break;
                    }
                }
                if (rank == 0 && in.getOwner() == null) {
                    // 此处不应该会出现店铺为空 为了防止数据错误 做的验证！
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
            rank--;
        } while (rank > 0);
    }

    /**
     * 验证 out 和 in 是否对应
     * 
     * @param in
     * @param out
     * @param type
     * @return
     */
    private boolean isCharacter(StvLine in, StvLine out, StockTransApplicationType type, int rank) {
        if ((StringUtil.isEmpty(in.getOwner()) || in.getOwner().equals(out.getOwner())) && out.getSku().getId() == in.getSku().getId()) {
            if (type.equals(StockTransApplicationType.TRANSIT_INNER)) {
                return FormatUtil.formatDate(out.getExpireDate(), "yyyyMMddHHmmss").equals(FormatUtil.formatDate(in.getExpireDate(), "yyyyMMddHHmmss")) && out.getInvStatus().getId() == in.getInvStatus().getId();
            } else if (type.equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE)) {
                return FormatUtil.formatDate(out.getExpireDate(), "yyyyMMddHHmmss").equals(FormatUtil.formatDate(in.getExpireDate(), "yyyyMMddHHmmss")) && out.getLocation().getId() == in.getLocation().getId();
            } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank == 5) {
                return out.getInvStatus().getId() == in.getInvStatus().getId() && out.getLocation().getId() == in.getLocation().getId();
            } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank == 4) {
                return out.getInvStatus().getId() == in.getInvStatus().getId();
            } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank == 3) {
                return out.getLocation().getId() == in.getLocation().getId();
            } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank == 2) {
                return FormatUtil.formatDate(out.getExpireDate(), "yyyyMMddHHmmss").equals(FormatUtil.formatDate(in.getExpireDate(), "yyyyMMddHHmmss"));
            } else if (type.equals(StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE) && rank == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 根据库存占用更新
     * 
     * @param line
     * @param origin
     */
    private void setLineInfo(StvLine line, StvLine origin) {
        line.setOwner(origin.getOwner());
        line.setBatchCode(origin.getBatchCode());
        line.setInBoundTime(origin.getInBoundTime());
        line.setProductionDate(origin.getProductionDate());
        line.setValidDate(origin.getValidDate());
        line.setSkuCost(origin.getSkuCost());
        line.setStaLine(origin.getStaLine());
        if (line.getLocation() == null) {
            line.setLocation(origin.getLocation());
            line.setDistrict(origin.getDistrict());
        }
    }


    /**
     * 盘点确认
     */
    public void invCheckDetermine(String code, User user) {
        InventoryCheck ic = icDao.findByCode(code);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND, code);
        }
        if (!InventoryCheckStatus.CREATED.equals(ic.getStatus()) && !InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, ic.getCode());
        }
        ic.setStatus(InventoryCheckStatus.CHECKWHINVENTORY);
        ic.setOperatorTime(new Date());
        ic.setOperatorUserId(user);
        calculationAdjustingFordifferences(ic.getId(), user.getId());
    }

    /**
     * 盘点调整单取消
     * 
     * @param icId
     * @param user
     */
    public void cancelAdjustingSta(Long icId, User user) {
        InventoryCheck ic = icDao.getByPrimaryKey(icId);
        if (!InventoryCheckStatus.CANCELED.equals(ic.getStatus()) && !InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        List<StockTransApplication> staList = staDao.findBySlipCodeAndStatus(ic.getCode());
        for (StockTransApplication sta : staList) {
            if (StockTransApplicationType.TRANSIT_INNER.equals(sta.getType()) || StockTransApplicationType.INVENTORY_STATUS_CHANGE.equals(sta.getType()) || StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE.equals(sta.getType())) {
                if (StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                    wareHouseManagerCancel.cancelTranistInner(sta.getId(), user != null ? user.getId() : null);
                } else if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                    wareHouseManagerCancel.cancelTranistInner(sta.getId(), user != null ? user.getId() : null);
                } else {
                    throw new BusinessException(ErrorCode.CANCEL_STA_STATUS_ERROR, new Object[] {sta.getCode(), sta.getStatus().getValue()});
                }
            } else {
                throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_SUPPORT_TRANSTYPE);
            }
        }
        // 针对 盘点库存已确认 取消 盘点状态回滚到 差异未处理
        if (InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) {
            List<InventoryCheckMoveLine> moveList = icMoveLineDao.findInvCheckMoveList(ic.getId());
            List<InventoryCheckDifferenceLine> differenceList = icDifferenceLineDao.findByInventoryCheck(ic.getId());
            for (InventoryCheckMoveLine move : moveList) {
                InventoryCheckDifferenceLine difBean = null;
                for (InventoryCheckDifferenceLine dif : differenceList) {
                    if (move.getSku().getId() == dif.getSku().getId() && move.getLocation().getId() == dif.getLocation().getId() && move.getStatus().getId() == dif.getStatus().getId()
                            && (move.getOwner() == null || dif.getOwner() == null || move.getOwner().equals(dif.getOwner())) && (move.getExpireDate() == null || dif.getExpireDate() == null || move.getExpireDate().equals(dif.getExpireDate()))
                            && (move.getProductionDate() == null || dif.getProductionDate() == null || move.getProductionDate().equals(dif.getProductionDate()))) difBean = dif;
                }
                if (difBean == null) {
                    InventoryCheckDifferenceLine difLine = new InventoryCheckDifferenceLine();
                    difLine.setDistrict(move.getLocation().getDistrict());
                    difLine.setExpireDate(move.getExpireDate());
                    difLine.setInventoryCheck(move.getInventoryCheck());
                    difLine.setOwner(move.getOwner());
                    difLine.setProductionDate(move.getProductionDate());
                    difLine.setQuantity(move.getQuantity());
                    difLine.setLocation(move.getLocation());
                    difLine.setSkuCost(move.getSkuCost());
                    difLine.setSku(move.getSku());
                    difLine.setStatus(move.getStatus());
                    difLine.setStoreMode(move.getStoreMode());
                    icDifferenceLineDao.save(difLine);
                } else {
                    difBean.setQuantity(difBean.getQuantity() + move.getQuantity());
                }
                icMoveLineDao.delete(move);
            }
        }
    }

    public void exeAdjustingSta(Long icId, User user) throws Exception {
        InventoryCheck ic = icDao.getByPrimaryKey(icId);
        if (!InventoryCheckStatus.FINISHED.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        List<StockTransApplication> staList = staDao.findBySlipCodeAndStatus(ic.getCode());
        for (StockTransApplication sta : staList) {
            if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                if (StockTransApplicationType.TRANSIT_INNER.equals(sta.getType())) {
                    executeExtTransitInner(sta.getId(), user != null ? user.getId() : null, true, false);
                } else if (StockTransApplicationType.INVENTORY_STATUS_CHANGE.equals(sta.getType())) {
                    executeInvStatusChangeForImpory(sta.getId(), user != null ? user.getId() : null, true, false);
                } else if (StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE.equals(sta.getType())) {
                    executeExtTransitInner(sta.getId(), user != null ? user.getId() : null, true, false);
                } else {
                    throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_SUPPORT_TRANSTYPE);
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CRANCEL_ERROR1, new Object[] {sta.getCode()});
            }
        }
    }

    /**
     * 计算盘点差异调整
     */
    public void calculationAdjustingFordifferences(Long icId, Long userId) {
        InventoryCheck ic = icDao.getByPrimaryKey(icId);
        if (!InventoryCheckStatus.CHECKWHINVENTORY.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        List<InventoryCheckDifferenceLineCommand> icLineList = icDifferenceLineDao.getSortingDifferentByInvCheck(icId, new BeanPropertyRowMapper<InventoryCheckDifferenceLineCommand>(InventoryCheckDifferenceLineCommand.class));
        Map<String, List<InventoryCheckDifferenceLineCommand>> inMap = new HashMap<String, List<InventoryCheckDifferenceLineCommand>>();
        Map<String, List<InventoryCheckDifferenceLineCommand>> outMap = new HashMap<String, List<InventoryCheckDifferenceLineCommand>>();
        List<String> listLocation = new ArrayList<String>();
        List<String> listInvStatus = new ArrayList<String>();
        List<String> listSku = new ArrayList<String>();
        for (InventoryCheckDifferenceLineCommand icLine : icLineList) {
            Map<String, List<InventoryCheckDifferenceLineCommand>> tempMap = null;
            String keySku = icLine.getSkuId().toString() + "_" + icLine.getOwner();
            String sKey = keySku + "_" + icLine.getSexpireDate();
            String keyLocation = sKey + "_" + icLine.getInvStatusId();
            String keyInvStatus = sKey + "_" + icLine.getLocationId();
            if (icLine.getQuantity() > 0) {
                tempMap = inMap;
                if (!listLocation.contains(keyLocation)) {
                    listLocation.add(keyLocation);
                }
                if (!listInvStatus.contains(keyInvStatus)) {
                    listInvStatus.add(keyInvStatus);
                }
                if (!listSku.contains(keySku)) {
                    listSku.add(keySku);
                }
            } else {
                tempMap = outMap;
            }
            mapPut(tempMap, keySku, icLine);
            mapPut(tempMap, keyLocation, icLine);
            mapPut(tempMap, keyInvStatus, icLine);
        }
        if (inMap.size() == 0 || outMap.size() == 0) return;

        // 判断是否存在 同商品同过期时间同库存状态 不同库位数据
        Map<String, List<StvLine>> localtionMap = new HashMap<String, List<StvLine>>();
        if (validateByMap(listLocation, inMap, outMap, localtionMap)) {
            StockTransApplication sta = createStaByAdj(localtionMap, ic, StockTransApplicationType.TRANSIT_INNER, userId);
            if (sta != null) {
                saveInventoryCheckMoveLine(sta, ic, localtionMap.get("in"));
                saveInventoryCheckMoveLine(sta, ic, localtionMap.get("out"));
            }
        }

        // 判断是否存在 同商品同过期时间同库存状态 不同库位数据
        Map<String, List<StvLine>> invStatusMap = new HashMap<String, List<StvLine>>();
        if (validateByMap(listInvStatus, inMap, outMap, invStatusMap)) {
            StockTransApplication sta = createStaByAdj(invStatusMap, ic, StockTransApplicationType.INVENTORY_STATUS_CHANGE, userId);
            if (sta != null) {
                saveInventoryCheckMoveLine(sta, ic, invStatusMap.get("in"));
                saveInventoryCheckMoveLine(sta, ic, invStatusMap.get("out"));
            }
        }
        // 剩余可以调整的数据做 保质期修改调整
        Map<String, List<StvLine>> olSku = new HashMap<String, List<StvLine>>();
        if (validateByMap(listSku, inMap, outMap, olSku)) {
            StockTransApplication sta = createStaByAdj(olSku, ic, StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE, userId);
            if (sta != null) {
                saveInventoryCheckMoveLine(sta, ic, olSku.get("in"));
                saveInventoryCheckMoveLine(sta, ic, olSku.get("out"));
            }
        }
        icDifferenceLineDao.flush();
        // 检查 存在调整数据
        for (InventoryCheckDifferenceLineCommand line : icLineList) {
            // 是否有改动
            if (line.getQuantity() != line.getOriginalQuantity()) {
                if (line.getQuantity() == 0) {
                    icDifferenceLineDao.deleteById(line.getId());
                } else {
                    icDifferenceLineDao.updateLineQty(line.getId(), line.getQuantity());
                }
            }
        }
    }

    private StockTransApplication createStaByAdj(Map<String, List<StvLine>> map, InventoryCheck ic, StockTransApplicationType type, Long userId) {
        if (map == null || map.size() == 0 || ic == null || type == null) {
            return null;
        }
        List<StvLine> inLine = map.get("in");
        List<StvLine> outLine = map.get("out");
        if (inLine == null || inLine.size() == 0 || outLine == null || outLine.size() == 0) {
            return null;
        }
        return createSta(inLine, outLine, type, ic, userId);
    }


    public StockTransApplication createSta(List<StvLine> inLine, List<StvLine> outLine, StockTransApplicationType type, InventoryCheck ic, Long userId) {
        if ((inLine == null || inLine.size() == 0) && (outLine == null || outLine.size() == 0)) {
            throw new BusinessException(ErrorCode.TRANIST_INNER_LINE_EMPTY);
        }
        if (type == null) {
            throw new BusinessException(ErrorCode.PREDEFINED_STA_TYPE_INCORRECT);
        }
        // 判断 出 和 入 数量是否正确
        boolean isTransitInner = false;
        boolean isInvStatusChange = false;
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        if (type.equals(StockTransApplicationType.TRANSIT_INNER)) {
            isTransitInner = true;
        } else if (type.equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE)) {
            isInvStatusChange = true;
        }
        for (StvLine l : outLine) {
            String key = l.getSku().getCode() + "_" + (isTransitInner ? l.getInvStatus().getId() : (isInvStatusChange ? l.getLocation().getId() : ""));
            if (qtyMap.containsKey(key)) {
                qtyMap.put(key, qtyMap.get(key) + l.getQuantity());
            } else {
                qtyMap.put(key, l.getQuantity());
            }
        }
        for (StvLine l : inLine) {
            String key = l.getSku().getCode() + "_" + (isTransitInner ? l.getInvStatus().getId() : (isInvStatusChange ? l.getLocation().getId() : ""));
            Long qty = qtyMap.get(key);
            if (qty != null) {
                qtyMap.put(key, qty - l.getQuantity());
            } else {
                throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND, new Object[] {key});
            }
        }
        for (String key : qtyMap.keySet()) {
            if (qtyMap.get(key) != 0) {
                throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND, new Object[] {key});
            }
        }
        TransactionType outType = transactionTypeDao.findByCode(TransactionType.returnTypeOutbound(type));
        if (outType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {type});
        }
        TransactionType inType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(type));
        if (inType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {type});
        }
        User user = null;
        if (userId != null) {
            userDao.getByPrimaryKey(userId);
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        if (userId != null) {
            sta.setCreator(user);
        }
        sta.setRefSlipCode(ic.getCode());
        sta.setOwner(ic.getShop() != null ? ic.getShop().getCode() : null);
        sta.setCreator(ic.getCreator());
        sta.setIsNeedOccupied(false);
        sta.setMainWarehouse(ic.getOu());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setType(type);
        sta.setIsNotPacsomsOrder(true);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        staDao.save(sta);
        Map<String, StaLine> staLineMap = new HashMap<String, StaLine>();
        for (StvLine l : outLine) {
            // 合并重复行
            String key = l.getSku().getId() + "_" + (l.getInvStatus() == null ? "null" : l.getInvStatus().getId()) + (l.getOwner() == null ? sta.getOwner() : l.getOwner());
            if (staLineMap.containsKey(key)) {
                StaLine staLine = staLineMap.get(key);
                staLine.setQuantity(staLine.getQuantity() + l.getQuantity());
                staLineDao.save(staLine);
            } else {
                StaLine staLine = new StaLine();
                staLine.setInvStatus(l.getInvStatus());
                staLine.setSku(l.getSku());
                staLine.setOwner(l.getOwner() == null ? sta.getOwner() : l.getOwner());
                staLine.setQuantity(l.getQuantity());
                staLine.setSta(sta);
                staLineDao.save(staLine);
                staLineMap.put(key, staLine);
            }
        }
        createStv(sta, sta.getCode() + "01", outType, outLine);
        createStv(sta, sta.getCode() + "02", inType, inLine);
        return sta;
    }

    public StockTransVoucher createStv(StockTransApplication sta, String stvCode, TransactionType type, List<StvLine> lineList) {
        StockTransVoucher stv = new StockTransVoucher();
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        stv.setCode(stvCode);
        stv.setCreateTime(new Date());
        stv.setCreator(sta.getCreator());
        stv.setDirection(type.getDirection());
        stv.setSta(sta);
        stv.setOwner(sta.getOwner());
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setTransactionType(type);
        stv.setWarehouse(sta.getMainWarehouse());
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        Long customerId = null;
        stvDao.save(stv);
        if (wh != null && wh.getCustomer() != null) {
            customerId = wh.getCustomer().getId();
        }
        for (StvLine stvl : lineList) {
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
            // if (!StringUtils.hasText(stvl.getOwner())) {
            // throw new BusinessException(ErrorCode.OWNER_IS_NULL);
            // }
            stvl.setLocation(loc);
            stvl.setSku(sku);
            stvl.setInvStatus(sts);
            stvl.setDistrict(stvl.getLocation().getDistrict());
            stvl.setOwner(stvl.getOwner() == null ? stv.getOwner() : null);
            stvl.setDirection(stv.getDirection());
            stvl.setStv(stv);
            stvl.setTransactionType(type);
            stvl.setWarehouse(stv.getWarehouse());
            stvLineDao.save(stvl);
        }
        stvDao.flush();
        return stv;
    }


    /**
     * 调整记录save
     * 
     * @param sta
     * @param ic
     * @param lineList
     */
    private void saveInventoryCheckMoveLine(StockTransApplication sta, InventoryCheck ic, List<StvLine> lineList) {
        if (sta == null || lineList == null || lineList.size() == 0) return;
        for (StvLine line : lineList) {
            InventoryCheckMoveLine l = new InventoryCheckMoveLine();
            l.setExpireDate(line.getExpireDate());
            l.setVersion(new Date());
            l.setInventoryCheck(ic);
            l.setLocation(line.getLocation());
            l.setOwner(line.getOwner());
            l.setProductionDate(line.getProductionDate());
            l.setStatus(line.getInvStatus());
            l.setQuantity(line.getDirection().equals(TransactionDirection.INBOUND) ? line.getQuantity() : (line.getQuantity() * -1));
            l.setSku(line.getSku());
            l.setSkuCost(l.getSkuCost());
            l.setSta(sta);
            icMoveLineDao.save(l);
        }
    }

    /**
     * 验证 盘点调整数据
     * 
     * @param keys
     * @param inMap
     * @param outMap
     * @return
     */
    private boolean validateByMap(List<String> keys, Map<String, List<InventoryCheckDifferenceLineCommand>> inMap, Map<String, List<InventoryCheckDifferenceLineCommand>> outMap, Map<String, List<StvLine>> adjMap) {
        boolean result = false;
        if (keys != null && keys.size() > 0) {
            Map<String, StvLine> staLineMap = new HashMap<String, StvLine>();
            List<StvLine> inLine = new ArrayList<StvLine>();
            List<StvLine> outLine = new ArrayList<StvLine>();
            adjMap.put("in", inLine);
            adjMap.put("out", outLine);
            for (String key : keys) {
                if (outMap.containsKey(key) && inMap.containsKey(key)) {
                    List<InventoryCheckDifferenceLineCommand> outList = outMap.get(key);
                    List<InventoryCheckDifferenceLineCommand> inList = inMap.get(key);
                    for (InventoryCheckDifferenceLineCommand out : outList) {
                        for (InventoryCheckDifferenceLineCommand in : inList) {
                            if (in.getQuantity() == 0) continue;
                            if (out.getQuantity() == 0) break;
                            long quantity = 0;
                            if ((out.getQuantity() + in.getQuantity()) < 0) {
                                quantity = in.getQuantity();
                                out.setQuantity(out.getQuantity() + quantity);
                                in.setQuantity(0l);
                            } else {
                                quantity = out.getQuantity() * -1;
                                out.setQuantity(0l);
                                in.setQuantity(in.getQuantity() - quantity);
                            }
                            Sku sku = new Sku();
                            sku.setId(in.getSkuId());
                            String outKey = out.getSkuId().toString() + "_" + out.getOwner() + "_" + out.getSexpireDate() + "_" + out.getInvStatusId() + "_" + out.getLocationId();
                            if (staLineMap.containsKey(outKey + "_o")) {
                                StvLine outStvLine = staLineMap.get(outKey + "_o");
                                outStvLine.setQuantity(quantity + outStvLine.getQuantity());
                            } else {
                                setStvLine(sku, out.getLocationId(), out.getInvStatusId(), out.getOwner(), quantity, out.getExpireDate(), out.getProductionDate(), TransactionDirection.OUTBOUND, outKey + "_o", outLine, staLineMap);
                            }
                            String inKey = in.getSkuId().toString() + "_" + in.getOwner() + "_" + in.getSexpireDate() + "_" + in.getInvStatusId() + "_" + in.getLocationId();
                            if (staLineMap.containsKey(inKey + "_o")) {
                                StvLine inStvLine = staLineMap.get(inKey + "_i");
                                inStvLine.setQuantity(quantity + inStvLine.getQuantity());
                            } else {
                                setStvLine(sku, in.getLocationId(), in.getInvStatusId(), in.getOwner(), quantity, in.getExpireDate(), in.getProductionDate(), TransactionDirection.INBOUND, inKey + "_i", inLine, staLineMap);
                            }
                        }
                    }
                }
            }
            result = inLine.size() > 0;
        }
        return result;
    }

    private void setStvLine(Sku sku, Long locationId, Long invStatusId, String owner, Long quantity, Date expireDate, Date productionDate, TransactionDirection direction, String mapKey, List<StvLine> lineList, Map<String, StvLine> map) {
        StvLine line = new StvLine();
        WarehouseLocation loc = new WarehouseLocation();
        loc.setId(locationId);
        InventoryStatus invStatus = new InventoryStatus();
        invStatus.setId(invStatusId);
        line.setOwner(owner);
        line.setSku(sku);
        line.setLocation(loc);
        line.setInvStatus(invStatus);
        line.setQuantity(quantity);
        line.setDirection(direction);
        line.setExpireDate(expireDate);
        line.setProductionDate(productionDate);
        map.put(mapKey, line);
        lineList.add(line);
    }

    public void mapPut(Map<String, List<InventoryCheckDifferenceLineCommand>> map, String key, InventoryCheckDifferenceLineCommand bean) {
        if (map == null || key == null) return;
        List<InventoryCheckDifferenceLineCommand> list = null;
        if (map.containsKey(key)) {
            list = map.get(key);
        } else {
            list = new ArrayList<InventoryCheckDifferenceLineCommand>();
            map.put(key, list);
        }
        list.add(bean);
    }


    /**
     * 特殊商品处理
     */
    public void createGiftLine(List<GiftLineCommand> giftLineList) {
        if (giftLineList != null) {
            for (GiftLineCommand giftComd : giftLineList) {
                if ((giftComd.getStaLine() == null || giftComd.getStaLine().getId() == null) && giftComd.getStaLineId() == null) {
                    throw new BusinessException(ErrorCode.CREATE_GIFTLINE_STALINE_IS_NULL, new Object[] {"TYPE:" + giftComd.getType() == null ? giftComd.getIntType() : giftComd.getType().getValue() + ";MEMO:" + giftComd.getMemo() + ";SanCardCode"
                            + giftComd.getSanCardCode()});
                }
                if (giftComd.getIntType() == null && giftComd.getType() == null) {
                    throw new BusinessException(ErrorCode.CREATE_GIFTLINE_TYPE_IS_NULL, new Object[] {"MEMO:" + giftComd.getMemo() + ";SanCardCode" + giftComd.getSanCardCode()});
                }
                GiftLine gift = new GiftLine();
                gift.setMemo(giftComd.getMemo());
                gift.setSanCardCode(giftComd.getSanCardCode());
                gift.setType(giftComd.getType() == null ? GiftType.valueOf(giftComd.getIntType()) : giftComd.getType());
                if (giftComd.getStaLineId() != null) {
                    StaLine staLine = new StaLine();
                    staLine.setId(giftComd.getStaLineId());
                    gift.setStaLine(staLine);
                } else {
                    gift.setStaLine(giftComd.getStaLine());
                }
                giftLineDao.save(gift);
            }
        }
    }

    public List<SkuSn> findEmploySkuSN(Long staId) {
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvs == null || stvs.size() != 1) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        return snDao.findSkuSnListByStv(stvs.get(0).getId(), SkuSnStatus.CHECKING);
    }

    @Override
    public void validateBiChannelSupport(StockTransVoucher stv, String owner) {
        if (owner != null) {
            validateOwnere(owner);
        }
        if (stv != null) {
            if (TransactionDirection.INBOUND.equals(stv.getDirection())) {
                if (stv == null || stv.getId() == null) throw new BusinessException(ErrorCode.VALIDATE_BI_CHANNEL_ERROR);
                List<StvLine> lineList = stvLineDao.findStvLineListByStvId(stv.getId());
                for (StvLine line : lineList) {
                    validateOwnere(line.getOwner());
                }
            }
        }
    }

    @Override
    public List<StvLineCommand> getOutBoundBachCode(StockTransApplicationType staType, String staCode, String slipCode, String slipCode1, String code) {
        List<StvLineCommand> stvLineList = new ArrayList<StvLineCommand>();
        if (staType != null && StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(staType)) {
            // ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("snOrExpDate", "1");
            ChooseOption ch = getChooseOptionCache("snOrExpDate");
            if (ch != null && ch.getOptionValue().equals("1")) {
                StockTransApplication sta = staDao.getByCode(code);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(new Date());
                Date inBoundTime;
                try {
                    inBoundTime = sdf.parse(date);
                } catch (ParseException e) {
                    inBoundTime = new Date();
                }
                List<StaLineCommand> stvList = staLineDao.findLineAndSkuByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (stvList != null && stvList.size() > 0) {
                    String batchCode = String.valueOf(System.currentTimeMillis());
                    for (StaLineCommand stvLine : stvList) {
                        if (stvLine.getIsSn() != null && stvLine.getIsSn()) {
                            List<StaCheckDetialCommand> staCheckList = staCheckDetialDao.findSnAllBySkuIdStaId(sta.getId(), stvLine.getSkuId(), new BeanPropertyRowMapper<StaCheckDetialCommand>(StaCheckDetialCommand.class));
                            if (staCheckList != null && staCheckList.size() > 0) {
                                for (StaCheckDetialCommand staCheck : staCheckList) {
                                    StvLineCommand stvLineCommand = new StvLineCommand();
                                    stvLineCommand.setSkuId(stvLine.getSkuId());
                                    stvLineCommand.setQuantity(staCheck.getQty());
                                    stvLineCommand.setOwner(stvLine.getOwner());
                                    stvLineCommand.setSns(staCheck.getSn());
                                    stvLineCommand.setBatchCode(batchCode);
                                    stvLineCommand.setInBoundTime(inBoundTime);
                                    stvLineList.add(stvLineCommand);
                                }
                            }
                        } else if (stvLine.getEffectSku() != null && stvLine.getEffectSku() == 33) {
                            List<StaCheckDetialCommand> staCheckList = staCheckDetialDao.findExpDateAllBySkuIdStaId(sta.getId(), stvLine.getSkuId(), new BeanPropertyRowMapper<StaCheckDetialCommand>(StaCheckDetialCommand.class));
                            for (StaCheckDetialCommand staCheck : staCheckList) {
                                StvLineCommand stvLineCommand = new StvLineCommand();
                                stvLineCommand.setSkuId(stvLine.getSkuId());
                                stvLineCommand.setQuantity(staCheck.getQty());
                                stvLineCommand.setOwner(stvLine.getOwner());
                                stvLineCommand.setExpireDate(staCheck.getExpDate());
                                stvLineCommand.setBatchCode(batchCode);
                                stvLineCommand.setInBoundTime(inBoundTime);
                                stvLineList.add(stvLineCommand);
                            }
                        } else {
                            StvLineCommand stvLineCommand = new StvLineCommand();
                            stvLineCommand.setSkuId(stvLine.getSkuId());
                            stvLineCommand.setQuantity(stvLine.getQuantity());
                            stvLineCommand.setOwner(stvLine.getOwner());
                            stvLineCommand.setBatchCode(batchCode);
                            stvLineCommand.setInBoundTime(inBoundTime);
                            stvLineList.add(stvLineCommand);
                        }

                    }
                }
                return stvLineList;
            } else {
                // 获取前置单据是否 合并订单
                String groupStaCode = staDao.findGroupStaCode(slipCode1, new SingleColumnRowMapper<String>(String.class));
                if (StringUtil.isEmpty(groupStaCode)) {
                    stvLineList = stvLineDao.findOutBatchCode(null, null, null, slipCode1, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    if (stvLineList == null || stvLineList.size() == 0) {
                        stvLineList = stvLineDao.findOutBatchCodeBackups(null, null, null, slipCode1, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    }
                    return stvLineList;
                } else {
                    stvLineList = stvLineDao.findOutBatchCode(groupStaCode, null, null, slipCode1, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    if (stvLineList == null || stvLineList.size() == 0) {
                        stvLineList = stvLineDao.findOutBatchCodeBackups(groupStaCode, null, null, slipCode1, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    }
                    return stvLineList;
                }
            }

        }
        stvLineList = stvLineDao.findOutBatchCode(null, staCode, slipCode, slipCode1, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        return stvLineList;
    }

    private void validateOwnere(String owner) {
        BiChannel channel = biChannelDao.getByCode(owner);
        if (channel == null) {
            throw new BusinessException(ErrorCode.CHANNEL_NOT_FOUNT, new Object[] {owner});
        }
        if (channel.getType() == null ? false : BiChannelType.VIRTUAL.equals(channel.getType())) {
            throw new BusinessException(ErrorCode.VALIDATE_BI_CHANNEL_TYPE_ERROR, new Object[] {channel.getName()});
        }
        if (channel.getStatus() == null ? false : BiChannelStatus.CANCEL.equals(channel.getStatus())) {
            throw new BusinessException(ErrorCode.VALIDATE_BI_CHANNEL_STATUS_ERROR, new Object[] {channel.getName()});
        }
    }

    @Override
    public String returnPackageRegister(List<ReturnPackageCommand> rpList, OperationUnit ou, User user) {
        if (rpList == null || rpList.size() == 0) {
            return null;
        }
        Long index = returnPackageDao.getReturnPackageSequence(new SingleColumnRowMapper<Long>(Long.class));
        String indexStr = index.toString();
        String batchCode = FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + (index > 1000 ? (indexStr.substring(indexStr.length() - 4, indexStr.length())) : index);
        for (ReturnPackageCommand comm : rpList) {
            if (StringUtil.isEmpty(comm.getLpcode())) {
                throw new BusinessException(ErrorCode.RETURN_PACKAGE_LPCODE_IS_NULL);
            }
            if (comm.getIntStatus() == null) {
                throw new BusinessException(ErrorCode.RETURN_PACKAGE_TYPE_IS_NULL);
            }
            if (StringUtil.isEmpty(comm.getTrackingNo())) {
                throw new BusinessException(ErrorCode.RETURN_PACKAGE_TRACKINGNO_IS_NULL);
            }
            String trackingNo = comm.getTrackingNo().trim();
            List<ReturnPackage> pgs = returnPackageDao.getPackageByTrackingNo(trackingNo);
            ReturnPackage pg = null;
            if (pgs.size() == 1 || pgs.size() == 2) {
                pg = pgs.get(0);
                String tempStatus = "";// 错误提示
                String tempStatus2 = "";// 错误提示
                String tempWhName = "没绑定物理仓";
                if (pg.getStatus().equals(ReturnPackageStatus.REFUSE_TO_ACCEPT)) {
                    tempStatus = "拒收";
                } else {
                    tempStatus = "录入";
                }
                if (comm.getIntStatus() == 0) {
                    tempStatus2 = "拒收";
                } else {
                    tempStatus2 = "录入";
                }
                if (pg.getPwOu() != null) {
                    PhysicalWarehouse se = physicalWarehouseDao.getByPrimaryKey(pg.getPwOu().getId());
                    if (se != null) {
                        tempWhName = se.getName();
                    }
                }
                if (pgs.size() == 2) {
                    throw new BusinessException(ErrorCode.RETURN_PACKAGE_TRACKINGNO_REPEAT, new Object[] {trackingNo, tempWhName, tempStatus, tempStatus2});
                }
                if (pgs.size() == 1 && pg.getStatus().equals(ReturnPackageStatus.valueOf(comm.getIntStatus()))) {
                    throw new BusinessException(ErrorCode.RETURN_PACKAGE_TRACKINGNO_REPEAT, new Object[] {trackingNo, tempWhName, tempStatus, tempStatus2});
                }
            }
            pg = new ReturnPackage();
            pg.setCreator(user);
            pg.setCreateTime(new Date());
            pg.setLastModifyTime(new Date());
            pg.setLpcode(comm.getLpcode());
            pg.setRemarksb(comm.getRemarksb());
            pg.setTrackingNo(trackingNo);
            pg.setRejectionReasons(comm.getRejectionReasons());
            pg.setStatus(ReturnPackageStatus.valueOf(comm.getIntStatus()));
            pg.setBatchCode(batchCode);
            // 增加重量字段
            if (comm.getWeight() != null) {
                pg.setWeight(comm.getWeight().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            PhysicalWarehouse se = physicalWarehouseDao.getByPrimaryKey(comm.getPhyWhId());
            if (se == null) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
            pg.setPwOu(se);
            returnPackageDao.save(pg);
        }
        return batchCode;
    }

    @Override
    public boolean lpCodeWhetherValid(String code) {
        Transportator Transportator = transportatorDao.findTransportatorByExpCode(code);
        if (null != Transportator) {
            return true;
        }
        return false;
    }

    /**
     * 作业单解冻
     */
    public void unfreezeByStaid(StockTransApplication staParam, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staParam.getId());
        if (sta == null) throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        if (sta.getStatus().getValue() != StockTransApplicationStatus.FROZEN.getValue()) {
            throw new BusinessException(ErrorCode.STA_IS_NOT_FROZEN, new Object[] {sta.getCode()});
        }
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        sta.setLastModifyTime(new Date());
        sta.setInvoiceNumber(staParam.getInvoiceNumber());
        sta.setCurrency(staParam.getCurrency());
        sta.setSkuQty(staParam.getSkuQty());
        sta.setTotalFOB(staParam.getTotalFOB());
        sta.setTotalGTP(staParam.getTotalGTP());
        sta.setDutyPercentage(staParam.getDutyPercentage());
        sta.setMiscFeePercentage(staParam.getMiscFeePercentage());
        staDao.save(sta);
        return;
    }

    public void executeExtTransitInner(Long staId, Long operatorId, boolean isNotVolidateExpireDate, boolean isTransactionType) throws Exception {
        User user = null;
        if (operatorId != null) {
            user = userDao.getByPrimaryKey(operatorId);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher outStv = stvList.get(0);
        wareHouseManager.removeInventory(sta, stvList.get(0));
        outStv.setFinishTime(new Date());
        outStv.setOperator(user);
        outStv.setStatus(StockTransVoucherStatus.FINISHED);
        outStv.setLastModifyTime(new Date());
        stvDao.save(outStv);
        stvDao.flush();

        List<StockTransVoucher> inStvList = stvDao.findByStaWithDirection(staId, TransactionDirection.INBOUND);
        if (inStvList == null || inStvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        wareHouseManager.purchaseReceiveStep2(inStvList.get(0).getId(), inStvList.get(0).getStvLines(), true, user, isNotVolidateExpireDate, isTransactionType);
    }


    @Override
    public Map<String, Object> o2oQsSalesStaOutBound(Long plpId, Long userId, Long ouid, String trackingNo, BigDecimal weight, List<StaAdditionalLine> saddlines) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> staCodeList = new ArrayList<String>();
        // 出库操作
        if (plpId != null) {
            List<StockTransApplication> staList = staDao.findAllStaByPickingListPackageId(plpId);
            try {
                for (StockTransApplication staId : staList) {
                    StockTransApplication sta = staDao.getByPrimaryKey(staId.getId());
                    boolean bool = wareHouseManager.salesStaOutBound(staId.getId(), userId, ouid, trackingNo, weight, saddlines, false,null);
                    if (bool) {
                        staCodeList.add(sta.getCode());
                        sta.setStatus(StockTransApplicationStatus.FINISHED);
                        staDao.save(sta);
                    } else {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }

                }
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    throw (BusinessException) e;
                } else {
                    log.error("", e);
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        PickingListPackage plp = pickingListPackageDao.getByPrimaryKey(plpId);
        plp.setWeight(weight);
        if (saddlines != null && saddlines.size() > 0) {
            String barCode = saddlines.get(0).getSku().getBarCode();
            Sku sku = skuDao.getByBarcode1(barCode);
            if (sku == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            plp.setSkuId(sku);
        }
        plp.setStatus(DefaultStatus.FINISHED);
        map.put("staCodeList", staCodeList);
        return map;
    }

    private boolean isNeedRecordType(StockTransApplication sta) {
        StockTransApplicationType type = sta.getType();
        switch (type) {
            case INBOUND_SETTLEMENT:// 结算经销入库
            case INBOUND_CONSIGNMENT:// 代销入库
            case INBOUND_MOBILE:// 移库入库
            case INBOUND_OTHERS:// 其他入库
            case VMI_INBOUND_CONSIGNMENT:// WMI移库入库
            case TRANSIT_CROSS:// 库间移动
                return true;
            case INVENTORY_STATUS_CHANGE:// 库存状态修改
                if (sta.getRefSlipCode() == null || sta.getRefSlipCode().startsWith("I")) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public void invChangeLogNoticePac(Long staId, Long stvId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (!StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            try {
                Thread.sleep(60 * 1000);
                sta = staDao.getByPrimaryKey(staId);
            } catch (InterruptedException e) {}
        }
        if (!StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            log.error("sta not finish ,staID :{}", staId);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        StockTransApplicationType staType = sta.getType();
        SlipType staSlipType = sta.getRefSlipType();
        Date maxTransactionTime = stv.getFinishTime();
        if (maxTransactionTime == null) {
            maxTransactionTime = new Date();
        }
        OperationBill operationBill = new OperationBill();
        operationBill.setMaxTransactionTime(maxTransactionTime);
        operationBill.setCode(stv.getSta().getCode());
        operationBill.setWhCode(stv.getWarehouse().getCode());
        if (staSlipType != null && staSlipType.equals(SlipType.REVERSE_NP_ADJUSTMENT_INBOUND)) {
            operationBill.setType(SlipType.REVERSE_NP_ADJUSTMENT_INBOUND.getValue());
        } else if (isNeedRecordType(sta)) {
            operationBill.setType(222);
        } else {
            operationBill.setType(sta.getType().getValue());
        }
        operationBill.setSlipCode(stv.getSta().getRefSlipCode());
        if (StockTransApplicationType.INBOUND_PURCHASE.equals(staType)) {
            operationBill.setSysSourceCode(chooseOptionDao.findAllOptionListByOptionKey(Constants.SYS_SOURCE_CODE, Constants.SYS_SOURCE_CODE, new SingleColumnRowMapper<String>(String.class)));
            operationBill.setBatchNo(stv.getCode());
        }
        // 按箱收货反馈采购订单入库数量是，基于Carton Sta查询关联Root Sta,反馈明细基于Carton sta反馈，单据信息基于Root Sta反馈
        if (!StringUtils.isEmpty(sta.getGroupSta()) && (StockTransApplicationType.INBOUND_PURCHASE.equals(staType) || StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.equals(staType))) {
            Long rootstaId = sta.getGroupSta().getId();
            StockTransApplication rootsta = staDao.getByPrimaryKey(rootstaId);
            operationBill.setCode(rootsta.getCode());
            operationBill.setSlipCode(rootsta.getRefSlipCode());
        }
        if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            if (sta.getStaDeliveryInfo().getReturnReasonType() != null) {
                List<ChooseOption> statusList = chooseOptionDao.findOptionListByCategoryCode("returnReasonType");
                for (ChooseOption chooseOption : statusList) {
                    if (ReturnReasonType.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()).toString().equals(chooseOption.getOptionKey())) {
                        operationBill.setInBoundRemark(chooseOption.getOptionValue() + ":" + (sta.getStaDeliveryInfo().getReturnReasonMemo() == null ? "" : sta.getStaDeliveryInfo().getReturnReasonMemo()));
                        operationBill.setInboundRemarkCode(String.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()));
                    }
                }
            }
        }
        List<ExpiredDateInfo> list_ = new ArrayList<ExpiredDateInfo>();
        operationBill.setMemo(stv.getMemo());
        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
            operationBill.setSlipCode(stv.getSta().getSlipCode1());
        }
        if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            List<SkuSnLogCommand> skus = skuSnLogDao.findOutboundSnBySta(sta.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
            List<SnFeedbackInfo> snFeedbackInfos = new ArrayList<SnFeedbackInfo>();
            for (SkuSnLogCommand sku : skus) {
                SnFeedbackInfo feedbackInfo = new SnFeedbackInfo();
                feedbackInfo.setSnNo(sku.getSn());
                feedbackInfo.setJmskuCode(sku.getSkuCode());
                snFeedbackInfos.add(feedbackInfo);
            }
            operationBill.setInboundSnInfo(snFeedbackInfos);
        }
        List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
        List<StvLine> list = stvLineDao.findStvLineListByStvId(stv.getId());
        if (stv.getDirection().getValue() == 1) {// 入
            operationBill.setDirection(OperationBill.ONLY_INBOUND);
            for (int i = 0; i < list.size(); i++) {
                StvLine line = list.get(i);
                InventoryStatus status = inventoryStatusDao.getByPrimaryKey(line.getInvStatus().getId());
                OperationBillLine operationBillLine = new OperationBillLine();
                operationBillLine.setInvBatchCode(line.getBatchCode());
                operationBillLine.setInvStatusCode(status.getName());
                operationBillLine.setQty(line.getQuantity());
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                operationBillLine.setSkuCode(sku.getCustomerSkuCode());
                operationBillLine.setWhCode(line.getWarehouse().getCode());
                // 新增是否可销售
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                BiChannel companyShop = companyShopDao.getByCode(line.getOwner());
                if (companyShop == null) {
                    throw new BusinessException("");
                }
                // 店铺切换 接口调整-调整渠道编码
                operationBillLine.setShopCode(line.getOwner());
                billLines.add(operationBillLine);
                if (InboundStoreMode.SHELF_MANAGEMENT.equals(line.getSku().getStoremode())) {
                    ExpiredDateInfo d = new ExpiredDateInfo();
                    d.setExpiredDate(line.getExpireDate());
                    d.setInvStatusCode(line.getInvStatus().getName());
                    d.setJmskuCode(sku.getJmCode());
                    d.setQty(line.getQuantity());
                    list_.add(d);
                }
            }
            operationBill.setInboundLines(billLines);
            operationBill.setInboundExpiredDateInfo(list_.size() > 0 ? list_ : null);
            List<WarrantyCardResp> warrantyCardList = new ArrayList<WarrantyCardResp>();
            List<GiftLineCommand> glList = giftLineDao.findGiftByStaAndType(sta.getId(), GiftType.COACH_CARD.getValue(), new BeanPropertyRowMapper<GiftLineCommand>(GiftLineCommand.class));
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (GiftLineCommand gl : glList) {
                WarrantyCardResp resp = new WarrantyCardResp();
                resp.setCardNo(gl.getSanCardCode());
                resp.setDirection(2);
                Date date = null;
                if (!StringUtil.isEmpty(gl.getMemo())) {
                    try {
                        date = formatDate.parse(gl.getMemo());
                    } catch (Exception e) {
                        log.error("String to Date format error! StringDate:" + gl.getMemo());
                        throw new BusinessException(ErrorCode.STRING_TO_DATE_FORMAT_ERROR);
                    }
                }
                resp.setExpirationDate(date);
                resp.setJmskuCode(gl.getSkuCode());
                warrantyCardList.add(resp);
            }
            operationBill.setWarrantyCardList(warrantyCardList);
        } else if (stv.getDirection().getValue() == 2) {// 出
            operationBill.setDirection(OperationBill.ONLY_OUTBOUND);
            for (int i = 0; i < list.size(); i++) {
                StvLine line = list.get(i);
                OperationBillLine operationBillLine = new OperationBillLine();
                operationBillLine.setInvBatchCode(line.getBatchCode());
                operationBillLine.setInvStatusCode(line.getInvStatus().getName());
                operationBillLine.setQty(line.getQuantity());
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                operationBillLine.setSkuCode(sku.getCustomerSkuCode());
                operationBillLine.setWhCode(line.getWarehouse().getCode());
                // 新增是否可销售
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                if (line.getOwner() == null) {
                    log.error("send msg to oms , owner is null!");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                if (InboundStoreMode.SHELF_MANAGEMENT.equals(line.getSku().getStoremode())) {
                    ExpiredDateInfo d = new ExpiredDateInfo();
                    d.setExpiredDate(line.getExpireDate());
                    d.setInvStatusCode(line.getInvStatus().getName());
                    d.setJmskuCode(sku.getJmCode());
                    d.setQty(line.getQuantity());
                    list_.add(d);
                }
                // 店铺切换 接口调整-调整渠道编码
                operationBillLine.setShopCode(line.getOwner());
                billLines.add(operationBillLine);
            }
            operationBill.setOutboundLines(billLines);
            operationBill.setOutboundExpiredDateInfo(list_.size() > 0 ? list_ : null);
        }
        List<OperationBill> operationBillList = new ArrayList<OperationBill>();
        operationBillList.add(operationBill);
        BaseResult baseResult = rmi4Wms.wmsOperationsFeedback(operationBill);
        try {
            log.info(sta.getCode() + ":" + net.sf.json.JSONObject.fromObject(operationBill).toString());
        } catch (Exception e) {
            log.error("", e);
        }
        if (baseResult.getStatus() == 0) {
            log.error("staId" + staId + "baseResult:" + baseResult.getMsg());
            throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
        }
        log.info("inv change notic pac end" + sta.getCode());
    }

    public void executeInvStatusChangeForImpory(Long staId, Long userId, boolean isNotVolidateExpireDate, boolean isTransactionType) throws Exception {
        User user = null;
        if (userId != null) {
            user = userDao.getByPrimaryKey(userId);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher outStv = stvList.get(0);
        wareHouseManager.removeInventory(sta, stvList.get(0));
        outStv.setFinishTime(new Date());
        outStv.setOperator(user);
        outStv.setStatus(StockTransVoucherStatus.FINISHED);
        outStv.setLastModifyTime(new Date());
        stvDao.save(outStv);
        // save Sta
        staDao.save(sta);
        staDao.flush();
        stvDao.flush();

        List<StockTransVoucher> inStvList = stvDao.findByStaWithDirection(staId, TransactionDirection.INBOUND);
        if (inStvList == null || inStvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(outStv));
        } catch (BusinessException e) {
            throw e;
        }
        // 库存状态调整数出，入数量校验
        // 查询入的数量
        List<StockTransTxLogCommand> LogList1 = stockTransTxLogDao.findLogListByStaCode(1, sta.getCode(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        // 查询出的数量
        List<StockTransTxLogCommand> LogList2 = stockTransTxLogDao.findLogListByStaCode(2, sta.getCode(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        if (LogList1.size() > 0 && LogList2.size() > 0) {
            for (StockTransTxLogCommand stockTransTxLogCommand1 : LogList1) {
                for (StockTransTxLogCommand stockTransTxLogCommand2 : LogList2) {
                    if (stockTransTxLogCommand1.getSkuId().equals(stockTransTxLogCommand2.getSkuId())) {
                        // 数量不一致，抛出业务异常
                        if (!stockTransTxLogCommand1.getOutQty().equals(stockTransTxLogCommand2.getOutQty())) {
                            throw new BusinessException("库存状态调整出入数量不相等:商品ID" + stockTransTxLogCommand1.getSkuId());
                        }
                    }
                }
            }
        }
        List<StvLine> stvLine = stvLineDao.findStvLineListByStvId(inStvList.get(0).getId());
        if (stvLine == null || stvLine.size() == 0) {
            throw new BusinessException();
        }
        wareHouseManager.purchaseReceiveStep2(inStvList.get(0).getId(), stvLine, true, user, isNotVolidateExpireDate, isTransactionType);
    }

    public void updateSkuInfoByJmCode(String packageBarCode, String jmCode, BigDecimal length, BigDecimal width, BigDecimal height, BigDecimal weight, String categorieName, TransDeliveryType deliveryType, SkuType skuType) {
        List<Sku> skuList = skuDao.getByJmCode(jmCode);
        SkuModifyLog smf = null;
        Long categorieId = null;
        Sku paperSku = null;
        if (StringUtils.hasText(categorieName)) {
            categorieId = skuCategoriesDao.fingSkuCategoriesIdByName(categorieName, new SingleColumnRowMapper<BigDecimal>()) == null ? null : skuCategoriesDao.fingSkuCategoriesIdByName(categorieName, new SingleColumnRowMapper<BigDecimal>()).longValue();
        }
        if (StringUtils.hasText(packageBarCode)) {
            paperSku = skuDao.getByBarcode1(packageBarCode);
        }
        for (Sku sku : skuList) {
            sku.setLength(length);
            sku.setWidth(width);
            sku.setHeight(height);
            sku.setGrossWeight(weight);
            if (categorieId != null) {
                sku.setSkuCategoriesId(categorieId);
            }
            if (paperSku != null) {
                sku.setPaperSku(paperSku);
            }
            sku.setSkuType(skuType);
            sku.setDeliveryType(deliveryType);
            sku.setLastModifyTime(new Date());

            smf = wareHouseManager.refreshSkuModifyLog(sku);
            if (smf != null) {
                skuModifyLogDao.save(smf);// 将新建或修改后的SKU保存进变更日志表
            }
            smf = null;
        }
    }

    @Deprecated
    public void executeInvStatusChange(boolean isExcel, Long staId, List<StvLineCommand> stvlineList, Long operatorId) throws Exception {
        if (stvlineList == null || stvlineList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANIST_INNER_LINE_EMPTY);
        }
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        User user = userDao.getByPrimaryKey(operatorId);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher outStv = stvList.get(0);
        if (!isExcel) {
            List<StvLineCommand> outLineList = stvLineDao.findByStvIdGroupBySkuLocationOwner(outStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            // 校验入库数量
            Map<String, Long> vmap = new HashMap<String, Long>();
            for (StvLineCommand l : outLineList) {
                String key = l.getSkuId() + ":" + l.getBarCode();
                if (vmap.get(key) == null) {
                    vmap.put(key, l.getQuantity());
                } else {
                    vmap.put(key, vmap.get(key) + l.getQuantity());
                }
            }

            for (StvLineCommand cmd : stvlineList) {
                String key = cmd.getSkuId() + ":" + cmd.getBarCode();
                if (vmap.get(key) == null) {
                    vmap.put(key, -cmd.getQuantity());
                } else {
                    if (vmap.get(key) - cmd.getQuantity() == 0L) {
                        vmap.remove(key);
                    } else {
                        vmap.put(key, vmap.get(key) - cmd.getQuantity());
                    }

                }
            }
            BusinessException root = null;
            for (Entry<String, Long> l : vmap.entrySet()) {
                String[] v = l.getKey().split(":");
                Long skuId = Long.parseLong(v[0]);
                String bachcode = v[1];
                if (l.getValue() != 0) {
                    if (root == null) {
                        root = new BusinessException();
                    }
                    BusinessException current = root;
                    while (current != null) {
                        if (current.getLinkedException() == null) {
                            break;
                        }
                        current = current.getLinkedException();
                    }
                    Sku sku = skuDao.getByPrimaryKey(skuId);
                    if (sku != null) {
                        current.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_NOT_EQ_FOR_INV_STATUS_CHANGE, new Object[] {sku.getCode(), sku.getBarCode(), bachcode == null ? "" : bachcode}));
                    } else {
                        current.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_NOT_EQ_FOR_INV_STATUS_CHANGE, new Object[] {}));
                    }
                }
            }
            if (root != null) {
                throw root;
            }
            // 校验提交数据
            for (StvLineCommand cmd : stvlineList) {
                if (!StringUtils.hasText(cmd.getOwner())) {
                    throw new BusinessException(ErrorCode.OWNER_IS_NULL);
                }
                if (cmd.getLocation() == null) {
                    WarehouseLocation loc = warehouseLocationDao.getByPrimaryKey(cmd.getLocationId());
                    if (loc == null) {
                        throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND);
                    } else {
                        cmd.setLocation(loc);
                    }
                }
                if (cmd.getSku() == null) {
                    Sku sku = skuDao.getByPrimaryKey(cmd.getSkuId());
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {""});
                    } else {
                        cmd.setSku(sku);
                    }
                }
                if (cmd.getInvStatus() == null) {
                    InventoryStatus iss = inventoryStatusDao.getByPrimaryKey(cmd.getIntInvstatus());
                    if (iss == null) {
                        throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
                    } else {
                        cmd.setInvStatus(iss);
                    }
                }
            }
        }
        // 完成出库单
        wareHouseManager.removeInventory(sta, stvList.get(0));
        outStv.setFinishTime(new Date());
        outStv.setOperator(user);
        outStv.setStatus(StockTransVoucherStatus.FINISHED);
        outStv.setLastModifyTime(new Date());
        stvDao.save(outStv);
        // 更新sta
        sta.setFinishTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), operatorId, sta.getMainWarehouse().getId());
        sta.setOutboundOperator(user);
        sta.setInboundOperator(user);
        sta.setOutboundTime(new Date());
        sta.setInboundTime(new Date());
        // 锁定Sta (调整-库存状态调整创建店铺必须按并且单选，创建后锁定，创建后调用OMS接口)
        sta.setIsLocked(true);
        staDao.save(sta);
        if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                vmi.generateReceivingWhenFinished(sta);
            }
        }
        // 创建入库单
        StockTransVoucher stv = new StockTransVoucher();
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>()));
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        stv.setCreateTime(new Date());
        stv.setCreator(user);
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setMode(InboundStoreMode.TOGETHER);
        stv.setOperator(user);
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setLastModifyTime(new Date());
        stv.setTransactionType(type);
        stv.setWarehouse(sta.getMainWarehouse());
        List<StvLine> list = new ArrayList<StvLine>();
        List<StvLine> outList = stvLineDao.findStvLineListByStvId(outStv.getId());
        // 根据批次号查询入库时间 并保存进入库StvLine 保持与出处的StvLine上的inboundTime一致
        for (StvLineCommand cmd : stvlineList) {
            StvLine l = new StvLine();
            l.setDirection(TransactionDirection.INBOUND);
            l.setBatchCode(cmd.getBatchCode());
            l.setSkuCost(cmd.getSkuCost());
            l.setDistrict(cmd.getLocation().getDistrict());
            l.setInvStatus(cmd.getInvStatus());
            l.setLocation(cmd.getLocation());
            l.setOwner(cmd.getOwner());
            l.setQuantity(cmd.getQuantity());
            l.setSku(cmd.getSku());
            l.setStv(stv);
            l.setTransactionType(type);
            l.setWarehouse(sta.getMainWarehouse());
            // 匹配sta line
            for (StaLine line : staLineDao.findByStaId(sta.getId())) {
                if (line.getSku().getId().equals(l.getSku().getId()) && line.getOwner().equals(l.getOwner())) {
                    l.setStaLine(line);
                    break;
                }
            }
            for (StvLine sl : outList) {
                if (sl.getSku().getId().equals(l.getSku().getId()) && sl.getBatchCode().equals(l.getBatchCode()) && sl.getOwner().equals(l.getOwner())) {
                    l.setInBoundTime(sl.getInBoundTime());
                    break;
                }
            }
            list.add(l);
        }
        stv.setStvLines(list);
        stvDao.save(stv);
        stvDao.flush();
        wareHouseManager.purchaseReceiveStep2(stv.getId(), list, true, user, false, true);
    }

    public void executeLocationByStaId(Long staid, String code) {
        List<StaLineCommand> staLineList = staLineDao.findStaLineListByStaId(staid, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        for (StaLineCommand data : staLineList) {
            Long skuId = data.getSkuId();
            // 删除原有的库位信息
            pdaSkuLocationDao.deleteLocationBySkuIdAndCode(skuId, code);
            // 查找商品相关库位
            List<Long> invList = inventoryDao.findBySkuId(skuId, new SingleColumnRowMapper<Long>(Long.class));
            for (int i = 0; i < invList.size(); i++) {
                if (i >= 10) {
                    break;
                }
                PdaSkuLocation pdaData = new PdaSkuLocation();
                WarehouseLocation location = new WarehouseLocation();
                location.setId(invList.get(i));
                pdaData.setSkuId(skuId);
                pdaData.setLocation(location);
                pdaData.setCode(code);
                pdaSkuLocationDao.save(pdaData);
            }
        }
    }

    public String updateStaLpcode(Long ouid, String updatelpcode, Date createTime, Date endCreateTime, StockTransApplicationCommand sta, List<Long> idsList, Boolean isSelectAll, List<Long> wids) {
        String status = "";
        if (isSelectAll) {
            String code = null;
            String refSlipCode = null;
            String lpcode = null;
            String owner = null;
            if (sta != null) {
                if (StringUtils.hasText(sta.getRefSlipCode())) {
                    refSlipCode = sta.getRefSlipCode() + "%";
                }
                if (StringUtils.hasText(sta.getCode())) {
                    code = sta.getCode() + "%";
                }
                if (StringUtils.hasText(sta.getLpcode())) {
                    lpcode = sta.getLpcode().trim();
                }
                if (StringUtils.hasText(sta.getOwner())) {
                    owner = sta.getOwner().trim();
                }
            }
            if (wids.size() == 0) {
                wids = null;
            }
            List<StockTransApplication> staList = staDao.queryLpcodeByForm(ouid, updatelpcode, createTime, endCreateTime, code, refSlipCode, lpcode, owner, wids);
            if (staList != null && staList.size() > 0) {
                for (StockTransApplication bean : staList) {
                    status = updateStaLpcode(bean, updatelpcode);
                }
            }
        } else {
            for (Long id : idsList) {
                status = updateStaLpcode(staDao.getByPrimaryKey(id), updatelpcode);
            }
        }
        return status;
    }

    private String updateStaLpcode(StockTransApplication sta, String newLpcode) {
        if (sta != null) {
            if (sta.getStatus().getValue() == 10) {
                // 修改作业单状态
                sta.setStatus(StockTransApplicationStatus.INTRANSIT);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.INTRANSIT.getValue(), null, sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                // 修改出库单
                // sta.setHoList(null);
            }
            if (StringUtil.isEmpty(newLpcode)) {
                throw new BusinessException(ErrorCode.TRANSPORTATOR_REF_TRANSPORTATOR_IS_NULL);
            }
            if (!StringUtils.hasText(sta.getStaDeliveryInfo().getTrackingNo())) {
                if (sta.getStaDeliveryInfo().getIsCod()) {
                    Transportator transportator = transportatorDao.findByCode(newLpcode);
                    if (transportator.getIsSupportCod()) {
                        staDao.updateLpcodeById(sta.getId(), newLpcode);
                    } else {
                        throw new BusinessException(ErrorCode.TRANSPORTATOR_REF_TRANSPORTATOR_IS_COD);
                    }
                } else {
                    staDao.updateLpcodeById(sta.getId(), newLpcode);
                }


            } else {
                return "Error";
            }


        } else {
            // 没获取到相关单据
            return "";
        }
        return "";
    }

    // pad 确认收货
    public StockTransVoucher executePdaPurchase(Long staid, Long ouid, Long userid) {
        // 1. ispda, 2. 创建 stv stvline ,3.推荐库位
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (sta.getIsPDA() != null && sta.getIsPDA()) {
            throw new BusinessException(ErrorCode.STA_IS_ALREADY_PDA_INBOUND, new Object[] {sta.getCode()});
        } else {
            // 1. ispda
            sta.setIsPDA(true);
            // 创建 入库 stv
            BigDecimal ttid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>());
            if (ttid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            int tdType = TransactionDirection.INBOUND.getValue();
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());

            // 2. 创建 stv stvline
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userid, tdType, sta.getMainWarehouse().getId(), ttid.longValue());
            String batchCode = Long.valueOf(new Date().getTime()).toString();
            stvLineDao.createPDAInBoundStvLine(sta.getId(), batchCode, ttid.longValue());
            staDao.save(sta);
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
            if (stv == null) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND_GENERIC);
            }
            stv.setIsPda(true);
            stvDao.save(stv);
            // 3.推荐库位
            wareHouseManager.suggestInboundLocation(stv.getId(), false);

            // 判断STA类型，sta是否已经生成过
            // vmi根据店铺生成反馈文件
            if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
                staDao.flush();
                BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                    vmi.generateReceivingWhenInbound(sta, stv);
                }
            }
            return stv;
        }
    }

    /**
     * 负向采购退货入库上架执行
     */
    @Override
    public void executeProcurementReturnPutaway(StockTransVoucher temp, List<StvLineCommand> stvlineList, Boolean finish, User operator, Boolean isForced) {
        log.error("......begin executeProcurementReturnPutaway......." + temp);
        isForced = (isForced == null) ? false : isForced;
        StockTransVoucher stv = stvDao.getByPrimaryKey(temp.getId());
        if (!StockTransVoucherStatus.CREATED.equals(stv.getStatus())) {
            throw new BusinessException(ErrorCode.STV_STATUS_ERROR);
        }
        String slipCode = stv.getSta().getSlipCode2();
        List<StvLineCommand> slList = stvLineDao.findExpireDateByStaSlipCode(slipCode, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        Map<String, List<StvLineCommand>> map = new HashMap<String, List<StvLineCommand>>();
        for (StvLineCommand com : slList) {
            String key = com.getSkuId() + "_" + com.getStrExpireDate();
            List<StvLineCommand> tempList = null;
            if (map.containsKey(key)) {
                tempList = map.get(key);
            } else {
                tempList = new ArrayList<StvLineCommand>();
                map.put(key, tempList);
            }
            tempList.add(com);
        }
        log.info("Esp t4 po:" + stv.getSta().getRefSlipCode() + " set invoiceNumber:" + temp.getInvoiceNumber() + " dutyPercentage:" + temp.getDutyPercentage() + " miscFeePercentage" + temp.getMiscFeePercentage());
        stv.setInvoiceNumber(temp.getInvoiceNumber());
        stv.setDutyPercentage(temp.getDutyPercentage());
        stv.setMiscFeePercentage(temp.getMiscFeePercentage());
        stvDao.save(stv);
        List<StvLine> lineList = new ArrayList<StvLine>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        for (StvLineCommand l : stvlineList) {
            Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
            l.setSku(sku);
            // 判断是否保质期商品
            if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                wareHouseManagerProxy.setStvLineProductionDateAndExpireDate(l, l.getStrPoductionDate(), l.getStrExpireDate());
                // 校验保质期商品 收货校验保质期于出库作业单配货清单中，如不正确提示错误信息
                String strEpireDate = formatDate.format(l.getExpireDate());
                String key = sku.getId() + "_" + strEpireDate;
                if (map.containsKey(key)) {
                    List<StvLineCommand> tempList = map.get(key);
                    Long quantity = l.getQuantity();// 页面数量
                    for (int i = 0; i < tempList.size(); i++) {
                        StvLineCommand sl = tempList.get(i);
                        if (sl.getQuantity() >= quantity) {
                            lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), quantity,
                                    l.getBatchCode(), l.getInBoundTime(), sl.getProductionDate(), sl.getValidDate(), sl.getExpireDate(), l.getStaLine(), stv));
                            sl.setQuantity(sl.getQuantity() - quantity);
                            quantity = 0l;
                        } else {
                            lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), sl.getQuantity(),
                                    l.getBatchCode(), l.getInBoundTime(), sl.getProductionDate(), sl.getValidDate(), sl.getExpireDate(), l.getStaLine(), stv));
                            quantity -= sl.getQuantity();
                            sl.setQuantity(0l);
                        }
                        if (sl.getQuantity() < 1) {
                            tempList.remove(i);
                        }
                        if (quantity.equals(0L)) {
                            break;
                        }
                    }
                    if (quantity > 0) {
                        // 保质期不存在出库记录中
                        throw new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate});
                    }
                } else {
                    // 抛出错误提示保质期不存在出库记录中
                    throw new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate});
                }
            } else {
                lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), l.getQuantity(), l.getBatchCode(),
                        l.getInBoundTime(), l.getProductionDate(), l.getValidDate(), l.getExpireDate(), l.getStaLine(), stv));
            }
        }
        wareHouseManager.purchaseReceiveStep2(stv.getId(), lineList, finish, operator, false, true);
        StockTransApplication sta = stv.getSta();
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
    }

    @Override
    public void editSkuByCode(SkuCommand sku) {
        Boolean flag = null;
        Sku pSku = null;
        SkuCategories sc = null;
        if (sku.getSkuCategoriesName() != null && !sku.getSkuCategoriesName().trim().equals("")) {
            String key = sku.getSkuCategoriesName().trim();
            sc = skuCategoriesDao.getBySkuCategoriesName(key);
            if (sc == null) {
                flag = false;
            } else {
                flag = true;
            }
            if (!flag) {
                throw new BusinessException(ErrorCode.IMPORT_CATAGORY_NOTEXISTS, new Object[] {sku.getSkuCategoriesName()});
            }// 编号对应的商品分类不存在
        }
        flag = null;
        if (sku.getPackageBarCode() != null && !sku.getPackageBarCode().trim().equals("")) {
            String key = sku.getPackageBarCode().trim();
            pSku = skuDao.getByBarcode1(key);
            if (pSku == null) {
                flag = false;
            } else {
                if (pSku.getCode().equals(sku.getCode())) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
            if (!flag) {
                // 编码{0}对应的箱型不存在/与当前维护商品一致
                throw new BusinessException(ErrorCode.IMPORT_PACKAGESKU_ERROR, new Object[] {sku.getCode()});
            }
        }
        try {
            if (sku.getRailwayStr() != null) {
                if (sku.getRailwayStr().equals("是")) {
                    sku.setDeliveryType(TransDeliveryType.LAND);
                } else {
                    sku.setDeliveryType(TransDeliveryType.ORDINARY);
                }
            }

            Sku sku1 = skuDao.getByCode(sku.getCode());
            if (sku.getSkuTypeId() != null) {
                sku1.setSkuType(skuTypeDao.getByPrimaryKey(sku.getSkuTypeId()));
            }
            if ("是".equals(sku.getIsConsumable())) {
                sku1.setSpType(SkuSpType.CONSUMPTIVE_MATERIAL);
            } else {
                sku1.setSpType(null);
            }
            sku1.setLength(sku.getLength());
            sku1.setWidth(sku.getWidth());
            sku1.setHeight(sku.getHeight());
            sku1.setGrossWeight(sku.getGrossWeight());
            sku1.setPaperSku(pSku);
            if (sc != null) {
                sku1.setSkuCategoriesId(sc.getId());
            }
            sku1.setDeliveryType(sku.getDeliveryType());
            sku1.setLastModifyTime(new Date());
            sku1.setHtsCode(sku.getHtsCode());
            sku1.setUnitName(sku.getUnitName());
            sku1.setCountryOfOrigin(sku.getCountryOfOrigin());
            SkuModifyLog smf = wareHouseManager.refreshSkuModifyLog(sku1);
            if (smf != null) {
                skuModifyLogDao.save(smf);// 将新建或修改后的SKU保存进变更日志表
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.UPDATE_PRO_INFO_ERROR);
        }
    }

    public InventoryCheck createInventoryCheck(String remork, Long ouId, List<InventoryCheckLineCommand> list, Long userId, Boolean daily) {
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_LINE_NO_LOCATION);
        }
        User user = userDao.getByPrimaryKey(userId);
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        Set<Long> locSet = new HashSet<Long>();
        for (InventoryCheckLineCommand cmd : list) {
            if (InventoryCheckLineCommand.LINE_TYPE_DISTRICT == cmd.getLineType()) {
                // 库区处理
                WarehouseDistrict dis = warehouseDistrictDao.getByPrimaryKey(cmd.getParamId());
                if (dis == null || !dis.getIsAvailable() || !dis.getOu().getId().equals(ou.getId())) {
                    throw new BusinessException(ErrorCode.WH_DISTRICE_NOT_FOUND, new Object[] {""});
                }
                Map<String, Long> locLockedMap = warehouseLocationDao.findAllByDistrict(dis.getId(), true, Constants.LOCATION_LOCKED_RESULT_LIMIT, new MapRowMapper());
                if (locLockedMap == null || locLockedMap.size() == 0) {
                    Map<String, Long> locMap = warehouseLocationDao.findAllByDistrict(dis.getId(), false, null, new MapRowMapper());
                    if (locMap != null) {
                        locSet.addAll(locMap.values());
                    }
                } else {
                    BusinessException root = new BusinessException(ErrorCode.INVNETORY_CHECK_CREATE_ERROR);
                    for (String key : locLockedMap.keySet()) {
                        BusinessException current = root;
                        while (current.getLinkedException() != null) {
                            current = current.getLinkedException();
                        }
                        current.setLinkedException(new BusinessException(ErrorCode.DISTRICT_LOCATION_IS_LOCKED_OR_OCCUPAID, new Object[] {dis.getCode(), key}));
                    }
                    throw root;
                }
            } else if (InventoryCheckLineCommand.LINE_TYPE_LOCATION == cmd.getLineType()) {
                // 库位处理
                WarehouseLocation loc = warehouseLocationDao.getByPrimaryKey(cmd.getParamId());
                BigDecimal locId = warehouseLocationDao.findLockedAndNoOccupaidLocation(loc.getId(), new SingleColumnRowMapper<BigDecimal>());
                if (locId != null) {
                    throw new BusinessException(ErrorCode.WH_LOCATION_IS_LOCKED_OR_OCCPUAID, new Object[] {loc.getDistrict().getName(), loc.getCode()});
                } else {
                    locSet.add(loc.getId());
                }
            }
        }
        InventoryCheck invCk = wareHouseManager.createInventoryCheck(user, ou, remork, daily);
        wareHouseManager.createInventoryCheckLineNative(locSet, invCk);
        InventoryCheck invCkDest = new InventoryCheck();
        BeanUtils.copyProperties(invCk, invCkDest);
        invCkDest.setCancelUser(null);
        invCkDest.setCreator(null);
        invCkDest.setManagerUserId(null);
        invCkDest.setOperatorUserId(null);
        invCkDest.setOu(null);
        invCkDest.setShop(null);
        return invCkDest;
    }

    @Override
    public void managerConfirmCheck(Long Id, String code, User usermanager, boolean check) {
        // 修改状态为仓库经理确认
        InventoryCheck ic = inventoryCheckDao.findByCode(code);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND, code);
        }
        if (!InventoryCheckStatus.CHECKWHINVENTORY.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, ic.getCode());
        }

        ic.setStatus(InventoryCheckStatus.CHECKWHMANAGER);
        if (usermanager != null) {
            ic.setManagerUserId(usermanager);
        }
        ic.setManagerTime(new Date());
        inventoryCheckDao.flush();
        // 调整单库存占用
        occupyInventoryCheckSta(ic.getId());
        inventoryCheckDao.flush();
        // 占用库存
        Map<String, Object> invparams1 = new HashMap<String, Object>();
        invparams1.put("in_ic_id", Id);
        SqlParameter[] occinvp = {new SqlParameter("in_ic_id", Types.NUMERIC)};
        inventoryCheckDao.executeSp("sp_inventory_check_opc_inv", occinvp, invparams1);
        // 重新生成差异明细 1、删除盘亏明细 2、根据库存占用重新生成明细
        inventoryCheckDifferenceLineDao.deleteOutDifferentLineById(Id);
        inventoryCheckDifferenceLineDao.reCreateOutDifferentLineById(code, Id);
        inventoryCheckDifferenceLineDao.flush();
        // 如果有盘亏数据 插入同步IM中间表
        wmsIMOccupiedAndReleaseDao.insertWmsOccupiedAndReleaseByInvCheckLoss(ic.getId(), ic.getCode());
        inventoryCheckDifferenceLineDao.flush();
        if (check) {
            // 仓库经理确认调用oms
            wareHouseManager.managerCheckforoms(code);
        }
    }

    public InventoryCheck createCKInventoryCheck(Long ouId, String remork) {
        List<WarehouseLocation> locs = warehouseLocationDao.findAllLocationsListByOuId(ouId, new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));

        if (locs == null || locs.size() == 0) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_LINE_NO_LOCATION);
        }
        Set<Long> locIds = new HashSet<Long>();
        for (WarehouseLocation loc : locs) {
            locIds.add(loc.getId());
        }
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        InventoryCheck invCk = wareHouseManager.createInventoryCheck(null, ou, remork, null);
        wareHouseManager.createInventoryCheckLineNative(locIds, invCk);
        return invCk;
    }

    @Override
    public Map<String, Object> operateDate(InventoryCommand inventoryCommand, InventoryCommand inv) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.hasText(inv.getPoductionDate()) && StringUtils.hasText(inv.getSexpireDate())) {
            Date pDate = sdf.parse(inv.getPoductionDate());
            Date eDate = sdf.parse(inv.getSexpireDate());
            boolean b = pDate.before(eDate);
            if (b) {
                map.put("pDate", pDate);
                map.put("eDate", eDate);
            } else {
                throw new BusinessException(ErrorCode.TIME_INPUT_ERROR, new Object[] {inv.getSexpireDate(), inv.getPoductionDate()});
            }
        } else {
            if (null == inventoryCommand.getValidDate()) {
                throw new BusinessException(ErrorCode.VALI_DATE_NOT_EXISTS);
            } else {
                if (StringUtils.hasText(inv.getPoductionDate()) && !StringUtils.hasText(inv.getSexpireDate())) {
                    Date pDate = sdf.parse(inv.getPoductionDate());
                    Integer validDate = inventoryCommand.getValidDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(pDate); // 设置当前日期
                    c.add(Calendar.DATE, validDate);
                    Date expireDate = c.getTime();
                    map.put("pDate", pDate);
                    map.put("eDate", expireDate);
                }
                if (!StringUtils.hasText(inv.getPoductionDate()) && StringUtils.hasText(inv.getSexpireDate())) {
                    Date eDate = sdf.parse(inv.getSexpireDate());
                    Integer validDate = inventoryCommand.getValidDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(eDate); // 设置当前日期
                    c.add(Calendar.DATE, -validDate);
                    Date productionDate = c.getTime();
                    map.put("pDate", productionDate);
                    map.put("eDate", eDate);
                }
            }
        }
        return map;
    }

    /**
     * 
     * @author LuYingMing
     * @throws ParseException
     * @see com.jumbo.wms.manager.warehouse.WareHouseManager#validityAdjustModify(java.lang.Long,
     *      com.jumbo.wms.model.warehouse.InventoryCommand)
     */
    @Override
    public String validityAdjustModify(Long whOuId, InventoryCommand inv) throws ParseException {
        String result = "ERROR";
        List<InventoryCommand> list = checkValidDate(whOuId, inv);
        if (null != list && list.size() > 0) {
            for (InventoryCommand model : list) {
                Map<String, Object> dateMap = operateDate(model, inv);
                if (null != dateMap && dateMap.size() > 0) {
                    Date productionDate = (Date) dateMap.get("pDate");
                    Date expireDate = (Date) dateMap.get("eDate");
                    Long id = model.getId();
                    inventoryDao.updateInventoryByValidDate(id, productionDate, expireDate); // 更新效期
                }
            }
            result = "SUCCESS";
        } else {
            result = "NONE";
        }

        return result;
    }


    /**
     * 
     * @author LuYingMing
     * @see com.jumbo.wms.manager.warehouse.WareHouseManager#checkValidDate(java.lang.Long,
     *      com.jumbo.wms.model.warehouse.InventoryCommand)
     */
    private List<InventoryCommand> checkValidDate(Long whOuId, InventoryCommand inv) {
        List<InventoryCommand> list = new ArrayList<InventoryCommand>();
        // 封装参数
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (null == whOuId) {
                return list;
            } else {
                params.put("whOuId", whOuId);
            }
            if (StringUtils.hasText(inv.getLocationCode())) {
                WarehouseLocation location = warehouseLocationDao.findLocationByCode2(inv.getLocationCode(), whOuId);
                if (null != location && null != location.getId()) {
                    params.put("locationId", location.getId());
                } else {
                    return list;
                }
            } else {
                params.put("locationId", null);
            }
            if (null == inv.getSkuId()) {
                params.put("skuId", null);
            } else {
                params.put("skuId", inv.getSkuId());
            }
            if (null == inv.getInventoryStatusId()) {
                params.put("statusId", null);
            } else {
                params.put("statusId", inv.getInventoryStatusId());
            }
            if (StringUtils.hasText(inv.getInvOwner())) {
                params.put("invOwner", inv.getInvOwner());
            } else {
                params.put("invOwner", null);
            }
            if (StringUtils.hasText(inv.getpDate())) {
                Date pDate = sdf.parse(inv.getpDate());
                params.put("pDate", pDate);
            } else {
                params.put("pDate", null);
            }
            if (StringUtils.hasText(inv.geteDate())) {
                Date eDate = sdf.parse(inv.geteDate());
                params.put("eDate", eDate);
            } else {
                params.put("eDate", null);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return inventoryDao.findvalidDateByParameter(params, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }

    public void executeTransitInner(boolean isExcel, Long staId, List<StvLineCommand> stvlineList, Long operatorId) {
        if (stvlineList == null || stvlineList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANIST_INNER_LINE_EMPTY);
        }
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_INNER_IN);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_TRANIST_INNER_NOT_FOUND);
        }
        User user = operatorId == null ? null : userDao.getByPrimaryKey(operatorId);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher outStv = stvList.get(0);

        List<StvLineCommand> outLineList = stvLineDao.findByStvIdGroupBySkuLocationOwner(outStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        // 校验入库数量
        for (StvLineCommand l : outLineList) {
            for (StvLineCommand cmd : stvlineList) {
                log.debug("BatchCode is {}", l.getBatchCode());
                if (l.getSkuId().equals(cmd.getSkuId()) && l.getBatchCode().equals(cmd.getBatchCode()) && l.getOwner().equals(cmd.getOwner()) && l.getIntInvstatus().equals(cmd.getIntInvstatus())) {
                    l.setQuantity(l.getQuantity() - cmd.getQuantity());
                }
            }
        }
        BusinessException root = null;
        for (StvLineCommand l : outLineList) {
            if (l.getQuantity() != 0) {
                if (root == null) {
                    root = new BusinessException();
                }
                BusinessException current = root;
                while (current != null) {
                    if (current.getLinkedException() == null) {
                        break;
                    }
                    current = current.getLinkedException();
                }
                Sku sku = skuDao.getByPrimaryKey(l.getSkuId());
                InventoryStatus iss = inventoryStatusDao.getByPrimaryKey(l.getIntInvstatus());
                current.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_NOT_EQ, new Object[] {sku.getCode(), sku.getBarCode(), l.getOwner(), iss.getName()}));
            }
        }
        if (root != null) {
            throw root;
        }
        log.debug("qty checked");
        if (!isExcel) {
            // 校验提交数据
            for (StvLineCommand cmd : stvlineList) {
                if (!StringUtils.hasText(cmd.getOwner())) {
                    throw new BusinessException(ErrorCode.OWNER_IS_NULL);
                }
                if (cmd.getLocation() == null) {
                    WarehouseLocation loc = warehouseLocationDao.getByPrimaryKey(cmd.getLocationId());
                    if (loc == null) {
                        throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND);
                    } else {
                        cmd.setLocation(loc);
                    }
                }
                if (cmd.getSku() == null) {
                    Sku sku = skuDao.getByPrimaryKey(cmd.getSkuId());
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {""});
                    } else {
                        cmd.setSku(sku);
                    }
                }
                if (cmd.getInvStatus() == null) {
                    InventoryStatus iss = inventoryStatusDao.getByPrimaryKey(cmd.getIntInvstatus());
                    if (iss == null) {
                        throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
                    } else {
                        cmd.setInvStatus(iss);
                    }
                }
            }
        }
        // 完成出库单
        if (!isExcel) {
            wareHouseManager.removeInventory(sta, stvList.get(0));
            outStv.setFinishTime(new Date());
            outStv.setOperator(user);
            outStv.setStatus(StockTransVoucherStatus.FINISHED);
            outStv.setLastModifyTime(new Date());
            stvDao.save(outStv);
            // 更新sta
            sta.setFinishTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.FINISHED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), operatorId, sta.getMainWarehouse().getId());
            sta.setOutboundOperator(user);
            sta.setInboundOperator(user);
            sta.setOutboundTime(new Date());
            sta.setInboundTime(new Date());
            staDao.save(sta);
        }
        log.debug("start create stv");
        // 创建入库单
        StockTransVoucher stv = new StockTransVoucher();
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>()));
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
        stv.setCreateTime(new Date());
        stv.setCreator(user);
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setMode(InboundStoreMode.TOGETHER);
        stv.setOperator(user);
        stv.setSta(sta);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setTransactionType(type);
        stv.setWarehouse(sta.getMainWarehouse());
        List<StvLine> list = new ArrayList<StvLine>();
        log.debug("start create stv line");
        Map<String, List<StvLineCommand>> map = new HashMap<String, List<StvLineCommand>>();
        for (StvLineCommand sl : stvLineDao.findInboundTimeByStvId(outStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class))) {
            String key = sl.getStvLineKey();
            if (map.containsKey(key)) {
                map.get(key).add(sl);
            } else {
                List<StvLineCommand> tempList = new ArrayList<StvLineCommand>();
                tempList.add(sl);
                map.put(key, tempList);
            }
        }
        for (StvLineCommand cmd : stvlineList) {
            StvLine l = new StvLine();
            // l.setBatchCode(batchCode);
            l.setDirection(TransactionDirection.INBOUND);
            l.setBatchCode(cmd.getBatchCode());
            l.setSkuCost(cmd.getSkuCost());
            l.setDistrict(cmd.getLocation().getDistrict());
            l.setInvStatus(cmd.getInvStatus());
            l.setLocation(cmd.getLocation());
            l.setOwner(cmd.getOwner());
            l.setQuantity(cmd.getQuantity());
            l.setSku(cmd.getSku());
            l.setStv(stv);
            l.setTransactionType(type);
            l.setWarehouse(sta.getMainWarehouse());
            l.setProductionDate(cmd.getProductionDate());
            l.setExpireDate(cmd.getExpireDate());
            l.setValidDate(cmd.getValidDate());
            // 匹配sta line
            for (StaLine line : staLineDao.findByStaId(sta.getId())) {
                if (line.getSku().getId().equals(l.getSku().getId()) && line.getOwner().equals(l.getOwner()) && line.getInvStatus().getId().equals(l.getInvStatus().getId())) {
                    l.setStaLine(line);
                    break;
                }
            }
            String key = l.getBatchCode() + l.getOwner() + l.getInvStatus().getId() + l.getSku().getId();
            if (map.containsKey(key)) {
                List<StvLineCommand> tempList = map.get(key);
                long qty = l.getQuantity();
                for (int i = 0; i < tempList.size(); i++) {
                    StvLineCommand temp = tempList.get(i);
                    l.setInBoundTime(temp.getInBoundTime());
                    if (temp.getQuantity() > qty) {
                        temp.setQuantity(temp.getQuantity() - qty);
                        qty = 0l;
                        list.add(l);
                        break;
                    } else if (temp.getQuantity().equals(qty)) {
                        tempList.remove(i--);
                        qty = 0l;
                        list.add(l);
                        break;
                    } else {
                        tempList.remove(i--);
                        l.setQuantity(temp.getQuantity());
                        qty -= temp.getQuantity();
                        list.add(l);
                        StvLine stvl = null;
                        try {
                            stvl = l.clone();
                        } catch (CloneNotSupportedException e) {
                            log.debug("STV_LINE_IN_BOUND_TIME_IS_NULL ... Clone StvLine!");
                            throw new BusinessException(ErrorCode.STV_LINE_IN_BOUND_TIME_IS_NULL);
                        }
                        stvl.setDistrict(l.getDistrict());
                        stvl.setLocation(l.getLocation());
                        stvl.setQuantity(qty);
                        l = stvl;
                    }
                }
                if (qty != 0l) {
                    log.debug("STV_LINE_IN_BOUND_TIME_IS_NULL ... Qty Error!");
                    throw new BusinessException(ErrorCode.STV_LINE_IN_BOUND_TIME_IS_NULL);
                }
            } else {
                log.debug("STV_LINE_IN_BOUND_TIME_IS_NULL");
                throw new BusinessException(ErrorCode.STV_LINE_IN_BOUND_TIME_IS_NULL);
            }
        }
        stv.setStvLines(list);
        stvDao.save(stv);
        if (!isExcel) {
            stvDao.flush();
            wareHouseManager.purchaseReceiveStep2(stv.getId(), list, true, user, false, true);
        }
    }

    // vmi 退仓 execute
    public void executeVmiReturnOutBound(Long staID, Long userid, Long ouid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staID);
        if (sta == null) throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (wh.getIsNeedWrapStuff() != null && wh.getIsNeedWrapStuff()) {
            List<StaAdditionalLine> temp = staAdditionalLineDao.findByStaId(sta.getId());
            if (temp == null || temp.size() == 0) {
                throw new BusinessException(ErrorCode.OUT_BOUND_NEED_WRAP_STUFF);
            }
        }
        if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
            wareHouseManager.occupyInventoryByStaId(sta.getId(), null, null);
            staDao.flush();
        }
        List<Inventory> inventorys = inventoryDao.findByOccupiedCode(sta.getCode());
        if (inventorys == null || inventorys.isEmpty()) throw new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);

        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staID);
        if (stv == null) throw new BusinessException(ErrorCode.STV_NOT_FOUND);

        TransactionType transType = transactionTypeDao.findByCode(Constants.VMI_RETURN_OUT);
        if (transType == null) throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND);

        User user = userid == null ? null : userDao.getByPrimaryKey(userid);
        // if(user == null ) throw new
        // BusinessException(ErrorCode.USER_NOT_FOUND);

        // delete stvline
        stvLineDao.deleteLineById(staID);

        // save stvline
        StaLine staLine = null;
        for (Inventory inventory : inventorys) {
            StvLine stvLine = new StvLine();
            stvLine.setBatchCode(inventory.getBatchCode());
            stvLine.setInBoundTime(inventory.getInboundTime());
            stvLine.setDirection(TransactionDirection.OUTBOUND);
            stvLine.setOwner(sta.getOwner());
            stvLine.setDistrict(inventory.getDistrict());
            stvLine.setInvStatus(inventory.getStatus());
            stvLine.setLocation(inventory.getLocation());
            stvLine.setSku(inventory.getSku());
            stvLine.setStv(stv);
            stvLine.setTransactionType(transType);
            stvLine.setWarehouse(sta.getMainWarehouse());
            stvLine.setSkuCost(inventory.getSkuCost());
            stvLine.setProductionDate(inventory.getProductionDate());
            stvLine.setExpireDate(inventory.getExpireDate());
            stvLine.setValidDate(inventory.getValidDate());
            stvLine.setQuantity(inventory.getQuantity());
            staLine = staLineDao.findByInvStatusSkuSta(staID, inventory.getStatus().getId(), inventory.getSku().getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
            stvLine.setStaLine(staLine);
            stvLineDao.save(stvLine);
        }
        stvLineDao.flush();
        stv.setOperator(user);
        stvDao.save(stv);
        stvDao.flush();
        wareHouseManager.removeInventory(sta, stv);
        // 记录SN出库日志
        snLogDao.createOutboundByStvIdSql(stv.getId());
        // 删除SN
        snDao.deleteSNByStvIdSql(stv.getId());
        validateBiChannelSupport(stv, null);
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setFinishTime(new Date());
        stv.setLastModifyTime(new Date());
        stvDao.save(stv);

        sta.setIsNeedOccupied(false);
        sta.setOutboundOperator(user);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), userid, sta.getMainWarehouse().getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), userid, sta.getMainWarehouse().getId());
        }
        sta.setFinishTime(new Date());
        // sta.setInboundTime(new Date());
        sta.setOutboundTime(new Date());
        sta.setInboundOperator(user);
        staDao.save(sta);
        // 其他出库更新中间表，传递明细给oms/pac
        WmsOtherOutBoundInvNoticeOms wto = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
        if (wto != null) {
            wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 10l);
        }
        // 唯品会 run
        // 根据作业单标示 来调用采购出库接口来反馈给pacs 并且过滤品牌反馈
        if ("1".equals(sta.getIsPf())) {//
            eventObserver.onEvent(new TransactionalEvent(stv));// 反馈给pacs
        }
        staDao.flush();
        eventObserver.onEvent(new TransactionalEvent(sta));
        if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                vmi.generateReceivingWhenFinished(sta);
            }
        }
        /*
         * List<StaLine> stalines = staLineDao.findByStaId(staID); if (stalines != null &&
         * !stalines.isEmpty()) { for (StaLine staline : stalines) {
         * staline.setCompleteQuantity(staline.getQuantity()); staLineDao.save(staline); } }
         */
        // 更改核对数量
        staLineDao.updateCompleteQuantityByStaId(sta.getId());

        // staLineDao.flush();
        // 唯品会
        if ("1".equals(sta.getIsPf()) || !StringUtil.isEmpty(sta.getDataSource())) {} else {
            // VMI退仓反馈
            BiChannel sh = companyShopDao.getByCode(sta.getOwner());
            if (sh != null && sh.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
                vf.generateRtnWh(sta);
            }
        }
        // 部分出库的单据 未执行量插入取消占用表给IM
        hubWmsService.insertOccupiedAndReleaseUnDeal(sta.getId());
        // sf和ems执行出库回传物流商确认信息
        transInfoManager.vmiRtoOrderConfrimToLogistics(staID);
    }

    /**
     * 差异确认修改KJL 原始不变
     */
    public void confirmVMIInvCKAdjustmentNew(InventoryCheck invckComm) {
        // 更新盘点批状态
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invckComm.getId());
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_NOT_UNEXECUTE, new Object[] {ic.getCode()});
        }
        TransactionType inType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_INBOUND_CONSIGNMENT);
        if (inType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_VMI_INVENTORY_CHECK_IN_NOT_FOUND);
        }
        TransactionType outType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_OUTBOUND_CONSIGNMENT);
        if (outType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_VMI_INVENTORY_CHECK_OUT_NOT_FOUND);
        }
        ic.setStatus(InventoryCheckStatus.FINISHED);
        String batchCode = Long.valueOf(new Date().getTime()).toString();
        // 更新库存
        Map<String, Object> invparams = new HashMap<String, Object>();
        invparams.put("in_ic_id", ic.getId());
        invparams.put("in_batch_code", batchCode);
        invparams.put("in_cmp_id", ic.getOu().getParentUnit().getParentUnit().getId());
        invparams.put("in_out_transtype_id", outType.getId());
        invparams.put("in_in_transtype_id", inType.getId());
        SqlParameter[] invSqlP =
                {new SqlParameter("in_ic_id", Types.NUMERIC), new SqlParameter("in_batch_code", Types.VARCHAR), new SqlParameter("in_cmp_id", Types.NUMERIC), new SqlParameter("in_out_transtype_id", Types.NUMERIC),
                        new SqlParameter("in_in_transtype_id", Types.NUMERIC)};
        @SuppressWarnings("unused")
        Map<String, Object> result = staDao.executeSp("sp_vmi_adj_inv_exe", invSqlP, invparams);
        BiChannel sh = ic.getShop();
        if (sh != null && sh.getVmiCode() != null) {
            VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
            vf.generateVMIReceiveInfoByInvCk(ic);
        }
        // VMI库存调整stLog库存变化通知pac
        wareHouseManager.createWmsOtherOutBoundInvNoticeOms(ic.getId(), 10l, WmsOtherOutBoundInvNoticeOmsStatus.VMI_ADJUSTMENT);
        // VMI库存调整通知菜鸟
        cnInterfaceTask.makeVMIInvAdjNoticeToCaiNiao(ic);
        try {
            eventObserver.onEvent(new TransactionalEvent(ic));
        } catch (BusinessException e) {
            throw e;
        }
    }

    public InventoryCheck createInventoryCheckMode2(String remork, Long ouId, List<InventoryCheckLineCommand> list, Long userId) {
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_LINE_NO_LOCATION);
        }
        User user = userDao.getByPrimaryKey(userId);
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        Set<Long> locSet = new HashSet<Long>();
        for (InventoryCheckLineCommand l : list) {
            if (l.getLineType().equals(InventoryCheckLineCommand.LINE_TYPE_OWNER)) {
                Map<String, Long> locLockedMap = warehouseLocationDao.findAllByOwner(l.getOwner(), true, ouId, Constants.LOCATION_LOCKED_RESULT_LIMIT, new MapRowMapper());
                if (locLockedMap == null || locLockedMap.size() == 0) {
                    Map<String, Long> locMap = warehouseLocationDao.findAllByOwner(l.getOwner(), false, ouId, null, new MapRowMapper());
                    if (locMap != null) {
                        locSet.addAll(locMap.values());
                    }
                } else {
                    BusinessException root = new BusinessException(ErrorCode.INVNETORY_CHECK_CREATE_ERROR);
                    for (String key : locLockedMap.keySet()) {
                        BusinessException current = root;
                        while (current.getLinkedException() != null) {
                            current = current.getLinkedException();
                        }
                        current.setLinkedException(new BusinessException(ErrorCode.LOCATION_IS_LOCKED_OR_OCCUPAID, new Object[] {key}));
                    }
                    throw root;
                }
            } else if (l.getLineType().equals(InventoryCheckLineCommand.LINE_TYPE_BRAND)) {
                Map<String, Long> locLockedMap = warehouseLocationDao.findAllByBrand(Long.parseLong(l.getOwner()), true, ouId, Constants.LOCATION_LOCKED_RESULT_LIMIT, new MapRowMapper());
                if (locLockedMap == null || locLockedMap.size() == 0) {
                    Map<String, Long> locMap = warehouseLocationDao.findAllByBrand(Long.parseLong(l.getOwner()), false, ouId, null, new MapRowMapper());
                    if (locMap != null) {
                        locSet.addAll(locMap.values());
                    }
                } else {
                    BusinessException root = new BusinessException(ErrorCode.INVNETORY_CHECK_CREATE_ERROR);
                    for (String key : locLockedMap.keySet()) {
                        BusinessException current = root;
                        while (current.getLinkedException() != null) {
                            current = current.getLinkedException();
                        }
                        current.setLinkedException(new BusinessException(ErrorCode.LOCATION_IS_LOCKED_OR_OCCUPAID, new Object[] {key}));
                    }
                    throw root;
                }
            }
        }
        InventoryCheck invCk = wareHouseManager.createInventoryCheck(user, ou, remork, null);
        wareHouseManager.createInventoryCheckLineNative(locSet, invCk);
        return invCk;
    }

    public void confirmInventoryCheck(String confirmUser, Long invCkId) {
        // 更新盘点批状态
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus()) && !InventoryCheckStatus.CHECKWHMANAGER.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_NOT_UNEXECUTE, new Object[] {ic.getCode()});
        }
        if (!ic.getStatus().equals(InventoryCheckStatus.CHECKWHMANAGER)) {
            if (!StringUtils.hasText(confirmUser)) {
                throw new BusinessException(ErrorCode.INVENTORY_CHECK_CONFIRM_USER_EMPTY, new Object[] {ic.getCode()});
            }
        }
        TransactionType inType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_CHECK_IN);
        if (inType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_CHECK_IN_NOT_FOUND);
        }
        TransactionType outType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_CHECK_OUT);
        if (outType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_CHECK_OUT_NOT_FOUND);
        }
        List<InventoryCheckDifferenceLine> list = inventoryCheckDifferenceLineDao.findCheckOverageIsNullByIcid(invCkId);
        if (list != null && list.size() > 0) {
            throw new BusinessException(ErrorCode.INV_CHECK_IS_UNTREATED);
        }
        if (!ic.getStatus().equals(InventoryCheckStatus.CHECKWHMANAGER)) {
            ic.setConfirmUser(confirmUser);
        }
        ic.setStatus(InventoryCheckStatus.FINISHED);
        inventoryCheckDao.save(ic);
        // 解锁库位
        warehouseLocationDao.unLockByInvCheck(ic.getId());
        String batchCode = Long.valueOf(new Date().getTime()).toString();
        // 更新库存
        Map<String, Object> invparams = new HashMap<String, Object>();
        invparams.put("in_ic_id", ic.getId());
        invparams.put("in_batch_code", batchCode);
        invparams.put("in_cmp_id", ic.getOu().getParentUnit().getParentUnit().getId());
        invparams.put("in_out_transtype_id", outType.getId());
        invparams.put("in_in_transtype_id", inType.getId());
        SqlParameter[] invSqlP =
                {new SqlParameter("in_ic_id", Types.NUMERIC), new SqlParameter("in_batch_code", Types.VARCHAR), new SqlParameter("in_cmp_id", Types.NUMERIC), new SqlParameter("in_out_transtype_id", Types.NUMERIC),
                        new SqlParameter("in_in_transtype_id", Types.NUMERIC)};
        staDao.executeSp("sp_inventory_check_execute", invSqlP, invparams);
        // 更新SN号
        snLogDao.createLogByIc(ic.getId(), batchCode);
        snDao.deleteByIc(ic.getId());
        snDao.createByIc(ic.getId(), batchCode);

        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(ic));
        } catch (BusinessException e) {
            throw e;
        }
    }

    public void transitCrossStaOccupaid(Long staId, Long invStatusId, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        User user = userDao.getByPrimaryKey(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        InventoryStatus invs = null;
        if (sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
            invs = inventoryStatusDao.getByPrimaryKey(invStatusId);
        } else if (sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
            invs = sta.getAddiStatus();
            List<OperationUnitCommand> list = operationUnitDao.findWarehouseByCompanyList(sta.getMainWarehouse().getId(), sta.getOwner(), new BeanPropertyRowMapper<OperationUnitCommand>(OperationUnitCommand.class));
            if (list == null || list.size() == 0) {
                throw new BusinessException(ErrorCode.VMI_FLITTING_OUT_SHOP_REF, new Object[] {sta.getMainWarehouse().getName(), sta.getOwner()});
            }
            List<OperationUnitCommand> list1 = operationUnitDao.findWarehouseByCompanyList(sta.getAddiWarehouse().getId(), sta.getAddiOwner(), new BeanPropertyRowMapper<OperationUnitCommand>(OperationUnitCommand.class));
            if (list1 == null || list1.size() == 0) {
                throw new BusinessException(ErrorCode.VMI_FLITTING_OUT_SHOP_REF, new Object[] {sta.getAddiWarehouse().getName(), sta.getAddiOwner()});
            }
        }
        if (invs == null) {
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        if (sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
            sta.setMainStatus(invs);
            sta.setAddiStatus(invs);

            List<StaLine> stalList = staLineDao.findByStaId(staId);
            for (StaLine l : stalList) {
                l.setInvStatus(invs);
                staLineDao.save(l);
            }
        }
        sta.setOutboundTime(new Date());
        staDao.save(sta);
        staDao.flush();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_sta_id", staId);
        SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
        SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
        Map<String, Object> result = staDao.executeSp("sp_occ_inv_for_transit_cross", sqlParameters, params);
        String errorSku = (String) result.get("error_sku_id");
        BusinessException root = null;
        if (StringUtils.hasText(errorSku)) {
            String[] skus = errorSku.split(",");
            for (String str : skus) {
                String[] strs = str.split(Constants.STA_SKUS_SLIPT_STR);
                Long skuId = Long.parseLong(strs[0]);
                Long qty = Long.parseLong(strs[1]);
                if (root == null) {
                    root = new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
                }
                BusinessException current = root;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                }
                Sku sku = skuDao.getByPrimaryKey(skuId);
                BusinessException be = new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new Object[] {sku.getName(), sku.getCode(), sku.getBarCode(), qty});
                current.setLinkedException(be);
            }
            throw root;
        } else {
            //
            // 更新sta状态为库存占用
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            staDao.save(sta);
            // 创建stv
            TransactionType t = null;
            if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
                t = transactionTypeDao.findByCode(Constants.VMI_FLITTING_OUT);
            } else {
                t = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_CROSS_OUT);
            }
            if (t == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_TRANSIT_CROSS_OUT_NOT_FOUND);
            }
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userId, tdType, sta.getMainWarehouse().getId(), t.getId());

            if (StringUtils.hasText(sta.getOwner())) {
                staLineDao.deleteStaLineByStaId(sta.getId());
                staLineDao.createByStaId(sta.getId());
                staLineDao.flush();
            }
            stvLineDao.createForCrossByStaId(sta.getId());
            // 检验占用库位是否是暂存区的商品
            wmExecute.valdateOutBoundLocationIsGI(stvDao.findStvCreatedByStaId(sta.getId()));
        }
    }

    /**
     * VIM_库间移动-入库
     */
    public void transactionTypeTransitCross(Long staId, List<StaLine> stalineList) throws Exception {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<StaLine> stalines = staLineDao.findByStaId(staId);
        Map<String, StaLine> map = new HashMap<String, StaLine>();
        StockTransVoucher stv = null;
        for (StaLine staLine : stalines) {
            for (StaLine staLineinfo : stalineList) {
                if (staLine.getSku().getId().equals(staLineinfo.getSku().getId())) {
                    if (!staLine.getQuantity().equals(staLineinfo.getQuantity())) {
                        log.error("传入单据" + staLineinfo.getQuantity() + "与本地StaLine 数量不一致");
                        // 数量不同
                        throw new BusinessException("");
                    }
                    if (!staLine.getInvStatus().getId().equals(staLineinfo.getInvStatus().getId())) {
                        log.error("传入单据" + staLineinfo.getQuantity() + "与本地StaLine 商品状态不一致");
                        throw new BusinessException("");
                    }
                }
            }
            String key = staLine.getSku().getCode();
            map.put(key, staLine);
        }

        // 入库
        WarehouseLocation locList = warehouseLocationDao.findOneWarehouseLocationByOuid(sta.getMainWarehouse().getId());
        if (locList == null) {
            log.error("没有对应仓位");
            throw new BusinessException("");
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        stv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv.setBusinessSeqNo(biSeqNo.longValue());
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        stv.setMode(null);
        stv.setCreateTime(new Date());
        stv.setDirection(TransactionDirection.INBOUND);
        stv.setOwner(sta.getOwner());
        stv.setSta(sta);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setLastModifyTime(new Date());
        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())) {
            stv.setWarehouse(sta.getAddiWarehouse());
        } else {
            stv.setWarehouse(sta.getMainWarehouse());
        }
        stv.setTransactionType(tranType);// 作业类型
        stv = stvDao.save(stv);
        List<StvLine> stvLines = new ArrayList<StvLine>();
        for (StaLine staLine : stalineList) {

            StvLine stvLine = new StvLine();
            stvLine.setInvStatus(staLine.getInvStatus());
            stvLine.setSku(staLine.getSku());
            stvLine.setStaLine(map.get(staLine.getSku().getCode()));
            stvLine.setDirection(TransactionDirection.INBOUND);
            stvLine.setStv(stv);
            stvLine.setTransactionType(tranType);
            stvLine.setLocation(locList);
            stvLine.setDistrict(locList.getDistrict());
            stvLine.setWarehouse(stv.getWarehouse());
            stvLine.setOwner(sta.getOwner());
            stvLine.setQuantity(staLine.getQuantity());
            if (map.get(staLine.getSku().getCode()) == null) {
                stvLine.setSkuCost(new BigDecimal(0));
            } else {
                stvLine.setSkuCost(map.get(staLine.getSku().getCode()).getSkuCost());
            }
            stvLine = stvLineDao.save(stvLine);
            stvLines.add(stvLine);
        }
        validateBiChannelSupport(stv, null);
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setLastModifyTime(new Date());
        stv.setFinishTime(new Date());
        stvDao.save(stv);
        stvDao.flush();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_ou_id", stv.getWarehouse().getId());
        params.put("in_stv_id", stv.getId());
        params.put("in_com_id", wareHouseManager.findCompanyOUByWarehouseId(stv.getWarehouse().getId()).getId());
        params.put("is_in_cost", stv.getTransactionType().getIsInCost() ? 1 : 0);
        SqlParameter[] sqlParameters = {new SqlParameter("in_ou_id", Types.NUMERIC), new SqlParameter("in_stv_id", Types.NUMERIC), new SqlParameter("in_com_id", Types.NUMERIC), new SqlParameter("is_in_cost", Types.NUMERIC)};
        inventoryDao.executeSp("sp_insert_inventory", sqlParameters, params);
        wareHouseManager.updateStaLineQuantityForPurchase(stv.getSta().getId(), stvLines);
        wareHouseManager.updateSTAForPurchase(stv.getSta().getId(), stv.getOperator(), sta.getIntStatus() == StockTransApplicationStatus.FINISHED.getValue());
    }

    public void updatePackageInfoTrackingNo(ExpressOrderCommand expressOrderCmd, List<ExpressOrderCommand> expLineCmd) {
        String lpcode = expressOrderCmd.getLpcode();// 获取物流商code
        BusinessException root = null;
        BusinessException parent = null;
        // 验证快递单号合法性
        for (ExpressOrderCommand exporCmd : expLineCmd) {
            boolean flag = false;
            flag = wareHouseManager.checkTrackingNoByLpcode(lpcode, exporCmd.getTrackingNo());
            if (!flag && root == null) {
                root = new BusinessException(ErrorCode.EXPRESS_ORDER_TRACKINGNO_CHECK);
                parent = root;
            }
            if (!flag) {
                BusinessException current = new BusinessException(ErrorCode.EXPRESS_ORDER_TRACKINGNO_NO_LEGAL, new Object[] {exporCmd.getTrackingNo()});
                parent.setLinkedException(current);
                parent = current;
            }
        }
        // 不合法快递单号,抛出异常
        if (root != null) throw root;

        Long piQty = packageInfoDao.findPackageQtyByStaIdSql(expressOrderCmd.getId(), new SingleColumnRowMapper<Long>(Long.class));
        if (piQty > 0) {

            root = new BusinessException(ErrorCode.EXPRESS_ORDER_TRACKINGNO_NOT_CREATE);
            throw root;
        }
        packageInfoDao.deletePackageInfoByStaId(expressOrderCmd.getId());// 删除原作业单关联快递单号
        // 删除包材 信息
        staAdditionalLineDao.deleteStaAddLineByStaId(expressOrderCmd.getId());
        int count = 0;// 计数器
        // 添加新的快递单号，关联对应STA
        for (ExpressOrderCommand exporCmd : expLineCmd) {
            StaDeliveryInfo stadell = staDeliveryInfoDao.findStaDeliveryInfoByTrackingNo(exporCmd.getTrackingNo());
            if (stadell != null && root == null) {
                // 非合并订单，如果 快递单号存在，就报错
                StockTransApplication sta = staDao.getByPrimaryKey(stadell.getId());
                if (sta.getGroupSta() != null) {
                    sta = staDao.getByPrimaryKey(sta.getGroupSta().getId());
                }
                if (sta.getIsMerge() == null || !sta.getIsMerge()) {
                    root = new BusinessException(ErrorCode.EXPRESS_ORDER_TRACKINGNO_UNIQUENE, new Object[] {exporCmd.getTrackingNo()});
                    parent = root;
                    throw root;
                }
            }

            Long qty = packageInfoDao.countQtyByTrackingNo(lpcode, exporCmd.getTrackingNo(), expressOrderCmd.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (qty > 0) {
                root = new BusinessException(ErrorCode.EXPRESS_ORDER_TRACKINGNO_UNIQUENE, new Object[] {exporCmd.getTrackingNo()});
                parent = root;
                throw root;
            }

            if (count == 0) {
                StaDeliveryInfo staDel = staDeliveryInfoDao.getByPrimaryKey(expressOrderCmd.getId());
                staDel.setTrackingNo(exporCmd.getTrackingNo());
                // 是否分包 fanht
                if (expLineCmd.size() > 1) {
                    staDel.setIsMorePackage(true);
                } else {
                    staDel.setIsMorePackage(false);
                }
                staDel.setLastModifyTime(new Date());
                staDeliveryInfoDao.save(staDel);
                count++;
            }
            packageInfoDao.insertPackageInfo(lpcode, exporCmd.getTrackingNo(), expressOrderCmd.getId());
        }
    }

    @Override
    public ReadStatus purchaseSetImperfect(File file, User operator) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = staReaderInboundShelves.readAll(new FileInputStream(file), beans);
        } catch (FileNotFoundException e) {
            if (log.isErrorEnabled()) {
                log.error("purchaseSetImperfect FileNotFoundException", e);
            }
        }
        if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return readStatus;
        }
        @SuppressWarnings("unchecked")
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLineList");

        Map<String, List<StvLineCommand>> maps = new HashMap<String, List<StvLineCommand>>();
        for (StvLineCommand stvLineCommand : stvLines) {
            List<StvLineCommand> commands = maps.get(stvLineCommand.getStaCode());
            if (commands == null) {
                List<StvLineCommand> lineCommands = new ArrayList<StvLineCommand>();
                lineCommands.add(stvLineCommand);
                maps.put(stvLineCommand.getStaCode(), lineCommands);
            } else {
                List<StvLineCommand> commands1 = maps.get(stvLineCommand.getStaCode());
                commands1.add(stvLineCommand);
                maps.put(stvLineCommand.getStaCode(), commands1);
            }
        }
        for (String staCode : maps.keySet()) {
            StockTransApplication sta = staDao.getByCode(staCode);
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
            if (!StockTransVoucherStatus.CREATED.equals(stv.getStatus())) {
                throw new BusinessException(ErrorCode.STV_STATUS_ERROR);
            }
            // 暂存区出库
            if (stv.getSta() != null && StockTransApplicationType.GI_PUT_SHELVES.equals(stv.getSta().getType())) {
                try {
                    wmExecute.executeGIout(stv.getSta(), stv.getId(), operator.getId());
                } catch (CloneNotSupportedException e) {
                    log.error("", e);
                    throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
                }
            }

            // 查询退货对应销售的批货批次当中所对应出库的商品已经过期时间
            String slipCode = stv.getSta().getSlipCode1();
            String groupStaCode = staDao.findGroupStaCode(slipCode, new SingleColumnRowMapper<String>(String.class));
            List<StvLineCommand> slList = stvLineDao.findExpireDateByPickingList(groupStaCode, slipCode, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            Map<String, List<StvLineCommand>> map = new HashMap<String, List<StvLineCommand>>();
            for (StvLineCommand com : slList) {
                String key = com.getSkuId() + "_" + com.getStrExpireDate();
                List<StvLineCommand> tempList = null;
                if (map.containsKey(key)) {
                    tempList = map.get(key);
                } else {
                    tempList = new ArrayList<StvLineCommand>();
                    map.put(key, tempList);
                }
                tempList.add(com);
            }
            List<StvLine> lineList = new ArrayList<StvLine>();
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
            for (StvLineCommand l : maps.get(staCode)) {
                WarehouseLocation location = warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId());
                if (location == null) {
                    throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_FOUND, new Object[] {l.getLocation().getCode()});
                }
                Sku sku = skuDao.getSkuByBarcode(l.getSku().getBarCode());
                BiChannel biChannel = biChannelDao.getByName(l.getOwner());

                StvLine stvline = stvLineDao.findConfirmDiversityStvLine(stv.getId(), sku.getId(), l.getInvStatus().getName(), biChannel.getCode());
                l.setStaLine(stvline.getStaLine());
                l.setBatchCode(stvline.getBatchCode());
                l.setInvStatus(stvline.getInvStatus());
                l.setSku(sku);
                l.setOwner(biChannel.getCode());
                // 判断是否保质期商品
                if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                    wareHouseManagerProxy.setStvLineProductionDateAndExpireDate(l, l.getStrPoductionDate(), l.getStrExpireDate());
                    // 校验保质期商品 收货校验保质期于出库作业单配货清单中，如不正确提示错误信息，确认强行入库时则只能入残次品状态
                    String strEpireDate = formatDate.format(l.getExpireDate());
                    String key = sku.getId() + "_" + strEpireDate;
                    if (map.containsKey(key)) {
                        List<StvLineCommand> tempList = map.get(key);
                        Long quantity = l.getQuantity();
                        for (int i = 0; i < tempList.size(); i++) {
                            StvLineCommand sl = tempList.get(i);
                            if (sl.getQuantity() >= quantity) {
                                lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), quantity,
                                        l.getBatchCode(), l.getInBoundTime(), sl.getProductionDate(), sl.getValidDate(), sl.getExpireDate(), l.getStaLine(), stv));
                                sl.setQuantity(sl.getQuantity() - quantity);
                                quantity = 0l;
                            } else {
                                lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), sl.getQuantity(),
                                        l.getBatchCode(), l.getInBoundTime(), sl.getProductionDate(), sl.getValidDate(), sl.getExpireDate(), l.getStaLine(), stv));
                                quantity -= sl.getQuantity();
                                sl.setQuantity(0l);
                            }
                            if (sl.getQuantity() < 1) {
                                tempList.remove(i);
                            }
                            if (quantity.equals(0L)) {
                                break;
                            }
                        }
                        if (quantity > 0) {
                            // 抛出错误提示
                            throw new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate});
                        }
                    } else {
                        // 抛出错误提示
                        throw new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate});
                    }
                } else {
                    lineList.add(wareHouseManager.createStvLine(sku, l.getOwner(), warehouseLocationDao.findByLocationCode(l.getLocation().getCode(), stv.getWarehouse().getId()), l.getInvStatus(), l.getTransactionType(), l.getQuantity(),
                            l.getBatchCode(), l.getInBoundTime(), l.getProductionDate(), l.getValidDate(), l.getExpireDate(), l.getStaLine(), stv));
                }
            }
            wareHouseManager.purchaseReceiveStep2(stv.getId(), lineList, false, operator, false, true);
            // inboundNoticePAC
            StockTransApplication sta1 = stv.getSta();
            if (sta1 == null) {
                throw new BusinessException(ErrorCode.STA_NOT_FOUND);
            }
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 10l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_INBOUND);// 2为占用、10为完成、17为取消、20为作废

        }
        return readStatus;
    }


    @Override
    public void editAllSkuByCode(SkuCommand sku, Long userId) {
        Boolean flag = null;
        Sku pSku = null;
        SkuCategories sc = null;
        if (sku.getSkuCategoriesName() != null && !sku.getSkuCategoriesName().trim().equals("")) {
            String key = sku.getSkuCategoriesName().trim();
            sc = skuCategoriesDao.getBySkuCategoriesName(key);
            if (sc == null) {
                flag = false;
            } else {
                flag = true;
            }
            if (!flag) {
                throw new BusinessException(ErrorCode.IMPORT_CATAGORY_NOTEXISTS, new Object[] {sku.getSkuCategoriesName()});
            }// 编号对应的商品分类不存在
        }
        flag = null;
        if (sku.getPackageBarCode() != null && !sku.getPackageBarCode().trim().equals("")) {
            String key = sku.getPackageBarCode().trim();
            pSku = skuDao.getByBarcode1(key);
            if (pSku == null) {
                flag = false;
            } else {
                if (pSku.getCode().equals(sku.getCode())) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
            if (!flag) {
                // 编码{0}对应的箱型不存在/与当前维护商品一致
                throw new BusinessException(ErrorCode.IMPORT_PACKAGESKU_ERROR, new Object[] {sku.getCode()});
            }
        }
        try {
            if (sku.getRailwayStr() != null) {
                if (sku.getRailwayStr().equals("是")) {
                    sku.setDeliveryType(TransDeliveryType.LAND);
                } else {
                    sku.setDeliveryType(TransDeliveryType.ORDINARY);
                }
            }

            // Sku sku1 = skuDao.getByCode(sku.getCode());
            Sku sku1 = skuDao.getByPrimaryKey(sku.getId());
            if (sku.getSkuTypeId() != null) {
                sku1.setSkuType(skuTypeDao.getByPrimaryKey(sku.getSkuTypeId()));
            }
            if ("是".equals(sku.getIsConsumable())) {
                sku1.setSpType(SkuSpType.CONSUMPTIVE_MATERIAL);
            } else {
                sku1.setSpType(null);
            }
            sku1.setLength(sku.getLength());
            sku1.setWidth(sku.getWidth());
            sku1.setHeight(sku.getHeight());
            sku1.setGrossWeight(sku.getGrossWeight());
            sku1.setPaperSku(pSku);
            if (sc != null) {
                sku1.setSkuCategoriesId(sc.getId());
            }
            sku1.setDeliveryType(sku.getDeliveryType());
            sku1.setLastModifyTime(new Date());
            sku1.setHtsCode(sku.getHtsCode());
            sku1.setUnitName(sku.getUnitName());
            sku1.setCountryOfOrigin(sku.getCountryOfOrigin());
            sku1.setListPrice(sku.getListPrice());
            // //
            sku1.setCode(sku.getCode());
            sku1.setName(sku.getName());
            sku1.setSupplierCode(sku.getSupplierCode());
            sku1.setColor(sku.getColor());
            sku1.setColorName(sku.getColorName());
            sku1.setSkuSize(sku.getSkuSize());
            sku1.setEnName(sku.getEnName());
            sku1.setExtProp1(sku.getExtProp1());
            sku1.setExtProp2(sku.getExtProp2());
            sku1.setExtProp3(sku.getExtProp3());
            sku1.setExtProp4(sku.getExtProp4());
            sku1.setBarCode(sku.getBarCode());
            sku1.setCustomerSkuCode(sku.getCustomerSkuCode());
            sku1.setJmCode(sku.getJmCode());
            sku1.setValidDate(sku.getValidDate());
            sku1.setWarningDate(sku.getWarningDate());
            sku1.setCategory(sku.getCategory());
            sku1.setKeyProperties(sku.getKeyProperties());
            // //
            SkuModifyLog smf = wareHouseManager.refreshSkuModifyLog(sku1);
            smf.setOpUserId(userId);
            if (smf != null) {
                skuModifyLogDao.save(smf);// 将新建或修改后的SKU保存进变更日志表
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("editAllSkuByCode Exception:" + sku.getCode(), e);
            }
            throw new BusinessException(ErrorCode.UPDATE_PRO_INFO_ERROR);
        }
    }

    /**
     * 新增退货申请
     */
    public String addReturnApplication(Long ouId, User user, ReturnApplicationCommand app) {
        // returnStatus : 0：作废 、 1：新建、 2.已提交、3：已反馈，4：新建.5:重新尝试
        // 作废退货申请
        if (!app.getReturnStatus().equals("") && app.getReturnStatus().equals("0")) {
            ReturnApplication rapps = returnApplicationDao.getByPrimaryKey(app.getId());
            rapps.setSatus(ReturnApplicationStatus.USELESS);
            return "";
        }
        // 重新尝试，将原订单作废,生成新的订单
        if (app.getReturnStatus().equals("5")) {
            ReturnApplication rapps = returnApplicationDao.getByPrimaryKey(app.getId());
            rapps.setSatus(ReturnApplicationStatus.USELESS);
            app.setId(null);
        }
        // 校验库位
        WarehouseLocation loc = warehouseLocationDao.findLocationByCode(app.getLocationCode(), ouId);
        if (loc == null) {
            throw new BusinessException(ErrorCode.LOCATION_NOT_USING, new Object[] {app.getLocationCode()});
        }
        ReturnApplication rap = null;
        StockTransApplication sta = null; // 退货入
        StockTransApplication sta1 = null; // 销售出
        StockTransApplication sta2 = null; // 待查询作业单号
        if (app.getId() == null) { // id不为空，则修改
            rap = new ReturnApplication();
        } else {
            rap = returnApplicationDao.getByPrimaryKey(app.getId());
            if (rap == null) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
        }
        if (!app.getStaCode().equals("") || !app.getSlipCode().equals("") || !app.getSlipCode1().equals("") || !app.getSlipCode2().equals("")) {
            if (app.getStaCode().equals("")) {
                app.setStaCode(null);
            }
            if (app.getSlipCode().equals("")) {
                app.setSlipCode(null);
            }
            if (app.getSlipCode1().equals("")) {
                app.setSlipCode1(null);
            }
            if (app.getSlipCode2().equals("")) {
                app.setSlipCode2(null);
            }
            // 退货入作业单
            if (app.getStaCode() == null && app.getSlipCode() == null) {
                sta = null;
            } else {
                sta = staDao.findStaByTypeAndPro(null, 41l, app.getStaCode(), app.getSlipCode(), null, null, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
            }
            // 销售出作业单
            if (app.getSlipCode1() == null && app.getSlipCode2() == null) {
                sta1 = null;
            } else {
                sta1 = staDao.findStaByTypeAndPro(null, 21l, null, app.getSlipCode1(), null, app.getSlipCode2(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
            }
            if (sta == null && sta1 == null) {
                throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
            } else {
                // 根据销售单找到对应的退货入单
                if (sta == null && sta1 != null) {
                    sta2 = staDao.findStaByTypeAndPro(null, 41l, null, null, sta1.getSlipCode1(), null, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                    if (sta2 != null) {
                        rap.setExtended(sta2.getCode());
                        rap.setSlipCode(sta2.getRefSlipCode());
                    }
                    rap.setSlipCode2(sta1.getSlipCode2());
                    rap.setSlipCode1(sta1.getRefSlipCode()); // 销售出的单号
                } else if (sta != null && sta1 == null) { // 根据退货入找到对应的销售出
                    sta2 = staDao.findStaByTypeAndPro(null, 21l, null, null, sta.getSlipCode1(), null, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                    if (sta2 != null) {
                        rap.setSlipCode2(sta2.getSlipCode2());
                        rap.setSlipCode1(sta2.getRefSlipCode()); // 销售出的单号
                    }
                    rap.setExtended(sta.getCode());
                    rap.setSlipCode(sta.getRefSlipCode());
                } else {
                    rap.setExtended(sta.getCode());
                    rap.setSlipCode(sta.getRefSlipCode());
                    rap.setSlipCode2(sta1.getSlipCode2());
                    rap.setSlipCode1(sta1.getRefSlipCode()); // 销售出的单号
                }
            }
        }
        // 保存退货申请

        rap.setWhOuId(ouId);
        rap.setVersion(0l);
        rap.setCreateUserId(user.getId());
        rap.setLastModiftTime(new Date());
        rap.setLpCode(app.getLpCode());
        rap.setSatus(ReturnApplicationStatus.NEW_CREATE);
        rap.setTelephone(app.getTelephone());
        // 新增则添加，修改无需改字段
        if (app.getId() == null) {
            if (sta == null) {
                // 无指令CODE生成。 格式 W+日期转String
                Date date = new Date();
                String StringDate = Long.toString(date.getTime());
                rap.setCode("W" + StringDate);
            } else {
                // 有指令CODE生成。 格式 Y+日期转String
                Date date = new Date();
                String StringDate = Long.toString(date.getTime());
                rap.setCode("Y" + StringDate);
            }
        }
        if (app.getIsUpload().equals("true")) {
            rap.setExtended2("1");
        } else {
            rap.setExtended2("0");
        }
        rap.setCreateTime(new Date());
        rap.setTrankNo(app.getTrankNo());
        rap.setReturnUser(app.getReturnUser());
        rap.setLocationCode(app.getLocationCode());
        rap.setOwner(app.getOwner());
        rap.setMemo(app.getMemo());
        //如果是ad设置brand为1
        if("1".equals(app.getBrand())){
            rap.setBrand("1");
        }
        returnApplicationDao.save(rap);
        returnApplicationDao.flush();
        // 修改退货申请，删除明细，然后重新添加。
        if (app.getId() != null) {
            returnApplicationLineDao.deleteLineByRaId(rap.getId());
        }
        // 保存退货申请明细
        String sku1[] = app.getSkuString1().split("/");
        List<com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.PackageLine> lineList = new ArrayList<com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.PackageLine>(); // 封装调用oms接口的明细
        Long skuQty = 0l;
        // 解析并保存条码扫描商品信息
        for (int i = 0; i < sku1.length; i++) {
            if (!sku1[i].equals("")) {
                ReturnApplicationLine line = new ReturnApplicationLine();
                Long skuId =0L;
                Long quantity =0L;
                String status=null;
                String wmsOrdeType=null;
                if("1".equals(app.getBrand())){
                    skuId = Long.parseLong(sku1[i].substring(0, sku1[i].indexOf(",")));
                    quantity = Long.parseLong(sku1[i].substring(sku1[i].indexOf(",") + 1, sku1[i].indexOf(";")));
                    status = sku1[i].substring(sku1[i].indexOf(";") + 1, sku1[i].indexOf("#"));
                    wmsOrdeType = sku1[i].substring(sku1[i].indexOf("#") + 1, sku1[i].length());
                }else{
                    skuId = Long.parseLong(sku1[i].substring(0, sku1[i].indexOf(",")));
                    quantity = Long.parseLong(sku1[i].substring(sku1[i].indexOf(",") + 1, sku1[i].indexOf(";")));
                    status = sku1[i].substring(sku1[i].indexOf(";") + 1, sku1[i].length());
                }
                skuQty += quantity;
                line.setSkuId(skuId);
                line.setQty(quantity);
                InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu(status, rap.getWhOuId());
                if (invStatus != null) {
                    line.setInvStatusId(invStatus.getId());
                }
                // line.setInvStatusId(status);
                line.setRaId(rap.getId());
                line.setWmsOrdeType(wmsOrdeType);
                returnApplicationLineDao.save(line);
                // 封装调用oms接口的明细
                com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.PackageLine lines = new com.jumbo.pac.manager.extsys.wms.rmi.model.exceptionPackage.PackageLine();
                Sku sku = skuDao.getByPrimaryKey(skuId);
                lines.setSkuCode(sku.getCode());
                lines.setInvStatus(status);
                lines.setQty(quantity);
                // InventoryStatus to = inventoryStatusDao.getByPrimaryKey(status);
                // lines.setInvStatus(to.getName());
                lineList.add(lines);
            }
        }
        // 更新退货申请的商品数量
        ReturnApplication rapp = returnApplicationDao.getByPrimaryKey(rap.getId());
        rapp.setSkuQty(skuQty);
        // 创建并提交 调用oms接口
        if ((app.getReturnStatus() != null) && (app.getReturnStatus().equals("4") || app.getReturnStatus().equals("5"))) {
            BiChannel bichannel = biChannelDao.getByCode(rapp.getOwner());
            if (null != bichannel && null != bichannel.getCustomer().getCode() && "adidas".equals(bichannel.getCustomer().getCode())) {
                rapp.setSource("adidas");
                rapp.setSatus(ReturnApplicationStatus.ISCOMMIT);
            } else {
                // 根据店铺判断平台店铺
                // 封装调用oms接口所需要的信息
                try {
                    ShopInfoResponse br = rmi4Wms.findShopInfoByInnerShopCode(rapp.getOwner());
                    if (br == null) {
                        throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
                    }
                    if (br.getExtSysCode() == null || br.getExtSysCode().equals("")) {
                        throw new BusinessException(ErrorCode.CHANNEL_NOT_FOUNT);
                    }
                    rapp.setSource(br.getExtSysCode());
                    // 若果是tmall，待hub查询调用
                    if (rapp.getSource().equals("tmalloms")) {
                        rapp.setSatus(ReturnApplicationStatus.ISCOMMIT);
                    } else {
                        // pacs直接反馈pacs
                        PackageHead head = new PackageHead();
                        head.setWmsBillCode(rapp.getCode());
                        head.setShopOwner(rapp.getOwner());
                        head.setMobile(rapp.getMobile());
                        head.setRemark(rapp.getMemo());
                        head.setRtnPersion(rapp.getReturnUser());
                        head.setSlipCode1(rapp.getSlipCode2()); // 平台订单号
                        head.setSlipCode2(rapp.getSlipCode1()); // 销售相关单据号
                        head.setSlipCode3(rapp.getSlipCode()); // 退货入相关单据号
                        head.setTelephone(rapp.getTelephone());
                        head.setTrackingNo(rapp.getTrankNo());
                        head.setTransCode(rapp.getLpCode());
                        OperationUnit unit = operationUnitDao.getByPrimaryKey(ouId);
                        head.setWarehouseCode(unit.getCode());
                        head.setRtnPersion(rapp.getReturnUser());
                        head.setWmsOperator(user.getUserName());
                        head.setCheckedTime(rapp.getCreateTime());
                        if (rapp.getExtended2() != null && rapp.getExtended2().equals("1")) {
                            head.setFileName(rap.getCode());
                        } else {
                            head.setFileName(null);
                        }
                        if (rapp.getCode().substring(0, 1).equals("Y")) {
                            head.setPackageType(ExceptionPackageType.PACKAGE_WITH_ORDER);// 包裹类型2：退回包裹有指令
                        } else {
                            head.setPackageType(ExceptionPackageType.PACKAGE_WITHOUT_ORDER);// 包裹类型1：退回包裹无指令
                        }
                        head.setLines(lineList);
                        try {
                            log.debug("Call oms returnApplication interface------------------->BEGIN");
                            BaseResult brs = rmi4Wms.packageInfoSync(head);
                            if (brs.getStatus() == 0) {
                                // 接口异常
                                log.error("Call oms returnApplication interface-------------------->ERROR");
                                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                            }
                            rapp.setSatus(ReturnApplicationStatus.ISCOMMIT); // 更新状态为已提交
                            log.debug("Call oms returnApplication interface------------------->BEGIN");
                        } catch (Exception e) {
                            throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                        }
                    }
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
                }
            }
        }
        return rap.getCode();
    }

    @Override
    public void invChangeLongAddErrorCount(Long id) {
        InvetoryChange invetoryChange = invetoryChangeDao.getByPrimaryKey(id);
        if (invetoryChange != null) {
            Long errorCount = invetoryChange.getErrorCount();
            invetoryChange.setErrorCount(errorCount + 1);
        }

    }

    @Override
    public void editCartonNumByStaId(Long staId,Integer cartonNum){
        //获取作业申请单数据
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        //无作业申请单数据，则返回操作错误
        if (sta == null){
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] { staId });
        }
        //状态未已完成的 不可以修改废纸箱数量 CREATED(1):已创建     OCCUPIED(2):库存占用（配货中）  CHECKED(3):已核对 INTRANSIT(4):已转出   PARTLY_RETURNED(5):部分转入 PACKING(8):装箱中
        if(StockTransApplicationStatus.CREATED.equals(sta.getStatus())
                        ||StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())
                        ||StockTransApplicationStatus.CHECKED.equals(sta.getStatus())
                        ||StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())
                        ||StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())
                        ||StockTransApplicationStatus.PACKING.equals(sta.getStatus())) {
            sta.setCartonNum(cartonNum);
            staDao.save(sta);
            
        }
    }
    

    public ChooseOption getChooseOptionCache(String strCode) {
        if (StringUtil.isEmpty(strCode)) return null;
        ChooseOption result = chooseOptionCache.get(strCode);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            result = cacheChooseOption(strCode);
        }
        return result;
    }

    private synchronized ChooseOption cacheChooseOption(String strCode) {
        ChooseOption result = chooseOptionDao.findByCategoryCodeAndKey("snOrExpDate", "1");
        chooseOptionCache.put(strCode, result, 10 * 60 * 1000);
        return result;
    }
}
