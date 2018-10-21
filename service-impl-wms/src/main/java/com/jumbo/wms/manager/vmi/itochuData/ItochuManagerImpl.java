package com.jumbo.wms.manager.vmi.itochuData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.etamData.EtamRtnDataDao;
import com.jumbo.dao.vmi.etamData.EtamRtnDataLineDao;
import com.jumbo.dao.vmi.itochuData.ItochuCheckInventoryDao;
import com.jumbo.dao.vmi.itochuData.ItochuMsgInboundOrderDao;
import com.jumbo.dao.vmi.itochuData.ItochuRtnInvDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnDao;
import com.jumbo.dao.vmi.warehouse.MsgTypeDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.UnicodeReader;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.etamData.EtamRtnData;
import com.jumbo.wms.model.vmi.etamData.EtamRtnDataLine;
import com.jumbo.wms.model.vmi.warehouse.ItochuCheckInventory;
import com.jumbo.wms.model.vmi.warehouse.ItochuMsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.ItochuRtnInv;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.MsgType;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

/**
 * ETAM
 * 
 * @author Administrator
 * 
 */

@Transactional
@Service("itochuManager")
public class ItochuManagerImpl implements ItochuManager {

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private EtamRtnDataDao etamRtnDataDao;
    @Autowired
    private ItochuMsgInboundOrderDao itochuMsgInboundOrderDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private ItochuCheckInventoryDao itochuCheckInventoryDao;
    @Autowired
    private MsgRtnReturnDao msgRtnReturnDao;
    @Autowired
    private MsgTypeDao msgTypeDao;
    @Autowired
    private ItochuRtnInvDao itochuRtnInvDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wmsOtherOutBoundInvNoticeOmsDao;
    @Autowired
    private HubWmsService hubWmsService;

    /**
	 * 
	 */
    private static final long serialVersionUID = 959714255077233892L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    /**
     * 行 起始前缀
     */

    private String delimiter = "";

    // private static String ITOCHU_FILE_POSTFIX = ".shupl";
    private String outBoundRtn = "outBoundRtn";
    private String inBoundRtn = "inBoundRtn";
    private String inBoundRtn2 = "inBoundRtn2";
    private String checkInventory = "checkInventory";

    /**
     * 出库通知
     */
    @Transactional
    public void outBoundNotify(String localFileDir) {
        log.debug("-------------Etam outBoundNotify---------------start-------");
        // 1.读中间表
        List<MsgOutboundOrder> orders = msgOutboundOrderDao.findMsgOutboundOrder(Constants.VIM_WH_SOURCE_ITOCHU, null, DefaultStatus.CREATED.getValue(), true, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
        String fileName = "Deliver_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".sh";
        StringBuilder sbl = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        // 2.写到本地文件
        List<MsgType> msgTypeList = msgTypeDao.findTypeListBySource(Constants.VIM_WH_SOURCE_ITOCHU, MsgType.TYPE_ORDER_TYPE);
        for (MsgOutboundOrder order : orders) {
            List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(order.getId());
            for (MsgOutboundOrderLine line : lines) {
                sbl.append(connectOutBoundLineData(order, line));
            }
            // String orderShopCode = map.get(order.getShopId());
            String data = connectOutBoundData(order, msgTypeList);
            if (data == null || data.trim().length() == 0) {
                log.debug("data is null.......");
                continue;
            }
            sb.append(data);
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // update 状态
        for (MsgOutboundOrder o : orders) {
            msgOutboundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), o.getId());
        }

        log.debug("-------------Etam outBoundNotify---------------end-------");
    }

    private String connectOutBoundData(MsgOutboundOrder order, List<MsgType> msgTypeList) {
        StringBuilder sb = new StringBuilder();
        sb.append("SHHDRDNNEW");
        sb.append(delimiter);
        sb.append(Constants.VIM_WH_SOURCE_ITOCHU);
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append("ETAM");
        sb.append(delimiter);
        sb.append(order.getReceiver());
        sb.append(delimiter);
        sb.append(order.getTotalActual() == null ? 0 : order.getTotalActual());// orderPrice
        sb.append(delimiter);
        sb.append(order.getTransferFee() == null ? 0 : order.getTransferFee());// transFee
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append(order.getReceiver() == null ? "" : order.getReceiver());
        sb.append(delimiter);
        sb.append(order.getCountry() == null ? "" : order.getCountry());
        sb.append(delimiter);
        sb.append(order.getProvince() == null ? "" : order.getProvince());
        sb.append(delimiter);
        sb.append(order.getCity() == null ? "" : order.getCity());
        sb.append(delimiter);
        sb.append(order.getDistrict() == null ? "" : order.getDistrict());
        sb.append(delimiter);
        sb.append(order.getZipcode() == null ? "100000" : order.getZipcode());
        sb.append(delimiter);
        String addr = order.getAddress();
        if (addr != null) {
            // addr = addr.replaceAll(System.getProperty("line.separator"), "");
            Pattern p = Pattern.compile("\r|\n|\r\n");
            Matcher m = p.matcher(addr);
            addr = m.replaceAll(" ");
        }
        String addr0 = "", addr1 = "", addr2 = "";
        if (addr != null) {
            if (addr.length() > 50 && addr.length() <= 100) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
            } else if ((addr.length() > 50 && addr.length() <= 150)) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
                addr2 = addr.substring(100, addr.length());
            } else if (addr.length() > 50 && addr.length() > 150) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
                addr2 = addr.substring(100, 150);
            } else {
                addr0 = addr;
            }
        }
        sb.append(addr0 == null ? "" : addr0);
        sb.append(delimiter);
        sb.append(addr1);
        sb.append(delimiter);
        sb.append(addr2);
        sb.append(delimiter);
        sb.append("SF".equals(order.getLpCode()) ? "SF" : "快递");
        // sb.append(StringUtils.hasText(order.getLpCode()) ? order.getLpCode() : "快递");
        sb.append(delimiter);
        sb.append("SF".equals(order.getLpCode()) ? "SF" : "快递");
        // sb.append(StringUtils.hasText(order.getLpCode()) ? order.getLpCode() : "快递");
        sb.append(delimiter);
        sb.append(order.getTelePhone() == null ? "" : order.getTelePhone());// phone
        sb.append(delimiter);
        sb.append(order.getMobile() == null ? "" : order.getMobile());// mobile
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("0");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);

        StockTransApplication sta = staDao.findStaByCode(order.getStaCode());
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String taobaoCode = "";
        String remark0 = "";
        String type0 = "";
        for (MsgType t : msgTypeList) {
            if (sta.getActivitySource() != null && order.getShopId().equals(t.getShop().getId())) {
                String[] sources = sta.getActivitySource().split(StockTransApplication.ACTIVITY_SOURCE_SLIPT);
                for (String source : sources) {
                    if (source.equals(t.getActivitySource())) {
                        type0 = t.getSourceType();
                        break;
                    }
                }
            } else if (sta.getActivitySource() == null && order.getShopId().equals(t.getShop().getId())) {
                type0 = t.getSourceType();
            }
        }
        if (type0 == null || "".equals(type0)) {
            type0 = msgTypeList.get(0).getSourceType();
        }
        taobaoCode = sta.getSlipCode2();
        remark0 = sta.getMemo();
        sb.append(taobaoCode == null ? "" : subStringByFlagFirst("_", taobaoCode));// 淘宝订单号
        sb.append(delimiter);
        sb.append(remark0);
        sb.append(delimiter);
        sb.append("Y");
        sb.append(delimiter);
        sb.append(type0 == null ? "" : type0);
        sb.append("\r\n");
        return sb.toString();
    }

    private String connectOutBoundLineData(MsgOutboundOrder order, MsgOutboundOrderLine line) {
        StringBuilder sb = new StringBuilder();
        sb.append("SHDTLDNNEW");
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append(delimiter);
        sb.append("ETAM");
        sb.append(delimiter);
        sb.append(line.getSku().getBarCode());
        sb.append(delimiter);
        sb.append(line.getUnitPrice() == null ? 0 : line.getUnitPrice());
        sb.append(delimiter);
        sb.append(line.getQty());
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        if (line.getActivitySource() == null) {
            sb.append("");
        } else if ("JHS".equals(line.getActivitySource())) {
            sb.append("聚划算");
        } else {
            sb.append("");
        }
        // sb.append(line.getActivitySource() == null ? "" : line.getActivitySource());
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append("\r\n");
        // sb.append("\r");

        return sb.toString();
    }

    /**
     * 将文件数据写到出库反馈中间表
     * 
     * @throws ParseException
     */
    @Transactional
    public boolean outBoundRtnInsertIntoDB(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);

                    if (result.length == 11) {
                        MsgRtnOutbound msg0 = msgRtnOutboundDao.findByStaCode(result[2]);
                        // 数据重复则返回
                        if (msg0 != null) {
                            continue;
                        }
                        MsgRtnOutbound rtn = new MsgRtnOutbound();
                        rtn.setSource(result[1]);
                        rtn.setStaCode(result[2]);
                        rtn.setOutboundTime(FormatUtil.stringToDate(result[4], "yyyyMMdd"));
                        rtn.setLpCode(result[5]);
                        rtn.setTrackingNo(result[6]);
                        rtn.setTransFeeCost(new BigDecimal(result[7]));
                        rtn.setRemark(result[8]);
                        rtn.setField1(result[9]);
                        rtn.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU);
                        rtn.setType(result[10]);
                        rtn.setStatus(DefaultStatus.CREATED);
                        msgRtnOutboundDao.save(rtn);
                    } else if (result.length == 8) {
                        MsgRtnOutbound msg0 = msgRtnOutboundDao.findByStaCode(result[2]);
                        MsgRtnOutboundLine msgline0 = msgRtnOutboundLineDao.findMsgOutBoundLineByStaCodeAndSkuCode(result[2], result[3], new BeanPropertyRowMapper<MsgRtnOutboundLine>(MsgRtnOutboundLine.class));
                        if (msg0 == null || msgline0 != null) {
                            continue;
                        }
                        MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                        line.setSkuCode(result[3]);
                        line.setQty(Long.parseLong(result[4]));
                        MsgRtnOutboundLine line2 = msgRtnOutboundLineDao.save(line);
                        msgRtnOutboundLineDao.flush();
                        // find,update
                        MsgRtnOutbound msg = msgRtnOutboundDao.findByStaCode(result[2]);
                        msgRtnOutboundLineDao.updateMsgIdById(msg.getId(), line2.getId());
                    }
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    log.error("", e);
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return flag;
    }

    /**
     * 将文件数据写到入库反馈中间表
     */
    @Transactional
    public boolean inBoundRtnInsertIntoDB(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    ItochuMsgInboundOrder ito2 = itochuMsgInboundOrderDao.findItochuMsgByBoxAndSku(result[0], result[2], result[3]);
                    if (ito2 != null) {
                        continue;
                    }
                    ItochuMsgInboundOrder itoMsg = new ItochuMsgInboundOrder();
                    itoMsg.setBillNo(result[0]);
                    itoMsg.setDT(result[1]);
                    itoMsg.setBoxNo(result[2]);
                    itoMsg.setSku(result[3]);
                    itoMsg.setQuantity(Integer.parseInt(result[5]));
                    itoMsg.setUserDef1(result[6]);
                    itoMsg.setUserDef2("");
                    itoMsg.setUserDef3("");
                    itoMsg.setInvStatus(result[4]);
                    itoMsg.setStatus(DefaultStatus.CREATED);
                    itochuMsgInboundOrderDao.save(itoMsg);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }

        }
        return flag;
    }

    /**
     * 将退货文件数据写到入库反馈中间表
     */
    @Transactional
    public boolean inBoundRtnInsertIntoDB2(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            Warehouse wh = warehouseDao.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_ITOCHU);
            Long customerId = wareHouseManagerQuery.getCustomerByWarehouse(wh);
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    if (result.length > 6 && result.length < 8) {
                        String result2[] = new String[8];
                        for (int i = 0; i < result.length; i++) {
                            result2[i] = result[i];
                        }
                        result2[result.length] = "";
                        result = result2;
                    }
                    if (result.length == 6) {
                        // 去除重复的行
                        MsgRtnInboundOrder rtn2 = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        if (rtn2 != null) {
                            continue;
                        }
                        MsgRtnInboundOrder rtn = new MsgRtnInboundOrder();
                        rtn.setStaCode(result[1]);
                        Integer t = msgTypeDao.findTypeBySourceandType2(Constants.VIM_WH_SOURCE_ITOCHU, result[2], new SingleColumnRowMapper<Integer>(Integer.class));
                        int type0 = 0;
                        if (t != null) {
                            type0 = t.intValue();
                        }
                        rtn.setType(type0);
                        rtn.setSource(Constants.VIM_WH_SOURCE_ITOCHU);
                        rtn.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU);
                        rtn.setStatus(DefaultStatus.CREATED);
                        rtn.setCreateTime(new Date());
                        rtn.setInboundTime(FormatUtil.stringToDate(result[4], "yyyyMMdd"));
                        msgRtnInboundOrderDao.save(rtn);
                        msgRtnInboundOrderDao.flush();
                    } else if (result.length == 8) {
                        MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
                        Sku k = skuDao.getByBarcode(result[5], customerId);
                        // 去除重复的行
                        MsgRtnInboundOrder rtn2 = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        MsgRtnInboundOrderLine line1 = msgRtnInboundOrderLineDao.findRtnOrderLineByStaCodeAndSkuCode2(result[1], k.getCode(), new BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
                        if (rtn2 == null || line1 != null) {
                            continue;
                        }
                        // is 库存状态
                        Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();
                        InventoryStatus invStatus = stsMap.get(result[7]);
                        if (invStatus == null) {
                            invStatus = inventoryStatusDao.findByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU, result[7], new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                            stsMap.put(result[7], invStatus);
                        }
                        line.setSkuCode(k.getCode());
                        line.setQty(Long.parseLong(result[6]));
                        line.setInvStatus(invStatus);
                        line = msgRtnInboundOrderLineDao.save(line);
                        msgRtnInboundOrderLineDao.flush();
                        MsgRtnInboundOrder rtnMsg = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        msgRtnInboundOrderLineDao.updateRtnMsgIdById(rtnMsg.getId(), line.getId());
                    }
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    log.error("", e);
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return flag;
    }

    /**
     * 读文件数据
     */
    @Transactional
    public void readFileIntoDBInOutBoundRtn(String localFileDir, String bakFileDir, String condition, String fileStart, String fileEnd) {
        File fileDir = new File(localFileDir);
        File[] files = fileDir.listFiles();
        // log.debug("files.length="+files.length);
        if (files == null || files.length == 0) {
            log.debug("{} is null, has no file ============================", localFileDir);
        } else {
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (!file.isDirectory() && (file.getName().indexOf(fileStart) != -1 && file.getName().indexOf(fileEnd) != -1)) {
                    FileInputStream in = null;
                    BufferedReader buffRead = null;
                    UnicodeReader uReader = null;
                    boolean success = false;
                    try {
                        String line = null;
                        List<String> results = new ArrayList<String>();
                        in = new FileInputStream(file);
                        uReader = new UnicodeReader(in, Charset.defaultCharset().name());
                        buffRead = new BufferedReader(uReader);
                        while ((line = buffRead.readLine()) != null) {
                            results.add(line);
                        }
                        if (condition.equals(outBoundRtn)) {
                            outBoundRtnInsertIntoDB(results);
                        } else if (condition.equals(inBoundRtn)) {
                            inBoundRtnInsertIntoDB(results);
                        } else if (condition.equals(inBoundRtn2)) {
                            inBoundRtnInsertIntoDB2(results);
                        } else if (condition.equals(checkInventory)) {
                            checkInventoryInsertIntoDB2(results);
                        }
                        success = true;
                    } catch (FileNotFoundException e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
                    } catch (IOException e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.IO_EXCEPTION);
                    } catch (Exception e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    } finally {
                        if (buffRead != null) {
                            try {
                                buffRead.close();
                            } catch (IOException e) {}
                        }
                        if (uReader != null) {
                            try {
                                uReader.close();
                            } catch (IOException e) {}
                        }

                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {}
                        }
                        try {
                            if (success) {
                                FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                                file.delete();
                            }
                        } catch (IOException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.IO_EXCEPTION);
                        }
                    }
                }
            }
        }
    }

    /**
     * 入库通知
     */
    /*
     * public void inBoundNotify(String localFileDir) { String fileName = "Receipt_" +
     * FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".rc"; // 读中间表 List<MsgInboundOrder>
     * ins = msgInboundOrderDao.findMsgInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU, new
     * BeanPropertyRowMapperExt<MsgInboundOrder>(MsgInboundOrder.class));
     * 
     * // 写数据到本地文件 StringBuilder sbl = new StringBuilder(); for (MsgInboundOrder in : ins) {
     * StringBuilder sb = new StringBuilder(); sb.append("RCHDRDNNEW"); sb.append(delimiter);
     * sb.append(Constants.VIM_WH_SOURCE_ITOCHU); sb.append(delimiter); sb.append(in.getStaCode());
     * sb.append(delimiter); sb.append(in.getStaCode()); sb.append(delimiter); sb.append("ETAM");
     * sb.append(delimiter); sb.append(in.getType().getValue()); sb.append(delimiter);
     * sb.append(in.getRemark() == null ? "" : in.getRemark()); if (sb.toString() == null ||
     * sb.toString().trim().length() == 0) { log.debug("data is null......."); continue; }
     * writeDataFile(sb.toString(), localFileDir, fileName);
     * msgInboundOrderDao.updateMsgInboundStatus(in.getId(), DefaultStatus.FINISHED.getValue());
     * 
     * List<MsgInboundOrderLineCommand> lines =
     * msgInboundOrderLineDao.findVmiMsgInboundOrderLine(in.getId(), new
     * BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class)); for
     * (MsgInboundOrderLineCommand line : lines) { sbl.append("RCDTLDNNEW"); sbl.append(delimiter);
     * sbl.append(in.getStaCode()); sbl.append(delimiter); sbl.append(line.getId());
     * sbl.append(delimiter); sbl.append("ETAM"); sbl.append(delimiter);
     * sbl.append(line.getSpuCode()); sbl.append(delimiter); sbl.append(0); sbl.append(delimiter);
     * sbl.append(line.getQty()); sbl.append(delimiter); sbl.append(""); sbl.append(delimiter);
     * sbl.append(""); sbl.append(delimiter); sbl.append(""); sbl.append(delimiter);
     * sbl.append(line.getId()); sbl.append(delimiter); sbl.append(in.getType().getValue());
     * sbl.append(delimiter); sbl.append(line.getId()); sbl.append("\r\n"); } }
     * writeDataFile(sbl.toString(), localFileDir, fileName);
     * 
     * }
     */

    /**
     * 退货入库通知
     */
    @Transactional
    public void rtnInBoundNotify(String localFileDir) {
        log.debug("-------------Etam rtnInBoundNotify-----------------start-------");
        // 读中间表
        List<MsgInboundOrder> ins = msgInboundOrderDao.findMsgReturnInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU, null, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        // 写数据到本地文件
        StringBuilder sbl = new StringBuilder();
        String fileName = "Deliver_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".rc";
        StringBuilder sb = new StringBuilder();
        for (MsgInboundOrder in : ins) {
            StockTransApplication sta = staDao.findStaByCode(in.getStaCode());
            sb.append("RCHDRRTNEW");
            sb.append(delimiter);
            sb.append(Constants.VIM_WH_SOURCE_ITOCHU);
            sb.append(delimiter);
            sb.append(in.getStaCode());
            sb.append(delimiter);
            sb.append(in.getStaCode());
            sb.append(delimiter);
            sb.append("ETAM");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append(in.getTotalActual() == null ? 0 : in.getTotalActual());//
            sb.append(delimiter);
            sb.append("");//
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append(in.getTelephone() == null ? "" : in.getTelephone());// phone
            sb.append(delimiter);
            sb.append(in.getMobile() == null ? "" : in.getMobile());// mobile
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            String taobaoCode = sta.getSlipCode2();
            sb.append(taobaoCode == null ? "" : subStringByFlagFirst("_", taobaoCode));
            sb.append(delimiter);
            String type0 = msgTypeDao.findTypeBySourceandType(Constants.VIM_WH_SOURCE_ITOCHU, sta.getType().getValue(), new SingleColumnRowMapper<String>(String.class));
            if (type0 == null) {
                type0 = "";
            }
            sb.append(type0);
            sb.append(delimiter);
            sb.append("");
            sb.append("\r\n");
            if (sb.toString() != null && sb.toString().trim().length() != 0) {
                // msgInboundOrderDao.updateMsgInboundStatus(in.getId(),
                // DefaultStatus.FINISHED.getValue());
                List<MsgInboundOrderLineCommand> lines = msgInboundOrderLineDao.findVmiMsgInboundOrderLine(in.getId(), new BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));
                for (MsgInboundOrderLineCommand line : lines) {
                    sbl.append("RCDTLDNNEW");
                    sbl.append(delimiter);
                    sbl.append(in.getStaCode());
                    sbl.append(delimiter);
                    sbl.append(line.getId());
                    sbl.append(delimiter);
                    sbl.append("ETAM");
                    sbl.append(delimiter);
                    sbl.append(line.getBarCode());
                    sbl.append(delimiter);
                    sbl.append(0);
                    sbl.append(delimiter);
                    sbl.append(line.getQty());
                    sbl.append(delimiter);
                    sbl.append("");
                    sbl.append(delimiter);
                    sbl.append("");
                    sbl.append(delimiter);
                    sbl.append("");
                    sbl.append(delimiter);
                    sbl.append(line.getId());// 流水号
                    sbl.append(delimiter);
                    sbl.append(type0);
                    sbl.append(delimiter);
                    sbl.append(line.getId());
                    sbl.append("\r\n");
                }
            }
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // update status

        for (MsgInboundOrder in : ins) {
            msgInboundOrderDao.updateMsgInboundStatus(in.getId(), DefaultStatus.FINISHED.getValue());
        }
        log.debug("-------------Etam rtnInBoundNotify-----------------end-------");

    }

    /**
     * 根据特殊字符截取字符串
     * 
     * @param string
     * @param taobaoCode
     * @return
     */
    private String subStringByFlagFirst(String string, String taobaoCode) {
        int i = taobaoCode.indexOf(string);
        if (i >= 0) {
            return taobaoCode.substring(0, i);
        } else {
            return taobaoCode;
        }
    }

    /**
     * 退货入库取消通知
     */
    @Transactional
    public void rtnCancelNotify(String localFileDir) {
        log.debug("-------------Etam rtnCancelNotify-----------------start-------");
        // 读中间表
        List<MsgRaCancel> list = msgRaCancelDao.findNewMsgBySource(Constants.VIM_WH_SOURCE_ITOCHU, new BeanPropertyRowMapperExt<MsgRaCancel>(MsgRaCancel.class));
        // 写数据到本地文件
        StringBuilder sbl = new StringBuilder();
        String fileName = "RcDel_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".rc";
        StringBuilder sb = new StringBuilder();
        for (MsgRaCancel msg : list) {
            sb.append("REDEL");
            sb.append(delimiter);
            sb.append(msg.getStaCode());
            sb.append(delimiter);
            sb.append(msg.getStaCode());
            sb.append("");
            sb.append("\r\n");
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        for (MsgRaCancel msg : list) {
            msgRaCancelDao.updateStatusById(msg.getId(), DefaultStatus.FINISHED.getValue());
        }
        log.debug("-------------Etam rtnCancelNotify-----------------end-------");
    }

    /**
     * 将数据写入到本地文件
     */
    @Transactional
    private boolean writeDataFile(String data, String localDir, String fileName) {
        if (data == null || data.trim().length() == 0) {
            log.debug("data is null ***********************  app exit");
            return false;
        }
        boolean flag = false;
        if (data == null || data.length() == 0) return false;
        BufferedWriter br = null;
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        try {
            out = new FileOutputStream(localDir + File.separator + fileName, true);
            // br = new BufferedWriter(new FileWriter(new File(localDir + File.separator +
            // fileName),true)); // 追加方式-添加数据
            osw = new OutputStreamWriter(out, "UTF-8");
            br = new BufferedWriter(osw);
            br.write(data);
            // br.newLine();
            flag = true;
        } catch (IOException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.IO_EXCEPTION);
        } finally {
            try {
                if (br != null) {
                    br.flush();
                    br.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.IO_EXCEPTION);
            }
        }
        return flag;
    }

    /**
     * 创STA
     */

    @Transactional
    public void createInBoundSta(List<ItochuMsgInboundOrder> boxs) {
        log.debug("-------------Etam create STA-----------------start-------");
        for (ItochuMsgInboundOrder box : boxs) {
            List<ItochuMsgInboundOrder> orders = itochuMsgInboundOrderDao.findAllInboundOrderByStatus(box.getBoxNo(), new BeanPropertyRowMapperExt<ItochuMsgInboundOrder>(ItochuMsgInboundOrder.class));
            if (orders == null || (orders != null && orders.size() == 0)) {
                continue;
            }

            OperationUnit ou = null;
            String innerShopCode = null;
            BiChannel shop = null;
            Warehouse wh = null;

            if (wh == null) {
                wh = whManager.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_ITOCHU);
                if (wh == null) {
                    // log.debug("=========OPERATION UNIT {} NOT FOUNT===========",new
                    // Object[]{vmiCode});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                } else {
                    Long ouId = wh.getOu().getId();
                    ou = ouDao.getByPrimaryKey(ouId);
                    if (ou == null) {
                        // log.debug("=========OPERATION UNIT {} NOT FOUNT===========",new
                        // Object[]{vmiCode});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                }
            }
            if (shop == null) {
                List<BiChannel> shops = shopDao.getAllRefShopByWhOuId(wh.getOu().getId());
                if (shops != null && shops.size() > 0) {
                    shop = shops.get(0);
                }
                if (shop == null) {
                    log.debug("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            }
            wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
            innerShopCode = shop.getCode();
            StockTransApplication sta = new StockTransApplication();
            sta.setRefSlipCode(box.getBoxNo());
            sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            sta.setOwner(innerShopCode);
            sta.setMainWarehouse(ou);
            // sta.setMainStatus(is);
            staDao.save(sta);
            sta = staDao.save(sta);
            staDao.flush();
            // source -> bi_wh - > shop ->

            // //////////////////////////////////
            // 入库反馈中间表
            MsgRtnInboundOrder rtnOrder = new MsgRtnInboundOrder();
            rtnOrder.setSource(Constants.VIM_WH_SOURCE_ITOCHU);
            rtnOrder.setCreateTime(new Date());
            rtnOrder.setInboundTime(new Date());
            rtnOrder.setStaCode(sta.getCode());
            rtnOrder.setStatus(DefaultStatus.CREATED);
            rtnOrder.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU);
            rtnOrder = msgRtnInboundOrderDao.save(rtnOrder);
            Long skuQty = 0l;
            Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
            boolean isNoSkuError = false;
            for (ItochuMsgInboundOrder ins : orders) {
                Sku sku = skuDao.getByBarcode(ins.getSku(), customerId);
                if (sku == null) {
                    baseinfoManager.sendMsgToOmsCreateSku(ins.getSku(), shop.getVmiCode());
                    isNoSkuError = true;
                    continue;
                }
                // is 库存状态
                Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();
                InventoryStatus invStatus = stsMap.get(ins.getInvStatus());
                if (invStatus == null) {
                    invStatus = inventoryStatusDao.findByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU, ins.getInvStatus(), new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                    stsMap.put(ins.getInvStatus(), invStatus);
                }
                if (invStatus == null) {
                    log.debug("===========================invStatus is  null invStatus :{} ===============================", invStatus);
                    throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
                }

                StaLine staLine = new StaLine();
                staLine.setQuantity(Long.parseLong(ins.getQuantity() + ""));
                staLine.setSku(sku);
                staLine.setCompleteQuantity(0L);// 已执行数量
                staLine.setSta(sta);
                skuQty += staLine.getQuantity();
                staLine.setOwner(innerShopCode);
                staLine.setInvStatus(invStatus);
                staLine = staLineDao.save(staLine);
                // 入库反馈中间表行
                MsgRtnInboundOrderLine rtnLine = new MsgRtnInboundOrderLine();
                rtnLine.setBarcode(staLine.getSku().getBarCode());
                rtnLine.setInvStatus(staLine.getInvStatus());
                rtnLine.setSkuCode(staLine.getSku().getCode());
                rtnLine.setSkuId(staLine.getSku().getId());
                rtnLine.setQty(staLine.getQuantity());
                rtnLine.setMsgRtnInOrder(rtnOrder);
                msgRtnInboundOrderLineDao.save(rtnLine);
                // //////////////////////////////////
                // 更新状态
                itochuMsgInboundOrderDao.nativeUpdate(ins.getBoxNo(), ins.getSku(), staLine.getSta().getId(), staLine.getId(), DefaultStatus.FINISHED.getValue());
                // itochuMsgInboundOrderDao.save(ins);

            }
            if (isNoSkuError) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            sta.setSkuQty(skuQty);
            staDao.save(sta);
            log.debug("===============sta {} create success ================", new Object[] {sta.getCode()});

        }
        log.debug("-------------Etam create STA-----------------end-------");
    }

    @Autowired
    private EtamRtnDataLineDao etamRtnDataLineDao;

    // 入库中间表数据库插入数据
    public Boolean saveEtamRtnData(List<String> lines) {
        Boolean flag = false, canMove = false;
        if (lines == null || lines.isEmpty()) {
            return flag;
        }
        try {
            // * 业务单据编号
            String billCode;
            Integer whCode;
            // * 伊藤忠的仓库号
            Integer shopCode;
            // * 箱号
            String boxNo;
            // * 出库日期
            String outBoundTime;
            // * sku条码
            String skuCode;
            // * 数量
            Long quantity;
            // * 库存状态
            String invStatus;
            // * 备用字段1
            String userDef1;
            // * 备用字段2
            String userDef2;
            // * 备用字段3
            String userDef3;
            log.debug("=============================lines size: {}======================", lines.size());

            String orderCode = null;
            for (String s : lines) {
                if (s == null || "".equals(s)) {
                    continue;
                }
                String result[] = s.split(delimiter);
                if (result == null || result.length == 0) continue;
                log.debug("=============================result s: {}======================", s);
                log.debug("=============================result length: {}======================", result.length);

                billCode = result[0];
                whCode = Integer.parseInt(result[1]);
                shopCode = Integer.parseInt(result[2]);
                boxNo = result[3];
                outBoundTime = result[4];
                skuCode = result[5];
                // quantity = Long.parseLong(result[6]);
                quantity = new BigDecimal(result[6]).longValue();
                invStatus = result[7];
                userDef1 = null;
                userDef2 = null;
                userDef3 = null;

                // 跳过重复
                int cnt = etamRtnDataDao.findRtnByBoxNoAndBillCodeAndSku(result[0], result[3], result[5], new SingleColumnRowMapper<Integer>(Integer.class));
                if (cnt > 0) {
                    continue;
                }

                if (orderCode == null || !orderCode.equals(result[0])) {
                    etamRtnDataDao.createEtamDataRtnSql(billCode, whCode, shopCode, boxNo, outBoundTime, invStatus, EtamRtnData.TODO_STATUS);
                    orderCode = result[0];
                }
                /*
                 * if (orderCode != null && orderCode.equals(result[0])){ }else {
                 * etamRtnDataDao.createEtamDataRtnSql(billCode, whCode, shopCode, boxNo,
                 * outBoundTime, invStatus, EtamRtnData.TODO_STATUS); orderCode = result[0]; }
                 */
                etamRtnDataLineDao.createEtamDataRtnLineSql(billCode, skuCode, quantity, invStatus, userDef1, userDef2, userDef3, EtamRtnDataLine.TODO_STATUS);
            }
            List<EtamRtnData> etamRtnDatas = etamRtnDataDao.findAllEtamRtnDatas();
            for (EtamRtnData etamRtnData : etamRtnDatas) {
                etamRtnDataLineDao.updateEtamRtnId(etamRtnData.getId(), EtamRtnDataLine.CREATE_STATUS, etamRtnData.getBillCode());
            }
            // etamDataRtnRealityDao.flush();
            canMove = true;
            flag = true;
        } catch (Exception e) {
            // delete
            try {
                etamRtnDataDao.deleteEtamRtnByTodoStatus(EtamRtnData.TODO_STATUS);
            } catch (Exception e1) {
                if (log.isErrorEnabled()) {
                    log.error("Ito saveEtamRtnData Exception:", e1);
                }
            }
            flag = false;
            canMove = false;
            log.error("", e);
        } finally {
            // update
            if (canMove) {
                flag = true;
                try {
                    etamRtnDataDao.updateEtamRtnByTodoStatus(EtamRtnData.CREATE_STATUS, EtamRtnData.TODO_STATUS);
                } catch (Exception e) {
                    flag = false;
                    log.error("", e);
                }
            }
        }
        return flag;
    }

    /**
     * etam- 退库返厂实绩数据 - 解析文件 - 中间表
     */
    @Transactional
    public boolean returnToEtam(String localFileDir, String bakFileDir) {
        String line = null;
        boolean flag = false;
        List<String> results = null;
        List<String> resultsLine = null;
        BufferedReader buffRead = null;
        File[] files = null;
        try {
            File fileDir = new File(localFileDir);
            files = fileDir.listFiles();
            if (files == null || files.length == 0) {
                log.debug("==================={} is null, has no file ============================", localFileDir);
                return flag;
            }
            results = new ArrayList<String>();
            resultsLine = new ArrayList<String>();
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                log.debug("=================== file  name ===================={} ", file.getName());
                if (file.isDirectory() || (file.getName().indexOf("RecReturn") == -1)) {
                    continue;
                }
                // buffRead = new BufferedReader(new InputStreamReader(new
                // FileInputStream(file),"UTF-8"));
                buffRead = new BufferedReader(new UnicodeReader(new FileInputStream(file), "UTF-8"));
                while ((line = buffRead.readLine()) != null) {
                    log.debug("===================line data : {}  ===================={} ", line);
                    results.add(line);
                }
                log.debug("===================results size:  {} resultsLine size:  {}=================== ", results.size(), resultsLine.size());
                flag = saveEtamRtnData(results);
                results.clear();
                if (buffRead != null) buffRead.close();
                if (flag) {
                    FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                    file.delete();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (buffRead != null) buffRead.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return flag;
    }

    @Transactional
    public void createStaFroEtamRtn(Long id) {
        String vmiSource = Constants.VIM_WH_SOURCE_ITOCHU;
        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(vmiSource);
        if (warehouse == null) throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(warehouse.getOu().getId());
        if (operationUnit == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_OU_NOT_FOUND);
        }
        BigDecimal transactionid = transactionTypeDao.findByStaType(StockTransApplicationType.VMI_RETURN.getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        // inventoryStatusDao.findInvStatusForSaleByWarehouseOuId(operationUnit.getId(), new
        // BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
        createStaForEtamReturn(vmiSource, id, transactionType, operationUnit);
    }

    private void createStaForEtamReturn(String vmiSource, Long etamDataId, TransactionType transType, OperationUnit ou) {
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_RETURN);
        sta.setMainWarehouse(ou);
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        sta.setIsNeedOccupied(true);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        staDao.flush();

        // 批量更新sku_id
        etamRtnDataLineDao.updateSkuIdByEtamRtnId(etamDataId);
        // save staline
        List<EtamRtnDataLine> etamRtnDataLines = etamRtnDataLineDao.findByEtamRtnId(etamDataId, EtamRtnDataLine.CREATE_STATUS, new BeanPropertyRowMapperExt<EtamRtnDataLine>(EtamRtnDataLine.class));

        Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();

        for (EtamRtnDataLine etamRtnDataLine : etamRtnDataLines) {
            StaLine staLine = new StaLine();
            staLine.setQuantity(etamRtnDataLine.getQuantity());
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            InventoryStatus invStatus = stsMap.get(etamRtnDataLine.getInvStatus());
            if (invStatus == null) {
                invStatus = inventoryStatusDao.findByVmiSourceAndStatus(vmiSource, etamRtnDataLine.getInvStatus(), new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                stsMap.put(etamRtnDataLine.getInvStatus(), invStatus);
            }
            if (invStatus == null) {
                log.debug("===========================invStatus is  null invStatus :{} ===============================", invStatus);
                throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
            }
            staLine.setInvStatus(invStatus);
            // sku = skuDao.findSkuByBarCode(etamRtnDataLine.getSkuCode());
            if (etamRtnDataLine.getSkuId() == null) {
                log.debug("===========================sku is  null sku :{} ===============================", etamRtnDataLine.getSkuCode());
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            staLine.setSku(new Sku(etamRtnDataLine.getSkuId()));
            staLineDao.save(staLine);
        }
        staLineDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        // 占用库存
        StockTransVoucher stv = occupyInventoryByStaId(sta.getId(), transType, ou);
        // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
        whManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

        // 删除库存出库
        wareHouseManager.removeInventory(sta, stv);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 其他出库更新中间表，传递明细给oms/pac
        WmsOtherOutBoundInvNoticeOms wtoms = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
        if (wtoms != null) {
            wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 10l);
        }

        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta.getMainWarehouse().getId());
        staLineDao.flush();
        // 更新数据表
        etamRtnDataDao.updateFinishById(etamDataId);
        etamRtnDataLineDao.updateFinishByEtamRtnId(etamDataId);
    }

    // vmi 退仓- vmi(etam / ids )占用库存
    public StockTransVoucher occupyInventoryByStaId(Long staId, TransactionType transType, OperationUnit ou) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 库存占用
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_sta_id", staId);
        SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
        SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
        Map<String, Object> result = null;
        result = staDao.executeSp("sp_occupy_inv_for_rtn_no_loc", sqlParameters, params);
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
                // System.out.println(sku.getBarCode() + "--" + qty);
                BusinessException be = new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new Object[] {sku.getName(), sku.getCode(), sku.getBarCode(), qty});
                current.setLinkedException(be);
            }
            throw root;
        } else {
            // 创建 STV ,STV LINE
            // save stv
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            StockTransVoucher stvCreate = new StockTransVoucher();
            stvCreate.setCode(code);
            stvCreate.setSta(sta);
            stvCreate.setLastModifyTime(new Date());
            stvCreate.setStatus(StockTransVoucherStatus.CREATED);
            stvCreate.setTransactionType(transType);
            stvCreate.setDirection(TransactionDirection.OUTBOUND);
            stvCreate.setWarehouse(ou);
            stvDao.save(stvCreate);
            // find stave
            stvDao.flush();
            stvLineDao.createByStaId(staId);
            // 更新sta状态为库存占用
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse().getId());
            staDao.save(sta);
            // IM
            hubWmsService.insertOccupiedAndRelease(sta.getId());
            return stvCreate;
        }
    }

    public void removeInventory(StockTransApplication sta, StockTransVoucher stv) {
        List<Inventory> inventorys = inventoryDao.findByOccupiedCode(sta.getCode());
        if (inventorys == null || inventorys.isEmpty()) throw new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
        /*
         * StockTransVoucher stv = stvDao.findStvCreatedByStaId(staID); if (stv == null) throw new
         * BusinessException(ErrorCode.STV_NOT_FOUND);
         */
        TransactionType transType = transactionTypeDao.findByCode(Constants.VMI_RETURN_OUT);
        if (transType == null) throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND);

        // delete stvline
        stvLineDao.deleteByStaId(sta.getId());
        // create stvline
        StaLine staLine = null;
        for (Inventory inventory : inventorys) {
            StvLine stvLine = new StvLine();
            stvLine.setBatchCode(inventory.getBatchCode());
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
            stvLine.setQuantity(inventory.getQuantity());
            stvLine.setProductionDate(inventory.getProductionDate());
            stvLine.setValidDate(inventory.getValidDate());
            stvLine.setExpireDate(inventory.getExpireDate());
            staLine = staLineDao.findByInvStatusSkuSta(sta.getId(), inventory.getStatus().getId(), inventory.getSku().getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
            stvLine.setStaLine(staLine);
            stvLineDao.save(stvLine);
        }
        removeInventoryStep2(sta, stv);
        stv.setLastModifyTime(new Date());
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        stv.setFinishTime(new Date());
        stvDao.save(stv);

        sta.setIsNeedOccupied(false);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta.getMainWarehouse().getId());
        sta.setFinishTime(new Date());
        sta.setOutboundTime(new Date());
        staDao.save(sta);
        List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
        if (stalines != null && !stalines.isEmpty()) {
            for (StaLine staline : stalines) {
                staline.setCompleteQuantity(staline.getQuantity());
                staLineDao.save(staline);
            }
        }
    }

    private void removeInventoryStep2(StockTransApplication sta, StockTransVoucher stv) {
        if (null == sta) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (null == stv) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        stv = stvDao.findStvByStvId(stv.getId());
        List<Inventory> list = inventoryDao.findByOccupiedCode(sta.getCode());
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.NO_OCCUPIED_INVENTORY, new Object[] {sta.getCode()});
        }
        msgRtnReturnDao.updateFinishByStacode(sta.getCode());
        User user = stv.getOperator();
        for (Inventory inv : list) {
            // 记录日志
            StockTransTxLog log = new StockTransTxLog();
            log.setDirection(stv.getDirection());
            log.setDistrictId(inv.getDistrict().getId());
            log.setInvStatusId(inv.getStatus().getId());
            log.setLocationId(inv.getLocation().getId());
            log.setOwner(inv.getOwner());
            log.setQuantity(inv.getQuantity());
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
            log.setStaCode(sta.getCode());
            log.setSlipCode(sta.getRefSlipCode());
            log.setSlipCode1(sta.getSlipCode1());
            log.setSlipCode2(sta.getSlipCode2());
            if (null != user) {
                log.setOpUserName(user.getUserName());
            }
            /** -------------归档查询优化------------------ */
            stockTransTxLogDao.save(log);
            inventoryDao.delete(inv);
        }
    }

    /**
     * save 盘点数据
     */
    @Transactional
    public void saveCheckInventory(String localFileDir, String bakFileDir) {
        log.debug("-------------Etam saveCheckInventory-----------------start-------");
        // save date
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, checkInventory, "RecCheck", "txt");
        log.debug("-------------Etam saveCheckInventory-----------------end-------");
    }

    /**
     * //insert 盘点
     * 
     * @param results
     * @return
     */
    @Transactional
    private boolean checkInventoryInsertIntoDB2(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    ItochuCheckInventory ito2 = itochuCheckInventoryDao.findItochuCheckInventoryByBillNoAndSku(result[0], result[2], new BeanPropertyRowMapperExt<ItochuCheckInventory>(ItochuCheckInventory.class));
                    if (ito2 != null) {
                        continue;
                    }
                    if (result.length < 8) {
                        String result2[] = new String[8];
                        for (int i = 0; i < result.length; i++) {
                            result2[i] = result[i];
                        }
                        for (int j = result.length; j < 8; j++) {
                            result2[j] = "";
                        }
                        result = result2;
                    }

                    ItochuCheckInventory itoMsg = new ItochuCheckInventory();
                    itoMsg.setBillNo(result[0]);
                    itoMsg.setDT(result[1]);
                    itoMsg.setSku(result[2]);
                    itoMsg.setSysType(result[3]);
                    itoMsg.setQuanlity(result[4]);
                    itoMsg.setUserDef1(result[5]);
                    itoMsg.setUserDef2(result[6]);
                    itoMsg.setUserDef3(result[7]);
                    itoMsg.setStatus(DefaultStatus.CREATED);
                    itochuCheckInventoryDao.save(itoMsg);
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    log.error("", e);
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }

        }
        return flag;
    }

    /**
     * 读伊藤忠反馈的库存数据
     */
    private void readItochuRtnInv(List<String> lines) {
        Date date = new Date();
        String result[] = new String[6];
        if (lines == null || lines.isEmpty()) {
            return;
        }
        try {
            for (String s : lines) {
                result = s.split(delimiter);
                ItochuRtnInv inv = new ItochuRtnInv();
                inv.setInvup(result[0]);
                inv.setCompany(result[1]);
                inv.setWarehouse(result[2]);
                inv.setSku(result[3]);
                inv.setQty(new BigDecimal(result[4]).longValue());
                inv.setInvStatus(result[5]);
                inv.setCreateTime(date);
                itochuRtnInvDao.save(inv);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException();
        }
    }

    /**
     * 读文件数据 （伊藤忠库存数据）
     */
    private void readItochuRtnInvToDB_2(String localFileDir, String bakFileDir, String fileStart, String fileEnd) {
        File fileDir = new File(localFileDir);
        File[] files = fileDir.listFiles();
        if (files == null || files.length == 0) {
            log.debug("{} is null, has no file ============================", localFileDir);
        } else {
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (!file.isDirectory() && (file.getName().indexOf(fileStart) != -1 && file.getName().indexOf(fileEnd) != -1)) {
                    FileInputStream in = null;
                    BufferedReader buffRead = null;
                    UnicodeReader uReader = null;
                    boolean success = false;
                    try {
                        String line = null;
                        List<String> results = new ArrayList<String>();
                        in = new FileInputStream(file);
                        uReader = new UnicodeReader(in, Charset.defaultCharset().name());
                        buffRead = new BufferedReader(uReader);
                        while ((line = buffRead.readLine()) != null) {
                            results.add(line);
                        }
                        readItochuRtnInv(results);
                        success = true;
                    } catch (FileNotFoundException e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
                    } catch (IOException e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.IO_EXCEPTION);
                    } catch (Exception e) {
                        log.error("", e);
                        success = false;
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    } finally {
                        if (buffRead != null) {
                            try {
                                buffRead.close();
                            } catch (IOException e) {}
                        }
                        if (uReader != null) {
                            try {
                                uReader.close();
                            } catch (IOException e) {}
                        }

                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {}
                        }
                        try {
                            if (success) {
                                FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                                file.delete();
                            }
                        } catch (IOException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.IO_EXCEPTION);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * 读文件数据 （伊藤忠库存数据）
     */
    public void readItochuRtnInvToDB(String localFileDir, String bakFileDir) {
        readItochuRtnInvToDB_2(localFileDir, bakFileDir, "IBAL", "ibupl");
    }
}
