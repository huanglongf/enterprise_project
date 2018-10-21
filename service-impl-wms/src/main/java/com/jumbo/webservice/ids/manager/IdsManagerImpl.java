package com.jumbo.webservice.ids.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

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
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.task.IDSFeedbackInfoCommandDao;
import com.jumbo.dao.vmi.ids.AeoSkuMasterInfoDao;
import com.jumbo.dao.vmi.ids.IdsInventorySynchronousDao;
import com.jumbo.dao.vmi.ids.IdsInventorySynchronousLineDao;
import com.jumbo.dao.vmi.ids.IdsServerInformationDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgInventoryStatusDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutAdditionalLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.RelationNikeDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.webservice.ids.command.CoList;
import com.jumbo.webservice.ids.command.CoList.Co;
import com.jumbo.webservice.ids.command.CoResult;
import com.jumbo.webservice.ids.command.OrderList;
import com.jumbo.webservice.ids.command.OrderListConfirm;
import com.jumbo.webservice.ids.command.OrderOutbound;
import com.jumbo.webservice.ids.command.OrderOutbound.Order;
import com.jumbo.webservice.ids.command.RoInbound;
import com.jumbo.webservice.ids.command.RoList;
import com.jumbo.webservice.ids.command.RoList.Ro;
import com.jumbo.webservice.ids.command.RoList.Ro.RoLine;
import com.jumbo.webservice.ids.service.ServiceType;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.Packages;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.ids.AeoSkuMasterInfo;
import com.jumbo.wms.model.vmi.ids.BaozunOrderRequest;
import com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderHeader;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronousLine;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;
import com.jumbo.wms.model.vmi.ids.WmsAsn;
import com.jumbo.wms.model.vmi.ids.WmsAsn.ASNDT;
import com.jumbo.wms.model.vmi.ids.WmsAsn.ASNHD;
import com.jumbo.wms.model.vmi.ids.WmsAsn.Carrier;
import com.jumbo.wms.model.vmi.ids.WmsItr;
import com.jumbo.wms.model.vmi.ids.WmsItr.ITRDT;
import com.jumbo.wms.model.vmi.ids.WmsItr.ITRHD;
import com.jumbo.wms.model.vmi.ids.WmsOrder;
import com.jumbo.wms.model.vmi.ids.WmsOrder.Consignee;
import com.jumbo.wms.model.vmi.ids.WmsOrder.ORDDT;
import com.jumbo.wms.model.vmi.ids.WmsOrder.ORDHD;
import com.jumbo.wms.model.vmi.ids.WmsOrder.OrderInfo;
import com.jumbo.wms.model.vmi.ids.WmsOrder.OrderInfoDetail;
import com.jumbo.wms.model.vmi.ids.WmsOrder.UserDefine;
import com.jumbo.wms.model.vmi.ids.WmsOrder.UserDefineInfo;
import com.jumbo.wms.model.vmi.ids.WmsPreOrder;
import com.jumbo.wms.model.vmi.ids.WmsRec;
import com.jumbo.wms.model.vmi.ids.WmsRec.RECDT;
import com.jumbo.wms.model.vmi.ids.WmsRec.RECHD;
import com.jumbo.wms.model.vmi.ids.WmsShp;
import com.jumbo.wms.model.vmi.ids.WmsShp.Container;
import com.jumbo.wms.model.vmi.ids.WmsShp.shpHd;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgInventoryStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustmentLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutAdditionalLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturnLine;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.RelationNike;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseSourceSkuType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;
import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service("idsManager")
public class IdsManagerImpl extends BaseManagerImpl implements IdsManager {

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgRtnOutAdditionalLineDao msgRtnOutAdditionalLineDao;

    @Autowired
    private RelationNikeDao relationNikeDao;
    @Autowired
    private BiChannelDao channelDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private IDSFeedbackInfoCommandDao idsFeedbackDao;
    @Autowired
    private MsgInboundOrderLineDao msgILineDao;
    @Autowired
    private MsgRtnInboundOrderLineDao rtnOrderLineDao;
    @Autowired
    private MsgRtnInboundOrderDao rtnOrderDao;
    @Autowired
    private InOutBoundManager inoutManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnReturnLineDao msgRtnReturnLineDao;
    @Autowired
    private MsgRtnReturnDao msgRtnReturnDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private MsgRtnAdjustmentLineDao msgRtnAdjustmentLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryCheckDao invDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryCheckDifferenceLineDao icDifferenceLineDao;
    @Autowired
    private WarehouseLocationDao locDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private IdsInventorySynchronousDao idsInvSynDao;
    @Autowired
    private IdsInventorySynchronousLineDao IdsInvSynLineDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    public Rmi4Wms rmi4Wms;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private AeoSkuMasterInfoDao aeoSkuMasterInfoDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private IdsServerInformationDao idsServerInformationDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;

    @Autowired
    private MsgInventoryStatusDao msgInventoryStatusDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private ChooseOptionDao chooseDao;

    @Autowired
    private RocketMQProducerServer producerServer;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Autowired
    private MessageConfigDao messageConfigDao;

    @Autowired
    private ChooseOptionManager chooseOptionManager;

    /**
     * 
     */
    private static final long serialVersionUID = -7845664201273659818L;
    protected static final Logger log = LoggerFactory.getLogger(IdsManagerImpl.class);

    private Map<String, String> aeoOwnerCode = new HashMap<String, String>();

    public List<MsgRtnOutbound> findoutboundOrder(String resultXml) throws Exception {
        OrderOutbound orderOutbound = (OrderOutbound) MarshallerUtil.buildJaxb(OrderOutbound.class, resultXml);
        List<MsgRtnOutbound> msgoutList = new ArrayList<MsgRtnOutbound>();
        if (orderOutbound != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            for (Order order : orderOutbound.getOrder()) {
                try {
                    MsgRtnOutbound outOrder = new MsgRtnOutbound();
                    outOrder.setStatus(DefaultStatus.CREATED);
                    outOrder.setSource("IDS");
                    outOrder.setStaCode(order.getCode());
                    if (order.getLogistic() != null && OrderList.Order.JD.equals(order.getLogistic())) {
                        outOrder.setLpCode(staDeliveryInfoDao.findLpCodeByStaCode(order.getCode(), new SingleColumnRowMapper<String>(String.class)));
                    } else {
                        outOrder.setLpCode(order.getLogistic());
                    }
                    outOrder.setTrackingNo(order.getTrackingCode());
                    outOrder.setWeight(new BigDecimal(order.getWeight()));
                    try {
                        outOrder.setOutboundTime(sdf.parse(order.getOutboundTime()));
                        outOrder.setType(order.getType());
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    outOrder = msgRtnDao.save(outOrder);
                    msgRtnDao.flush();
                    msgoutList.add(outOrder);
                } catch (BusinessException ex) {
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        return msgoutList;
    }

    /**
     * 获取取消单据
     */
    public String vimIdsnoticeCancelOrderBound() {
        CoList colist = new CoList();
        Long batchNo = 0l;
        Long count = 0l;
        List<MsgOutboundOrderCancel> orderlist = msgOutboundOrderCancelDao.findMsgOutboundOrderCancelList(Constants.VIM_WH_SOURCE_IDS);
        if (orderlist != null && orderlist.size() > 0) {
            batchNo = orderlist.get(0).getBatchId();
            // 获取已执行次数
            count = orderlist.get(0).getErrorCount() == null ? 0 : orderlist.get(0).getErrorCount();
            // 判断次数是否大于4
            if (count < 5) {
                // 查询是否存在已反馈的数据
                List<MsgOutboundOrderCancel> orderlist1 = msgOutboundOrderCancelDao.findMsgOutboundOrderCancelBatch(batchNo);
                if (orderlist1.size() == 0) {
                    // 不做操作
                    // batchNo = orderlist.get(0).getBatchId();
                } else {
                    batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                    // 查询新建取消失败的单据
                    orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(Constants.VIM_WH_SOURCE_IDS);
                }
            } else {
                batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                // 查询新建取消失败的单据
                orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(Constants.VIM_WH_SOURCE_IDS);
            }
        } else {
            batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            // 查询新建取消失败的单据
            orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(Constants.VIM_WH_SOURCE_IDS);

        }
        colist.setTotalQty(String.valueOf(orderlist.size()));
        colist.setBatchId(String.valueOf(batchNo));
        for (MsgOutboundOrderCancel order : orderlist) {
            Co co = new Co();
            co.setActionflag("D");
            co.setCode(order.getStaCode());
            colist.getCo().add(co);
            order.setBatchId(batchNo);
            order.setStatus(MsgOutboundOrderCancelStatus.SENT);
            msgOutboundOrderCancelDao.save(order);
        }

        String respXml = MarshallerUtil.buildJaxb(colist);
        return respXml;
    }

    /**
     * 单独开事务，先更新一批数据，如果存在为2的就不查询了
     */
    @Override
    public int updateFixNumberData() {
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findOutBoundList(Constants.VIM_WH_SOURCE_IDS);
        if (list == null || list.size() == 0) {
            // 更新指定批次数据状态为2
            Long batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            // 更新数据
            int rs = msgOutboundOrderDao.updateLevisBatchData(Constants.VIM_WH_SOURCE_IDS, true, null, DefaultStatus.CREATED.getValue(), batchNo, DefaultStatus.SENT.getValue());
            return rs;
        }
        return list.size();
    }

    /**
     * 获取销售单据
     */
    public String iDSPullSo() {
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findOutBoundList(Constants.VIM_WH_SOURCE_IDS);
        String respXml = "";
        int count = 0;
        long batchNo = 0;
        if (list == null || list.size() == 0) {
            batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        } else {
            batchNo = list.get(0).getBatchId();
        }
        OrderList orderList = new OrderList();
        orderList.setBatchId("" + batchNo);
        count = list.size();
        List<MsgOutboundOrderLineCommand> lines = new ArrayList<MsgOutboundOrderLineCommand>();
        // 如果存在记录查询明细行记录
        if (list.size() != 0) {
            List<Long> ids = new ArrayList<Long>();
            // 获取所有头ID
            for (MsgOutboundOrder order : list) {
                ids.add(order.getId());
            }
            lines = msgOutboundOrderLineDao.findMsgDataByOrderIds(ids, new BeanPropertyRowMapperExt<MsgOutboundOrderLineCommand>(MsgOutboundOrderLineCommand.class));
        }

        for (int j = 0; j < list.size(); j++) {
            MsgOutboundOrder o = list.get(j);
            StockTransApplication sta = staDao.findStaByCode(o.getStaCode());
            if (sta.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                msgOutboundOrderDao.updateBatchNoByID(batchNo, DefaultStatus.FINISHED.getValue(), o.getId());
                count--;
                continue;
            }
            BiChannel companyShop = channelDao.getByCode(sta.getOwner());
            String plfCode = "";
            OrderList.Order order = new OrderList.Order();
            order.setCode(o.getStaCode());

            if (sta.getSlipCode1().indexOf("TB") != -1) {
                plfCode = sta.getSlipCode2();
            } else {
                plfCode = sta.getSlipCode1();
            }

            order.setPlfCode(plfCode == null ? "" : plfCode);
            BiChannel cs = channelDao.getByCode(sta.getOwner());
            order.setStoreCode(cs.getCode());
            order.setType(o.getStaType() == 42 ? "EX" : "SS");
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            order.setCreateTime(sf.format(new Date()));
            order.setCountry(o.getCity());
            order.setProvince(o.getProvince());
            order.setCity(o.getCity());
            order.setStoreCode(companyShop.getVmiWHSource());
            order.setDistrict(o.getDistrict() == null ? "" : o.getDistrict());
            order.setAddress(o.getAddress());
            order.setZipcode(o.getZipcode());
            order.setTelephone(o.getTelePhone());
            order.setMobile(o.getMobile() == null ? "" : o.getMobile());
            order.setReceiver(o.getReceiver());
            order.setLogistic(o.getLpCode() == null ? "" : o.getLpCode());
            if (order.getLogistic().equals(Transportator.JD) || order.getLogistic().equals(Transportator.JDCOD)) {
                order.setLogistic(OrderList.Order.JD);
            }
            if (o.getTransferFee() == null) {
                o.setTransferFee(new BigDecimal(0));
            }
            if (o.getTotalActual() == null) {
                o.setTotalActual(new BigDecimal(0));
            }
            order.setLogisticFee("" + o.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP));
            order.setRemark(o.getRemark() == null ? " " : o.getRemark());
            if (StringUtils.hasText(o.getLpCode())) {
                if ((Transportator.SF).equals(o.getLpCode()) || (Transportator.JD).equals(o.getLpCode()) || (Transportator.JDCOD).equals(o.getLpCode()) || (Transportator.EMS).equals(o.getLpCode()) || (Transportator.EMS_COD).equals(o.getLpCode())) {
                    if (companyShop != null) {
                        order.setSender(companyShop.getName());
                        order.setSenderTel(companyShop.getTelephone());
                        order.setSenderAddress(companyShop.getAddress());
                        order.setTransNo(o.getTransNo());
                        order.setCityCode(o.getSfCityCode());
                        order.setIsCod(o.getIsCodOrder() == null ? "" : o.getIsCodOrder() == true ? "1" : "0");
                        if (o.getIsCodOrder() != null && o.getIsCodOrder()) {
                            // COD传递金额
                            order.setTotalAmt((o.getTotalActual().add(o.getTransferFee()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                            order.setTotalActual((o.getTotalActual().add(o.getTransferFee()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                        }
                    }
                }
            }

            for (MsgOutboundOrderLineCommand line : lines) {
                if (line.getMsgId().equals(o.getId())) {
                    OrderList.Order.OrderLine orderLine = new OrderList.Order.OrderLine();
                    orderLine.setBarcode(line.getBarCode());
                    orderLine.setBzskuCode(line.getSkuCode());
                    orderLine.setQuantity("" + line.getQty());
                    orderLine.setSku(line.getSkuExtCode2() == null ? " " : line.getSkuExtCode2());
                    order.getOrderLine().add(orderLine);
                }
            }
            orderList.getOrder().add(order);
        }
        orderList.setTotalQty(String.valueOf(count));
        respXml = (String) MarshallerUtil.buildJaxb(orderList);
        return respXml;
    }

    /**
     * 确认接收单据
     */
    public void iDSCfOrder(OrderListConfirm confirm, String filename) {

        if (confirm.getBatchType().equals("Sale")) {
            if ("0".equals(confirm.getStatus())) {
                if (confirm.getOrder() != null && confirm.getOrder().size() > 0) {
                    for (OrderListConfirm.Order o : confirm.getOrder()) {
                        String staCode = o.getCode();
                        msgOutboundOrderDao.updateOutBoundStatubatchNo(DefaultStatus.CANCELED.getValue(), staCode);
                        msgOutboundOrderDao.flush();
                        List<MsgOutboundOrder> outOrderList = msgOutboundOrderDao.findeMsgOutboundOrderBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                        for (MsgOutboundOrder order : outOrderList) {
                            msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), order.getStaCode());
                        }
                    }
                }
            } else if ("1".equals(confirm.getStatus())) {
                if (confirm.getOrder() != null && confirm.getOrder().size() > 0) {
                    List<MsgOutboundOrder> outOrderList = msgOutboundOrderDao.findeMsgOutboundOrderBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                    for (MsgOutboundOrder order : outOrderList) {
                        msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), order.getStaCode());
                    }
                }
            }
        } else if (confirm.getBatchType().equals("Return")) {
            if ("0".equals(confirm.getStatus())) {
                if (confirm.getOrder() != null && confirm.getOrder().size() > 0) {
                    for (OrderListConfirm.Order o : confirm.getOrder()) {
                        String staCode = o.getCode();
                        msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.CANCELED.getValue(), staCode);
                        msgInboundOrderDao.flush();
                        List<MsgInboundOrder> inboundOrderList = msgInboundOrderDao.findMsgInbounderListByBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                        for (MsgInboundOrder inboundOrder : inboundOrderList) {
                            msgInboundOrderDao.updateInboundOrderStatusByStaCode(inboundOrder.getStaCode(), DefaultStatus.FINISHED.getValue());
                        }
                    }
                }
            } else if ("1".equals(confirm.getStatus())) {
                if (confirm.getOrder() != null && confirm.getOrder().size() > 0) {
                    List<MsgInboundOrder> inboundOrderList = msgInboundOrderDao.findMsgInbounderListByBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                    for (MsgInboundOrder inboundOrder : inboundOrderList) {
                        msgInboundOrderDao.updateInboundOrderStatusByStaCode(inboundOrder.getStaCode(), DefaultStatus.FINISHED.getValue());
                    }
                }
            }
        }
    }

    /**
     * 确认取消
     * 
     * @param resultXml
     */
    public void vimExecuteCreateCancelOrder(String resultXml) {
        CoResult bhSyncRequest = (CoResult) MarshallerUtil.buildJaxb(CoResult.class, resultXml);
        if (bhSyncRequest != null) {
            for (CoResult.Co co : bhSyncRequest.getCo()) {
                List<MsgOutboundOrderCancel> list = msgOutboundOrderCancelDao.findByStaCode(co.getCode());
                MsgOutboundOrderCancel cancleOrder = null;
                if (list != null && list.size() > 0) {
                    cancleOrder = list.get(0);
                } else {
                    continue;
                }
                if (cancleOrder.getStatus().equals(MsgOutboundOrderCancelStatus.FINISHED)) {
                    continue;
                }
                if (bhSyncRequest.getBatchId() != null && !"".equals(bhSyncRequest.getBatchId())) {
                    cancleOrder.setBatchId(Long.parseLong(bhSyncRequest.getBatchId()));
                }
                cancleOrder.setStatus(MsgOutboundOrderCancelStatus.SENT);
                if (co.getStatus() != null && co.getStatus().equals("1")) {
                    cancleOrder.setIsCanceled(true);
                    cancleOrder.setResult(co.getResult());
                } else if (co.getStatus() != null && co.getStatus().equals("0")) {
                    if (co.getResult().equals("C")) {
                        cancleOrder.setIsCanceled(true);
                        cancleOrder.setResult(co.getResult());
                    } else {
                        cancleOrder.setIsCanceled(false);
                        cancleOrder.setResult(co.getResult());
                    }
                }
            }
        }
    }

    /**
     * 反馈给IDS的信息
     */
    public void feedbackIDSInfo() {
        // 跟新须要同步的数据状态为临时状态
        msgInboundOrderDao.updateIDSASNStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS);
        // 根据临时状态数据生成同步信息
        idsFeedbackDao.saveASNByMIBO(Constants.IDS_TEMP_STATUS);
        // 将已经生成的同步信息的临时状态数据更新为完成
        msgInboundOrderDao.updateIDSASNStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue());
    }

    /**
     * 接收收货确认的反馈信息
     * 
     * @throws IOException
     */
    public void receiveRECFeedback(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
        String r = br.readLine();
        while (r != null) {
            if (r.length() > 0) {
                if (Constants.IDS_FTP_FILE_TYPE_RECHD.equals(r.substring(0, 5))) {// 获取头信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String receiptKey = r.substring(6, 16);
                    receiptKey = receiptKey == null ? null : receiptKey.trim();
                    String externReceiptKey = r.substring(16, 36);
                    externReceiptKey = externReceiptKey == null ? null : externReceiptKey.trim();
                    Date receiptDate = stringToDate(r.substring(71, 85), "yyyyMMddhhmmss");
                    String recType = r.substring(724, 734);
                    recType = recType == null ? null : recType.trim();
                    String facility = r.substring(754, 759);
                    facility = facility == null ? null : facility.trim();
                    idsFeedbackDao.saveOutBoundRECHD(interfaceActionFlag, receiptKey, externReceiptKey, receiptDate, recType, facility);
                } else if (Constants.IDS_FTP_FILE_TYPE_RECDT.equals(r.substring(0, 5))) { // 获取行信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String externReceiptKey = r.substring(6, 26);
                    externReceiptKey = externReceiptKey == null ? null : externReceiptKey.trim();
                    String externLineNo = r.substring(26, 46);
                    externLineNo = externLineNo == null ? null : externLineNo.trim();
                    String sku = r.substring(61, 82);
                    sku = sku == null ? null : sku.trim();
                    String qtyExpected = r.substring(99, 109);
                    qtyExpected = qtyExpected == null ? null : qtyExpected.trim();
                    String qtyReceived = r.substring(109, 119);
                    qtyReceived = qtyReceived == null ? null : qtyReceived.trim();
                    idsFeedbackDao.saveOutBoundRECDT(interfaceActionFlag, externReceiptKey, externLineNo, sku, qtyExpected, qtyReceived);
                }
            }
            r = br.readLine();
        }
        br.close();
    }

    /**
     * 接收发货确认的反馈信息
     */
    public void receiveSHPFeedback(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
        String r = br.readLine();
        while (r != null) {
            if (r.length() > 0) {
                if (Constants.IDS_FTP_FILE_TYPE_SHPHD.equals(r.substring(0, 5))) {// 获取头信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String externReceiptKey = r.substring(21, 41);
                    externReceiptKey = externReceiptKey == null ? null : externReceiptKey.trim();
                    String consigneeKey = r.substring(79, 94);
                    consigneeKey = consigneeKey == null ? null : consigneeKey.trim();
                    String type = r.substring(299, 309);
                    type = type == null ? null : type.trim();
                    String facility = r.substring(679, 684);
                    facility = facility == null ? null : facility.trim();
                    idsFeedbackDao.saveOutBoundSHPHD(interfaceActionFlag, externReceiptKey, consigneeKey, type, facility);
                } else if (Constants.IDS_FTP_FILE_TYPE_SHPDT.equals(r.substring(0, 5))) { // 获取行信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String externReceiptKey = r.substring(6, 26);
                    externReceiptKey = externReceiptKey == null ? null : externReceiptKey.trim();
                    String externLineNo = r.substring(26, 36);
                    externLineNo = externLineNo == null ? null : externLineNo.trim();
                    String sku = r.substring(36, 56);
                    sku = sku == null ? null : sku.trim();
                    String shippedQty = r.substring(116, 126);
                    shippedQty = shippedQty == null ? null : shippedQty.trim();
                    idsFeedbackDao.saveOutBoundSHPDT(interfaceActionFlag, externReceiptKey, externLineNo, sku, shippedQty);
                }
            }
            r = br.readLine();
        }
        br.close();
    }

    /**
     * 接收库存调整的反馈信息
     * 
     * @throws IOException
     */
    public void receiveADJFeedback(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
        String r = br.readLine();
        int i = 0;
        while (r != null) {
            if (r.length() > 0) {
                if (Constants.IDS_FTP_FILE_TYPE_ADJHD.equals(r.substring(0, 5))) {// 获取头信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String adjustmentKey = r.substring(6, 16);
                    adjustmentKey = adjustmentKey == null ? null : adjustmentKey.trim();
                    Date effectiveDate = stringToDate(r.substring(31, 45), "yyyyMMddhhmmss");
                    String adjustmentType = r.substring(55, 58);
                    adjustmentType = adjustmentType == null ? null : adjustmentType.trim();
                    i = StringUtil.getChineseNumber(r.substring(0, 264));
                    String facility = r.substring(264 - i, 269 - i);
                    facility = facility == null ? null : facility.trim();
                    idsFeedbackDao.saveOutBoundADJHD(interfaceActionFlag, adjustmentKey, effectiveDate, facility, adjustmentType);
                } else if (Constants.IDS_FTP_FILE_TYPE_ADJDT.equals(r.substring(0, 5))) { // 获取行信息
                    String interfaceActionFlag = r.substring(5, 6);
                    interfaceActionFlag = interfaceActionFlag == null ? null : interfaceActionFlag.trim();
                    String adjustmentKey = r.substring(6, 16);
                    adjustmentKey = adjustmentKey == null ? null : adjustmentKey.trim();
                    String adjustmentLineNumber = r.substring(16, 21);
                    adjustmentLineNumber = adjustmentLineNumber == null ? null : adjustmentLineNumber.trim();
                    String sku = r.substring(36, 56);
                    sku = sku == null ? null : sku.trim();
                    String reasonCode = r.substring(94, 104);
                    reasonCode = reasonCode == null ? null : reasonCode.trim();
                    String qty = r.substring(114, 124);
                    qty = qty == null ? null : qty.trim();
                    idsFeedbackDao.saveOutBoundADJDT(interfaceActionFlag, adjustmentKey, adjustmentLineNumber, sku, reasonCode, qty);
                }
            }
            r = br.readLine();
        }
        br.close();
    }

    public void synchronizationREC() {
        // 跟新须要同步的数据状态为临时状态
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_RECHD);
        // 根据临时状态数据生成头信息
        rtnOrderDao.saveByIDSREC(Constants.IDS_TEMP_STATUS, DefaultStatus.CREATED.getValue(), Constants.VIM_WH_SOURCE_IDS);
        // 根据临时状态数据生成行信息
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_RECDT);
        rtnOrderLineDao.saveByIDSRECLine(Constants.IDS_TEMP_STATUS);
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_RECDT);
        // 将已经生成的同步信息的临时状态数据更新为完成
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_RECHD);
    }

    /**
     * 将收货确认(SHP)文件数据表里面的数据写入到中间表
     */
    public void synchronizationSHP() {
        // 跟新须要同步的数据状态为临时状态
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_SHPHD);
        // 根据临时状态数据生成头 和 行信息
        msgRtnReturnDao.saveByIDSSHP(Constants.IDS_TEMP_STATUS, DefaultStatus.CREATED.getValue(), Constants.VIM_WH_SOURCE_IDS);
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_SHPDT);
        msgRtnReturnLineDao.saveByIDSSHPLine(Constants.IDS_TEMP_STATUS);
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_SHPDT);
        // 将已经生成的同步信息的临时状态数据更新为完成
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_SHPHD);
    }

    /**
     * 将库存调整(ADJ)文件数据表里面的数据写入到中间表
     */
    public void synchronizationADJ() {
        // 跟新须要同步的数据状态为临时状态
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_ADJHD);
        // 根据临时状态数据生成同步信息
        msgRtnAdjustmentDao.saveByIDSADJ(Constants.IDS_TEMP_STATUS, DefaultStatus.CREATED.getValue(), Constants.VIM_WH_SOURCE_IDS);
        idsFeedbackDao.updateStatus(DefaultStatus.CREATED.getValue(), Constants.IDS_TEMP_STATUS, Constants.IDS_FTP_FILE_TYPE_ADJDT);
        msgRtnAdjustmentLineDao.saveByIDSADJLine(Constants.IDS_TEMP_STATUS);
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_ADJDT);
        // 将已经生成的同步信息的临时状态数据更新为完成
        idsFeedbackDao.updateStatus(Constants.IDS_TEMP_STATUS, DefaultStatus.FINISHED.getValue(), Constants.IDS_FTP_FILE_TYPE_ADJHD);
    }

    /**
     * 字符转日期
     * 
     * @param date
     * @param fmt
     * @return
     */
    private Date stringToDate(String date, String fmt) {
        if (date == null || fmt == null || date.length() == 0 || fmt.length() == 0) {
            return null;
        }
        DateFormat sdf = new SimpleDateFormat(fmt);
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            log.error("", e);
        }
        return result;
    }

    /**
     * 根据IDS反馈退出出库文件数据执行退仓出库
     * 
     * @param msgRtnReturn
     */
    public void executeVmiReturnOutBound(MsgRtnReturn msgRtnReturn) {
        if (msgRtnReturn == null) {
            return;
        }
        // 判断类型、状态是否正确
        StockTransApplication sta = staDao.findStaByCode(msgRtnReturn.getStaCode());
        if ("1".equals(sta.getIsPf())) {} else {
            if (sta == null || sta.getType() == null || sta.getStatus() == null || !StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())
                    || (!StockTransApplicationType.VMI_RETURN.equals(sta.getType()) && !StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType()))) {
                msgRtnReturn.setStatus(DefaultStatus.ERROR);
                msgRtnReturnDao.save(msgRtnReturn);
                return;
            }
        }
        List<StaLine> staLine = staLineDao.findByStaId(sta.getId());
        List<MsgRtnReturnLine> retList = msgRtnReturnLineDao.queryByReturnId(msgRtnReturn.getId());
        Map<String, Long> mapQty = new HashMap<String, Long>();
        for (StaLine sl : staLine) {
            String key = sl.getSku().getId() + "_" + sl.getInvStatus().getId();
            if (mapQty.containsKey(key)) {
                mapQty.put(key, mapQty.get(key) + sl.getQuantity());
            } else {
                mapQty.put(key, sl.getQuantity());
            }
        }
        for (int i = 0; i < retList.size(); i++) {
            MsgRtnReturnLine rtnl = retList.get(i);
            String key = rtnl.getSkuId() + "_" + rtnl.getInvStatus().getId();
            if (mapQty.containsKey(key)) {
                long qty = mapQty.get(key);
                long rtnQty = rtnl.getQty();
                if (qty == rtnQty) {
                    retList.remove(i--);
                    mapQty.remove(key);
                } else if (qty > rtnQty) {
                    qty -= rtnQty;
                    mapQty.put(key, qty);
                    retList.remove(i--);
                } else if (qty < rtnQty) {
                    rtnl.setQty(rtnQty - qty);
                    mapQty.remove(key);
                }
            }
        }
        if (mapQty.size() > 0 || retList.size() > 0) {
            msgRtnReturn.setStatus(DefaultStatus.ERROR);
            msgRtnReturnDao.save(msgRtnReturn);
            return;
        }
        wareHouseManagerExe.executeVmiReturnOutBound(sta.getId(), null, sta.getMainWarehouse().getId());
        msgRtnReturn.setStatus(DefaultStatus.FINISHED);
        msgRtnReturnDao.save(msgRtnReturn);
    }

    // 失败数据
    public void executeError(MsgRtnReturn msgRtnReturn) {
        msgRtnReturn.setStatus(DefaultStatus.CANCELED);
        msgRtnReturnDao.save(msgRtnReturn);
    }

    // 创建库存调整
    public InventoryCheck createVmiAdjustment(MsgRtnAdjustment msgRtnADJ) {
        Warehouse wh = warehouseDao.getWHByVmiSource(msgRtnADJ.getSource(), msgRtnADJ.getSourceWh());
        if (wh == null) {
            log.error("IDS->WMS Adjustment error ! Warehouse is null");
            throw new BusinessException();
        }

        List<BiChannel> shopList = channelDao.getAllRefShopByWhOuId(wh.getOu().getId());
        if (shopList == null || shopList.size() == 0) {
            log.error("IDS->WMS Adjustment error ! Shop is null");
            throw new BusinessException();
        }
        // if (shopList.size() != 1) {
        // log.error("IDS->WMS Adjustment error ! Shop not 1");
        // throw new BusinessException();
        // }
        BiChannel shop = channelDao.getByvmiWHSource1(msgRtnADJ.getOwnerSource());
        if (shop == null) {
            log.error("IDS->WMS Adjustment error ! Shop is null");
            throw new BusinessException();
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        InventoryCheck ic = new InventoryCheck();
        ic.setCreateTime(new Date());
        ic.setStatus(InventoryCheckStatus.CREATED);
        ic.setType(InventoryCheckType.VMI);
        ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
        ic.setSlipCode(msgRtnADJ.getIdsKey());
        ic.setRemork("ids adjustment");
        ic.setOu(wh.getOu());
        ic.setShop(shop);
        invDao.save(ic);
        InventoryCheckDifTotalLine line = null;
        List<MsgRtnAdjustmentLine> listLine = msgRtnAdjustmentLineDao.queryLineByADJId(msgRtnADJ.getId());
        if (listLine == null || listLine.size() == 0) {
            throw new BusinessException();
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
        return ic;
    }

    public void executionVmiAdjustment(InventoryCheck ic) {
        if (ic == null || !InventoryCheckStatus.CREATED.equals(ic.getStatus())) {
            log.error("IDS->WMS Error! Adjustment execution ,Status is error!");
            throw new BusinessException();
        }
        List<InventoryCheckDifTotalLine> lineList = icLineDao.findByInventoryCheck(ic.getId());
        WarehouseLocation location = locDao.findOneWarehouseLocationByOuid(ic.getOu().getId());
        for (InventoryCheckDifTotalLine line : lineList) {
            InventoryCheckDifferenceLine icdifference = new InventoryCheckDifferenceLine();
            icdifference.setSku(line.getSku());
            icdifference.setQuantity(line.getQuantity());
            icdifference.setInventoryCheck(ic);
            icdifference.setLocation(location);
            icdifference.setStatus(line.getStatus());
            if (ic != null && ic.getShop() != null && ic.getShop().getId() != null) {
                BiChannel shop = shopDao.getByPrimaryKey(ic.getShop().getId());
                icdifference.setOwner(shop.getCode());
            }
            icDifferenceLineDao.save(icdifference);
        }
        ic.setStatus(InventoryCheckStatus.UNEXECUTE);
        invDao.save(ic);
        invDao.flush();
        // 执行
        wareHouseManager.confirmVMIInvCKAdjustment(ic);
    }

    // 跟新数据
    public void updateADJStatus(MsgRtnAdjustment msgRtnADJ, DefaultStatus status) {
        msgRtnADJ.setStatus(status);
        msgRtnAdjustmentDao.save(msgRtnADJ);
    }

    public void outOrderToSale(List<MsgRtnOutbound> msgoutList, String filename) {
        for (MsgRtnOutbound msgRtnbound : msgoutList) {

            StockTransApplication sta = staDao.findStaByCode(msgRtnbound.getStaCode());
            try {
                if (sta != null) {
                    if (msgRtnbound.getType().equals("P")) {
                        wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                    } else if (msgRtnbound.getType().equals("S")) {
                        if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                            wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnbound.getId());
                        } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                            wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                        }
                    }
                } else {
                    wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                }
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("IdsManager outOrderToSale:" + filename, ex);
                }
            }

        }
        // idsLogDao.updateIdsLogStatusByPayLoad(1l, filename);
    }

    public String findinboundReturnRequest() throws Exception {
        List<MsgInboundOrder> inboundList = null;
        Long batchNo = 0l;
        inboundList = msgInboundOrderDao.findMsgInboundOrderList(Constants.VIM_WH_SOURCE_IDS, null);
        if (inboundList != null && inboundList.size() > 0) {
            batchNo = inboundList.get(0).getBatchId();
        } else {
            batchNo = msgInboundOrderDao.findInOrderBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            inboundList = msgInboundOrderDao.findMsgReturnInboundByStatus(Constants.VIM_WH_SOURCE_IDS, null, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        RoList rolist = new RoList();
        rolist.setTotalQty(String.valueOf(inboundList.size()));

        rolist.setBatchId(String.valueOf(batchNo));
        for (MsgInboundOrder msginorder : inboundList) {
            Ro ro = new Ro();
            String plfCode = "";
            ro.setCode(msginorder.getStaCode());
            StockTransApplication sta = staDao.findStaByCode(msginorder.getStaCode());
            BiChannel channel = channelDao.getByCode(sta.getOwner());
            plfCode = sta.getSlipCode2();
            plfCode = sta.getSlipCode1();

            if (sta.getSlipCode1().indexOf("TB") != -1) {
                plfCode = sta.getSlipCode2();
            } else {
                plfCode = sta.getSlipCode1();
            }

            ro.setStoreCode(channel.getVmiWHSource());
            ro.setPlfCode(plfCode == null ? "" : plfCode);
            ro.setLogistic(msginorder.getLpCode() == null ? "" : msginorder.getLpCode());
            ro.setTrackingNo(msginorder.getTrackingNo() == null ? "" : msginorder.getTrackingNo());
            ro.setReceiver(msginorder.getReceiver() == null ? "" : msginorder.getReceiver());
            ro.setTelephone(msginorder.getTelephone() == null ? "" : msginorder.getTelephone());
            ro.setMobile(msginorder.getMobile() == null ? "" : msginorder.getMobile());
            ro.setCreateTime(sf.format(msginorder.getCreateTime()));
            ro.setRemark(msginorder.getRemark() == null ? "" : msginorder.getRemark());
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msginorder.getId());
            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                RoLine roline = new RoLine();
                roline.setSku(inLinecomd.getSku().getExtensionCode2() == null ? "" : inLinecomd.getSku().getExtensionCode2());
                roline.setBzskuCode(inLinecomd.getSku().getCode() == null ? "" : inLinecomd.getSku().getCode());
                roline.setBarcode(inLinecomd.getSku().getBarCode() == null ? "" : inLinecomd.getSku().getBarCode());
                roline.setQuantity(String.valueOf(inLinecomd.getQty()) == null ? "" : String.valueOf(inLinecomd.getQty()));
                roline.setStatus("");
                ro.getRoLine().add(roline);
            }
            rolist.getRo().add(ro);

            msgInboundOrderDao.updateInboundOrder(batchNo, DefaultStatus.SENT.getValue(), msginorder.getId());

        }
        return MarshallerUtil.buildJaxb(rolist);
    }

    public List<MsgRtnInboundOrder> transactionRtnInbound(String resultXml) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        List<MsgRtnInboundOrder> rtnList = new ArrayList<MsgRtnInboundOrder>();
        RoInbound colist = (RoInbound) MarshallerUtil.buildJaxb(RoInbound.class, resultXml);
        if (colist != null) {
            for (RoInbound.Ro ro : colist.getRo()) {
                try {
                    if (ro.getCode() == null || ro.getCode().equals("")) {
                        continue;
                    }
                    MsgRtnInboundOrder msgrtnInbound = new MsgRtnInboundOrder();
                    msgrtnInbound.setStaCode(ro.getCode());
                    Map<String, InventoryStatus> invoiceNumMap = inoutManager.findMsgInvStatusByStaCode(msgrtnInbound.getStaCode());
                    try {
                        msgrtnInbound.setSource(Constants.VIM_WH_SOURCE_IDS);
                        msgrtnInbound.setSourceWh(Constants.VIM_WH_SOURCE_IDS);
                        msgrtnInbound.setInboundTime(sf.parse(ro.getInboundTime()));
                        msgrtnInbound.setCreateTime(new Date());
                        msgrtnInbound.setStatus(DefaultStatus.CREATED);
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    rtnList.add(msgrtnInbound);
                    msgrtnInbound = rtnOrderDao.save(msgrtnInbound);
                    StockTransApplication sta = staDao.getByCode(ro.getCode());
                    BiChannel c = shopDao.getByCode(sta.getOwner());
                    for (RoInbound.Ro.RoLine line : ro.getRoLine()) {
                        MsgRtnInboundOrderLine rtnline = new MsgRtnInboundOrderLine();
                        Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(line.getSku(), c.getCustomer().getId(), c.getId());
                        if (sku != null) {
                            rtnline.setSkuCode(sku.getCode());
                        }
                        rtnline.setBarcode(line.getBarcode());
                        rtnline.setQty(Long.valueOf(line.getQuantity().toString()));
                        if (line.getStatus() == null || line.getStatus().equals("")) {
                            rtnline.setInvStatus(invoiceNumMap.get("1"));// 可销售库存
                        } else {
                            InventoryStatus status = invoiceNumMap.get(line.getStatus());
                            if (status == null) {
                                rtnline.setInvStatus(invoiceNumMap.get("1"));// 可销售库存
                            } else {
                                rtnline.setInvStatus(status);
                            }
                        }

                        rtnline.setMsgRtnInOrder(msgrtnInbound);
                        rtnOrderLineDao.save(rtnline);
                        rtnOrderLineDao.flush();
                    }
                } catch (BusinessException ex) {
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        return rtnList;
    }

    /**
     * 记录IDSPayload
     */
    public String wirteLocalFile(String payloadStr, String localFileDir) {
        log.debug("-------------IDS wirtePayloadFile---------------start-------");
        String fileName = "IDSPayload_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".txt";
        StringBuilder sb = new StringBuilder();
        // 2.写到本地文件
        if (payloadStr == null || payloadStr.trim().length() == 0) {
            log.debug("payload is null.......");
            return null;
        }
        sb.append(payloadStr);
        writeDataFile(sb.toString(), localFileDir, fileName);
        log.debug("-------------IDS wirtePayloadFile---------------end-------");
        return fileName;
    }

    /**
     * 将数据写入到本地文件
     */
    @Transactional
    private boolean writeDataFile(String data, String localDir, String fileName) {
        if (data == null || data.trim().length() == 0) {
            log.debug("payload is null ***********************  app exit");
            return false;
        }
        boolean flag = false;
        if (data == null || data.length() == 0) return false;
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localDir + File.separator + fileName, true), "UTF-8"));
            br.write(data);
            br.flush();
            flag = true;
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return flag;
    }

    @Override
    public WmsPreOrder createPreOrderRequestStr(Long batchNo, List<AdvanceOrderAddInfo> orderList, IdsServerInformation idsServerInformation, String source) {
        WmsPreOrder wmsorder = null;
        if (orderList.size() > 0) {
            wmsorder = new WmsPreOrder();
            List<com.jumbo.wms.model.vmi.ids.WmsPreOrder.ORDHD> ordhdList = new ArrayList<com.jumbo.wms.model.vmi.ids.WmsPreOrder.ORDHD>();
            com.jumbo.wms.model.vmi.ids.WmsPreOrder.ORDHD o = null;
            for (AdvanceOrderAddInfo ad : orderList) {
                o = new com.jumbo.wms.model.vmi.ids.WmsPreOrder.ORDHD();
                o.setInterfaceActionFlag("R");// R-释放订单
                o.setStorerKey(idsServerInformation.getStorerKey());// 商户编号, 由利丰指定唯一客户号
                o.setFacility(idsServerInformation.getFacility());
                o.setExternOrderKey(ad.getLfMemo());// 作业单
                ordhdList.add(o);
            }
            wmsorder.setORDHD(ordhdList);
            wmsorder.setBatchID(String.valueOf(batchNo));
        }
        return wmsorder;
    }

    public WmsOrder createOrderRequestStr(long batchNo, List<MsgOutboundOrder> orderList, IdsServerInformation idsServerInformation, WarehouseSourceSkuType skuType) {
        WmsOrder wmsorder = null;
        if (orderList.size() > 0) {
            ChooseOption chooseOption = chooseDao.findByCategoryCodeAndKey(idsServerInformation.getSource(), "godiva");
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            List<ORDHD> ordhdList = new ArrayList<ORDHD>();
            wmsorder = new WmsOrder();
            Map<String, BiChannel> channelMap = new HashMap<String, BiChannel>();
            String errorSta = "";
            // Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            for (MsgOutboundOrder orderinfo : orderList) {
                try {
                    StockTransApplication sta = staDao.findStaByCode(orderinfo.getStaCode());
                    errorSta = orderinfo.getStaCode();
                    BiChannel ch = channelMap.get(sta.getOwner());
                    if (ch == null) {
                        ch = biChannelDao.getByCode(sta.getOwner());
                        channelMap.put(sta.getOwner(), ch);
                    }
                    // 判断发送集合中是否有刚刚取消订单
                    if (sta != null && !sta.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                        ORDHD ordhd = new ORDHD();

                        ordhd.setInterfaceActionFlag("A");
                        // ordhd.setStorerKey("ANF");// 商户编号, 由利丰指定
                        // ordhd.setFacility("BS13");
                        if (Constants.VIM_WH_SOURCE_UA_IDS.equals(idsServerInformation.getSource())) {
                            List<StaSpecialExecutedCommand> list =
                                    staSpecialExecutedDao.findStaSpecialByStaId(sta.getId(), StaSpecialExecuteType.IDS_SH_UA.getValue(), new BeanPropertyRowMapperExt<StaSpecialExecutedCommand>(StaSpecialExecutedCommand.class));
                            if (null != list && list.size() > 0) {
                                ordhd.setBuyerPO(String.valueOf(StaSpecialExecuteType.IDS_SH_UA.getValue()));
                                ordhd.setC_Company(list.get(0).getMemo());
                            }
                        }
                        findAEOOwnerCode();
                        if (aeoOwnerCode.get("AEOJD").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_JD_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_JD_IDS);
                        } else if (aeoOwnerCode.get("AEOGF").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_IDS);
                        } else if (aeoOwnerCode.get("AEORTN").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_RTN_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_RTN_IDS);
                        } else {
                            ordhd.setStorerKey(idsServerInformation.getStorerKey());
                            ordhd.setFacility(idsServerInformation.getFacility());
                        }

                        ordhd.setExternOrderKey(orderinfo.getStaCode());
                        if (orderinfo.getStaType() == 42) {
                            // ordhd.setM_Company(sta.getSlipCode1());
                            if (StringUtils.hasText(sta.getSlipCode2())) {
                                if (sta.getSlipCode2().indexOf("EAS") != -1) {
                                    ordhd.setM_Company(sta.getSlipCode1());
                                } else {
                                    ordhd.setM_Company(sta.getSlipCode2());
                                }
                            } else {
                                ordhd.setM_Company(sta.getSlipCode1());
                            }

                        } else {
                            if (Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(idsServerInformation.getSource())) {
                                ordhd.setM_Company(sta.getRefSlipCode());
                            } else {
                                ordhd.setM_Company(orderinfo.getOuterOrderCode() == null ? "" : orderinfo.getOuterOrderCode());
                            }
                        }
                        StaDeliveryInfo staDelivery = sta.getStaDeliveryInfo();
                        if (staDelivery != null) {
                            BigDecimal labelPrice = channelInsuranceManager.getInsurance(sta.getOwner(), sta.getStaDeliveryInfo().getInsuranceAmount());
                            if (labelPrice == null) {
                                ordhd.setLabelPrice("");
                            } else {
                                ordhd.setLabelPrice(labelPrice.toString());
                            }
                        } else {
                            ordhd.setLabelPrice("");
                        }
                        // nike外包仓传时限类型
                        if ("NBJ01".equals(idsServerInformation.getSource())) {
                            String transTimeType = null;
                            if (staDelivery.getTransTimeType().getValue() == 5) {
                                transTimeType = "LIMIT41";
                            } else if (staDelivery.getTransTimeType().getValue() == 6) {
                                transTimeType = " LIMIT43";
                            } else {
                                transTimeType = "";
                            }
                            ordhd.setDeliveryNote(transTimeType);
                        }
                        ordhd.setSalesman("");// 分销商 TRANS_TIME_TYPE
                        ordhd.setDeliveryPlace(orderinfo.getSfCityCode() == null ? "" : orderinfo.getSfCityCode());// 快递目的地代码
                        if ("1".equals(sta.getIsPreSale())) {
                            ordhd.setPriority("P");// 预售
                        } else {
                            ordhd.setPriority("1");// 零售
                        }

                        if (orderinfo.getIsCodOrder() != null && orderinfo.getIsCodOrder()) {
                            ordhd.setType("COD");
                        } else {
                            ordhd.setType("NORMAL");
                        }
                        if (orderinfo.getTransferFee() == null) {
                            orderinfo.setTransferFee(new BigDecimal(0));
                        }
                        if (orderinfo.getTotalActual() == null) {
                            orderinfo.setTotalActual(new BigDecimal(0));
                        }
                        ordhd.setInvoiceAmount(orderinfo.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP).add(orderinfo.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP)));

                        List<StaInvoice> staInvoices = staInvoiceDao.getBySta(sta.getId());

                        boolean isNeedInvoice = staInvoices.size() == 0 ? false : true;

                        ordhd.setPrintFlag(isNeedInvoice ? "1" : "0");// 是否需要发票
                        ordhd.setIntermodalVehicle("EXPRESS");
                        if (Constants.VIM_WH_SOURCE_IDS.equals(idsServerInformation.getSource()) && null != orderinfo.getLpCode() && (orderinfo.getLpCode().equals("JD") || orderinfo.getLpCode().equals("JDCOD"))) {
                            ordhd.setShipperKey("JDEX");
                        } else {
                            ordhd.setShipperKey(orderinfo.getLpCode() == null ? "" : orderinfo.getLpCode()); // 承运商代码
                        }
                        Consignee consignee = new Consignee();
                        consignee.setC_Contact1(orderinfo.getReceiver().replaceAll("&", " "));
                        consignee.setC_Zip(orderinfo.getZipcode());
                        consignee.setC_Phone1(orderinfo.getMobile() == null ? "" : orderinfo.getMobile());
                        consignee.setC_Phone2(orderinfo.getTelePhone() == null ? "" : orderinfo.getTelePhone());
                        consignee.setC_State(orderinfo.getProvince());
                        consignee.setC_City(orderinfo.getCity());
                        consignee.setC_Address1(orderinfo.getDistrict());
                        consignee.setC_Address2(orderinfo.getAddress().replaceAll("&", " "));
                        ordhd.setConsignee(consignee);
                        if (null != idsServerInformation.getSource() && Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(idsServerInformation.getSource())) {
                            if (null != sta.getMemo() && !"".equals(sta.getMemo()) && sta.getMemo().indexOf("psDate") > 0 && sta.getMemo().indexOf("period") > 0) {
                                JSONObject object = JSONObject.fromObject(sta.getMemo().trim());
                                String psDate = object.getString("psDate");
                                String day = object.getString("period");
                                ordhd.setNotes(psDate + "," + day);
                            } else {
                                ordhd.setNotes(null);
                            }
                        } else {
                            if (Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(idsServerInformation.getSource())) {
                                ordhd.setNotes(sta.getMemo());
                            } else {
                                ordhd.setNotes(orderinfo.getRemark());
                            }

                        }
                        ordhd.setOrderDate(sf.format(orderinfo.getCreateTime()));
                        List<UserDefine> userDefines = new ArrayList<UserDefine>();
                        // 运费
                        UserDefine userdefind1 = new UserDefine();
                        UserDefineInfo info = new UserDefineInfo();
                        info.setUserDefine_No("1");
                        info.setUserDefine_Value(orderinfo.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        userdefind1.setUserDefine(info);
                        userDefines.add(userdefind1);

                        // 其他费用(换购费/服务费)
                        UserDefineInfo info2 = new UserDefineInfo();
                        UserDefine userdefind2 = new UserDefine();
                        info2.setUserDefine_No("2");
                        info2.setUserDefine_Value(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        userdefind2.setUserDefine(info2);
                        userDefines.add(userdefind2);

                        // 店铺名称
                        UserDefineInfo info3 = new UserDefineInfo();
                        UserDefine userdefind3 = new UserDefine();
                        info3.setUserDefine_No("3");
                        if (Constants.VIM_WH_SOURCE_IDS.equals(idsServerInformation.getSource())) {
                            info3.setUserDefine_Value(ch.getVmiWHSource());
                        } else {
                            info3.setUserDefine_Value(ch.getName());
                        }
                        userdefind3.setUserDefine(info3);
                        userDefines.add(userdefind3);

                        // 快递单号
                        UserDefineInfo info4 = new UserDefineInfo();
                        UserDefine userdefind4 = new UserDefine();
                        info4.setUserDefine_No("4");
                        info4.setUserDefine_Value(orderinfo.getTransNo() == null ? "" : orderinfo.getTransNo());
                        userdefind4.setUserDefine(info4);
                        userDefines.add(userdefind4);

                        // Cod代收金额
                        UserDefine userdefind5 = new UserDefine();
                        UserDefineInfo info5 = new UserDefineInfo();
                        info5.setUserDefine_No("5");
                        if (orderinfo.getIsCodOrder() != null && orderinfo.getIsCodOrder()) {
                            info5.setUserDefine_Value((orderinfo.getTotalActual().add(orderinfo.getTransferFee()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                        } else {
                            info5.setUserDefine_Value(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        }
                        userdefind5.setUserDefine(info5);
                        userDefines.add(userdefind5);
                        ordhd.setUserDefines(userDefines);
                        List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
                        if (isNeedInvoice) {
                            OrderInfo infovv = new OrderInfo();
                            staInvoices = staInvoiceDao.getBySta(sta.getId());
                            for (StaInvoice staInvoice : staInvoices) {
                                // 需要发票
                                // 查询invoiceTax
                                int j = 1;

                                OrderInfoDetail infoDetail = new OrderInfoDetail();
                                infoDetail.setOrderInfo_No("" + j);
                                infoDetail.setOrderInfo_Type("");
                                infoDetail.setOrderInfo_Amount(staInvoice.getAmt());// A
                                // F
                                // 要加运费
                                infoDetail.setOrderInfo_Title(staInvoice.getPayer());
                                if (null != idsServerInformation && Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(idsServerInformation.getSource())) {
                                    infoDetail.setOrderInfo_Content("食品");
                                } else {
                                    infoDetail.setOrderInfo_Content("服装//配件");
                                }
                                if (null != chooseOption && "1".equals(chooseOption.getOptionValue())) {
                                    infoDetail.setOrderInfo_Num(staInvoice.getIdentificationNumber());
                                }
                                infovv.setOrderInfo(infoDetail);
                                orderInfos.add(infovv);
                            }
                        } else {
                            // 无需发票
                            OrderInfo orderinfoc = new OrderInfo();
                            OrderInfoDetail infoDetail = new OrderInfoDetail();
                            infoDetail.setOrderInfo_No("1");
                            // info.setOrderInfo_Type(salesOrder.getInvoiceTypeInt()==1?"普通商业零售":" 增值税专用");
                            infoDetail.setOrderInfo_Amount(new BigDecimal(0));
                            infoDetail.setOrderInfo_Title("");
                            infoDetail.setOrderInfo_Content("");
                            orderinfoc.setOrderInfo(infoDetail);
                            orderInfos.add(orderinfoc);
                        }
                        ordhd.setOrderInformation(orderInfos);
                        ordhdList.add(ordhd);


                        List<ORDDT> orddtList = new ArrayList<ORDDT>();
                        List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(orderinfo.getId());
                        int i = 1;
                        for (MsgOutboundOrderLine line : lines) {
                            ORDDT orddt = new ORDDT();
                            orddt.setExternLineNo("0000" + i);
                            String sku = WarehouseSourceSkuType.getSkuByWhSourceSkuType(line.getSku(), skuType);
                            // 此处未维护类型时 暂时默认为 ext_code2
                            orddt.setSKU(sku == null ? line.getSku().getExtensionCode2() : sku);
                            orddt.setOpenQty(line.getQty().intValue());
                            if (line.getTotalActual() == null) {
                                orddt.setUnitPrice(new BigDecimal("0.00"));
                            } else {
                                orddt.setUnitPrice(line.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP));
                            }

                            // GODIVA 定制

                            if (Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(idsServerInformation.getSource())) {
                                if (null != line.getLineReserve1() && !"".equals(line.getLineReserve1())) {
                                    List<Packages> packagesList = new ArrayList<Packages>();
                                    String memo = line.getLineReserve1();
                                    try { // Matcher m = p.matcher(memo); // memo = //
                                          // m.replaceAll("");
                                        JSONObject obj = JSONObject.fromObject(memo.trim());
                                        JSONArray jsonArray = (JSONArray) obj.get("skus");
                                        Packages packages = new Packages();
                                        for (int k = 0; k < jsonArray.size(); k++) {
                                            JSONObject jsonObject = (JSONObject) jsonArray.get(k);
                                            if (jsonObject.containsKey("sidaiCode")) {
                                                packages.setSidaiCode(jsonObject.getString("sidaiCode"));
                                            } else {
                                                packages.setSidaiCode("");
                                            }
                                            if (jsonObject.containsKey("peishiCode")) {
                                                packages.setPeishiCode(jsonObject.getString("peishiCode"));
                                            } else {
                                                packages.setPeishiCode("");
                                            }

                                            packages.setQty(1);
                                            if (jsonObject.containsKey("skuCode")) {
                                                packages.setSkuCode(jsonObject.getString("skuCode"));
                                            } else {
                                                packages.setSkuCode("");
                                            }
                                            if (jsonObject.containsKey("wishCard")) {
                                                packages.setWishCard(jsonObject.getString("wishCard"));
                                            } else {
                                                packages.setWishCard("");
                                            }
                                            if (jsonObject.containsKey("wishComment")) {
                                                packages.setWishComment(jsonObject.getString("wishComment"));
                                            } else {
                                                packages.setWishComment("");
                                            }
                                        }
                                        packagesList.add(packages);
                                        orddt.setPackages(packagesList);
                                    } catch (Exception e) {
                                        log.error("===>GODIVA  createOrderRequestStr error sta is: {}" + e, errorSta);
                                    }
                                }
                            }


                            List<UserDefine> userDefineList = new ArrayList<UserDefine>();
                            UserDefine userDefine = new UserDefine();
                            UserDefineInfo userDefineinfo = new UserDefineInfo();
                            userDefineinfo.setUserDefine_No("1");
                            userDefineinfo.setUserDefine_Value(line.getSkuName() == null ? "" : line.getSkuName());
                            userDefine.setUserDefine(userDefineinfo);
                            userDefineList.add(userDefine);



                            UserDefine userDefine2 = new UserDefine();
                            UserDefineInfo userDefineinfo2 = new UserDefineInfo();
                            userDefineinfo2.setUserDefine_No("2");
                            if (Constants.VIM_WH_SOURCE_IDS_VS.equals(idsServerInformation.getSource())) {
                                if (line.getSku() != null) {
                                    StaLine staLine = staLineDao.findListPriceByStaIdAndSkuId(sta.getId(), line.getSku().getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
                                    if (null != staLine) {
                                        userDefineinfo2.setUserDefine_Value(staLine.getListPrice() == null ? new BigDecimal("0.00").toString() : staLine.getListPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                    } else {
                                        userDefineinfo2.setUserDefine_Value(line.getSku().getListPrice() == null ? new BigDecimal("0.00").toString() : line.getSku().getListPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                    }

                                } else {
                                    userDefineinfo2.setUserDefine_Value(new BigDecimal("0.00").toString());
                                }
                            } else {
                                if (line.getSku() != null) {
                                    userDefineinfo2.setUserDefine_Value(line.getSku().getListPrice() == null ? new BigDecimal("0.00").toString() : line.getSku().getListPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                } else {
                                    userDefineinfo2.setUserDefine_Value(new BigDecimal("0.00").toString());
                                }
                            }

                            userDefine2.setUserDefine(userDefineinfo2);
                            userDefineList.add(userDefine2);
                            orddt.setUserDefines(userDefineList);
                            orddtList.add(orddt);
                            i++;
                        }
                        ordhd.setORDDT(orddtList);

                        // 更新批次号和状态
                        // msgOutboundOrderDao.updateBatchNoByID(batchNo,
                        // DefaultStatus.SENT.getValue(), orderinfo.getId());
                    } else {
                        msgOutboundOrderDao.updateBatchNoByID(batchNo, DefaultStatus.INEXECUTION.getValue(), orderinfo.getId());
                    }
                } catch (BusinessException ex) {
                    log.error("===>IDS  createOrderRequestStr error sta is: {}", errorSta);
                    log.error("===>IDS  createOrderRequestStr error:", ex);
                }
            }
            wmsorder.setORDHD(ordhdList);
            wmsorder.setBatchID(String.valueOf(batchNo));
        }
        return wmsorder;

    }

    /**
     * 订单在wms系统中创建后反馈 0 – 订单已接收 5 – 已包装
     * 
     * @param resultXml
     */
    public OrderConfirm orderConfirmResponse(String resultXml) {
        WmsShp wmsShp = (WmsShp) MarshallerUtil.buildJaxb(WmsShp.class, resultXml);
        OrderConfirm shpComfirm = new OrderConfirm();
        List<ConfirmResult> resultList = new ArrayList<ConfirmResult>();
        for (shpHd hd : wmsShp.getSHPHD()) {
            StockTransApplication sta = staDao.findStaByCode(hd.getExternOrderKey());
            ConfirmResult result = new ConfirmResult();
            if (sta != null && !sta.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                result.setExternDocKey(hd.getExternOrderKey());
                result.setSuccess("true");
                log.debug("Debug-LF logistic_order_status sta:" + result.getExternDocKey() + " status:" + hd.getSOStatus());
                if (ServiceType.LF_STATUS_CREATE.equals(hd.getSOStatus())) {
                    msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.EXECUT_CREATE.getValue(), hd.getExternOrderKey());
                } else if (ServiceType.LF_STATUS_PICKING.equals(hd.getSOStatus())) {
                    msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), hd.getExternOrderKey());
                } else {
                    log.debug("Debug-LF logistic_order_status 异常:" + result.getExternDocKey() + " status:" + hd.getSOStatus());
                    msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), hd.getExternOrderKey());
                }
            } else {
                result.setExternDocKey(hd.getExternOrderKey());
                result.setSuccess("false");
                result.setReason("B11");
                result.setDescription("订单已关闭或状态异常");
                msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.INEXECUTION.getValue(), hd.getExternOrderKey());
            }
            resultList.add(result);
        }
        shpComfirm.setResult(resultList);
        shpComfirm.setBatchID(wmsShp.getBatchID());
        return shpComfirm;
    }

    /**
     * 没有接收创单成功反馈的订单状态重置从新发送
     */
    public void updateAfreshSendOrder(String batchID, String source) {
        if (StringUtils.hasText(batchID)) {
            msgOutboundOrderDao.sendSOOutboundOrderAfreshs(Long.parseLong(batchID), source);
        }
    }

    /**
     * 订单出库信息反馈
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public OrderConfirm orderDeliveryRequest(String resultXml, String source) {
        Boolean isMq = false;
        WmsShp wmssh = (WmsShp) MarshallerUtil.buildJaxb(WmsShp.class, resultXml);
        // MQ 开关 LF 出库反馈
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_MQ_RTN_OUTBOUNT_LF, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            isMq = true;
        }
        OrderConfirm orderConfirm = null;
        if (wmssh != null) {
            orderConfirm = new OrderConfirm();
            orderConfirm.setBatchID(wmssh.getBatchID());

            List<ConfirmResult> resultList = new ArrayList<ConfirmResult>();
            List<shpHd> shphds = wmssh.getSHPHD();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
            for (shpHd order : shphds) {
                try {
                    MsgRtnOutbound outOrder = new MsgRtnOutbound();
                    outOrder.setStatus(DefaultStatus.CREATED);
                    outOrder.setSource(source);
                    outOrder.setStaCode(order.getExternOrderKey());
                    if (isMq) {
                        outOrder.setIsMq("1");
                    }
                    if (source.equals(Constants.VIM_WH_SOURCE_IDS) && "JDEX".equals(order.getShipperKey())) {
                        outOrder.setLpCode("JD");
                    } else {
                        outOrder.setLpCode(order.getShipperKey());
                    }

                    outOrder.setTrackingNo(order.getLoadKey());
                    if (order.getWeight() != null && order.getWeight() != "") {
                        outOrder.setWeight(new BigDecimal(order.getWeight()));
                    }
                    try {
                        if (order.getEffectiveDate().length() > 12) {
                            outOrder.setOutboundTime(sdfs.parse(order.getEffectiveDate()));
                        } else {
                            outOrder.setOutboundTime(sdf.parse(order.getEffectiveDate()));
                        }

                        // outOrder.setType(order.getType());
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    outOrder = msgRtnDao.save(outOrder);
                    WmsShp.Mbol mbolinfo = order.getMbol();
                    if (mbolinfo != null) {
                        List<Container> containers = null;
                        if (mbolinfo.getContainers() != null) {
                            containers = mbolinfo.getContainers().getContainer();
                        }
                        if (containers != null && containers.size() > 0) {
                            StringBuffer stringBuffer = new StringBuffer();
                            Map<String, MsgRtnOutAdditionalLine> map = new HashMap<String, MsgRtnOutAdditionalLine>();
                            for (Container container : containers) {
                                stringBuffer.append(container.getCtnType());
                                stringBuffer.append(",");
                                String sc = container.getCtnType().trim();
                                if (!StringUtil.isEmpty(sc)) {
                                    if (map.containsKey(sc)) {
                                        MsgRtnOutAdditionalLine l = map.get(sc);
                                        l.setQty(l.getQty() + 1);
                                        msgRtnOutAdditionalLineDao.save(l);
                                    } else {
                                        MsgRtnOutAdditionalLine l = new MsgRtnOutAdditionalLine();
                                        l.setBarCode(sc);
                                        l.setSkuCode(sc);
                                        l.setQty(1l);
                                        l.setMsgRtnOutbound(outOrder);
                                        msgRtnOutAdditionalLineDao.save(l);
                                    }
                                }
                            }
                            String ctnType = stringBuffer.toString();
                            outOrder.setCtnType(ctnType.length() > 1000 ? (ctnType.substring(0, 996) + "...") : ctnType);
                            outOrder = msgRtnDao.save(outOrder);
                        }
                    }
                    msgRtnDao.flush();
                    //
                    if (isMq) {// 发mq
                        try {
                            MsgRtnOutbound msgRtnOut2 = wareHouseManager.saveMsgRtnOut(outOrder);// 单起事物保存
                            // MsgRtnOutbound msgRtnOut2 =
                            // msgRtnDao.getByPrimaryKey(outOrder.getId());// 发mq
                            String reqJson = JsonUtil.writeValue(msgRtnOut2);
                            MessageCommond mes = new MessageCommond();
                            mes.setIsMsgBodySend(false);
                            mes.setMsgId(msgRtnOut2.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                            mes.setMsgType("com.jumbo.webservice.ids.manager.IdsManagerImpl.orderDeliveryRequest");
                            mes.setMsgBody(reqJson);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date();
                            mes.setSendTime(sdf2.format(date));
                            producerServer.sendDataMsgConcurrently(MQ_WMS3_MQ_RTN_OUTBOUNT_LF, mes);
                            // 保存进mongodb
                            MongoDBMessage mdbm = new MongoDBMessage();
                            BeanUtils.copyProperties(mes, mdbm);
                            mdbm.setStaCode(msgRtnOut2.getStaCode());
                            mdbm.setOtherUniqueKey(null);
                            mdbm.setMsgBody(reqJson);
                            mdbm.setMemo(MQ_WMS3_MQ_RTN_OUTBOUNT_LF);
                            mongoOperation.save(mdbm);
                            log.debug("rmi Call oms orderDeliveryRequest response interface by MQ end:" + msgRtnOut2.getId());
                            msgRtnOut2.setMqStatus("10");
                            msgRtnDao.save(msgRtnOut2);
                        } catch (Exception e) {
                            log.error("orderDeliveryRequest isMQ  error---:" + outOrder.getStaCode());
                            log.error("orderDeliveryRequest_mq", e);
                        }
                    }
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(order.getExternOrderKey());
                    result.setSuccess("true");
                    resultList.add(result);
                } catch (BusinessException ex) {
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(order.getExternOrderKey());
                    result.setSuccess("false");
                    resultList.add(result);
                    log.error("", ex);
                    // throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
            orderConfirm.setResult(resultList);
        }
        return orderConfirm;
    }

    /**
     * 获取退货信息
     */
    public String returnNotifyReques(String source, IdsServerInformation idsServerInformation) {
        List<MsgInboundOrder> inboundList = null;
        Long batchNo = 0l;
        inboundList = msgInboundOrderDao.findInboundOrderByStatus(DefaultStatus.SENT, source, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        if (inboundList.size() == 0) {
            batchNo = msgInboundOrderDao.findInOrderBatchNo(new SingleColumnRowMapper<Long>(Long.class));
            inboundList = msgInboundOrderDao.findMsgReturnInboundByStatusToLF(source, null, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        } else {
            batchNo = inboundList.get(0).getBatchId();
        }

        WmsAsn wmsAsn = new WmsAsn();
        wmsAsn.setBatchID(batchNo.toString());
        List<WmsAsn.ASNHD> asnhdList = new ArrayList<ASNHD>();
        for (MsgInboundOrder msginorder : inboundList) {
            StockTransApplication sta = staDao.getByCode(msginorder.getStaCode());
            if (sta == null) {
                continue;
            }
            WmsAsn.ASNHD asnhd = new WmsAsn.ASNHD();
            asnhd.setInterfaceActionFlag("A");
            // asnhd.setStorerKey("ANF");
            // asnhd.setFacility("BS13S");

            // AEO
            if (Constants.VIM_WH_SOURCE_AEO_IDS.equals(msginorder.getSource())) {
                findAEOOwnerCode();
                BiChannel bc = biChannelDao.getByPrimaryKey(msginorder.getShopId());
                asnhd.setStorerKey(Constants.VIM_WH_STORER_AEO_IDS);
                if (aeoOwnerCode.get("AEOJD").equals(bc.getCode())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_JD_IDS);
                } else if (aeoOwnerCode.get("AEOGF").equals(bc.getCode())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_IDS);
                } else if (aeoOwnerCode.get("AEORTN").equals(bc.getCode())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_RTN_IDS);
                }
            } else {
                asnhd.setStorerKey(idsServerInformation.getStorerKey());
                asnhd.setFacility(idsServerInformation.getFacility2());// AF退货要退到残次仓

            }


            asnhd.setExternReceiptKey(msginorder.getStaCode());

            asnhd.setWarehouseReference(msginorder.getRefSlipCode() == null ? "" : msginorder.getRefSlipCode());
            // 推送寄回快递单号
            StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            if (info != null) {
                asnhd.setVehicleNumber(info.getTrackingNo() == null ? "" : info.getTrackingNo());
            } else {
                asnhd.setVehicleNumber("");
            }
            Carrier carrier = new Carrier();
            carrier.setCarrierKey(msginorder.getLpCode() == null ? "" : msginorder.getLpCode());
            carrier.setCarrierName(msginorder.getReceiver() == null ? "" : msginorder.getReceiver().replaceAll("&", " "));
            carrier.setCarrierZip("");
            carrier.setCarrierState("");
            carrier.setCarrierCity("");
            carrier.setCarrierAddress1("");
            carrier.setCarrierAddress2("");
            asnhd.setCarrier(carrier);
            asnhd.setDocType("R");
            if (null != source && Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(source)) {
                asnhd.setNotes(info.getTransMemo() == null ? sta.getMemo() : info.getTransMemo());
            } else {
                asnhd.setNotes(msginorder.getRemark() == null ? "" : msginorder.getRemark());
            }
            // asnhd.setNotes(msginorder.getRemark() == null ? "" : msginorder.getRemark());
            List<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine> userDefines = new ArrayList<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine>();

            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo1 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo1.setUserDefine_No("1");
            if (StringUtils.hasText(sta.getSlipCode2())) {
                if (sta.getSlipCode2().indexOf("RAS") != -1 || sta.getSlipCode2().indexOf("EAS") != -1) {
                    userDefineinfo1.setUserDefine_Value(sta.getSlipCode1() == null ? "" : sta.getSlipCode1());
                } else {
                    userDefineinfo1.setUserDefine_Value(sta.getSlipCode2() == null ? "" : sta.getSlipCode2());
                }
            } else {
                userDefineinfo1.setUserDefine_Value(sta.getSlipCode1() == null ? "" : sta.getSlipCode1());
            }
            userDefine.setUserDefine(userDefineinfo1);
            userDefines.add(userDefine);


            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine2 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo2 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo2.setUserDefine_No("2");
            userDefineinfo2.setUserDefine_Value(msginorder.getMobile() == null ? "" : msginorder.getMobile());
            userDefine2.setUserDefine(userDefineinfo2);
            userDefines.add(userDefine2);


            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine3 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo3 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo3.setUserDefine_No("3");
            userDefineinfo3.setUserDefine_Value(msginorder.getTelephone() == null ? "" : msginorder.getTelephone());
            userDefine3.setUserDefine(userDefineinfo3);
            userDefines.add(userDefine3);
            asnhd.setUserDefines(userDefines);
            ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("cancel", Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
            if (null != chooseOption && "0".equals(chooseOption.getOptionValue())) {

            } else {
                if (null != source && (Constants.VIM_WH_SOURCE_NBAUA_IDS.equals(source) || Constants.VIM_WH_SOURCE_UA_IDS.equals(source)) || (Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(source)) || (Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(source))) {
                    BiChannel channel = biChannelDao.getByCode(sta.getOwner());
                    com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine4 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
                    com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo4 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
                    userDefineinfo4.setUserDefine_No("4");
                    userDefineinfo4.setUserDefine_Value(channel.getName());
                    userDefine4.setUserDefine(userDefineinfo4);
                    userDefines.add(userDefine4);
                }
            }
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msginorder.getId());
            List<ASNDT> asndtList = new ArrayList<ASNDT>();
            int i = 1;
            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                ASNDT asndt = new ASNDT();
                asndt.setExternLineNo("000" + i);
                if (Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(source)) {
                    asndt.setSKU(inLinecomd.getSku().getBarCode() == null ? "" : inLinecomd.getSku().getBarCode());
                } else {
                    asndt.setSKU(inLinecomd.getSku().getExtensionCode2() == null ? "" : inLinecomd.getSku().getExtensionCode2());

                }

                asndt.setQtyExpected(String.valueOf(inLinecomd.getQty()) == null ? "" : String.valueOf(inLinecomd.getQty()));
                asndt.setUnitPrice(inLinecomd.getUnitPrice() == null ? new BigDecimal(0) : inLinecomd.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP));


                String vmiStatus = "";
                InventoryStatus inventoryStatus = null;
                if (inLinecomd.getInvStatus() != null) {
                    vmiStatus = msgInventoryStatusDao.findInventoryStatusByBzStatus(inLinecomd.getInvStatus().getId(), source, new SingleColumnRowMapper<String>(String.class));
                    if (Constants.VIM_WH_SOURCE_IDS.equals(source)) {
                        inventoryStatus = inventoryStatusDao.findStatusNameByid(inLinecomd.getInvStatus().getId().toString(), new BeanPropertyRowMapper<InventoryStatus>(InventoryStatus.class));
                        if (null != inventoryStatus) {
                            if ("残次品".equals(inventoryStatus.getDescription())) {
                                asnhd.setFacility(idsServerInformation.getFacility2());
                            } else {
                                asnhd.setFacility(idsServerInformation.getFacility());
                            }
                        }
                    }
                }

                List<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine> userLines = new ArrayList<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine>();
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefineLine = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
                userDefineinfo.setUserDefine_No("1");
                userDefineinfo.setUserDefine_Value(vmiStatus == null ? "" : vmiStatus);
                userDefineLine.setUserDefine(userDefineinfo);
                userLines.add(userDefineLine);
                asndt.setUserDefines(userLines);
                asndtList.add(asndt);

                i++;
            }
            asnhd.setASNDT(asndtList);

            asnhdList.add(asnhd);
            msgInboundOrderDao.updateInboundOrder(batchNo, DefaultStatus.SENT.getValue(), msginorder.getId());
        }
        String respXml = null;
        if (asnhdList.size() > 0) {
            wmsAsn.setASNHD(asnhdList);
            respXml = (String) MarshallerUtil.buildJaxb(wmsAsn);
            // System.out.println(respXml);
        }

        return respXml;

    }

    /**
     * IDS退换货订单 修改请注意各个品牌定制内容
     */
    public OrderConfirm returnArrivalRequest(String resultXml, String source) {
        IdsServerInformation idsServerInformation = idsServerInformationDao.findServerInformationBySource(source);
        WmsRec wmsRec = (WmsRec) MarshallerUtil.buildJaxb(WmsRec.class, resultXml);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmm");
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
        InventoryStatus isForSaleFlase = null;
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForGoodSaleFlase = null;
        List<MsgRtnInboundOrder> rtnList = new ArrayList<MsgRtnInboundOrder>();
        InventoryStatus is = null;
        OperationUnit ou = null;
        Warehouse wh = null;


        OrderConfirm orderConfirm = new OrderConfirm();

        if (wh == null) {
            wh = warehouseDao.getBySource(source, source);
            if (wh == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            } else {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {source});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            }
        }

        Long companyId = null;
        if (is == null) {
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSales(companyId, false);
        }
        List<ConfirmResult> reulstList = new ArrayList<ConfirmResult>();
        if (wmsRec != null) {
            for (RECHD rechd : wmsRec.getRECHD()) {
                try {
                    if (rechd != null) {
                        if (rechd.getExternReceiptKey() == null || rechd.getExternReceiptKey().equals("")) {
                            continue;
                        }
                    }
                    MsgRtnInboundOrder msgrtnInbound = new MsgRtnInboundOrder();
                    msgrtnInbound.setStaCode(rechd.getExternReceiptKey());
                    try {
                        // LF外包仓退货来源修改
                        if (source.equals("IDS-SH-UA")) {
                            MsgInboundOrder inorder = msgInboundOrderDao.findByStaCode(msgrtnInbound.getStaCode());
                            if (inorder != null) {
                                msgrtnInbound.setSource(inorder.getSource());
                                msgrtnInbound.setSourceWh(inorder.getSourceWh());
                            } else {
                                msgrtnInbound.setSource(source);
                                msgrtnInbound.setSourceWh(source);
                            }
                        } else if (source.equals("IDS-NIKE") || source.equals("IDS-NIKE001")) {
                            idsServerInformation = idsServerInformationDao.findServerInformationByFacility(rechd.getFacility());
                            if (idsServerInformation != null) {
                                msgrtnInbound.setSource(idsServerInformation.getVmiSource());
                                msgrtnInbound.setSourceWh(idsServerInformation.getVmiSource());
                            }
                        } else {
                            msgrtnInbound.setSource(source);
                            msgrtnInbound.setSourceWh(source);
                        }
                        if (rechd.getEffectiveDate().length() > 12) {
                            msgrtnInbound.setInboundTime(sdfs.parse(rechd.getEffectiveDate()));
                        } else {
                            msgrtnInbound.setInboundTime(sf.parse(rechd.getEffectiveDate()));
                        }
                        msgrtnInbound.setCreateTime(new Date());
                        msgrtnInbound.setStatus(DefaultStatus.CREATED);

                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    rtnList.add(msgrtnInbound);
                    msgrtnInbound = rtnOrderDao.save(msgrtnInbound);
                    StockTransApplication sta = staDao.getByCode(rechd.getExternReceiptKey());
                    BiChannel c = shopDao.getByCode(sta.getOwner());
                    for (RECDT line : rechd.getRECDT()) {
                        MsgRtnInboundOrderLine rtnline = new MsgRtnInboundOrderLine();
                        if (Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(source)) {
                            SkuCommand sku = skuDao.getSkuByBarCodeAndCostomer(line.getSKU(), c.getCustomer().getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                            if (sku != null) {
                                rtnline.setSkuCode(sku.getCode());
                                rtnline.setSkuId(sku.getId());
                            }
                        } else if (Constants.VIM_WH_SOURCE_NIKE_IDS.equals(source) || Constants.VIM_WH_SOURCE_NIKE_IDS_TM.equals(source)) {
                            Sku sku = skuDao.getByExtCode1AndCustomer(line.getSKU(), c.getCustomer().getId());
                            if (sku != null) {
                                rtnline.setSkuCode(sku.getCode());
                                rtnline.setSkuId(sku.getId());
                            }
                        } else {
                            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(line.getSKU(), c.getCustomer().getId(), c.getId());
                            if (sku != null) {
                                rtnline.setSkuCode(sku.getCode());
                                rtnline.setSkuId(sku.getId());

                            }
                        }

                        rtnline.setQty(Long.valueOf(line.getQtyReceived()));
                        // 退回商品库存状态全部设置为，残次
                        String facility = rechd.getFacility();

                        if (source.equals(Constants.VIM_WH_SOURCE_AF_IDS) || source.equals(Constants.VIM_WH_SOURCE_IDS)) {
                            if (facility.equals(idsServerInformation.getFacility())) {
                                rtnline.setInvStatus(isForSaleTrue);
                            } else if (facility.equals(idsServerInformation.getFacility2())) {
                                rtnline.setInvStatus(isForSaleFlase);
                            }
                        } else {
                            if (source.equals(Constants.VIM_WH_SOURCE_NEWLOOK_IDS) || source.equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI) || source.equals(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS) || Constants.VIM_WH_SOURCE_UA_IDS.equals(source)
                            ) {
                                MsgInventoryStatus msg = msgInventoryStatusDao.findInventoryStatusByVmiStatus(msgrtnInbound.getSource(), line.getHostWHCode(), msgrtnInbound.getSource());
                                if (msg != null) {
                                    InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(msg.getWhStatus());
                                    rtnline.setInvStatus(inventoryStatus);
                                }
                            } else if (("RE").equals(line.getHostWHCode())) {
                                isForGoodSaleFlase = inventoryStatusDao.findGoodInvStatusisForSale(companyId, false, "RE");
                                rtnline.setInvStatus(isForGoodSaleFlase);
                            } else {
                                rtnline.setInvStatus(isForSaleFlase);
                            }
                        }
                        if (source.equals(Constants.VIM_WH_SOURCE_NIKE_IDS_TM) || source.equals(Constants.VIM_WH_SOURCE_NIKE_IDS) || source.equals(Constants.VIM_WH_SOURCE_CONVERSE_IDS)) {
                            rtnline.setInvStatus(isForSaleTrue);
                        }
                        rtnline.setMsgRtnInOrder(msgrtnInbound);
                        rtnOrderLineDao.save(rtnline);
                    }
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(rechd.getExternReceiptKey());
                    result.setSuccess("true");
                    reulstList.add(result);
                } catch (BusinessException ex) {
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(rechd.getExternReceiptKey());
                    result.setSuccess("false");
                    result.setReason("S05");
                    result.setDescription("系统错误");
                    reulstList.add(result);
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        orderConfirm.setBatchID(wmsRec.getBatchID());
        orderConfirm.setResult(reulstList);
        return orderConfirm;
    }

    /**
     * 发送取消订单
     */
    public String orderCancelResponse(String source, IdsServerInformation idsServerInformation) {
        WmsOrder colist = new WmsOrder();
        Long batchNo = 0l;
        List<MsgOutboundOrderCancel> orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(source);

        batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        // 查询新建取消失败的单据
        // orderlist =
        // msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(Constants.VIM_WH_SOURCE_AEO_IDS);

        List<ORDHD> ordhdList = new ArrayList<ORDHD>();
        for (MsgOutboundOrderCancel order : orderlist) {
            ORDHD ordhd = new ORDHD();
            ordhd.setInterfaceActionFlag("D");
            // ordhd.setStorerKey("ANF");// 商户编号,商户编号
            // ordhd.setFacility("BS13"); // 仓库编码
            ordhd.setStorerKey(idsServerInformation.getStorerKey());
            ordhd.setFacility(idsServerInformation.getFacility());
            ordhd.setExternOrderKey(order.getStaCode());
            ordhd.setM_Company("");// 物流
            ordhd.setNotes(order.getMsg() == null ? "" : order.getMsg());
            ordhdList.add(ordhd);
            msgOutboundOrderCancelDao.updateCancleOrderBachIdById(order.getId(), batchNo, DefaultStatus.SENT.getValue());
            msgOutboundOrderCancelDao.flush();
        }
        colist.setORDHD(ordhdList);
        colist.setBatchID(batchNo.toString());
        String respXml = null;
        if (ordhdList.size() > 0) {
            respXml = MarshallerUtil.buildJaxb(colist);

        }
        return respXml;
    }

    /**
     * 取消推送接口。实时
     */
    public String siginOrderCancelResponse(MsgOutboundOrderCancel order, IdsServerInformation idsServerInformation) {
        WmsOrder colist = new WmsOrder();
        Long batchNo = 0l;
        batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        log.info("----siginOrderCancelResponse-- batchNo:" + batchNo);
        List<ORDHD> ordhdList = new ArrayList<ORDHD>();
        ORDHD ordhd = new ORDHD();
        ordhd.setInterfaceActionFlag("D");
        // AEO
        if (Constants.VIM_WH_SOURCE_AEO_IDS.equals(order.getSource())) {
            StockTransApplication sta = staDao.getByPrimaryKey(order.getStaId());
            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_IDS);
            findAEOOwnerCode();
            if (aeoOwnerCode.get("AEOJD").equals(sta.getOwner())) {
                ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_JD_IDS);
            } else if (aeoOwnerCode.get("AEOGF").equals(sta.getOwner())) {
                ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_IDS);
            } else if (aeoOwnerCode.get("AEORTN").equals(sta.getOwner())) {
                ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_RTN_IDS);
            }
        } else {
            ordhd.setStorerKey(idsServerInformation.getStorerKey());
            ordhd.setFacility(idsServerInformation.getFacility());

        }
        ordhd.setExternOrderKey(order.getStaCode());
        ordhd.setM_Company("");// 物流
        ordhd.setNotes(order.getMsg() == null ? "" : order.getMsg());
        ordhdList.add(ordhd);
        order.setBatchId(batchNo);
        order.setUpdateTime(new Date());
        order.setStatus(MsgOutboundOrderCancelStatus.SENT);
        msgOutboundOrderCancelDao.save(order);
        colist.setORDHD(ordhdList);
        colist.setBatchID(batchNo.toString());
        String respXml = null;
        if (ordhdList.size() > 0) {
            respXml = MarshallerUtil.buildJaxb(colist);

        }
        return respXml;
    }

    /**
     * LF外包仓 退货取消接口
     */
    public String cancelReturnResponseLF(IdsServerInformation idsServerInformation, Long msgId) {
        if (idsServerInformation == null) {
            log.debug("-----IdsServerInformation is null -----------");
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
        }
        WmsAsn asn = new WmsAsn();
        Long batchNo = 0l;
        // 根据ID查询取消作业单
        MsgRaCancel cancel = msgRaCancelDao.getByPrimaryKey(msgId);
        // 生成batchNo
        batchNo = msgRaCancelDao.findRaCancelBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        log.info("------create cancel batchNo------" + batchNo + "cancelStaCode" + cancel.getStaCode());
        List<ASNHD> asList = new ArrayList<ASNHD>();
        if (cancel.getStatus() == DefaultStatus.CREATED) {
            ASNHD asnhd = new ASNHD();
            asnhd.setInterfaceActionFlag("D");

            if (Constants.VIM_WH_SOURCE_AEO_IDS.equals(cancel.getSource())) {
                StockTransApplication sta = staDao.getByCode(cancel.getStaCode());
                asnhd.setStorerKey(Constants.VIM_WH_STORER_AEO_IDS);
                findAEOOwnerCode();
                if (aeoOwnerCode.get("AEOJD").equals(sta.getOwner())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_JD_IDS);
                } else if (aeoOwnerCode.get("AEOGF").equals(sta.getOwner())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_IDS);
                } else if (aeoOwnerCode.get("AEORTN").equals(sta.getOwner())) {
                    asnhd.setFacility(Constants.VIM_WH_FACILITY_AEO_RTN_IDS);
                }
            } else {
                asnhd.setStorerKey(idsServerInformation.getStorerKey());
                asnhd.setFacility(idsServerInformation.getFacility());
            }

            asnhd.setWarehouseReference(cancel.getSlipCode() == null ? "" : cancel.getSlipCode());
            asnhd.setExternReceiptKey(cancel.getStaCode());
            asList.add(asnhd);
            msgRaCancelDao.updateStatusById(msgId, DefaultStatus.SENT.getValue());
            msgRaCancelDao.flush();
        }
        asn.setASNHD(asList);
        asn.setBatchID(batchNo.toString());
        String respXml = null;
        if (asList.size() > 0) {
            respXml = MarshallerUtil.buildJaxb(asn);
        }
        return respXml;
    }


    /**
     * order cancle 取消反馈
     * 
     * @param resultXml
     */
    public void orderCancelRespone(String resultXml) {
        OrderConfirm orderConfirm = (OrderConfirm) MarshallerUtil.buildJaxb(OrderConfirm.class, resultXml);
        if (orderConfirm != null) {
            List<ConfirmResult> resultList = orderConfirm.getResult();
            for (ConfirmResult result : resultList) {
                List<MsgOutboundOrderCancel> list = msgOutboundOrderCancelDao.findByStaCode(result.getExternDocKey());
                MsgOutboundOrderCancel cancleOrder = null;
                if (list != null && list.size() > 0) {
                    cancleOrder = list.get(0);
                } else {
                    continue;
                }
                if (cancleOrder.getStatus().equals(MsgOutboundOrderCancelStatus.FINISHED)) {
                    continue;
                }
                // if (bhSyncRequest.getBatchId() != null && !"".equals(bhSyncRequest.getBatchId()))
                // {
                // cancleOrder.setBatchId(Long.parseLong(bhSyncRequest.getBatchId()));
                // }
                cancleOrder.setStatus(MsgOutboundOrderCancelStatus.SENT);
                if (StringUtils.hasText(result.getSuccess())) {
                    if (("true").equals(result.getSuccess())) {
                        cancleOrder.setIsCanceled(true);
                    } else if (("false").equals(result.getSuccess())) {
                        cancleOrder.setIsCanceled(false);
                    }
                }

            }
        }

    }

    /**
     * 出入库同步接口
     * 
     * @param resultXml
     */
    public OrderConfirm vmiInventory(String resultXml, String source) {

        OrderConfirm orderConfirm = new OrderConfirm();
        WmsItr wmsItr = (WmsItr) MarshallerUtil.buildJaxb(WmsItr.class, resultXml);
        if (wmsItr != null) {
            List<ConfirmResult> resultList = new ArrayList<ConfirmResult>();
            List<ITRHD> itrhdsList = wmsItr.getITRHD();

            for (ITRHD itrhd : itrhdsList) {
                try {
                    IdsInventorySynchronous invsyn = new IdsInventorySynchronous();
                    invsyn.setBatchID(wmsItr.getBatchID());
                    invsyn.setiTRType(itrhd.getITRType());
                    invsyn.setwMSDocKey(itrhd.getWMSDocKey());
                    invsyn.setStorerKey(itrhd.getStorerKey());
                    invsyn.setFacility(itrhd.getFacility());
                    invsyn.setCustomerRefNo(itrhd.getCustomerRefNo());
                    invsyn.setOtherRefNo(itrhd.getOtherRefNo());
                    invsyn.setStatus(DefaultStatus.CREATED);
                    invsyn.setCreateDate(new Date());
                    invsyn.setEffectiveDate(itrhd.getEffectiveDate());
                    invsyn.setReasonCode(itrhd.getReasonCode());
                    invsyn.setRemark(itrhd.getRemark());
                    invsyn.setErrorCount(0);

                    // 【UATRFB2EN】 ReasonCode 值 Retail to ECOM NBA，ECOM NBA加库存，ECOM NBA to
                    // Retail，ECOM NBA减库存 【UATRFE2BN】 || "UEN".equals(itrhd.getReasonCode()) ||
                    // "UAENBA".equals(itrhd.getReasonCode()
                    if (null != itrhd.getReasonCode() && (itrhd.getReasonCode().equals("UATRFB2EN") || itrhd.getReasonCode().equals("UATRFE2BN") || "UEN".equals(itrhd.getReasonCode()) || "UAENBA".equals(itrhd.getReasonCode()))) {
                        invsyn.setSource(Constants.VIM_WH_SOURCE_NBAUA_IDS);
                    } else {
                        invsyn.setSource(source);
                    }

                    if (invsyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOK_IDS) || invsyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS)) {
                        if ("JD".equals(itrhd.getReasonCode())) {
                            invsyn.setSource(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
                        } else if ("TM".equals(itrhd.getReasonCode())) {
                            invsyn.setSource(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
                        }
                    }

                    if (invsyn.getSource().equals(Constants.VIM_WH_SOURCE_NBAUA_IDS) || invsyn.getSource().equals(Constants.VIM_WH_SOURCE_UA_IDS) || invsyn.getSource().equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI)) {
                        invsyn.setiTRType("ADJ");
                    } else {
                        invsyn.setiTRType(itrhd.getITRType());
                    }



                    invsyn = idsInvSynDao.save(invsyn);

                    List<ITRDT> itrdList = itrhd.getITRDT();
                    if (itrdList != null) {
                        for (ITRDT itrdt : itrdList) {
                            IdsInventorySynchronousLine line = new IdsInventorySynchronousLine();
                            line.setwMSDocLineNo(itrdt.getWMSDocLineNo());
                            line.setSku(itrdt.getSKU());
                            line.setQty(itrdt.getQty());
                            line.setHostWHCode(itrdt.getHostWHCode());
                            line.setIdsInventorySynchronous(invsyn);
                            IdsInvSynLineDao.save(line);
                        }
                    }
                    idsInvSynDao.flush();
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(itrhd.getWMSDocKey());
                    result.setSuccess("true");

                    resultList.add(result);

                } catch (Exception e) {
                    ConfirmResult result = new ConfirmResult();
                    result.setExternDocKey(itrhd.getWMSDocKey());
                    result.setSuccess("false");
                    resultList.add(result);
                    log.error("", e);
                }
            }
            orderConfirm.setBatchID(wmsItr.getBatchID());
            orderConfirm.setResult(resultList);
        }
        return orderConfirm;

    }

    /**
     * IDS 收货
     */
    public StockTransApplication vmiInventorySynchronous(IdsInventorySynchronous idsInvSyn) {
        log.debug("=============== sta {} start success ================", new Object[] {idsInvSyn.getSource()});
        OperationUnit ou = null;
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(idsInvSyn.getSource() + "_" + idsInvSyn.getwMSDocKey());

        sta.setLastModifyTime(new Date());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        staDao.save(sta);
        staDao.flush();
        String innerShopCode = null;
        BiChannel shop = null;
        Warehouse wh = null;
        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;
        InventoryStatus isForGoodSaleFlase = null;
        Long skuQty = 0l;
        if (!StringUtils.hasText(idsInvSyn.getiTRType()) || !("REC").equals(idsInvSyn.getiTRType())) {
            log.debug("=============== VMI ADJUSTMENT EXECUTE TYPE ERROR================", new Object[] {idsInvSyn.getSource()});
            throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
        }

        String vmiCode = idsInvSyn.getSource();
        if (vmiCode == null || vmiCode.equals("")) {
            log.debug("=========SHOP STORERKEY{} NOT FOUNT===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        if (shop == null) {
            if (Constants.VIM_WH_SOURCE_AEO_IDS.equals(idsInvSyn.getSource())) {
                findAEOOwnerCode();
                if (Constants.VIM_WH_STORER_AEO_IDS.equals(idsInvSyn.getStorerKey())) {
                    shop = shopDao.getByCode(aeoOwnerCode.get("AEOGF"));
                } else if (Constants.VIM_WH_STORER_AEO_JD_IDS.equals(idsInvSyn.getStorerKey())) {
                    shop = shopDao.getByCode(aeoOwnerCode.get("AEOJD"));
                } else if (Constants.VIM_WH_STORER_AEO_RTN_IDS.equals(idsInvSyn.getStorerKey())) {
                    shop = shopDao.getByCode(aeoOwnerCode.get("AEORTN"));
                }
            } else {

                shop = shopDao.getByvmiWHSource(vmiCode);
            }
            if (shop == null) {
                log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {vmiCode});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
        }
        wareHouseManagerExe.validateBiChannelSupport(null, shop.getCode());
        if (wh == null) {
            wh = whManager.getWareHouseByVmiSource(idsInvSyn.getSource());
            if (wh == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getSource()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            } else {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                if (ou == null) {
                    log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getSource()});
                    throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                }
            }
        }
        Long companyId = null;
        if (isForSaleFlase == null) {
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
        }

        List<IdsInventorySynchronousLine> idsInvSynList = IdsInvSynLineDao.findInvSynLineById(idsInvSyn.getId(), new BeanPropertyRowMapper<IdsInventorySynchronousLine>(IdsInventorySynchronousLine.class));
        boolean isNoSkuError = false;
        for (IdsInventorySynchronousLine invSyn : idsInvSynList) {
            String skuCode = invSyn.getSku();
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(skuCode, shop.getCustomer().getId(), shop.getId());
            if (idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS)) {
                if (sku == null) {
                    baseinfoManager.sendMsgToOmsCreateSku(skuCode, "NewLook");
                    isNoSkuError = true;
                    continue;
                }
            } else {
                if (sku == null) {
                    baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiWHSource1());
                    isNoSkuError = true;
                    continue;
                }
            }
            log.debug("===============SKU {} CREATE SUCCESS ================", new Object[] {invSyn.getSku()});

            StaLine staLine = new StaLine();
            int qty = Math.abs(invSyn.getQty());
            staLine.setQuantity(Long.valueOf(qty));
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            innerShopCode = shop.getCode();

            staLine.setOwner(innerShopCode);

            if (idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOK_IDS) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_UA_IDS) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS)
                    || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_AEO_IDS)) {
                MsgInventoryStatus msg = msgInventoryStatusDao.findInventoryStatusByVmiStatus(idsInvSyn.getSource(), invSyn.getHostWHCode(), idsInvSyn.getSource());
                if (msg != null) {
                    InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(msg.getWhStatus());
                    staLine.setInvStatus(inventoryStatus);
                }
            } else {
                if (("BS13").equals(idsInvSyn.getFacility()) || ("ATS").equals(invSyn.getHostWHCode()) || ("AVAL").equals(invSyn.getHostWHCode()) || ("Normal").equals(invSyn.getHostWHCode()) || ("NORMAL").equals(invSyn.getHostWHCode())) {
                    staLine.setInvStatus(isForSaleTrue);
                } else if (("RE").equals(idsInvSyn.getFacility())) {
                    isForGoodSaleFlase = inventoryStatusDao.findGoodInvStatusisForSale(companyId, false, "RE");
                    staLine.setInvStatus(isForGoodSaleFlase);
                } else {
                    staLine.setInvStatus(isForSaleFlase);
                }
            }
            staLineDao.save(staLine);
            log.debug("===============staLine sku:{}({}) ================", new Object[] {sku.getBarCode(), qty});
            skuQty += staLine.getQuantity();
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(skuQty);
        log.debug("===============sta {} create success ================", new Object[] {sta.getCode()});
        if (StringUtils.hasText(innerShopCode) && ou != null && isForSaleFlase != null) {
            sta.setOwner(innerShopCode);
            sta.setMainWarehouse(ou);
            sta.setMainStatus(isForSaleTrue);
            staDao.save(sta);
            staDao.flush();
            staDao.updateSkuQtyById(sta.getId());
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
        idsInvSynDao.updateMsgStaById(DefaultStatus.FINISHED.getValue(), idsInvSyn.getId(), sta.getCode());
        return sta;
    }

    public InventoryCheck vmiInvAdjustByWh(IdsInventorySynchronous idsInvSyn) {
        boolean isCreateSuccess = true;
        InventoryCheck ic = null;
        if (idsInvSyn != null) {
            log.debug("=============== INVENTORYCHECK {} start success ================", new Object[] {idsInvSyn.getSource()});
            IdsInventorySynchronous invsyn = idsInvSynDao.getByPrimaryKey(idsInvSyn.getId());
            OperationUnit ou = null;
            BiChannel shop = null;
            Warehouse wh = null;

            InventoryStatus isForSaleTrue = null;
            InventoryStatus isForSaleFlase = null;
            InventoryStatus isForGoodSaleFlase = null;

            if (invsyn != null) {
                ic = new InventoryCheck();
                try {
                    ic.setCreateTime(new Date());
                    ic.setStatus(InventoryCheckStatus.CREATED);
                    ic.setSlipCode(idsInvSyn.getSource() + "_" + idsInvSyn.getwMSDocKey());
                    ic.setType(InventoryCheckType.VMI);

                    if (Constants.VIM_WH_SOURCE_IDS.equals(invsyn.getSource())) {
                        if (("TRF").equals(idsInvSyn.getiTRType())) {
                            ic.setInvType(2);
                        } else if (("ADJ").equals(idsInvSyn.getiTRType())) {
                            ic.setInvType(1);
                        }
                    }

                    ic.setCode(sequenceManager.getCode(InventoryCheck.class.getName(), ic));
                    ic.setRemork(invsyn.getSource() + " adjustment");
                    invDao.save(ic);

                    if (shop == null) {

                        if (Constants.VIM_WH_SOURCE_IDS.equals(invsyn.getSource())) {
                            shop = shopDao.getByvmiWHSource1(invsyn.getReasonCode());
                        } else if (Constants.VIM_WH_SOURCE_AEO_IDS.equals(invsyn.getSource())) {
                            findAEOOwnerCode();
                            if (Constants.VIM_WH_STORER_AEO_IDS.equals(invsyn.getStorerKey())) {
                                shop = shopDao.getByCode(aeoOwnerCode.get("AEOGF"));
                            } else if (Constants.VIM_WH_STORER_AEO_JD_IDS.equals(invsyn.getStorerKey())) {
                                shop = shopDao.getByCode(aeoOwnerCode.get("AEOJD"));
                            } else if (Constants.VIM_WH_STORER_AEO_RTN_IDS.equals(invsyn.getStorerKey())) {
                                shop = shopDao.getByCode(aeoOwnerCode.get("AEORTN"));
                            }
                        } else {
                            shop = shopDao.getByvmiWHSource(invsyn.getSource());
                        }
                    }
                    if (shop == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getStorerKey()});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    if (wh == null) {
                        wh = whManager.getWareHouseByVmiSource(idsInvSyn.getSource());
                    }
                    if (wh == null) {
                        log.debug("=========WAREHOUSE UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getSource()});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    Long ouId = wh.getOu().getId();
                    if (ouId == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getStorerKey()});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    // OperationUnit ouShop = ouDao.getByPrimaryKey(shop.getOu().getId());
                    // if (ouShop == null) {
                    // log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[]
                    // {idsInvSyn.getStorerKey()});
                    // throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    // }
                    ou = ouDao.getByPrimaryKey(ouId);
                    if (ou == null) {
                        log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getStorerKey()});
                        throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
                    }
                    Long companyId = null;
                    if (isForSaleFlase == null) {
                        if (ou.getParentUnit() != null) {
                            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                            if (ou1 != null) {
                                companyId = ou1.getParentUnit().getId();
                            }
                        }
                        isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
                        isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
                    }

                    List<IdsInventorySynchronousLine> idsInvSynList = IdsInvSynLineDao.findInvSynLineById(idsInvSyn.getId(), new BeanPropertyRowMapper<IdsInventorySynchronousLine>(IdsInventorySynchronousLine.class));
                    boolean isExist = false;
                    for (IdsInventorySynchronousLine idsinvLine : idsInvSynList) {
                        Sku sku = null;
                        if (null != wh.getWhSourceSkuType() && Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(invsyn.getSource())) {
                            WarehouseSourceSkuType type = WarehouseSourceSkuType.valueOf(wh.getWhSourceSkuType().getValue());
                            if ("BAR_CODE".equals(type.toString())) {
                                sku = skuDao.getByBarCodeAndCustomer(idsinvLine.getSku(), shop.getCustomer().getId());
                            } else if ("CODE".equals(type.toString())) {
                                sku = skuDao.getByCodeAndCustomer(idsinvLine.getSku(), shop.getCustomer().getId());
                            } else if ("EXT_CODE1".equals(type.toString())) {
                                sku = skuDao.getByExtCode1AndCustomer(idsinvLine.getSku(), shop.getCustomer().getId());
                            } else {
                                sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(idsinvLine.getSku(), shop.getCustomer().getId(), shop.getId());
                            }
                        } else {
                            sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(idsinvLine.getSku(), shop.getCustomer().getId(), shop.getId());
                        }
                        // Sku sku = skuDao.getByExtCode2AndCustomer(idsinvLine.getSku(),
                        // shop.getCustomer().getId());
                        if (Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(invsyn.getSource())) {
                            if (sku == null) {
                                // 如果sku在系统中没有，就生成，生成失败返回false
                                baseinfoManager.sendMsgToOmsCreateSku(idsinvLine.getSku(), "NewLook");
                                isExist = true;
                                continue;
                            }
                        } else {
                            if (sku == null) {
                                // 如果sku在系统中没有，就生成，生成失败返回false
                                baseinfoManager.sendMsgToOmsCreateSku(idsinvLine.getSku(), shop.getVmiCode());
                                isExist = true;
                                continue;
                            }
                        }

                        InventoryCheckDifTotalLine line = new InventoryCheckDifTotalLine();
                        line.setInventoryCheck(ic);
                        line.setSku(sku);
                        line.setQuantity(new Long(idsinvLine.getQty()));

                        if (Constants.VIM_WH_SOURCE_IDS.equals(invsyn.getSource())) {
                            if (("LVT").equals(idsInvSyn.getToFacility()) || ("LVT").equals(idsInvSyn.getFacility())) {
                                line.setStatus(isForSaleTrue);
                            } else if (("LVTD").equals(idsInvSyn.getFacility()) || ("LVTD").equals(idsInvSyn.getToFacility())) {
                                line.setStatus(isForSaleFlase);
                            } else {
                                line.setStatus(isForSaleTrue);
                            }
                        } else {
                            if (idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_NEWLOOK_IDS) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_UA_IDS) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_GODIVA_HAVI)
                                    || Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(idsInvSyn.getSource()) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_AEO_IDS) || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_NBAUA_IDS)
                                    || idsInvSyn.getSource().equals(Constants.VIM_WH_SOURCE_IDS_VS)) {
                                MsgInventoryStatus msg = msgInventoryStatusDao.findInventoryStatusByVmiStatus(idsInvSyn.getSource(), idsinvLine.getHostWHCode(), idsInvSyn.getSource());
                                if (msg != null) {
                                    InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(msg.getWhStatus());
                                    line.setStatus(inventoryStatus);
                                }
                            } else {
                                if (("BS13").equals(idsInvSyn.getFacility()) || ("ATS").equals(idsinvLine.getHostWHCode()) || ("AVAL").equals(idsinvLine.getHostWHCode()) || ("Normal").equals(idsinvLine.getHostWHCode())
                                        || ("NORMAL").equals(idsinvLine.getHostWHCode()) || ("UN").equals(idsinvLine.getHostWHCode())) {
                                    line.setStatus(isForSaleTrue);
                                } else if (("RE").equals(idsinvLine.getHostWHCode())) {
                                    isForGoodSaleFlase = inventoryStatusDao.findGoodInvStatusisForSale(companyId, false, "RE");
                                    line.setStatus(isForGoodSaleFlase);
                                } else {
                                    line.setStatus(isForSaleFlase);
                                }
                            }

                        }


                        icLineDao.save(line);
                        ic.setShop(shop);
                        ic.setOu(ou);
                        invDao.save(ic);
                    }
                    if (isExist) {
                        throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                    }
                } catch (BusinessException be) {
                    isCreateSuccess = false;
                    idsInvSynDao.updateMsgStatusById(DefaultStatus.ERROR.getValue(), "", idsInvSyn.getId(), "");
                    log.error("vmiInvAdjustByWh error, be :{} , id : {}", be.getErrorCode(), idsInvSyn.getId());
                } catch (Exception e) {
                    isCreateSuccess = false;
                    idsInvSynDao.updateMsgStatusById(DefaultStatus.ERROR.getValue(), "", idsInvSyn.getId(), "");
                    log.error("", e);
                }

                if (isCreateSuccess && ou != null && shop != null) {
                    // 设置组织
                    ic.setOu(wh.getOu());
                    ic.setShop(shop);
                    invDao.flush();
                    idsInvSynDao.updateMsgStaById(DefaultStatus.FINISHED.getValue(), idsInvSyn.getId(), ic.getCode());
                    log.debug("===============InventoryCheck {} create success ================", new Object[] {ic.getCode()});
                } else {
                    log.debug("===============InventoryCheck {} create error ================", new Object[] {ic.getCode()});
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return ic;
    }

    public void idsInvSynInfoSave(String requestXML) {
        MsgOutboundOrder order = new MsgOutboundOrder();
        order.setAddress(requestXML);
        order.setSource("IDS_AF");
        msgOutboundOrderDao.save(order);
    }

    public void receiveAEOSkuMasterByMq(String message) {

        log.info("=========AEOSKUMASTER START===========");
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);

            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            cn.baozun.bh.connector.aeo.model.Skus skus = (cn.baozun.bh.connector.aeo.model.Skus) JSONUtil.jsonToBean(messageContent, cn.baozun.bh.connector.aeo.model.Skus.class);
            List<cn.baozun.bh.connector.aeo.model.Skus.Sku> skuList = skus.getSkus();
            for (cn.baozun.bh.connector.aeo.model.Skus.Sku sku : skuList) {
                AeoSkuMasterInfo skuinfo = new AeoSkuMasterInfo();
                skuinfo.setBarCode(sku.getBarCode());
                skuinfo.setBrand(sku.getBrand());
                skuinfo.setColor(sku.getColor());
                skuinfo.setColorSize(sku.getColorSize());
                skuinfo.setCreateDate(new Date());
                skuinfo.setEnName(sku.getEnName());
                skuinfo.setListPrice(new BigDecimal(sku.getListPrice()).divide(new BigDecimal(10000)));
                skuinfo.setAeoSize(sku.getSize());
                skuinfo.setSkuClass(sku.getSkuClass());
                skuinfo.setStyle(sku.getStyle());
                skuinfo.setUpc(sku.getUpc());
                skuinfo.setVendor(sku.getVendor());
                aeoSkuMasterInfoDao.save(skuinfo);
                aeoSkuMasterInfoDao.flush();
            }

        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("receiveAEOSkuMasterByMq:" + message, ex);
            }
        }
        log.info("=========AEOSKUMASTER END===========");
    }

    public String findAEOurl() {
        ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey("AEO", "AEOURL");
        return option.getOptionValue();
    }

    public StockTransApplication vmiInvUpdateStatus(IdsInventorySynchronous idsInvSyn) {
        StockTransApplication sta = null;
        TransactionType tTypeOut = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT);
        if (tTypeOut == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT_NOT_FOUND);
        }
        TransactionType tTypeIn = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (tTypeIn == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        /*
         * CompanyShop shop = shopDao.findCompanyShopBySource(idsInvSyn.getSource());
         */

        BiChannel shop = null;
        Warehouse wh = null;

        InventoryStatus isForSaleTrue = null;
        InventoryStatus isForSaleFlase = null;
        OperationUnit ou = null;
        WarehouseLocation location = null;
        String innerShopCode1 = null;
        String vmiCode = idsInvSyn.getSource();
        if (vmiCode == null || vmiCode.equals("")) {
            log.debug("=========SHOP STORERKEY{} NOT FOUNT===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        if (shop == null) {
            shop = shopDao.getByVmiCode(vmiCode);
            innerShopCode1 = shop.getCode();
        }

        if (wh == null) {
            wh = whManager.getWareHouseByVmiSource(idsInvSyn.getSource());
            if (wh == null) {
                log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {idsInvSyn.getSource()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            } else {
                Long ouId = wh.getOu().getId();
                ou = ouDao.getByPrimaryKey(ouId);
                location = locDao.findOneWarehouseLocationByOuid(ou.getId());
            }
        }
        if (isForSaleFlase == null) {
            Long companyId = null;
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            isForSaleTrue = inventoryStatusDao.findInvStatusisForSale(companyId, true);
            isForSaleFlase = inventoryStatusDao.findInvStatusisForSale(companyId, false);
        }

        try {

            List<IdsInventorySynchronousLine> outInv = IdsInvSynLineDao.findOutInvSynLineById(idsInvSyn.getId(), new BeanPropertyRowMapper<IdsInventorySynchronousLine>(IdsInventorySynchronousLine.class));
            List<IdsInventorySynchronousLine> inInv = IdsInvSynLineDao.findinInvSynLineById(idsInvSyn.getId(), new BeanPropertyRowMapper<IdsInventorySynchronousLine>(IdsInventorySynchronousLine.class));
            List<StvLine> list = new ArrayList<StvLine>();
            for (IdsInventorySynchronousLine cmd : outInv) {
                boolean isSame = false;
                for (StvLine sl : list) {
                    InventoryStatus inventoryStatus = null;
                    if (("ATS").equals(cmd.getHostWHCode()) || ("AVAL").equals(cmd.getHostWHCode())) {
                        inventoryStatus = isForSaleTrue;
                    } else {
                        inventoryStatus = isForSaleFlase;
                    }
                    if (sl.getSku().getId().equals(cmd.getSku()) && sl.getInvStatus().getId().equals(inventoryStatus.getId())) {
                        sl.setQuantity(sl.getQuantity() + Math.abs(cmd.getQty()));
                        isSame = true;
                    }
                }
                if (!isSame) {
                    Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(cmd.getSku(), shop.getCustomer().getId(), shop.getId());

                    StvLine l = new StvLine();
                    l.setSku(sku);
                    l.setLocation(location);
                    l.setOwner(innerShopCode1);
                    if (("ATS").equals(cmd.getHostWHCode()) || ("AVAL").equals(cmd.getHostWHCode())) {
                        l.setInvStatus(isForSaleTrue);
                    } else {
                        l.setInvStatus(isForSaleFlase);
                    }
                    l.setQuantity(Long.valueOf(Math.abs(cmd.getQty())));
                    list.add(l);

                }
            }

            sta = wareHouseManager.createInvStatusChangeSta(false, idsInvSyn.getwMSDocKey(), 0l, ou.getId(), list);
            List<StvLineCommand> stvlineList = new ArrayList<StvLineCommand>();
            List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(sta.getId(), TransactionDirection.OUTBOUND.getValue(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            Map<String, List<StvLineCommand>> lMap = new HashMap<String, List<StvLineCommand>>();
            for (StvLineCommand cmd : outStvLine) {
                // String key = cmd.getSkuId()+cmd.getLocationCode();
                String key = cmd.getSkuId() + "";
                List<StvLineCommand> cl = lMap.get(key);
                if (cl == null) {
                    cl = new ArrayList<StvLineCommand>();
                }
                cl.add(cmd);
                log.debug("put key : {}", key);
                lMap.put(key, cl);
            }
            for (IdsInventorySynchronousLine cmd : inInv) {
                Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(cmd.getSku(), shop.getCustomer().getId(), shop.getId());
                log.debug("key is : {}", sku.getId());
                List<StvLineCommand> cl = lMap.get(sku.getId() + "");
                long qty = cmd.getQty();
                for (int i = 0; i < cl.size(); i++) {
                    StvLineCommand mcmd = cl.get(i);
                    if (qty == 0L) {
                        break;
                    }
                    if (mcmd.getQuantity() >= qty) {
                        StvLineCommand l = new StvLineCommand();
                        l.setLocation(location);
                        l.setLocationId(location.getId());
                        l.setSku(sku);
                        l.setSkuId(sku.getId());
                        if (("ATS").equals(cmd.getHostWHCode()) || ("AVAL").equals(cmd.getHostWHCode())) {
                            l.setInvStatus(isForSaleTrue);
                            l.setIntInvstatus(isForSaleTrue.getId());
                        } else {
                            l.setInvStatus(isForSaleFlase);
                            l.setIntInvstatus(isForSaleFlase.getId());
                        }
                        l.setOwner(mcmd.getOwner());
                        l.setQuantity(qty);

                        l.setSkuCost(mcmd.getSkuCost());
                        l.setBatchCode(mcmd.getBatchCode());
                        StaLine stal = new StaLine();
                        stal.setId(mcmd.getStalineId());
                        l.setStaLine(stal);
                        stvlineList.add(l);
                        if (mcmd.getQuantity().equals(qty)) {
                            cl.remove(i--);
                        } else {
                            mcmd.setQuantity(mcmd.getQuantity() - qty);
                        }
                        break;
                    } else if (mcmd.getQuantity().longValue() < qty) {
                        StvLineCommand l = new StvLineCommand();
                        l.setLocation(location);
                        l.setLocationId(location.getId());
                        l.setSku(sku);
                        l.setSkuId(sku.getId());

                        if (("ATS").equals(cmd.getHostWHCode()) || ("AVAL").equals(cmd.getHostWHCode())) {
                            l.setInvStatus(isForSaleTrue);
                            l.setIntInvstatus(isForSaleTrue.getId());
                        } else {
                            l.setInvStatus(isForSaleFlase);
                            l.setIntInvstatus(isForSaleFlase.getId());
                        }

                        l.setOwner(mcmd.getOwner());
                        l.setQuantity(mcmd.getQuantity().longValue());
                        l.setSkuCost(mcmd.getSkuCost());
                        l.setBatchCode(mcmd.getBatchCode());
                        StaLine stal = new StaLine();
                        stal.setId(mcmd.getStalineId());
                        l.setStaLine(stal);
                        stvlineList.add(l);
                        qty = qty - mcmd.getQuantity();
                        cl.remove(i--);
                    }
                }
            }
            noExecuteInvStatusChangeForImpory(true, sta.getId(), stvlineList);

            // 调用OMS接口
            List<StockTransVoucher> stvLists = stvLineDao.findStvByStaId(sta.getId());
            OperationBill ob = new OperationBill();
            ob.setCode(sta.getCode());
            ob.setType(sta.getType().getValue());
            ob.setMemo(sta.getMemo());
            ob.setWhCode(sta.getMainWarehouse().getCode());
            for (StockTransVoucher st : stvLists) {
                List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
                String innerShopCode = staLineDao.findByInnerShopCode(sta.getId(), new SingleColumnRowMapper<String>(String.class));
                BiChannel companyShop = channelDao.getByCode(innerShopCode);
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
                        operationBillLine.setInvStatusCode(lists.get(i).getInvStatus().getName());
                        operationBillLine.setInvBatchCode(lists.get(i).getBatchCode());
                        operationBillLine.setWhCode(lists.get(i).getWarehouse().getCode());
                        // 店铺切换 接口调整-调整渠道编码
                        operationBillLine.setShopCode(lists.get(i).getOwner());
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
                        operationBillLine.setShopCode(lists.get(i).getOwner());
                        // operationBillLine.setShopOuId(companyShop.getOu().getId());
                        billLines.add(operationBillLine);
                    }
                    ob.setOutboundLines(billLines);
                }
            }
            try {
                log.debug("Call oms inventory status change confirm interface------------------->BEGIN");
                BaseResult baseResult = rmi4Wms.operationBillCreate(ob);
                if (baseResult.getStatus() == 0) {
                    throw new BusinessException(baseResult.getMsg());
                }
                log.debug("Call oms inventory status change confirm interface------------------->END");
                String slipCode = baseResult.getSlipCode();
                sta.setRefSlipCode(slipCode);
                // KJL添加店铺
                // CompanyShop shop = shopDao.findCompanyShopBySource(idsInvSyn.getSource());
                sta.setOwner(shop.getCode());
                staDao.save(sta);
                staDao.flush();
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
            }

        } catch (FileNotFoundException e) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_FILE_READER_ERROR);
        } catch (Exception e) {
            log.error("", e);
        }
        return sta;
    }


    private void noExecuteInvStatusChangeForImpory(boolean isExcel, Long staId, List<StvLineCommand> stvlineList) throws Exception {
        if (stvlineList == null || stvlineList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANIST_INNER_LINE_EMPTY);
        }
        TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN);
        if (type == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN_NOT_FOUND);
        }
        User user = null;
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
        stv.setStatus(StockTransVoucherStatus.CREATED);
        stv.setLastModifyTime(new Date());
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

    public BaozunOrderRequest createAeoOrderRequestStr(long batchNo, List<MsgOutboundOrder> orderList, WarehouseSourceSkuType skuType, IdsServerInformation idsServerInformation) {
        BaozunOrderRequest wmsorder = null;
        if (orderList.size() > 0) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            List<OrderHeader> ordhdList = new ArrayList<OrderHeader>();
            wmsorder = new BaozunOrderRequest();
            Map<String, BiChannel> channelMap = new HashMap<String, BiChannel>();
            String errorSta = "";
            // Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            for (MsgOutboundOrder orderinfo : orderList) {
                try {
                    StockTransApplication sta = staDao.findStaByCode(orderinfo.getStaCode());
                    errorSta = orderinfo.getStaCode();
                    BiChannel ch = channelMap.get(sta.getOwner());
                    if (ch == null) {
                        ch = biChannelDao.getByCode(sta.getOwner());
                        channelMap.put(sta.getOwner(), ch);
                    }
                    // 判断发送集合中是否有刚刚取消订单
                    if (sta != null && !sta.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                        OrderHeader ordhd = new OrderHeader();
                        ordhd.setInterfaceActionFlag("A");
                        // ordhd.setStorerKey("ANF");// 商户编号, 由利丰指定
                        // ordhd.setFacility("BS13");

                        findAEOOwnerCode();
                        if (aeoOwnerCode.get("AEOJD").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_JD_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_JD_IDS);
                        } else if (aeoOwnerCode.get("AEOGF").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_IDS);
                        } else if (aeoOwnerCode.get("AEORTN").equals(sta.getOwner())) {
                            ordhd.setStorerKey(Constants.VIM_WH_STORER_AEO_RTN_IDS);
                            ordhd.setFacility(Constants.VIM_WH_FACILITY_AEO_RTN_IDS);
                        } else {
                            ordhd.setStorerKey(idsServerInformation.getStorerKey());
                            ordhd.setFacility(idsServerInformation.getFacility());
                        }

                        ordhd.setExternOrderKey(orderinfo.getStaCode());
                        if (orderinfo.getStaType() == 42) {
                            // ordhd.setM_Company(sta.getSlipCode1());
                            if (StringUtils.hasText(sta.getSlipCode2())) {
                                if (sta.getSlipCode2().indexOf("EAS") != -1) {
                                    ordhd.setM_Company(sta.getSlipCode1());
                                } else {
                                    ordhd.setM_Company(sta.getSlipCode2());
                                }
                            } else {
                                ordhd.setM_Company(sta.getSlipCode1());
                            }

                        } else {
                            ordhd.setM_Company(sta.getSlipCode2());
                        }
                        StaDeliveryInfo staDelivery = sta.getStaDeliveryInfo();
                        if (staDelivery != null) {
                            BigDecimal labelPrice = channelInsuranceManager.getInsurance(sta.getOwner(), sta.getStaDeliveryInfo().getInsuranceAmount());
                            if (labelPrice == null) {
                                ordhd.setLabelPrice("");
                            } else {
                                ordhd.setLabelPrice(labelPrice.toString());
                            }
                        } else {
                            ordhd.setLabelPrice("");
                        }

                        ordhd.setSalesman("");// 分销商 TRANS_TIME_TYPE
                        ordhd.setDeliveryPlace(orderinfo.getSfCityCode() == null ? "" : orderinfo.getSfCityCode());// 快递目的地代码
                        ordhd.setPriority("1");// 零售

                        if (orderinfo.getIsCodOrder() != null && orderinfo.getIsCodOrder()) {
                            ordhd.setType("COD");
                        } else {
                            ordhd.setType("NORMAL");
                        }
                        if (orderinfo.getTransferFee() == null) {
                            orderinfo.setTransferFee(new BigDecimal(0));
                        }
                        if (orderinfo.getTotalActual() == null) {
                            orderinfo.setTotalActual(new BigDecimal(0));
                        }
                        ordhd.setInvoiceAmount(orderinfo.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP).add(orderinfo.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP)));

                        List<StaInvoice> staInvoices = staInvoiceDao.getBySta(sta.getId());

                        boolean isNeedInvoice = staInvoices.size() == 0 ? false : true;

                        ordhd.setPrintFlag(isNeedInvoice ? "1" : "0");// 是否需要发票
                        ordhd.setIntermodalVehicle("EXPRESS");
                        ordhd.setShipperKey(orderinfo.getLpCode() == null ? "" : orderinfo.getLpCode()); // 承运商代码
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.Consignee consignee = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.Consignee();
                        consignee.setC_Contact1(orderinfo.getReceiver().replaceAll("&", " "));
                        consignee.setC_Zip(orderinfo.getZipcode());
                        consignee.setC_Phone1(orderinfo.getMobile() == null ? "" : orderinfo.getMobile());
                        consignee.setC_Phone2(orderinfo.getTelePhone() == null ? "" : orderinfo.getTelePhone());
                        consignee.setC_State(orderinfo.getProvince());
                        consignee.setC_City(orderinfo.getCity());
                        consignee.setC_Address1(orderinfo.getDistrict());
                        consignee.setC_Address2(orderinfo.getAddress().replaceAll("&", " "));
                        ordhd.setConsignee(consignee);
                        /*
                         * if (null != idsServerInformation.getSource() &&
                         * Constants.VIM_WH_SOURCE_GODIVA_HAVI
                         * .equals(idsServerInformation.getSource())) { ordhd.setNotes(null); } else
                         * { ordhd.setNotes(orderinfo.getRemark()); }
                         */
                        ordhd.setOrderDate(sf.format(orderinfo.getCreateTime()));
                        List<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine> userDefines = new ArrayList<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine>();
                        // 运费
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userdefind1 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo info = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                        info.setUserDefine_No("1");
                        info.setUserDefine_Value(orderinfo.getTransferFee().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        userdefind1.setUserDefine(info);
                        userDefines.add(userdefind1);

                        // 其他费用(换购费/服务费)
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo info2 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userdefind2 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                        info2.setUserDefine_No("2");
                        info2.setUserDefine_Value(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        userdefind2.setUserDefine(info2);
                        userDefines.add(userdefind2);

                        // 店铺名称
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo info3 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userdefind3 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                        info3.setUserDefine_No("3");
                        info3.setUserDefine_Value(ch.getName());
                        userdefind3.setUserDefine(info3);
                        userDefines.add(userdefind3);

                        // 快递单号
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo info4 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userdefind4 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                        info4.setUserDefine_No("4");
                        info4.setUserDefine_Value(orderinfo.getTransNo() == null ? "" : orderinfo.getTransNo());
                        userdefind4.setUserDefine(info4);
                        userDefines.add(userdefind4);

                        // Cod代收金额
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userdefind5 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                        com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo info5 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                        info5.setUserDefine_No("5");
                        if (orderinfo.getIsCodOrder() != null && orderinfo.getIsCodOrder()) {
                            info5.setUserDefine_Value((orderinfo.getTotalActual().add(orderinfo.getTransferFee()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
                        } else {
                            info5.setUserDefine_Value(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        }
                        userdefind5.setUserDefine(info5);
                        userDefines.add(userdefind5);
                        ordhd.setUserDefines(userDefines);
                        List<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo> orderInfos = new ArrayList<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo>();
                        if (isNeedInvoice) {
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo infovv = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo();
                            staInvoices = staInvoiceDao.getBySta(sta.getId());
                            for (StaInvoice staInvoice : staInvoices) {
                                // 需要发票
                                // 查询invoiceTax
                                int j = 1;

                                com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfoDetail infoDetail = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfoDetail();
                                infoDetail.setOrderInfo_No("" + j);
                                infoDetail.setOrderInfo_Type("");
                                infoDetail.setOrderInfo_Amount(staInvoice.getAmt());// A
                                // F
                                // 要加运费
                                infoDetail.setOrderInfo_Title(staInvoice.getPayer());
                                infoDetail.setOrderInfo_Content("服装//配件");
                                infovv.setOrderInfo(infoDetail);
                                orderInfos.add(infovv);
                            }
                        } else {
                            // 无需发票
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo orderinfoc = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfo();
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfoDetail infoDetail = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.OrderInfoDetail();
                            infoDetail.setOrderInfo_No("1");
                            // info.setOrderInfo_Type(salesOrder.getInvoiceTypeInt()==1?"普通商业零售":" 增值税专用");
                            infoDetail.setOrderInfo_Amount(new BigDecimal(0));
                            infoDetail.setOrderInfo_Title("");
                            infoDetail.setOrderInfo_Content("");
                            orderinfoc.setOrderInfo(infoDetail);
                            orderInfos.add(orderinfoc);
                        }
                        ordhd.setOrderInformation(orderInfos);
                        ordhdList.add(ordhd);


                        List<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.ORDDT> orddtList = new ArrayList<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.ORDDT>();
                        List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(orderinfo.getId());
                        int i = 1;
                        for (MsgOutboundOrderLine line : lines) {
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.ORDDT orddt = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.ORDDT();
                            orddt.setExternLineNo("0000" + i);
                            // String sku =
                            // WarehouseSourceSkuType.getSkuByWhSourceSkuType(line.getSku(),
                            // skuType);
                            // 此处未维护类型时 暂时默认为 ext_code2
                            orddt.setSKU(line.getSku().getExtensionCode1() == null ? line.getSku().getBarCode() : line.getSku().getExtensionCode1());
                            orddt.setOpenQty(line.getQty().intValue());
                            if (line.getTotalActual() == null) {
                                orddt.setUnitPrice(new BigDecimal("0.00"));
                            } else {
                                orddt.setUnitPrice(line.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            List<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine> userDefineList = new ArrayList<com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine>();
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userDefine = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo userDefineinfo = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                            userDefineinfo.setUserDefine_No("1");
                            userDefineinfo.setUserDefine_Value(line.getSkuName() == null ? "" : line.getSkuName());
                            userDefine.setUserDefine(userDefineinfo);
                            userDefineList.add(userDefine);

                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine userDefine2 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefine();
                            com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo userDefineinfo2 = new com.jumbo.wms.model.vmi.ids.BaozunOrderRequest.UserDefineInfo();
                            userDefineinfo2.setUserDefine_No("2");
                            if (line.getSku() != null) {
                                userDefineinfo2.setUserDefine_Value(line.getSku().getListPrice() == null ? new BigDecimal("0.00").toString() : line.getSku().getListPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                            } else {
                                userDefineinfo2.setUserDefine_Value(new BigDecimal("0.00").toString());
                            }
                            userDefine2.setUserDefine(userDefineinfo2);
                            userDefineList.add(userDefine2);
                            orddt.setUserDefines(userDefineList);
                            orddtList.add(orddt);
                            i++;
                        }
                        ordhd.setORDDT(orddtList);

                        // 更新批次号和状态
                        // msgOutboundOrderDao.updateBatchNoByID(batchNo,
                        // DefaultStatus.SENT.getValue(), orderinfo.getId());
                    } else {
                        msgOutboundOrderDao.updateBatchNoByID(batchNo, DefaultStatus.INEXECUTION.getValue(), orderinfo.getId());
                    }
                } catch (BusinessException ex) {
                    log.error("===>IDS  createOrderRequestStr error sta is: {}", errorSta);
                    log.error("===>IDS  createOrderRequestStr error:", ex);
                }
            }
            wmsorder.setOrderHeader(ordhdList);
            wmsorder.setBatchID(String.valueOf(batchNo));
        }
        return wmsorder;

    }

    @Override
    public String inboundIdsNike(String source, IdsServerInformation idsServerInformation) {
        List<MsgInboundOrder> inboundList = null;
        Long batchNo = 0l;
        batchNo = msgInboundOrderDao.findInOrderBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        inboundList = msgInboundOrderDao.findMsgReturnInboundByStatusToLF(source, null, StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        WmsAsn wmsAsn = new WmsAsn();
        wmsAsn.setBatchID(batchNo.toString());
        List<WmsAsn.ASNHD> asnhdList = new ArrayList<ASNHD>();
        for (MsgInboundOrder msginorder : inboundList) {
            StockTransApplication sta = staDao.getByCode(msginorder.getStaCode());
            if (sta == null) {
                continue;
            }
            WmsAsn.ASNHD asnhd = new WmsAsn.ASNHD();
            asnhd.setInterfaceActionFlag("A");
            asnhd.setStorerKey(idsServerInformation.getStorerKey());
            asnhd.setFacility(idsServerInformation.getFacility2());// AF退货要退到残次仓
            asnhd.setExternReceiptKey(msginorder.getStaCode());
            asnhd.setWarehouseReference(msginorder.getRefSlipCode() == null ? "" : msginorder.getRefSlipCode());
            // 推送寄回快递单号
            StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            if (info != null) {
                asnhd.setVehicleNumber(info.getTrackingNo() == null ? "" : info.getTrackingNo());
            } else {
                asnhd.setVehicleNumber("");
            }
            Carrier carrier = new Carrier();
            carrier.setCarrierKey(msginorder.getLpCode() == null ? "" : msginorder.getLpCode());
            carrier.setCarrierName("BAOZUN ASN");
            carrier.setCarrierZip("");
            carrier.setCarrierState("");
            carrier.setCarrierCity("");
            carrier.setCarrierAddress1("");
            carrier.setCarrierAddress2("");
            asnhd.setCarrier(carrier);
            asnhd.setDocType("R");
            if (null != source && Constants.VIM_WH_SOURCE_NEWLOOK_IDS.equals(source)) {
                asnhd.setNotes(info.getRemark() == null ? "" : info.getRemark());
            } else {
                asnhd.setNotes(msginorder.getRemark() == null ? "" : msginorder.getRemark());
            }
            List<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine> userDefines = new ArrayList<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine>();

            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo1 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo1.setUserDefine_No("1");
            if (StringUtils.hasText(sta.getSlipCode2())) {
                if (sta.getSlipCode2().indexOf("RAS") != -1 || sta.getSlipCode2().indexOf("EAS") != -1) {
                    userDefineinfo1.setUserDefine_Value(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());
                } else {
                    userDefineinfo1.setUserDefine_Value(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());
                }
            } else {
                RelationNike relationNike = relationNikeDao.findSysByPid(sta.getRefSlipCode());
                if (relationNike != null) {
                    userDefineinfo1.setUserDefine_Value(relationNike.getEnPid());
                } else {
                    userDefineinfo1.setUserDefine_Value(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());
                }
                // userDefineinfo1.setUserDefine_Value(sta.getRefSlipCode() == null ? "" :
                // sta.getRefSlipCode());
            }
            userDefine.setUserDefine(userDefineinfo1);
            userDefines.add(userDefine);


            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine2 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo2 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo2.setUserDefine_No("2");
            userDefineinfo2.setUserDefine_Value(msginorder.getMobile() == null ? "" : msginorder.getMobile());
            userDefine2.setUserDefine(userDefineinfo2);
            userDefines.add(userDefine2);


            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine3 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
            com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo3 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
            userDefineinfo3.setUserDefine_No("3");
            userDefineinfo3.setUserDefine_Value(msginorder.getTelephone() == null ? "" : msginorder.getTelephone());
            userDefine3.setUserDefine(userDefineinfo3);
            userDefines.add(userDefine3);
            asnhd.setUserDefines(userDefines);
            if (null != source && (Constants.VIM_WH_SOURCE_NBAUA_IDS.equals(source) || Constants.VIM_WH_SOURCE_UA_IDS.equals(source)) || (Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS.equals(source))) {
                BiChannel channel = biChannelDao.getByCode(sta.getOwner());
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefine4 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo4 = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
                userDefineinfo4.setUserDefine_No("4");
                userDefineinfo4.setUserDefine_Value(channel.getName());
                userDefine4.setUserDefine(userDefineinfo4);
                userDefines.add(userDefine4);
            }
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msginorder.getId());
            List<ASNDT> asndtList = new ArrayList<ASNDT>();
            int i = 1;
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                ASNDT asndt = new ASNDT();
                asndt.setExternLineNo("000" + i);
                if (Constants.VIM_WH_SOURCE_GODIVA_HAVI.equals(source)) {
                    asndt.setSKU(inLinecomd.getSku().getBarCode() == null ? "" : inLinecomd.getSku().getBarCode());
                } else if (Constants.VIM_WH_SOURCE_CONVERSE_IDS.equals(source)) {
                    String sku = null;
                    if(wh!=null&&wh.getWhSourceSkuType()!=null) {
                        sku = WarehouseSourceSkuType.getSkuByWhSourceSkuType(inLinecomd.getSku(), wh.getWhSourceSkuType());
                    }
                    asndt.setSKU(sku == null ? inLinecomd.getSku().getExtensionCode2() : sku);
                } else {
                    asndt.setSKU(inLinecomd.getSku().getExtensionCode1() == null ? "" : inLinecomd.getSku().getExtensionCode1());

                }
                asndt.setQtyExpected(String.valueOf(inLinecomd.getQty()) == null ? "" : String.valueOf(inLinecomd.getQty()));
                asndt.setUnitPrice(inLinecomd.getUnitPrice() == null ? new BigDecimal(0) : inLinecomd.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP));


                String vmiStatus = "";
                if (inLinecomd.getInvStatus() != null) {
                    vmiStatus = msgInventoryStatusDao.findInventoryStatusByBzStatus(inLinecomd.getInvStatus().getId(), source, new SingleColumnRowMapper<String>(String.class));
                }

                List<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine> userLines = new ArrayList<com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine>();
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine userDefineLine = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefine();
                com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo userDefineinfo = new com.jumbo.wms.model.vmi.ids.WmsAsn.UserDefineInfo();
                userDefineinfo.setUserDefine_No("1");
                userDefineinfo.setUserDefine_Value(vmiStatus == null ? "" : vmiStatus);
                userDefineLine.setUserDefine(userDefineinfo);
                userLines.add(userDefineLine);
                asndt.setUserDefines(userLines);
                asndtList.add(asndt);

                i++;
            }
            asnhd.setASNDT(asndtList);

            asnhdList.add(asnhd);
            msgInboundOrderDao.updateInboundOrder(batchNo, DefaultStatus.SENT.getValue(), msginorder.getId());
        }
        String respXml = null;
        if (asnhdList.size() > 0) {
            wmsAsn.setASNHD(asnhdList);
            respXml = (String) MarshallerUtil.buildJaxb(wmsAsn);
        }

        return respXml;
    }

    public synchronized void findAEOOwnerCode() {
        if (aeoOwnerCode.size() == 0) {
            aeoOwnerCode = chooseOptionManager.getOptionByCategoryCode("aeoOwnerCode");
        }

    }
}
