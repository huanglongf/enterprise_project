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
package com.jumbo.wms.manager.warehouse.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
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
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.PopUpAreaDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.dao.lf.StaLfDao;
import com.jumbo.dao.lf.StaLineLfDao;
import com.jumbo.dao.lf.ZdhPiciDao;
import com.jumbo.dao.lf.ZdhPiciLineDao;
import com.jumbo.dao.odo.OdoDao;
import com.jumbo.dao.odo.OdoLineDao;
import com.jumbo.dao.pickZone.WhPickZoneDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.vmi.espData.ESPInvoicePercentageDao;
import com.jumbo.dao.vmi.espData.ESPPoTypeDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiChannelSkuSuppliesDao;
import com.jumbo.dao.warehouse.EspritStoreDao;
import com.jumbo.dao.warehouse.ImportFileLogDao;
import com.jumbo.dao.warehouse.ImportPrintDataDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceSnLineDao;
import com.jumbo.dao.warehouse.InventoryCheckLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgSkuUpdateDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.PickingReplenishCfgDao;
import com.jumbo.dao.warehouse.RelationNikeDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.SkuTagDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
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
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderDeliveryInfo;
import com.jumbo.rmi.warehouse.OrderLine;
import com.jumbo.rmiservice.RmiService;
import com.jumbo.util.DateUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.InvChangeNoticPacThread;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiDefault;
import com.jumbo.wms.manager.vmi.VmiEsprit;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.MsgSkuUpdate;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.EspritStoreCommand;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.automaticEquipment.InboundRoleCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.lf.StaLf;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.lf.StaLineLf;
import com.jumbo.wms.model.lf.ZdhPici;
import com.jumbo.wms.model.lf.ZdhPiciLine;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.odo.OdoLineCommand;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.trans.SkuTag;
import com.jumbo.wms.model.vmi.Default.VmiOpType;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineDefault;
import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;
import com.jumbo.wms.model.vmi.espData.ESPPoType;
import com.jumbo.wms.model.vmi.espData.ESPPoTypeCommand;
import com.jumbo.wms.model.vmi.espData.EspritOrderPOType;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelSkuSupplies;
import com.jumbo.wms.model.warehouse.FreightMode;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.ImportPrintData;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceSnLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceSnLineType;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingReplenishCfg;
import com.jumbo.wms.model.warehouse.RelationNike;
import com.jumbo.wms.model.warehouse.ReturnPackage;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaSnImportCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TimeTypeMode;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.opensymphony.xwork2.ActionContext;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelUtil;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.definition.ExcelBlock;
import loxia.support.excel.definition.ExcelSheet;
import loxia.support.excel.impl.DefaultReadStatus;

@Transactional
@Service("excelReadManager")
public class ExcelReadManagerImpl extends BaseManagerImpl implements ExcelReadManager {

    /**
     * 
     */
    private static final long serialVersionUID = -7991454872302284193L;
    private EventObserver eventObserver;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BiChannelSkuSuppliesDao biChannelSkuSuppliesDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private ESPPoTypeDao potypeDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WareHouseManagerProxy whManagerProxy;
    @Autowired
    private SkuSnLogDao snLogDao;
    @Autowired
    private ESPInvoicePercentageDao ipDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private InventoryCheckDifTotalLineDao vmiinvCheckLineDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private ZdhPiciDao zdhPiciDao;
    @Autowired
    private ZdhPiciLineDao zdhPiciLineDao;
    @Autowired
    private EspritStoreDao espritStoreDao;
    @Autowired
    private InventoryCheckLineDao inventoryCheckLineDao;
    @Autowired
    private PickingReplenishCfgDao pickingReplenishCfgDao;
    @Autowired
    private InventoryCheckDifferenceSnLineDao inventoryCheckDifferenceSnLineDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private ChannelWhRefRefDao warehouseShopRefDao;
    @Autowired
    private RmiService rmiService;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private ImportPrintDataDao importPrintDataDao;
    @Autowired
    private InventoryStatusDao isDao;
    @Autowired
    private MsgSkuUpdateDao msgSkuUpdateDao;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private SkuTagDao skuTagDao;
    @Autowired
    private RelationNikeDao relationNikeDao;
    @Autowired
    private NikeStockInDao nikeStockInDao;
    @Resource(name = "orderLineReader")
    private ExcelReader orderLineReader;
    @Resource(name = "pickingListReader")
    private ExcelReader pickingListReader;
    @Resource(name = "predefinedOutReader")
    private ExcelReader predefinedOutReader;
    @Resource(name = "packagingMaterialsReader")
    private ExcelReader packagingMaterialsReader;
    @Resource(name = "locationReader")
    private ExcelReader locationReader;
    @Resource(name = "staReaderForPurchase")
    private ExcelReader staReaderForPurchase;
    @Resource(name = "staReaderForPredefined")
    private ExcelReader staReaderForPredefined;
    @Resource(name = "staReaderForRepair")
    private ExcelReader staReaderForRepair;
    @Resource(name = "externalOutWHReader")
    private ExcelReader externalOutWHReader;
    @Resource(name = "snsReader")
    private ExcelReader snsReader;
    @Resource(name = "trackingNoReaderForHandOverList")
    private ExcelReader trackingNoReaderForHandOverList;
    @Resource(name = "inventoryInitializeReader")
    private ExcelReader inventoryInitializeReader;
    @Resource(name = "checkOverageImportReader")
    private ExcelReader checkOverageImportReader;
    @Resource(name = "transitInnerReader")
    private ExcelReader transitInnerReader;
    @Resource(name = "betweenLibaryInitializeReader")
    private ExcelReader betweenLibaryInitializeReader;
    @Resource(name = "odoLineReader")
    private ExcelReader odoLineReader;
    @Resource(name = "inventoryCheckImportReader")
    private ExcelReader inventoryCheckImportReader;
    @Resource(name = "otherStaReader")
    private ExcelReader otherStaReader;
    @Resource(name = "predefinedStaReader")
    private ExcelReader predefinedStaReader;
    @Resource(name = "inventoryStatusChangeReader")
    private ExcelReader inventoryStatusChangeReader;
    @Resource(name = "outboundSnImportReader")
    private ExcelReader outboundSnImportReader;
    @Resource(name = "salesOutSnImportReader")
    private ExcelReader salesOutSnImportReader;
    @Resource(name = "pdaPurchaseSnImportReader")
    private ExcelReader pdaPurchaseSnImportReader;
    @Resource(name = "vmiAdjustmentImportReader")
    private ExcelReader vmiAdjustmentImportReader;
    @Resource(name = "inboundReceiveAmountConfirmReader")
    private ExcelReader inboundReceiveAmountConfirmReader;
    @Resource(name = "vmiOwnerTransferImportReader")
    private ExcelReader vmiOwnerTransferImportReader;
    @Resource(name = "vmiAdjustInventoryReader")
    private ExcelReader vmiAdjustInventoryReader;
    @Resource(name = "vmiReturnImportReader")
    private ExcelReader vmiReturnImportReader;

    @Resource(name = "vmiEspritReturnImportReader")
    private ExcelReader vmiEspritReturnImportReader;


    @Resource(name = "vmicreateStaForVMIReturnPlImportReader")
    private ExcelReader vmicreateStaForVMIReturnPlImportReader;

    @Resource(name = "zdhPiciImportReader")
    private ExcelReader zdhPiciImportReader;


    @Resource(name = "vmiReturnImportGucciReader")
    private ExcelReader vmiReturnImportGucciReader;


    @Resource(name = "inventoryCheckByLocationImportReader")
    private ExcelReader inventoryCheckByLocationImportReader;
    @Resource(name = "vmiPOTypeReader")
    private ExcelReader vmiPOTypeReader;
    @Resource(name = "vmiInvoicePercentageReader")
    private ExcelReader vmiInvoicePercentageReader;
    @Resource(name = "invReaderForPurchase")
    private ExcelReader invReaderForPurchase;
    @Resource(name = "warehouseReplenishStockInfoReader")
    private ExcelReader warehouseReplenishStockInfoReader;
    @Resource(name = "productForBoxReader")
    private ExcelReader productForBoxReader;
    @Resource(name = "skuProvideMaxCountMaintainReader")
    private ExcelReader skuProvideMaxCountMaintainReader;
    @Resource(name = "snnumberMainTainReader")
    private ExcelReader snnumberMainTainReader;
    @Resource(name = "skuShelfLifeReader")
    private ExcelReader skuShelfLifeReader;
    @Resource
    private ApplicationContext applicationContext;
    @Resource(name = "importPrintReader")
    private ExcelReader importPrintReader;
    @Resource(name = "refSkuReader")
    private ExcelReader refSkuReader;
    @Resource(name = "snSkuReadForPurchase")
    private ExcelReader snSkuReadForPurchase;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransTxLogDao stLogDao;
    @Autowired
    private TransOlManager transOlManager;
    @Resource(name = "pickZoneImportReader")
    private ExcelReader pickZoneImportReader;
    @Autowired
    private WhPickZoneDao whPickZoneDao;
    @Autowired
    private StaLfDao staLfDao;
    @Autowired
    private StaLineLfDao staLineLfDao;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private PopUpAreaDao popUpAreaDao;
    @Resource(name = "inboundRoleReader")
    private ExcelReader inboundRoleReader;
    @Resource(name = "skuTypeReader")
    private ExcelReader skuTypeReader;

    @Resource(name = "skuSpTypeReader")
    private ExcelReader skuSpTypeReader;


    @Resource(name = "channelSkuSpTypeReader")
    private ExcelReader channelSkuSpTypeReader;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;

    @Resource(name = "skuReader")
    private ExcelReader skuReader;

    @Resource(name = "returnPackageReader")
    private ExcelReader returnPackageReader;

    @Resource(name = "outOfStorageSnImport")
    private ExcelReader outOfStorageSnImport;

    @Resource(name = "converseShutoutImport")
    private ExcelReader converseShutoutImport;
    @Autowired
    private ImportFileLogDao importFileLogDao;

    @Autowired
    private ChooseOptionManager chooseOptionManager;


    @Resource(name = "orderOutBoundReader")
    private ExcelReader orderOutBoundReader;

    @Resource(name = "nikeRelationImport")
    private ExcelReader nikeRelationImport;
    
    @Resource(name = "supplierCodeImport")
    private ExcelReader supplierCodeImport;
    @Autowired
    private OdoDao odoDao;
    @Autowired
    private OdoLineDao odoLineDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named jmsTemplate Class:ExcelReadManagerImpl");
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importSNnumber(File file, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs;
        try {
            rs = snnumberMainTainReader.readSheet(new FileInputStream(file), 0, beans);
            if (rs == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("ExcelReadManager.FileNotFoundException Exception:", e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
            if (rs != null) {
                Sku sku = null;
                List<SkuSn> list = (List<SkuSn>) beans.get("skusn");
                if (list.isEmpty() || list.size() == 0) {
                    throw new BusinessException(ErrorCode.SNS_NO_DATA);
                }
                List<String> errlist = new ArrayList<String>();
                log.debug("**********************" + "Excel是否有重复比较");
                for (int i = 0; i < list.size(); i++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        if (list.get(i).getSn().equals(list.get(j).getSn())) {
                            errlist.add(list.get(j).getSn());
                        }
                    }
                }

                if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {errlist.toString()});
                errlist.clear();
                if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});
                Long customerId = null;
                Warehouse wh = warehouseDao.getByOuId(ouid);
                if (wh != null && wh.getCustomer() != null) {
                    customerId = wh.getCustomer().getId();
                }
                for (SkuSn e : list) {
                    if (!StringUtils.hasText(e.getSn())) {
                        errlist.add(e.getSn());
                    }
                    if (e.getSku().getCode() == null) throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    Sku s = skuDao.getByBarcode(e.getSku().getCode(), customerId);
                    Boolean issnproduct = s.getIsSnSku();
                    if (issnproduct == null) {
                        throw new BusinessException(ErrorCode.SNS_NOT_SN, new Object[] {e.getSku().getCode()});
                    } else if (issnproduct.equals(false)) {
                        throw new BusinessException(ErrorCode.SNS_NOT_SN, new Object[] {e.getSku().getCode()});
                    }
                    if (e.getSn() == null) throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    if (e.getSn() != null) {
                        Boolean b = false;
                        if (e.getSn().length() != e.getSn().trim().length()) {
                            b = true;
                        } else {
                            if (e.getSn().trim().replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0) {
                                b = true;
                            }
                        }
                        if (b) {
                            throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {e.getSn()});
                        }
                    }
                    // if (e.getSn() != null && e.getSn().indexOf(" ") != -1) throw new
                    // BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION);
                    // if (e.getSn().replaceAll("[a-z]*[A-Z]*\\d*-*_*", "").length() != 0) throw new
                    // BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION);
                    SkuSn snSkuSn = skuSnDao.findSkuSnBySnSingle(e.getSn());
                    if (snSkuSn != null) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUEO, new Object[] {e.getSn()});
                    sku = skuDao.getByBarcode(e.getSku().getCode(), customerId);
                    if (sku == null) throw new BusinessException(ErrorCode.IMPORT_SN_SKU_ISNOTFIND, new Object[] {e.getSku().getCode()});
                    try {
                        wareHouseManager.createSnImport(e.getSn().trim(), 1, ouid, sku.getId());
                    } catch (Exception e2) {
                        log.error(e2.getMessage());
                        throw new BusinessException(ErrorCode.IMPORT_SN_ERROR, new Object[] {e.getSn()});
                    }
                }

            }

        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus packagingMaterialsImport(Long staId, File staFile, User creator) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 校验出库是否已经完成
        if (!sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        readStatus = packagingMaterialsReader.readAll(new FileInputStream(staFile), beans);
        if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return readStatus;
        }
        List<StaAdditionalLine> pmList = (List<StaAdditionalLine>) beans.get("pmList");
        if (pmList == null || pmList.isEmpty()) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }
        // 获取公司ID
        Long whId = sta.getMainWarehouse().getParentUnit().getParentUnit().getId();
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        Long customerId = null;
        if (wh != null && wh.getCustomer() != null) {
            customerId = wh.getCustomer().getId();
        }
        /***************************** 验证sheet1数据 ***************************************/
        int index = 4;
        if (pmList != null && !pmList.isEmpty()) {
            // 删除上次导入的数据
            staAdditionalLineDao.deleteStaAddLineByStaId(staId);
            Iterator<StaAdditionalLine> it = pmList.iterator();
            while (it.hasNext()) {
                StaAdditionalLine addlineRaw = it.next();
                index++;
                if (addlineRaw != null && addlineRaw.getSku() != null && addlineRaw.getSku().getBarCode() != null) {
                    if (addlineRaw.getQuantity() == null && addlineRaw.getQuantity() < 1) {
                        throw new BusinessException(ErrorCode.INV_CHECK_QUANTITY_ERROR, new Object[] {index});
                    }
                    String barcode = addlineRaw.getSku().getBarCode();
                    Inventory inv = wareHouseManager.getAddlineSkuCache(barcode, whId, customerId);
                    if (inv == null) {
                        throw new BusinessException(ErrorCode.OUT_BOUND_NEED_WRAP_STUFF_NOT_FOUND, new Object[] {barcode});
                    }
                    StaAdditionalLine addline = new StaAdditionalLine();
                    addline.setSku(inv.getSku());
                    addline.setSta(sta);
                    addline.setCreateTime(new Date());
                    addline.setOwner(sta.getOwner());
                    addline.setLpcode(sta.getStaDeliveryInfo() == null ? null : sta.getStaDeliveryInfo().getLpCode());
                    addline.setQuantity(addlineRaw.getQuantity());
                    addline.setSkuCost(inv.getSkuCost());
                    staAdditionalLineDao.save(addline);
                } else {
                    it.remove();
                }
            }
        } else {
            throw new BusinessException(ErrorCode.INV_CHECK_IMPORT_IS_NULL);
        }
        if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return readStatus;
        }
        return readStatus;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importSkuProvideMaxMaintain(File file, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = skuProvideMaxCountMaintainReader.readAll(new FileInputStream(file), beans);
            List<SkuCommand> skus = (List<SkuCommand>) beans.get("skus");
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");
            Map<String, WarehouseDistrict> districtCache = new HashMap<String, WarehouseDistrict>();
            Map<String, Sku> skuCache = new HashMap<String, Sku>();
            Map<String, PickingReplenishCfg> cfgCache = new HashMap<String, PickingReplenishCfg>();
            // 获取当前仓库信息，新增逻辑需要知道是否为共享仓
            Warehouse wh = warehouseDao.getByOuId(ouid);
            // List<String> cslist = companyShopDao.findShopInnerCodeByWarehouse(ouid, new
            // SingleColumnRowMapper<String>(String.class));
            Map<String, String> nameMap = new HashMap<String, String>();
            // for (String s : cslist) {
            // nameMap.put(s, s);
            // }
            rs = validateForSkuProvideMaintain(rs, type, skus, skuCache, districtCache, cfgCache, ouid, wh.getIsShare(), nameMap);

            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            if (skus != null && !skus.isEmpty()) {
                PickingReplenishCfg cfg = null;
                OperationUnit ou = operationUnitDao.getByPrimaryKey(ouid);
                for (SkuCommand skuCmd : skus) {
                    if (cfgCache.get(skuCmd.getCode() + skuCmd.getDistrictCode() + skuCmd.getShopOwner()) == null) {
                        cfg = new PickingReplenishCfg();
                        cfg.setDistrict(districtCache.get(skuCmd.getDistrictCode()));
                        cfg.setSku(skuCache.get(skuCmd.getCode()));
                        cfg.setMaxQty(Long.valueOf(skuCmd.getQuantity()));
                        cfg.setOu(ou);
                        cfg.setWarningPre(skuCmd.getWarningPre());
                        cfg.setOwner(skuCmd.getShopOwner());
                        pickingReplenishCfgDao.save(cfg);
                    } else {
                        pickingReplenishCfgDao.updateBySkuAndDistrict(skuCache.get(skuCmd.getCode()).getId(), districtCache.get(skuCmd.getDistrictCode()).getId(), Long.valueOf(skuCmd.getQuantity()), skuCmd.getWarningPre(), ouid, skuCmd.getShopOwner());
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return rs;
    }

    private ReadStatus validateForSkuProvideMaintain(ReadStatus rs, String type, List<SkuCommand> skus, Map<String, Sku> skuCache, Map<String, WarehouseDistrict> districtCache, Map<String, PickingReplenishCfg> cfgCache, Long ouid, Boolean isShare,
            Map<String, String> nameMap) {
        final ExcelSheet sheet = skuProvideMaxCountMaintainReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheet.getSortedExcelBlocks();
        int intType;
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(0).getStartRow(), blocksOut.get(0).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // 校验
        final ExcelSheet sheets = skuProvideMaxCountMaintainReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        String currCell = null;
        Sku sku = null;
        WarehouseDistrict district = null;
        // 所属店铺 非共享仓使用
        String innerShopCode = null;
        PickingReplenishCfg cfg = null;
        Warehouse wh = warehouseDao.getByOuId(ouid);
        Long customerId = null;
        if (wh != null && wh.getCustomer() != null) {
            customerId = wh.getCustomer().getId();
        }
        for (SkuCommand skuCmd : skus) {
            sku = skuCache.get(skuCmd.getCode());
            if (sku == null) {
                if (intType == 1) {
                    sku = skuDao.getByBarcode(skuCmd.getCode(), customerId);
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SKU_NOT_FOUND_FOR_EXCEL_IMPORT, new Object[] {SHEET_0, currCell, skuCmd.getCode()}));
                    } else {
                        skuCache.put(skuCmd.getCode(), sku);
                    }
                } else if (intType == 0) {
                    sku = skuDao.getByCode(skuCmd.getCode());
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SKU_NOT_FOUND_FOR_EXCEL_IMPORT, new Object[] {SHEET_0, currCell, skuCmd.getCode()}));
                    } else {
                        skuCache.put(skuCmd.getCode(), sku);
                    }
                }
            }
            // 判断库区是否存在
            district = districtCache.get(skuCmd.getDistrictCode());
            if (district == null) {
                district = warehouseDistrictDao.findDistrictByCodeAndOu(skuCmd.getDistrictCode(), ouid);
                if (district == null) {
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.WAREHOUSE_DISTRICT_NOT_FOUND_FOR_EXCEL_IMPORT, new Object[] {SHEET_0, currCell, skuCmd.getDistrictCode()}));
                } else {
                    districtCache.put(skuCmd.getDistrictCode(), district);
                }
            }
            // 判断是否拣货区的
            if (districtCache.get(skuCmd.getDistrictCode()).getType().getValue() != 1) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.WAREHOUSE_DISTRICT_NOT_PICKING_DISTRICT, new Object[] {SHEET_0, currCell, skuCmd.getDistrictCode()}));
            }

            if (skuCmd.getWarningPre().compareTo(new BigDecimal(0.01)) == -1 || skuCmd.getWarningPre().compareTo(new BigDecimal(1)) == 1) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 3);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.SKU_PROVIDE_WARNINGPRE_IS_ERROR, new Object[] {SHEET_0, currCell, skuCmd.getWarningPre()}));
            }
            // 如果非共享仓 校验所属店铺必须维护，
            if (!isShare) {
                // 判断店铺是否维护
                innerShopCode = skuCmd.getShopOwner();
                if (innerShopCode == null) {// 如果没有维护店铺，提示没有维护店铺错误
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 4);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.SHOP_OWNER_NO_DATA, new Object[] {SHEET_0, currCell}));
                } else {
                    if (nameMap.get(innerShopCode) == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 4);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SHOP_OWNER_ERROR, new Object[] {SHEET_0, currCell, skuCmd.getShopOwner()}));
                    }
                }
            }
            if (intType == 1) {// barcode
                cfg = pickingReplenishCfgDao.findBySkuDistrictAndOu(skuCmd.getCode(), null, skuCmd.getDistrictCode(), ouid, skuCmd.getShopOwner(), new BeanPropertyRowMapperExt<PickingReplenishCfg>(PickingReplenishCfg.class));
            } else if (intType == 0) {// sku code
                cfg = pickingReplenishCfgDao.findBySkuDistrictAndOu(null, skuCmd.getCode(), skuCmd.getDistrictCode(), ouid, skuCmd.getShopOwner(), new BeanPropertyRowMapperExt<PickingReplenishCfg>(PickingReplenishCfg.class));
            }

            if (cfg != null) {
                cfgCache.put(skuCmd.getCode() + skuCmd.getDistrictCode() + skuCmd.getShopOwner(), cfg);
            }
            cfg = null;
            offsetRow++;
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importProductFoxBoxOperate(File file, Long userid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = productForBoxReader.readAll(new FileInputStream(file), beans);
            List<Sku> products = (List<Sku>) beans.get("products");
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            if (products != null && !products.isEmpty()) {
                Sku product = null;
                final ExcelSheet sheets = productForBoxReader.getDefinition().getExcelSheets().get(0);
                String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
                int offsetRow = 0;
                String currCell = null;
                for (Sku p : products) {
                    if (p == null) {
                        continue;
                    }
                    List<Sku> skuList = skuDao.getByJmCode(p.getCode());
                    if (product == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_PRODUCT_NOT_FOUND, new Object[] {SHEET_0, currCell, p.getCode()}));
                        return rs;
                    } else {
                        if (p.getBoxQty().compareTo(Constants.ONE) == -1) {
                            currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_PRODUCT_BOX_QUANTITY_ERROR, new Object[] {SHEET_0, currCell, p.getBoxQty()}));
                            return rs;
                        }
                        for (Sku sku : skuList) {
                            sku.setBoxQty(p.getBoxQty());
                            sku.setLastModifyTime(new Date());
                        }
                    }
                    product = null;
                    offsetRow++;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return rs;
    }


    // vmi批量 退大仓
    @SuppressWarnings("unchecked")
    public ReadStatus createStaForVMIReturnPl(String owner, StaDeliveryInfo stadelivery2, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag)
            throws Exception {
        List<StaLineCommand> stalinecmds2 = new ArrayList<StaLineCommand>();
        // String lpCode = "";
        String type = "SKU条码";// nikeCRW 定制基于upc（barcode）

        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmicreateStaForVMIReturnPlImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            Map<String, Map<String, Long>> mapSta = new HashMap<String, Map<String, Long>>();
            Map<String, StaLfCommand> mapStaEn = new HashMap<String, StaLfCommand>();
            Map<String, StaLfCommand> mapStaUpcEn = new HashMap<String, StaLfCommand>();


            List<StaLfCommand> stalfcmds = (List<StaLfCommand>) beans.get("stalfcmds");
            // 赋值到stalinecmds
            for (StaLfCommand staLfCommand : stalfcmds) {
                StaLineCommand staLineCommand = new StaLineCommand();
                staLineCommand.setSkuCode(staLfCommand.getSkuUpc());
                System.out.println(staLfCommand.getSkuUpc());
                staLineCommand.setQuantity(staLfCommand.getSkuQty());
                staLineCommand.setIntInvstatusName("良品");
                stalinecmds2.add(staLineCommand);
            }

            for (StaLfCommand line : stalfcmds) {
                String staUnique = line.getSlipcode();
                StaLfCommand en = mapStaEn.get(staUnique);
                if (en == null) {// 根据slipcode 插入唯一实体
                    mapStaEn.put(staUnique, line);
                }

                StaLfCommand en2 = mapStaUpcEn.get(staUnique + line.getSkuUpc());
                if (en2 == null) {// 根据slipcode+upc 插入实体
                    mapStaUpcEn.put(staUnique + ";" + line.getSkuUpc(), line);
                }

                // 商品为一维度
                Map<String, Long> mapSKu = new HashMap<String, Long>();
                // slipcode为一维度
                Map<String, Long> mapSKu2 = mapSta.get(staUnique);
                if (mapSKu2 == null) {
                    // upc,数量
                    mapSKu.put(line.getSkuUpc(), line.getSkuQty());
                    mapSta.put(staUnique, mapSKu);
                } else {
                    Long num = mapSKu2.get(line.getSkuUpc());
                    if (num == null) {
                        mapSKu2.put(line.getSkuUpc(), line.getSkuQty());
                    } else {
                        mapSKu2.put(line.getSkuUpc(), num + line.getSkuQty());
                    }
                }
            }
            StringBuffer sb = new StringBuffer();
            Map<String, String> mapString = new HashMap<String, String>();
            // 验证 slicode唯一 其他数值都一样
            for (StaLfCommand line : stalfcmds) {
                String staUnique = line.getSlipcode();
                // 首先判断地址 4个地址不能同时为空
                if (line.getAddress1() == null && line.getAddress2() == null && line.getAddress3() == null && line.getAddress4() == null) {
                    sb.append("LF出库单号：" + staUnique + "," + "4个地址不能同时为空;");
                    break;
                } else {
                    String staUniqueOther =
                            line.getTransportMode() + line.getOrderType() + line.getSlipcode1() + line.getSlipcode2() + line.getPackSlipNo() + line.getPlantime() + line.getYunshutime() + line.getNfsStoreCode() + line.getCity() + line.getZip()
                                    + line.getAddress1() + line.getAddress2() + line.getAddress3() + line.getAddress4() + line.getCompanyName() + line.getNikePo() + line.getVasCode() + line.getDivisionCode();
                    String staUniqueOther2 = mapString.get(staUnique);
                    if ("".equals(staUniqueOther2) || staUniqueOther2 == null) {
                        mapString.put(staUnique, staUniqueOther);
                    } else {
                        if (!staUniqueOther2.equals(staUniqueOther)) {
                            sb.append("LF出库单号：" + staUnique + "等其他字段不唯一" + ";");
                            break;
                        }
                    }
                }
            }
            if (!StringUtil.isEmpty(sb.toString())) {
                throw new BusinessException(sb.toString());
            }
            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
            if (!"1028".equals(shop.getVmiCode())) {
                throw new BusinessException("请选择Nike CRW线下配送店");
            }
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());

            // 检验所有文件商品数量库存是满足 S
            StockTransApplication s = new StockTransApplication();
            s.setType(StockTransApplicationType.valueOf(101));// 退大仓
            s.setFreightMode(FreightMode.valueOf(10));// 上门自取
            BigDecimal transactionid = transactionTypeDao.findByStaType(s.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            if (transactionid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
            Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
            Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();
            List<StaLine> stalines = new ArrayList<StaLine>();
            rs = vmiReturnValidate2(rs, stalinecmds2, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, s);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }


            // 封装sta sta物流信息
            for (Map.Entry<String, Map<String, Long>> en : mapSta.entrySet()) {
                // 验证作业单
                StockTransApplication returnSta = staDao.findReturnMaxWarehouseOrder(en.getKey(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                if (null != returnSta) {
                    throw new BusinessException(ErrorCode.RETURN_ORDER_STA_IS_CREATED, new Object[] {en.getKey()});
                }
                StaLfCommand staLfCommand = mapStaEn.get(en.getKey());
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.valueOf(101));// 退大仓
                sta.setRefSlipCode(staLfCommand.getSlipcode());// slipcode
                sta.setSlipCode1(staLfCommand.getSlipcode1());// slipcode1
                sta.setSlipCode2(staLfCommand.getSlipcode2());// slipcode2
                sta.setFreightMode(FreightMode.valueOf(10));// 上门自取
                if (staLfCommand.getPlantime() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = df.parse(staLfCommand.getPlantime());
                    sta.setPlanOutboundTime(date);// 计划发货时间
                }
                StaDeliveryInfo stadelivery = new StaDeliveryInfo();
                stadelivery.setProvince(null);
                stadelivery.setCity(staLfCommand.getCity());
                stadelivery.setDistrict(null);
                stadelivery.setAddress(staLfCommand.getAddress1() + "," + staLfCommand.getAddress2() + "," + staLfCommand.getAddress3() + "," + staLfCommand.getAddress4());
                stadelivery.setReceiver(null);
                stadelivery.setTelephone(null);
                stadelivery.setLpCode(null);

                // 封装
                List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
                Map<String, Long> skus = en.getValue();
                for (Map.Entry<String, Long> sku : skus.entrySet()) {
                    // String[] codes = sku.getKey().split(";");
                    StaLineCommand staLineCommand = new StaLineCommand();
                    staLineCommand.setSkuCode(sku.getKey());
                    staLineCommand.setQuantity(sku.getValue());
                    staLineCommand.setIntInvstatusName("良品");
                    stalinecmds.add(staLineCommand);
                }
                // 基于店铺进行店铺退仓操作line
                rs = creStaForVMIReturnEsToLine(stalinecmds, rs, type, shop, sta, stadelivery, file, innerShopCode, toLocation, ouid, cmpOuid, userid, reasonCode, espritFlag, espritTransferFlag, true, staLfCommand, mapStaUpcEn);
                if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    return rs;
                }



            }



        } catch (Exception e) {
            e.printStackTrace();
            if (log.isErrorEnabled()) {
                // log.error("creStaForVMIReturnEs Exception:" + lpCode, e);
            }
            // log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    // vmi 退仓
    @SuppressWarnings("unchecked")
    public ReadStatus createStaForVMIReturn(StockTransApplication sta, StaDeliveryInfo stadelivery, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag)
            throws Exception {
        String refSlipCode = sta.getRefSlipCode();
        StockTransApplication returnSta = staDao.findReturnMaxWarehouseOrder(refSlipCode, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
        if (null != returnSta) {
            throw new BusinessException(ErrorCode.RETURN_ORDER_STA_IS_CREATED, new Object[] {refSlipCode});
        }

        BiChannel shop = companyShopDao.getByCode(innerShopCode);
        if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouid);
        if (!StringUtil.isEmpty(shop.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(shop.getOpType())) {
                // 如果有配置需要验证
                boolean b = true;
                String typeName = "";
                if (sta.getType() == StockTransApplicationType.VMI_TRANSFER_RETURN) {
                    b = VmiDefault.checkVmiOpType(shop.getOpType(), VmiOpType.TFX);
                    typeName = "VMI转店退仓";
                }
                if (sta.getType() == StockTransApplicationType.VMI_RETURN) {
                    b = VmiDefault.checkVmiOpType(shop.getOpType(), VmiOpType.RTW);
                    typeName = "VMI退大仓";
                }
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {shop.getName(), typeName});
                }
            }
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiReturnImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");
            List<StaLineCommand> stalinecmds = (List<StaLineCommand>) beans.get("stalinecmds");
            if (stalinecmds != null && stalinecmds.size() > 1000
                    && (shop.getCode().equals(Constants.NIKE_SHOP1_ID) || shop.getCode().equals(Constants.NIKE_SHOP2_ID) || shop.getCode().equals(Constants.NIKE_SHOP3_ID) || shop.getCode().equals(Constants.NIKE_SHOP4_ID))) {
                throw new BusinessException(ErrorCode.NIKE_IMPORT_SIZE);
            }
            List<StaLine> stalines = new ArrayList<StaLine>();

            Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
            Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();


            BigDecimal transactionid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            if (transactionid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
            rs = vmiReturnValidate(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, sta, shop);

            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            InventoryStatus inventoryStatus = null;

            User user = userDao.getByPrimaryKey(userid);
            // save sta
            sta.setToLocation(toLocation);
            sta.setMainWarehouse(operationUnit);
            sta.setCreateTime(new Date());
            sta.setOwner(shop.getCode());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setDeliveryType(TransDeliveryType.ORDINARY);
            // 订单状态与账号关联
            if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userid, operationUnit.getId());
            } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userid, operationUnit.getId());
            }
            sta.setCreator(user);
            sta.setOutboundOperator(user);
            sta.setIsNeedOccupied(true);
            if (reasonCode != null) sta.setMemo(reasonCode);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());

            // VMI退仓反馈
            if (shop != null && shop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {
                    // puma退仓指令单号不能为空
                    // String slipCode = StringUtils.trimWhitespace(sta.getRefSlipCode());
                    // if (StringUtil.isEmpty(slipCode)) {
                    // throw new BusinessException(ErrorCode.RETURN_ORDER_CODE_IS_NULL);
                    // }
                } else {
                    if (true == espritFlag && false == espritTransferFlag) {
                        sta.setRefSlipCode("");
                    } else {
                        String slipCode = "";
                        if (null != sta.getToLocation() && "4046655078762".equals(sta.getToLocation())) {
                            slipCode = generateRtnStaSlipCode();
                        } else {
                            slipCode = vf.generateRtnStaSlipCode(shop.getVmiCode(), sta.getType());
                        }
                        sta.setRefSlipCode(slipCode);
                    }
                    if (true == espritTransferFlag) {
                        sta.setInterfaceType(VmiEsprit.INTERFACETYPE);
                        sta.setFromLocation(ESPDelivery.DELIVERED_FROM_GLN);
                        // 如果页面选择转店，则Esprit品牌只支持转店退仓
                        if (StockTransApplicationType.VMI_TRANSFER_RETURN.getValue() != sta.getType().getValue()) {
                            throw new BusinessException(ErrorCode.VMI_ESPRIT_OUTBOUND_TYPE_ERROR);
                        }
                    }
                }
            }
            sta.setIsNotPacsomsOrder(true);
            staDao.save(sta);
            staDao.flush();
            if (stadelivery != null) {
                StaDeliveryInfo staDeliveryInfo = new StaDeliveryInfo();
                staDeliveryInfo.setId(sta.getId());
                staDeliveryInfo.setCountry("中国");
                staDeliveryInfo.setProvince(stadelivery.getProvince());
                staDeliveryInfo.setCity(stadelivery.getCity());
                staDeliveryInfo.setDistrict(stadelivery.getDistrict());
                staDeliveryInfo.setAddress(stadelivery.getAddress());
                staDeliveryInfo.setReceiver(stadelivery.getReceiver());
                staDeliveryInfo.setTelephone(stadelivery.getTelephone());
                staDeliveryInfo.setMobile(stadelivery.getTelephone());
                staDeliveryInfo.setLpCode(stadelivery.getLpCode());
                staDeliveryInfo.setTransTimeType(TransTimeType.ORDINARY);
                staDeliveryInfoDao.save(staDeliveryInfo);
            }

            if (!StringUtil.isEmpty(stadelivery.getLpCode())) {
                // 获取对应快递单号
                transOlManager.matchingTransNo(sta.getId(), stadelivery.getLpCode(), ouid);
            }
            // save stv
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());

            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userid, tdType, sta.getMainWarehouse().getId(), transactionid.longValue());


            // find stv
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
            // save staline
            Long skuQty = 0L;
            Map<String, StaLine> staMap = new HashMap<String, StaLine>();
            for (final StaLineCommand cmd : stalinecmds) {
                String key = cmd.getSku().getId() + "_" + cmd.getIntInvstatusName();
                inventoryStatus = invmap.get(cmd.getIntInvstatusName());
                StaLine staLine = null;
                if (staMap.get(key) == null) {
                    staLine = new StaLine();
                    staLine.setOwner(shop.getCode());
                    staLine.setQuantity(cmd.getQuantity());
                    skuQty += cmd.getQuantity();
                    staLine.setCompleteQuantity(0L);
                    staLine.setSta(sta);
                    staLine.setInvStatus(inventoryStatus);
                    staLine.setSku(cmd.getSku());
                } else {
                    skuQty += cmd.getQuantity();
                    staLine = staMap.get(key);
                    staLine.setQuantity(staLine.getQuantity() + cmd.getQuantity());
                }
                staMap.put(key, staLine);
                // StaLine staLine = new StaLine();
                // staLine.setOwner(shop.getInnerShopCode());
                // staLine.setQuantity(cmd.getQuantity());
                // skuQty += cmd.getQuantity();
                // staLine.setCompleteQuantity(0L);
                // staLine.setSta(sta);
                // inventoryStatus = invmap.get(cmd.getIntInvstatusName());
                // staLine.setInvStatus(inventoryStatus);
                //
                // staLine.setSku(cmd.getSku());

                // save stvlie
                StvLine stvLine = new StvLine();
                stvLine.setDirection(TransactionDirection.OUTBOUND);
                stvLine.setOwner(sta.getOwner());
                stvLine.setQuantity(cmd.getQuantity());
                stvLine.setSku(cmd.getSku());
                // stvLine.setInvStatus(invmap.get(cmd.getIntInvstatusName()));
                stvLine.setInvStatus(inventoryStatus);
                stvLine.setLocation(cmd.getWarehouseLocation());
                stvLine.setTransactionType(transactionType);
                stvLine.setWarehouse(operationUnit);
                stvLine.setStv(stv);
                stvLine.setDistrict(cmd.getWarehouseLocation().getDistrict());
                stvLineDao.save(stvLine);
            }
            for (String s : staMap.keySet()) {
                StaLine staLine = staMap.get(s);
                staLineDao.save(staLine);
            }
            sta.setSkuQty(skuQty);
            staLineDao.flush();
            stvLineDao.flush();
            // 计算销售可用量KJL
            wareHouseManager.isInventoryNumber(sta.getId());
            // 库存占用
            wareHouseManager.occupyInventoryByStaId(sta.getId(), userid, shop);
            stvLineDao.flush();
            // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

            if (shop != null && shop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
                vf.validateReturnManager(sta);
            }
            VmiInterfaceExt vmiBrandExt = null;
            if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同品牌可根据业务需要传入不同的参数
                    extParam.setSta(sta);
                    extParam.setShop(shop);
                    extParam.setInvStatusMap(invmap);
                    extParam.setStaLineMap(staMap);
                    extParam.setStrType(type);
                }
                vmiBrandExt.createStaForVMIReturnAspect(extParam);
            }
            rs.setMessage(sta.getCode());
            /***** 调整逻辑：前置退仓增量 ************************************/
            incrementInv(sta.getId());
            /***** Edit by KJL 2015-03-17 ***********************************/
            /***** mongoDB库存变更添加逻辑 ******************************/
            sta.setStatus(StockTransApplicationStatus.CREATED);
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            /***** mongoDB库存变更添加逻辑 ******************************/
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus creStaForVMIReturnEs(String owner, String lpCode, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiEspritReturnImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");
            Map<String, Map<String, Long>> mapStore = new HashMap<String, Map<String, Long>>();
            List<StaLineCommand> stalinecmds2 = (List<StaLineCommand>) beans.get("stalinecmds");
            for (StaLineCommand line : stalinecmds2) {
                line.setIntInvstatusName("良品");
                String store = line.getmName() + "-" + line.getmCode();
                // 商品为一维度
                Map<String, Long> mapSKu = new HashMap<String, Long>();
                // mapStore 是否是该店铺下的
                Map<String, Long> mapSKu2 = mapStore.get(store);
                if (mapSKu2 == null) {
                    // 已门店名称门店编码唯一维度 SKU条码/编码唯一维度
                    mapSKu.put(line.getSkuCode(), line.getQuantity());
                    mapStore.put(store, mapSKu);
                } else {
                    Long num = mapSKu2.get(line.getSkuCode());
                    if (num == null) {
                        mapSKu2.put(line.getSkuCode(), line.getQuantity());
                    } else {
                        mapSKu2.put(line.getSkuCode(), num + line.getQuantity());
                    }
                }
            }
            // 验证门店信息是否存在 EspritStore.findEspritEn
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, Map<String, Long>> en : mapStore.entrySet()) {
                // System.out.println(en.getKey() + "," + en.getValue());
                String[] codes = en.getKey().split("-");
                String name = codes[0];
                String code = codes[1];
                EspritStoreCommand es = espritStoreDao.findEspritEn(name, code, null, null, null, new BeanPropertyRowMapperExt<EspritStoreCommand>(EspritStoreCommand.class));
                if (es == null) {
                    sb.append("门店名称：" + name + "," + "门店编码:" + code + ";");
                }
            }
            if (!StringUtil.isEmpty(sb.toString())) {
                sb.append("门店信息不存在");
                throw new BusinessException(sb.toString());
            }

            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            // OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouid);
            if (!StringUtil.isEmpty(shop.getVmiCode())) {
                // 验证品牌操作权限
                if (!StringUtil.isEmpty(shop.getOpType())) {
                    // 如果有配置需要验证
                    boolean b = true;
                    String typeName = "";
                    b = VmiDefault.checkVmiOpType(shop.getOpType(), VmiOpType.TFX);
                    typeName = "VMI转店退仓";
                    if (b) {
                        // 无操作权限抛出异常
                        throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {shop.getName(), typeName});
                    }
                }
            }


            // 检验所有文件商品数量库存是满足 S
            StockTransApplication s = new StockTransApplication();
            s.setType(StockTransApplicationType.valueOf(102));// 转店退仓
            s.setFreightMode(FreightMode.valueOf(20));// 物流配送
            BigDecimal transactionid = transactionTypeDao.findByStaType(s.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            if (transactionid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
            Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
            Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();
            List<StaLine> stalines = new ArrayList<StaLine>();
            rs = vmiReturnValidate2(rs, stalinecmds2, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, s);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            // ///////////////////////////////// E


            // 封装sta sta物流信息
            for (Map.Entry<String, Map<String, Long>> en : mapStore.entrySet()) {
                String[] codes = en.getKey().split("-");
                String name = codes[0];
                String code = codes[1];
                EspritStoreCommand es = espritStoreDao.findEspritEn(name, code, null, null, null, new BeanPropertyRowMapperExt<EspritStoreCommand>(EspritStoreCommand.class));
                if (es == null) {
                    throw new BusinessException("其中门店信息不存在");
                }
                if (es.getAddress() == null || es.getProvince() == null || es.getCity() == null || es.getDistrict() == null || es.getContacts() == null || es.getTelephone() == null) {
                    throw new BusinessException("门店名称:" + name + ",信息维护不全");
                }

                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.valueOf(102));// 转店退仓
                // sta.setSlipCode1(this.slipCode1);
                // sta.setActivitySource(activitySource);
                // sta.setImperfectType(this.imperfectType);
                // sta.setRefSlipCode(orderCode);
                sta.setFreightMode(FreightMode.valueOf(20));// 物流配送

                sta.setToLocation(es.getGln());// 门店GLN
                sta.setCtCode(es.getCode());// 门店code

                StaDeliveryInfo stadelivery = new StaDeliveryInfo();
                stadelivery.setProvince(es.getProvince());
                stadelivery.setCity(es.getCity());
                stadelivery.setDistrict(es.getDistrict());
                stadelivery.setAddress(es.getAddress());
                stadelivery.setReceiver(es.getContacts());
                stadelivery.setTelephone(es.getTelephone());
                stadelivery.setLpCode(lpCode);
                // 封装
                List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
                Map<String, Long> skus = en.getValue();
                for (Map.Entry<String, Long> sku : skus.entrySet()) {
                    StaLineCommand staLineCommand = new StaLineCommand();
                    // System.out.println(sku.getKey() + "," + sku.getValue());
                    staLineCommand.setSkuCode(sku.getKey());
                    staLineCommand.setQuantity(sku.getValue());
                    staLineCommand.setIntInvstatusName("良品");
                    stalinecmds.add(staLineCommand);
                }
                // 基于店铺进行店铺退仓操作line
                rs = creStaForVMIReturnEsToLine(stalinecmds, rs, type, shop, sta, stadelivery, file, innerShopCode, toLocation, ouid, cmpOuid, userid, reasonCode, espritFlag, espritTransferFlag, false, null, null);
                if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    return rs;
                }
                // 非一个事物获取运单号
                // if (!StringUtil.isEmpty(stadelivery.getLpCode())) {
                // try {// 需求不校验是否获取运单号
                // // 获取对应快递单号
                // transOlManager.matchingTransNo(sta.getId(), stadelivery.getLpCode(), ouid);
                // } catch (BusinessException e) {
                // System.out.println("hahah");
                // log.error("espritGetNo:" + sta.getId() + "," + stadelivery.getLpCode());
                // }
                // }

            }



        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("creStaForVMIReturnEs Exception:" + lpCode, e);
            }
            // log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }


    // vmi esprit 退仓2
    public ReadStatus creStaForVMIReturnEsToLine(List<StaLineCommand> stalinecmds, ReadStatus rs, String type, BiChannel shop, StockTransApplication sta, StaDeliveryInfo stadelivery, File file, String innerShopCode, String toLocation, Long ouid,
            Long cmpOuid, Long userid, String reasonCode, boolean espritFlag, boolean espritTransferFlag, boolean isNikeReturn, StaLfCommand staLfCommand, Map<String, StaLfCommand> mapStaUpcEn) throws Exception {
        try {
            BigDecimal transactionid = transactionTypeDao.findByStaType(sta.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            if (transactionid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
            Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
            Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();
            List<StaLine> stalines = new ArrayList<StaLine>();
            List<StaLineCommand> stalinecmds2 = new ArrayList<StaLineCommand>();
            stalinecmds2.addAll(stalinecmds);
            // 按店铺校验商品数量
            if (shop.getIsPartOutbound() != null && shop.getIsPartOutbound()) {
                rs = vmiReturnValidate(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, sta, shop);
            } else {
                rs = vmiReturnValidate2(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, sta);
            }
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }

            OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouid);
            User user = null;
            if (userid != null) {
                user = userDao.getByPrimaryKey(userid);
            }
            // sta.setToLocation(toLocation);
            sta.setMainWarehouse(operationUnit);
            sta.setCreateTime(new Date());
            sta.setOwner(shop.getCode());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setDeliveryType(TransDeliveryType.ORDINARY);
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));// 设置作业单
            // 订单状态与账号关联
            if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userid, operationUnit.getId());
            } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
                whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userid, operationUnit.getId());
            }
            if (!isNikeReturn) {
                sta.setIsEsprit(1);
            }
            sta.setCreator(user);
            sta.setOutboundOperator(user);
            sta.setIsNeedOccupied(true);
            if (reasonCode != null) sta.setMemo(reasonCode);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            if (!isNikeReturn) {
                // VMI退仓反馈
                if (shop != null && shop.getVmiCode() != null) {
                    VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
                    if (true == espritFlag && false == espritTransferFlag) {
                        sta.setRefSlipCode("");
                    } else {
                        String slipCode = vf.generateRtnStaSlipCode(shop.getVmiCode(), sta.getType());
                        sta.setRefSlipCode(slipCode);
                    }
                    if (true == espritTransferFlag) {
                        sta.setInterfaceType(VmiEsprit.INTERFACETYPE);
                        sta.setFromLocation(ESPDelivery.DELIVERED_FROM_GLN);
                        // 如果页面选择转店，则Esprit品牌只支持转店退仓
                        if (StockTransApplicationType.VMI_TRANSFER_RETURN.getValue() != sta.getType().getValue()) {
                            throw new BusinessException(ErrorCode.VMI_ESPRIT_OUTBOUND_TYPE_ERROR);
                        }
                    }
                }
            }

            sta.setIsNotPacsomsOrder(true);
            staDao.save(sta);
            staDao.flush();

            if (stadelivery != null) {
                StaDeliveryInfo staDeliveryInfo = new StaDeliveryInfo();
                staDeliveryInfo.setId(sta.getId());
                staDeliveryInfo.setCountry("中国");
                staDeliveryInfo.setProvince(stadelivery.getProvince());
                staDeliveryInfo.setCity(stadelivery.getCity());
                staDeliveryInfo.setDistrict(stadelivery.getDistrict());
                staDeliveryInfo.setAddress(stadelivery.getAddress());
                staDeliveryInfo.setReceiver(stadelivery.getReceiver());
                staDeliveryInfo.setTelephone(stadelivery.getTelephone());
                staDeliveryInfo.setMobile(stadelivery.getTelephone());
                staDeliveryInfo.setLpCode(stadelivery.getLpCode());
                staDeliveryInfo.setTransTimeType(TransTimeType.ORDINARY);
                staDeliveryInfoDao.save(staDeliveryInfo);
            }

            // 获取运单号
            if (!StringUtil.isEmpty(stadelivery.getLpCode())) {
                try {// 需求不校验是否获取运单号
                     // 获取对应快递单号
                    transOlManager.matchingTransNoEs(sta.getId(), stadelivery.getLpCode(), ouid);
                } catch (Throwable e) {
                    log.error("espritGetNo:" + sta.getId() + "," + stadelivery.getLpCode());
                }

            }

            // save stv
            int tdType = TransactionDirection.OUTBOUND.getValue();
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());


            stvDao.createStv(code, sta.getOwner(), sta.getId(), StockTransVoucherStatus.CREATED.getValue(), userid, tdType, sta.getMainWarehouse().getId(), transactionid.longValue());
            // find stv
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
            // save staline
            Long skuQty = 0L;
            Map<String, StaLine> staMap = new HashMap<String, StaLine>();

            InventoryStatus inventoryStatus = null;
            //
            for (final StaLineCommand cmd : stalinecmds2) {
                String key = cmd.getSku().getId() + "_" + cmd.getIntInvstatusName();
                inventoryStatus = invmap.get(cmd.getIntInvstatusName());
                StaLine staLine = null;
                if (staMap.get(key) == null) {
                    staLine = new StaLine();
                    staLine.setOwner(shop.getCode());
                    staLine.setQuantity(cmd.getQuantity());
                    skuQty += cmd.getQuantity();
                    staLine.setCompleteQuantity(0L);
                    staLine.setSta(sta);
                    staLine.setInvStatus(inventoryStatus);
                    staLine.setSku(cmd.getSku());
                    staLine.setExtMemo(cmd.getExtMemo());
                } else {
                    skuQty += cmd.getQuantity();
                    staLine = staMap.get(key);
                    staLine.setQuantity(staLine.getQuantity() + cmd.getQuantity());
                }
                staMap.put(key, staLine);
            }

            for (final StaLineCommand cmd : stalinecmds) {

                // StaLine staLine = new StaLine();
                // staLine.setOwner(shop.getInnerShopCode());
                // staLine.setQuantity(cmd.getQuantity());
                // skuQty += cmd.getQuantity();
                // staLine.setCompleteQuantity(0L);
                // staLine.setSta(sta);
                // inventoryStatus = invmap.get(cmd.getIntInvstatusName());
                // staLine.setInvStatus(inventoryStatus);
                //
                // staLine.setSku(cmd.getSku());

                // save stvlie
                StvLine stvLine = new StvLine();
                stvLine.setDirection(TransactionDirection.OUTBOUND);
                stvLine.setOwner(sta.getOwner());
                stvLine.setQuantity(cmd.getQuantity());
                stvLine.setSku(cmd.getSku());
                // stvLine.setInvStatus(invmap.get(cmd.getIntInvstatusName()));
                stvLine.setInvStatus(inventoryStatus);
                stvLine.setLocation(cmd.getWarehouseLocation());
                stvLine.setTransactionType(transactionType);
                stvLine.setWarehouse(operationUnit);
                stvLine.setStv(stv);
                stvLine.setDistrict(cmd.getWarehouseLocation().getDistrict());
                stvLineDao.save(stvLine);
            }
            for (String s : staMap.keySet()) {
                StaLine staLine = staMap.get(s);
                staLineDao.save(staLine);
            }
            sta.setSkuQty(skuQty);
            staLineDao.flush();
            stvLineDao.flush();

            if (isNikeReturn) {// nikeCRW定制 插入T_WH_STA_LF,t_wh_sta_line_lf 中间表
                StaLf staLf = new StaLf();
                staLf.setAddress1(staLfCommand.getAddress1());
                staLf.setAddress2(staLfCommand.getAddress2());
                staLf.setAddress3(staLfCommand.getAddress3());
                staLf.setAddress4(staLfCommand.getAddress4());
                staLf.setCity(staLfCommand.getCity());
                staLf.setCompanyName(staLfCommand.getCompanyName());
                staLf.setCrd(null);// crd时间
                staLf.setDivisionCode(staLfCommand.getDivisionCode());
                if ("10".equals(staLfCommand.getDivisionCode())) {
                    staLf.setDivisionCodeTranslation("服装");
                } else if ("20".equals(staLfCommand.getDivisionCode())) {
                    staLf.setDivisionCodeTranslation("鞋");
                } else if ("30".equals(staLfCommand.getDivisionCode())) {
                    staLf.setDivisionCodeTranslation("装备");
                }
                staLf.setNfsStoreCode(staLfCommand.getNfsStoreCode());
                staLf.setNikePo(staLfCommand.getNikePo());
                staLf.setOrderType(staLfCommand.getOrderType());
                staLf.setOuId(ouid);
                staLf.setPackSlipNo(staLfCommand.getPackSlipNo());
                staLf.setStaId(sta.getId());
                staLf.setTransportMode(staLfCommand.getTransportMode());
                staLf.setTransportPra(staLfCommand.getTransportPra());
                staLf.setVasCode(staLfCommand.getVasCode());
                staLf.setZip(staLfCommand.getZip());
                staLf.setIsMoreWh(staLfCommand.getIsMoreWh());
                staLf.setTransMethod(staLfCommand.getTransMethod());
                if (staLfCommand.getPlantime() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = df.parse(staLfCommand.getPlantime());
                    staLf.setCrd(StringUtil.getLfCrd(date, Integer.valueOf(staLfCommand.getTransportPra()), staLfCommand.getNikePo()));
                }
                staLfDao.save(staLf);
                List<StaLineCommand> staLineList = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                for (StaLineCommand staLineCommand : staLineList) {
                    StaLfCommand staLfCom = null;
                    String vas = null;
                    if (mapStaUpcEn != null) {
                        staLfCom = mapStaUpcEn.get(sta.getRefSlipCode() + ";" + staLineCommand.getBarCode());
                        vas = staLfCom == null ? "" : staLfCom.getSkuVas();
                    } else if (!StringUtil.isEmpty(staLineCommand.getExtMemo())) {
                        String memo = staLineCommand.getExtMemo();
                        StaLineLf sll = com.baozun.utilities.json.JsonUtil.readValue(memo, StaLineLf.class);
                        if (sll != null) {
                            vas = sll.getVas();
                        }
                    }
                    StaLineLf staLineLf = new StaLineLf();
                    staLineLf.setOuId(ouid);
                    staLineLf.setSkuId(staLineCommand.getSkuId());
                    staLineLf.setStaLineId(staLineCommand.getId());
                    staLineLf.setVas(vas);
                    staLineLf.setStaId(sta.getId());
                    staLineLfDao.save(staLineLf);
                }

            }


            // 库存占用
            if (shop.getIsPartOutbound() != null && shop.getIsPartOutbound()) {
                // 允许部分占用
                wareHouseManager.occupyInventoryByStaIdPartial(sta.getId(), null, shop, operationUnit, transactionType, stv, true);
            } else {
                // 计算销售可用量KJL
                wareHouseManager.isInventoryNumber(sta.getId());
                wareHouseManager.occupyInventoryByStaId(sta.getId(), userid, shop);
            }
            stvLineDao.flush();
            // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
            wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

            if (shop != null && shop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(shop.getVmiCode());
                vf.validateReturnManager(sta);
            }
            VmiInterfaceExt vmiBrandExt = null;
            if (null != shop.getIsVmiExt() && true == shop.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
            }
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                // if (Constants.PUMA_VMI_CODE.equals(shop.getVmiCode())) {// 不同品牌可根据业务需要传入不同的参数
                // extParam.setSta(sta);
                // extParam.setShop(shop);
                // extParam.setInvStatusMap(invmap);
                // extParam.setStaLineMap(staMap);
                // extParam.setStrType(type2);
                // }
                vmiBrandExt.createStaForVMIReturnAspect(extParam);
            }

            /***** 调整逻辑：前置退仓增量 ************************************/
            incrementInv(sta.getId());
            /***** Edit by KJL 2015-03-17 ***********************************/
            /***** mongoDB库存变更添加逻辑 ******************************/
            sta.setStatus(StockTransApplicationStatus.CREATED);
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            /***** mongoDB库存变更添加逻辑 ******************************/
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } catch (Exception e) {
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("creStaForVMIReturnEsToLine Exception:" + sta.getCode(), e);
            }
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }


    public void incrementInv(Long id) {
        stLogDao.insertIncrementInv(id);
    }

    @SuppressWarnings({"unchecked"})
    @Transactional
    public ReadStatus staImportForPurchase(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            readStatus = null;
            if (sta.getType().getValue() == StockTransApplicationType.INBOUND_SETTLEMENT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_CONSIGNMENT.getValue()
                    || sta.getType().getValue() == StockTransApplicationType.INBOUND_GIFT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_MOBILE.getValue()
                    || sta.getType().getValue() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue()) {
                readStatus = staReaderForPredefined.readAll(new FileInputStream(staFile), beans);
            } else {
                readStatus = staReaderForPurchase.readAll(new FileInputStream(staFile), beans);
            }
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            if (sta.getType().getValue() == StockTransApplicationType.INBOUND_SETTLEMENT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_CONSIGNMENT.getValue()
                    || sta.getType().getValue() == StockTransApplicationType.INBOUND_GIFT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_MOBILE.getValue()
                    || sta.getType().getValue() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue()) {
                staImportForPredefinedValidate(readStatus, sta, beans);
            } else {
                staImportForPurchaseValidate(readStatus, sta, beans);
            }
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }

            List<StvLine> list = new ArrayList<StvLine>();
            List<StvLine> sheet1 = (List<StvLine>) beans.get("stvLines");
            List<StvLine> sheet2 = (List<StvLine>) beans.get("stvLines2");
            list.addAll(sheet1);
            list.addAll(sheet2);
            StockTransVoucher stvCreated = wareHouseManager.purchaseReceiveStep1(sta, list, null, creator, null, null, false);
            if (stv != null) {
                log.info("Esp t4 po:" + sta.getRefSlipCode() + " set invoiceNumber:" + stv.getInvoiceNumber() + " dutyPercentage:" + stv.getDutyPercentage() + " miscFeePercentage" + stv.getMiscFeePercentage());
                stvCreated.setInvoiceNumber(stv.getInvoiceNumber());
                stvCreated.setDutyPercentage(stv.getDutyPercentage());
                stvCreated.setMiscFeePercentage(stv.getMiscFeePercentage());
            }
            if (log.isDebugEnabled()) {
                log.debug("stv created : {}", stvCreated);
            }
            wareHouseManager.purchaseReceiveStep2(stvCreated.getId(), list, false, creator, false, true);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        return readStatus;
    }

    @Transactional
    public ReadStatus staImportForRepair(Long staId, StockTransVoucher stv, File staFile, User creator, InboundStoreMode mode) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            readStatus = null;
            readStatus = staReaderForRepair.readAll(new FileInputStream(staFile), beans);
            // 是否错误
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StvLine> stvLineList = staImportForrepairValidate(readStatus, sta, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            Map<String, StvLine> tempMap = new HashMap<String, StvLine>();
            List<StvLine> list = new ArrayList<StvLine>();
            // 合并重复行
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
            for (int i = 0; i < stvLineList.size(); i++) {
                StvLine l = stvLineList.get(i);
                String key = l.getSku().getId() + "_" + l.getLocation().getCode() + "_" + l.getInvStatus().getId() + "_" + (!InboundStoreMode.SHELF_MANAGEMENT.equals(l.getSku().getStoremode()) ? "" : formatDate.format(l.getExpireDate()));
                if (tempMap.containsKey(key)) {
                    StvLine temp = tempMap.get(key);
                    temp.setQuantity(temp.getQuantity() + l.getQuantity());
                    Boolean isSn = temp.getSku().getIsSnSku();
                    if (isSn != null && isSn) {
                        temp.setSns(temp.getSns() + "," + l.getSns());
                    }
                } else {
                    list.add(l);
                    tempMap.put(key, l);
                }
            }
            StockTransVoucher stvCreated = wareHouseManager.purchaseReceiveStep1(sta, list, null, creator, null, null, false);
            if (log.isDebugEnabled()) {
                log.debug("stv created : {}", stvCreated);
            }
            wareHouseManager.purchaseReceiveStep2(stvCreated.getId(), stvLineDao.findStvLineListByStvId(stvCreated.getId()), false, creator, false, true);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        return readStatus;
    }

    @SuppressWarnings("unchecked")
    // 收货-数量确认导入
    public Map<String, Object> inboundAmountConfirmImport(Long staId, File staFile, User creator) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            rs = inboundReceiveAmountConfirmReader.readAll(new FileInputStream(staFile), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                result.put("readStatus", rs);
                return result;
            }
            String type = (String) beans.get("type");
            List<StvLineCommand> sheet1 = (List<StvLineCommand>) beans.get("stvline1"); // 普通商品
            List<StvLineCommand> sheet2 = (List<StvLineCommand>) beans.get("stvline2"); // 有sn号商品
            List<StvLine> list = new ArrayList<StvLine>();
            Map<String, Long> qtyMap = new HashMap<String, Long>();
            qtyMap.put("quantity", 0L);
            rs = inboundAmountConfirmValidate(rs, sheet1, sheet2, list, qtyMap, type, staId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                result.put("readStatus", rs);
                return result;
            }
            StockTransVoucher stvCreated = wareHouseManager.purchaseReceiveStep1(sta, list, null, creator, null, true, false);
            result.put("quantity", qtyMap.get("quantity"));
            if (log.isDebugEnabled()) {
                log.debug("stv created : {}", stvCreated);
            }
            result.put("stv", stvCreated);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        return result;
    }

    private ReadStatus inboundAmountConfirmValidate(ReadStatus rs, List<StvLineCommand> sheet1, List<StvLineCommand> sheet2, List<StvLine> stvlines, Map<String, Long> qtyMap, String type, Long staId) {
        int intType;
        final ExcelSheet sheetOut = inboundReceiveAmountConfirmReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();

        if (type == null) {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.SNS_SKU_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // sheet1 普通商品
        validateSkuForInbound(rs, sheet1, stvlines, qtyMap, intType, staId, false);
        // sheet2 SN号商品
        validateSkuForInbound(rs, sheet2, stvlines, qtyMap, intType, staId, true);
        return rs;
    }

    private ReadStatus validateSkuForInbound(ReadStatus rs, List<StvLineCommand> sheet, List<StvLine> stvlines, Map<String, Long> qtyMap, int intType, Long staId, boolean isSn) {
        Sku sku = null;
        List<StaLineCommand> stalines = staLineDao.findStaLineListByStaIdSql(staId, false, new Sort[] {new Sort("sku.BAR_CODE asc")}, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        Map<String, StaLineCommand> map = new HashMap<String, StaLineCommand>();
        for (StaLineCommand staLine : stalines) {
            if (intType == 1) {// barcode
                String key = staLine.getBarCode();
                map.put(key, staLine);
            } else if (intType == 0) { // skucode
                String key = staLine.getSkuCode();
                map.put(key, staLine);
            }
        }
        List<String> errorSns = new ArrayList<String>();
        Map<String, StvLineCommand> cache = new HashMap<String, StvLineCommand>();
        Map<String, String> snmap = new HashMap<String, String>();
        String key = null;
        Long quantity = 0L;
        for (StvLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            if (isSn) {// sn号商品数量合并 && 校验重复SN号
                key = cmd.getSkuCode();
                quantity += 1;
                if (cache.get(key) == null) {
                    cmd.setQuantity(1L);
                    cache.put(key, cmd);
                } else {
                    StvLineCommand stvlineCmd = cache.get(key);
                    stvlineCmd.setQuantity(stvlineCmd.getQuantity() + 1);
                    String sns = stvlineCmd.getSns() + "," + cmd.getSns();
                    stvlineCmd.setSns(sns);
                }
                // SN号重复校验
                String snKey = cmd.getSns();
                if (snmap.get(snKey) == null) {
                    snmap.put(snKey, snKey);
                } else {
                    errorSns.add(snKey);
                }
            } else { // 非SN号商品 合并重复行
                quantity += cmd.getQuantity();
                key = cmd.getSkuCode();
                if (cache.get(key) == null) {
                    cache.put(key, cmd);
                } else {
                    StvLineCommand stvlineCmd = cache.get(key);
                    stvlineCmd.setQuantity(stvlineCmd.getQuantity() + cmd.getQuantity());
                }
            }
        }
        qtyMap.put("quantity", qtyMap.get("quantity") + quantity);
        if (!errorSns.isEmpty() && errorSns.size() > 0) {
            rs.setStatus(-2);
            rs.addException(new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {isSn ? SHEET_1 : SHEET_0, errorSns.toString()}));
        }
        sheet.clear();
        sheet.addAll(cache.values());

        final ExcelSheet sheets = inboundReceiveAmountConfirmReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        Long customerId = null;
        if (wh != null && wh.getCustomer() != null) {
            customerId = wh.getCustomer().getId();
        }

        for (StvLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            if (intType == 1) {// barcode
                sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                if (sku == null) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {isSn ? SHEET_1 : SHEET_0, currCell, cmd.getBarCode()}));
                } else
                    cmd.setSku(sku);
            } else if (intType == 0) { // skucode
                sku = skuDao.getByCode(cmd.getSkuCode());
                if (sku == null) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {isSn ? SHEET_1 : SHEET_0, currCell, cmd.getSkuCode()}));
                } else
                    cmd.setSku(sku);
            }
            StaLineCommand staLine = map.get(cmd.getSkuCode());
            if (staLine != null) {
                Long Qty = staLine.getQuantity() == null ? 0 : staLine.getQuantity();
                Long comQty = staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity();
                Long unComQty = Qty - comQty;
                if (cmd.getQuantity() > unComQty) { // 大于未完成的数量 - exception
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                    rs.setStatus(-2); // sheet{0} 单元格 {1} 商品 [{2}]
                    // 当前收货数量为[{3}]大于系统未完成收货量为[{4}]
                    rs.addException(new BusinessException(ErrorCode.INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE, new Object[] {isSn ? SHEET_1 : SHEET_0, currCell, cmd.getSkuCode(), cmd.getQuantity(), unComQty}));
                } else {
                    if (staLine.getIsSnSku().equals(1) && cmd.getSns() == null) {
                        log.debug("staLine.getIsSnSku().equals(1) && cmd.getSns() == null");
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE_NEED_SN, new Object[] {SHEET_0, currCell, cmd.getSkuCode(), unComQty}));
                    }
                    StvLine stvLine = new StvLine();
                    stvLine.setSku(sku);
                    stvLine.setQuantity(cmd.getQuantity());
                    log.debug("==================> cmd.getSns() {} ", cmd.getSns());
                    stvLine.setSns(cmd.getSns());
                    stvlines.add(stvLine);
                }
            } else {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.INBOUND_IMPORT_AMOUNT_CONFIRM_RECEIVE_NOT_FOUND, new Object[] {isSn ? SHEET_1 : SHEET_0, currCell, cmd.getSkuCode()}));
            }
            offsetRow++;
        }
        return rs;
    }


    @SuppressWarnings("unchecked")
    public ReadStatus predefinedStaImport(File file, String memo, String owner, Long invStatus, Long transactionType, Long ouid, Long userId) {
        log.debug("===========predefinedStaImport============");
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            ReadStatus rs = predefinedStaReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            // 验证
            String type = (String) beans.get("type");
            final ExcelSheet sheetOut = predefinedStaReader.getDefinition().getExcelSheets().get(0);
            List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();
            int intType = 0;
            if ("SKU条码".equals(type)) {
                intType = 1;
            } else if ("SKU编码".equals(type)) {
                intType = 0;
            } else {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            }
            TransactionType tType = null;
            if (transactionType == null) {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow() + 1, blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_TRANSACTION_TYPE_NOT_FOUND, new Object[] {SHEET_0, strCell, transactionType}));
            }
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            } else if (StockTransApplicationType.INBOUND_CONSIGNMENT.getValue() == transactionType) {
                tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_CONSIGNMENT_INBOUND);
            } else if (StockTransApplicationType.INBOUND_SETTLEMENT.getValue() == transactionType) {
                tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_SETTLEMENT_INBOUND);
            } else if (StockTransApplicationType.INBOUND_GIFT.getValue() == transactionType) {
                tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_GIFT_INBOUND);
            } else if (StockTransApplicationType.INBOUND_MOBILE.getValue() == transactionType) {
                tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_MOBILE_INBOUND);
            }
            if (tType == null
                    || (!Constants.TRANSACTION_TYPE_SETTLEMENT_INBOUND.equals(tType.getCode()) && !Constants.TRANSACTION_TYPE_CONSIGNMENT_INBOUND.equals(tType.getCode()) && !Constants.TRANSACTION_TYPE_GIFT_INBOUND.equals(tType.getCode()) && !Constants.TRANSACTION_TYPE_MOBILE_INBOUND
                            .equals(tType.getCode()))) {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow() + 1, blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_TRANSACTION_TYPE_NOT_FOUND, new Object[] {SHEET_0, strCell, transactionType}));
            }
            if (invStatus == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INBOUND_PDA_NOT_INV_STATUS));
            }
            InventoryStatus status = inventoryStatusDao.getByPrimaryKey(invStatus);
            if (status == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INBOUND_PDA_NOT_INV_STATUS));
            }
            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null) {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow() + 2, blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_0, strCell, owner}));
            }
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            List<StaLineCommand> stalList = (List<StaLineCommand>) beans.get("inv");
            List<StaLineCommand> staLineTemp = new ArrayList<StaLineCommand>();
            final ExcelSheet sheet = predefinedStaReader.getDefinition().getExcelSheets().get(0);
            String strCell = ExcelUtil.getCellIndex(sheet.getExcelBlocks().get(0).getStartRow(), sheet.getExcelBlocks().get(0).getStartCol());
            Map<String, Sku> cacheSku = new HashMap<String, Sku>();
            int offsetRow = 0;
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouid);
            for (StaLineCommand cmd : stalList) {
                Sku sku = cacheSku.get(cmd.getSkuCode());
                if (sku == null) {
                    if (intType == 1) {
                        // 条码
                        sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                        if (sku == null) {
                            SkuBarcode addcode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                            if (addcode != null) {
                                sku = addcode.getSku();
                            }
                        }
                        if (sku == null) {
                            String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.PREDEFINED_EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                        } else {
                            if (!sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                // 如果是结算经销入库，导入商品：结算经销或混合经营模式（代销+结算经销）或代销;
                                /*
                                 * if (tType.getCode().equals("INBOUND_SETTLEMENT")) { if
                                 * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                 * rs.setStatus(-1); rs.addException(new
                                 * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new
                                 * Object[] {sku.getBarCode(), sku.getCode()})); } } //
                                 * 如果是代销入库，导入商品：结算经销或混合经营模式（代销+结算经销）或代销; if
                                 * (tType.getCode().equals("INBOUND_CONSIGNMENT")) { if
                                 * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                 * rs.setStatus(-1); rs.addException(new
                                 * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new
                                 * Object[] {sku.getBarCode(), sku.getCode()})); } }
                                 */
                            } else {
                                rs.setStatus(-1);
                                rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                            }
                            cmd.setSku(sku);
                            cacheSku.put(cmd.getSkuCode(), sku);
                        }
                    } else {
                        sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                        if (sku == null) {
                            String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.PREDEFINED_EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                        } else {
                            if (!sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                /*
                                 * // 如果是结算经销入库，导入商品:结算经销或混合经营模式（代销+结算经销）或代销； if
                                 * (tType.getCode().equals("INBOUND_SETTLEMENT")) { if
                                 * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                 * rs.setStatus(-1); rs.addException(new
                                 * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new
                                 * Object[] {sku.getBarCode(), sku.getCode()})); } } // 如果是代销入库，导入商品
                                 * :代销或混合经营模式（代销+结算经销）或结算经销； if
                                 * (tType.getCode().equals("INBOUND_CONSIGNMENT")) { if
                                 * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                 * rs.setStatus(-1); rs.addException(new
                                 * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new
                                 * Object[] {sku.getBarCode(), sku.getCode()})); } }
                                 */
                            } else {
                                rs.setStatus(-1);
                                rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                            }
                            cmd.setSku(sku);
                            cacheSku.put(cmd.getSkuCode(), sku);
                        }
                    }
                } else {
                    if (!sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                        /*
                         * // 如果是结算经销入库，导入商品只能是结算经销商品 if
                         * (tType.getCode().equals("INBOUND_SETTLEMENT")) { if
                         * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) { rs.setStatus(-1);
                         * rs.addException(new
                         * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[]
                         * {sku.getBarCode(), sku.getCode()})); } } // 如果是代销入库，只能导入代销商品 if
                         * (tType.getCode().equals("INBOUND_CONSIGNMENT")) { if
                         * (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) { rs.setStatus(-1);
                         * rs.addException(new
                         * BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[]
                         * {sku.getBarCode(), sku.getCode()})); } }
                         */
                    } else {
                        rs.setStatus(-1);
                        rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                    }
                    cmd.setSku(sku);
                }
                cmd.setInvStatus(status);
                if (cmd.getQuantity() == null || cmd.getQuantity() < 1) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.BETWENLIBARY_STA_QTY_IS_NOT_NULL, new Object[] {SHEET_0, currCell}));
                }
                if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                    // 同商品同状态 合并
                    boolean bool = true;
                    for (StaLineCommand temp : staLineTemp) {
                        if (sku != null && status != null && temp.getSku().getId().equals(sku.getId()) && temp.getInvStatus().getId().equals(status.getId())) {
                            bool = false;
                            temp.setQuantity(temp.getQuantity() + cmd.getQuantity());
                            break;
                        }
                    }
                    if (sku != null && status != null && bool) {
                        staLineTemp.add(cmd);
                    }
                }
                offsetRow++;
            }
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            User user = userDao.getByPrimaryKey(userId);
            try {
                wareHouseManager.createPredefinedIn(true, transactionType, user, ou, owner, memo, staLineTemp);
            } catch (Exception e) {
                throw e;
            }
            return rs;
        } catch (Exception e) {
            log.error("", e);
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
            }
        }
    }

    private ReadStatus validatetransitInner(ReadStatus rs, String type, List<InventoryCommand> outInv, List<InventoryCommand> inInv, TransactionType tType, Long ouId) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        final ExcelSheet sheetOut = transitInnerReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();
        int intType;
        Map<String, Sku> cacheSku = new HashMap<String, Sku>();

        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // 校验出库
        String strCell = ExcelUtil.getCellIndex(blocksOut.get(0).getStartRow(), blocksOut.get(0).getStartCol());
        int offsetRow = 0;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        for (int i = 0; i < outInv.size(); i++) {
            InventoryCommand cmd = outInv.get(i);
            Sku sku = cacheSku.get(cmd.getBarCode());
            if (sku == null) {
                if (intType == 1) {
                    sku = skuDao.getByBarcode(cmd.getBarCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(cmd.getBarCode(), customerId);
                        if (addSkuCode != null) {
                            sku = addSkuCode.getSku();
                            log.debug("sku find by add sku barcode {}", cmd.getBarCode());
                        }
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getBarCode()}));
                    } else {
                        cacheSku.put(cmd.getBarCode(), sku);
                    }
                } else if (intType == 0) {
                    sku = skuDao.getByCodeAndCustomer(cmd.getBarCode(), customerId);
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getBarCode()}));
                    } else {
                        cacheSku.put(cmd.getBarCode(), sku);
                    }
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            }
            BiChannel shop = companyShopDao.getByName(cmd.getInvOwner());
            if (shop == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getInvOwner()}));
            }
            WarehouseLocation loc = warehouseLocationDao.findLocationByCode(cmd.getLocationCode(), ouId);
            if (loc == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getLocationCode()}));
            } else {
                cmd.setLocation(loc);
            }

            InventoryStatus invs = inventoryStatusDao.findByNameUnionSystem(cmd.getInvStatusName(), ou.getParentUnit().getParentUnit().getId());
            if (invs == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 4);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getInvStatusName()}));
            } else {
                cmd.setStatus(invs);
            }
            if (cmd.getQuantity() == null || cmd.getQuantity() < 1) {
                // outInv.remove(i--);
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 3);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_QUANTITY_MINUS, new Object[] {SHEET_0, currCell}));
            }
            offsetRow++;
        }
        // 检验入库
        strCell = ExcelUtil.getCellIndex(SHEET_INBOUND_START_ROW, SHEET_INBOUND_START_COL);
        offsetRow = 0;
        // 单批隔离
        // int storeMode = InboundStoreMode.RESPECTIVE.getValue();
        for (int i = 0; i < inInv.size(); i++) {
            InventoryCommand cmd = inInv.get(i);
            Sku sku = cacheSku.get(cmd.getBarCode());
            // 在出库缓存中查询SKU 未在出库中找到表明商品存在问题
            if (sku != null) {
                cmd.setSku(sku);
                log.debug("sku is found {}", cmd.getBarCode());
            } else {
                log.debug("sku is not found : {}", cmd.getBarCode());
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getBarCode()}));
            }
            BiChannel shop = companyShopDao.getByName(cmd.getInvOwner());
            if (shop == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getInvOwner()}));
            }
            WarehouseLocation loc = warehouseLocationDao.findLocationByCode(cmd.getLocationCode(), ouId);
            if (loc == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getLocationCode()}));
            } else {
                cmd.setLocation(loc);
            }
            InventoryStatus invs = inventoryStatusDao.findByNameUnionSystem(cmd.getInvStatusName(), ou.getParentUnit().getParentUnit().getId());
            if (invs == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 4);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getInvStatusName()}));
            } else {
                cmd.setStatus(invs);
            }

            if (cmd.getQuantity() == null || cmd.getQuantity() < 1) {
                // inInv.remove(i--);
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 3);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_QUANTITY_MINUS, new Object[] {SHEET_0, currCell}));
            }

            offsetRow++;
        }
        // 校验出入库数量是否相等
        if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
            // 合并忽略location
            List<InventoryCommand> list = new ArrayList<InventoryCommand>();
            for (InventoryCommand cmd : outInv) {
                if (list.size() == 0) {
                    list.add(cmd);
                    cmd.setOccupyQty(cmd.getQuantity());
                    continue;
                } else {
                    boolean isSame = false;
                    for (InventoryCommand ic : list) {
                        if (cmd.getSku().getId().equals(ic.getSku().getId()) && cmd.getInvOwner().equals(ic.getInvOwner()) && cmd.getStatus().getId().equals(ic.getStatus().getId())) {
                            isSame = true;
                            ic.setOccupyQty(ic.getOccupyQty() + cmd.getQuantity());
                            break;
                        }
                    }
                    if (!isSame) {
                        list.add(cmd);
                        cmd.setOccupyQty(cmd.getQuantity());
                    }
                }
            }
            for (InventoryCommand oc : list) {
                for (InventoryCommand ic : inInv) {
                    if (oc.getSku().getId().equals(ic.getSku().getId()) && oc.getInvOwner().equals(ic.getInvOwner()) && oc.getStatus().getId().equals(ic.getStatus().getId())) {
                        oc.setConfirmQty(ic.getQuantity() + (oc.getConfirmQty() == null ? 0L : oc.getConfirmQty()));
                    }
                }
            }
            for (InventoryCommand oc : list) {
                if (oc.getOccupyQty() - (oc.getConfirmQty() == null ? 0 : oc.getConfirmQty()) != 0) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_IN_OUT_IS_NOT_EQUALS, new Object[] {oc.getSku().getBarCode(), oc.getLocation().getCode(), oc.getStatus().getName()}));
                }
            }
        }
        log.debug("=====================validatetransitInner end========================");
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus inventoryStatusChangeImport(File file, Long shopId, String remork, Long ouId, Long userId) throws Exception {
        log.info("inventoryStatusChangeImport start,shop : {},user : {}", shopId, userId);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        TransactionType tTypeOut = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT);
        if (tTypeOut == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT_NOT_FOUND);
        }
        TransactionType tTypeIn = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (tTypeIn == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        InventoryStatus iStatus = null;
        BiChannel shop = companyShopDao.getByPrimaryKey(shopId);
        String innerShopCode1 = shop.getCode();
        Map<String, Object> sheet1Bean = new HashMap<String, Object>();
        try {
            ReadStatus rs = inventoryStatusChangeReader.readAll(new FileInputStream(file), sheet1Bean);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) sheet1Bean.get("type");
            List<InventoryCommand> outInv = (List<InventoryCommand>) sheet1Bean.get("inv");
            List<InventoryCommand> inInv = (List<InventoryCommand>) sheet1Bean.get("inv2");
            if ((outInv != null && outInv.size() > 1000) || (inInv != null && inInv.size() > 1000)) {
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_MORE_THAN_1000));
                rs.setStatus(-2);
                return rs;
            }
            iStatus = isDao.getByName("临近保质期", ou.getParentUnit().getParentUnit().getId());
            // 判断是否有该状态
            if (iStatus == null) {
                iStatus = getInventoryStatus(operationUnitDao.getByPrimaryKey(ou.getParentUnit().getParentUnit().getId()));
            }
            log.info("inventoryStatusChangeImport validate out start,shop : {},user : {}", shopId, userId);
            rs = validateInventoryStatusChange(rs, type, outInv, inInv, tTypeOut, ouId);
            log.info("inventoryStatusChangeImport validate out end,shop : {},user : {}", shopId, userId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            for (InventoryCommand ic : inInv) {
                String code = null;
                if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
                    // 商品条码
                    code = ic.getSku().getBarCode();
                }
                if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
                    // 商品编码
                    code = ic.getSku().getCode();
                }
                // 验证是不是保质期商品
                if (ic.getInvStatusName().equals("临近保质期")) {
                    if (ic.getSku().getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_ERROR, new Object[] {type, code}));
                        return rs;
                    }
                }
                if (ic.getSku().getStoremode() != null && ic.getSku().getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    // 验证生产日期和过期时间
                    rs = wareHouseManager.checkPoductionDateAndExpireDate(ic.getPoductionDate(), ic.getSexpireDate(), rs, code, ic.getSku(), null, 0);
                    if (rs != null && rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                        return rs;
                    }
                }
            }
            log.info("inventoryStatusChangeImport validate check sku end,shop : {},user : {}", shopId, userId);
            List<StvLine> list = new ArrayList<StvLine>();
            for (InventoryCommand cmd : outInv) {
                boolean isSame = false;
                for (StvLine sl : list) {
                    if (sl.getSku().getId().equals(cmd.getSku().getId()) && sl.getLocation().getId().equals(cmd.getLocation().getId()) && sl.getInvStatus().getId().equals(cmd.getStatus().getId())) {
                        sl.setQuantity(sl.getQuantity() + cmd.getQuantity());
                        isSame = true;
                    }
                }
                if (!isSame) {
                    StvLineCommand l = new StvLineCommand();
                    l.setSku(cmd.getSku());
                    l.setLocation(cmd.getLocation());
                    l.setOwner(innerShopCode1);
                    if (cmd.getInvStatusName().equals("临近保质期")) {
                        l.setInvStatus(iStatus);
                    } else {
                        l.setInvStatus(cmd.getStatus());
                    }
                    l.setQuantity(cmd.getQuantity());
                    list.add(l);
                }
            }
            // 创建作业单占用库存
            log.info("inventoryStatusChangeImport create sta start,shop : {},user : {}", shopId, userId);
            StockTransApplication sta = wareHouseManager.createInvStatusChangeSta(true, remork, userId, ouId, list);
            log.info("inventoryStatusChangeImport create sta end,shop : {},user : {}", shopId, userId);
            // 合并入库单
            List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            Map<String, List<StvLineCommand>> lMap = new HashMap<String, List<StvLineCommand>>();
            for (StvLineCommand cmd : outStvLine) {
                // String key = cmd.getSkuId()+cmd.getLocationCode();
                String key = cmd.getSkuId().toString();
                List<StvLineCommand> cl = lMap.get(key);
                if (cl == null) {
                    cl = new ArrayList<StvLineCommand>();
                }
                cl.add(cmd);
                log.debug("put key : {}", key);
                lMap.put(key, cl);
            }
            List<StvLineCommand> inlist = new ArrayList<StvLineCommand>();
            for (InventoryCommand cmd : inInv) {
                String key = cmd.getSku().getId().toString();
                List<StvLineCommand> cl = lMap.get(key);
                boolean isSM = cmd.getSku().getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT);
                if (cl == null || cl.size() == 0) throw new BusinessException((isSM ? ErrorCode.SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL : ErrorCode.INBOUND_BATCH_CODE_ERROR), new Object[] {cmd.getSku().getBarCode()});
                Long qty = cmd.getQuantity();
                for (int i = 0; i < cl.size(); i++) {
                    StvLineCommand stvl = cl.get(i);
                    StvLineCommand l = new StvLineCommand();
                    l.setLocation(cmd.getLocation());
                    l.setLocationId(cmd.getLocation().getId());
                    l.setSku(cmd.getSku());
                    l.setSkuId(cmd.getSku().getId());
                    if (cmd.getInvStatusName().equals("临近保质期")) {
                        l.setInvStatus(iStatus);
                    } else {
                        l.setInvStatus(cmd.getStatus());
                    }
                    l.setOwner(stvl.getOwner());
                    l.setIntInvstatus(cmd.getStatus().getId());
                    l.setProductionDate(cmd.getStrPoductionDate());
                    l.setExpireDate(cmd.getStrExpireDate());
                    l.setSkuCost(stvl.getSkuCost());
                    l.setBatchCode(stvl.getBatchCode());
                    boolean isTrue = false;
                    // 保质期管理逻辑开始===========
                    if (isSM) {
                        whManagerProxy.setStvLineProductionDateAndExpireDate(l, cmd.getPoductionDate(), cmd.getSexpireDate());
                        String inExpireDate = formatDate.format(l.getExpireDate());
                        String outExpireDate = formatDate.format(stvl.getExpireDate());
                        // 效期优化，当商品的timeType由天更新为月或者年时，导入的过期时间匹配不上原始库存过期时间
                        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.INV_STATUS_CHANGE_EXP_DATE, Constants.DATE_ADJUST);
                        String flag = op == null ? "false" : (op.getOptionValue() == null ? "false" : op.getOptionValue());
                        if ("true".equals(flag)) {
                            try {
                                Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
                                if (!TimeTypeMode.DAY.equals(sku.getTimeType()) && !(inExpireDate.endsWith("01") || inExpireDate.endsWith("0101"))) {
                                    log.info("inv-status-change-import:" + cmd.getPoductionDate() + "：" + cmd.getSexpireDate());
                                    Date productionDate = null;
                                    if (!StringUtil.isEmpty(cmd.getPoductionDate())) {
                                        productionDate = formatDate.parse(cmd.getPoductionDate().trim());
                                    }
                                    Date expireDate = null;
                                    if (!StringUtil.isEmpty(cmd.getSexpireDate())) {
                                        expireDate = formatDate.parse(cmd.getSexpireDate().trim());
                                    }
                                    if (expireDate == null) {
                                        Calendar c = Calendar.getInstance();
                                        c.setTime(productionDate); // 设置生成日期
                                        c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                                        expireDate = c.getTime();
                                    }
                                    l.setExpireDate(expireDate);
                                    inExpireDate = formatDate.format(l.getExpireDate());
                                    log.info("inv-status-change-import:" + cmd.getPoductionDate() + "：" + inExpireDate);
                                }
                            } catch (Exception e) {
                                log.error("修正入库商品过期时间异常！", e);
                            } finally {
                                inExpireDate = formatDate.format(l.getExpireDate());
                            }
                        }
                        if (inExpireDate.equals(outExpireDate)) {
                            l.setProductionDate(stvl.getProductionDate());
                            l.setValidDate(stvl.getValidDate());
                            isTrue = true;
                        }
                    } else {
                        isTrue = true;
                    }
                    // 保质期管理逻辑结束

                    if (isTrue) {
                        long quantity = 0l;
                        if (qty > stvl.getQuantity()) {
                            quantity = stvl.getQuantity();
                        } else {
                            quantity = qty;
                        }
                        qty -= quantity;
                        l.setQuantity(quantity);
                        stvl.setQuantity(stvl.getQuantity() - quantity);
                        inlist.add(l);
                        if (stvl.getQuantity() == 0l) {
                            cl.remove(i--);
                            if (cl.size() == 0) {
                                lMap.remove(key);
                            }
                        }
                        if (qty == 0l) {
                            break;
                        }
                    }
                }
                if (qty != 0l) {
                    throw new BusinessException((isSM ? ErrorCode.SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL : ErrorCode.INBOUND_BATCH_CODE_ERROR), new Object[] {cmd.getSku().getBarCode()});
                }
            }
            // 创建入库作业单
            log.info("Call oms inventory status create inbound sta start,shop : {},user : {}", shopId, userId);
            noExecuteInvStatusChangeForImpory(true, sta.getId(), inlist, userId);
            log.info("Call oms inventory status create inbound sta end,shop : {},user : {}", shopId, userId);

            // 调用OMS接口
            if (!shop.getCustomer().getCode().equals("adidas") && !shop.getCustomer().getCode().equals(Constants.CAINIAO_DB_CUSTOMER_CODE)) {
                InvChangeNoticPacThread t = new InvChangeNoticPacThread(sta.getId());
                Thread tx = new Thread(t);
                tx.start();
            } else {
                // 菜鸟的库存调整不锁定作业单
                if (shop.getCustomer().getCode().equals(Constants.CAINIAO_DB_CUSTOMER_CODE)) {
                    sta.setIsLocked(false);
                    sta.setSystemKey(Constants.CAINIAO_DB_SYSTEM_KEY);
                }
                staDao.save(sta);
                staDao.flush();
            }
            sta.setOwner(innerShopCode1);
            log.info("inventoryStatusChangeImport end,shop : {},user : {}", shopId, userId);
            return rs;
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        } catch (Exception e) {
            throw e;
        }
    }

    public void invChangeNoticPac(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            // 作业单未查到，则等待1分钟尝试
            try {
                Thread.sleep(60 * 1000);
                sta = staDao.getByPrimaryKey(staId);
            } catch (InterruptedException e) {}
        }
        if (sta == null) {
            // 作业单未查到，则等待1分钟尝试
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {}
        }
        sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            log.error("sta not found,sta:{}", staId);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<StockTransVoucher> stvLists = stvLineDao.findStvByStaId(sta.getId());
        OperationBill ob = new OperationBill();
        ob.setCode(sta.getCode());
        ob.setType(sta.getType().getValue());
        ob.setMemo(sta.getMemo());
        ob.setWhCode(sta.getMainWarehouse().getCode());
        for (StockTransVoucher st : stvLists) {
            List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
            String innerShopCode = staLineDao.findByInnerShopCode(sta.getId(), new SingleColumnRowMapper<String>(String.class));
            BiChannel companyShop = companyShopDao.getByCode(innerShopCode);
            if (companyShop == null) {
                throw new BusinessException("");
            }
            // List<StvLine> stvLines = stvLineDao.findStvLineByStvId(st.getId());
            List<StvLine> lists = stvLineDao.findStvLineListByStvId(st.getId());
            if (st.getDirection().getValue() == 1) {
                for (int i = 0; i < lists.size(); i++) {
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setSkuCode(lists.get(i).getSku().getCode());
                    operationBillLine.setQty(lists.get(i).getQuantity());
                    // operationBillLine.setInvStatusId(lists.get(i).getInvStatus().getId());
                    operationBillLine.setInvStatusCode(lists.get(i).getInvStatus().getName());
                    operationBillLine.setInvBatchCode(lists.get(i).getBatchCode());
                    // operationBillLine.setWhOuId(lists.get(i).getWarehouse().getId());
                    operationBillLine.setWhCode(lists.get(i).getWarehouse().getCode());
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(lists.get(i).getOwner() == null ? companyShop.getCode() : lists.get(i).getOwner());
                    // operationBillLine.setShopOuId(companyShop.getOu().getId());
                    billLines.add(operationBillLine);
                }
                ob.setInboundLines(billLines);
            } else if (st.getDirection().getValue() == 2) {
                for (int i = 0; i < lists.size(); i++) {
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setSkuCode(lists.get(i).getSku().getCode());
                    operationBillLine.setQty(lists.get(i).getQuantity());
                    // operationBillLine.setInvStatusId(lists.get(i).getInvStatus().getId());
                    operationBillLine.setInvStatusCode(lists.get(i).getInvStatus().getName());
                    operationBillLine.setInvBatchCode(lists.get(i).getBatchCode());
                    operationBillLine.setWhCode(lists.get(i).getWarehouse().getCode());
                    // operationBillLine.setWhOuId(lists.get(i).getWarehouse().getId());
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(lists.get(i).getOwner() == null ? companyShop.getCode() : lists.get(i).getOwner());
                    // operationBillLine.setShopOuId(companyShop.getOu().getId());
                    billLines.add(operationBillLine);
                }
                ob.setOutboundLines(billLines);
            }
        }
        try {
            log.info("Call oms inventory status change confirm interface->BEGIN,sta:{}", staId);
            BaseResult baseResult = rmi4Wms.operationBillCreate(ob);
            if (baseResult.getStatus() == 0) {
                throw new BusinessException(baseResult.getMsg());
            }
            log.info("Call oms inventory status change confirm interface->END,sta: {}", staId);
            String slipCode = baseResult.getSlipCode();
            sta.setRefSlipCode(slipCode);
            // KJL添加店铺
            staDao.save(sta);
        } catch (BusinessException e) {
            log.error("invchange notice pac error,sta: {}", staId);
        } catch (Exception e) {
            log.error("invchange notice pac error,sta: {}", staId, e);
        }
    }

    /**
     * 添加临近保质期状态
     */
    public InventoryStatus getInventoryStatus(OperationUnit ou) {
        InventoryStatus is = new InventoryStatus();
        is.setName("临近保质期");
        is.setOu(ou);
        is.setIsAvailable(true);
        is.setIsForSale(false);
        is.setLastModifyTime(new Date());
        is.setIsInCost(false);
        is.setIsSystem(false);
        isDao.save(is);
        return is;
    }

    private void noExecuteInvStatusChangeForImpory(boolean isExcel, Long staId, List<StvLineCommand> stvlineList, Long operatorId) throws Exception {
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
                    if (current == null) {
                        current = new BusinessException();
                    }
                    current.setLinkedException(new BusinessException(ErrorCode.SKU_QTY_NOT_EQ_FOR_INV_STATUS_CHANGE, new Object[] {sku == null ? "" : sku.getCode(), sku == null ? "" : sku.getBarCode(), sku == null ? "" : bachcode}));
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
        List<StvLine> outList = stvLineDao.findStvLineListByStvId(outStv.getId());
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
            l.setProductionDate(cmd.getProductionDate());
            l.setExpireDate(cmd.getExpireDate());
            l.setValidDate(cmd.getValidDate());
            // 12.1
            List<String> error = wareHouseManager.validateIsSameBatch(cmd);
            if (error != null && error.size() > 0) throw new BusinessException(ErrorCode.INVENTORY_SKU_LOCATION_IS_SINGLE_STOREMODE_ERROR, new Object[] {error.toString()});

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
    }

    @SuppressWarnings("unchecked")
    public ReadStatus createInitializeInventoryImport(StockTransApplication stacode, File file, Long ouId, Long cmpOuId, Long userId) {
        TransactionType tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INBOUND_INITIALIZE);
        if (tType == null) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_INITIALIZE_TRANSATION_TYPE_NOT_FOUND);
        }
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM, ChooseOption.KEY_INITIALIZE_INVENTORY_NULL_CHECK);
        if (ChooseOption.KEY_INITIALIZE_INVENTORY_NULL_CHECK_TRUE.equals(co.getOptionValue())) {
            // 检查是否允许初始化
            Pagination<InventoryCommand> pg = inventoryDao.findAllByOuid(0, 1, ouId, new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class), null);
            if (pg.getCount() != 0) {
                throw new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_NOT_EMPTY);
            }
        }
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        User user = userDao.getByPrimaryKey(userId);
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            // 读取excel
            ReadStatus rs = inventoryInitializeReader.readAll(new FileInputStream(file), beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            } else {
                String type = (String) beans.get("type");
                List<InventoryCommand> result = (List<InventoryCommand>) beans.get("inv");
                List<InventoryCommand> resultSheet2 = (List<InventoryCommand>) beans.get("invSheet2");
                // 验证excel数据
                if (result.size() == 0 && resultSheet2.size() == 0) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_EXCEL_IS_NULL);
                }
                Map<String, Long> locMap = warehouseLocationDao.findAllByOu(ouId, new MapRowMapper());
                List<BiChannel> shopList = companyShopDao.getAllRefShopByWhOuId(ouId);
                Map<String, Long> stsMap = inventoryStatusDao.findAllByCmpOu(cmpOuId, new MapRowMapper());
                Map<String, InventoryCommand> invMap = new HashMap<String, InventoryCommand>();
                // 验证SHEET1，无SN号商品
                if (result.size() != 0) {
                    Map<String, InventoryCommand> invMapSh1 = validateInventoryInitializeExcel(stsMap, shopList, locMap, rs, type, result, tType, ouId, cmpOuId);
                    invMap.putAll(invMapSh1);
                }
                // 验证SHEET2，SN号商品
                Map<String, Long> snMap = new HashMap<String, Long>();
                if (resultSheet2.size() != 0) {
                    Map<String, InventoryCommand> invMapSh2 = validateInventoryInitializeExcelSheet2(snMap, stsMap, shopList, locMap, rs, type, resultSheet2, tType, ouId, cmpOuId);
                    invMap.putAll(invMapSh2);
                }
                if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    return rs;
                }
                // create sta
                StockTransApplication sta = new StockTransApplication();
                sta.setArriveTime(new Date());
                if (StringUtils.hasText(stacode.getCode())) {
                    sta.setCode(stacode.getCode());
                } else {
                    sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
                }
                sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
                sta.setCreateTime(new Date());
                sta.setCreator(user);
                sta.setFinishTime(new Date());
                sta.setInboundOperator(user);
                sta.setInboundTime(new Date());
                sta.setMainWarehouse(ou);
                sta.setLastModifyTime(new Date());
                sta.setStatus(StockTransApplicationStatus.FINISHED);

                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), userId, sta.getMainWarehouse().getId());
                sta.setType(StockTransApplicationType.INBOUND_INVENTORY_INITIALIZE);
                // create stv
                StockTransVoucher stv = new StockTransVoucher();
                stv.setSta(sta);
                stv.setCode(sta.getCode() + "01");
                stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)).longValue());
                stv.setCreateTime(new Date());
                stv.setCreator(user);
                stv.setDirection(TransactionDirection.INBOUND);
                stv.setFinishTime(new Date());
                stv.setMode(InboundStoreMode.TOGETHER);
                stv.setOperator(user);
                stv.setLastModifyTime(new Date());
                stv.setStatus(StockTransVoucherStatus.FINISHED);
                stv.setTransactionType(tType);
                stv.setWarehouse(ou);
                List<StaLine> stals = new ArrayList<StaLine>();
                List<StvLine> stvls = new ArrayList<StvLine>();
                String batchCode = Long.valueOf(new Date().getTime()).toString();
                for (InventoryCommand inv : invMap.values()) {
                    // create sta line
                    StaLine sl = new StaLine();
                    sl.setCompleteQuantity(inv.getQuantity());
                    sl.setInvStatus(inv.getStatus());
                    sl.setOwner(inv.getInvOwner());
                    sl.setQuantity(inv.getQuantity());
                    sl.setSku(inv.getSku());
                    sl.setSkuCost(inv.getSkuCost());
                    sl.setSta(sta);
                    stals.add(sl);
                    staLineDao.save(sl);
                    // create stv line
                    StvLine stvl = new StvLine();
                    stvl.setBatchCode(batchCode);
                    stvl.setDirection(stv.getDirection());
                    stvl.setDistrict(inv.getLocation().getDistrict());
                    stvl.setLocation(inv.getLocation());
                    stvl.setOwner(inv.getInvOwner());
                    stvl.setQuantity(inv.getQuantity());
                    stvl.setSku(inv.getSku());
                    stvl.setSkuCost(inv.getSkuCost());
                    stvl.setStv(stv);
                    stvl.setTransactionType(stv.getTransactionType());
                    stvl.setWarehouse(stv.getWarehouse());
                    stvl.setInvStatus(inv.getStatus());
                    stvl.setSkuCost(inv.getSkuCost());
                    stvl.setStaLine(sl);
                    stvls.add(stvl);
                }
                stv.setStvLines(stvls);
                staDao.save(sta);
                if (sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) && sta.getVmiRCStatus() != Boolean.TRUE) {
                    BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                    VmiInterface vmi = vmiFactory.getBrandVmi(shop.getVmiCode());
                    if (vmi != null && StringUtil.isEmpty(sta.getDataSource())) {
                        vmi.generateReceivingWhenFinished(sta);
                    }
                }
                stvDao.save(stv);
                stvDao.flush();
                staDao.updateSkuQtyById(sta.getId());
                // 插入sn号，sn日志
                if (snMap != null && snMap.size() != 0) {
                    List<String> errlist = new ArrayList<String>();
                    for (String sn : snMap.keySet()) {
                        if (!StringUtils.hasText(sn)) {
                            errlist.add(sn);
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});
                    for (Entry<String, Long> ety : snMap.entrySet()) {
                        SkuSn sn = new SkuSn();
                        sn.setOu(sta.getMainWarehouse());
                        Sku sku = new Sku();
                        sku.setId(ety.getValue());
                        sn.setSku(sku);
                        sn.setBatchCode(batchCode);
                        sn.setSn(ety.getKey());
                        sn.setStatus(SkuSnStatus.USING);
                        sn.setStv(stv);

                        snDao.save(sn);
                    }
                    snDao.flush();
                    snLogDao.createInboundLogByStvIdSql(stv.getId(), batchCode);
                }
                // 更新库区
                stvLineDao.updateDistrict(stv.getId());
                wareHouseManagerExe.validateBiChannelSupport(stv, null);
                // 插入库存、日志、0库存、计算成本
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("in_ou_id", stv.getWarehouse().getId());
                params.put("in_stv_id", stv.getId());
                params.put("in_com_id", wareHouseManager.findCompanyOUByWarehouseId(stv.getWarehouse().getId()).getId());
                params.put("is_in_cost", stv.getTransactionType().getIsInCost() ? 1 : 0);
                SqlParameter[] sqlParameters = {new SqlParameter("in_ou_id", Types.NUMERIC), new SqlParameter("in_stv_id", Types.NUMERIC), new SqlParameter("in_com_id", Types.NUMERIC), new SqlParameter("is_in_cost", Types.NUMERIC)};
                inventoryDao.executeSp("sp_insert_inventory", sqlParameters, params);
                return rs;
            }
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importwhReplenishStock(File file, Long whouid) {
        ReadStatus rs = null;
        try {
            Map<String, Object> beans = new HashMap<String, Object>();
            // 读取excel
            rs = warehouseReplenishStockInfoReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                List<WarehouseLocationCommand> list = (List<WarehouseLocationCommand>) beans.get("data");
                final ExcelSheet sheet = warehouseReplenishStockInfoReader.getDefinition().getExcelSheets().get(0);
                List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
                String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());

                int offsetRow = 0;
                // 处理数据（判断那些数据不允许为空）
                for (WarehouseLocationCommand whloction : list) {
                    WarehouseLocation whlocation = warehouseLocationDao.findwhLocationByCode(whloction.getCode(), whouid);
                    if (whlocation != null) {
                        if (whloction.getCapacity() != null) {
                            whlocation.setCapacity(whloction.getCapacity());
                        }
                        if (whloction.getCapaRatioNum() != null) {
                            whlocation.setCapaRatio(whloction.getCapaRatioNum().intValue());
                        }
                        whlocation.setWarningNumber(whloction.getWarningNumber());
                        whlocation.setLastModifyTime(new Date());
                        warehouseLocationDao.save(whlocation);
                    } else {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.WAREHOUSELOCATION_IS_NULL, new Object[] {2, currCell, whloction.getCode()}));
                        offsetRow++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus createPurchaseInboundImport(File file, Long staid, Long stvId, User operator) {
        List<StvLineCommand> stvLines = stvLineDao.findStvLineListByStaId(staid, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
        if (stvLines == null || stvLines.size() == 0) {
            throw new BusinessException(ErrorCode.STV_LINE_NOT_FOUND);
        }
        StvLine stvLine = stvLines.get(0);
        if (stvLine == null) {
            throw new BusinessException(ErrorCode.STV_LINE_NOT_FOUND);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = invReaderForPurchase.readSheet(new FileInputStream(file), 0, beans);
            if (readStatus == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("createPurchaseInboundImport FileNotFoundException:" + sta.getCode(), e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            if (readStatus != null) {
                Object type = beans.get("type");
                if (type == null) {
                    throw new BusinessException(ErrorCode.PURCHASE_SKU_TYPE);
                } else {
                    boolean code = true;
                    if ("SKU条码".equals(type)) {
                        code = false;
                    }
                    List<StvLineCommand> dataList = (List<StvLineCommand>) beans.get("listData");
                    if (dataList == null) {
                        throw new BusinessException(ErrorCode.PURCHASE_NO_DATA);
                    }
                    // 清除skucode为空的数据
                    for (int size = 0; size < dataList.size(); size++) {
                        StvLineCommand sLinedata = dataList.get(size);
                        if (null == sLinedata) {
                            dataList.remove(size);
                            size = size - 1;
                        } else if (null == sLinedata.getSkuCode() || sLinedata.getSkuCode().trim().length() <= 0) {
                            dataList.remove(size);
                            size = size - 1;
                        }
                    }

                    List<StvLineCommand> tempList = new ArrayList<StvLineCommand>();
                    List<StvLineCommand> dataList2 = new ArrayList<StvLineCommand>();
                    if (!code) {
                        // 商品条码，将同一类的条码归为一个barcode
                        for (int i = 0; i < dataList.size(); i++) {
                            StvLineCommand data = dataList.get(i);
                            String barcode = getMainBarCode(data.getSkuCode(), customerId);
                            data.setSkuCode(barcode);
                            tempList.add(data);
                            dataList2 = tempList;
                        }

                    } else {
                        tempList = dataList;
                    }
                    // 比较excel中的skuCode是否有相同的, 有相同的数量叠加
                    for (int i = 0; i < tempList.size(); i++) {
                        for (int j = i + 1; j < tempList.size(); j++) {
                            if (tempList.get(i).getSkuCode().equals(tempList.get(j).getSkuCode())) {
                                Long qty = tempList.get(i).getQuantity();
                                tempList.get(i).setQuantity(qty + tempList.get(j).getQuantity());
                                tempList.remove(j);
                                j = j - 1;
                            }
                        }
                    }
                    if (tempList.size() != stvLines.size()) {
                        // 导入的商品数量与实际需要上架的商品数量不相等
                        throw new BusinessException(ErrorCode.IMPORT_STVLINE_QTY_IS_NOT_MATCH);
                    }
                    // 数据库中的记录，存在map中，key为sku编码，value为库存数量
                    Map<String, Long> smap = new HashMap<String, Long>();
                    if (code) {
                        // 商品编码
                        for (StvLineCommand slc : stvLines) {
                            smap.put(slc.getSkuCode(), slc.getQuantity());
                        }
                    } else {
                        // 商品条码
                        for (StvLineCommand slc : stvLines) {
                            smap.put(slc.getBarCode(), slc.getQuantity());
                        }
                    }

                    // excel读取的记录，存在map中，key为sku编码，value为库存数量
                    for (StvLineCommand e : tempList) {
                        String skuCode = e.getSkuCode();
                        if (smap.get(skuCode) == null) {
                            // 不存在该skuCode
                            throw new BusinessException(ErrorCode.IMPORT_SKU_ISNOT_MEET_REGULATION, new Object[] {skuCode});
                        } else if (!smap.get(skuCode).equals(e.getQuantity())) {
                            // 本次上架数量与输入上架数量不相等
                            throw new BusinessException(ErrorCode.IMPORT_QTY_IS_OVER, new Object[] {skuCode});
                        }
                    }
                    // set stvLineList数据
                    List<StvLine> stvLineList = new ArrayList<StvLine>();

                    if (!code) {
                        dataList = dataList2;
                    }
                    for (StvLineCommand data : dataList) {
                        StvLine stvLineData = new StvLine();
                        Sku sku = new Sku();
                        StaLine staLine = new StaLine();
                        WarehouseLocation location = new WarehouseLocation();
                        for (StvLineCommand data2 : stvLines) {
                            String skuCode = "";
                            if (code) {
                                skuCode = data2.getSkuCode();
                            } else {
                                skuCode = data2.getBarCode();
                            }
                            if (skuCode.equals(data.getSkuCode())) {
                                data.setSkuId(data2.getSkuId());
                                data.setStalineId(data2.getStalineId());
                                data.setOwner(data2.getOwner());
                            }
                        }
                        sku.setId(data.getSkuId());
                        staLine.setId(data.getStalineId());
                        location.setCode(data.getLocationCode());
                        stvLineData.setSku(sku);
                        stvLineData.setStaLine(staLine);
                        stvLineData.setOwner(data.getOwner());
                        stvLineData.setLocation(location);
                        stvLineData.setQuantity(data.getQuantity());
                        stvLineList.add(stvLineData);
                    }
                    // 执行上架
                    wareHouseManager.purchaseReceiveStep2(stvId, stvLineList, false, operator, false, true);
                }
            }
        }
        return readStatus;
    }

    /**
     * 导入sn商品至前台
     */
    @SuppressWarnings("unchecked")
    public List<StaSnImportCommand> importsnToWeb(File file, Long ouId, List<SkuSnLogCommand> snList, String snBarCode) {
        List<StaSnImportCommand> list = new ArrayList<StaSnImportCommand>();
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = snSkuReadForPurchase.readSheet(new FileInputStream(file), 0, beans);
            if (readStatus == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("importsnToWeb FileNotFoundException:", e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            if (readStatus != null) {
                List<StaSnImportCommand> dataList = (List<StaSnImportCommand>) beans.get("listData");
                Map<String, String> map = new HashMap<String, String>();// 判断sn号
                for (StaSnImportCommand staLineCommand : dataList) {// 遍历EXL数据
                    Sku sku = null;
                    boolean isSN = false;
                    // boolean isSN = true;//测试 改为true
                    StaSnImportCommand staList = new StaSnImportCommand();
                    String snNumber = staLineCommand.getSnNumber();// SN号
                    String barCode = staLineCommand.getBarCode();// 条形码
                    String status = staLineCommand.getIntInvstatusName();// 库存状态
                    Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);// 客户ID
                    if (barCode != null && !snBarCode.contains(barCode)) {
                        staList.setErrorBarCode(barCode);
                    }
                    if (barCode != null && barCode != "") {
                        sku = skuDao.getByBarcode(barCode, customerId);// 根据条码获取商品信息
                    }
                    if (sku != null) {
                        // 校验SN号。满足两个条件。1.销售出库SN号存在；2.SN表不存在
                        if (snNumber != null && snNumber != "") {
                            for (SkuSnLogCommand skuSn : snList) {
                                if (skuSn.getSn().equals(snNumber)) {// 销售出库SN号存在
                                    SkuSn skus = snDao.findSkuSnBySnSingle(snNumber);
                                    if (skus == null) {// SN表不存在
                                        isSN = true;
                                    }
                                }
                            }
                        }
                        if (isSN) {// SN存在
                            if (map.get(snNumber) == null) {// SN号不能重用
                                map.put(snNumber, barCode);
                                // 校验库存状态
                                if (status != null && status != "") {
                                    InventoryStatus inventoryStatus = inventoryStatusDao.getByName(status, ouId);
                                    if (inventoryStatus == null) {
                                        staList.setErrorStatus(barCode);// 库存状态错误的barCode信息
                                    }
                                    /** 校验通过的数据 * */
                                    staList.setBarCode(barCode);
                                    staList.setSnNumber(snNumber);
                                    staList.setIntInvstatusName(status);
                                } else {
                                    staList.setErrorStatus(barCode);// 库存状态错误的barCode信息
                                }
                            } else {
                                staList.setErrorSnList(snNumber);// SN号校验错误的信息
                            }
                        } else {
                            staList.setErrorSnList(snNumber);// SN号校验错误的信息
                        }
                    } else {
                        staList.setErrorBarCode(barCode);// barCode校验错误的信息
                    }
                    list.add(staList);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus createOperationOut(File file, StockTransApplicationCommand staComd, Long ouId, Long userId) throws Exception {
        StockTransApplicationType type = staComd.getType();
        if (type == null) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANSIT_INNER_TRANSATION_TYPE_NOT_FOUND);
        }
        if (staComd.getOwner() == null) {
            throw new BusinessException(ErrorCode.OWNER_IS_NULL);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = predefinedOutReader.readSheet(new FileInputStream(file), 0, beans);
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        List<StaLineCommand> staLineTemp = new ArrayList<StaLineCommand>();
        // 验证
        String skuType = (String) beans.get("type");
        final ExcelSheet sheetOut = predefinedOutReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();
        int intType = 0;
        if ("SKU条码".equals(skuType)) {
            intType = 1;
        } else if ("SKU编码".equals(skuType)) {
            intType = 0;
        } else {
            rs.setStatus(-1);
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
        }
        TransactionType tType = null;
        int transactionType = type.getValue();
        if (StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue() == transactionType) {
            tType = transactionTypeDao.findByCode(Constants.CONSIGNMENT_OUTBOUND);
        } else if (StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue() == transactionType) {
            tType = transactionTypeDao.findByCode(Constants.SETTLEMENT_OUTBOUND);
        } else if (StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.getValue() == transactionType) {
            tType = transactionTypeDao.findByCode(Constants.PACKAGING_MATERIALS_OUTBOUND);
        }
        if (tType == null || (!Constants.CONSIGNMENT_OUTBOUND.equals(tType.getCode()) && !Constants.SETTLEMENT_OUTBOUND.equals(tType.getCode()) && !Constants.PACKAGING_MATERIALS_OUTBOUND.equals(tType.getCode()))) {
            rs.setStatus(-1);
            rs.addException(new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND));
        }
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        List<StaLineCommand> stalList = (List<StaLineCommand>) beans.get("outStaLine");
        final ExcelSheet sheet = predefinedOutReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheet.getExcelBlocks().get(0).getStartRow(), sheet.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        Warehouse wh = warehouseDao.getByOuId(ouId);
        String cuo = wh.getCustomer().getCode();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        for (StaLineCommand cmd : stalList) {
            Sku sku = skuCache.get(cmd.getSkuCode());
            if (sku == null) {
                if (intType == 1) {
                    // 条码
                    sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addSkuCode != null) {
                            sku = addSkuCode.getSku();
                        }
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.PREDEFINED_EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        if (type.getValue() == StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue() || type.getValue() == StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {

                            if (tType.getCode().equals("SETTLEMENT_OUTBOUND")) {
                                if (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                    rs.setStatus(-1);
                                    rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                                }
                            }

                            if (tType.getCode().equals("CONSIGNMENT_OUTBOUND")) {
                                if (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                    rs.setStatus(-1);
                                    rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                                }
                            }
                        }
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else {
                    sku = skuDao.getByCode(cmd.getSkuCode());
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.PREDEFINED_EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        if (type.getValue() == StockTransApplicationType.OUTBOUND_SETTLEMENT.getValue() || type.getValue() == StockTransApplicationType.OUTBOUND_CONSIGNMENT.getValue()) {
                            // 如果是结算经销出库，导入商品只能是结算经销商品
                            if (tType.getCode().equals("SETTLEMENT_OUTBOUND")) {
                                if (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                    rs.setStatus(-1);
                                    rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                                }
                            }
                            // 如果是代销出库，只能导入代销商品
                            if (tType.getCode().equals("CONSIGNMENT_OUTBOUND")) {
                                if (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                                    rs.setStatus(-1);
                                    rs.addException(new BusinessException(ErrorCode.PAYMENT_SKU_NOT_BE_CREATED, new Object[] {sku.getBarCode(), sku.getCode()}));
                                }
                            }
                        }
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            }

            InventoryStatus status = inventoryStatusDao.findByNameUnionSystem(cmd.getIntInvstatusName(), wareHouseManager.findCompanyOUByWarehouseId(ouId).getId());

            if (status == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getIntInvstatusName()}));
            } else {
                cmd.setInvStatus(status);
            }
            cmd.setInvStatus(status);
            if (cmd.getQuantity() == null || cmd.getQuantity() < 1) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.BETWENLIBARY_STA_QTY_IS_NOT_NULL, new Object[] {SHEET_0, currCell}));
            }
            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                // 同商品同状态 合并
                boolean bool = true;
                for (StaLineCommand temp : staLineTemp) {
                    if (sku != null && status != null && temp.getSku().getId().equals(sku.getId()) && temp.getInvStatus().getId().equals(status.getId())) {
                        bool = false;
                        temp.setQuantity(temp.getQuantity() + cmd.getQuantity());
                        break;
                    }
                }
                if (sku != null && status != null && bool) {
                    staLineTemp.add(cmd);
                }
            }
            offsetRow++;
        }
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        Order order = new Order();
        Date dt = new Date();
        Long time = dt.getTime();
        order.setCode(time + "");
        order.setMainWhOuId(ouId);
        order.setMemo(staComd.getMemo());
        order.setCustomerCode(cuo);
        order.setMainWhOuCode(wh.getOu().getCode());
        order.setOwner(staComd.getOwner());
        order.setType(type.getValue());
        order.setInvStatusId(staLineTemp.get(0).getInvStatus().getId());
        OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
        deliveryInfo.setAddress(staComd.getAddress());
        deliveryInfo.setTelephone(staComd.getTelephone());
        deliveryInfo.setMobile(staComd.getMobile());
        deliveryInfo.setReceiver(staComd.getReceiver());
        order.setDeliveryInfo(deliveryInfo);
        List<OrderLine> orderLinelist = new ArrayList<OrderLine>();
        for (StaLine line : staLineTemp) {
            OrderLine orderLine = new OrderLine();
            orderLine.setInvStatusId(line.getInvStatus().getId());
            orderLine.setOwner(order.getOwner());
            orderLine.setQty(line.getQuantity());
            orderLine.setSkuCode(line.getSku().getCode());
            orderLinelist.add(orderLine);

        }
        order.setLines(orderLinelist);
        order.setIsNotPacsomsOrder(true);
        com.jumbo.rmi.warehouse.BaseResult bs = rmiService.orderCreate(order);
        if (bs.getStatus() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {bs.getMsg()});
        }
        return rs;

    }



    public void createOperationOut(Order order) {
        StockTransApplication sta = new StockTransApplication();
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(order.getMainWhOuId());// 源头出库
        if (ou1 == null) {
            // 1.源仓库不存在
            throw new BusinessException(ErrorCode.START_OPERATION_UNIT_NOT_FOUNT);
        }
        OperationUnit ou2 = operationUnitDao.getByPrimaryKey(order.getAddWhOuId());// 目标出库
        if (ou2 == null) {
            // 2.目标仓库不存在
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        if (ou1.getId().equals(ou2.getId())) {
            // 3.源仓库和目标仓库一致
            throw new BusinessException(ErrorCode.EI_SOURCE_TO_WH_SAME);
        }
        if (!StringUtils.hasLength(order.getOwner())) {
            // 4.源店铺不存在
            throw new BusinessException(ErrorCode.START_OWNER_NOT_FOUNT, new Object[] {order.getOwner()});
        }
        String owner = warehouseShopRefDao.getShopInfoByWarehouseAndShopCode(ou1.getId(), order.getOwner(), new SingleColumnRowMapper<String>(String.class));
        if (owner == null) {
            // 5.源仓库和源店铺没有绑定
            throw new BusinessException(ErrorCode.EI_SOURCE_WH_SHOP_NOREF);
        }
        wareHouseManagerExe.validateBiChannelSupport(null, owner);
        if (order.getType() == StockTransApplicationType.TRANSIT_CROSS.getValue() || order.getType() == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue()) {
            if (!ou1.getParentUnit().getParentUnit().getId().equals(ou2.getParentUnit().getParentUnit().getId())) {
                // 6.源仓库和目标仓库不在同一公司下
            }
        }
        if (order.getType() == StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue() || order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
            if (!StringUtils.hasLength(order.getAddOwner())) {
                // 7.目标店铺不存在
                throw new BusinessException(ErrorCode.END_OWNER_NOT_FOUNT, new Object[] {order.getAddOwner()});
            }
            String owner1 = warehouseShopRefDao.getShopInfoByWarehouseAndShopCode(ou2.getId(), order.getAddOwner(), new SingleColumnRowMapper<String>(String.class));
            if (owner1 == null) {
                // 8.目标仓库和目标店铺没有绑定
                throw new BusinessException("");
            }
            if (order.getOwner().equals(order.getAddOwner())) {
                // 9.源店铺和目标店铺一致
            }
            if (order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
                if (ou1.getParentUnit().getParentUnit().getId().equals(ou2.getParentUnit().getParentUnit().getId())) {
                    // 10.源仓库和目标仓库在同一个公司下
                }
            }
        }

        if (order.getType() == StockTransApplicationType.DIFF_COMPANY_TRANSFER.getValue()) {
            InventoryStatus invStatus1 = inventoryStatusDao.getByPrimaryKeyAndOuId(order.getInvAddStatusId(), ou2.getParentUnit().getParentUnit().getId());
            if (invStatus1 == null) {
                // 12.目标仓库库存状态不正确
                throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
            } else {
                sta.setAddiStatus(invStatus1);
            }
        }

        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setCreator(null);
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(operationUnitDao.getByPrimaryKey(order.getMainWhOuId()));
        sta.setOwner(order.getOwner());
        sta.setMemo(order.getMemo());
        sta.setType(StockTransApplicationType.valueOf(order.getType()));
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse().getId());
        staDao.save(sta);
        StaDeliveryInfo di = new StaDeliveryInfo();

        di.setAddress(order.getDeliveryInfo().getAddress());
        di.setTelephone(order.getDeliveryInfo().getTelephone());
        di.setMobile(order.getDeliveryInfo().getMobile());
        di.setReceiver(order.getDeliveryInfo().getReceiver());
        di.setSta(sta);
        di.setId(sta.getId());
        staDeliveryInfoDao.save(di);
        sta.setStaDeliveryInfo(di);
        List<StaLine> staLine = new ArrayList<StaLine>();
        Long qty = 0L;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(order.getMainWhOuId());

        for (OrderLine line : order.getLines()) {
            InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKeyAndOuId(line.getInvStatusId(), ou1.getParentUnit().getParentUnit().getId());
            if (invStatus == null) {
                // 11.源仓库库存状态不对
                throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
            }
            StaLine stal = new StaLine();
            stal.setInvStatus(invStatus);
            stal.setOwner(sta.getOwner());
            stal.setQuantity(line.getQty());
            stal.setSku(skuDao.getByBarcode(line.getSkuCode(), customerId));
            stal.setSta(sta);
            qty += line.getQty();
            staLineDao.save(stal);
            staLine.add(stal);
        }
        sta.setSkuQty(qty);
        staDao.save(sta);
        staDao.flush();
        wareHouseManager.isInventoryNumber(sta.getId());
        // 库位占用
        wareHouseManager.predefinedOutOccupation(sta.getId(), null);
        staDao.updateSkuQtyById(sta.getId());
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
    }

    private String getMainBarCode(String barcode, Long customerId) {
        Sku skuData = skuDao.getByAddBarcode(barcode, customerId);
        if (null != skuData) {
            barcode = skuData.getBarCode();
        }
        return barcode;
    }

    private Map<String, InventoryCommand> validateInventoryInitializeExcel(Map<String, Long> stsMap, List<BiChannel> shopList, Map<String, Long> locMap, ReadStatus rs, String type, List<InventoryCommand> result, TransactionType tType, Long ouId,
            Long cmpOuId) {
        final ExcelSheet sheet = inventoryInitializeReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
        int intType;
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocks.get(1).getStartRow(), blocks.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {sheet.getDisplayName(), strCell}));
            return null;
        }
        String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
        int offsetRow = 0;
        // 检查EXCEL数据正确性
        Map<String, Long> skuMap = null;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        if (intType == 0) {
            skuMap = skuDao.findAllKeyCodeNotNeedSn(customerId, new MapRowMapper());
        } else {
            skuMap = skuDao.findAllKeyBarcodeNotNeedSn(customerId, new MapRowMapper());
        }
        Map<String, InventoryCommand> invMap = new HashMap<String, InventoryCommand>();// key
        // int storeMode = InboundStoreMode.RESPECTIVE.getValue();
        for (InventoryCommand inv : result) {
            boolean isError = false;
            // 库位检查
            Long locId = locMap.get(inv.getLocationCode());
            if (locId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_0, currCell, inv.getLocationCode()}));
                isError = true;
            } else {
                WarehouseLocation loc = new WarehouseLocation();
                loc.setId(locId);
                inv.setLocation(loc);
            }
            // sku检查
            Long skuId = skuMap.get(inv.getBarCode());
            if (skuId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, inv.getBarCode()}));
                isError = true;
            } else {
                Sku sku = skuDao.getByPrimaryKey(skuId);
                if (locId != null && InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) {
                    Long invCount = inventoryDao.findInventoryBySkuIdLocationId(skuId, locId, null, new SingleColumnRowMapper<Long>(Long.class));
                    if (invCount != null && invCount > 0) {// 单批隔离，有商品，不能存放
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_STOREMODE_ERROR, new Object[] {SHEET_0, currCell, type, inv.getBarCode(), inv.getLocationCode()}));
                        isError = true;
                    } else {
                        inv.setSku(sku);
                    }
                } else {
                    inv.setSku(sku);
                }
            }
            // 库存状态
            Long stsId = stsMap.get(inv.getInvStatusName());
            if (stsId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_0, currCell, inv.getInvStatusName()}));
                isError = true;
            } else {
                InventoryStatus sts = new InventoryStatus();
                sts.setId(stsId);
                inv.setStatus(sts);
            }
            // 店铺
            boolean isShop = false;
            for (BiChannel shop : shopList) {
                if (shop.getName().equals(inv.getInvOwner())) {
                    inv.setInvOwner(shop.getCode());
                    isShop = true;
                    break;
                }
            }
            if (!isShop) {
                isError = true;
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_0, currCell, inv.getInvOwner()}));
            }
            if (!isError) {
                String key = inv.getLocation().getId() + ":" + inv.getSku().getId() + ":" + inv.getStatus().getId() + ":" + inv.getInvOwner();
                InventoryCommand cmd = invMap.get(key);
                // 计算设置数量
                if (cmd == null) {
                    cmd = inv;
                    invMap.put(key, cmd);
                } else {
                    cmd.setQuantity(cmd.getQuantity() + inv.getQuantity());
                }
            }
            offsetRow++;
        }
        return invMap;
    }

    private Map<String, InventoryCommand> validateInventoryInitializeExcelSheet2(Map<String, Long> snMap, Map<String, Long> stsMap, List<BiChannel> shopList, Map<String, Long> locMap, ReadStatus rs, String type, List<InventoryCommand> result,
            TransactionType tType, Long ouId, Long cmpOuId) {
        final ExcelSheet sheet = inventoryInitializeReader.getDefinition().getExcelSheets().get(1);
        List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
        int intType;
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocks.get(1).getStartRow(), blocks.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {sheet.getDisplayName(), strCell}));
            return null;
        }
        String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
        int offsetRow = 0;
        // 检查EXCEL数据正确性
        Map<String, Long> skuMap = null;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        if (intType == 0) {
            skuMap = skuDao.findAllKeyCodeNeedSn(customerId, new MapRowMapper());
        } else {
            skuMap = skuDao.findAllKeyBarcodeNeedSn(customerId, new MapRowMapper());
        }
        Map<String, InventoryCommand> invMap = new HashMap<String, InventoryCommand>();// key
        // int storeMode = InboundStoreMode.RESPECTIVE.getValue();
        for (InventoryCommand inv : result) {
            boolean isError = false;
            // 库位检查
            Long locId = locMap.get(inv.getLocationCode());
            if (locId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_1, currCell, inv.getLocationCode()}));
                isError = true;
            } else {
                WarehouseLocation loc = new WarehouseLocation();
                loc.setId(locId);
                inv.setLocation(loc);
            }
            // sku检查
            Long skuId = skuMap.get(inv.getBarCode());
            if (skuId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_SN_NOT_FOUND, new Object[] {SHEET_1, currCell, inv.getBarCode()}));
                isError = true;
            } else {
                // Integer storemode = productDao.findSkuStoreMode(skuId, new
                // SingleColumnRowMapper<Integer>(Integer.class));
                Sku sku = skuDao.getByPrimaryKey(skuId);
                if (InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) {
                    Long invCount = inventoryDao.findInventoryBySkuIdLocationId(skuId, locId, null, new SingleColumnRowMapper<Long>(Long.class));
                    if (invCount != null && invCount > 0) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_STOREMODE_ERROR, new Object[] {SHEET_0, currCell, type, inv.getBarCode(), inv.getLocationCode()}));
                        isError = true;
                    } else {
                        inv.setSku(sku);
                    }
                } else {
                    inv.setSku(sku);
                }
            }
            // 库存状态
            Long stsId = stsMap.get(inv.getInvStatusName());
            if (stsId == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_1, currCell, inv.getInvStatusName()}));
                isError = true;
            } else {
                InventoryStatus sts = new InventoryStatus();
                sts.setId(stsId);
                inv.setStatus(sts);
            }
            // 店铺
            boolean isShop = false;
            for (BiChannel shop : shopList) {
                if (shop.getName().equals(inv.getInvOwner())) {
                    inv.setInvOwner(shop.getCode());
                    isShop = true;
                    break;
                }
            }
            if (!isShop) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                isError = true;
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_1, currCell, inv.getInvOwner()}));
            }
            if (!isError) {
                // 校验SN重复性
                int size = snMap.size();
                snMap.put(inv.getSn(), inv.getSku().getId());
                if (size == snMap.size()) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 5);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SN_EXISTS, new Object[] {SHEET_1, currCell, inv.getLocationCode()}));
                    isError = true;
                }
                String key = inv.getLocation().getId() + ":" + inv.getSku().getId() + ":" + inv.getStatus().getId() + ":" + inv.getInvOwner();
                InventoryCommand cmd = invMap.get(key);
                // 计算设置数量
                if (cmd == null) {
                    inv.setQuantity(1L);
                    cmd = inv;
                    invMap.put(key, cmd);
                } else {
                    cmd.setQuantity(cmd.getQuantity() + 1);
                }
            }
            offsetRow++;
        }
        return invMap;
    }

    /**
     * vmi POType - 导入
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importInvoicePercentage(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiInvoicePercentageReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            Date date = new Date();
            List<ESPInvoicePercentage> iplines = (List<ESPInvoicePercentage>) beans.get("InvoicePercentSheet");
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            ESPInvoicePercentage ip = null;
            for (ESPInvoicePercentage invoicePercent : iplines) {
                ip = ipDao.findByInvoice(invoicePercent.getInvoiceNum());
                if (ip == null) {
                    ip = new ESPInvoicePercentage();
                }
                ip.setVersion(date);
                ip.setPo(invoicePercent.getPo());
                ip.setInvoiceNum(invoicePercent.getInvoiceNum());
                ip.setDutyPercentage(invoicePercent.getDutyPercentage());
                ip.setMiscFeePercentage(invoicePercent.getMiscFeePercentage());
                ip.setCommPercentage(invoicePercent.getCommPercentage());
                ipDao.save(ip);
            }

        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus transitInnverImport(File file, String remork, Long ouId, Long userId, Long fileId) throws Exception {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        TransactionType tType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_INNER_OUT);
        if (tType == null) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANSIT_INNER_TRANSATION_TYPE_NOT_FOUND);
        }
        Map<String, Object> sheet1Bean = new HashMap<String, Object>();
        try {
            InventoryStatus iStatus = null;
            ReadStatus rs = transitInnerReader.readAll(new FileInputStream(file), sheet1Bean);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) sheet1Bean.get("type");
            List<InventoryCommand> outInv = (List<InventoryCommand>) sheet1Bean.get("inv");
            List<InventoryCommand> inInv = (List<InventoryCommand>) sheet1Bean.get("inv2");
            if ((outInv != null && outInv.size() > 1000) || (inInv != null && inInv.size() > 1000)) {
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_MORE_THAN_1000));
                rs.setStatus(-2);
                return rs;
            }

            iStatus = isDao.getByName("临近保质期", ou.getParentUnit().getParentUnit().getId());
            // 判断是否有该状态
            if (iStatus == null) {
                iStatus = getInventoryStatus(operationUnitDao.getByPrimaryKey(ou.getParentUnit().getParentUnit().getId()));
            }
            // 校验数据 以及出入库商品数量
            rs = validatetransitInner(rs, type, outInv, inInv, tType, ouId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            for (int i = 0; i < inInv.size(); i++) {
                InventoryCommand cmd = inInv.get(i);
                String code = null;
                if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
                    // 商品条码
                    code = cmd.getSku().getBarCode();
                }
                if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
                    // 商品编码
                    code = cmd.getSku().getCode();
                }
                // 验证是不是保质期商品
                if (cmd.getInvStatusName().equals("临近保质期")) {
                    if (cmd.getSku().getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_ERROR, new Object[] {type, code}));
                        return rs;
                    }
                }
                if (cmd.getSku().getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    // 验证生产日期和过期时间
                    rs = wareHouseManager.checkPoductionDateAndExpireDate(cmd.getPoductionDate(), cmd.getSexpireDate(), rs, code, cmd.getSku(), null, 0);
                    if (rs != null && rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                        return rs;
                    }
                }
            }
            List<StvLine> list = new ArrayList<StvLine>();
            for (InventoryCommand cmd : outInv) {
                StvLine l = new StvLine();
                l.setSku(cmd.getSku());
                l.setLocation(cmd.getLocation());
                l.setOwner(companyShopDao.getByName(cmd.getInvOwner()).getCode());
                if (cmd.getInvStatusName().equals("临近保质期")) {
                    l.setInvStatus(iStatus);
                } else {
                    l.setInvStatus(cmd.getStatus());
                }
                l.setQuantity(cmd.getQuantity());
                list.add(l);
            }

            StockTransApplication sta = wareHouseManager.createTransitInnerSta(true, null, remork, userId, ouId, list);
            List<StvLineCommand> stvlineList = new ArrayList<StvLineCommand>();
            List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));

            Map<String, List<StvLineCommand>> tmpMap = new HashMap<String, List<StvLineCommand>>();
            for (StvLineCommand cmd : outStvLine) {
                String key = cmd.getOwner() + ",-," + cmd.getSkuId() + ",-," + cmd.getIntInvstatus();
                List<StvLineCommand> val = tmpMap.get(key);
                if (val == null) {
                    val = new ArrayList<StvLineCommand>();
                }
                val.add(cmd);
                tmpMap.put(key, val);
            }
            for (int i = 0; i < inInv.size(); i++) {
                InventoryCommand cmd = inInv.get(i);
                cmd.setInvOwner(companyShopDao.getByName(cmd.getInvOwner()).getCode());
                String key = cmd.getInvOwner() + ",-," + cmd.getSku().getId() + ",-," + cmd.getStatus().getId();
                List<StvLineCommand> val = tmpMap.get(key);
                Long qty = cmd.getQuantity();
                // 是否保质期商品
                boolean isSM = cmd.getSku().getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT);
                if (val == null || val.size() == 0) throw new BusinessException((isSM ? ErrorCode.SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL : ErrorCode.INBOUND_BATCH_CODE_ERROR), new Object[] {cmd.getSku().getBarCode()});
                for (int j = 0; j < val.size(); j++) {
                    StvLineCommand stvl = val.get(j);
                    StvLineCommand l = new StvLineCommand();
                    l.setLocation(cmd.getLocation());
                    l.setLocationId(cmd.getLocation().getId());
                    l.setSku(cmd.getSku());
                    l.setSkuId(cmd.getSku().getId());
                    if (cmd.getInvStatusName().equals("临近保质期")) {
                        l.setInvStatus(iStatus);
                    } else {
                        l.setInvStatus(cmd.getStatus());
                    }
                    l.setOwner(cmd.getInvOwner());
                    l.setIntInvstatus(cmd.getStatus().getId());
                    l.setSkuCost(stvl.getSkuCost());
                    l.setBatchCode(stvl.getBatchCode());
                    l.setSku(cmd.getSku());
                    boolean isTrue = false;
                    if (isSM) {
                        whManagerProxy.setStvLineProductionDateAndExpireDate(l, cmd.getPoductionDate(), cmd.getSexpireDate());
                        String inExpireDate = formatDate.format(l.getExpireDate());
                        String outExpireDate = formatDate.format(stvl.getExpireDate());
                        if (inExpireDate.equals(outExpireDate)) {
                            l.setProductionDate(stvl.getProductionDate());
                            l.setValidDate(stvl.getValidDate());
                            isTrue = true;
                        }
                    } else {
                        isTrue = true;
                    }
                    if (isTrue) {
                        long quantity = 0l;
                        if (qty > stvl.getQuantity()) {
                            quantity = stvl.getQuantity();
                        } else {
                            quantity = qty;
                        }
                        qty -= quantity;
                        l.setQuantity(quantity);
                        stvl.setQuantity(stvl.getQuantity() - quantity);
                        stvlineList.add(l);
                        if (stvl.getQuantity() == 0l) {
                            val.remove(j--);
                            if (val.size() == 0) {
                                tmpMap.remove(key);
                            }
                        }
                        if (qty == 0l) {
                            break;
                        }
                    }
                }
                if (qty != 0l) {
                    throw new BusinessException((isSM ? ErrorCode.SYSTEM_SKU_SHELF_MANAGEMENT_IS_NULL : ErrorCode.INBOUND_BATCH_CODE_ERROR), new Object[] {cmd.getSku().getBarCode()});
                }
            }
            if (tmpMap.size() > 0) {
                throw new BusinessException(ErrorCode.INSKU_NOT_EQ_OUTSKU);
            }
            log.debug("executeTransitInner strat");
            wareHouseManagerExe.executeTransitInner(true, sta.getId(), stvlineList, userId);
            if (fileId != null) {
                int count = importFileLogDao.bindFileByStaCode(fileId, null, sta.getCode());
                if (count != 1) {
                    throw new BusinessException(ErrorCode.PDA_UPLOAD_FAILED);
                }
            }
            log.debug("executeTransitInner end");
            return rs;
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    private ReadStatus validateInventoryStatusChange(ReadStatus rs, String type, List<InventoryCommand> outInv, List<InventoryCommand> inInv, TransactionType tType, Long ouId) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
        final ExcelSheet sheetOut = inventoryStatusChangeReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();
        int intType;
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // 校验出库
        String strCell = ExcelUtil.getCellIndex(blocksOut.get(0).getStartRow(), blocksOut.get(0).getStartCol());
        Map<String, Long> map = new HashMap<String, Long>();
        int offsetRow = 0;
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        // 库存状态缓存
        Map<String, InventoryStatus> invStatusMap = new HashMap<String, InventoryStatus>();
        // 查询所有当前仓库库存状态
        Map<String, Long> stsMap = inventoryStatusDao.findAllByCmpOu(ou.getParentUnit().getParentUnit().getId(), new MapRowMapper());
        for (Entry<String, Long> ent : stsMap.entrySet()) {
            InventoryStatus sts = inventoryStatusDao.getByPrimaryKey(ent.getValue());
            invStatusMap.put(sts.getName(), sts);
        }
        // 库位缓存
        Map<String, WarehouseLocation> locatonMap = new HashMap<String, WarehouseLocation>();

        for (InventoryCommand cmd : outInv) {
            Sku sku = skuCache.get(cmd.getBarCode());
            if (sku == null) {
                if (intType == 1) {
                    sku = skuDao.getByBarcode(cmd.getBarCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(cmd.getBarCode(), customerId);
                        if (addSkuCode != null) {
                            sku = addSkuCode.getSku();
                        }
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getBarCode()}));
                    } else {
                        skuCache.put(cmd.getBarCode(), sku);
                    }
                } else if (intType == 0) {
                    sku = skuDao.getByCode(cmd.getBarCode());
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getBarCode()}));
                    } else {
                        skuCache.put(cmd.getBarCode(), sku);
                    }
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            }
            // 库位查询
            WarehouseLocation loc = locatonMap.get(cmd.getLocationCode());
            if (loc == null) {
                loc = warehouseLocationDao.findLocationByCode(cmd.getLocationCode(), ouId);
                if (loc != null) {
                    locatonMap.put(cmd.getLocationCode(), loc);
                }
            }
            if (loc == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getLocationCode()}));
            } else {
                cmd.setLocation(loc);
            }

            // 库存状态查询
            InventoryStatus invs = invStatusMap.get(cmd.getInvStatusName());
            if (invs == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 3);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getInvStatusName()}));
            } else {
                cmd.setStatus(invs);
            }
            if (cmd.getSku() != null) {
                String key = cmd.getSku().getId().toString();
                if (map.get(key) == null) {
                    map.put(key, cmd.getQuantity());
                } else {
                    map.put(key, map.get(key) + cmd.getQuantity());
                }
            }
            if (cmd.getQuantity() == null || cmd.getQuantity() < 0) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_QUANTITY_MINUS, new Object[] {SHEET_0, currCell}));
            }
            offsetRow++;
        }

        // 检验入库
        strCell = ExcelUtil.getCellIndex(SHEET_INBOUND_START_ROW, SHEET_INBOUND_START_COL);
        offsetRow = 0;
        // 单批隔离
        // int storeMode = InboundStoreMode.RESPECTIVE.getValue();
        for (InventoryCommand cmd : inInv) {
            Sku sku = skuCache.get(cmd.getBarCode());

            if (sku != null) {
                cmd.setSku(sku);
            } else {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getBarCode()}));
            }

            // 库位查询
            WarehouseLocation loc = locatonMap.get(cmd.getLocationCode());
            if (loc == null) {
                loc = warehouseLocationDao.findLocationByCode(cmd.getLocationCode(), ouId);
                if (loc != null) {
                    locatonMap.put(cmd.getLocationCode(), loc);
                }
            }
            if (loc == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getLocationCode()}));
            } else {
                cmd.setLocation(loc);
            }

            // 库存状态查询
            InventoryStatus invs = invStatusMap.get(cmd.getInvStatusName());
            if (invs == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 3);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getInvStatusName()}));
            } else {
                cmd.setStatus(invs);
            }

            if (sku != null) {
                String key = cmd.getSku().getId().toString();
                if (map.get(key) == null) {
                    map.put(key, -cmd.getQuantity());
                } else {
                    map.put(key, map.get(key) - cmd.getQuantity());
                }
            }
            if (cmd.getQuantity() == null || cmd.getQuantity() < 0) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_QUANTITY_MINUS, new Object[] {SHEET_0, currCell}));
            }
            offsetRow++;
        }

        // 校验出入库数量是否相等
        if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
            for (Entry<String, Long> ent : map.entrySet()) {
                if (ent.getValue().longValue() != 0L) {
                    String[] v = ent.getKey().split(Constants.STA_SKUS_SLIPT_STR);
                    Long skuId = Long.parseLong(v[0]);
                    Sku sku = skuDao.getByPrimaryKey(skuId);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_IN_OUT_IS_NOT_EQUALS_FOR_INV_STATUS_CHANGE, new Object[] {sku.getName()}));
                }
            }
        }
        return rs;
    }


    @SuppressWarnings("unchecked")
    public List<String> hoListImport(File trackingNoFile) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        trackingNoReaderForHandOverList.readSheet(new FileInputStream(trackingNoFile), 0, beans);
        List<Map<String, String>> result = (List<Map<String, String>>) beans.get("trackingNoList");
        Set<String> trackNoList = new HashSet<String>();
        for (Map<String, String> r : result) {
            if (StringUtils.hasText(r.get("trackingNo"))) trackNoList.add(r.get("trackingNo"));
        }
        return new ArrayList<String>(trackNoList);
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importRefreshPickingList(File file, Long plId, Long ouId, Long userId) throws FileNotFoundException {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        if (pl == null) {
            throw new BusinessException(ErrorCode.NOT_PICKING_LIST);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = pickingListReader.readSheet(new FileInputStream(file), 0, beans);
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        List<PickingListCommand> plc = (List<PickingListCommand>) beans.get("list");
        for (PickingListCommand comd : plc) {
            // try {
            StockTransApplication sta = staDao.findStaByCode(comd.getStaCode());
            wareHouseManager.salesCreatePage(ouId, userId, comd.getTrackingNo(), new BigDecimal(comd.getTemp()), sta == null ? null : sta.getId(), comd.getLpcode(), null, comd.getBarcode());
            // } catch (Exception e) {
            // log.error("ExcelReadManagerImpl.importRefreshPickingList error :" + e);
            // }
        }
        return rs;
    }


    public ReadStatus vmiReturnValidate(ReadStatus rs, List<StaLineCommand> stalinecmds, List<StaLine> stalines, Map<String, InventoryStatus> invmap, Map<String, WarehouseLocation> locationmap, Long cmpOuid, Long ouid, String type, String biCode,
            TransactionType transactionType, StockTransApplication sta, BiChannel shop) {
        int intType;
        final ExcelSheet sheetOut = vmiReturnImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();

        if (type == null) {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.SNS_SKU_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // sku信息校验


        validateVMIReturnSkuInfo(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ouid, intType, biCode, transactionType, sta, shop);

        return rs;
    }

    // es
    public ReadStatus vmiReturnValidate2(ReadStatus rs, List<StaLineCommand> stalinecmds, List<StaLine> stalines, Map<String, InventoryStatus> invmap, Map<String, WarehouseLocation> locationmap, Long cmpOuid, Long ouid, String type, String biCode,
            TransactionType transactionType, StockTransApplication sta) {
        int intType;
        final ExcelSheet sheetOut = vmiReturnImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();

        if (type == null) {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.SNS_SKU_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else if ("3".equals(type)) {
            intType = 3;// nike upc
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // sku信息校验
        validateVMIReturnSkuInfo2(rs, stalinecmds, stalines, invmap, locationmap, cmpOuid, ouid, intType, biCode, transactionType, sta);

        return rs;
    }

    // vmi esprit 退仓2
    private ReadStatus validateVMIReturnSkuInfo2(ReadStatus rs, List<StaLineCommand> sheet, List<StaLine> stalines, Map<String, InventoryStatus> invmap, Map<String, WarehouseLocation> locationmap, Long cmpOuid, Long ouid, int intType, String biCode,
            TransactionType transactionType, StockTransApplication sta) {
        boolean b = false;
        ChooseOption ch = chooseOptionDao.findByCategoryCode("OUTBOUND_SEQ");
        if (ch != null && ch.getOptionKey() != null) {
            b = true;
        }
        // Sku sku = null;
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        final ExcelSheet sheets = vmiReturnImportReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        String currCell = null;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouid);
        if (sheet.size() == 0) {
            rs.setStatus(-2);
            rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_ERROR, new Object[] {}));
        }
        boolean status = true;
        for (StaLineCommand cmd : sheet) {
            for (StaLineCommand cmd1 : sheet) {
                if (!cmd.getIntInvstatusName().equals(cmd1.getIntInvstatusName())) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.DOUBLE_INVSTATUS_ERROR));
                    status = false;
                    break;
                }
            }

            if (!status) {
                break;
            }
            InventoryStatus inventoryStatus = null;
            // sku校验
            if (cmd == null) {
                continue;
            }
            if ("1Nike官方旗舰店".equals(biCode) || "1NIKE中国官方商城".equals(biCode) || "1(UAT)NIKE店铺".equals(biCode) || "香港nike中国官方商城".equals(biCode) || "1NIKE-GLOBLE官方旗舰店".equals(biCode) || "5Nike-Global Swoosh 官方商城".equals(biCode)
                    || "2Nike-Global Swoosh 官方商城".equals(biCode) || "3Nike-Global Swoosh 官方商城".equals(biCode) || "Nike-Global Inline 官方商城".equals(biCode) || "5Nike-Global Inline官方商城".equals(biCode)) {
                if (cmd.getIntInvstatusName().equals("残次品") && sta.getImperfectType().equals("")) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.INVSTATUS_ERROR));
                    status = false;
                    break;
                }
            }

            if (StringUtil.isEmpty(cmd.getSkuCode())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, "", "SKU条码/编码"}));

            }
            if (StringUtil.isEmpty(cmd.getIntInvstatusName())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, "", "库存状态"}));
            }
            if (cmd.getQuantity() == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, "", "数量"}));
            }
            Sku sku = skuCache.get(cmd.getSkuCode());
            if (sku == null) {
                if (intType == 1) {// barcode
                    sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addskuCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addskuCode != null) {
                            sku = addskuCode.getSku();
                        }
                    }
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, "", cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else if (intType == 0) { // skucode
                    sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, "", cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else if (intType == 3) { // nike 定制 upc ext_code2
                    sku = skuDao.getByExtCode2AndCustomer(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, "", cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            } else {
                continue;
            }
            // 库存状态
            inventoryStatus = invmap.get(cmd.getIntInvstatusName());
            if (inventoryStatus == null) {
                inventoryStatus = inventoryStatusDao.findByName(cmd.getIntInvstatusName(), cmpOuid);
                if (inventoryStatus == null) {
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND_BY_NAME, new Object[] {SHEET_0, "", cmd.getIntInvstatusName()}));
                } else {
                    invmap.put(cmd.getIntInvstatusName(), inventoryStatus);
                    cmd.setInvStatus(inventoryStatus);
                }
            }
            // 库位
            // location = locationmap.get(cmd.getLocation());
            // if (location == null) {
            // location = warehouseLocationDao.findLocationByCode(cmd.getLocation(), ouid);
            // if (location == null) {
            // currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
            // rs.setStatus(-2);
            // rs.addException(new BusinessException(ErrorCode.EXCEL_LOCATION_NOT_FOUND, new
            // Object[] {SHEET_0, currCell, cmd.getLocation()}));
            // } else {
            // locationmap.put(cmd.getLocation(), location);
            //
            // }
            // }
            // cmd.setWarehouseLocation(location);

            // 数量
            if (cmd.getQuantity() <= 0) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_QUANTITY_IS_NEGATIVE, new Object[] {SHEET_0, ""}));
            }
            offsetRow++;
        }
        if (rs.getStatus() < 0) {
            return rs;
        }
        // sku 合并重复行
        Map<String, StaLineCommand> cache = new HashMap<String, StaLineCommand>();
        String key = null;
        for (StaLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            key = new StringBuilder(cmd.getSku().getBarCode() + "_" + cmd.getIntInvstatusName()).toString();
            if (cache.get(key) == null) {
                cache.put(key, cmd);
            } else {
                StaLineCommand stalineCmd = cache.get(key);
                stalineCmd.setQuantity(stalineCmd.getQuantity() + cmd.getQuantity()); // ??
            }
        }
        sheet.clear();
        sheet.addAll(cache.values());
        // 拆分库位占用库存
        for (int i = sheet.size() - 1; i >= 0; i--) {
            Long zQty = sheet.get(i).getQuantity();
            Sku sku = skuDao.getByPrimaryKey(sheet.get(i).getSku().getId());
            InventoryStatus inventoryStatus = inventoryStatusDao.findByName(sheet.get(i).getIntInvstatusName(), cmpOuid);
            Long qty = inventoryDao.checkVmiReturnSkuQty(biCode, sku.getId(), ouid, inventoryStatus.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (qty == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sheet.get(i).getSkuCode(), sheet.get(i).getQuantity()}));
                return rs;
            }
            if (sheet.get(i).getQuantity() > qty) {
                Long cy = sheet.get(i).getQuantity() - qty;
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sheet.get(i).getSkuCode(), cy}));
                return rs;
            }

            Long qtyOcp = inventoryDao.findQtyOccpInv(biCode, sku.getId(), ouid, new SingleColumnRowMapper<Long>(Long.class));// 数量占用的库存
            Long locationid = inventoryDao.getVmiReturnLoaction(biCode, sku.getId(), ouid, sheet.get(i).getQuantity(), inventoryStatus.getId(), transactionType.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (locationid != null && !b) {
                // 有足够库存直接分配库位
                WarehouseLocation location = warehouseLocationDao.getByPrimaryKey(locationid);
                sheet.get(i).setWarehouseLocation(location);
                sheet.get(i).setSku(sku);
                sheet.get(i).setInvStatus(inventoryStatus);
                locationmap.put(location.getCode(), location);
            } else {
                String name = sheet.get(i).getIntInvstatusName();
                sheet.remove(i);

                List<InventoryCommand> iList = null;
                if (b) {// 按仓库区域排序
                    iList = inventoryDao.findVmiReturnSkuQtyLocation2(biCode, sku.getId(), ouid, inventoryStatus.getId(), transactionType.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                } else {
                    // 没有单个足够库存库位拆分不同库位
                    iList = inventoryDao.findVmiReturnSkuQtyLocation(biCode, sku.getId(), ouid, inventoryStatus.getId(), transactionType.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                }

                if (inventoryStatus.getIsForSale() != null && inventoryStatus.getIsForSale() && qtyOcp != null && qtyOcp != 0 && iList != null && iList.size() > 0) {

                    Iterator<InventoryCommand> it = iList.iterator();
                    while (it.hasNext()) {
                        InventoryCommand x = it.next();
                        qtyOcp = qtyOcp - x.getQuantity();

                        if (qtyOcp > 0) {
                            it.remove();
                        } else if (qtyOcp == 0) {
                            it.remove();
                            break;
                        } else {
                            x.setQuantity(-qtyOcp);
                            break;
                        }
                    }
                }

                // 拆分库位
                for (InventoryCommand inv : iList) {
                    if (zQty <= 0) {
                        break;
                    }
                    WarehouseLocation location1 = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                    StaLineCommand sl = new StaLineCommand();
                    sl.setSku(sku);
                    sl.setWarehouseLocation(location1);
                    if (inv.getQuantity() > zQty) {
                        sl.setQuantity(zQty);
                    } else {
                        sl.setQuantity(inv.getQuantity());
                    }
                    sl.setInvStatus(inventoryStatus);
                    sl.setIntInvstatusName(name);
                    invmap.put(name, inventoryStatus);
                    locationmap.put(location1.getCode(), location1);
                    zQty = zQty - inv.getQuantity();
                    sheet.add(sl);
                }
                if (zQty > 0) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sku.getBarCode(), zQty}));
                    return rs;
                }
            }
        }
        return rs;
    }


    // vmi 退仓
    private ReadStatus validateVMIReturnSkuInfo(ReadStatus rs, List<StaLineCommand> sheet, List<StaLine> stalines, Map<String, InventoryStatus> invmap, Map<String, WarehouseLocation> locationmap, Long cmpOuid, Long ouid, int intType, String biCode,
            TransactionType transactionType, StockTransApplication sta, BiChannel shop) {
        boolean b = false;
        ChooseOption ch = chooseOptionDao.findByCategoryCode("OUTBOUND_SEQ");
        if (ch != null && ch.getOptionKey() != null) {
            b = true;
        }
        // Sku sku = null;
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        final ExcelSheet sheets = vmiReturnImportReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        String currCell = null;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouid);
        if (sheet.size() == 0) {
            rs.setStatus(-2);
            rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_ERROR, new Object[] {}));
        }
        boolean status = true;
        Integer skuNum = 0;
        for (StaLineCommand cmd : sheet) {
            for (StaLineCommand cmd1 : sheet) {
                if (!cmd.getIntInvstatusName().equals(cmd1.getIntInvstatusName())) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.DOUBLE_INVSTATUS_ERROR));
                    status = false;
                    break;
                }
            }
            skuNum++;
            if (!status) {
                break;
            }
            InventoryStatus inventoryStatus = null;
            // sku校验
            if (cmd == null) {
                continue;
            }
            if ("5香港NIKE官方商城".equals(biCode) || "1Nike官方旗舰店".equals(biCode) || "1NIKE中国官方商城".equals(biCode) || "1(UAT)NIKE店铺".equals(biCode) || "香港nike中国官方商城".equals(biCode) || "1NIKE-GLOBLE官方旗舰店".equals(biCode)
                    || "5Nike-Global Swoosh 官方商城".equals(biCode) || "2Nike-Global Swoosh 官方商城".equals(biCode) || "3Nike-Global Swoosh 官方商城".equals(biCode) || "Nike-Global Inline 官方商城".equals(biCode) || "5Nike-Global Inline官方商城".equals(biCode)) {
                if (cmd.getIntInvstatusName().equals("残次品") && sta.getImperfectType().equals("")) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.INVSTATUS_ERROR));
                    status = false;
                    break;
                }
            }

            if (StringUtil.isEmpty(cmd.getSkuCode())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, currCell, "SKU条码/编码"}));
            }
            if (StringUtil.isEmpty(cmd.getIntInvstatusName())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, currCell, "库存状态"}));
            }
            if (cmd.getQuantity() == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_SHEET_SKU_ERROR, new Object[] {SHEET_0, currCell, "数量"}));
            }
            Sku sku = skuCache.get(cmd.getSkuCode());
            if (sku == null) {
                if (intType == 1) {// barcode
                    sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addskuCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addskuCode != null) {
                            sku = addskuCode.getSku();
                        }
                    }
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else if (intType == 0) { // skucode
                    sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            } else {
                continue;
            }
            // 库存状态
            inventoryStatus = invmap.get(cmd.getIntInvstatusName());
            if (inventoryStatus == null) {
                inventoryStatus = inventoryStatusDao.findByName(cmd.getIntInvstatusName(), cmpOuid);
                if (inventoryStatus == null) {
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND_BY_NAME, new Object[] {SHEET_0, currCell, cmd.getIntInvstatusName()}));
                } else {
                    invmap.put(cmd.getIntInvstatusName(), inventoryStatus);
                    cmd.setInvStatus(inventoryStatus);
                }
            }
            // 库位
            // location = locationmap.get(cmd.getLocation());
            // if (location == null) {
            // location = warehouseLocationDao.findLocationByCode(cmd.getLocation(), ouid);
            // if (location == null) {
            // currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
            // rs.setStatus(-2);
            // rs.addException(new BusinessException(ErrorCode.EXCEL_LOCATION_NOT_FOUND, new
            // Object[] {SHEET_0, currCell, cmd.getLocation()}));
            // } else {
            // locationmap.put(cmd.getLocation(), location);
            //
            // }
            // }
            // cmd.setWarehouseLocation(location);

            // 数量
            if (cmd.getQuantity() <= 0) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_QUANTITY_IS_NEGATIVE, new Object[] {SHEET_0, currCell}));
            }
            offsetRow++;
        }

        if ("puma".equals(shop.getVmiCode()) && sta.getType() == StockTransApplicationType.VMI_RETURN) {
            Map<String, Long> map = new HashMap<String, Long>();
            List<VmiRtoLineCommand> rtoList = vmiRtoLineDao.findRtoLineListByRtoIdAndOrderCode(shop.getVmiCode(), sta.getRefSlipCode(), new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
            if (null != rtoList && rtoList.size() > 0) {
                if (rtoList.size() >= skuNum) {

                } else {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.PAMA_RTO_SKU_IS_NOT_EQUAL, new Object[] {SHEET_0}));
                }
                for (VmiRtoLineCommand list : rtoList) {
                    map.put(list.getUpc(), list.getNum());
                }
            } else {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.PAMA_RTO_IS_NOT_EQUAL));
            }

            if (null != map && !map.isEmpty()) { // 判断是否包含这些商品and 数量对不对上
                Map<String, Long> mapSheet = new HashMap<String, Long>();
                for (StaLineCommand cmd : sheet) {
                    if (mapSheet.get(cmd.getSku().getExtensionCode2()) == null) {
                        mapSheet.put(cmd.getSku().getExtensionCode2(), cmd.getQuantity());
                    } else {
                        Long qty = mapSheet.get(cmd.getSku().getExtensionCode2());
                        mapSheet.put(cmd.getSku().getExtensionCode2(), qty + cmd.getQuantity());
                    }
                }

                for (Map.Entry<String, Long> entry : mapSheet.entrySet()) {
                    if (map.containsKey(entry.getKey())) {
                        if (entry.getValue() > map.get(entry.getKey())) {
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.PAMA_EXCEL_QUANTITY_IS_NOT_EQUAL, new Object[] {SHEET_0, entry.getKey()}));
                        }
                    } else {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.PAMA_EXCEL_SKU_IS_NULL, new Object[] {SHEET_0, entry.getKey()}));
                    }
                }
            }
        }


        if (rs.getStatus() < 0) {
            return rs;
        }
        // sku 合并重复行
        Map<String, StaLineCommand> cache = new HashMap<String, StaLineCommand>();
        String key = null;
        for (StaLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            key = new StringBuilder(cmd.getSku().getBarCode() + "_" + cmd.getIntInvstatusName()).toString();
            if (cache.get(key) == null) {
                cache.put(key, cmd);
            } else {
                StaLineCommand stalineCmd = cache.get(key);
                stalineCmd.setQuantity(stalineCmd.getQuantity() + cmd.getQuantity()); // ??
            }
        }
        sheet.clear();
        sheet.addAll(cache.values());
        
        int sortSkuRow = 0;
        int skuRow = sheet.size();
        BusinessException be=null;
        
        // 拆分库位占用库存
        for (int i = sheet.size() - 1; i >= 0; i--) {
            Long zQty = sheet.get(i).getQuantity();
            Sku sku = skuDao.getByPrimaryKey(sheet.get(i).getSku().getId());
            InventoryStatus inventoryStatus = inventoryStatusDao.findByName(sheet.get(i).getIntInvstatusName(), cmpOuid);
            Long qty = inventoryDao.checkVmiReturnSkuQty(biCode, sku.getId(), ouid, inventoryStatus.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (qty == null) {
                if (shop.getIsPartOutbound() != null && shop.getIsPartOutbound()) {
                    qty=0L;
                    sortSkuRow++;
                    be = new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sheet.get(i).getSkuCode(), sheet.get(i).getQuantity()});
                    continue;
                }else {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sheet.get(i).getSkuCode(), sheet.get(i).getQuantity()}));
                    return rs;
                }
            }
            if (sheet.get(i).getQuantity() > qty) {
                if (shop.getIsPartOutbound() == null || !shop.getIsPartOutbound()) {
                    Long cy = sheet.get(i).getQuantity() - qty;
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sheet.get(i).getSkuCode(), cy}));
                    return rs;
                }
            }

            Long qtyOcp = inventoryDao.findQtyOccpInv(biCode, sku.getId(), ouid, new SingleColumnRowMapper<Long>(Long.class));// 数量占用的库存
            Long locationid = inventoryDao.getVmiReturnLoaction(biCode, sku.getId(), ouid, sheet.get(i).getQuantity(), inventoryStatus.getId(), transactionType.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (locationid != null && !b) {
                // 有足够库存直接分配库位
                WarehouseLocation location = warehouseLocationDao.getByPrimaryKey(locationid);
                sheet.get(i).setWarehouseLocation(location);
                sheet.get(i).setSku(sku);
                sheet.get(i).setInvStatus(inventoryStatus);
                locationmap.put(location.getCode(), location);
            } else {
                String name = sheet.get(i).getIntInvstatusName();
                sheet.remove(i);
                List<InventoryCommand> iList = null;
                if (b) {// 按仓库区域排序
                    iList = inventoryDao.findVmiReturnSkuQtyLocation2(biCode, sku.getId(), ouid, inventoryStatus.getId(), transactionType.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                } else {
                    // 没有单个足够库存库位拆分不同库位
                    iList = inventoryDao.findVmiReturnSkuQtyLocation(biCode, sku.getId(), ouid, inventoryStatus.getId(), transactionType.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                }

                if (inventoryStatus.getIsForSale() != null && inventoryStatus.getIsForSale() && qtyOcp != null && qtyOcp != 0 && iList != null && iList.size() > 0) {

                    Iterator<InventoryCommand> it = iList.iterator();
                    while (it.hasNext()) {
                        InventoryCommand x = it.next();
                        qtyOcp = qtyOcp - x.getQuantity();
                        if (qtyOcp > 0) {
                            it.remove();
                        } else if (qtyOcp == 0) {
                            it.remove();
                            break;
                        } else {
                            x.setQuantity(-qtyOcp);
                            break;
                        }
                    }
                }

                if (iList == null || iList.size() == 0) {
                    sortSkuRow++;
                    be = new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sku.getCode(), zQty});
                    continue;
                }

                // 拆分库位
                for (InventoryCommand inv : iList) {
                    if (zQty <= 0) {
                        break;
                    }
                    WarehouseLocation location1 = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                    StaLineCommand sl = new StaLineCommand();
                    sl.setSku(sku);
                    sl.setWarehouseLocation(location1);
                    if (inv.getQuantity() > zQty) {
                        sl.setQuantity(zQty);
                    } else {
                        sl.setQuantity(inv.getQuantity());
                    }
                    sl.setInvStatus(inventoryStatus);
                    sl.setIntInvstatusName(name);
                    invmap.put(name, inventoryStatus);
                    locationmap.put(location1.getCode(), location1);
                    zQty = zQty - inv.getQuantity();
                    sheet.add(sl);
                }
                if (zQty > 0) {
                    if (shop.getIsPartOutbound() != null && shop.getIsPartOutbound()) {
                        /*
                         * StaLineCommand sl = new StaLineCommand(); sl.setSku(sku);
                         * sl.setQuantity(zQty); sl.setInvStatus(inventoryStatus);
                         * sl.setIntInvstatusName(name); invmap.put(name, inventoryStatus);
                         * sheet.add(sl);
                         */
                    }else {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_SKU_INVENTORY_ERROR, new Object[] {SHEET_0, sku.getBarCode(), zQty}));
                        return rs;
                        
                    }
                }
            }
        }
        if (sortSkuRow >= skuRow) {
            rs.setStatus(-2);
            rs.addException(be);
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public void locationImport(Long districtId, File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("locations", new ArrayList<WarehouseLocation>());
        ReadStatus readStatus = null;
        try {
            readStatus = locationReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
        } finally {
            // ReadStatus.STATUS_SUCCESS == readStatus.getStatus()
            if (readStatus != null) {
                log.debug("......ReadStatus.getStatus=" + readStatus.getStatus());
                log.debug("......ReadStatus.getMessage=" + readStatus.getMessage());
            }
            List<WarehouseLocation> locations = (List<WarehouseLocation>) beans.get("locations");
            if (locations == null || locations.isEmpty()) {
                return;
            }
            WarehouseDistrict district = warehouseDistrictDao.getByPrimaryKey(districtId);
            locationImportValidate(district, locations);
        }
    }

    private boolean locationImportValidate(WarehouseDistrict district, List<WarehouseLocation> locations) {
        List<BusinessException> errors = new ArrayList<BusinessException>();
        Long index = 1L;
        Map<String, Map<String, Long>> locationMapCache = wareHouseLocationManager.findLocationCodeMapByWarehouse(district.getOu().getId());
        Map<String, Map<String, Long>> localCache = new HashMap<String, Map<String, Long>>();
        localCache.put(LOCATION_CODE, new HashMap<String, Long>());
        localCache.put(LOCATION_BAR_CODE, new HashMap<String, Long>());
        for (WarehouseLocation location : locations) {
            index++;
            byte b = 0;
            StringBuffer sysCompileCode = new StringBuffer();
            sysCompileCode.append(district.getCode());
            if (StringUtils.hasLength(location.getDimX())) {
                b |= 8;
                sysCompileCode.append(location.getDimX());
            }
            if (StringUtils.hasLength(location.getDimY())) {
                b |= 4;
                sysCompileCode.append("-").append(location.getDimY());
            }
            if (StringUtils.hasLength(location.getDimZ())) {
                b |= 2;
                sysCompileCode.append("-").append(location.getDimZ());
            }
            if (StringUtils.hasLength(location.getDimC())) {
                b |= 1;
                sysCompileCode.append("-").append(location.getDimC());
            }
            if ((b == 0 && StringUtils.hasLength(location.getBarCode())) || (b != 8 && b != 12 && b != 14 && b != 15)) {
                errors.add(new BusinessException(ErrorCode.WH_LOCATION_SYSCOMPILECODE_ERROR, new Object[] {index}));
            } else {
                location.setCode(sysCompileCode.toString());
                location.setSysCompileCode(sysCompileCode.toString());
                if (!wareHouseLocationManager.locationCodeBarCodeValidate(localCache, location, errors, index, true) | !wareHouseLocationManager.locationCodeBarCodeValidate(locationMapCache, location, errors, index, false)) {
                    continue;
                }
                localCache.get(LOCATION_CODE).put(location.getCode().toUpperCase(), index);
                if (StringUtils.hasLength(location.getBarCode())) {
                    localCache.get(LOCATION_BAR_CODE).put(location.getBarCode().toUpperCase(), index);
                }
                if (location.getPopUpAreaCode() != null && !"".equals(location.getPopUpAreaCode())) {
                    PopUpArea popUpArea = popUpAreaDao.getPopUpAreaByCodeAndOuid(location.getPopUpAreaCode());
                    if (popUpArea != null) {
                        location.setPopUpArea(popUpArea);
                    } else {
                        errors.add(new BusinessException(ErrorCode.WH_LOCATION_POPUPAREACODE_ERROR, new Object[] {location.getPopUpAreaCode()}));
                    }
                }
                if (errors.size() == 0) {
                    location.setDistrict(district);
                    location.setOu(district.getOu());
                    location.setIsLocked(false);
                    location.setLastModifyTime(new Date());
                    warehouseLocationDao.save(location);
                }
            }
        }
        businessExceptionPost(errors);
        return true;
    }

    /**
     * 校验 预定义 入库 导入
     * 
     * @param rs
     * @param sta
     * @param beans
     * @param mode
     */
    @SuppressWarnings("unchecked")
    private void staImportForPredefinedValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
        }

        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLines");
        List<StvLine> stvLinesSheet2 = (List<StvLine>) beans.get("stvLines2");

        if ((stvLines == null || stvLines.isEmpty()) && (stvLinesSheet2 == null || stvLinesSheet2.isEmpty())) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }

        List<StaLine> stalineList = staLineDao.findByStaId(sta.getId());
        Map<String, Long> staLBarCodeList = staLineDao.findStalAddBarcode(sta.getId(), new MapRowMapper());


        /***************************** 验证sheet1数据 ***************************************/
        Iterator<StvLine> it = stvLines.iterator();
        int index = 2;
        // 对于相同skuId分组
        Map<String, List<StvLine>> stvLineMap = new HashMap<String, List<StvLine>>();
        // 保存数量
        Map<String, Long> qtyMap = new HashMap<String, Long>();
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
                    if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode()) || stvLine.getQuantity() == null) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                        continue;
                    }

                    // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                    if (stvLine.getInvStatus() == null || !StringUtils.hasLength(stvLine.getInvStatus().getName())) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                        continue;
                    }

                    StaLine staLine = null;
                    for (StaLine sl : stalineList) {
                        boolean isInvStatus = sl.getInvStatus().getName().equals(stvLine.getInvStatus().getName());
                        boolean isBarCode = (sl.getSku().getBarCode() == null && !StringUtils.hasLength(stvLine.getSku().getBarCode())) || sl.getSku().getBarCode().equals(stvLine.getSku().getBarCode());
                        boolean isJMCode = sl.getSku().getJmCode().equals(stvLine.getSku().getJmCode());
                        boolean isKeyProperties = (sl.getSku().getKeyProperties() == null && !StringUtils.hasLength(stvLine.getSku().getKeyProperties())) || sl.getSku().getKeyProperties().equals(stvLine.getSku().getKeyProperties());
                        boolean isAddBarCode = false;
                        if (staLBarCodeList != null) {
                            Long stalid = staLBarCodeList.get(stvLine.getSku().getBarCode());
                            if (stalid != null && stalid.equals(sl.getId())) {
                                isAddBarCode = true;
                            }
                        }
                        if (isAddBarCode || (isInvStatus && isBarCode && isJMCode && isKeyProperties)) {
                            staLine = sl;
                            break;
                        }
                    }
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
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    stvLine.setLocation(location);
                    stvLine.setInvStatus(staLine.getInvStatus());
                    String key = staLine.getSku().getId() + "_" + stvLine.getInvStatus().getId();
                    if (stvLineMap.containsKey(key)) {
                        Long total = 0L;
                        for (StvLine each : stvLineMap.get(key)) {
                            total += each.getQuantity();
                        }
                        // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                        if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        stvLineMap.get(key).add(stvLine);
                    } else {
                        if (stvLine.getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        List<StvLine> stvLineList = new ArrayList<StvLine>();
                        stvLineList.add(stvLine);
                        stvLineMap.put(key, stvLineList);
                        qtyMap.put(key, stvLine.getStaLine().getQuantity());
                    }
                    stvLine.setStaLine(staLine);
                    stvLine.setSku(sku);
                }
            } else {
                it.remove();
            }
        }

        /***************************** 验证sheet2数据 ***************************************/
        Iterator<StvLine> it2 = stvLinesSheet2.iterator();
        int index2 = 1;
        // 对于相同skuId分组
        Map<String, List<StvLine>> stvLineMap2 = new HashMap<String, List<StvLine>>();
        // 对于StvLine按skuID进行分组
        while (it2.hasNext()) {
            StvLine stvLine = it2.next();
            if (stvLine == null) {
                continue;
            }
            // 1行记录1个SN号商品数量1
            stvLine.setQuantity(1L);
            index2++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it2.remove();
                    continue;
                } else {
                    StaLine staLine = null;
                    for (StaLine sl : stalineList) {
                        boolean isInvStatus = sl.getInvStatus().getName().equals(stvLine.getInvStatus().getName());
                        boolean isBarCode = (sl.getSku().getBarCode() == null && !StringUtils.hasLength(stvLine.getSku().getBarCode())) || sl.getSku().getBarCode().equals(stvLine.getSku().getBarCode());
                        boolean isJMCode = sl.getSku().getJmCode().equals(stvLine.getSku().getJmCode());
                        boolean isKeyProperties = (sl.getSku().getKeyProperties() == null && !StringUtils.hasLength(stvLine.getSku().getKeyProperties())) || sl.getSku().getKeyProperties().equals(stvLine.getSku().getKeyProperties());
                        if (isInvStatus && isBarCode && isJMCode && isKeyProperties) {
                            staLine = sl;
                            break;
                        }
                    }
                    if (staLine == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    stvLine.getStaLine().setId(staLine.getId());
                    stvLine.getStaLine().setSkuCost(staLine.getSkuCost());
                    // business_exception_10008=作业申请单行[{0}]条码[{1}]JMCode[{2}]实际本次执行量已经超出剩余待确认收货量
                    if (stvLine.getStaLine().getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                        errors.add(new BusinessException(ErrorCode.STA_QUANTITY_UNPLANNED, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                    if (sku.getIsSnSku() != null && sku.getIsSnSku() && !StringUtils.hasLength(stvLine.getSns())) {
                        errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    stvLine.setLocation(location);
                    stvLine.setInvStatus(staLine.getInvStatus());
                    String key = staLine.getSku().getId() + "_" + stvLine.getInvStatus().getId();
                    if (stvLineMap2.containsKey(key)) {
                        Long total = 0L;
                        for (StvLine each : stvLineMap2.get(key)) {
                            total += each.getQuantity();
                        }
                        // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                        if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        stvLineMap2.get(key).add(stvLine);
                    } else {
                        if (stvLine.getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        List<StvLine> stvLineList = new ArrayList<StvLine>();
                        stvLineList.add(stvLine);
                        stvLineMap2.put(key, stvLineList);
                        qtyMap.put(key, stvLine.getStaLine().getQuantity());
                    }
                    stvLine.setStaLine(staLine);
                    stvLine.setSku(sku);
                }
            } else {
                it2.remove();
            }
        }
        stvLineMap.putAll(stvLineMap2);
        for (Map.Entry<String, List<StvLine>> entry : stvLineMap.entrySet()) {
            StvLine stvLine = entry.getValue().get(0);
            Long total = qtyMap.get(stvLine.getSku().getId() + "_" + stvLine.getInvStatus().getId());
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

    /**
     * 校验 预定义 入库 导入
     * 
     * @param rs
     * @param sta
     * @param beans
     * @param mode
     */
    @SuppressWarnings("unchecked")
    private List<StvLine> staImportForrepairValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        List<StvLine> result = new ArrayList<StvLine>();
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
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
                            if ((stvLines == null || stvLines.isEmpty()) && !sl.getSku().getIsSnSku().equals(1)) {
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
                        // 是否是SN号商品
                        if (!sta.getType().equals(StockTransApplicationType.SERIAL_NUMBER_INBOUND) && sku.getIsSnSku() != null && sku.getIsSnSku()) {
                            errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getCode()}));
                            continue;
                        }
                        WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                        if (location == null) {// 库位编码不存在
                            // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        StvLine sl = new StvLine();
                        sl.setSku(sku);
                        try {
                            whManagerProxy.setStvLineProductionDateAndExpireDate(sl, stvLine.getStrPoductionDate(), stvLine.getStrExpireDate());
                        } catch (BusinessException e) {
                            errors.add(e);
                            continue;
                        }
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
                        sl.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                        sl.setQuantity(stvLine.getQuantity());
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
            Map<String, String> snMap = new HashMap<String, String>();
            Map<String, String> logMap = new HashMap<String, String>();
            if (sta.getType().equals(StockTransApplicationType.SAMPLE_INBOUND)) {
                List<SkuSnLogCommand> logCommands = skuSnLogDao.findSNBySlipcode1(sta.getSlipCode1(), new BeanPropertyRowMapperExt<SkuSnLogCommand>(SkuSnLogCommand.class));
                for (SkuSnLogCommand skuSnLogCommand : logCommands) {
                    logMap.put(skuSnLogCommand.getSn(), skuSnLogCommand.getBarcode());
                }
            }
            int index2 = 1;
            // 对于StvLine按skuID进行分组
            while (it2.hasNext()) {
                StvLineCommand stvLine = it2.next();
                if (stvLine == null) {
                    continue;
                }
                // 1行记录1个SN号商品数量1
                stvLine.setQuantity(1L);
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
                        WarehouseLocation loc = wareHouseManager.checkLocationByOuid(stvLine.getSku(), sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                        if (loc == null) {// 库位编码不存在
                            // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        // 判断SN是否重复
                        if (snMap.containsKey(stvLine.getSns())) {
                            errors.add(new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {index2, stvLine.getSns()}));
                        } else {
                            snMap.put(stvLine.getSns(), stvLine.getSku().getBarCode());
                        }
                        // 判断SN是否是出的SN
                        if (sta.getType().equals(StockTransApplicationType.SAMPLE_INBOUND)) {
                            if (logMap.containsKey(stvLine.getSns())) {
                                if (logMap.get(stvLine.getSns()).equals(stvLine.getSku().getBarCode())) {
                                    logMap.remove(stvLine.getSns());
                                } else {
                                    errors.add(new BusinessException(ErrorCode.SN_IS_NOT_OUT_SN, new Object[] {stvLine.getSku().getBarCode(), stvLine.getSns()}));
                                    continue;
                                }
                            } else {
                                errors.add(new BusinessException(ErrorCode.SN_IS_NOT_OUT_SN, new Object[] {stvLine.getSku().getBarCode(), stvLine.getSns()}));
                                continue;
                            }
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
                                try {
                                    whManagerProxy.setStvLineProductionDateAndExpireDate(sl, stvLine.getStrPoductionDate(), stvLine.getStrExpireDate());
                                } catch (BusinessException e) {
                                    errors.add(e);
                                    continue;
                                }
                                sl.setLocation(loc);
                                sl.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                                sl.setStaLine(staLine);
                                sl.setInvStatus(invStatus);
                                sl.setQuantity(stvLine.getQuantity());
                                sl.setSns(stvLine.getSns());
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
        // 判断数量是否与计划量一致
        if (errors.isEmpty()) {
            for (StaLine staLine : stalineList) {
                String key = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId();
                Long total = qtyMap.get(key);
                // 判断数量 不等于计划量
                if (total == null || !total.equals(staLine.getQuantity())) {
                    errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {staLine.getSku().getBarCode(), staLine.getSku().getJmCode()}));
                }
            }
        }
        if (!errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void staImportForPurchaseValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        // 已创建/部分完成才能导入
        if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus()) && !StockTransApplicationStatus.PARTLY_RETURNED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
        }
        // 前置单据错误
        if (!sta.getRefSlipCode().equals(beans.get("refSlipCode").toString())) {
            throw new BusinessException(ErrorCode.STA_PO_ERROR, new Object[] {sta.getRefSlipCode(), beans.get("refSlipCode").toString()});
        }
        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLines");
        List<StvLine> stvLinesSheet2 = (List<StvLine>) beans.get("stvLines2");

        if ((stvLines == null || stvLines.isEmpty()) && (stvLinesSheet2 == null || stvLinesSheet2.isEmpty())) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
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
                    if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode()) || stvLine.getQuantity() == null) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                        continue;
                    }
                    StaLine staLine = wareHouseManager.findStaLineByBarCodeOrCodeProps(stvLine.getSku(), sta.getId());
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
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    stvLine.setLocation(location);
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

        /***************************** 验证sheet2数据 ***************************************/
        Iterator<StvLine> it2 = stvLinesSheet2.iterator();
        int index2 = 1;
        // 对于相同skuId分组
        Map<Long, List<StvLine>> stvLineMap2 = new HashMap<Long, List<StvLine>>();
        // 对于StvLine按skuID进行分组
        while (it2.hasNext()) {
            StvLine stvLine = it2.next();
            if (stvLine == null) {
                continue;
            }
            // 1行记录1个SN号商品数量1
            stvLine.setQuantity(1L);
            index2++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it2.remove();
                    continue;
                } else {
                    StaLine staLine = wareHouseManager.findStaLineByBarCodeOrCodeProps(stvLine.getSku(), sta.getId());
                    if (staLine == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    stvLine.getStaLine().setId(staLine.getId());
                    stvLine.getStaLine().setSkuCost(staLine.getSkuCost());
                    // business_exception_10008=作业申请单行[{0}]条码[{1}]JMCode[{2}]实际本次执行量已经超出剩余待确认收货量
                    if (stvLine.getStaLine().getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                        errors.add(new BusinessException(ErrorCode.STA_QUANTITY_UNPLANNED, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                    if (sku.getIsSnSku() != null && sku.getIsSnSku() && !StringUtils.hasLength(stvLine.getSns())) {
                        errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index2, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    stvLine.setLocation(location);
                    if (stvLineMap2.containsKey(staLine.getSku().getId())) {
                        Long total = 0L;
                        for (StvLine each : stvLineMap2.get(staLine.getSku().getId())) {
                            total += each.getQuantity();
                        }
                        // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                        if ((total + stvLine.getQuantity()) > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        stvLineMap2.get(staLine.getSku().getId()).add(stvLine);
                    } else {
                        if (stvLine.getQuantity() > (staLine.getQuantity() - staLine.getCompleteQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index2, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        List<StvLine> stvLineList = new ArrayList<StvLine>();
                        stvLineList.add(stvLine);
                        stvLineMap2.put(staLine.getSku().getId(), stvLineList);
                    }
                    stvLine.setSku(sku);
                }
            } else {
                it2.remove();
            }
        }
        stvLineMap.putAll(stvLineMap2);
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

    // 入库
    @SuppressWarnings("unchecked")
    private void validateVMIImportInvCKSheet1(Map<String, Object> importBean, ReadStatus rs, InventoryCheck ic, List<InventoryCheckDifferenceLine> importData, Long companyId, Long whOUID, Long customerId) throws Exception {
        // 获取入库行
        List<InventoryCheckDifTotalLineCommand> lines = (List<InventoryCheckDifTotalLineCommand>) importBean.get("vmiAdjustImportInbound");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        if (ic == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Map<String, Long> skuMap = skuDao.findVmiAdjSku(ic.getId(), new MapRowMapper());
        Map<String, Long> skuQuantity = vmiinvCheckLineDao.findVmiAdjSkuQuantity(ic.getId(), Boolean.TRUE, new MapRowMapper());
        final ExcelSheet sheet0 = vmiAdjustmentImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> sheet0Blocks = sheet0.getSortedExcelBlocks();
        String sheet0strCell = ExcelUtil.getCellIndex(sheet0Blocks.get(0).getStartRow(), sheet0Blocks.get(0).getStartCol());
        int sheet0OffsetRow = 0;
        Map<String, Long> reQuantity = new HashMap<String, Long>();
        InventoryCheckDifferenceLine icdifference = null;
        Map<String, Long> skuidCache = new HashMap<String, Long>();
        for (InventoryCheckDifTotalLineCommand line : lines) {
            String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
            String locationCode = line.getLocationCode();
            Sku sku = null;
            String invStatus = line.getInventoryStatusName();
            InventoryStatus is = null;
            if (invStatus == null || invStatus.equals("")) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST, new Object[] {SHEET_0, currCell, invStatus}));
            } else {
                is = inventoryStatusDao.findByName(invStatus, companyId);
            }
            if (is == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST, new Object[] {SHEET_0, currCell, invStatus}));
            }
            WarehouseLocation location = null;
            if (locationCode == null || locationCode.equals("")) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_NOLOCATION, new Object[] {SHEET_0, currCell, locationCode}));
            } else {
                location = warehouseLocationDao.findLocationByCode(line.getLocationCode(), ic.getOu().getId());
            }
            if (location == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_LOCATION_NOT_EXIST, new Object[] {SHEET_0, currCell, locationCode}));
            } else if (location.getIsLocked() != null && location.getIsLocked()) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_LOCKED, new Object[] {SHEET_0, currCell, locationCode}));
            }
            Long skuId = skuMap.get(line.getSkuBarCode());
            if (skuId == null) {
                skuId = skuidCache.get(line.getSkuBarCode());
                if (skuId == null) {
                    SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(line.getSkuBarCode(), customerId);
                    if (addSkuCode != null) {
                        skuId = addSkuCode.getSku().getId();
                        skuidCache.put(line.getSkuBarCode(), skuId);
                    }
                }
            }
            if (skuId == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_NOT_FOUND, new Object[] {SHEET_0, currCell, line.getSkuBarCode()}));
            } else {
                sku = skuDao.getByPrimaryKey(skuId);
            }
            if (sku == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_NOT_FOUND, new Object[] {SHEET_0, currCell, line.getSkuBarCode()}));
            }

            // 验证商品对应客户是否=仓库对应客户
            if (sku.getCustomer().getId() != customerId) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.SKU_WAREHOUSE_CUSTOMER_ERROR, new Object[] {"JMSKUCODE:", line.getSkuCode()}));
            }

            // 验证是不是保质期商品
            if (line.getInventoryStatusName().equals("临近保质期")) {
                if (sku.getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_TYPE_ERROR, new Object[] {"JMSKUCODE:", line.getSkuCode()}));
                }
            }
            rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), line.getSexpireDate(), rs, line.getSkuCode(), sku, null, 0);
            if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                // 如果是保质期商品,需要验证填入过期时间是不是和库存内过期时间相同
                List<Inventory> in = inventoryDao.findSkuLocationOuIdOwner(whOUID, location.getId(), sku.getId(), new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
                if (in.size() > 0) {
                    if (!StringUtil.isEmpty(line.getSexpireDate())) {
                        rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), line.getSexpireDate(), rs, line.getSkuCode(), sku, in.get(0).getExpireDate(), 1);
                    }
                    if (!StringUtil.isEmpty(line.getPoductionDate()) && StringUtil.isEmpty(line.getSexpireDate())) {
                        c.setTime(formatDate.parse(line.getPoductionDate())); // 设置生成日期
                        c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                        rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), formatDate.format(c.getTime()), rs, line.getSkuCode(), sku, in.get(0).getExpireDate(), 1);
                    }
                }
            }

            Long qty = reQuantity.get(line.getSkuBarCode());
            if (qty == null) {
                reQuantity.put(line.getSkuBarCode(), line.getQuantity());
            } else {
                reQuantity.put(line.getSkuBarCode(), qty + line.getQuantity());
            }
            icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(sku);
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(is);
            if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                // 保质期商品需要插入生产日期和过期时间
                if (!StringUtil.isEmpty(line.getSexpireDate())) {
                    if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                        String dataStr = (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? line.getSexpireDate().substring(0, 6) + "01" : line.getSexpireDate().substring(0, 4) + "0101");
                        icdifference.setExpireDate(formatDate.parse(dataStr));
                    } else {
                        icdifference.setExpireDate(formatDate.parse(line.getSexpireDate()));
                    }
                }
                if (!StringUtil.isEmpty(line.getPoductionDate()) && StringUtil.isEmpty(line.getSexpireDate())) {
                    c.setTime(formatDate.parse(line.getPoductionDate())); // 设置生成日期
                    c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                    if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                        SimpleDateFormat tempFD = new SimpleDateFormat(TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "yyyyMM" : "yyyy");
                        String dataStr = tempFD.format(c.getTime()) + (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "01" : "0101");
                        icdifference.setExpireDate(formatDate.parse(dataStr));
                    } else {
                        icdifference.setExpireDate(c.getTime());
                    }
                    icdifference.setProductionDate(formatDate.parse(line.getPoductionDate()));
                }
            }
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                BiChannel shop = ic.getShop();
                icdifference.setOwner(shop.getCode());
            }
            importData.add(icdifference);

            sheet0OffsetRow++;
        }
        if (skuQuantity != null) {
            if (reQuantity.size() != skuQuantity.size()) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_QUANTITY_NOT_SAME, new Object[] {}));
            }
            for (Entry<String, Long> ent : skuQuantity.entrySet()) {
                Long qty = reQuantity.get(ent.getKey());
                if (qty == null || !qty.equals(ent.getValue())) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_QUANTITY_NOT_SAME, new Object[] {ent.getKey()}));
                }
            }
        }
    }

    /**
     * vmi POType - 导入
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importEspritPoType(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiPOTypeReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            Date date = new Date();
            List<ESPPoTypeCommand> poTypelines = (List<ESPPoTypeCommand>) beans.get("POTypeSheet");
            ESPPoType pt = null;
            if (poTypelines != null && (!poTypelines.isEmpty())) {
                rs = vmiEspritPOTypeValidate(rs, poTypelines);
            }
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }

            for (ESPPoTypeCommand poType : poTypelines) {
                if (!StringUtils.hasText(poType.getPo())) {
                    continue;
                }
                if (!StringUtils.hasText(poType.getTypeName())) {
                    continue;
                }
                Integer typeNum = ESPPoType.ESPRIT_PO_TYPE.get(poType.getTypeName());
                if (typeNum == null) {
                    continue;
                }
                EspritOrderPOType type = EspritOrderPOType.valueOf(typeNum);
                if (type == null) {
                    continue;
                }
                if (!StringUtils.hasText(poType.getPoStyle())) {
                    continue;
                }
                String poInv = poType.getPoStyle().substring((poType.getPoStyle().length() > 2) ? (poType.getPoStyle().length() - 2) : 0);
                pt = potypeDao.findByPo(poType.getPo());
                if (pt == null) {
                    pt = new ESPPoType();
                }
                pt.setPoStyle(poType.getPoStyle());
                pt.setVersion(date);
                pt.setPo(poType.getPo());
                pt.setType(type);
                pt.setInvoiceNumber(null);
                pt.setDutyPercentage(null);
                pt.setMiscFeePercentage(null);
                pt.setCommPercentage(null);
                if (EspritOrderPOType.DIRECTSEND.equals(type)) {
                    pt.setInvoiceNumber(pt.getPo() + poInv);
                    pt.setDutyPercentage(new Double(0));
                    pt.setMiscFeePercentage(Double.valueOf("4.00"));
                    pt.setCommPercentage(new Double(0));
                } else if (EspritOrderPOType.NOTDIRECTSEND.equals(type)) {
                    pt.setInvoiceNumber(pt.getPo() + poInv);
                    pt.setDutyPercentage(Double.valueOf("2.86"));
                    pt.setMiscFeePercentage(Double.valueOf("4.00"));
                    pt.setCommPercentage(new Double(0));
                } else if (EspritOrderPOType.IMPORT.equals(type)) {
                    // 须要
                } else if (EspritOrderPOType.SPECIAL.equals(type)) {
                    pt.setInvoiceNumber(pt.getPo());
                    pt.setDutyPercentage(new Double(0));
                    pt.setMiscFeePercentage(Double.valueOf("4.00"));
                    pt.setCommPercentage(new Double(0));
                } else if (EspritOrderPOType.DALIAN_MENTION.equals(type)) {
                    pt.setInvoiceNumber(pt.getPo() + poInv);
                    pt.setDutyPercentage(Double.valueOf("2.86"));
                    pt.setMiscFeePercentage(Double.valueOf("4.00"));
                    pt.setCommPercentage(new Double(0));
                }
                potypeDao.save(pt);
            }

        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }



    // vmi - 调整sku校验
    private ReadStatus vmiEspritPOTypeValidate(ReadStatus rs, List<ESPPoTypeCommand> typeList) {
        final ExcelSheet sheets = vmiAdjustInventoryReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;

        for (ESPPoTypeCommand pt : typeList) {
            if (pt.getPo() == null || pt.getPo().equals("")) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.VMI_INBOUND_PO_NUM_ERROR, new Object[] {currCell}));
                continue;
            }
            if (pt.getTypeName() == null) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.VMI_INBOUND_PO_TYPE_ERROR, new Object[] {currCell, pt.getTypeName()}));
                continue;
            } else {
                if (!pt.getTypeName().equals(ESPPoTypeCommand.ESPRIT_PO_TYPE_1) && !pt.getTypeName().equals(ESPPoTypeCommand.ESPRIT_PO_TYPE_3) && !pt.getTypeName().equals(ESPPoTypeCommand.ESPRIT_PO_TYPE_5)
                        && !pt.getTypeName().equals(ESPPoTypeCommand.ESPRIT_PO_TYPE_7) && !pt.getTypeName().equals(ESPPoTypeCommand.ESPRIT_PO_TYPE_9)) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.VMI_INBOUND_PO_TYPE_ERROR, new Object[] {currCell, pt.getTypeName()}));
                    continue;
                }
            }
        }
        return rs;
    }

    public ReadStatus vmiAdjustmentImport(File file, Long invCkId, Long companyId, Long whOUID) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!(InventoryCheckStatus.CREATED.equals(ic.getStatus()) || InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus()))) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        Map<String, Object> bean = new HashMap<String, Object>();
        List<InventoryCheckDifferenceLine> importData = new ArrayList<InventoryCheckDifferenceLine>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(whOUID);
        try {
            ReadStatus rs = vmiAdjustmentImportReader.readAll(new FileInputStream(file), bean);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            /*********************** 核对数据 ********************************************************/
            validateVMIImportInvCKSheet1(bean, rs, ic, importData, companyId, whOUID, customerId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXECL_SHEET_ERROR, new Object[] {SHEET_0}));
                return rs;
            }
            validateVMIImportInvCKSheet2(bean, rs, ic, importData, companyId, whOUID, customerId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXECL_SHEET_ERROR, new Object[] {SHEET_1}));
                return rs;
            }
            // 核对无错误执行操作
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                // 删除上次导入
                List<InventoryCheckDifferenceLine> dlist = inventoryCheckDifferenceLineDao.findByInventoryCheck(ic.getId());
                for (InventoryCheckDifferenceLine l : dlist) {
                    inventoryCheckDifferenceLineDao.delete(l);
                }
                mergeVMIInvCKDifLine(importData);
                // 删除上次占用
                inventoryDao.updateOccupyByCode(ic.getCode());
                // ======================
                // 更新盘点批状态
                ic.setStatus(InventoryCheckStatus.UNEXECUTE);
                inventoryCheckDao.save(ic);
                /************************************** 修改逻辑 直接占用库存 *************************************/
                inventoryCheckDao.flush();
                TransactionType inType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_INBOUND_CONSIGNMENT);
                if (inType == null) {
                    throw new BusinessException(ErrorCode.TRANSACTION_TYPE_VMI_INVENTORY_CHECK_IN_NOT_FOUND);
                }
                TransactionType outType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_OUTBOUND_CONSIGNMENT);
                if (outType == null) {
                    throw new BusinessException(ErrorCode.TRANSACTION_TYPE_VMI_INVENTORY_CHECK_OUT_NOT_FOUND);
                }
                Map<String, Object> invparams = new HashMap<String, Object>();
                invparams.put("in_ic_id", ic.getId());
                invparams.put("in_cmp_id", ic.getOu().getParentUnit().getParentUnit().getId());
                SqlOutParameter s = new SqlOutParameter("rst", Types.NUMERIC);
                SqlParameter[] invSqlP = {new SqlParameter("in_ic_id", Types.NUMERIC), new SqlParameter("in_cmp_id", Types.NUMERIC), s};
                Map<String, Object> result = staDao.executeSp("sp_vmi_adj_inv_occp", invSqlP, invparams);
                BigDecimal rst = (BigDecimal) result.get("rst");
                if (rst != null && !rst.equals(BigDecimal.ZERO)) {
                    throw new BusinessException(ErrorCode.VMI_INV_CHECK_CONFIRM_NUM_NOT_SAME);
                }
                //同步IM
                hubWmsService.insertOccupiedAndReleaseByCheck(ic.getId());
                /************************************** 修改逻辑 直接占用库存 *************************************/
            }
            return rs;
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                log.error("", e);
                throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
            }
            // throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    // 出库
    @SuppressWarnings("unchecked")
    private void validateVMIImportInvCKSheet2(Map<String, Object> importBean, ReadStatus rs, InventoryCheck ic, List<InventoryCheckDifferenceLine> importData, Long companyId, Long whOUID, Long customerId) throws Exception {
        // 获取出库行
        List<InventoryCheckDifTotalLineCommand> lines = (List<InventoryCheckDifTotalLineCommand>) importBean.get("vmiAdjustImportOutbound");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        if (ic == null) {
            log.error("validateVMIImportInvCKSheet2 InventoryCheck is null");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Map<String, Long> skuMap = skuDao.findVmiAdjSku(ic.getId(), new MapRowMapper());
        Map<String, Long> skuQuantity = vmiinvCheckLineDao.findVmiAdjSkuQuantity(ic.getId(), Boolean.FALSE, new MapRowMapper());
        final ExcelSheet sheet0 = vmiAdjustmentImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> sheet0Blocks = sheet0.getSortedExcelBlocks();
        String sheet0strCell = ExcelUtil.getCellIndex(sheet0Blocks.get(0).getStartRow(), sheet0Blocks.get(0).getStartCol());
        int sheet0OffsetRow = 0;
        Map<String, Long> reQuantity = new HashMap<String, Long>();
        InventoryCheckDifferenceLine icdifference = null;
        Map<String, Long> skuidCache = new HashMap<String, Long>();
        for (InventoryCheckDifTotalLineCommand line : lines) {

            String locationCode = line.getLocationCode();
            WarehouseLocation location = null;
            if (locationCode == null || locationCode.equals("")) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_NOLOCATION, new Object[] {SHEET_0, locationCode}));
            } else {
                location = warehouseLocationDao.findLocationByCode(line.getLocationCode(), ic.getOu().getId());
            }
            if (location == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_LOCATION_NOT_EXIST, new Object[] {SHEET_0, locationCode}));
            } else if (location.getIsLocked() != null && location.getIsLocked()) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_LOCKED, new Object[] {SHEET_0, locationCode}));
            }

            String invStatus = line.getInventoryStatusName();
            InventoryStatus is = null;
            Long isId = null;
            if (invStatus == null || invStatus.equals("")) {
                String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST, new Object[] {SHEET_0, currCell, invStatus}));
            } else {
                is = inventoryStatusDao.findByName(invStatus, companyId);
            }
            if (is == null) {
                String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST, new Object[] {SHEET_0, currCell, invStatus}));
            } else {
                isId = is.getId();
            }

            Sku sku = null;
            Long skuId = skuMap.get(line.getSkuBarCode());
            if (skuId == null) {
                skuId = skuidCache.get(line.getSkuBarCode());
                if (skuId == null) {
                    SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(line.getSkuBarCode(), customerId);
                    if (addSkuCode != null) {
                        skuId = addSkuCode.getSku().getId();
                        skuidCache.put(line.getSkuBarCode(), skuId);
                    }
                }
            }
            if (skuId == null) {
                String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_NOT_FOUND, new Object[] {SHEET_0, currCell, line.getSkuBarCode()}));
            } else {
                sku = skuDao.getByPrimaryKey(skuId);
            }
            BigDecimal skuCount = null;
            if (sku == null) {
                String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_NOT_FOUND, new Object[] {SHEET_0, currCell, line.getSkuBarCode()}));
            } else {
                skuCount = inventoryDao.findInventoryBySkuIdandLocationIdandInvStatus(skuId, line.getLocationCode(), whOUID, isId, new SingleColumnRowMapper<Long>());
            }

            if (skuCount == null || skuCount.longValue() < line.getQuantity()) {
                String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_LACK, new Object[] {SHEET_0, currCell, line.getLocationCode(), line.getSkuBarCode(), invStatus}));
            }

            // 验证商品对应客户是否=仓库对应客户
            if (sku.getCustomer().getId() != customerId) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.SKU_WAREHOUSE_CUSTOMER_ERROR, new Object[] {"JMSKUCODE:", line.getSkuCode()}));
            }

            // 验证是不是保质期商品
            if (line.getInventoryStatusName().equals("临近保质期")) {
                if (sku.getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_TYPE_ERROR, new Object[] {"JMSKUCODE:", line.getSkuCode()}));
                }
            }
            rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), line.getSexpireDate(), rs, line.getSkuCode(), sku, null, 0);
            if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                // 如果是保质期商品,需要验证填入过期时间是不是和库存内过期时间相同
                List<Inventory> in = inventoryDao.findSkuLocationOuIdOwner(whOUID, location.getId(), sku.getId(), new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
                if (in.size() > 0) {
                    if (!StringUtil.isEmpty(line.getSexpireDate())) {
                        rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), line.getSexpireDate(), rs, line.getSkuCode(), sku, in.get(0).getExpireDate(), 1);
                    }
                    if (!StringUtil.isEmpty(line.getPoductionDate()) && StringUtil.isEmpty(line.getSexpireDate())) {
                        c.setTime(formatDate.parse(line.getPoductionDate())); // 设置生成日期
                        c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                        rs = wareHouseManager.checkPoductionDateAndExpireDate(line.getPoductionDate(), formatDate.format(c.getTime()), rs, line.getSkuCode(), sku, in.get(0).getExpireDate(), 1);
                    }
                } else {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.INVENTORY_NULL_ERROR, new Object[] {SHEET_1 + " JMSKUCODE:" + line.getSkuCode() + " 库位编码:" + line.getLocationCode()}));
                }
            }
            Long qty = reQuantity.get(line.getSkuBarCode());
            if (qty == null) {
                reQuantity.put(line.getSkuBarCode(), line.getQuantity());
            } else {
                reQuantity.put(line.getSkuBarCode(), qty + line.getQuantity());
            }
            icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(sku);
            icdifference.setQuantity(0 - line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(is);
            if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                // 保质期商品需要插入生产日期和过期时间
                if (!StringUtil.isEmpty(line.getSexpireDate())) {
                    if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                        String dataStr = (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? line.getSexpireDate().substring(0, 6) + "01" : line.getSexpireDate().substring(0, 4) + "0101");
                        icdifference.setExpireDate(formatDate.parse(dataStr));
                    } else {
                        icdifference.setExpireDate(formatDate.parse(line.getSexpireDate()));
                    }
                }
                if (!StringUtil.isEmpty(line.getPoductionDate()) && StringUtil.isEmpty(line.getSexpireDate())) {
                    c.setTime(formatDate.parse(line.getPoductionDate())); // 设置生成日期
                    c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                    if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                        SimpleDateFormat tempFD = new SimpleDateFormat(TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "yyyyMM" : "yyyy");
                        String dataStr = tempFD.format(c.getTime()) + (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "01" : "0101");
                        icdifference.setExpireDate(formatDate.parse(dataStr));
                    } else {
                        icdifference.setExpireDate(c.getTime());
                    }
                    icdifference.setProductionDate(formatDate.parse(line.getPoductionDate()));
                }
            }
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                BiChannel shop = ic.getShop();
                icdifference.setOwner(shop.getCode());
            }
            importData.add(icdifference);

            sheet0OffsetRow++;
        }
        if (skuQuantity != null && reQuantity != null) {
            if (reQuantity.size() != skuQuantity.size()) {
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_QUANTITY_NOT_SAME, new Object[] {}));
            }
            for (Entry<String, Long> ent : skuQuantity.entrySet()) {
                Long qty = reQuantity.get(ent.getKey());
                if (qty == null || qty + ent.getValue() != 0) {
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_SKU_QUANTITY_NOT_SAME, new Object[] {ent.getKey()}));
                }
            }
        }

    }

    @SuppressWarnings({"unchecked"})
    private void validateImporyInvCkSheet0(Map<String, Object> importBean, Map<String, Long> locMap, ReadStatus rs, InventoryCheck ic, Map<String, Long> importData, Long ouId, Map<String, String> importDataToSM) throws Exception {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        List<InventoryCommand> invList = (List<InventoryCommand>) importBean.get("inventory");
        // ***********************校验无SN号SHEET******************************//
        // 查询盘点关联商品
        Map<String, Long> skuNoSnMap = null;
        if (invList != null && invList.size() != 0) {
            // skuNoSnMap = skuDao.findAllKeyCodeByOuWithSn(ic.getOu().getId(), false, new
            // MapRowMapper());
            skuNoSnMap = skuDao.findAllKeyCodeByOuWithSn(ic.getOu().getId(), ic.getId(), null, new MapRowMapper());
        }
        if (skuNoSnMap == null) {
            skuNoSnMap = new HashMap<String, Long>();
        }
        final ExcelSheet sheet0 = inventoryCheckImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> sheet0Blocks = sheet0.getSortedExcelBlocks();
        String sheet0strCell = ExcelUtil.getCellIndex(sheet0Blocks.get(0).getStartRow(), sheet0Blocks.get(0).getStartCol());
        int sheet0OffsetRow = 0;
        if (invList != null) {
            for (InventoryCommand cmd : invList) {
                // 校验库位
                Long locId = locMap.get(cmd.getLocationCode());
                if (locId == null) {
                    rs.setStatus(-1);
                    String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 0);
                    rs.getExceptions().add(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_CHECK_NO_LOCATION, new Object[] {SHEET_0, currCell, cmd.getLocationCode(), ic.getCode()}));
                } else {
                    WarehouseLocation loc = new WarehouseLocation();
                    loc.setId(locId);
                    cmd.setLocationId(locId);
                    cmd.setLocation(loc);
                }
                // 校验商品
                Long skuId = skuNoSnMap.get(cmd.getSkuCode());
                if (skuId == null) {
                    Sku sku = skuDao.getByCode(cmd.getSkuCode());
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 1);
                        rs.setStatus(-1);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        skuId = sku.getId();
                        cmd.setSku(sku);
                        cmd.setSkuId(sku.getId());
                    }
                } else {
                    Sku sku = new Sku();
                    sku.setId(skuId);
                    cmd.setSku(sku);
                    cmd.setSkuId(skuId);
                }
                // List<Inventory> iv = inventoryDao.findSkuLocationOuIdOwner(ouId, locId, skuId,
                // new BeanPropertyRowMapperExt<Inventory>(Inventory.class));
                String key = "";
                // key规则 商品ID+库位ID+库存状态ID+保质期时间(仅限保质期商品)
                Sku sku = skuDao.getByPrimaryKey(skuId);
                // 如果是保质期商品需要格式化生产日期和过期时间
                String dataStr = "";
                InventoryStatus is = inventoryStatusDao.findByName(cmd.getInvStatusName().trim(), ouId);
                if (is == null) {
                    rs.setStatus(-1);
                    String currCell = ExcelUtil.offsetCellIndex(sheet0strCell, sheet0OffsetRow, 0);
                    rs.getExceptions().add(new BusinessException(ErrorCode.EXCEL_IMPORT_VMI_INVCK_STATUS_NOT_EXIST, new Object[] {SHEET_0, currCell, cmd.getInvStatusName().trim()}));
                    continue;
                }
                if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    if (!StringUtil.isEmpty(cmd.getSexpireDate())) {
                        if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                            dataStr = (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? cmd.getSexpireDate().substring(0, 6) + "01" : cmd.getSexpireDate().substring(0, 4) + "0101");
                        } else {
                            dataStr = cmd.getSexpireDate();
                        }
                    }
                    if (!StringUtil.isEmpty(cmd.getPoductionDate()) && StringUtil.isEmpty(cmd.getSexpireDate())) {
                        c.setTime(formatDate.parse(cmd.getPoductionDate())); // 设置生成日期
                        c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
                        if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
                            SimpleDateFormat tempFD = new SimpleDateFormat(TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "yyyyMM" : "yyyy");
                            dataStr = tempFD.format(c.getTime()) + (TimeTypeMode.MONTH.equals(cmd.getSku().getTimeType()) ? "01" : "0101");
                        } else {
                            dataStr = formatDate.format(c.getTime());
                        }
                    }
                    key = skuId + "-" + locId + "-" + is.getId() + "-" + dataStr;// 保质期商品需要过期时间
                } else {
                    key = skuId + "-" + locId + "-" + is.getId() + "-";// 不是保质期商品不需要过期时间
                }
                // 存入计算结果map
                if (importData.get(key) == null) {
                    importData.put(key, cmd.getConfirmQty());// 整合数量
                    if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                        importDataToSM.put(key, (StringUtil.isEmpty(cmd.getPoductionDate()) ? "" : cmd.getPoductionDate()) + "-" + dataStr);// 整合生产日期和过期时间
                    }
                } else {
                    importData.put(key, cmd.getConfirmQty() + importData.get(key));
                }
                sheet0OffsetRow++;
            }
        }
    }

    /**
     * 校验库盘点导入SN号SHEET
     */
    @SuppressWarnings({"unchecked", "unused"})
    private Map<Long, List<String>> validateImporyInvCkSheet1(Map<String, Object> importBean, Map<String, Long> locMap, ReadStatus rs, InventoryCheck ic, Map<String, Long> importData) {
        List<InventoryCommand> invSnList = (List<InventoryCommand>) importBean.get("inventorySn");
        Set<String> snSet = new HashSet<String>();
        Map<String, Long> skuSnMap = null;
        if (invSnList != null && invSnList.size() != 0) {
            skuSnMap = skuDao.findAllKeyCodeByOuWithSn(ic.getOu().getId(), ic.getId(), true, new MapRowMapper());
        }
        if (skuSnMap == null) {
            skuSnMap = new HashMap<String, Long>();
        }
        Map<Long, List<String>> impSnMap = new HashMap<Long, List<String>>();
        final ExcelSheet sheet1 = inventoryCheckImportReader.getDefinition().getExcelSheets().get(1);
        List<ExcelBlock> sheet1Blocks = sheet1.getSortedExcelBlocks();
        String sheet1strCell = ExcelUtil.getCellIndex(sheet1Blocks.get(0).getStartRow(), sheet1Blocks.get(0).getStartCol());
        int sheet1OffsetRow = 0;
        if (invSnList != null) {
            for (InventoryCommand cmd : invSnList) {
                // 校验库位
                Long locId = locMap.get(cmd.getLocationCode());
                if (locId == null) {
                    rs.setStatus(-1);
                    String currCell = ExcelUtil.offsetCellIndex(sheet1strCell, sheet1OffsetRow, 0);
                    rs.getExceptions().add(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_CHECK_NO_LOCATION, new Object[] {SHEET_1, currCell, cmd.getLocationCode(), ic.getCode()}));
                } else {
                    WarehouseLocation loc = new WarehouseLocation();
                    loc.setId(locId);
                    cmd.setLocationId(locId);
                    cmd.setLocation(loc);
                }
                Long skuId = skuSnMap.get(cmd.getSkuCode());
                if (skuId == null) {
                    Sku sku = skuDao.getByCode(cmd.getSkuCode());
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(sheet1strCell, sheet1OffsetRow, 1);
                        rs.setStatus(-1);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getSkuCode()}));
                    } else if (sku.getIsSnSku() == null || sku.getIsSnSku() == false) {
                        String currCell = ExcelUtil.offsetCellIndex(sheet1strCell, sheet1OffsetRow, 1);
                        rs.setStatus(-1);
                        rs.addException(new BusinessException(ErrorCode.IMPUT_SNSKU_IS_NOT_SNSKU, new Object[] {SHEET_1, currCell, cmd.getSkuCode()}));
                    } else {
                        skuId = sku.getId();
                        cmd.setSku(sku);
                        cmd.setSkuId(sku.getId());
                    }
                } else {
                    Sku sku = new Sku();
                    sku.setId(skuId);
                    cmd.setSku(sku);
                    cmd.setSkuId(skuId);
                }

                // 核对SN 是否重复
                int size = snSet.size();
                snSet.add(cmd.getSn());
                if (size == snSet.size()) {
                    String currCell = ExcelUtil.offsetCellIndex(sheet1strCell, sheet1OffsetRow, 4);
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SN_EXISTS, new Object[] {SHEET_1, currCell, cmd.getSn()}));
                }
                // 存入计算结果map 1行sn号对应1个商品
                String key = skuId + "-" + locId;
                if (importData.get(key) == null) {
                    importData.put(key, 1L);
                } else {
                    importData.put(key, 1L + importData.get(key));
                }
                // 存入商品对应sn
                List<String> snList = impSnMap.get(skuId);
                if (snList == null) {
                    snList = new ArrayList<String>();
                }
                snList.add(cmd.getSn());
                impSnMap.put(skuId, snList);
                sheet1OffsetRow++;
            }
        }
        return impSnMap;
    }

    public ReadStatus inventoryCheckImport(File file, Long invCkId, Long ouId, Long whOUID, Long userId) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!(InventoryCheckStatus.CREATED.equals(ic.getStatus()) || InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus()))) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        Map<String, Object> bean = new HashMap<String, Object>();
        try {
            OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(whOUID);
            ReadStatus rs = inventoryCheckImportReader.readAll(new FileInputStream(file), bean);
            /*********************** 核对数据 ********************************************************/
            // 盘点批中库位
            Map<String, Long> locMap = inventoryCheckLineDao.findLocMap(invCkId, new MapRowMapper());
            Map<String, Long> importData = new HashMap<String, Long>();
            Map<String, String> importDataToSM = new HashMap<String, String>();
            // Map<Long, List<String>> impSnMap = null;

            // 验证如果是保质期商品 那输入的生产日期和过期时间是否格式正确
            rs = checkInventoryCheckImport(rs, bean, customerId);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            // 校验导入数据
            validateImporyInvCkSheet0(bean, locMap, rs, ic, importData, ou.getParentUnit().getParentUnit().getId(), importDataToSM);
            // 获取sn列表
            // impSnMap = validateImporyInvCkSheet1(bean, locMap, rs, ic, importData);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            // 删除上次导入
            List<InventoryCheckDifferenceLine> dlist = inventoryCheckDifferenceLineDao.findByInventoryCheck(ic.getId());
            for (InventoryCheckDifferenceLine l : dlist) {
                inventoryCheckDifferenceLineDao.delete(l);
            }
            margenIncentoryCheckDifLinePC(ic, importData, importDataToSM);
            // 计算是否存在调整数据 并且创建调整单
            // executeInvCkSn(impSnMap, ic);
            // 更新盘点批状态
            ic.setStatus(InventoryCheckStatus.UNEXECUTE);
            inventoryCheckDao.save(ic);
            return rs;
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus createStaForVMITransfer(StockTransApplication sta, File file, Long ownerid, Long addiownerid, Long ouid, Long cmpOuid, Long userid, Long invstatusId) throws Exception {
        BiChannel originShop = companyShopDao.getByPrimaryKey(ownerid);
        BiChannel targetShop = companyShopDao.getByPrimaryKey(addiownerid);
        if (originShop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        if (targetShop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        wareHouseManagerExe.validateBiChannelSupport(null, originShop.getCode());
        wareHouseManagerExe.validateBiChannelSupport(null, targetShop.getCode());
        if (!StringUtil.isEmpty(originShop.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(originShop.getOpType())) {
                // 如果有配置需要验证
                boolean b = VmiDefault.checkVmiOpType(originShop.getOpType(), VmiOpType.TFXRSN);
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {"转出：" + originShop.getName(), "VMI转店"});
                }
            }
        }
        if (!StringUtil.isEmpty(targetShop.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(targetShop.getOpType())) {
                // 如果有配置需要验证
                boolean b = VmiDefault.checkVmiOpType(targetShop.getOpType(), VmiOpType.TFXRSN);
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {"转入：" + targetShop.getName(), "VMI转店"});
                }
            }
        }
        // 判断 店铺是否都存在品牌对接编码 或者 都不存在品牌对接编码
        if ((originShop.getVmiCode() == null ? true : originShop.getVmiCode().isEmpty()) != (targetShop.getVmiCode() == null ? true : targetShop.getVmiCode().isEmpty())) {
            throw new BusinessException(ErrorCode.SHOP_TYPE_IS_ERROR);
        }

        Boolean msg = rmi4Wms.checkPaymentDistribution(originShop.getCode());
        if (null != msg && msg) {
            throw new BusinessException(ErrorCode.SHOP_TYPE_IS_PAYMENT_ERROR, new Object[] {originShop.getName()});
        }
        Boolean msg1 = rmi4Wms.checkPaymentDistribution(targetShop.getCode());
        if (null != msg1 && msg1) {
            throw new BusinessException(ErrorCode.SHOP_TYPE_IS_PAYMENT_ERROR, new Object[] {targetShop.getName()});
        }
        List<BiChannel> shoplist = companyShopDao.getAllRefShopByWhOuId(ouid);
        boolean isRefOriginShop = false;
        boolean isRefTargetShop = false;
        for (BiChannel tmpShop : shoplist) {
            if (originShop.getId().equals(tmpShop.getId())) {
                isRefOriginShop = true;
            }
            if (targetShop.getId().equals(tmpShop.getId())) {
                isRefTargetShop = true;
            }
        }
        boolean isRelative = false;
        if (isRefOriginShop && isRefTargetShop) {
            isRelative = true;
        }
        if (!isRelative) {
            // 店铺未关联同一家仓库
            throw new BusinessException(ErrorCode.TWO_COMAPNY_SHOP_IS_NOT_RELATIVE, new Object[] {originShop.getName(), targetShop.getName()});
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiOwnerTransferImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");
            List<StaLineCommand> stalinecmds = (List<StaLineCommand>) beans.get("stalinecmds");
            if (stalinecmds != null && stalinecmds.size() > 1000
                    && (originShop.getCode().equals(Constants.NIKE_SHOP1_ID) || originShop.getCode().equals(Constants.NIKE_SHOP2_ID) || originShop.getCode().equals(Constants.NIKE_SHOP3_ID) || originShop.getCode().equals(Constants.NIKE_SHOP4_ID))) {
                throw new BusinessException(ErrorCode.NIKE_IMPORT_SIZE);
            }
            List<StaLine> stalines = new ArrayList<StaLine>();
            rs = vmiOwnerTransferValidate(rs, stalinecmds, stalines, type, ouid);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }

            // save sta
            InventoryStatus inventorystatus = inventoryStatusDao.getByPrimaryKey(invstatusId);
            User user = userDao.getByPrimaryKey(userid);
            sta.setMainStatus(inventorystatus);
            sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
            sta.setMainWarehouse(operationUnitDao.getByPrimaryKey(ouid));
            sta.setCreateTime(new Date());
            sta.setOwner(originShop.getCode());
            sta.setAddiOwner(targetShop.getCode());
            sta.setToLocation(targetShop.getVmiCode());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setSystemKey(Constants.WMS);// 转店作业单来源于WMS系统自己创建
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userid, sta.getMainWarehouse().getId());
            sta.setCreator(user);
            sta.setOutboundOperator(user);
            sta.setIsNeedOccupied(true);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());

            // VMI转店反馈
            if (originShop != null && originShop.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(originShop.getVmiCode());
                if (vf != null) {
                    sta.setRefSlipCode(vf.generateRtnStaSlipCode(originShop.getVmiCode(), sta.getType()));
                }
            }
            sta.setIsNotPacsomsOrder(true);
            staDao.save(sta);
            Long skuQty = 0l;
            // save staline
            for (StaLineCommand cmd : stalinecmds) {
                if (cmd.getQuantity() != 0) {
                    StaLine staLine = new StaLine();
                    staLine.setInvStatus(inventorystatus);
                    staLine.setOwner(originShop.getCode());
                    staLine.setQuantity(cmd.getQuantity());
                    staLine.setSku(cmd.getSku());
                    staLine.setSta(sta);
                    skuQty += staLine.getQuantity();
                    staLineDao.save(staLine);
                }
            }
            sta.setSkuQty(skuQty);
            staDao.flush();
            staDao.save(sta);
            staDao.updateSkuQtyById(sta.getId());
            wareHouseManager.isInventoryNumber(sta.getId());
            /***** mongoDB库存变更添加逻辑 ******************************/
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
            // 占用库存 create stv, stvline
            wareHouseManager.createTransferByStaId(sta.getId(), userid);
            if (sta.getSystemKey() != null && Constants.WMS.equals(sta.getSystemKey())) {
                wareHouseManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.VMI_OWNER_TRANSFER);
            }

            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    private ReadStatus vmiOwnerTransferValidate(ReadStatus rs, List<StaLineCommand> stalinecmds, List<StaLine> stalines, String type, Long ouid) {
        int intType;
        final ExcelSheet sheetOut = vmiOwnerTransferImportReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();

        if (type == null) {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.SNS_SKU_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            return rs;
        }
        // sku信息校验
        validateSkuInfo(rs, stalinecmds, stalines, intType, ouid);
        return rs;
    }

    private ReadStatus validateSkuInfo(ReadStatus rs, List<StaLineCommand> sheet, List<StaLine> stalines, int intType, Long ouid) {
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        final ExcelSheet sheets = vmiOwnerTransferImportReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;

        // if(sheet.size() == 1){
        int totalCount = 0;
        for (StaLineCommand cmd : sheet) {
            totalCount += cmd.getQuantity();
        }
        if (totalCount == 0) {
            rs.setStatus(-2);
            rs.addException(new BusinessException(ErrorCode.SKU_NUMBER_IS_NULL));
        }
        // }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouid);
        for (StaLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            Sku sku = skuCache.get(cmd.getSkuCode());
            if (sku == null) {
                if (intType == 1) {// barcode
                    sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addSkuCode != null) {
                            sku = addSkuCode.getSku();
                        }
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_BARCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else if (intType == 0) { // skucode
                    sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else {
                    cmd.setSku(sku);
                }
            }
            if (sku != null) {
                cmd.setSku(sku);
            }

            if (cmd.getQuantity() == null || cmd.getQuantity() < 0) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_QUANTITY_MINUS, new Object[] {SHEET_0, currCell}));
            }

            offsetRow++;
        }
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }

        Map<String, StaLineCommand> cache = new HashMap<String, StaLineCommand>();
        String key = null;
        for (StaLineCommand cmd : sheet) {
            if (cmd == null) {
                continue;
            }
            // sku 合并重复行
            key = cmd.getSku().getCode();
            if (cache.get(key) == null) {
                cache.put(key, cmd);
            } else {
                StaLineCommand stalineCmd = cache.get(key);
                stalineCmd.setQuantity(stalineCmd.getQuantity() + cmd.getQuantity()); // ??
            }

        }
        sheet.clear();
        sheet.addAll(cache.values());
        return rs;
    }

    /**
     * 部分盘点 不盘盈SN号除商品被修改,全盘支持SN号盘盈盘亏
     * 
     * @param impSnMap
     * @param ic
     */
    @SuppressWarnings("unused")
    private void executeInvCkSn(Map<Long, List<String>> impSnMap, InventoryCheck ic) {
        // 清空差异sn号
        inventoryCheckDifferenceSnLineDao.deleteByIc(ic.getId());
        // 全盘盘亏处理
        BigDecimal cnt = warehouseLocationDao.findUnLockCountByOu(ic.getOu().getId(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        Map<String, Long> snMap = snDao.findAllByOu(ic.getOu().getId(), new MapRowMapper());
        if (snMap == null) {
            snMap = new HashMap<String, Long>();
        }
        for (Entry<Long, List<String>> entry : impSnMap.entrySet()) {
            for (String str : entry.getValue()) {
                Long skuId = snMap.get(str);
                if (skuId == null || !skuId.equals(entry.getKey())) {
                    // 盘盈处理
                    InventoryCheckDifferenceSnLine l = new InventoryCheckDifferenceSnLine();
                    l.setInventoryCheck(ic);
                    Sku sku = new Sku();
                    sku.setId(entry.getKey());
                    l.setSku(sku);
                    l.setSn(str);
                    l.setType(InventoryCheckDifferenceSnLineType.INV_CHECK_IN);
                    log.debug("1---------> 盘盈处理skuId: " + skuId + "  entry.getKey():  " + entry.getKey());
                    inventoryCheckDifferenceSnLineDao.save(l);
                }
                // 判断是否商品被更改，如商品被修改原sn号改为盘亏
                if (skuId != null && !skuId.equals(entry.getKey())) {
                    SkuSn ouSn = snDao.findSkuSnBySkuSn(skuId, str, ic.getOu().getId());
                    InventoryCheckDifferenceSnLine l = new InventoryCheckDifferenceSnLine();
                    l.setInventoryCheck(ic);
                    l.setSku(ouSn.getSku());
                    l.setSn(ouSn.getSn());
                    l.setType(InventoryCheckDifferenceSnLineType.INV_CHECK_OUT);
                    log.debug("2---------> 盘亏处理skuId: " + skuId + "  entry.getKey():  " + entry.getKey());
                    inventoryCheckDifferenceSnLineDao.save(l);
                }
                snMap.remove(str);
            }
        }
        // sn盘亏
        if (cnt == null || cnt.intValue() == 0) {
            for (Entry<String, Long> ent : snMap.entrySet()) {
                SkuSn sn = snDao.findSkuSnBySkuSn(ent.getValue(), ent.getKey(), ic.getOu().getId());
                InventoryCheckDifferenceSnLine l = new InventoryCheckDifferenceSnLine();
                l.setInventoryCheck(ic);
                l.setSku(sn.getSku());
                l.setSn(sn.getSn());
                l.setType(InventoryCheckDifferenceSnLineType.INV_CHECK_OUT);
                log.debug("3---------> sn盘亏cnt : " + cnt);
                inventoryCheckDifferenceSnLineDao.save(l);
            }
        }
    }

    private void mergeVMIInvCKDifLine(List<InventoryCheckDifferenceLine> list) {
        for (InventoryCheckDifferenceLine line : list) {
            inventoryCheckDifferenceLineDao.save(line);
        }
    }

    public void margenIncentoryCheckDifLine(InventoryCheck ic, Map<String, Long> importData) {
        // 获取库存数量
        Map<String, Long> invData = inventoryDao.findQtyByInventoryCheck(ic.getId(), new MapRowMapper());
        Set<String> keyset = new HashSet<String>();
        if (importData != null && importData.size() > 0) {
            keyset.addAll(importData.keySet());
        }
        if (invData != null && invData.size() > 0) {
            keyset.addAll(invData.keySet());
        }
        for (String key : keyset) {
            Long cqty = importData == null ? null : importData.get(key);
            Long iqty = invData == null ? null : invData.get(key);
            Long qty = null;
            if (cqty == null) {
                // 导入文件无此商品记录
                qty = -iqty;
            } else if (iqty == null) {
                // 库存无此商品记录
                qty = cqty;
            } else {
                // 导入和库存都有此商品记录
                qty = cqty - iqty;
            }
            if (qty != 0) {
                String[] strs = key.split("-");
                Long skuId = Long.parseLong(strs[0]);
                Long locId = Long.parseLong(strs[1]);
                WarehouseLocation loc = new WarehouseLocation();
                loc.setId(locId);
                Sku sku = new Sku();
                sku.setId(skuId);
                InventoryCheckDifferenceLine difLine = new InventoryCheckDifferenceLine();
                difLine.setInventoryCheck(ic);
                difLine.setLocation(loc);
                difLine.setQuantity(qty);
                difLine.setSku(sku);
                inventoryCheckDifferenceLineDao.save(difLine);
            }
        }
    }


    public void margenIncentoryCheckDifLineMode2(InventoryCheck ic, Map<String, Long> importData) {
        // 盘点差异 插入导入结果
        for (Entry<String, Long> ent : importData.entrySet()) {
            String[] strs = ent.getKey().split("-");
            Long skuId = Long.parseLong(strs[0]);
            Long locId = Long.parseLong(strs[1]);
            WarehouseLocation loc = new WarehouseLocation();
            loc.setId(locId);
            Sku sku = new Sku();
            sku.setId(skuId);
            InventoryCheckDifferenceLine difLine = new InventoryCheckDifferenceLine();
            difLine.setInventoryCheck(ic);
            difLine.setLocation(loc);
            difLine.setQuantity(ent.getValue());
            difLine.setSku(sku);
            inventoryCheckDifferenceLineDao.save(difLine);
        }
        inventoryCheckDifferenceLineDao.flush();
        Map<String, Object> invparams = new HashMap<String, Object>();
        invparams.put("in_inv_check_id", ic.getId());
        System.out.println("==============>" + ic.getId());
        SqlParameter[] invSqlP = {new SqlParameter("in_inv_check_id", Types.NUMERIC)};
        staDao.executeSp("sp_inv_check_margen_dif_line", invSqlP, invparams);
    }

    /**
     * vmi 出库调整 - 导入
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ReadStatus generateVMIInventoryAdjust(String slipCode, String reasonCode, File file, Long ownerid, Long ouid, Long userid, InventoryCheck ic) throws Exception {
        BiChannel owner = companyShopDao.getByPrimaryKey(ownerid);
        if (owner == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
        wareHouseManagerExe.validateBiChannelSupport(null, owner.getCode());
        if (!StringUtil.isEmpty(owner.getVmiCode())) {
            // 验证品牌操作权限
            if (!StringUtil.isEmpty(owner.getOpType())) {
                // 如果有配置需要验证
                boolean b = VmiDefault.checkVmiOpType(owner.getOpType(), VmiOpType.ADJ);
                if (b) {
                    // 无操作权限抛出异常
                    throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {owner.getName(), "VMI库存调整"});
                }
            }
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = vmiAdjustInventoryReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");

            List<InventoryCheckDifTotalLineCommand> outiclines = (List<InventoryCheckDifTotalLineCommand>) beans.get("outsheet");
            List<InventoryCheckDifTotalLineCommand> iniclines = (List<InventoryCheckDifTotalLineCommand>) beans.get("insheet");

            List<InventoryCheckDifTotalLineCommand> outiclinesTmp = null;
            List<InventoryCheckDifTotalLineCommand> iniclinesTmp = null;

            if (outiclines != null && (!outiclines.isEmpty())) {
                outiclinesTmp = new ArrayList<InventoryCheckDifTotalLineCommand>();
                rs = vmiAdjustmentInventoryValidate(rs, outiclines, outiclinesTmp, type, ouid);
            }
            if (iniclines != null && (!iniclines.isEmpty())) {
                iniclinesTmp = new ArrayList<InventoryCheckDifTotalLineCommand>();
                rs = vmiAdjustmentInventoryValidate(rs, iniclines, iniclinesTmp, type, ouid);
            }

            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }

            User user = userDao.getByPrimaryKey(userid);

            InventoryCheck inventoryCheck = new InventoryCheck();
            inventoryCheck.setCreateTime(new Date());
            inventoryCheck.setStatus(InventoryCheckStatus.CREATED);
            inventoryCheck.setType(InventoryCheckType.VMI);
            inventoryCheck.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), inventoryCheck));
            // inventoryCheck.setRemork("esprit adjustment");
            inventoryCheck.setCreator(user);

            if (ic != null) {
                inventoryCheck.setInvoiceNumber(ic.getInvoiceNumber());
                inventoryCheck.setMiscFeePercentage(ic.getMiscFeePercentage());
                inventoryCheck.setDutyPercentage(ic.getDutyPercentage());
            }
            inventoryCheck.setOu(operationUnitDao.getByPrimaryKey(ouid));
            inventoryCheck.setShop(owner);
            if (slipCode != null) inventoryCheck.setSlipCode(slipCode);
            if (reasonCode != null) inventoryCheck.setRemork(reasonCode);
            inventoryCheck.setReasonCode(reasonCode);
            inventoryCheckDao.save(inventoryCheck);
            // 出库
            if (outiclinesTmp != null && (!outiclinesTmp.isEmpty())) for (InventoryCheckDifTotalLineCommand cmd : outiclinesTmp) {
                InventoryCheckDifTotalLine icDifTotalLine = new InventoryCheckDifTotalLine();
                icDifTotalLine.setSku(cmd.getSku());
                icDifTotalLine.setQuantity(-cmd.getQuantity());
                icDifTotalLine.setInventoryCheck(inventoryCheck);
                // icDifTotalLine.setStatus(status);
                vmiinvCheckLineDao.save(icDifTotalLine);
            }
            // 入库
            if (iniclinesTmp != null && (!iniclinesTmp.isEmpty())) for (InventoryCheckDifTotalLineCommand cmd : iniclinesTmp) {
                InventoryCheckDifTotalLine icDifTotalLine = new InventoryCheckDifTotalLine();
                icDifTotalLine.setSku(cmd.getSku());
                icDifTotalLine.setQuantity(cmd.getQuantity());
                icDifTotalLine.setInventoryCheck(inventoryCheck);
                vmiinvCheckLineDao.save(icDifTotalLine);
            }
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    // vmi - 调整sku校验
    private ReadStatus vmiAdjustmentInventoryValidate(ReadStatus rs, List<InventoryCheckDifTotalLineCommand> icLinesRaw, List<InventoryCheckDifTotalLineCommand> icLineTmp, String type, Long ouid) {
        int intType;
        final ExcelSheet sheetOut = vmiAdjustInventoryReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();

        if (type == null) {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.SNS_SKU_TYPE, new Object[] {SHEET_0, strCell}));
            rs.setStatus(-2);
            return rs;
        }
        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
        } else {
            String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            rs.setStatus(-2);
            return rs;
        }
        // sku信息校验
        validateSkuInfoForVmiAdjust(rs, icLinesRaw, icLineTmp, intType, ouid);
        return rs;
    }

    // vmi - 调整sku校验
    private ReadStatus validateSkuInfoForVmiAdjust(ReadStatus rs, List<InventoryCheckDifTotalLineCommand> icLinesRaw, List<InventoryCheckDifTotalLineCommand> icLineTmp, int intType, Long ouid) {
        Map<String, InventoryCheckDifTotalLineCommand> cache = new HashMap<String, InventoryCheckDifTotalLineCommand>();
        String key = null;

        final ExcelSheet sheets = vmiAdjustInventoryReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouid);
        for (InventoryCheckDifTotalLineCommand cmd : icLinesRaw) {
            if (cmd == null) {
                continue;
            }
            Sku sku = skuCache.get(cmd.getSkuCode());
            if (sku == null) {
                if (intType == 1) {// barcode
                    sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    if (sku == null) {
                        SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addSkuCode != null) {
                            sku = addSkuCode.getSku();
                        }
                    }
                } else if (intType == 0) { // skucode
                    sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                }
            }
            if (sku != null) {
                skuCache.put(cmd.getSkuCode(), sku);
                cmd.setSku(sku);
            } else {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
            }
            offsetRow++;
        }
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        // sku 合并重复行
        for (InventoryCheckDifTotalLineCommand cmd : icLinesRaw) {
            if (cmd == null) {
                continue;
            }
            key = cmd.getSku().getCode();
            if (cache.get(key) == null) {
                cache.put(key, cmd);
            } else {
                InventoryCheckDifTotalLineCommand icLineCmd = cache.get(key);
                icLineCmd.setQuantity(icLineCmd.getQuantity() + cmd.getQuantity()); // ??
            }
        }
        if (!icLineTmp.isEmpty()) icLineTmp.clear();
        icLineTmp.addAll(cache.values());
        return rs;
    }


    /***
     * 库位导入-创建盘点表
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> createInventoryCheckByImportLocation(File file, String remark, Long ouid, Long userid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ReadStatus rs = null;
        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            rs = inventoryCheckByLocationImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                map.put("rs", rs);
                return map;
            }
            List<InventoryCheckLineCommand> inventorychecklines = (List<InventoryCheckLineCommand>) beans.get("locs");
            Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();

            rs = inventoryCheckByImportLocationValidate(rs, inventorychecklines, ouid, locationCache);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                map.put("rs", rs);
                return map;
            }
            User user = userDao.getByPrimaryKey(userid);
            WarehouseLocation loc = null;
            BigDecimal locId = null;
            Collection<Long> locSet = new HashSet<Long>();
            for (InventoryCheckLineCommand entry : inventorychecklines) {
                // 库位处理
                loc = locationCache.get(entry.getLocationCode());
                locId = warehouseLocationDao.findLockedAndNoOccupaidLocation(loc.getId(), new SingleColumnRowMapper<BigDecimal>());
                if (locId != null) {
                    throw new BusinessException(ErrorCode.WH_LOCATION_IS_LOCKED_OR_OCCPUAID, new Object[] {loc.getDistrict().getName(), loc.getCode()});
                } else {
                    locSet.add(loc.getId());
                }
            }
            InventoryCheck invCk = wareHouseManager.createInventoryCheck(user, operationUnit, remark, null);
            wareHouseManager.createInventoryCheckLineNative(locSet, invCk);
            InventoryCheckCommand command = inventoryCheckDao.findICById(invCk.getId(), new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
            map.put("inventoryCheck", command);
            return map;
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
    }


    @SuppressWarnings("unchecked")
    public ReadStatus otherStaImport(String slipCode, File file, String owner, StaDeliveryInfo stadelivery, String memo, Long transactionId, Long ouid, Long userId) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            ReadStatus rs = otherStaReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            // 验证
            String type = (String) beans.get("type");
            final ExcelSheet sheetOut = otherStaReader.getDefinition().getExcelSheets().get(0);
            List<ExcelBlock> blocksOut = sheetOut.getSortedExcelBlocks();
            int intType = 0;
            if ("SKU条码".equals(type)) {
                intType = 1;
            } else if ("SKU编码".equals(type)) {
                intType = 0;
            } else {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow(), blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE, new Object[] {SHEET_0, strCell}));
            }
            TransactionType tType = transactionTypeDao.getByPrimaryKey(transactionId);
            if (tType == null) {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow() + 1, blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_TRANSACTION_TYPE_NOT_FOUND, new Object[] {SHEET_0, strCell, transactionId}));
            }
            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null) {
                rs.setStatus(-1);
                String strCell = ExcelUtil.getCellIndex(blocksOut.get(1).getStartRow() + 2, blocksOut.get(1).getStartCol());
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_0, strCell, owner}));
            }
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            List<StvLineCommand> stvlList = (List<StvLineCommand>) beans.get("inv");
            List<StvLineCommand> tlList = new ArrayList<StvLineCommand>();
            Map<String, StvLineCommand> tlMap = new HashMap<String, StvLineCommand>();
            final ExcelSheet sheet = otherStaReader.getDefinition().getExcelSheets().get(0);
            String strCell = ExcelUtil.getCellIndex(sheet.getExcelBlocks().get(0).getStartRow(), sheet.getExcelBlocks().get(0).getStartCol());
            int offsetRow = 0;

            Map<String, WarehouseLocation> locCache = new HashMap<String, WarehouseLocation>();
            Map<String, Sku> skuCache = new HashMap<String, Sku>();
            Map<String, InventoryStatus> stsCache = new HashMap<String, InventoryStatus>();

            Long customerId = null;
            Warehouse wh = warehouseDao.getByOuId(ouid);
            if (wh != null && wh.getCustomer() != null) {
                customerId = wh.getCustomer().getId();
            }

            for (StvLineCommand cmd : stvlList) {
                Sku sku = null;
                if (intType == 1) {
                    // 条码
                    sku = skuCache.get(cmd.getSkuCode());
                    if (sku == null) {
                        sku = skuDao.getByBarcode(cmd.getSkuCode(), customerId);
                    }
                    if (sku == null) {
                        SkuBarcode addCode = skuBarcodeDao.findByBarCode(cmd.getSkuCode(), customerId);
                        if (addCode != null) {
                            sku = addCode.getSku();
                        }
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        cmd.setSku(sku);
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                } else {
                    sku = skuCache.get(cmd.getSkuCode());
                    if (sku == null) {
                        sku = skuDao.getByCodeAndCustomer(cmd.getSkuCode(), customerId);
                    }
                    if (sku == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_CODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getSkuCode()}));
                    } else {
                        cmd.setSku(sku);
                        skuCache.put(cmd.getSkuCode(), sku);
                    }
                }
                WarehouseLocation loc = null;
                loc = locCache.get(cmd.getLocationCode());
                if (loc == null) {
                    loc = warehouseLocationDao.findLocationByCode(cmd.getLocationCode(), ouid);
                }
                if (loc == null) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LOCATION_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getLocationCode()}));
                } else {
                    cmd.setLocation(loc);
                    locCache.put(cmd.getLocationCode(), loc);
                }
                InventoryStatus status = null;
                status = stsCache.get(cmd.getIntInvstatusName());
                if (status == null) {
                    status = inventoryStatusDao.findByName(cmd.getIntInvstatusName(), wareHouseManager.findCompanyOUByWarehouseId(ouid).getId());
                }
                if (status == null) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_STATUS_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getIntInvstatusName()}));
                } else {
                    cmd.setInvStatus(status);
                    stsCache.put(cmd.getIntInvstatusName(), status);
                }
                /*
                 * if (loc != null) { // 判断库位是否支持作业另类型 if (!locTransCache.contains(loc.getId())) {
                 * BigDecimal r = warehouseLocationDao.findIsSupportTranstype(loc.getId(),
                 * tType.getId(), new SingleColumnRowMapper<BigDecimal>()); if (r == null) {
                 * rs.setStatus(-2); rs.addException(new
                 * BusinessException(ErrorCode.WH_LOCATION_NO_TRANSACTION_TYPE, new Object[]
                 * {loc.getCode(), tType.getName()})); } else { locTransCache.add(loc.getId()); } }
                 * }
                 */
                if (cmd.getQuantity() == null || cmd.getQuantity() < 1) {
                    String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.BETWENLIBARY_STA_QTY_IS_NOT_NULL, new Object[] {SHEET_0, currCell}));
                }
                if (tType.getDirection().equals(TransactionDirection.INBOUND)) {
                    if (sku != null && loc != null && InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) {
                        // 判断批次混放的商品是否在库位上已经存在
                        Long l = skuDao.findSkuOnLoaction(sku.getId(), loc.getId(), new SingleColumnRowMapper<Long>(Long.class));
                        if (l != null) {
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.SKU_TOGETHER_LOC_IS_SKU, new Object[] {offsetRow + 5, sku.getCode(), loc.getCode(), sku.getCode()}));
                        }
                    }
                    // 如果是入库 商品是保质期商品 必须做过期时间计算
                    if (sku != null && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                        try {
                            whManagerProxy.setStvLineProductionDateAndExpireDate(cmd, cmd.getStrPoductionDate(), cmd.getStrExpireDate());
                        } catch (BusinessException e) {
                            rs.setStatus(-2);
                            rs.addException(e);
                        }
                    }
                }
                if (sku != null && sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SALE_MODE_ERROR, new Object[] {cmd.getSkuCode(), sku.getBarCode()}));
                }
                if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                    String spd = StringUtil.isEmpty(cmd.getStrPoductionDate()) ? "" : cmd.getStrPoductionDate().trim();
                    String sed = StringUtil.isEmpty(cmd.getStrExpireDate()) ? "" : cmd.getStrExpireDate().trim();
                    String key = sku.getId() + "_" + loc.getId() + "_" + status.getId() + (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode()) ? "_" + spd + "_" + sed : "");
                    if (tlMap.containsKey(key)) {
                        StvLineCommand tempCmd = tlMap.get(key);
                        tempCmd.setQuantity(tempCmd.getQuantity() + cmd.getQuantity());
                    } else {
                        tlMap.put(key, cmd);
                        tlList.add(cmd);
                    }
                }
                offsetRow++;
            }
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            }
            User user = userDao.getByPrimaryKey(userId);
            try {
                wareHouseManager.createOthersInOrOutBoundSta(slipCode, true, tType.getId(), user, stadelivery, ou, owner, memo, tlList, false, null);
            } catch (Exception e) {
                log.error("", e);
                throw e;
            }
            return rs;
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                log.error("", e);
                throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> importBetweenLibraryMoveApplication(File file, Long main_whouId) {

        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            // 读取excel
            ReadStatus rs = betweenLibaryInitializeReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                String type = (String) beans.get("type");
                List<BetweenLabaryMoveCommand> result = (List<BetweenLabaryMoveCommand>) beans.get("inv");
                // 验证excel数据
                Map<String, Sku> skuMap = validateBetweenLibaryInitializeExcel(rs, type, result, main_whouId, null);
                if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                    List<BetweenLabaryMoveCommand> list = wareHouseManager.mergeEqualsMoveSku(result, skuMap);
                    res.put(Constants.IMPORT_EXL_RESULT, list);
                }
            }
            res.put(Constants.IMPORT_EXL_ERROR, rs);
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
        return res;
    }

    private Map<String, Sku> validateBetweenLibaryInitializeExcel(ReadStatus rs, String type, List<BetweenLabaryMoveCommand> result, Long main_whouId, String owner) {
        if (result == null || result.size() == 0) {
            throw new BusinessException(ErrorCode.END_OPERATION_TYPE_NOT_FOUNT1);
        }
        Map<String, Sku> skuMap = new HashMap<String, Sku>();
        int intType = -1;
        final ExcelSheet sheet = betweenLibaryInitializeReader.getDefinition().getExcelSheets().get(0);
        List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();

        if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
            intType = 1;
            // skuMap = findAllSkuBarCodeMapByWhouId(main_whouId, owner);
        } else if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
            intType = 0;
            // skuMap = findAllSkuCodeMapWhouId(main_whouId, owner);
        } else {
            String strCell = ExcelUtil.getCellIndex(blocks.get(1).getStartRow(), blocks.get(1).getStartCol());
            rs.setStatus(-2);
            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_BETWEENLIBARYMOVE_INITIALIZE_TYPE, new Object[] {strCell}));
            return skuMap;
        }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(main_whouId);
        String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
        int offsetRow = 0;

        // 检查EXCEL数据正确性
        for (BetweenLabaryMoveCommand inv : result) {
            // 移动数量
            if (inv.getMoveQuantity() <= 0) {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_BETWEENLIBARYMOVE_NOT_QUANTITY, new Object[] {currCell, inv.getMoveQuantity()}));
            }
            Sku sku = null;
            if (intType == 1) {
                sku = skuDao.getByBarcode(inv.getCode(), customerId);
                if (sku == null) {
                    SkuBarcode addSkuCode = skuBarcodeDao.findByBarCode(inv.getCode(), customerId);
                    if (addSkuCode != null) {
                        sku = addSkuCode.getSku();
                    }
                }
            } else {
                sku = skuDao.getByCode(inv.getCode());
            }
            if (sku != null) {
                skuMap.put(inv.getCode(), sku);
            } else {
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_BETWEENLIBARYMOVE_NOT_SKU, new Object[] {currCell, inv.getCode()}));
                offsetRow++;
            }
        }
        return skuMap;
    }

    private ReadStatus inventoryCheckByImportLocationValidate(ReadStatus rs, List<InventoryCheckLineCommand> invchecks, Long ouid, Map<String, WarehouseLocation> locationCache) {
        if (invchecks == null || invchecks.isEmpty()) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_IMPORT_BY_LOCATION_IS_NULL);
        }
        Collection<String> invalidLocs = new HashSet<String>();
        String location = null;
        WarehouseLocation whLocation = null;
        final ExcelSheet sheets = inventoryCheckByLocationImportReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        int offsetRow = 0;
        String currCell = null;
        for (InventoryCheckLineCommand entry : invchecks) {
            location = entry.getLocationCode();
            if (location == null) {
                continue;
            }
            whLocation = warehouseLocationDao.findByLocationCode(location, ouid);
            if (whLocation == null) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_LOCATION_NOT_FOUND, new Object[] {SHEET_0, currCell, location}));
                invalidLocs.add(location);
            } else {
                locationCache.put(location, whLocation);
            }
            offsetRow++;
        }
        if (!invalidLocs.isEmpty()) {
            throw new BusinessException(ErrorCode.LOCATION_IS_INVALID, new Object[] {SHEET_0, invalidLocs.toString()});
        }
        if (rs.getStatus() < 0) {
            return rs;
        }
        return rs;
    }

    /**
     * 库间移动入库 导入
     */
    public ReadStatus externalInImport(Long staId, File staFile, User creator, InboundStoreMode mode) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);

            // 校验出库是否已经完成
            if (!sta.getStatus().equals(StockTransApplicationStatus.INTRANSIT)) {
                throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
            }
            readStatus = externalOutWHReader.readAll(new FileInputStream(staFile), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            List<StockTransVoucher> stvs = stvDao.findStvFinishListByStaId(staId);
            if (stvs == null || stvs.size() != 1 || !stvs.get(0).getStatus().equals(StockTransVoucherStatus.FINISHED)) {
                if (log.isDebugEnabled()) {
                    if (stvs == null) {
                        log.debug("stv is null");
                    } else if (stvs.size() != 1) {
                        log.debug("stvs size error");
                    } else if (!stvs.get(0).getStatus().equals(StockTransVoucherStatus.FINISHED)) {
                        log.debug("stv status error {}", stvs.get(0).getStatus());
                    }
                }
                throw new BusinessException(ErrorCode.BETWENLIBARY_STV_OUTBOUND_NO_FINISH, new Object[] {sta.getCode()});
            }
            List<StvLine> list = externalInImportValidate(readStatus, sta, beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }

            // 创建入库stv
            StockTransVoucher stv = new StockTransVoucher();
            stv.setBusinessSeqNo(stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            stv.setCode(stvDao.getCode(staId, new SingleColumnRowMapper<String>()));
            stv.setCreateTime(new Date());
            stv.setCreator(creator);
            stv.setDirection(TransactionDirection.INBOUND);
            TransactionType t = null;
            if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
                stv.setOwner(sta.getAddiOwner());
                if (list == null || list.size() == 0) {
                    throw new BusinessException(ErrorCode.VMI_FLITTING_OUT_SHOP_REF, new Object[] {sta.getAddiWarehouse().getName(), sta.getAddiWarehouse()});
                }
                t = transactionTypeDao.findByCode(Constants.VMI_FLITTING_IN);
            } else {
                t = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_CROSS_IN);
                stv.setOwner(sta.getOwner());
            }
            stv.setSta(sta);
            stv.setLastModifyTime(new Date());
            stv.setStatus(StockTransVoucherStatus.CREATED);
            stv.setTransactionType(t);
            stv.setWarehouse(sta.getAddiWarehouse());
            List<StvLine> stvls = new ArrayList<StvLine>();
            // 创建入库stv line
            for (StvLine l : list) {
                try {
                    StvLine newstvl = l.clone();
                    if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
                        newstvl.setOwner(stv.getOwner());
                    }
                    newstvl.setDirection(TransactionDirection.INBOUND);
                    newstvl.setTransactionType(t);
                    newstvl.setWarehouse(sta.getAddiWarehouse());
                    newstvl.setStv(stv);
                    newstvl.setLocation(l.getLocation());
                    newstvl.setDistrict(l.getDistrict());
                    newstvl.setQuantity(l.getQuantity());
                    stvls.add(newstvl);
                    stvLineDao.save(newstvl);
                } catch (CloneNotSupportedException e) {
                    log.error("", e);
                    log.error(e.getMessage());
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
            sta.setInboundTime(new Date());
            staDao.save(sta);
            stv.setStvLines(stvls);
            stvDao.save(stv);
            stvDao.flush();
            if (sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS)) {
                List<StvLineCommand> errorLineList = stvLineDao.findNotEqualSku(sta.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                if (errorLineList != null && errorLineList.size() > 0) {
                    throw new BusinessException(ErrorCode.INSKU_NOT_EQ_OUTSKU);
                }
            }
            snDao.createInSnByOutStv(sta.getId(), sta.getAddiWarehouse().getId(), stv.getId());
            wareHouseManager.purchaseReceiveStep2(stv.getId(), stvls, false, creator, false, true);
        } catch (BusinessException e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        return readStatus;

    }

    /**
     * 校验 .. 入库 导入
     * 
     * @param rs
     * @param sta
     * @param beans
     * @param mode
     */
    @SuppressWarnings("unchecked")
    private List<StvLine> externalInImportValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
        // 已转出的才能导入
        if (!StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        List<StvLine> result = new ArrayList<StvLine>();
        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLines");
        List<StvLine> stvLinesSheet2 = (List<StvLine>) beans.get("stvLines2");

        if ((stvLines == null || stvLines.isEmpty()) && (stvLinesSheet2 == null || stvLinesSheet2.isEmpty())) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }
        TransactionType tranType = transactionTypeDao.findByCode(TransactionType.returnTypeInbound(sta.getType()));
        if (tranType == null) {
            throw new BusinessException(ErrorCode.STV_TRAN_TYPE_ERROR, new Object[] {sta.getType().name()});
        }
        if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
            List<OperationUnitCommand> list2 = operationUnitDao.findWarehouseByCompanyList(sta.getAddiWarehouse().getId(), sta.getAddiOwner(), new BeanPropertyRowMapper<OperationUnitCommand>(OperationUnitCommand.class));
            if (list2 == null || list2.size() == 0) {
                throw new BusinessException(ErrorCode.VMI_FLITTING_OUT_SHOP_REF, new Object[] {sta.getAddiWarehouse().getName(), sta.getAddiOwner()});
            }
        }
        List<StaLineCommand> stalineList = staLineDao.findByStaIdGroupSkuAnd(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        List<StaLineCommand> batchList = staLineDao.findBySkuStorMode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        Map<String, List<String>> batchMap = new HashMap<String, List<String>>();
        if (batchList != null) {
            for (StaLineCommand sc : batchList) {
                String key = sc.getId() + "_" + sc.getSkuId() + "_" + sc.getOwner() + "_" + sc.getInvStatusId();
                if (batchMap.containsKey(key)) {
                    batchMap.get(key).add(sc.getBatchCode());
                } else {
                    List<String> valueList = new ArrayList<String>();
                    valueList.add(sc.getBatchCode());
                    batchMap.put(key, valueList);
                }
            }
        }

        Map<String, StvLine> tempMap = new HashMap<String, StvLine>();
        Map<String, Sku> isBatchList = new HashMap<String, Sku>();
        /***************************** 验证sheet1数据 ***************************************/
        Iterator<StvLine> it = stvLines.iterator();
        int index = 2;
        // 对于相同skuId分组
        Map<String, List<StvLine>> stvLineMap = new HashMap<String, List<StvLine>>();
        // 保存数量
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        // 对于StvLine按skuID进行分组
        Map<String, Sku> skuCache = new HashMap<String, Sku>();
        while (it.hasNext()) {
            StvLine stvLine = it.next();
            index++;
            if (stvLine != null && stvLine.getSku() != null) {
                if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                    it.remove();
                    continue;
                } else {
                    // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                    if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode()) || stvLine.getQuantity() == null) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                        continue;
                    }

                    // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                    if (stvLine.getInvStatus() == null || !StringUtils.hasLength(stvLine.getInvStatus().getName())) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                        continue;
                    }
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
                        }
                    }
                    StaLineCommand staLine = null;
                    if (sku == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    } else {
                        if (sku != null) {
                            for (StaLineCommand sl : stalineList) {
                                if (sku.getId().equals(sl.getSkuId())) {
                                    staLine = sl;
                                    if (sl.getOwner().equals(stvLine.getOwner())) {
                                        // stalineList.remove(sl);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (staLine == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    } else if (!staLine.getOwner().equals(stvLine.getOwner())) {
                        // sheet{0} 执行量不等于计划执行量
                        errors.add(new BusinessException(ErrorCode.EXTERNAL_EXECUTION_NOT_EQ_PLAN, new Object[] {1, index,}));
                        continue;
                    }
                    stvLine.setSku(sku);
                    stvLine.getStaLine().setId(staLine.getId());
                    // 判断此商品 是否 是 sn 号商品
                    if (sku.getIsSnSku() != null && sku.getIsSnSku() && !StringUtils.hasLength(stvLine.getSns())) {
                        errors.add(new BusinessException(ErrorCode.SNS_SKU_NO_DATA, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getCode()}));
                        continue;
                    }
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getAddiWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, sku.getBarCode()}));
                        continue;
                    }

                    // 获取批次号
                    String batchCode = staLine.getBatchCode();
                    if (!StringUtils.hasLength(batchCode)) {
                        List<String> temp = batchMap.get(staLine.getId() + "_" + staLine.getSkuId() + "_" + staLine.getOwner() + "_" + staLine.getInvStatusId());
                        if (temp == null || temp.size() == 0) {
                            errors.add(new BusinessException(ErrorCode.STV_BATCH_CODE_IS_NULL, new Object[] {index, stvLine.getLocation().getCode()}));
                            continue;
                        }
                        batchCode = temp.get(0);
                        if (temp.size() > 1) {
                            temp.remove(0);
                        } else {
                            batchMap.remove(temp);
                        }
                    }
                    stvLine.setBatchCode(batchCode);
                    stvLine.setLocation(location);

                    // 判断是否有单批隔离的相同的商品在同一库位上面
                    // Product pt = sku.getProduct();
                    if (sku.getStoremode() != null && InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) {
                        String key = sku.getId() + "_" + stvLine.getBatchCode() + "_" + location.getCode();
                        if (isBatchList.containsKey(key)) {
                            // 第{0}行 商品：{1} 是单批隔离的商品，而库位:{2} 已经存在批次不同的{3}商品
                            errors.add(new BusinessException(ErrorCode.SKU_TOGETHER_LOC_IS_SKU, new Object[] {index, sku.getCode(), stvLine.getLocation().getCode(), sku.getCode()}));
                            continue;
                        } else {
                            isBatchList.put(key, sku);
                        }
                    }
                    String key = staLine.getSkuId() + "_" + staLine.getOwner() + "_" + staLine.getBatchCode();
                    if (sku.getStoremode() != null && InboundStoreMode.TOGETHER.getValue() == sku.getStoremode().getValue()) {
                        key = staLine.getSkuId() + "_" + staLine.getOwner();
                    }
                    if (stvLineMap.containsKey(key)) {
                        Long total = qtyMap.get(key);
                        // for (StvLine each : stvLineMap.get(key)) {
                        // total += each.getQuantity();
                        // }
                        // business_exception_10006=作业申请单Excel第[{0}]条码[{1}]JMCode[{2}]实际上架数量的总和已经超出计划执行量
                        if ((total + stvLine.getQuantity()) > staLine.getQuantity()) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        stvLineMap.get(key).add(stvLine);
                        qtyMap.put(key, total + stvLine.getQuantity());
                    } else {
                        if (stvLine.getQuantity() > (staLine.getQuantity())) {// 计划量与执行量不等
                            errors.add(new BusinessException(ErrorCode.STA_QUANTITY_NOT_SAME, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                            continue;
                        }
                        List<StvLine> stvLineList = new ArrayList<StvLine>();
                        stvLineList.add(stvLine);
                        stvLineMap.put(key, stvLineList);
                        qtyMap.put(key, stvLine.getQuantity());
                    }
                    stvLine.setDistrict(location.getDistrict());
                    // if (sta.getType().equals(StockTransApplicationType.VMI_FLITTING)) {
                    // stvLine.setOwner(sta.getAddiOwner());
                    // } else {
                    // stvLine.setOwner(staLine.getOwner());
                    // }
                    stvLine.setSkuCost(staLine.getSkuCost());
                    stvLine.setInvStatus(inventoryStatusDao.getByPrimaryKey(staLine.getInvStatusId()));
                    stvLine.setStaLine(staLineDao.getByPrimaryKey(staLine.getId()));
                    stvLine.setSku(sku);
                    String mapKey = sku.getId() + "_" + location.getId() + "_" + staLine.getBatchCode() + "_" + staLine.getId();
                    if (tempMap.containsKey(mapKey)) {
                        StvLine temp = tempMap.get(mapKey);
                        temp.setQuantity(temp.getQuantity() + stvLine.getQuantity());
                    } else {
                        tempMap.put(mapKey, stvLine);
                        result.add(stvLine);
                    }
                }
            } else {
                it.remove();
            }
        }

        /***************************** 验证sheet2数据 ***************************************/
        Iterator<StvLine> it2 = stvLinesSheet2.iterator();

        Map<Long, Long> snQtyMap = new HashMap<Long, Long>();
        // 查寻移动出库的sn号
        List<SkuSnLogCommand> snList = snLogDao.findOutboundSnBySta(sta.getId(), new BeanPropertyRowMapperExt<SkuSnLogCommand>(SkuSnLogCommand.class));
        Map<String, SkuSnLogCommand> snMap = new HashMap<String, SkuSnLogCommand>();
        if (snList != null && snList.size() > 0) {
            for (SkuSnLogCommand sn : snList) {
                snMap.put(sn.getSn(), sn);
            }
        }
        int index2 = 1;
        // 对于StvLine按skuID进行分组
        while (it2.hasNext()) {
            StvLine stvLine = it2.next();
            // 1行记录1个SN号商品数量1
            stvLine.setQuantity(1L);
            index2++;
            if (stvLine != null && stvLine.getSns() != null) {
                if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode())) {// 没有库位和数量
                    errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                    continue;
                }
                SkuSnLogCommand sn = snMap.get(stvLine.getSns());
                if (sn == null) {
                    errors.add(new BusinessException(ErrorCode.EXTERNAL_SN_IS_NULL, new Object[] {index, stvLine.getSns()}));
                    continue;
                }

                Sku sku = skuDao.getByPrimaryKey(sn.getSkuId());
                if (sku == null) {
                    // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                    errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                    continue;
                }
                WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getAddiWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                if (location == null) {// 库位编码不存在
                    // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                    errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                    continue;
                }
                snMap.remove(stvLine.getSns());
                stvLine.setBatchCode(sn.getBatchCode());
                stvLine.setLocation(location);

                // 判断是否有单批隔离的相同的商品在同一库位上面
                if (sku.getStoremode() != null && InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) {
                    String key = sku.getId() + "_" + stvLine.getBatchCode() + "_" + location.getCode();
                    if (isBatchList.containsKey(key)) {
                        // 第{0}行 商品：{1} 是单批隔离的商品，而库位:{2} 已经存在批次不同的{3}商品
                        errors.add(new BusinessException(ErrorCode.SKU_TOGETHER_LOC_IS_SKU, new Object[] {index, sku.getCode(), stvLine.getLocation().getCode(), sku.getCode()}));
                        continue;
                    } else {
                        isBatchList.put(key, sku);
                    }
                }

                StaLineCommand staLine = null;
                for (StaLineCommand sl : stalineList) {
                    if (sn.getSkuId().equals(sl.getSkuId())) {
                        if (!snQtyMap.containsKey(sl.getId()) || snQtyMap.get(sl.getId()) < sl.getQuantity()) {
                            staLine = sl;
                            break;
                        }
                    }
                }
                if (staLine == null) {
                    // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                    errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, sku.getBarCode(), sku.getJmCode()}));
                    continue;
                }

                snQtyMap.put(staLine.getId(), (snQtyMap.containsKey(staLine.getId()) ? (snQtyMap.get(staLine.getId()) + 1L) : 1L));
                stvLine.setDistrict(location.getDistrict());
                stvLine.setInvStatus(staLine.getInvStatus());
                stvLine.setStaLine(staLine);
                stvLine.setSku(sku);
                if (sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER)) {
                    stvLine.setOwner(sta.getAddiOwner());
                } else {
                    stvLine.setOwner(staLine.getOwner());
                }
                stvLine.setSkuCost(staLine.getSkuCost());
                stvLine.setInvStatus(inventoryStatusDao.getByPrimaryKey(staLine.getInvStatusId()));
                String key = sku.getId() + "_" + location.getId() + "_" + sn.getBatchCode() + "_" + staLine.getId();
                if (tempMap.containsKey(key)) {
                    StvLine temp = tempMap.get(key);
                    temp.setQuantity(temp.getQuantity() + 1);
                    temp.setSns(temp.getSns() + "," + stvLine.getSns());
                } else {
                    stvLine.setQuantity(1L);
                    tempMap.put(key, stvLine);
                    result.add(stvLine);
                }
            } else {
                it2.remove();
            }
        }
        if (errors.isEmpty()) {
            List<StaLineCommand> list = staLineDao.findkuByStaIdAndIsSn(sta.getId(), false, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            for (StaLineCommand sl : list) {
                String key = sl.getSkuId() + "_" + sl.getOwner() + "_" + sl.getBatchCode();
                if (sl.getStoreMode() != null && sl.getStoreMode().equals(InboundStoreMode.TOGETHER.getValue())) {
                    key = sl.getSkuId() + "_" + sl.getOwner();
                }
                Long qty = qtyMap.get(key);
                if (qty == null || !qty.equals(sl.getQuantity())) {
                    errors.add(new BusinessException(ErrorCode.STA_QUANTITY_ERROR, new Object[] {sl.getBarCode(), sl.getJmcode()}));
                }
            }
            StringBuffer sbSn = new StringBuffer();
            for (SkuSnLogCommand sn : snMap.values()) {
                sbSn.append(sn.getSn()).append(",");
            }
            if (sbSn.length() > 0) {
                String s = sbSn.substring(0, sbSn.length() - 1);
                errors.add(new BusinessException(ErrorCode.EXTERNAL_SN_SKU_IS_NOT_NULL, new Object[] {s}));
            }
        }

        if (errors != null && !errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> importVMIFlittingApplication(File file, Long main_whouId, String owner) {
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            // 读取excel
            ReadStatus rs = betweenLibaryInitializeReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                if (!StringUtils.hasLength(owner)) {
                    throw new BusinessException(ErrorCode.START_OWNER_NOT_FOUNT, new Object[] {""});
                }
                String type = (String) beans.get("type");
                List<BetweenLabaryMoveCommand> result = (List<BetweenLabaryMoveCommand>) beans.get("inv");
                // 验证excel数据
                Map<String, Sku> skuMap = validateBetweenLibaryInitializeExcel(rs, type, result, main_whouId, owner);
                if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                    List<BetweenLabaryMoveCommand> list = wareHouseManager.mergeEqualsMoveSku(result, skuMap);
                    res.put(Constants.IMPORT_EXL_RESULT, list);
                }
            }
            res.put(Constants.IMPORT_EXL_ERROR, rs);
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
        return res;
    }


    /**
     * 预定义出库 导入占用
     */
    public ReadStatus predefinedOutImport(Long staId, File staFile, User user, InboundStoreMode mode) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // 非配货中状态 不允许重新占用
        if (!StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.STA_CRANCEL_ERROR1, new Object[] {sta.getCode()});
        }
        readStatus = orderLineReader.readAll(new FileInputStream(staFile), beans);
        @SuppressWarnings("unchecked")
        List<StaLineCommand> orderlist = (List<StaLineCommand>) beans.get("staList");
        // 验证
        String skuType = (String) beans.get("type");
        int intType = 0;
        if ("SKU条码".equals(skuType)) {
            intType = 1;
        } else if ("SKU编码".equals(skuType)) {
            intType = 0;
        } else {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_INVENTORY_INITIALIZE_TYPE);
        }
        if (orderlist == null) {
            throw new BusinessException(ErrorCode.EI_DETAIL_LINE_IS_NULL);
        }
        for (int i = 0; i < orderlist.size(); i++) {
            if (orderlist.get(i).getLocation() == null) {
                throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_FOUND, new Object[] {orderlist.get(i).getLocation()});
            }
            WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(orderlist.get(i).getLocation(), sta.getMainWarehouse().getId());
            if (warehouseLocation == null) {
                throw new BusinessException(ErrorCode.PDA_LOCATION_NOT_FOUND);
            }
        }
        List<StaLine> stalist = staLineDao.findByStaId(staId);
        // 拷贝
        List<StaLine> stalistPre = new ArrayList<StaLine>();
        for (StaLine l : stalist) {
            StaLine l2 = new StaLine();
            l2.setSku(l.getSku());
            l2.setQuantity(l.getQuantity());
            l2.setInvStatus(l.getInvStatus());
            stalistPre.add(l2);
        }
        for (int i = 0; i < stalist.size(); i++) {
            Long skuid = null;
            long Quantity = 0;
            for (StaLineCommand com : orderlist) {
                String code = "";
                if (intType == 0) {
                    code = stalist.get(i).getSku().getCode();
                } else {
                    code = stalist.get(i).getSku().getBarCode();
                }
                if (code.equals(com.getSkuCode())) {
                    if (stalist.get(i).getInvStatus().getName().equals(com.getIntInvstatusName())) {
                        Quantity += com.getQuantity();
                        stalist.get(i).setQuantity(Quantity);
                        com.setSkuId(stalist.get(i).getSku().getId());
                        skuid = stalist.get(i).getSku().getId();
                        staLineDao.save(stalist.get(i));
                        staLineDao.flush();

                    } else {
                        throw new BusinessException(ErrorCode.STA_STATUS_ERROR);
                    }
                }

            }
            try {
                if (skuid == null) {
                    throw new BusinessException(ErrorCode.ERROR_STA_NOT_CODE);
                }
                // int sum = inventoryDao.findInventorycontrast(sta.getCode(), skuid, new
                // SingleColumnRowMapper<Integer>(Integer.class));
                // if (sum != qty) {
                // throw new BusinessException(ErrorCode.STA_STALINE_NUMBER_ERROR);
                // }
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.STA_STALINE_NUMBER_ERROR);
            }
        }
        wareHouseManager.updatepredefinedOutOccupation(sta, orderlist);
        // List<StvLine> stvLines = stvLineDao.findAllByStaId(sta.getId(), new
        // BeanPropertyRowMapperExt<StvLine>(StvLine.class));
        long qty = 0;
        for (StaLineCommand com : orderlist) {
            qty += com.getQuantity();
        }
        // int sum = inventoryDao.findInventorycontrast(sta.getCode(), new
        // SingleColumnRowMapper<Integer>(Integer.class));
        if (qty != 0) {
            throw new BusinessException(ErrorCode.STA_STALINE_NUMBER_ERROR);
        }
        String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EBS_IMPORT), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
        readStatus.setMessage(errorMsg);
        // 导入Excel覆盖原始计划量插入取消占用表给IM
        hubWmsService.insertOccupiedAndReleaseCover(sta.getId(), stalistPre, stalist);
        return readStatus;


    }

    /**
     * 校验 .. 入库 导入
     * 
     * @param rs
     * @param sta
     * @param beans
     * @param mode
     */
    @SuppressWarnings({"unchecked", "unused"})
    private List<StvLine> predefinedOutValidate(ReadStatus rs, StockTransApplication sta, Map<String, Object> beans) {
        List<StvLine> stvLines = (List<StvLine>) beans.get("stvLines");
        List<StvLine> snList = (List<StvLine>) beans.get("outSkuSN");
        if ((stvLines == null || stvLines.isEmpty()) && (snList == null || snList.isEmpty())) {
            throw new BusinessException(ErrorCode.STA_STALINE_EMPTY);
        }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(sta.getMainWarehouse().getId());
        Map<String, WarehouseLocation> locationCache = new HashMap<String, WarehouseLocation>();
        List<StvLine> result = new ArrayList<StvLine>();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        List<StaLineCommand> stalineList = staLineDao.findByPredefinedOutByStaId(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        Map<String, StaLineCommand> stalMap = new HashMap<String, StaLineCommand>();
        for (StaLineCommand sc : stalineList) {
            stalMap.put(sc.getSkuId() + "_" + sc.getInvStatusId() + "_" + sc.getIsSnSku(), sc);
        }
        // 获取公司ID
        Long whId = sta.getMainWarehouse().getParentUnit().getParentUnit().getId();
        /***************************** 验证sheet1数据 ***************************************/
        int index = 2;
        if (stvLines != null && !stvLines.isEmpty()) {
            Iterator<StvLine> it = stvLines.iterator();
            while (it.hasNext()) {
                StvLine stvLine = it.next();
                index++;
                if (stvLine != null && stvLine.getSku() != null) {
                    if ((!StringUtils.hasLength(stvLine.getSku().getBarCode()) && !StringUtils.hasLength(stvLine.getSku().getCode()))) {// 没有条码和编码
                        it.remove();
                        continue;
                    } else {
                        // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                        if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode()) || stvLine.getQuantity() == null) {// 没有库位和数量
                            errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                            continue;
                        }
                        // business_exception_10003=作业申请单行[{0}]库位和数量都不能为空
                        if (stvLine.getInvStatus() == null || !StringUtils.hasLength(stvLine.getInvStatus().getName())) {// 没有库位和数量
                            errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                            continue;
                        }
                        Sku sku = skuDao.findSkuByParameter(stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode(), stvLine.getSku().getKeyProperties(), customerId);
                        if (sku == null) {
                            // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        }
                        InventoryStatus invs = inventoryStatusDao.findByNameUnionSystem(stvLine.getInvStatus().getName(), whId);
                        StaLineCommand staLine = null;
                        if (invs == null) {
                            errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                            continue;
                        } else {
                            if (sku != null) {
                                staLine = stalMap.get(sku.getId() + "_" + invs.getId() + "_0");
                            }
                        }
                        if (staLine == null) {
                            // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                            errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                            continue;
                        } else if (staLine.getQuantity() < stvLine.getQuantity()) {
                            // sheet{0} 执行量不等于计划执行量
                            errors.add(new BusinessException(ErrorCode.EXTERNAL_EXECUTION_NOT_EQ_PLAN, new Object[] {1, index,}));
                            continue;
                        }
                        // 判断此商品 是否 是 sn 号商品
                        WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                        if (location == null) {// 库位编码不存在
                            // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                            errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index, sku.getBarCode()}));
                            continue;
                        }
                        stvLine.setSku(sku);
                        stvLine.setInvStatus(invs);
                        stvLine.setLocation(location);
                        stvLine.setDistrict(location.getDistrict());
                        stvLine.setOwner(sta.getAddiOwner());
                        stvLine.setSkuCost(staLine.getSkuCost());
                        stvLine.setStaLine(staLineDao.getByPrimaryKey(staLine.getId()));
                        if (staLine.getQuantity().equals(stvLine.getQuantity())) {
                            stalMap.remove(sku.getId() + "_" + invs.getId() + "_0");
                        } else {
                            staLine.setQuantity(staLine.getQuantity() - stvLine.getQuantity());
                        }
                        result.add(stvLine);
                    }
                } else {
                    it.remove();
                }
            }
        }
        /***************************** 验证sheet2数据 ***************************************/
        if (snList != null && !snList.isEmpty()) {
            Iterator<StvLine> it2 = snList.iterator();
            int index2 = 1;
            // 对于StvLine按skuID进行分组
            while (it2.hasNext()) {
                StvLine stvLine = it2.next();
                // 1行记录1个SN号商品数量1
                index2++;
                if (stvLine != null && stvLine.getSns() != null) {
                    if (stvLine.getLocation() == null || !StringUtils.hasLength(stvLine.getLocation().getCode())) {// 没有库位和数量
                        errors.add(new BusinessException(ErrorCode.STA_STALINE_LOCATION_QUANTITY_EMPTY, new Object[] {index}));
                        continue;
                    }
                    SkuSn sn = snDao.findSkuSnBySn(stvLine.getSns(), sta.getMainWarehouse().getId(), SkuSnStatus.USING);
                    if (sn == null) {
                        errors.add(new BusinessException(ErrorCode.EXTERNAL_SN_IS_NULL, new Object[] {index, stvLine.getSns()}));
                        continue;
                    }
                    Sku sku = sn.getSku();
                    StaLineCommand staLine = null;
                    if (sku == null) {
                        // business_exception_10005=作业申请单Excel第[{0}]的Sku[条码：{1},商品编码：{2}]不在当前采购计划
                        errors.add(new BusinessException(ErrorCode.STA_SKU_BARCODE_CODE_EMPTY, new Object[] {index, stvLine.getSku().getBarCode(), stvLine.getSku().getJmCode()}));
                        continue;
                    }
                    InventoryStatus invs = inventoryStatusDao.findByNameUnionSystem(stvLine.getInvStatus().getName(), whId);
                    if (invs == null) {
                        errors.add(new BusinessException(ErrorCode.STV_NOT_INV_STATUS, new Object[] {index}));
                        continue;
                    } else {
                        staLine = stalMap.get(sku.getId() + "_" + invs.getId() + "_1");
                    }
                    if (staLine == null) {
                        // 根据SN[{}]找到商品[SKU编码{0}],库存状态为{0} 不在出库单内
                        errors.add(new BusinessException(ErrorCode.PREDEFINED_OUT_BY_SN_IS_NOT_INFO, new Object[] {sn.getSn(), stvLine.getSku().getBarCode(), stvLine.getInvStatus().getName()}));
                        continue;
                    } else if (staLine.getCompleteQuantity() != null && staLine.getCompleteQuantity() > staLine.getQuantity() + 1) {
                        // sheet{0} 执行量不等于计划执行量
                        errors.add(new BusinessException(ErrorCode.EXTERNAL_EXECUTION_NOT_EQ_PLAN, new Object[] {1, index,}));
                        continue;
                    }
                    WarehouseLocation location = wareHouseManager.checkLocationByOuid(sku, sta.getMainWarehouse().getId(), stvLine.getLocation().getCode(), locationCache);
                    if (location == null) {// 库位编码不存在
                        // business_exception_10107=作业申请单行[{0}]对应编码为{1}的库位不适合当前操作,请自选合适的库位
                        errors.add(new BusinessException(ErrorCode.STV_LOCATION_LINE_ERROR, new Object[] {index2, stvLine.getLocation().getCode()}));
                        continue;
                    }
                    staLine.setCompleteQuantity(staLine.getCompleteQuantity() == null ? 0 : staLine.getCompleteQuantity() + 1);
                    if (staLine.getCompleteQuantity().equals(staLine.getQuantity())) {
                        stalMap.remove(staLine);
                    }
                    stvLine.setQuantity(1L);
                    stvLine.setBatchCode(sn.getBatchCode());
                    stvLine.setLocation(location);
                    stvLine.setDistrict(location.getDistrict());
                    stvLine.setInvStatus(invs);
                    stvLine.setStaLine(staLineDao.getByPrimaryKey(staLine.getId()));
                    stvLine.setSku(sku);
                    stvLine.setOwner(sta.getOwner());
                    stvLine.setSkuCost(staLine.getSkuCost());
                    result.add(stvLine);
                } else {
                    it2.remove();
                }
            }
        }
        if (errors.isEmpty()) {
            if (stalMap.size() > 0) {
                for (StaLineCommand sl : stalMap.values()) {
                    errors.add(new BusinessException(ErrorCode.PREDEFINED_OUT_BY_SKU_NUB_ERROR, new Object[] {sl.getSkuCode()}));
                }
            }
        }
        if (errors != null && !errors.isEmpty()) {
            rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            rs.getExceptions().addAll(errors);
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public ReadStatus snsImport(File file, Long staid, Long ouid) {
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(staid);
        if (stv == null) throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = snsReader.readSheet(new FileInputStream(file), 0, beans);
            if (readStatus == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("snsImport FileNotFoundException:" + staid, e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            if (readStatus != null) {
                Object type = beans.get("type");
                if (type == null) {
                    throw new BusinessException(ErrorCode.SNS_SKU_TYPE);
                } else {
                    boolean code = true;
                    if ("SKU条码".equals(type)) code = false;
                    List<SkuSn> snsList = (List<SkuSn>) beans.get("sns");
                    if (snsList == null) throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    // SKU编码 SKU条码 页面上的数量
                    Map<String, Long> staLineskuQtyMap = staLineDao.findIsSnSkuCodeQtyByStaId(staid, ouid, new Boolean(code), new MapRowMapper());
                    if (staLineskuQtyMap == null) staLineskuQtyMap = new HashMap<String, Long>();

                    List<String> errlist = new ArrayList<String>();
                    log.debug("**********************" + "Excel是否有重复比较");
                    for (int i = 0; i < snsList.size(); i++) {
                        for (int j = i + 1; j < snsList.size(); j++) {
                            if (snsList.get(i).getSn().equals(snsList.get(j).getSn())) {
                                errlist.add(snsList.get(j).getSn());
                            }
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {errlist.toString()});
                    Map<String, List<SkuSn>> snmap = new HashMap<String, List<SkuSn>>();
                    List<SkuSn> snlist = null;
                    for (SkuSn e : snsList) {
                        String sncode = e.getSku().getCode();
                        if (snmap.get(sncode) == null) {
                            snlist = new ArrayList<SkuSn>();
                            snlist.add(e);
                            snmap.put(sncode, snlist);
                        } else {
                            snlist = snmap.get(sncode);
                            snlist.add(e);
                        }
                    }
                    if (staLineskuQtyMap.size() != snmap.size())
                        throw new BusinessException(ErrorCode.SKU_QUANTITY_NOT_EQUALS_SN_QUANTITY, new Object[] {staLineskuQtyMap.size(), snmap.size()});
                    else {
                        Set<String> keys = staLineskuQtyMap.keySet();
                        // log.debug("**********************" +
                        // "SKu数量和Excel中的SN数量是否匹配");
                        for (String s : keys) {
                            long qty1 = staLineskuQtyMap.get(s);
                            if (snmap.get(s) == null || snmap.get(s).isEmpty()) throw new BusinessException(ErrorCode.SKU_NOT_FOUND_IN_EXCEL, new Object[] {s});
                            long qty2 = snmap.get(s).size();
                            // log.debug("**********************sncode： " + s +
                            // "     skuqty: " + qty1 + "     Excel:  " + qty2);
                            if (qty1 != qty2) {
                                throw new BusinessException(ErrorCode.SKU_QUANTITY_NOT_MATCHING_SN_QUANTITY, new Object[] {s, qty1, qty2});
                            }
                        }
                    }
                    snDao.deleteSNByStvIdSql(stv.getId());
                    Sku sku = null;
                    errlist.clear();
                    Long customerId = null;
                    Warehouse wh = warehouseDao.getByOuId(ouid);
                    if (wh != null && wh.getCustomer() != null) {
                        customerId = wh.getCustomer().getId();
                    }
                    for (SkuSn sn : snsList) {
                        if (code)
                            sku = skuDao.getByCode(sn.getSku().getCode());
                        else
                            sku = skuDao.getByBarcode(sn.getSku().getCode(), customerId);
                        if (sku == null) throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {""});
                        if (!StringUtils.hasText(sn.getSn())) {
                            errlist.add(sn.getSn());
                        }
                        if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});
                        try {
                            snDao.createSnByInboudImport(sn.getSn(), SkuSnStatus.CHECKING.getValue(), ouid, sku.getId(), stv.getId());
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {sn.getSn()});
                        }
                    }
                }
            }
        }
        return readStatus;
    }

    /**
     * 校验sn号出入导入sn excel
     * 
     * @param rs
     * @param stv
     * @param sns
     */
    private void volidateOutboundSnExcel(ReadStatus rs, StockTransVoucher stv, List<String> sns, Long ouid) {
        if (sns == null || sns.size() == 0) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_EXCEL_IS_NULL);
        } else {
            // ****************校验excel sn重复*********************************//
            Set<String> snSet = new HashSet<String>();
            snSet.addAll(sns);
            List<String> snList = new ArrayList<String>();
            snList.addAll(sns);
            if (sns.size() != snSet.size()) {
                for (String str : snSet) {
                    snList.remove(str);
                }
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SN_HAS_SAME, new Object[] {snList.toString()}));
            } else {
                // ****************校验SN有效性************************************//
                // 还原上次导入
                snDao.updateOutBoundSnRevertByStv(stv.getId());
                final ExcelSheet sheet = outboundSnImportReader.getDefinition().getExcelSheets().get(0);
                List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
                String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
                int offsetRow = 0;
                // ****************校验stv中需要sn商品数量************************//
                Map<Long, Long> skuCount = new HashMap<Long, Long>(); // 导入商品数量
                Map<String, Long> snsMap = snDao.findAllSnByStvWithStatus(stv.getId(), ouid, SkuSnStatus.USING.getValue(), new MapRowMapper());
                if (snsMap == null) {
                    snsMap = new HashMap<String, Long>();
                }

                for (String sn : sns) {
                    Long id = snsMap.get(sn);
                    if (id == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SN_NOT_EXISTS, new Object[] {SHEET_0, currCell, sn}));
                    } else {
                        // 统计导入商品数量
                        Long qty = skuCount.get(id);
                        if (qty == null) {
                            skuCount.put(id, 1L);
                        } else {
                            skuCount.put(id, qty + 1);
                        }
                    }
                    offsetRow++;
                }
                // ****************校验stv中需要sn商品数量************************//
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    List<StvLineCommand> ls = stvLineDao.findStvLineListByStvIdGroupBySku(stv.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    for (StvLineCommand line : ls) {
                        Long qty = skuCount.get(line.getSkuId());
                        if (qty == null || !qty.equals(line.getQuantity())) {
                            Sku sku = skuDao.getByPrimaryKey(line.getSkuId());
                            // 核对数量不正确
                            rs.setStatus(-2);
                            rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_QTY_NOT_EQ, new Object[] {sku.getCode(), sku.getBarCode(), qty, line.getQuantity()}));
                        }
                    }
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    public ReadStatus outboundSnImportByStv(File file, Long staId, Long ouid) {
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvs == null || stvs.size() != 1) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher stv = stvs.get(0);
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            // 读取excel
            ReadStatus rs = outboundSnImportReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            } else {
                // 读取excel，核对数据
                List<Map<String, String>> result = (List<Map<String, String>>) beans.get("sns");
                List<String> sns = new ArrayList<String>();
                for (Map<String, String> r : result) {
                    if (StringUtils.hasText(r.get("sn"))) {
                        sns.add(StringUtils.trimWhitespace(r.get("sn")));
                    }
                }
                volidateOutboundSnExcel(rs, stv, sns, ouid);
                if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    return rs;
                }
                // ************************校验通过执行*************************//
                // 更新导入sn号状态
                for (String sn : sns) {
                    snDao.updateStatusBySn(sn, SkuSnStatus.CHECKING.getValue(), stv.getId());
                }
                return rs;
            }
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }


    /**
     * pda收货 SN号导入
     * 
     */
    @SuppressWarnings("unchecked")
    public ReadStatus pdaPurchaseSnImport(File file, Long staid, Long ouid) {
        List<StockTransVoucher> stvs = stvDao.findByStaWithDirection(staid, TransactionDirection.INBOUND);
        if (stvs == null || stvs.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransVoucher stv = stvs.get(0);
        if (stv == null) throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = pdaPurchaseSnImportReader.readSheet(new FileInputStream(file), 0, beans);
            if (readStatus == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("pdaPurchaseSnImport FileNotFoundException:" + staid, e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            if (readStatus != null) {
                Object type = beans.get("type");
                if (type == null) {
                    throw new BusinessException(ErrorCode.SNS_SKU_TYPE);
                } else {
                    boolean code = true;
                    if ("SKU条码".equals(type)) code = false;
                    List<SkuSn> snsList = (List<SkuSn>) beans.get("sns");
                    if (snsList == null) throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    // 系统中的 需要sn号的sku 数量 , key为sku编码，value为未完成收货数量
                    Map<String, Long> staLineskuQtyMap = staLineDao.findIsSnSkuCodeUnCompleteQtyByStaId(staid, ouid, new Boolean(code), new MapRowMapper());
                    if (staLineskuQtyMap == null) staLineskuQtyMap = new HashMap<String, Long>();
                    List<String> errlist = new ArrayList<String>();
                    // 比较excel中的sn号是否有相同的
                    for (int i = 0; i < snsList.size(); i++) {
                        for (int j = i + 1; j < snsList.size(); j++) {
                            // if(snsList.get(i).getSn() ==
                            // snsList.get(j).getSn() ||
                            // (snsList.get(i).getSn()).equals(snsList.get(j).getSn())){
                            if (snsList.get(i).getSn().equals(snsList.get(j).getSn())) {
                                errlist.add(snsList.get(j).getSn());
                            }
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {errlist.toString()});
                    Map<String, List<SkuSn>> snmap = new HashMap<String, List<SkuSn>>();
                    List<SkuSn> snlist = null;
                    errlist.clear();
                    // excel读取的记录，存在map中，key为sku编码，value为SkuSn的数组，数组的长度即为sn号的数量-以便和系统中的数量比较
                    for (SkuSn e : snsList) {
                        String sncode = e.getSku().getCode();
                        if (snmap.get(sncode) == null) {
                            snlist = new ArrayList<SkuSn>();
                            snlist.add(e);
                            snmap.put(sncode, snlist);
                        } else {
                            snlist = snmap.get(sncode);
                            snlist.add(e);
                        }
                        if (!StringUtils.hasText(e.getSn())) {
                            errlist.add(e.getSn());
                        }
                    }
                    if (!errlist.isEmpty() && errlist.size() > 0) throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_MEET_REGULATION, new Object[] {errlist.toString()});

                    // 取得系统中所有的sku编码 snmap - excel中的数据，staLineskuQtyMap -
                    // 系统中当前作业单的数据
                    Set<String> keys = snmap.keySet();
                    for (String s : keys) {
                        if (staLineskuQtyMap.get(s) == null) throw new BusinessException(ErrorCode.SKU_NOT_FOUND_IN_CURRENT_STA, new Object[] {s});
                        // 系统中的sku 未完成数量
                        long qty1 = staLineskuQtyMap.get(s);
                        // Excel中的sku 数量， excel中的数量应该小于系统中的数量
                        long qty2 = snmap.get(s).size();
                        if (qty1 < qty2) {
                            throw new BusinessException(ErrorCode.SKU_QUANTITY_NOT_MATCHING_SN_QUANTITY, new Object[] {s, qty1, qty2});
                        }
                    }
                    // =================
                    // 取得系统中所有的sku编码
                    // =================
                    snDao.deleteSNByStvIdSql(stv.getId());
                    Sku sku = null;
                    String skucode = null;
                    Warehouse wh = warehouseDao.getByOuId(ouid);
                    Long customerId = null;
                    if (wh != null && wh.getCustomer() != null) {
                        customerId = wh.getCustomer().getId();
                    }
                    for (SkuSn sn : snsList) {
                        skucode = sn.getSku().getCode();
                        if (code)
                            sku = skuDao.getByCode(skucode);
                        else
                            sku = skuDao.getByBarcode(skucode, customerId);
                        if (sku == null) throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {""});
                        try {
                            snDao.createSnByInboudImport(sn.getSn(), SkuSnStatus.CHECKING.getValue(), ouid, sku.getId(), stv.getId());
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUE, new Object[] {sn.getSn()});
                        }
                    }
                }
            }
        }
        return readStatus;
    }


    /**
     * 盘点 盘盈 需处理数据
     */
    public ReadStatus importCheckOverage(File file, Long invCkId) {
        InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(invCkId);
        if (ic == null) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_NOT_FOUND);
        }
        if (!(InventoryCheckStatus.UNEXECUTE.equals(ic.getStatus())) && !(InventoryCheckStatus.CHECKWHINVENTORY.equals(ic.getStatus()))) {
            throw new BusinessException(ErrorCode.INVENTORY_CHECK_STATUS_ERROR, new Object[] {ic.getCode()});
        }
        Map<String, Object> bean = new HashMap<String, Object>();
        try {
            ReadStatus rs = checkOverageImportReader.readAll(new FileInputStream(file), bean);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            /*********************** 核对数据 ********************************************************/
            // 盘点批中库位
            List<InventoryCheckDifferenceLine> lineList = inventoryCheckDifferenceLineDao.findCheckOverageListByIcid(invCkId);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                // 校验导入数据
                validateImporyCheckOverage(lineList, rs, ic, bean);
            }
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            // 核对无错误执行操作
            for (InventoryCheckDifferenceLine temp : lineList) {
                inventoryCheckDifferenceLineDao.save(temp);
            }
            return rs;
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private void validateImporyCheckOverage(List<InventoryCheckDifferenceLine> lineList, ReadStatus rs, InventoryCheck ic, Map<String, Object> bean) {
        List<InventoryCheckDifferenceLineCommand> invList = (List<InventoryCheckDifferenceLineCommand>) bean.get("invCk");
        // ***********************校验无SN号SHEET******************************//
        // 查询盘点关联商品
        if (invList == null || invList.size() == 0) {
            rs.setStatus(-1);
            rs.addException(new BusinessException(ErrorCode.INV_CHECK_IMPORT_IS_NULL));
            return;
        }
        if (invList.size() != lineList.size()) {
            rs.setStatus(-1);
            rs.addException(new BusinessException(ErrorCode.INV_CHECK_IMPORT_COUNT_ERROR));
            return;
        }
        int sheet0OffsetRow = 5;
        Map<String, List<InventoryCheckDifferenceLine>> lineMap = new HashMap<String, List<InventoryCheckDifferenceLine>>();
        for (InventoryCheckDifferenceLine l : lineList) {
            String key = l.getSku().getCode() + "_" + l.getLocation().getCode();
            if (lineMap.containsKey(key)) {
                lineMap.get(key).add(l);
            } else {
                List<InventoryCheckDifferenceLine> tempList = new ArrayList<InventoryCheckDifferenceLine>();
                tempList.add(l);
                lineMap.put(key, tempList);
            }
        }
        for (InventoryCheckDifferenceLineCommand cmd : invList) {
            boolean isOwner = false;
            // 校验商品
            if (cmd.getLocationCode() == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INV_CHECK_LOCATION_IS_NULL, new Object[] {sheet0OffsetRow}));
            }
            if (cmd.getSkuCode() == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INV_CHECK_SKU_IS_NULL, new Object[] {sheet0OffsetRow}));
            }
            String key = cmd.getSkuCode() + "_" + cmd.getLocationCode();
            InventoryCheckDifferenceLine idl = lineMap.containsKey(key) ? lineMap.get(key).get(0) : null;
            if (idl == null) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INV_CHECK_IMPORT_NOT_FOUND, new Object[] {sheet0OffsetRow, cmd.getSkuCode(), cmd.getLocationCode()}));
            }
            if (idl != null && (cmd.getQuantity() == null || !idl.getQuantity().equals(cmd.getQuantity()))) {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INV_CHECK_QUANTITY_ERROR, new Object[] {sheet0OffsetRow}));
            }

            String owner = cmd.getOwner();
            if (owner != null) {
                BiChannel cs = companyShopDao.getByName(owner);
                if (cs == null) {
                    rs.setStatus(-1);
                    rs.addException(new BusinessException(ErrorCode.INV_CHECK_OWNER_IS_NULL, new Object[] {sheet0OffsetRow}));
                } else if (idl != null) {
                    idl.setOwner(cs.getCode());
                    isOwner = true;
                }
            } else {
                rs.setStatus(-1);
                rs.addException(new BusinessException(ErrorCode.INV_CHECK_OWNER_IS_NULL, new Object[] {sheet0OffsetRow}));
            }
            if (isOwner) {
                List<InventoryCheckDifferenceLine> list = lineMap.get(key);
                if (list.size() < 2) {
                    lineMap.remove(key);
                } else {
                    list.remove(0);
                }
            }
            sheet0OffsetRow++;
        }
        // 判断是否有需要处理的 sku 商品没在处理列表内
        if (rs.getStatus() == ReadStatus.STATUS_SUCCESS && lineMap.size() > 0) {
            rs.setStatus(-1);
            for (String key : lineMap.keySet()) {
                for (InventoryCheckDifferenceLine l : lineMap.get(key)) {
                    rs.addException(new BusinessException(ErrorCode.INV_CHECK_IMPORT_NOT_SKU, new Object[] {l.getSku().getCode(), l.getLocation().getCode()}));
                }
            }
        }
    }


    /**
     * 销售导入
     * 
     * @param file
     * @param ouid
     * @return map key {"readStatus","snList"}
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> salesOutSnImport(File file, Long staId, Long ouId) {
        if (log.isDebugEnabled()) {
            log.debug("======begin salesOutSnImport=====");
        }
        Map<String, Object> result = new HashMap<String, Object>(2);
        try {
            // 读取excel
            Map<String, Object> beans = new HashMap<String, Object>();
            ReadStatus rs = salesOutSnImportReader.readSheet(new FileInputStream(file), 0, beans);
            result.put("readStatus", rs);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return result;
            } else {
                // 读取excel，核对数据
                List<SkuSnCommand> sns = (List<SkuSnCommand>) beans.get("sns");
                // 更新导入sn号状态
                final ExcelSheet sheet = salesOutSnImportReader.getDefinition().getExcelSheets().get(0);
                List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
                String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
                int offsetRow = 0;
                if (sns.size() == 0) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.IMPORT_SN_EXECL_ERROR, new Object[] {}));
                    return result;
                }
                if (log.isDebugEnabled()) {
                    log.debug("import sn list.size()" + sns.size());
                }
                for (SkuSnCommand skuSn : sns) {
                    if (StringUtil.isEmpty(skuSn.getBarcode())) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.IMPORT_SN_BARCODE_IS_NULL, new Object[] {SHEET_0, currCell}));
                    }
                    if (StringUtil.isEmpty(skuSn.getSn())) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.IMPORT_SN_IS_NULL, new Object[] {SHEET_0, currCell}));
                    }
                }
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    return result;
                }
                Map<String, String> importSN = new HashMap<String, String>();
                Map<String, String> snsMap = new HashMap<String, String>();
                List<SkuSnCommand> skusnList = snDao.findAllSnListByStaWithStatus(staId, ouId, SkuSnStatus.USING.getValue(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                for (SkuSnCommand sn : skusnList) {
                    snsMap.put(sn.getSn(), sn.getBarcode());
                }
                for (SkuSnCommand sn : sns) {
                    String barCode = snsMap.get(sn.getSn());
                    SkuSnCommand skusn = snDao.findBySn(sn.getSn(), ouId, SkuSnStatus.USING.getValue(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                    if (log.isDebugEnabled()) {
                        log.debug("=====find skusn===" + skusn);
                    }
                    // 验证是否是后端验证SKU商品
                    if (skusn != null) {
                        if (!StringUtil.isEmpty(skusn.getSnType())) {
                            // SN导入不支持后端验证SKU商品
                            if (SkuSnType.NO_BARCODE_SKU.equals(SkuSnType.valueOf(Integer.parseInt(skusn.getSnType())))) {
                                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                                rs.setStatus(-2);
                                rs.addException(new BusinessException(ErrorCode.IMPORT_SN_TYPE_ERROR, new Object[] {SHEET_0, currCell, sn.getBarcode()}));
                            }
                        }
                    }
                    if (barCode == null || !sn.getBarcode().equals(barCode)) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.IMPORT_SN_NOT_FOUND, new Object[] {SHEET_0, currCell, sn.getBarcode(), sn.getSn()}));
                    } else if (importSN.containsKey(sn.getSn())) {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {SHEET_0, sn.getSn()}));
                    } else {
                        importSN.put(sn.getSn(), barCode);
                    }
                    offsetRow++;
                }
                if (log.isDebugEnabled()) {
                    log.debug("======end salesOutSnImport=====");
                }
                result.put("snList", sns);
                return result;
            }
        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }


    }

    /**
     * 保质期商品导入bin.hu
     */
    @SuppressWarnings("unchecked")
    public ReadStatus createSkuForShelfLife(File file) throws Exception {
        Sku sku = null;
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        rs = skuShelfLifeReader.readAll(new FileInputStream(file), beans);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        String type = (String) beans.get("type");// 获取商品表达方式
        int timeT = 0;
        List<Sku> skuList = (List<Sku>) beans.get("skuList");// 获取对应数据
        // 验证商品是否存在，是否是保质期商品，时间维护类型是否填写正确
        rs = skuShelfLifeValidate(rs, type, skuList);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        for (Sku s : skuList) {
            MsgSkuUpdate msg = new MsgSkuUpdate();
            if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
                // 商品条码
                sku = skuDao.getByBarcode1(s.getBarCode());
            }
            if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
                // 商品编码
                sku = skuDao.getByCode(s.getBarCode());
            }
            sku.setStoremode(InboundStoreMode.SHELF_MANAGEMENT);// 保质期管理
            sku.setValidDate(s.getValidDate());// 保质期天数
            if (s.getName().equals("年")) {
                timeT = TimeTypeMode.YEAR.getValue();
            }
            if (s.getName().equals("月")) {
                timeT = TimeTypeMode.MONTH.getValue();
            }
            if (s.getName().equals("日")) {
                timeT = TimeTypeMode.DAY.getValue();
            }
            sku.setTimeType(TimeTypeMode.valueOf(timeT));// 时间维护类型
            sku.setLastModifyTime(new Date());
            skuDao.save(sku);
            // 插入发送OMS队列表
            msg.setSku(sku);
            msg.setCustomer(sku.getCustomer());
            msg.setValidDate(s.getValidDate());
            msg.setTimeType(TimeTypeMode.valueOf(timeT));
            msg.setExeCount(0);
            msg.setCreatTime(new Date());
            msgSkuUpdateDao.save(msg);
        }
        return rs;
    }

    /**
     * 验证SKU是否存在
     */
    private ReadStatus skuShelfLifeValidate(ReadStatus rs, String type, List<Sku> skuList) {
        Sku sku = null;
        for (Sku s : skuList) {
            String[] timeType = {"年", "月", "日"};
            int error = 0;
            if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
                // 商品条码
                sku = skuDao.getByBarcode1(s.getBarCode());
            }
            if (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) {
                // 商品编码
                sku = skuDao.getByCode(s.getBarCode());
            }
            if (sku == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKUCODE_NOTEXISTS, new Object[] {type, s.getBarCode()}));
            }
            // 验证是不是保质期商品
            if (sku.getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.SKU_NOT_SHELF_MANAGEMENT_ERROR, new Object[] {type, s.getBarCode()}));
            }
            for (int i = 0; i < timeType.length; i++) {
                if (s.getName().equals(timeType[i])) {
                    error = 1;
                    break;
                }
            }
            if (error == 0) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKU_TIMETYPE_TYPE_ERROR, new Object[] {type, sku.getBarCode()}));
            }
        }
        return rs;
    }

    /**
     * 如果是保质期商品，验证预警天数是否填写
     */
    public ReadStatus checkShelfLifeWarningDate(ReadStatus rs, String type, List<Sku> skuList) {
        for (Sku sku : skuList) {
            String[] timeType = {"年", "月", "日"};
            int error = 0;
            if (sku.getWarningDate() == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKU_WARNINGDATE, new Object[] {type, sku.getBarCode()}));
            }
            if (StringUtil.isEmpty(sku.getName())) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKU_TIMETYPE_ERROR, new Object[] {type, sku.getBarCode()}));
            }
            for (int i = 0; i < timeType.length; i++) {
                if (sku.getName().equals(timeType[i])) {
                    error = 1;
                    break;
                }
            }
            if (error == 0) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKU_TIMETYPE_TYPE_ERROR, new Object[] {type, sku.getBarCode()}));
            }
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus checkInventoryCheckImport(ReadStatus rs, Map<String, Object> importBean, Long customerId) throws Exception {
        List<InventoryCommand> invList = (List<InventoryCommand>) importBean.get("inventory");
        Sku sku = null;
        for (InventoryCommand i : invList) {
            sku = skuDao.getByCode(i.getSkuCode());
            if (sku == null) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EI_SKU_NOTEXISTS, new Object[] {i.getSkuCode()}));
                return rs;
            }
            // 验证商品对应客户是否=仓库对应客户
            if (sku.getCustomer().getId() != customerId) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.SKU_WAREHOUSE_CUSTOMER_ERROR, new Object[] {"JMSKUCODE:", i.getSkuCode()}));
                return rs;
            }
            // 验证是不是保质期商品
            if ("临近保质期".equals(i.getInvStatusName())) {
                if (sku.getStoremode().getValue() != InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_TYPE_ERROR, new Object[] {"JMSKUCODE:", i.getSkuCode()}));
                    return rs;
                }
            }
            rs = wareHouseManager.checkPoductionDateAndExpireDate(i.getPoductionDate(), i.getSexpireDate(), rs, i.getSkuCode(), sku, null, 0);
            if (rs != null && rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
        }
        return rs;
    }

    /**
     * 针对保质期商品盘点 插入库存盘点明细表逻辑处理bin.hu
     */
    public void margenIncentoryCheckDifLinePC(InventoryCheck ic, Map<String, Long> importData, Map<String, String> importDataToSM) throws Exception {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        // 获取库存数量
        Map<String, Long> invData = inventoryDao.findQtyByInventoryCheckPC(ic.getId(), new MapRowMapper());
        Set<String> keyset = new HashSet<String>();
        if (importData != null && importData.size() > 0) {
            keyset.addAll(importData.keySet());
        }
        if (invData != null && invData.size() > 0) {
            keyset.addAll(invData.keySet());
        }
        // keyset.addAll(importDataToSM.keySet()); // 此处 不应该加
        for (String key : keyset) {
            Long cqty = importData == null ? null : importData.get(key);
            Long iqty = invData == null ? null : invData.get(key);
            String stDate = importDataToSM.get(key);
            Long qty = null;
            if (cqty == null) {
                // 导入文件无此商品记录
                qty = -iqty;
            } else if (iqty == null) {
                // 库存无此商品记录
                qty = cqty;
            } else {
                // 导入和库存都有此商品记录
                qty = cqty - iqty;
            }
            if (qty != 0) {
                String[] strs = key.split("-");
                Long skuId = Long.parseLong(strs[0]);
                Long locId = Long.parseLong(strs[1]);
                WarehouseLocation loc = new WarehouseLocation();
                loc.setId(locId);
                Sku sku = skuDao.getByPrimaryKey(skuId);
                InventoryStatus is = inventoryStatusDao.getByPrimaryKey(Long.parseLong(strs[2]));// 库存状态
                InventoryCheckDifferenceLine difLine = new InventoryCheckDifferenceLine();
                difLine.setInventoryCheck(ic);
                difLine.setLocation(loc);
                difLine.setQuantity(qty);
                difLine.setSku(sku);
                difLine.setStatus(is);
                if (sku.getStoremode().getValue() == InboundStoreMode.SHELF_MANAGEMENT.getValue()) {
                    if (stDate == null) {
                        // 导入无此商品时间数据
                        // 如果是保质期商品需要导入过期时间
                        difLine.setExpireDate(formatDate.parse(strs[3]));
                    } else {
                        // 导入有此商品时间数据
                        String[] strs1 = stDate.split("-");
                        if (!StringUtil.isEmpty(strs1[0])) {
                            difLine.setProductionDate(formatDate.parse(strs1[0]));
                        }
                        difLine.setExpireDate(formatDate.parse(strs1[1]));
                    }
                }
                inventoryCheckDifferenceLineDao.save(difLine);
            }
        }
    }

    @Transactional
    public ReadStatus importForBatchReceiving(String staCode, List<StvLineCommand> stvLines, User creator) throws Exception {
        ReadStatus readStatus = new DefaultReadStatus();
        List<StvLine> stvLinesValidate = new ArrayList<StvLine>();
        StockTransApplication sta = null;
        if (null != staCode && !"".equals(staCode)) sta = staDao.getByCode(staCode);
        // 判断该作业单是否包含SN商品
        Long s = skuDao.findSkuCountByStaCode(staCode, 1L, 33L, new SingleColumnRowMapper<Long>(Long.class));
        BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        boolean check = false;// 判断是否是特定品牌包含保质期商品需要无条件收货
        boolean vmiCheck = false;
        VmiDefaultInterface vv = null;
        if (!StringUtil.isEmpty(bi.getDefaultCode())) {
            // 判断是否有品牌通用定制逻辑
            vv = vmiDefaultFactory.getVmiDefaultInterface(bi.getDefaultCode());
            check = vv.importForBatchReceiving();
            vmiCheck = true;
        }
        if (!check) {
            if (s > 0) {// 包含则抛异常返回
                readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                readStatus.addException(new BusinessException(ErrorCode.ERROR_NOT_SNSKU_OR_NOT_STORESKU, new Object[] {staCode}));
                return readStatus;
            }
        }
        // 作业单数据校验
        Iterator<StvLineCommand> it = stvLines.iterator();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        List<StaLine> stalineList = staLineDao.findByStaId(sta.getId());
        boolean lineFlag = true;
        boolean locFlag = true;
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        while (it.hasNext()) {
            StvLineCommand stvLine = it.next();
            String skuBarCode = stvLine.getSku().getBarCode().trim();
            stvLine.getSku().setBarCode(skuBarCode);
            String locationCode = stvLine.getLocation().getCode().trim();
            stvLine.getLocation().setCode(locationCode);
            String invStatusName = stvLine.getInvStatus().getName().trim();
            stvLine.getInvStatus().setName(invStatusName);
            // long quantity = stvLine.getQuantity();
            if (null != skuBarCode && null != locationCode && stvLine.getQuantity() > 0) {
                // 作业单明细校验
                StaLine staLine = null;
                for (StaLine sl : stalineList) {
                    boolean isInvStatus = sl.getInvStatus().getName().equals(invStatusName);
                    boolean isBarCode = (sl.getSku().getBarCode() == null && !StringUtils.hasLength(skuBarCode)) || sl.getSku().getBarCode().equals(skuBarCode);
                    // boolean isQuantity = (sl.getQuantity() == quantity);
                    if (isInvStatus && isBarCode) {
                        staLine = sl;
                        break;
                    }
                }
                if (staLine == null) {// 作业单号【{0}】Sku条码：【{1}】不在当前收货计划
                    lineFlag = false;
                    errors.add(new BusinessException(ErrorCode.ERROR_NOT_STALINE_SKU, new Object[] {staCode, skuBarCode}));
                    break;
                }
                // 商品数量
                String key = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId();
                if (qtyMap.containsKey(key)) {
                    Long total = qtyMap.get(key);
                    qtyMap.put(key, total + stvLine.getQuantity());
                } else {
                    qtyMap.put(key, stvLine.getQuantity());
                }
                // 库位校验
                WarehouseLocation location = wareHouseManager.findLocationByCode(locationCode, sta.getMainWarehouse().getId());
                if (location == null) {// 作业单号【{0}】库位：【{1}】可能被锁定、禁用或未找到
                    locFlag = false;
                    errors.add(new BusinessException(ErrorCode.ERROR_WRONG_LOC_CODE, new Object[] {staCode, locationCode}));
                    break;
                }
                Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                StvLine validateLine = new StvLine();
                validateLine.setSku(sku);
                validateLine.setLocation(location);
                validateLine.setInvStatus(staLine.getInvStatus());
                validateLine.setStaLine(staLine);
                validateLine.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                validateLine.setQuantity(stvLine.getQuantity());
                if (vmiCheck) {
                    vv.importForBatchReceivingSaveStvLine(validateLine, sta, sku);
                }
                stvLinesValidate.add(validateLine);
            }
        }
        // 判断数量是否与计划量一致
        if (errors.isEmpty()) {
            Map<String, Long> staqtyMap = new HashMap<String, Long>();
            for (StaLine staLine : stalineList) {
                String key = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId();
                if (staqtyMap.containsKey(key)) {
                    Long total = staqtyMap.get(key);
                    staqtyMap.put(key, total + staLine.getQuantity());
                } else {
                    staqtyMap.put(key, staLine.getQuantity());
                }
                Long total = qtyMap.get(key);
                // 判断数量 不等于计划量
                if (total == null || !total.equals(staLine.getQuantity())) {
                    errors.add(new BusinessException(ErrorCode.ERROR_STA_SKU_QTY_NOT_SAME, new Object[] {staCode}));
                }
            }
            if (qtyMap.size() != staqtyMap.size()) {
                errors.add(new BusinessException(ErrorCode.ERROR_STA_SKU_QTY_NOT_SAME, new Object[] {staCode}));
            }
        }
        if (lineFlag && locFlag && errors.isEmpty()) {
            // 批量收货
            StockTransVoucher stvCreated = wareHouseManager.purchaseReceiveStep1(sta, stvLinesValidate, null, creator, null, null, false);
            if (log.isDebugEnabled()) {
                log.debug("stv created : {}", stvCreated);
            }
            wareHouseManager.purchaseReceiveStep2(stvCreated.getId(), stvLineDao.findStvLineListByStvId(stvCreated.getId()), false, creator, false, true);
        }
        if (!errors.isEmpty()) {
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.getExceptions().addAll(errors);
        }
        return readStatus;
    }


    @Transactional
    public ReadStatus findShelfLifeSkuCountByStaCode(String staCode, List<StvLineCommand> stvLines, User creator) throws Exception {
        ReadStatus readStatus = new DefaultReadStatus();
        List<StvLine> stvLinesValidate = new ArrayList<StvLine>();
        StockTransApplication sta = null;
        if (null != staCode && !"".equals(staCode)) sta = staDao.getByCode(staCode);
        // 判断该作业单是不是保质期商品
        Long s = skuDao.findShelfLifeSkuCountByStaCode(staCode, 33L, new SingleColumnRowMapper<Long>(Long.class));
        BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        boolean vmiCheck = false;
        VmiDefaultInterface vv = null;
        if (!StringUtil.isEmpty(bi.getDefaultCode())) {
            // 判断是否有品牌通用定制逻辑
            vv = vmiDefaultFactory.getVmiDefaultInterface(bi.getDefaultCode());
            vv.importForBatchReceiving();
            vmiCheck = true;
        }
        /*
         * if (!check) { if (s == 0) {// 不包含则抛异常返回 readStatus.setStatus(ReadStatus.STATUS_SUCCESS -
         * 1); readStatus.addException(new BusinessException(ErrorCode.ERROR_NOT_SNSKU, new Object[]
         * {staCode})); return readStatus; } }
         */

        if (s == 0) {// 不包含则抛异常返回
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.addException(new BusinessException(ErrorCode.ERROR_NOT_SNSKU, new Object[] {staCode}));
            return readStatus;
        }
        // 作业单数据校验
        Iterator<StvLineCommand> it = stvLines.iterator();
        List<BusinessException> errors = new ArrayList<BusinessException>();
        List<StaLine> stalineList = staLineDao.findByStaId(sta.getId());
        boolean lineFlag = true;
        boolean locFlag = true;
        Map<String, Long> qtyMap = new HashMap<String, Long>();
        while (it.hasNext()) {
            StvLineCommand stvLine = it.next();
            String skuBarCode = stvLine.getSku().getBarCode().trim();
            stvLine.getSku().setBarCode(skuBarCode);
            String locationCode = stvLine.getLocation().getCode().trim();
            stvLine.getLocation().setCode(locationCode);
            String invStatusName = stvLine.getInvStatus().getName().trim();
            stvLine.getInvStatus().setName(invStatusName);
            String sexpireDate = stvLine.getSexpireDate().trim().replaceAll("-", "").replaceAll("_", "");
            // long quantity = stvLine.getQuantity();
            if (null != skuBarCode && null != locationCode && stvLine.getQuantity() > 0 && null != sexpireDate) {
                // 作业单明细校验
                StaLine staLine = null;
                for (StaLine sl : stalineList) {
                    boolean isInvStatus = sl.getInvStatus().getName().equals(invStatusName);
                    boolean isBarCode = (sl.getSku().getBarCode() == null && !StringUtils.hasLength(skuBarCode)) || sl.getSku().getBarCode().equals(skuBarCode);
                    // boolean isQuantity = (sl.getQuantity() == quantity);
                    if (isInvStatus && isBarCode) {
                        staLine = sl;
                        break;
                    }
                }
                if (staLine == null) {// 作业单号【{0}】Sku条码：【{1}】不在当前收货计划
                    lineFlag = false;
                    errors.add(new BusinessException(ErrorCode.ERROR_NOT_STALINE_SKU, new Object[] {staCode, skuBarCode}));
                    break;
                }
                // 商品数量
                String key = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId();
                if (qtyMap.containsKey(key)) {
                    Long total = qtyMap.get(key);
                    qtyMap.put(key, total + stvLine.getQuantity());
                } else {
                    qtyMap.put(key, stvLine.getQuantity());
                }
                // 库位校验
                WarehouseLocation location = wareHouseManager.findLocationByCode(locationCode, sta.getMainWarehouse().getId());
                if (location == null) {// 作业单号【{0}】库位：【{1}】可能被锁定、禁用或未找到
                    locFlag = false;
                    errors.add(new BusinessException(ErrorCode.ERROR_WRONG_LOC_CODE, new Object[] {staCode, locationCode}));
                    break;
                }
                Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                StvLine validateLine = new StvLine();
                validateLine.setSku(sku);
                validateLine.setLocation(location);
                validateLine.setInvStatus(staLine.getInvStatus());
                validateLine.setStaLine(staLine);
                validateLine.setOwner(staLine.getOwner() == null ? sta.getOwner() : staLine.getOwner());
                validateLine.setQuantity(stvLine.getQuantity());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                validateLine.setExpireDate(sdf.parse(sexpireDate.trim()));
                if (vmiCheck) {
                    vv.importForBatchReceivingSaveStvLine(validateLine, sta, sku);
                }
                stvLinesValidate.add(validateLine);
            }
        }
        // 判断数量是否与计划量一致
        if (errors.isEmpty()) {
            Map<String, Long> staqtyMap = new HashMap<String, Long>();
            for (StaLine staLine : stalineList) {
                String key = staLine.getSku().getId() + "_" + staLine.getInvStatus().getId();
                if (staqtyMap.containsKey(key)) {
                    Long total = staqtyMap.get(key);
                    staqtyMap.put(key, total + staLine.getQuantity());
                } else {
                    staqtyMap.put(key, staLine.getQuantity());
                }
                Long total = qtyMap.get(key);
                // 判断数量 不等于计划量
                if (total == null || !total.equals(staLine.getQuantity())) {
                    errors.add(new BusinessException(ErrorCode.ERROR_STA_SKU_QTY_NOT_SAME, new Object[] {staCode}));
                }
            }
            if (qtyMap.size() != staqtyMap.size()) {
                errors.add(new BusinessException(ErrorCode.ERROR_STA_SKU_QTY_NOT_SAME, new Object[] {staCode}));
            }
        }
        if (lineFlag && locFlag && errors.isEmpty()) {
            // 批量收货
            StockTransVoucher stvCreated = wareHouseManager.purchaseReceiveStep1(sta, stvLinesValidate, null, creator, null, null, false);
            if (log.isDebugEnabled()) {
                log.debug("stv created : {}", stvCreated);
            }
            wareHouseManager.purchaseReceiveStep2(stvCreated.getId(), stvLineDao.findStvLineListByStvId(stvCreated.getId()), false, creator, false, true);
        }
        if (!errors.isEmpty()) {
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.getExceptions().addAll(errors);
        }
        return readStatus;
    }

    @Override
    public ReadStatus importStaDeliveryInfo(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = importPrintReader.readAll(new FileInputStream(file), beans);
            @SuppressWarnings("unchecked")
            List<ImportPrintData> listInfo = (List<ImportPrintData>) beans.get("infoLines");
            if (listInfo == null || listInfo.size() == 0) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA, new Object[] {});
            } else {
                for (ImportPrintData info : listInfo) {
                    importPrintDataDao.save(info);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return readStatus;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus importRefSku(File file, Long tagId) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = new DefaultReadStatus();
        SkuTag skuTag = skuTagDao.getByPrimaryKey(tagId);
        if (null == skuTag) {
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.addException(new BusinessException(ErrorCode.SKU_TAG_NOT_FOUND));
            return readStatus;
        }
        try {
            readStatus = refSkuReader.readAll(new FileInputStream(file), beans);
            List<SkuCommand> skuList = (List<SkuCommand>) beans.get("refSkus");
            if (skuList == null || skuList.size() == 0) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA, new Object[] {});
            } else {
                skuTagDao.deleteSkuRef(tagId);
                int num = 0;
                StringBuilder sb = new StringBuilder();
                List<String> success = new ArrayList<String>();
                List<String> errors = new ArrayList<String>();
                for (SkuCommand sku : skuList) {
                    if (null == sku.getCode()) continue;
                    Sku current = skuDao.getByCode(sku.getCode());
                    if (null == current) {
                        if (!errors.contains(sku.getCode())) {
                            num++;
                            if (1 == num) {
                                sb.append("<br/>").append(sku.getCode());
                            } else {
                                sb.append(",").append(sku.getCode());
                            }
                            if (num % 4 == 0) {
                                sb.append("<br/>");
                            }
                            errors.add(sku.getCode());
                        }
                        continue;
                    }
                    if (!success.contains(sku.getCode())) {
                        skuTagDao.insertSkuRef(tagId, current.getId());
                        success.add(sku.getCode());
                    }

                }
                if (0 < num) {
                    throw new BusinessException(ErrorCode.SKU_REF_IMPORT_FAIL, new Object[] {num, sb.toString()});
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return readStatus;
    }

    @Override
    public ReadStatus importPickZoneInfo(File file, Long userId, Long ouId, Integer flag) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        Integer errorFlag = 0;
        Integer overrideFlag = 0;

        try {
            readStatus = pickZoneImportReader.readAll(new FileInputStream(file), beans);
            if (readStatus.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return readStatus;
            }
            @SuppressWarnings("unchecked")
            List<WhPickZoneInfoCommand> pickZoneInfoList = (List<WhPickZoneInfoCommand>) beans.get("pickZoneUpload");
            final ExcelSheet sheet = pickZoneImportReader.getDefinition().getExcelSheets().get(0);
            List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
            String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
            if (pickZoneInfoList == null || pickZoneInfoList.size() == 0) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA, new Object[] {});
            } else {
                int offsetRow = 0;
                for (WhPickZoneInfoCommand info : pickZoneInfoList) {
                    // 校验导入文件数据
                    String location = info.getLocation();
                    String district = info.getDistrict();
                    Integer sort = info.getSort();
                    String code = info.getCode();
                    if (location == null) {
                        // 缺失库位或者库区
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        readStatus.setStatus(-2);
                        readStatus.addException(new BusinessException(ErrorCode.LOCATION_OR_DISTRICT_IS_NULL, new Object[] {currCell}));
                        errorFlag = 1;
                    } else {
                        WhPickZoneInfoCommand whPickZoneInfo = warehouseLocationDao.findLocationByLocationAndDistrict(ouId, location, district, new BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand.class));
                        if (whPickZoneInfo == null) {
                            // 导入的库区或者库位不存在
                            String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                            readStatus.setStatus(-2);
                            readStatus.addException(new BusinessException(ErrorCode.PICK_ZONE_LOCATION_DISTRICT_IS_NULL, new Object[] {2, currCell, "库位: " + location + " 和 库区: " + district}));
                            errorFlag = 1;
                        }
                        if ((sort == null) ^ (code == null)) {
                            // 导入的顺序和拣货区域编码应该同时为空或同时不为空
                            String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                            readStatus.setStatus(-2);
                            readStatus.addException(new BusinessException(ErrorCode.PICK_ZONE_AND_SORT_REQUIRED, new Object[] {2, currCell, "库位: " + location + " 和 库区: " + district}));
                            errorFlag = 1;
                        }
                        if (code != null) {
                            // 导入的拣货区域不存在
                            WhPickZoon pickZone = whPickZoneDao.findPickZoneByCode(code, ouId, new BeanPropertyRowMapper<WhPickZoon>(WhPickZoon.class));
                            if (pickZone == null || pickZone.getStatus() == 0) {
                                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                                readStatus.setStatus(-2);
                                readStatus.addException(new BusinessException(ErrorCode.PICK_ZONE_IS_NULL, new Object[] {currCell, code}));
                                errorFlag = 1;
                            }
                        }
                        // if(whPickZoneInfo != null && flag == 0){
                        // //导入的库位已存在拣货区域
                        // String pickZoneCode = whPickZoneInfo.getCode();
                        // Integer sort1 = whPickZoneInfo.getSort();
                        // if(pickZoneCode != null){
                        // if((readStatus.getStatus() != -2)){
                        // readStatus.setStatus(-1);
                        // }
                        // readStatus.addException(new
                        // BusinessException(ErrorCode.PICK_ZONE_LOCATION_DISTRICT_IS_EXIST, new
                        // Object[] {"库位: "+location+"; 库区: "+district, pickZoneCode, sort1}));
                        // overrideFlag = 1;
                        // }
                        // }
                    }
                    offsetRow++;
                }
                if (errorFlag == 0 && overrideFlag == 0) {
                    for (WhPickZoneInfoCommand info : pickZoneInfoList) {
                        String location = info.getLocation();
                        String district = info.getDistrict();
                        String pickZoneCode = info.getCode();
                        Integer sort = info.getSort();
                        Date lastModifyTime = new Date();
                        warehouseLocationDao.updateByLocationAndDistrict(ouId, location, district, pickZoneCode, sort, userId, null, lastModifyTime);
                        /*
                         * try{ WhLocationLog wll = new WhLocationLog();
                         * wll.setLastModifyUserId(userId); wll.setLastModifyTime(createDate);
                         * wll.setCode(location); wll.setWhId(ouId); wll.setZoonId(zoonId);
                         * wll.setSort(sort);
                         * 
                         * } catch(Exception e){ log.debug("库位变动日志添加失败！"); }
                         */
                        warehouseLocationDao.createLog(ouId, location, pickZoneCode, sort, userId, lastModifyTime);
                        /*
                         * String location = info.getLocation(); String district =
                         * info.getDistrict(); Integer sort = info.getSort(); WhPickZoneInfoCommand
                         * whPickZoneInfo =
                         * warehouseLocationDao.findLocationByLocationAndDistrict(ouId, location,
                         * district, new
                         * BeanPropertyRowMapper<WhPickZoneInfoCommand>(WhPickZoneInfoCommand
                         * .class)); if(whPickZoneInfo == null){ //导入的库区或者库位不存在
                         * 
                         * String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                         * readStatus.setStatus(-2); readStatus.addException(new
                         * BusinessException(ErrorCode.PICK_ZONE_LOCATION_DISTRICT_IS_NULL, new
                         * Object[] {2, currCell, info.getLocation()+"|"+info.getDistrict()}));
                         * offsetRow++; } else if(offsetRow == 0){
                         */
                        // 不存在任何错误

                        /*
                         * if(pickZoneCode != null){ //导入的库位已经创建过拣货区域和顺序 if(flag == 0){ //给用户提示
                         * 是否覆盖原来已创建好的拣货区域和顺序 readStatus.setStatus(-1); readStatus.addException(new
                         * BusinessException(ErrorCode.PICK_ZONE_LOCATION_DISTRICT_IS_EXIST, new
                         * Object[] {info.getLocation()+"|"+info.getDistrict(),
                         * whPickZoneInfo.getName()})); } else{ //用户点击"确认覆盖"后 更新数据库 Date createDate
                         * = new Date(); warehouseLocationDao.updateByLocationAndDistrict(ouId,
                         * location, district, pickZoneCode, sort, userId, createDate); } } else{
                         */
                        // 导入的库位没有创建拣货区域和顺序

                        // }
                        // }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return readStatus;
    }

    @Override
    public ReadStatus importBatchProcucrementInbound(String staCode, List<StvLineCommand> stvLines, User creator, Long ouId) throws Exception {
        ReadStatus readStatus = new DefaultReadStatus();
        StockTransApplication sta = staDao.getByCode(staCode);
        StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
        if (null != stv && !StockTransVoucherStatus.CREATED.equals(stv.getStatus())) {
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.addException(new BusinessException(ErrorCode.STV_STATUS_ERROR, new Object[] {staCode}));
            return readStatus;
        }
        String staSlipCode = sta.getSlipCode2();
        // 获取出库效期
        List<StvLineCommand> slList = stvLineDao.findExpireDateByStaSlipCode(staSlipCode, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
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
        boolean lineFlag = true;
        boolean locationFlag = true;
        List<BusinessException> errors = new ArrayList<BusinessException>();
        List<StaLine> stalineList = staLineDao.findByStaId(sta.getId());
        List<StvLine> stvl = stvLineDao.findStvLineListByStvId(stv.getId());
        Map<String, StvLine> mapLine = new HashMap<String, StvLine>();
        for (StvLine stvLine : stvl) {
            String key = stvLine.getSku().getBarCode() + "_" + stvLine.getInvStatus().getName();
            StvLine cmd = null;
            if (mapLine.containsKey(key)) {
                cmd = mapLine.get(key);
                cmd.setQuantity(cmd.getQuantity() + mapLine.get(key).getQuantity());
            } else {
                cmd = stvLine;
                mapLine.put(key, cmd);
            }
        }
        List<StvLine> lineList = new ArrayList<StvLine>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        for (StvLineCommand ls : stvLines) {
            Sku sku = skuDao.getByCode(ls.getSkuCode());
            ls.setSku(sku);
            String locationCode = ls.getLocationCode().trim();
            String invStatusName = ls.getIntInvstatusName().trim();
            String skuBarCode = ls.getBarCode().trim();
            StaLine staLine = null;
            StvLine stvLine = null;
            // 获取stVLine明细行
            for (StvLine stvLs : stvl) {
                boolean isInvStatus = stvLs.getInvStatus().getName().equals(invStatusName);
                boolean isBarCode = (stvLs.getSku().getBarCode() == null && !StringUtils.hasLength(skuBarCode)) || stvLs.getSku().getBarCode().equals(skuBarCode);
                if (isInvStatus && isBarCode) {
                    stvLine = stvLs;
                    break;
                }
            }
            // 获取staLine明细行
            for (StaLine stal : stalineList) {
                boolean isInvStatus = stal.getInvStatus().getName().equals(invStatusName);
                boolean isBarCode = (stal.getSku().getBarCode() == null && !StringUtils.hasLength(skuBarCode)) || stal.getSku().getBarCode().equals(skuBarCode);
                // 校验明细行
                if (isInvStatus && isBarCode) {
                    staLine = stal;
                    break;
                }
            }
            if (staLine == null || stvLine == null) {
                lineFlag = false;
                errors.add(new BusinessException(ErrorCode.ERROR_NOT_STALINE_SKU, new Object[] {staCode, skuBarCode}));
                break;
            }
            // 库位校验
            WarehouseLocation location = wareHouseManager.findLocationByCode(locationCode, sta.getMainWarehouse().getId());
            if (location == null) {// 作业单号【{0}】库位：【{1}】可能被锁定、禁用或未找到
                locationFlag = false;
                errors.add(new BusinessException(ErrorCode.ERROR_WRONG_LOC_CODE, new Object[] {staCode, locationCode}));
                break;
            }
            if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                whManagerProxy.setStvLineProductionDateAndExpireDate(ls, ls.getStrPoductionDate(), ls.getStrExpireDate());
                // 校验保质期商品 收货校验保质期于出库作业单配货清单中，如不正确提示错误信息
                String strEpireDate = formatDate.format(ls.getExpireDate());
                String key = sku.getId() + "_" + strEpireDate;
                if (map.containsKey(key)) {
                    List<StvLineCommand> tempList = map.get(key);
                    Long quantity = ls.getReceiptQty();// 页面数量
                    for (int i = 0; i < tempList.size(); i++) {
                        StvLineCommand stvLcomd = tempList.get(i);
                        if (staLine.getQuantity() >= quantity) {
                            lineList.add(createStvLine(sku, stvLine.getOwner(), warehouseLocationDao.findByLocationCode(ls.getLocationCode(), stv.getWarehouse().getId()), ls.getInvStatus(), ls.getTransactionType(), quantity, stvLine.getBatchCode(),
                                    ls.getInBoundTime(), stvLcomd.getProductionDate(), stvLcomd.getValidDate(), stvLcomd.getExpireDate(), staLine, stv));
                            stvLcomd.setQuantity(stvLcomd.getQuantity() - quantity);
                            quantity = 0l;
                        } else {
                            lineList.add(createStvLine(sku, stvLine.getOwner(), warehouseLocationDao.findByLocationCode(ls.getLocationCode(), stv.getWarehouse().getId()), ls.getInvStatus(), ls.getTransactionType(), quantity, stvLine.getBatchCode(),
                                    ls.getInBoundTime(), stvLcomd.getProductionDate(), stvLcomd.getValidDate(), stvLcomd.getExpireDate(), staLine, stv));
                            quantity -= stvLcomd.getQuantity();
                            stvLcomd.setQuantity(0l);
                        }
                        if (stvLcomd.getQuantity() < 1) {
                            tempList.remove(i);
                        }
                        if (quantity.equals(0L)) {
                            break;
                        }
                    }
                    if (quantity > 0) {
                        // 保质期不存在出库记录中
                        errors.add(new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate}));
                        break;
                    }
                } else {
                    // 抛出错误提示保质期不存在出库记录中
                    errors.add(new BusinessException(ErrorCode.RETURN_ORDER_ERROR, new Object[] {sku.getBarCode(), strEpireDate}));
                    break;
                }

            } else {
                String key = skuBarCode + "_" + invStatusName;
                // 数量是否等于计划量
                if (mapLine.containsKey(key)) {
                    Long receiptQty = ls.getReceiptQty();
                    Long quantity = mapLine.get(key).getQuantity();
                    if (quantity.longValue() != receiptQty.longValue() || quantity.longValue() != ls.getTotalQuantity().longValue()) {
                        errors.add(new BusinessException(ErrorCode.ERROR_STA_SKU_QTY_NOT_SAME, new Object[] {staCode}));
                        break;
                    }
                } else {
                    errors.add(new BusinessException(ErrorCode.ERROR_NOT_STALINE_SKU, new Object[] {staCode, skuBarCode}));
                    break;
                }
                lineList.add(createStvLine(sku, stvLine.getOwner(), warehouseLocationDao.findByLocationCode(ls.getLocationCode(), stv.getWarehouse().getId()), ls.getInvStatus(), ls.getTransactionType(), ls.getReceiptQty(), stvLine.getBatchCode(),
                        ls.getInBoundTime(), ls.getProductionDate(), ls.getValidDate(), ls.getExpireDate(), staLine, stv));
            }


        }

        if (lineFlag && locationFlag && errors.isEmpty()) {
            wareHouseManager.purchaseReceiveStep2(stv.getId(), lineList, true, creator, false, true);
        }
        if (!errors.isEmpty()) {
            readStatus.setStatus(ReadStatus.STATUS_SUCCESS - 1);
            readStatus.getExceptions().addAll(errors);
        }
        return readStatus;
    }

    private StvLine createStvLine(Sku sku, String owner, WarehouseLocation loc, InventoryStatus invStatus, TransactionType tranType, Long quantity, String batchCode, Date inboundTime, Date productionDate, Integer validDate, Date expireDate,
            StaLine staLine, StockTransVoucher stv) {
        StvLine stvLine = new StvLine();
        stvLine.setSku(sku);
        stvLine.setBatchCode(batchCode);
        stvLine.setDirection(stv.getDirection());
        stvLine.setLocation(loc);
        stvLine.setDistrict(loc.getDistrict());
        stvLine.setInBoundTime(inboundTime);
        stvLine.setInvStatus(invStatus);
        stvLine.setOwner(owner);
        stvLine.setQuantity(quantity);
        stvLine.setStv(stv);
        stvLine.setStaLine(staLine);
        stvLine.setTransactionType(tranType);
        stvLine.setWarehouse(stv.getWarehouse());
        stvLine.setProductionDate(productionDate);
        stvLine.setValidDate(validDate);
        stvLine.setExpireDate(expireDate);
        return stvLine;
    }

    /**
     * 导入货箱定位规则
     * 
     * @param file
     * @param ouId
     * @return
     * @throws Exception
     */
    public ReadStatus importInboundRole(File file, Long ouId) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        StringBuilder sb = new StringBuilder();
        rs = inboundRoleReader.readAll(new FileInputStream(file), beans);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        @SuppressWarnings("unchecked")
        List<InboundRoleCommand> ircList = (List<InboundRoleCommand>) beans.get("data");
        int i = 2;
        for (InboundRoleCommand irc : ircList) {
            irc.setOuId(ouId);
            String msg = autoBaseInfoManager.verifySaveData(irc);
            if (!"".equals(msg)) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行规则不正确：" + msg + "};\r\n");
            }
            i++;
        }
        if (rs.getStatus() < 1) {
            for (InboundRoleCommand irc : ircList) {
                autoBaseInfoManager.saveInboundRole(irc);
            }
        }
        rs.setMessage(sb.toString());
        return rs;
    }

    public ReadStatus importSkuType(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        StringBuilder sb = new StringBuilder();
        rs = skuTypeReader.readAll(new FileInputStream(file), beans);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        @SuppressWarnings("unchecked")
        List<InboundRoleCommand> ircList = (List<InboundRoleCommand>) beans.get("data");
        int i = 2;
        for (InboundRoleCommand irc : ircList) {
            String msg = autoBaseInfoManager.verifySkuAndSkuType(irc);
            if (!"".equals(msg)) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据不正确：" + msg + "};\r\n");
            }
            i++;
        }
        if (rs.getStatus() < 1) {
            for (InboundRoleCommand irc : ircList) {
                autoBaseInfoManager.updateSkuBySkuType(irc.getSku().getId(), irc.getSkuType().getId());
            }
        }
        rs.setMessage(sb.toString());
        return rs;
    }

    public ReadStatus importSkuSpType(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        StringBuilder sb = new StringBuilder();
        rs = skuSpTypeReader.readAll(new FileInputStream(file), beans);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        @SuppressWarnings("unchecked")
        List<InboundRoleCommand> ircList = (List<InboundRoleCommand>) beans.get("data");
        int i = 2;
        for (InboundRoleCommand irc : ircList) {
            String msg = autoBaseInfoManager.verifySkuSpAndSkuType(irc);
            if (!"".equals(msg) && null != msg) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据不正确：" + msg + "};\r\n");
            }
            i++;
        }
        if (null != sb && !"".equals(sb) && sb.length() > 0) {
            rs.setMessage(sb.toString());
            return rs;
        } else {
            Sku sku = null;
            for (InboundRoleCommand irc : ircList) {
                sku = skuDao.getByCode(irc.getSkuCode());
                sku.setPaperSku(skuDao.getByCode(irc.getSkuSpCode()));
                sku.setLength(irc.getLength());
                sku.setHeight(irc.getHeight());
                sku.setWidth(irc.getWidth());
                sku.setGrossWeight(irc.getWeight());
                if (skuCategoriesDao.getBySkuCategoriesName(irc.getSkuCategoriesName()) != null) {
                    sku.setSkuCategoriesId(skuCategoriesDao.getBySkuCategoriesName(irc.getSkuCategoriesName()).getId());
                }
                if ("是".equals(irc.getDeliveryType())) {
                    sku.setDeliveryType(TransDeliveryType.LAND);
                } else {
                    sku.setDeliveryType(TransDeliveryType.ORDINARY);
                }

            }
        }
        rs.setStatus(ReadStatus.STATUS_SUCCESS);
        return rs;
    }



    public ReadStatus channelSkuSpTypeImport(File file) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        StringBuilder sb = new StringBuilder();
        rs = channelSkuSpTypeReader.readAll(new FileInputStream(file), beans);
        if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
            return rs;
        }
        @SuppressWarnings("unchecked")
        List<InboundRoleCommand> ircList = (List<InboundRoleCommand>) beans.get("data");
        int i = 2;
        for (InboundRoleCommand irc : ircList) {
            String msg = autoBaseInfoManager.verifyChannelSkuSpAndSkuType(irc);
            if (!"".equals(msg) && null != msg) {
                rs.setStatus(ReadStatus.STATUS_DATA_COLLECTION_ERROR);
                sb.append("{第" + i + "行数据不正确：" + msg + "};\r\n");
            }
            i++;
        }
        if (null != sb && !"".equals(sb) && sb.length() > 0) {
            rs.setMessage(sb.toString());
            return rs;
        } else {
            Sku sku = null;
            Sku paperSku = null;
            BiChannel biChannel = null;
            BiChannelSkuSupplies biChannelSkuSupplies = null;
            for (InboundRoleCommand irc : ircList) {
                sku = skuDao.getByCode(irc.getSkuCode());
                paperSku = skuDao.getByCode(irc.getSkuSpCode());
                biChannel = biCannelDao.getByName(irc.getOwner());
                biChannelSkuSupplies = biChannelSkuSuppliesDao.findBiChannelSkuSupplies(biChannel.getId(), sku.getId());
                if (biChannelSkuSupplies == null) {
                    biChannelSkuSupplies = new BiChannelSkuSupplies();
                }
                biChannelSkuSupplies.setPaperSku(paperSku.getId());
                biChannelSkuSupplies.setShopId(biChannel.getId());
                biChannelSkuSupplies.setSkuId(sku.getId());
                biChannelSkuSuppliesDao.save(biChannelSkuSupplies);
            }
        }
        rs.setStatus(ReadStatus.STATUS_SUCCESS);
        return rs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> importParticularcommdySku(File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SkuCommand> listSku = null;
        List<String> sku = null;
        ReadStatus rs = null;
        try {
            rs = skuReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return sku;
            }
            listSku = (List<SkuCommand>) beans.get("skuList");
            if (!"".equals(listSku)) {
                sku = new ArrayList<String>();
                for (SkuCommand skuCommand : listSku) {
                    if (!"".equals(skuCommand.getBarCode())) {
                        sku.add(skuCommand.getBarCode().trim());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Import special commodity SKU file failed!");
        }
        return sku;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ReturnPackage> importReturnPackage(File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<ReturnPackage> RTList = null;
        ReadStatus rs = null;
        try {
            rs = returnPackageReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return RTList;
            }
            RTList = (List<ReturnPackage>) beans.get("ReturnPackageList");

        } catch (FileNotFoundException e) {
            log.error("Import returnPackage file failed!");
        }
        return RTList;
    }

    /**
     * @author LuYingMing
     * @date 2016年9月12日 上午10:15:42
     * @see com.jumbo.wms.manager.warehouse.excel.ExcelReadManager#outOfStorageSnImport(java.io.File,
     *      java.lang.Long)
     */
    @Override
    public ReadStatus outOfStorageSnImport(File file, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs;
        try {
            rs = outOfStorageSnImport.readSheet(new FileInputStream(file), 0, beans);
            if (rs == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("outOfStorageSnImport FileNotFoundException:" + ouid, e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
            if (rs != null) {
                Sku sku = null;
                @SuppressWarnings("unchecked")
                List<SkuSnCommand> list = (List<SkuSnCommand>) beans.get("skusncommand");
                String direction = (String) beans.get("direction");
                if (list.isEmpty() || list.size() == 0) {
                    throw new BusinessException(ErrorCode.SNS_NO_DATA);
                }
                // 临时存放需要处理的sn号
                Map<String, String> temp = new HashMap<String, String>();
                for (SkuSnCommand e : list) {
                    if (e.getSku().getCode() == null) throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    // 找商品
                    sku = skuDao.getByCode(e.getSku().getCode());
                    if (sku == null) throw new BusinessException(ErrorCode.IMPORT_SN_SKU_ISNOTFIND, new Object[] {e.getSku().getCode()});
                    Boolean issnproduct = sku.getIsSnSku();
                    if (issnproduct == null || !issnproduct) {
                        throw new BusinessException(ErrorCode.SNS_NOT_SN, new Object[] {e.getSku().getCode()});
                    }
                    if (e.getSn() == null || !StringUtils.hasLength(e.getSn())) {
                        throw new BusinessException(ErrorCode.SNS_NO_DATA);
                    }
                    StockTransApplication sta = staDao.getByCode(e.getSta().getCode());
                    if (!StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
                        throw new BusinessException(ErrorCode.RMI_SERVICE_STA_UNFINISHED, new Object[] {sta.getCode(), sta.getSlipCode1()});
                    }
                    /********************** Excel的校验逻辑 ***************************/

                    // 找商品 SN
                    SkuSn snSkuSn = skuSnDao.findSkuSnBySnSingle(e.getSn());
                    String staCode = e.getSta().getCode();
                    if (Constants.CAINIAO_SN_INBOUND.equals(direction)) {
                        if (null == snSkuSn) {
                            List<StockTransVoucher> stvList = stvDao.findStvIdByStaWithSkuAndDirection(staCode, 11l, new BeanPropertyRowMapper<StockTransVoucher>(StockTransVoucher.class));
                            if (null != stvList && stvList.size() > 0) {
                                OperationUnit ouEntity = operationUnitDao.getByPrimaryKey(ouid);
                                SkuSn entity = new SkuSn();
                                entity.setSn(e.getSn());
                                entity.setStatus(SkuSnStatus.USING);
                                entity.setSku(sku);
                                entity.setStv(stvList.get(0));
                                entity.setOu(ouEntity);
                                entity.setLastModifyTime(new Date());
                                skuSnDao.save(entity);
                                SkuSnLog skuSnLog = new SkuSnLog();
                                skuSnLog.setDirection(TransactionDirection.INBOUND);
                                skuSnLog.setOuId(ouid);
                                skuSnLog.setSkuId(sku.getId());
                                skuSnLog.setSn(e.getSn());
                                skuSnLog.setStvId(stvList.get(0).getId());
                                skuSnLog.setTransactionTime(new Date());
                                skuSnLog.setLastModifyTime(new Date());
                                skuSnLogDao.save(skuSnLog);
                            } else {
                                throw new BusinessException(ErrorCode.NOT_FIND_STV, new Object[] {e.getSta().getCode()});
                            }
                        } else {
                            throw new BusinessException(ErrorCode.IMPORT_SN_ISNOT_UNIQUEO, new Object[] {e.getSn()});
                        }
                    } else if (Constants.CAINIAO_SN_OUTBOUND.equals(direction)) {
                        if (null != snSkuSn) {
                            SkuSnCommand skuSnCommand = skuSnDao.findBySkuIdAndSn(sku.getId(), e.getSn(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                            List<StockTransVoucher> stvList = stvDao.findStvIdByStaWithSkuAndDirection(staCode, null, new BeanPropertyRowMapper<StockTransVoucher>(StockTransVoucher.class));
                            if (null != skuSnCommand) {
                                Integer result = skuSnCommand.getSkuSnStatus();
                                if (result == SkuSnStatus.USING.getValue()) {
                                    // SkuSnLog skuSnLog = new SkuSnLog();
                                    // skuSnLog.setDirection(TransactionDirection.OUTBOUND);
                                    // skuSnLog.setOuId(ouid);
                                    // skuSnLog.setSkuId(sku.getId());
                                    // skuSnLog.setSn(e.getSn());
                                    // skuSnLog.setStvId(stvList.get(0).getId());
                                    // skuSnLog.setTransactionTime(new Date());
                                    // skuSnLog.setLastModifyTime(new Date());
                                    // skuSnLogDao.save(skuSnLog);
                                    // skuSnDao.deleteByPrimaryKey(skuSnCommand.getId());
                                    // 删除优化
                                    // map的key为skuId号，value为 ouid,skuId,sn,stvId,snId
                                    String value = ouid + "," + sku.getId() + "," + e.getSn() + "," + stvList.get(0).getId() + "," + skuSnCommand.getId();
                                    temp.put(e.getSn() + "", value);
                                    if (temp.size() == list.size()) {
                                        batchDeleteSkuSn(temp);
                                    }
                                } else {
                                    throw new BusinessException(ErrorCode.SKUSN_WAITING_FOR_PROCESSING, new Object[] {skuSnCommand.getSn()});
                                }
                            } else {
                                throw new BusinessException(ErrorCode.SKUSN_RELATION_NOT_EXISTS, new Object[] {sku.getCode()});
                            }
                        } else {
                            throw new BusinessException(ErrorCode.SKUSN_NOT_EXISTS, new Object[] {});
                        }
                    } else {
                        throw new BusinessException(ErrorCode.EXCEL_TRANSACTION_DIRECTION_IS_NULL, new Object[] {e.getSta().getCode()});
                    }
                }
            }
        }
        return rs;
    }

    /**
     * 批量删除出库sn号并记录日志
     * 
     * @param ouid
     * @param sku
     * @param e
     * @param skuSnCommand
     * @param stvList
     */
    public void batchDeleteSkuSn(Map<String, String> temp) {
        for (Map.Entry<String, String> entry : temp.entrySet()) {
            String value = entry.getValue();
            String[] splits = org.apache.commons.lang.StringUtils.split(value, ",");
            SkuSnLog skuSnLog = new SkuSnLog();
            skuSnLog.setDirection(TransactionDirection.OUTBOUND);
            skuSnLog.setOuId(Long.parseLong(splits[0]));
            skuSnLog.setSkuId(Long.parseLong(splits[1]));
            skuSnLog.setSn(splits[2]);
            skuSnLog.setStvId(Long.parseLong(splits[3]));
            skuSnLog.setTransactionTime(new Date());
            skuSnLog.setLastModifyTime(new Date());
            skuSnLogDao.save(skuSnLog);
            skuSnDao.deleteByPrimaryKey(Long.parseLong(splits[4]));
        }
    }

    @Override
    public ReadStatus vmiReturnImportConverse(String owner, String returnReason, File file, String innerShopCode, String toLocation, Long ouid, Long cmpOuid, Long userid, String reasonCode, boolean converseFlag, boolean converseTransferFlag)
            throws Exception {

        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = converseShutoutImport.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            String type = (String) beans.get("type");
            Map<String, Map<String, Long>> mapStore = new HashMap<String, Map<String, Long>>();
            List<StaLineCommand> stalinecmds2 = (List<StaLineCommand>) beans.get("stalinecmds");
            for (StaLineCommand line : stalinecmds2) {
                line.setIntInvstatusName("良品");
                String store = line.getmCode();
                // 商品为一维度
                Map<String, Long> mapSKu = new HashMap<String, Long>();
                // mapStore 是否是该店铺下的
                Map<String, Long> mapSKu2 = mapStore.get(store);
                if (mapSKu2 == null) {
                    // 已门店名称门店编码唯一维度 SKU条码/编码唯一维度
                    mapSKu.put(line.getSkuCode(), line.getQuantity());
                    mapStore.put(store, mapSKu);
                } else {
                    Long num = mapSKu2.get(line.getSkuCode());
                    if (num == null) {
                        mapSKu2.put(line.getSkuCode(), line.getQuantity());
                    } else {
                        mapSKu2.put(line.getSkuCode(), num + line.getQuantity());
                    }
                }
            }
            BiChannel shop = companyShopDao.getByCode(owner);
            if (shop == null) throw new BusinessException(ErrorCode.COMAPNY_SHOP_IS_NOT_FOUND);
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            // OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(ouid);
            if (!StringUtil.isEmpty(shop.getVmiCode())) {
                // 验证品牌操作权限
                if (!StringUtil.isEmpty(shop.getOpType())) {
                    // 如果有配置需要验证
                    boolean b = true;
                    String typeName = "";
                    b = VmiDefault.checkVmiOpType(shop.getOpType(), VmiOpType.TFX);
                    typeName = "VMI转店退仓";
                    if (b) {
                        // 无操作权限抛出异常
                        throw new BusinessException(ErrorCode.VMI_OP_ERROR, new Object[] {shop.getName(), typeName});
                    }
                }
            }
            // 检验所有文件商品数量库存是满足 S
            StockTransApplication s = new StockTransApplication();
            s.setType(StockTransApplicationType.valueOf(102));// 转店退仓
            s.setFreightMode(FreightMode.valueOf(20));// 物流配送
            BigDecimal transactionid = transactionTypeDao.findByStaType(s.getType().getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            if (transactionid == null) {
                throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
            }
            TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
            if (transactionType == null) {
                throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
            }
            Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
            Map<String, WarehouseLocation> locationmap = new HashMap<String, WarehouseLocation>();
            List<StaLine> stalines = new ArrayList<StaLine>();
            rs = vmiReturnValidate2(rs, stalinecmds2, stalines, invmap, locationmap, cmpOuid, ouid, type, shop.getCode(), transactionType, s);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            // 封装sta sta物流信息
            for (Map.Entry<String, Map<String, Long>> en : mapStore.entrySet()) {
                String[] codes = en.getKey().split("-");
                String code = codes[0];
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.valueOf(102));// 转店退仓
                sta.setFreightMode(FreightMode.valueOf(20));// 物流配送
                sta.setCtCode(code);// 门店code
                if (reasonCode != null && reasonCode != "") {
                    sta.setMemo(returnReason);// 退仓原因
                }
                sta.setSlipCode1(reasonCode);// 退货原因编码
                sta.setToLocation(code);// 目标店铺
                StaDeliveryInfo stadelivery = new StaDeliveryInfo();
                // 封装
                List<StaLineCommand> stalinecmds = new ArrayList<StaLineCommand>();
                Map<String, Long> skus = en.getValue();
                for (Map.Entry<String, Long> sku : skus.entrySet()) {
                    StaLineCommand staLineCommand = new StaLineCommand();
                    staLineCommand.setSkuCode(sku.getKey());
                    staLineCommand.setQuantity(sku.getValue());
                    staLineCommand.setIntInvstatusName("良品");
                    stalinecmds.add(staLineCommand);
                }
                // 基于店铺进行店铺退仓操作line
                // rs = createStaForVMIReturn( sta, stadelivery, file, owner, toLocation, ouid,
                // cmpOuid, userid, reasonCode, false, false);
                rs = creStaForVMIReturnEsToLine(stalinecmds, rs, type, shop, sta, stadelivery, file, owner, toLocation, ouid, cmpOuid, userid, reasonCode, false, false, false, null, null);
                if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    return rs;
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    public String generateRtnStaSlipCode() {
        String slipCode = String.valueOf(staDao.findEspritIF2RDSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
        return slipCode;
    }

    public void insertImprotFileLog(Long whId, String userName, String memo, Long createId, File file, String fileName) {
        ChooseOption co1 = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("exl_file_url", "exl_file_url");
        String durl = co1.getOptionValue();
        File f = new File(durl);
        if (!f.exists()) {
            f.mkdirs();
        }
        String tempDirs = fileName + "_" + DateUtil.getSysDateFormat("yyyyMMddHHmmss") + "_" + new Random().nextInt(900);
        try {
            FileUtils.copyFileToDirectory(file, new File(durl + tempDirs), true);
        } catch (IOException e) {
            log.error("insertImprotFileLog is error:", e);
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
        ImportFileLog log = new ImportFileLog();
        log.setFileName(tempDirs);
        log.setWhId(whId);
        log.setUserName(userName);
        log.setColum1(memo);
        log.setColum2(file.getName()); // 文件名
        log.setType(1); // 库内移动
        log.setStatus(1);
        log.setCreateId(createId);
        log.setCreateTime(new Date());
        importFileLogDao.save(log);

    }

    public List<Long> findAllTodowhId(Integer type, Integer status) {
        return importFileLogDao.findAllTodoWhId(type, status, new SingleColumnRowMapper<Long>(Long.class));
    }

    public List<ImportFileLog> findAllToDeleteFile() {
        return importFileLogDao.findAllToDeleteFile(new BeanPropertyRowMapperExt<ImportFileLog>(ImportFileLog.class));
    }


    public String exeFileInput(ImportFileLog files) {
        ChooseOption co1 = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("exl_file_url", "exl_file_url");
        String errorMsg = "";
        String url = co1.getOptionValue();
        url = url + files.getFileName() + "/" + files.getColum2();
        try {
            log.info("exeFileInput file is :" + url);
            File file = new File(url);
            ReadStatus rs = this.transitInnverImport(file, files.getMemo(), files.getWhId(), files.getCreateId(), files.getId());
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                errorMsg = "执行失败：";
            }

            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                // 成功
            } else if (rs.getStatus() > 0) {
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    errorMsg += s + ",";
                }
            } else if (rs.getStatus() < 0 || rs.getStatus() > 0) {
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException bex = (BusinessException) ex;
                        String msg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs(), Locale.SIMPLIFIED_CHINESE);
                        errorMsg += msg + ",";
                    }
                }
            }
        } catch (BusinessException e) {
            if (log.isErrorEnabled()) {
                log.error("exeFileInput BusinessException:", e);
            }
            log.error("bindFileByStaCode is error:" + files.getId(), e.getMessage());
            if (e.getErrorCode() > 0) {
                String m = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
                errorMsg += m + ",";
            }
            String ss = null;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                ss = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
                errorMsg += ss + ",";
            }
            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {errorMsg});
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("exeFileInput Exception:", e);
            }
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                String msg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs(), Locale.SIMPLIFIED_CHINESE);
                errorMsg += msg + ",";
            } else {
                log.error("bindFileByStaCode is error:" + files.getId(), e);
                errorMsg += "读取文件错误，请检查单元格式" + ",";
            }
            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {errorMsg});
        }
        return errorMsg;
    }

    public Locale getLocale() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            return null;
        }
    }

    public void exeDeleteFileTask(ImportFileLog file) {
        ChooseOption co1 = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("exl_file_url", "exl_file_url");
        String url = co1.getOptionValue();
        String fileUrl = url + file.getFileName();
        try {
            org.codehaus.plexus.util.FileUtils.deleteDirectory(fileUrl); // 删除文件
            importFileLogDao.delete(file);// 删除数据
        } catch (Exception e) {
            log.error("exeDeleteFileTask is error:fileUrl is" + fileUrl, e);
        }
    }

    @Override
    public Map<String, Object> orderOutBoundInfoImport(File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = orderOutBoundReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return beans;
            }
        } catch (FileNotFoundException e) {
            log.error("Import orderOutBound file failed!");
        }
        return beans;
    }

    /**
     * gucci 退大仓的库存状态
     * 
     * @param file
     * @param rtoId
     * @return
     * @throws Exception
     */
    public ReadStatus gucciRtoInvStatus(File file, Long rtoId, Long ouId) throws Exception {

        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        Map<String, InventoryStatus> invmap = new HashMap<String, InventoryStatus>();
        try {
            rs = vmiReturnImportGucciReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            List<VmiRtoLineCommand> rtolinecmdsE = (List<VmiRtoLineCommand>) beans.get("vmiRtoLineCommand");
            if (rtolinecmdsE != null && rtolinecmdsE.size() > 0) {
                List<VmiRtoLineCommand> vrlcListD = vmiRtoLineDao.findRtoLineListByRtoId(rtoId, new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
                Map<Long, String> vrlcMapD = new HashMap<Long, String>();
                Map<String, String> vrlcMapE = new HashMap<String, String>();
                Set<String> invStatusSet = new HashSet<String>();
                Set<String> rtoLineSkuCodeSet = new HashSet<String>();
                if (vrlcListD != null && vrlcListD.size() > 0) {
                    if (!StringUtil.isEmpty(vrlcListD.get(0).getInvStatus())) {
                        throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"已经维护过库存状态！"});
                    }
                } else {
                    throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"没找到退仓明细数据！"});
                }
                for (VmiRtoLineCommand vrlcD : vrlcListD) {
                    vrlcMapD.put(vrlcD.getId(), vrlcD.getSkuCode());
                    rtoLineSkuCodeSet.add(vrlcD.getSkuCode());
                }
                // 校验商品是否正确
                for (VmiRtoLineCommand vrlcE : rtolinecmdsE) {
                    if (vrlcMapD.values().contains(vrlcE.getSkuCode())) {
                        if (!StringUtil.isEmpty(vrlcE.getInvStatus())) {
                            invStatusSet.add(vrlcE.getInvStatus());
                            // 库存状态
                            InventoryStatus inventoryStatus = null;
                            inventoryStatus = invmap.get(vrlcE.getInvStatus());
                            if (inventoryStatus == null) {
                                inventoryStatus = inventoryStatusDao.findByNameAndOu(vrlcE.getInvStatus(), ouId);
                                if (inventoryStatus == null) {
                                    throw new BusinessException(ErrorCode.INV_STATUS_NOT_FOUND, new Object[] {vrlcE.getInvStatus()});
                                } else {
                                    invmap.put(vrlcE.getInvStatus(), inventoryStatus);
                                }
                            }
                            vrlcMapE.put(vrlcE.getSkuCode(), vrlcE.getInvStatus());
                        } else {
                            throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"商品" + vrlcE.getSkuCode() + "库存状态不正确！"});
                        }
                    } else {
                        throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"商品" + vrlcE.getSkuCode() + "不正确！"});
                    }

                }

                if (invStatusSet.size() > 1) {
                    throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"库存状态需要一致！"});
                }

                // 行数校验
                if (rtoLineSkuCodeSet.size() != vrlcMapE.size()) {

                    throw new BusinessException(ErrorCode.INV_CHECK_IMPORT_COUNT_ERROR);
                }

                // 保存库存状态
                for (Long vrlcId : vrlcMapD.keySet()) {
                    VmiRtoLineDefault vrld = vmiRtoLineDao.getByPrimaryKey(vrlcId);
                    String invStatus = vrlcMapE.get(vrlcMapD.get(vrlcId));
                    if (!StringUtil.isEmpty(invStatus)) {
                        vrld.setInvStatus(invStatus);
                    } else {
                        throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"商品" + vrlcMapD.get(vrlcId) + "库存状态不正确！"});
                    }
                }

            } else {
                throw new BusinessException(ErrorCode.RTO_INFO_IMPORT_ERROR, new Object[] {"数据不可为空！"});
            }



        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    @Override
    public ReadStatus zdhPiciImport(File file, Long ouid, Long userId) throws Exception {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = zdhPiciImportReader.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return rs;
            }
            List<StaLfCommand> stalfcmds = (List<StaLfCommand>) beans.get("stalfcmds");
            Map<String, String> mapOwnWhCode = new HashMap<String, String>();
            String ownWhCode = null;
            for (StaLfCommand staLfCommand : stalfcmds) {
                ownWhCode = staLfCommand.getSlipcode() + ";" + staLfCommand.getSlipcode1();
                if (mapOwnWhCode.get(ownWhCode) == null) {
                    mapOwnWhCode.put(ownWhCode, ownWhCode);
                }
            }
            String key = null;
            String owner = null;// 店铺code
            String whCode = null;// 仓库code
            BiChannel biChannel = null;// 店铺
            OperationUnit u = null;// 仓库
            ZdhPici zdhPici = new ZdhPici();
            zdhPici.setCreateTime(new Date());
            zdhPici.setStatus(1);
            zdhPici.setMoveStatus(1);
            zdhPici.setUserId(userId);
            zdhPici.setCode("ZP" + zdhPiciDao.getZdhPiciCode(new SingleColumnRowMapper<String>(String.class)));
            zdhPiciDao.save(zdhPici);
            for (Map.Entry<String, String> en : mapOwnWhCode.entrySet()) {
                ZdhPiciLine zdhPiciLine = new ZdhPiciLine();
                key = en.getKey();
                // 验证店铺与仓库
                String[] codes = key.split(";");
                owner = codes[0];// 店铺 code
                whCode = codes[1];// 仓库 code
                biChannel = biCannelDao.getByCode(owner);
                if (biChannel == null) {
                    throw new BusinessException("店铺code不存在:" + owner);
                }
                u = operationUnitDao.getByCode(whCode);
                if (u == null) {
                    throw new BusinessException("仓库code不存在:" + whCode);
                }
                zdhPiciLine.setChannelId(biChannel.getId());
                zdhPiciLine.setWhId(u.getId());
                zdhPiciLine.setPiCiId(zdhPici.getId());
                zdhPiciLineDao.save(zdhPiciLine);
            }
        } catch (Exception e) {
            throw e;
        }
        return rs;
    }

    @Override
    public ReadStatus goodsImport(File file) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ReadStatus nikeRelationImport(File file, Long ouid, String userName) {

        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs;
        try {
            rs = nikeRelationImport.readSheet(new FileInputStream(file), 0, beans);
            if (rs == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } catch (FileNotFoundException e1) {
            if (log.isErrorEnabled()) {
                log.error("nikeRelationImport FileNotFoundException:" + ouid, e1);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
            if (rs != null) {
                @SuppressWarnings("unchecked")
                List<RelationNike> list = (List<RelationNike>) beans.get("nikeRelation");

                if (list.isEmpty() || list.size() == 0) {
                    throw new BusinessException(ErrorCode.NIKE_RELATION_NO_DATA);
                }

                String sysPid = "";
                String enPid = "";
                List<NikeVmiStockInCommand> nikeList = null;

                for (int i = 0; i < list.size() - 1; i++) {
                    sysPid = list.get(i).getSysPid().toString().trim();
                    enPid = list.get(i).getEnPid().toString().trim();

                    for (int j = i + 1; j < list.size(); j++) {
                        if (sysPid.equals(list.get(j).getSysPid().toString().trim()) || enPid.equals(list.get(j).getEnPid().toString().trim())) {
                            throw new BusinessException(ErrorCode.NIKE_RELATION_IMPORT_SAME, new Object[] {i + 1, j + 1});
                        }
                    }

                    
                }
                
                for(int i = 0; i < list.size() ; i++) {
                    sysPid = list.get(i).getSysPid().toString().trim();
                    nikeList = nikeStockInDao.getByReferenceNo(sysPid);
                    if (nikeList.size() == 0) {
                        throw new BusinessException(ErrorCode.NIKE_RELATION_NO_SYSID, new Object[] {i + 1});
                    }

                    if (relationNikeDao.getBySysPid(list.get(i).getSysPid().toString().trim(), ouid) != null && relationNikeDao.getByEnPid(list.get(i).getEnPid().toString().trim(), ouid) != null) {

                        throw new BusinessException(ErrorCode.NIKE_RELATION_DATA_SAME, new Object[] {i + 1});
                    }
                }
                for (RelationNike e : list) {
                    OperationUnit ouEntity = operationUnitDao.getByPrimaryKey(ouid);
                    RelationNike entity = new RelationNike();
                    
                    entity.setEnPid(e.getEnPid().toString().trim());
                    entity.setSysPid(e.getSysPid().toString().trim());
                    entity.setCreateTime(new Date());
                    entity.setCreatePerson(userName);
                    entity.setWhOu(ouEntity);

                    RelationNike nike1 = relationNikeDao.getBySysPid(e.getSysPid().toString().trim(), ouid);
                    if (nike1 != null) {
                        entity = relationNikeDao.getByPrimaryKey(nike1.getId());
                        entity.setEnPid(e.getEnPid().toString().trim());
                        entity.setSysPid(e.getSysPid().trim());
                        entity.setCreateTime(new Date());
                        entity.setCreatePerson(userName);
                        entity.setWhOu(ouEntity);
                    }
                    RelationNike nike2 = relationNikeDao.getByEnPid(e.getEnPid().trim(), ouid);
                    if (nike2 != null) {
                        entity = relationNikeDao.getByPrimaryKey(nike2.getId());
                        entity.setEnPid(e.getEnPid().toString().trim());
                        entity.setSysPid(e.getSysPid().toString().trim());
                        entity.setCreateTime(new Date());
                        entity.setCreatePerson(userName);
                        entity.setWhOu(ouEntity);
                    }
                    relationNikeDao.save(entity);

                }
            }
        }
        return rs;

    }


    /**
     * 导入临时库间移动
     * 
     * @param file
     * @param mainWhouId
     * @param targetOuId
     * @param owner
     * @param userId
     * @return
     */
    public String importOdoLine(File file, Long mainWhouId, Long targetOuId, String owner, Long userId, Long invStatusId, Long odoId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            // 读取excel
            ReadStatus rs = odoLineReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                List<OdoLineCommand> result = (List<OdoLineCommand>) beans.get("odoLines");
                // 验证excel数据
                String errorMsg = validateOdoLineExcel(result, mainWhouId, targetOuId, owner, userId, invStatusId, odoId);
                return errorMsg;
            } else {
                return "数据读取失败！";
            }

        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }


    private String validateOdoLineExcel(List<OdoLineCommand> result, Long main_whouId, Long targetOuId, String owner, Long userId, Long invStatusId, Long odoId) {
        StringBuilder msg = new StringBuilder();
        if (result == null || result.size() == 0) {
            throw new BusinessException(ErrorCode.END_OPERATION_TYPE_NOT_FOUNT1);
        }

        Map<String, Long> skuMap = new HashMap<String, Long>();
        List<Sku> skuListAll = new ArrayList<Sku>();


        for (OdoLineCommand olc : result) {
            if (skuMap.get(olc.getSkuBarcode()) != null) {
                skuMap.put(olc.getSkuBarcode(), skuMap.get(olc.getSkuBarcode()) + olc.getQty());
            } else {
                skuMap.put(olc.getSkuBarcode(), olc.getQty());
            }
        }

        InventoryStatus is = null;
        if (odoId == null) {
            is = inventoryStatusDao.getByPrimaryKey(invStatusId);

            if (is == null) {
                return "库存状态错误！";
            }
        }

        Odo oldOdo = null;
        if (odoId != null) {
            oldOdo = odoDao.getByPrimaryKey(odoId);
            if (oldOdo.getStatus() != 1) {
                return "此单不允许修改明细行！";
            }
            odoDao.deleteOdoLineByOdoId(odoId);
            main_whouId = oldOdo.getOuId();
            // odoDao.flush();
        }
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(main_whouId);

        int size = skuMap.size() / 500;
        if (skuMap.size() % 500 != 0) {
            size += 1;
        }
        List<String> barcodes = new ArrayList<String>();
        barcodes.addAll(skuMap.keySet());
        for (int s = 0; s < size; s++) {

            List<String> barcodeList = new ArrayList<String>();
            int len = 0;
            if (s + 1 == size) {
                len = skuMap.size() % 500;
                if (len == 0) {
                    len = 500;
                }
            } else {
                len = 500;
            }
            for (int i = 0; i < len; i++) {
                barcodeList.add(barcodes.get(s * 500 + i));
            }

            // 按批校验数据
            List<Sku> skuList = skuDao.findSkuByBarcodeList(barcodeList, customerId, new BeanPropertyRowMapper<Sku>(Sku.class));
            skuListAll.addAll(skuList);
        }


        // 无效的商品
        if (skuListAll.size() != skuMap.size()) {
            msg.append("下列商品不存在！");
            for (String barcode : skuMap.keySet()) {
                boolean b = false;
                for (Sku sku : skuListAll) {
                    if (barcode.equals(sku.getBarCode())) {
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    msg.append(barcode + ",");
                }
            }
            return msg.toString();
        }
        Odo odo = null;
        if (oldOdo == null) {
            Date date = new Date();
            odo = new Odo();
            odo.setCode("ODO" + date.getTime());
            odo.setCreateTime(date);
            odo.setCreatorId(userId);
            odo.setOuId(main_whouId);
            odo.setTargetOuId(targetOuId);
            odo.setOwner(owner);
            odo.setStatus(1);
            odo.setInvStatusId(is.getId());
            odo.setErrorCount(0L);
            odo.setVersion(0);
            odoDao.save(odo);
        } else {
            odo = oldOdo;
        }
        // 检查EXCEL数据正确性
        for (Sku sku : skuListAll) {
            OdoLine odoLine = new OdoLine();
            odoLine.setCode(odo.getCode());
            odoLine.setOdoId(odo.getId());
            odoLine.setQty(skuMap.get(sku.getBarCode()));
            odoLine.setSkuId(sku.getId());
            odoLine.setType(1);
            odoLine.setVersion(0);
            odoLineDao.save(odoLine);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> importsupplierCode(File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SkuCommand> listSku = null;
        List<String> sku = null;
        ReadStatus rs = null;
        try {
            rs = supplierCodeImport.readAll(new FileInputStream(file), beans);
            if (rs.getStatus() != ReadStatus.STATUS_SUCCESS) {
                return sku;
            }
            listSku = (List<SkuCommand>) beans.get("list");
            if (!"".equals(listSku)) {
                sku = new ArrayList<String>();
                for (SkuCommand skuCommand : listSku) {
                    if (!"".equals(skuCommand.getSupplierCode())) {
                        sku.add(skuCommand.getSupplierCode().trim());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            log.error("Import ssupplierCode file failed!");
        }
        return sku;
    }


}
