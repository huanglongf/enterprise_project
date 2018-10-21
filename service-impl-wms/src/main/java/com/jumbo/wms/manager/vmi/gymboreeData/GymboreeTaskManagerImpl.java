package com.jumbo.wms.manager.vmi.gymboreeData;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransLpcodeWhRefDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveDataDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveDataLineDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveRtnDataDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeRtnOutDataDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeWarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.itochuData.ItochuManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.TransLpcodeWhRef;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveDataLine;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveRtnData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeRtnOutData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeWarehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustmentLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import cn.baozun.bh.connector.ei.model.CancelBody;
import cn.baozun.bh.connector.ei.model.DeliverReturns;
import cn.baozun.bh.connector.ei.model.DeliverReturns.DeliverReturn;
import cn.baozun.bh.connector.ei.model.Delivers;
import cn.baozun.bh.connector.ei.model.Delivers.Deliver;
import cn.baozun.bh.connector.ei.model.OtherInOutstockBody;
import cn.baozun.bh.connector.ei.model.OutstockBody;
import cn.baozun.bh.connector.ei.model.PerformanceCancelBody;
import cn.baozun.bh.connector.ei.model.PerformanceReturnBody;
import cn.baozun.bh.connector.ei.model.PerformanceReturnHead;
import cn.baozun.bh.connector.ei.model.ShipBody;
import cn.baozun.bh.connector.ei.model.ShipHead;
import cn.baozun.bh.connector.gymboree.constants.ConstantsGymboree;
import cn.baozun.bh.connector.gymboree.inoutvouch.model.InOutVouch;
import cn.baozun.bh.connector.gymboree.inoutvouch.model.InOutVouchDetail;
import cn.baozun.bh.connector.gymboree.inoutvouch.model.Row;
import cn.baozun.bh.connector.gymboree.stockoutvouch.model.Detail;
import cn.baozun.bh.connector.gymboree.stockoutvouch.model.Main;
import cn.baozun.bh.connector.gymboree.stockoutvouch.model.NewDataSet;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;
import loxia.support.jasperreport.ClasspathJasperPrinter;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 金宝贝业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("gymboreeTaskManager")
public class GymboreeTaskManagerImpl extends BaseManagerImpl implements GymboreeTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = -5240238786302720040L;
    private static final String COMPANY = "GYMBOREE";
    private static final String DEFAULTCOUNTRY = "CN";
    private static final String DEFAULTZIPCODE = "100000";
    private static final String PROCCESSTYPE = "0";
    private static final String ACCOCATECOMPLETE = "Y";
    private static final String ORDERTYPESALSE = "SALE_OUT";
    private static final String ORDERTYPERTNSALSE = "RMA_IN";
    private static final String ORDERTYPEIN = "VARIANCE_IN";
    private static final String ORDERTYPEOUT = "VARIANCE_OUT";
    private static final String INVA = "INV_STTS_AVAILABLE";
    private static final String INVB = "INV_STTS_USED";
    private static final String GYMBOREE_EMAIL = "GYMBOREE_EMAIL";
    private EventObserver eventObserver;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private GymboreeRtnOutDataDao gymboreeRtnOutDataDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private MsgRtnAdjustmentLineDao msgRtnAdjustmentLineDao;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private GymboreeWarehouseDao gymboreeWarehouseDao;
    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransLpcodeWhRefDao transLpcodeWhRefDao;
    @Autowired
    private GymboreeReceiveDataDao gymboreeReceiveDataDao;
    @Autowired
    private GymboreeReceiveDataLineDao gymboreeReceiveDataLineDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private GymboreeReceiveRtnDataDao gymboreeReceiveRtnDataDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private ItochuManager itochuManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ExcelReadManager excelReadManager;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate Class:GymboreeTaskManagerImpl");
        }
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

    private JasperPrint generateGymborePackingPage(Long staId) {
        String mainTemplate = "jasperprint/trunk_main_for_gymboree.jasper";
        String subTemplate = "jasperprint/trunk_detail_for_gymboree.jasper";
        List<PackingObj> dataList = new ArrayList<PackingObj>();
        Map<Long, PackingObj> tmpList = channelManager.findPackingPageNoLocation(null, staId);
        if (tmpList != null) {
            dataList.addAll(tmpList.values());
        }
        if (dataList == null || dataList.isEmpty()) {
            dataList.add(new PackingObj());
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subReport", subTemplate);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(mainTemplate);
        try {
            cjp.initializeReport(map, dataSource);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        }
        try {
            return cjp.print();
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 零售单指示
     * 
     * @throws Exception
     */
    @Override
    public void outboundNotice(String flag) throws Exception {
        List<MsgOutboundOrder> orders = msgOutboundOrderDao.findVmiOutboundNeedSendHaveType(Constants.VMI_WH_SOURCE_GYMBOREE, flag);
        if (orders.size() > 0) {
            Map<String, String> cfg = configManager.getCommonFTPConfig(flag.equals(Constants.GYM_SC) ? Constants.CONFIG_GROUP_GYMBOREE_FTP : Constants.CONFIG_GROUP_GYMSC_FTP);
            String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
            Delivers ds = new Delivers();
            List<Deliver> list = new ArrayList<Deliver>();
            for (MsgOutboundOrder order : orders) {
                MsgOutboundOrder od = msgOutboundOrderDao.getByPrimaryKey(order.getId());
                StockTransApplication sta = staDao.getByCode(od.getStaCode());
                JasperPrint jp = generateGymborePackingPage(sta.getId());
                try {
                    JasperExportManager.exportReportToPdfFile(jp, localDir + sta.getCode() + ".pdf");
                } catch (Exception e) {
                    continue;
                }
                Deliver de = new Deliver();
                de.setHead(true);
                // de.setWarehouse("1201");
                de.setWarehouse(flag);// Edit by KJL 2015-12-21
                de.setInterfaceRecordID(od.getStaCode());
                de.setShipmentID(od.getStaCode());
                de.setCompany(COMPANY);
                de.setOwner(flag);// Edit by KJL 2015-12-21
                de.setCustomerName(od.getReceiver());
                de.setShipToName(od.getReceiver());
                de.setShipToCountry(DEFAULTCOUNTRY);
                de.setShipToState(od.getProvince());
                de.setShipToCity(od.getCity());
                de.setCustomerAddress3(od.getDistrict());
                de.setShipToPostalCode(od.getZipcode() == null ? DEFAULTZIPCODE : od.getZipcode());
                de.setShipToAddress1(StringUtil.getRealAddress(od.getAddress(), od.getProvince(), od.getCity(), od.getDistrict(), true));
                de.setCustomerPhoneNum(od.getTotalActual().toString());
                de.setCustomerFaxNum(od.getTransferFee().toString());
                TransLpcodeWhRef tlr = transLpcodeWhRefDao.getWhCarrierByLpCodeAndSource(od.getLpCode(), od.getSource());
                de.setCarrier(tlr.getCarrier());
                de.setCarrierServices(tlr.getCarrierService());
                de.setShipToPhoneNum(od.getTelePhone() == null ? "" : od.getTelePhone());
                de.setShipToFaxNum(od.getMobile() == null ? "" : od.getMobile());
                de.setProcessType(PROCCESSTYPE);
                de.setAllocateComplete(ACCOCATECOMPLETE);
                de.setOrderType(ORDERTYPESALSE);
                de.setUserDef5(sta.getSlipCode2());
                de.setShhdrdnnew("SHHDRDNNEW");
                list.add(de);
                List<MsgOutboundOrderLine> ll = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(od.getId());
                for (MsgOutboundOrderLine line : ll) {
                    Deliver db = new Deliver();
                    db.setShdtldnnew("SHDTLDNNEW");
                    db.setHead(false);
                    db.setInterfaceLinkId(od.getStaCode());
                    db.setInterfaceRecordId(line.getId() + "");
                    db.setErpOrderLineNum(line.getId() + "");
                    db.setCompany(COMPANY);
                    db.setItem(line.getSku().getBarCode());
                    db.setItemListPrice(line.getUnitPrice().toString());
                    db.setGoodsName(line.getSku().getName());
                    db.setStyleName(line.getSku().getSkuSize());
                    db.setTotalQty(line.getQty() + "");
                    db.setUserDef3(line.getId() + "");
                    list.add(db);
                }
                od.setStatus(DefaultStatus.FINISHED);
                od.setUpdateTime(new Date());
            }
            ds.setDelivers(list);
            try {
                SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH), cfg.get(Constants.VMI_FTP_UP_LOCALPATH),
                        cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
            } catch (Exception e) {
                log.error("", e);
                throw e;
            }
            String msg = JSONUtil.beanToJson(ds);
            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
            connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_DELIVER);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            String mqMsg = JSONUtil.beanToJson(connectorMessage);
            // 发送mq消息
            if (flag.equals(Constants.GYM_SC)) {
                MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_deliver", mqMsg);
            } else {
                MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_deliver_tmall", mqMsg);// 待定
            }
        }
    }

    @Override
    public void cancelOutbound(MsgOutboundOrderCancel cl) {
        CancelBody cb = new CancelBody();
        cb.setReceiptId(cl.getStaCode());
        cb.setShhdrdnnew("SHHDRDNNEW");
        StockTransApplication sta = staDao.getByCode(cl.getStaCode());
        BiChannel c = shopDao.getByCode(sta.getOwner());
        String msg = JSONUtil.beanToJson(cb);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
        connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_CANCEL);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        // 发送mq消息
        if (c.getVmiCode().equals(Constants.GYM_SC)) {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_cancel", mqMsg);
        } else {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_cancel_tmall", mqMsg);
        }
        MsgOutboundOrderCancel moc = msgOutboundOrderCancelDao.getByPrimaryKey(cl.getId());
        moc.setStatus(MsgOutboundOrderCancelStatus.SENT);
        moc.setUpdateTime(new Date());
    }

    /**
     * 待确定问题，固定字段需要校验吗 单据类型，货主代码
     */
    @Override
    public void receiveOutboundMsg(String msg) throws Exception {
        try {
            Map<String, Object> map = JSONUtil.jsonToMap(msg, true);
            String tmp = map.get("shipHeads").toString();
            String detail = map.get("shipBodies").toString();
            JSONArray ja = JSONArray.fromObject(tmp);
            JSONArray jb = JSONArray.fromObject(detail);
            List<ShipHead> hl = new ArrayList<ShipHead>();
            List<ShipBody> dl = new ArrayList<ShipBody>();
            // ShipBody sb = new ShipBody();
            // ShipHead sh = new ShipHead();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                ShipHead ih = (ShipHead) JSONObject.toBean(ob, ShipHead.class);
                hl.add(ih);
            }
            for (int i = 0; i < jb.size(); i++) {
                JSONObject ob = (JSONObject) jb.get(i);
                ShipBody sb = (ShipBody) JSONObject.toBean(ob, ShipBody.class);
                dl.add(sb);
            }
            for (ShipHead head : hl) {
                MsgRtnOutbound oo = new MsgRtnOutbound();
                oo.setStaCode(head.getShipmentId());
                oo.setSourceWh(head.getWarehouse());
                oo.setSource(Constants.VMI_WH_SOURCE_GYMBOREE);
                oo.setStatus(DefaultStatus.CREATED);
                if (!head.getOrderType().equals(ORDERTYPESALSE)) {
                    log.error("reciverOutboundMsg orderType is not " + ORDERTYPESALSE);
                    throw new BusinessException("");
                }
                oo.setType(StockTransApplicationType.OUTBOUND_SALES.getValue() + "");
                oo.setTrackingNo(head.getUserDef2());
                oo.setTrackName(head.getCarrier());
                TransLpcodeWhRef trl = transLpcodeWhRefDao.getLpCodeByWhAndCarrier(Constants.VMI_WH_SOURCE_GYMBOREE, oo.getTrackName());
                oo.setLpCode(trl == null ? null : trl.getLpCode());
                oo.setCreateTime(new Date());
                msgRtnOutboundDao.save(oo);
                for (ShipBody body : dl) {
                    if (body.getShipmentId().equals(head.getShipmentId())) {
                        MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                        line.setMsgOutbound(oo);
                        line.setBarCode(body.getShdtlup());
                        line.setQty(Long.valueOf(body.getShippedQty()));
                        msgRtnOutboundLineDao.save(line);
                    }
                }

            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝零售单实绩Exception------------------------");
            throw e;
        }
    }

    /**
     * 每单存储
     */
    @Override
    public void receiveCancelMsg(String msg) throws Exception {
        try {
            Map<String, Object> map = JSONUtil.jsonToMap(msg, true);
            String tmp = map.get("performanceCancelBodies").toString();
            JSONArray ja = JSONArray.fromObject(tmp);
            List<PerformanceCancelBody> list = new ArrayList<PerformanceCancelBody>();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                PerformanceCancelBody ih = (PerformanceCancelBody) JSONObject.toBean(ob, PerformanceCancelBody.class);
                list.add(ih);
            }
            for (PerformanceCancelBody pb : list) {
                try {
                    MsgOutboundOrderCancel moc = msgOutboundOrderCancelDao.getByStaCode(pb.getReceiptId());
                    Boolean isCancel = null;
                    String remark = null;
                    String code = pb.getCode();
                    if (code.equals("T")) {
                        isCancel = true;
                        remark = "取消成功";
                    }
                    if (code.equals("F01")) {
                        isCancel = false;
                        remark = "取消失败（已发货）";
                    }
                    if (code.equals("F02")) {
                        isCancel = true;
                        remark = "取消失败（已取消）";
                    }
                    if (code.equals("F03")) {
                        isCancel = false;
                        remark = "取消失败（WMS中未找到）";
                    }
                    if (code.equals("F04")) {
                        isCancel = false;
                        remark = "取消失败（已关闭）";
                    }
                    moc.setIsCanceled(isCancel);
                    moc.setMsg(remark);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝取消实绩Exception-------------------");
            throw e;
        }
    }

    @Override
    public void createStaForInBound(GymboreeReceiveData rtn) {
        OperationUnit ou = null;
        String innerShopCode = null;
        BiChannel shop = null;
        Warehouse wh = null;
        if (wh == null) {
            wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
            if (wh == null) {
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            } else {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            }
        }
        if (shop == null) {
            /****** Edit by KJL 2015-12-21 ********************/
            shop = shopDao.getByVmiCode(rtn.getOwner() == null ? Constants.GYM_SC : rtn.getOwner());
            // List<BiChannel> shops = shopDao.getAllRefShopByWhOuId(wh.getOu().getId());
            // if (shops != null && shops.size() > 0) {
            // shop = shops.get(0);
            // }
            if (shop == null) {
                log.error("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        innerShopCode = shop.getCode();
        InventoryStatus is = inventoryStatusDao.findSalesAviliableStatus(ou.getId());
        if (is == null) {
            log.error("===========================invStatus is  null invStatus :{} ===============================", is);
            throw new BusinessException(ErrorCode.INVENTORY_STATUS_NOT_FOUND);
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setRefSlipCode(rtn.getFchrCode());
        sta.setCreateTime1(rtn.getFdtmDate());
        sta.setPackingUser(rtn.getFchrEmployeeID());
        sta.setPickingUser(rtn.getFchrMaker());
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
        sta.setMainStatus(is);
        sta.setFromLocation(rtn.getFchrWarehouseId());
        sta.setSlipCode1(rtn.getFchrInOutVouchID());
        staDao.save(sta);
        staDao.flush();

        MsgRtnInboundOrder rtnOrder = new MsgRtnInboundOrder();
        rtnOrder.setSource(Constants.VMI_WH_SOURCE_GYMBOREE);
        rtnOrder.setCreateTime(new Date());
        rtnOrder.setInboundTime(new Date());
        rtnOrder.setStaCode(sta.getCode());
        rtnOrder.setStatus(DefaultStatus.CREATED);
        rtnOrder.setSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
        msgRtnInboundOrderDao.save(rtnOrder);
        Long skuQty = 0l;
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        // GYMBOREE店存入库创单明细行数据合并
        List<GymboreeReceiveDataLine> dl = gymboreeReceiveDataLineDao.findLineListByDataId(rtn.getId());
        Map<String, GymboreeReceiveDataLine> grdlMap = new HashMap<String, GymboreeReceiveDataLine>();
        Map<String, Long> qMap = new HashMap<String, Long>();
        Map<String, BigDecimal> taMap = new HashMap<String, BigDecimal>();
        boolean isNoSkuError = false;
        for (GymboreeReceiveDataLine line : dl) {
            Long quantity = 0l;
            BigDecimal totalActual = null;
            String fchrBarCode = line.getFchrBarCode();
            if (grdlMap.containsKey(fchrBarCode)) {
                quantity = qMap.get(fchrBarCode) + line.getFlotQuantity();
                if (taMap.get(fchrBarCode) == null && line.getFlotMoney() == null) {
                    totalActual = null;
                } else if (taMap.get(fchrBarCode) != null && line.getFlotMoney() != null) {
                    totalActual = taMap.get(fchrBarCode).add(line.getFlotMoney());
                } else {
                    throw new BusinessException("Notice:The flot_money field must be empty or not empty(T_GYMBOREE_RECEIVE_DATA_LINE)");
                }
            } else {
                quantity = line.getFlotQuantity();
                totalActual = line.getFlotMoney();
            }
            qMap.put(fchrBarCode, quantity);
            taMap.put(fchrBarCode, totalActual);
            grdlMap.put(fchrBarCode, line);
        }

        for (Map.Entry<String, GymboreeReceiveDataLine> m : grdlMap.entrySet()) {
            String fchrBarCode = m.getKey();
            GymboreeReceiveDataLine line = m.getValue();
            Sku sku = whManager.getByExtCode2AndCustomerAndShopId(fchrBarCode, customerId, shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(fchrBarCode, shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(qMap.get(fchrBarCode));
            staLine.setUnitPrice(line.getFlotQuotePrice());
            staLine.setTotalActual(taMap.get(fchrBarCode));
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            skuQty += staLine.getQuantity();
            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(is);
            staLineDao.save(staLine);

            // 入库反馈中间表行
            MsgRtnInboundOrderLine rtnLine = new MsgRtnInboundOrderLine();
            rtnLine.setBarcode(staLine.getSku().getBarCode());
            rtnLine.setInvStatus(staLine.getInvStatus());
            rtnLine.setSkuCode(staLine.getSku().getCode());
            rtnLine.setSkuId(staLine.getSku().getId());
            rtnLine.setQty(staLine.getQuantity());
            rtnLine.setMsgRtnInOrder(rtnOrder);
            msgRtnInboundOrderLineDao.save(rtnLine);
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(skuQty);
        staDao.save(sta);
        GymboreeReceiveData rd = gymboreeReceiveDataDao.getByPrimaryKey(rtn.getId());
        rd.setStatus(DefaultStatus.FINISHED);
        rd.setStaId(sta.getId());
        rd.setFinishTime(new Date());
    }

    @Override
    public void receiveInboundData(String msg) throws Exception {
        log.debug(msg);
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(msg, ConnectorMessage.class);
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            cn.baozun.bh.connector.gymboree.inoutvouch.model.NewDataSet ns = (cn.baozun.bh.connector.gymboree.inoutvouch.model.NewDataSet) JSONUtil.jsonToBean(messageContent, cn.baozun.bh.connector.gymboree.inoutvouch.model.NewDataSet.class);
            InOutVouch iv = ns.getInOutVouch();
            Row row = iv.getRow();
            if (!StringUtils.hasText(row.getFchrCode())) {
                throw new BusinessException("Notice:Here Gymboree Give me no SlipCode(FCHRCODE)");
            } else {
                GymboreeReceiveData grd = gymboreeReceiveDataDao.getDataByCode(row.getFchrCode(), new BeanPropertyRowMapper<GymboreeReceiveData>(GymboreeReceiveData.class));
                if (grd != null) {
                    throw new BusinessException("Notice:Data has exists.....");
                }
            }
            GymboreeReceiveData rd = new GymboreeReceiveData();
            rd.setFchrCode(row.getFchrCode());
            rd.setFchrEmployeeID(row.getFchrEmployeeID());
            rd.setFdtmDate(row.getFdtmDate());
            rd.setFchrWarehouseId(row.getFchrWarehouseID());
            rd.setFchrInOutVouchID(row.getFchrInOutVouchID());
            rd.setStatus(DefaultStatus.CREATED);
            rd.setCreateTime(new Date());
            rd.setOwner(row.getFchrRequireWhCode().toString());// 新增入库店铺 Edit by KJL 2015-12-11
            gymboreeReceiveDataDao.save(rd);
            InOutVouchDetail id = ns.getInOutVouchDetail();
            List<Row> list = id.getRow();
            for (Row r : list) {
                GymboreeReceiveDataLine line = new GymboreeReceiveDataLine();
                line.setData(rd);
                line.setFchrBarCode(r.getFchrBarCode());
                line.setFchrItemID(r.getFchrItemID());
                line.setFlotMoney((r.getFlotMoney() == null || r.getFlotMoney().equals("")) ? null : new BigDecimal(r.getFlotMoney()));
                line.setFlotQuotePrice((r.getFlotQuotePrice() == null || r.getFlotQuotePrice().equals("")) ? null : new BigDecimal(r.getFlotQuotePrice()));
                line.setFlotQuantity(r.getFlotQuantity().longValue());
                line.setFchrOutVouchDetailID(r.getFchrInOutVouchDetailID());
                gymboreeReceiveDataLineDao.save(line);
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝店存入库指示Exception----------------------------");
            throw e;
        }
    }

    @Override
    public void rtnInboundNotice(String flag) {
        List<MsgInboundOrder> ml = msgInboundOrderDao.findVmiInboundNeedSendHaveType(Constants.VMI_WH_SOURCE_GYMBOREE, flag);
        if (ml.size() > 0) {
            DeliverReturns drs = new DeliverReturns();
            List<DeliverReturn> rl = new ArrayList<DeliverReturn>();
            for (MsgInboundOrder in : ml) {
                MsgInboundOrder inr = msgInboundOrderDao.getByPrimaryKey(in.getId());
                DeliverReturn dh = new DeliverReturn();
                dh.setHead(true);
                dh.setRchdrrtnew("RCHDRRTNEW");
                // dh.setWarehouse("1201");
                dh.setWarehouse(flag);
                dh.setOwner(flag);
                dh.setInterfaceRecordId(inr.getStaCode());
                StockTransApplication sta = staDao.getByCode(inr.getStaCode());
                StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                dh.setReceiptId(inr.getStaCode());
                dh.setCompany(COMPANY);
                dh.setSourceName(di.getOrderUserCode());
                dh.setShipFrom(inr.getReceiver());
                dh.setShipFromState(di.getProvince());
                dh.setShipFromCity(di.getCity());
                dh.setSourceEmailAddress((di.getLpCode() == null ? "" : di.getLpCode()) + (di.getTrackingNo() == null ? "" : ("/" + di.getTrackingNo())));
                dh.setSourceAddress1(di.getDistrict());
                dh.setShipFromPostalCode(di.getZipcode() == null ? DEFAULTZIPCODE : di.getZipcode());
                dh.setShipFromAddress1(di.getAddress());
                dh.setReceiptIdType(ORDERTYPERTNSALSE);
                dh.setSourceId(staDao.findOrderBySlipCode(sta.getSlipCode1(), new SingleColumnRowMapper<String>(String.class)));
                rl.add(dh);
                List<MsgInboundOrderLine> il = msgInboundOrderLineDao.fomdMsgInboundOrderLineByOId(inr.getId());
                for (MsgInboundOrderLine mol : il) {
                    DeliverReturn dr = new DeliverReturn();
                    dr.setHead(false);
                    dr.setRcdtldnnew("RCDTLDNNEW");
                    dr.setInterfaceLinkId(inr.getStaCode());
                    dr.setInterfaceRecordId(mol.getId() + "");
                    dr.setCompany(COMPANY);
                    dr.setItem(mol.getSku().getBarCode());
                    dr.setGoodsName(mol.getSku().getName());
                    dr.setStyleName(mol.getSku().getSkuSize());
                    dr.setTotalQty(mol.getQty() + "");
                    dr.setErpOrderLineNum(mol.getId() + "");
                    dr.setErpOrderType(ORDERTYPERTNSALSE);
                    dr.setUserDef3(mol.getId() + "");
                    rl.add(dr);
                }
                inr.setStatus(DefaultStatus.FINISHED);
            }
            drs.setDeliverReturns(rl);
            String msg = JSONUtil.beanToJson(drs);
            ConnectorMessage connectorMessage = new ConnectorMessage();
            connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
            connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_DELIVERRETURN);
            connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
            connectorMessage.setIsCompress(Boolean.TRUE);
            connectorMessage.setMessageContent(ZipUtil.compress(msg));
            String mqMsg = JSONUtil.beanToJson(connectorMessage);
            if (flag.equals(Constants.GYM_SC)) {
                // 发送mq消息
                MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_deliverreturn", mqMsg);
            } else {
                MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_deliverreturn_tmall", mqMsg);
            }
        }
    }

    /**
     * 接收hub端反馈的退货实绩，记录数据，待定时任务执行
     */
    @Override
    public void receiveRtnInboundMsg(String msg) throws Exception {
        try {
            Map<String, Object> map = JSONUtil.jsonToMap(msg, true);
            String tmp = map.get("performanceReturnHeads").toString();
            JSONArray ja = JSONArray.fromObject(tmp);
            List<PerformanceReturnHead> hl = new ArrayList<PerformanceReturnHead>();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                PerformanceReturnHead ih = (PerformanceReturnHead) JSONObject.toBean(ob, PerformanceReturnHead.class);
                hl.add(ih);
            }
            tmp = map.get("performanceReturnBodies").toString();
            ja = JSONArray.fromObject(tmp);
            List<PerformanceReturnBody> bl = new ArrayList<PerformanceReturnBody>();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                PerformanceReturnBody ib = (PerformanceReturnBody) JSONObject.toBean(ob, PerformanceReturnBody.class);
                bl.add(ib);
            }
            for (PerformanceReturnHead head : hl) {
                try {
                    MsgRtnInboundOrder mio = new MsgRtnInboundOrder();
                    mio.setStaCode(head.getReceiptId());
                    mio.setStatus(DefaultStatus.CREATED);
                    mio.setType(StockTransApplicationType.INBOUND_RETURN_REQUEST.getValue());
                    mio.setCreateTime(new Date());
                    mio.setSource(Constants.VMI_WH_SOURCE_GYMBOREE);
                    mio.setSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
                    msgRtnInboundOrderDao.save(mio);
                    Warehouse wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
                    StockTransApplication sta = staDao.findStaByCode(head.getReceiptId());
                    BiChannel shop = null;
                    if (sta != null) {
                        shop = shopDao.getByCode(sta.getOwner());
                    }
                    for (int i = 0; i < bl.size(); i++) {
                        PerformanceReturnBody body = bl.get(i);
                        if (body.getReceiptId().equals(mio.getStaCode())) {
                            MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
                            line.setBarcode(body.getItem());
                            line.setQty(new BigDecimal(body.getTotalQty()).longValue());
                            line.setOutStatus(body.getUserDef4());
                            InventoryStatus is = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, body.getUserDef4());
                            line.setInvStatus(is);
                            line.setMsgRtnInOrder(mio);
                            Sku sku = whManager.getByExtCode2AndCustomerAndShopId(line.getBarcode(), wh.getCustomer().getId(), shop == null ? null : shop.getId());
                            if (sku != null) {
                                line.setSkuCode(sku.getCode());
                                line.setSkuId(sku.getId());
                            }
                            msgRtnInboundOrderLineDao.save(line);
                            bl.remove(i);
                            i--;
                        }
                    }
                } catch (BusinessException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝退货实绩Exception-------------------------");
            throw e;
        }
    }

    @Override
    public void receiveRtnOutboundMsg(String msg) throws Exception {
        try {
            Map<String, Object> map = JSONUtil.jsonToMap(msg, true);
            String tmp = map.get("outstockBodies").toString();
            JSONArray ja = JSONArray.fromObject(tmp);
            List<OutstockBody> bl = new ArrayList<OutstockBody>();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                OutstockBody ih = (OutstockBody) JSONObject.toBean(ob, OutstockBody.class);
                bl.add(ih);
            }
            for (OutstockBody ob : bl) {
                GymboreeReceiveRtnData rrd = new GymboreeReceiveRtnData();
                rrd.setInoutTime(ob.getInoutTime());
                rrd.setFchrWarehouseID(ob.getFchrWarehouseID());
                rrd.setItem(ob.getItem());
                rrd.setUserDef4(ob.getUserDef4());
                rrd.setTotalQty(new BigDecimal(ob.getTotalQty()).longValue());
                rrd.setErpVouchCode(StringUtils.hasText(ob.getErpVouchCode()) ? ob.getErpVouchCode() : null);
                rrd.setMoveTo(ob.getMoveTo());
                rrd.setStatus(DefaultStatus.CREATED);
                rrd.setCreateTime(new Date());
                rrd.setOwner(ob.getOwner());
                gymboreeReceiveRtnDataDao.save(rrd);
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝店存出库指示Exception---------------------");
            throw e;
        }
    }

    /**
     * 根据指示创建退仓单 查询相同ERP单号创一单，然后商品和状态相同进行合并
     */
    @Override
    public void careateRtnOutboundSta(String slipCode, List<GymboreeReceiveRtnData> list) {
        OperationUnit ou = null;
        BiChannel shop = null;
        String cl = "";
        Warehouse wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
        if (wh == null) {
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        } else {
            Long ouId = wh.getOu().getId();
            ou = ouDao.getByPrimaryKey(ouId);
            if (ou == null) {
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
        }
        if (shop == null) {
            shop = shopDao.getByVmiCode((list.get(0).getOwner()) == null ? Constants.GYM_SC : (list.get(0).getOwner()));
            if (shop == null) {
                log.error("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_RETURN);
        sta.setCreateTime(new Date());
        sta.setMainWarehouse(ou);
        sta.setRefSlipCode(slipCode);
        sta.setOwner(shop.getCode());
        sta.setChannelList(cl);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        sta.setIsNeedOccupied(true);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        String gwh = gymboreeWarehouseDao.getIdByCode(list.get(0).getMoveTo(), new SingleColumnRowMapper<String>(String.class));
        if (gwh == null) {
            log.error("gymbore outbound msg warehouse not found----------------");
            throw new BusinessException("");
        } else {
            sta.setToLocation(gwh);
        }
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        staDao.flush();
        Map<String, GymboreeReceiveRtnData> gm = new HashMap<String, GymboreeReceiveRtnData>();
        for (GymboreeReceiveRtnData gd : list) {
            String key = gd.getSkuId() + "_" + gd.getInvStatusId();
            if (gm.get(key) == null) {
                gm.put(key, gd);
            } else {
                gm.get(key).setTotalQty(gm.get(key).getTotalQty() + gd.getTotalQty());
            }
            GymboreeReceiveRtnData grd = gymboreeReceiveRtnDataDao.getByPrimaryKey(gd.getId());
            grd.setStaId(sta.getId());
            grd.setStatus(DefaultStatus.FINISHED);
            grd.setFinishTime(new Date());
        }
        for (String key : gm.keySet()) {
            StaLine line = new StaLine();
            line.setSta(sta);
            line.setQuantity(gm.get(key).getTotalQty());
            line.setCompleteQuantity(0L);
            line.setOwner(shop.getCode());
            line.setInvStatus(inventoryStatusDao.getByPrimaryKey(gm.get(key).getInvStatusId()));
            line.setSku(skuDao.getByPrimaryKey(gm.get(key).getSkuId()));
            staLineDao.save(line);
        }
        staLineDao.flush();
        staDao.updateIsSnSta(sta.getId());
        staDao.updateSkuQtyById(sta.getId());
        BigDecimal transactionid = transactionTypeDao.findByStaType(StockTransApplicationType.VMI_RETURN.getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        StockTransVoucher stv = itochuManager.occupyInventoryByStaId(sta.getId(), transactionType, ou);
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        /*************************** 退仓增量 ************************/
        excelReadManager.incrementInv(sta.getId());
        /*************************** 退仓增量 ************************/
        whManager.removeInventory(sta, stv);
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 其他出库更新中间表，传递明细给oms/pac
        try {
            eventObserver.onEvent(new TransactionalEvent(sta));
        } catch (BusinessException e) {
            throw e;
        }
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.FINISHED.getValue(), null, sta.getMainWarehouse().getId());
        staDao.flush();
        BiChannel sh = companyShopDao.getByCode(sta.getOwner());
        if (sh != null && sh.getVmiCode() != null && StringUtil.isEmpty(sta.getDataSource())) {
            VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
            vf.generateRtnWh(sta);
        }
    }

    @Override
    public void sendRtnOutboundResult(String code, List<GymboreeRtnOutData> list) {
        String flag = null;
        NewDataSet set = new NewDataSet();
        Main main = new Main();
        main.setFdtmDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd"));
        main.setFchrMemo(code);
        main.setFchrEmployeeID("0D000000-0000-0DB1-0000-00009F56F541");
        if (list.get(0).getFchrWarehouseID() != null) {
            main.setFchrRequireWarehouseID(list.get(0).getFchrWarehouseID());
            flag = list.get(0).getOwner();
        }
        if (flag.equals(Constants.GYM_TMALL)) {
            main.setFchrMaker(Constants.GYM_TMALL_FMARKER);
            main.setFchrEmployeeID(Constants.GYM_TMALL_EPID);
        }
        set.setMain(main);
        List<Detail> ds = new ArrayList<Detail>();
        for (GymboreeRtnOutData rd : list) {
            Detail detail = new Detail();
            detail.setFchritemid(rd.getFchrItemId());
            detail.setFchrbarcode(rd.getFchrBarcode());
            detail.setFlotquantity(rd.getFlotQuantity().toString());
            detail.setFchrFree2(rd.getFchrFree2());
            ds.add(detail);
            GymboreeRtnOutData d = gymboreeRtnOutDataDao.getByPrimaryKey(rd.getId());
            d.setStatus(DefaultStatus.FINISHED);
            d.setFinishTime(new Date());
        }
        set.setDetail(ds);
        set.setName("StockOutVouch");
        String msg = JSONUtil.beanToJson(set);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
        connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_DELIVERRETURN);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        if (flag.equals(Constants.GYM_SC)) {
            // 发送mq消息
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_stockoutvouch", mqMsg);
        } else {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_stockoutvouch_tmall", mqMsg);
        }
    }

    @Override
    public void sendRtnInboundResult(String code, List<GymboreeRtnOutData> list) {
        String flag = null;
        cn.baozun.bh.connector.gymboree.stockinvouch.model.NewDataSet set = new cn.baozun.bh.connector.gymboree.stockinvouch.model.NewDataSet();
        cn.baozun.bh.connector.gymboree.stockinvouch.model.Main main = new cn.baozun.bh.connector.gymboree.stockinvouch.model.Main();
        main.setFdtmDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd"));
        main.setFchrMemo(code);
        if (list.get(0).getFchrMaker() != null) {
            main.setFchrMaker(list.get(0).getFchrMaker());
        }
        if (list.get(0).getFchrWarehouseID() != null) {
            main.setFchrWarehouseID(list.get(0).getFchrWarehouseID());
            flag = list.get(0).getOwner();
        }
        main.setFchrEmployeeID("0D000000-0000-0DB1-0000-00009F56F541");
        if (flag.equals(Constants.GYM_TMALL)) {
            main.setFchrMaker(Constants.GYM_TMALL_FMARKER);
            main.setFchrEmployeeID(Constants.GYM_TMALL_EPID);
        }
        if (list.get(0).getFchrInOutVouchID() != null) {
            main.setFchrOutVouchID(list.get(0).getFchrInOutVouchID());
        }
        set.setMain(main);
        List<cn.baozun.bh.connector.gymboree.stockinvouch.model.Detail> ds = new ArrayList<cn.baozun.bh.connector.gymboree.stockinvouch.model.Detail>();
        for (GymboreeRtnOutData rd : list) {
            cn.baozun.bh.connector.gymboree.stockinvouch.model.Detail detail = new cn.baozun.bh.connector.gymboree.stockinvouch.model.Detail();
            detail.setFchritemid(rd.getFchrItemId());
            detail.setFlotquantity(rd.getFlotQuantity().toString());
            detail.setFchrbarcode(rd.getFchrBarcode());
            detail.setFchrFree2(rd.getFchrFree2());
            detail.setFchrOutVouchDetailID(rd.getFchrOutVouchDetailID());
            ds.add(detail);
            GymboreeRtnOutData d = gymboreeRtnOutDataDao.getByPrimaryKey(rd.getId());
            d.setStatus(DefaultStatus.FINISHED);
            d.setFinishTime(new Date());
        }
        set.setDetail(ds);
        set.setName("StockInVouch");
        String msg = JSONUtil.beanToJson(set);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
        connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_STOCKINVOUCH);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        if (flag.equals(Constants.GYM_SC)) {
            // 发送mq消息
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_stockinvouch", mqMsg);
        } else {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_stockinvouch_tmall", mqMsg);
        }
    }

    @Override
    public void receiveOtherInOut(String msg) throws Exception {
        try {
            Map<String, Object> map1 = JSONUtil.jsonToMap(msg, true);
            String tmp = map1.get("otherInOutstockBodies").toString();
            JSONArray ja = JSONArray.fromObject(tmp);
            List<OtherInOutstockBody> list = new ArrayList<OtherInOutstockBody>();
            for (int i = 0; i < ja.size(); i++) {
                JSONObject ob = (JSONObject) ja.get(i);
                OtherInOutstockBody ih = (OtherInOutstockBody) JSONObject.toBean(ob, OtherInOutstockBody.class);
                list.add(ih);
            }
            Map<String, List<OtherInOutstockBody>> map = new HashMap<String, List<OtherInOutstockBody>>();
            for (OtherInOutstockBody ob : list) {
                if (map.get(ob.getErpVouchCode()) == null) {
                    List<OtherInOutstockBody> list1 = new ArrayList<OtherInOutstockBody>();
                    list1.add(ob);
                    map.put(ob.getErpVouchCode(), list1);
                } else {
                    map.get(ob.getErpVouchCode()).add(ob);
                }
            }
            for (String key : map.keySet()) {
                List<OtherInOutstockBody> oList = map.get(key);
                MsgRtnAdjustment mj = new MsgRtnAdjustment();
                mj.setSource(Constants.VMI_WH_SOURCE_GYMBOREE);
                mj.setSourceWh(Constants.VMI_WH_SOURCE_GYMBOREE);
                mj.setOwnerSource((oList.get(0).getOwner()) == null ? Constants.GYM_SC : (oList.get(0).getOwner()));
                mj.setCreateTime(new Date());
                mj.setStatus(DefaultStatus.CREATED);
                mj.setIdsKey(key);
                msgRtnAdjustmentDao.save(mj);
                for (OtherInOutstockBody ob : oList) {
                    if (ob.getInoutType().trim().equals(ORDERTYPEIN)) {
                        MsgRtnAdjustmentLine line = new MsgRtnAdjustmentLine();
                        line.setAdjustment(mj);
                        InventoryStatus iss = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, ob.getUserDef4());
                        if (iss == null) {
                            log.error("Gymboree interface inventoryStatus is null----");
                            throw new BusinessException("Gymboree interface inventoryStatus is null----");
                        } else {
                            line.setInvStatus(iss);
                        }
                        line.setQty(new BigDecimal(ob.getTotalQty()).longValue());
                        // line.setSkuId(skuDao.getByBarcode(ob.getItem(), 1L).getId());
                        line.setGbcode(ob.getItem());
                        msgRtnAdjustmentLineDao.save(line);
                    } else if (ob.getInoutType().trim().equals(ORDERTYPEOUT)) {
                        MsgRtnAdjustmentLine line = new MsgRtnAdjustmentLine();
                        line.setAdjustment(mj);
                        InventoryStatus iss = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, ob.getUserDef4());
                        if (iss == null) {
                            log.error("Gymboree interface inventoryStatus is null----");
                            throw new BusinessException("Gymboree interface inventoryStatus is null----");
                        } else {
                            line.setInvStatus(iss);
                        }
                        line.setQty(-new BigDecimal(ob.getTotalQty()).longValue());
                        // line.setSkuId(skuDao.getByBarcode(ob.getItem(), 1L).getId());
                        line.setGbcode(ob.getItem());
                        msgRtnAdjustmentLineDao.save(line);
                    } else {
                        MsgRtnAdjustmentLine line = new MsgRtnAdjustmentLine();
                        line.setAdjustment(mj);
                        InventoryStatus iss = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, ob.getUserDef4());
                        if (iss == null) {
                            log.error("Gymboree interface inventoryStatus is null----");
                            throw new BusinessException("Gymboree interface inventoryStatus is null----");
                        } else {
                            line.setInvStatus(iss);
                        }
                        line.setQty(-new BigDecimal(ob.getTotalQty()).longValue());
                        line.setGbcode(ob.getItem());
                        // line.setSkuId(skuDao.getByBarcode(ob.getItem(), 1L).getId());
                        msgRtnAdjustmentLineDao.save(line);
                        if (ob.getUserDef4().equals(INVA)) {
                            MsgRtnAdjustmentLine line1 = new MsgRtnAdjustmentLine();
                            line1.setAdjustment(mj);
                            InventoryStatus is1 = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, INVB);
                            if (is1 == null) {
                                log.error("Gymboree interface inventoryStatus data init error---");
                                throw new BusinessException("Gymboree interface inventoryStatus data init error---");
                            } else {
                                line1.setInvStatus(is1);
                            }
                            line1.setQty(new BigDecimal(ob.getTotalQty()).longValue());
                            line1.setGbcode(ob.getItem());
                            // line1.setSkuId(skuDao.getByBarcode(ob.getItem(), 1L).getId());
                            msgRtnAdjustmentLineDao.save(line1);
                        } else {
                            MsgRtnAdjustmentLine line2 = new MsgRtnAdjustmentLine();
                            line2.setAdjustment(mj);
                            InventoryStatus is1 = inventoryStatusDao.findInventoryByWhAndName(Constants.VMI_WH_SOURCE_GYMBOREE, Constants.VMI_WH_SOURCE_GYMBOREE, INVA);
                            if (is1 == null) {
                                log.error("Gymboree interface inventoryStatus data init error---");
                                throw new BusinessException("Gymboree interface inventoryStatus data init error---");
                            } else {
                                line2.setInvStatus(is1);
                            }
                            line2.setQty(new BigDecimal(ob.getTotalQty()).longValue());
                            line2.setGbcode(ob.getItem());
                            // line2.setSkuId(skuDao.getByBarcode(ob.getItem(), 1L).getId());
                            msgRtnAdjustmentLineDao.save(line2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝其他出入库指示Exception-------------------");
            throw e;
        }
    }

    @Override
    public void otherInboundRtn(String code, List<GymboreeRtnOutData> list) {
        String flag = null;
        cn.baozun.bh.connector.gymboree.otherinvouch.model.NewDataSet set = new cn.baozun.bh.connector.gymboree.otherinvouch.model.NewDataSet();
        cn.baozun.bh.connector.gymboree.otherinvouch.model.Main main = new cn.baozun.bh.connector.gymboree.otherinvouch.model.Main();
        main.setFdtmDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd"));
        main.setFchrMemo(code);
        main.setFchrEmployeeID("0D000000-0000-0DB1-0000-00009F56F541");
        flag = list.get(0).getOwner();
        if (flag.equals(Constants.GYM_TMALL)) {
            main.setFchrMaker(Constants.GYM_TMALL_FMARKER);
            main.setFchrEmployeeID(Constants.GYM_TMALL_EPID);
        }
        set.setMain(main);
        List<cn.baozun.bh.connector.gymboree.otherinvouch.model.Detail> ds = new ArrayList<cn.baozun.bh.connector.gymboree.otherinvouch.model.Detail>();
        for (GymboreeRtnOutData rd : list) {
            cn.baozun.bh.connector.gymboree.otherinvouch.model.Detail detail = new cn.baozun.bh.connector.gymboree.otherinvouch.model.Detail();
            detail.setFchritemid(rd.getFchrItemId());
            detail.setFlotquantity(rd.getFlotQuantity().toString());
            detail.setFchrbarcode(rd.getFchrBarcode());
            detail.setFchrFree2(rd.getFchrFree2());
            ds.add(detail);
            GymboreeRtnOutData d = gymboreeRtnOutDataDao.getByPrimaryKey(rd.getId());
            d.setStatus(DefaultStatus.FINISHED);
            d.setFinishTime(new Date());
        }
        set.setDetail(ds);
        set.setName("OtherInVouch");
        String msg = JSONUtil.beanToJson(set);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
        connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_BARCODERULE);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        if (flag.equals(Constants.GYM_SC)) {
            // 发送mq消息
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_otherinvouch", mqMsg);
        } else {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_otherinvouch_tmall", mqMsg);
        }

    }

    @Override
    public void otherOutboundRtn(String code, List<GymboreeRtnOutData> list) {
        String flag = null;
        cn.baozun.bh.connector.gymboree.otheroutvouch.model.NewDataSet set = new cn.baozun.bh.connector.gymboree.otheroutvouch.model.NewDataSet();
        cn.baozun.bh.connector.gymboree.otheroutvouch.model.Main main = new cn.baozun.bh.connector.gymboree.otheroutvouch.model.Main();
        main.setFdtmDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd"));
        main.setFchrMemo(code);
        main.setFchrEmployeeID("0D000000-0000-0DB1-0000-00009F56F541");
        flag = list.get(0).getOwner();
        if (flag.equals(Constants.GYM_TMALL)) {
            main.setFchrMaker(Constants.GYM_TMALL_FMARKER);
            main.setFchrEmployeeID(Constants.GYM_TMALL_EPID);
        }
        set.setMain(main);
        List<cn.baozun.bh.connector.gymboree.otheroutvouch.model.Detail> ds = new ArrayList<cn.baozun.bh.connector.gymboree.otheroutvouch.model.Detail>();
        for (GymboreeRtnOutData rd : list) {
            cn.baozun.bh.connector.gymboree.otheroutvouch.model.Detail detail = new cn.baozun.bh.connector.gymboree.otheroutvouch.model.Detail();
            detail.setFchritemid(rd.getFchrItemId());
            detail.setFlotquantity(rd.getFlotQuantity().toString());
            detail.setFchrbarcode(rd.getFchrBarcode());
            detail.setFchrFree2(rd.getFchrFree2());
            ds.add(detail);
            GymboreeRtnOutData d = gymboreeRtnOutDataDao.getByPrimaryKey(rd.getId());
            d.setStatus(DefaultStatus.FINISHED);
            d.setFinishTime(new Date());
        }
        set.setDetail(ds);
        set.setName("OtherOutVouch");
        String msg = JSONUtil.beanToJson(set);
        ConnectorMessage connectorMessage = new ConnectorMessage();
        connectorMessage.setPlatformCode(ConstantsGymboree.BH_CONNECTOR_PLATFORM_CODE_GYMBOREE);
        connectorMessage.setInterfaceCode(ConstantsGymboree.BH_CONNECTOR_INTERFACE_CODE_GYMBOREE_OTHERINVOUCH);
        connectorMessage.setMessageContentType(ConnectorMessage.MESSAGE_CONTENT_TYPE_JSON);
        connectorMessage.setIsCompress(Boolean.TRUE);
        connectorMessage.setMessageContent(ZipUtil.compress(msg));
        String mqMsg = JSONUtil.beanToJson(connectorMessage);
        if (flag.equals(Constants.GYM_SC)) {
            // 发送mq消息
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_otheroutvouch", mqMsg);
        } else {
            MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_gymboree_otheroutvouch_tmall", mqMsg);
        }

    }

    @Override
    public void receiveGymboreeWarehouseMsg(String msg) throws Exception {
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(msg, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            cn.baozun.bh.connector.gymboree.warehouse.model.NewDataSet nd = JSONUtil.jsonToBean(messageContent, cn.baozun.bh.connector.gymboree.warehouse.model.NewDataSet.class);
            List<cn.baozun.bh.connector.gymboree.warehouse.model.Row> rl = nd.getWarehouse().getRow();
            for (cn.baozun.bh.connector.gymboree.warehouse.model.Row row : rl) {
                List<GymboreeWarehouse> GymboreeWarehouse = gymboreeWarehouseDao.getIdByFchrWarehouse(row.getFchrWhCode(), row.getFchrWarehouseID());
                if (GymboreeWarehouse.size() == 0) {
                    GymboreeWarehouse wh = new GymboreeWarehouse();
                    wh.setFchrMemo(row.getFchrMemo());
                    wh.setFchrWarehouseID(row.getFchrWarehouseID());
                    wh.setFchrWhCode(row.getFchrWhCode());
                    wh.setFchrWhName(row.getFchrWhName());
                    wh.setFchrAddress(row.getFchrAddress());
                    wh.setFchrPhone(row.getFchrPhone());
                    wh.setFchrLinkman(row.getFchrLinkman());
                    wh.setFchrStoreFlag(row.getFchrStoreFlag());
                    wh.setFchrWarehouseOrgID(row.getFchrWarehouseOrgID());
                    wh.setFbitNoUsed(row.getFbitNoUsed());
                    wh.setFbitWhPos(row.getFbitWhPos());
                    wh.setFchrAccountID(row.getFchrAccountID());
                    wh.setFchrOrgCode(row.getFchrOrgCode().toString());
                    wh.setFchrOrgName(row.getFchrOrgName());
                    wh.setCreateTime(new Date());
                    gymboreeWarehouseDao.save(wh);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error("金宝贝仓库主档Exception---------------------");
            throw e;
        }

    }

    @Override
    public void sendMailForErrorReback() {
        log.debug("GymboreeTask sendMailForErrorReback begin");
        msgRtnOutboundDao.updateGymboreeErrorOrder();
        msgRtnOutboundDao.flush();
        List<MsgRtnOutbound> num = msgRtnOutboundDao.findNeedEmailGymboreeOrder(new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        if (num != null && num.size() > 0) {
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(GYMBOREE_EMAIL);
            MailTemplate template = mailTemplateDao.findTemplateByCode(GYMBOREE_EMAIL);
            String[] s = template.getMailBody().split("@");
            String prefix = s[0];
            String suffix = s[1];
            StringBuffer mailBody = new StringBuffer(prefix);
            for (int i = 0; i < num.size(); i++) {
                MsgRtnOutbound msb = num.get(i);
                String tr = MessageFormat.format(suffix, msb.getStaCode(), msb.getLpCode(), msb.getTrackingNo());
                mailBody = mailBody.append(tr);
            }
            MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody.toString(), true, null);
            msgRtnOutboundDao.updateGymboreeErrorOrderSend();
            log.debug("GymboreeTask sendMailForErrorReback end");
        }
    }
}
