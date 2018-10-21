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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.SkuTypeDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.godivaData.GodivaInventoryAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageSkuDao;
import com.jumbo.dao.warehouse.PackageSkuLogDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SecKillSkuDao;
import com.jumbo.dao.warehouse.SfOrderCancelQueueDao;
import com.jumbo.dao.warehouse.SfOrderFilterLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhStaPickingListLogDao;
import com.jumbo.dao.warehouse.ZtoConfirmOrderQueueDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.webservice.sfOrder.command.OrderFilterResult;
import com.jumbo.webservice.sfOrder.command.OrderFilterResultAccept;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.godivaData.GodivaManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.SkuType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.ImportFileLog;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.SfOrderCancelQueue;
import com.jumbo.wms.model.warehouse.SfOrderFilterLog;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TimeTypeMode;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;
import com.jumbo.wms.model.warehouse.WhAddStatusSource;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.ZtoConfirmOrderQueue;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelUtil;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.definition.ExcelBlock;
import loxia.support.excel.definition.ExcelSheet;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

@Service("wareHouseManagerProxy")
public class WareHouseManagerProxyImpl extends BaseManagerImpl implements WareHouseManagerProxy {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3279327090810517993L;

    @Resource(name = "outSalesOrderReader")
    private ExcelReader outSalesOrderReader;
    @Resource(name = "productInfoMainTainReader")
    private ExcelReader productInfoMainTainReader;
    @Resource(name = "nikeProductInfoMaintainReader")
    private ExcelReader nikeProductInfoMaintainReader;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
  /*  @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;*/
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgLineDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnDao;
    @Autowired
    ZtoConfirmOrderQueueDao ztoConfirmOrderQueueDao;
    @Autowired
    private InOutBoundManager inoutManager;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SfOrderCancelQueueDao sfOrderCancelQueueDao;
    @Autowired
    private GodivaManager godivaManager;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private SfOrderFilterLogDao sfOrderFilterLogDao;
    @Autowired
    private GodivaInventoryAdjustmentDao gdaoAdjustmentDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private PackageSkuDao packageSkuDao;
    @Autowired
    private PackageSkuLogDao packageSkuLogDao;
    @Autowired
    private WhStaPickingListLogDao pickingListLogDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private SkuTypeDao skuTypeDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Resource
    private ApplicationContext applicationContext;
    @Value("${zk.notice.task.wms}")
    private String znode;
    @Autowired
    private ZkClient zkClient;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @SuppressWarnings("unchecked")
    public ReadStatus outSalesOrderImport(File file, Long ouId, Long userId, List<StockTransApplication> resultSta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = outSalesOrderReader.readAll(new FileInputStream(file), beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                return rs;
            } else {
                List<StockTransApplicationCommand> staCmdList = (List<StockTransApplicationCommand>) beans.get("sta");
                List<StaLineCommand> lineList = (List<StaLineCommand>) beans.get("line");
                // Map<String, Long> shopMap =
                // companyShopDao.findInnerShopCode(new MapRowMapper());
                // if (shopMap == null) {
                // shopMap = new HashMap<String, Long>();
                // }
                Map<String, Long> lpCodeMap = chooseOptionDao.findAllTransportator(new MapRowMapper());
                if (lpCodeMap == null) {
                    lpCodeMap = new HashMap<String, Long>();
                }
                Map<String, Map<Long, StaLine>> lineMap = createStaLineMapForImport(lineList, ouId, rs);
                final ExcelSheet sheet = outSalesOrderReader.getDefinition().getExcelSheets().get(0);
                List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
                String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
                int offsetRow = 0;
                Map<String, StockTransApplicationCommand> staCmdMap = new HashMap<String, StockTransApplicationCommand>();
                for (StockTransApplicationCommand cmd : staCmdList) {
                    BiChannel channel = companyShopDao.getByName(cmd.getOwner());
                    if (channel == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_COMPANY_SHOP_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getOwner()}));
                    }
                    Long tranId = lpCodeMap.get(cmd.getLpcode());
                    if (tranId == null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 13);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_LPCODE_NOT_FOUND, new Object[] {SHEET_0, currCell, cmd.getLpcode()}));
                    }
                    StockTransApplicationCommand staCmd = staCmdMap.get(cmd.getSlipCode());
                    if (staCmd != null) {
                        String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SLIP_CODE_IS_EXISTS, new Object[] {SHEET_0, currCell, cmd.getRefSlipCode()}));
                    } else {
                        staCmdMap.put(cmd.getSlipCode(), cmd);
                    }
                    offsetRow++;
                }
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    return rs;
                }
                // 检验sheet1 sheet1 slipcode是否一一匹配
                Set<String> lineKeys = lineMap.keySet();
                Set<String> handKeys = staCmdMap.keySet();
                Set<String> tmp = new HashSet<String>();
                Set<String> tmp1 = new HashSet<String>();
                tmp.addAll(lineKeys);
                tmp.addAll(handKeys);
                tmp1.addAll(lineKeys);
                tmp1.addAll(handKeys);
                tmp.removeAll(lineKeys);
                tmp1.removeAll(handKeys);
                if (tmp.size() != 0) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SLIP_CODE_HEAD_NOT_MATCH, new Object[] {tmp.toString()}));
                }
                if (tmp1.size() != 0) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SLIP_CODE_LINE_NOT_MATCH, new Object[] {tmp1.toString()}));
                }
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    return rs;
                }
                // 执行处理
                OperationUnit ou = operationUnitDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    throw new BusinessException(ErrorCode.OPERATION_UNIT_NOT_FOUNT);
                }
                User user = userDao.getByPrimaryKey(userId);
                if (user == null) {
                    throw new BusinessException(ErrorCode.USER_NOT_FOUND);
                }
                if (resultSta == null) {
                    resultSta = new ArrayList<StockTransApplication>();
                }
                for (StockTransApplicationCommand cmd : staCmdList) {
                    BigDecimal staId = staDao.findUnCancelStaBySlipCode(cmd.getRefSlipCode(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
                    if (staId != null) {
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_STA_FONDU, new Object[] {cmd.getRefSlipCode()}));
                        continue;
                    }
                    BiChannel channel = companyShopDao.getByName(cmd.getOwner());
                    wareHouseManagerExe.validateBiChannelSupport(null, channel.getCode());
                    StockTransApplication sta = new StockTransApplication();
                    sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
                    sta.setCreateTime(new Date());
                    sta.setCreator(user);
                    sta.setOwner(channel.getCode());
                    sta.setChannelList(channel.getCode());
                    sta.setIsNeedOccupied(true);
                    sta.setMainWarehouse(ou);
                    sta.setMemo(cmd.getMemo());
                    sta.setTotalActual(cmd.getTotalActual());
                    sta.setRefSlipCode(cmd.getRefSlipCode());
                    sta.setRefSlipType(SlipType.SALES_ORDER);
                    sta.setStatus(StockTransApplicationStatus.CREATED);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                    sta.setLastModifyTime(new Date());
                  //  sta.setType(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES);
                    sta.setType(StockTransApplicationType.OUTBOUND_SALES);
                    sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
                    sta.setDataSource("other");
                    StaDeliveryInfo sd = new StaDeliveryInfo();
                    sd.setAddress(cmd.getAddress());
                    sd.setCity(cmd.getCity());
                    sd.setCountry(cmd.getCountry());
                    sd.setDistrict(cmd.getDistrict());
                    sd.setLpCode(cmd.getLpcode());
                    sd.setMobile(cmd.getMobile());
                    sd.setProvince(cmd.getProvince());
                    sd.setReceiver(cmd.getReceiver());
                    sd.setTransTimeType(TransTimeType.ORDINARY);
                    sd.setTransType(TransType.ORDINARY);
                    sd.setSta(sta);
                    sta.setStaDeliveryInfo(sd);
                    sd.setTelephone(cmd.getTelephone());
                    sd.setTransferFee(cmd.getTransferFee());
                    sd.setZipcode(cmd.getZipcode());
                    Collection<StaLine> lines = lineMap.get(cmd.getSlipCode()).values();
                    try {
                        List<StaLine> ls = new ArrayList<StaLine>();
                        ls.addAll(lines);
                        sta = wareHouseManager.createStaForOutSalesOrder(sta, ls);
                        resultSta.add(sta);
                    } catch (BusinessException e) {
                        rs.addException(e);
                    }
                }
                return rs;
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        }
    }

    private Map<String, Map<Long, StaLine>> createStaLineMapForImport(List<StaLineCommand> lineList, Long ouId, ReadStatus rs) {
        final ExcelSheet sheet = outSalesOrderReader.getDefinition().getExcelSheets().get(1);
        List<ExcelBlock> blocks = sheet.getSortedExcelBlocks();
        String strCell = ExcelUtil.getCellIndex(blocks.get(0).getStartRow(), blocks.get(0).getStartCol());
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        Map<String, Map<Long, StaLine>> lineMap = new HashMap<String, Map<Long, StaLine>>();
        Map<String, Long> skuCodeMap = skuDao.findAllKeyCodeByOu(ouId, new MapRowMapper());
        if (skuCodeMap == null) {
            skuCodeMap = new HashMap<String, Long>();
        }
        Map<String, Long> skuBarCodeMap = skuDao.findAllKeyBarcodeByOu(ouId, new MapRowMapper());
        if (skuBarCodeMap == null) {
            skuBarCodeMap = new HashMap<String, Long>();
        }
        int offsetRow = 0;
        for (StaLineCommand cmd : lineList) {
            boolean isError = false;
            // 验证sta line 数据
            StaLine l = new StaLine();
            if (cmd.getQuantity() <= 0) {
                isError = true;
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SEND_QTY_LS_ZERO, new Object[] {SHEET_1, currCell, cmd.getSlipCode(), cmd.getQuantity()}));
            } else {
                l.setQuantity(cmd.getQuantity());
            }
            Long skuId = skuCodeMap.get(cmd.getJmskuCode());
            if (skuId == null) {
                skuId = skuBarCodeMap.get(cmd.getJmskuCode());
            }
            if (skuId == null) {
                isError = true;
                String currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 2);
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SKU_NOT_FOUND, new Object[] {SHEET_1, currCell, cmd.getSlipCode(), cmd.getJmskuCode()}));
            } else {
                Sku sku = skuDao.getByPrimaryKey(skuId);
                if (sku.getSalesMode().equals(SkuSalesModel.PAYMENT)) {
                    isError = true;
                    rs.setStatus(-2);
                    if (skuDao.getByCode(cmd.getJmskuCode()) == null) {
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SALE_MODE_ERROR, new Object[] {skuDao.getByBarcode(cmd.getJmskuCode(), customerId).getCode(), cmd.getJmskuCode()}));
                    } else {
                        rs.addException(new BusinessException(ErrorCode.EXCEL_IMPORT_SALE_MODE_ERROR, new Object[] {cmd.getJmskuCode(), skuDao.getByCode(cmd.getJmskuCode()).getBarCode()}));
                    }
                } else {
                    l.setSku(sku);
                }
            }
            offsetRow++;
            if (!isError) {
                // 将相同订单line 归类
                Map<Long, StaLine> map = lineMap.get(cmd.getSlipCode());
                if (map == null) {
                    map = new HashMap<Long, StaLine>();
                    map.put(l.getSku().getId(), l);
                } else {
                    StaLine stal = map.get(l.getSku().getId());
                    if (stal == null) {
                        map.put(l.getSku().getId(), l);
                    } else {
                        stal.setQuantity(l.getQuantity() + stal.getQuantity());
                    }
                }
                lineMap.put(cmd.getSlipCode(), map);
            }
        }
        return lineMap;
    }


    /**
     * VMI 销售出库反馈-执行作业单出库
     * 
     * @throws Exception
     */

    @Transactional
    public void callVmiSalesStaOutBound(long msgId) throws Exception {
        MsgRtnOutbound rtnmsg = msgRtnOutboundDao.getByPrimaryKey(msgId);
        if (rtnmsg != null) {
            wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.CANCELED.getValue());
            StockTransApplication sta = staDao.findStaByCode(rtnmsg.getStaCode());
            if (sta != null) {
                if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType()) || StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType()) || StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.equals(sta.getType())) {
                    // 存在作业单出库、完成、取消未处理、取消已处理，更新反馈信息为完成
                    if (StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus()) || StockTransApplicationStatus.FINISHED.equals(sta.getStatus()) || StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus())
                            || StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                        wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                    } else {
                        // 出库
                        MsgRtnOutboundCommand msg = msgRtnOutboundDao.findVmiMsgOutbound(msgId, new BeanPropertyRowMapper<MsgRtnOutboundCommand>(MsgRtnOutboundCommand.class));
                        if (msg.getLpCode() != null && msg.getLpCode().equals(Transportator.KERRY)) {
                            rtnmsg.setLpCode(Transportator.OTHER);
                            // rtnmsg.setTrackingNo("");
                        }
                        wareHouseManager.vmiSalesCreatePage(msg.getOuId(), msg.getTrackingNo(), msg.getWeight(), msg.getStaId(), null, msg.getLpCode(), msg);
                        wareHouseManager.transactionsalesStaOutBound(msg.getStaId());
                        wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                    }
                } else if (StockTransApplicationType.OUTBOUND_OTHERS.equals(sta.getType())) {
                    // 其他出库
                    wareHouseManager.snOccupiedForRtnOutbound(sta.getId());
                    wareHouseManager.othersStaInOutbound(sta.getId(), null, sta.getMainWarehouse().getId(), null);
                    wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                } else if (StockTransApplicationType.OUTBOUND_PURCHASE.equals(sta.getType())// 采购出库
                        || StockTransApplicationType.SAMPLE_OUTBOUND.equals(sta.getType())// 样品领用出库
                        || StockTransApplicationType.SERIAL_NUMBER_GROUP_OUTBOUND.equals(sta.getType())// 串号组合出库
                        || StockTransApplicationType.SERIAL_NUMBER_OUTBOUND.equals(sta.getType())// 串号拆分出库
                        || StockTransApplicationType.OUTBOUND_SETTLEMENT.equals(sta.getType())// 结算经销出库
                        || StockTransApplicationType.WELFARE_USE.equals(sta.getType())// 福利领用
                        || StockTransApplicationType.FIXED_ASSETS_USE.equals(sta.getType())// 固定资产
                        || StockTransApplicationType.SCARP_OUTBOUND.equals(sta.getType())// 报废出库
                        || StockTransApplicationType.SALES_PROMOTION_USE.equals(sta.getType())// 促销出库
                        || StockTransApplicationType.LOW_VALUE_CONSUMABLE_USE.equals(sta.getType())// 低值易耗品
                        || StockTransApplicationType.SKU_EXCHANGE_OUTBOUND.equals(sta.getType())// 商品置换出库
                        || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())// 同公司调拨
                        || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())// 不同公司调拨
                        || StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType())// 库间移动
                        || StockTransApplicationType.REAPAIR_OUTBOUND.equals(sta.getType())// 送修出库
                ) {
                    wareHouseManager.snOccupiedForRtnOutbound(sta.getId());
                    wareHouseManager.predefinedOutExecution(sta.getId(), null);
                    wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                } else if (StockTransApplicationType.VMI_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType())) {
                    // VMI退大仓、VMI转店退仓
                    // wareHouseManager.executeVmiReturnOutBound(sta.getId(),
                    // null,
                    // sta.getMainWarehouse().getId());
                    wareHouseManager.snOccupiedForRtnOutbound(sta.getId());
                    wareHouseManager.executeVmiReturnOutBoundAllAndPart(sta.getId(), sta.getMainWarehouse().getId(), msgId);
                    wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                }
                // else if
                // (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()))
                // {
                // // update 8.30
                // wareHouseManagerExecute.transitCrossStaOutBound(staId, null);
                // wareHouseManager.updateMsgRtnOutbound(msgId,
                // DefaultStatus.FINISHED.getValue());
                // }
            }
        } else {
            log.error("callVmiSalesStaOutBound_msgId" + msgId);
            // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 处理标杆反馈出库信息
     */
    public String outboundToBzProxy(RtnOutBoundCommand rtnOutBound) {
        if (rtnOutBound == null) {
            InOutBoundResponse resp = new InOutBoundResponse();
            resp.setSuccess("false");
            resp.setDesc("XML info error!");
            return MarshallerUtil.buildJaxb(resp);
        }
        MsgRtnOutbound msg = new MsgRtnOutbound();
        Date d = null;
        try {
            d = FormatUtil.stringToDate(rtnOutBound.getShipTime());
        } catch (Exception e) {} finally {
            if (d == null) {
                d = new Date();
            }
        }
        msg.setOutboundTime(d);
        if (!StringUtils.hasText(rtnOutBound.getWeight())) {
            msg.setWeight(new BigDecimal(0));
        } else {
            try {
                msg.setWeight(new BigDecimal(rtnOutBound.getWeight()));
            } catch (Exception e) {
                msg.setWeight(new BigDecimal(0));
            }
        }
        msg.setStaCode(rtnOutBound.getOrderNo());
        msg.setTrackingNo(rtnOutBound.getShipNo());
        msg.setLpCode(rtnOutBound.getCarrierID());
        if ("null".equals(rtnOutBound.getCarrierName()) || rtnOutBound.getCarrierName() == null) {
            msg.setTrackName(rtnOutBound.getOrderNo());
        } else {
            msg.setTrackName(rtnOutBound.getCarrierName());
        }
        msg.setStatus(DefaultStatus.CREATED);
        msg.setSourceWh(rtnOutBound.getBgNo());
        try {
            // 保存信息
            wareHouseManager.saveVmiWhRtnOutboundMsg(msg, Constants.VIM_WH_SOURCE_BIAOGAN);
            if (rtnOutBound.getSend() != null && rtnOutBound.getSend().getSku() != null) {
                // for(RtnOutBoundLineCommand line :
                // rtnOutBound.getSend().getSku()){
                // // if(line.getType() != null &&
                // line.getType().equals(Constants.BIAOGAN_OUT_RTN_LINE_ADDITIONAL)){
                // // MsgRtnOutAdditionalLine add = new
                // MsgRtnOutAdditionalLine();
                // // add.setSkuCode(line.getSkuCode());
                // // add.setQty(Long.valueOf(line.getSkuNum()));
                // // add.setMsgRtnOutbound(msg);
                // // msgRtnOutAdditionalLineDao.save(add);
                // // }
                // }
            }
            InOutBoundResponse resp = new InOutBoundResponse();
            resp.setSuccess("true");
            resp.setDesc("");
            return MarshallerUtil.buildJaxb(resp);
        } catch (Exception e) {
            log.error("", e);
            InOutBoundResponse resp = new InOutBoundResponse();
            resp.setSuccess("false");
            resp.setDesc("");
            return MarshallerUtil.buildJaxb(resp);
        }
    }

    /**
     * 销售出库反馈定时任务
     */
    public void outboundToBzProxyTask() {
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_BIAOGAN);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("outboundToBzProxyTask error ! Out STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 销售出库反馈定时任务---根据作业仓库源 WLB
     */
    public void outboundToBzProxyByWlbTask() {
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_WLB);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("outboundToBzProxyByWlbTask error ! Out STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 销售出库反馈定时任务---根据作业仓库源
     */
    public void outboundToBzProxyTask(String source) {
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(source);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("outboundToBzProxyTask error ! Out STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 入库反馈统一逻辑处理，取得外包仓数据，进行初步校验，校验外包仓数据的正确性：skuId，数量不能为空，库存状态不为空且必须存在！
     * 不同公司调拨特殊处理，要求库存状态必须为另外公司的库存状态，即和sta上的附加状态相同
     */
    @Transactional
    public void msgInBoundWareHouse(MsgRtnInboundOrder rntorder) throws Exception {
        boolean orderStatus = false;
        List<StaLine> staline = new ArrayList<StaLine>();
        StockTransApplication sta = staDao.findStaByCode(rntorder.getStaCode());
        if (sta != null && StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) {
            msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.FINISHED.getValue());
            log.debug("The list is complete");
            return;
        }

        if (sta != null && StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // ------------------------------------------------
        // 1.验证MSG数据正确性(不需要作业单类型)
        // 1.1 验证SKU
        // 1.2 验证状态
        // 1.4 验证失败取消、或者其他处理
        // ------------------------------------------------
        // 2 验证单据类型与MSG反馈数据是否正确(需要作业单类型)
        // function (验证数量一致、库存状态一致,其他严格校验)
        // function (验证数量一致，库存状态看外包仓的,如退换货入库)
        // function (验证数量<=计划量，库存状态看作业单头的状态,部分入库单据)
        // 2.1 验证反馈数量
        // 2.2 验证反馈库存状态
        // 2.3 校验失败不同仓库不同处理 ，增加外包仓枚举
        // ------------------------------------------------
        // 3 执行入库
        // 根据单据类型
        // （1）新批次号入库
        // （2）与出库批次号一致的入库
        // ------------------------------------------------
        if (sta != null) {
            if (StockTransApplicationType.INBOUND_PURCHASE.equals(sta.getType())// y
                    || StockTransApplicationType.INBOUND_SETTLEMENT.equals(sta.getType()) // y
                    || StockTransApplicationType.INBOUND_OTHERS.equals(sta.getType())// y
                    || StockTransApplicationType.INBOUND_CONSIGNMENT.equals(sta.getType()) // y
                    || StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType()) // y
                    || StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.equals(sta.getType())// y
                    || StockTransApplicationType.INBOUND_GIFT.equals(sta.getType()) // y
                    || StockTransApplicationType.INBOUND_MOBILE.equals(sta.getType()) // y
                    || StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType())// 库间移动
                    || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())// 同公司调拨KJL
                    || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())// 不同公司调拨KJL
                    || StockTransApplicationType.SAMPLE_INBOUND.equals(sta.getType())// 样品领用入库
                    || StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND.equals(sta.getType())// 串号合并入库
                    || StockTransApplicationType.SERIAL_NUMBER_INBOUND.equals(sta.getType())// 串号拆分入库
                    || StockTransApplicationType.SKU_RETURN_INBOUND.equals(sta.getType())// 货返入库
                    || StockTransApplicationType.SKU_EXCHANGE_INBOUND.equals(sta.getType())// 商品置换入库
                    || StockTransApplicationType.REAPAIR_INBOUND.equals(sta.getType())// 送修入库
            ) {
                // 换货入库
                if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
                    List<MsgRtnInboundOrderLineCommand> tnInboundOrderLinelist =
                            msgRtnInboundOrderLineDao.findstaLinBymsgInboundId(sta.getCode(), rntorder.getSource(), rntorder.getId(), new BeanPropertyRowMapper<MsgRtnInboundOrderLineCommand>(MsgRtnInboundOrderLineCommand.class));
                    for (MsgRtnInboundOrderLineCommand msgrntLine : tnInboundOrderLinelist) {
                        InventoryStatus instatus = null;
                        if (msgrntLine.getSkuId() == null) {
                            msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                            log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! SKU is null");
                            return;
                        }
                        if (msgrntLine.getQty() == null || msgrntLine.getQty().equals(0l)) {
                            log.debug("IDS -> WMS STA[" + sta.getCode() + "]! QTY is null or QTY is 0");
                            if (Constants.VIM_WH_SOURCE_IDS.equals(rntorder.getSource())) {
                                continue;
                            } else {
                                msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                                return;
                            }
                        }
                        if (msgrntLine.getInvStatusId() == null) {
                            msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                            log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! InvStatus is null");
                            return;
                        } else {
                            instatus = inventoryStatusDao.getByPrimaryKey(msgrntLine.getInvStatusId());
                            if (instatus == null) {
                                msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                                log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! InvStatus is null");
                                return;
                            }
                        }
                        StaLine line = new StaLine();
                        line.setQuantity(msgrntLine.getQty());
                        line.setInvStatus(instatus);
                        Sku sku = skuDao.getByPrimaryKey(msgrntLine.getSkuId());
                        if (sku == null) {
                            log.error("inbound sku not found----------------------------------");
                            throw new BusinessException("");
                        }
                        line.setSku(sku);
                        staline.add(line);
                    }
                } else {
                    List<MsgRtnInboundOrderLine> rntLine = msgLineDao.findRtnOrderLineByRId(rntorder.getId());
                    for (MsgRtnInboundOrderLine msgrntLine : rntLine) {
                        if (msgrntLine.getSkuId() == null) {
                            // gdv 特殊处理排除sku 不存在的行
                            if ((Constants.VIM_WH_SOURCE_KERRYEAS).equals(rntorder.getSource()) || (Constants.VIM_WH_SOURCE_KERRYEAS_SH).equals(rntorder.getSource()) || (Constants.VIM_WH_SOURCE_KERRYEAS_BJ).equals(rntorder.getSource())
                                    || (Constants.VIM_WH_SOURCE_KERRYEAS_GZ).equals(rntorder.getSource())) {
                                continue;
                            } else {
                                msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                                log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! SKU is null");
                                return;
                            }
                        }
                        if (msgrntLine.getInvStatus() == null) {
                            msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                            log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! InvStatus is null");
                            return;
                        } else {
                            if (StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())) {
                                if (!msgrntLine.getInvStatus().equals(sta.getAddiStatus())) {
                                    msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                                    log.debug("IDS -> WMS STA[" + sta.getCode() + "] is error! InvStatus is Error");
                                    return;
                                }
                            }
                        }
                        if (msgrntLine.getQty() == null || msgrntLine.getQty().equals(0l)) {
                            log.debug("IDS -> WMS STA[" + sta.getCode() + "]! QTY is null or QTY is 0");
                            continue;
                        }
                        StaLine line = new StaLine();
                        line.setQuantity(msgrntLine.getQty());
                        line.setInvStatus(msgrntLine.getInvStatus());
                        // 外包仓商品对接定制
                        ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, rntorder.getSource());
                        Sku sku = null;
                        BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                        if (choose != null) {
                            if (choose.getOptionValue().equals("CODE")) {
                                sku = skuDao.getByCode(msgrntLine.getSkuCode());
                            } else if (choose.getOptionValue().equals("BARCODE")) {
                                sku = skuDao.getSkuByBarcode(msgrntLine.getSkuCode());
                            } else if (choose.getOptionValue().equals("EXTCODE1")) {
                                sku = skuDao.getSkuByExtensionCode1(msgrntLine.getSkuCode());
                            } else if (choose.getOptionValue().equals("EXTCODE2")) {
                                sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(msgrntLine.getSkuCode(), warehouseDao.getByOuId(sta.getMainWarehouse().getId()).getCustomer().getId(), shop == null ? null : shop.getId());
                            }
                        } else {
                            sku = skuDao.getByCode(msgrntLine.getSkuCode());
                        }
                        if (sku == null) {
                            log.info("sku not found ,sku:{}", msgrntLine.getSkuCode());
                            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                        }
                        line.setSku(sku);
                        staline.add(line);
                    }
                }
                try {
                    if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
                        wareHouseManager.transactionTypeInboundReturn(sta.getId(), staline, rntorder);
                        orderStatus = true;
                    } else if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType())// 库间移动KJL迁移
                            || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())// 同公司调拨KJL
                            || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(sta.getType())// 不同公司调拨KJL
                            || StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.equals(sta.getType())// VMI移库入库迁移KJL
                            || StockTransApplicationType.INBOUND_PURCHASE.equals(sta.getType()) || StockTransApplicationType.INBOUND_SETTLEMENT.equals(sta.getType())
                            || StockTransApplicationType.INBOUND_OTHERS.equals(sta.getType())
                            || StockTransApplicationType.INBOUND_CONSIGNMENT.equals(sta.getType()) || StockTransApplicationType.INBOUND_GIFT.equals(sta.getType())
                            || StockTransApplicationType.INBOUND_MOBILE.equals(sta.getType())
                            || StockTransApplicationType.SAMPLE_INBOUND.equals(sta.getType())// 样品领用入库
                            || StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND.equals(sta.getType())// 串号合并入库
                            || StockTransApplicationType.SERIAL_NUMBER_INBOUND.equals(sta.getType())// 串号拆分入库
                            || StockTransApplicationType.SKU_RETURN_INBOUND.equals(sta.getType())// 货返入库
                            || StockTransApplicationType.SKU_EXCHANGE_INBOUND.equals(sta.getType())// 商品置换入库
                            || StockTransApplicationType.REAPAIR_INBOUND.equals(sta.getType())// 送修入库
                    ) {
                        wareHouseManager.purchaseReceiveStep(sta.getId(), staline, rntorder);
                        orderStatus = true;
                    }
                } catch (BusinessException e) {
                    log.warn("transactionTypeInboundReturn error,error code is : {}", ((BusinessException) e).getErrorCode());
                } catch (Exception e) {
                    msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.CANCELED.getValue());
                    log.error("", e);
                }
                if (orderStatus) {
                    msgRtnDao.updateOrderStauts(rntorder.getId(), DefaultStatus.FINISHED.getValue());
                }
            }
        }
    }

    public void inboundToBz(List<MsgRtnInboundOrder> rntorderList) {
        for (MsgRtnInboundOrder rntorder : rntorderList) {
            try {
                inoutManager.updateInOrderSkuId(rntorder.getId());
                msgInBoundWareHouse(rntorder);
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    log.error("inboundToBz error IN ,error code is : {}", ((BusinessException) e).getErrorCode());
                } else {
                    log.error("", e);
                }
            }
        }
    }

    public void vmiSalesCreatePageInfo(long msgId) {
        MsgRtnOutboundCommand msg = msgRtnOutboundDao.findVmiMsgOutbound(msgId, new BeanPropertyRowMapper<MsgRtnOutboundCommand>(MsgRtnOutboundCommand.class));
        if (msg != null) {
            StockTransApplication sta = staDao.getByPrimaryKey(msg.getStaId());
            try {
                if (sta != null) {
                    if ((sta.getStatus().getValue() == 4 || sta.getStatus().getValue() == 10 || sta.getStatus().getValue() == 15 || sta.getStatus().getValue() == 17)) {
                        wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                    } else {
                        wareHouseManager.vmiSalesCreatePage(msg.getOuId(), msg.getTrackingNo(), msg.getWeight(), msg.getStaId(), null, msg.getLpCode(), msg);
                        wareHouseManager.transactionsalesStaOutBound(msg.getStaId());
                        wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
                    }
                }
            } catch (Exception ex) {
                wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.CANCELED.getValue());
                if (log.isErrorEnabled()) {
                    log.error("vmiSalesCreatePageInfo Exception:" + msgId, ex);
                }
            }
        } else {
            wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.CANCELED.getValue());
        }

    }

    public void salesStaOutBound(long msgId) {

        MsgRtnOutboundCommand msg = msgRtnOutboundDao.findVmiMsgOutbound(msgId, new BeanPropertyRowMapper<MsgRtnOutboundCommand>(MsgRtnOutboundCommand.class));
        if (msg != null) {
            try {
                wareHouseManager.transactionsalesStaOutBound(msg.getStaId());
                wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.FINISHED.getValue());
            } catch (Exception ex) {
                wareHouseManager.updateMsgRtnOutbound(msgId, DefaultStatus.CANCELED.getValue());
                if (log.isErrorEnabled()) {
                    log.error("salesStaOutBound Exception:" + msgId, ex);
                }
            }
        }
    }

    @Override
    public void uaInboundToBz(MsgRtnInboundOrder rntorder) {
        try {
            inoutManager.updateInOrderSkuId(rntorder.getId());
            msgInBoundWareHouse(rntorder);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                log.error("uaInboundToBz error ,error code is : {}", ((BusinessException) e).getErrorCode());
            } else {
                log.error("", e);
            }
        }
    }

    public SfOrderFilterLog saveSfOrderFilter(OrderFilterResult rs) {
        SfOrderFilterLog lg = new SfOrderFilterLog();
        lg.setCreateTime(new Date());
        lg.setOrderId(rs.getOrderid());
        if (OrderFilterResultAccept.FLAG_SUCCESS.equals(rs.getAccept().getFlag())) {
            lg.setMailno(rs.getAccept().getMailNo());
            lg.setIsAccept(true);
        } else {
            lg.setIsAccept(false);
        }
        lg = sfOrderFilterLogDao.save(lg);
        return lg;
    }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeSfCancelOrderQueue() {
        List<OperationUnit> cmps = operationUnitDao.findAllByType(OperationUnitType.OUTYPE_COMPANY_SHOP);
        if (cmps != null) {
            for (OperationUnit ou : cmps) {
                List<SfOrderCancelQueue> list = sfOrderCancelQueueDao.findExtOrderByOu(Constants.SF_QUEUE_TYP_COUNT, ou.getId());
                TransSfInfo t = null;// transSfInfoDao.findTransSfInfo(ou.getId());
                for (SfOrderCancelQueue q : list) {
                    try {
                        wareHouseManager.sfCancelOrder(q, t, ou.getId());
                    } catch (Exception e) {
                        wareHouseManager.sfCancelOrderAddCount(q);
                        log.error("", e);
                    }
                }
            }
        }
    }


    // @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    // public void exeSfConfirmOrderQueue() {
    // List<SfConfirmOrderQueue> qs =
    // sfConfirmOrderQueueDao.findExtOrder(Constants.SF_QUEUE_TYP_COUNT);
    // for (SfConfirmOrderQueue q : qs) {
    // wareHouseManager.exeSfConfirmOrder(q);
    // }
    // }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeZtoConfirmOrderQueue() {
        List<ZtoConfirmOrderQueue> qs = ztoConfirmOrderQueueDao.findExtOrder(Constants.SF_QUEUE_TYP_COUNT);
        for (ZtoConfirmOrderQueue q : qs) {
            wareHouseManager.exeZtoConfirmOrder(q);
        }
    }

    public void gdvOutboundconfirm(String message) {
        MsgRtnOutbound rtnOutbound = godivaManager.receiveGodivaOutboundByMq(message);
        gdvinBound(rtnOutbound);
    }

    public void gdvinBound(MsgRtnOutbound rtnOutbound) {
        // MsgRtnOutbound
        // rtnOutbound=godivaManager.receiveGodivaOutboundByMq(message);
        if (rtnOutbound != null) {
            StockTransApplication sta = staDao.findStaByCode(rtnOutbound.getStaCode());
            try {
                if (sta != null) {
                    if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                        callVmiSalesStaOutBound(rtnOutbound.getId());
                    } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                        wareHouseManager.updateMsgRtnOutbound(rtnOutbound.getId(), DefaultStatus.FINISHED.getValue());
                    }
                } else {
                    // 判断是否重复反馈
                    StockTransApplication stainfo = staDao.findStaBySlipCode(rtnOutbound.getSlipCode());
                    if (stainfo != null) {
                        wareHouseManager.updateMsgRtnOutbound(rtnOutbound.getId(), DefaultStatus.FINISHED.getValue());
                    } else {
                        // gdv 线下订单处理
                        StockTransApplication staApplication = godivaManager.createOutSalesOrder(rtnOutbound.getId());
                        if (staApplication != null) {
                            callVmiSalesStaOutBound(rtnOutbound.getId());
                        }
                    }
                }
            } catch (Exception ex) {
                wareHouseManager.updateMsgRtnOutbound(rtnOutbound.getId(), DefaultStatus.ERROR.getValue());
                if (ex instanceof BusinessException) {
                    log.error("gdvinBound error,error code is : {}", ((BusinessException) ex).getErrorCode());
                } else {
                    log.error("", ex);
                }
            }
        }
    }

    public void godivaInventoryAdjustment(String message) {
        log.info("========= start godivaInventoryAdjustment ===========");
        Map<String, List<GodivaInventoryAdjustment>> adjustMap = null;
        try {
            adjustMap = godivaManager.receiveGodivaInventoryAdjustment(message);
            godivaManager.executeGdvInventoryAdjustment(adjustMap);
            log.info("========= end godivaInventoryAdjustment ===========");
        } catch (BusinessException e) {
            log.error("----------->>Godiva->WMS Error ! Adjustment execution Error!");
            List<GodivaInventoryAdjustment> ajustAddList = adjustMap.get("Add");
            if (ajustAddList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustAddList) {
                    gdaoAdjustmentDao.updateStatusByInvId(DefaultStatus.ERROR.getValue(), adjustment.getId());
                }
            }
            List<GodivaInventoryAdjustment> ajustMinusList = adjustMap.get("Minus");
            if (ajustMinusList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustMinusList) {
                    gdaoAdjustmentDao.updateStatusByInvId(DefaultStatus.ERROR.getValue(), adjustment.getId());
                }
            }
            log.error("", e);
        } catch (Exception e) {
            List<GodivaInventoryAdjustment> ajustAddList = adjustMap.get("Add");
            if (ajustAddList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustAddList) {
                    gdaoAdjustmentDao.updateStatusByInvId(DefaultStatus.ERROR.getValue(), adjustment.getId());
                }
            }
            List<GodivaInventoryAdjustment> ajustMinusList = adjustMap.get("Minus");
            if (ajustMinusList != null) {
                for (GodivaInventoryAdjustment adjustment : ajustMinusList) {
                    gdaoAdjustmentDao.updateStatusByInvId(DefaultStatus.ERROR.getValue(), adjustment.getId());
                }
            }
            log.error("", e);
        }

    }

    public void godivaInventoryMovement(String message) {

        List<MsgRtnInboundOrder> msgRtnorderList = godivaManager.receiveGodivaOutInventoryMovement(message);
        try {

            if (msgRtnorderList != null) {
                godivaManager.executeGdvMovement(msgRtnorderList);
            }

        } catch (BusinessException e) {
            for (MsgRtnInboundOrder msgRtnorder : msgRtnorderList) {
                msgRtnDao.updateOrderStauts(msgRtnorder.getId(), DefaultStatus.ERROR.getValue());
            }
            throw new BusinessException();
        } catch (Exception e) {
            for (MsgRtnInboundOrder msgRtnorder : msgRtnorderList) {
                msgRtnDao.updateOrderStauts(msgRtnorder.getId(), DefaultStatus.ERROR.getValue());
            }
            log.error("----------->>Godiva->WMS Error ! Movement execution Error!");
            log.error("", e);
        }
    }

    public void executeGodvInbound() {
        List<String> slipseCodeList = msgRtnDao.getMsgRtnInboundByStatus(Constants.VIM_WH_SOURCE_KERRYEAS, new SingleColumnRowMapper<String>(String.class));

        for (String slipCode : slipseCodeList) {
            List<MsgRtnInboundOrder> msgRtnorderList = msgRtnDao.findMsgRtnInboundOrders(Constants.VIM_WH_SOURCE_KERRYEAS, slipCode, new BeanPropertyRowMapper<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));

            if (msgRtnorderList != null) {
                godivaManager.executeGdvMovement(msgRtnorderList);
            }
        }
    }

    public void executeGodvRtnOubound() {
        List<MsgRtnOutbound> outboundsList = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_KERRYEAS, new BeanPropertyRowMapperExt<MsgRtnOutbound>(MsgRtnOutbound.class));
        for (MsgRtnOutbound rtnOutbound : outboundsList) {
            gdvinBound(rtnOutbound);
        }
    }

    @Override
    public Pagination<SkuCommand> findAllProduct(int start, int pageSize, SkuCommand proCmd, Sort[] sorts) {
        return skuDao.findAllProducts(start, pageSize, proCmd.getSkuInfoMap(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class), sorts);
    }

    @Override
    public Pagination<SkuCommand> findAllProduct2(int start, int pageSize, SkuCommand proCmd, Sort[] sorts) {
        return skuDao.findAllProducts2(start, pageSize, proCmd.getSkuInfoMap(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class), sorts);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus pdaPurchaseSnImport(File file) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = productInfoMainTainReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            List<SkuCommand> pros = (List<SkuCommand>) beans.get("pro");
            if (pros == null) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA);// 没有数据
            }
            List<String> errList = new ArrayList<String>();
            for (int i = 0; i < pros.size(); i++) {
                for (int j = i + 1; j < pros.size(); j++) {
                    if (pros.get(j).getCode().equals(pros.get(i).getCode())) {
                        errList.add(pros.get(j).getCode());
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPOERT_DATA_NOTUNIQUE, new Object[] {errList.toString()});
            }// 存在重复
            errList.clear();
            for (int i = 0; i < pros.size(); i++) {
                List<Sku> skuList = skuDao.getByJmCode(pros.get(i).getCode());
                if (skuList == null || skuList.size() == 0) {
                    errList.add(pros.get(i).getCode());
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPORT_DATA_NOTEXISTS, new Object[] {errList.toString()});
            }// 编号对应商品不存在
            errList.clear();
            Map<String, SkuCategories> map = new HashMap<String, SkuCategories>();
            for (int i = 0; i < pros.size(); i++) {
                if (pros.get(i).getSkuCategoriesName() != null && !pros.get(i).getSkuCategoriesName().equals("")) {
                    String key = pros.get(i).getSkuCategoriesName().trim();
                    if (map.containsKey(key)) continue;
                    SkuCategories sc = skuCategoriesDao.getBySkuCategoriesName(key);
                    if (sc == null) {
                        errList.add(key);
                    } else {
                        map.put(key, sc);
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPORT_CATAGORY_NOTEXISTS, new Object[] {errList.toString()});
            }// 编号对应的商品分类不存在
            errList.clear();
            Map<String, String> map1 = new HashMap<String, String>();
            for (int i = 0; i < pros.size(); i++) {
                if (pros.get(i).getPackageBarCode() != null && !pros.get(i).getPackageBarCode().equals("")) {
                    String key = pros.get(i).getPackageBarCode().trim();
                    if (map.containsKey(key)) continue;
                    Sku pSku = skuDao.getByBarcode1(key);
                    if (pSku == null) {
                        errList.add(key);
                    } else {
                        if (pSku.getJmCode().equals(pros.get(i).getJmCode())) {
                            errList.add(key);
                        } else {
                            map1.put(key, key);
                        }
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                // 编码{0}对应的箱型不存在/与当前维护商品一致
                throw new BusinessException(ErrorCode.IMPORT_PACKAGESKU_ERROR, new Object[] {errList.toString()});
            }
            errList.clear();
            for (int i = 0; i < pros.size(); i++) {
                String isRail = pros.get(i).getRailwayStr();
                if (isRail != null) {
                    if (!(isRail.equals("是") || isRail.equals("否"))) {
                        throw new BusinessException(ErrorCode.ISRAILWAY_MUST_TRUE_FALSE);
                    }
                }
            }
            for (int i = 0; i < pros.size(); i++) {
                String tn = pros.get(i).getSkuTypeName();
                if (tn != null && !"".equals(tn)) {
                    SkuType stList = skuTypeDao.getSkuTypeByName(tn);
                    if (stList != null) {
                        pros.get(i).setSkuType(stList);
                    } else {
                        errList.add(tn);
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                // 编码{0}对应的箱型不存在/与当前维护商品一致
                throw new BusinessException(ErrorCode.IMPORT_SKUTYPENAME_ERROR, new Object[] {errList.toString()});
            }
            errList.clear();
            try {
                for (int i = 0; i < pros.size(); i++) {
                    SkuCommand sku = pros.get(i);
                    if (sku.getRailwayStr() != null) {
                        if (sku.getRailwayStr().equals("是")) {
                            sku.setDeliveryType(TransDeliveryType.LAND);
                        } else {
                            sku.setDeliveryType(TransDeliveryType.ORDINARY);
                        }
                    }
                    wareHouseManagerExe.updateSkuInfoByJmCode(sku.getPackageBarCode() == null ? null : sku.getPackageBarCode().trim(), sku.getCode(), sku.getLength(), sku.getWidth(), sku.getHeight(), sku.getGrossWeight(),
                            sku.getSkuCategoriesName() == null ? null : sku.getSkuCategoriesName().trim(), sku.getDeliveryType(), sku.getSkuType() == null ? null : sku.getSkuType());
                }
            } catch (Exception e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.UPDATE_PRO_INFO_ERROR);
            }
        }
        return readStatus;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus nikeSkuInfoImport(File file, String brandName) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = nikeProductInfoMaintainReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            List<SkuCommand> pros = (List<SkuCommand>) beans.get("pro");
            if (pros.size() == 0) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA);// 没有数据
            }
            List<String> errList = new ArrayList<String>();
            List<String> errList1 = new ArrayList<String>();
            for (int i = 0; i < pros.size(); i++) {
                for (int j = i + 1; j < pros.size(); j++) {
                    if (pros.get(j).getBarCode().equals(pros.get(i).getBarCode())) {
                        errList.add(pros.get(j).getBarCode());
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPOERT_DATA_NOTUNIQUE, new Object[] {errList.toString()});
            }// 存在重复
            errList.clear();
            for (int i = 0; i < pros.size(); i++) {
                Sku s = skuDao.getByBarcode1(pros.get(i).getBarCode());
                if (s == null) {
                    errList.add(pros.get(i).getBarCode());
                } else {
                    Brand brand = brandDao.getByPrimaryKey(s.getBrand().getId());
                    if (brand != null && !brandName.equals(StringUtils.trimWhitespace(brand.getName()))) {
                        errList1.add(pros.get(i).getBarCode());
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPORT_DATA_NOTEXISTS, new Object[] {errList.toString()});
            }// 编号对应商品不存在
            if (!errList1.isEmpty() && errList1.size() > 0) {
                throw new BusinessException(ErrorCode.IMPORT_DATA_BRAND_ERROR, new Object[] {errList1.toString()});
            }// 编号对应品牌不正确
            errList.clear();
            errList1.clear();
            Map<String, SkuCategories> map = new HashMap<String, SkuCategories>();
            for (int i = 0; i < pros.size(); i++) {
                if (pros.get(i).getSkuCategoriesName() != null && !pros.get(i).getSkuCategoriesName().equals("")) {
                    String key = pros.get(i).getSkuCategoriesName().trim();
                    if (map.containsKey(key)) continue;
                    SkuCategories sc = skuCategoriesDao.getBySkuCategoriesName(key);
                    if (sc == null) {
                        errList.add(key);
                    } else {
                        map.put(key, sc);
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.IMPORT_CATAGORY_NOTEXISTS, new Object[] {errList.toString()});
            }// 编号对应的商品分类不存在
            errList.clear();
            Map<String, String> map1 = new HashMap<String, String>();
            for (int i = 0; i < pros.size(); i++) {
                if (pros.get(i).getPackageBarCode() != null && !pros.get(i).getPackageBarCode().equals("")) {
                    String key = pros.get(i).getPackageBarCode().trim();
                    if (map.containsKey(key)) continue;
                    Sku pSku = skuDao.getByBarcode1(key);
                    if (pSku == null) {
                        errList.add(key);
                    } else {
                        if (pSku.getJmCode().equals(pros.get(i).getJmCode())) {
                            errList.add(key);
                        } else {
                            map1.put(key, key);
                        }
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                // 编码{0}对应的箱型不存在/与当前维护商品一致
                throw new BusinessException(ErrorCode.IMPORT_PACKAGESKU_ERROR, new Object[] {errList.toString()});
            }
            errList.clear();
            try {
                for (int i = 0; i < pros.size(); i++) {
                    SkuCommand sku = pros.get(i);
                    wareHouseManager.updateNikeSkuInfoByBarCode(sku.getPackageBarCode() == null ? null : sku.getPackageBarCode().trim(), sku.getBarCode(), sku.getSupplierCode(), sku.getKeyProperties(), sku.getName(), sku.getLength(), sku.getWidth(), sku
                            .getHeight(), sku.getGrossWeight(), sku.getSkuCategoriesName() == null ? null : sku.getSkuCategoriesName().trim(), sku.getCountryOfOrigin() == null ? null : sku.getCountryOfOrigin().trim(), sku.getHtsCode() == null
                            ? null
                            : sku.getHtsCode().trim(), sku.getUnitName() == null ? null : sku.getUnitName().trim());
                }
            } catch (Exception e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.UPDATE_PRO_INFO_ERROR);
            }
        }
        return readStatus;
    }

    @Override
    public List<Brand> selectAllBrandName() {

        List<Brand> bList = new ArrayList<Brand>();
        List<Brand> brand = brandDao.findAllBrand(new BeanPropertyRowMapper<Brand>(Brand.class));

        for (Brand b : brand) {
            Brand bc = new Brand();
            try {
                org.springframework.beans.BeanUtils.copyProperties(b, bc);
                bList.add(bc);
            } catch (Exception e) {
                log.error("Copy Bean properties error for Brand");
                log.error("", e);
                throw new RuntimeException("Copy Bean properties error for Brand");
            }
        }

        return bList;
    }

    @Override
    public JSONArray selectAllCategoriesName() {
        JSONArray ja = new JSONArray();
        try {
            List<SkuCategories> cate = skuCategoriesDao.findAllCategories(new BeanPropertyRowMapper<SkuCategories>(SkuCategories.class));
            for (int i = 0; i < cate.size(); i++) {
                SkuCategories s = cate.get(i);
                JSONObject jo = new JSONObject();
                jo.put("skuCategoriesName", s.getSkuCategoriesName());
                jo.put("id", s.getId());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    /**
     * 配货清单占用库存
     * 
     * @param plid
     * @param ouId
     * @param userId
     * @throws Exception
     */
    public void occupiedInventoryByPickinglist(Long plid, Long userId, Long ouId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plid);
        Boolean isSkipWeight = warehouseDao.getByOuId(ouId).getIsSkipWeight();
        List<SkuCommand> skuList = skuDao.findNoThreeDimensionalSku(plid, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (skuList.size() > 0 && isSkipWeight == true) {
            MongDbNoThreeDimensional mongDbNoThreeDimensional = new MongDbNoThreeDimensional();
            mongDbNoThreeDimensional.setPinkingListId(pl.getId());
            mongDbNoThreeDimensional.setPinkingCode(pl.getCode());
            mongDbNoThreeDimensional.setCreateTime(new Date());
            mongDbNoThreeDimensional.setOuId(pl.getWarehouse().getId());
            mongDbNoThreeDimensional.setSkuList(skuList);
            mongDbNoThreeDimensional.setStore(skuList.get(0).getOwner());
            pl.setStatus(PickingListStatus.FAILED);
            pickingListDao.save(pl);
            mongoOperation.save(mongDbNoThreeDimensional);
        }
        if (!PickingListStatus.FAILED_SEND.equals(pl.getStatus()) && !PickingListStatus.FAILED.equals(pl.getStatus())) {
            List<StockTransApplication> stalist = staDao.findStaByPickingList(plid, pl.getWarehouse().getId());
            // 占用库存
            if (stalist != null) {
                BusinessException rootBe = new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
                boolean isError = false;// 是否所有作业单配货成功
                // for (StockTransApplication sta : stalist) {
                // 判断是不是合并的作业单
                // if (sta.getIsMerge() != null && sta.getIsMerge()) {
                // // // 此处为合并的作业单
                // List<StockTransApplication> oldStaList =
                // staDao.findStaByNewStaId(sta.getId());
                // List<Long> staS = new ArrayList<Long>();// 保存成功占用库存的子STAID
                // List<Long> staE = new ArrayList<Long>();// 保存失败占用库存的子STAID
                // for (StockTransApplication s : oldStaList) {
                // // 合并作业单的子作业单信息
                // // 作业单占用库存，失败会抛出ERROR
                // String result = occupiedInventoryByStaStr(s, userId);
                // if (result.equals("SUCCESS")) {
                // // 占用库存成功
                // staS.add(s.getId());
                // }
                // if (result.equals("ERROR")) {
                // // 占用库存失败
                // staE.add(s.getId());
                // }
                // }
                // try {
                // if (staS.size() != oldStaList.size()) {
                // mergeStaManager.newStaNEoldSta(staS, staE, sta, pl);// 配货清单子订单部分占用库存成功
                // }
                // if (staS.size() == oldStaList.size()) {
                // mergeStaManager.newStaEqualOldSta(staS, sta);// 配货清单子订单全部占用库存成功
                // }
                // } catch (BusinessException e) {
                // throw e;
                // }
                // } else {
                // 普通作业单
                // try {
                // // 作业单不占用库存，失败会抛出异常
                // occupiedInventoryBySta(sta, userId);
                // } catch (BusinessException e) {
                // isError = true;
                // // 增加错误信息
                // setLinkedBusinessException(rootBe, e);
                // }
                // }
                // }
                List<StockTransApplication> pickListSta = staDao.findStaByPickingList(plid, pl.getWarehouse().getId());// 验证配货清单是否有对应的作业单
                if (pickListSta.size() == 0) {
                    // 配货清单下没有作业单数据
                    isError = true;
                }
                for (StockTransApplication s : pickListSta) {
                    if (s.getStatus() == StockTransApplicationStatus.FAILED) {
                        // 如果配货清单下有配货失败单据
                        isError = true;
                    }
                    // if (s.getStatus() == StockTransApplicationStatus.CREATED && s.getIsMerge()) {
                    // // 如果是新建状态(合并的订单) 和配货清单取消关联
                    // s.setPickingList(null);
                    // staDao.save(s);
                    // }
                }
                if (isError) {
                    // 失败更新配货清单状态
                    List<StockTransApplication> pickListS = staDao.findStaByPickingList(plid, pl.getWarehouse().getId());// 验证配货清单是否有对应的作业单
                    warehouseOutBoundManager.occupiedPickingListFailed(plid, userId);
                    if (pickListS.size() == 0) {
                        // 配货清单下没有作业单数据 删除此配货清单
                        // pickingListLogDao.deletePickingListLog(plid);
                        pickingListLogDao.updatePickingListLog(plid, "WareHouseManagerProxyImpl.occupiedInventoryByPickinglist");
                        pickingListDao.deleteByPrimaryKey(plid);
                    }
                    // 失败抛出异常
                    throw rootBe;
                } else {
                    // 成功更新配货清单状态
                    warehouseOutBoundManager.occupiedPickingListSuccess(plid, userId);
                    pickingModeSkuCounter(plid, pl.getWarehouse().getId());


                }






            }
        }
    }

    /**
     * 格式配货模式计数器计算
     * 
     * @param plid
     * @param ouId
     */
    public void pickingModeSkuCounter(Long plid, Long ouId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plid);
        if (PickingListStatus.PACKING.equals(pl.getStatus())) {
            List<StockTransApplication> stalist = staDao.findStaByPickingList(plid, ouId);
            if (stalist != null) {
                if (pl.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                    secKillSkuDao.deleteSecSkillSkuIsSystemByOu(ouId, stalist.get(0).getSkus());
                } else if (pl.getCheckMode().equals(PickingListCheckMode.PICKING_PACKAGE)) {
                    List<Long> idList = staDao.findIfExistsStaNotPick(stalist.get(0).getPackageSku(), new SingleColumnRowMapper<Long>(Long.class));
                    if (idList.size() == 0) {
                        PackageSku ps = packageSkuDao.getByPrimaryKey(stalist.get(0).getPackageSku());
                        if (ps.getIsSystem() != null && ps.getIsSystem()) {
                            // 插入新的packageSkuLog
                            packageSkuLogDao.newPackageSkuLog(stalist.get(0).getPackageSku());
                            // 根据Id删除对应的套装组合商品
                            packageSkuDao.deleteByPrimaryKey(stalist.get(0).getPackageSku());
                        }
                    }
                } else {
                    for (StockTransApplication sta : stalist) {
                        if (!sta.getPickingType().equals(StockTransApplicationPickingType.GROUP)) {
                            warehouseOutBoundManager.sedKillSkuCounterByPickingList(sta);
                        }
                    }
                }
            }
        }
    }

    /** 
     *
     */
    @Override
    public void occupiedInventoryBySta(Long staId, Long userId) {
        if (log.isInfoEnabled()) {
            log.info("WareHouseManagerProxyImpl.occupiedInventoryBySta...Enter staId:" + staId + ",userId:" + userId);
        }
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            occupiedInventoryBySta(sta, userId);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("", e);
            }
            if (log.isInfoEnabled()) {
                log.info("OCP INV FAILED");
            }
        }
        if (log.isInfoEnabled()) {
            log.info("WareHouseManagerProxyImpl.occupiedInventoryBySta...end staId:" + staId + ",userId:" + userId);
        }
    }

    private void occupiedInventoryBySta(StockTransApplication sta, Long userId) {
        if (StockTransApplicationStatus.FAILED.equals(sta.getStatus()) || StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            try {
                wareHouseManager.createStvByStaId(sta.getId(), userId, null, false);
            } catch (BusinessException e) {
                // 配货失败
                wareHouseManager.setStaOccupaidFailed(sta.getId());
                throw e;
            } catch (Exception e) {
                // 系统异常
                wareHouseManager.setStaOccupaidFailed(sta.getId());
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    public void newOccupiedInventoryBySta(Long staId, Long userId, String wooCode) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (StockTransApplicationStatus.FAILED.equals(sta.getStatus()) || StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
            try {
                wareHouseManager.createStvByStaId(sta.getId(), userId, wooCode, true);
            } catch (BusinessException e) {
                // 配货失败
                log.error(wooCode + ":" + staId + " newStaOcpInv BusinessException..........");
                wareHouseManager.setStaOccupaidFailed(sta.getId());
                throw e;
            } catch (Exception e) {
                // 系统异常
                log.error(wooCode + ":" + staId + " newStaOcpInv Exception..........");
                wareHouseManager.setStaOccupaidFailed(sta.getId());
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    public void newOccupiedInventoryByStaShou(String staCode,Long ouId) {
        String[] ss=  staCode.split(","); 
        if(ss.length>20){
            throw new BusinessException("单据不可以超过20单");
        }
        if(ss.length==0){
            throw new BusinessException("请输入单据");
        }
        for (String code : ss) {
            StockTransApplication sta = staDao.getByCode(code);
            if(sta!=null &&  ouId.toString().equals(sta.getMainWarehouse().getId().toString())){
                newOccupiedInventoryBySta(sta.getId(), null, null);
            }else if(sta==null){
                throw new BusinessException(code+"单据不存在");
            }
        }
    }

    
    /**
     * 设置StvLine 的生产日期 和 过期时间
     * 
     * @param stvLine 需要设置的StvLine
     * @param strProductionDate 需要转换的生产日期
     * @param strExpireDate 需要过期时间
     */
    public void setStvLineProductionDateAndExpireDate(StvLine stvLine, String strProductionDate, String strExpireDate) {
        if (stvLine.getSku() == null || stvLine.getSku().getId() == null) {
            throw new BusinessException(ErrorCode.PDA_SKU_NOT_FOUND);
        }
        Sku sku = skuDao.getByPrimaryKey(stvLine.getSku().getId());
        if (sku.getStoremode() == null || !InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
            return;
        }
        // 对于保质期商品的生产日期 转换
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        // 保质期商品必需提供上产日期和过期时间的其中之一
        if (StringUtil.isEmpty(strProductionDate) && StringUtil.isEmpty(strExpireDate)) {
            throw new BusinessException(ErrorCode.SKU_PD_AND_ED_IS_NULL, new Object[] {sku.getBarCode()});
        }

        // 生产日期默认格式为 YYYYMMDD 为八位
        if (!StringUtil.isEmpty(strProductionDate) && strProductionDate.trim().length() != 8) {
            throw new BusinessException(ErrorCode.SKU_PODUCTION_DATE_IS_ERROR, new Object[] {sku.getBarCode(), strProductionDate});
        }

        // 过期时间默认格式为 YYYYMMDD 为八位
        if (!StringUtil.isEmpty(strExpireDate) && strExpireDate.trim().length() != 8) {
            throw new BusinessException(ErrorCode.SKU_EXPIRE_DATE_IS_ERROR, new Object[] {sku.getBarCode(), strExpireDate});
        }

        Date productionDate = null;
        if (!StringUtil.isEmpty(strProductionDate)) {
            try {
                // 生成日期格式转换
                productionDate = formatDate.parse(strProductionDate.trim());
            } catch (ParseException e) {
                throw new BusinessException(ErrorCode.SKU_PODUCTION_DATE_IS_ERROR, new Object[] {sku.getBarCode(), strProductionDate});
            }
        }

        Date expireDate = null;
        if (!StringUtil.isEmpty(strExpireDate)) {
            try {
                // 过期时间格式转换
                expireDate = formatDate.parse(strExpireDate.trim());
            } catch (ParseException e) {
                throw new BusinessException(ErrorCode.SKU_EXPIRE_DATE_IS_ERROR, new Object[] {sku.getBarCode(), strExpireDate});
            }
        }
        expireDate = getFormatExpireDate(productionDate, expireDate, sku);
        stvLine.setProductionDate(productionDate);
        stvLine.setValidDate(sku.getValidDate());
        stvLine.setExpireDate(expireDate);
    }

    /**
     * 获取格式化后的过期时间
     * 
     * @param productionDate 生成日期
     * @param expireDate 过期时间
     * @param sku 商品
     * @return
     */
    public Date getFormatExpireDate(Date productionDate, Date expireDate, Sku sku) {
        if (sku == null || sku.getStoremode() == null || !InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
            return null;
        }
        if (productionDate == null && expireDate == null) {
            throw new BusinessException(ErrorCode.SKU_EXPIRE_DATE_IS_NULL, new Object[] {sku.getBarCode()});
        }
        // 判断 时间类型 是否没有维护
        if (sku.getTimeType() == null) {
            throw new BusinessException(ErrorCode.SKU_TIME_TYPE_IS_NULL, new Object[] {sku.getBarCode()});
        }
        if (expireDate == null) {
            // 生产日期提供，而过期时间没提供时 判断商品有保质时间是否维护
            if (sku.getValidDate() == null) {
                throw new BusinessException(ErrorCode.SKU_SHELF_MANAGEMENT_VALID_DATE, new Object[] {sku.getBarCode()});
            }
            Calendar c = Calendar.getInstance();
            c.setTime(productionDate); // 设置生成日期
            c.add(Calendar.DATE, sku.getValidDate()); // 加上保质时间(天)
            expireDate = c.getTime();
        }
        if (!TimeTypeMode.DAY.equals(sku.getTimeType())) {
            SimpleDateFormat tempFD = new SimpleDateFormat(TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "yyyyMM" : "yyyy");
            String dataStr = tempFD.format(expireDate) + (TimeTypeMode.MONTH.equals(sku.getTimeType()) ? "01" : "0101");
            try {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
                expireDate = formatDate.parse(dataStr);
            } catch (ParseException e) {
                throw new BusinessException(ErrorCode.SKU_EXPIRE_DATE_IS_ERROR, new Object[] {sku.getBarCode(), dataStr});
            }
        }
        return expireDate;
    }

    /**
     * 分页查询品牌名称
     * 
     * @return
     **/
    public Pagination<Brand> findBrandByPage(int start, int pagesize, Sort[] sorts) {
        return brandDao.findBrandByPage(start, pagesize, new BeanPropertyRowMapper<Brand>(Brand.class), sorts);
    }

    /**
     * 订单匹配集货口
     */
    public void mateShiipingPoint(Long ouId, List<Long> staIdList) {
        // TODO Auto-generated method stub

    }

    public String exeExlFile(ImportFileLog files) {
        String msg = "";
        try {
            msg = excelReadManager.exeFileInput(files);
        } catch (BusinessException e) {
            msg += "执行失败：" + applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
        } catch (Exception e) {
            log.error("exeExlFile_123", e);
            msg += "执行失败-：";
        }
        return msg;
    }

    /**
     * 修改仓库信息
     */
    @Override
    public void updateWarehouse(Warehouse warehouse, OperationUnit ou, WhAddStatusSource wss, String transIds, AutoPlConfigCommand apc) {
        baseinfoManager.updateWarehouse(warehouse, ou, wss, transIds, apc);
        // if (!StringUtil.isEmpty(taskValue)) {
        // // 更新ZK节点
        // zkClient.writeData(znode, taskValue);
        // }
    }

    // private String occupiedInventoryByStaStr(StockTransApplication sta, Long userId) {
    //
    // String result = "";
    // if (StockTransApplicationStatus.FAILED.equals(sta.getStatus()) ||
    // StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
    // try {
    // wareHouseManager.createStvByStaId(sta.getId(), userId);
    // result = "SUCCESS";
    // } catch (BusinessException e) {
    // // 配货失败
    // wareHouseManager.setStaOccupaidFailed(sta.getId());
    // result = "ERROR";
    // // throw e;
    // } catch (Exception e) {
    // // 系统异常
    // wareHouseManager.setStaOccupaidFailed(sta.getId());
    // result = "ERROR";
    // log.error("", e);
    // // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    // }
    // }
    // return result;
    // }
}
