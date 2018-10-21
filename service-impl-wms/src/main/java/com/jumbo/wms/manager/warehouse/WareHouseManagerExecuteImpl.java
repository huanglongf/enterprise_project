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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.ecp.ip.command.response.Response;
import com.baozun.ecp.ip.manager.wms3.Wms3AdapterInteractManager;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.baseinfo.InterfaceSecurityInfoDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.MsgOrderCancelDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.CartonLineDao;
import com.jumbo.dao.warehouse.DeliveryChanngeLogDao;
import com.jumbo.dao.warehouse.EMSConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.HandOverListDao;
import com.jumbo.dao.warehouse.HandOverListLineDao;
import com.jumbo.dao.warehouse.ImperfectCartonLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.OutBoundPackDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PdaOrderDao;
import com.jumbo.dao.warehouse.PdaOrderLineDao;
import com.jumbo.dao.warehouse.SkuChildSnDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.SkuSnCheckCfgDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.SkuSnMappingDao;
import com.jumbo.dao.warehouse.SkuSnOpLogDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaCancelNoticeOmsDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WareHandOverListDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderLine;
import com.jumbo.rmiservice.RmiService;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.hub2wms.WmsThreePLManager;
import com.jumbo.wms.manager.hub4.WmsOrderServiceToHub4Manager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.TransInfoManager;
import com.jumbo.wms.manager.task.pda.PdaTaskManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManager;
import com.jumbo.wms.model.DefaultLifeCycleStatus;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.SShouRongQi;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;
import com.jumbo.wms.model.baseinfo.SkuSnCheckCfgType;
import com.jumbo.wms.model.baseinfo.SkuSnCheckMode;
import com.jumbo.wms.model.baseinfo.SkuSnMapping;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuSnCheckCfgCommand;
import com.jumbo.wms.model.hub2wms.MsgOrderCancel;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrder;
import com.jumbo.wms.model.hub2wms.WmsShipping;
import com.jumbo.wms.model.hub2wms.WmsShippingLine;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderType;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLineCommand;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLineCommand;
import com.jumbo.wms.model.warehouse.CartonStatus;
import com.jumbo.wms.model.warehouse.DeliveryChanngeLog;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListCommand;
import com.jumbo.wms.model.warehouse.HandOverListLine;
import com.jumbo.wms.model.warehouse.HandOverListStatus;
import com.jumbo.wms.model.warehouse.ImperfectCartonLineCommand;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryOccupyCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.OutBoundPack;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.SkuChildSn;
import com.jumbo.wms.model.warehouse.SkuChildSnCommand;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnOpLog;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaCancelNoticeOms;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucherType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WareHandOverList;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.web.commond.StockTransCartonCommand;
import com.opensymphony.xwork2.Action;

import cn.baozun.bh.util.JSONUtil;
import loxia.dao.support.BaseRowMapper;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

@Transactional
@Service("wareHouseManagerExecute")
public class WareHouseManagerExecuteImpl extends BaseManagerImpl implements WareHouseManagerExecute {

    /**
     * 
     */
    private static final long serialVersionUID = -370114353779783454L;

    @Resource(name = "staReaderInbound")
    private ExcelReader staReaderInbound;
    @Resource(name = "staReaderInboundTurn")
    private ExcelReader staReaderInboundTurn;
    @Resource(name = "staReaderConfirmDiversity")
    private ExcelReader staReaderConfirmDiversity;
    @Resource(name = "staReaderInboundShelves")
    private ExcelReader staReaderInboundShelves;
    @Resource(name = "virtualInBoundShelvesRed")
    private ExcelReader virtualInBoundShelvesRed;
    @Resource(name = "skuSeedSn")
    private ExcelReader inSkuChildSn;
    
    @Resource(name = "staReaderCartonNUmImport")
    private ExcelReader staReaderCartonImport;

    @Autowired
    private PdaTaskManager pdaTaskManager;
    @Autowired
    private PdaOrderDao pdaOrderDao;
    @Autowired
    private PdaOrderLineDao pdaLineDao;
    @Autowired
    private WarehouseDistrictDao districtDao;
    @Autowired
    private OutBoundPackDao outBoundPackDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private HandOverListLineDao handOverListLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HandOverListDao handOverListDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private CartonLineDao cartonLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WarehouseLocationDao locationDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private EMSConfirmOrderQueueDao emsConfirmOrderQueueDao;
    @Autowired
    private WareHouseManagerProxy whManagerProxy;
    @Autowired
    private InterfaceSecurityInfoDao interfaceSecurityInfoDao;
    @Autowired
    private StaCancelNoticeOmsDao staCancelNoticeOmsDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private RmiService rmiService;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    private EventObserver eventObserver;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private SkuSnCheckCfgDao skuSnCheckCfgDao;
    @Autowired
    private SkuSnMappingDao skuSnMappingDao;
    @Autowired
    private SkuSnOpLogDao skuSnOpLogDao;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private MsgOrderCancelDao msgOrderCancelDao;
    @Autowired
    private ImperfectCartonLineDao imperfectCartonLineDao;
    @Autowired
    private SkuImperfectDao imperfectDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgInboundOrderLineDao inboundOrderLineDao;
    @Autowired
    private WareHandOverListDao wareHandOverListDao;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private SkuChildSnDao childSnDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private DeliveryChanngeLogDao dliveryChanngeLogDao;

    @Autowired
    private VmiStaCreateManager vmiStaCreateManager;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private TransInfoManager transInfoManager;
    
    
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Autowired
    private Wms3AdapterInteractManager wms3AdapterInteractManager;

    @Autowired
    private WmsOrderServiceToHub4Manager wmsOrderServiceToHub4Manager;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:WareHouseManagerExecuteImpl");
        }
    }

    public void updateStaTransNoForOccupied(String staCode, String transNo) {
        StockTransApplication sta = staDao.findStaByCode(staCode);
        if (StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            sta.getStaDeliveryInfo().setTrackingNo(transNo);
            String lpcode = sta.getStaDeliveryInfo().getLpCode();
            if (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode)) {
                emsConfirmOrderQueueDao.updateBillNo(staCode, transNo);
            }
        } else {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {staCode});
        }
    }


    @Override
    public void updateStaOuId(String staCode, String name) {
        StockTransApplication sta = staDao.findStaByCode(staCode);
        List<StockTransVoucherCommand> stvs = stvDao.findEdwTwhStv(sta.getId(), new BeanPropertyRowMapper<StockTransVoucherCommand>(StockTransVoucherCommand.class));
        if (stvs.size() > 0) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {staCode});
        }
        if (StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            OperationUnit operationUnit = operationUnitDao.findByName(name);
            sta.setMainWarehouse(operationUnit);
            staDao.save(sta);
            Warehouse wh = warehouseDao.getByOuId(operationUnit.getId());
            if (StringUtils.hasText(wh.getVmiSource())) {
                MsgInboundOrder msgInorder = null;
                msgInorder = new MsgInboundOrder();
                Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                msgInorder.setUuid(id);
                msgInorder.setSource(wh.getVmiSource());
                msgInorder.setSourceWh(wh.getVmiSourceWh());
                msgInorder.setStaCode(sta.getCode());
                msgInorder.setStatus(DefaultStatus.CREATED);
                StockTransApplication stainfo = staDao.findStaByCode(sta.getCode());
                if (stainfo != null) {
                    msgInorder.setType(stainfo.getType());
                }
                msgInorder.setCreateTime(sta.getCreateTime());
                msgInorder.setPlanArriveTime(sta.getArriveTime());
                msgInorder.setTotalActual(sta.getTotalActual());
                msgInorder.setRefSlipCode(sta.getRefSlipCode());
                if (sta.getOwner() != null) {
                    msgInorder.setShopId(companyShopDao.getByCode(sta.getAddiOwner() == null ? sta.getOwner() : sta.getAddiOwner()).getId());
                }
                msgInboundOrderDao.save(msgInorder);
                msgInboundOrderDao.flush();
                List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
                for (StaLine line : stalines) {
                    MsgInboundOrderLine inline = new MsgInboundOrderLine();
                    inline.setQty(line.getQuantity());
                    inline.setSku(line.getSku());
                    inline.setMsgInOrder(msgInorder);
                    inline.setInvStatus(line.getInvStatus());
                    inboundOrderLineDao.save(inline);
                    inboundOrderLineDao.flush();
                }
            }
        } else {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {staCode});
        }

    }

    /**
     * 商品组合 - 执行操作 - 库存删减
     */
    public void skuGroupExecution(Long userId, Long invCkId) {
        // 更新盘点批状态
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_NOT_UNEXECUTE, new Object[] {ic.getCode()});
        }
        User user = userDao.getByPrimaryKey(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
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

        BigDecimal invStatusId = inventoryCheckDifferenceLineDao.findInvStatusIdByInvCheckId(invCkId, new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (invStatusId == null) {
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        ic.setConfirmUser(user.getUserName());
        ic.setStatus(InventoryCheckStatus.FINISHED);
        ic.setFinishTime(new Date());
        inventoryCheckDao.save(ic);
        // 解锁库位
        // warehouseLocationDao.unLockByInvCheck(ic.getId());
        // ===============================

        String batchCode = Long.valueOf(new Date().getTime()).toString();
        Long in_is_split = -1L;
        if (ic.getType().getValue() == 3) {
            in_is_split = 1L;
        } else if (ic.getType().getValue() == 4) {
            in_is_split = 0L;
        }
        Map<String, Object> invparams = new HashMap<String, Object>();
        invparams.put("in_ic_id", ic.getId());
        invparams.put("in_batch_code", batchCode);
        invparams.put("in_cmp_id", ic.getOu().getParentUnit().getParentUnit().getId());
        invparams.put("in_out_transtype_id", outType.getId());
        invparams.put("in_in_transtype_id", inType.getId());
        invparams.put("in_inv_status_id", invStatusId.longValue());
        invparams.put("in_is_split", in_is_split);

        log.debug("========================={} ======================= ic id :" + ic.getId() + "  batchCode: " + batchCode + "   cmpid :  " + ic.getOu().getParentUnit().getParentUnit().getId());
        log.debug("========================={} =======================outType id : " + outType.getId() + "  inType id :  " + inType.getId() + "  invStatusId id :   " + invStatusId.longValue());
        log.debug("================in_is_split {} ==================", in_is_split);

        SqlParameter[] invSqlP =
                {new SqlParameter("in_ic_id", Types.NUMERIC), new SqlParameter("in_batch_code", Types.VARCHAR), new SqlParameter("in_cmp_id", Types.NUMERIC), new SqlParameter("in_out_transtype_id", Types.NUMERIC),
                        new SqlParameter("in_in_transtype_id", Types.NUMERIC), new SqlParameter("in_inv_status_id", Types.NUMERIC), new SqlParameter("in_is_split", Types.NUMERIC)};

        staDao.executeSp("sp_sku_aggregation_execute", invSqlP, invparams);

        /***** mongoDB库存变更添加逻辑 ******************************/
        try {
            eventObserver.onEvent(new TransactionalEvent(ic));
        } catch (BusinessException e) {
            throw e;
        }
    }

    // 取消
    public void skuGroupCancel(Long invCheckId, User user) {
        InventoryCheck inventoryCheck = inventoryCheckDao.getByPrimaryKey(invCheckId);
        if (inventoryCheck == null) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND, new Object[] {""});
        }
        inventoryDao.updateOccupyIsNull(inventoryCheck.getCode());
        inventoryCheck.setStatus(InventoryCheckStatus.CANCELED);
        inventoryCheckDao.save(inventoryCheck);
        // 取消相关调整单
        whExe.cancelAdjustingSta(inventoryCheck.getId(), user);
    }

    public void partlyOutbound(Long staId, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!sta.getStatus().equals(StockTransApplicationStatus.PACKING)) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getStatus()});
        }
        User user = userDao.getByPrimaryKey(userId);
        List<CartonLineCommand> cl = cartonLineDao.findDiffSku(sta.getId(), new BeanPropertyRowMapperExt<CartonLineCommand>(CartonLineCommand.class));
        if (cl == null || cl.size() == 0) {
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            staDao.flush();
            // 执行出库
            wareHouseManager.predefinedOutExecution(staId, user);
        } else {
            Long cartonLineCount = cartonLineDao.findCompleteCartonLineCountByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (null == cartonLineCount || 0 == cartonLineCount) {
                throw new BusinessException(ErrorCode.ERROR_CARTON_LINE_ISNULL);
            }
            for (CartonLineCommand cmd : cl) {
                if (cmd.getQty() == null || cmd.getQty() < 0) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {"qty < 0"});
                }
            }

            BiChannel bc = biChannelDao.getByCode(sta.getOwner());

            // 部分出库
            StockTransVoucher outStv = stvDao.findStvCreatedByStaId(staId);
            if (outStv == null) {
                throw new BusinessException(ErrorCode.STV_NOT_FOUND);
            }
            // 创建取消STV
            StockTransVoucher stv = new StockTransVoucher();
            BigDecimal bn = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            stv.setBusinessSeqNo(bn.longValue());
            stv.setCode(stvDao.getCode(staId, new SingleColumnRowMapper<String>(String.class)));
            stv.setCreateTime(new Date());
            stv.setCreator(user);
            stv.setDirection(TransactionDirection.OUTBOUND);
            stv.setOperator(user);
            stv.setOwner(sta.getOwner());
            stv.setSta(sta);
            stv.setLastModifyTime(new Date());
            stv.setStatus(StockTransVoucherStatus.CANCELED);
            stv.setTransactionType(outStv.getTransactionType());
            stv.setWarehouse(outStv.getWarehouse());
            stvDao.save(stv);
            stvDao.flush();
            // 调用SP 修改库存占用
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("in_sta_id", staId);
            params.put("in_stv_id", stv.getId());
            params.put("in_transtype_id", stv.getTransactionType().getId());
            SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
            SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), new SqlParameter("in_stv_id", Types.NUMERIC), new SqlParameter("in_transtype_id", Types.NUMERIC), s};
            Map<String, Object> result = null;
            if (bc.getIsPartOutbound() != null && bc.getIsPartOutbound()) {
                result = staDao.executeSp("sp_rm_occ_inv_for_part_out", sqlParameters, params);
            } else {
                result = staDao.executeSp("sp_rm_occ_inv_for_out", sqlParameters, params);
            }
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
                // 执行出库
                // 重建原出库stv line
                stvLineDao.deleteStvLineByStvId(outStv.getId());
                stvLineDao.createByStaId(staId);
                // 完成 stv
                outStv.setLastModifyTime(new Date());
                outStv.setStatus(StockTransVoucherStatus.FINISHED);
                outStv.setFinishTime(new Date());
                outStv.setOperator(user);
                stvDao.save(stv);
                sta.setIsNeedOccupied(false);
                sta.setOutboundOperator(user);
                sta.setOutboundTime(new Date());
                sta.setInboundTime(new Date());
                sta.setFinishTime(new Date());
                sta.setLastModifyTime(new Date());
                sta.setStatus(StockTransApplicationStatus.FINISHED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                staDao.save(sta);
                if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
                    BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                    VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                    if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                        vmi.generateReceivingWhenFinished(sta);
                    }
                }
                staDao.flush();
                staLineDao.updateCompleteQuantityByStaId(sta.getId());
                // 计算成本
                staLineDao.updateSkuCostBySta(staId, sta.getMainWarehouse().getParentUnit().getParentUnit().getId());
                // 记录SN日志
                snLogDao.createTransitCrossLogByStvIdSql(outStv.getId());
                // 删除sn号
                snDao.deleteSNByStvIdSql(outStv.getId());
                // 移除占用库存
                wareHouseManager.removeInventory(sta, outStv);
                // 判断前置单据状态是否正确
                eventObserver.onEvent(new TransactionalEvent(sta));
                // 删除多余包裹
                cartonDao.deleteCreatedCartonBySta(sta.getId());
            }
            if ("1".equals(sta.getIsPf())) {// 唯品会 run
                eventObserver.onEvent(new TransactionalEvent(outStv));// 反馈给pacs
            }
        }
        // 唯品会 run
        // 根据作业单标示 来调用采购出库接口来反馈给pacs 并且过滤品牌反馈
        if ("1".equals(sta.getIsPf()) || !StringUtil.isEmpty(sta.getDataSource())) {//
        } else {
            // VMI退仓反馈
            BiChannel sh = companyShopDao.getByCode(sta.getOwner());
            if (sh != null && sh.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
                vf.generateRtnWh(sta);
            }
        }
        /**
         * 操作残次商品出库
         */
        List<ImperfectCartonLineCommand> cartonLineCommands = imperfectCartonLineDao.findImperfectCartonLineStaId(staId, new BeanPropertyRowMapperExt<ImperfectCartonLineCommand>(ImperfectCartonLineCommand.class));
        for (ImperfectCartonLineCommand cartonLineCommand : cartonLineCommands) {
            SkuImperfect imperfect = imperfectDao.getByPrimaryKey(cartonLineCommand.getImperfect().getId());
            imperfect.setStatus(2);
            imperfectDao.save(imperfect);
        }
        List<Carton> cartons = cartonDao.findCartonByStaId(staId);
        for (Carton carton : cartons) {
            if (carton.getStatus().equals(CartonStatus.CREATED) || carton.getStatus().equals(CartonStatus.DOING)) {
                cartonLineDao.deleteByCartonId(carton.getId());
                cartonDao.delete(carton);
            }
        }
        // 部分出库的单据 未执行量插入取消占用表给IM
        hubWmsService.insertOccupiedAndReleaseUnDeal(sta.getId());
        // sf和ems执行出库回传物流商确认信息
        transInfoManager.vmiRtoOrderConfrimToLogistics(staId);

    }

    public void createBetweenMoveSta(BetweenLabaryMoveCommand betwenMoveCmd, User user, OperationUnit ou, List<BetweenLabaryMoveCommand> staLineCmd) throws Exception {
        Order order = new Order();
        order.setAddOwner(betwenMoveCmd.getOwner());
        order.setOwner(betwenMoveCmd.getOwner());
        Warehouse wh = warehouseDao.getByOuId(betwenMoveCmd.getStartWarehouseId());
        String cuo = wh.getCustomer().getCode();
        order.setCustomerCode(cuo);
        Warehouse wh1 = warehouseDao.getByOuId(betwenMoveCmd.getEndWarehouseId());
        order.setAddWhOuCode(wh1.getOu().getCode());
        order.setMainWhOuCode(wh.getOu().getCode());
        order.setMainWhOuId(betwenMoveCmd.getStartWarehouseId());
        order.setAddWhOuId(betwenMoveCmd.getEndWarehouseId());
        order.setInvStatusId(betwenMoveCmd.getInvStatusId());
        order.setType(StockTransApplicationType.TRANSIT_CROSS.getValue());
        List<OrderLine> orderList = new ArrayList<OrderLine>();
        for (BetweenLabaryMoveCommand cd : staLineCmd) {
            OrderLine ol = new OrderLine();
            Sku sku = skuDao.getByPrimaryKey(cd.getId());
            ol.setSkuCode(sku.getCode());
            ol.setQty(cd.getMoveQuantity());
            orderList.add(ol);
        }
        order.setLines(orderList);
        order.setIsNotPacsomsOrder(true);
        BaseResult rs = rmiService.orderCreate(order);
        if (rs.getStatus() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {rs.getMsg()});
        }
    }

    public void occpuyForTransitCross(StockTransApplication sta) {
        List<InventoryOccupyCommand> list = inventoryDao.findForTransitCrossToOccupyInventory(sta.getId(), new BeanPropertyRowMapper<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        Long skuId = null;
        Long tqty = null;
        for (InventoryOccupyCommand cmd : list) {
            // 商品是排序的，如果商品不同则说明之前商品已经占用完成
            // 切换商品，初始化待占用数
            if (skuId == null || !skuId.equals(cmd.getSkuId())) {
                log.debug("============change sku,from {} to {}", skuId, cmd.getSkuId());
                skuId = cmd.getSkuId();
                tqty = cmd.getPlanOccupyQty() == null ? 0L : cmd.getPlanOccupyQty();
            }
            log.debug("to occupy inv, sku : {},inv qty : " + cmd.getQuantity() + ", paln qty : " + cmd.getPlanOccupyQty(), cmd.getSkuId());
            log.debug("to occupy inv,owner : {} ,inv status : {}", cmd.getOwner(), cmd.getStatusId());
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
        // 验证占用量
        wareHouseManager.validateOccupy(sta.getId());
    }

    /***
     * 库间移动出库
     */
    public void transitCrossStaOutBound(Long staId, Long userId) {
        // 外包仓库 逻辑 添加 - 8.27
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (sta.getMainWarehouse() == null) {
            throw new BusinessException(ErrorCode.CROSS_OUT_MAINHOUSE_IS_NULL);
        }
        if (sta.getAddiWarehouse() == null) {
            throw new BusinessException(ErrorCode.CROSS_IN_MAINHOUSE_IS_NULL);
        }
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND);
        if (stvList.size() != 1) {
            throw new BusinessException(ErrorCode.TRANIST_CROSS_STV_LINE_EMPTY);
        }
        StockTransVoucher stv = stvList.get(0);
        // 外包仓库
        if (StringUtils.hasLength(wh.getVmiSource()) && sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
            threePlOutBound(sta, stv);
        }
        User user = null;
        if (userId != null) {
            user = userDao.getByPrimaryKey(userId);
        }
        if (wh.getIsNeedWrapStuff() != null && wh.getIsNeedWrapStuff()) {
            List<StaAdditionalLine> temp = staAdditionalLineDao.findByStaId(sta.getId());
            if (temp == null || temp.size() == 0) {
                throw new BusinessException(ErrorCode.OUT_BOUND_NEED_WRAP_STUFF);
            }
        }
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        Date temp = new Date();
        // 出库
        wareHouseManager.removeInventory(sta, stv);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setFinishTime(temp);
        if (user != null) stv.setOperator(user);
        sta.setIsNeedOccupied(false);
        if (user != null) sta.setOutboundOperator(user);
        sta.setOutboundTime(temp);
        sta.setLastModifyTime(new Date());
        sta.setInboundTime(new Date());
        sta.setStatus(StockTransApplicationStatus.INTRANSIT);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.INTRANSIT.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        staDao.save(sta);
        staDao.flush();
        // 计算成本
        // staline 完成数量的更新， 取完成的stvline数量
        // 记录SN日志
        snLogDao.createTransitCrossLogByStvIdSql(stv.getId());
        // 删除sn号
        snDao.deleteSNByStvIdSql(stv.getId());
        log.debug("create trans cross sta end : {}", sta.getCode());
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * 外包仓出库
     * 
     * @param sta
     */
    private void threePlOutBound(StockTransApplication sta, StockTransVoucher stv) {
        Long staId = sta.getId();
        List<MsgRtnOutboundLineCommand> msgLines = msgRtnOutboundLineDao.findByMsgRtnOutboundByStaCode(sta.getCode(), new BeanPropertyRowMapperExt<MsgRtnOutboundLineCommand>(MsgRtnOutboundLineCommand.class));
        if (msgLines == null || msgLines.isEmpty()) {
            return;
        }
        List<StvLine> stvLines = stvLineDao.findAllByStaIdSort(staId, new BeanPropertyRowMapperExt<StvLine>(StvLine.class));
        if (stvLines == null || stvLines.isEmpty()) {
            throw new BusinessException(ErrorCode.STV_LINE_NOT_FOUND);
        }
        Map<Long, Long> resultMap = new HashMap<Long, Long>();
        Long qty = null, skuId = null;

        validateOutRtnMsg(msgLines, stvLines, resultMap);

        if (!resultMap.isEmpty()) {
            Map<Long, Long> stalines = staLineDao.findSkuIdStalineIdByStaId(staId, new BaseRowMapper<Map<Long, Long>>() {
                private Map<Long, Long> result = new HashMap<Long, Long>();

                public Map<Long, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long skuId = getResultFromRs(rs, "skuId", Long.class);
                    Long staLineId = getResultFromRs(rs, "staLineId", Long.class);
                    result.put(skuId, staLineId);
                    return result;
                }
            });
            // 取消的stvid
            BigDecimal newStvId = stvDao.getSequence(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            for (Map.Entry<Long, Long> entry : resultMap.entrySet()) {
                // 存在差异的skuid
                skuId = entry.getKey();
                // 差异数量
                qty = resultMap.get(skuId);
                // 释放当前sku库存，数量为qty -- > 调整原stvline 信息 , 创建取消 stvline
                releaseOccupyInventory(staId, stv, stalines, skuId, sta.getCode(), newStvId, qty);
            }
            // 库存调整完成，删除原来stvline
            stvLineDao.deleteRawStvlineByStaId(staId);
            // 库存调整后，重创新的stvline信息、
            if (StringUtils.hasLength(sta.getOwner())) {
                stvLineDao.createForCrossByStaId(staId);
            } else {
                stvLineDao.createForCrossByStaIdNoOwner(staId);
            }
            // 3. 取消的stv创建
            stvDao.generateCancelStatusStv(sta.getId(), newStvId, StockTransVoucherStatus.CANCELED.getValue());
        }
    }

    /***
     * 校验外包仓反馈的数据与出库数据是否一致
     * 
     * @param msgLines
     * @param stvLines
     * @param resultMap
     */
    private void validateOutRtnMsg(List<MsgRtnOutboundLineCommand> msgLines, List<StvLine> stvLines, Map<Long, Long> resultMap) {
        Long key = null;
        Map<Long, Long> msgSkuMap = new HashMap<Long, Long>();
        Map<Long, Long> stvLineSkuMap = new HashMap<Long, Long>();
        for (MsgRtnOutboundLineCommand msgLine : msgLines) {
            key = msgLine.getSkuId();
            if (key == null) {
                continue;
            }
            if (msgSkuMap.get(key) == null) {
                msgSkuMap.put(key, msgLine.getQty());
            } else {
                msgSkuMap.put(key, msgSkuMap.get(key) + msgLine.getQty());
            }
        }
        key = null;
        for (StvLine stvLine : stvLines) {
            if (stvLine.getSku() == null) {
                continue;
            }
            key = stvLine.getSku().getId();
            if (stvLineSkuMap.get(key) == null) {
                stvLineSkuMap.put(key, stvLine.getQuantity());
            } else {
                stvLineSkuMap.put(key, stvLineSkuMap.get(key) + stvLine.getQuantity());
            }
        }
        key = null;
        long msgQty = 0L, stvLineQty = 0;
        List<Long> skuIds = new ArrayList<Long>();
        List<Long> greatSkuIds = new ArrayList<Long>();
        for (Map.Entry<Long, Long> entry : msgSkuMap.entrySet()) {
            key = entry.getKey();
            if (!stvLineSkuMap.containsKey(key)) {
                skuIds.add(key);
                continue;
            }
            // 外包仓反馈的sku数量
            msgQty = msgSkuMap.get(key).longValue();
            // 出库的sku数量
            stvLineQty = stvLineSkuMap.get(key).longValue();
            if (stvLineQty >= msgQty) {
                resultMap.put(key, stvLineQty - msgQty);
            } else {
                greatSkuIds.add(key);
            }
        }
        if (!skuIds.isEmpty()) {
            Sku sku = null;
            BusinessException root = new BusinessException(ErrorCode.THREEPL_OUT_BOUND_FAILED_MSG_RETURN_SKU_MATCH);
            for (Long id : skuIds) {
                sku = skuDao.getByPrimaryKey(id);
                BusinessException current = root;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                }
                BusinessException be = new BusinessException(ErrorCode.SKU_NOT_MATCH_OUTBOUND_STVLINE, new Object[] {sku.getBarCode(), sku.getJmCode(), sku.getKeyProperties()});
                current.setLinkedException(be);
            }
            throw root;
        }
        // 反馈sku数量大于出库sku数量
        if (!greatSkuIds.isEmpty()) {
            Sku sku = null;
            BusinessException root = new BusinessException(ErrorCode.THREEPL_OUT_BOUND_FAILED_MSG_RETURN_SKU_MATCH);
            for (Long id : greatSkuIds) {
                sku = skuDao.getByPrimaryKey(id);
                BusinessException current = new BusinessException(ErrorCode.MSG_SKU_QUANTITY_GREATE_OUT_BOUND_SKU_QUANTITY, new Object[] {sku.getCode(), sku.getBarCode()});
                root.setLinkedException(current);
            }
            throw root;
        }
    }

    /***
     * 部分出库-库存调整-释放库存
     * 
     * @param staId
     * @param skuId
     * @param code
     * @param newStvid
     * @param rawQty
     */
    private void releaseOccupyInventory(Long staId, StockTransVoucher stv, Map<Long, Long> stalines, Long skuId, String code, BigDecimal newStvid, Long rawQty) {
        List<InventoryOccupyCommand> inventorys = inventoryDao.findForPartOutBoundInventory(code, skuId, new BeanPropertyRowMapperExt<InventoryOccupyCommand>(InventoryOccupyCommand.class));
        if (inventorys == null || inventorys.isEmpty()) {
            throw new BusinessException("find occpuy inventory is null or empty....");
        }
        long qty = rawQty.longValue();
        Long invQty = null;
        for (InventoryOccupyCommand cmd : inventorys) {
            // 商品是排序的，如果商品不同则说明之前商品已经占用完成
            // 切换商品，初始化待占用数
            if (skuId.equals(cmd.getSkuId())) {
                if (qty <= 0L) {
                    break;
                }
                invQty = cmd.getQuantity() == null ? 0L : cmd.getQuantity();
                if (invQty.longValue() <= 0L) {
                    continue;
                }
                // 库存占用数 > 需要释放的库存数
                if (invQty.longValue() > qty) {
                    // 库存数量大于待占用量拆分库存份 插入invQty-qty数量占用的库存， qty 数量库存释放。
                    inventoryDao.releaseInventoryById(cmd.getId(), qty);
                    inventoryDao.generateNewInventory(cmd.getBatchCode(), skuId, invQty - qty, cmd.getDistrictId(), cmd.getLocationId(), cmd.getOuId(), cmd.getStatusId(), cmd.getOwner(), cmd.getInboundTime(), cmd.getSkuCost(), cmd.getOccupationCode());
                    // 取消的stvline信息
                    generateCancelStvLineByInventory(cmd, stv, stalines, newStvid.longValue(), qty);
                    // 重置待占用量
                    qty = 0L;
                } else {
                    // 库存数量小于等于待占用量,直接占用库存份 invQty 全释放库存
                    inventoryDao.releaseInventoryById(cmd.getId(), null);
                    // 取消的stvline信息
                    generateCancelStvLineByInventory(cmd, stv, stalines, newStvid.longValue(), invQty);
                    qty = qty - invQty;
                }
            }
        }
    }

    /**
     * 取消stvline信息的创建
     * 
     * @param l
     * @param newStvid
     * @param qty
     */
    private void generateCancelStvLineByInventory(InventoryOccupyCommand l, StockTransVoucher stv, Map<Long, Long> stalines, Long newStvid, Long qty) {
        Long id = stalines.get(l.getSkuId());
        StvLine stvl = new StvLine();
        stvl.setBatchCode(l.getBatchCode());
        stvl.setDirection(TransactionDirection.OUTBOUND);
        stvl.setOwner(l.getOwner());
        stvl.setQuantity(qty);
        stvl.setSkuCost(l.getSkuCost());
        stvl.setDistrict(new WarehouseDistrict(l.getDistrictId()));
        stvl.setLocation(new WarehouseLocation(l.getLocationId()));
        stvl.setInvStatus(new InventoryStatus(l.getStatusId()));
        stvl.setWarehouse(new OperationUnit(l.getOuId()));
        stvl.setSku(new Sku(l.getSkuId()));
        if (id != null) stvl.setStaLine(new StaLine(id));
        stvl.setStv(new StockTransVoucher(newStvid));
        stvl.setTransactionType(stv.getTransactionType());
        stvl.setInBoundTime(new Date());
        stvLineDao.save(stvl);
    }

    /**
     * 创建交接清单
     */
    @Override
    public HandOverList createHandOverListDelete(String lpcode, List<Long> packageIds, Long whOuId, Long userId, boolean msg) {
        // 创建交接清单
        HandOverList list = createHandOverList(lpcode, packageIds, whOuId, userId, msg);
        List<Long> packList = new ArrayList<Long>();
        int j = 0;
        for (int i = 0; i < packageIds.size(); i++) {
            packList.add(packageIds.get(i));
            j++;
            if (j == 990) {
                outBoundPackDao.deleteOutBoundPack(null, null, whOuId, userId, packList);// 设置用户出库
                                                                                         // 已交接
                packList.clear();
                j = 0;
            } else {
                if (i == packageIds.size() - 1) {
                    outBoundPackDao.deleteOutBoundPack(null, null, whOuId, userId, packList);// 设置用户出库
                    // 已交接
                    packList.clear();
                    j = 0;
                }
            }

        }

        return list;
    }

    public HandOverList createHandOverList(String lpcode, List<Long> packageIds, Long whOuId, Long userId, boolean msg) {
        User user = null;
        if (userId != null) {
            user = userDao.getByPrimaryKey(userId);
            if (user == null) {
                throw new BusinessException(ErrorCode.USER_NOT_FOUND);
            }
        }
        // 校验是否有创建了未取消的快递单号
        List<String> trackingNos = packageInfoDao.findTrackingNosById(packageIds, new SingleColumnRowMapper<String>(String.class));
        if (trackingNos != null && !trackingNos.isEmpty()) {
            throw new BusinessException(ErrorCode.HANDOVER_LIST_TRACKING_NO_IS_MULTIPLE, new Object[] {trackingNos.toString()});
        }
        // 校验是否有预售包裹
        if (msg) {} else {
            List<String> trackingNos2 = packageInfoDao.findPreSaleTrackingNosById(packageIds, new SingleColumnRowMapper<String>(String.class));
            if (trackingNos2 != null && !trackingNos2.isEmpty()) {
                throw new BusinessException(ErrorCode.HANDOVER_LIST_TRACKING_NO_IS_PRE, new Object[] {trackingNos2.toString()});
            }
        }


        // OperationUnit ou = operationUnitDao.getByPrimaryKey(whOuId);
        // Long ou1 = operationUnitDao.getCompanyIdByWarehouseOuId(whOuId, new
        // SingleColumnRowMapper<Long>(Long.class));
        // OperationUnitCommand ouCmd = new OperationUnitCommand();
        // ouCmd.setId(ou1);
        List<HandOverListLine> holList = new ArrayList<HandOverListLine>();
        HandOverList ho = new HandOverList();
        String code = lpcode + "-" + FormatUtil.formatDate(new Date(), "yyMMddHHmmss");
        ho.setCode(code);
        ho.setCreateTime(new Date());
        ho.setLpcode(lpcode);
        ho.setOperator(user);
        // ho.setOu(ouCmd);
        ho.setLastModifyTime(new Date());
        ho.setStatus(HandOverListStatus.CREATED);
        HandOverList handover = handOverListDao.save(ho);
        // 创建交接清单仓库列表
        // 1:通过快递运单id查询出有多少仓库
        List<Long> wIds = packageInfoDao.findWareHousesById(packageIds, new SingleColumnRowMapper<Long>(Long.class));
        if (wIds.isEmpty()) {// 快递订单没有绑定仓库
            throw new BusinessException(ErrorCode.PACKAGE_INFO_NOT_FOUND, new Object[] {packageIds.toString()});
        }
        // 2:循环保存交接清单仓库
        for (Long id : wIds) {
            WareHandOverList wareover = new WareHandOverList();
            wareover.setHandOverList(handover);
            wareover.setOuId(id);
            wareHandOverListDao.save(wareover);
        }
        // /////////////
        // Set<StockTransApplication> staList = new HashSet<StockTransApplication>();
        BigDecimal totalWeight = new BigDecimal(0);
        Set<Long> pgSet = new HashSet<Long>();
        pgSet.addAll(packageIds);
        for (Long id : pgSet) {
            PackageInfo pg = packageInfoDao.getByPrimaryKey(id);
            if (pg == null) {
                throw new BusinessException(ErrorCode.PACKAGE_INFO_NOT_FOUND);
            }
            if (handOverListLineDao.findByTrackingNo(pg.getTrackingNo()) != null) {
                throw new BusinessException(ErrorCode.PACKAGE_INFO_NOT_FOUND, new Object[] {pg.getTrackingNo()});
            }
            HandOverListLine hol = new HandOverListLine();
            // StockTransApplicationCommand staCmd = new StockTransApplicationCommand();
            // staCmd.setId(pg.getStaDeliveryInfo().getSta().getId());
            // if (StockTransApplicationStatus.INTRANSIT !=
            // pg.getStaDeliveryInfo().getSta().getStatus()) {
            // throw new BusinessException(ErrorCode.OUVR_ORDER_STA_STATUS_NOT_INTRANSIT);
            // }
            // hol.setSta(staCmd);
            if (msg) {
                hol.setIsPreSale(true);
            }
            hol.setStatus(DefaultLifeCycleStatus.CREATED);
            hol.setTrackingNo(pg.getTrackingNo());
            hol.setWeight(pg.getWeight());
            hol.setHoList(handover);
            holList.add(hol);
            handOverListLineDao.save(hol);
            handOverListLineDao.flush();
            // staList.add(pg.getStaDeliveryInfo().getSta());
            totalWeight = totalWeight.add(pg.getWeight());
            // StockTransApplication sta = staDao.getByPrimaryKey(staCmd.getId());
            // sta.setHoList(ho);
            // staDao.save(sta);
        }
        ho.setBillCount(packageIds.size());
        ho.setPackageCount(packageIds.size());
        ho.setTotalWeight(totalWeight);
        ho.setLines(holList);
        handOverListDao.save(ho);
        handOverListDao.flush();
        // 在快递单号设置交接明细id
        // List<HandOverListLine> lines = ho.getLines();
        // for (HandOverListLine handOverListLine : lines) {
        // PackageInfo pg = packageInfoDao.getByPrimaryKey(handOverListLine.get);
        // }
        for (Long id : pgSet) {
            PackageInfo pg = packageInfoDao.getByPrimaryKey(id);
            HandOverListLine line = handOverListLineDao.findByTrackingNo2(pg.getTrackingNo(), new BeanPropertyRowMapperExt<HandOverListLine>(HandOverListLine.class));
            pg.setHandOverLine(line);
            packageInfoDao.save(pg);
        }
        packageInfoDao.flush();
        // ///////////////
        HandOverList handOverList = handOverListDao.findEdwTwhStaHoListId(ho.getId(), new BeanPropertyRowMapperExt<HandOverList>(HandOverList.class));
        List<HandOverListLine> line = handOverListLineDao.findEdwTwhStaHoListLine1(ho.getId(), new BeanPropertyRowMapperExt<HandOverListLine>(HandOverListLine.class));
        handOverList.setLines(line);
        return handOverList;
    }

    /**
     * 物流交接单 保存
     * 
     * @param handOverList
     * @param ouid
     * @return
     */
    public HandOverListCommand saveHandOverListInfo(HandOverList handOverList, Long userId) {
        try {
            HandOverList entry = saveHandOverList(handOverList, userId);
            HandOverListCommand copyEntry = new HandOverListCommand();
            BeanUtils.copyProperties(entry, copyEntry);
            copyEntry.setOperator(null);
            copyEntry.setModifier(null);
            copyEntry.setOu(null);
            copyEntry.setLines(null);
            return copyEntry;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 新建状态交接清单的保存。
     * 
     * @param handOverList
     * @param userId
     * @return
     */
    public HandOverList saveHandOverList(HandOverList handOverList, Long userId) {
        HandOverList ho = handOverListDao.getByPrimaryKey(handOverList.getId());
        User user = userDao.getByPrimaryKey(userId);

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (ho == null) {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
        }

        if (StringUtils.hasText(handOverList.getPartyAOperator())) {
            if (StringUtils.hasText(handOverList.getPartyAOperator())) ho.setPartyAOperator(handOverList.getPartyAOperator());
        } else {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_ENOUGNT_MESSAGE, new Object[] {ho.getId()});
        }
        if (StringUtils.hasText(handOverList.getPartyBOperator())) {
            if (StringUtils.hasText(handOverList.getPartyBOperator())) ho.setPartyBOperator(handOverList.getPartyBOperator());
        } else {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_ENOUGNT_MESSAGE, new Object[] {ho.getId()});
        }
        if (StringUtils.hasText(handOverList.getPaytyAMobile())) {
            if (StringUtils.hasText(handOverList.getPaytyAMobile())) ho.setPaytyAMobile(handOverList.getPaytyAMobile());
        } else {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_ENOUGNT_MESSAGE, new Object[] {ho.getId()});
        }
        if (StringUtils.hasText(handOverList.getPaytyBMobile())) {
            if (StringUtils.hasText(handOverList.getPaytyBMobile())) ho.setPaytyBMobile(handOverList.getPaytyBMobile());
        } else {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_ENOUGNT_MESSAGE, new Object[] {ho.getId()});
        }
        if (StringUtils.hasText(handOverList.getPaytyBPassPort())) {
            if (StringUtils.hasText(handOverList.getPaytyBPassPort())) ho.setPaytyBPassPort(handOverList.getPaytyBPassPort());
        } else {
            throw new BusinessException(ErrorCode.HAND_OVER_NOT_ENOUGNT_MESSAGE, new Object[] {ho.getId()});
        }
        ho.setLastModifyTime(new Date());
        ho.setModifier(user);
        handOverListDao.save(ho);
        return ho;
    }

    /**
     * 验证出库商品是否是暂存区的商品
     * 
     * @param stv
     */
    public void valdateOutBoundLocationIsGI(StockTransVoucher stv) {
        if (TransactionDirection.OUTBOUND.equals(stv.getDirection())) {
            List<StvLineCommand> stvline = stvLineDao.findGILocationByStvId(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            if (stvline != null && stvline.size() > 0) {
                BusinessException error = new BusinessException();
                BusinessException head = error;
                for (StvLineCommand sl : stvline) {
                    BusinessException temp = new BusinessException(ErrorCode.OUT_BOUND_GI_LOCATION, new Object[] {sl.getBarCode(), sl.getQuantity(), sl.getLocationCode()});
                    error.setLinkedException(temp);
                    error = temp;
                }
                throw head;
            }
        }
    }

    /**
     * 创建暂存区商品上架入库单
     * 
     * @param locId
     * @param userId
     * @param ouId
     */
    public void createGIInboundByLoc(Long locId, User user, OperationUnit ou) {
        WarehouseLocation loc = locationDao.getByPrimaryKey(locId);
        if (loc == null) {
            throw new BusinessException(ErrorCode.WAREHOUSELOCATION_IS_NULL);
        }
        String[] ss = loc.getCode().split("_");
        String superStaCode = ss[ss.length - 2];
        StockTransApplication superSta = staDao.findStaByCode(superStaCode);
        String owner = superSta.getOwner();
        StockTransApplication sta = new StockTransApplication();
        sta.setMainWarehouse(ou);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(superStaCode);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), user.getId(), ou.getId());
        sta.setType(StockTransApplicationType.GI_PUT_SHELVES);
        sta.setIsNeedOccupied(false);
        sta.setCreator(user);
        sta.setOwner(owner);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // create stv
        TransactionType transactionType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_GI_PUT_SHELVES_OUTBOUND);
        StockTransVoucher stv = new StockTransVoucher();
        stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        stv.setCreator(user);
        stv.setCreateTime(new Date());
        stv.setDirection(transactionType.getDirection());
        stv.setOwner(owner);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setType(StockTransVoucherType.OUTBOUND_GIOUT);
        stv.setWarehouse(ou);
        stv.setCode(sta.getCode() + "01");
        stv.setTransactionType(transactionType);
        stv.setSta(sta);
        staDao.save(sta);
        stvDao.save(stv);
        staDao.flush();
        // create sta line and stv line
        staLineDao.createByGILocId(loc.getId(), sta.getId());
        stvLineDao.createByGILocId(loc.getId(), stv.getId());
        stvLineDao.flush();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_sta_id", sta.getId());
        SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
        SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
        Map<String, Object> result = staDao.executeSp("sp_occ_inv_for_GI", sqlParameters, params);
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
        }
        staDao.flush();
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    public void executeGIout(StockTransApplication sta, Long inStvId, Long creatorID) throws CloneNotSupportedException {
        if (sta == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_STA);
        }
        if (!StockTransApplicationType.GI_PUT_SHELVES.equals(sta.getType())) {
            throw new BusinessException(ErrorCode.STA_TYPE_ERROR);
        }
        StockTransVoucher outStv = stvDao.findByStaAndTypeAndStatus(sta.getId(), StockTransVoucherStatus.CREATED, StockTransVoucherType.OUTBOUND_GIOUT);
        StockTransVoucher inStv = stvDao.getByPrimaryKey(inStvId);

        // List<StockTransVoucher> listStv = stvDao.findStvCreatedListByStaId(sta.getId());
        // for(StockTransVoucher temp : listStv){
        // if(temp.getDirection().getValue() == 1){
        // inStv = temp;
        // } else {
        // outStv = temp;
        // }
        // }
        // 没找到 占用的STV 或者 要入库的stv
        if (outStv == null || inStv == null) {
            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR);
        }
        // 判断入的是否是出的数量
        List<StvLineCommand> l = stvLineDao.findOutQtyIsInQty(outStv.getId(), inStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        if (l != null && l.size() > 0) {
            // 创建应该出的STV
            String stvCode = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            stvDao.createGIOutStv(outStv.getId(), stvCode, creatorID);
            StockTransVoucher newOutStv = stvDao.findByStvCode(stvCode);
            if (newOutStv == null) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
            List<StvLine> newOutStvLine = new ArrayList<StvLine>();
            // 创建应该出的STVLine
            List<StvLine> outStvLine = outStv.getStvLines();
            List<StvLine> inStvLine = inStv.getStvLines();
            StvLine out = null;
            StvLine in = null;
            // 记录剩余的入库商品
            Map<String, Long> inMap = new HashMap<String, Long>();
            // 做数据统计
            Map<String, Long> isMap = new HashMap<String, Long>();
            Map<String, String> skuMap = new HashMap<String, String>();
            long skuId = 0, statusId = 0;
            String owner = "";
            for (int i = 0; i < inStvLine.size(); i++) {
                in = inStvLine.get(i);
                skuId = in.getSku().getId();
                // 采购入库不设计库存
                // statusId=in.getInvStatus().getId();
                owner = in.getOwner();
                String key = skuId + "_" + statusId + "_" + owner;
                // 作为数据统计
                if (isMap.containsKey(key + "_IN")) {
                    isMap.put(key + "_IN", isMap.get(key + "_IN") + in.getQuantity());
                } else {
                    isMap.put(key + "_IN", in.getQuantity());
                    skuMap.put(key, in.getSku().getBarCode());
                }

                for (int j = 0; j < outStvLine.size(); j++) {
                    out = outStvLine.get(j);
                    if (i == 0) {
                        if (isMap.containsKey(key + "_OUT")) {
                            isMap.put(key + "_OUT", isMap.get(key + "_OUT") + in.getQuantity());
                        } else {
                            isMap.put(key + "_OUT", in.getQuantity());
                        }
                    }
                    if (out.getSku().getId() == skuId
                    // && out.getInvStatus().getId()==statusId
                            && out.getOwner().equals(owner)) {
                        // 创建须要出库的STVLine
                        StvLine tenpStvl = out.clone();
                        tenpStvl.setDistrict(out.getDistrict());
                        tenpStvl.setLocation(out.getLocation());
                        tenpStvl.setStv(newOutStv);
                        if (inMap.get(key) != null) {
                            Long qty = inMap.get(key);
                            if (qty > out.getQuantity()) {
                                inMap.put(key, qty - out.getQuantity());
                                outStvLine.remove(j);
                                tenpStvl.setQuantity(out.getQuantity());
                            } else if (qty < out.getQuantity()) {
                                inMap.remove(key);
                                tenpStvl.setQuantity(qty);
                                out.setQuantity(out.getQuantity() - qty);
                            } else {
                                inMap.remove(key);
                                outStvLine.remove(j);
                                tenpStvl.setQuantity(out.getQuantity());
                            }
                        } else if (in.getQuantity() > out.getQuantity()) {// 当占用的商品不在一个stvLine上面的时候可能会出现情况
                            inMap.put(key, in.getQuantity() - out.getQuantity());
                            outStvLine.remove(j);
                            tenpStvl.setQuantity(out.getQuantity());
                        } else if (in.getQuantity() < out.getQuantity()) {
                            out.setQuantity(out.getQuantity() - in.getQuantity());
                            tenpStvl.setQuantity(in.getQuantity());
                        } else {
                            outStvLine.remove(j);
                            tenpStvl.setQuantity(out.getQuantity());
                        }
                        stvLineDao.save(tenpStvl);
                        newOutStvLine.add(tenpStvl);
                    }
                }
            }
            // 计划量小于执行量
            if (inMap.size() != 0) {
                BusinessException head = new BusinessException();
                BusinessException next = head;
                Iterator<String> it = inMap.keySet().iterator();
                for (; it.hasNext();) {
                    String key = it.next();
                    next.setLinkedException(new BusinessException(ErrorCode.INBOUND_PLAN_NOT_EQ_EXEQTY, new Object[] {skuMap.get(key), isMap.get(key + "_OUT"), isMap.get(key + "_IN")}));
                    next = next.getLinkedException();
                }
                throw head;
            }
            outStv = newOutStv;
        }
        // 出库 移除占用库存
        removeInventory(sta, outStv);
        outStv.setFinishTime(new Date());
        outStv.setOperator(userDao.getByPrimaryKey(creatorID));
        outStv.setLastModifyTime(new Date());
        outStv.setStatus(StockTransVoucherStatus.FINISHED);
        stvDao.save(outStv);
        stvDao.flush();
    }

    public void removeInventory(StockTransApplication sta, StockTransVoucher stv) {
        if (null == sta) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (null == stv) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        List<Inventory> list = inventoryDao.findByOccupiedCode(sta.getCode());
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
        }
        BiChannel shop = companyShopDao.getByCode(stv.getOwner());
        Map<String, BiChannel> shopMap = new HashMap<String, BiChannel>();
        if (shop != null) {
            shopMap.put(shop.getCode(), shop);
        }
        List<StvLine> outLine = stvLineDao.findStvLineListByStvId(stv.getId());
        Map<String, Long> out = new HashMap<String, Long>();
        stv.setFinishTime(new Date());
        long skuId = 0, statusId = 0, locId = 0;
        String owner = "", batchCode = "";
        int n = 0;
        boolean isBreak = false;
        User user = stv.getOperator();
        for (StvLine sl : outLine) {
            skuId = sl.getSku().getId();
            statusId = sl.getInvStatus().getId();
            owner = sl.getOwner();
            locId = sl.getLocation().getId();
            batchCode = sl.getBatchCode();
            n++;
            for (int i = 0; i < list.size(); i++) {
                Inventory inv = list.get(i);
                if (inv.getBatchCode().equals(batchCode) && inv.getSku().getId() == skuId
                // && inv.().getId() == statusId
                        && inv.getOwner().equals(owner) && inv.getLocation().getId() == locId) {
                    // 记录日志
                    StockTransTxLog log = new StockTransTxLog();
                    log.setDirection(stv.getDirection());
                    log.setDistrictId(inv.getDistrict().getId());
                    log.setInvStatusId(inv.getStatus().getId());
                    log.setLocationId(inv.getLocation().getId());
                    log.setOwner(inv.getOwner());
                    log.setSkuId(inv.getSku().getId());
                    log.setTransactionTime(new Date());
                    log.setTransactionType(stv.getTransactionType());
                    log.setWarehouseOuId(inv.getOu().getId());
                    log.setBatchCode(inv.getBatchCode());
                    log.setInboundTime(inv.getInboundTime());
                    log.setProductionDate(inv.getProductionDate());
                    log.setValidDate(inv.getValidDate());
                    log.setExpireDate(inv.getExpireDate());
                    log.setStvId(stv.getId());
                    log.setOcpCode(inv.getOcpCode());
                    Long quantity = 0L;
                    String key = batchCode + "_" + owner + "_" + statusId + "_" + skuId + "_" + n;
                    if (out.containsKey(key)) {
                        Long qty = out.get(key);
                        if (qty > inv.getQuantity()) {
                            out.put(key, qty - inv.getQuantity());
                            quantity = inv.getQuantity();
                            list.remove(i--);
                            inventoryDao.delete(inv);
                            isBreak = false;
                        } else if (qty < inv.getQuantity()) {
                            out.remove(key);
                            quantity = qty;
                            inv.setQuantity(inv.getQuantity() - qty);
                            inventoryDao.save(inv);
                            isBreak = true;
                        } else {
                            quantity = qty;
                            inventoryDao.delete(inv);
                            list.remove(i--);
                            out.remove(key);
                            isBreak = true;
                        }
                    } else if (inv.getQuantity() > sl.getQuantity()) {
                        quantity = sl.getQuantity();
                        inv.setQuantity(inv.getQuantity() - quantity);
                        inventoryDao.save(inv);
                        isBreak = true;
                    } else if (inv.getQuantity().equals(sl.getQuantity())) {
                        quantity = sl.getQuantity();
                        inventoryDao.delete(inv);
                        list.remove(i--);
                        isBreak = true;
                    } else {
                        quantity = inv.getQuantity();
                        list.remove(i--);
                        inventoryDao.delete(inv);
                        out.put(key, sl.getQuantity() - inv.getQuantity());
                        isBreak = false;
                    }
                    log.setQuantity(quantity);
                    log.setStaCode(sta.getCode());
                    log.setSlipCode(sta.getRefSlipCode());
                    log.setSlipCode1(sta.getSlipCode1());
                    log.setSlipCode2(sta.getSlipCode2());
                    if (null != user) {
                        log.setOpUserName(user.getUserName());
                    }
                    /** -------------归档查询优化------------------ */
                    stockTransTxLogDao.save(log);
                    if (isBreak) break;
                }
            }
        }
    }

    public ReadStatus importStaInbound(Long staId, File staFile, User creator) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus()) && !StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
            readStatus = null;
            readStatus = staReaderInbound.readAll(new FileInputStream(staFile), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> list = staImportInboundValidate(readStatus, sta, null, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            if (list.size() < 1) {
                throw new BusinessException(ErrorCode.NIKE_CHECK_IMPORT_QTY_ERROR);
            }
            staInboundConfirm(sta, list, creator, true, false);
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }

    public void inBoundAffirm(Long staId, User user) {
        StockTransVoucher stv = null;
        stvDao.flush();
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<StockTransVoucher> stvList = stvDao.findStvCreatedListByStaId(staId);
        if (stvList != null && stvList.size() > 0) {
            if (stvList.size() != 1) {
                throw new BusinessException(ErrorCode.STV_STRUTS_ERROR);
            }
            for (StockTransVoucher temp : stvList) {
                if (temp.getType() != null && temp.getType().equals(StockTransVoucherType.INBOUND_SHELVES)) {
                    throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_A);
                } else {
                    if (temp.getStatus().equals(StockTransVoucherStatus.CREATED) && temp.getDirection().equals(TransactionDirection.INBOUND) && (temp.getType() == null || temp.getType().equals(StockTransVoucherType.INBOUND_ORDER))) {
                        stv = temp;
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_B);
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CHECK)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_C);
                    }
                }
            }
        }

        if (stv == null) {
            throw new BusinessException(ErrorCode.IN_BOUND_AFFIRM);
        }
        List<StvLineCommand> list = stvLineDao.findInboundError(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        if (list != null && list.size() > 0) {
            throw new BusinessException(ErrorCode.IN_BOUND_AFFIRM);
        }

        stv.setStatus(StockTransVoucherStatus.CONFIRMED);
        stv.setLastModifyTime(new Date());
        stv.setReceiptor(user);
        stv.setOperator(user);
        stvDao.save(stv);
        stvDao.flush();
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CONFIRM_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CONFIRM_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }
        wmsThreePLManager.createCnWmsOrderStatusUploadByStv(stv.getId());
        // 无差异,无需发票且商品基础信息已维护则自动上架审核
        BusinessException skuErrors = new BusinessException();
        BusinessException temp = skuErrors;
        for (StvLine l : stvLineDao.findStvLineListByStvId(stv.getId())) {
            if (!isSkuEssential(l.getSku())) {
                temp.setLinkedException(new BusinessException(ErrorCode.SKU_NOT_ESSENTIAL, new Object[] {l.getSku().getCode()}));
                temp = temp.getLinkedException();
            }
        }
        List<StvLineCommand> stvLines = stvLineDao.findStvLineDifferenceWithPlanInbound(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        boolean isNoDifference = true;
        for (StvLine stvl : stvLines) {
            if (0L != stvl.getDifferenceQty()) {
                isNoDifference = false;
            }
        }
        if (isNoDifference && (null == skuErrors.getLinkedException())) {
            // 自动上架审核
            confirmInBoundSta(stv.getId(), sta.getInvoiceNumber(), sta.getDutyPercentage(), sta.getMiscFeePercentage(), user);
        }
    }

    private void virtualInBoundAffirm(Long staId, User user) {
        StockTransVoucher stv = null;
        stvDao.flush();
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<StockTransVoucher> stvList = stvDao.findStvCreatedListByStaId(staId);
        if (stvList != null && stvList.size() > 0) {
            if (stvList.size() != 1) {
                throw new BusinessException(ErrorCode.STV_STRUTS_ERROR);
            }
            for (StockTransVoucher temp : stvList) {
                if (temp.getType() != null && temp.getType().equals(StockTransVoucherType.INBOUND_SHELVES)) {
                    throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_A);
                } else {
                    if (temp.getStatus().equals(StockTransVoucherStatus.CREATED) && temp.getDirection().equals(TransactionDirection.INBOUND) && (temp.getType() == null || temp.getType().equals(StockTransVoucherType.INBOUND_ORDER))) {
                        stv = temp;
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_B);
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CHECK)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_C);
                    }
                }
            }
        }

        if (stv == null) {
            throw new BusinessException(ErrorCode.IN_BOUND_AFFIRM);
        }
        List<StvLineCommand> list = stvLineDao.findInboundError(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        if (list != null && list.size() > 0) {
            throw new BusinessException(ErrorCode.IN_BOUND_AFFIRM);
        }

        stv.setStatus(StockTransVoucherStatus.CONFIRMED);
        stv.setLastModifyTime(new Date());
        stv.setReceiptor(user);
        stv.setOperator(user);
        stvDao.save(stv);
        stvDao.flush();
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CONFIRM_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CONFIRM_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }
    }

    public void noConinBoundAffirm(Long staId, User user) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (!sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
            return;
        }
        // 判断 是新建
        List<StaLineCommand> staLineList = staLineDao.findStaLineListByStaIdSql2(sta.getId(), false, null, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        List<StvLine> stvLineList = new ArrayList<StvLine>();
        for (StaLineCommand comm : staLineList) {
            StvLine line = new StvLine();
            line.setSku(skuDao.getByPrimaryKey(comm.getSkuId()));
            // 判断状态
            InventoryStatus invs = new InventoryStatus();
            invs.setId(comm.getInvStatusId());
            line.setInvStatus(invs);
            line.setOwner(comm.getOwner());
            line.setReceiptQty(comm.getQuantity());
            line.setSkuCost(comm.getSkuCost());
            line.setStaLine(staLineDao.getByPrimaryKey(comm.getId()));
            stvLineList.add(line);
        }
        StockTransVoucher stv = staInboundConfirm(sta, stvLineList, user, false, false);
        if (stv == null) {
            throw new BusinessException(ErrorCode.PDA_EXECUTE_FAILED);
        }
        List<StvLineCommand> list = stvLineDao.findInboundError(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        if (list != null && list.size() > 0) {
            throw new BusinessException(ErrorCode.PDA_EXECUTE_FAILED);
        }
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CHECK);
        stv.setReceiptor(user);
        stv.setOperator(user);
        stv.setAffirmor(user);
        stvDao.save(stv);
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CHECK_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }
        wmsThreePLManager.createCnWmsOrderStatusUploadByStv(stv.getId());
        stvDao.flush();
        if (sta.getVmiRCStatus() != Boolean.TRUE && sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && StringUtil.isEmpty(sta.getDataSource())) {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vmi != null) {
                vmi.generateReceivingWhenInbound(sta, stv);
            }
        }
    }


    /**
     * 手动收货确认
     * 
     * @param staId
     * @param user
     */
    public Map<String, Object> inBoundAffirmHand(Long staId, List<StvLineCommand> list, List<StvLineCommand> lists, User user, Long ouId, Boolean flag) {
        InventoryStatus inventoryStatus = null;
        // 查询sta(作业申请单)
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus()) && !StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        if (!flag) {
            if (list.size() > 0) {
                inventoryStatus = inventoryStatusDao.getByPrimaryKey(list.get(0).getInvStatusId());
            } else {
                throw new BusinessException("pda收货,list=0");
            }
        }
        Map<Long, List<InventoryStatus>> map = new HashMap<Long, List<InventoryStatus>>();// Map<skuid,List<库存状态>>
        Map<String, Long> mapInvSkuQty = new HashMap<String, Long>();// key:skuid+"_"+invstatusId
        // value:qty 对应商品库存状态 的入库数量
        // 库间移动 查询出库stvline (库存状态)
        List<StvLineCommand> stvLineList = stvLineDao.findOutBoundStvLine(staId, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        for (StvLineCommand sl : stvLineList) {
            Long key = sl.getSkuId();
            InventoryStatus value = inventoryStatusDao.getByPrimaryKey(sl.getIntInvstatus());
            if (map.containsKey(key)) {
                map.get(key).add(value);
            } else {
                List<InventoryStatus> temp = new ArrayList<InventoryStatus>();
                temp.add(value);
                map.put(key, temp);
            }
            String qtyMapKey = key + "_" + value.getId();
            mapInvSkuQty.put(qtyMapKey, (mapInvSkuQty.containsKey(qtyMapKey) ? mapInvSkuQty.get(qtyMapKey) + sl.getQuantity() : sl.getQuantity()));
        }
        // 实现数据拼装 --- importStaInbound
        // 获取库存状态 默认为良品（针对采购入库）
        InventoryStatus status = inventoryStatusDao.findInvStatusForSale(wareHouseManager.findCompanyOUByWarehouseId(sta.getMainWarehouse().getId()).getId());
        // 验证后的操作明细行数据
        List<StvLine> stvLinelist = new ArrayList<StvLine>();
        // 遍历前台数据 并且验证数据是否正确
        for (StvLineCommand com : list) {
            if (com != null) {
                // 获取商品数据
                Sku sku = skuDao.getByPrimaryKey(com.getSkuId());
                // 此次收货当前商品收货量
                Long residueQty = com.getReceiptQty();// 前台输入的收货数量
                if (residueQty == null || residueQty < 1) {
                    throw new BusinessException(ErrorCode.SKU_QTY_IS_ERROR, new Object[] {sku.getBarCode(), residueQty});
                }
                // 获取对应的商品的作业单明细行数据（staLine）
                List<StaLine> staLineList = staLineDao.findByOwnerAndStatus(sta.getId(), sku.getId(), null, null);
                if (staLineList != null && staLineList.size() > 0) {
                    // 针对实际收货量大于预收获量
                    StaLine tempStaLine = null;
                    StvLine tempStvLine = null;
                    for (StaLine sl : staLineList) {
                        tempStaLine = sl;
                        // 当前staLine 行最大执行量（行总收获量-行已执行已执行量）
                        Long staLineQuantity = sl.getQuantity() - (sl.getCompleteQuantity() == null ? 0L : sl.getCompleteQuantity());
                        if (staLineQuantity < 1) {
                            continue;
                        }
                        // 此行收货量(针对作业单明细行对应的操作明细行数)
                        Long receiptQty = residueQty > staLineQuantity ? staLineQuantity : residueQty;
                        // 重新计算 此次收货当前商品收货量
                        residueQty = residueQty - receiptQty;

                        StvLine stvLine = null;
                        String owner = sl.getOwner() == null ? sta.getOwner() : sl.getOwner();
                        // 如果作业单明细行存在库存状态就获取作业单明细行存在库存状态 ，如果不存在默认获取库存状态良品
                        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
                            List<InventoryStatus> invList = map.get(sku.getId());
                            if (invList == null || invList.size() == 0) {
                                // 不等于出库数量
                                throw new BusinessException(ErrorCode.INSKU_NOT_EQ_OUTSKU);
                            }
                            long invStatusQty = receiptQty;
                            for (int i = 0; i < invList.size(); i++) {
                                String key = sku.getId() + "_" + invList.get(i).getId();
                                Long qty = mapInvSkuQty.get(key);
                                if (qty == null || qty < 1) {
                                    continue;
                                }
                                Long tempQty = invStatusQty > qty ? qty : invStatusQty;
                                mapInvSkuQty.put(key, qty - tempQty);
                                stvLine = saveStvLine(invList.get(i), tempQty, owner, sl, sku);
                                stvLinelist.add(stvLine);
                                invStatusQty -= tempQty;
                                if (invStatusQty < 1) {
                                    break;
                                }
                            }
                            if (invStatusQty != 0) {
                                throw new BusinessException(ErrorCode.INSKU_NOT_EQ_OUTSKU);
                            }
                        } else {// run
                            if (flag) {
                                stvLine = saveStvLine(sl.getInvStatus() == null ? status : sl.getInvStatus(), receiptQty, owner, sl, sku);
                                stvLinelist.add(stvLine);
                            } else {// pda收货
                                stvLine = saveStvLine(sl.getInvStatus() == null ? status : inventoryStatus, receiptQty, owner, sl, sku);
                                stvLinelist.add(stvLine);
                            }

                        }
                        // 设置此次收货量
                        tempStvLine = stvLine;
                        if (residueQty < 1) {
                            break;
                        }
                    }

                    // 多余实际收货量处理
                    if (residueQty > 0) {
                        // 保存多余的实际收获量
                        if (tempStvLine == null) {
                            StvLine stvLine = new StvLine();
                            // 如果作业单明细行存在库存状态就获取作业单明细行存在库存状态 ，如果不存在默认获取库存状态良品
                            stvLine.setInvStatus(tempStaLine.getInvStatus() == null ? status : tempStaLine.getInvStatus());
                            // 设置此次收货量
                            stvLine.setReceiptQty(residueQty);
                            stvLine.setOwner(tempStaLine.getOwner() == null ? sta.getOwner() : tempStaLine.getOwner());
                            stvLine.setStaLine(tempStaLine);
                            stvLine.setSku(sku);
                            stvLinelist.add(stvLine);
                        } else {
                            tempStvLine.setReceiptQty(tempStvLine.getReceiptQty() + residueQty);
                        }
                    }
                } else {
                    // 商品{0}，不在计划内
                    throw new BusinessException(ErrorCode.SKU_NOT_PLOT, new Object[] {sku.getBarCode()});
                }
            }
        }
        // 创建收货单
        StockTransVoucher stv = staInboundConfirm(sta, stvLinelist, user, false, false);
        // 确认收货单
        inBoundAffirm(staId, user);
        // 插入卡号到mapping表
        insertSkuSnMapping(staId, lists, user, stv, ouId);
        if (!flag) {// false 不推荐 pda收货 直接是已经审核完状态
            stv.setStatus(StockTransVoucherStatus.CHECK);
        }
        stvDao.flush();
        stvLineDao.flush();

        if (!flag) {// false 走pda收货品牌反馈
            // 添加开关
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("PDA_RE", "PDA_RE");
            if (op != null && op.getOptionValue() != null) {// 开
                if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
                    staDao.flush();
                    BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                    VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                    if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                        vmi.generateReceivingWhenInbound(sta, stv);
                    }
                }
            }
        }
        // 自动化仓添加货箱号
        Warehouse w = warehouseDao.getByOuId(ouId);
        MsgToWcs msg = new MsgToWcs();
        Map<String, Object> m = new HashMap<String, Object>();
        String error = null;
        if (flag) {// true：推荐自动化 false:不推荐
            if (w.getIsAutoWh() != null && w.getIsAutoWh() == true && sta.getContainerCode() != null && !"".equals(sta.getContainerCode())) {
                // 获取弹出口
                m = autoBaseInfoManager.recommandLocationByStv(stv.getId());
                error = (String) m.get("ERROR");

                if (error == null || "".equals(error)) {

                    // sta.setContainerCode(sequenceManager.getAutoInboundBoxCode());

                    @SuppressWarnings("unchecked")
                    List<PopUpArea> popList = (List<PopUpArea>) m.get("DATA");
                    List<SShouRongQi> ssList = new ArrayList<SShouRongQi>();
                    for (PopUpArea pop : popList) {
                        // 封装数据成json格式
                        SShouRongQi ss = new SShouRongQi();
                        ss.setContainerNO(sta.getContainerCode()); // 容器号
                        ss.setDestinationNO(pop.getCode()); // 目的地位置
                        ss.setSerialNumber(pop.getSort().toString());
                        ssList.add(ss);
                    }
                    String context = JsonUtil.collection2jsonStr(ssList);
                    // 保存数据到中间表
                    msg.setContext(context);
                    msg.setContainerCode(sta.getContainerCode());
                    msg.setCreateTime(new Date());
                    msg.setErrorCount(0);
                    msg.setStatus(true);
                    msg.setType(WcsInterfaceType.SShouRongQi);
                    msgToWcsDao.save(msg);
                    msgToWcsDao.flush();
                }
            }
        }
        staDao.save(sta);
        m.put("msgId", msg.getId());
        return m;
    }

    /**
     * 自动化仓收货设置货箱号
     * 
     * @param staId
     * @return
     */
    public String containerCodeSetting(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Warehouse w = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        if (w != null && w.getIsAutoWh() != null && w.getIsAutoWh() == true) {
            if (sta.getContainerCode() == null || "".equals(sta.getContainerCode())) {
                sta.setContainerCode(sequenceManager.getAutoInboundBoxCode());
                staDao.save(sta);
                staDao.flush();

            }
        }
        return sta.getContainerCode();
    }

    /**
     * 星巴克录入卡号
     * 
     */
    private void insertSkuSnMapping(Long staId, List<StvLineCommand> lists, User user, StockTransVoucher stvs, Long ouId) {
        if (lists != null && lists.size() > 0) {
            for (StvLineCommand stv : lists) {
                Sku sku = skuDao.getByPrimaryKey(stv.getSkuId());
                // 商品 snType必须是无条码SKU
                // Object skuSnType = sku.getSnType() == null ? "" : sku.getSnType();
                if (sku.getSnType() != null && SkuSnType.NO_BARCODE_SKU.equals(sku.getSnType())) {
                    Integer skuSnCheckMode = sku.getSnCheckMode().getValue();
                    String startNum = stv.getStartNum(); // 获取前端的起始卡号
                    String stopNum = stv.getStopNum(); // 获取录入的终止卡号
                    boolean alge = true;
                    if (sku != null) {
                        List<SkuSnCheckCfgCommand> cfgList = skuSnCheckCfgDao.getSkuSnCheckCfgBySnCheckMode(sku.getSnCheckMode().getValue(), new BeanPropertyRowMapperExt<SkuSnCheckCfgCommand>(SkuSnCheckCfgCommand.class));
                        if (cfgList.size() > 0) {
                            for (SkuSnCheckCfgCommand cfg : cfgList) {
                                // 如果有mapping 则插入mapping表
                                if (SkuSnCheckCfgType.MAPPING.getValue() == cfg.getTypeInt()) {
                                    alge = false;
                                    if (SkuSnCheckMode.STB_SVCQ.getValue() != skuSnCheckMode) {
                                        if (SkuSnCheckMode.STB_BEN.getValue() == skuSnCheckMode) {// 星巴克本
                                            if(stopNum.equals(startNum)){
                                                SkuSnMapping ma = new SkuSnMapping();
                                                SkuSnMapping maSn = skuSnMappingDao.findMappingSkuSnBySn(startNum, new BeanPropertyRowMapperExt<SkuSnMapping>(SkuSnMapping.class));
                                                if (maSn != null) throw new BusinessException(ErrorCode.SKU_MAPPING_SN_ISNOT_UNIQUEO, new Object[] {maSn.getSn()});
                                                ma.setSn(startNum);
                                                ma.setCreateTime(new Date());
                                                ma.setVersion(0);
                                                ma.setSkuId(stv.getSkuId());
                                                ma.setStaId(staId);
                                                ma.setStvId(stvs.getId());
                                                ma.setOuId(ouId);
                                                skuSnMappingDao.save(ma);
                                            }else{
                                                throw new BusinessException(ErrorCode.SKU_MAPPING_SN_BEN, new Object[] {startNum});
                                            }
                                        } else {
                                            long start = Long.valueOf(startNum);
                                            long stop = Long.valueOf(stopNum);
                                            for (long i = start; i <= stop; i++) {
                                                SkuSnMapping ma = new SkuSnMapping();
                                                SkuSnMapping maSn = skuSnMappingDao.findMappingSkuSnBySn(Long.toString(i), new BeanPropertyRowMapperExt<SkuSnMapping>(SkuSnMapping.class));
                                                if (maSn != null) throw new BusinessException(ErrorCode.SKU_MAPPING_SN_ISNOT_UNIQUEO, new Object[] {maSn.getSn()});
                                                ma.setSn(i + "");
                                                ma.setCreateTime(new Date());
                                                ma.setVersion(0);
                                                ma.setSkuId(stv.getSkuId());
                                                ma.setStaId(staId);
                                                ma.setStvId(stvs.getId());
                                                ma.setOuId(ouId);
                                                skuSnMappingDao.save(ma);
                                            }
                                        }
                                    } else {
                                        // mapping为7 制定操作
                                        String[] start = startNum.split("-");
                                        String star = start[1];
                                        String[] stop = stopNum.split("-");
                                        String sto = stop[1];
                                        long startFormat = Long.valueOf(star);
                                        long stopFormat = Long.valueOf(sto);
                                        for (long i = startFormat; i <= stopFormat; i = i + 20) {
                                            String formatSta = String.format("%07d", i); // 前面补0操作
                                            String subSta = formatSta.substring(4, 7);
                                            long staValue = Long.valueOf(subSta);
                                            String staFormat = String.format("%03d", staValue + 19);
                                            if (staFormat.length() > 3) {
                                                staFormat = staFormat.substring(1);
                                            }
                                            String str = MessageFormat.format(start[0] + "-{0}-{1}", formatSta, staFormat);
                                            SkuSnMapping maSn = skuSnMappingDao.findMappingSkuSnBySn(str, new BeanPropertyRowMapperExt<SkuSnMapping>(SkuSnMapping.class));
                                            if (maSn != null) throw new BusinessException(ErrorCode.SKU_MAPPING_SN_ISNOT_UNIQUEO, new Object[] {maSn.getSn()});
                                            SkuSnMapping ma = new SkuSnMapping();
                                            ma.setSn(str);
                                            ma.setCreateTime(new Date());
                                            ma.setVersion(0);
                                            ma.setSkuId(stv.getSkuId());
                                            ma.setStaId(staId);
                                            ma.setStvId(stvs.getId());
                                            ma.setOuId(ouId);
                                            skuSnMappingDao.save(ma);
                                        }

                                    }
                                }

                            }
                        }
                        // 没有mapping
                        if (alge == true) {
                            long start = Long.valueOf(startNum);
                            long stop = Long.valueOf(stopNum);
                            for (long i = start; i <= stop; i++) {
                                // 插入SN
                                SkuSn skuSn = new SkuSn();
                                skuSn.setSn(Long.toString(i));
                                skuSn.setStatus(SkuSnStatus.USING);
                                skuSn.setVersion(0);
                                OperationUnit unit = operationUnitDao.getByPrimaryKey(ouId);
                                skuSn.setOu(unit);
                                skuSn.setSku(sku);
                                skuSn.setStv(stvs);
                                skuSn.setStaId(staId);
                                skuSn.setLastModifyTime(new Date());
                                skuSn.setCardStatus(SkuSnCardStatus.NONACTIVATED);
                                snDao.save(skuSn);
                                // 插入snOPLog
                                SkuSnOpLog snop = new SkuSnOpLog();
                                snop.setSn(Long.toString(i));
                                snop.setSku(sku);
                                snop.setUser(user);
                                snop.setCardStatus(SkuSnCardStatus.NONACTIVATED);
                                skuSnOpLogDao.save(snop);
                            }
                        }
                    }
                }
            }
        }

    }

    private StvLine saveStvLine(InventoryStatus invStatus, Long qty, String owner, StaLine sl, Sku sku) {
        StvLine stvLine = new StvLine();
        stvLine.setInvStatus(invStatus);
        stvLine.setReceiptQty(qty);
        stvLine.setOwner(owner);
        stvLine.setStaLine(sl);
        stvLine.setSku(sku);
        return stvLine;
    }

    public void cancelInBoundConfirm(Long stvId, User user, Long staId) {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        if (stv == null || !stv.getType().equals(StockTransVoucherType.INBOUND_ORDER) || !stv.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_CONFIRM_STV);
        }
        skuSnMappingDao.deleteSkuSnMappingByStaIdAndStvId(staId, stvId);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CANCELED);
        stv.setOperator(user);
        stvDao.save(stv);
    }

    public void confirmInBoundSta(Long stvId, String invoiceNumber, Double dutyPercentage, Double miscFeePercentage, User user) {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        if (stv == null || !stv.getType().equals(StockTransVoucherType.INBOUND_ORDER) || !stv.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_CONFIRM_STV);
        }
        // 在差异调整确认的时候 判断商品基本信息是否维护过
        BusinessException skuErrors = new BusinessException();
        BusinessException temp = skuErrors;
        for (StvLine l : stvLineDao.findStvLineListByStvId(stvId)) {
            if (!isSkuEssential(l.getSku())) {
                temp.setLinkedException(new BusinessException(ErrorCode.SKU_NOT_ESSENTIAL, new Object[] {l.getSku().getCode()}));
                temp = temp.getLinkedException();
            }
        }
        if (skuErrors.getLinkedException() != null) {
            throw skuErrors;
        }

        // 判断整个核对单里面是否还存在 超出计划量的商品
        // List<StvLineCommand> errorList = stvLineDao.findConfirmDiversityError(stvId, new
        // BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        // if (errorList != null && errorList.size() > 0) {
        // BusinessException errors = new BusinessException();
        // temp = errors;
        // for (StvLineCommand l : errorList) {
        // if (l.getQuantity() == null) {
        // temp.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_IS_NOT_NULL, new Object[]
        // {l.getSkuCode()}));
        // } else if (l.getQuantity() <= 0) {
        // temp.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_IS_ERROR, new Object[]
        // {l.getSkuCode(), l.getQuantity()}));
        // } else {
        // temp.setLinkedException(new BusinessException(ErrorCode.DIFFERENCE_NUMBER_ERROR, new
        // Object[] {l.getSkuCode(), l.getOwner()}));
        // }
        // temp = temp.getLinkedException();
        // }
        // throw errors;
        // }
        // 如果是库间移动或者VMI调拨， 判断最终执行量一定要等于 stv出库单中出库的数量
        StockTransApplication sta = staDao.getByPrimaryKey(stv.getSta().getId());
        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
            List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
            List<StvLineCommand> stvLs = stvLineDao.findOutBoundStvLine(sta.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            Map<String, Long> map = new HashMap<String, Long>();
            for (StvLineCommand l : stvLs) {
                String key = l.getOwner() + "_" + l.getIntInvstatus() + "_" + l.getSkuId();
                if (map.get(key) == null) {
                    map.put(key, l.getQuantity());
                } else {
                    map.put(key, map.get(key) + l.getQuantity());
                }
            }
            for (StvLine sl : stvLines) {
                String key = sl.getOwner() + "_" + sl.getInvStatus().getId() + "_" + sl.getSku().getId();
                if (sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
                    String s = sl.getOwner();
                    Long id = sl.getInvStatus().getId();
                    if (sl.getOwner().equals(sta.getAddiOwner())) {
                        s = sta.getOwner();
                    }
                    if (sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
                        if (sl.getInvStatus().equals(sta.getAddiStatus())) {
                            id = sta.getMainStatus().getId();
                        }
                    }
                    key = s + "_" + id + "_" + sl.getSku().getId();
                }
                if (map.get(key) == null || (map.get(key) != null && !map.get(key).equals(sl.getQuantity()))) {
                    throw new BusinessException(ErrorCode.VMI_FLITTING_TRANSIT_CROSS_ERROR, new Object[] {sta.getCode(), sta.getType()});
                } else {
                    map.remove(key);
                }
            }
            if (map.size() != 0) {
                throw new BusinessException(ErrorCode.VMI_FLITTING_TRANSIT_CROSS_ERROR, new Object[] {sta.getCode(), sta.getType()});
            }
        }
        // 判断是否都没有审核商品
        Long skuQty = stvLineDao.findStvSkuNumByStvId(stvId, new SingleColumnRowMapper<Long>(Long.class));
        if (skuQty < 1) {
            throw new BusinessException(ErrorCode.STA_INBOUND_SKU_NUM_ERROR);
        }
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CHECK);
        /*
         * log.info("Esp t1 po:" + sta.getRefSlipCode() + " set invoiceNumber:" + invoiceNumber +
         * " dutyPercentage:" + dutyPercentage + " miscFeePercentage" + miscFeePercentage);
         * stv.setInvoiceNumber(invoiceNumber); stv.setDutyPercentage(dutyPercentage);
         * stv.setMiscFeePercentage(miscFeePercentage);
         */
        stv.setSkuQty(skuQty);
        stv.setOperator(user);
        stv.setAffirmor(user);
        stvDao.save(stv);
        if (sta.getVmiRCStatus() != Boolean.TRUE && sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && StringUtil.isEmpty(sta.getDataSource())) {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
            if (vmi != null) {
                vmi.generateReceivingWhenInbound(sta, stv);
            }
        }
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CHECK_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CHECK_RECEIVE.getValue(), user.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }
        wmsThreePLManager.createCnWmsOrderStatusUploadByStv(stv.getId());
    }

    /**
     * 推荐库位 1. 系统只需要提示1个知道库位 2. 系统推荐7天内最近的销售相关出库库位 3. 系统推荐拣货区库位存在良品库位 4. 如无满足库位则不推荐库位
     * 
     * @param stvId
     */
    public void locationRecommend(Long stvId, Long whId) {
        List<StvLine> list = stvLineDao.findStvLineListByStvId(stvId);
        for (StvLine sl : list) {
            Long skuId = sl.getSku().getId();
            // 7天内最近的销售相关出库库位
            Long locId = stockTransTxLogDao.getRecommendLocationId(whId, skuId, new SingleColumnRowMapper<Long>(Long.class));
            if (locId == null) {
                // 推荐拣货区库位存在良品库位
                Long locId1 = inventoryDao.getRecommendLocationId(whId, skuId, new SingleColumnRowMapper<Long>(Long.class));
                if (locId1 != null) {
                    WarehouseLocation lc = new WarehouseLocation();
                    lc.setId(locId1);
                    sl.setLocation(lc);
                    stvLineDao.save(sl);
                }
            } else {
                WarehouseLocation lc = new WarehouseLocation();
                lc.setId(locId);
                sl.setLocation(lc);
                stvLineDao.save(sl);
            }
        }
    }

    public ReadStatus importConfirmDiversity(Long stvId, File file, User creator) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
            if (!StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) || !StockTransVoucherStatus.CONFIRMED.equals(stv.getStatus())) {
                throw new BusinessException(ErrorCode.STV_STRUTS_ERROR);
            }
            readStatus = null;
            readStatus = staReaderConfirmDiversity.readAll(new FileInputStream(file), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> list = staImportInboundValidate(readStatus, stv.getSta(), stv, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            confirmDiversity(stv, list, creator);
            stvDao.flush();
            // 判断整个核对单里面是否还存在 超出计划量的商品
            List<StvLineCommand> errorList = stvLineDao.findConfirmDiversityError(stvId, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            if (errorList != null && errorList.size() > 0) {
                List<BusinessException> errors = new ArrayList<BusinessException>();
                for (StvLineCommand l : errorList) {
                    errors.add(new BusinessException(ErrorCode.DIFFERENCE_NUMBER_ERROR, new Object[] {l.getSkuCode(), l.getOwner()}));
                }
                readStatus.getExceptions().addAll(errors);
                return readStatus;
            }
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }


    public ReadStatus importInboundShelves(Long stvId, File file, User creator) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
            readStatus = null;
            readStatus = staReaderInboundShelves.readAll(new FileInputStream(file), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> list = staImportInboundValidate(readStatus, stv.getSta(), stv, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            StockTransApplication sta = stv.getSta();
            boolean isCheckDate = true;
            /*** KJL添加修改 ***************************/
            if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
                invalidateThreeType(stv, list);
                /*** 根据仓库设置，判断是否需要校验有效期 modify by FXL */
                isCheckDate = isCheckDate(stv.getSta());
            }
            /*** KJL添加修改 ***************************/
            StockTransVoucher shelvesStv = staInboundShelves(stv, list, creator, isCheckDate);
            stvDao.flush();
            executeInBoundShelves(shelvesStv, stv, creator, false);
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }

    /**
     * 虚拟仓收货导入 导入后直接完成收货、审核、上架 3个操作
     */
    public ReadStatus importStaInboundShelves(Long staId, File file, User creator, Long ouId) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus()) && !StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
            readStatus = null;
            readStatus = virtualInBoundShelvesRed.readAll(new FileInputStream(file), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> stvLineList = staVirImportForrepairValidate(readStatus, sta, beans, ouId);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            Map<String, StvLine> tempMap = new HashMap<String, StvLine>();
            List<StvLine> stvList = new ArrayList<StvLine>();
            // 合并重复行
            for (int i = 0; i < stvLineList.size(); i++) {
                StvLine l = stvLineList.get(i);
                String key = l.getSku().getId() + "_" + l.getLocation().getCode() + "_" + l.getInvStatus().getId();
                if (tempMap.containsKey(key)) {
                    StvLine temp = tempMap.get(key);
                    temp.setQuantity(temp.getQuantity() + l.getQuantity());
                    Boolean isSn = temp.getSku().getIsSnSku();
                    if (isSn != null && isSn) {
                        temp.setSns(temp.getSns() + "," + l.getSns());
                    }
                } else {
                    stvList.add(l);
                    tempMap.put(key, l);
                }
            }
            // 收货
            StockTransVoucher stv = staInboundConfirm(sta, stvList, creator, true, true);
            // 确认
            virtualInBoundAffirm(sta.getId(), creator);
            // 审核
            confirmInBoundSta(stv.getId(), sta.getInvoiceNumber(), sta.getDutyPercentage(), sta.getMiscFeePercentage(), creator);
            // 上架
            StockTransApplication stas = stv.getSta();
            if (stas.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || stas.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER) || stas.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
                invalidateThreeType(stv, stvList);
            }
            StockTransVoucher shelvesStv = staInboundShelves(stv, stvList, creator, false);
            executeInBoundShelves(shelvesStv, stv, creator, true);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return readStatus;
    }

    // 手动上架
    public void inboundShelvesHand(Long staId, List<StvLineCommand> list, User creator) throws Exception {
        // 查询sta(作业申请单)
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 判断sta状态 必须为：已创建，或者为 部分转入，否则抛出异常
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus())) && !(StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) && !(StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 根据staId 查询作业明细单stv
        StockTransVoucher stv = stvDao.findByStaAndTypeAndStatus(staId, StockTransVoucherStatus.CHECK, StockTransVoucherType.INBOUND_ORDER);
        if (stv == null) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        List<StvLine> lineList = new ArrayList<StvLine>();
        // 已创建/部分完成才能导入
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        // 数量判断
        Map<Long, Long> qtyMap = new HashMap<Long, Long>();

        // 根据作业明细单stv查询作业明细单行stvline
        List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId()); // 入库单 StvLine
        Map<String, List<StvLine>> lineMap = new HashMap<String, List<StvLine>>();
        boolean isAddStatus = true;
        if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            isAddStatus = false;
        }
        for (StvLine l : stvLines) {
            String key = l.getSku().getId() + "_" + (isAddStatus ? l.getInvStatus().getId() : "");
            if (lineMap.containsKey(key)) {
                lineMap.get(key).add(l);
            } else {
                List<StvLine> tempLineList = new ArrayList<StvLine>();
                tempLineList.add(l);
                lineMap.put(key, tempLineList);
            }
        }
        for (StvLineCommand com : list) {
            Sku sku = skuDao.getByPrimaryKey(com.getSkuId());
            if (sku == null) {
                throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
            }
            // 验证库位
            if (com.getLocationCode() == null) {
                throw new BusinessException(ErrorCode.STA_STALINE_LOCATION_NOT_EXIST);
            }
            WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, stv.getWarehouse().getId(), com.getLocationCode(), locationCache);
            if (location == null) {// 库位编码不存在
                throw new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR);
            }
            // 数量 已核对的数量
            if (com.getAddedQty() == null || com.getAddedQty() < 1) {
                throw new BusinessException(ErrorCode.SKU_QTY_IS_ERROR, new Object[] {sku.getBarCode(), com.getAddedQty()});
            }
            // 库存状态验证
            if (com.getIntInvstatus() == null) {
                throw new BusinessException(ErrorCode.INBOUND_PDA_NOT_INV_STATUS);
            }
            InventoryStatus status = inventoryStatusDao.getByPrimaryKey(com.getIntInvstatus());
            if (status == null) {
                throw new BusinessException(ErrorCode.INBOUND_PDA_NOT_INV_STATUS);
            }
            String key = sku.getId() + "_" + (isAddStatus ? status.getId() : "");
            List<StvLine> stvLine = lineMap.get(key);
            // 判断stvLineList是否为空
            if (stvLine == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_CONFIRM_STV);
            }
            com.setSku(sku);
            // 判断是否是保质期商品
            if (sku.getStoremode() != null && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                try {
                    whManagerProxy.setStvLineProductionDateAndExpireDate(com, com.getStrPoductionDate(), com.getStrExpireDate());
                } catch (BusinessException e) {
                    throw e;
                }
            }
            // 此行需要上架的总量
            Long addedQty = com.getAddedQty();
            for (StvLine l : stvLine) {
                // 计算此收货行数据可上架多少数量
                // 总 收获量 - 以前上架量 - 此次上架中别的上架行的上架量
                Long qty = l.getQuantity() - (l.getAddedQty() == null ? 0 : l.getAddedQty()) - (qtyMap.containsKey(l.getId()) ? qtyMap.get(l.getId()) : 0);
                if (qty < 1) {
                    continue;
                }
                StvLine stvLineBean = l.clone();
                stvLineBean.setSns(com.getSns());
                stvLineBean.setInvStatus(status);
                stvLineBean.setLocation(location);
                stvLineBean.setDistrict(location.getDistrict());
                stvLineBean.setProductionDate(com.getProductionDate());
                stvLineBean.setValidDate(com.getValidDate());
                stvLineBean.setExpireDate(com.getExpireDate());
                stvLineBean.setQuantity(qty > addedQty ? addedQty : qty);
                lineList.add(stvLineBean);
                qtyMap.put(l.getId(), qtyMap.containsKey(l.getId()) ? (qtyMap.get(l.getId()) + stvLineBean.getQuantity()) : stvLineBean.getQuantity());
                addedQty -= stvLineBean.getQuantity();
            }
            if (addedQty != 0) {
                throw new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {sta.getCode(), sku.getJmCode()});
            }
        }
        /*** KJL添加修改 ***************************/
        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
            invalidateThreeType(stv, lineList);
        }
        /*** KJL添加修改 ***************************/
        StockTransVoucher shelvesStv = staInboundShelves(stv, lineList, creator, true);
        stvDao.flush();
        executeInBoundShelves(shelvesStv, stv, creator, false);
    }

    /**
     * 新增校验方法
     * 
     * @param stv
     * @param lineList
     */
    private void invalidateThreeType(StockTransVoucher stv, List<StvLine> lineList) {
        Map<String, Long> omap = new HashMap<String, Long>();
        Map<String, Long> rmap = new HashMap<String, Long>();
        List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stv.getId());
        for (StvLine l : stvLines) {
            String okey = l.getSku().getId() + "_" + l.getInvStatus().getId() + "_" + l.getOwner();
            if (omap.containsKey(okey)) {
                omap.put(okey, omap.get(okey) + l.getQuantity());
            } else {
                omap.put(okey, l.getQuantity());
            }
        }
        for (StvLine l : lineList) {
            String rkey = l.getSku().getId() + "_" + l.getInvStatus().getId() + "_" + l.getOwner();
            if (rmap.containsKey(rkey)) {
                rmap.put(rkey, rmap.get(rkey) + l.getQuantity());
            } else {
                rmap.put(rkey, l.getQuantity());
            }
        }
        for (String s : omap.keySet()) {
            if (!rmap.containsKey(s) || !rmap.get(s).equals(omap.get(s))) {
                throw new BusinessException(ErrorCode.STA_NOT_PART_INBOUND);
            }
        }

    }

    /**
     * 合并上架单上架
     * 
     * @param stv
     * @param inboundId
     * @param list
     * @param creator
     */
    public ReadStatus importMergeInboundShelves(String ids, File file, User creator) throws Exception {
        List<Long> stvList = new ArrayList<Long>();
        if (StringUtil.isEmpty(ids)) {
            throw new BusinessException();
        }
        for (String s : ids.split(",")) {
            try {
                Long temp = Long.valueOf(s);
                if (temp > 0) {
                    stvList.add(temp);
                }
            } catch (Exception e) {
                throw new BusinessException();
            }
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = null;
            readStatus = staReaderInboundShelves.readAll(new FileInputStream(file), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            Map<Long, List<StvLine>> map = importMergeInboundValidate(readStatus, stvList, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            for (Long stvId : map.keySet()) {
                StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
                StockTransVoucher shelvesStv = staInboundShelves(stv, map.get(stvId), creator, true);
                stvDao.flush();
                executeInBoundShelves(shelvesStv, stv, creator, false);
            }
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;
    }

    /**
     * 上架单上架
     * 
     * @param stv
     * @param inboundId
     * @param list
     * @param creator
     */
    public void executeInBoundShelves(StockTransVoucher shelvesStv, StockTransVoucher inboundStv, User creator, boolean isCheckDate) {
        // 判断是否是GI入库上架
        if (shelvesStv.getSta() != null && StockTransApplicationType.GI_PUT_SHELVES.equals(shelvesStv.getSta().getType())) {
            try {
                executeGIout(shelvesStv.getSta(), shelvesStv.getId(), shelvesStv.getId());
            } catch (CloneNotSupportedException e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
        }
        log.info("Esp t2 po:" + shelvesStv.getSta().getRefSlipCode() + " set invoiceNumber:" + inboundStv.getInvoiceNumber() + " dutyPercentage:" + inboundStv.getDutyPercentage() + " miscFeePercentage" + inboundStv.getMiscFeePercentage());
        shelvesStv.setInvoiceNumber(inboundStv.getInvoiceNumber());
        shelvesStv.setDutyPercentage(inboundStv.getDutyPercentage());
        shelvesStv.setMiscFeePercentage(inboundStv.getMiscFeePercentage());
        stvDao.flush();
        List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(shelvesStv.getId());
        if (shelvesStv.getSta().getType() == StockTransApplicationType.INBOUND_PURCHASE) {
            for (StvLine each : stvLines) {
                if (each.getSns() == null) {
                    continue;
                }
                String codes[] = each.getSns().split(",");
                for (String code : codes) {
                    SkuSnLog snLog = skuSnLogDao.findSkuSnLogBySn(code);
                    if (snLog != null) {
                        // 已经采购入了的SN号，如果是以采购出库（采购退货出库）的作业类型出库的，则还可再次被采购入库
                        List<Long> staO = staDao.findStaOutboundPurchaseBySkuSn(code, new SingleColumnRowMapper<Long>(Long.class));
                        // 已经采购入了的SN号，如果是以前置单据类型（Slipe_type）为“反向NP调整入库”的采购入库作业类型再次发生入库的，则系统仍可支持入相同的SN号
                        List<Long> staRnp = staDao.findStaRNPAdjustmentInboundBySkuSn(code, new SingleColumnRowMapper<Long>(Long.class));
                        if (staO.size() > 0 || staRnp.size() > 0) {} else {
                            throw new BusinessException(ErrorCode.ERROR_SN_IS_EXIT, new Object[] {code});
                        }
                    }
                }
            }
        }
        whExe.createSN(shelvesStv, stvLines);
        stvDao.flush();
        wareHouseManager.purchaseReceiveStep2(shelvesStv.getId(), stvLineDao.findStvLineListByStvId(shelvesStv.getId()), false, creator, isCheckDate, true);
        stvLineDao.updateAddedQtyByShelves(inboundStv.getId(), shelvesStv.getId());
        stvDao.flush();
        StockTransApplication sta = staDao.getByPrimaryKey(shelvesStv.getSta().getId());
        if (StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            inboundStv.setStatus(StockTransVoucherStatus.FINISHED);
            inboundStv.setFinishTime(new Date());
            inboundStv.setLastModifyTime(new Date());
        } else {
            List<StvLineCommand> line = stvLineDao.findNotFinishedStvLine(inboundStv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            if (line == null || line.size() == 0) {
                inboundStv.setStatus(StockTransVoucherStatus.FINISHED);
                inboundStv.setFinishTime(new Date());
                inboundStv.setLastModifyTime(new Date());
            }
        }
        stvDao.save(inboundStv);
    }


    private void confirmDiversity(StockTransVoucher stv, List<StvLine> stvLineList, User creator) {
        if (!StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) || !StockTransVoucherStatus.CONFIRMED.equals(stv.getStatus())) {
            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR, new Object[] {stv.getCode()});
        }
        stv.setAffirmor(creator);
        stv.setOperator(creator);
        stv = stvDao.save(stv);
        for (StvLine stvLine : stvLineList) {
            if (stvLine == null) {
                continue;
            }
            // 调整差异量
            stvLine.setDifferenceQty(stvLine.getDifferenceQty() + stvLine.getQuantity());
            // 设置最终核对量
            stvLine.setQuantity(stvLine.getDifferenceQty() + stvLine.getRemainPlanQty());
            // 判断调整完后是否超出剩余计划量
            if (stvLine.getQuantity() > stvLine.getRemainPlanQty()) {
                throw new BusinessException(ErrorCode.DIFFERENCE_NUMBER_ERROR, new Object[] {stvLine.getSku().getBarCode(), stvLine.getOwner()});
            }
            stvLineDao.save(stvLine);
        }
        stvDao.save(stv);
    }


    /**
     * 收获入库 单创建
     * 
     * @param sta
     * @param stvLineList
     * @param creator
     * @return
     */
    private StockTransVoucher staInboundConfirm(StockTransApplication sta, List<StvLine> stvLineList, User creator, boolean isUpdateVersion, boolean isVirtual) {
        StockTransVoucher stv = null;
        List<StockTransVoucher> stvList = stvDao.findStvCreatedListByStaId(sta.getId());
        if (stvList != null && stvList.size() > 0) {
            for (StockTransVoucher temp : stvList) {
                if (temp.getType() != null && temp.getType().equals(StockTransVoucherType.INBOUND_SHELVES)) {
                    throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_A);
                } else {
                    if (temp.getStatus().equals(StockTransVoucherStatus.CREATED) && temp.getDirection().equals(TransactionDirection.INBOUND) && (temp.getType() == null || temp.getType().equals(StockTransVoucherType.INBOUND_ORDER))) {
                        stv = temp;
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CONFIRMED)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_B);
                    } else if (temp.getStatus().equals(StockTransVoucherStatus.CHECK)) {
                        throw new BusinessException(ErrorCode.IS_NOT_INBOUND_ORDER_C);
                    }
                }
            }
        }
        if (stv != null) {
            stvLineDao.deleteByStvId(stv.getId());
            stvDao.delete(stv);
            stvDao.flush();
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        Date date = new Date();
        stv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv.setBusinessSeqNo(biSeqNo.longValue());
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        stv.setMode(null);
        stv.setDirection(TransactionDirection.INBOUND);
        if (StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            stv.setOwner(sta.getAddiOwner());
        } else {
            stv.setOwner(sta.getOwner());
        }
        stv.setSta(sta);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setType(StockTransVoucherType.INBOUND_ORDER);
        stv.setCreateTime(date);
        stv.setCreator(creator);
        stv.setOperator(creator);
        stv.setTransactionType(tranType);
        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            stv.setWarehouse(sta.getAddiWarehouse());
        } else {
            stv.setWarehouse(sta.getMainWarehouse());
        }
        stv = stvDao.save(stv);
        stvDao.flush();
        List<StvLine> stvLines = new ArrayList<StvLine>();
        // 保证批次号不变
        List<StvLineCommand> slList = stvLineDao.findBatchCodeByStaId(sta.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
        Long skuQty = 0L;
        // 入库日期应该与出库日期相同 不能生成当前时间
        // 查询出库StvLine的时间(INBOUND_TIME) 并把入库StvLine的入库时间（INBOUND_TIME）设置为相同
        String batchCode = Long.valueOf(new Date().getTime()).toString();
        //
        Date expDate = null;
        if (sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS) || sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)
                || sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.SAMPLE_INBOUND)) {
            for (StvLine stvLine : stvLineList) {
                String keyTemp = stvLine.getSku().getId() + "_";
                for (int i = 0; i < slList.size(); i++) {
                    String key = slList.get(i).getSkuId() + "_";
                    if (keyTemp.equals(key)) {
                        stvLine.setBatchCode(slList.get(i).getBatchCode());
                        stvLine.setInBoundTime(slList.get(i).getInBoundTime());
                    }
                }
                if (StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                    stvLine.setInvStatus(sta.getAddiStatus());
                }
                StaLine staLine = stvLine.getStaLine();
                if (stvLine.getReceiptQty() < 1) {
                    throw new BusinessException(ErrorCode.SKU_QTY_IS_ERROR, new Object[] {stvLine.getSku().getCode(), stvLine.getReceiptQty()});
                }
                // 设置剩余计划量
                Long remainPlanQty = staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity());
                // 设置差异量
                Long differenceQty = stvLine.getReceiptQty() - remainPlanQty;
                // 预算核对量(核对时 可修改)
                Long quantity = stvLine.getReceiptQty() > remainPlanQty ? remainPlanQty : stvLine.getReceiptQty();
                if (isVirtual) {
                    expDate = stvLine.getExpireDate();
                } else {
                    expDate = null;
                }
                StvLine sl =
                        createStvLine(stvLine.getBatchCode(), TransactionDirection.INBOUND, stvLine.getInBoundTime(), stvLine.getInvStatus(), null, stv.getOwner(), remainPlanQty, stvLine.getReceiptQty(), differenceQty, quantity, null, stvLine.getSku(),
                                staLine.getSkuCost(), null, null, null, expDate, staLine, stv);
                stvLines.add(sl);
                skuQty += sl.getReceiptQty();
                stvDao.flush();
            }
        } else {
            Date inBoundTime = new Date();
            for (StvLine stvLine : stvLineList) {
                StaLine staLine = stvLine.getStaLine();
                if (stvLine.getReceiptQty() < 1) {
                    throw new BusinessException(ErrorCode.SKU_QTY_IS_ERROR, new Object[] {stvLine.getSku().getCode(), stvLine.getReceiptQty()});
                }
                // 设置剩余计划量
                Long remainPlanQty = staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity());
                // 设置差异量
                Long differenceQty = stvLine.getReceiptQty() - remainPlanQty;
                // 预算核对量(核对时 可修改)
                Long quantity = stvLine.getReceiptQty() > remainPlanQty ? remainPlanQty : stvLine.getReceiptQty();
                if (isVirtual) {
                    expDate = stvLine.getExpireDate();
                } else {
                    expDate = null;
                }
                StvLine sl =
                        createStvLine(batchCode, TransactionDirection.INBOUND, inBoundTime, stvLine.getInvStatus(), null, stv.getOwner(), remainPlanQty, stvLine.getReceiptQty(), differenceQty, quantity, null, stvLine.getSku(), staLine.getSkuCost(), null,
                                null, null, null, staLine, stv);
                stvLines.add(sl);
                skuQty += sl.getReceiptQty();
                stvDao.flush();
            }
        }

        // 避免新建stv并发，对sta修改。
        StockTransApplication stas = staDao.getByPrimaryKey(sta.getId());
        stas.setLastModifyTime(new Date());
        stv.setSkuQty(skuQty);
        stvDao.save(stv);
        stvDao.flush();
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.RECEIVE.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.RECEIVE.getValue(), creator.getId(), sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        }
        // if (isUpdateVersion) {
        // int v = stvDao.updateStvVersion(stv.getId(), StockTransVoucherStatus.CREATED.getValue());
        // if (v != 1) {
        // throw new BusinessException(ErrorCode.ERROR_SLIPCODE, new Object[] {sta.getCode()});
        // }
        // }

        return stv;
    }

    /**
     * 入库上架 单创建
     * 
     * @param sta
     * @param stvLineList
     * @param creator
     * @return
     */
    private StockTransVoucher staInboundShelves(StockTransVoucher stv, List<StvLine> stvLineList, User creator, boolean isCheckDate) {
        if (stv == null) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        if (!StockTransVoucherStatus.CHECK.equals(stv.getStatus()) || !StockTransVoucherType.INBOUND_ORDER.equals(stv.getType())) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        StockTransApplication sta = stv.getSta();
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        List<StvLineCommand> barCodes = null;
        // 退货入库 或者 库间移动 批次入的必须是出的批次
        if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            barCodes = whExe.getOutBoundBachCode(sta.getType(), null, null, sta.getSlipCode1(), sta.getCode());
        } else if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            barCodes = whExe.getOutBoundBachCode(sta.getType(), sta.getCode(), null, null, null);
        }
        // 查询出库StvLine的时间(INBOUND_TIME) 并把入库StvLine的入库时间（INBOUND_TIME）设置为相同
        Date date = new Date();
        StockTransVoucher shelvesStv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        shelvesStv.setBusinessSeqNo(biSeqNo.longValue());
        shelvesStv.setCreateTime(date);
        shelvesStv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        shelvesStv.setMode(InboundStoreMode.TOGETHER);
        shelvesStv.setDirection(TransactionDirection.INBOUND);
        if (StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            shelvesStv.setOwner(sta.getAddiOwner());
        } else {
            shelvesStv.setOwner(sta.getOwner());
        }
        shelvesStv.setSta(sta);
        shelvesStv.setLastModifyTime(new Date());
        shelvesStv.setStatus(StockTransVoucherStatus.CREATED);
        shelvesStv.setType(StockTransVoucherType.INBOUND_SHELVES);
        shelvesStv.setCreator(creator);
        shelvesStv.setOperator(creator);
        shelvesStv.setTransactionType(tranType);
        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            shelvesStv.setWarehouse(sta.getAddiWarehouse());
        } else {
            shelvesStv.setWarehouse(sta.getMainWarehouse());
        }
        shelvesStv = stvDao.save(shelvesStv);
        List<StvLine> stvLines = new ArrayList<StvLine>();
        String batchCode = Long.valueOf(date.getTime()).toString();
        Long skuQty = 0L;
        for (StvLine stvLine : stvLineList) {
            if (stvLine == null) {
                continue;
            }
            if (stvLine.getBatchCode() == null) {
                stvLine.setBatchCode(batchCode);
            }
            stvLine.setOwner(shelvesStv.getOwner());
            stvLine.setWarehouse(shelvesStv.getWarehouse());
            if (StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                stvLine.setInvStatus(sta.getAddiStatus());
            }
            if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                skuQty += stvLine.getQuantity();
                List<StvLine> temp = createStvLineByDate(shelvesStv, stvLine, barCodes, isCheckDate);
                if (StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                    for (StvLine tl : temp) {
                        tl.setOwner(stv.getOwner());
                        stvLineDao.save(tl);
                    }
                }
                stvLines.addAll(temp);
            } else {
                stvLines.add(createStvLine(stvLine.getBatchCode(), TransactionDirection.INBOUND, stvLine.getInBoundTime(), stvLine.getInvStatus(), stvLine.getLocation(), stvLine.getOwner(), null, null, null, stvLine.getQuantity(), null, stvLine.getSku(),
                        stvLine.getSkuCost(), stvLine.getSns(), stvLine.getProductionDate(), stvLine.getValidDate(), stvLine.getExpireDate(), stvLine.getStaLine(), shelvesStv));
                skuQty += stvLine.getQuantity();
            }
        }
        shelvesStv.setSkuQty(skuQty);
        stvDao.save(shelvesStv);
        return shelvesStv;
    }

    /**
     * 计算入库SKU批次信息 新增 isCheckDate 参数。 true正常校验有效期，false:虚拟仓不需要校验
     */
    public List<StvLine> createStvLineByDate(StockTransVoucher stv, StvLine receiveLine, List<StvLineCommand> unInboundBatchCodes, boolean isCheckDate) {
        Sku sku = skuDao.getByPrimaryKey(receiveLine.getSku().getId());
        List<StvLine> rtnStvlines = new ArrayList<StvLine>();
        if (unInboundBatchCodes == null || unInboundBatchCodes.size() == 0) {
            log.error("when inbound to check sku batchcodes,no un inbound batchcode was found.");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 校验是否需要SN号
        Set<String> sns = null;
        if (receiveLine.getSns() != null && receiveLine.getSns().length() != 0) {
            sns = new HashSet<String>();
            String[] strSns = receiveLine.getSns().split(",");
            if (strSns == null || strSns.length != receiveLine.getQuantity().intValue()) {
                log.error("sn qty is not equals receive qty.");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            for (String tmp : strSns) {
                sns.add(tmp);
            }
            if (sns.size() != receiveLine.getQuantity().intValue()) {
                log.error("exists the same sn in inbound sn data.");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        // 临时修复Big 针对退货入库
        StockTransApplication sta = null;
        if (stv.getSta() != null && stv.getSta().getId() != null) {
            sta = staDao.getByPrimaryKey(stv.getSta().getId());
        }


        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        long toInboundQty = receiveLine.getQuantity().longValue();
        for (int i = 0; i < unInboundBatchCodes.size(); i++) {
            StvLineCommand cmd = unInboundBatchCodes.get(i);
            if (toInboundQty == 0L) {
                // 本次入库计算完成
                break;
            }
            if (!receiveLine.getSku().getId().equals(cmd.getSkuId())) {
                continue;
            }

            boolean isShelfManagement = sku.getStoremode() != null && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode());
            if (isShelfManagement && isCheckDate) {
                // 临时解决方案
                if (sta != null && (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST) || sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_PURCHASE))) {
                    receiveLine.setExpireDate(cmd.getExpireDate());
                }
                // 判断系统中是否是正确数据
                if (cmd.getExpireDate() == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL, new Object[] {sku.getBarCode()});
                }
                // 是否提供生产日期
                if (receiveLine.getExpireDate() == null) {
                    throw new BusinessException(ErrorCode.SKU_EXPIRE_DATE_IS_NULL, new Object[] {sku.getBarCode()});
                }
            }
            // 校验商品有效期，执行量。 新增虚拟仓无需校验有效期。 by FXL
            if ((isCheckDate == false && receiveLine.getExpireDate() != null) || cmd.getSkuId().equals(receiveLine.getSku().getId())
                    && ((isShelfManagement ? formatDate.format(receiveLine.getExpireDate()).equals(formatDate.format(cmd.getExpireDate())) : true))) {
                if (!StringUtils.hasText(cmd.getBatchCode())) {
                    log.error("when inbound to check sku batchcodes,found data no batchcode.");
                    throw new BusinessException(ErrorCode.INBOUND_BATCH_CODE_ERROR, new Object[] {sku.getBarCode()});
                }
                if (cmd.getInBoundTime() == null) {
                    log.error("when inbound to check sku batchcodes,found data no inbound time.");
                    throw new BusinessException(ErrorCode.INBOUND_TIME_IS_NULL);
                }
                long currentQty = 0L;
                if (toInboundQty >= cmd.getQuantity()) {
                    currentQty = cmd.getQuantity();
                    toInboundQty = toInboundQty - cmd.getQuantity();
                    unInboundBatchCodes.remove(i--);
                } else {
                    cmd.setQuantity(cmd.getQuantity() - toInboundQty);
                    currentQty = toInboundQty;
                    toInboundQty = 0L;

                }
                // 计算SN号
                String currentsns = null;
                if (sns != null) {
                    StringBuffer sb = new StringBuffer();
                    long j = 1;
                    Set<String> snsTmp = new HashSet<String>();
                    snsTmp.addAll(sns);
                    for (String str : snsTmp) {
                        if (j > currentQty) {
                            break;
                        }
                        sns.remove(str);
                        sb.append(str).append(",");
                        j++;
                    }
                    currentsns = sb.toString().substring(0, sb.toString().length() - 1);
                }
                // 创建stv line
                Date expDate = null;
                if (!isCheckDate) {
                    expDate = receiveLine.getExpireDate();
                } else {
                    expDate = cmd.getExpireDate();
                }
                rtnStvlines.add(createStvLine(cmd.getBatchCode(), TransactionDirection.INBOUND, cmd.getInBoundTime(), receiveLine.getInvStatus(), receiveLine.getLocation(), cmd.getOwner(), null, null, null, currentQty, null, sku,
                        receiveLine.getSkuCost(), currentsns, cmd.getProductionDate(), cmd.getValidDate(), expDate, receiveLine.getStaLine(), stv));
            }
        }
        if (toInboundQty > 0 && isCheckDate) {
            log.error("when inbound to check sku batchcodes,no new stv line is create.");
            throw new BusinessException(InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode()) ? ErrorCode.INBOUND_BATCH_CODE_ERROR_1 : ErrorCode.INBOUND_BATCH_CODE_ERROR, new Object[] {sku.getBarCode()});
        }
        return rtnStvlines;
    }

    public StvLine createStvLine(String batchCode, TransactionDirection direction, Date inBoundTime, InventoryStatus invStatus, WarehouseLocation location, String owner, Long remainPlanQty, Long receiptQty, Long differenceQty, Long quantity,
            Long addedQty, Sku sku, BigDecimal skuCost, String sns, Date productionDate, Integer validDate, Date expireDate, StaLine staLine, StockTransVoucher stv) {
        StvLine stvLine = new StvLine();
        stvLine.setBatchCode(batchCode);
        stvLine.setOwner(owner);
        stvLine.setRemainPlanQty(remainPlanQty);
        stvLine.setReceiptQty(receiptQty);
        stvLine.setDifferenceQty(differenceQty);
        stvLine.setQuantity(quantity);
        stvLine.setAddedQty(addedQty);
        stvLine.setSkuCost(skuCost);
        stvLine.setDirection(direction);
        stvLine.setTransactionType(stv.getTransactionType());
        stvLine.setSku(sku);
        stvLine.setWarehouse(stv.getWarehouse());
        stvLine.setDistrict(location == null ? null : location.getDistrict());
        stvLine.setLocation(location);
        stvLine.setInvStatus(invStatus);
        stvLine.setInBoundTime(inBoundTime);
        stvLine.setStv(stv);
        stvLine.setStaLine(staLine);
        stvLine.setProductionDate(productionDate);
        if (null != expireDate && null != inBoundTime) {
            Calendar aCalendar = Calendar.getInstance();
            aCalendar.setTime(expireDate);
            int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
            aCalendar.setTime(inBoundTime);
            int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
            stvLine.setValidDate(day1 - day2);
        } else {
            stvLine.setValidDate(validDate);
        }
        stvLine.setExpireDate(expireDate);
        stvLine.setSns(sns);
        stvLineDao.save(stvLine);
        return stvLine;
    }


    @SuppressWarnings("unchecked")
    private Map<Long, List<StvLine>> importMergeInboundValidate(ReadStatus rs, List<Long> stvId, Map<String, Object> beans) throws CloneNotSupportedException {
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLineList");
        if ((stvLines == null || stvLines.isEmpty())) {
            List<StvLine> stvLinesSN = (List<StvLine>) beans.get("stvLineSNList");
            if ((stvLinesSN == null || stvLinesSN.isEmpty())) {
                throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
            }
        }
        List<StockTransVoucher> stvList = stvDao.findByStvIds(stvId);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        OperationUnit whou = stvList.get(0).getWarehouse();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(whou.getId());
        // 合并保存 同商品 同库存状态 同店铺的收货明细行
        Map<String, List<StvLine>> lineMap = new HashMap<String, List<StvLine>>();
        for (StockTransVoucher stv : stvList) {
            for (StvLine line : stv.getStvLines()) {
                String key = line.getSku().getId() + "_" + line.getInvStatus().getName() + "_" + line.getOwner();
                if (lineMap.containsKey(key)) {
                    lineMap.get(key).add(line);
                } else {
                    List<StvLine> temp = new ArrayList<StvLine>();
                    temp.add(line);
                    lineMap.put(key, temp);
                }
            }
        }
        // 返回结果集 key 是对应的stvId , value 是所须要上架的line
        Map<Long, List<StvLine>> result = new HashMap<Long, List<StvLine>>();
        // 对于相同skuId分组
        Map<String, StvLine> stvLineMap = new HashMap<String, StvLine>();
        // 对应的作业单上架的商品数量 主要针对退换货入库
        Map<Long, Map<Long, Long>> staSkuQty = new HashMap<Long, Map<Long, Long>>();
        // 对于同一个入库单 并且同一个StaLine的商品做入库上架统计
        Map<Long, List<StvLine>> stvLineListMap = new HashMap<Long, List<StvLine>>();
        // 商品保存
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        int index = 3;
        Iterator<StvLine> it = stvLines.iterator();
        while (it.hasNext()) {
            StvLine stvLine = it.next();
            index++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it.remove();
                    continue;
                } else {
                    if (stvLine.getQuantity() == null || stvLine.getQuantity() < 1) {
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                        continue;
                    }
                    if (stvLine.getLocation() == null || !StringUtils.hasText(stvLine.getLocation().getCode())) {
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_NOT_EXIST, new Object[] {index}));
                        continue;
                    }
                    // 获取商品
                    Sku sku = skuCache.get(stvLine.getSku().getBarCode());
                    if (sku == null) {
                        sku = skuDao.findSkuByParameter(stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode(), stvLine.getSku().getKeyProperties(), customerId);
                        if (sku == null) {
                            SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(stvLine.getSku().getBarCode(), customerId);
                            if (addSkuCode != null) {
                                sku = addSkuCode.getSku();
                            }
                        }
                        if (sku != null) {
                            skuCache.put(stvLine.getSku().getBarCode(), sku);
                            stvLine.setSku(sku);
                        } else {
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        }
                    }
                    // 判断商品信息
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, whou.getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    String lineKey = sku.getId() + "_" + stvLine.getInvStatus().getName() + "_" + stvLine.getOwner();
                    if (lineMap.containsKey(lineKey)) {
                        StvLine temp = null;
                        List<StvLine> lineList = lineMap.get(lineKey);
                        for (int i = 0; i < lineList.size(); i++) {
                            temp = lineList.get(i);
                            StvLine stvLineBean = temp.clone();
                            stvLineBean.setDistrict(location.getDistrict());
                            stvLineBean.setLocation(location);
                            // 当前明细最大执行量
                            Long maxQty = (temp.getQuantity() - (temp.getAddedQty() == null ? 0 : temp.getAddedQty()));
                            // 当前实际执行量
                            Long realityQty = stvLine.getQuantity() > maxQty ? maxQty : stvLine.getQuantity();
                            // 在同一个入库上架单里面如果同商品同库存状态同店铺同库位 就做合并.
                            String key = temp.getStaLine().getId() + location.getCode();
                            if (stvLineListMap.containsKey(temp.getStaLine().getId())) {
                                Long total = 0L;
                                for (StvLine each : stvLineListMap.get(temp.getStaLine().getId())) {
                                    total += each.getQuantity();
                                }
                                // 重新计算 当前明细最大执行量
                                maxQty = maxQty - total;
                                // 当前实际执行量
                                realityQty = stvLine.getQuantity() > maxQty ? maxQty : stvLine.getQuantity();
                                // 判断是同库位 如果同库位 就做合并
                                if (stvLineMap.containsKey(key)) {
                                    StvLine l = stvLineMap.get(key);
                                    l.setQuantity(l.getQuantity() + realityQty);
                                } else {
                                    stvLineBean.setQuantity(realityQty);
                                    stvLineListMap.get(temp.getStaLine().getId()).add(stvLineBean);
                                    stvLineMap.put(key, stvLineBean);
                                    // 保存返回值
                                    Long resultKey = temp.getStv().getId();
                                    if (result.containsKey(resultKey)) {
                                        result.get(resultKey).add(stvLineBean);
                                    } else {
                                        List<StvLine> value = new ArrayList<StvLine>();
                                        value.add(stvLineBean);
                                        result.put(resultKey, value);
                                    }
                                }
                            } else {
                                // 当前实际执行量
                                stvLineBean.setQuantity(realityQty);
                                List<StvLine> stvLineList = new ArrayList<StvLine>();
                                stvLineList.add(stvLineBean);
                                stvLineListMap.put(temp.getStaLine().getId(), stvLineList);
                                stvLineMap.put(key, stvLineBean);
                                // 保存返回值
                                Long resultKey = temp.getStv().getId();
                                if (result.containsKey(resultKey)) {
                                    result.get(resultKey).add(stvLineBean);
                                } else {
                                    List<StvLine> value = new ArrayList<StvLine>();
                                    value.add(stvLineBean);
                                    result.put(resultKey, value);
                                }
                            }
                            // 记录StaLine上架数量
                            if (temp.getStv().getSta().getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                                Long staLineQty = realityQty;
                                Map<Long, Long> skuQty = staSkuQty.get(temp.getStv().getSta().getId());
                                if (skuQty == null) {
                                    skuQty = new HashMap<Long, Long>();
                                    staSkuQty.put(temp.getStv().getSta().getId(), skuQty);
                                } else if (skuQty.containsKey(temp.getStaLine().getId())) {
                                    staLineQty += skuQty.get(temp.getStaLine().getId());
                                }
                                skuQty.put(temp.getStaLine().getId(), staLineQty);
                            }
                            // 更新未上架的数据量 减掉已经上架的数量
                            stvLine.setQuantity(stvLine.getQuantity() - realityQty);
                            // 判断 当前收货明细行最大上架量 是否 等于实际上架量 如果当前收货明细行实际收货量等于了最大上架量 就可以排除在剩余上架内
                            if (maxQty.equals(realityQty)) {
                                lineList.remove(i--);
                            }
                            if (lineList.size() == 0) {
                                lineMap.remove(lineKey);
                                break;
                            }
                            // 行已上架完成
                            if (stvLine.getQuantity() < 1) break;
                        }
                    } else {
                        errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                        continue;
                    }
                }
            } else {
                it.remove();
            }
        }
        // /***************************** 验证sheet2数据 ***************************************/
        List<StvLine> stvLinesSN = (List<StvLine>) beans.get("stvLineSNList");
        Iterator<StvLine> it2 = stvLinesSN.iterator();
        Map<String, Boolean> snMap = new HashMap<String, Boolean>();
        int index2 = 1;
        // 对于StvLine按skuID进行分组
        while (it2.hasNext()) {
            StvLine stvLine = it2.next();
            // 1行记录1个SN号商品数量1
            stvLine.setQuantity(1L);
            index2++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it2.remove();
                    continue;
                } else {
                    // 商品获取
                    Sku sku = skuCache.get(stvLine.getSku().getBarCode());
                    if (sku == null) {
                        sku = skuDao.findSkuByParameter(stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode(), stvLine.getSku().getKeyProperties(), customerId);
                        if (sku == null) {
                            SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(stvLine.getSku().getBarCode(), customerId);
                            if (addSkuCode != null) {
                                sku = addSkuCode.getSku();
                            }
                        }
                        if (sku != null) {
                            skuCache.put(stvLine.getSku().getBarCode(), sku);
                            stvLine.setSku(sku);
                        } else {
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        }
                    }
                    // 判断商品是否SN商品
                    if (sku.getIsSnSku() == null || !sku.getIsSnSku()) {
                        errors.add(new BusinessException(ErrorCode.IMPUT_SNSKU_IS_NOT_SNSKU, new Object[] {2, index2, sku.getCode()}));
                        continue;
                    }
                    // 判断库位是否
                    if (stvLine.getLocation() == null || !StringUtils.hasText(stvLine.getLocation().getCode())) {
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_NOT_EXIST, new Object[] {index}));
                        continue;
                    }
                    // 判断是否存在SN
                    if (stvLine.getSns() == null) {
                        errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getCode()}));
                        continue;
                    }
                    // 判断SN是否有重复
                    if (snMap.containsKey(stvLine.getSns())) {
                        errors.add(new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {2, stvLine.getSns()}));
                        continue;
                    }
                    snMap.put(stvLine.getSns(), true);
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, whou.getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    String lineKey = sku.getId() + "_" + stvLine.getInvStatus().getName() + "_" + stvLine.getOwner();
                    if (lineMap.containsKey(lineKey)) {
                        List<StvLine> lineList = lineMap.get(lineKey);
                        if (lineList.size() == 0) {
                            errors.add(new BusinessException(ErrorCode.IS_INBOUND_SHELVES_QTY_ERROR, new Object[] {sku.getBarCode()}));
                            continue;
                        }
                        StvLine temp = lineList.get(0);
                        if (temp != null) {
                            StvLine stvLineBean = temp.clone();
                            stvLineBean.setDistrict(location.getDistrict());
                            stvLineBean.setLocation(location);
                            stvLineBean.setQuantity(1L);
                            String key = temp.getStaLine().getId() + location.getCode();
                            if (stvLineListMap.containsKey(temp.getStaLine().getId())) {
                                Long total = 0L;
                                for (StvLine each : stvLineListMap.get(temp.getStaLine().getId())) {
                                    total += each.getQuantity();
                                }
                                // 判断是同库位 如果同库位 就做合并
                                if (stvLineMap.containsKey(key)) {
                                    stvLineBean = stvLineMap.get(key);
                                    stvLineBean.setQuantity(stvLineBean.getQuantity() + 1);
                                    stvLineBean.setSns(stvLineBean.getSns() + "," + stvLine.getSns());
                                } else {
                                    // 同商品同库存状态同店铺同库位
                                    stvLineMap.put(key, stvLineBean);
                                    // 保存同
                                    stvLineListMap.get(temp.getStaLine().getId()).add(stvLineBean);
                                    // 保存返回值
                                    Long resultKey = temp.getStv().getId();
                                    if (result.containsKey(resultKey)) {
                                        result.get(resultKey).add(stvLineBean);
                                    } else {
                                        List<StvLine> value = new ArrayList<StvLine>();
                                        value.add(stvLineBean);
                                        result.put(resultKey, value);
                                    }
                                }
                            } else {
                                List<StvLine> stvLineList = new ArrayList<StvLine>();
                                stvLineList.add(stvLineBean);
                                stvLineListMap.put(temp.getStaLine().getId(), stvLineList);
                                // 同商品同库存状态同店铺同库位
                                stvLineMap.put(key, stvLineBean);
                                // 保存返回值
                                Long resultKey = temp.getStv().getId();
                                if (result.containsKey(resultKey)) {
                                    result.get(resultKey).add(stvLineBean);
                                } else {
                                    List<StvLine> value = new ArrayList<StvLine>();
                                    value.add(stvLineBean);
                                    result.put(resultKey, value);
                                }
                            }
                            // 记录StaLine上架数量
                            if (temp.getStv().getSta().getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                                Long staLineQty = 1L;
                                Map<Long, Long> skuQty = staSkuQty.get(temp.getStv().getSta().getId());
                                if (skuQty == null) {
                                    skuQty = new HashMap<Long, Long>();
                                    staSkuQty.put(temp.getStv().getSta().getId(), skuQty);
                                } else if (skuQty.containsKey(temp.getStaLine().getId())) {
                                    staLineQty += skuQty.get(temp.getStaLine().getId());
                                }
                                skuQty.put(temp.getStaLine().getId(), staLineQty);
                            }
                            Long maxQty = (temp.getQuantity() - (temp.getAddedQty() == null ? 0 : temp.getAddedQty()));
                            if (maxQty.equals(stvLineBean.getQuantity())) {
                                lineList.remove(0);
                            }
                        } else {
                            errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                            continue;
                        }
                    } else {
                        errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                        continue;
                    }
                }
            } else {
                it2.remove();
            }
        }
        // 退换货默认完成 所以须要判断入库数量是否等于计划量
        if (staSkuQty.size() > 0) {
            for (Long key : staSkuQty.keySet()) {
                stvQtyIsStaQty(errors, key, staSkuQty.get(key));
            }
        }
        if (errors != null && !errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private List<StvLine> staImportInboundValidate(ReadStatus rs, StockTransApplication sta, StockTransVoucher stv, Map<String, Object> beans) throws CloneNotSupportedException {
        List<BusinessException> errors = new ArrayList<BusinessException>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
        // 已创建/部分完成才能导入
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLineList");
        if ((stvLines == null || stvLines.isEmpty())) {
            List<StvLineCommand> stvLinesSN = (List<StvLineCommand>) beans.get("stvLineSNList");
            if ((stvLinesSN == null || stvLinesSN.isEmpty())) {
                throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
            }
        }
        List<StvLine> result = new ArrayList<StvLine>();
        // kjl---
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        Map<String, InventoryStatus> invStatus = null;
        if (stv == null || (StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) && StockTransVoucherStatus.CREATED.equals(stv.getStatus()))) {
            invStatus = new HashMap<String, InventoryStatus>();
            InventoryStatus lp = inventoryStatusDao.findInvStatusForSale(wareHouseManager.findCompanyOUByWarehouseId(sta.getMainWarehouse().getId()).getId());
            invStatus.put("MR", lp);
            invStatus.put(lp.getName(), lp);
        }
        Iterator<StvLineCommand> it = stvLines.iterator();
        // 对于相同skuId分组
        Map<String, StvLine> stvLineMap = new HashMap<String, StvLine>();
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        // 数量判断
        Map<Long, Long> qtyMap = new HashMap<Long, Long>();
        // 对于保质期商品的生产日期 转换
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        int index = 2;
        // 对于StvLine按skuID进行分组
        // 保存对应的StaLine 执行过的数量
        while (it.hasNext()) {
            StvLineCommand stvLine = it.next();
            index++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it.remove();
                    continue;
                } else {
                    // 获取商品
                    Sku sku = skuCache.get(stvLine.getSku().getBarCode());
                    if (sku == null) {
                        sku = skuDao.findSkuByParameter(stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode(), stvLine.getSku().getKeyProperties(), customerId);
                        if (sku == null) {
                            SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(stvLine.getSku().getBarCode(), customerId);
                            if (addSkuCode != null) {
                                sku = addSkuCode.getSku();
                            }
                        }
                        if (sku != null) {
                            skuCache.put(stvLine.getSku().getBarCode(), sku);
                            stvLine.setSku(sku);
                        } else {
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        }
                    }
                    // 店铺 不能为空
                    if (!StringUtils.hasText(stvLine.getOwner())) {
                        errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                        continue;
                    }
                    BiChannel bc = biChannelDao.getByName(stvLine.getOwner());
                    if (bc == null) {
                        errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                        continue;
                    }
                    stvLine.setOwner(bc.getCode());
                    // 判断是否是入库差异调整确认
                    if (stv != null && StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) && StockTransVoucherStatus.CONFIRMED.equals(stv.getStatus())) {
                        if (stvLine.getQuantity() == null) {
                            continue;
                        }
                        StvLine temp = stvLineDao.findConfirmDiversityStvLine(stv.getId(), sku.getId(), stvLine.getInvStatus().getName(), stvLine.getOwner());
                        if (temp == null || temp.getDifferenceQty() == null || temp.getDifferenceQty() == 0) {
                            errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                            continue;
                        }
                        if (stvLineMap.containsKey(temp.getId().toString())) {
                            temp = stvLineMap.get(temp.getId().toString());
                            temp.setQuantity(stvLine.getQuantity() + temp.getQuantity());
                        } else {
                            temp.setQuantity(stvLine.getQuantity());
                            stvLineMap.put(temp.getId().toString(), temp);
                            result.add(temp);
                        }
                        // 入库上架导入
                    } else if (stv != null && StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) && StockTransVoucherStatus.CHECK.equals(stv.getStatus())) {
                        if (stvLine.getLocation() == null || !StringUtils.hasText(stvLine.getLocation().getCode())) {
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_NOT_EXIST, new Object[] {index}));
                            continue;
                        }
                        if (stvLine.getQuantity() == null || stvLine.getQuantity() < 1) {
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                            continue;
                        }
                        StvLine temp = stvLineDao.findConfirmDiversityStvLine(stv.getId(), sku.getId(), stvLine.getInvStatus().getName(), stvLine.getOwner());
                        if (temp == null) {
                            errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                            continue;
                        }
                        StvLine stvLineBean = temp.clone();
                        WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, stv.getWarehouse().getId(), stvLine.getLocation().getCode().trim(), locationCache);
                        if (location == null) {// 库位编码不存在
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        stvLineBean.setQuantity(stvLine.getQuantity());

                        Long stalineIdQty = qtyMap.get(temp.getStaLine().getId()) == null ? 0 : qtyMap.get(temp.getStaLine().getId());
                        if ((stalineIdQty + stvLineBean.getQuantity()) > (temp.getQuantity() - (temp.getAddedQty() == null ? 0 : temp.getAddedQty()))) {
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {sta.getCode(), sku.getJmCode()}));
                            continue;
                        }

                        // 判断是否是保质期商品
                        if (sku.getStoremode() != null && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                            try {
                                whManagerProxy.setStvLineProductionDateAndExpireDate(stvLineBean, stvLine.getStrPoductionDate(), stvLine.getStrExpireDate());
                            } catch (BusinessException e) {
                                errors.add(e);
                                continue;
                            }
                        }
                        stvLineBean.setDistrict(location.getDistrict());
                        stvLineBean.setLocation(location);
                        // 不同生产日期的商品是不润许在同一库位
                        String key = temp.getStaLine().getId() + location.getCode() + (stvLineBean.getExpireDate() == null ? "" : formatDate.format(stvLineBean.getExpireDate()));
                        // 判断是同库位如果同库就做合并
                        if (stvLineMap.containsKey(key)) {
                            StvLine l = stvLineMap.get(key);
                            l.setQuantity(l.getQuantity() + stvLineBean.getQuantity());
                        } else {
                            stvLineMap.put(key, stvLineBean);
                            result.add(stvLineBean);
                        }
                        qtyMap.put(temp.getStaLine().getId(), stalineIdQty + stvLineBean.getQuantity());
                        // 收货入库导入
                    } else if (stv == null || (StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) && StockTransVoucherStatus.CREATED.equals(stv.getStatus()))) {
                        if (stvLine.getReceiptQty() == null || stvLine.getReceiptQty() < 1) {
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                            continue;
                        }
                        String statusName = (stvLine.getInvStatus() == null ? null : stvLine.getInvStatus().getName());
                        InventoryStatus status = invStatus.get(statusName);
                        // 库存状态
                        if (status == null) {
                            status = (StringUtils.hasText(statusName) ? inventoryStatusDao.findByNameUnionSystem(statusName, wareHouseManager.findCompanyOUByWarehouseId(sta.getMainWarehouse().getId()).getId()) : null);
                            if (status != null) {
                                invStatus.put(status.getName(), status);
                            } else {
                                status = invStatus.get("MR");
                            }
                        }
                        stvLine.setInvStatus(status);
                        // }
                        // 剩余量
                        Long residueQty = stvLine.getReceiptQty();
                        // 获取对应的 staLine
                        List<StaLineCommand> staLineList =
                                staLineDao.findByOwnerAndStatusSql(sta.getId(), sku.getId(), StringUtils.hasText(stvLine.getOwner()) ? stvLine.getOwner() : null, (statusName == null ? null : status.getId()), new BeanPropertyRowMapper<StaLineCommand>(
                                        StaLineCommand.class));
                        // 记录StaLine 可能存在重复行的数据
                        StvLine residueQtyLine = null;
                        if (staLineList != null && staLineList.size() > 0) {
                            for (StaLine sl : staLineList) {
                                Long qty = qtyMap.get(sl.getId());
                                // 此行收货量
                                Long receiptQty = 0L;
                                // 当前staLine 预计执行量
                                Long staLineQuantity = sl.getQuantity() - (sl.getCompleteQuantity() == null ? 0L : sl.getCompleteQuantity());
                                if (qty == null) {
                                    receiptQty = staLineQuantity > residueQty ? residueQty : staLineQuantity;
                                } else if (qty < sl.getQuantity() - (sl.getCompleteQuantity() == null ? 0L : sl.getCompleteQuantity())) {
                                    receiptQty = (staLineQuantity - qty) > residueQty ? residueQty : (staLineQuantity - qty);
                                } else {
                                    residueQtyLine = stvLineMap.get(sl.getId().toString());
                                    continue;
                                }
                                // 计算出剩余量
                                residueQty = residueQty - receiptQty;
                                // 设置此次收货量
                                stvLine.setReceiptQty(receiptQty);
                                stvLine.setStaLine(sl);
                                stvLine.setSku(sku);
                                // 合并重复
                                if (stvLineMap.containsKey(sl.getId().toString())) {
                                    StvLine temp = stvLineMap.get(sl.getId().toString());
                                    temp.setReceiptQty(stvLine.getReceiptQty() + temp.getReceiptQty());
                                    residueQtyLine = temp;
                                    qtyMap.put(sl.getId(), qtyMap.get(sl.getId()) + stvLine.getReceiptQty());
                                } else {
                                    result.add(stvLine);
                                    qtyMap.put(sl.getId(), stvLine.getReceiptQty());
                                    residueQtyLine = stvLine;
                                    stvLineMap.put(sl.getId().toString(), stvLine);
                                }
                                if (residueQty.equals(0L)) {
                                    break;
                                }
                            }
                            if (residueQty > 0) {
                                if (residueQtyLine != null) {
                                    residueQtyLine.setReceiptQty(residueQtyLine.getReceiptQty() + residueQty);
                                } else {
                                    errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                                    continue;
                                }
                            }
                        } else {
                            // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        }
                    }
                }
            } else {
                it.remove();
            }
        }

        if (stv != null && StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) && StockTransVoucherStatus.CHECK.equals(stv.getStatus())) {
            /***************************** 验证sheet2数据 ***************************************/
            List<StvLineCommand> stvLinesSN = (List<StvLineCommand>) beans.get("stvLineSNList");
            Iterator<StvLineCommand> it2 = stvLinesSN.iterator();
            Map<String, Boolean> snMap = new HashMap<String, Boolean>();
            int index2 = 1;
            // 对于StvLine按skuID进行分组
            while (it2.hasNext()) {
                StvLineCommand stvLine = it2.next();
                // 1行记录1个SN号商品数量1
                stvLine.setQuantity(1L);
                index2++;
                if (stvLine != null && stvLine.getSku() != null) {
                    if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                        it2.remove();
                        continue;
                    } else {
                        Sku sku = skuCache.get(stvLine.getSku().getBarCode());
                        if (sku == null) {
                            sku = skuDao.findSkuByParameter(stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode(), stvLine.getSku().getKeyProperties(), customerId);
                            if (sku == null) {
                                SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(stvLine.getSku().getBarCode(), customerId);
                                if (addSkuCode != null) {
                                    sku = addSkuCode.getSku();
                                }
                            }
                            if (sku != null) {
                                skuCache.put(stvLine.getSku().getBarCode(), sku);
                                stvLine.setSku(sku);
                            } else {
                                errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                                continue;
                            }
                        }
                        // 判断商品是否SN商品
                        if (sku.getIsSnSku() == null || !sku.getIsSnSku()) {
                            errors.add(new BusinessException(ErrorCode.IMPUT_SNSKU_IS_NOT_SNSKU, new Object[] {2, index2, sku.getCode()}));
                            continue;
                        }
                        // 判断库位是否
                        if (stvLine.getLocation() == null || !StringUtils.hasText(stvLine.getLocation().getCode())) {
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_NOT_EXIST, new Object[] {index}));
                            continue;
                        }
                        if (stvLine.getSns() == null) {
                            errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getCode()}));
                            continue;
                        }
                        // 判断SN是否有重复
                        if (snMap.containsKey(stvLine.getSns())) {
                            errors.add(new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {2, stvLine.getSns()}));
                            continue;
                        }
                        // 店铺 不能为空
                        if (!StringUtils.hasText(stvLine.getOwner())) {
                            errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                            continue;
                        }
                        BiChannel bc = biChannelDao.getByName(stvLine.getOwner());
                        if (bc == null) {
                            errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                            continue;
                        }
                        snMap.put(stvLine.getSns(), true);
                        StvLine temp = stvLineDao.findConfirmDiversityStvLine(stv.getId(), sku.getId(), stvLine.getInvStatus().getName(), bc.getCode());
                        if (temp == null || temp.getQuantity() == null || temp.getQuantity() == 0) {
                            errors.add(new BusinessException(ErrorCode.SKU_IS_NOT_DIFFERENCE, new Object[] {sku.getBarCode()}));
                            continue;
                        }
                        WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, stv.getWarehouse().getId(), stvLine.getLocation().getCode().trim(), locationCache);
                        if (location == null) {// 库位编码不存在
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                            continue;
                        }

                        StvLine stvLineBean = temp.clone();
                        stvLineBean.setQuantity(1L);

                        Long stalineIdQty = qtyMap.get(temp.getStaLine().getId()) == null ? 0 : qtyMap.get(temp.getStaLine().getId());
                        if ((stalineIdQty + stvLineBean.getQuantity()) > (temp.getQuantity() - (temp.getAddedQty() == null ? 0 : temp.getAddedQty()))) {
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {sta.getCode(), sku.getJmCode()}));
                            continue;
                        }

                        // 判断是否是保质期商品
                        if (sku.getStoremode() != null && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                            try {
                                whManagerProxy.setStvLineProductionDateAndExpireDate(stvLineBean, stvLine.getStrPoductionDate(), stvLine.getStrExpireDate());
                            } catch (BusinessException e) {
                                errors.add(e);
                                continue;
                            }
                        }

                        stvLineBean.setSns(stvLine.getSns());
                        stvLineBean.setDistrict(location.getDistrict());
                        stvLineBean.setLocation(location);
                        String key = temp.getStaLine().getId() + location.getCode() + (stvLineBean.getExpireDate() == null ? "" : formatDate.format(stvLineBean.getExpireDate()));
                        // 判断是同库位如果同库就做合并
                        if (stvLineMap.containsKey(key)) {
                            StvLine l = stvLineMap.get(key);
                            l.setQuantity(l.getQuantity() + stvLineBean.getQuantity());
                            l.setSns(l.getSns() + "," + stvLine.getSns());
                        } else {
                            stvLineMap.put(key, stvLineBean);
                            result.add(stvLineBean);
                        }
                        qtyMap.put(temp.getStaLine().getId(), stalineIdQty + stvLineBean.getQuantity());
                    }
                } else {
                    it2.remove();
                }
            }
        }
        // 判断SN商品是否存在
        if (stv != null && stv.getStatus() != null && stv.getStatus().equals(StockTransVoucherStatus.CHECK)) {
            for (StvLine sl : result) {
                Boolean b = sl.getSku().getIsSnSku();
                SkuSnType s = sl.getSku().getSnType();
                if (b != null && b && (s == null || s == SkuSnType.GENERAL)) {
                    if (sl.getSns() != null) {
                        long qty = 0;

                        int no = sl.getSns().indexOf(',');
                        if (no > 0) {
                            qty = sl.getSns().split(",").length;
                        } else {
                            qty = 1;
                        }
                        if (sl.getQuantity() != qty) {
                            errors.add(new BusinessException(ErrorCode.SNS_ACTUAL_ERROR, new Object[] {sl.getSku().getCode(), sl.getSku().getBarCode(), sl.getQuantity(), qty}));
                        }
                    } else {
                        errors.add(new BusinessException(ErrorCode.SNS_ACTUAL_ERROR, new Object[] {sl.getSku().getCode(), sl.getSku().getBarCode(), sl.getQuantity(), 0}));
                    }
                }
            }
        }

        // 退换货默认完成 所以须要判断入库数量是否等于计划量
        if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            stvQtyIsStaQty(errors, sta.getId(), qtyMap);
        }
        if (errors != null && !errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }

    /**
     * 判断实际入库量是否等于计划量
     * 
     * @param errors
     * @param staId
     * @param qtyMap
     */
    public void stvQtyIsStaQty(List<BusinessException> errors, Long staId, Map<Long, Long> qtyMap) {
        List<StaLine> staLineList = staLineDao.findByStaId(staId);
        for (StaLine line : staLineList) {
            Long qty = qtyMap.get(line.getId());
            if (qty == null || !line.getQuantity().equals(qty)) {
                errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {line.getSta().getCode(), line.getSku().getJmCode()}));
            }
        }
    }


    public void inboundShelvesGI(Long stvId, User user) throws CloneNotSupportedException {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        if (stv == null) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        if (!StockTransVoucherStatus.CHECK.equals(stv.getStatus()) || !StockTransVoucherType.INBOUND_ORDER.equals(stv.getType())) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        if (StockTransApplicationType.GI_PUT_SHELVES.equals(stv.getSta().getType())) {
            throw new BusinessException(ErrorCode.IN_BOUND_SHELVES);
        }
        // 创建上架单
        StockTransVoucher shelvesStv = createGIStv(stv.getId(), user);
        stvDao.flush();
        // 执行上架单
        executeInBoundShelves(shelvesStv, stv, user, false);
    }

    /**
     * 判断商品基本信息是否维护过
     * 
     * @param sku
     * @return
     */
    public boolean isSkuEssential(Sku sku) {
        if (sku != null && sku.getWidth() != null && sku.getHeight() != null && sku.getLength() != null) return true;
        return false;
    }


    /**
     * 无差异明细执行
     * 
     * @param staCode
     * @param creator
     */
    public void executeNotDifPDALine(String staCode, User creator) {

        StockTransApplication sta = staDao.findStaByCode(staCode);
        if (sta == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_STA);
        }

        StockTransVoucher stv = stvDao.findByStaAndTypeAndStatus(sta.getId(), StockTransVoucherStatus.CHECK, StockTransVoucherType.INBOUND_ORDER);
        if (stv == null) {
            throw new BusinessException(ErrorCode.PDA_NOT_FIND_STV);
        }

        List<Integer> pdaTypeList = new ArrayList<Integer>();
        pdaTypeList.add(DefaultStatus.CREATED.getValue());
        pdaTypeList.add(DefaultStatus.CANCELED.getValue());
        pdaTypeList.add(DefaultStatus.EXECUTING.getValue());

        // 查询PDA上架未处理的数据
        List<PdaOrderLine> pdaLineList = pdaLineDao.findShelevesPdaLine(sta.getCode(), stv.getId(), new BeanPropertyRowMapperExt<PdaOrderLine>(PdaOrderLine.class));

        if (pdaLineList == null || pdaLineList.size() == 0) {
            throw new BusinessException(ErrorCode.PDA_NOT_FUND_DATA);
        }
        pdaTaskManager.inboundSheleves(sta, stv, null, pdaLineList, creator);
    }


    public void savePdaAddedQty(StvLineCommand saveInfo, User creator) {
        if (saveInfo == null || saveInfo.getPdaId() == null || saveInfo.getPdaLineId() == null || saveInfo.getQuantity() == null) {
            throw new BusinessException(ErrorCode.INFO_ERROR_NOT_SAVE);
        }
        pdaExeValidate(saveInfo.getPdaId());
        PdaOrderLine pdaline = pdaLineDao.getByPrimaryKey(saveInfo.getPdaLineId());
        if (pdaline.getPdaUploadQty() == null) {
            // 保存PDA上传记录
            pdaline.setPdaUploadQty(pdaline.getQty());
        }
        pdaline.setAdjustQty(saveInfo.getQuantity());
        pdaline.setQty(pdaline.getPdaUploadQty() + pdaline.getAdjustQty());
        // 保存操作人
        pdaline.setOperateUser(creator);
        pdaLineDao.save(pdaline);
    }


    public void executePda(String staCode, StvLineCommand pdaId, User creator) {
        // 校验数据
        StockTransApplication sta = staDao.findStaByCode(staCode);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        StockTransVoucher stv = stvDao.findByStaAndTypeAndStatus(sta.getId(), StockTransVoucherStatus.CHECK, StockTransVoucherType.INBOUND_ORDER);
        if (stv == null) {
            throw new BusinessException(ErrorCode.PDA_ORDER_NOTEXIST_OR_ERROR);
        }
        PdaOrder pda = pdaExeValidate(pdaId.getPdaId());
        if (pda == null) {
            throw new BusinessException(ErrorCode.PDA_EXECUTE_ERROR);
        }
        pdaTaskManager.inboundSheleves(sta, stv, pda, null, creator);
        pdaOrderDao.flush();
    }


    private PdaOrder pdaExeValidate(Long pdaId) {
        PdaOrder pda = pdaOrderDao.getByPrimaryKey(pdaId);
        // 判断是否存在PDA的记录 同时是否是上架单记录
        if (pda == null || !PdaOrderType.INBOUND_SHELEVES.equals(pda.getType())) {
            throw new BusinessException(ErrorCode.PDA_EXECUTE_ERROR);
        }

        // 查看当前PDA状态是否未完成
        if (!DefaultStatus.CANCELED.equals(pda.getStatus()) && !DefaultStatus.CREATED.equals(pda.getStatus()) && !DefaultStatus.EXECUTING.equals(pda.getStatus())) {
            throw new BusinessException(ErrorCode.PDA_POST_LOG_STATUS_ERROR);
        }
        return pda;
    }

    private StockTransVoucher createGIStv(Long stvId, User creator) throws CloneNotSupportedException {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        if (!StockTransVoucherType.INBOUND_ORDER.equals(stv.getType()) || !StockTransVoucherStatus.CHECK.equals(stv.getStatus())) {
            throw new BusinessException(ErrorCode.STV_STRUTS_ERROR);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(stv.getSta().getId());
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        // 获取库区
        WarehouseDistrict dis = districtDao.findDistrictByCodeAndOu("GI", stv.getWarehouse().getId());
        if (dis == null) {
            dis = new WarehouseDistrict();
            dis.setCode("GI");
            dis.setName("收货暂存区");
            dis.setIntDistrictType(WarehouseDistrictType.RECEIVING.getValue());
            dis.setOu(stv.getWarehouse());
            districtDao.save(dis);
        }
        if (!WarehouseDistrictType.RECEIVING.equals(dis.getType())) {
            throw new BusinessException(ErrorCode.WH_DISTRICT_TYPE_ERROR);
        }
        StockTransVoucher shelvesStv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        shelvesStv.setBusinessSeqNo(biSeqNo.longValue());
        shelvesStv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        // 创建库位
        WarehouseLocation loc = new WarehouseLocation();
        String code = "GI_" + sta.getMainWarehouse().getCode() + "_" + sta.getCode() + "_";
        String count = shelvesStv.getCode().replaceAll(sta.getCode(), "");
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
        shelvesStv.setMode(null);
        shelvesStv.setCreateTime(new Date());
        shelvesStv.setCreator(creator);
        shelvesStv.setDirection(TransactionDirection.INBOUND);
        shelvesStv.setOwner(sta.getOwner());
        shelvesStv.setSta(sta);
        shelvesStv.setLastModifyTime(new Date());
        shelvesStv.setStatus(StockTransVoucherStatus.CREATED);
        shelvesStv.setType(StockTransVoucherType.INBOUND_SHELVES);
        shelvesStv.setWarehouse(sta.getMainWarehouse());
        shelvesStv.setTransactionType(tranType);
        shelvesStv.setGiLocationCode(loc.getCode());
        stvDao.save(shelvesStv);
        stvDao.flush();
        List<StvLine> stvLines = stvLineDao.findStvLineListByStvId(stvId);
        Long qty = 0L;
        for (StvLine l : stvLines) {
            StvLine stvLine = l.clone();
            stvLine.setStv(shelvesStv);
            stvLine.setQuantity(l.getQuantity() - (l.getAddedQty() == null ? 0 : l.getAddedQty()));
            stvLine.setLocation(loc);
            stvLine.setDistrict(dis);
            qty += stvLine.getQuantity();
            stvLine = stvLineDao.save(stvLine);
        }
        shelvesStv.setSkuQty(qty);
        stvDao.save(shelvesStv);
        stvDao.flush();
        return shelvesStv;
    }

    public boolean httpPostOmsOrderCancel(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StaDeliveryInfo d = sta.getStaDeliveryInfo();
        if (!StringUtils.hasText(sta.getSystemKey())) {} else {
            if (!sta.getSystemKey().equals("pacs")) {
                Boolean flag = false;
                List<String> chooseOptionList = chooseOptionManager.findByCategoryCode(Constants.WMS_ADAPTER_SYSTEM_KEY_CONFIG);
                if (null != chooseOptionList && chooseOptionList.size() > 0) {
                    if (!chooseOptionList.contains(sta.getSystemKey())) {
                        flag = true;
                    }
                }
                if(flag){
                    try {
                        WmsConfirmOrder wmsConfirmOrder=new WmsConfirmOrder();
                        wmsConfirmOrder.setOrderCode(sta.getRefSlipCode());
                        wmsConfirmOrder.setOrderType(sta.getType().getValue());
                        wmsConfirmOrder.setStatus(true);
                        wmsConfirmOrder.setOwner(sta.getOwner());
                        wmsConfirmOrder.setRefWarehouseCode(sta.getMainWarehouse().getCode());
                        wmsConfirmOrder.setStatusType("CANCEL");
                        wmsConfirmOrder.setSystemKey(sta.getSystemKey());
                        List<WmsShipping> wmsShippingList=new ArrayList<WmsShipping>();
                        WmsShipping wmsShipping=new WmsShipping();
                        wmsShipping.setShippingCode(sta.getCode());
                        wmsShipping.setWhCode(sta.getMainWarehouse().getCode());
                        List<StaLineCommand> staLineList=staLineDao.findByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                        List<WmsShippingLine> wmsShippingLineList=new ArrayList<WmsShippingLine>();
                        for(StaLineCommand list:staLineList){
                            WmsShippingLine wmsShippingLine=new WmsShippingLine();
                            wmsShippingLine.setLineNo(list.getLineNo());
                            wmsShippingLine.setQty(list.getQuantity());
                            wmsShippingLine.setSku(list.getCode());
                            wmsShippingLineList.add(wmsShippingLine);
                        }
                        wmsShipping.setLines(wmsShippingLineList);
                        wmsShippingList.add(wmsShipping);
                        wmsConfirmOrder.setShippings(wmsShippingList);
                        log.info("rmi Call hub4.0 wmsOrderFinishOms response begin!");
                        Response response = wms3AdapterInteractManager.wmsOrderFinishOms(wmsConfirmOrder);
                        log.info("rmi Call hub4.0 wmsOrderFinishOms response end!");
                        if (response != null) {
                            log.info("rmi Call hub4.0 wmsOrderFinishOms response!response=" + com.baozun.utilities.json.JsonUtil.writeValue(response));
                        }
                        MongoDBMessage mdbm = new MongoDBMessage();
                        mdbm.setStaCode(null);
                        mdbm.setOtherUniqueKey(wmsConfirmOrder.getOrderCode());
                        mdbm.setMsgBody(com.baozun.utilities.json.JsonUtil.writeValue(wmsConfirmOrder));
                        mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        if (response != null) {
                            mdbm.setMemo("推送wms3.0->hub4.0接口适配器(创单反馈)->response:" + com.baozun.utilities.json.JsonUtil.writeValue(response));
                        } else {
                            mdbm.setMemo("推送wms3.0->hub4.0接口适配器(创单反馈)->response is null!");
                        }
                        mongoOperation.save(mdbm);
                        if (response != null && response.getResponse()!=null&&1==response.getResponse().intValue()) {
                            // 发送OK
                            removeStaCancelNoticeOms(staId);
                            return true;
                        } else {
                            addStaCancelNoticeOmsCount(staId);
                            return false;
                        }
                    } catch (Exception e) {
                        log.error("rmi Call hub4.0 wmsOrderFinishOms response error :" + sta.getRefSlipCode(), e);
                        addStaCancelNoticeOmsCount(staId);
                        return false;
                    }
                }else{
                    MsgOrderCancel orderCancel = new MsgOrderCancel();
                    orderCancel.setCreateTime(new Date());
                    orderCancel.setIsCanceled(true);
                    orderCancel.setMsg("物流不可达");
                    orderCancel.setSource(sta.getStaDeliveryInfo().getLpCode());
                    orderCancel.setStaCode(sta.getCode());
                    orderCancel.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
                    orderCancel.setUpdateTime(new Date());
                    orderCancel.setSystemKey(sta.getSystemKey());
                    msgOrderCancelDao.save(orderCancel);
                    StaCancelNoticeOms log = staCancelNoticeOmsDao.getByStaId(staId);
                    if (log != null) {
                        log.setErrorCount(5L);
                    }
                    staCancelNoticeOmsDao.save(log);
                    return true;
                } 
           }
        }
        Map<String, String> params = new HashMap<String, String>();
        String callDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        InterfaceSecurityInfo s = interfaceSecurityInfoDao.findUseringUserBySource(Constants.OMS3_SOURCE, new Date());
        params.put("username", s.getUsername());
        params.put("source", s.getSource());
        params.put("calldate", callDate);
        StringBuffer sb = new StringBuffer();
        sb.append(s.getUsername());
        sb.append(s.getPassword());
        sb.append(callDate);
        params.put("secretkey", AppSecretUtil.generateSecret(sb.toString()));
        params.put("soCode", sta.getRefSlipCode());
        if (d.getIsCod() != null && d.getIsCod()) {
            params.put("lpCode", Transportator.EMS_COD);
        } else {
            params.put("lpCode", Transportator.EMS);
        }
        String result = "";
        try {
            String rs = HttpClientUtil.httpPost(OMS_SF_OUTBOUND, params);
            Map<String, Object> rsMap = JSONUtil.jsonToMap(rs, true);
            result = (String) rsMap.get("result");
            if (Action.SUCCESS.equals(result)) {
                // 处理成功
                log.error("httpPostOmsOrderSuccess" + staId);
                removeStaCancelNoticeOms(staId);
                return true;
            } else {
                // 处理失败
                addStaCancelNoticeOmsCount(staId);
                return false;
            }
        } catch (Exception e) {
            log.error("httpPostOmsOrderCancel" + staId + result, e);
            // ERROR
            addStaCancelNoticeOmsCount(staId);
            return false;
        }
    }

    private void removeStaCancelNoticeOms(Long staId) {
        StaCancelNoticeOms log = staCancelNoticeOmsDao.getByStaId(staId);
        if (log != null) {
            staCancelNoticeOmsDao.deleteByPrimaryKey(log.getId());
        }
    }

    public void addStaCancelNoticeOmsCount(Long staId) {
        StaCancelNoticeOms log = staCancelNoticeOmsDao.getByStaId(staId);
        if (log != null) {
            log.setErrorCount(log.getErrorCount() + 1);
        } else {
            log = new StaCancelNoticeOms();
            log.setErrorCount(0L);
            log.setStaId(staId);
        }
        staCancelNoticeOmsDao.save(log);
    }

    @SuppressWarnings("unchecked")
    private List<StvLine> staVirImportForrepairValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans, Long ouId) {
        List<StvLine> result = new ArrayList<StvLine>();
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus()) && !StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLines");
        List<StvLineCommand> stvLinesSheet2 = (List<StvLineCommand>) beans.get("stvLinesn");
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }

        List<StaLine> stalineList = staLineDao.findByStaId(sta.getId());
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        int index = 2;
        // 用于虚拟仓判断是否校验SN和有效期
        Warehouse house = warehouseDao.getByOuId(ouId); // 根据组织查找仓库
        if (house == null) {
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
        Warehouse addHouse = null;
        // 库间移动类型、同公司调拨、不同公司调拨，当出库仓库忽略了对应信息时，入库时不做校验，只做数据记录
        if (sta.getType() == StockTransApplicationType.TRANSIT_CROSS || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
            if (sta.getMainWarehouse() == null) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
            addHouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if (addHouse == null) {
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
        }

        /***************************** 验证sheet1数据 ***************************************/
        Iterator<StvLineCommand> it = stvLines.iterator();
        if (!(stvLines == null || stvLines.isEmpty())) {
            // 对于StvLine按skuID进行分组
            while (it.hasNext()) {
                StvLineCommand stvLine = it.next();
                index++;
                if (stvLine != null && stvLine.getSku() != null) {
                    if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                        it.remove();
                        continue;
                    } else {
                        stvLine.getSku().setBarCode(stvLine.getSku().getBarCode().trim());
                        // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                        if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode().trim()) || stvLine.getQuantity() == null) {// 没有库位和数量
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                            continue;
                        }
                        stvLine.getLocation().setCode(stvLine.getLocation().getCode().trim());
                        // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                        if (stvLine.getInvStatus() == null || !StringUtils.hasLength(stvLine.getInvStatus().getName())) {// 没有库位和数量
                            errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                            continue;
                        }
                        stvLine.getInvStatus().setName(stvLine.getInvStatus().getName().trim());
                        StaLine staLine = null;
                        for (StaLine sl : stalineList) {
                            if ((stvLines == null || stvLines.isEmpty()) && !sl.getSku().getIsSnSku()) {
                                throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
                            }
                            boolean isInvStatus = false;
                            // 判断是否是同一商品才校验库存状态，不然会报错库存状态不一致 xiaolong.fei
                            if (sl.getSku().getBarCode().equals(stvLine.getSku().getBarCode())) {
                                isInvStatus = sl.getInvStatus().getName().equals(stvLine.getInvStatus().getName());
                                if (!isInvStatus) {
                                    errors.add(new BusinessException(ErrorCode.STA_SKU_ISINVSTATUS_ERROR, new Object[] {index, stvLine.getSku().getBarCode()}));
                                }
                            }
                            boolean isBarCode = (sl.getSku().getBarCode() == null && !StringUtils.hasLength(stvLine.getSku().getBarCode())) || sl.getSku().getBarCode().equals(stvLine.getSku().getBarCode());
                            if (isInvStatus && isBarCode) {
                                staLine = sl;
                                break;
                            }
                        }
                        if (staLine == null) {
                            // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode()}));
                            continue;
                        }

                        Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                        // 存在有效期商品，且当前仓库没有勾选 是否不校验有效期
                        if (!stvLine.getIsShelfManagement().equals("否") && (house.getIsNotExpireDate() == null || !house.getIsNotExpireDate())) {
                            throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_DATE_ERROR);
                        }
                        // 库间移动类型。 存在有效期商品，且当前仓库没有勾选 是否不校验有效期
                        if (addHouse != null) {
                            if (!stvLine.getIsShelfManagement().equals("否") && (addHouse.getIsNotExpireDate() == null || !addHouse.getIsNotExpireDate())) {
                                throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_DATE_ERROR1);
                            }
                        }
                        // 库间移动，同公司调拨，不同公司调拨的 库位校验 出库仓的
                        Long whIdLong = null;
                        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                            whIdLong = sta.getAddiWarehouse().getId();
                        } else {
                            whIdLong = sta.getMainWarehouse().getId();
                        }
                        WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, whIdLong, stvLine.getLocation().getCode(), locationCache);
                        if (location == null) {// 库位编码不存在
                            // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        StvLine sl = new StvLine();
                        sl.setSku(sku);
                        String key = sku.getId() + "_" + staLine.getInvStatus().getId();
                        if (qtyMap.containsKey(key)) {
                            Long total = qtyMap.get(key);
                            if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity()))) {// 计划量与执行量不等
                                errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                                continue;
                            }
                            qtyMap.put(key, total + stvLine.getQuantity());
                        } else {
                            if (staLine.getCompleteQuantity() == null) {
                                staLine.setCompleteQuantity(0L);
                            }
                            if (stvLine.getQuantity() > (staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity()))) {// 计划量与执行量不等
                                errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                                continue;
                            }
                            qtyMap.put(key, stvLine.getQuantity());
                        }
                        sl.setLocation(location);
                        sl.setInvStatus(staLine.getInvStatus());
                        sl.setStaLine(staLine);
                        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                            sl.setOwner(stvLine.getOwner());
                        } else {
                            sl.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                        }
                        sl.setQuantity(stvLine.getQuantity());
                        sl.setReceiptQty(stvLine.getQuantity());
                        sl.setInBoundTime(new Date());
                        if (sl.getSku().getStoremode() == InboundStoreMode.SHELF_MANAGEMENT) {
                            Calendar cal = Calendar.getInstance();// 获取一个Claender实例
                            cal.set(2099, 00, 01, 00, 00, 00);// 设置日期，此时的日期是2099年01月01号
                            sl.setExpireDate(cal.getTime()); // 虚拟仓收货不校验有效期，设置时间，避免后续校验不通过
                        }
                        result.add(sl);
                    }
                } else {
                    it.remove();
                }
            }
        }
        /***************************** 验证sheet2数据 ***************************************/
        Iterator<StvLineCommand> it2 = stvLinesSheet2.iterator();
        if (!(stvLinesSheet2 == null || stvLinesSheet2.isEmpty())) {
            int index2 = 1;
            // 对于StvLine按skuID进行分组
            while (it2.hasNext()) {
                StvLineCommand stvLine = it2.next();
                if (stvLine == null) {
                    continue;
                }
                // stvLine.setQuantity(1L);
                index2++;
                if (stvLine != null && stvLine.getSku() != null) {
                    if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                        it2.remove();
                        continue;
                    } else {
                        // 判断商品 null
                        if (stvLine.getSku().getBarCode() == null) {
                            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
                        }
                        // 判断库存状态 是否null
                        if (stvLine.getInvStatus().getName() == null) {
                            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
                        }
                        // 存在有效期商品，且当前仓库没有勾选 是否不校验有效期
                        if (!stvLine.getIsShelfManagement().equals("否") && (house.getIsNotExpireDate() == null || !house.getIsNotExpireDate())) {
                            throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_DATE_ERROR);
                        }
                        // 仓库设置了校验SN，抛出异常
                        if (house.getIsNotSn() == null || !house.getIsNotSn()) {
                            throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_SN_ERROR);
                        }
                        // 库间移动类型、调拨
                        if (addHouse != null) {
                            // 存在有效期商品，且当前仓库没有勾选 是否不校验有效期
                            if (!stvLine.getIsShelfManagement().equals("否") && (addHouse.getIsNotExpireDate() == null || !addHouse.getIsNotExpireDate())) {
                                throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_DATE_ERROR1);
                            }
                            // 存在有效期商品，且当前仓库没有勾选 是否不校验有效期
                            if (addHouse.getIsNotSn() == null || !addHouse.getIsNotSn()) {
                                throw new BusinessException(ErrorCode.SKU_NOT_SHELF_LOCATION_SN_ERROR1);
                            }
                        }
                        // 库间移动，同公司调拨，不同公司调拨的 库位校验 出库仓的
                        Long whIdLong = null;
                        if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                            whIdLong = sta.getAddiWarehouse().getId();
                        } else {
                            whIdLong = sta.getMainWarehouse().getId();
                        }
                        WarehouseLocation loc = wareHouseManager.checkLocationByOuid(stvLine.getSku(), whIdLong, stvLine.getLocation().getCode(), locationCache);
                        if (loc == null) {// 库位编码不存在
                            // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        // 找出StvLine 对应的 StaLine
                        boolean isSet = false;
                        for (StaLine staLine : stalineList) {
                            if (staLine.getSku().getBarCode().equals(stvLine.getSku().getBarCode()) && staLine.getInvStatus().getName().equals(stvLine.getInvStatus().getName())) {
                                Sku sku = staLine.getSku();
                                if (sku.getIsSnSku() == null || !sku.getIsSnSku()) {
                                    errors.add(new BusinessException(ErrorCode.IMPUT_SNSKU_IS_NOT_SNSKU, new Object[] {index2, -1, stvLine.getSku().getBarCode()}));
                                }
                                InventoryStatus invStatus = staLine.getInvStatus();
                                // 保存已经检验过的商品数量
                                String key = sku.getId() + "_" + invStatus.getId();
                                if (qtyMap.containsKey(key)) {
                                    Long total = qtyMap.get(key);
                                    // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                                    if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity()))) {// 计划量与执行量不等
                                        errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                                        continue;
                                    }
                                    qtyMap.put(key, total + stvLine.getQuantity());
                                } else {
                                    if (staLine.getCompleteQuantity() == null) {
                                        staLine.setCompleteQuantity(0L);
                                    }
                                    if (stvLine.getQuantity() > (staLine.getQuantity() - (staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity()))) {// 计划量与执行量不等
                                        errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                                        continue;
                                    }
                                    qtyMap.put(key, stvLine.getQuantity());
                                }
                                StvLine sl = new StvLine();
                                sl.setSku(sku);
                                sl.setLocation(loc);
                                if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType()) || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                                    sl.setOwner(stvLine.getOwner());
                                } else {
                                    sl.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                                }
                                sl.setStaLine(staLine);
                                sl.setInvStatus(invStatus);
                                sl.setQuantity(stvLine.getQuantity());
                                sl.setReceiptQty(stvLine.getQuantity());
                                if (sl.getSku().getStoremode() == InboundStoreMode.SHELF_MANAGEMENT) {
                                    Calendar cal = Calendar.getInstance();// 获取一个Claender实例
                                    cal.set(2099, 00, 01, 00, 00, 00);// 设置日期，此时的日期是2099年01月01号
                                    sl.setExpireDate(cal.getTime()); // 虚拟仓收货不校验有效期，设置时间，避免后续校验不通过
                                }
                                result.add(sl);
                                isSet = true;
                            }
                        }
                        if (!isSet) {
                            errors.add(new BusinessException(ErrorCode.SKU_NOT_PLOT, new Object[] {stvLine.getSku().getBarCode()}));
                            continue;
                        }
                    }
                } else {
                    it2.remove();
                }
            }
        }
        /*
         * // 判断数量是否与计划量一致 if (errors.isEmpty()) { for (StaLine staLine : stalineList) { String key
         * = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId(); Long total =
         * qtyMap.get(key); // 判断数量 不等于计划量 if (total == null ||
         * !total.equals(staLine.getQuantity())) { errors.add(new
         * BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[]
         * {staLine.getSku().getBarCode(), staLine.getSku().getJmCode()})); } } }
         */
        if (!errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }

    // 根据仓库设置 ，判断是需要校验有效期
    private boolean isCheckDate(StockTransApplication sta) {
        if (sta.getMainWarehouse() != null) {
            Warehouse mainHouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if (mainHouse.getIsNotExpireDate() == null || !mainHouse.getIsNotExpireDate()) {
                return true;
            } else if (mainHouse.getIsNotExpireDate()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void closeInBoundFinish(Long staId, User user) {
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
                sta.setStatus(StockTransApplicationStatus.FINISHED);// 把状态修改成完成
                sta.setLastModifyTime(new Date());
                sta.setCreator(user);
                staDao.save(sta);
                staDao.flush();
                // Esprit关单反馈定制流程
                BiChannel shop = companyShopDao.getByCode(sta.getOwner());

                if (null != shop && null != shop.getVmiCode() && StringUtil.isEmpty(sta.getDataSource())) {
                    ChooseOption c = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("generateReceivingWhenClose", shop.getVmiCode());
                    if (c != null) {
                        VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                        if (vmi != null) {
                            vmi.generateReceivingWhenClose(sta);
                        }
                    }
                }


                // Gucci关单反馈定制流程
                if (org.apache.commons.lang3.StringUtils.equals(sta.getSystemKey(), Constants.GUCCI_SYSTEM_KEY) && StringUtil.isEmpty(sta.getDataSource())) {
                    VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                    if (vmi != null) {
                        vmi.generateReceivingWhenClose(sta);
                    }
                }
                // 通用品牌定制逻辑
                if (!StringUtil.isEmpty(shop.getDefaultCode()) && StringUtil.isEmpty(sta.getDataSource())) {
                    VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
                    vv.generateReceivingWhenClose(sta.getId());
                }
                // 自动转店
                vmiStaCreateManager.autoTransferByVmiInbound(sta);
                if (StringUtils.hasText(sta.getDataSource())) {
                    wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), 0);
                }
            }
        } catch (BusinessException e) {
            log.error("inbound response interface error:");
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException();
        }
    }

    /**
     * 获取运单号失败记录
     * 
     * @param id 作业单ID
     */
    @Override
    public void failedToGetTransno(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        sta.setTransMatchCount(sta.getTransMatchCount() == null ? 1 : sta.getTransMatchCount() + 1);
        long currentTime = System.currentTimeMillis() + (5 * sta.getTransMatchCount()) * 60 * 1000;
        Date date = new Date(currentTime);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dates = sdf.parse(nowTime);
            sta.setNextGetTransnoTime(dates);
        } catch (ParseException e) {
            log.error("", e);
        }
    }

    @Override
    public void msgUnLockedError(Long id) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(id);
        // 判断顺丰可否送达，否则修改快递供应商为EMS/EMSCOD
        MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta1.getCode());
        Warehouse wh = warehouseDao.getByOuId(sta1.getMainWarehouse().getId());
        StaDeliveryInfo sinfo = sta1.getStaDeliveryInfo();
        if (mo != null && null != sta1.getTransMatchCount() && sta1.getTransMatchCount().intValue() > 7) {
            if (!Constants.VIM_WH_SOURCE_GUESS.equals(mo.getSource()) && !org.apache.commons.lang3.StringUtils.equals(sinfo.getLpCode(), Constants.CJ_CNJH)) {
                if (sinfo.getIsCod() != null && sinfo.getIsCod()) {
                    sinfo.setLpCode(Transportator.EMS_COD);
                    mo.setLpCode(Transportator.EMS_COD);
                } else {
                    sinfo.setLpCode(Transportator.EMS);
                    mo.setLpCode(Transportator.EMS);
                }
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    mo.setIsLocked(true);
                } else {
                    mo.setIsLocked(false);
                    mo.setCreateTime(new Date());
                }
                msgOutboundOrderDao.save(mo);
                staDeliveryInfoDao.save(sinfo);
            }
        }

    }

    @Override
    public ReadStatus importStaInboundTurn(String mainWarehouseName, File staFile, User creator, Long ouId) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = null;
            readStatus = staReaderInboundTurn.readAll(new FileInputStream(staFile), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            @SuppressWarnings("unchecked")
            List<StockTransApplicationCommand> stas = (List<StockTransApplicationCommand>) beans.get("staCode");
            boolean status = true;
            String msg = "";
            for (StockTransApplicationCommand stockTransApplicationCommand : stas) {
                List<StockTransApplication> sta = staDao.findBySlipCodeOuId(stockTransApplicationCommand.getRefSlipCode(), ouId);
                if (sta.size() > 0) {
                    for (StockTransApplication stockTransApplication : sta) {
                        Warehouse wh1 = warehouseDao.getByOuId(stockTransApplication.getMainWarehouse().getId());
                        if (StringUtils.hasText(wh1.getVmiSource())) {
                            status = false;
                            if (msg.equals("")) {
                                msg = stockTransApplication.getRefSlipCode() + ":外包仓不允许转仓";
                                break;
                            } else {
                                msg += "," + stockTransApplication.getRefSlipCode() + ":外包仓不允许转仓";
                                break;
                            }
                        }
                        if (stockTransApplication.getStatus().getValue() != 1) {
                            status = false;
                            if (msg.equals("")) {
                                msg = stockTransApplication.getRefSlipCode();
                            } else {
                                msg += "," + stockTransApplication.getRefSlipCode();
                            }
                        } else {
                            List<StockTransVoucherCommand> stvs = stvDao.findEdwTwhStv(stockTransApplication.getId(), new BeanPropertyRowMapper<StockTransVoucherCommand>(StockTransVoucherCommand.class));
                            if (stvs == null || stvs.size() == 0) {
                                OperationUnit ou1 = operationUnitDao.findByName(mainWarehouseName);
                                Warehouse wh = warehouseDao.getByOuId(ou1.getId());
                                if (StringUtils.hasText(wh.getVmiSource())) {
                                    MsgInboundOrder msgInorder = new MsgInboundOrder();
                                    Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                                    msgInorder.setUuid(id);
                                    msgInorder.setSource(wh.getVmiSource());
                                    msgInorder.setSourceWh(wh.getVmiSourceWh());
                                    msgInorder.setStaCode(stockTransApplication.getCode());
                                    // bug
                                    msgInorder.setStatus(DefaultStatus.CREATED);
                                    StockTransApplication stainfo = staDao.findStaByCode(stockTransApplication.getCode());
                                    if (stainfo != null) {
                                        msgInorder.setType(stainfo.getType());
                                    }
                                    msgInorder.setCreateTime(new Date());
                                    msgInorder.setPlanArriveTime(stockTransApplication.getArriveTime());
                                    msgInorder.setTotalActual(stockTransApplication.getTotalActual());
                                    msgInorder.setRefSlipCode(stockTransApplication.getRefSlipCode());
                                    if (stockTransApplication.getOwner() != null) {
                                        msgInorder.setShopId(companyShopDao.getByCode(stockTransApplication.getAddiOwner() == null ? stockTransApplication.getOwner() : stockTransApplication.getAddiOwner()).getId());
                                    }
                                    msgInboundOrderDao.save(msgInorder);
                                    List<StaLine> stalines = staLineDao.findByStaId(stockTransApplication.getId());
                                    for (StaLine line : stalines) {
                                        MsgInboundOrderLine inline = new MsgInboundOrderLine();
                                        inline.setQty(line.getQuantity());
                                        inline.setSku(line.getSku());
                                        inline.setMsgInOrder(msgInorder);
                                        inline.setInvStatus(line.getInvStatus());
                                        inboundOrderLineDao.save(inline);
                                    }
                                }
                                stockTransApplication.setMainWarehouse(ou1);
                                staDao.save(stockTransApplication);
                            } else {
                                status = false;
                                if (msg.equals("")) {
                                    msg = stockTransApplication.getRefSlipCode();
                                } else {
                                    msg += "," + stockTransApplication.getRefSlipCode();
                                }
                            }
                        }
                    }
                } else {
                    status = false;
                    if (msg.equals("")) {
                        msg = stockTransApplicationCommand.getRefSlipCode() + "：未找到对应单据";
                    } else {
                        msg += "," + stockTransApplicationCommand.getRefSlipCode() + "：未找到对应单据";
                    }
                }
            }
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            if (!status) {
                throw new BusinessException(msg);
            }
            return readStatus;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("importStaInboundTurn Exception:", e);
            }
            throw e;
        }
    }

    /*
     * 获取一键交接出库实体list
     */
    @Override
    public Map<String, Object> getOneHandOutPack(Long userId, Long wId, String lpcode) {
        String hoIds = "";
        String brand = "0";
        Map<String, Object> map = new HashMap<String, Object>();
        List<OutBoundPack> list = outBoundPackDao.oneOutBoundPack(userId, wId, new BeanPropertyRowMapperExt<OutBoundPack>(OutBoundPack.class));
        if (lpcode != null) {
            String[] lpcodes = lpcode.split("\\|");
            for (String string : lpcodes) {
                List<Long> packageIds2 = new ArrayList<Long>();
                for (OutBoundPack out : list) {
                    if (string.equals(out.getLpcode())) {
                        packageIds2.add(out.getPackageId());
                    }
                }
                if (packageIds2.size() > 0) {
                    HandOverList ho = createHandOverListDelete(string, packageIds2, wId, userId, false);
                    // 交接
                    wareHouseManager.handoverListhandOver(ho, userId);
                    brand = "1";// 含有一键交接运单
                    hoIds += ho.getId().toString() + ",";//
                }
            }
        }
        map.put("brand", brand);
        map.put("hoIds", hoIds);
        return map;
    }

    /**
     * 自动化仓交接
     * 
     * @param userId
     * @param lpcode
     * @return
     */
    public Map<String, Object> getAutoOneHandOutPack(Long userId, String lpcode) {
        String hoIds = "";
        // String brand = "0";
        Map<String, Object> map = new HashMap<String, Object>();
        String lpcodeS[] = lpcode.split(",");
        List<HandOverList> hoLists = new ArrayList<HandOverList>();
        for (String lp : lpcodeS) {
            List<HandOverList> hoList = createAutoWhHandOverList(lp, userId);
            hoLists.addAll(hoList);
        }

        // 交接
        for (HandOverList ho : hoLists) {
            wareHouseManager.autoWhHandoverListhandOver(ho, userId);
            hoIds += ho.getId().toString() + ",";//
        }
        map.put("hoIds", hoIds);
        return map;
    }

    /**
     * 自动化创建交接清单
     * 
     * @param lpcode
     * @param userId
     * @return
     */
    public List<HandOverList> createAutoWhHandOverList(String lpcode, Long userId) {
        User user = null;
        int insert = 0;// 插入的条数
        int limit = 0;// 明细最大值
        if (userId != null) {
            user = userDao.getByPrimaryKey(userId);
            if (user == null) {
                throw new BusinessException(ErrorCode.USER_NOT_FOUND);
            }
        }

        ChooseOption c = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("autoWhHoListLimit", "limit");
        insert = limit = Integer.parseInt(c.getOptionValue());
        // 循环创建交接清单
        List<HandOverList> hoList = new ArrayList<HandOverList>();
        for (; insert == limit;) {
            HandOverList ho = new HandOverList();
            String code = lpcode + "-" + FormatUtil.formatDate(new Date(), "yyMMddHHmmss");
            ho.setCode(code);
            ho.setCreateTime(new Date());
            ho.setLpcode(lpcode);
            ho.setOperator(user);
            ho.setLastModifyTime(new Date());
            ho.setStatus(HandOverListStatus.CREATED);
            HandOverList handover = handOverListDao.save(ho);

            handOverListDao.flush();

            // 插入交接清单明细，返回插入的条数
            insert = outBoundPackDao.insertAutoWhHandOverList(limit, lpcode, handover.getId());
            if (insert == 0) {
                handOverListDao.delete(ho);
                break;
            }


            // 更新明细信息到交接清单中
            outBoundPackDao.updateHandOverListByLine(handover.getId());

            // 保存交接清单仓库
            outBoundPackDao.insertAutoWareHandOverList(handover.getId());

            // 更新包裹的交接清单明细
            outBoundPackDao.updatePackageInfoByHoList(handover.getId());

            // 自动化仓生成交接批成功后，设置中间表 已交接
            outBoundPackDao.deleteAutoWhOutBoundPack(handover.getId());

            hoList.add(handover);
        }

        return hoList;
    }

    @Override
    public ReadStatus importSeedSn(File staFile) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = null;
            readStatus = inSkuChildSn.readAll(new FileInputStream(staFile), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            @SuppressWarnings("unchecked")
            List<SkuChildSnCommand> childSns = (List<SkuChildSnCommand>) beans.get("skuChildSnList");
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (SkuChildSnCommand snCommand : childSns) {
                Integer sum = map.get(snCommand.getBarCode() + "-" + snCommand.getSn());
                if (sum == null) {
                    map.put(snCommand.getBarCode() + "-" + snCommand.getSn(), 1);
                } else {
                    map.put(snCommand.getBarCode() + "-" + snCommand.getSn(), map.get(snCommand.getBarCode() + "-" + snCommand.getSn()) + 1);
                }
            }
            for (SkuChildSnCommand skuChildSnCommand : childSns) {
                Sku sku = skuDao.getByBarcode1(skuChildSnCommand.getBarCode());
                if (sku.getChildSnQty() != null) {
                    if (sku.getChildSnQty().equals(map.get(skuChildSnCommand.getBarCode() + "-" + skuChildSnCommand.getSn()))) {
                        SkuChildSn childSn = new SkuChildSn();
                        childSn.setCreateTime(new Date());
                        childSn.setSeedSn(skuChildSnCommand.getSeedSn());
                        childSn.setSkuId(sku);
                        childSn.setSn(skuChildSnCommand.getSn());
                        childSn.setStatus(SkuSnCardStatus.NONACTIVATED);
                        childSnDao.save(childSn);
                    } else {
                        throw new BusinessException(ErrorCode.PDA_INBOUND_SKU_SN_QTY_ERROR, new Object[] {sku.getBarCode()});
                    }
                } else {
                    throw new BusinessException(ErrorCode.SKU_QTY_IS_NOT_NULL, new Object[] {sku.getBarCode()});
                }
            }
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
        return readStatus;

    }

    @Override
    public void updateNextGetTransTime(Long staid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        sta.setNextGetTransnoTime(c.getTime());
        staDao.flush();
    }


    @Override
    public void checkTrackingNoForHoList(String transNo) {
        List<String> list = new ArrayList<String>();
        list.add(transNo);
        List<PackageInfoCommand> ll = packageInfoDao.findByTrackingNoList2(list, null, null, new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
        if (ll == null || ll.size() == 0) {
            throw new BusinessException(ErrorCode.TRACKING_NO_IS_ERROR, new Object[] {transNo});
        }
    }

    @Override
    public String checkTrackingNo(String transNo, String lpCode) {
        PackageInfo p = packageInfoDao.findByTrackingNo(transNo);
        if (p == null) {
            return "1";
        } else {
            if (p.getLpCode().equals(lpCode)) {
                return "1";
            } else {
                DeliveryChanngeLog cl = dliveryChanngeLogDao.getDeliveryChanngeLogByTrackingNo(transNo, new BeanPropertyRowMapper<DeliveryChanngeLog>(DeliveryChanngeLog.class));
                if (cl != null && lpCode.equals(cl.getNewLpcode())) {
                    return "1";
                } else {
                    return "0";
                }
            }
        }
    }

    
    /**
     * 导入入库作业纸箱数量
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importCartonNum(File file, User creator, Long ouId) {
        log.info("===========importCartonNum start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StockTransCartonCommand> cartonList = null;

        ReadStatus rs = null;
        String errormsg="";
        try {
            rs = staReaderCartonImport.readAll(new FileInputStream(file), beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            
            cartonList = (List<StockTransCartonCommand>) beans.get("cartonList");
            if(cartonList!=null&&cartonList.size()>1000) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                rs.setMessage("列表数据大于1000条，不可导入！");;
                return rs;
            }
            for (StockTransCartonCommand carton : cartonList) {
                if (carton.getCode() == null){
                    continue;
                }
                

                if (carton.getCartonNum() == null){
                    if (errormsg.equals("")) {
                        errormsg = "入库做作业单号：" + carton.getCode() + "的纸箱数不可为空！";
                    }else {
                        errormsg += ",入库做作业单号：" + carton.getCode() + "的纸箱数不可为空！";
                    }
                    rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                    continue;
                }

                StockTransApplication stockSta = staDao.getByCodeAndOuID(carton.getCode(), ouId);
                //无作业申请单数据，则返回操作错误
                if (stockSta == null){
                    if (errormsg.equals("")) {
                        errormsg = "入库做作业单号：" + carton.getCode() + " 纸箱数:"+carton.getCartonNum()+"在本仓库中没有对应信息";
                    }else {
                        errormsg += ",入库做作业单号：" + carton.getCode() + " 纸箱数:"+carton.getCartonNum()+"在本仓库中没有对应信息";
                    }
                    rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                    continue;
                }
                //状态未已完成的 不可以修改废纸箱数量 CREATED(1):已创建     OCCUPIED(2):库存占用（配货中）  CHECKED(3):已核对 INTRANSIT(4):已转出   PARTLY_RETURNED(5):部分转入 PACKING(8):装箱中
                if (StockTransApplicationStatus.CREATED.equals(stockSta.getStatus()) || StockTransApplicationStatus.OCCUPIED.equals(stockSta.getStatus()) || StockTransApplicationStatus.CHECKED.equals(stockSta.getStatus())
                                || StockTransApplicationStatus.INTRANSIT.equals(stockSta.getStatus()) || StockTransApplicationStatus.PARTLY_RETURNED.equals(stockSta.getStatus()) || StockTransApplicationStatus.PACKING.equals(stockSta.getStatus())){
                    stockSta.setCartonNum(carton.getCartonNum());
                    staDao.save(stockSta);
                }
                
            }
        } catch (Exception e) {
            log.error("importCartonNum exception:", e);
            rs.setStatus(ReadStatus.STATUS_SYSTEM_ERROR);
            if (errormsg.equals("")) {
                errormsg="importCartonNum 导入异常。";
                
            }else {
                errormsg+=",importCartonNum 导入异常。";
            }
        }
        if(!errormsg.equals("")) {
            throw new BusinessException(errormsg);
        }
        return rs;
    }
    

}
