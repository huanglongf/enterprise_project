package com.jumbo.wms.manager.vmi.espData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.vmi.espData.ESPInvoiceBDPoDao;
import com.jumbo.dao.vmi.espData.ESPInvoicePercentageDao;
import com.jumbo.dao.vmi.espData.ESPOrderDao;
import com.jumbo.dao.vmi.espData.ESPPoTypeDao;
import com.jumbo.dao.vmi.espData.ESPReceivingDao;
import com.jumbo.dao.vmi.espData.ESPTransferOrderDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDeliveries;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDelivery;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDeliveryHeader;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDeliveryItem;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDeliveryItems;
import com.jumbo.wms.model.command.vmi.esprit.xml.delivery.EspritDeliveryRoot;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritItemXml;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderRootXml;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderXml;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.EspRecv;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Header;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Invoice;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Item;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Items;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Receiving;
import com.jumbo.wms.model.command.vmi.esprit.xml.receiving.Receivings;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPInvoiceBDPo;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentageCommand;
import com.jumbo.wms.model.vmi.espData.ESPOrder;
import com.jumbo.wms.model.vmi.espData.ESPOrderCommand;
import com.jumbo.wms.model.vmi.espData.ESPPoType;
import com.jumbo.wms.model.vmi.espData.ESPPoTypeCommand;
import com.jumbo.wms.model.vmi.espData.ESPReceiving;
import com.jumbo.wms.model.vmi.espData.ESPTransferOrder;
import com.jumbo.wms.model.vmi.espData.EspritOrderPOType;
import com.jumbo.wms.model.vmi.espData.EspritOrderStatus;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.InventoryTest;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.json.JSONObject;

@Transactional
@Service("espOrderManager")
public class ESPOrderManagerImpl extends BaseManagerImpl implements ESPOrderManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2917557098720611365L;
    private static final String ESPRIT_RECEIVE_NOTICE = "ESPRIT_RECEIVE_NOTICE";
    @Autowired
    private ESPOrderDao espOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private ESPTransferOrderDao transferOrderDao;
    @Autowired
    private ESPPoTypeDao poTypeDao;
    @Autowired
    private ESPInvoicePercentageDao ipDao;
    @Autowired
    private ESPInvoiceBDPoDao bdpoDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private ESPReceivingDao espReceivingDao;
    @Autowired
    private ESPDeliveryDao espDeliveryDao;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private ESPPoTypeDao esptypeDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private StockTransVoucherDao stockTransVoucherDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;


    /**
     * 生成转店反馈文件
     * 
     * @param locationUploadPath
     * @throws Exception
     */
    @Override
    public void writeDeliveryDataToFile(String locationUploadPath) throws Exception {
        // 更新所有待执行记录状态为处理中'2'
        espDeliveryDao.updateStatusToDoing();
        List<ESPDelivery> list = espDeliveryDao.findDeliveryList();
        if (list != null && list.size() > 0) {
            Map<String, EspritDeliveryRoot> map = new HashMap<String, EspritDeliveryRoot>();
            for (ESPDelivery d : list) {
                EspritDeliveryRoot root = map.get(d.getHeaderSequenceNumber());
                if (root == null) {
                    root = new EspritDeliveryRoot();
                    Date sys = new Date();
                    String date = FormatUtil.formatDate(sys, "yyyyMMdd");
                    String time = FormatUtil.formatDate(sys, "HHmmss");
                    EspritDeliveryHeader head = new EspritDeliveryHeader();
                    head.setGenerationDate(date);
                    head.setGenerationTime(time);
                    head.setFromGLN(d.getHeaderFromgln());
                    head.setFromNode(d.getHeaderFromnode());
                    head.setNumberOfRecords(1);
                    head.setSequenceNumber(FormatUtil.addCharForString(d.getHeaderSequenceNumber(), '0', 6, 1));
                    root.setHeader(head);
                    EspritDeliveries espritDeliveries = new EspritDeliveries();
                    EspritDelivery delivery = new EspritDelivery();
                    delivery.setDeliveredFromGLN(d.getDeliveryDeliveredfromGLN());
                    delivery.setDeliveredToGLN(d.getDeliveryDeliveredtoGLN());
                    delivery.setGoodsReceiptDate(d.getDeliveryDeliveryDate());
                    delivery.setDeliveryDate(d.getDeliveryDeliveryDate());
                    delivery.setDeliveryNo(d.getDeliveryDeliveryNO());
                    delivery.setDeliveryStatus(d.getDeliveryDeliveryStatus());
                    delivery.setDeliveryType(d.getDeliveryDeliverytype());
                    EspritDeliveryItems items = new EspritDeliveryItems();
                    items.setItem(new ArrayList<EspritDeliveryItem>());
                    delivery.setItems(items);
                    espritDeliveries.setDelivery(new ArrayList<EspritDelivery>());
                    espritDeliveries.getDelivery().add(delivery);
                    root.setDeliveries(espritDeliveries);
                }
                EspritDeliveryItem item = new EspritDeliveryItem();
                item.setReceivedQty(d.getItemReceivedQTY());
                item.setSku(d.getItemSku());
                root.getDeliveries().getDelivery().get(0).getItems().getItem().add(item);
                map.put(d.getHeaderSequenceNumber(), root);
            }
            for (Entry<String, EspritDeliveryRoot> ent : map.entrySet()) {
                try {
                    EspritDeliveryRoot root = ent.getValue();
                    String seqNo = ent.getKey();
                    EspritDeliveryHeader header = root.getHeader();
                    // 生成文件
                    String fileName = header.getFromGLN() + "_" + header.getToGLN() + "_espDelivery_" + FormatUtil.addCharForString(header.getSequenceNumber(), '0', 8, 1) + Constants.FILE_EXTENSION_XML;
                    JAXBContext jaxbContext = JAXBContext.newInstance(EspritDeliveryRoot.class);
                    StringWriter sw = new StringWriter();
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    marshaller.marshal(root, sw);
                    FileUtil.writeStringToFile(new File(locationUploadPath + "/" + fileName), sw.toString());
                    // 更新状态
                    espDeliveryDao.updateStatusToFinished(seqNo);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    private void updateReceivingDataInvoiceInfo() {
        List<ESPReceiving> list = espReceivingDao.findAllNoneInvoiceAndNotFeedbackReceivingData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (ESPReceiving rec : list) {
            List<ESPReceiving> recList = espReceivingDao.findAllReceivingDataByOrderNo(rec.getReceivingOrdernumber());
            for (ESPReceiving li : recList) {
                if (null == li.getId()) continue;
                ESPReceiving re = espReceivingDao.getByPrimaryKey(li.getId());
                if (null != re) {
                    String slipCode = re.getReceivingOrdernumber();
                    if (StringUtil.isEmpty(slipCode)) {
                        continue;
                    }
                    ESPPoType poType = esptypeDao.findByPo1(slipCode, new BeanPropertyRowMapper<ESPPoType>(ESPPoType.class));
                    if (null != poType) {
                        poType = esptypeDao.getByPrimaryKey(poType.getId());
                        if (EspritOrderPOType.IMPORT.equals(poType.getType())) {
                            // 进口
                            List<ESPInvoicePercentage> invoiceList = ipDao.findESPInvoiceByPoOrderByCreateTime1(slipCode, new BeanPropertyRowMapperExt<ESPInvoicePercentage>(ESPInvoicePercentage.class));
                            if (null == invoiceList || 0 == invoiceList.size()) {
                                continue;
                            }
                            ESPInvoicePercentage invoice = ipDao.getByPrimaryKey(invoiceList.get(0).getId());
                            re.setInvoiceInvoicenumber(invoice.getInvoiceNum());
                            re.setReceivingDutyPercentage(new Double(0).toString());
                            re.setReceivingMiscfeepercentage(new Double(0).toString());
                            re.setReceivingCommPercentage(new Double(0).toString());
                        } else {
                            StockTransApplication sto = staDao.findStaByPo(rec.getReceivingOrdernumber(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                            // StockTransVoucher stvoucher =
                            // stockTransVoucherDao.findStockTransVoucherByStaId(sto.getId(), new
                            // BeanPropertyRowMapperExt<StockTransVoucher>(StockTransVoucher.class));
                            // String receivingDate = re.getReceivingGoodsReceivedDate();
                           
                            /*
                             * if (null != stvoucher.getFinishTime()) { receivingDate =
                             * sdf.format(stvoucher.getFinishTime()); } else { receivingDate =
                             * re.getReceivingGoodsReceivedDate(); }
                             */
                            String receivingDate = sdf.format(new Date());;

                            String receiveDate = receivingDate.substring(4);
                            if (StringUtil.isEmpty(receivingDate)) {
                                continue;
                            }

                            if (null != poType.getDutyPercentage() && !"".equals(poType.getDutyPercentage()) && null != poType.getMiscFeePercentage() && !"".equals(poType.getMiscFeePercentage())) {
                                if ("1.92".equals(poType.getDutyPercentage().toString()) && "1.06".equals(poType.getMiscFeePercentage().toString())) {
                                    poType.setDutyPercentage(1.84D);
                                    poType.setMiscFeePercentage(0.99D);
                                }
                                if ("1.06".equals(poType.getDutyPercentage().toString()) && "1.92".equals(poType.getMiscFeePercentage().toString())) {
                                    poType.setDutyPercentage(1.84D);
                                    poType.setMiscFeePercentage(0.99D);
                                }
                            }
                            if (null != poType.getDutyPercentage() && !"".equals(poType.getDutyPercentage()) && null != poType.getMiscFeePercentage() && !"".equals(poType.getMiscFeePercentage())) {
                                if ("1.84".equals(poType.getDutyPercentage().toString()) && "0.99".equals(poType.getMiscFeePercentage().toString()) && null != poType.getInvoiceNumber() && !"".equals(poType.getInvoiceNumber())) {
                                    if (poType.getInvoiceNumber().length() < 10) {
                                        re.setInvoiceInvoicenumber(poType.getInvoiceNumber() + "B" + receiveDate);
                                    } else {
                                        re.setInvoiceInvoicenumber(poType.getInvoiceNumber());
                                    }
                                }
                                if ("0".equals(poType.getDutyPercentage().toString()) && "0".equals(poType.getMiscFeePercentage().toString()) && null != poType.getInvoiceNumber() && !"".equals(poType.getInvoiceNumber())) {
                                    if (poType.getInvoiceNumber().length() < 10) {
                                        re.setInvoiceInvoicenumber(poType.getInvoiceNumber() + "B" + receiveDate);
                                    } else {
                                        re.setInvoiceInvoicenumber(poType.getInvoiceNumber());
                                    }

                                }
                            } else {
                                re.setInvoiceInvoicenumber(poType.getInvoiceNumber() + "B" + receiveDate);
                            }
                            if (null == re.getInvoiceInvoicenumber() || "".equals(re.getInvoiceInvoicenumber())) {
                                re.setInvoiceInvoicenumber(poType.getInvoiceNumber() + "B" + receiveDate);
                            }
                            re.setReceivingDutyPercentage((null != poType.getDutyPercentage() ? poType.getDutyPercentage().toString() : new Double(0).toString()));
                            re.setReceivingMiscfeepercentage((null != poType.getMiscFeePercentage() ? poType.getMiscFeePercentage().toString() : new Double(0).toString()));
                            re.setReceivingCommPercentage((null != poType.getCommPercentage() ? poType.getCommPercentage().toString() : new Double(0).toString()));
                        }
                        espReceivingDao.save(re);
                    }
                }
            }
        }
        if (null != list && list.size() > 0) {
            espReceivingDao.flush();
        }
    }

    /**
     * 生成ESPRIT收货反馈文件
     */
    @Override
    public void writeReceivingDataToFile(String locationUploadPath) throws Exception {
        // 匹配发票
        updateReceivingDataInvoiceInfo();
        // 更新所有待执行记录状态为处理中'2'
        espReceivingDao.updateReceivingDataForWriting();
        List<ESPReceiving> list = espReceivingDao.findAllWrintingData();
        if (list != null && list.size() > 0) {
            EspRecv rspRecv = new EspRecv();
            Receivings rs = new Receivings();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Map<String, Receiving> tmp = new LinkedHashMap<String, Receiving>();
            for (ESPReceiving rec : list) {
                if (tmp.get(rec.getReceivingOrdernumber()) == null) {
                    Receiving line = new Receiving();
                    StockTransApplication sto = staDao.findStaByPo(rec.getReceivingOrdernumber(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                    StockTransVoucher stvoucher = stockTransVoucherDao.findStockTransVoucherByStaId(sto.getId(), new BeanPropertyRowMapperExt<StockTransVoucher>(StockTransVoucher.class));
                    line.setReceivingNo("RCV" + FormatUtil.addCharForString(tmp.size() + 1 + "", '0', 3, 1));
                    if (null != stvoucher && null != stvoucher.getFinishTime()) {
                        line.setGoodsReceivedDate(sdf.format(stvoucher.getFinishTime()));
                    } else {
                        line.setGoodsReceivedDate(rec.getReceivingGoodsReceivedDate());
                    }
                    line.setWarehouseReference(rec.getReceivingWarehouseReference());
                    line.setOrderNumber(rec.getReceivingOrdernumber());
                    line.setPoReference(rec.getReceivingPoReference());
                    line.setDutyPercentage(rec.getReceivingDutyPercentage());
                    // line.setDutyPercentage(StringUtil.isEmpty(rec.getReceivingDutyPercentage()) ?
                    // "" : rec.getReceivingDutyPercentage());
                    line.setMiscFeePercentage(rec.getReceivingMiscfeepercentage());
                    // line.setMiscFeePercentage(StringUtil.isEmpty(rec.getReceivingMiscfeepercentage())
                    // ? "" : rec.getReceivingMiscfeepercentage());
                    line.setCommissionPercentage(StringUtil.isEmpty(rec.getReceivingCommPercentage()) ? new Double(0).toString() : rec.getReceivingCommPercentage());
                    // line.setCommissionPercentage(StringUtil.isEmpty(rec.getReceivingCommPercentage())
                    // ? "" : rec.getReceivingCommPercentage());
                    line.setBuyerGLN(rec.getReceivingBuyergln());
                    line.setDeliveryPartyGLN(rec.getReceivingDeliverypartygln());
                    line.setWarehouse(rec.getReceivingWarehouse());
                    Invoice inv = new Invoice();
                    inv.setInvoiceNumber(rec.getInvoiceInvoicenumber());
                    inv.setCurrency(rec.getInvoiceCurrency());
                    inv.setTotalFOB(rec.getInvoiceTotalfob());
                    inv.setTotalGTP(rec.getInvoiceTotalgtp());
                    inv.setTotalQty(rec.getInvoiceTotalqty());
                    line.setInvoice(inv);
                    Items items = new Items();
                    items.setItem(new ArrayList<Item>());
                    Item item = new Item();
                    item.setReceivedQty(rec.getItemReceivedqty().toString());
                    item.setSku(rec.getItemSku());
                    items.getItem().add(item);
                    line.setItems(items);
                    tmp.put(line.getOrderNumber(), line);
                } else {
                    Receiving line = tmp.get(rec.getReceivingOrdernumber());
                    Items items = line.getItems();
                    Item item = new Item();
                    item.setReceivedQty(rec.getItemReceivedqty());
                    item.setSku(rec.getItemSku());
                    items.getItem().add(item);
                }
            }
            // 设置头信息
            Header header = new Header();
            Date sys = new Date();
            header.setGenerationDate(FormatUtil.formatDate(sys, "yyyyMMdd"));
            header.setGenerationTime(FormatUtil.formatDate(sys, "HHmmss"));
            String seq = espReceivingDao.findHeaderSeq(new SingleColumnRowMapper<String>(String.class));
            header.setSequenceNumber(seq);
            header.setFromGLN(list.get(0).getHeaderFromGLN());
            header.setFromNode(list.get(0).getHeaderFromNode());
            header.setToGLN(list.get(0).getHeaderToGLN());
            header.setToNode(list.get(0).getHeaderToNode());
            List<Receiving> rl = new ArrayList<Receiving>();
            rl.addAll(tmp.values());
            rs.setReceiving(rl);
            rspRecv.setReceivings(rs);
            header.setNumberOfRecords(rspRecv.getReceivings().getReceiving().size() + "");
            rspRecv.setHeader(header);
            // 生成文件
            String fileName = header.getFromGLN() + "_" + header.getToGLN() + "_espRecv_" + FormatUtil.addCharForString(header.getSequenceNumber(), '0', 8, 1) + Constants.FILE_EXTENSION_XML;
            JAXBContext jaxbContext = JAXBContext.newInstance(EspRecv.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(rspRecv, sw);
            String toWriteStr = sw.toString().replace("<espRecv>", "<espRecv xmlns=\"http://www.esprit.com.cn/XMLSchema/eShopInterface/espRecv.xsd\">");
            FileUtil.writeStringToFile(new File(locationUploadPath + "/" + fileName), toWriteStr);
            // FileUtil.writeStringToFile(new File("D:/test/mytest2.txt"), toWriteStr);
            List<ESPReceiving> ESPReceivingList = espReceivingDao.receivingList(new BeanPropertyRowMapper<ESPReceiving>(ESPReceiving.class));
            if (ESPReceivingList != null && ESPReceivingList.size() > 0) {
                for (ESPReceiving eSPReceiving : ESPReceivingList) {
                    ESPInvoiceBDPo eSPInvoiceBDPo = bdpoDao.findByInvoice(eSPReceiving.getInvoiceInvoicenumber());
                    if (eSPInvoiceBDPo != null) {
                        ESPInvoiceBDPo NewESPInvoiceBDPo = new ESPInvoiceBDPo();
                        NewESPInvoiceBDPo.setCreateTime(new Date());
                        NewESPInvoiceBDPo.setPo(eSPInvoiceBDPo.getPo());
                        if (eSPInvoiceBDPo.getInvoiceNumber().contains("_")) {
                            BigDecimal qty = new BigDecimal(eSPInvoiceBDPo.getInvoiceNumber().split("_")[1]);
                            qty = qty.add(new BigDecimal(1));
                            StringBuffer sb = new StringBuffer(eSPInvoiceBDPo.getInvoiceNumber().split("_")[0]);
                            sb = sb.append("_").append(qty);
                            NewESPInvoiceBDPo.setInvoiceNumber(sb.toString());
                        } else {
                            StringBuffer sb = new StringBuffer(eSPInvoiceBDPo.getInvoiceNumber());
                            sb = sb.append("_").append(1);
                            NewESPInvoiceBDPo.setInvoiceNumber(sb.toString());
                        }
                        bdpoDao.save(NewESPInvoiceBDPo);
                    }

                    ESPInvoicePercentage esp = ipDao.findByInvoice(eSPReceiving.getInvoiceInvoicenumber());
                    if (esp != null) {
                        ESPInvoicePercentage NewEsp = new ESPInvoicePercentage();
                        NewEsp.setCommPercentage(esp.getCommPercentage());
                        NewEsp.setDutyPercentage(esp.getDutyPercentage());
                        NewEsp.setMiscFeePercentage(esp.getMiscFeePercentage());
                        NewEsp.setPo(esp.getPo());
                        NewEsp.setVersion(new Date());
                        if (esp.getInvoiceNum().contains("_")) {
                            BigDecimal qty = new BigDecimal(esp.getInvoiceNum().split("_")[1]);
                            qty = qty.add(new BigDecimal(1));
                            StringBuffer sb = new StringBuffer(esp.getInvoiceNum().split("_")[0]);
                            sb = sb.append("_").append(qty);
                            NewEsp.setInvoiceNum(sb.toString());
                        } else {
                            StringBuffer sb = new StringBuffer(esp.getInvoiceNum());
                            sb = sb.append("_").append(1);
                            NewEsp.setInvoiceNum(sb.toString());
                        }
                        ipDao.save(NewEsp);
                    }
                }
            }
            // 更新状态
            espReceivingDao.updateReceivingDataToFinish(header.getSequenceNumber());
        }
    }

    @Override
    public void saveEspritOrderData(EspritOrderRootXml root, File sourceFile, String backupPath) throws IOException {
        log.info("esprit save po data start");
        BigDecimal seq = espOrderDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        for (EspritOrderXml orderXml : root.getOrders().getOrder()) {
            for (EspritItemXml item : orderXml.getOrderDetails().getItem()) {
                ESPOrder order = new ESPOrder();
                order.setBatchID(seq);
                order.setHeaderFromGLN(root.getHeader().getFromGLN());
                order.setHeaderFromNode(root.getHeader().getFromNode());
                order.setHeaderGenerationDate(root.getHeader().getGenerationDate());
                order.setHeaderGenerationTime(root.getHeader().getGenerationTime());
                order.setHeaderNumberOfrecords(root.getHeader().getNumberOfRecords());
                order.setHeaderSequenceNumber(root.getHeader().getSequenceNumber());
                order.setHeaderToGLN(root.getHeader().getToGLN());
                order.setHeaderToNode(root.getHeader().getToNode());
                order.setOdBuyingSeasonCode(orderXml.getBuyingSeasonCode());
                order.setOdBuyingSeasonYear(orderXml.getBuyingSeasonYear());
                order.setOdCountryoforigin(orderXml.getCountryOfOrigin());
                order.setOdCurrency(orderXml.getCurrency());
                order.setOdExfactoryDate(orderXml.getExFactoryDate());
                order.setOdExpectedDeliveryDate(orderXml.getExpectedDeliveryDate());
                order.setOdFactory(orderXml.getFactory());
                order.setOdFobinCurrency(item.getFobInCurrency());
                order.setOdFromNodeGLN(orderXml.getFromNodeGLN());
                order.setOdGlobalTransferPrice(item.getGlobalTransferPrice());
                order.setOdOrderQty(item.getOrderQty());
                order.setOdPO(orderXml.getPo());
                order.setOdPoreference(orderXml.getPoReference());
                order.setOdPortoFloading(orderXml.getPortOfLoading());
                order.setOdShippingMethod(orderXml.getShippingMethod());
                order.setOdSku(item.getSku());
                order.setOdStatusineDifile(orderXml.getStatusInEDIFile());
                order.setOdStyle(orderXml.getStyle());
                order.setOdSupplier(orderXml.getSupplier());
                order.setOdToNodeGLN(orderXml.getToNodeGLN());
                order.setStatus(1);
                espOrderDao.save(order);
            }
        }
        try {
            FileUtils.moveToDirectory(sourceFile, new File(backupPath + "/Order"), true);
        } catch (Exception e) {
            log.equals("esprit move file error");
            throw new BusinessException("esprit move file error");
        }

    }

    @Override
    @Transactional
    public List<String> getNotOperaterSeqNum() {
        return espOrderDao.findNotOperateOrderSeqNum(new SingleColumnRowMapper<String>());
    }

    @Override
    @Transactional
    public List<ESPOrderCommand> getOrdersGroupBySeqNumAndPO(String status) {
        return espOrderDao.findOrdersGroupBySeqNumAndPO(status, new BeanPropertyRowMapperExt<ESPOrderCommand>(ESPOrderCommand.class));
    }

    @Override
    @Transactional
    public List<ESPOrder> findOrdersBySeqNumAndPO(String seqNum, String status, String po) {
        return espOrderDao.findOrdersBySeqNumAndPO(seqNum, status, po);
    }

    @Override
    @Transactional
    public void updateOrderStatus(String seqNum, Integer staStatus, String po, String ordStatus) {
        espOrderDao.updateOrderStatus(staStatus, seqNum, po, ordStatus);
    }

    @Override
    @Transactional
    public List<Long> findStaIdNotGenerateTransfer() {
        return transferOrderDao.findStaIdNotGenerateTransfer(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    @Transactional
    public List<Long> findInvIdNotGenerateTransfer() {
        return transferOrderDao.findInvIdNotGenerateTransfer(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    @Transactional
    public String getTONodeByStaID(String staId) {
        return transferOrderDao.getTONodeByStaID(staId.toString(), new SingleColumnRowMapper<String>());
    }

    @Override
    @Transactional
    public String getTONodeByInvID(String invId) {
        return transferOrderDao.getTONodeByInvID(invId.toString(), new SingleColumnRowMapper<String>());
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findTransferOrdersByStaId(String staId) {
        return transferOrderDao.findTransferOrdersByStaId(staId);
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findTransferOrdersByInvId(String invId) {
        return transferOrderDao.findTransferOrdersByInvId(invId);
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findOrdersByStaIdEndWithO(Long staId) {
        return transferOrderDao.findOrdersByStaIdEndWithO(staId);
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findOrdersByStaIdNotEndWithO(Long staId) {
        return transferOrderDao.findOrdersByStaIdNotEndWithO(staId);
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findOrdersByInvIdEndWithO(Long invId) {
        return transferOrderDao.findOrdersByInvIdEndWithO(invId);
    }

    @Override
    @Transactional
    public List<ESPTransferOrder> findOrdersByInvIdNotEndWithO(Long invId) {
        return transferOrderDao.findOrdersByInvIdNotEndWithO(invId);
    }

    /**
     * 创建转仓STA-------->改成转店
     * 
     * @param espOrderList
     */
    @Override
    @Transactional
    public void generateTransferWHSTAFromESPOrder(List<ESPTransferOrder> espOrderList, String toWHVMICode, String sourceVMICode) {

        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // 订单状态与账号关联
        sta.setIsNeedOccupied(true);
        OperationUnit ou = null;
        BiChannel shop = shopDao.getByVmiCode(toWHVMICode);
        BiChannel sourceShop = shopDao.getByVmiCode(sourceVMICode);
        if (shop == null || sourceShop == null) {
            if (shop == null) {
                log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {toWHVMICode});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            if (sourceShop == null) {
                log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {sourceVMICode});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        wmExe.validateBiChannelSupport(null, shop.getCode());
        if (shop.getId().equals(sourceShop.getId())) {
            log.debug("=========Transfer WH ERRER: shop {} and sourceShop {} same===========", new Object[] {toWHVMICode, sourceVMICode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toWHVMICode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        Long companyId = null;
        if (ou.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus is = null;
        if (companyId != null) {
            is = inventoryStatusDao.findInvStatusForSale(companyId);
        }
        sta.setMainWarehouse(ou);// 源仓库
        sta.setOwner(sourceShop.getCode()); // 源店铺
        sta.setAddiOwner(shop.getCode()); // 目标店铺
        sta.setMainStatus(is);
        sta.setAddiStatus(is);
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        Long skuQty = 0l;
        Date d = new Date();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        for (ESPTransferOrder order : espOrderList) {
            Sku sku = skuDao.getByBarcode(order.getOdSku(), customerId);
            if (sku == null) {
                log.debug("===============SKU {} NOT FOUND ================", new Object[] {order.getOdSku()});
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            StaLine staLine = new StaLine();
            Long qty = Long.parseLong(order.getOdOrderQty());
            staLine.setQuantity(qty);
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            staLine.setOwner(sta.getOwner());
            staLine.setInvStatus(is);
            staLine = staLineDao.save(staLine);
            skuQty += qty;
            order.setTransferWHSta(sta);
            order.setTransferWHStaLine(staLine);
            order.setModifyTime(d);
            transferOrderDao.save(order);
        }
        sta.setSkuQty(skuQty);
        staDao.save(sta);
        staDao.flush();
        Long staId = sta.getId();
        if (staId != null) {
            // 库存占用
            whManager.createTransferByStaId(staId, null);
        } else {
            throw new BusinessException(ErrorCode.BETWENLIBARY_STA_CREATE_ERROR);
        }
        log.debug("create trans cross sta line end : {}", sta.getCode());
    }

    @Override
    @Transactional
    public void generateChangeInboundSta(ESPOrderCommand espComd, String toLocation) {
        String po = espComd.getOdPO();
        if (po == null) {
            throw new BusinessException(ErrorCode.VMI_INBOUND_PO_NO_IS_NULL);
        }
        StockTransApplication oldSta = staDao.findStaBySlipCode(po);

        if (oldSta != null) {
            if (oldSta.getStatus().equals(StockTransApplicationStatus.FROZEN) || oldSta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                oldSta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(oldSta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, oldSta.getMainWarehouse() == null ? null : oldSta.getMainWarehouse().getId());
                oldSta.setCancelTime(new Date());
                staDao.save(oldSta);
                generateSTAFromESPOrder(espComd, toLocation);
                staDao.flush();
                updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FINISHED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
            } else {
                updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FAILED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
            }
        } else {
            generateSTAFromESPOrder(espComd, toLocation);
            staDao.flush();
            updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FINISHED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
        }
    }

    @Override
    @Transactional
    public void generateNewInboungSta(ESPOrderCommand espComd, String toLocation) {
        String po = espComd.getOdPO();
        if (po == null) {
            throw new BusinessException(ErrorCode.VMI_INBOUND_PO_NO_IS_NULL);
        }
        StockTransApplication sta = staDao.findStaBySlipCode(po);
        if (sta == null) {
            generateSTAFromESPOrder(espComd, Constants.ESPRIT_VIM_CODE);
            staDao.flush();
            updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FINISHED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
        } else {
            // order的状态改为10
            updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FINISHED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
        }
    }

    @Override
    @Transactional
    public void generetaCancelInboundSta(ESPOrderCommand espComd) {
        String po = espComd.getOdPO();
        if (po == null) {
            throw new BusinessException(ErrorCode.VMI_INBOUND_PO_NO_IS_NULL);
        }
        StockTransApplication sta = staDao.findStaBySlipCode(espComd.getOdPO());
        if (sta != null) {
            if (sta.getStatus().equals(StockTransApplicationStatus.FROZEN) || sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                sta.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setLastModifyTime(new Date());
                sta.setCancelTime(new Date());
                staDao.save(sta);
                staDao.flush();
            }
        }
        // order的状态改为10
        updateOrderStatus(espComd.getHeaderSequenceNumber(), EspritOrderStatus.FINISHED.getValue(), espComd.getOdPO(), espComd.getOdStatusineDifile());
    }

    /**
     * 创入库单
     */
    @Override
    @Transactional
    public void generateSTAFromESPOrder(ESPOrderCommand espComd, String toLocation) {
        List<ESPOrder> orderList = findOrdersBySeqNumAndPO(espComd.getHeaderSequenceNumber(), espComd.getOdStatusineDifile(), espComd.getOdPO());
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        // 订单状态与账号关联
        BiChannel shop = shopDao.getByVmiCode(toLocation);
        if (shop == null) {
            log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {toLocation});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        Long companyId = null;
        OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());

        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {toLocation});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        if (ou.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus is = inventoryStatusDao.findInvStatusForSale(companyId);
        sta.setMainStatus(is);
        sta.setOwner(shop.getCode());
        sta.setMainWarehouse(ou);
        whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        String po = "";
        sta = staDao.save(sta);
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        boolean isNoSkuError = false;
        for (ESPOrder order : orderList) {
            if (order.getOdSku() == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            Sku sku = skuDao.getByBarcode(order.getOdSku(), customerId);
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(order.getOdSku(), shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            Long qty = Long.parseLong(order.getOdOrderQty());
            staLine.setQuantity(qty);
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            staLine.setInvStatus(is);
            staLine.setOwner(sta.getOwner());
            staLine = staLineDao.save(staLine);
            order.setStaLine(staLine);
            order.setSta(sta);
            espOrderDao.save(order);
            po = order.getOdPO();
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setRefSlipCode(po);
        staDao.save(sta);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
    }

    @Override
    public Pagination<ESPPoTypeCommand> findESPPoTypeList(int start, int pageSize, ESPPoTypeCommand ptCommand, Sort[] sorts) {
        if (ptCommand != null) {
            ptCommand.setQueryLikeParam();
        } else {
            ptCommand = new ESPPoTypeCommand();
        }
        return poTypeDao.findPoTypeList(start, pageSize, ptCommand.getStartTime(), ptCommand.getEndTime(), ptCommand.getPo(), ptCommand.getTypeName(), sorts, new BeanPropertyRowMapper<ESPPoTypeCommand>(ESPPoTypeCommand.class));
    }

    @Override
    public Pagination<ESPInvoicePercentage> findInvoicePertentage(int start, int pageSize, ESPInvoicePercentageCommand ipCommand, Sort[] sorts) {
        if (ipCommand != null) {
            ipCommand.setQueryLikeParam();
        } else {
            ipCommand = new ESPInvoicePercentageCommand();
        }
        return ipDao.findInvoicePercentList(start, pageSize, ipCommand.getStartTime(), ipCommand.getEndTime(), ipCommand.getInvoiceNum(), ipCommand.getDutyPercentage(), ipCommand.getMiscFeePercentage(), ipCommand.getCommPercentage(), sorts);
    }

    @Override
    public JSONObject findPoTypeByPo(JSONObject json, String po) throws Exception {
        ESPPoType type = poTypeDao.findByPo(po);
        if (type == null) {
            throw new BusinessException(ErrorCode.ESP_PO_NOT_TYPE, new Object[] {po});
        }
        if (EspritOrderPOType.IMPORT.equals(type.getType())) {
            json.put("TYPE", type.getType().getValue());
            List<ESPInvoicePercentage> list = ipDao.findESPInvoiceByPo(po, new BeanPropertyRowMapperExt<ESPInvoicePercentage>(ESPInvoicePercentage.class));
            if (list == null || list.size() == 0) {
                throw new BusinessException(ErrorCode.ESP_PO_NOT_INV, new Object[] {po});
            }
            json.put("invData", JsonUtil.collection2json(list));
        } else {
            if (type.getInvoiceNumber() == null || "".equals(type.getInvoiceNumber())) {
                throw new BusinessException(ErrorCode.ESP_PO_NOT_TYPE_INV, new Object[] {po});
            }
            json.put("TYPE", type.getType().getValue());
            json.put("invData", JsonUtil.obj2json(type));
        }
        return json;
    }

    @Override
    @Transactional
    public void espPoAndInvoiceBD(String poCode, String invoiceNum) {
        ESPPoType po = poTypeDao.findByPo(poCode);
        if (po == null) {
            throw new BusinessException(ErrorCode.ESP_PO_IS_NOT_NULL, new Object[] {poCode});
        } else if (!EspritOrderPOType.IMPORT.equals(po.getType())) {
            throw new BusinessException(ErrorCode.ESP_PO_TYPE_ERROR, new Object[] {poCode});
        }
        ESPInvoicePercentage invnum = ipDao.findByInvoice(invoiceNum);
        if (invnum == null) {
            throw new BusinessException(ErrorCode.ESP_PO_INV_IS_NOT_NULL, new Object[] {invoiceNum});
        }
        if (bdpoDao.findByInvoiceAndPo(poCode, invoiceNum) == null) {
            ESPInvoiceBDPo bean = new ESPInvoiceBDPo();
            bean.setCreateTime(new Date());
            bean.setPo(poCode);
            bean.setInvoiceNumber(invoiceNum);
            bdpoDao.save(bean);
        }
    }

    @Override
    public Pagination<ESPInvoiceBDPo> findESPPoInvoiceBD(int start, int size, String po, String invoiceNum, Sort[] sorts) {
        if (po != null && "".equals(po)) {
            po = null;
        }
        if (invoiceNum != null && "".equals(invoiceNum)) {
            invoiceNum = null;
        }
        return bdpoDao.findESPPoInvoiceBD(start, size, po, invoiceNum, sorts, new BeanPropertyRowMapperExt<ESPInvoiceBDPo>(ESPInvoiceBDPo.class));
    }

    @Override
    public ESPInvoicePercentage findIP(String invoiceNum) {
        ESPInvoicePercentage sep = ipDao.findByInvoice(invoiceNum);
        ESPInvoicePercentage ecmd = new ESPInvoicePercentage();
        BeanUtils.copyProperties(sep, ecmd);
        return ecmd;
    }

    /**
     * 大仓收货未反馈邮件通知
     */
    @Override
    public void receivedNotUploadEmailInform() {
        Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(ESPRIT_RECEIVE_NOTICE);
        MailTemplate template = mailTemplateDao.findTemplateByCode(ESPRIT_RECEIVE_NOTICE);
        String receiver = "";
        String carboncopy = "";
        String subject = "";
        String mailBody = "";
        if (null != option) {
            receiver = option.get("RECEIVER");
            carboncopy = option.get("CARBONCOPY");
        }
        if (null != template) {
            subject = template.getSubject();
            mailBody = template.getMailBody();
        }
        if ("".equals(receiver) || "".equals(subject) || "".equals(mailBody)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        List<ESPReceiving> recList = espReceivingDao.findAllNotFeedbackReceivingData();
        if (null != recList && recList.size() > 0) {
            sb.append(mailBody);
            for (ESPReceiving rec : recList) {
                String order = rec.getReceivingOrdernumber();
                if (!StringUtil.isEmpty(order)) sb.append("\n").append(order);
            }
            MailUtil.sendMail(subject, receiver, carboncopy, sb.toString(), false, null);
        }
    }


    @SuppressWarnings("null")
    @Override
    public void saveToTable(File file) {
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String reg = "\"([^\"]*)\"";
        FileInputStream fileInputStream = null;
        TransactionType t = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_OTHERS_INBOUND);
        int k = 0;
        int j = 0;
        String key = null;
        BiChannel biChannel = null;
        OperationUnit operationUnit = null;
        WarehouseDistrict warehouseDistrict = null;
        Sku sku = null;
        Map<String, Sku> skuMap = new HashMap<String, Sku>();
        Map<String, BiChannel> biChannelMap = new HashMap<String, BiChannel>();
        Map<Long, InventoryStatus> inventoryStatusTrueMap = new HashMap<Long, InventoryStatus>();
        Map<Long, InventoryStatus> inventoryStatusFalseMap = new HashMap<Long, InventoryStatus>();
        List<WarehouseLocation> locationList = new ArrayList<WarehouseLocation>();
        String inString = null;
        String batchCode = null;
        Long ouId = null;
        String[] s;
        Map<String, List<WarehouseLocation>> map = new HashMap<String, List<WarehouseLocation>>();
        List<WarehouseLocation> warehouseLocationList = new ArrayList<WarehouseLocation>();
        List<String> inStringList = new ArrayList<String>();
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((inString = reader.readLine()) != null) {
                inStringList.add(inString);
            }
            if (null != inStringList && inStringList.size() > 0) {
                inStringList.remove(0);
            }
            List<InventoryTest> inventoryTestList = new ArrayList<InventoryTest>();
            for (String list : inStringList) {
                if (null == list || "".equals(list)) {
                    continue;
                }
                InventoryTest inventoryTest = new InventoryTest();
                s = list.split(",");
                inventoryTest.setInnerShopCode(s[0].replaceAll(reg, "$1"));
                inventoryTest.setWarehouseCode(s[1].replaceAll(reg, "$1"));
                inventoryTest.setSkuCode(s[2].replaceAll(reg, "$1"));
                inventoryTest.setPartion(s[3].replaceAll(reg, "$1"));
                inventoryTest.setQty(Long.parseLong(s[4].replaceAll(reg, "$1")));
                inventoryTest.setWarehouseStatus(Integer.parseInt(s[5].replaceAll(reg, "$1")));
                inventoryTestList.add(inventoryTest);
                if (null != map) {
                    if (!map.containsKey(inventoryTest.getWarehouseCode() + "," + inventoryTest.getPartion())) {
                        operationUnit = operationUnitDao.getByCode(inventoryTest.getWarehouseCode());
                        warehouseDistrict = warehouseDistrictDao.findDistrictByCodeAndOu(inventoryTest.getPartion(), operationUnit.getId());
                        warehouseLocationList = warehouseLocationDao.findLocationByDiId(warehouseDistrict.getId(), operationUnit.getId(), new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));
                        map.put(inventoryTest.getWarehouseCode() + "," + inventoryTest.getPartion(), warehouseLocationList);
                    }
                } else {
                    operationUnit = operationUnitDao.getByCode(inventoryTest.getWarehouseCode());
                    warehouseDistrict = warehouseDistrictDao.findDistrictByCodeAndOu(inventoryTest.getPartion(), operationUnit.getId());
                    warehouseLocationList = warehouseLocationDao.findLocationByDiId(warehouseDistrict.getId(), operationUnit.getId(), new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));
                    map.put(inventoryTest.getWarehouseCode() + "," + inventoryTest.getPartion(), warehouseLocationList);
                }
                if (null != skuMap) {
                    if (!skuMap.containsKey(inventoryTest.getSkuCode())) {
                        sku = skuDao.getByCode(inventoryTest.getSkuCode());
                        skuMap.put(inventoryTest.getSkuCode(), sku);
                    }
                } else {
                    sku = skuDao.getByCode(inventoryTest.getSkuCode());
                    skuMap.put(inventoryTest.getSkuCode(), sku);
                }
                if (null != biChannelMap) {
                    if (!biChannelMap.containsKey(inventoryTest.getInnerShopCode())) {
                        biChannel = biChannelDao.getByCode(inventoryTest.getInnerShopCode());
                        biChannelMap.put(inventoryTest.getInnerShopCode(), biChannel);
                    }
                } else {
                    biChannel = biChannelDao.getByCode(inventoryTest.getInnerShopCode());
                    biChannelMap.put(inventoryTest.getInnerShopCode(), biChannel);
                }
                if (1 == inventoryTest.getWarehouseStatus()) {
                    if (null != inventoryStatusTrueMap) {
                        if (!inventoryStatusTrueMap.containsKey(operationUnit.getId())) {
                            ouId = operationUnitDao.getByPrimaryKey(operationUnitDao.getByPrimaryKey(operationUnit.getId()).getParentUnit().getId()).getParentUnit().getId();
                            inventoryStatusTrueMap.put(operationUnit.getId(), inventoryStatusDao.findInvStatusisForSale(ouId, true));
                        }
                    } else {
                        ouId = operationUnitDao.getByPrimaryKey(operationUnitDao.getByPrimaryKey(operationUnit.getId()).getParentUnit().getId()).getParentUnit().getId();
                        inventoryStatusTrueMap.put(operationUnit.getId(), inventoryStatusDao.findInvStatusisForSale(ouId, true));
                    }
                } else {
                    if (null != inventoryStatusFalseMap) {
                        if (!inventoryStatusFalseMap.containsKey(operationUnit.getId())) {
                            ouId = operationUnitDao.getByPrimaryKey(operationUnitDao.getByPrimaryKey(operationUnit.getId()).getParentUnit().getId()).getParentUnit().getId();
                            inventoryStatusFalseMap.put(operationUnit.getId(), inventoryStatusDao.findInvStatusisForSale(ouId, false));
                        }
                    } else {
                        ouId = operationUnitDao.getByPrimaryKey(operationUnitDao.getByPrimaryKey(operationUnit.getId()).getParentUnit().getId()).getParentUnit().getId();
                        inventoryStatusFalseMap.put(operationUnit.getId(), inventoryStatusDao.findInvStatusisForSale(ouId, false));
                    }
                }
            }
            if (null != inventoryTestList && inventoryTestList.size() > 0) {
                Collections.sort(inventoryTestList);
                for (int i = 0; i < inventoryTestList.size(); i++) {
                    if (null != key && !"".equals(key)) {
                        if (!key.equals(inventoryTestList.get(i).getWarehouseCode() + "," + inventoryTestList.get(i).getPartion())) {
                            key = inventoryTestList.get(i).getWarehouseCode() + "," + inventoryTestList.get(i).getPartion();
                            j = 0;
                        }
                    } else {
                        key = inventoryTestList.get(i).getWarehouseCode() + "," + inventoryTestList.get(i).getPartion();
                    }
                    if (map.containsKey(inventoryTestList.get(i).getWarehouseCode() + "," + inventoryTestList.get(i).getPartion())) {
                        locationList = map.get(inventoryTestList.get(i).getWarehouseCode() + "," + inventoryTestList.get(i).getPartion());
                        k = j % (locationList.size());
                        Inventory inventory = new Inventory();
                        batchCode = Long.valueOf(new Date().getTime()).toString();
                        inventory.setBatchCode(batchCode);
                        inventory.setDistrict(locationList.get(k).getDistrict());
                        inventory.setInboundTime(new Date());
                        inventory.setLocation(locationList.get(k));
                        inventory.setOu(locationList.get(k).getOu());
                        inventory.setOwner(biChannelMap.get(inventoryTestList.get(i).getInnerShopCode()).getCode());
                        inventory.setQuantity(inventoryTestList.get(i).getQty());
                        inventory.setSku(skuMap.get(inventoryTestList.get(i).getSkuCode()));
                        if (1 == inventoryTestList.get(i).getWarehouseStatus()) {
                            inventory.setStatus(inventoryStatusTrueMap.get(locationList.get(k).getOu().getId()));
                        } else {
                            inventory.setStatus(inventoryStatusFalseMap.get(locationList.get(k).getOu().getId()));
                        }
                        inventoryDao.save(inventory);
                        StockTransTxLog stockTransTxLog = new StockTransTxLog();
                        stockTransTxLog.setDirection(TransactionDirection.INBOUND);
                        stockTransTxLog.setBatchCode(batchCode);
                        stockTransTxLog.setDistrictId(locationList.get(k).getDistrict().getId());
                        stockTransTxLog.setInvStatusId(inventory.getStatus().getId());
                        stockTransTxLog.setLocationId(locationList.get(k).getId());
                        stockTransTxLog.setOwner(biChannelMap.get(inventoryTestList.get(i).getInnerShopCode()).getCode());
                        stockTransTxLog.setQuantity(inventoryTestList.get(i).getQty());
                        stockTransTxLog.setSkuId(skuMap.get(inventoryTestList.get(i).getSkuCode()).getId());
                        stockTransTxLog.setTransactionTime(new Date());
                        stockTransTxLog.setWarehouseOuId(locationList.get(k).getOu().getId());
                        stockTransTxLog.setTransactionType(t);
                        stockTransTxLogDao.save(stockTransTxLog);
                    }
                    j++;
                }
            }
        } catch (Exception e) {
            log.error("saveToTable1", e);
        } finally {
            try {
                inputStreamReader.close();
            } catch (Exception e1) {
                inputStreamReader = null;
            }
            try {
                reader.close();
            } catch (Exception e1) {
                reader = null;
            }
            try {
                fileInputStream.close();
            } catch (Exception e1) {
                fileInputStream = null;
            }
        }
        try {
            skuMap.clear();
            biChannelMap.clear();
            inventoryStatusFalseMap.clear();
            inventoryStatusTrueMap.clear();
        } catch (Exception e) {
            log.equals("saveToTable move file error");
            throw new BusinessException("saveToTable move file error");
        }
    }

    public static void main(String[] args) {
        String str = "dfdf";
        String reg = "\"([^\"]*)\"";
        str = str.replaceAll(reg, "$1");
        System.out.println(str);
    }
}
