package com.jumbo.wms.manager.task.inv;



import java.math.BigDecimal;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.cj.CjLgOrderCodeUrlDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutAdditionalLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.vmi.warehouse.RtnSnDetailDao;
import com.jumbo.dao.vmi.warehouse.WhThreePlAreaInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.RelationNikeDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WXConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuBarcodeDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WxConfirmOrderQueueLogDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.rmi.warehouse.threePL.WmsErrorResponse;
import com.jumbo.rmi.warehouse.threePL.WmsErrorResponse.ErrorMsg;
import com.jumbo.rmi.warehouse.threePL.WmsInboundOrder;
import com.jumbo.rmi.warehouse.threePL.WmsInboundOrderLine;
import com.jumbo.rmi.warehouse.threePL.WmsInboundOrderResponseLine;
import com.jumbo.rmi.warehouse.threePL.WmsInvChangeOrderLineResponse;
import com.jumbo.rmi.warehouse.threePL.WmsInvStatusChangeOrderLineResponse;
import com.jumbo.rmi.warehouse.threePL.WmsMsgCancelRespose;
import com.jumbo.rmi.warehouse.threePL.WmsMsgCancelRespose.MsgCancelResponseMessage;
import com.jumbo.rmi.warehouse.threePL.WmsMsgInRespose;
import com.jumbo.rmi.warehouse.threePL.WmsMsgInRespose.MsgInRequsetMessage;
import com.jumbo.rmi.warehouse.threePL.WmsMsgOutRespose;
import com.jumbo.rmi.warehouse.threePL.WmsMsgOutRespose.MsgOutResponseMessage;
import com.jumbo.rmi.warehouse.threePL.WmsMsgRtnInResponse;
import com.jumbo.rmi.warehouse.threePL.WmsMsgRtnInResponse.RtnResponseMessage;
import com.jumbo.rmi.warehouse.threePL.WmsMsgSaleOutRespose;
import com.jumbo.rmi.warehouse.threePL.WmsMsgSaleOutRespose.MsgSaleOutRequsetMessage;
import com.jumbo.rmi.warehouse.threePL.WmsOutBoundOrder;
import com.jumbo.rmi.warehouse.threePL.WmsOutBoundOrderDelivery;
import com.jumbo.rmi.warehouse.threePL.WmsOutBoundOrderLine;
import com.jumbo.rmi.warehouse.threePL.WmsOutboundOrderResponseLine;
import com.jumbo.rmi.warehouse.threePL.WmsOutboundOrderResponseSnLine;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrder;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderDelivery;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderGiftLine;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderInvoice;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderInvoiceLine;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderLine;
import com.jumbo.rmi.warehouse.threePL.WmsSalesOrderSe;
import com.jumbo.rmi.warehouse.threePL.WmsSku;
import com.jumbo.rmi.warehouse.threePL.WmsSkuRespose;
import com.jumbo.rmi.warehouse.threePL.WmsSkuRespose.MsgSkuRequsetMessage;
import com.jumbo.rmi.warehouse.threePL.wmsOrderCancel;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.util.mq.MqMsgUtil;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.ThreePLManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.cj.CjLgOrderCodeUrl;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustmentLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.RtnSnDetail;
import com.jumbo.wms.model.vmi.warehouse.WhThreePlAreaInfo;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.RelationNike;
import com.jumbo.wms.model.warehouse.SpecialSkuType;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseMsgSku;
import com.jumbo.wms.model.warehouse.WarehouseMsgSkuBarcode;
import com.jumbo.wms.model.warehouse.WarehouseMsgSkuCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WxConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.WxConfirmOrderQueueLog;

import cn.baozun.bh.util.JSONUtil;
import cn.baozun.model.top.BzConsignContactDto;
import cn.baozun.model.top.BzConsignGoodsDto;
import cn.baozun.model.top.BzConsignGoodsDto.ConsignPackageDto;
import cn.baozun.model.top.BzConsignTopInfoDto;
import cn.baozun.model.top.BzConsignTopRequestDto;
import cn.baozun.model.top.BzConsignTopResponseDto.BzConsignInfoResDtolist;
import cn.baozun.model.top.BzResponse;
import cn.baozun.model.top.BzTmsServiceDto;
import cn.baozun.model.top.BzWlbItemAddRequest;
import cn.baozun.model.top.BzWlbItemAddResponse;
import cn.baozun.model.top.BzWlbNotifyMessagePageGetRequest;
import cn.baozun.model.top.BzWlbNotifyMessagePageGetResponse;
import cn.baozun.model.top.BzWlbNotifyMessagePageGetResponse.HubWlbMessage;
import cn.baozun.model.top.BzWlbOrderCreateRequest;
import cn.baozun.model.top.BzWlbOrderCreateResponse;
import cn.baozun.model.top.BzWlbOrderPageGetRequest;
import cn.baozun.model.top.BzWlbOrderPageGetResponse;
import cn.baozun.model.top.BzWlbOrderPageGetResponse.BzWlbOrder;
import cn.baozun.model.top.BzWlbOrderitemPageGetRequest;
import cn.baozun.model.top.BzWlbOrderitemPageGetResponse;
import cn.baozun.model.top.BzWlbOrderitemPageGetResponse.BzOrderItemList;
import cn.baozun.model.top.BzWlbTmsOrderQueryResponse;
import cn.baozun.rmi.top.TopRmiService;
import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONObject;

@Transactional
@Service("threePLManager")
public class ThreePLManagerImpl extends BaseManagerImpl implements ThreePLManager {

    /**
     * 
     */
    private static final long serialVersionUID = -563578681096102110L;

    protected static final Logger log = LoggerFactory.getLogger(ThreePLManagerImpl.class);

    @Autowired
    private IdsManager idsManager;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private WarehouseMsgSkuBarcodeDao warehouseMsgSkuBarcodeDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private TopRmiService topRmiService;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private WXConfirmOrderQueueDao wXConfirmOrderQueueDao;
    @Autowired
    private WxConfirmOrderQueueLogDao wxconfirmOrderQueueLogDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private MsgRtnAdjustmentLineDao msgRtnAdjustmentLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private StvLineDao stvLineDao;
    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WhThreePlAreaInfoDao whThreePlAreaInfoDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private InventoryCheckDao invDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private WarehouseLocationDao locDao;
    @Autowired
    private RelationNikeDao relationNikeDao;
    @Autowired
    private InventoryCheckDifferenceLineDao icDifferenceLineDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private RtnSnDetailDao rtnSnDetailDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private CjLgOrderCodeUrlDao cjLgOrderCodeUrlDao;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private MessageConfigDao messageConfigDao;

    @Autowired
    private RocketMQProducerServer producerServer;


    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    
    @Autowired
    private MsgRtnOutAdditionalLineDao msgRtnOutAdditionalLineDao;
    
    
    protected static final Logger logger = LoggerFactory.getLogger(ThreePLManagerImpl.class);


    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate Class:ThreePLManagerImpl");
        }
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

    /**
     * 物流宝商品同步接口
     */
    public void syncWmsSkuToHab(WarehouseMsgSku msgSku) {
        Sku sku = skuDao.getByCode(msgSku.getCode());
        BzWlbItemAddRequest item = new BzWlbItemAddRequest();
        item.setName(msgSku.getName()); // 商品名字
        item.setItemCode(msgSku.getCode());// 商品编码
        item.setRemark(msgSku.getExtMemo());
        item.setType("NORMAL");
        item.setIsSku("true");
        item.setColor(msgSku.getColor());
        item.setLength(msgSku.getLength());
        item.setWidth(msgSku.getWidth());
        item.setHeight(msgSku.getHeight());
        // item.setWmsShopId(21110l);
        item.setWmsShopId(msgSku.getShopId());
        // 重量
        if (sku.getGrossWeight() != null) {
            item.setWeight(sku.getGrossWeight().multiply(new BigDecimal(1000)));
        }
        // 价格
        if (sku.getListPrice() != null) {
            item.setPrice(sku.getListPrice().multiply(new BigDecimal(100)));
        } else {
            item.setPrice(new BigDecimal(0l));
        }
        WarehouseMsgSku msgSkus = warehouseMsgSkuDao.getByPrimaryKey(msgSku.getId());
        try {
            log.info("Call hab wlbItemAdd interface------------------->BEGIN");
            BzWlbItemAddResponse addResponse = topRmiService.wlbItemAdd(item);
            if (addResponse.getItemId() != null) {
                msgSkus.setStatus(10l);
                msgSkus.setSkuThreeplCode(addResponse.getItemId().toString());
                log.info("Call hab wlbItemAdd interface------------------->END");
            } else {
                if (msgSkus.getErrorCount() == null || msgSkus.getErrorCount().equals("")) {
                    msgSkus.setErrorCount(0l);
                }
                msgSkus.setMsg(addResponse.getErrorMessage());
                msgSkus.setErrorCount(msgSkus.getErrorCount() + 1);
                log.debug("Call hab wlbOrderCreate interface error:");
            }

        } catch (Exception e) {
            if (msgSkus.getErrorCount() == null || msgSkus.getErrorCount().equals("")) {
                msgSkus.setErrorCount(0l);
            }
            msgSkus.setMsg(e.getMessage());
            msgSkus.setErrorCount(msgSkus.getErrorCount() + 1);
            log.error("Call hab wlbItemAdd interface error:");
            log.error("", e);
        }
    }

    /**
     * 外包仓入库信息同步hab 物流宝 *
     * 
     * @param orderList
     */
    public void sendWlbInboundToHab(MsgInboundOrder msgInbound) {
        BzWlbOrderCreateRequest orderCreate = new BzWlbOrderCreateRequest();
        StockTransApplication sta = staDao.getByCode(msgInbound.getStaCode());
        // StaDeliveryInfo info = sta.getStaDeliveryInfo();
        Date date = new Date();
        String StringDate = Long.toString(date.getTime());
        orderCreate.setOutBizCode(StringDate); // 外部订单业务ID
        orderCreate.setStoreCode(msgInbound.getSourceWh()); // 仓库编码
        orderCreate.setWmsShopId(msgInbound.getShopId()); // WMS店铺ID
        if (msgInbound.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            // 退货入库
            orderCreate.setOrderType(BzWlbOrderCreateRequest.ORDERTYPE_RETURN_IN);
        } else {
            // 正常入库
            orderCreate.setOrderType(BzWlbOrderCreateRequest.ORDERTYPE_NORMAL_IN);
        }
        orderCreate.setOrderSubType("PURCHASE"); // 因組合商品原因，都改為非淘系訂單
        // orderCreate.setTmsInfo(lpCode + "^^^张三^^^" + TrackNo +
        // "^^^13555552231^^^430839198823822281"); // 物流商信息
        if (sta.getOrderTotalActual() != null) {
            orderCreate.setTotalAmount(sta.getOrderTotalActual()); // 总金额
        } else {
            orderCreate.setTotalAmount(new BigDecimal(0)); // 总金额
        }
        /*
         * Warehouse info = warehouseDao.getByOuId(sta.getMainWarehouse().getId()); //
         * 收货方信息手机和电话必选其一。 收货方信息： 邮编^^^省^^^市^^^区^^^具体地址^^^收件方名称^^^手机^^^电话 如果某一个字段的数据为空时，必须传NA String
         * zipCode = (StringUtils.hasText(info.getZipcode()) ? info.getZipcode() : "NA"); String
         * province = (StringUtils.hasText(info.getProvince()) ? info.getProvince() : "NA"); String
         * city = (StringUtils.hasText(info.getCity()) ? info.getCity() : "NA"); String district =
         * "闸北区"; String address = (StringUtils.hasText(info.getAddress()) ? info.getAddress() :
         * "NA"); String receiver = (StringUtils.hasText(info.getPic()) ? info.getPic() : "NA");
         * String mobile = (StringUtils.hasText(info.getFax()) ? info.getFax() : "NA"); String
         * telephone = (StringUtils.hasText(info.getPhone()) ? info.getPhone() : "NA");
         */
        orderCreate.setReceiverInfo("200000^^^上海^^^上海市^^^闸北区^^^万荣路1188号^^^张三^^^15675895564^^^NA");
        orderCreate.setSenderinfo("31000^^^浙江省^^^杭州市^^^西湖区^^^创业大厦6楼^^^陈四^^^NA^^^057188155188");
        // // 是否需要发票
        // if (info.getStoreComIsNeedInvoice() != null && info.getStoreComIsNeedInvoice()) {
        // // // 内部类转成json
        // // BzWlbOrderCreateRequest.InvoinceInfo invoInfos = new
        // // BzWlbOrderCreateRequest.InvoinceInfo();
        // // invoInfos.setBill_type("普通"); // 发票类型
        // // invoInfos.setBill_title(info.getStoreComInvoiceTitle()); // 发票标题
        // // if (info.getStoreComTotalAmount() != null) {
        // // invoInfos.setBill_amount(info.getStoreComTotalAmount().toString()); // 发票金额
        // // } else {
        // // invoInfos.setBill_amount("0"); // 发票金额
        // // }
        // // invoInfos.setBill_content(info.getStoreComInvoiceContent()); // 发票内容
        // // BzWlbOrderCreateRequest.Invoince invoince = new
        // // BzWlbOrderCreateRequest.Invoince();
        // // invoince.setInvoince_Info(invoInfos);
        // // String invoInfoStr = JSONObject.fromObject(invoince).toString();
        // if (info.getStoreComTotalAmount() == null) {
        // info.setStoreComTotalAmount(new BigDecimal(0));
        // }
        // String invoInfoStr =
        // "{\"invoince_info\":[{\"bill_type\":\"普通\",\"bill_title\":\"" +
        // info.getStoreComInvoiceTitle() + "\",\"bill_amount\":\"" +
        // info.getStoreComTotalAmount().toString() + "\",\"bill_content\":\"" +
        // info.getStoreComInvoiceContent()
        // + "\"}]}";
        // orderCreate.setInvoinceInfo(invoInfoStr); // 发票信息
        // }
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        orderCreate.setRemark(sta.getMemo()); // 备注
        // 50条商品一个批次，超过的分多个批次传，带上上一次的order_code
        int indexBegin = 0;
        for (int i = 0; i <= staLines.size() / 50; i++) {
            List<BzWlbOrderCreateRequest.OrderItem> infoList = eachLine(staLines, indexBegin, sta.getSlipCode2(), msgInbound.getSource(), null);
            // 将商品集合转json
            BzWlbOrderCreateRequest.orderItems orderItems = new BzWlbOrderCreateRequest.orderItems();
            orderItems.setOrder_item_list(infoList);
            String orderItemsJson = JSONObject.fromObject(orderItems).toString();
            // 设置订单商品列表
            orderCreate.setOrderItemList(orderItemsJson);
            if (staLines.size() / 50 == i) {
                orderCreate.setIsFinished(true);
            } else {
                orderCreate.setIsFinished(false);
            }
            indexBegin += 50;
            MsgInboundOrder msgInboundOrder = msgInboundOrderDao.getByPrimaryKey(msgInbound.getId());
            try {
                log.info("Call hab wlbOrderCreate interface------------------->BEGIN");
                BzWlbOrderCreateResponse wlbOrderCreate = topRmiService.wlbOrderCreate(orderCreate);
                if (StringUtils.hasLength(wlbOrderCreate.getOrderCode())) {
                    Date date2 = new Date();
                    String StringDates = Long.toString(date2.getTime());
                    orderCreate.setOutBizCode(StringDates); // 外部订单业务ID
                    orderCreate.setOrderCode(wlbOrderCreate.getOrderCode()); // 第二次传记录上一次的订单编号
                    if (orderCreate.getIsFinished()) {
                        sta.setWlbOrderCode(wlbOrderCreate.getOrderCode());
                        msgInboundOrder.setWlbCode(wlbOrderCreate.getOrderCode());
                        msgInboundOrder.setStatus(DefaultStatus.FINISHED);
                    } else {
                        msgInboundOrder.setStatus(DefaultStatus.EXECUT_CREATE);
                    }
                    log.info("Call hab wlbOrderCreate interface------------------->END");
                } else {
                    if (msgInboundOrder.getErrorCount() == null || msgInboundOrder.getErrorCount().equals("")) {
                        msgInboundOrder.setErrorCount(0l);
                    }
                    if (orderCreate.getIsFinished()) {
                        msgInboundOrder.setRemark(wlbOrderCreate.getErrorMessage());
                        msgInboundOrder.setErrorCount(msgInboundOrder.getErrorCount() + 1);
                        msgInboundOrder.setStatus(DefaultStatus.CANCELED);
                    }
                    log.debug("Call hab wlbOrderCreate interface error:");
                }

            } catch (Exception e) {
                if (msgInboundOrder.getErrorCount() == null || msgInboundOrder.getErrorCount().equals("")) {
                    msgInboundOrder.setErrorCount(0l);
                }
                msgInboundOrder.setRemark(e.getMessage());
                msgInboundOrder.setErrorCount(msgInboundOrder.getErrorCount() + 1);
                msgInboundOrder.setStatus(DefaultStatus.CANCELED);
                log.error("Call hab wlbOrderCreate interface error:");
                log.error("", e);
                break;
            }
        }
    }

    // 遍历商品，50条商品一个批次
    private List<BzWlbOrderCreateRequest.OrderItem> eachLine(List<StaLine> staLines, int indexBegin, String slipCode2, String source, String exeMemo) {
        // 订单商品信息
        List<BzWlbOrderCreateRequest.OrderItem> infoList = new ArrayList<BzWlbOrderCreateRequest.OrderItem>();
        int j = 0;
        for (int i = indexBegin; i < staLines.size(); i++) {
            if (j < 50) {
                BzWlbOrderCreateRequest.OrderItem orderItem = new BzWlbOrderCreateRequest.OrderItem();
                // orderItem.setTrade_code(slipCode2); // 淘宝交易订单
                // orderItem.setSub_trade_code(slipCode2);
                // if (exeMemo != null && !exeMemo.equals("")) {
                // orderItem.setSub_trade_code(exeMemo); // 淘宝子交易号
                // } else {
                // orderItem.setSub_trade_code(slipCode2); // 淘宝子交易号
                // }
                WarehouseMsgSku msgSku = warehouseMsgSkuDao.getMsgSkuBySkuCodeAndWh(staLines.get(i).getSku().getCode(), source);
                if (msgSku != null) {
                    orderItem.setItem_id(msgSku.getSkuThreeplCode());
                }
                orderItem.setItem_code(staLines.get(i).getSku().getCode()); // 商品编码
                orderItem.setItem_name(staLines.get(i).getSku().getName()); // 商品名称
                orderItem.setItem_quantity(staLines.get(i).getQuantity().toString());
                // 商品价格，单位为分
                if (staLines.get(i).getUnitPrice() != null && !staLines.get(i).getUnitPrice().equals("")) {
                    Long price = staLines.get(i).getUnitPrice().longValue() * 100;
                    orderItem.setItem_price(price.toString());
                } else {
                    orderItem.setItem_price("0");
                }
                orderItem.setFlag("0"); // 判断是否为赠品
                // 库存状态 201 冻结类型库存 301 在途库存 待定
                if (staLines.get(i).getInvStatus() == null) {
                    // 销售出 默认良品
                    orderItem.setInventory_type("1"); // 可销售库存
                } else {
                    if (staLines.get(i).getInvStatus().getName().equals("良品")) {
                        orderItem.setInventory_type("1"); // 可销售库存
                    } else if (staLines.get(i).getInvStatus().getName().equals("残次品")) {
                        orderItem.setInventory_type("101"); // 残次品
                    }
                }
                infoList.add(orderItem);
            } else {
                break;
            }
            j++;
        }
        return infoList;
    }

    /**
     * 物流宝新入库查询
     */
    public void queryWlbNewInbound(StockTransApplicationCommand sta) {
        BzWlbOrderPageGetRequest reqGet = new BzWlbOrderPageGetRequest();
        reqGet.setOrderCode(sta.getWlbOrderCode());
        reqGet.setOrderType("NORMAL_IN"); // 入库
        reqGet.setOrderSubType("PURCHASE"); // 采购
        reqGet.setPageNo(1l);
        reqGet.setPageSize(20l);
        reqGet.setWmsShopId(Long.parseLong(sta.getShopId()));
        boolean isContinue = false;
        BzWlbOrderPageGetResponse resGet = topRmiService.wlbOrderPageGet(reqGet);
        if (resGet.getBzWlborder() != null && resGet.getBzWlborder().size() > 0) {
            List<BzWlbOrder> bzWlborder = resGet.getBzWlborder();
            for (BzWlbOrder bzWlbOrder2 : bzWlborder) {
                // 100 || 200 代表菜鸟做入库，可以进行明细查询
                if (!"100".equals(bzWlbOrder2.getOrderStatus()) && !"200".equals(bzWlbOrder2.getOrderStatus())) {
                    continue;
                } else {
                    isContinue = true;
                    break;
                }
            }
        }
        if (!isContinue) {
            return;
        }
        BzWlbOrderitemPageGetRequest request = new BzWlbOrderitemPageGetRequest();
        request.setOrderCode(sta.getWlbOrderCode());
        request.setWmsShopId(Long.parseLong(sta.getShopId()));
        request.setPageNo(1l);
        request.setPageSize(50l);
        BzWlbOrderitemPageGetResponse response = topRmiService.wlbOrderItemPageGet(request);
        MsgRtnInboundOrder msgInbound = new MsgRtnInboundOrder();
        MsgInboundOrder msgIn = new MsgInboundOrder();
        if (response.getOrderItemList() != null && response.getOrderItemList().size() > 0) {
            for (int i = 0; i <= response.getTotalCount() / 50; i++) {
                if (i > 0) {
                    request.setPageNo(i + 1l);
                    response = topRmiService.wlbOrderItemPageGet(request);
                }
                Map<String, Long> map = new HashMap<String, Long>();
                for (BzOrderItemList msg : response.getOrderItemList()) {
                    if (msg.getRealQuantity() > 0) {
                        if (map.get(msg.getOrderCode()) == null) {
                            // 保存入库反馈
                            MsgInboundOrder inb = msgInboundOrderDao.getByWlbCode(msg.getOrderCode());
                            MsgRtnInboundOrder rtn2 = msgRtnInboundOrderDao.findInboundByStaCode(inb.getStaCode(), new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                            if (rtn2 != null) {
                                continue;
                            }
                            map.put(msg.getOrderCode(), msg.getItemId());
                            msgIn = msgInboundOrderDao.findByStaCode(inb.getStaCode());
                            msgInbound.setCreateTime(new Date());
                            msgInbound.setInboundTime(new Date());
                            msgInbound.setSource(msgIn.getSource());
                            msgInbound.setSourceWh(msgIn.getSourceWh());
                            msgInbound.setStaCode(inb.getStaCode());
                            msgInbound.setStatus(DefaultStatus.CREATED);
                            msgInbound.setType(inb.getType().getValue());
                            msgInbound.setVersion(0);
                            msgInbound.setShopId(msgIn.getShopId());
                            msgInbound.setRemark(msg.getBatchRemark());
                            msgRtnInboundOrderDao.save(msgInbound);
                        }
                        // 保存入库反馈明细
                        WarehouseMsgSku sku = warehouseMsgSkuDao.getMsgSkuByWlbIdAndWh(msg.getItemId().toString(), msgIn.getSourceWh());
                        MsgRtnInboundOrderLine orderLine = new MsgRtnInboundOrderLine();
                        orderLine.setQty(msg.getRealQuantity());
                        orderLine.setSkuCode(sku.getCode());
                        Sku skus = skuDao.getByCode(sku.getCode());
                        orderLine.setSkuId(skus.getId());
                        orderLine.setBarcode(skus.getBarCode());
                        InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("INVENTORY_TYPE_SELL".equals(msg.getInventoryType()) == true ? "良品" : "残次品", sta.getMainWhId().longValue());
                        orderLine.setInvStatus(invStatus);
                        orderLine.setMsgRtnInOrder(msgInbound);
                        msgRtnInboundOrderLineDao.save(orderLine);
                    }
                }
            }
        }
    }


    /**
     * 外包仓出库信息同步hab 物流宝 *
     * 
     * @param orderList
     */
    public void sendWlbOutboundToHab(MsgOutboundOrder msgOutbound) {
        BzWlbOrderCreateRequest orderCreate = new BzWlbOrderCreateRequest();
        StockTransApplication sta = staDao.getByCode(msgOutbound.getStaCode());
        // StaDeliveryInfo info = sta.getStaDeliveryInfo();
        Date date = new Date();
        String StringDate = Long.toString(date.getTime());
        orderCreate.setOutBizCode(StringDate); // 外部订单业务ID
        orderCreate.setStoreCode(msgOutbound.getSourceWh()); // 仓库编码
        orderCreate.setWmsShopId(msgOutbound.getShopId()); // WMS店铺ID
        if (msgOutbound.getStaType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST)) {
            // 换货出库
            orderCreate.setOrderType(BzWlbOrderCreateRequest.ORDERTYPE_EXCHANGE_OUT);
        } else {
            // 正常出库
            orderCreate.setOrderType(BzWlbOrderCreateRequest.ORDERTYPE_NORMAL_OUT);
        }
        orderCreate.setOrderSubType("OTHER_TRADE"); // 組合商品推送解決方案，都傳非淘系訂單
        // if (sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES)) {
        // orderCreate.setOrderSubType("TAOBAO_TRADE"); // 待hab给出映射关系
        // } else {
        // orderCreate.setOrderSubType("OTHER"); // 待hab给出映射关系
        // }
        orderCreate.setTmsServiceCode(msgOutbound.getLpCode()); // 物流公司编码
        // orderCreate.setTmsOrderCode(info.getTrackingNo()); // 快递单号
        if (sta.getOrderTotalActual() != null) {
            orderCreate.setTotalAmount(sta.getOrderTotalActual()); // 总金额
        } else {
            orderCreate.setTotalAmount(new BigDecimal(0)); // 总金额
        }
        // 是否COD
        if (sta.getStaDeliveryInfo() != null && sta.getStaDeliveryInfo().getIsCod() != null && sta.getStaDeliveryInfo().getIsCod()) {
            if (sta.getOrderTransferFree() != null && sta.getOrderTotalActual() != null) {
                orderCreate.setPayableAmount(sta.getOrderTransferFree().add(sta.getOrderTotalActual())); // 应收金额
            } else {
                orderCreate.setPayableAmount(sta.getOrderTotalActual()); // 应收金额
            }
            orderCreate.setServiceFee(sta.getOrderTransferFree()); // COD服务费
        }
        // 是否需要发票
        // if (info.getStoreComIsNeedInvoice() != null && info.getStoreComIsNeedInvoice()) {
        // if (info.getStoreComTotalAmount() == null) {
        // info.setStoreComTotalAmount(new BigDecimal(0));
        // }
        // String invoInfoStr =
        // "{\"invoince_info\":[{\"bill_type\":\"普通\",\"bill_title\":\"" +
        // info.getStoreComInvoiceTitle() + "\",\"bill_amount\":\"" +
        // info.getStoreComTotalAmount().toString() + "\",\"bill_content\":\"" +
        // info.getStoreComInvoiceContent()
        // + "\"}]}";
        // orderCreate.setInvoinceInfo(invoInfoStr); // 发票信息
        // }
        // 收货方信息手机和电话必选其一。 收货方信息： 邮编^^^省^^^市^^^区^^^具体地址^^^收件方名称^^^手机^^^电话 如果某一个字段的数据为空时，必须传NA
        String zipCode = (StringUtils.hasText(msgOutbound.getZipcode()) ? msgOutbound.getZipcode() : "NA");
        String province = (StringUtils.hasText(msgOutbound.getProvince()) ? msgOutbound.getProvince() : "NA");
        String city = (StringUtils.hasText(msgOutbound.getCity()) ? msgOutbound.getCity() : "NA");
        String district = (StringUtils.hasText(msgOutbound.getDistrict()) ? msgOutbound.getDistrict() : "NA");
        String address = (StringUtils.hasText(msgOutbound.getAddress()) ? msgOutbound.getAddress() : "NA");
        String receiver = (StringUtils.hasText(msgOutbound.getReceiver()) ? msgOutbound.getReceiver() : "NA");
        String mobile = (StringUtils.hasText(msgOutbound.getMobile()) ? msgOutbound.getMobile() : "NA");
        String telephone = (StringUtils.hasText(msgOutbound.getTelePhone()) ? msgOutbound.getTelePhone() : "NA");
        orderCreate.setReceiverInfo(zipCode + "^^^" + province + "^^^" + city + "^^^" + district + "^^^" + address + "^^^" + receiver + "^^^" + mobile + "^^^" + telephone);
        orderCreate.setSenderinfo("310000^^^浙江省^^^杭州市^^^西湖区^^^创业大厦6楼^^^陈四^^^NA^^^057188155188");
        // orderCreate.setTmsInfo("申通物流^^^张三^^^EG123123^^^13945234321^^^330621589478541244");
        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
        orderCreate.setRemark(sta.getMemo()); // 备注
        // 50条商品一个批次，超过的分多个批次传，带上上一次的order_code
        int indexBegin = 0;
        for (int i = 0; i <= staLines.size() / 50; i++) {
            List<BzWlbOrderCreateRequest.OrderItem> infoList = eachLine(staLines, indexBegin, sta.getSlipCode2(), msgOutbound.getSource(), msgOutbound.getExtMemo());
            // 将商品集合转json
            BzWlbOrderCreateRequest.orderItems orderItems = new BzWlbOrderCreateRequest.orderItems();
            orderItems.setOrder_item_list(infoList);
            String orderItemsJson = JSONObject.fromObject(orderItems).toString();
            // 设置订单商品列表
            orderCreate.setOrderItemList(orderItemsJson);
            if (staLines.size() / 50 == i) {
                orderCreate.setIsFinished(true);
            } else {
                orderCreate.setIsFinished(false);
            }
            indexBegin += 50;
            MsgOutboundOrder msgOutboundOrder = msgOutboundOrderDao.getByPrimaryKey(msgOutbound.getId());
            try {
                log.info("Call hab wlbOrderCreate interface------------------->BEGIN");
                BzWlbOrderCreateResponse wlbOrderCreate = topRmiService.wlbOrderCreate(orderCreate);
                if (StringUtils.hasLength(wlbOrderCreate.getOrderCode())) {
                    Date date2 = new Date();
                    String StringDates = Long.toString(date2.getTime());
                    orderCreate.setOutBizCode(StringDates); // 外部订单业务ID
                    orderCreate.setOrderCode(wlbOrderCreate.getOrderCode()); // 第二次传记录上一次的订单编号
                    if (orderCreate.getIsFinished()) {
                        sta.setWlbOrderCode(wlbOrderCreate.getOrderCode());
                        msgOutboundOrder.setWlbCode(wlbOrderCreate.getOrderCode());
                        msgOutboundOrder.setStatus(DefaultStatus.FINISHED);
                    } else {
                        msgOutboundOrder.setStatus(DefaultStatus.EXECUT_CREATE);
                    }
                    log.info("Call hab wlbOrderCreate interface------------------->END");
                } else {
                    if (msgOutboundOrder.getErrorCount() == null || msgOutboundOrder.getErrorCount().equals("")) {
                        msgOutboundOrder.setErrorCount(0l);
                    }
                    if (orderCreate.getIsFinished()) {
                        msgOutboundOrder.setRemark(wlbOrderCreate.getErrorMessage());
                        msgOutboundOrder.setErrorCount(msgOutboundOrder.getErrorCount() + 1);
                        msgOutboundOrder.setStatus(DefaultStatus.CANCELED);
                    }
                    log.debug("Call hab wlbOrderCreate interface error:");
                }

            } catch (Exception e) {
                if (msgOutboundOrder.getErrorCount() == null || msgOutboundOrder.getErrorCount().equals("")) {
                    msgOutboundOrder.setErrorCount(0l);
                }
                msgOutboundOrder.setRemark(e.getMessage());
                msgOutboundOrder.setErrorCount(msgOutboundOrder.getErrorCount() + 1);
                msgOutboundOrder.setStatus(DefaultStatus.CANCELED);
                log.error("Call hab wlbOrderCreate interface error:");
                log.error("", e);
                break;
            }
        }
    }

    /**
     * 物流宝出库查询
     */
    public void queryWlbOutbound(StockTransApplicationCommand sta) {
        if (sta.getIntStaType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
            BzWlbTmsOrderQueryResponse response = topRmiService.wlbTmsOrderQuery(sta.getWlbOrderCode(), Long.parseLong(sta.getShopId()));
            if (response.getList().size() > 0) {
                if (StringUtils.hasLength(response.getList().get(0).getCode()) && StringUtils.hasLength(response.getList().get(0).getCompanyCode()) && StringUtils.hasLength(response.getList().get(0).getOrderCode())
                        && response.getList().get(0).getOrderCode().equals(sta.getWlbOrderCode())) {
                    // 物流宝反馈 成功，查询作业单明细，插入入库反馈表。
                    List<StaLine> staLine = staLineDao.findByStaId(sta.getId());
                    // 保存出库反馈
                    MsgRtnOutbound msgOutbound = new MsgRtnOutbound();
                    MsgRtnOutbound out = msgRtnOutboundDao.findByStaCode(sta.getCode());
                    if (out != null) {
                        return;
                    }
                    msgOutbound.setLpCode(response.getList().get(0).getCompanyCode());
                    msgOutbound.setTrackingNo(response.getList().get(0).getCode());
                    msgOutbound.setSource(sta.getWhSource());
                    msgOutbound.setSourceWh(sta.getWhSourceCode());
                    msgOutbound.setCreateTime(new Date());
                    msgOutbound.setStaCode(sta.getCode());
                    msgOutbound.setStatus(DefaultStatus.CREATED);
                    msgOutbound.setWlbCode(sta.getWlbOrderCode());
                    // msgOutbound.setWeight(info.getWeight());
                    msgOutbound.setType(sta.getIntStaType().toString());
                    msgOutbound.setVersion(0);
                    msgRtnOutboundDao.save(msgOutbound);
                    // 保存出库反馈明细
                    for (StaLine lines : staLine) {
                        MsgRtnOutboundLine orderLine = new MsgRtnOutboundLine();
                        orderLine.setQty(lines.getQuantity());
                        orderLine.setSkuCode(lines.getSku().getCode());
                        orderLine.setBarCode(lines.getSku().getBarCode());
                        orderLine.setInvStatus(lines.getInvStatus());
                        orderLine.setMsgOutbound(msgOutbound);
                        orderLine.setVersion(0);
                        msgRtnOutboundLineDao.save(orderLine);
                    }
                }
            } else {
                log.info("wlb outBound query no Date");
            }
        } else {
            // 其他出（库间移动、移库入库）
            try {
                BzWlbOrderPageGetRequest reqGet = new BzWlbOrderPageGetRequest();
                reqGet.setOrderCode(sta.getWlbOrderCode());
                reqGet.setOrderType("NORMAL_OUT"); // 出库
                reqGet.setOrderSubType("OTHER"); // 其他
                reqGet.setPageNo(1l);
                reqGet.setPageSize(20l);
                reqGet.setWmsShopId(Long.parseLong(sta.getShopId()));
                BzWlbOrderPageGetResponse resGet = topRmiService.wlbOrderPageGet(reqGet);
                if (resGet.getBzWlborder() != null && resGet.getBzWlborder().size() > 0) {
                    List<BzWlbOrder> bzWlborder = resGet.getBzWlborder();
                    for (BzWlbOrder bzWlbOrder2 : bzWlborder) {
                        // 100 || 200 代表菜鸟做出库，可以进行明细查询
                        if (!"100".equals(bzWlbOrder2.getOrderStatus()) || !"200".equals(bzWlbOrder2.getOrderStatus())) {
                            continue;
                        }
                    }
                } else {
                    return;
                }
                BzWlbOrderitemPageGetRequest request = new BzWlbOrderitemPageGetRequest();
                request.setOrderCode(sta.getWlbOrderCode());
                request.setWmsShopId(Long.parseLong(sta.getShopId()));
                request.setPageNo(1l);
                request.setPageSize(50l);
                BzWlbOrderitemPageGetResponse response = topRmiService.wlbOrderItemPageGet(request);
                MsgRtnOutbound msgOutbound = new MsgRtnOutbound();
                if (response.getOrderItemList() != null && response.getOrderItemList().size() > 0) {
                    for (int i = 0; i <= response.getTotalCount() / 50; i++) {
                        if (i > 0) {
                            request.setPageNo(i + 1l);
                            response = topRmiService.wlbOrderItemPageGet(request);
                        }
                        Map<String, Long> map = new HashMap<String, Long>();
                        for (BzOrderItemList msg : response.getOrderItemList()) {
                            if (msg.getRealQuantity() != null && msg.getRealQuantity() > 0 && (msg.getRealQuantity() == msg.getPlanQuantity() || msg.getConfirmStatus().equals("CONFIRMED"))) {
                                if (map.get(msg.getOrderCode()) == null) {
                                    map.put(msg.getOrderCode(), msg.getItemId());
                                    msgOutbound.setSource(sta.getWhSource());
                                    msgOutbound.setSourceWh(sta.getWhSourceCode());
                                    msgOutbound.setSlipCode(sta.getSlipCode());
                                    msgOutbound.setWlbCode(sta.getWlbOrderCode());
                                    msgOutbound.setCreateTime(new Date());
                                    msgOutbound.setStaCode(sta.getCode());
                                    msgOutbound.setStatus(DefaultStatus.CREATED);
                                    msgOutbound.setType(sta.getIntStaType().toString());
                                    msgOutbound.setVersion(0);
                                    msgOutbound.setRemark(msg.getRemark());
                                    msgRtnOutboundDao.save(msgOutbound);
                                }
                                WarehouseMsgSku sku = warehouseMsgSkuDao.getMsgSkuByWlbIdAndWh(msg.getItemId().toString(), sta.getWhSourceCode());
                                MsgRtnOutboundLine orderLine = new MsgRtnOutboundLine();
                                orderLine.setQty(msg.getRealQuantity());
                                orderLine.setSkuCode(sku.getCode());
                                orderLine.setBarCode(sku.getBarcode());
                                InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("INVENTORY_TYPE_SELL".equals(msg.getInventoryType()) == true ? "良品" : "残次品", sta.getMainWhId().longValue());
                                orderLine.setInvStatus(invStatus);
                                orderLine.setMsgOutbound(msgOutbound);
                                orderLine.setVersion(0);
                                msgRtnOutboundLineDao.save(orderLine);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Call hab queryWlbNewInbound interface error:");
                log.error("", e);
                throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
            }
        }

    }

    /**
     * 物流宝入库差异查询
     */
    // public void queryInboundNotice(Date beginDate, Date endDate) {
    // BzWlbNotifyMessagePageGetRequest request = new BzWlbNotifyMessagePageGetRequest();
    // request.setMsgCode(BzWlbNotifyMessagePageGetRequest.MSGCODE_STOCK_IN_NOT_CONSISTENT); //
    // 入库单差异不一致
    // // request.setStatus(BzWlbNotifyMessagePageGetRequest.STATUS_NO_NEED_CONFIRM); // 不需要确认
    // request.setPageNo(1l); // 页数
    // request.setPageSize(50l); // 条数
    // request.setStartDate(beginDate); // 开始时间
    // request.setEndDate(endDate); // 结束时间
    // request.setWmsShopId(3344l);
    // boolean isSuccess = false; // 是否成功
    // try {
    // // 每次最大查询50条，超出部分多次查询
    // BzWlbNotifyMessagePageGetResponse response = topRmiService.wlbNotifyMessagePageGet(request);
    // if (response.getHubWlbMessage() != null && response.getHubWlbMessage().size() > 0) {
    // for (int i = 0; i <= response.getHubWlbMessage().size() / 50; i++) {
    // if (i > 0) {
    // isSuccess = false;
    // request.setPageNo(i + 1l);
    // response = topRmiService.wlbNotifyMessagePageGet(request);
    // }
    // Map<String, String> map = new HashMap<String, String>();
    // for (HubWlbMessage msg : response.getHubWlbMessage()) {
    // isSuccess = false;
    // // 解析 msgContent字符串
    // String st = msg.getMsgContent();
    // String arr[] = st.split(";");
    // String relQty = "", orderCode = "", itemId = "", planQty = "";
    // for (int j = 0; j < arr.length; j++) {
    // if (arr[j].contains("relQuantity")) {
    // relQty = arr[j].substring(arr[j].indexOf("relQuantity:") + 12, arr[j].length());
    // } else if (arr[j].contains("orderCode")) {
    // orderCode = arr[j].substring(arr[j].indexOf("orderCode:") + 10, arr[j].length());
    // } else if (arr[j].contains("itemId")) {
    // itemId = arr[j].substring(arr[j].indexOf("itemId:") + 7, arr[j].length());
    // } else if (arr[j].contains("planQuantity")) {
    // planQty = arr[j].substring(arr[j].indexOf("planQuantity:") + 13, arr[j].length());
    // }
    // }
    // MsgRtnInboundOrder msgInbound = new MsgRtnInboundOrder();
    // MsgInboundOrder msgIn = new MsgInboundOrder();
    // if (map.get(orderCode) == null) {
    // // 保存入库反馈
    // map.put(orderCode, itemId);
    // MsgInboundOrder inb = msgInboundOrderDao.getByWlbCode(orderCode);
    // msgIn = msgInboundOrderDao.findByStaCode(inb.getStaCode());
    // msgInbound.setCreateTime(new Date());
    // msgInbound.setInboundTime(msg.getGmtCreate());
    // msgInbound.setSource(msgIn.getSource());
    // msgInbound.setSourceWh(msgIn.getSourceWh());
    // msgInbound.setStaCode(inb.getStaCode());
    // msgInbound.setStatus(DefaultStatus.CREATED);
    // msgInbound.setType(inb.getType().getValue());
    // msgInbound.setVersion(0);
    // msgInbound.setShopId(msgIn.getShopId());
    // msgInbound.setRemark(msg.getMsgDescription());
    // msgRtnInboundOrderDao.save(msgInbound);
    // }
    // // 保存入库反馈明细
    // WarehouseMsgSku sku = warehouseMsgSkuDao.getMsgSkuByWlbIdAndWh(itemId, msgIn.getSourceWh());
    // MsgRtnInboundOrderLine orderLine = new MsgRtnInboundOrderLine();
    // orderLine.setQty(Long.parseLong(planQty) - Long.parseLong(relQty));
    // orderLine.setSkuCode(sku.getCode());
    // Sku skus = skuDao.getByCode(sku.getCode());
    // orderLine.setSkuId(skus.getId());
    // StaLine lines = staLineDao.findStaLineBySkuId(skus.getId(), sta.getId());
    // orderLine.setInvStatus(lines.getInvStatus());
    // orderLine.setMsgRtnInOrder(msgInbound);
    // msgRtnInboundOrderLineDao.save(orderLine);
    // isSuccess = true;
    // }
    // }
    // if (isSuccess) {
    // // 记录执行时间
    // msgInboundOrderDao.insertWlbInQueryTime(1);
    // }
    // }
    // } catch (Exception e) {
    // log.error("Call hab queryInboundNotice interface error:");
    // log.error("", e);
    // }
    // }

    /**
     * 物流宝单据取消结果查询
     */
    public void queryOrderCancel(Date beginDate, Date endDate, Long channel) {
        BzWlbNotifyMessagePageGetRequest request = new BzWlbNotifyMessagePageGetRequest();
        // request.setMsgCode(BzWlbNotifyMessagePageGetRequest.MSGCODE_CANCEL_ORDER_SUCCESS); //
        // 取消订单成功
        // request.setStatus(BzWlbNotifyMessagePageGetRequest.STATUS_TO_BE_CONFIRM); // 不需要确认
        request.setPageNo(1l); // 页数
        request.setPageSize(50l); // 条数
        request.setStartDate(beginDate); // 开始时间
        request.setEndDate(endDate); // 结束时间
        request.setWmsShopId(channel);
        boolean isSuccess = false; // 是否成功
        try {
            // 每次最大查询50条，超出部分多次查询
            BzWlbNotifyMessagePageGetResponse response = topRmiService.wlbNotifyMessagePageGet(request);
            if (response.getHubWlbMessage() != null && response.getHubWlbMessage().size() > 0) {
                for (int i = 0; i <= response.getTotalCount() / 50; i++) {
                    if (i > 0) {
                        request.setPageNo(i + 1l);
                        response = topRmiService.wlbNotifyMessagePageGet(request);
                    }
                    for (HubWlbMessage msg : response.getHubWlbMessage()) {
                        if ("CANCEL_ORDER_SUCCESS".endsWith(msg.getMsgCode()) || "CANCEL_ORDER_FAILURE".endsWith(msg.getMsgCode())) {
                            // 解析 msgContent字符串
                            String st = msg.getMsgContent();
                            String orderCode = st.substring(st.indexOf("Code") + 5, st.length() - 1);
                            MsgOutboundOrder inb = msgOutboundOrderDao.getByWlbCode(orderCode);
                            if (inb == null) {
                                continue;
                            }
                            MsgOutboundOrderCancel cancel = msgOutboundOrderCancelDao.getByStaCode(inb.getStaCode());
                            if (cancel != null && "CANCEL_ORDER_SUCCESS".endsWith(msg.getMsgCode())) {
                                isSuccess = true;
                                cancel.setIsCanceled(true);
                                cancel.setUpdateTime(new Date());
                            } else if (cancel != null && "CANCEL_ORDER_FAILURE".endsWith(msg.getMsgCode())) {
                                isSuccess = true;
                                cancel.setIsCanceled(false);
                                cancel.setUpdateTime(new Date());
                            }

                        }
                    }
                }
                if (isSuccess) {
                    // 记录执行时间
                    msgInboundOrderDao.insertWlbInQueryTime(2, endDate);
                }
            }
        } catch (Exception e) {
            log.error("Call hab queryInboundNotice interface error:");
            log.error("", e);
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
    }

    /**
     * 物流宝单据取消推送
     */
    public void wlbOutboundCancelToHab(List<MsgOutboundOrderCancel> skuList) {
        for (MsgOutboundOrderCancel msgCancel : skuList) {
            StockTransApplication sta = staDao.getByCode(msgCancel.getStaCode());
            BiChannel bi = biChannelDao.getByCode(sta.getOwner());
            if (StringUtils.hasLength(sta.getWlbOrderCode()) && bi != null) {
                try {
                    BzResponse response = topRmiService.wlbOrderCancel(sta.getWlbOrderCode(), bi.getId());
                    MsgOutboundOrderCancel cancel = msgOutboundOrderCancelDao.getByPrimaryKey(msgCancel.getId());
                    if (response != null && response.getResults() == 1 && !StringUtils.hasLength(response.getErrorCode()) && !StringUtils.hasLength(response.getErrorMessage())) {
                        // 取消推送
                        cancel.setStatus(MsgOutboundOrderCancelStatus.SENT);
                        cancel.setUpdateTime(new Date());
                    }
                } catch (Exception e) {
                    log.error("Call hab wlbOutboundCancelToHab interface error:");
                    log.error("", e);
                    throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
                }

            }

        }
    }


    /**
     * 外包仓公共邮件通知
     */
    public void msgThreePLEmailNotice(String optionKey, String codeKey, List<WarehouseMsgSku> msgSkus, List<MsgInboundOrder> msgInbound, List<MsgOutboundOrder> msgOutbound, List<MsgRtnInboundOrder> msgRtnInbound, List<MsgRtnOutbound> msgRtnOutboun,
            List<MsgOutboundOrderCancel> msgOutCancel, String sourceValue) {
        // 查询常量表 邮件推送信息
        ChooseOption option = chooseOptionDao.findByCategoryCode(optionKey);
        // 查询邮件模板
        MailTemplate template = mailTemplateDao.findTemplateByCode(codeKey);
        // 查询系统常量表 收件人
        if (option != null && !StringUtil.isEmpty(option.getOptionValue())) {
            if (template != null) {
                String code = ""; // 用于存储Sku编码，以英文逗号隔开
                String source = ""; // 用于存储外包仓来源，以英文逗号隔开
                String errorMsg = ""; // 用于存储失败的异常信息，以英文逗号隔开
                if (msgSkus != null) { // 商品接口邮件内容
                    for (WarehouseMsgSku item : msgSkus) {
                        code += "'" + item.getCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getCode() + ":" + item.getMsg() + "; ";
                    }
                } else if (msgInbound != null) { // 入库接口邮件内容
                    for (MsgInboundOrder item : msgInbound) {
                        code += "'" + item.getStaCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getStaCode() + ":" + item.getRemark() + "; ";
                    }
                } else if (msgOutbound != null) { // 出库接口邮件内容
                    for (MsgOutboundOrder item : msgOutbound) {
                        code += "'" + item.getStaCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getStaCode() + ":" + item.getRemark() + "; ";
                    }
                } else if (msgRtnInbound != null) { // 入库执行失败邮件内容
                    for (MsgRtnInboundOrder item : msgRtnInbound) {
                        code += "'" + item.getStaCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getStaCode() + ":" + item.getRemark() + "; ";
                    }
                } else if (msgRtnOutboun != null) { // 出库执行失败邮件内容
                    for (MsgRtnOutbound item : msgRtnOutboun) {
                        code += "'" + item.getStaCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getStaCode() + ":" + item.getRemark() + "; ";
                    }
                } else if (msgOutCancel != null) { // 出库执行失败邮件内容
                    for (MsgOutboundOrderCancel item : msgOutCancel) {
                        code += "'" + item.getStaCode() + "', ";
                        source += "'" + item.getSource() + "', ";
                        errorMsg += item.getStaCode() + ":" + item.getMsg() + "; ";
                    }
                }
                code = code.substring(0, code.length() - 2);
                source = source.substring(0, source.length() - 2);
                errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
                String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("$"));// 邮件内容
                String mailHtml = template.getMailBody().substring(template.getMailBody().indexOf("$") + 1, template.getMailBody().length());// 邮件样式
                String subject = sourceValue + template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                // 替换样式内容
                String tcontent = mailHtml.replaceAll("mailBody", mailBody);
                if (!StringUtil.isEmpty(sourceValue)) {
                    tcontent = tcontent.replaceAll("STRING0", sourceValue);
                }
                tcontent = tcontent.replaceAll("STRING1", code);
                tcontent = tcontent.replaceAll("STRING2", source);
                tcontent = tcontent.replaceAll("STRING3", errorMsg);
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", tcontent, true, null);
                if (bool) {
                    if (msgSkus != null) {
                        // 商品邮件通知成功修改状态
                        for (WarehouseMsgSku items : msgSkus) {
                            warehouseMsgSkuDao.updateMsgSkuById(21l, items.getId());
                        }
                    } else if (msgInbound != null) {
                        // 入库邮件通知成功修改标识
                        for (MsgInboundOrder items : msgInbound) {
                            msgInboundOrderDao.updateInboundById(items.getId());
                        }
                    } else if (msgOutbound != null) {
                        // 出库邮件通知成功修改标识
                        for (MsgOutboundOrder items : msgOutbound) {
                            msgOutboundOrderDao.updateOutboundById(items.getId());
                        }
                    } else if (msgRtnInbound != null) {
                        // 入库执行邮件通知成功修改标识
                        for (MsgRtnInboundOrder items : msgRtnInbound) {
                            msgRtnInboundOrderDao.updateRtnInboundById(items.getId());
                        }
                    } else if (msgRtnOutboun != null) {
                        // 出库执行邮件通知成功修改标识
                        for (MsgRtnOutbound items : msgRtnOutboun) {
                            msgRtnOutboundDao.updateRtnOutboundById(items.getId());
                        }
                    } else if (msgOutCancel != null) {
                        // 取消执行失败邮件通知成功修改标识
                        for (MsgOutboundOrderCancel items : msgOutCancel) {
                            msgOutboundOrderCancelDao.updateMsgCancelById(items.getId());
                        }
                    }
                } else {
                    // 通知失败修改状态
                    if (msgSkus != null) {
                        for (WarehouseMsgSku items : msgSkus) {
                            warehouseMsgSkuDao.updateMsgSkuById(20l, items.getId());
                        }
                    }
                    log.debug("邮件通知失败,请联系系统管理员！");
                }
            } else {
                log.debug("邮件模板不存在或被禁用");
            }
        } else {
            log.debug("邮件通知失败,收件人为空！");
        }
    }

    /*
     * 发送wx出库通知
     */
    @Override
    public void wxOutountMq(String staCode, String mailno) {
        // 获取hub所需要的数据
        StaDeliveryInfoCommand staD = staDeliveryInfoDao.getWxData(staCode, mailno, new BeanPropertyRowMapper<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class)); // 获取商品的数据
        if (staD == null) {
            return;
        }
        List<PackageInfoCommand> pa = packageInfoDao.getpackageInfoByDeliveryInfo(staD.getId(), staD.getLpCode(), new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
        // 重量转为克
        String weith = "";
        BigDecimal b1 = staD.getWeight();
        BigDecimal b2 = new BigDecimal(1000);
        Long weight = (long) b1.multiply(b2).doubleValue();
        weith = weight.toString();
        BzConsignTopRequestDto conDto = new BzConsignTopRequestDto();
        BzConsignTopInfoDto consignInfo = new BzConsignTopInfoDto();
        // 如果是多包裹 订单号加运单号, 否则 订单号不加运单号
        conDto.setConfirmConsign(false);
        consignInfo.setOutTradeNo(staD.getSlipCode2() + "_" + staD.getTrackingNo());
        consignInfo.setConsignType(1L);// 0 : 商家自己联系（线下快递公司） 1： 菜鸟在线发货（菜鸟配或者菜鸟仓发货）
        ChooseOption wxCode = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.WX_OUT_CODE_TYPE, staD.getChannelId().toString());
        consignInfo.setLogisticsSolutionsCode(wxCode.getOptionValue()); // -- 包裹信息
        List<BzConsignGoodsDto> goods = new ArrayList<BzConsignGoodsDto>();
        for (int i = 0; i < pa.size(); i++) {
            BzConsignGoodsDto good = new BzConsignGoodsDto();
            ConsignPackageDto con = new ConsignPackageDto(); // 包裹附件信息
            con.setPackageWeight(weith);
            con.setPackageId(staD.getPackageId());
            con.setPackageLength(pa.get(i).getSkuLength());
            con.setPackageWidth(pa.get(i).getSkuWidth());
            con.setPackageHeight(pa.get(i).getSkuHeight());
            con.setPackageVolume("0");
            good.setItemName(pa.get(i).getSkuName());
            good.setOutSubTradeNo(staD.getSlipCode2());
            good.setQuantity(pa.get(i).getSkuQuantity());
            good.setItemPrice(pa.get(i).getTotalActual().longValue());
            good.setItemId(pa.get(i).getSkuId().toString());
            good.setItemCode(pa.get(i).getSkuCode());
            good.setConsignPackageDto(con);
            goods.add(good);
        }
        consignInfo.setConsignGoods(goods);
        // ----发货信息=======================================
        BzConsignContactDto fa = new BzConsignContactDto();
        fa.setZipCode(staD.getSendZipCode());
        fa.setMobilePhone(staD.getSenderMobile());
        fa.setRemark(staD.getDistrictF());
        fa.setContactName(staD.getPic());
        fa.setAddress(staD.getSenderAddress().replaceAll(" ", ""));
        WhThreePlAreaInfo areaid = whThreePlAreaInfoDao.findAreaIdByName(staD.getCityF(), staD.getDistrictF());
        if (areaid != null) {
            fa.setAreaId(areaid.getAreaId());
        }
        consignInfo.setSenderContact(fa);
        // 收货信息---------------=====================================================================
        BzConsignContactDto shou = new BzConsignContactDto(); //
        WhThreePlAreaInfo areaInfo = whThreePlAreaInfoDao.findAreaIdByName(staD.getCity(), staD.getDistrict());
        if (areaInfo != null) {
            shou.setAreaId(areaInfo.getAreaId());
        }
        shou.setMobilePhone(staD.getMobile());
        shou.setZipCode(staD.getZipcode());
        shou.setRemark(staD.getDistrict());
        shou.setAddress(staD.getAddress().replaceAll(" ", ""));
        shou.setContactName(staD.getReceiver());
        consignInfo.setReceiverContact(shou);
        // 收货信息 =====================================================================

        // 普通配送 =====================================================================
        BzTmsServiceDto ser = new BzTmsServiceDto();
        ser.setMailNo(staD.getTrackingNo());
        ser.setCpCode("default");
        consignInfo.setTmsServiceDto(ser);
        // 普通配送 =====================================================================

        // 退货信息
        BzConsignContactDto rtn = new BzConsignContactDto();
        rtn.setAddress("上海上海市闸北区万荣路1188号");
        rtn.setMobilePhone("13322224444");
        rtn.setDdd("0571");
        rtn.setWangwangId("diagram店铺");
        rtn.setContactName("Chris");
        rtn.setZipCode("200000");
        rtn.setBranch("890");
        rtn.setTelephone("88602334");
        rtn.setRemark("闸北区");
        consignInfo.setRefundContact(rtn);
        // 退货信息 =====================================================================

        conDto.setOrderSource(3L); // 天猫
        conDto.setWmsShopId(staD.getChannelId());
        conDto.setConsignInfo(consignInfo);
        String msg = JSONUtil.beanToJson(conDto);
        // 发送MQ信息数据给到hub
        MqMsgUtil.sendStringMsgToMq(bhMqJmsTemplate, "wms2bh_cainiao_logistics_consign_queue", msg);

    }


    /**
     * Hub发货反馈消息
     */
    public void wxShipmentsReturnInfoMsg(String msg) throws Exception {
        log.debug("msg={}", msg);
        try {
            loxia.support.json.JSONObject json = new loxia.support.json.JSONObject(msg);
            String success = json.get("results").toString(); // 是否发货成功
            int result = Integer.parseInt(success);
            String traceId = "";
            if (json.get("traceId") != null && !json.get("traceId").equals("")) {
                traceId = json.get("traceId").toString();;
            }
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(msg);
            net.sf.json.JSONArray rtnArray = jsonobject.getJSONArray("bzConsignInfoResDtolist");
            List<BzConsignInfoResDtolist> rtnList = new ArrayList<BzConsignInfoResDtolist>();
            // 转换JSON 数据
            for (int i = 0; i < rtnArray.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) rtnArray.get(i);
                BzConsignInfoResDtolist passport = (BzConsignInfoResDtolist) net.sf.json.JSONObject.toBean(object, BzConsignInfoResDtolist.class);
                if (passport != null) {
                    rtnList.add(passport);
                }
            }
            for (BzConsignInfoResDtolist each : rtnList) {
                String trackingNo = each.getMailNo();
                String orderCode = each.getOrderConsignCode();
                String orderWmsCode = each.getOrderWmsCode();
                // 获取日志记录所需要数据
                WxConfirmOrderQueue wx = wXConfirmOrderQueueDao.getWxOrderQueurLogByTrackingNo(trackingNo);
                WxConfirmOrderQueueLog wxlog = new WxConfirmOrderQueueLog();
                // 如果成功
                if (result == 1 && wx != null) {
                    // 1.插入日志记录
                    wxlog.setCheckword(wx.getCheckword());
                    wxlog.setCreateTime(wx.getCreateTime());
                    wxlog.setFilter1(orderWmsCode); // 菜鸟LBX单号
                    wxlog.setFilter2(wx.getFilter2()); // 是否发送
                    // wxlog.setFilter3(traceId); // 菜鸟反馈异常信息ID
                    wxlog.setFilter4(wx.getFilter4()); // 包裹ID
                    wxlog.setFinishTime(new Date());
                    wxlog.setMailno(wx.getMailno());
                    wxlog.setOrderCode(orderCode);
                    wxlog.setOrderId(wx.getOrderId());
                    wxlog.setStaCode(wx.getStaCode());
                    wxlog.setWeight(wx.getWeight());
                    wxconfirmOrderQueueLogDao.save(wxlog);
                    // 2.删除中间表数据
                    wXConfirmOrderQueueDao.delete(wx);
                } else {
                    wx.setFilter3(traceId);
                }
            }
        } catch (Exception e) {
            log.error("");
            log.error("hub 反馈Exception-------------------");
            throw e;
        }
    }


    /**
     * 创建库存调整。 1. 创建库存调整作业单 。2.占用库存。 3. 调用OMS接口。 4. 释放库存。
     * 
     * @return
     */
    public StockTransApplication createAdjustmentSta(MsgRtnAdjustment msgRtnADJ) {
        Warehouse wh = warehouseDao.getWHByVmiSource(msgRtnADJ.getSource(), msgRtnADJ.getSourceWh());
        if (wh == null) {
            log.error("threePL->WMS createAdjustmentSta error ! Warehouse is null");
            throw new BusinessException();
        }
        /** 1. 创建库存调整作业单 */
        StockTransApplication sta = createInvStatusChangeSta(msgRtnADJ, wh);
        /** 2. 占用库存 */
        logger.error("occupy start"+msgRtnADJ.getId());
        wareHouseManagerExe.occupyInventoryCheckMethod(sta.getId());
        logger.error("occupy end"+msgRtnADJ.getId());
        // 根据占用情况重新生成stvLine
        Long outStvId = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND).get(0).getId();
        stvLineDao.deleteByStvId(outStvId);
        stvLineDao.createTIOutByStaId(sta.getId(), outStvId);
        stvLineDao.flush();
        // 修改入库STV
        updateInboundStv(sta);
        // 库存占用成功 状态变更
        sta.setStatus(StockTransApplicationStatus.OCCUPIED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        staDao.save(sta);
        // IM
        hubWmsService.insertOccupiedAndRelease(sta.getId());

        /** 3. 调用OMS接口 */
        logger.error("Call oms inventory status change confirm  封装数据开始------------------->msgRtnADJId:" + msgRtnADJ.getId());
        List<StockTransVoucher> stvLists = stvLineDao.findStvByStaId(sta.getId());
        OperationBill ob = new OperationBill();
        ob.setCode(sta.getCode());
        ob.setType(sta.getType().getValue());
        ob.setMemo(sta.getMemo());
        ob.setWhCode(sta.getMainWarehouse().getCode());
        String innerShopCode = "";
        for (StockTransVoucher st : stvLists) {
            List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
            innerShopCode = staLineDao.findByInnerShopCode2(sta.getId(), new SingleColumnRowMapper<String>(String.class));
            BiChannel companyShop = companyShopDao.getByCode(innerShopCode);
            if (companyShop == null) {
                logger.error("createAdjustmentSta 没有店铺！" + msgRtnADJ.getId());
                throw new BusinessException("");
            }
            List<StvLine> lists = stvLineDao.findStvLineListByStvId(st.getId());
            if (st.getDirection().getValue() == 1) {
                for (int i = 0; i < lists.size(); i++) {
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setSkuCode(lists.get(i).getSku().getCode());
                    operationBillLine.setQty(lists.get(i).getQuantity());
                    operationBillLine.setInvStatusCode(lists.get(i).getInvStatus().getName());
                    operationBillLine.setInvBatchCode(lists.get(i).getBatchCode());
                    operationBillLine.setWhCode(lists.get(i).getWarehouse().getCode());
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(lists.get(i).getOwner() == null ? companyShop.getCode() : lists.get(i).getOwner());
                    billLines.add(operationBillLine);
                }
                ob.setInboundLines(billLines);
            } else if (st.getDirection().getValue() == 2) {
                for (int i = 0; i < lists.size(); i++) {
                    OperationBillLine operationBillLine = new OperationBillLine();
                    operationBillLine.setSkuCode(lists.get(i).getSku().getCode());
                    operationBillLine.setQty(lists.get(i).getQuantity());
                    operationBillLine.setInvStatusCode(lists.get(i).getInvStatus().getName());
                    operationBillLine.setInvBatchCode(lists.get(i).getBatchCode());
                    operationBillLine.setWhCode(lists.get(i).getWarehouse().getCode());
                    // 店铺切换 接口调整-调整渠道编码
                    operationBillLine.setShopCode(lists.get(i).getOwner() == null ? companyShop.getCode() : lists.get(i).getOwner());
                    billLines.add(operationBillLine);
                }
                ob.setOutboundLines(billLines);
            }
        }
        logger.error("Call oms inventory status change confirm  封装数据结束------------------->msgRtnADJId:" + msgRtnADJ.getId());
        try {
            logger.error("Call oms inventory status change confirm interface------------------->staCode:" + ob.getCode());
            logger.error("Call oms inventory status change confirm interface------------------->BEGIN"+msgRtnADJ.getId());
            BaseResult baseResult = rmi4Wms.operationBillCreate(ob);
            if (baseResult.getStatus() == 0) {
                logger.error("OMSERROR:" + baseResult.getMsg());
                throw new BusinessException(baseResult.getMsg());
            }
            logger.error("Call oms inventory status change confirm interface------------------->END"+msgRtnADJ.getId());
            String slipCode = baseResult.getSlipCode();
            sta.setRefSlipCode(slipCode);
            // 添加店铺
            sta.setOwner(innerShopCode);
            staDao.save(sta);
        } catch (BusinessException e) {
            log.error("createAdjustmentSta1"+msgRtnADJ.getId()+"  "+e);
            throw e;
        } catch (Exception e) {
            log.error("createAdjustmentSta2"+msgRtnADJ.getId()+"   "+e);
            throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
        }
        return sta;
    }

    private void updateInboundStv(StockTransApplication sta) {
        List<StockTransVoucher> outStv = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.OUTBOUND);
        List<StockTransVoucher> inStv = stvDao.findByStaWithDirection(sta.getId(), TransactionDirection.INBOUND);
        List<StvLine> outList = stvLineDao.findStvLineListByStvId(outStv.get(0).getId());
        List<StvLine> inList = stvLineDao.findStvLineListByStvId(inStv.get(0).getId());
        for (StvLine cmd : outList) {
            for (StvLine stvLine : inList) {
                if (stvLine.getSku().getId() == cmd.getSku().getId()) {
                    stvLine.setDistrict(cmd.getLocation().getDistrict());
                    stvLine.setLocation(cmd.getLocation());
                    stvLine.setOwner(cmd.getOwner());
                    stvLine.setBatchCode(cmd.getBatchCode()); // 设置出库的批次号
                    stvLineDao.save(stvLine);
                }
            }
        }
    }

    private StockTransApplication createInvStatusChangeSta(MsgRtnAdjustment msgRtnADJ, Warehouse wh) {
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setSlipCode1(msgRtnADJ.getOrderCode());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, wh.getOu().getId());
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.INVENTORY_STATUS_CHANGE);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setIsNeedOccupied(false);
        sta.setIsLocked(true);
        sta.setOwner(msgRtnADJ.getOwnerSource()); // 新增店铺
        sta.setMainWarehouse(wh.getOu());
        sta.setMemo(msgRtnADJ.getMemo());
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        // 创建库存调整STV -- 出
        StockTransVoucher stv = new StockTransVoucher();
        BigDecimal bn = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv.setBusinessSeqNo(bn.longValue());
        stv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        stv.setCreateTime(new Date());
        stv.setDirection(TransactionDirection.OUTBOUND);
        stv.setOwner(sta.getOwner());
        stv.setSta(sta);
        stv.setTransactionType(type);
        stv.setLastModifyTime(new Date());
        stv.setMode(InboundStoreMode.TOGETHER);
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setWarehouse(sta.getMainWarehouse());
        stvDao.save(stv);
        // // 创建库存调整STV -- 入
        StockTransVoucher stv2 = new StockTransVoucher();
        BigDecimal bn2 = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        stv2.setBusinessSeqNo(bn2.longValue());
        stv2.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        stv2.setCreateTime(new Date());
        stv2.setDirection(TransactionDirection.INBOUND);
        stv2.setOwner(sta.getOwner());
        stv2.setSta(sta);
        stv2.setTransactionType(type);
        stv2.setMode(InboundStoreMode.TOGETHER);
        stv2.setLastModifyTime(new Date());
        stv2.setStatus(StockTransVoucherStatus.CREATED);
        stv2.setWarehouse(sta.getMainWarehouse());
        stvDao.save(stv2);
        List<MsgRtnAdjustmentLine> listLine = msgRtnAdjustmentLineDao.queryLineByADJId(msgRtnADJ.getId());
        if (listLine == null || listLine.size() == 0) {
            throw new BusinessException();
        }
        // 创作业单明细
        List<StaLine> staLineList = new ArrayList<StaLine>();
        for (MsgRtnAdjustmentLine adjLine : listLine) {
            if (adjLine.getId() != null) {
                Sku sku = skuDao.getByPrimaryKey(adjLine.getSkuId());
                if (sku == null) {
                    throw new BusinessException();
                }
                // 数量为负的是 出，原库存状态
                if (adjLine.getQty() < 0) {
                    // 创建STAline
                    StaLine staLine = new StaLine();
                    staLine.setQuantity(Math.abs(adjLine.getQty()));
                    staLine.setVersion(1);
                    staLine.setInvStatus(adjLine.getInvStatus());
                    staLine.setOwner(sta.getOwner());
                    staLine.setSku(sku);
                    staLine.setSta(sta);
                    staLineDao.save(staLine);
                    staLineList.add(staLine);
                    // 创建库存调整STVLine -- 出
                    StvLine stvl = new StvLine();
                    stvl.setDirection(TransactionDirection.OUTBOUND);
                    stvl.setQuantity(Math.abs(adjLine.getQty()));
                    stvl.setInvStatus(adjLine.getInvStatus());
                    stvl.setWarehouse(sta.getMainWarehouse());
                    stvl.setSku(sku);
                    stvl.setOwner(sta.getOwner());
                    stvl.setStaLine(staLine);
                    stvl.setStv(stv);
                    stvl.setTransactionType(stv.getTransactionType());
                    stvl.setInBoundTime(new Date());
                    stvl.setVersion(0);
                    // TODO 第一期不做效期，故stv明细不插入过期时间
                    stvLineDao.save(stvl);
                } else {
                    // 创建库存调整STVLine -- 入
                    StvLine stvl2 = new StvLine();
                    stvl2.setDirection(TransactionDirection.INBOUND);
                    stvl2.setQuantity(Math.abs(adjLine.getQty()));
                    stvl2.setInvStatus(adjLine.getInvStatus());
                    stvl2.setWarehouse(sta.getMainWarehouse());
                    stvl2.setOwner(sta.getOwner());
                    stvl2.setTransactionType(type);
                    stvl2.setSku(sku);
                    // 匹配sta line
                    for (StaLine line : staLineList) {
                        if (line.getSku().getId().equals(stvl2.getSku().getId())) {
                            stvl2.setStaLine(line);
                            break;
                        }
                    }
                    stvl2.setStv(stv2);
                    stvl2.setTransactionType(stv.getTransactionType());
                    stvl2.setInBoundTime(new Date());
                    stvl2.setVersion(0);
                    // TODO 第一期不做效期，故stv明细不插入过期时间
                    stvLineDao.save(stvl2);
                }
            }
        }
        stvDao.flush();
        return sta;
    }

    /**
     * 外包仓库存状态修改执行 1.释放库存，新增调整库存 2.通知OMS
     */
    public void executeAdjustmentSta(Long staId) {
        List<StockTransVoucher> stvList = stvDao.findByStaWithDirection(staId, TransactionDirection.OUTBOUND);
        if (stvList == null || stvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StockTransVoucher outStv = stvList.get(0);
        // 删除占用库存
        removeInventory(sta, stvList.get(0));
        outStv.setFinishTime(new Date());
        outStv.setStatus(StockTransVoucherStatus.FINISHED);
        outStv.setLastModifyTime(new Date());
        stvDao.save(outStv);
        stvDao.flush();
        List<StockTransVoucher> inStvList = stvDao.findByStaWithDirection(staId, TransactionDirection.INBOUND);
        if (inStvList == null || inStvList.size() == 0) {
            throw new BusinessException(ErrorCode.STV_NOT_FOUND);
        }
        List<StvLine> stvLine = stvLineDao.findStvLineListByStvId(inStvList.get(0).getId());
        if (stvLine == null || stvLine.size() == 0) {
            throw new BusinessException();
        }
        wareHouseManager.purchaseReceiveStep2(inStvList.get(0).getId(), stvLine, true, null, false, true);
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
        User user = stv.getOperator();
        /*
         * BiChannel shop = companyShopDao.getByCode(stv.getOwner()); Map<String, BiChannel> shopMap
         * = new HashMap<String, BiChannel>(); if (shop != null) { shopMap.put(shop.getCode(),
         * shop); }
         */
        stv.setFinishTime(new Date());
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
            log.setStvId(stv.getId());
            log.setInboundTime(inv.getInboundTime());
            log.setBatchCode(inv.getBatchCode());
            /** -------------新建保质期字段---------------- */
            log.setValidDate(inv.getValidDate());
            log.setExpireDate(inv.getExpireDate());
            log.setProductionDate(inv.getProductionDate());
            /** -------------新建保质期字段---------------- */
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
     * 库存数量调整 -- 创建库存盘点
     * 
     * @param msgRtnADJ
     * @return
     */
    public void createThreePLAdjustment(MsgRtnAdjustment msgRtnADJ) {
        List<ChooseOption> chooseOptionList = chooseOptionDao.findAllOptionListByCategoryCode("InvChange");
        if (chooseOptionList != null && chooseOptionList.size() > 0 && chooseOptionList.get(0).getOptionValue().equals("1")) {
            executeIDSInvAdjustADJ(msgRtnADJ.getId());
        } else {
            InventoryCheck ic = new InventoryCheck();
            ic.setCreateTime(new Date());
            ic.setStatus(InventoryCheckStatus.CREATED);
            ic.setType(InventoryCheckType.NORMAL);
            ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
            ic.setRemork(msgRtnADJ.getSource());
            ic.setReasonCode(msgRtnADJ.getOrderCode()); // 存放外编码
            Warehouse wh = warehouseDao.getBySource(msgRtnADJ.getSource(), msgRtnADJ.getSourceWh());
            BiChannel channel = biChannelDao.getByCode(msgRtnADJ.getOwnerSource()); // 店铺信息
            if (wh != null) {
                ic.setOu(wh.getOu());
            }
            if (channel != null) {
                ic.setShop(channel);
            }
            invDao.save(ic);
            InventoryCheckDifTotalLine line = null;
            List<MsgRtnAdjustmentLine> listLine = msgRtnAdjustmentLineDao.queryLineByADJId(msgRtnADJ.getId());
            if (listLine == null || listLine.size() == 0) {
                throw new BusinessException("系统异常");
            }
            for (MsgRtnAdjustmentLine adjLine : listLine) {
                if (adjLine.getId() != null) {
                    Sku sku = skuDao.getByPrimaryKey(adjLine.getSkuId());
                    if (sku == null) {
                        throw new BusinessException();
                    }
                    line = new InventoryCheckDifTotalLine();
                    line.setInventoryCheck(ic);
                    line.setSku(sku);
                    line.setStatus(adjLine.getInvStatus());
                    line.setQuantity(adjLine.getQty());
                    icLineDao.save(line);
                }
            }
            updateADJStatus(msgRtnADJ, DefaultStatus.FINISHED);
            noticeOMSAdjustment(ic);
        }

    }

    /**
     * 占用库存，通知OMS调整库存
     */
    public void noticeOMSAdjustment(InventoryCheck ic) {
        if (ic == null || !InventoryCheckStatus.CREATED.equals(ic.getStatus())) {
            log.error("THREEPL->WMS Error! Adjustment execution ,Status is error!");
            throw new BusinessException();
        }
        List<InventoryCheckDifTotalLine> lineList = icLineDao.findByInventoryCheck(ic.getId());
        for (InventoryCheckDifTotalLine line : lineList) {
            InventoryCheckDifferenceLine icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(line.getSku());
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setStatus(line.getStatus());
            if (line.getQuantity() > 0) {
                if (ic.getShop() != null) {
                    icdifference.setOwner(ic.getShop().getCode());
                } else {
                    // 盘盈 无需占用库存，获取该商品库存日志，入库的店铺
                    StockTransTxLogCommand stlog = stockTransTxLogDao.findOwnerByWHAndType(ic.getOu().getId(), line.getSku().getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    if (stlog != null) {
                        icdifference.setOwner(stlog.getOwner());
                    }
                }

                WarehouseLocation location = locDao.findOneWarehouseLocationByOuid(ic.getOu().getId());
                if (location != null) {
                    icdifference.setLocation(location);
                }
            } else {
                if (ic.getShop() != null) {
                    icdifference.setOwner(ic.getShop().getCode());
                }
            }
            icDifferenceLineDao.save(icdifference);
        }
        ic.setStatus(InventoryCheckStatus.CHECKWHINVENTORY);
        invDao.save(ic);
        invDao.flush();
        // 修改状态为仓库经理确认
        InventoryCheck ics = inventoryCheckDao.findByCode(ic.getCode());
        ics.setStatus(InventoryCheckStatus.CHECKWHMANAGER);
        ics.setManagerTime(new Date());
        inventoryCheckDao.flush();
        // wareHouseManager.managerConfirmCheck(ic.getId(), ic.getCode(), null, true);
        // 基于盘点单占用库存 无库位
        try {
            Map<String, Object> invparams1 = new HashMap<String, Object>();
            invparams1.put("in_ic_id", ics.getId());
            SqlParameter[] occinvp = {new SqlParameter("in_ic_id", Types.NUMERIC)};
            // inventoryCheckDao.executeSp("sp_inventory_check_opc_inv", occinvp, invparams1);
            inventoryCheckDao.executeSp("sp_inv_check_opc_inv_noloc", occinvp, invparams1);
            // 重新生成差异明细 1、删除盘亏明细 2、根据库存占用重新生成明细
            inventoryCheckDifferenceLineDao.deleteOutDifferentLineById(ics.getId());
            inventoryCheckDifferenceLineDao.reCreateOutDifferentLineById(ics.getCode(), ics.getId());
            inventoryCheckDifferenceLineDao.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("noticeOMSAdjustment:" + ic.getCode(), e);
            }
        }
        // 通知OMS
        wareHouseManager.managerCheckforoms(ic.getCode());
    }

    // 更新数据
    public void updateADJStatus(MsgRtnAdjustment msgRtnADJ, DefaultStatus status) {
        msgRtnADJ.setStatus(status);
        msgRtnAdjustmentDao.save(msgRtnADJ);
    }


    /**
     * 3.1 外包仓商品接口 -- 宝尊WMS通知第三方仓储实际仓库作业中的商品信息，当商品信息发生变更也使用此接口推送信息
     */
    public String wmsSku(String customer, String requst) {
        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(requst);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            // 查询越海外包仓关联的品牌
            // List<Long> brandList = skuWarehouseRefDao.findBrandByRefSource(customer, new
            // SingleColumnRowMapper<Long>(Long.class));
            // 根据时间段查询品牌的商品
            List<WarehouseMsgSkuCommand> skuList = warehouseMsgSkuDao.findMsgSKuByTime(startTime, endTime, customer, new BeanPropertyRowMapperExt<WarehouseMsgSkuCommand>(WarehouseMsgSkuCommand.class));
            List<WmsSku> entry = new ArrayList<WmsSku>();
            for (int i = page * pageSize - pageSize; i < skuList.size(); i++) {
                if (i == page * pageSize) {
                    break;
                    // 当下标等于查询的最大条数时，跳出循环
                }
                WmsSku sku = new WmsSku();
                if (skuList.get(i).getValidDate() != 0) {
                    sku.setValidDate(skuList.get(i).getValidDate());
                }
                sku.setUuid(skuList.get(i).getUuid().toString());
                // 外包仓商品对接定制
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                if (choose != null) {
                    if (choose.getOptionValue().equals("CODE")) {
                        sku.setCode(skuList.get(i).getCode());
                    } else if (choose.getOptionValue().equals("BARCODE")) {
                        sku.setCode(skuList.get(i).getBarcode());
                    } else if (choose.getOptionValue().equals("EXTCODE1")) {
                        sku.setCode(skuList.get(i).getExtCode1());
                    } else if (choose.getOptionValue().equals("EXTCODE2")) {
                        sku.setCode(skuList.get(i).getExtCode2());
                    }
                } else {
                    sku.setCode(skuList.get(i).getCode());
                }
                sku.setCategories(skuList.get(i).getCategories());
                sku.setBarcode(skuList.get(i).getBarcode());
                // 条码明细转换成数组findBySkuId
                List<WarehouseMsgSkuBarcode> barCodes = warehouseMsgSkuBarcodeDao.getSkuBarcodeByMsgId(skuList.get(i).getId());
                int j = 0;
                if (barCodes != null && barCodes.size() > 0) {
                    String[] barCodesArray = new String[barCodes.size()];
                    for (WarehouseMsgSkuBarcode skuBarcodes : barCodes) {
                        barCodesArray[j] = skuBarcodes.getBarcode();
                        j++;
                    }
                    sku.setBarcodes(barCodesArray);
                }
                sku.setSupplierCode(skuList.get(i).getSupplierCode());
                if (StringUtils.hasText(skuList.get(i).getName())) {
                    sku.setName(skuList.get(i).getName().replaceAll("\"", " "));
                } else {
                    sku.setName(skuList.get(i).getName());
                }
                if (StringUtils.hasText(skuList.get(i).getEnName())) {
                    sku.setName(skuList.get(i).getEnName().replaceAll("\"", " "));
                } else {
                    sku.setEnName(skuList.get(i).getEnName());
                }

                // 设置ext_memo
                ChooseOption choose2 = chooseOptionDao.findByCategoryCodeAndKey("extMemo", "1");
                if (choose2 != null) {
                    JSONObject jsono = new JSONObject();
                    if (skuList.get(i) != null && skuList.get(i).getExtCode1() != null) {
                        jsono.put("extCode1", skuList.get(i).getExtCode1());
                        sku.setExtMemo(jsono.toString());
                    }
                }

                sku.setColor(skuList.get(i).getColor());
                sku.setSize(skuList.get(i).getSkuSize());
                sku.setBrand(skuList.get(i).getBrandName());
                sku.setLength(skuList.get(i).getLength());
                sku.setWidth(skuList.get(i).getWidth());
                sku.setHeight(skuList.get(i).getHeight());
                sku.setIsSn(skuList.get(i).getIsSn());
                sku.setSource(customer);
                sku.setCustomer(customer);
                sku.setWhCode(skuList.get(i).getWhCode());
                // 是否保质期商品
                if ("33".equals(skuList.get(i).getStoreMode())) {
                    sku.setIsValidityPeriod(true);
                } else {
                    sku.setIsValidityPeriod(false);
                }
                entry.add(sku);
            }
            // 商品数据转JSON
            WmsSkuRespose response = new WmsSkuRespose();
            MsgSkuRequsetMessage msg = new MsgSkuRequsetMessage();
            msg.setTotal(skuList.size());
            msg.setEntry(entry);
            response.setMessage(msg);
            response.setCustomer(customer);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------wmsSku error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.2 外包仓入库单接口-- 入库单接口提供给第三方仓储系统入库单数据，以便第三方仓储根据入库单据数据处理后续的收货流程。
     * 
     * @return
     */
    public String wmsInboundOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            // 时间段查询外包仓入库数据 非退货入
            List<MsgInboundOrder> inList = msgInboundOrderDao.findMsgInListByDate(startTime, endTime, customer, new BeanPropertyRowMapperExt<MsgInboundOrder>(MsgInboundOrder.class));
            List<WmsInboundOrder> msgIn = new ArrayList<WmsInboundOrder>();
            for (int i = page * pageSize - pageSize; i < inList.size(); i++) {
                if (i == page * pageSize) {
                    break;
                    // 当下标等于查询的最大条数时，跳出循环
                }
                WmsInboundOrder inOrder = new WmsInboundOrder();
                StockTransApplication sta = staDao.getByCode(inList.get(i).getStaCode()); // 作业单信息
                BiChannel channel = biChannelDao.getByCode(sta.getOwner()); // 店铺信息
                inOrder.setUuid(inList.get(i).getUuid().toString());
                inOrder.setWarehouseCode(inList.get(i).getSourceWh());
                inOrder.setOrderCode(inList.get(i).getStaCode());
                RelationNike relationNike = relationNikeDao.findSysByPid(sta.getRefSlipCode());
                if (relationNike != null) {
                    inOrder.setSlipCode(relationNike.getEnPid());
                } else {
                    inOrder.setSlipCode(sta.getRefSlipCode());
                }
                inOrder.setSlipCode1(sta.getSlipCode1());
                inOrder.setSlipCode2(sta.getSlipCode2());
                inOrder.setOrderDate(String.valueOf(FormatUtil.formatDate(inList.get(i).getCreateTime(), "yyyyMMddHHmmss")));
                if (inList.get(i).getPlanArriveTime() != null) {
                    inOrder.setArriveTime(String.valueOf(FormatUtil.formatDate(inList.get(i).getPlanArriveTime(), "yyyyMMddHHmmss")));
                }
                inOrder.setType("1");
                inOrder.setOwner(channel.getName());
                inOrder.setMemo(inList.get(i).getRemark());
                if (sta.getMainStatus() != null) {
                    if ("良品".equals(sta.getMainStatus().getName())) {
                        inOrder.setInvStatus("accepted");
                    } else if ("残次品".equals(sta.getMainStatus().getName())) {
                        inOrder.setInvStatus("imperfect");
                    } else {
                        inOrder.setInvStatus("unsales"); // 良品不可销售
                    }
                }
                List<MsgInboundOrderLineCommand> lines = msgInboundOrderLineDao.findVmiMsgInboundOrderLine(inList.get(i).getId(), new BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));
                int j = 0;
                if (lines != null && lines.size() > 0) {
                    WmsInboundOrderLine[] lineArray = new WmsInboundOrderLine[lines.size()];
                    for (MsgInboundOrderLineCommand orderLine : lines) {
                        WmsInboundOrderLine inLine = new WmsInboundOrderLine();
                        if ("良品".equals(orderLine.getInvStsName())) {
                            inLine.setInvStatus("accepted");
                        } else if ("残次品".equals(orderLine.getInvStsName())) {
                            inLine.setInvStatus("imperfect");
                        } else {
                            inLine.setInvStatus("unsales"); // 良品不可销售
                        }
                        inLine.setOwner(channel.getName());
                        inLine.setQty(orderLine.getQty());
                        // 外包仓商品对接定制
                        ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                        if (choose != null) {
                            if (choose.getOptionValue().equals("CODE")) {
                                inLine.setSkuCode(orderLine.getSpuCode());
                            } else if (choose.getOptionValue().equals("BARCODE")) {
                                inLine.setSkuCode(orderLine.getBarCode());
                            } else if (choose.getOptionValue().equals("EXTCODE1")) {
                                inLine.setSkuCode(orderLine.getEcode());
                            } else if (choose.getOptionValue().equals("EXTCODE2")) {
                                inLine.setSkuCode(orderLine.getEcode2());
                            }
                        } else {
                            inLine.setSkuCode(orderLine.getSpuCode());
                        }
                        lineArray[j] = inLine;
                        j++;
                    }
                    inOrder.setLines(lineArray);
                }
                msgIn.add(inOrder);
            }
            // 外包仓入库数据转JSON
            WmsMsgInRespose response = new WmsMsgInRespose();
            MsgInRequsetMessage msgs = new MsgInRequsetMessage();
            msgs.setTotal(inList.size());
            msgs.setEntry(msgIn);
            response.setMessage(msgs);
            response.setCustomer(customer);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------wmsInboundOrder error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.3 外包仓入库单收货反馈接口-- 第三方仓库实际接收完成后将收货数据反馈给宝尊WMS系统
     */
    public String uploadWmsInboundOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = "";
        // 成功返回的参数
        WmsMsgRtnInResponse rtnIn = new WmsMsgRtnInResponse();
        rtnIn.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        ErrorMsg msg = new ErrorMsg();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 入库单唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String inboundDate = inObj.get("inboundTime").toString(); // 实际入库时间
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date inboundTime = df.parse(inboundDate);
            if (!"1".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgInboundOrder msgIn = msgInboundOrderDao.getByCodeAndSource(orderCode, warehouseCode);
            if (msgIn == null) {
                msg.setErrorCode("1000010004");
                msg.setMsg("反馈数据与入库单据不符，请检查入库单唯一对接编码和仓库编码");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // String inRuest = inObj.get("lines").toString();
            // 入库明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsInboundOrderResponseLine> list = new ArrayList<WmsInboundOrderResponseLine>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsInboundOrderResponseLine passport = (WmsInboundOrderResponseLine) net.sf.json.JSONObject.toBean(object, WmsInboundOrderResponseLine.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            // 校验数据
            String errormsg = validateMsgInDate(list, msgIn, uuid);
            if (StringUtils.hasLength(errormsg)) {
                msg.setErrorCode(errormsg.substring(0, errormsg.indexOf("^")));
                msg.setMsg(errormsg.substring(errormsg.indexOf("^") + 1, errormsg.length()));
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 保存入库反馈
            MsgRtnInboundOrder inOrder = new MsgRtnInboundOrder();
            inOrder.setCreateTime(new Date());
            inOrder.setInboundTime(inboundTime);
            inOrder.setSource(customer);
            inOrder.setSourceWh(warehouseCode);
            inOrder.setStaCode(orderCode);
            inOrder.setType(msgIn.getType().getValue());
            inOrder.setStatus(DefaultStatus.CREATED);
            inOrder.setExtMemo(extMemo);
            inOrder.setVersion(0);;
            inOrder.setUuid(Long.parseLong(uuid));
            msgRtnInboundOrderDao.save(inOrder);
            StockTransApplication sta = staDao.getByCode(orderCode);
            BiChannel c = companyShopDao.getByCode(sta.getOwner());
            // 保存入库反馈明细
            for (WmsInboundOrderResponseLine inLine : list) {
                MsgRtnInboundOrderLine msgLine = new MsgRtnInboundOrderLine();
                // 外包仓商品对接定制
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                Sku sku = null;
                if (choose != null) {
                    if (choose.getOptionValue().equals("CODE")) {
                        sku = skuDao.getByCode(inLine.getSkuCode());
                    } else if (choose.getOptionValue().equals("BARCODE")) {
                        sku = skuDao.getSkuByBarCodeAndCostomer(inLine.getSkuCode(), c.getCustomer().getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    } else if (choose.getOptionValue().equals("EXTCODE1")) {
                        sku = skuDao.getSkuByExtensionCode1(inLine.getSkuCode());
                    } else if (choose.getOptionValue().equals("EXTCODE2")) {
                        sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(inLine.getSkuCode(), c.getCustomer().getId(), c.getId());
                    }
                } else {
                    sku = skuDao.getByCode(inLine.getSkuCode());
                }
                msgLine.setSkuCode(inLine.getSkuCode());
                msgLine.setSkuId(sku.getId());
                msgLine.setQty(inLine.getQty());
                String invStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(inLine.getInvStatus())) {
                    invStatus = "良品";
                } else if ("imperfect".equals(inLine.getInvStatus())) {
                    invStatus = "残次品";
                } else {
                    invStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(invStatus, customer, warehouseCode);
                msgLine.setInvStatus(status);
                if (inLine.getExpDate() != null && !"".equals(inLine.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(inboundDate);
                    msgLine.setExpDate(expDate);
                }
                msgLine.setMsgRtnInOrder(inOrder);
                msgLine.setExtMemo(inLine.getExtMemo());
                msgRtnInboundOrderLineDao.save(msgLine);
                msgRtnInboundOrderLineDao.flush();
                // 保存反馈SN明细
                if (inLine.getSnLines() != null && inLine.getSnLines().length > 0) {
                    for (int j = 0; j < inLine.getSnLines().length; j++) {
                        RtnSnDetail snDetail = new RtnSnDetail();
                        snDetail.setInLine(msgLine);
                        snDetail.setSkuCode(inLine.getSnLines()[j].getSkuCode());
                        snDetail.setSn(inLine.getSnLines()[j].getSn());
                        rtnSnDetailDao.save(snDetail);
                    }
                }
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnIn.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnIn).toString();

        } catch (Exception e) {
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------wmsInboundOrderResponse error---" + customer);
        }
        return resposeJson;
    }


    /**
     * 3.4 外包仓出库单接口 非销售出、换货出
     */
    public String wmsOutBoundOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            // 时间段查询粤海外包仓非销售出、换货出库数据
            List<MsgOutboundOrder> outList = msgOutboundOrderDao.findMsgOutListByDate(startTime, endTime, customer, new BeanPropertyRowMapperExt<MsgOutboundOrder>(MsgOutboundOrder.class));
            List<WmsOutBoundOrder> msgOut = new ArrayList<WmsOutBoundOrder>();
            for (int i = page * pageSize - pageSize; i < outList.size(); i++) {
                if (i == page * pageSize) {
                    break;
                    // 当下标等于查询的最大条数时，跳出循环
                }
                WmsOutBoundOrder outOrder = new WmsOutBoundOrder();
                StockTransApplication sta = staDao.getByCode(outList.get(i).getStaCode()); // 作业单信息
                BiChannel channel = biChannelDao.getByCode(sta.getOwner()); // 店铺信息
                outOrder.setUuid(outList.get(i).getUuid().toString());
                outOrder.setWarehouseCode(outList.get(i).getSourceWh());
                outOrder.setOrderCode(outList.get(i).getStaCode());
                outOrder.setSlipCode(sta.getRefSlipCode());
                outOrder.setSlipCode1(sta.getSlipCode1());
                outOrder.setSlipCode2(sta.getSlipCode2());
                outOrder.setOrderDate(String.valueOf(FormatUtil.formatDate(outList.get(i).getCreateTime(), "yyyyMMddHHmmss")));
                outOrder.setType("10");
                outOrder.setOwner(channel.getName());
                outOrder.setToLocation(sta.getToLocation());
                if (sta.getMainStatus() != null) {
                    if (sta.getMainStatus().getName().equals("良品")) {
                        outOrder.setInvStatus("accepted");
                    } else if (sta.getMainStatus().getName().equals("残次品")) {
                        outOrder.setInvStatus("imperfect");
                    } else {
                        outOrder.setInvStatus("unsales"); // 良品不可销售
                    }
                }
                // 出库单据发货信息
                StaDeliveryInfo staInfo = sta.getStaDeliveryInfo();
                WmsOutBoundOrderDelivery msgInfo = new WmsOutBoundOrderDelivery();
                msgInfo.setCountry(staInfo.getCountry());
                msgInfo.setProvince(staInfo.getProvince());
                msgInfo.setCity(staInfo.getCity());
                msgInfo.setDistrict(staInfo.getDistrict());
                msgInfo.setAddress(staInfo.getAddress());
                msgInfo.setTelephone(staInfo.getTelephone());
                msgInfo.setMobile(staInfo.getMobile());
                msgInfo.setReceiver(staInfo.getReceiver());
                msgInfo.setLpCode(staInfo.getLpCode());
                msgInfo.setTransNo(staInfo.getTrackingNo());
                msgInfo.setRemark(staInfo.getRemark());
                msgInfo.setZipcode(staInfo.getZipcode());
                /**
                 * staDeliveryInfo判断是否是自提件,是的需要Set msgInfo
                 */

                outOrder.setDeliveryInfo(msgInfo);
                // 出库单据明细
                // List<StaLine> staLine = staLineDao.findByStaId(sta.getId());
                List<StaLineCommand> staLine = staLineDao.findStalineByStaId2(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                WmsOutBoundOrderLine[] lineArray = new WmsOutBoundOrderLine[staLine.size()];
                int j = 0; // 出库明细数组下标
                for (StaLineCommand lines : staLine) {
                    WmsOutBoundOrderLine orderLine = new WmsOutBoundOrderLine();
                    // 外包仓商品对接定制
                    ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                    if (choose != null) {
                        if (choose.getOptionValue().equals("CODE")) {
                            orderLine.setSkuCode(lines.getSkuCode());
                        } else if (choose.getOptionValue().equals("BARCODE")) {
                            orderLine.setSkuCode(lines.getExtCode2());
                        } else if (choose.getOptionValue().equals("EXTCODE1")) {
                            orderLine.setSkuCode(lines.getExtCode1());
                        } else if (choose.getOptionValue().equals("EXTCODE2")) {
                            orderLine.setSkuCode(lines.getBarCode());
                            // 用原来的sql，barcode 对应extCode2不知道哪个SB写的
                        }
                    } else {
                        orderLine.setSkuCode(lines.getSkuCode());
                    }
                    if (lines.getInvInvstatusId() != null) {
                        InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(lines.getInvInvstatusId());
                        if (inventoryStatus != null) {
                            if (inventoryStatus.getName().equals("良品")) {
                                outOrder.setInvStatus("accepted");
                            } else if (inventoryStatus.getName().equals("残次品")) {
                                outOrder.setInvStatus("imperfect");
                            } else {
                                outOrder.setInvStatus("unsales"); // 良品不可销售
                            }
                        }
                    }
                    orderLine.setInvStatus(outOrder.getInvStatus());
                    orderLine.setOwner(channel.getName());
                    orderLine.setQty(lines.getQuantity());
                    // 效期范围
                    InventoryCommand inv = inventoryDao.findInventoryByCode1(lines.getExtCode1(), channel.getName(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                    if (inv != null) {
                        if (inv.getMaxPDate() != null) {
                            orderLine.setExpDateStart(String.valueOf(FormatUtil.formatDate(inv.getMaxPDate(), "yyyy-MM-dd")));
                        }
                        if (inv.getMaxExpDate() != null) {
                            orderLine.setExpDateEnd(String.valueOf(FormatUtil.formatDate(inv.getMaxExpDate(), "yyyy-MM-dd")));
                        }
                    }
                    lineArray[j] = orderLine;
                    j++;
                }
                outOrder.setLines(lineArray);
                msgOut.add(outOrder);
            }
            // 外包仓出库数据转JSON
            WmsMsgOutRespose response = new WmsMsgOutRespose();
            MsgOutResponseMessage msgs = new MsgOutResponseMessage();
            msgs.setTotal(outList.size());
            msgs.setEntry(msgOut);
            response.setMessage(msgs);
            response.setCustomer(customer);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.info("-------------------------wmsInboundOrder error---" + customer);
            log.error("wmsSalesOrder error " + e.getMessage(), e);
        }
        return resposeJson;
    }

    /**
     * 3.5 出库单反馈接口 --第三方仓库实际接出库完成后将出库数据反馈给宝尊WMS系统，宝尊WMS同1出库单只允许反馈1次出库信息
     */
    public String uploadWmsOutboundOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = ""; // 成功返回的参数
        WmsMsgRtnInResponse rtnOut = new WmsMsgRtnInResponse();
        rtnOut.setCustomer(customer);
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        ErrorMsg msg = new ErrorMsg();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 出库单唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String outboundDate = inObj.get("outboundTime").toString(); // 实际入库时间
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date outboundTime = df.parse(outboundDate);
            String lpCode = inObj.get("lpCode").toString();
            String trackingNo = inObj.get("trackingNo").toString();
            if (!"10".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgRtnOutbound rtnOuts = msgRtnOutboundDao.findByStaCode(orderCode);
            if (rtnOuts != null) {
                msg.setErrorCode("1000010009");
                msg.setMsg("单据重复反馈：宝尊WMS同1出库单只允许反馈1次出库信息");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgOutboundOrder msgOut = msgOutboundOrderDao.getByCodeAndSource(orderCode, warehouseCode);
            if (msgOut == null) {
                msg.setErrorCode("1000010004");
                msg.setMsg("反馈数据与出库单据不符，请检查出库单唯一对接编码和仓库编码");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 入库明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsOutboundOrderResponseLine> list = new ArrayList<WmsOutboundOrderResponseLine>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsOutboundOrderResponseLine passport = (WmsOutboundOrderResponseLine) net.sf.json.JSONObject.toBean(object, WmsOutboundOrderResponseLine.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            // SN明细
            net.sf.json.JSONArray snArray = jsonobject.getJSONArray("snLines");
            List<WmsOutboundOrderResponseSnLine> snList = new ArrayList<WmsOutboundOrderResponseSnLine>();
            for (int i = 0; i < snArray.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) snArray.get(i);
                WmsOutboundOrderResponseSnLine passport = (WmsOutboundOrderResponseSnLine) net.sf.json.JSONObject.toBean(object, WmsOutboundOrderResponseSnLine.class);
                if (passport != null) {
                    snList.add(passport);
                }
            }
            // 校验数据
            String errormsg = validateMsgOutDate(list, msgOut, uuid);
            if (StringUtils.hasLength(errormsg)) {
                msg.setErrorCode(errormsg.substring(0, errormsg.indexOf("^")));
                msg.setMsg(errormsg.substring(errormsg.indexOf("^") + 1, errormsg.length()));
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 保存出库反馈
            MsgRtnOutbound msgRtnOut = new MsgRtnOutbound();
            msgRtnOut.setLpCode(lpCode);
            msgRtnOut.setOutboundTime(outboundTime);
            msgRtnOut.setSource(customer);
            msgRtnOut.setSourceWh(warehouseCode);
            msgRtnOut.setWeight(new BigDecimal(0));
            msgRtnOut.setStaCode(orderCode);
            msgRtnOut.setStatus(DefaultStatus.CREATED);
            msgRtnOut.setTrackName(lpCode);
            msgRtnOut.setTrackingNo(trackingNo);
            msgRtnOut.setVersion(0);
            msgRtnOut.setCreateTime(new Date());
            msgRtnOut.setUpdateTime(new Date());
            msgRtnOut.setExtMemo(extMemo);
            msgRtnOutboundDao.save(msgRtnOut);
            StockTransApplication sta = staDao.getByCode(orderCode);
            BiChannel c = companyShopDao.getByCode(sta.getOwner());
            // 保存出库反馈明细
            for (WmsOutboundOrderResponseLine each : list) {
                MsgRtnOutboundLine outLine = new MsgRtnOutboundLine();
                // Sku sku = skuDao.getByCode(each.getSkuCode());
                // outLine.setBarCode(sku.getBarCode());
                if (StockTransApplicationType.VMI_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType())) {
                	// 外包仓商品对接定制
                    ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                    Sku sku = null;
                    if (choose != null) {
                        if (choose.getOptionValue().equals("CODE")) {
                            sku = skuDao.getByCode(each.getSkuCode());
                        } else if (choose.getOptionValue().equals("BARCODE")) {
                            sku = skuDao.getSkuByBarCodeAndCostomer(each.getSkuCode(), c.getCustomer().getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                        } else if (choose.getOptionValue().equals("EXTCODE1")) {
                            sku = skuDao.getSkuByExtensionCode1(each.getSkuCode());
                        } else if (choose.getOptionValue().equals("EXTCODE2")) {
                            sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(each.getSkuCode(), c.getCustomer().getId(), c.getId());
                        }
                    } else {
                        sku = skuDao.getByCode(each.getSkuCode());
                    }
                    if(sku==null) {
                    	sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(each.getSkuCode(), c.getCustomer().getId(), c.getId());
                    }
                    outLine.setSkuCode(sku.getCode());
                } else {
                    outLine.setSkuCode(each.getSkuCode());
                }
                outLine.setQty(Long.parseLong(each.getQty()));
                String invStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(each.getInvStatus())) {
                    invStatus = "良品";
                } else if ("imperfect".equals(each.getInvStatus())) {
                    invStatus = "残次品";
                } else {
                    invStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(invStatus, customer, warehouseCode);
                outLine.setInvStatus(status);
                if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(each.getExpDate());
                    outLine.setExpDate(expDate);
                }
                outLine.setCartonNo(each.getCartonNo());
                outLine.setExtMemo(each.getExtMemo());
                outLine.setVersion(0);
                outLine.setMsgOutbound(msgRtnOut);
                msgRtnOutboundLineDao.save(outLine);
            }
            // 保存SN明细
            for (WmsOutboundOrderResponseSnLine each : snList) {
                RtnSnDetail snDetail = new RtnSnDetail();
                snDetail.setOut(msgRtnOut);
                snDetail.setSkuCode(each.getSkuCode());
                snDetail.setSn(each.getSn());
                rtnSnDetailDao.save(snDetail);
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnOut.setCustomer(customer);
            rtnOut.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnOut).toString();
        } catch (Exception e) {
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setCustomer(customer);
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------uploadWmsOutboundOrder error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.6. 销售出库单接口 宝尊WMS通知第三方仓储销售出库单接口。
     * 
     */
    public String wmsSalesOrder(String customer, String request) {

        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);

            int startNum = page * pageSize - pageSize;
            int endNum = page * pageSize;
            // 时间段查询粤海外包仓销售出、换货出库数据
            List<MsgOutboundOrderCommand> outList = msgOutboundOrderDao.findMsgOutListByDate2(startTime, endTime, customer, startNum, endNum, new BeanPropertyRowMapperExt<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
            List<WmsSalesOrder> msgOut = new ArrayList<WmsSalesOrder>(); // 封装数据
            for (int i = 0; i < outList.size(); i++) {
                WmsSalesOrder saleOrder = new WmsSalesOrder();
                StockTransApplication sta = staDao.getByCode(outList.get(i).getStaCode()); // 作业单信息
                CjLgOrderCodeUrl cu = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStaId(sta.getId());
                StaDeliveryInfo staInfo = sta.getStaDeliveryInfo();
                BiChannel channel = biChannelDao.getByCode(sta.getOwner()); // 店铺信息
                saleOrder.setUuid(outList.get(i).getUuid().toString());
                saleOrder.setWarehouseCode(outList.get(i).getSourceWh());
                saleOrder.setOrderCode(outList.get(i).getStaCode());
                saleOrder.setSlipCode(sta.getRefSlipCode());
                saleOrder.setSlipCode1(sta.getSlipCode1());
                saleOrder.setSlipCode2(sta.getSlipCode2());
                saleOrder.setOrderDate(String.valueOf(FormatUtil.formatDate(outList.get(i).getCreateTime(), "yyyyMMddHHmmss")));
                saleOrder.setType("21");
                saleOrder.setOwner(channel.getName());
                saleOrder.setNikePickUpCode(sta.getNikePickUpCode());
                saleOrder.setNikePickUpType(sta.getNikePickUpType());
                saleOrder.setNikeIsPick(sta.getIsNikePick());
                saleOrder.setToLocation(sta.getToLocation());
                // 是否 QS
                if (sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging() && sta.getSpecialType() == null) {
                    saleOrder.setIsQs(true);
                } else {
                    saleOrder.setIsQs(false);
                }
                if (sta.getSpecialType() != null && sta.getSpecialType().equals(SpecialSkuType.NORMAL)) {
                    saleOrder.setSpecialType(1);
                } else {
                    saleOrder.setSpecialType(null);
                }
                // COD金额为 订单总金额加运费
                if (staInfo.getIsCod() && staInfo.getTransferFee() != null) {
                    saleOrder.setCodAmount(sta.getTotalActual().add(staInfo.getTransferFee()));
                } else {
                    saleOrder.setCodAmount(new BigDecimal(0));
                }
                saleOrder.setOrderAmount(sta.getTotalActual());
                saleOrder.setOrder_user_mail(outList.get(i).getMemberEmail());
                saleOrder.setOrder_user_code(outList.get(i).getUserId());
                saleOrder.setInvStatus("accepted");
                saleOrder.setExtMemo(outList.get(i).getExtMemo());
                // 订单发货信息
                WmsSalesOrderDelivery msgInfo = new WmsSalesOrderDelivery();
                msgInfo.setCountry(staInfo.getCountry());
                msgInfo.setProvince(staInfo.getProvince());
                msgInfo.setCity(staInfo.getCity());
                msgInfo.setDistrict(staInfo.getDistrict());
                msgInfo.setAddress(staInfo.getAddress());
                msgInfo.setTelephone(staInfo.getTelephone());
                msgInfo.setMobile(staInfo.getMobile());
                msgInfo.setReceiver(staInfo.getReceiver());
                msgInfo.setLpCode(staInfo.getLpCode());
                msgInfo.setTransNo(staInfo.getTrackingNo());
                msgInfo.setZipcode(staInfo.getZipcode());
                msgInfo.setCityCode(staInfo.getTransCityCode());
                msgInfo.setIsCod(staInfo.getIsCod());
                msgInfo.setRemark(staInfo.getRemark());
                msgInfo.setReturnTransNo(staInfo.getReturnTransNo());
                if (staInfo.getTransType() != null) {
                    msgInfo.setTransType(staInfo.getTransType().getValue());
                } else {
                    msgInfo.setTransType(1);
                }
                if (staInfo.getTransTimeType() != null) {
                    msgInfo.setTransTimeType(staInfo.getTransTimeType().getValue());
                } else {
                    msgInfo.setTransTimeType(1);
                }
                msgInfo.setTransMemo(staInfo.getTransMemo());
                msgInfo.setInsuranceAmount(staInfo.getInsuranceAmount());
                msgInfo.setAddressEn(staInfo.getAddressEn());
                msgInfo.setCountryEn(staInfo.getCountryEn());
                msgInfo.setProvinceEn(staInfo.getProvinceEn());
                msgInfo.setCityEn(staInfo.getCityEn());
                msgInfo.setDistrictEn(staInfo.getDistrictEn());
                msgInfo.setRemarkEn(staInfo.getRemarkEn());
                if (org.apache.commons.lang3.StringUtils.equals("CJ", customer)) {
                    JSONObject jsono = new JSONObject();
                    if (cu != null && cu.getLgorderCode() != null) {
                        // jsono.put("lgorderCode", cu.getLgorderCode());
                        jsono.put("waybillUrl", cu.getWaybillurl());
                        msgInfo.setExtMemo(jsono.toString());
                    }
                }
                saleOrder.setDeliveryInfo(msgInfo);
                // 发票信息
                // if (staInfo.getStoreComIsNeedInvoice()) {
                // List<StaInvoice> staInv = staInvoiceDao.getBySta(sta.getId());
                List<StaInvoiceCommand> staInv = staInvoiceDao.findInvoiceByStaid(sta.getId());
                if (staInv.size() > 0) {
                    saleOrder.setIsInvoice(true);
                    WmsSalesOrderInvoice[] invArray = new WmsSalesOrderInvoice[staInv.size()];
                    int j = 0; // 发票数组下标
                    for (StaInvoiceCommand lines : staInv) {
                        WmsSalesOrderInvoice invoice = new WmsSalesOrderInvoice();
                        invoice.setInvoiceDate(lines.getInvoiceDate());
                        invoice.setPayer(lines.getPayer());
                        invoice.setItem(lines.getItem());
                        invoice.setQty(lines.getQty());
                        invoice.setUnitPrice(lines.getUnitPrice());
                        invoice.setAmt(lines.getAmt());
                        invoice.setMemo(lines.getMemo());
                        invoice.setPayee(lines.getPayee());
                        invoice.setDrawer(lines.getDrawer());
                        invoice.setCompanyName(lines.getCompanyName());
                        List<StaInvoiceLine> lineInv = staInvoiceLineDao.getByStaInvoiceId(lines.getId());
                        WmsSalesOrderInvoiceLine[] invLineArray = new WmsSalesOrderInvoiceLine[lineInv.size()];
                        int k = 0; // 发票明细数组下标
                        for (StaInvoiceLine line : lineInv) {
                            WmsSalesOrderInvoiceLine invLine = new WmsSalesOrderInvoiceLine();
                            invLine.setIteam(line.getItem());
                            invLine.setQty(line.getQty());
                            invLine.setUnitPrice(line.getUnitPrice());
                            invLine.setAmt(line.getAmt());
                            invLineArray[k] = invLine;
                            k++;
                        }
                        invoice.setDetials(invLineArray);
                        invArray[j] = invoice;
                        j++;
                    }
                    saleOrder.setInvoices(invArray);
                } else {
                    saleOrder.setIsInvoice(false);
                }
                // }
                // 出库单明细
                List<StaLineCommand> staLine = staLineDao.findStalineByStaId2(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                WmsSalesOrderLine[] saleLine = new WmsSalesOrderLine[staLine.size()];
                int x = 0; // 出库明细数组下标
                for (StaLineCommand lines : staLine) {
                    WmsSalesOrderLine salesLine = new WmsSalesOrderLine();
                    // 外包仓商品对接定制
                    ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                    if (choose != null) {
                        if (choose.getOptionValue().equals("CODE")) {
                            salesLine.setSkuCode(lines.getSkuCode());
                        } else if (choose.getOptionValue().equals("BARCODE")) {
                            salesLine.setSkuCode(lines.getExtCode2());
                        } else if (choose.getOptionValue().equals("EXTCODE1")) {
                            salesLine.setSkuCode(lines.getExtCode1());
                        } else if (choose.getOptionValue().equals("EXTCODE2")) {
                            salesLine.setSkuCode(lines.getBarCode());
                            // 用原来的sql，barcode 对应extCode2不知道哪个SB写的
                        }
                    } else {
                        salesLine.setSkuCode(lines.getSkuCode());
                    }
                    salesLine.setQty(lines.getQuantity());
                    salesLine.setTotalActual(lines.getTotalActual());
                    salesLine.setListPrice(lines.getListPrice());
                    salesLine.setUnitPrice(lines.getUnitPrice());
                    salesLine.setOwner(channel.getName());
                    salesLine.setInvStatus("accepted");
                    if (org.apache.commons.lang3.StringUtils.equals("CJ", customer)) {
                        JSONObject jo = new JSONObject();
                        jo.put("extCode2", lines.getBarCode());
                        salesLine.setExtMemo(jo.toString());
                    }
                    // 效期范围
                    InventoryCommand inv = inventoryDao.findInventoryByCode1(lines.getExtCode1(), channel.getName(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                    if (inv != null) {
                        if (inv.getMaxPDate() != null) {
                            salesLine.setExpDateStart(String.valueOf(FormatUtil.formatDate(inv.getMaxPDate(), "yyyy-MM-dd")));
                        }
                        if (inv.getMaxExpDate() != null) {
                            salesLine.setExpDateEnd(String.valueOf(FormatUtil.formatDate(inv.getMaxExpDate(), "yyyy-MM-dd")));
                        }
                    }
                    // 如果需要特殊包装
                    if (saleOrder.getSpecialType() != null && saleOrder.getSpecialType() == 1) {
                        List<GiftLine> gifts = giftLineDao.getLinesByStalineId(lines.getId());
                        WmsSalesOrderGiftLine[] giftArray = new WmsSalesOrderGiftLine[gifts.size()];
                        int y = 0; // 商品包装礼品数组下标
                        for (GiftLine gift : gifts) {
                            WmsSalesOrderGiftLine giftLine = new WmsSalesOrderGiftLine();
                            giftLine.setType(gift.getType().getValue());
                            giftLine.setMemo(gift.getMemo());
                            giftArray[y] = giftLine;
                            y++;
                        }
                        salesLine.setGifts(giftArray);
                    }
                    saleLine[x] = salesLine;
                    x++;
                }
                saleOrder.setLines(saleLine);
                // 整单特殊定制
                if (saleOrder.getSpecialType() != null && saleOrder.getSpecialType() == 1) {
                    List<StaSpecialExecutedCommand> staSpe = staSpecialExecutedDao.findStaSpecialByStaId(sta.getId(), null, new BeanPropertyRowMapper<StaSpecialExecutedCommand>(StaSpecialExecutedCommand.class));
                    WmsSalesOrderSe[] seArray = new WmsSalesOrderSe[staSpe.size()];
                    int z = 0;
                    for (StaSpecialExecutedCommand spe : staSpe) {
                        WmsSalesOrderSe sp = new WmsSalesOrderSe();
                        sp.setType(spe.getIntType());
                        sp.setMemo(spe.getMemo());
                        seArray[z] = sp;
                        z++;
                    }
                    saleOrder.setSpecialExecutes(seArray);
                }
                // 外包仓销售出库数据转JSON
                msgOut.add(saleOrder);
            }
            WmsMsgSaleOutRespose response = new WmsMsgSaleOutRespose();
            MsgSaleOutRequsetMessage msg = new MsgSaleOutRequsetMessage();
            if (outList.size() == 0) {
                msg.setTotal(0);
            } else {
                msg.setTotal(outList.get(0).getCountSum());
            }
            msg.setEntry(msgOut);
            response.setCustomer(customer);
            response.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.info("-------------------------wmsSalesOrder error---" + customer);
            log.error("wmsSalesOrder error " + e.getMessage(), e);
        }
        try {
            if (log.isDebugEnabled()) {
                if (org.apache.commons.lang3.StringUtils.equals("CJ", customer)) {
                    log.debug("baozun Service time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    log.debug(request);
                    log.debug(resposeJson);
                    log.debug("----------------------CJ------------------------");
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return resposeJson;
    }

    /**
     * 3.7. 第三方仓库实际接出库完成后将出库数据反馈给宝尊WMS系统。 宝尊WMS同1出库单只允许反馈1次出库信息。 销售出库反馈
     * 
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String uploadWmsSalesOrder(String customer, String request) {
        Boolean isMq = false;
        String resposeJson = ""; // 返回的JSON
        String uuid = ""; // 成功返回的参数
        WmsMsgRtnInResponse rtnOut = new WmsMsgRtnInResponse();
        rtnOut.setCustomer(customer);
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        ErrorMsg msg = new ErrorMsg();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 出库单唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String outboundDate = inObj.get("outboundTime").toString(); // 实际出库时间
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date outboundTime = df.parse(outboundDate);
            String lpCode = inObj.get("lpCode").toString();
            String trackingNo = inObj.get("trackingNo").toString();
            String materialSkus = inObj.get("materialSkus").toString();
            String weights = inObj.get("weight").toString();
            String transNos = inObj.get("transNos").toString(); // 多包裹
            BigDecimal weight = new BigDecimal(weights);
            Sku skus = null;
            List<Sku> skuList=new ArrayList<Sku>();
            if ("HD".equals(customer) && !StringUtils.hasLength(materialSkus)) {
                msg.setErrorCode("1000010021");
                msg.setMsg("耗材不能为空！");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }

            if ("HD".equals(customer) && StringUtils.hasLength(materialSkus)) {
                skus = skuDao.getSkuByBarcode(materialSkus);
                if (skus == null) {
                    msg.setErrorCode("1000010022");
                    msg.setMsg("耗材条码不正确！");
                    error.setMessage(msg);
                    return net.sf.json.JSONObject.fromObject(error).toString();
                }
            }
            
            
            if (("NIKE-TEST002".equals(customer)|| "WH_OCL".equals(customer)||"WH_OOCL".equals(customer)
                    ||"WH_NIKE_WH_SF".equals(customer)) && null!=materialSkus&&!"".equals(materialSkus)) {
                String[] skuArray=materialSkus.split(",");
                for(String sku:skuArray){
                    skus = skuDao.getSkuByBarcode(sku);
                    if (skus == null) {
                        msg.setErrorCode("1000010022");
                        msg.setMsg("耗材条码不正确！");
                        error.setMessage(msg);
                        return net.sf.json.JSONObject.fromObject(error).toString();
                    }
                    skuList.add(skus);
                }
                
                
            }
            if (!"21".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgRtnOutbound rtnOuts = msgRtnOutboundDao.findByStaCode(orderCode);
            if (rtnOuts != null) {
                msg.setErrorCode("1000010009");
                msg.setMsg("单据重复反馈：宝尊WMS同1出库单只允许反馈1次出库信息");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgOutboundOrder msgOut = msgOutboundOrderDao.getByCodeAndSource(orderCode, warehouseCode);
            if (msgOut == null) {
                msg.setErrorCode("1000010004");
                msg.setMsg("反馈数据与出库单据不符，请检查出库单唯一对接编码和仓库编码");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 出库明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsOutboundOrderResponseLine> list = new ArrayList<WmsOutboundOrderResponseLine>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsOutboundOrderResponseLine passport = (WmsOutboundOrderResponseLine) net.sf.json.JSONObject.toBean(object, WmsOutboundOrderResponseLine.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            // SN明细
            net.sf.json.JSONArray snArray = jsonobject.getJSONArray("snLines");
            List<WmsOutboundOrderResponseSnLine> snList = new ArrayList<WmsOutboundOrderResponseSnLine>();
            for (int i = 0; i < snArray.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) snArray.get(i);
                WmsOutboundOrderResponseSnLine passport = (WmsOutboundOrderResponseSnLine) net.sf.json.JSONObject.toBean(object, WmsOutboundOrderResponseSnLine.class);
                if (passport != null) {
                    snList.add(passport);
                }
            }
            // 校验数据
            String errormsg = validateMsgOutDate(list, msgOut, uuid);
            if (StringUtils.hasLength(errormsg)) {
                msg.setErrorCode(errormsg.substring(0, errormsg.indexOf("^")));
                msg.setMsg(errormsg.substring(errormsg.indexOf("^") + 1, errormsg.length()));
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // MQ 开关
            List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_RTN_OUTBOUNT_YH, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                isMq = true;
            }
            // 保存出库反馈
            MsgRtnOutbound msgRtnOut = new MsgRtnOutbound();
            msgRtnOut.setLpCode(lpCode);
            msgRtnOut.setOutboundTime(outboundTime);
            msgRtnOut.setSource(customer);
            msgRtnOut.setSourceWh(warehouseCode);
            msgRtnOut.setStaCode(orderCode);
            msgRtnOut.setWeight(weight);
            msgRtnOut.setStatus(DefaultStatus.CREATED);
            msgRtnOut.setTrackName(lpCode);
            msgRtnOut.setTrackingNo(trackingNo);
            // 多包裹逻辑 多快递单号存放于 备注字段
            if (transNos != null && !"".equals(transNos)) {
                msgRtnOut.setRemark(transNos);
            }
            msgRtnOut.setVersion(0);
            msgRtnOut.setCreateTime(new Date());
            msgRtnOut.setUpdateTime(new Date());
            msgRtnOut.setExtMemo(extMemo);
            msgRtnOut.setField1(materialSkus); // 包材条码放到备用字段上
            if (isMq) {
                msgRtnOut.setIsMq("1");
            }
            msgRtnOutboundDao.save(msgRtnOut);
            // 保存出库反馈明细
            for (WmsOutboundOrderResponseLine each : list) {
                MsgRtnOutboundLine outLine = new MsgRtnOutboundLine();
                // Sku sku = skuDao.getByCode(each.getSkuCode());
                // outLine.setBarCode(sku.getBarCode());
                outLine.setSkuCode(each.getSkuCode());
                outLine.setQty(Long.parseLong(each.getQty()));
                String invStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(each.getInvStatus())) {
                    invStatus = "良品";
                } else if ("imperfect".equals(each.getInvStatus())) {
                    invStatus = "残次品";
                } else {
                    invStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(invStatus, customer, warehouseCode);
                outLine.setInvStatus(status);
                if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(each.getExpDate());
                    outLine.setExpDate(expDate);
                }
                outLine.setCartonNo(each.getCartonNo());
                outLine.setExtMemo(each.getExtMemo());
                outLine.setVersion(0);
                outLine.setMsgOutbound(msgRtnOut);
                msgRtnOutboundLineDao.save(outLine);
            }
            // 保存SN明细
            for (WmsOutboundOrderResponseSnLine each : snList) {
                RtnSnDetail snDetail = new RtnSnDetail();
                snDetail.setOut(msgRtnOut);
                snDetail.setSkuCode(each.getSkuCode());
                snDetail.setSn(each.getSn());
                rtnSnDetailDao.save(snDetail);
            }
            // 耗材商品保存
            if (null!=materialSkus&&!"".equals(materialSkus)) {
                StockTransApplication sta = staDao.getByCode(orderCode);
                if("NIKE-TEST002".equals(customer)|| "WH_OCL".equals(customer)||"WH_OOCL".equals(customer)
                        ||"WH_NIKE_WH_SF".equals(customer)){
                    if(null!=skuList&&skuList.size()>0){
                        for(Sku sku:skuList){
                        	StaAdditionalLine addline = new StaAdditionalLine();
                            addline.setSku(sku);
                            addline.setSta(sta);
                            addline.setCreateTime(new Date());
                            addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                            addline.setOwner(sta.getOwner());
                            addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                            addline.setLpcode(sta.getStaDeliveryInfo().getLpCode());
                            staAdditionalLineDao.save(addline);
                        }
                    }else{
                    	StaAdditionalLine addline = new StaAdditionalLine();
                        addline.setSku(skus);
                        addline.setSta(sta);
                        addline.setCreateTime(new Date());
                        addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                        addline.setOwner(sta.getOwner());
                        addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                        addline.setLpcode(sta.getStaDeliveryInfo().getLpCode());
                        staAdditionalLineDao.save(addline);
                    }
                }else{
                    StaAdditionalLine addline = new StaAdditionalLine();
                    addline.setSku(skus);
                    addline.setSta(sta);
                    addline.setCreateTime(new Date());
                    addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                    addline.setOwner(sta.getOwner());
                    addline.setTrackingNo(sta.getStaDeliveryInfo().getTrackingNo());
                    addline.setLpcode(sta.getStaDeliveryInfo().getLpCode());
                    staAdditionalLineDao.save(addline);
                }
            }

            if (isMq) {// 发mq
                try {
                    MsgRtnOutbound msgRtnOut2 = wareHouseManager.saveMsgRtnOut(msgRtnOut);// 单起事物保存
                    // MsgRtnOutbound msgRtnOut2 =
                    // msgRtnOutboundDao.getByPrimaryKey(msgRtnOut.getId());// 发mq
                    String reqJson = JsonUtil.writeValue(msgRtnOut2);
                    MessageCommond mes = new MessageCommond();
                    mes.setMsgId(msgRtnOut2.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("com.jumbo.wms.manager.task.inv.ThreePLManagerImpl.uploadWmsSalesOrder");
                    mes.setMsgBody(reqJson);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mes.setSendTime(sdf.format(date));
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_MQ_RTN_OUTBOUNT_YH, mes);
                    // 保存进mongodb
                    MongoDBMessage mdbm = new MongoDBMessage();
                    BeanUtils.copyProperties(mes, mdbm);
                    mdbm.setStaCode(msgRtnOut2.getStaCode());
                    mdbm.setOtherUniqueKey(null);
                    mdbm.setMsgBody(reqJson);
                    mongoOperation.save(mdbm);
                    log.debug("rmi Call oms uploadWmsSalesOrder response interface by MQ end:" + msgRtnOut2.getId());
                    msgRtnOut2.setMqStatus("10");
                    msgRtnOutboundDao.save(msgRtnOut2);
                } catch (Exception e) {
                    log.error("uploadWmsSalesOrder isMQ  error---:" + msgRtnOut.getStaCode());
                    log.error("uploadWmsSalesOrder_isMQ", e);
                }
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnOut.setCustomer(customer);
            rtnOut.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnOut).toString();
        } catch (Exception e) {
            // TODO 测试
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setCustomer(customer);
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            if (log.isErrorEnabled()) {
                log.error("threePLManager uploadWmsSalesOrder:" + resposeJson, e);
            }
            log.error("-------------------------uploadWmsSalesOrder error---" + customer);
        }
        return resposeJson;
    }



    /**
     * 3.8.单据取消接口 宝尊WMS通知第三方仓储单据取消。
     * 
     * @return
     */
    public String wmsOrderCancel(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            // 时间段查询外包仓取消单据
            List<MsgOutboundOrderCancelCommand> cancelList = msgOutboundOrderCancelDao.findMsgCancelListByDate(startTime, endTime, customer, new BeanPropertyRowMapperExt<MsgOutboundOrderCancelCommand>(MsgOutboundOrderCancelCommand.class));
            List<wmsOrderCancel> msgCancels = new ArrayList<wmsOrderCancel>();
            for (int i = page * pageSize - pageSize; i < cancelList.size(); i++) {
                if (i == page * pageSize) {
                    break;
                    // 当下标等于查询的最大条数时，跳出循环
                }
                wmsOrderCancel msgCancel = new wmsOrderCancel();
                msgCancel.setUuid(cancelList.get(i).getUuid().toString());
                msgCancel.setOrderCode(cancelList.get(i).getStaCode());
                msgCancel.setType(cancelList.get(i).getStaType());
                msgCancels.add(msgCancel);
                MsgOutboundOrderCancel msg = msgOutboundOrderCancelDao.getByPrimaryKey(cancelList.get(i).getId());
                msg.setStatus(MsgOutboundOrderCancelStatus.SENT);
            }
            // 外包仓入库数据转JSON
            WmsMsgCancelRespose response = new WmsMsgCancelRespose();
            MsgCancelResponseMessage msgs = new MsgCancelResponseMessage();
            msgs.setTotal(cancelList.size());
            msgs.setEntry(msgCancels);
            response.setMessage(msgs);
            response.setCustomer(customer);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------wmsOrderCancel error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.9.单据取消反馈接口 第三方仓储单反馈宝尊订单取消结果。
     * 
     * @param customer
     * @param request
     * @return
     */
    public String uploadWmsOrderCancel(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = ""; // 成功返回的参数
        WmsMsgRtnInResponse rtnOut = new WmsMsgRtnInResponse();
        rtnOut.setCustomer(customer);
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        ErrorMsg msg = new ErrorMsg();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String orderCode = inObj.get("orderCode").toString(); // 出库单唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String status = inObj.get("status").toString(); // 单据取消结果
            MsgOutboundOrderCancel msgCancel = msgOutboundOrderCancelDao.getMsgCancelByStaCode(orderCode);
            if (msgCancel == null) {
                msg.setErrorCode("10000100010");
                msg.setMsg("单据不存在！请检查单据编码");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            if (!type.equals("21") && !type.equals("41") && !type.equals("42")) {
                msg.setErrorCode("10000100011");
                msg.setMsg("单据类型错误！取消类型只支持 退货入和销售出和换货出");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            if (!status.equals("1") && !status.equals("0")) {
                msg.setErrorCode("10000100012");
                msg.setMsg("单据状态错误！取消状态只支持 0、1");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 修改 为 外包仓已取消
            if (status.endsWith("1")) {
                msgCancel.setIsCanceled(true);
                msgCancel.setUpdateTime(new Date());
            } else {
                msgCancel.setIsCanceled(false);
                msgCancel.setUpdateTime(new Date());
                if (type.equals("41")) {
                    msgCancel.setMsg("仓库已入库，无法取消"); // 和越海沟通，退货取消失败只有该情况，所以代码里写死。
                }
            }
            msgOutboundOrderCancelDao.save(msgCancel);
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnOut.setCustomer(customer);
            rtnOut.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnOut).toString();
        } catch (Exception e) {
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setCustomer(customer);
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------uploadWmsOrderCancel error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.10.销售退货接口 入库单接口提供给第三方仓储系统入库单数据，以便第三方仓储根据入库单据数据处理后续的收货流程。
     * 
     * @param customer
     * @param request
     * @return
     */
    public String wmsSalesReturnOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        try {
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(request);
            String startTimes = obj.get("startTime").toString(); // 开始时间
            String endTimes = obj.get("endTime").toString(); // 结束时间
            int page = Integer.parseInt(obj.get("page").toString()); // 页号
            int pageSize = Integer.parseInt(obj.get("pageSize").toString()); // 每页大小
            // 转换时间
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startTimes);
            Date endTime = df.parse(endTimes);
            // 时间段查询粤海外包仓入库数据 退货入
            List<MsgInboundOrder> inList = msgInboundOrderDao.findMsgInListByDate2(startTime, endTime, customer, new BeanPropertyRowMapperExt<MsgInboundOrder>(MsgInboundOrder.class));
            List<WmsInboundOrder> msgIn = new ArrayList<WmsInboundOrder>();
            for (int i = page * pageSize - pageSize; i < inList.size(); i++) {
                if (i == page * pageSize) {
                    break;
                    // 当下标等于查询的最大条数时，跳出循环
                }
                WmsInboundOrder inOrder = new WmsInboundOrder();
                StockTransApplication sta = staDao.getByCode(inList.get(i).getStaCode()); // 作业单信息
                BiChannel channel = biChannelDao.getByCode(sta.getOwner()); // 店铺信息
                inOrder.setUuid(inList.get(i).getUuid().toString());
                inOrder.setWarehouseCode(inList.get(i).getSourceWh());
                inOrder.setOrderCode(inList.get(i).getStaCode());
                inOrder.setSlipCode(sta.getRefSlipCode());
                inOrder.setSlipCode1(sta.getSlipCode1());
                inOrder.setSlipCode2(sta.getSlipCode2());
                inOrder.setOrderDate(String.valueOf(FormatUtil.formatDate(inList.get(i).getCreateTime(), "yyyyMMddHHmmss")));
                if (inList.get(i).getPlanArriveTime() != null) {
                    inOrder.setArriveTime(String.valueOf(FormatUtil.formatDate(inList.get(i).getPlanArriveTime(), "yyyyMMddHHmmss")));
                }
                inOrder.setType("41");
                inOrder.setOwner(channel.getName());
                inOrder.setMemo(inList.get(i).getRemark());
                inOrder.setExtMemo(inList.get(i).getExtMemo());
                // if (sta.getMainStatus() != null) {
                // if ("良品".equals(sta.getMainStatus().getName())) {
                // inOrder.setInvStatus("accepted");
                // } else if ("残次品".equals(sta.getMainStatus().getName())) {
                // inOrder.setInvStatus("imperfect");
                // } else {
                // inOrder.setInvStatus("unsales"); // 良品不可销售
                // }
                // }
                List<MsgInboundOrderLineCommand> lines = msgInboundOrderLineDao.findVmiMsgInboundOrderLine(inList.get(i).getId(), new BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));
                int j = 0;
                if (lines != null && lines.size() > 0) {
                    WmsInboundOrderLine[] lineArray = new WmsInboundOrderLine[lines.size()];
                    for (MsgInboundOrderLineCommand orderLine : lines) {
                        WmsInboundOrderLine inLine = new WmsInboundOrderLine();
                        if ("良品".equals(orderLine.getInvStsName())) {
                            inLine.setInvStatus("accepted");
                        } else if ("残次品".equals(orderLine.getInvStsName())) {
                            inLine.setInvStatus("imperfect");
                        } else {
                            inLine.setInvStatus("unsales"); // 良品不可销售
                        }
                        inLine.setOwner(channel.getName());
                        inLine.setQty(orderLine.getQty());
                        // 外包仓商品对接定制
                        ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                        if (choose != null) {
                            if (choose.getOptionValue().equals("CODE")) {
                                inLine.setSkuCode(orderLine.getSpuCode());
                            } else if (choose.getOptionValue().equals("BARCODE")) {
                                inLine.setSkuCode(orderLine.getBarCode());
                            } else if (choose.getOptionValue().equals("EXTCODE1")) {
                                inLine.setSkuCode(orderLine.getEcode());
                            } else if (choose.getOptionValue().equals("EXTCODE2")) {
                                inLine.setSkuCode(orderLine.getEcode2());
                            }
                        } else {
                            inLine.setSkuCode(orderLine.getSpuCode());
                        }
                        lineArray[j] = inLine;
                        j++;
                    }
                    inOrder.setLines(lineArray);
                }
                msgIn.add(inOrder);
            }
            // 外包仓入库数据转JSON
            WmsMsgInRespose response = new WmsMsgInRespose();
            MsgInRequsetMessage msgs = new MsgInRequsetMessage();
            msgs.setTotal(inList.size());
            msgs.setEntry(msgIn);
            response.setMessage(msgs);
            response.setCustomer(customer);
            resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            WmsErrorResponse error = new WmsErrorResponse();
            error.setCustomer(customer);
            ErrorMsg msg = new ErrorMsg();
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------wmsInboundOrder error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.11.销售退货反馈接口 第三方仓库实际接收完成后将收货数据反馈给宝尊WMS系统。 销售退货只允许按计划量执行反馈，可以调整库存状态。
     * 
     * @param customer
     * @param request
     * @return
     */
    public String uploadWmsSalesReturnOrder(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = "";
        // 成功返回的参数
        WmsMsgRtnInResponse rtnIn = new WmsMsgRtnInResponse();
        rtnIn.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        ErrorMsg msg = new ErrorMsg();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 入库单唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String inboundDate = inObj.get("inboundTime").toString(); // 实际入库时间
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date inboundTime = df.parse(inboundDate);
            if (!"41".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            MsgInboundOrder msgIn = msgInboundOrderDao.getByCodeAndSource(orderCode, warehouseCode);
            if (msgIn == null) {
                msg.setErrorCode("1000010004");
                msg.setMsg("反馈数据与入库单据不符，请检查入库单唯一对接编码和仓库编码");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 入库明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsInboundOrderResponseLine> list = new ArrayList<WmsInboundOrderResponseLine>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsInboundOrderResponseLine passport = (WmsInboundOrderResponseLine) net.sf.json.JSONObject.toBean(object, WmsInboundOrderResponseLine.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            // 校验数据
            String errormsg = validateMsgInDate(list, msgIn, uuid);
            if (StringUtils.hasLength(errormsg)) {
                msg.setErrorCode(errormsg.substring(0, errormsg.indexOf("^")));
                msg.setMsg(errormsg.substring(errormsg.indexOf("^") + 1, errormsg.length()));
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 保存入库反馈
            MsgRtnInboundOrder inOrder = new MsgRtnInboundOrder();
            inOrder.setCreateTime(new Date());
            inOrder.setInboundTime(inboundTime);
            inOrder.setSource(customer);
            inOrder.setSourceWh(warehouseCode);
            inOrder.setStaCode(orderCode);
            inOrder.setType(msgIn.getType().getValue());
            inOrder.setStatus(DefaultStatus.CREATED);
            inOrder.setExtMemo(extMemo);
            inOrder.setVersion(0);;
            inOrder.setUuid(Long.parseLong(uuid));
            msgRtnInboundOrderDao.save(inOrder);
            StockTransApplication sta = staDao.getByCode(orderCode);
            BiChannel c = companyShopDao.getByCode(sta.getOwner());
            // 保存入库反馈明细
            for (WmsInboundOrderResponseLine inLine : list) {
                MsgRtnInboundOrderLine msgLine = new MsgRtnInboundOrderLine();
                // 外包仓商品对接定制
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                Sku sku = null;
                if (choose != null) {
                    if (choose.getOptionValue().equals("CODE")) {
                        sku = skuDao.getByCode(inLine.getSkuCode());
                    } else if (choose.getOptionValue().equals("BARCODE")) {
                        sku = skuDao.getSkuByBarCodeAndCostomer(inLine.getSkuCode(), c.getCustomer().getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    } else if (choose.getOptionValue().equals("EXTCODE1")) {
                        sku = skuDao.getSkuByExtensionCode1(inLine.getSkuCode());
                    } else if (choose.getOptionValue().equals("EXTCODE2")) {
                        sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(inLine.getSkuCode(), c.getCustomer().getId(), c.getId());
                    }
                } else {
                    sku = skuDao.getByCode(inLine.getSkuCode());
                }
                msgLine.setSkuCode(inLine.getSkuCode());
                msgLine.setSkuId(sku.getId());
                msgLine.setQty(inLine.getQty());
                String invStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(inLine.getInvStatus())) {
                    invStatus = "良品";
                } else if ("imperfect".equals(inLine.getInvStatus())) {
                    invStatus = "残次品";
                } else {
                    invStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(invStatus, customer, warehouseCode);
                msgLine.setInvStatus(status);
                if (inLine.getExpDate() != null && !"".equals(inLine.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(inboundDate);
                    msgLine.setExpDate(expDate);
                }
                msgLine.setMsgRtnInOrder(inOrder);
                msgLine.setExtMemo(inLine.getExtMemo());
                msgRtnInboundOrderLineDao.save(msgLine);
                // 保存反馈SN明细
                if (inLine.getSnLines() != null && inLine.getSnLines().length > 0) {
                    for (int j = 0; j < inLine.getSnLines().length; j++) {
                        RtnSnDetail snDetail = new RtnSnDetail();
                        snDetail.setInLine(msgLine);
                        snDetail.setSkuCode(inLine.getSnLines()[j].getSkuCode());
                        snDetail.setSn(inLine.getSnLines()[j].getSn());
                        rtnSnDetailDao.save(snDetail);
                    }
                }
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnIn.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnIn).toString();
        } catch (Exception e) {
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------uploadWmsSalesReturnOrder error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.12.库存状态修改反馈接口 第三方仓库将库内商品状态调整推送给宝尊WMS。
     * 
     * @param customer
     * @param request
     * @return
     */
    public String uploadWmsInvStatusChange(String customer, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = "";
        // 成功返回的参数
        WmsMsgRtnInResponse rtnIn = new WmsMsgRtnInResponse();
        rtnIn.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        ErrorMsg msg = new ErrorMsg();
        MsgRtnAdjustment msgRtn = new MsgRtnAdjustment();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            String inboundDate = inObj.get("orderDate").toString(); // 实际调整时间
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 单据唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String memo = inObj.get("memo").toString(); // 调整原因
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String owner = null;
            String ownerCode = null;
            Long shopId = null;
            if (inObj.has("owner")) {
                owner = inObj.get("owner").toString(); // 店铺
            }
            if (owner != null) {
                BiChannel bi = biChannelDao.getByName(owner);
                if (bi == null) {
                    msg.setErrorCode("1000010019");
                    msg.setMsg("店铺【" + owner + "】在宝尊WMS不存在，请填写正确店铺名称！");
                    error.setMessage(msg);
                    return net.sf.json.JSONObject.fromObject(error).toString();
                } else {
                    ownerCode = bi.getCode();
                    shopId = bi.getId();
                }
            }
            Date orderDate = df.parse(inboundDate);
            if (!"50".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 反馈明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsInvStatusChangeOrderLineResponse> list = new ArrayList<WmsInvStatusChangeOrderLineResponse>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsInvStatusChangeOrderLineResponse passport = (WmsInvStatusChangeOrderLineResponse) net.sf.json.JSONObject.toBean(object, WmsInvStatusChangeOrderLineResponse.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            // 校验数据
            String errormsg = validateMsgInvStatus(list);
            if (StringUtils.hasLength(errormsg)) {
                msg.setErrorCode(errormsg.substring(0, errormsg.indexOf("^")));
                msg.setMsg(errormsg.substring(errormsg.indexOf("^") + 1, errormsg.length()));
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            msgRtn.setUuid(Long.parseLong(uuid));
            msgRtn.setSource(customer);
            msgRtn.setSourceWh(warehouseCode);
            msgRtn.setOrderCode(orderCode);
            msgRtn.setEntityId(0l);
            msgRtn.setOwnerSource(ownerCode);
            msgRtn.setEffectiveDate(orderDate);
            msgRtn.setType(Long.parseLong(type));
            msgRtn.setExtMemo(extMemo);
            msgRtn.setMemo(memo);
            msgRtn.setCreateTime(new Date());
            msgRtn.setVersion(0);
            msgRtn.setStatus(DefaultStatus.CREATED);
            msgRtnAdjustmentDao.save(msgRtn);
            Warehouse waerhouse = warehouseDao.getBySource(customer, warehouseCode);
            Long cid = null;
            if (waerhouse != null && waerhouse.getCustomer() != null) {
                cid = waerhouse.getCustomer().getId();
            }
            for (WmsInvStatusChangeOrderLineResponse each : list) {
                // 库存状态调整明细要对应两条。 原库存状态 负。 新库存状态+
                MsgRtnAdjustmentLine fromLine = new MsgRtnAdjustmentLine();
                // 外包仓商品对接定制
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                Sku sku = null;
                if (choose != null) {
                    if (choose.getOptionValue().equals("CODE")) {
                        sku = skuDao.getByCode(each.getSkuCode());
                    } else if (choose.getOptionValue().equals("BARCODE")) {
                        sku = skuDao.getSkuByBarCodeAndCostomer(each.getSkuCode(), cid, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    } else if (choose.getOptionValue().equals("EXTCODE1")) {
                        sku = skuDao.getSkuByExtensionCode1(each.getSkuCode());
                    } else if (choose.getOptionValue().equals("EXTCODE2")) {
                        sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(each.getSkuCode(), cid, shopId);
                    }
                } else {
                    sku = skuDao.getByCode(each.getSkuCode());
                }
                fromLine.setSkuId(sku.getId());
                fromLine.setQty(-each.getQty());
                String fromStatus = "", toStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(each.getFromInvStatus())) {
                    fromStatus = "良品";
                } else if ("imperfect".equals(each.getFromInvStatus())) {
                    fromStatus = "残次品";
                } else {
                    fromStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(fromStatus, customer, warehouseCode);
                fromLine.setInvStatus(status);
                if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(inboundDate);
                    fromLine.setExpDate(expDate);
                }
                fromLine.setAdjustment(msgRtn);
                fromLine.setExtMemo(each.getExtMemo());
                msgRtnAdjustmentLineDao.save(fromLine);

                // 库存状态调整明细要对应两条。 原库存状态 负。 新库存状态+
                MsgRtnAdjustmentLine toLine = new MsgRtnAdjustmentLine();
                toLine.setSkuId(sku.getId());
                toLine.setQty(each.getQty());
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(each.getToInvStatus())) {
                    toStatus = "良品";
                } else if ("imperfect".equals(each.getToInvStatus())) {
                    toStatus = "残次品";
                } else {
                    toStatus = "良品不可销售";
                }
                InventoryStatus status2 = inventoryStatusDao.getByNameAndWarehouse(toStatus, customer, warehouseCode);
                toLine.setInvStatus(status2);
                if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(inboundDate);
                    toLine.setExpDate(expDate);
                }
                toLine.setAdjustment(msgRtn);
                toLine.setExtMemo(each.getExtMemo());
                msgRtnAdjustmentLineDao.save(toLine);
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnIn.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnIn).toString();
        } catch (Exception e) {
            msgRtn.setStatus(DefaultStatus.ERROR);
            msgRtnAdjustmentDao.save(msgRtn);
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------uploadWmsInvStatusChange error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 3.13.库存数量调整反馈接口 第三方仓库将库内商品数量调整推送给宝尊WMS。
     * 
     * @param customer
     * @param sign
     * @param request
     * @return
     */
    public String uploadWmsInvChange(String customer, String sign, String request) {
        String resposeJson = ""; // 返回的JSON
        String uuid = "";
        // 成功返回的参数
        WmsMsgRtnInResponse rtnIn = new WmsMsgRtnInResponse();
        rtnIn.setCustomer(customer);
        RtnResponseMessage rtnMsg = new RtnResponseMessage();
        // 失败返回的参数
        WmsErrorResponse error = new WmsErrorResponse();
        error.setCustomer(customer);
        ErrorMsg msg = new ErrorMsg();
        MsgRtnAdjustment msgRtn = new MsgRtnAdjustment();
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(request);
            uuid = inObj.get("uuid").toString(); // uuid
            String warehouseCode = inObj.get("warehouseCode").toString(); // 仓库编码
            String orderCode = inObj.get("orderCode").toString(); // 单据唯一对接编码
            String type = inObj.get("type").toString(); // 单据类型
            String inboundDate = inObj.get("orderDate").toString(); // 实际调整时间
            String memo = inObj.get("memo").toString(); // 调整原因
            String extMemo = inObj.get("extMemo").toString(); // 扩展字段
            String owner = null;
            String ownerCode = null;
            Long shopId = null;
            if (inObj.has("owner")) {
                owner = inObj.get("owner").toString(); // 店铺
            }
            if (owner != null) {
                BiChannel bi = biChannelDao.getByName(owner);
                if (bi == null) {
                    msg.setErrorCode("1000010019");
                    msg.setMsg("店铺【" + owner + "】在宝尊WMS不存在，请填写正确店铺名称！");
                    error.setMessage(msg);
                    return net.sf.json.JSONObject.fromObject(error).toString();
                } else {
                    ownerCode = bi.getCode();
                    shopId = bi.getId();
                }
            }
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date orderDate = df.parse(inboundDate);
            if (!"51".equals(type)) {
                msg.setErrorCode("1000010003");
                msg.setMsg("单据类型错误,库存数量调整目前只支持增量");
                error.setMessage(msg);
                return net.sf.json.JSONObject.fromObject(error).toString();
            }
            // 反馈明细
            request=request.replace("\\", "\\\\");
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(request);
            net.sf.json.JSONArray array = jsonobject.getJSONArray("lines");
            List<WmsInvChangeOrderLineResponse> list = new ArrayList<WmsInvChangeOrderLineResponse>();
            for (int i = 0; i < array.size(); i++) {
                net.sf.json.JSONObject object = (net.sf.json.JSONObject) array.get(i);
                WmsInvChangeOrderLineResponse passport = (WmsInvChangeOrderLineResponse) net.sf.json.JSONObject.toBean(object, WmsInvChangeOrderLineResponse.class);
                if (passport != null) {
                    list.add(passport);
                }
            }
            msgRtn.setUuid(Long.parseLong(uuid));
            msgRtn.setSource(customer);
            msgRtn.setSourceWh(warehouseCode);
            msgRtn.setOrderCode(orderCode);
            msgRtn.setEffectiveDate(orderDate);
            msgRtn.setType(Long.parseLong(type));
            msgRtn.setExtMemo(extMemo);
            msgRtn.setMemo(memo);
            msgRtn.setOwnerSource(ownerCode);
            msgRtn.setEntityId(0l);
            msgRtn.setCreateTime(new Date());
            msgRtn.setVersion(0);
            msgRtn.setStatus(DefaultStatus.CREATED);
            msgRtnAdjustmentDao.save(msgRtn);
            Warehouse waerhouse = warehouseDao.getBySource(customer, warehouseCode);
            Long cid = null;
            if (waerhouse != null && waerhouse.getCustomer() != null) {
                cid = waerhouse.getCustomer().getId();
            }
            for (WmsInvChangeOrderLineResponse each : list) {
                MsgRtnAdjustmentLine fromLine = new MsgRtnAdjustmentLine();
                // 外包仓商品对接定制
                ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.THREEPL_SKU_CONFIG, customer);
                Sku sku = null;
                if (choose != null) {
                    if (choose.getOptionValue().equals("CODE")) {
                        sku = skuDao.getByCode(each.getSkuCode());
                    } else if (choose.getOptionValue().equals("BARCODE")) {
                        sku = skuDao.getSkuByBarCodeAndCostomer(each.getSkuCode(), cid, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                    } else if (choose.getOptionValue().equals("EXTCODE1")) {
                        sku = skuDao.getSkuByExtensionCode1(each.getSkuCode());
                    } else if (choose.getOptionValue().equals("EXTCODE2")) {
                        sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(each.getSkuCode(), cid, shopId);
                    }
                } else {
                    sku = skuDao.getByCode(each.getSkuCode());
                }
                fromLine.setSkuId(sku.getId());
                fromLine.setQty(each.getQty());
                String invStatus = "";
                // 根据外包仓反馈的库存状态，映射
                if ("accepted".equals(each.getInvStatus())) {
                    invStatus = "良品";
                } else if ("imperfect".equals(each.getInvStatus())) {
                    invStatus = "残次品";
                } else {
                    invStatus = "良品不可销售";
                }
                InventoryStatus status = inventoryStatusDao.getByNameAndWarehouse(invStatus, customer, warehouseCode);
                fromLine.setInvStatus(status);
                if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                    DateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date expDate = dfs.parse(inboundDate);
                    fromLine.setExpDate(expDate);
                }
                fromLine.setAdjustment(msgRtn);
                fromLine.setExtMemo(each.getExtMemo());
                msgRtnAdjustmentLineDao.save(fromLine);
            }
            rtnMsg.setUuid(uuid);
            rtnMsg.setStatus("1");
            rtnMsg.setMsg("SUCCESS");
            rtnIn.setMessage(rtnMsg);
            resposeJson = net.sf.json.JSONObject.fromObject(rtnIn).toString();
        } catch (Exception e) {
            msgRtn.setStatus(DefaultStatus.ERROR);
            msgRtnAdjustmentDao.save(msgRtn);
            msg.setErrorCode("10000300002");
            msg.setMsg("WMS系统异常：" + e.getMessage());
            error.setMessage(msg);
            resposeJson = net.sf.json.JSONObject.fromObject(error).toString();
            log.error("-------------------------uploadWmsInvChange error---" + customer);
        }
        return resposeJson;
    }

    /**
     * 校验外包仓入库反馈数据。
     * 
     * @return
     */
    private String validateMsgInDate(List<WmsInboundOrderResponseLine> list, MsgInboundOrder msgIn, String uuid) {
        String rtnMsg = "";
        // 1.校验商品，是不是入库单的商品。2.校验数量，允许少入，不允许多入。3.校验库存状态。 4.校验SN商品编码是否 = 入库明细的商品编码。5.检查过期时间格式
        // 入库明细
        // List<MsgInboundOrderLineCommand> inLineList =
        // msgInboundOrderLineDao.findVmiMsgInboundOrderLine(msgIn.getId(), new
        // BeanPropertyRowMapperExt<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));
        // 入库明细数据存入map, key：商品编码 ,value: 商品数量
        // Map<String, Long> inLineMap = new HashMap<String, Long>();
        // for (MsgInboundOrderLineCommand inLine : inLineList) {
        // inLineMap.put(inLine.getSpuCode(), inLine.getQty());
        // // 如存在相同sku，数量叠加
        // if (inLineMap.containsKey(inLine.getSpuCode())) {
        // Long qty = inLineMap.get(inLine.getSpuCode());
        // inLineMap.remove(inLine.getSpuCode());
        // inLineMap.put(inLine.getSpuCode(), inLine.getQty() + qty);
        // } else {
        // inLineMap.put(inLine.getSpuCode(), inLine.getQty());
        // }
        // }
        for (WmsInboundOrderResponseLine each : list) {
            // if (!inLineMap.containsKey(each.getSkuCode())) {
            // 1.校验商品，是不是入库单的商品
            // rtnMsg = "1000010005^入库反馈商品编码与入库单商品编码不一致";
            // return rtnMsg;
            // }
            // 接收时候不需要校验数据可执行性，所以注释掉
            // else {
            // if (type == 1) {
            // // 2.校验数量，允许少入，不允许多入
            // Long qty = each.getQty(); // 本次入的商品数量
            // Long inQty = inLineMap.get(each.getSkuCode()); // 总入库商品数量
            // MsgRtnInboundOrderLine msgLine =
            // msgRtnInboundOrderLineDao.findMsgInLineBySkuCode(uuid, each.getSkuCode(), new
            // BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
            // if (msgLine == null || msgLine.getQty() == 0) {
            // // 如果已入商品数量为空，代表从未如果库。 入库数量 <= 总入库数量 返回true
            // if (qty > inQty) {
            // rtnMsg = "1000010006^商品编码" + each.getSkuCode() + "入库数量异常：计划入" + inQty + "件,实际入" + qty
            // + "件,超出" + (qty - inQty) + "件";
            // return rtnMsg;
            // }
            // } else {
            // // 如果不为空， 入库数量 <= 总入库数量 - 已入数量 返回true
            // if (qty > inQty - msgLine.getQty()) {
            // rtnMsg = "1000010006^商品编码" + each.getSkuCode() + "入库数量异常：计划入" + inQty + "件,已入" +
            // msgLine.getQty() + "件,本次入" + qty + ",超出" + (qty - (inQty - msgLine.getQty())) + "件";
            // return rtnMsg;
            // }
            // }
            // } else {
            // // 2.校验数量，只允许按照计划量入
            // Long oldQty = inLineMap.get(each.getSkuCode()); // 出库数量
            // Long newQty = each.getQty(); // 本次入库数量
            // if (oldQty != newQty) {
            // rtnMsg = "1000010006^商品编码" + each.getSkuCode() + "入库数量异常：计划入" + oldQty + "件,本次入" +
            // newQty + "件";
            // return rtnMsg;
            // }
            // }
            // }
            // 3.校验库存状态
            String invStatus = each.getInvStatus();
            if (!"accepted".equals(invStatus) && !"unsales".equals(invStatus) && !"imperfect".equals(invStatus)) {
                rtnMsg = "1000010007^库存状态错误";
                return rtnMsg;
            }
            // 4.校验SN商品编码是否 = 入库明细的商品编码。
            // WmsInboundOrderResponseSnLine[] snArray = each.getSnLines();
            // for (int i = 0; i < snArray.length; i++) {
            // String snSkuCode = snArray[i].getSkuCode();
            // if (!snSkuCode.equals(each.getSkuCode())) {
            // rtnMsg += "数据异常：SN商品编码与入库明细商品编码不一致/";
            // }
            // }

            // 5.检查过期时间格式
            if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                if (each.getExpDate().length() != 10) {
                    rtnMsg = "1000010008^入库明细商品过期时间格式错误";
                    return rtnMsg;
                }
            }
        }
        return rtnMsg;
    }

    /**
     * 校验外包仓出库反馈数据
     * 
     * @return
     */
    private String validateMsgOutDate(List<WmsOutboundOrderResponseLine> list, MsgOutboundOrder msgOut, String uuid) {
        String rtnMsg = "";
        // 1.校验商品，是不是出库单的商品。2.校验数量，不允许部分出,只允许正常出。3.校验库存状态。4.检查过期时间格式

        // 出库明细数据存入map, key：商品编码 ,value: 商品数量
        // List<StaLineCommand> lineList = staLineDao.findStaLineBystaCode(msgOut.getStaCode(), new
        // BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        // Map<String, Long> inLineMap = new HashMap<String, Long>();
        // for (StaLineCommand inLine : lineList) {
        // inLineMap.put(inLine.getCode(), inLine.getReceiptQty());
        // // 如存在相同sku，数量叠加
        // if (inLineMap.containsKey(inLine.getCode())) {
        // Long qty = inLineMap.get(inLine.getCode());
        // inLineMap.remove(inLine.getCode());
        // inLineMap.put(inLine.getCode(), inLine.getReceiptQty() + qty);
        // } else {
        // inLineMap.put(inLine.getCode(), inLine.getReceiptQty());
        // }
        // }
        for (WmsOutboundOrderResponseLine each : list) {
            // if (!inLineMap.containsKey(each.getSkuCode())) {
            // // 1.校验商品，是不是出库单的商品
            // rtnMsg = "1000010005^出库反馈商品编码与出库单商品编码不一致";
            // return rtnMsg;
            // } else {
            // 不需要校验数据可执行性，所以注释掉
            // 2.校验数量，不允许部分出,只允许正常出
            // Long oldQty = inLineMap.get(each.getSkuCode()); // 出库数量
            // Long newQty = Long.parseLong(each.getQty()); // 本次出库数量
            // if (oldQty != newQty) {
            // rtnMsg = "1000010006^商品编码" + each.getSkuCode() + "出库数量异常：计划出" + oldQty + "件,本次出"
            // + newQty + "件";
            // return rtnMsg;
            // }
            // }
            // 3.校验库存状态
            String invStatus = each.getInvStatus();
            if (!"accepted".equals(invStatus) && !"unsales".equals(invStatus) && !"imperfect".equals(invStatus)) {
                rtnMsg = "1000010007^库存状态错误";
                return rtnMsg;
            }
            // 4.检查过期时间格式
            if (each.getExpDate() != null && !"".equals(each.getExpDate())) {
                if (each.getExpDate().length() != 10) {
                    rtnMsg = "^出库明细商品过期时间格式错误";
                    return rtnMsg;
                }
            }
        }
        return rtnMsg;
    }

    /**
     * 库存状态修改校验
     * 
     * @param list
     * @return
     */
    private String validateMsgInvStatus(List<WmsInvStatusChangeOrderLineResponse> list) {
        String rtnMsg = "";
        // 1 校验商品编码 是不是WMS商品，2 校验库存状态， 3.校验过期时间
        for (WmsInvStatusChangeOrderLineResponse each : list) {
            // Sku sku = skuDao.getByCode(each.getSkuCode());
            // if (sku == null) {
            // rtnMsg = "1000010006^商品编码错误";
            // return rtnMsg;
            // }
            if (!"accepted".equals(each.getToInvStatus()) && !"unsales".equals(each.getToInvStatus()) && !"imperfect".equals(each.getToInvStatus())) {
                rtnMsg = "1000010007^库存状态错误";
                return rtnMsg;
            }
            if (each.getFromInvStatus().equals(each.getToInvStatus())) {
                rtnMsg = "1000010013^原库存状态和新库存状态不能相同！";
                return rtnMsg;
            }
        }
        return rtnMsg;
    }


    public void executeIDSInvAdjustADJ(Long id) {
        try {
            MsgRtnAdjustment msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(id);
            InventoryCheck checkinfoCheck = inventoryCheckDao.findinvCheckBySlipCode(msgRtnADJ.getSource() + "_" + msgRtnADJ.getOrderCode());
            if (checkinfoCheck != null) {
                msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(msgRtnADJ.getId());
                msgRtnADJ.setStatus(DefaultStatus.FINISHED);
                msgRtnAdjustmentDao.save(msgRtnADJ);
                return;
            }
            InventoryCheck check = vmiInvAdjustByWh(msgRtnADJ);
            if (check != null) {
                msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(msgRtnADJ.getId());
                msgRtnADJ.setStatus(DefaultStatus.FINISHED);
                msgRtnAdjustmentDao.save(msgRtnADJ);
                idsManager.executionVmiAdjustment(check);
            } else {
                msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(msgRtnADJ.getId());
                msgRtnADJ.setStatus(DefaultStatus.ERROR);
                msgRtnAdjustmentDao.save(msgRtnADJ);
                return;
            }
        } catch (BusinessException e) {
            MsgRtnAdjustment msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(id);
            msgRtnADJ.setEntityId(msgRtnADJ.getEntityId() == null ? 1 : msgRtnADJ.getEntityId() + 1);
            msgRtnAdjustmentDao.save(msgRtnADJ);
            log.error("error  error code is :" + id + e);
        } catch (Exception e) {
            MsgRtnAdjustment msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(id);
            msgRtnADJ.setEntityId(msgRtnADJ.getEntityId() == null ? 1 : msgRtnADJ.getEntityId() + 1);
            msgRtnAdjustmentDao.save(msgRtnADJ);
            log.error("executeIDSInvAdjustADJ" + id, e);
        }
    }

    public InventoryCheck vmiInvAdjustByWh(MsgRtnAdjustment msgRtnADJ) throws Exception {
        InventoryCheck ic = null;
        if (msgRtnADJ != null) {
            msgRtnADJ = msgRtnAdjustmentDao.getByPrimaryKey(msgRtnADJ.getId());
            BiChannel channel = null;
            Warehouse wh = null;
            if (msgRtnADJ != null) {
                List<MsgRtnAdjustmentLine> listLine = msgRtnAdjustmentLineDao.queryLineByADJId(msgRtnADJ.getId());
                if (listLine == null || listLine.size() == 0) {
                    throw new BusinessException("系统异常");
                }
                ic = new InventoryCheck();
                ic.setCreateTime(new Date());
                ic.setStatus(InventoryCheckStatus.CREATED);
                ic.setSlipCode(msgRtnADJ.getSource() + "_" + msgRtnADJ.getOrderCode());
                ic.setType(InventoryCheckType.VMI);
                ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
                ic.setRemork(msgRtnADJ.getMemo());
                invDao.save(ic);
                wh = warehouseDao.getBySource(msgRtnADJ.getSource(), msgRtnADJ.getSourceWh());
                if (wh == null) {
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                String owner = biChannelDao.findBiChannelListByOuId(wh.getOu().getId(), listLine.get(0).getInvStatus().getId(), new SingleColumnRowMapper<String>(String.class));
                if (owner == null) {
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                channel = biChannelDao.getByCode(owner);
                if (channel == null) {
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                if (wh != null) {
                    ic.setOu(wh.getOu());
                    }
                if (channel != null) {
                    ic.setShop(channel);
                }

                Long ouId = wh.getOu().getId();
                if (ouId == null) {
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
                InventoryCheckDifTotalLine line = null;

                for (MsgRtnAdjustmentLine adjLine : listLine) {
                    if (adjLine.getId() != null) {
                        Sku sku = skuDao.getByPrimaryKey(adjLine.getSkuId());
                        if (sku == null) {
                            throw new BusinessException();
                        }
                        line = new InventoryCheckDifTotalLine();
                        line.setInventoryCheck(ic);
                        line.setSku(sku);
                        line.setStatus(adjLine.getInvStatus());
                        line.setQuantity(adjLine.getQty());
                        icLineDao.save(line);
                    }
                }
            }
        }
        return ic;
    }
}
